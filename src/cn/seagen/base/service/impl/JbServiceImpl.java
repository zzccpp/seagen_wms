package cn.seagen.base.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.seagen.base.bean.BoxBean;
import cn.seagen.base.bean.Waybill;
import cn.seagen.base.constant.OptConstant;
import cn.seagen.base.constant.SystemConstant;
import cn.seagen.base.mq.dto.MQBox;
import cn.seagen.base.mq.dto.MQJb;
import cn.seagen.base.service.BoxService;
import cn.seagen.base.service.JbService;
import cn.seagen.base.service.MqProducer;
import cn.seagen.base.service.PrinterDataService;
import cn.seagen.base.service.SortedService;
import cn.seagen.base.utils.ConvertUtils;

import com.alibaba.fastjson.JSON;

@Service
public class JbServiceImpl implements JbService {
	private Logger logger = LogManager.getLogger(JbServiceImpl.class);
	@Resource
	private MqProducer mqProducerImpl;
	@Resource
	private SortedService sortedServiceImpl;
	@Resource
	private BoxService boxServiceImpl;
	@Resource
	private PrinterDataService printerDataServiceImpl;
	@Override
	public boolean jbProcess(MQJb mqJb) {
		
		int chute_id = mqJb.getChute_id();
		String chute_num = mqJb.getChute_num();
		String jb_time = mqJb.getCreate_time();
		// 两秒内不可重复建包
		if (ConvertUtils.canjb(chute_id) == false) {
			/*if (SystemConstant.BOX_PRINT_TYPE == JdConvert.BOX_PRINT_IMAGE)
				MQMessageWork.send_cmd(getExportBoxImag(chute_id, "", OptConstant.PRINT_STYLE_NO_WAYBILL_DATA));
			else
				MQMessageWork.send_cmd(getExportBox(chute_id, "", OptConstant.PRINT_STYLE_NO_WAYBILL_DATA));
			LogDao.record_log(LogType.QUERY_JB, "格口:" + chute_id + "格口编号:" + chute_num, "无包裹数据不可建包");*/
			return false;
		}

		// 1判断是否是综合格口和异常格口，此类型的格口不可建包
		/*if (Convert.is_curr_chute(String.valueOf(chute_id))) {
			if (SystemConstant.BOX_PRINT_TYPE == JdConvert.BOX_PRINT_IMAGE)
				MQMessageWork.send_cmd(getExportBoxImag(chute_id, "", OptConstant.PRINT_STYLE_EXCEPT_CHUTE));
			else
				MQMessageWork.send_cmd(getExportBox(chute_id, "", OptConstant.PRINT_STYLE_EXCEPT_CHUTE));

			//LogDao.record_log(LogType.QUERY_JB, "格口:" + chute_id + "格口编号:" + chute_num, "综合口或异常口不可建包");
			return false;
		}*/

		// 4返回可打印的箱号信息给打印机，将该信息记录到打印机数据表中
		// # 获取打印机ip
		/*String printer_ip = PrinterDao.get_printer_ip(chute_id);
		if (printer_ip == null) {
			if (SystemConstant.BOX_PRINT_TYPE == JdConvert.BOX_PRINT_IMAGE)
				MQMessageWork.send_cmd(getExportBoxImag(chute_id, "", OptConstant.PRINT_STYLE_NO_PRINTER));
			else
				MQMessageWork.send_cmd(getExportBox(chute_id, "", OptConstant.PRINT_STYLE_NO_PRINTER));
			//LogDao.record_log(LogType.QUERY_JB, "格口:" + chute_id + "格口编号:" + chute_num, "没有配制打印机不可建包");
			return false;
		}*/

		// 2在sort表中取已分拣的包裹信息(只要投入格口的都算，包含正常分拣的，格口错的)
		List<Waybill> waybill_list = sortedServiceImpl.getUnJbSortedList(chute_id);

		// 把重复的以前的包裹分拣状态置为不可打包状态
		ConvertUtils.changeWaybillStatus(waybill_list);

		// # 取运单数量
		int waybill_count = ConvertUtils.getPackageCount(waybill_list);

		// # 判断是否有数据可建包
		if (waybill_count <= 0) {
			/*if (SystemConstant.BOX_PRINT_TYPE == JdConvert.BOX_PRINT_IMAGE)
				MQMessageWork.send_cmd(getExportBoxImag(chute_id, "", OptConstant.PRINT_STYLE_NO_WAYBILL_DATA));
			else
				MQMessageWork.send_cmd(getExportBox(chute_id, "", OptConstant.PRINT_STYLE_NO_WAYBILL_DATA));
			//LogDao.record_log(LogType.QUERY_JB, "格口:" + chute_id + "格口编号:" + chute_num, "无包裹数据不可建包");*/
			return false;
		}

		// 3取该格口对应的站点信息的箱号，如果box表中已无未使用的箱号，远程取一个
		// # 取得运单目的地
		// String site_code = waybill_list.get(0).getSite_code();
		// String site_code = ConvertUtils.getBoxSiteCode(chute_id);
		// 获取未使用的箱号
		//BoxBean box = BoxServiceImpl.getUnusedBox(1,site_code);
		BoxBean box  = new BoxBean();
		box.setBox_code(System.currentTimeMillis()+""+chute_id);
		/*if (box == null) {
			// 远程取新的箱号
			JdDownBoxCodeThread.down_boxcode(site_code);
			//
			box = BoxDao.get_unused(1, site_code);
			if (box == null) { // 取不到箱号
				if (SystemConstant.BOX_PRINT_TYPE == JdConvert.BOX_PRINT_IMAGE)
					MQMessageWork.send_cmd(getExportBoxImag(chute_id, "", OptConstant.PRINT_STYLE_NO_BOX_CODE));
				else
					MQMessageWork.send_cmd(getExportBox(chute_id, "", OptConstant.PRINT_STYLE_NO_BOX_CODE));
				LogDao.record_log(LogType.QUERY_JB, "格口:" + chute_id + "格口编号:" + chute_num, "无箱号可用不可建包");
				return false;
			}
		}*/

		// 设置包裹件数
		box.setPackage_count(waybill_count);
		// 设置打印机IP
		box.setPrinter_ip("0.0.0.0");
		// 格口号
		box.setChute_id(chute_id);

		// # 组装命令
		MQBox mqbox = ConvertUtils.convertFromObject(box);

		// 设置要下发的命令
		String print_cmd = null;
		try {
			print_cmd = JSON.toJSONString(mqbox);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		/*if (print_cmd == null) {
			if (SystemConstant.BOX_PRINT_TYPE == JdConvert.BOX_PRINT_IMAGE)
				MQMessageWork.send_cmd(getExportBoxImag(chute_id, "", OptConstant.PRINT_STYLE_NO_BOX_CODE));
			else
				MQMessageWork.send_cmd(getExportBox(chute_id, "", OptConstant.PRINT_STYLE_NO_BOX_CODE));
			//LogDao.record_log(LogType.QUERY_JB, "格口:" + chute_id + "格口编号:" + chute_num, "无箱号可用不可建包");
			return false;
		}*/

		// 设置打印机打印命令
		box.setPrint_cmd(print_cmd);

		// 图像字符打印
		/*if (SystemConstant.BOX_PRINT_TYPE == JdConvert.BOX_PRINT_IMAGE) {
			MQBoxImg mQBoxImg = null;
			JdBoxImageResponse boxImageResponse = JdSortingWork.get_boxcode_imag(String.valueOf(chute_id), box.getBox_code(), waybill_count);
			if ((boxImageResponse == null) || (boxImageResponse.getBoxImgStr() == null)) {
				MQMessageWork.send_cmd(getExportBoxImag(chute_id, "", OptConstant.PRINT_STYLE_NO_BOX_IMAGE));
				//LogDao.record_log(LogType.QUERY_JB, "格口:" + chute_id + "格口编号:" + chute_num, "无法取箱号相关的图像信息");
				return false;
			}
			box.setPrint_imag(boxImageResponse.getBoxImgStr());

			// 存储当前的建包打印记录
			if (!PrinterDataDao.insert_printer_data(box)) {
				MQMessageWork.send_cmd(getExportBoxImag(chute_id, "", OptConstant.PRINT_STYLE_SAVE_DATA_ERROR));
				LogDao.record_log(LogType.QUERY_JB, "格口:" + chute_id + "格口编号:" + chute_num, "打印机打印数据存储失败");
				return false;
			}

			// 赋值
			mQBoxImg = new MQBoxImg();
			mQBoxImg.setIndex_App();
			mQBoxImg.setPrinter_ip(printer_ip);
			mQBoxImg.setChute_id(chute_id);
			mQBoxImg.setPrinter_data(box.getPrint_imag());
			mQBoxImg.setStyle(0);
			mQBoxImg.setAddt_attrs("");
			mQBoxImg.setCreate_time(JUtils.getNow());
			// # 信息发送给打印机打印(这里是异步，不用担心后面的数据库执行的时间)
			if (!MQMessageWork.send_cmd(mQBoxImg)) {// 记录错误日志
				//LogDao.record_errlog(LogType.MQ_MESSAGE, "发送MQ消息", print_cmd, "下发箱号");
				return false;
			}

		} else {// 命令字符打印
			// 存储当前的建包打印记录
			if (!PrinterDataDao.insert_printer_data(box)) {
				MQMessageWork.send_cmd(getExportBox(chute_id, "", OptConstant.PRINT_STYLE_SAVE_DATA_ERROR));
				//LogDao.record_log(LogType.QUERY_JB, "格口:" + chute_id + "格口编号:" + chute_num, "打印机打印数据存储失败");
				return false;
			}

			// # 信息发送给打印机打印(这里是异步，不用担心后面的数据库执行的时间)
			if (!MQMessageWork.send_cmd(print_cmd)) {// 记录错误日志
				//LogDao.record_errlog(LogType.MQ_MESSAGE, "发送MQ消息", print_cmd, "下发箱号");
				return false;
			}
		}*/
		printerDataServiceImpl.insertPrinterData(box);// 存储当前的建包打印记录
		// 5更新箱号信息(本地和远程)
		// 设置box其它的数据
		box.setBatch_id(SystemConstant.BATCH_ID);// 批次ID,
		box.setChute_num(chute_num); // 格口号
		box.setPrint_status(OptConstant.PRINTE_SUCCESS); // 打印状态
		box.setPrint_time(jb_time); // 打印时间
		box.setUse_flag(OptConstant.STATE_USED); // 箱号已使用

		// # 更新远程箱号数据
		/*if (JdSortingWork.insert_box(box)) {
			box.setUpdate_flag(OptConstant.UPDATE_SUCCESS);
			box.setUpdate_time(DateUtils.getNow());
		} else {
			box.setUpdate_flag(OptConstant.UPDATE_FAIL);
			box.setUpdate_time(null);
		}*/
		// 更新箱号信息
		boxServiceImpl.updateBox(box);
		boxServiceImpl.insertBoxTemp(box);
		// 6更新包裹建包信息(本地和远程)
		for (Waybill waybill : waybill_list) {
			waybill.setBox_code(box.getBox_code());
			waybill.setSite_code(box.getTo_site_code());
			waybill.setSite_name(box.getTo_site_name());
			if (waybill.getJb_status() == OptConstant.JB_FAIL) {
				waybill.setJb_status(OptConstant.JB_SUCCESS);
				waybill.setJb_time(jb_time);
				waybill.setJb_update_flag(OptConstant.UPDATE_FAIL);
				waybill.setBox_code(box.getBox_code()); // 设置箱号
			}
			waybill.setJb_update_time(null);
		}

		// # 更新远程建包数据
		/*if (JdSortingWork.sort_process(waybill_list)) {
			// 注意下更新是最初查询的数据
			for (Waybill waybill : waybill_list) {
				if (waybill.getJb_status() == OptConstant.JB_SUCCESS) {
					waybill.setJb_update_flag(OptConstant.UPDATE_SUCCESS);
					waybill.setJb_update_time(DateUtils.getNow());
				}
			}
		}*/
		// # 更新本地建包数据
		return sortedServiceImpl.updateJbSortedList(waybill_list,chute_id);
	}

}
