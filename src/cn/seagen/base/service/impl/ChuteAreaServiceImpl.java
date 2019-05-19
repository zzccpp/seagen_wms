package cn.seagen.base.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.seagen.base.bean.ChuteArea;
import cn.seagen.base.dao.ChuteAreasDao;
import cn.seagen.base.service.ChuteAreaService;

@Service
public class ChuteAreaServiceImpl implements ChuteAreaService {

	@Resource
	private ChuteAreasDao chuteAreasDaoImpl;
	
	@Override
	public List<ChuteArea> getChuteAreas() {
		return chuteAreasDaoImpl.getChuteAreas();
	}

	@Override
	public boolean updateChuteArea(ChuteArea chuteArea) {
		return chuteAreasDaoImpl.updateChuteArea(chuteArea);
	}

	@Override
	public boolean insertChuteArea(ChuteArea chuteArea) {
		return chuteAreasDaoImpl.insertChuteArea(chuteArea);
	}

	@Override
	public boolean deleteChuteArea(int fRecno) {
		return chuteAreasDaoImpl.deleteChuteArea(fRecno);
	}

	@Override
	public boolean checkChuteArea(String chuteAreaName, int fRecno) {
		return chuteAreasDaoImpl.checkChuteArea(chuteAreaName, fRecno);
	}

}
