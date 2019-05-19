package cn.seagen.base.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.seagen.base.bean.BoxBean;
import cn.seagen.base.dao.BoxDao;
import cn.seagen.base.service.BoxService;
@Service
public class BoxServiceImpl implements BoxService {
	//private static Logger logger = LogManager.getLogger(BoxServiceImpl.class);
	@Resource
	private BoxDao boxDaoImpl;
	
	@Override
	public synchronized BoxBean getUnusedBox(int type, String val) {
		BoxBean box= boxDaoImpl.getUnusedBox(type, val);
		if(box != null){
			boolean res = updateBoxStatus(box.getF_recno());
			if(res == false)
				return null;
		}
		return box;
	}

	@Override
	public boolean updateBoxStatus(long boxId) {
		return boxDaoImpl.lockUnusedBox(boxId);
	}

	@Override
	public boolean updateBox(BoxBean box) {
		return boxDaoImpl.updateBox(box);
	}

	@Override
	public boolean insertBoxTemp(BoxBean box) {
		
		return boxDaoImpl.insertBoxTemp(box);
	}

}
