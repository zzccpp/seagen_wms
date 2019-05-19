package cn.seagen.base.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.seagen.base.bean.Printer;
import cn.seagen.base.dao.PrinterDao;

@Component
public class PrinterDaoImpl implements PrinterDao {

	private Logger logger = LogManager.getLogger(PrinterDaoImpl.class.getName());
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<Printer> getPrinters() {
		List<Printer> printerList = new ArrayList<Printer>();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = "select f_recno, printer_num, printer_ip, chute_num_list, re_mark, " 
				+ " date_format(create_time,'%Y-%m-%d %H:%i:%s') as create_time, " 
				+ " date_format(modify_time,'%Y-%m-%d %H:%i:%s') as modify_time "
				+ " from printer order by f_recno";
		try {
			list = jdbcTemplate.queryForList(sql);
			if(list.size()>0){
				for(Map<String,Object> map:list){
					Printer setVal = new Printer();
					int index = Integer.parseInt(map.get("f_recno").toString());
					setVal.setfRecno(index);
					String val = String.valueOf(map.get("printer_num"));
					if (val == null)
						val = "";
					val = val.trim();
					setVal.setPrinterNum(val);
					setVal.setPrinterIp(String.valueOf(map.get("printer_ip")));
					setVal.setChuteNumList(String.valueOf(map.get("chute_num_list")));
					setVal.setReMark(String.valueOf(map.get("re_mark")));
					setVal.setCreateTime(String.valueOf(map.get("create_time")));
					setVal.setModifyTime(String.valueOf(map.get("modify_time")));
					printerList.add(setVal);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getPrinters fail：" + e.getMessage(), e);
		} 
		return printerList;
	}

	@Override
	public boolean updatePrinter(Printer printer) {
		boolean res = false;
		if(printer == null) {
			return false;
		}
		String sql = "update printer set printer_num=?,printer_ip=?,chute_num_list=?,re_mark=? where f_recno=?";
		try {
			int flag =jdbcTemplate.update(sql,printer.getPrinterNum(),printer.getPrinterIp(),printer.getChuteNumList(),printer.getReMark(),printer.getfRecno());
			res =  (flag > 0);
		} catch (Exception e) {
			logger.error("updatePrinter异常:" + e);
			throw new RuntimeException("更新打印机配置失败："+sql);
		}
		return res;
	}

	@Override
	public boolean insertPrinter(Printer printer) {
		boolean res = false;
		String sql = "insert into printer (printer_num,printer_ip,chute_num_list,re_mark)" + " values(?,?,?,?)";
		try {
			Object[] args = {printer.getPrinterNum(),printer.getPrinterIp(),printer.getChuteNumList(),printer.getReMark()};
			int result = jdbcTemplate.update(sql, args);
			res = result>0;
		} catch (Exception e) {
			logger.error("insertPrinter:" + e.getMessage(), e);
			res = false;
			throw new RuntimeException("添加打印机列表入库失败："+e);
		} 
		return res;
	}

	@Override
	public boolean deletePrinter(int fRecno) {
		boolean res = false;
		String sql = "delete from printer where f_recno = "+fRecno;
		try {
			res = jdbcTemplate.update(sql)>0;
		} catch (Exception e) {
			logger.error("deletePrinter:" + e.getMessage(), e);
			res = false;
			throw new RuntimeException("删除打印机配置失败："+e);
		}
		return res;
	}

	@Override
	public boolean checkPrinterNum(String printerNum, int fRecno) {
		boolean res = false;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = "";
		if (-1 == fRecno) {// 添加
			sql = "select * from printer where printer_num='" + printerNum + "'";
		} else {// 修改
			sql = "select * from printer where printer_num='" + printerNum + "' and f_recno != "+fRecno;		}
		try {
			list = jdbcTemplate.queryForList(sql);
			if(!list.isEmpty())
				res = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("checkPrinterNum fail：" + e.getMessage(), e);
		}
		return res;
	}

	@Override
	public String get_printer_ip(int chute_id) {
		String ip = null;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String sql = "select printer_ip from printer where chute_num_list like '%," + String.valueOf(chute_id) + ",%' order by f_recno limit 1;";
		try {
			list = jdbcTemplate.queryForList(sql);
			if(list.size()>0){
				for(Map<String,Object> map:list){
					ip= String.valueOf(map.get("printer_ip"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取对应格口的打印机IP:" + e.getMessage(), e);
		}
		return ip;
	}

}
