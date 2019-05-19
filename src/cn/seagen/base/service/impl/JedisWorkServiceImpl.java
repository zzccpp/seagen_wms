package cn.seagen.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.seagen.base.bean.SortschemeDetail;
import cn.seagen.base.bean.Waybill;
import cn.seagen.base.constant.RedisConstant;
import cn.seagen.base.redis.JedisService;
import cn.seagen.base.service.JedisWorkService;
import cn.seagen.base.utils.JUtils;
/**
 * redis 业务处理，运单信息进行分库存储，分别存入1到10库，其他信息如分拣方案则存0库
 * @author Adians
 *
 */
@Service
public class JedisWorkServiceImpl implements JedisWorkService {
	private static Logger logger = LogManager.getLogger(JedisWorkServiceImpl.class.getName());
	@Resource
	private JedisService jedisServiceImpl;
	
	@Override
	public boolean insertWaybillToRedis(List<Waybill> wayBillList) {
		int index = 0;
		for (Waybill wb : wayBillList) {
			// 非正常订单设置的value,分配到异常格口
			String siteCode = (wb.getWaybill_status() == 0) ? wb.getSite_code() : "0000";
			String tranceId = String.valueOf(wb.getWaybill_id());
			String waybillCode = wb.getWaybill_code();
			if(JUtils.isEmpty(waybillCode))
				continue;
			index = getRedisIndex(waybillCode);
			String value = "[" + siteCode + "][" + tranceId + "]["
					+ wb.getWaybill_time() + "][" + wb.getExp_code() + "]";
			String key = RedisConstant.WAYBILL + waybillCode;
			jedisServiceImpl.setStringExpire(key, value, index); // 带超时时间

			String val = jedisServiceImpl.getString(key, index);
			if (JUtils.isEmpty(val) || (!val.equals(value))) {
				logger.error("写入redis失败:" + wb.getWaybill_code());
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean insertSitecodeToRedis(List<SortschemeDetail> schemeList) {
		
		for (SortschemeDetail scheme : schemeList) {
			String key = RedisConstant.SITECODE + scheme.getSite_code();
			String value = scheme.getChute_num();
			jedisServiceImpl.setList(key, value);
			List<Object> list = new ArrayList<Object>();
			list = jedisServiceImpl.getList(key);
			if (!list.contains(value))
				logger.error("写入redis->sitecode失败:" + scheme.getSite_code());
		}
		return true;
	}
	
	@Override
	public boolean deleteSitecodeFromRedis(List<SortschemeDetail> schemeList) {
		for (SortschemeDetail scheme : schemeList) {
			String key = RedisConstant.SITECODE + scheme.getSite_code();
			jedisServiceImpl.delkey(key);
		}
		return true;
	}
	
	/**
	 * 分库写入redis
	 * @param waybillCode 以条码最后一位数字作为库索引 ,1-9对应1-9库，0为10库
	 * @return dbIndex,1到10库
	 */
	private int getRedisIndex(String waybillCode){
		int len = waybillCode.length();
		int index = 10;
		if(len > 0){
			index = JUtils.StrToIntDef(waybillCode.substring(len - 1, len), 10);
			index = index == 0?10:index;
		}
		return index;
	}
	
	
	
	
	public static void main(String[] args) {
		System.out.println(new JedisWorkServiceImpl().getRedisIndex("9"));
	}

	
}
