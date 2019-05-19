package cn.seagen.base.timertasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.seagen.base.bean.ReportSortingBean;
import cn.seagen.base.bean.Waybill;
import cn.seagen.base.service.StatisticsService;
import cn.seagen.base.service.WaybillService;
import cn.seagen.base.utils.DateUtils;

/**
 * 
 * 扫描、分拣数据模拟，以及批次的重新生成
 * 每分钟生成2000条扫描、分拣数据（时间一样）
 * 判断分拣量是否达到10W，达到则生成新的批次
 *
 */
@Service
public class TaskStatisticsTest {
	@Resource
	private StatisticsService statisticsServiceImpl;
	
	@Resource
	private WaybillService waybillService;
	
	/** 存放插入字段*/
	private static String colums = "batch_id, sort_mode, sort_source, waybill_id, waybill_num, waybill_code, waybill_site_code,"
			+ " package_code, waybill_status, waybill_time, site_code, site_name, car_id, chute_id, scan_id, supply_id, layer_id,"
			+ " car_num, chute_num, scan_num, supply_num, layer_num, package_weight, package_length, package_width, package_height, "
			+ "supply_type, supply_time, scan_cycle, scan_status, scan_time, sorting_status, sorting_time, re_mark, update_flag,"
			+ " update_time, receive_time";
	
	/** 存放插入字段*/
	private static String scan_colums = "batch_id, sort_mode, sort_source, waybill_id, waybill_num, waybill_code, waybill_site_code, "
			+ "package_code, waybill_status, waybill_time, serialno, site_code, site_name, car_id, chute_id, scan_id, supply_id, layer_id, "
			+ "car_num, chute_num, scan_num, supply_num, layer_num, package_weight, package_length, package_width, package_height,"
			+ " supply_type, supply_time, scan_cycle, scan_status, scan_time, re_mark, update_flag, update_time, receive_time";
	
	
	/** 汇总、批次量统计表中的字段*/
	private static String report_sorting_keys = "f_recno,report_date,sum_name,sum_type,begin_time,end_time,supply_sum,scan_sum,"
			+ "layer_sum,all_sum,success_sum,err_sum,no_chute,more_data,no_reade,no_data,cancel_sum,err_chute,"
			+ "max_cycles,lost_data,box_sum,layer0,layer1,layer2,scan0,scan1,scan2,scan3,scan4,scan5,scan6,scan7,"
			+ "scan8,scan9,scan10,scan11,scan12,scan13,scan14,scan15,scan16,supply0,supply1,supply2,supply3,"
			+ "supply4,supply5,supply6,supply7,supply8,supply9,supply10,supply11,supply12,supply13,supply14,"
			+ "supply15,supply16,supply17,supply18,supply19,supply20,supply21,supply22,supply23,supply24,noread0,"
			+ "noread1,noread2,noread3,noread4,noread5,noread6,noread7,noread8,noread9,noread10,noread11,noread12,"
			+ "noread13,noread14,noread15,noread16,noread17,noread18,noread19,noread20,noread21,noread22,noread23,"
			+ "noread24";

	private Random random = new Random();
	private int carId;
	private int scanId;
	private int supplyId;
	private int chuteId;
	private int layerId;
	private int status;
	private int cy;
	
	private String waybill_id;
	private String waybill_code;
	private int waybill_status;
	private String site_code;
	
	private static Logger logger = LogManager.getLogger(TaskStatisticsTest.class);
	
//	@Scheduled(initialDelay=1*60*1000,fixedDelay = 1*60*1000)    //服务启动后60秒钟执行，然后执行后1分钟在执行
	public void timerTask(){
//		logger.info("开始分拣数据入库");
//		//开始时间
//		long begintime = System.currentTimeMillis();
//		//insert sorting
//		testSorting();
//		//结束时间
//		long runtime = System.currentTimeMillis() - begintime;
//		logger.info("结束分拣数据入库.共耗时：("+runtime+")");
		
		logger.info("开始waybill数据入库");
		//开始时间
		long begintime = System.currentTimeMillis();
		//insert sorting
		testWaybill();
		//结束时间
		long runtime = System.currentTimeMillis() - begintime;
		logger.info("结束waybill数据入库.共耗时：("+runtime+")");
	}
	
	
	public void testSorting(){
		ReportSortingBean bean = statisticsServiceImpl.findReportSortingBean("select "+report_sorting_keys
				+" from report_sorting where sum_type = 0 order by f_recno desc limit 1;");
		//大于10w重新生产批次
		if(bean==null || bean.getAll_sum() >= 1000000){
			String sql = "insert into report_sorting(sum_name, sum_type, report_date,begin_time, end_time) "
					+ "values(concat('B', replace(replace(replace(date_format(now(),'%Y-%m-%d %H:%i:%s'), '-', ''),"
					+ " ' ', ''), ':', '')), 0,date_format(now(),'%Y-%m-%d %H:%i'), now(), now());";
			if(statisticsServiceImpl.insertStatisticsProgress(sql))
				logger.error("重新生产批次成功。");
			bean = statisticsServiceImpl.findReportSortingBean("select "+report_sorting_keys
					+" from report_sorting where sum_type = 0 order by f_recno desc limit 1;");
		} else {
			logger.info("不需要重新生成批次。");
		}
		
		String time = DateUtils.getNow_W();
		for(int j = 0;j < 2;j++){
			List<String> sqls = new ArrayList<String>();
			for(int i = 0;i < 1000;i++){
				sqls.add(formatScanSql(time,bean));
			}
			statisticsServiceImpl.insertReportTable(sqls);
		}
		for(int j = 0;j < 2;j++){
			List<String> sqls = new ArrayList<String>();
			for(int i = 0;i < 1000;i++){
				sqls.add(formatSortSql(time,bean));
			}
			statisticsServiceImpl.insertReportTable(sqls);
		}
	}
	
