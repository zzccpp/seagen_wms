package cn.seagen.base.excel;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/** 操作Excel工具类 */
@SuppressWarnings("deprecation")
public class PoiExcel {
	private XSSFWorkbook workbook = null;
	private XSSFCellStyle cellTitleStyle;
	private XSSFCellStyle cellQueryStyle;
	private XSSFCellStyle cellHeadStyle;
	private XSSFCellStyle cellDataStyle;
	private XSSFCellStyle cellFooterStyle;
	private XSSFCellStyle cellDataColorStyle;
	private int startRow = 1, startCell = 1; // 记录开始位置
	private int splitRow = 0, splitCell = 0; // 记录分割位置
	private PoiData poiData = null; // 数据
	//
	private int headRows = 1;// 列表头点用行数
	private int dataRows = 1;// 数据点用行数
	private int dataCells = 0;// 数据占用列数

	public PoiExcel() {
		// 创建操作Excel文档对象
		workbook = getWorkbook();
		// 创建样式
		createTitleStyle();
		createQueryStyle();
		createHeadStyle();
		createDataStyle();
		createDataColorStyle();
		createFooterStyle();
	}

	public XSSFWorkbook getWorkbook() {
		if (workbook == null) {
			// 创建操作Excel文档对象
			workbook = new XSSFWorkbook();
			// 创建Sheet页
			workbook.createSheet();

		}
		return workbook;
	}

	public void setStartPos(int rowId, int cellId) {
		this.startRow = rowId;
		this.startCell = cellId;
	}

	public void setSplitPos(int rowId, int cellId) {
		this.splitRow = rowId;
		this.splitCell = cellId;
	}

	public void setSplitPosAndExcute(int rowId, int cellId) {
		this.splitRow = rowId;
		this.splitCell = cellId;
		XSSFSheet sheet = getWorkbook().getSheetAt(0);
		sheet.createFreezePane(cellId, rowId);
	}

	public PoiData getPoiData() {
		return poiData;
	}

	public void setPoiData(PoiData poiData) {
		this.poiData = poiData;
	}

	public void setDataAndExcute(PoiData poiData) {
		this.poiData = poiData;
		excute();
	}

	/** 创建标题样式 */

