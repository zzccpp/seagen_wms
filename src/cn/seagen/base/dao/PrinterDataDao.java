package cn.seagen.base.dao;

import java.util.List;
import java.util.Map;

import cn.seagen.base.bean.BoxBean;

public interface PrinterDataDao {
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
	public List<Map<String,Object>> queryPrinterData(int page, int rows, String boxCode, int chuteId);
	
	/**
	 * 查询箱号打印信息表数量
	 * @param boxCode
	 * @param chuteId
	 * @return
	 */
	public int queryPrinterDataCount(String boxCode, int chuteId);
}
