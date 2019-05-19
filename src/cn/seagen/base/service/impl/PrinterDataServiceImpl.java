package cn.seagen.base.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.seagen.base.bean.BoxBean;
import cn.seagen.base.dao.PrinterDataDao;
import cn.seagen.base.service.PrinterDataService;
import cn.seagen.base.vo.EasyuiGridData;
@Service
public class PrinterDataServiceImpl implements PrinterDataService {
	@Resource
	private PrinterDataDao printerDataDaoImpl;

	@Override
	public boolean insertPrinterData(BoxBean box) {
		return printerDataDaoImpl.insertPrinterData(box);
	}

	@Override
	public Object queryPrinterData(int page, int rows, String boxCode,
			int chuteId) {
		EasyuiGridData base = new EasyuiGridData();
		base.setRows(printerDataDaoImpl.queryPrinterData(page, rows, boxCode, chuteId));
		base.setTotal(printerDataDaoImpl.queryPrinterDataCount(boxCode, chuteId));
		return base;
	}
	
}
