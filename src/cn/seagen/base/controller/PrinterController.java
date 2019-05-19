package cn.seagen.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.seagen.base.bean.Printer;
import cn.seagen.base.constant.EquipmentConstant;
import cn.seagen.base.service.PrinterService;
import cn.seagen.base.utils.ParameterMap;
import cn.seagen.base.vo.ResponseBase;

@Controller
public class PrinterController {
	
	@Resource
	private PrinterService printerServiceImpl;
	
	@ResponseBody
	@RequestMapping(value = "/getPrinters.do")
	public List<Printer> getPrinters() {
		List<Printer> printers = printerServiceImpl.getPrinters();
		return printers;
	}
	
	@ResponseBody
	@RequestMapping(value = "/addPrinter.do")
	public ResponseBase addPrinter(String timestamp, HttpServletRequest request){
		ResponseBase responseBase = new ResponseBase();
		Map<String, Object> pmaps = ParameterMap.get(request);
		Printer printer = new Printer();
		printer.setPrinterNum((String)pmaps.get("printer_num"));
		printer.setPrinterIp((String)pmaps.get("printer_ip"));
		printer.setChuteNumList(","+(String)pmaps.get("chute_num_list"));
		printer.setReMark((String)pmaps.get("re_mark"));
		if((!printerServiceImpl.checkPrinterNum(printer.getPrinterNum(),-1)) && printerServiceImpl.insertPrinter(printer)){
			responseBase.setResult(0);
			responseBase.setMessage("success");
		}else{
			responseBase.setResult(1);
			responseBase.setMessage("添加失败!");
		}
		return responseBase;
	}
	@ResponseBody
	@RequestMapping(value = "/updatePrinter.do")
	public ResponseBase updatePrinter(String timestamp,int f_recno, HttpServletRequest request){
		ResponseBase responseBase = new ResponseBase();
		Map<String, Object> pmaps = ParameterMap.get(request);
		Printer printer = new Printer();
		printer.setPrinterNum((String)pmaps.get("printer_num"));
		printer.setPrinterIp((String)pmaps.get("printer_ip"));
		printer.setChuteNumList(","+(String)pmaps.get("chute_num_list")+",");
		printer.setReMark((String)pmaps.get("re_mark"));
		printer.setfRecno(f_recno);
		if((!printerServiceImpl.checkPrinterNum(printer.getPrinterNum(),f_recno)) && printerServiceImpl.updatePrinter(printer)){
			responseBase.setResult(0);
			responseBase.setMessage("success");
		}else{
			responseBase.setResult(1);
			responseBase.setMessage("修改失败!");
		}
		return responseBase;
	}
	@ResponseBody
	@RequestMapping(value = "/delPrinter.do")
	public ResponseBase delPrinter(int fRecno){
		ResponseBase responseBase = new ResponseBase();
		if(printerServiceImpl.deletePrinter(fRecno)){
			responseBase.setResult(0);
			responseBase.setMessage("success");
		}else{
			responseBase.setResult(1);
			responseBase.setMessage("删除失败!");
		}
		return responseBase;
	}
	
	@ResponseBody
	@RequestMapping(value = "/checkPrinterNum.do")
	public ResponseBase checkPrinterNum(String printerNum,int fRecno){
		ResponseBase responseBase = new ResponseBase();
		if(printerServiceImpl.checkPrinterNum(printerNum,fRecno)){
			responseBase.setResult(0);
			responseBase.setMessage("已使用");
		}else{
			responseBase.setResult(1);
			responseBase.setMessage("通过");
		}
		return responseBase;
	}
	
	@RequestMapping("/getPrinterChuteNum.do")
	@ResponseBody
	public Object getPrinterChuteNum (String timestamp, HttpServletRequest request) {
		Map<String,Object> data = new HashMap<String, Object>();
		List<Printer> printers = printerServiceImpl.getPrinters();
		String strChute = "";
		int len = printers.size();
		for(int i = 0;i < len;i++){
			if(i==0){
				strChute = printers.get(i).getChuteNumList();
			}else {
				strChute = strChute + ","+printers.get(i).getChuteNumList();
			}
		}
		// 格口个数
		data.put("chute_num", EquipmentConstant.chuteNum);
		//已分配的格口
		data.put("chute_used", strChute);
		return data;
	}
}
