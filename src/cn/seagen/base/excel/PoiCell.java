package cn.seagen.base.excel;

/** 单元格数据封装 */
public class PoiCell {
	private int rowid = 1;// 行序号
	private int cellid = 1;// 列充号
	private int rows = 1;// 占几行
	private int cells = 1;// 占几列
	private String context;

	/**
	 * 
	 * @param context
	 *            内容,此方法只适合于没有合并单元的情况
	 */
	public PoiCell(String context) {
		this.rowid = 0;
		this.cellid = 0;
		this.rows = 0;
		this.cells = 0;
		this.context = context;
	}

	/**
	 * 
	 * @param rowid
	 *            行序号
	 * @param colid
	 *            列充号
	 * @param context
	 *            内容
	 */
	public PoiCell(int rowid, int cellid, String context) {
		this.rowid = rowid;
		this.cellid = cellid;
		this.rows = 1;
		this.cells = 1;
		this.context = context;
	}

	/**
	 * 
	 * @param rowid
	 *            行序号
	 * @param rows
	 *            占几行
	 * @param cellid
	 *            列充号
	 * @param cells
	 *            占几列
	 * @param context内容
	 */
	public PoiCell(int rowid, int rows, int cellid, int cells, String context) {
		this.rowid = rowid;
		this.cellid = cellid;
		this.rows = rows;
		this.cells = cells;
		this.context = context;
	}

	public boolean isUnion() {
		return (rows > 1 || cells > 1);
	}
	public boolean isPrecise() {
		return (rowid > 0 && cellid > 0);
	}
	public int getRowid() {
		return rowid;
	}

	public void setRowid(int rowid) {
		this.rowid = rowid;
	}

	public int getCellid() {
		return cellid;
	}

	public void setCellid(int cellid) {
		this.cellid = cellid;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCells() {
		return cells;
	}

	public void setCells(int cells) {
		this.cells = cells;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Override
	public String toString() {
		return "PoiCell [rowid=" + rowid + ", cellid=" + cellid + ", rows=" + rows + ", cells=" + cells + ", context=" + context + "]";
	}

}
