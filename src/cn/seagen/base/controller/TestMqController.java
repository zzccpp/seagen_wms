package cn.seagen.base.controller;

import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.seagen.base.mq.dto.MQCtrlCom;
import cn.seagen.base.mq.dto.MQEvent;
import cn.seagen.base.mq.dto.MQJb;
import cn.seagen.base.mq.dto.MQLineNum;
import cn.seagen.base.mq.dto.MQLog;
import cn.seagen.base.mq.dto.MQPower;
import cn.seagen.base.mq.dto.MQSort;
import cn.seagen.base.mq.dto.MQSortMode;
import cn.seagen.base.mq.enums.MQMsgType;
import cn.seagen.base.service.MqProducer;
import cn.seagen.base.service.SortedService;
import cn.seagen.base.service.StatisticsService;
import cn.seagen.base.utils.DateUtils;
import cn.seagen.base.utils.JUtils;
import cn.seagen.base.vo.JSonResponse;

@Controller
public class TestMqController {
	
	public TestMqController() {
		super();
		System.out.println("===========构建TestMqController");
	}
	@Resource
	private SortedService sortServiceImpl;
	@Resource
	private StatisticsService statisticsServiceImpl;
	@Resource
	private MqProducer mqProducerImpl;
	private Random random = new Random();
	private int carId;
	private int scanId;
	private int supplyId;
	private int chuteId;
	private int layerId;
	private int status;
	private int cy;
	private MQSort sort;

	/**
	 * 测试扫描与分拣MQ消息
	 * 
	 * @param count
	 *            执行次数
	 * @param doCount 循环次数
	 * @return		
	 */
	@ResponseBody
	@RequestMapping(value = "/scanAndSortMQTest.do")
	public JSonResponse scanAndSortMQTest(int count,int doCount) {
		for(int j = 0;j < doCount;j++){
			for (int i = 0; i < count; i++) {
				initMQSort();
				// 1.发送扫描MQ消息
				sort.setIndex(MQMsgType.m_Scan_C.getValue());
				mqProducerImpl.sendToCqueue(sort);
				// logger.info(jsonStr);
				// 2.发送分拣MQ消息
				sort.setIndex(MQMsgType.m_Sorted_C.getValue());
				mqProducerImpl.sendToCqueue(sort);
				// logger.info(jsonStr);
			}
			JUtils.sleep(10*1000);
		}
		return new JSonResponse(200, "执行完成!");
	}

