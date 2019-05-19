package cn.seagen.base.dao;

import java.util.List;
import java.util.Map;

import cn.seagen.base.vo.RequestBase;

public interface ReportChuteDao {
	/**
	 * 获取格口报表数据
	 * @param requestBase
	 * @return
	 */
	public List<Map<String, Object>> queryChute(RequestBase requestBase);
}
