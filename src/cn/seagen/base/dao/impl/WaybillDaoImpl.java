package cn.seagen.base.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.seagen.base.bean.Waybill;
import cn.seagen.base.dao.ScanDao;
import cn.seagen.base.dao.SortedDao;
import cn.seagen.base.dao.WaybillDao;
import cn.seagen.base.redis.JedisService;
import cn.seagen.base.utils.QueryUtils;

@Component
public class WaybillDaoImpl implements WaybillDao {

	private Logger logger = LogManager.getLogger(WaybillDaoImpl.class.getName());
	@Resource
	private JdbcTemplate jdbcTemplate;
	@Resource
	private SortedDao sortedDaoImpl;
	
	@Resource
	private ScanDao scanDaoImpl;
	
	public void setSortedDaoImpl(SortedDao sortedDaoImpl) {
		this.sortedDaoImpl = sortedDaoImpl;
	}

	public void setScanDaoImpl(ScanDao scanDaoImpl) {
		this.scanDaoImpl = scanDaoImpl;
	}

	@Override
	public boolean insertWaybill(final List<Waybill> list) {
		boolean res = false;
		String sql = "insert into waybill(waybill_id,waybill_code,exp_code,waybill_status," +
				"site_code,sort_routing,waybill_time) " +
				"values(?,?,?,?,?,?,?)";
		try {
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					int tag = 0;
					Waybill s = list.get(i);
					ps.setString(++tag, s.getWaybill_id());
					ps.setString(++tag, s.getWaybill_code());
					ps.setString(++tag, s.getExp_code());
					ps.setInt(++tag, s.getWaybill_status());
					ps.setString(++tag, s.getSite_code());
					ps.setString(++tag, s.getSerialno());
					ps.setString(++tag, s.getWaybill_time());
				}
				public int getBatchSize() {
					return list.size();
				}
			});
			res = true;
		} catch (Exception e) {
			logger.error("insertWaybill:" + e.getMessage(), e);
			throw new RuntimeException("添加原始运单入库失败："+e);
		} 
		return res;
	}

	@Override
	public List<Map<String, Object>> findWaybillList(String sql) {
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		try {
			dataList = jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			logger.error("findWaybillList:" + e.getMessage(), e);
		}
		return dataList;
	}

	@Override
	public int findWaybillListNum(String sql) {
		try {
			List<Integer> list = jdbcTemplate.queryForList(sql, Integer.class);
			if (list!=null&&list.size()>0) {
				return list.get(0);
			}
		} catch (Exception e) {
			logger.error("findWaybillListNum error",e);
		}
		return 0;
	}

	/** 查询最新分拣记录,用于分析最新的运单操作轨迹 */
	@Override
	public Waybill findWaybillTrace(String waybillCode) {
		
		JedisService jedis = new JedisService();
		//存储预下格口号
		StringBuffer chute=new StringBuffer("");
		
		Waybill wb = null;
		List<Map<String, Object>> dataList= new ArrayList<Map<String, Object>>();
		
		String sql = QueryUtils.formatQueryWaybillSql(waybillCode,1,1);
		dataList = findWaybillList(sql);
		if(!dataList.isEmpty()){
			Map<String, Object> map = dataList.get(0);
			wb = new Waybill();
			wb.setReceive_time(map.get("create_time").toString());  //运单接收时间
			String siteCode = map.get("site_code").toString();
			wb.setSite_code(siteCode);     //格口站点编码
			
			List<Object> chutelist= new ArrayList<Object>();
			chutelist = jedis.getList("[sitecode]"+siteCode);
			if(null != chutelist&&!chutelist.isEmpty()){
				for(Object str:chutelist){
					chute.append(str.toString()+",");//添加找到的格口号
				}
			}
			wb.setRe_mark((chute.length()>0)?chute.deleteCharAt(chute.length()-1).toString():"分拣方案无站点格口");    //预分配目标格口
			wb.setWaybill_status(Integer.parseInt(map.get("waybill_status").toString()));             //运单状态
			
			
			//获取分拣数据列表的sql
			sql = QueryUtils.formatQuerySortedSql(waybillCode, "", 1, 1);
			dataList = sortedDaoImpl.findSortedList(sql);	
			if(!dataList.isEmpty()){   //分拣记录为空就去查扫描纪录
				//查找分拣记录
				Map<String, Object> sortedmap = dataList.get(0);
				wb.setSupply_time(String.valueOf(sortedmap.get("supply_time")));   //导入台上件时间
				wb.setSupply_num(String.valueOf(sortedmap.get("supply_id")));     //几号导入台上件
				
				wb.setScan_time(String.valueOf(sortedmap.get("scan_time")));                          //龙门架扫描时间
				wb.setScan_cycle(Integer.parseInt(sortedmap.get("scan_cycle").toString()));      //扫描次数
				wb.setScan_status(Integer.parseInt(sortedmap.get("scan_status").toString()));    //扫描状态
				
				wb.setSorting_time(String.valueOf(sortedmap.get("sorting_time")));                       //分拣时间
				wb.setChute_num(String.valueOf(sortedmap.get("chute_id")));                             //分拣落格格口号
				wb.setSite_code(String.valueOf(sortedmap.get("site_code")));                            //站点编码
				wb.setSorting_status(Integer.parseInt(sortedmap.get("sorting_status").toString())); //分拣状态
				
				wb.setJb_time(String.valueOf(sortedmap.get("jb_time")));                            //建包时间
//				wb.setSite_code(String.valueOf(sortedmap.get("site_code")));                        //站点编码
				wb.setBox_code(String.valueOf(sortedmap.get("box_code")));                          //箱号
				wb.setJb_status(Integer.parseInt(sortedmap.get("jb_status").toString()));      //建包状态
				
				wb.setJb_update_time(String.valueOf(sortedmap.get("jb_update_time")));                    //上传时间
//				wb.setSite_code(String.valueOf(sortedmap.get("site_code")));                        //站点编码
//				wb.setBox_code(String.valueOf(sortedmap.get("box_code")));                                   //箱号
				wb.setJb_update_flag(Integer.parseInt(String.valueOf(sortedmap.get("jb_update_flag"))));    //上传状态
			}else{
				//获取扫描数据列表的sql
				sql = QueryUtils.formatQueryScanSql(waybillCode, 1, 1);
				List<Map<String, Object>> scanList = scanDaoImpl.findScanList(sql);	
				if(!scanList.isEmpty()){
					Map<String, Object> scanmap = dataList.get(0);
					wb.setSupply_time(String.valueOf(scanmap.get("supply_time")));   //导入台上件时间
					wb.setSupply_num(String.valueOf(scanmap.get("supply_id")));     //几号导入台上件
					
					wb.setScan_time(String.valueOf(scanmap.get("scan_time")));                          //龙门架扫描时间
					wb.setScan_cycle(Integer.parseInt(scanmap.get("scan_cycle").toString()));      //扫描次数
					wb.setScan_status(Integer.parseInt(scanmap.get("scan_status").toString()));    //扫描状态
					
					wb.setSorting_time(null);   	// 分拣时间
					wb.setSorting_status(-1);       //分拣状态
					wb.setJb_status(-1);
					wb.setJb_update_time(null);     //数据上传更新时间
					wb.setJb_update_flag(-1);
				}
			}
		}
		return wb;
	}
}
