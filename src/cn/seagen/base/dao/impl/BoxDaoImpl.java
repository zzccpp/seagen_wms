package cn.seagen.base.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.seagen.base.bean.BoxBean;
import cn.seagen.base.dao.BoxDao;
@Component
public class BoxDaoImpl implements BoxDao {
	private Logger logger = LogManager.getLogger(BoxDaoImpl.class.getName());
	@Resource
	private JdbcTemplate jdbcTemplate;
	@Override
	public BoxBean getUnusedBox(int type, String val) {
		String sql = "select `f_recno`, `box_code`, `box_type`, `from_site_name`, "//
				+ " `to_site_code`, `to_site_name`, `mixing_box_type`, `opt_code`, "//
				+ " `cate_gory`, `carriage_router`, `carriage_router_num` from box " //
				+ " where use_flag = 0 ";
		switch (type) {
		case 1:
			sql += " and to_site_code = '" + val + "'";
			break;
		case 2:
			sql += " and box_type = '" + val + "'";
			break;
		default:
			break;
		}
		sql += " order by f_recno limit 1";
		try {
			List<BoxBean> list= jdbcTemplate.query(sql,new BeanPropertyRowMapper<BoxBean>(BoxBean.class));
			if(null == list || list.size()<=0)
				return null;
			return list.get(0);
		} catch (Exception e) {
			logger.error("getUnused Box  error:"+e.getMessage(),e);
			return null;
		}
	}
	@Override
	public boolean lockUnusedBox(long boxId) {
		String sql = "update box set use_flag = 3 where f_recno = " + boxId;
		try {
			int res = jdbcTemplate.update(sql);
			return (res > 0);
		} catch (Exception e) {
			logger.error("lockUnusedBox error:"+e.getMessage(),e);
			return false;
		}
		
	}
	@Override
	public boolean updateBox(BoxBean box) {
		String sql = "update box set `batch_id` = ?, `box_type` = ?,"
				+ "`opt_code` = ?, `package_count` = ?, `package_weight` = ?,"
				+ "`print_status` = ?, `printer_ip` = ?, `print_cmd` = ?,"
				+ "`print_time` = ?, `chute_id` = ?, `chute_num` = ?, `use_flag` = ?,"
				+ "`update_flag` = ?, `update_time` = ? where `box_code` = ?"
				+ "order by f_recno desc limit 1;";
		try {
			int res = jdbcTemplate.update(sql,box.getBatch_id(),box.getBox_type(),
					box.getOpt_code(),box.getPackage_count(),box.getPackage_weight(),
					box.getPrint_status(),box.getPrinter_ip(),box.getPrint_cmd(),
					box.getPrint_time(),box.getChute_id(),box.getChute_num(),box.getUse_flag(),
					box.getUpdate_flag(),box.getUpdate_time(),box.getBox_code());
			return (res>0);
		} catch (Exception e) {
			logger.error("updateBox error:"+e.getMessage(),e);
			return false;
		}
	}
	@Override
	public boolean insertBoxTemp(BoxBean box) {
		String sql = "insert box_temp(print_time,print_time_long,chute_id,batch_id) values(?,unix_timestamp(?),?,?)";
		jdbcTemplate.update(sql,box.getPrint_time(),box.getPrint_time(),box.getChute_id(),box.getBatch_id());
		return false;
	}

}
