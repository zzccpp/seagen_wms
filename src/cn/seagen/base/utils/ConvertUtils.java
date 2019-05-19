package cn.seagen.base.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.seagen.base.bean.BoxBean;
import cn.seagen.base.bean.SortschemeDetail;
import cn.seagen.base.bean.Waybill;
import cn.seagen.base.constant.OptConstant;
import cn.seagen.base.constant.SystemConstant;
import cn.seagen.base.enums.SortStatus;
import cn.seagen.base.enums.WaybillStatus;
import cn.seagen.base.mq.dto.MQBox;
import cn.seagen.base.mq.dto.MQSort;

public class ConvertUtils {
	protected static Map<String, Long> chuteList = new HashMap<String, Long>();
	
	public static Waybill convertFromObject(MQSort mQScan) {
		Waybill waybill = new Waybill();
		waybill.setWaybill_num(mQScan.getSorting_id());// 快件分拣过程唯一编号
		waybill.setPackage_code(mQScan.getPackage_code()); // 包裹号
		// 扫描过程用扫描状态
		waybill.setScan_status(mQScan.getStatue());// 扫描状态(0正常扫描,1无分拣方案,2无目的地,3noread,4订单取消5最大圈数,6格口错,7多条码，255其它)
		waybill.setSort_source(mQScan.getSort_source()); // 分拣来源
		waybill.setLayer_id(mQScan.getLayer_id());//层级编号
		waybill.setLayer_num(mQScan.getLayer_num());//层级编号
		waybill.setCar_id(mQScan.getCar_id()); // 小车编号
		waybill.setSupply_id(mQScan.getSupply_id()); // 供件台编号
		waybill.setScan_id(mQScan.getScan_id()); // 扫描器号(龙门架)
		waybill.setChute_id(mQScan.getChute_id()); // 滑槽口号
		waybill.setCar_num(mQScan.getCar_num()); // 小车编号
		waybill.setSupply_num(mQScan.getSupply_num()); // 供件台编号
		waybill.setScan_num(mQScan.getScan_num()); // 扫描器号(龙门架)
		waybill.setChute_num(mQScan.getChute_num()); // 滑槽口号
		waybill.setScan_cycle(mQScan.getCycle()); // 运行圈数 扫描一定是1，扫描后分拣中一定大于1

		waybill.setSite_code(mQScan.getSite_code());// 目的地代码
		waybill.setRe_mark(""); // 备注存附加属性，目的地代码

		String worktime = JUtils.formatDateTime(mQScan.getCreate_time(), 9);
		waybill.setSupply_time(worktime); // 导入时间
		waybill.setScan_time(worktime); // 扫描时间
		waybill.setSorting_time(worktime); // 分拣时间
		waybill.setSorting_time_date(waybill.getSorting_time().substring(0, 19));// 分拣时间:时间格式
		waybill.setDb_opt_time(DateUtils.getNow_W());
		// 运单号以及运单状态(0正常运单,1运单取消,2运单地址更改,3无数据)
		if ((waybill.getScan_status() == SortStatus.s_NoRead.getValue())|| (waybill.getScan_status() == SortStatus.s_Undefine.getValue())) {
			waybill.setWaybill_code(null);// 设置运单号(不是包裹号)
			waybill.setWaybill_status(WaybillStatus.w_NoData.getValue());
			//
			waybill.setWaybill_id("0");
			waybill.setWaybill_time("");
			waybill.setBox_site_code(waybill.getSite_code());
		} else {
			waybill.setWaybill_code(mQScan.getPackage_code()); // 运单号
			if (waybill.getScan_status() == SortStatus.s_Cancel.getValue())
				waybill.setWaybill_status(WaybillStatus.w_Cancel.getValue());
			else
				waybill.setWaybill_status(WaybillStatus.w_OK.getValue());
			 
			waybill.setBox_site_code(waybill.getSite_code());
		}

		waybill.setSorting_status(waybill.getScan_status()); // 数据当是分拣落格口的状态，如果是分拣数据以此为准

		waybill.setBatch_id(SystemConstant.BATCH_ID);// 批次ID,
		waybill.setSupply_type("AT");// 供件方式AT
		//MQ消息生成时间
		waybill.setReceive_time(mQScan.getCreate_time());
		return waybill;
	}
	
