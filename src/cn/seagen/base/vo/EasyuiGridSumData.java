package cn.seagen.base.vo;

import java.util.List;
import java.util.Map;

/** 前端Easyui dataGrid数据格式 */
public class EasyuiGridSumData {
	private int result;// 返回状态0成功非0失败
	private String message; // 返回消息
	private List<Object> frozenColumns;// 固定标题
	private List<Object> columns;// 移动标题
	private List<Map<String, Object>> data;// 数据
	private Map<String, Object> footer; // 脚本汇总

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Object> getFrozenColumns() {
		return frozenColumns;
	}

	public void setFrozenColumns(List<Object> frozenColumns) {
		this.frozenColumns = frozenColumns;
	}

	public List<Object> getColumns() {
		return columns;
	}

	public void setColumns(List<Object> columns) {
		this.columns = columns;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}

	public Map<String, Object> getFooter() {
		return footer;
	}

	public void setFooter(Map<String, Object> footer) {
		this.footer = footer;
	}

	@Override
	public String toString() {
		return "EasyuiGridSumData [result=" + result + ", message=" + message + ", frozenColumns=" + frozenColumns + ", columns=" + columns + ", data=" + data + ", footer=" + footer + "]";
	}

}
