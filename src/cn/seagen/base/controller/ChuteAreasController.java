package cn.seagen.base.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.seagen.base.bean.ChuteArea;
import cn.seagen.base.constant.EquipmentConstant;
import cn.seagen.base.service.ChuteAreaService;
import cn.seagen.base.utils.ParameterMap;
import cn.seagen.base.vo.ResponseBase;

/**
 * 用于格口区域信息相关设置
 * @author Adians
 * 
 */
@Controller
public class ChuteAreasController {
	protected Logger logger = LogManager.getLogger(ChuteAreasController.class.getName());
	
	@Resource
	private ChuteAreaService chuteAreaServiceImpl;
	
	@RequestMapping("/getChuteNum.do")
	@ResponseBody
	public Object getChuteNum (String timestamp, HttpServletRequest request) {
		Map<String,Object> data = new HashMap<String, Object>();
		List<ChuteArea> chuteAreas = chuteAreaServiceImpl.getChuteAreas();
		String strChute = "";
		int len = chuteAreas.size();
		for(int i = 0;i < len;i++){
			if(i==0){
				strChute = chuteAreas.get(i).getChuteNumList();
			}else {
				strChute = strChute + ","+chuteAreas.get(i).getChuteNumList();
			}
			
		}
		// 格口个数
		data.put("chute_num", EquipmentConstant.chuteNum);
		//已分配的格口
		data.put("chute_used", strChute);
		return data;
	}
	
	@RequestMapping("/getChuteAreas.do")
	@ResponseBody
	public List<ChuteArea> getChuteAreas () {
		return chuteAreaServiceImpl.getChuteAreas();
	}
	
	@RequestMapping("/addChuteArea.do")
	@ResponseBody
	public ResponseBase addChuteArea (String timestamp, HttpServletRequest request) {
		// 取得参数列表
		Map<String, Object> pmaps = ParameterMap.get(request);
		ResponseBase responseBase = new ResponseBase();
		ChuteArea chuteArea = new ChuteArea();
		
		chuteArea.setAreaName((String)pmaps.get("area_name"));
		chuteArea.setChuteNumList((String)pmaps.get("chute_num_list"));
		chuteArea.setReMark((String)pmaps.get("re_mark"));
		
		if (chuteAreaServiceImpl.insertChuteArea(chuteArea)) {
			responseBase.setResult(0);
			responseBase.setMessage("success");
		}else {
			responseBase.setResult(1);
			responseBase.setMessage("添加失败!");
		}
		return responseBase;
	}
	
	@RequestMapping("/updateChuteArea.do")
	@ResponseBody
	public ResponseBase updateChuteArea (String timestamp,int f_recno, HttpServletRequest request) {
		ResponseBase responseBase = new ResponseBase();
		ChuteArea chuteArea = new ChuteArea();
		Map<String, Object> pmaps = ParameterMap.get(request);
		chuteArea.setAreaName((String)pmaps.get("area_name"));
		chuteArea.setChuteNumList((String)pmaps.get("chute_num_list"));
		chuteArea.setReMark((String)pmaps.get("re_mark"));
		chuteArea.setfRecno(f_recno);
		if (chuteAreaServiceImpl.updateChuteArea(chuteArea)) {
			responseBase.setResult(0);
			responseBase.setMessage("success");
		}else {
			responseBase.setResult(1);
			responseBase.setMessage("修改失败!");
		}
		return responseBase;
	}
	
	@RequestMapping("/delChuteArea.do")
	@ResponseBody
	public ResponseBase delChuteArea (int f_recno) {
		ResponseBase responseBase = new ResponseBase();
		if (chuteAreaServiceImpl.deleteChuteArea(f_recno)) {
			responseBase.setResult(0);
			responseBase.setMessage("success");
		}else {
			responseBase.setResult(1);
			responseBase.setMessage("删除失败!");
		}
		return responseBase;
	}
	
	@RequestMapping("/checkChuteArea.do")
	@ResponseBody
	public ResponseBase checkChuteArea (int f_recno,String area_name){
		ResponseBase responseBase = new ResponseBase();
		
		if (chuteAreaServiceImpl.checkChuteArea(area_name,f_recno)) {
			responseBase.setResult(0);
			responseBase.setMessage("已使用");
		}else {
			responseBase.setResult(1);
			responseBase.setMessage("通过");
		}
		return responseBase;
	}
}