	private void createTitleStyle() {
		cellTitleStyle = getWorkbook().createCellStyle();
		// 位置
		cellTitleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置单元格居中
		cellTitleStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 字体
		Font font = getWorkbook().createFont();
		font.setBold(true);
		font.setFontName("黑体");
		font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 24);// 设置字体大小
		cellTitleStyle.setFont(font);
		// 边框
		cellTitleStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellTitleStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellTitleStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellTitleStyle.setBorderRight(CellStyle.BORDER_THIN);
		// 背景
		cellTitleStyle.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
		cellTitleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	}

	/** 创建条件样式 */
	private void createQueryStyle() {
		cellQueryStyle = getWorkbook().createCellStyle();
		// 位置
		cellQueryStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置单元格居中
		cellQueryStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 字体
		Font font = getWorkbook().createFont();
		font.setFontName("黑体");
		font.setColor(HSSFColor.BLUE.index);
		font.setFontHeightInPoints((short) 10);// 设置字体大小
		cellQueryStyle.setFont(font);
		// 边框
		cellQueryStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellQueryStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellQueryStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellQueryStyle.setBorderRight(CellStyle.BORDER_THIN);
		// 背景
		cellQueryStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellQueryStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	}

	/** 创建列表头样式 */
	private void createHeadStyle() {
		cellHeadStyle = getWorkbook().createCellStyle();
		// 位置
		cellHeadStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置单元格居中
		cellHeadStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 字体
		Font font = getWorkbook().createFont();
		font.setBold(true);
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 11);// 设置字体大小
		cellHeadStyle.setFont(font);
		// 边框
		cellHeadStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellHeadStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellHeadStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellHeadStyle.setBorderRight(CellStyle.BORDER_THIN);
		// 背景
		cellHeadStyle.setFillForegroundColor(HSSFColor.LEMON_CHIFFON.index);
		cellHeadStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

	}

	/** 创建数据格样式 */
	/** 主标题单元格样式，居中、粗体、字体【黑体】 * 字体大小 */
	private void createDataStyle() {
		cellDataStyle = getWorkbook().createCellStyle();
		// 位置
		cellDataStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置单元格居中
		cellDataStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 字体
		Font font = getWorkbook().createFont();
		font.setBold(false);
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);// 设置字体大小
		cellDataStyle.setFont(font);
		cellDataStyle.setWrapText(true);
		// 边框
		cellDataStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellDataStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellDataStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellDataStyle.setBorderRight(CellStyle.BORDER_THIN);
	}

	private void createDataColorStyle() {
		cellDataColorStyle = getWorkbook().createCellStyle();
		// 位置
		cellDataColorStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置单元格居中
		cellDataColorStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 字体
		Font font = getWorkbook().createFont();
		font.setBold(false);
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);// 设置字体大小
		cellDataColorStyle.setFont(font);
		cellDataColorStyle.setWrapText(true);
		// 边框
		cellDataColorStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellDataColorStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellDataColorStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellDataColorStyle.setBorderRight(CellStyle.BORDER_THIN);
		// 背景
		cellDataColorStyle.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
		cellDataColorStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	}

	/** 创建汇总脚本样式 */
	private void createFooterStyle() {
		cellFooterStyle = getWorkbook().createCellStyle();
		// 位置
		cellFooterStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 设置单元格居中
		cellFooterStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		// 字体
		Font font = getWorkbook().createFont();
		font.setBold(true);
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 11);// 设置字体大小
		cellFooterStyle.setFont(font);
		cellFooterStyle.setWrapText(true);
		// 边框
		cellFooterStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellFooterStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellFooterStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellFooterStyle.setBorderRight(CellStyle.BORDER_THIN);
		// 背景
		cellFooterStyle.setFillForegroundColor(HSSFColor.LEMON_CHIFFON.index);
		cellFooterStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

	}

	private XSSFCell getCell(XSSFRow row, int cellid) {
		XSSFCell cell = row.getCell(cellid);
		if (cell == null)
			cell = row.createCell(cellid);
		return cell;
	}

	private XSSFRow getRow(XSSFSheet sheet, int rowid) {
		XSSFRow row = sheet.getRow(rowid);
		if (row == null)
			row = sheet.createRow(rowid);
		return row;
	}

	/**
	 * 
	 * @param poiCells
	 *            行数据对象集合
	 */
	public void writeRow(List<PoiCell> poiCells) {
		writeRow(poiCells, 0);
	}

	/**
	 * 
	 * @param poiCells
	 *            行数据对象集合
	 * @param rowHight
	 *            行高 >0有效
	 */
	public void writeRow(List<PoiCell> poiCells, int rowHight) {
		writeRow(poiCells, rowHight, -1);
	}

	/**
	 * 
	 * @param poiCells
	 *            行数据对象集合
	 * @param rowHight
	 *            行高 >0有效
	 * @param flag
	 *            1标题 2条件3表头 4数据5汇总脚本，其它自定义
	 */
	public void writeRow(List<PoiCell> poiCells, int rowHight, int flag) {
		XSSFCellStyle cellStyle = null;

		switch (flag) {
		case 1:
			cellStyle = cellTitleStyle;
			break;
		case 2:
			cellStyle = cellQueryStyle;
			break;
		case 3:
			cellStyle = cellHeadStyle;
			break;
		case 4:
			cellStyle = cellDataStyle;
			break;
		case 5:
			cellStyle = cellFooterStyle;
			break;
		default:
			break;
		}
		writeRow(poiCells, rowHight, cellStyle);
	}

	/**
	 * 写行数据(精准位置写)
	 * 
	 * @param poiCells
	 *            行数据对象集合
	 * @param rowHight
	 *            行高 >0有效
	 * 
	 * @param cellStyle
	 *            自定义样式
	 */
	public void writeRow(List<PoiCell> poiCells, int rowHight, XSSFCellStyle cellStyle) {
		if (poiCells.size() == 0)
			return;
		int rowNum = poiCells.get(0).getRowid();
		// 得到sheet
		XSSFSheet sheet = getWorkbook().getSheetAt(0);

		// 创建行对象
		XSSFRow row = getRow(sheet, rowNum);
		// 设置行高
		if (rowHight > 0)
			row.setHeightInPoints(rowHight);
		CellRangeAddress region = null;
		XSSFCell cell = null;
		PoiCell pc = null;
		try {
			for (int i = 0; i < poiCells.size(); i++) {
				pc = poiCells.get(i);
				if (pc.isUnion()) {// 需要合并单元格
					// 先对每个格子设置样式
					for (int j = pc.getRowid(); j <= pc.getRowid() + pc.getRows() - 1; j++) {
						XSSFRow rowj = getRow(sheet, j);
						for (int j2 = pc.getCellid(); j2 <= pc.getCellid() + pc.getCells() - 1; j2++) {
							cell = rowj.createCell(j2);
							cell.setCellStyle(cellStyle);
						}
					}
					region = new CellRangeAddress(pc.getRowid(), pc.getRowid() + pc.getRows() - 1, //
							pc.getCellid(), pc.getCellid() + pc.getCells() - 1);
					sheet.addMergedRegion(region);
				}
				cell = getCell(row, pc.getCellid());
				cell.setCellValue(pc.getContext());
				cell.setCellStyle(cellStyle);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param poiCells
	 *            行数据对象集合
	 */
	public void writeRow_NoMerged(List<PoiCell> poiCells, int rowId, int cellId) {
		writeRow_NoMerged(poiCells, 0, rowId, cellId);
	}

	/**
	 * 
	 * @param poiCells
	 *            行数据对象集合
	 * @param rowHight
	 *            行高 >0有效
	 */
	public void writeRow_NoMerged(List<PoiCell> poiCells, int rowHight, int rowId, int cellId) {
		writeRow_NoMerged(poiCells, rowHight, rowId, cellId, -1);
	}

	/**
	 * 
	 * @param poiCells
	 *            行数据对象集合
	 * @param rowHight
	 *            行高 >0有效
	 * @param flag
	 *            1标题 2条件3表头 4数据5汇总脚本，其它自定义
	 */
	public void writeRow_NoMerged(List<PoiCell> poiCells, int rowHight, int rowId, int cellId, int flag) {
		XSSFCellStyle cellStyle = null;

		switch (flag) {
		case 1:
			cellStyle = cellTitleStyle;
			break;
		case 2:
			cellStyle = cellQueryStyle;
			break;
		case 3:
			cellStyle = cellHeadStyle;
			break;
		case 4:
			cellStyle = cellDataStyle;
			break;
		case 5:
			cellStyle = cellFooterStyle;
			break;
		default:
			break;
		}
		writeRow_NoMerged(poiCells, rowHight, rowId, cellId, cellStyle);
	}

	/**
	 * 写行数据(非精准无合并单无格)
	 * 
	 * @param poiCells
	 *            行数据对象集合
	 * @param rowHight
	 *            行高 >0有效
	 * @param rowId
	 *            行序号
	 * @param cellId
	 *            列序 号
	 * 
	 * @param cellStyle
	 *            当且flag=-1有效 自定义样式
	 */
	public void writeRow_NoMerged(List<PoiCell> poiCells, int rowHight, int rowId, int cellId, XSSFCellStyle cellStyle) {
		if (poiCells.size() == 0)
			return;
		// 得到sheet
		XSSFSheet sheet = getWorkbook().getSheetAt(0);

		// 创建行对象
		XSSFRow row = getRow(sheet, rowId);
		// 设置行高
		if (rowHight > 0)
			row.setHeightInPoints(rowHight);

		XSSFCell cell = null;
		PoiCell pc = null;
		int c = cellId;// 定义开始列
		try {
			for (int i = 0; i < poiCells.size(); i++) {
				pc = poiCells.get(i);
				cell = getCell(row, c);
				cell.setCellValue(pc.getContext());
				cell.setCellStyle(cellStyle);
				c += 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 生成数据 */
	public void excute() {
		try {
			writeHeadRows();
			writeDataRows();
			writeFooterRows();
			writeTitleRow();
			writeQueryRow();
			XSSFSheet sheet = getWorkbook().getSheetAt(0);
			for (int i = startCell; i < dataCells + startCell; i++) {
				sheet.autoSizeColumn(i);
			}
			// 冻结
			// XSSFSheet sheet = getWorkbook().getSheetAt(0);
			if (splitRow > 0 || splitCell > 0)
				sheet.createFreezePane(splitCell, splitRow);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 绘制Head
	private void writeHeadRows() {
		if ((poiData.getHeads().size() == 0) || (poiData.getHeads().get(0).size()) == 0)
			return;
		headRows = poiData.getHeads().size();
		// 精准
		if (poiData.getHeads().get(0).get(0).isPrecise()) {
			dataCells = 0;
			for (int i = 0; i < poiData.getHeads().size(); i++) {
				int dcs = 0;
				List<PoiCell> poiCells = new ArrayList<PoiCell>();
				for (int j = 0; j < poiData.getHeads().get(i).size(); j++) {
					PoiCell pc = poiData.getHeads().get(i).get(j);
					dcs += pc.getCells();
					poiCells.add(new PoiCell(pc.getRowid() + startRow + 1, pc.getRows(), //
							pc.getCellid() + startCell - 1, pc.getCells(), pc.getContext()));
				}
				if (dataCells < dcs)
					dataCells = dcs;
				writeRow(poiCells, 14, cellHeadStyle);
			}
		} else {
			int r = startRow + 2, c = startCell;// 定义开始行列
			dataCells = poiData.getHeads().get(0).size();
			for (int i = 0; i < poiData.getHeads().size(); i++) {
				writeRow_NoMerged(poiData.getHeads().get(i), 18, r, c, cellHeadStyle);
				r += 1;
			}
		}

	}

	// 绘制Data
	private void writeDataRows() {
		if ((poiData.getDatas().size() == 0) || (poiData.getDatas().get(0).size()) == 0)
			return;
		dataRows = poiData.getDatas().size();
		// 精准
		if (poiData.getDatas().get(0).get(0).isPrecise()) {
			for (int i = 0; i < poiData.getDatas().size(); i++) {
				List<PoiCell> poiCells = new ArrayList<PoiCell>();
				for (int j = 0; j < poiData.getDatas().get(i).size(); j++) {
					PoiCell pc = poiData.getDatas().get(i).get(j);
					poiCells.add(new PoiCell(pc.getRowid() + startRow + headRows + 1, pc.getRows(), //
							pc.getCellid() + startCell - 1, pc.getCells(), pc.getContext()));
				}
				if (poiCells.get(0).getRowid() % 2 == 0)
					writeRow(poiCells, 12, cellDataColorStyle);
				else
					writeRow(poiCells, 12, cellDataStyle);
			}
		} else {
			dataCells = poiData.getDatas().get(0).size();
			int r = startRow + 2 + headRows, c = startCell;// 定义开始行列
			for (int i = 0; i < poiData.getDatas().size(); i++) {
				if (r % 2 == 0)
					writeRow_NoMerged(poiData.getDatas().get(i), 18, r, c, cellDataColorStyle);
				else
					writeRow_NoMerged(poiData.getDatas().get(i), 18, r, c, cellDataStyle);
				r += 1;
			}
		}
	}

	// 绘制footer
	private void writeFooterRows() {
		if ((poiData.getFooters().size() == 0) || (poiData.getFooters().get(0).size()) == 0)
			return;
		// 精准
		if (poiData.getFooters().get(0).get(0).isPrecise()) {
			for (int i = 0; i < poiData.getFooters().size(); i++) {
				List<PoiCell> poiCells = new ArrayList<PoiCell>();
				for (int j = 0; j < poiData.getFooters().get(i).size(); j++) {
					PoiCell pc = poiData.getFooters().get(i).get(j);
					poiCells.add(new PoiCell(pc.getRowid() + startRow + headRows + dataRows + 1, //
							pc.getRows(), pc.getCellid() + startCell - 1, pc.getCells(), pc.getContext()));
				}
				writeRow(poiCells, 12, cellFooterStyle);
			}
		} else {
			int r = startRow + 2 + headRows + dataRows, c = startCell;// 定义开始行列
			for (int i = 0; i < poiData.getFooters().size(); i++) {
				writeRow_NoMerged(poiData.getFooters().get(i), 18, r, c, cellFooterStyle);
				r += 1;
			}
		}
	}

	// 绘制Title
	private void writeTitleRow() {
		List<PoiCell> pcs = new ArrayList<PoiCell>();
		PoiCell pc = poiData.getTitle();
		if (pc == null)
			return;
		if (!pc.isPrecise()) {// 精准
			pc.setRowid(startRow);
			pc.setCellid(startCell);
			pc.setRows(1);
			pc.setCells(dataCells);
		}
		pcs.add(pc);
		writeRow(pcs, 33, cellTitleStyle);

	}

	// 绘制query
	private void writeQueryRow() {
		List<PoiCell> pcs = new ArrayList<PoiCell>();
		PoiCell pc = poiData.getQuery();
		if (pc == null)
			return;
		if (!pc.isPrecise()) {// 精准
			pc.setRowid(startRow + 1);
			pc.setCellid(startCell);
			pc.setRows(1);
			pc.setCells(dataCells);
		}
		pcs.add(pc);
		writeRow(pcs, 18, cellQueryStyle);
	}
}