	public static MQBox convertFromObject(BoxBean box) {
		MQBox mqbox = new MQBox();
		mqbox.setIndex_App();
		// private String printer_ip;// 打印机IP IPV4
		mqbox.setPrinter_ip(box.getPrinter_ip());
		// private int chute_no;// 格口号
		mqbox.setChute_id(box.getChute_id());
		mqbox.setChute_num(box.getChute_num());
		// private int package_count; // 快件数量
		mqbox.setPackage_count(box.getPackage_count());
		// private String box_code; // 箱号条码
		mqbox.setBox_code(box.getBox_code());
		// private String box_type;// 箱号类型
		mqbox.setBox_type(box.getBox_type());
		// private String mixing_box_type;// 是否混箱
		mqbox.setMixing_box_type(box.getMixing_box_type());
		// private String from_site_name;// 出发站点名称
		mqbox.setFrom_site_name(box.getFrom_site_name());
		// private String from_site_code;// 出发站点代号
		mqbox.setFrom_site_code(box.getFrom_site_code());
		// private String to_site_name;// 接收站点名称
		mqbox.setTo_site_name(box.getTo_site_name());
		// private String to_site_code;// 接收站点代号
		mqbox.setTo_site_code(box.getTo_site_code());
		// private String cate_gory;// 运输方式
		mqbox.setCate_gory(box.getCate_gory());
		// private List<String> cate_waybill;// 运输路由 多个字符串，数组
		String[] router = null;
		// private int style;// 打印样式 1-6
		int printStyle = 1;
		if(JUtils.isEmpty(box.getCarriage_router()) == false){
			router = box.getCarriage_router().split(",");
			printStyle = ((router.length > 1) ? (router.length - 1) : 1);
			mqbox.setCate_rounter(java.util.Arrays.asList(router)); // 倒过来转换:list.toArray(stringArray)
		}
		mqbox.setCate_rounter_num(box.getCarriage_router_num());
		 
		mqbox.setStyle(printStyle);
		return mqbox;
	}
	
	/** 从分拣方案中获取箱号站点编码*/
	public static String getBoxSiteCode(int chute_id) {
		String box_site_code = null;
		List<SortschemeDetail> sortschemeDetailList = SystemConstant.SORTSCHEMELIST;
		try {
			for (SortschemeDetail sortschemeDetail : sortschemeDetailList) {
				if (sortschemeDetail.getChute_num().equals(
						String.valueOf(chute_id))) {
					box_site_code = sortschemeDetail.getBox_site_code();
					if (JUtils.isEmpty(box_site_code)) {
						box_site_code = null;
						continue;
					} else
						break;
				}
			}
		} catch (Exception e) {
			//logger.error("获取BOX_SITE_CODE出现错误(格口" + chute_id + ")："+ e.getMessage(),e);
		}
		return box_site_code;
	}
	
	/**是否能建包,同一格口在与上次建包在一定的时间内是不允许再次连续的按建包按钮*/
	public synchronized static boolean canjb(int chute) {
		String chuteKey = String.valueOf(chute);;
		long jbTime = System.currentTimeMillis();
		try {
			if ((jbTime - chuteList.get(chuteKey)) > 10 * 1000) {
				chuteList.put(chuteKey, jbTime);
				return true;
			} else
				return false;
		} catch (NullPointerException e) {
			chuteList.put(chuteKey, jbTime);
			return true;
		} catch (Exception e) {
			chuteList.put(chuteKey, jbTime);
			return true;
		}
	}
	
	/** 返回打包中实际的包裹数量*/
	public static int getPackageCount(List<Waybill> waybill_list) {
		int count = 0;
		for (Waybill waybill : waybill_list) {
			if (waybill.getJb_status() == OptConstant.JB_FAIL)
				count = count + 1;
		}
		return count;
	}

	/** 把重复的以前的包裹分拣状态置为不可打包状态 */
	public static void changeWaybillStatus(List<Waybill> waybill_list) {
		for (int i = 0; i < waybill_list.size(); i++) {
			Waybill waybill = waybill_list.get(i);
			if (waybill.getJb_status() == OptConstant.JB_DISABLE)
				continue;
			for (int j = i + 1; j < waybill_list.size(); j++) {
				Waybill waybill_temp = waybill_list.get(j);
				if (waybill.getPackage_code().equals(waybill_temp.getPackage_code())) {
					//这里判断分表，是因为有可能存在跨月数据
					if ((waybill.getTab_flag() < waybill_temp.getTab_flag())||(waybill.getF_recno() < waybill_temp.getF_recno())) {
						waybill.setJb_status(OptConstant.JB_DISABLE); // 把重复的不是最后一批分拣的状态改为不可建包
						waybill.setJb_update_flag(OptConstant.UPDATE_DISABLE);// 把重复的不是最后一批分拣的状态改为不可上传建包数据
						waybill = waybill_list.get(j);
					} else {
						waybill_temp.setJb_status(OptConstant.JB_DISABLE); // 把重复的不是最后一批分拣的状态改为不可建包
						waybill_temp.setJb_update_flag(OptConstant.UPDATE_DISABLE);// 把重复的不是最后一批分拣的状态改为不可上传建包数据
					}
				}
			}
		}
	}
}
