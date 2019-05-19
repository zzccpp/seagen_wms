package cn.seagen.base.vo;

import java.util.Date;

import cn.seagen.base.utils.DateUtils;

/** 报表请求结构体 */
public class RequestBase extends RequestIndex {
	// 0按时间段、1按日(明细为小时)2按月(明细为天)3按年(明细为月)
	private int type = 0;
	// 指定查询某日、月、年的数据，也是时间段查询的开始日期
	private String date = DateUtils.formatDateTime("yyyy-MM-dd HH:mm:ss",new Date());
	// 指定时间段查询的结束日期,
	private String end_date = DateUtils.formatDateTime("yyyy-MM-dd HH:mm:ss",new Date());
	// 当前显示的页数(当有时)
	private int page = 0;
	private int more = 0;
	// 是否显示更多信息0不显示，非0显示
	// 当前每页显示的行数(当有时)
	private int rows = 0;
	// 特殊规则：如排序、是否开启功能（显示该数据）（当有时）
	private int rule = 0;
	
	// 0查询全部层级、1查询第一层级、2查询第二层级
	private int layer = 0;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getMore() {
		return more;
	}

	public void setMore(int more) {
		this.more = more;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getRule() {
		return rule;
	}

	public void setRule(int rule) {
		this.rule = rule;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}
	
	@Override
	public String toString() {
		return "RequestBase [type=" + type + ", date=" + date + ", end_date="
				+ end_date + ", page=" + page + ", more=" + more + ", rows="
				+ rows + ", rule=" + rule + ", id=" + id + ", app_code="
				+ app_code + ", timestamp=" + timestamp + ", message="
				+ message + "]";
	}
}
