package cn.seagen.base.bean;

/**
 * 分拣方案表 
 * @author wp
 */
public class Sortscheme {
	/** 主键 */
	private long f_recno;
	/** 分拣方案主键 */
	private String scheme_id;
	/** 分拣方案名称 */
	private String scheme_name;
	/** 分拔中心中转场编号或站点编码 */
	private String site_no;
	/** 分拣机编号 */
	private String machine_no;
	/** 分拣模式1:最近2:瀑布3:循环 */
	private String sort_mode;
	/** 打印机打印样式 */
	private int print_style;
	/** 打印样式名称 */
	private String print_style_name;
	/** 渠道id */
	private int channel_id;
	/** 渠道名称或分拔中转场名称 */
	private String channel_name;
	/** 综合格口，多格口以逗号隔开，方案中综合格口默认为空 */
	private String multiple_chute;
	/** 附加属性或备注 */
	private String re_mark;
	/** 生成时间*/
	private String create_time;
	/** 修改时间 */
	private String modify_time;

	public long getF_recno() {
		return f_recno;
	}

	public void setF_recno(long f_recno) {
		this.f_recno = f_recno;
	}

	public String getScheme_id() {
		return scheme_id;
	}

	public void setScheme_id(String scheme_id) {
		this.scheme_id = scheme_id;
	}

	public String getSite_no() {
		return site_no;
	}

	public void setSite_no(String site_no) {
		this.site_no = site_no;
	}

	public String getMachine_no() {
		return machine_no;
	}

	public void setMachine_no(String machine_no) {
		this.machine_no = machine_no;
	}

	public String getSort_mode() {
		return sort_mode;
	}

	public void setSort_mode(String sort_mode) {
		this.sort_mode = sort_mode;
	}

	public int getPrint_style() {
		return print_style;
	}

	public void setPrint_style(int print_style) {
		this.print_style = print_style;
	}

	public String getPrint_style_name() {
		return print_style_name;
	}

	public void setPrint_style_name(String print_style_name) {
		this.print_style_name = print_style_name;
	}

	public int getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
	}

	public String getChannel_name() {
		return channel_name;
	}

	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}

	public String getRe_mark() {
		return re_mark;
	}

	public void setRe_mark(String re_mark) {
		this.re_mark = re_mark;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getModify_time() {
		return modify_time;
	}

	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}

	public String getMultiple_chute() {
		return multiple_chute;
	}

	public void setMultiple_chute(String multiple_chute) {
		this.multiple_chute = multiple_chute;
	}

	public String getScheme_name() {
		return scheme_name;
	}

	public void setScheme_name(String scheme_name) {
		this.scheme_name = scheme_name;
	}

	@Override
	public String toString() {
		return "Sortscheme [f_recno=" + f_recno + ", scheme_id=" + scheme_id + ", scheme_name=" + scheme_name + ", site_no=" + site_no + ", machine_no=" + machine_no + ", sort_mode=" + sort_mode + ", print_style=" + print_style + ", print_style_name=" + print_style_name + ", channel_id=" + channel_id + ", channel_name=" + channel_name + ", multiple_chute=" + multiple_chute + ", re_mark=" + re_mark + ", create_time=" + create_time + ", modify_time=" + modify_time + "]";
	}

}
