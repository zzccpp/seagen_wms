package cn.seagen.base.dao;

import java.util.List;

import cn.seagen.base.bean.SystemSet;

/** 系统配制相关数据操作 */
public interface SysSetDao {
	
	/**
	 * 获取全部SystemSet列表
	 * @return  List<SystemSet>  
	 */
	public List<SystemSet> getSystemSetList();
	
	/**
	 * 修改配置信息
	 * @param systemSet 实体类
	 * @return  成功则返回true；失败返回false；
	 */
	public boolean updateSystemSet(SystemSet systemSet);
	
	/**
	 * 获取系统参数值
	 * @param index   system_set主键
	 * @return  返回SystemSet对象
	 */
	public SystemSet getSystemSet(int index);
	
	/**
	 * 初始化参数
	 * @param list 系统默认的参数配置集合
	 */
	public boolean insertInitParam(List<SystemSet> list);
	
	/**
	 * 获取系统设置列表根据set_name字符串
	 * @param sets   set_name字段字符串(,scanNum,supplyNum,carNum,chuteNum,)
	 * @return SystemSet集合  
	 */
	public List<SystemSet> getSystemSetListByNames(String setNames);
	
}
