package cn.seagen.base.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.seagen.base.bean.Waybill;
import cn.seagen.base.dao.WaybillDao;
import cn.seagen.base.service.WaybillService;
import cn.seagen.base.utils.QueryUtils;
import cn.seagen.base.vo.EasyuiGridData;

@Service
public class WaybillServiceImpl implements WaybillService {

	@Resource
	private WaybillDao waybillDaoImpl;
	
	@Override
	public boolean insertWaybill(List<Waybill> list) {
		return waybillDaoImpl.insertWaybill(list);
	}

	@Override
	public Object findWaybillList(int page, int rows, String waybill_code) {
		EasyuiGridData easyuiGridData = new EasyuiGridData();
		//获取数量的sql
		String sql = QueryUtils.formatQueryWaybillNumSql(waybill_code);
		//判断sql语句是否正常
		if(StringUtils.isEmpty(sql))
			return easyuiGridData;
		int total = waybillDaoImpl.findWaybillListNum(sql);
		//设置页面总数
		easyuiGridData.setTotal(total);
		//获取数据列表的sql
		sql = QueryUtils.formatQueryWaybillSql(waybill_code, page, rows);
		if(StringUtils.isEmpty(sql))
			return easyuiGridData;
		List<Map<String, Object>> list= waybillDaoImpl.findWaybillList(sql);
		//设置页面显示数据
		easyuiGridData.setRows(list);
		return easyuiGridData;
	}

	@Override
	public Waybill findWaybillTrace(String waybillCode) {
		return waybillDaoImpl.findWaybillTrace(waybillCode);
	}
}
