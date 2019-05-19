package cn.seagen.base.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.seagen.base.bean.Printer;
import cn.seagen.base.dao.PrinterDao;
import cn.seagen.base.service.PrinterService;

@Service
public class PrinterServiceImpl implements PrinterService {

	@Resource
	private PrinterDao printerDaoImpl;
	
	@Override
	public List<Printer> getPrinters() {
		return printerDaoImpl.getPrinters();
	}

	@Override
	public boolean updatePrinter(Printer printer) {
		return printerDaoImpl.updatePrinter(printer);
	}

	@Override
	public boolean insertPrinter(Printer printer) {
		return printerDaoImpl.insertPrinter(printer);
	}

	@Override
	public boolean deletePrinter(int fRecno) {
		return printerDaoImpl.deletePrinter(fRecno);
	}

	@Override
	public boolean checkPrinterNum(String printerNum, int fRecno) {
		return printerDaoImpl.checkPrinterNum(printerNum, fRecno);
	}

	@Override
	public String get_printer_ip(int chute_id) {
		return printerDaoImpl.get_printer_ip(chute_id);
	}

}
