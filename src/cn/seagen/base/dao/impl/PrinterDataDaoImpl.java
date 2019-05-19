package cn.seagen.base.dao.impl;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import cn.seagen.base.bean.BoxBean;
import cn.seagen.base.dao.PrinterDataDao;
import cn.seagen.base.utils.JUtils;
import cn.seagen.base.utils.SqlUtils;
@Component
public class PrinterDataDaoImpl implements PrinterDataDao {
	@Resource
	private JdbcTemplate template;
	
	@Override
	public boolean insertPrinterData(final BoxBean box) {
		String sql = "insert into printer_data(`printer_ip`, `chute_id`, `chute_num`, `box_code`, "//
				+ " `box_type`, `mixing_box_type`, `cate_gory`, `carriage_rounter`, `carriage_router_num`, "//
				+ " `package_count`, `package_weight`, `site_name`, `site_code`, `print_cmd`, `print_imag`) values(%s)";
		sql = SqlUtils.formatSqlPargram(sql, 15);
		int res = template.update(sql,new PreparedStatementSetter(){
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				int tag = 0;
				ps.setString(++tag, box.getPrinter_ip());// 打印机IP
				ps.setInt(++tag, box.getChute_id());// 格口物理编号
				ps.setString(++tag, box.getChute_num());// 格口逻辑编号
				ps.setString(++tag, box.getBox_code()); // 箱号
				ps.setString(++tag, box.getBox_type()); // 箱号类型BC
				ps.setString(++tag, box.getMixing_box_type()); // 是否混箱
				ps.setString(++tag, box.getCate_gory());// 运输方式 公，铁，航
				ps.setString(++tag, box.getCarriage_router());// 运输路由(字符数组，用逗号隔开)
				ps.setString(++tag, box.getCarriage_router_num());// 运输路由编号
				ps.setInt(++tag, box.getPackage_count());// 箱中包裹数量
				ps.setInt(++tag, box.getPackage_weight());// 箱中包裹总重量
				ps.setString(++tag, box.getTo_site_name());// 目的地名称'
				ps.setString(++tag, box.getTo_site_code());// 目的地代码
				ps.setString(++tag, box.getPrint_cmd());// 打印机命令
				try {
					InputStream imagStream = JUtils.StringTOInputStream(box.getPrint_imag());
					ps.setBinaryStream(++tag, imagStream, imagStream.available()); // 第二个参数为文件的内容
				} catch (Exception e) {
					ps.setBinaryStream(++tag, null, 0); // 第二个参数为文件的内容
				}
			}
			
		});
		return res >0 ;
	}

	@Override
	public List<Map<String,Object>> queryPrinterData(int page, int rows, String boxCode,
			int chuteId) {
		List<Map<String, Object>> printerDatas = new ArrayList<Map<String, Object>>();
		int startIndex = (page -1)*rows;
		if(startIndex < 0)
			startIndex = 0;
		String whereStr = "";
		String sql = "select f_recno,printer_ip,chute_id as chuteId,chute_num as chuteNum,box_code as boxCode,package_count as packageCount,print_cmd as printCmd,date_format(create_time, '%Y-%m-%d %H:%i') as createTime from printer_data ";
		if(JUtils.isEmpty(boxCode) == false)
			whereStr = "where box_code = '" + boxCode+"'";
		
		if((JUtils.isEmpty(whereStr) == false) &&chuteId > 0)
			whereStr = whereStr+ "and chute_id = " + chuteId;
		
		if(JUtils.isEmpty(whereStr)&&chuteId > 0)
			whereStr = whereStr+ "where chute_id = " + chuteId;
		
		sql = sql + whereStr + " order by create_time desc limit "+startIndex+" ,"+rows+" ";
		try {
			printerDatas = template.queryForList(sql);
		} catch (Exception e) {
			
		}
		return printerDatas;
	}

	@Override
	public int queryPrinterDataCount(String boxCode, int chuteId) {
		int count = 0;
		String sql = "select count(f_recno) from printer_data "; 
		String whereStr = "";
		if(JUtils.isEmpty(boxCode) == false)
			whereStr = "where box_code = '" + boxCode+"'";
		
		if((JUtils.isEmpty(whereStr) == false) &&chuteId > 0)
			whereStr = whereStr+ "and chute_id = " + chuteId;
		
		if(JUtils.isEmpty(whereStr)&&chuteId > 0)
			whereStr = whereStr+ "where chute_id = " + chuteId;
		
		sql = sql + whereStr;
		try {
			count = template.queryForObject(sql, Integer.class);
		} catch (Exception e) {
		}
		
		return count;
	}

}
