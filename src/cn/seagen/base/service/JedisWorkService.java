package cn.seagen.base.service;

import java.util.List;

import cn.seagen.base.bean.SortschemeDetail;
import cn.seagen.base.bean.Waybill;

public interface JedisWorkService {
	/**
	 * 将下载的运单数据放入到redis内存数据库中
	 * @param wayBillList 运单信息集合
	 * @return
	 */
	public boolean insertWaybillToRedis(List<Waybill> wayBillList);
	/**
	 * 将当前运行的分拣方案明细数据放入到JEDIS内存数据库中
	 * @param schemeList 分拣方案明细数据集合
	 * @return
	 */
	public boolean insertSitecodeToRedis(List<SortschemeDetail> schemeList);
	/**
	 * 删除当前JEDIS内存数据库中运行的分拣方案明细数据
	 * @param schemeList 分拣方案明细数据集合
	 * @return
	 */
	public boolean deleteSitecodeFromRedis(List<SortschemeDetail> schemeList);
}
