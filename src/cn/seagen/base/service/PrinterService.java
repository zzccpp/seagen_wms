package cn.seagen.base.service;

import java.util.List;
import cn.seagen.base.bean.Printer;


public interface PrinterService {
	
	/**
	 * 获取打印机配置列表
	 * @return
	 */
	public List<Printer> getPrinters();
	
	/**
	 * 更新打印机配置信息
	 * @param printer
	 * @return
	 */
	public boolean updatePrinter(Printer printer);
	
	/**
	 * 打印机配置信息插入打印机表
	 * @param printer
	 * @return
	 */
	public boolean insertPrinter(Printer printer);
	
	/**
	 * 删除打印机
	 * @param fRecno
	 * @return
	 */
	public boolean deletePrinter(int fRecno);
	
	/**
	 * 检查打印机编码是否重复
	 * 
	 * @param printerNum
	 * @param fRecno
	 * @return true 已占用 false 未占用
	 */
	public boolean checkPrinterNum(String printerNum, int fRecno);
	
	/**
	 * 获取对应格口的打印机IP
	 * @param chute_id
	 * @return
	 */
	public String get_printer_ip(int chute_id);
}