	/**
	 * 发送开关机MQ
	 * 
	 * @param flag
	 *            0开机 1关机
	 * @param reset
	 *            0不重新记录批次 1重新记录批次
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/onAndOffMQTest.do")
	public JSonResponse onAndOffMQTest(int flag, int reset) {
		MQPower power = new MQPower();
		power.setIndex(MQMsgType.m_Power_C.getValue());
		power.setSort_mode("1");
		power.setAddt_attrs("");
		power.setCreate_time(DateUtils.getNow());
		if (flag == 0) {// 开机
			power.setTurn_on(true);
		} else if (flag == 1) {// 关机
			power.setTurn_on(false);
		}
		if (reset == 0) {
			power.setReset(false);
		} else if (reset == 1) {
			power.setReset(true);
		}
		mqProducerImpl.sendToCqueue(power);
		// logger.info(jsonStr);
		return new JSonResponse(200, "执行完成!");
	}

	/**
	 * 设置分拣模式
	 * 
	 * @param sortName
	 *            模式名称
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/setSortModeMQTest.do")
	public JSonResponse setSortModeMQTest(String sortName) {
		MQSortMode sortMode = new MQSortMode();
		sortMode.setIndex(MQMsgType.m_SortMode_C.getValue());
		sortMode.setSort_mode(sortName);
		sortMode.setAddt_attrs("");
		sortMode.setCreate_time(DateUtils.getNow());
		mqProducerImpl.sendToCqueue(sortMode);
		// logger.info(jsonStr);
		return new JSonResponse(200, "执行完成!");
	}

	/**
	 * 设置流水号
	 * 
	 * @param sortName
	 *            模式名称
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/setlineNumMQTest.do")
	public JSonResponse setlineNumMQTest(String pipeline) {
		MQLineNum lineNum = new MQLineNum();
		lineNum.setIndex(MQMsgType.m_LineNum_A.getValue());
		lineNum.setPipe_line(pipeline);
		lineNum.setAddt_attrs("");
		lineNum.setCreate_time(DateUtils.getNow());
		mqProducerImpl.sendToCqueue(lineNum);
		// logger.info(jsonStr);
		return new JSonResponse(200, "执行完成!");
	}

	/**
	 * 控制层发送应用层MQ 开关特性设备控制
	 * 
	 * @param devId
	 *            0为未知设备(排查错误) 1为小车、2为导入台 3为龙门架、4为格口
	 * @param ctrlData
	 *            控制状态
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendCtrlComMQTest.do")
	public JSonResponse sendCtrlComMQTest(int devId, String ctrlData) {
		MQCtrlCom ctrlCom = new MQCtrlCom();
		ctrlCom.setIndex(MQMsgType.m_CtrCom_A.getValue());
		ctrlCom.setCtrl_id(devId);// 设备ID
		ctrlCom.setCtrl_data(ctrlData);
		ctrlCom.setAddt_attrs("");
		ctrlCom.setCreate_time(DateUtils.getNow());
		mqProducerImpl.sendToCqueue(ctrlCom);
		// logger.info(jsonStr);
		return new JSonResponse(200, "执行完成!");
	}

	/**
	 * 发送建包请求MQ测试
	 * 
	 * @param type
	 *            0正常请求,处理分拣业 1格口请求,返回该格口最后的箱号信息供打印 2箱号请求,返回完整箱号信息，供打
	 * @param chuteNum
	 *            格口
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendJBMQTest.do")
	public JSonResponse sendJBMQTest(int jbtype, int type, int chuteNum) {
		MQJb jb = new MQJb();
		jb.setIndex(MQMsgType.m_Jb_C.getValue());
		jb.setType(type);
		jb.setBox_code("B5512345678128013912");
		jb.setAddt_attrs("");
		jb.setCreate_time(DateUtils.getNow());
		if (jbtype == 1) {
			for (int i = 1; i <= 88; i++) {
				jb.setChute_id(i);
				jb.setChute_num(i + "");
				mqProducerImpl.sendToCqueue(jb);
				// logger.info(jsonStr);
			}
		} else {
			jb.setChute_id(chuteNum);
			jb.setChute_num(chuteNum + "");
			mqProducerImpl.sendToCqueue(jb);
			// logger.info(jsonStr);
		}
		return new JSonResponse(200, "执行完成!");
	}

	/**
	 * 重量与量方请求MQ测试
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendVolumeMQTest.do")
	public JSonResponse sendVolumeMQTest(int count) {
		/*String jsonStr;
		MQVolume volume;
		for (int i = 0; i < count; i++) {
			volume = new MQVolume();
			volume.setIndex(MQMsgType.m_Volume_C.getValue());
			volume.setSorting_id(UUID.randomUUID().toString());
			volume.setPackage_code("1070695" + ((int) (Math.random() * 9000) + 1000) + "-1-1-3");
			volume.setSite_code(((int) (Math.random() * 9000) + 1000) + "");// 随机
			volume.setLength((int) (Math.random() * 900) + 100);
			volume.setWidth((int) (Math.random() * 900) + 100);
			volume.setHeight((int) (Math.random() * 900) + 100);
			volume.setWeight((int) (Math.random() * 900) + 100);
			volume.setAddt_attrs("");
			volume.setCreate_time(JUtils.getNow());
			jsonStr = JSON.toJSONString(volume);
			MQProducer.getInstance().sendC(jsonStr);
			// logger.info(jsonStr);
		}*/
		return new JSonResponse(200, "执行完成!");
	}

	/**
	 * 事件请求MQ测试
	 * {index: 70101,event_id:8,layer_id:1,event_target:”电机一”,event_val:”电流过大,自保护,停止运行”,addt_attrs:””,
	 * create_time:”2016-04-13 10:21:25.452” }
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendEventMQTest.do")
	public JSonResponse sendEventMQTest(int eventId, String target, String val) {
		MQEvent event = new MQEvent();
		event.setIndex(MQMsgType.m_Event_C.getValue());
		event.setEvent_id(eventId);
		event.setLayer_id(random.nextInt(2)+1);//层级
		event.setEvent_target(target);
		event.setEvent_val(val);
		event.setAddt_attrs("");
		event.setCreate_time(DateUtils.getNow());
		mqProducerImpl.sendToCqueue(event);
		return new JSonResponse(200, "执行完成!");
	}

	/**
	 * 事件请求MQ测试
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendLogMQTest.do")
	public JSonResponse sendLogMQTest(int logId, String target, String val) {
		MQLog log = new MQLog();
		log.setIndex(MQMsgType.m_Log_C.getValue());
		log.setLog_id(logId);
		log.setLog_target(target);
		log.setLog_val(val);
		log.setAddt_attrs("");
		log.setCreate_time(DateUtils.getNow());
		mqProducerImpl.sendToCqueue(log);
		// logger.info(jsonStr);
		return new JSonResponse(200, "执行完成!");
	}

	/**
	 * 初始化一个扫描/分拣MQ实体
	 * 
	 * @return
	 */
	public MQSort initMQSort() {
		sort = new MQSort();
		carId = random.nextInt(400) + 1;
		scanId = random.nextInt(16)+1;
		supplyId = random.nextInt(24)+1;
		chuteId = random.nextInt(300) + 1;
		layerId = random.nextInt(2)+1;
		status = random.nextInt(9);// 扫描分拣状态（SortStatus）
		cy = random.nextInt(10)+1;
		
		sort.setSorting_id(UUID.randomUUID().toString());
		sort.setPackage_code("1070695" + ((int) (Math.random() * 9000) + 1000) + "-1-1-3");
		sort.setWeight((int) (Math.random() * 1000));
		sort.setSite_code("12580");
		sort.setChute_id(chuteId);
		sort.setChute_num(chuteId+"");
		sort.setCar_num(carId + "");
		sort.setCar_id(carId);
		sort.setCar_num(carId + "");
		sort.setLayer_id(layerId);
		sort.setLayer_num(layerId+"");
		sort.setSupply_id(supplyId);
		sort.setSupply_num(supplyId + "");
		sort.setScan_id(scanId);
		sort.setScan_num(scanId + "");
		sort.setCycle(cy);
		sort.setStatue(status);
		sort.setSort_source("ccccc");
		sort.setChange(false);
		sort.setAddt_attrs("");
		sort.setCreate_time(DateUtils.getNow());
		return sort;
	}

	/**
	 * 清空数据库表数据： report_car, report_chute, report_minute, report_scan,
	 * report_site, report_sorting, report_supply,scan, sorting,mq_err_data
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/emptyTableData.do")
	public JSonResponse emptyTableData() {
		/*// String table =
		// "mq_err_data, report_car, report_chute, report_minute, "
		// +
		// "report_scan, report_site, report_sorting, report_supply, scan, sorting";
		String[] tableList = { "mq_err_data", "printer_data", "report_car", "report_chute", "report_minute",//
				"report_scan", "report_site", "report_sorting", "report_supply", "scan", "sorting" };
		// logger.info("emptyTableData:"+tableList);
		for (String table : tableList) {
			TestDao.truncate_table_data(table);
		}
		LogDao.record_log(1, "emptyTableData", "mq_err_data, printer_data, report_car, report_chute, " + "report_minute, report_scan, report_site, report_sorting, report_supply, scan, sorting");
		*/return new JSonResponse(200, "执行完成!");
	}

	/**
	 * 根据运单号获取格口MQ测试
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendGetChute.do")
	public JSonResponse sendGetChute(String package_code) {
		/*String jsonStr;
		MQGetChute mqGetChute = new MQGetChute();
		mqGetChute.setIndex(MQMsgType.m_GetChute_C.getValue());
		mqGetChute.setSorting_id(UUID.randomUUID().toString());
		mqGetChute.setPackage_code(package_code);
		mqGetChute.setChute_id(0);
		mqGetChute.setChute_num("0");
		mqGetChute.setAddt_attrs("");
		mqGetChute.setCreate_time(JUtils.getNow(3));
		jsonStr = JSON.toJSONString(mqGetChute);
		MQProducer.getInstance().sendC(jsonStr);
		// logger.info(jsonStr);*/
		return new JSonResponse(200, "执行完成!");
	}

	/**
	 * 满笼MQ测试
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendChuteState.do")
	public JSonResponse sendChuteState(int chuteState, int chuteNum) {
		/*String jsonStr;
		MQChuteState mqChuteState = new MQChuteState();
		mqChuteState.setIndex(MQMsgType.m_ChuteState_C.getValue());
		mqChuteState.setChute_status(chuteState);
		mqChuteState.setType(0);
		mqChuteState.setAddt_attrs("");
		mqChuteState.setChute_id(chuteNum);
		mqChuteState.setChute_num("" + chuteNum);
		mqChuteState.setCreate_time(JUtils.getNow(3));
		jsonStr = JSON.toJSONString(mqChuteState);
		MQProducer.getInstance().sendC(jsonStr);
		// logger.info(jsonStr);*/
		return new JSonResponse(200, "执行完成!");
	}
	
	public static void main(String[] args) {
		
	}
	
	
}
