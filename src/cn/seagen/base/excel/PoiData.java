package cn.seagen.base.excel;

import java.util.ArrayList;
import java.util.List;

/** 数据封装 */
public class PoiData {
	private PoiCell title; // 标题
	private PoiCell query;// 查询条件
	private List<List<PoiCell>> heads = new ArrayList<List<PoiCell>>();// 标题
	private List<List<PoiCell>> datas = new ArrayList<List<PoiCell>>();// 数据
	private List<List<PoiCell>> footers = new ArrayList<List<PoiCell>>();// 汇总

	public PoiCell getTitle() {
		return title;
	}

	public void setTitle(PoiCell titile) {
		this.title = titile;
	}

	public PoiCell getQuery() {
		return query;
	}

	public void setQuery(PoiCell query) {
		this.query = query;
	}

	public List<List<PoiCell>> getHeads() {
		return heads;
	}

	public void setHeads(List<List<PoiCell>> heads) {
		this.heads = heads;
	}

	public List<List<PoiCell>> getDatas() {
		return datas;
	}

	public void setDatas(List<List<PoiCell>> datas) {
		this.datas = datas;
	}

	public List<List<PoiCell>> getFooters() {
		return footers;
	}

	public void setFooters(List<List<PoiCell>> footers) {
		this.footers = footers;
	}

	@Override
	public String toString() {
		return "PoiData [titile=" + title + ", query=" + query + ", heads=" + heads + ", datas=" + datas + ", footers=" + footers + "]";
	}

}
