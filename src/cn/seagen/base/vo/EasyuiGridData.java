package cn.seagen.base.vo;

import java.util.List;
import java.util.Map;

/** 前端Easyui dataGrid数据格式 */
public class EasyuiGridData {
	private int total;// 总条数
	private List<Map<String, Object>> rows;// 数据
	private List<Map<String, Object>> footer; // 脚本汇总

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Map<String, Object>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String, Object>> rows) {
		this.rows = rows;
	}

	public List<Map<String, Object>> getFooter() {
		return footer;
	}

	public void setFooter(List<Map<String, Object>> footer) {
		this.footer = footer;
	}

	public EasyuiGridData() {
		this.total = 0;
		this.rows = null;
		this.footer = null;
	}

	@Override
	public String toString() {
		return "EasyuiGridData [total=" + total + ", rows=" + rows + ", footer=" + footer + "]";
	}

}
