package cn.seagen.base.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.seagen.base.bean.ChuteArea;
import cn.seagen.base.dao.ChuteAreasDao;

@Component
public class ChuteAreasDaoImpl implements ChuteAreasDao {

	private Logger logger = LogManager.getLogger(ChuteAreasDaoImpl.class.getName());
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<ChuteArea> getChuteAreas() {  
		List<ChuteArea> chuteAreaList = new ArrayList<ChuteArea>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = "select f_recno, area_name, chute_num_list, re_mark, " 
				+ " date_format(create_time,'%Y-%m-%d %H:%i:%s') as create_time, " 
				+ " date_format(modify_time,'%Y-%m-%d %H:%i:%s') as modify_time "
				+ " from chute_area order by f_recno";
		try {
			list = jdbcTemplate.queryForList(sql);
			if(list.size()>0){
				for(Map<String,Object> map:list){
					ChuteArea setVal = new ChuteArea();
					int index = Integer.parseInt(map.get("f_recno").toString());
					setVal.setfRecno(index);
					String val = String.valueOf(map.get("area_name"));
					if (val == null)
						val = "";
					val = val.trim();
					setVal.setAreaName(val);
					setVal.setChuteNumList(String.valueOf(map.get("chute_num_list")));
					setVal.setReMark(String.valueOf(map.get("re_mark")));
					setVal.setCreateTime(String.valueOf(map.get("create_time")));
					setVal.setModifyTime(String.valueOf(map.get("modify_time")));
					chuteAreaList.add(setVal);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getPrinters fail：" + e.getMessage(), e);
		} 
		return chuteAreaList;
	}

	@Override
	public boolean updateChuteArea(ChuteArea chuteArea) {
		boolean res = false;
		if(chuteArea == null) {
			return false;
		}
		String sql = "update chute_area set area_name=?,chute_num_list=?,re_mark=? where f_recno=?";
		try {
			int flag =jdbcTemplate.update(sql,chuteArea.getAreaName(),chuteArea.getChuteNumList(),chuteArea.getReMark(),chuteArea.getfRecno());
			res =  (flag > 0);
		} catch (Exception e) {
			logger.error("updateChuteArea异常:" + e);
			throw new RuntimeException("更新格口区域配置失败："+sql);
		}
		return res;
	}

	@Override
	public boolean insertChuteArea(ChuteArea chuteArea) {
		boolean res = false;
		String sql = "insert into chute_area (area_name,chute_num_list,re_mark)" + " values(?,?,?)";
		try {
			Object[] args = {chuteArea.getAreaName(),chuteArea.getChuteNumList(),chuteArea.getReMark()};
			int result = jdbcTemplate.update(sql, args);
			res = result>0;
		} catch (Exception e) {
			logger.error("insertChuteArea error:" + e.getMessage(), e);
			res = false;
			throw new RuntimeException("添加格口区域表入库失败："+e);
		} 
		return res;
	}

	@Override
	public boolean deleteChuteArea(int fRecno) {
		boolean res = false;
		String sql = "delete from chute_area where f_recno = "+fRecno;
		try {
			res = jdbcTemplate.update(sql)>0;
		} catch (Exception e) {
			logger.error("deleteChuteArea error:" + e.getMessage(), e);
			res = false;
			throw new RuntimeException("删除格口区域配置失败："+e);
		}
		return res;
	}

	@Override
	public boolean checkChuteArea(String areaName, int fRecno) {
		boolean res = false;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = "";
		if (-1 == fRecno) {// 添加
			sql = "select * from chute_area where area_name='" + areaName + "'";
		} else {// 修改
			sql = "select * from chute_area where area_name='" + areaName + "' and f_recno != "+fRecno;		}
		try {
			list = jdbcTemplate.queryForList(sql);
			if(!list.isEmpty())
				res = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("checkChuteArea error：" + e.getMessage(), e);
		}
		return res;
	}
}
