package cn.seagen.base.timertasks;

import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.seagen.base.mq.dto.MQSort;
import cn.seagen.base.mq.enums.MQMsgType;
import cn.seagen.base.service.MqProducer;
import cn.seagen.base.utils.DateUtils;

@Service
public class TaskTest {

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
	
//	@Scheduled(initialDelay=2*60*1000,fixedDelay = 1*60*1000)   //服务启动后60秒钟执行，然后执行后1分钟在执行
	public void timerTask(){
		System.out.println(Thread.currentThread().getName()+"定时调度方法执行");
		for (int i = 0; i < 2000; i++) {
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
	}
	
	/**
	 * 初始化一个扫描/分拣MQ实体
	 * 
	 * @return
	 */
	public MQSort initMQSort() {
		sort = new MQSort();
		carId = random.nextInt(200) + 1;
		scanId = random.nextInt(8)+1;
		supplyId = random.nextInt(24)+1;
		chuteId = random.nextInt(300) + 1;
		layerId = random.nextInt(2)+1;
		status = random.nextInt(9);// 扫描分拣状态（SortStatus）
		cy = random.nextInt(10)+1;
		
		sort.setSorting_id(UUID.randomUUID().toString());
		sort.setPackage_code("1070695" + ((int) (Math.random() * 9000) + 1000) + "-"+status+"-"+status+"-"+cy);
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
}
