package cn.seagen.base.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.seagen.base.bean.SystemSet;
import cn.seagen.base.constant.BackUpConstant;
import cn.seagen.base.constant.ChuteConstant;
import cn.seagen.base.constant.EquipmentConstant;
import cn.seagen.base.constant.VersionConstant;

@SuppressWarnings("rawtypes")
public class SystemSetUtils {

	private static Logger logger = LogManager.getLogger(SystemSetUtils.class);

	private static Map<String, Class> map = new LinkedHashMap<String, Class>();
	
	private static Map<String, String> title = new LinkedHashMap<String, String>();
	/**
	 * map存SystemSet对应的各常量类，set_name所对应值
	 */
	static {
		//设备
		map.put("scanNum", EquipmentConstant.class);
		map.put("firsrLayerScanNum", EquipmentConstant.class);
		map.put("secondLayerScanNum", EquipmentConstant.class);
		map.put("supplyNum", EquipmentConstant.class);
		map.put("firsrLayerSupplyNum", EquipmentConstant.class);
		map.put("secondLayerSupplyNum", EquipmentConstant.class);
		map.put("carNum", EquipmentConstant.class);
		map.put("chuteNum", EquipmentConstant.class);
		map.put("firsrLayer", EquipmentConstant.class);
		map.put("secondLayer", EquipmentConstant.class);
		//备份
		map.put("backupGenralData", BackUpConstant.class);
		map.put("backupMainData", BackUpConstant.class);
		map.put("backupReport", BackUpConstant.class);
		
		//格口
		map.put("specialChute", ChuteConstant.class);
		map.put("comChute", ChuteConstant.class);
		
		//版本
		map.put("sysDbVer", VersionConstant.class);
		map.put("softSourceCode", VersionConstant.class);
		map.put("softOutput", VersionConstant.class);
		map.put("softVersion", VersionConstant.class);
		
		//初始化类别标题
		title.put("ChuteConstant", "格口设置");
		title.put("EquipmentConstant", "设备数量设置");
		title.put("BackUpConstant", "数据备份设置");
		title.put("VersionConstant", "系统版本");
	}

	/**
	 * 初始化，参数赋值
	 * @param list
	 * @throws Exception
	 */
	public static void initValue(List<SystemSet> list) throws Exception {
		for (SystemSet systemSet : list) {
			Class<?> cls = map.get(systemSet.getSetName());
			initObjectValue(systemSet, cls);
		}
	}

	/**
	 * 常量对象初始化，参数赋值
	 * @param list List<SystemSet>
	 * @param cls  Class<?>
	 * @param o   常量对象
	 * @throws Exception
	 */
	public static void initObjectValue(SystemSet info, Class<?> cls) throws Exception {
		Field field = null;
		String type = null;
		try {
			field = cls.getField(info.getSetName());
		} catch (NoSuchFieldException e) {
			logger.error(info.getSetName() + "此参数变量不存在!");
			return;
		}
		if (null != field) {
			Object o = cls.newInstance();
			type = field.getType().toString();
			if (type.endsWith("String")) {
				field.set(o, info.getSetValue());
			} else if (type.endsWith("int")) {
				field.set(o, Integer.parseInt(info.getSetValue()));
			}
		}
	}

	/**
	 * 获取默认的初始化配置
	 * @return 返回需要初始化的参数集合
	 */
	public static List<SystemSet> getParams() throws Exception {
		String className = "";
		Field field = null;
		List<SystemSet> list = new ArrayList<SystemSet>();
		SystemSet info = null;
		if (!map.isEmpty()) {
			Class<?> cls = null;
			Object o = null;
			for (Entry<String, Class> entry : map.entrySet()) {
				String key = entry.getKey();
				cls = map.get(key);
				String clsName = cls.getName();
				if(className.equals(clsName))    //防止重复添加
					continue;
				
				o = cls.newInstance();
				Field[] fields = cls.getFields();
				for (int i = 0; i < fields.length; i++) {
					field = fields[i];
					if (!field.getName().endsWith("Comment")
							&& !field.getName().endsWith("Name")) {
						info = new SystemSet();
						info.setSetName(field.getName());
						info.setSetValue(field.get(o).toString());
						try {
							Field field2 = null;
							field2 = cls.getField(field.getName() + "Name");
							info.setSetNameCn(field2.get(o).toString());
							
							field = cls.getField(field.getName() + "Comment");
							info.setSetMark(field.get(o).toString());
						} catch (NoSuchFieldException e) {
							info.setSetNameCn("");
							info.setSetMark("");
							continue;
						}
						list.add(info);
					}
				}
				className = clsName;   
			}
		}
		return list;
	}

	/**
	 * 根据页面更改，修改参数值
	 * @param setName  参数名称
	 * @param setValue 参数值
	 * @throws Exception 
	 */
	public static void updateValue(String name, String value) throws Exception {
		Class<?> cls = map.get(name);
		Object o = cls.newInstance();

		Field field = null;
		String type = null;
		try {
			field = cls.getField(name);
		} catch (NoSuchFieldException e) {
			logger.error(name + "此参数变量不存在!");
			return;
		}
		if (null != field) {
			type = field.getType().toString();
			try {
				Method method = cls.getMethod("get"+StringUtils.capitalize(name)+"RegEx");
				String fieldRegEx = (String) method.invoke(o);
				Pattern compile = Pattern.compile(fieldRegEx);
				Matcher matcher = compile.matcher(value);
				if (!matcher.matches()) {// 数据格式不匹配
					method = cls.getMethod("get"+StringUtils.capitalize(name)+"Error");
					throw new RuntimeException((String) method.invoke(o));
				}
			} catch (NoSuchMethodException e) {
				logger.error("无此方法!",e);
			}
			if (type.endsWith("String")) {
				field.set(o, value);
			} else if (type.endsWith("int")) {
				field.set(o, Integer.parseInt(value));
			}
		}
	}
	
	/**
	 * 拼接常量类set_name对应字符串
	 * @param 类对象
	 * @return  字符串,如(",scanNum,supplyNum,carNum,chuteNum,")
	 */
	public static String initConstant(String name){
		StringBuffer sb = new StringBuffer(",");
		for (String key : map.keySet()) {
			if (map.get(key).getSimpleName().equals(name)) {
				sb.append(key+",");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 页面显示参数类型数据处理
	 * @return 
	 */
	public static Map<String,Object> getTitleMap(){
		Map<String, Object> map1 = new LinkedHashMap<String, Object>();
		List<Map<String,String>> titleMapList = new ArrayList<Map<String,String>>();
		
		for (Entry<String, String> entry : title.entrySet()) {
			 Map<String, String> map2 = new LinkedHashMap<String, String>();
		     map2.put("key", entry.getKey());
		     map2.put("value", entry.getValue());
		     titleMapList.add(map2);
		}
		map1.put("rows", titleMapList);
	    map1.put("total", 100);
		return map1;
	}
}