	/**
	 * 分拣数据
	 * @param time
	 * @param bean
	 * @return
	 */
	public String formatSortSql(String time,ReportSortingBean bean){
		carId = random.nextInt(400) + 1;
		scanId = random.nextInt(17)+1;
		supplyId = random.nextInt(25)+1;
		chuteId = random.nextInt(300) + 1;
		layerId = random.nextInt(2)+1;
		status = random.nextInt(9);// 扫描分拣状态（SortStatus）
		cy = random.nextInt(10)+1;
		String uuid = ""+random.nextInt(2147483647);
		String sql = "INSERT INTO sorting_"+DateUtils.findYearMonth(1)+"_0"+(chuteId%3)+" (" + colums + ") values(";
		StringBuilder builder = new StringBuilder();
		builder.append("");
		//batch_id批次ID,
	    builder.append(bean.getF_recno()).append(",");
	    //sort_mode
	    builder.append("1").append(",");
	    //sort_source
	    builder.append("1").append(",");  
	    //waybill_id运单跟踪ID
	    builder.append("'").append(uuid).append("',");  
	    //waybill_num快件分拣过程唯一编号GUID
	    builder.append("'").append(uuid).append("',");  
	    //waybill_code运单号
	    builder.append("'").append(uuid).append("',");  
	    //waybill_site_code运单表中目的地代码
	    builder.append("'").append(uuid).append("',");  
	    //package_code包裹号
	    builder.append("'").append(uuid).append("',");  
	    //waybill_status运单状态(0正常运单,1运单取消,2运单地址更改,3无数据)
	    builder.append(0).append(","); 
	    //waybill_time运单生成时间
	    builder.append("'").append(time).append("',"); 
	    //site_code目的地代码
	    builder.append("1").append(",");
	    //site_name目的地名称
	    builder.append("1").append(",");
	    //car_id小车编号
	    builder.append(carId).append(",");
	    //chute_id滑槽口号
	    builder.append(chuteId).append(",");
	    //scan_id扫描仪编号
	    builder.append(scanId).append(",");
	    //supply_id供件台编号导入号台
	    builder.append(supplyId).append(",");
	    //layer_id层级id
	    builder.append(layerId).append(",");
	    //car_num小车编号
	    builder.append(carId).append(",");
	    //chute_num滑槽口编号
	    builder.append(chuteId).append(",");
	    //scan_num扫描仪编号
	    builder.append(scanId).append(",");
	    //supply_num供件台编号
	    builder.append(supplyId).append(",");
	    //layer_num层级编号
	    builder.append(layerId).append(",");
	    //package_weight重量
	    builder.append(44).append(",");
	    //package_length长度
	    builder.append(33).append(",");
	    //package_width宽度
	    builder.append(22).append(",");
	    //package_height高度
	    builder.append(11).append(",");
	    //supply_type供件方式暂定1
	    builder.append(1).append(",");
	    //supply_time供件时间
	    builder.append("'").append(time).append("',"); 
	    //scan_cycle扫描圈数
	    builder.append(cy).append(",");
	    //scan_status扫描状态(0正常扫描,1无分拣方案,2无数据,3无读,4取消,5最大圈数,6格口错,7多条码,8迷失,255未知)
	    builder.append(status).append(",");
	    //scan_time扫描时间
	    builder.append("'").append(time).append("',"); 
	    //sorting_status分拣状态(0正常扫描,1无分拣方案,2无数据,3无读,4取消,5最大圈数,6格口错,7多条码,8迷失,255未知)
	    builder.append(status).append(",");
	    //sorting_time分拣时间
	    builder.append("'").append(time).append("',"); 
	    //re_mark备注
	    builder.append("1").append(",");
	    //update_flag更新标识0：未更新1：已更新2不需要更新
	    builder.append("1").append(",");
	    //update_time更新时间
	    builder.append("'").append(time).append("',"); 
	    //receive_time消息接收时间
	    builder.append("'").append(time).append("'"); 
	    
	    sql = sql + builder.toString()+");";
	    
	    return sql;
	}
	
