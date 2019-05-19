package cn.seagen.base.vo;

import java.util.HashMap;
import java.util.Map;

/** 用于报表中常用的函数 */
public class Reportfun {
	/**
	 * 生成Easy DataGrid的标题结构体
	 * 
	 * @param title
	 *            标题名称
	 * @param field
	 *            字段名称可为null
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param sortable
	 *            是否排序
	 * @param halign
	 *            垂直位置
	 * @param align
	 *            水平位置
	 * @param resizable
	 *            是否自动拉或
	 * @param rowspan
	 *            占几行
	 * @param colspan
	 *            占几列
	 * @param formatter
	 *            格式化函数名称，该函数在前端实现，可为null
	 * @return
	 */
	public static Map<String, Object> getColumn(String title, String field, //
			int width, int height, boolean sortable, String halign,//
			String align, boolean resizable, int rowspan, int colspan, String formatter) {
		Map<String, Object> column = new HashMap<String, Object>();
		column.put("title", title);
		column.put("height", height);
		if (sortable == true){
			column.put("sortable", sortable);
			column.put("sorter", "number_sort"); // 默认为数字对比排序		
		}
		column.put("halign", halign);
		column.put("align", align);
		if (resizable)
			column.put("resizable", resizable);
		int w = width;
		if (w == 0) {
			w = (title.length() > 5) ? (90 + (title.length() - 4) * 5) : 90;
		}
		column.put("width", w);
		if (field != null)
			column.put("field", field);
		if (rowspan > 1) {
			column.put("rowspan", rowspan);
			column.put("height", height * rowspan);
		}
		if (colspan > 1)
			column.put("colspan", colspan);

		if (formatter != null)
			column.put("formatter", formatter);
		return column;
	}

	/**
	 * 生成Easy DataGrid的标题结构体
	 * 
	 * @param title
	 *            标题名称
	 * @param field
	 *            字段名称可为null
	 * 
	 * @return
	 */
	public static Map<String, Object> getColumn(String title, String field) {
		return Reportfun.getColumn(title, field, 0, 50, false, "center", "center", false, 1, 1, null);
	}

	/**
	 * 生成Easy DataGrid的标题结构体
	 * 
	 * @param title
	 *            标题名称
	 * @param field
	 *            字段名称可为null
	 * @param sortable
	 *            是否排序
	 * @return
	 */
	public static Map<String, Object> getColumn(String title, String field, boolean sortable) {
		return Reportfun.getColumn(title, field, 0, 50, sortable, "center", "center", false, 1, 1, null);
	}
	/**
	 * 生成Easy DataGrid的标题结构体
	 * 
	 * @param title
	 *            标题名称
	 * @param field
	 *            字段名称可为null
	 * @param sortable
	 *            是否排序
	 * @return
	 */
	public static Map<String, Object> getColumn(String title, String field, boolean sortable, String sort_name) {
		Map<String, Object> columns = Reportfun.getColumn(title, field, 0, 50, sortable, "center", "center", false, 1, 1, null);
		columns.put("sorter", sort_name); // 替换默认的数字排序	 
		return columns;
	}
	
	
	/**
	 * 生成Easy DataGrid的标题结构体
	 * 
	 * @param title
	 *            标题名称
	 * @param field
	 *            字段名称可为null
	 * @param span
	 *            占几行
	 * @return
	 */
	public static Map<String, Object> getColumn(String title, String field, int rowspan, int colspan) {
		return Reportfun.getColumn(title, field, 0, 50, false, "center", "center", false, rowspan, colspan, null);
	}

	/**
	 * 生成Easy DataGrid的标题结构体
	 * 
	 * @param title
	 *            标题名称
	 * @param field
	 *            字段名称可为null
	 * @param width
	 *            宽度
	 * @return
	 */
	public static Map<String, Object> getColumn(String title, String field, int width) {
		return Reportfun.getColumn(title, field, width, 50, false, "center", "center", false, 1, 1, null);
	}
	
	/**
	 * 生成Easy DataGrid的标题结构体
	 * 
	 * @param title
	 *            标题名称
	 * @param field
	 *            字段名称可为null
	 * @param width
	 *            宽度
	 * @param sortable
	 *            是否排序
	 * @return
	 */
	public static Map<String, Object> getColumn(String title, String field, int width, boolean sortable) {
		return Reportfun.getColumn(title, field, width, 50, sortable, "center", "center", false, 1, 1, null);
	}
	
	/**
	 * 生成Easy DataGrid的标题结构体
	 * 
	 * 
	 * @param title
	 *            标题名称
	 * @param field
	 *            字段名称可为null
	 * @param formatter
	 *            格式化函数名称，该函数在前端实现，可为null
	 * @param sortable
	 *            是否排序
	 * @return
	 */
	public static Map<String, Object> getColumn(String title, String field, String formatter, boolean sortable) {
		return Reportfun.getColumn(title, field, 0, 50, sortable, "center", "center", false, 1, 1, formatter);
	}
	/**
	 * 生成Easy DataGrid的标题结构体
	 * 
	 * 
	 * @param title
	 *            标题名称
	 * @param field
	 *            字段名称可为null
	 * @param formatter
	 *            格式化函数名称，该函数在前端实现，可为null
	 * @return
	 */
	public static Map<String, Object> getColumn(String title, String field, String formatter) {
		return Reportfun.getColumn(title, field, 0, 50, false, "center", "center", false, 1, 1, formatter);
	}

	/**
	 * 生成Easy DataGrid的标题结构体
	 * 
	 * @param title
	 *            标题名称
	 * @param span
	 *            占几行
	 * @return
	 */
	public static Map<String, Object> getColumn(String title, int rowspan, int colspan) {
		return Reportfun.getColumn(title, null, 0, 50, false, "center", "center", false, rowspan, colspan, null);
	}
	/**
	 * 生成Easy DataGrid的标题结构体
	 * 
	 * @param title
	 *            标题名称
	 * @param span
	 *            占几行
	 * @param width
	 *           宽度            
	 * @return
	 */
	public static Map<String, Object> getColumn(String title, int rowspan, int colspan, int width) {
		return Reportfun.getColumn(title, null, width, 50, false, "center", "center", false, rowspan, colspan, null);
	}
	
	/**
	 * 生成Easy DataGrid的标题结构体
	 * 
	 * 
	 * @param title
	 *            标题名称
	 * @param field
	 *            字段名称可为null
	 * @param formatter
	 *            格式化函数名称，该函数在前端实现，可为null
	 * @param sortable
	 *            是否排序
	 * @param width
	 *           宽度           
	 * @return
	 */
	public static Map<String, Object> getColumn(String title, String field, String formatter, boolean sortable, int width) {
		return Reportfun.getColumn(title, field, width, 50, sortable, "center", "center", false, 1, 1, formatter);
	}
	
	/**
	 * 生成Easy DataGrid的标题结构体
	 * 
	 * @param title
	 *            标题名称
	 * @param field
	 *            字段名称可为null
	 * @param sortable
	 *            是否排序
	 * @param width
	 *           宽度     
	 * @return
	 */
	public static Map<String, Object> getColumn(String title, String field, boolean sortable, int width) {
		return Reportfun.getColumn(title, field, width, 50, sortable, "center", "center", false, 1, 1, null);
	}
}
