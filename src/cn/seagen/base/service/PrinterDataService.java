package cn.seagen.base.service;

import cn.seagen.base.bean.BoxBean;

public interface PrinterDataService {
	/**
	 * 添加箱号打印记录
	 * @param box
	 * @return
	 */
	public boolean insertPrinterData(BoxBean box);
	
	/**
	 * 分页查询打印记录
	 * @param page
	 * @param rows
	 * @param boxCode
	 * @param chuteId
	 * @return
	 */
	public Object queryPrinterData(int page, int rows, String boxCode, int chuteId);
}