	/**
	 * 扫描数据
	 * @param time
	 * @param bean
	 * @return
	 */
	public String formatScanSql(String time,ReportSortingBean bean){
		carId = random.nextInt(400) + 1;
		scanId = random.nextInt(17);
		supplyId = random.nextInt(25);
		chuteId = random.nextInt(300) + 1;
		layerId = random.nextInt(3);
		status = random.nextInt(9);// 扫描分拣状态（SortStatus）
		cy = random.nextInt(10)+1;
		String uuid = ""+random.nextInt(2147483647);
		String sql = "INSERT INTO scan_"+DateUtils.findYearMonth(1)+"_0"+(chuteId%3)+" (" + scan_colums + ") values(";
		StringBuilder builder = new StringBuilder();
		builder.append("");
		//batch_id批次ID,
	    builder.append(bean.getF_recno()).append(",");
	    //sort_mode
	    builder.append("1").append(",");
	    //sort_source
	    builder.append("1").append(",");  
	    //waybill_id运单跟踪ID
	    builder.append("'").append(uuid).append("',");  
	    //waybill_num快件分拣过程唯一编号GUID
	    builder.append("'").append(uuid).append("',");  
	    //waybill_code运单号
	    builder.append("'").append(uuid).append("',");  
	    //waybill_site_code运单表中目的地代码
	    builder.append("'").append(uuid).append("',");  
	    //package_code包裹号
	    builder.append("'").append(uuid).append("',");  
	    //waybill_status运单状态(0正常运单,1运单取消,2运单地址更改,3无数据)
	    builder.append(0).append(","); 
	    //waybill_time运单生成时间
	    builder.append("'").append(time).append("',"); 
	    //serialno流水线号（德邦）
	    builder.append("1").append(",");
	    //site_code目的地代码
	    builder.append("1").append(",");
	    //site_name目的地名称
	    builder.append("1").append(",");
	    //car_id小车编号
	    builder.append(carId).append(",");
	    //chute_id滑槽口号
	    builder.append(chuteId).append(",");
	    //scan_id扫描仪编号
	    builder.append(scanId).append(",");
	    //supply_id供件台编号导入号台
	    builder.append(supplyId).append(",");
	    //layer_id层级id
	    builder.append(layerId).append(",");
	    //car_num小车编号
	    builder.append(carId).append(",");
	    //chute_num滑槽口编号
	    builder.append(chuteId).append(",");
	    //scan_num扫描仪编号
	    builder.append(scanId).append(",");
	    //supply_num供件台编号
	    builder.append(supplyId).append(",");
	    //layer_num层级编号
	    builder.append(layerId).append(",");
	    //package_weight重量
	    builder.append(44).append(",");
	    //package_length长度
	    builder.append(33).append(",");
	    //package_width宽度
	    builder.append(22).append(",");
	    //package_height高度
	    builder.append(11).append(",");
	    //supply_type供件方式暂定1
	    builder.append(1).append(",");
	    //supply_time供件时间
	    builder.append("'").append(time).append("',"); 
	    //scan_cycle扫描圈数
	    builder.append(cy).append(",");
	    //scan_status扫描状态(0正常扫描,1无分拣方案,2无数据,3无读,4取消,5最大圈数,6格口错,7多条码,8迷失,255未知)
	    builder.append(status).append(",");
	    //scan_time扫描时间
	    builder.append("'").append(time).append("',"); 
	    //re_mark备注
	    builder.append("1").append(",");
	    //update_flag更新标识0：未更新1：已更新2不需要更新
	    builder.append("1").append(",");
	    //update_time更新时间
	    builder.append("'").append(time).append("',"); 
	    //receive_time消息接收时间
	    builder.append("'").append(time).append("'"); 
	    
	    sql = sql + builder.toString()+");";
	    
	    return sql;
	}

	
	public void testWaybill(){
		List<Waybill> waybills = new ArrayList<Waybill>();
		for(int i = 0;i < 3000;i++){
			Waybill waybill = formatWaybillSql();
			waybills.add(waybill);
		}
		waybillService.insertWaybill(waybills);
	}

	/**
	 * waybill
	 */
	public Waybill formatWaybillSql(){
		int num = ((int) (Math.random() * 9000) + 1000);
		waybill_status = random.nextInt(4);
		waybill_id = "1070695" + num;
		waybill_code = "1070695" + num + "-"+waybill_status+"-"+waybill_status+"-"+waybill_status;
		site_code = "test"+num;
		Waybill waybill = new Waybill();
		waybill.setWaybill_id(waybill_id);
		waybill.setWaybill_code(waybill_code);
		waybill.setExp_code("0");
		waybill.setWaybill_status(waybill_status);
		waybill.setSite_code(site_code);
		waybill.setSerialno("0");
		waybill.setWaybill_time(DateUtils.getNow_W());
		return waybill;
	}
	
}
