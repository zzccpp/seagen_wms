package cn.seagen.base.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ParameterMap {
	protected static Logger logger = LogManager.getLogger(ParameterMap.class.getName());

	// 从request中获得参数Map，并返回可读的Map
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> get(HttpServletRequest request) {
		// properties返回的是只读的，不能修改，是因为系统对返回的参数进行了只读保护
		Map properties = request.getParameterMap();
		// 返回值Map
		Map<String, Object> map = new HashMap<String, Object>();
		//
		try {
			Iterator entries = properties.entrySet().iterator();
			Map.Entry entry;
			String name = "";
			Object value = "";
			String parameterstr = "ParameterMap:";

			while (entries.hasNext()) {
				entry = (Map.Entry) entries.next();
				name = ((String) entry.getKey()).toLowerCase().trim();
				Object valueObj = entry.getValue();
				if (null == valueObj) {
					value = "";
				} else if (valueObj instanceof String[]) {
					String[] values = (String[]) valueObj;
					String val = "";
					for (int i = 0; i < values.length; i++) {
						val = val + values[i].trim() + ";";
					}
					val = val.substring(0, val.length() - 1);
					value = val;
				} else {
					value = valueObj.toString().trim();
				}
				map.put(name, value);
				parameterstr = parameterstr + "&" + name + "=" + value;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(" ParameterMap getobj error:" + e.getMessage(), e);
		}
		return map;
	}
}
