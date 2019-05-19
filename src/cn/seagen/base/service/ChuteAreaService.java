package cn.seagen.base.service;

import java.util.List;

import cn.seagen.base.bean.ChuteArea;

public interface ChuteAreaService {
	
	/**
	 * 获取格口区域配置列表
	 * @return
	 */
	public List<ChuteArea> getChuteAreas();
	
	/**
	 * 更新格口区域配置信息
	 * @param chuteArea
	 * @return
	 */
	public boolean updateChuteArea(ChuteArea chuteArea);
	
	/**
	 * 格口区域信息插入
	 * @param chuteArea
	 * @return
	 */
	public boolean insertChuteArea(ChuteArea chuteArea);
	
	/**
	 * 删除格口区域
	 * @param fRecno
	 * @return
	 */
	public boolean deleteChuteArea(int fRecno);
	
	/**
	 * 检查区域名称是否已存在
	 * @param chuteAreaName
	 * @param fRecno
	 *            判断是否是添加还是修改，如果修改的话区域名称比对不比对自己的
	 * @return true 已占用 false 未占用
	 */
	public boolean checkChuteArea(String chuteAreaName, int fRecno);
}
