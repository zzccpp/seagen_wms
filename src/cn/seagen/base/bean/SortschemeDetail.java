package cn.seagen.base.bean;

public class SortschemeDetail {
	/** 主键*/
	private int f_recno;
	/** 分拣方案主键 */
	private String scheme_id;
	/** 运单目的地代码或站点编码 */
	private String site_code;
	/** 箱号目的地名称(或站点名称) */
	private String box_site_name;
	/** 箱号目的地代码 */
	private String box_site_code;
	/** 滑槽号*/
	private String chute_num;
	/** 格口打包重量单位克*/
	private int weight;
	/** 附加属性或者德邦包类型 */
	private String re_mark;
	/** 是否打印(德邦)*/
	private String is_print;
	/** 打印名称(德邦)*/
	private String print_name;
	/** 补码简称(德邦)*/
	private String complement_name;
	/** 生成时间*/
	private String create_time;
	
	public int getF_recno() {
		return f_recno;
	}
	public void setF_recno(int f_recno) {
		this.f_recno = f_recno;
	}
	public String getScheme_id() {
		return scheme_id;
	}
	public void setScheme_id(String scheme_id) {
		this.scheme_id = scheme_id;
	}
	public String getSite_code() {
		return site_code;
	}
	public void setSite_code(String site_code) {
		this.site_code = site_code;
	}
	public String getBox_site_name() {
		return box_site_name;
	}
	public void setBox_site_name(String box_site_name) {
		this.box_site_name = box_site_name;
	}
	public String getBox_site_code() {
		return box_site_code;
	}
	public void setBox_site_code(String box_site_code) {
		this.box_site_code = box_site_code;
	}
	public String getChute_num() {
		return chute_num;
	}
	public void setChute_num(String chute_num) {
		this.chute_num = chute_num;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getRe_mark() {
		return re_mark;
	}
	public void setRe_mark(String re_mark) {
		this.re_mark = re_mark;
	}
	public String getIs_print() {
		return is_print;
	}
	public void setIs_print(String is_print) {
		this.is_print = is_print;
	}
	public String getPrint_name() {
		return print_name;
	}
	public void setPrint_name(String print_name) {
		this.print_name = print_name;
	}
	public String getComplement_name() {
		return complement_name;
	}
	public void setComplement_name(String complement_name) {
		this.complement_name = complement_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	@Override
	public String toString() {
		return "SortschemeDetail [f_recno=" + f_recno + ", scheme_id="
				+ scheme_id + ", site_code=" + site_code + ", box_site_name="
				+ box_site_name + ", box_site_code=" + box_site_code
				+ ", chute_num=" + chute_num + ", weight=" + weight
				+ ", re_mark=" + re_mark + ", is_print=" + is_print
				+ ", print_name=" + print_name + ", complement_name="
				+ complement_name + ", create_time=" + create_time + "]";
	}
}
