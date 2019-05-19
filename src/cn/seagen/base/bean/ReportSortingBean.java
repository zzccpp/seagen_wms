package cn.seagen.base.bean;
/**
 *汇总量、批次量统计实体类
 */
public class ReportSortingBean extends ReportBaseBean {
	/**主键*/
	private long f_recno;
	/**记录日期*/
	private String report_date;
	/**统计名称*/
	private String sum_name;
	/**统计方式：0为批次；1为小时*/
	private int sum_type;
	/**开始时间*/
	private String begin_time;
	/**结束时间*/
	private String end_time;
	/**导入台供件量*/
	private int supply_sum;
	/**层级总量*/
	private int layer_sum;
	/**供件量(包含没有经过导入对口的)*/
	private int scan_sum;
	/**总的分拣数*/
	private int all_sum;
	/**打包(建包)数*/
	private int box_sum;
	/**层级0的件数(返回错误的人为的都为0)*/
	private int layer0;
	/**层级1的件数*/
	private int layer1;
	/**层级2的件数*/
	private int layer2;
	/**扫描器0的件数(返回错误的人为的都为0)*/
	private int scan0;
	/**扫描器1的件数*/
	private int scan1;
	/**扫描器2的件数*/
	private int scan2;
	/**扫描器3的件数*/
	private int scan3;
	/**扫描器4的件数*/
	private int scan4;
	/**扫描器5的件数*/
	private int scan5;
	/**扫描器6的件数*/
	private int scan6;
	/**扫描器7的件数*/
	private int scan7;
	/**扫描器8的件数*/
	private int scan8;
	/**扫描器9的件数*/
	private int scan9;
	/**扫描器10的件数*/
	private int scan10;
	/**扫描器11的件数*/
	private int scan11;
	/**扫描器12的件数*/
	private int scan12;
	/**扫描器13的件数*/
	private int scan13;
	/**扫描器14的件数*/
	private int scan14;
	/**扫描器15的件数*/
	private int scan15;
	/**扫描器16的件数*/
	private int scan16;
	/**供件台0的件数(不经过导入台,人为放置小车上,或错误的)*/
	private int supply0;
	/**供件台1的件数*/
	private int supply1;
	/**供件台2的件数*/
	private int supply2;
	/**供件台3的件数*/
	private int supply3;
	/**供件台4的件数*/
	private int supply4;
	/**供件台5的件数*/
	private int supply5;
	/**供件台6的件数*/
	private int supply6;
	/**供件台7的件数*/
	private int supply7;
	/**供件台8的件数*/
	private int supply8;
	/**供件台9的件数*/
	private int supply9;
	/**供件台10的件数*/
	private int supply10;
	/**供件台11的件数*/
	private int supply11;
	/**供件台12的件数*/
	private int supply12;
	/**供件台13的件数*/
	private int supply13;
	/**供件台14的件数*/
	private int supply14;
	/**供件台15的件数*/
	private int supply15;
	/**供件台16的件数*/
	private int supply16;
	/**供件台17的件数*/
	private int supply17;
	/**供件台18的件数*/
	private int supply18;
	/**供件台19的件数*/
	private int supply19;
	/**供件台20的件数*/
	private int supply20;
	/**供件台21的件数*/
	private int supply21;
	/**供件台22的件数*/
	private int supply22;
	/**供件台23的件数*/
	private int supply23;
	/**供件台24的件数*/
	private int supply24;
	/**供件台无读0的件数(不经过导入台,人为放置小车上)*/
	private int noread0;
	/**供件台无读1的件数*/
	private int noread1;
	/**供件台无读2的件数*/
	private int noread2;
	/**供件台无读3的件数*/
	private int noread3;
	/**供件台无读4的件数*/
	private int noread4;
	/**供件台无读5的件数*/
	private int noread5;
	/**供件台无读6的件数*/
	private int noread6;
	/**供件台无读7的件数*/
	private int noread7;
	/**供件台无读8的件数*/
	private int noread8;
	/**供件台无读9的件数*/
	private int noread9;
	/**供件台无读10的件数*/
	private int noread10;
	/**供件台无读11的件数*/
	private int noread11;
	/**供件台无读12的件数*/
	private int noread12;
	/**供件台无读13的件数*/
	private int noread13;
	/**供件台无读14的件数*/
	private int noread14;
	/**供件台无读15的件数*/
	private int noread15;
	/**供件台无读16的件数*/
	private int noread16;
	/**供件台无读17的件数*/
	private int noread17;
	/**供件台无读18的件数*/
	private int noread18;
	/**供件台无读19的件数*/
	private int noread19;
	/**供件台无读20的件数*/
	private int noread20;
	/**供件台无读21的件数*/
	private int noread21;
	/**供件台无读22的件数*/
	private int noread22;
	/**供件台无读23的件数*/
	private int noread23;
	/**供件台无读24的件数*/
	private int noread24;
	public long getF_recno() {
		return f_recno;
	}
	public void setF_recno(long f_recno) {
		this.f_recno = f_recno;
	}
	public String getReport_date() {
		return report_date;
	}
	public void setReport_date(String report_date) {
		this.report_date = report_date;
	}
	public String getSum_name() {
		return sum_name;
	}
	public void setSum_name(String sum_name) {
		this.sum_name = sum_name;
	}
	public int getSum_type() {
		return sum_type;
	}
	public void setSum_type(int sum_type) {
		this.sum_type = sum_type;
	}
	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public int getSupply_sum() {
		return supply_sum;
	}
	public void setSupply_sum(int supply_sum) {
		this.supply_sum = supply_sum;
	}
	public int getLayer_sum() {
		return layer_sum;
	}
	public void setLayer_sum(int layer_sum) {
		this.layer_sum = layer_sum;
	}
	public int getScan_sum() {
		return scan_sum;
	}
	public void setScan_sum(int scan_sum) {
		this.scan_sum = scan_sum;
	}
	public int getAll_sum() {
		return all_sum;
	}
	public void setAll_sum(int all_sum) {
		this.all_sum = all_sum;
	}
	public int getBox_sum() {
		return box_sum;
	}
	public void setBox_sum(int box_sum) {
		this.box_sum = box_sum;
	}
	public int getLayer0() {
		return layer0;
	}
	public void setLayer0(int layer0) {
		this.layer0 = layer0;
	}
	public int getLayer1() {
		return layer1;
	}
	public void setLayer1(int layer1) {
		this.layer1 = layer1;
	}
	public int getLayer2() {
		return layer2;
	}
	public void setLayer2(int layer2) {
		this.layer2 = layer2;
	}
	public int getScan0() {
		return scan0;
	}
	public void setScan0(int scan0) {
		this.scan0 = scan0;
	}
	public int getScan1() {
		return scan1;
	}
	public void setScan1(int scan1) {
		this.scan1 = scan1;
	}
	public int getScan2() {
		return scan2;
	}
	public void setScan2(int scan2) {
		this.scan2 = scan2;
	}
	public int getScan3() {
		return scan3;
	}
	public void setScan3(int scan3) {
		this.scan3 = scan3;
	}
	public int getScan4() {
		return scan4;
	}
	public void setScan4(int scan4) {
		this.scan4 = scan4;
	}
	public int getScan5() {
		return scan5;
	}
	public void setScan5(int scan5) {
		this.scan5 = scan5;
	}
	public int getScan6() {
		return scan6;
	}
	public void setScan6(int scan6) {
		this.scan6 = scan6;
	}
	public int getScan7() {
		return scan7;
	}
	public void setScan7(int scan7) {
		this.scan7 = scan7;
	}
	public int getScan8() {
		return scan8;
	}
	public void setScan8(int scan8) {
		this.scan8 = scan8;
	}
	public int getScan9() {
		return scan9;
	}
	public void setScan9(int scan9) {
		this.scan9 = scan9;
	}
	public int getScan10() {
		return scan10;
	}
	public void setScan10(int scan10) {
		this.scan10 = scan10;
	}
	public int getScan11() {
		return scan11;
	}
	public void setScan11(int scan11) {
		this.scan11 = scan11;
	}
	public int getScan12() {
		return scan12;
	}
	public void setScan12(int scan12) {
		this.scan12 = scan12;
	}
	public int getScan13() {
		return scan13;
	}
	public void setScan13(int scan13) {
		this.scan13 = scan13;
	}
	public int getScan14() {
		return scan14;
	}
	public void setScan14(int scan14) {
		this.scan14 = scan14;
	}
	public int getScan15() {
		return scan15;
	}
	public void setScan15(int scan15) {
		this.scan15 = scan15;
	}
	public int getScan16() {
		return scan16;
	}
	public void setScan16(int scan16) {
		this.scan16 = scan16;
	}
	public int getSupply0() {
		return supply0;
	}
	public void setSupply0(int supply0) {
		this.supply0 = supply0;
	}
	public int getSupply1() {
		return supply1;
	}
	public void setSupply1(int supply1) {
		this.supply1 = supply1;
	}
	public int getSupply2() {
		return supply2;
	}
	public void setSupply2(int supply2) {
		this.supply2 = supply2;
	}
	public int getSupply3() {
		return supply3;
	}
	public void setSupply3(int supply3) {
		this.supply3 = supply3;
	}
	public int getSupply4() {
		return supply4;
	}
	public void setSupply4(int supply4) {
		this.supply4 = supply4;
	}
	public int getSupply5() {
		return supply5;
	}
	public void setSupply5(int supply5) {
		this.supply5 = supply5;
	}
	public int getSupply6() {
		return supply6;
	}
	public void setSupply6(int supply6) {
		this.supply6 = supply6;
	}
	public int getSupply7() {
		return supply7;
	}
	public void setSupply7(int supply7) {
		this.supply7 = supply7;
	}
	public int getSupply8() {
		return supply8;
	}
	public void setSupply8(int supply8) {
		this.supply8 = supply8;
	}
	public int getSupply9() {
		return supply9;
	}
	public void setSupply9(int supply9) {
		this.supply9 = supply9;
	}
	public int getSupply10() {
		return supply10;
	}
	public void setSupply10(int supply10) {
		this.supply10 = supply10;
	}
	public int getSupply11() {
		return supply11;
	}
	public void setSupply11(int supply11) {
		this.supply11 = supply11;
	}
	public int getSupply12() {
		return supply12;
	}
	public void setSupply12(int supply12) {
		this.supply12 = supply12;
	}
	public int getSupply13() {
		return supply13;
	}
	public void setSupply13(int supply13) {
		this.supply13 = supply13;
	}
	public int getSupply14() {
		return supply14;
	}
	public void setSupply14(int supply14) {
		this.supply14 = supply14;
	}
	public int getSupply15() {
		return supply15;
	}
	public void setSupply15(int supply15) {
		this.supply15 = supply15;
	}
	public int getSupply16() {
		return supply16;
	}
	public void setSupply16(int supply16) {
		this.supply16 = supply16;
	}
	public int getSupply17() {
		return supply17;
	}
	public void setSupply17(int supply17) {
		this.supply17 = supply17;
	}
	public int getSupply18() {
		return supply18;
	}
	public void setSupply18(int supply18) {
		this.supply18 = supply18;
	}
	public int getSupply19() {
		return supply19;
	}
	public void setSupply19(int supply19) {
		this.supply19 = supply19;
	}
	public int getSupply20() {
		return supply20;
	}
	public void setSupply20(int supply20) {
		this.supply20 = supply20;
	}
	public int getSupply21() {
		return supply21;
	}
	public void setSupply21(int supply21) {
		this.supply21 = supply21;
	}
	public int getSupply22() {
		return supply22;
	}
	public void setSupply22(int supply22) {
		this.supply22 = supply22;
	}
	public int getSupply23() {
		return supply23;
	}
	public void setSupply23(int supply23) {
		this.supply23 = supply23;
	}
	public int getSupply24() {
		return supply24;
	}
	public void setSupply24(int supply24) {
		this.supply24 = supply24;
	}
	public int getNoread0() {
		return noread0;
	}
	public void setNoread0(int noread0) {
		this.noread0 = noread0;
	}
	public int getNoread1() {
		return noread1;
	}
	public void setNoread1(int noread1) {
		this.noread1 = noread1;
	}
	public int getNoread2() {
		return noread2;
	}
	public void setNoread2(int noread2) {
		this.noread2 = noread2;
	}
	public int getNoread3() {
		return noread3;
	}
	public void setNoread3(int noread3) {
		this.noread3 = noread3;
	}
	public int getNoread4() {
		return noread4;
	}
	public void setNoread4(int noread4) {
		this.noread4 = noread4;
	}
	public int getNoread5() {
		return noread5;
	}
	public void setNoread5(int noread5) {
		this.noread5 = noread5;
	}
	public int getNoread6() {
		return noread6;
	}
	public void setNoread6(int noread6) {
		this.noread6 = noread6;
	}
	public int getNoread7() {
		return noread7;
	}
	public void setNoread7(int noread7) {
		this.noread7 = noread7;
	}
	public int getNoread8() {
		return noread8;
	}
	public void setNoread8(int noread8) {
		this.noread8 = noread8;
	}
	public int getNoread9() {
		return noread9;
	}
	public void setNoread9(int noread9) {
		this.noread9 = noread9;
	}
	public int getNoread10() {
		return noread10;
	}
	public void setNoread10(int noread10) {
		this.noread10 = noread10;
	}
	public int getNoread11() {
		return noread11;
	}
	public void setNoread11(int noread11) {
		this.noread11 = noread11;
	}
	public int getNoread12() {
		return noread12;
	}
	public void setNoread12(int noread12) {
		this.noread12 = noread12;
	}
	public int getNoread13() {
		return noread13;
	}
	public void setNoread13(int noread13) {
		this.noread13 = noread13;
	}
	public int getNoread14() {
		return noread14;
	}
	public void setNoread14(int noread14) {
		this.noread14 = noread14;
	}
	public int getNoread15() {
		return noread15;
	}
	public void setNoread15(int noread15) {
		this.noread15 = noread15;
	}
	public int getNoread16() {
		return noread16;
	}
	public void setNoread16(int noread16) {
		this.noread16 = noread16;
	}
	public int getNoread17() {
		return noread17;
	}
	public void setNoread17(int noread17) {
		this.noread17 = noread17;
	}
	public int getNoread18() {
		return noread18;
	}
	public void setNoread18(int noread18) {
		this.noread18 = noread18;
	}
	public int getNoread19() {
		return noread19;
	}
	public void setNoread19(int noread19) {
		this.noread19 = noread19;
	}
	public int getNoread20() {
		return noread20;
	}
	public void setNoread20(int noread20) {
		this.noread20 = noread20;
	}
	public int getNoread21() {
		return noread21;
	}
	public void setNoread21(int noread21) {
		this.noread21 = noread21;
	}
	public int getNoread22() {
		return noread22;
	}
	public void setNoread22(int noread22) {
		this.noread22 = noread22;
	}
	public int getNoread23() {
		return noread23;
	}
	public void setNoread23(int noread23) {
		this.noread23 = noread23;
	}
	public int getNoread24() {
		return noread24;
	}
	public void setNoread24(int noread24) {
		this.noread24 = noread24;
	}
	@Override
	public String toString() {
		return "ReportSortingBean [f_recno=" + f_recno + ", report_date="
				+ report_date + ", sum_name=" + sum_name + ", sum_type=" + sum_type + ", begin_time="
				+ begin_time + ", end_time=" + end_time + ", supply_sum="
				+ supply_sum + ", layer_sum=" + layer_sum + ", scan_sum="
				+ scan_sum + ", all_sum=" + all_sum + ", box_sum=" + box_sum
				+ ", layer0=" + layer0 + ", layer1=" + layer1 + ", layer2="
				+ layer2 + ", scan0=" + scan0 + ", scan1=" + scan1 + ", scan2="
				+ scan2 + ", scan3=" + scan3 + ", scan4=" + scan4 + ", scan5="
				+ scan5 + ", scan6=" + scan6 + ", scan7=" + scan7 + ", scan8="
				+ scan8 + ", scan9=" + scan9 + ", scan10=" + scan10
				+ ", scan11=" + scan11 + ", scan12=" + scan12 + ", scan13="
				+ scan13 + ", scan14=" + scan14 + ", scan15=" + scan15
				+ ", scan16=" + scan16 + ", supply0=" + supply0 + ", supply1="
				+ supply1 + ", supply2=" + supply2 + ", supply3=" + supply3
				+ ", supply4=" + supply4 + ", supply5=" + supply5
				+ ", supply6=" + supply6 + ", supply7=" + supply7
				+ ", supply8=" + supply8 + ", supply9=" + supply9
				+ ", supply10=" + supply10 + ", supply11=" + supply11
				+ ", supply12=" + supply12 + ", supply13=" + supply13
				+ ", supply14=" + supply14 + ", supply15=" + supply15
				+ ", supply16=" + supply16 + ", supply17=" + supply17
				+ ", supply18=" + supply18 + ", supply19=" + supply19
				+ ", supply20=" + supply20 + ", supply21=" + supply21
				+ ", supply22=" + supply22 + ", supply23=" + supply23
				+ ", supply24=" + supply24 + ", noread0=" + noread0
				+ ", noread1=" + noread1 + ", noread2=" + noread2
				+ ", noread3=" + noread3 + ", noread4=" + noread4
				+ ", noread5=" + noread5 + ", noread6=" + noread6
				+ ", noread7=" + noread7 + ", noread8=" + noread8
				+ ", noread9=" + noread9 + ", noread10=" + noread10
				+ ", noread11=" + noread11 + ", noread12=" + noread12
				+ ", noread13=" + noread13 + ", noread14=" + noread14
				+ ", noread15=" + noread15 + ", noread16=" + noread16
				+ ", noread17=" + noread17 + ", noread18=" + noread18
				+ ", noread19=" + noread19 + ", noread20=" + noread20
				+ ", noread21=" + noread21 + ", noread22=" + noread22
				+ ", noread23=" + noread23 + ", noread24=" + noread24
				+ ", toString()=" + super.toString() + "]";
	}
}
