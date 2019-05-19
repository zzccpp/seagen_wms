package cn.seagen.base.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.seagen.base.vo.ScheduledRuntimer;

public class ScheduledRuntimerManager {
	// 把有线程可任务执行的时间集
	private static Map<String, ScheduledRuntimer> runtimers = new HashMap<String, ScheduledRuntimer>();

	public static Map<String, ScheduledRuntimer> getRuntimers() {
		return runtimers;
	}

	public static void setRuntimers(Map<String, ScheduledRuntimer> runtimers) {
		ScheduledRuntimerManager.runtimers = runtimers;
	}

	public static ScheduledRuntimer getRuntimer(String title) {
		ScheduledRuntimer sr = runtimers.get(title);
		if (sr == null) {
			sr = new ScheduledRuntimer(title);
			runtimers.put(title, sr);
		}
		return sr;
	}
	
	public static synchronized Object getRuntimerList() {
		Map<String, ScheduledRuntimer> runtimerList = getRuntimers();
		Map<String,Object> runtimerMap = new HashMap<String, Object>();
		runtimerMap.put("count", runtimerList.size());
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for(String key : runtimerList.keySet()) {
			Map<String, Object> runtimerdetail = new HashMap<String, Object>();
			runtimerdetail.put("title", key);
			runtimerdetail.put("min", runtimerList.get(key).getMin());
			runtimerdetail.put("max", runtimerList.get(key).getMax());
			runtimerdetail.put("avg", runtimerList.get(key).getAvg());
			runtimerdetail.put("size", runtimerList.get(key).getSize());
			dataList.add(runtimerdetail);
		}
		runtimerMap.put("data", dataList);
		return runtimerMap;
	}

}
