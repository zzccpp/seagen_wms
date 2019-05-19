package cn.seagen.base.bean;

/** 针对京东的箱号表 */
public class BoxBean {
	// `F_RECNO` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
	private long f_recno = 0;
	// `BATCH_ID` INT(20) DEFAULT 0 COMMENT '批次ID',
	private int batch_id = 0;
	// `BOX_CODE` VARCHAR(40) DEFAULT NULL COMMENT '箱号',
	private String box_code = null;
	// `BOX_NUM` VARCHAR(32) DEFAULT NULL COMMENT '箱号的编号或序号',
	private String box_num = null;
	// `BOX_TYPE` VARCHAR(32) NOT NULL COMMENT '箱号类型BC',
	private String box_type = null;
	// `OPT_CODE` VARCHAR(6) DEFAULT 0 COMMENT '操作码,保留暂时未用,京东的暂定为618',
	private String opt_code = null;
	// `MIXING_BOX_TYPE` VARCHAR(10) NOT NULL COMMENT '是否混箱',
	private String mixing_box_type = null;
	// `CATE_GORY` VARCHAR(10) NOT NULL COMMENT '运输方式 公，铁，航',
	private String cate_gory = null;
	// `CARRIAGE_ROUTER` VARCHAR(50) NOT NULL COMMENT '运输路由(字符数组，用逗号隔开)',
	private String carriage_router = null;
	// `CARRIAGE_ROUTER_NUM` varchar(20) DEFAULT NULL COMMENT '运输路由编号',
	private String carriage_router_num = null;
	// `PACKAGE_COUNT` INT(4) DEFAULT 0 COMMENT '箱中包裹数量',
	private int package_count = 0;
	// `package_weight` INT(4) DEFAULT 0 COMMENT '箱中包裹总重量',
	private int package_weight = 0;
	// `TO_SITE_NAME` VARCHAR(30) NOT NULL COMMENT '接收目的地名称',
	private String to_site_name = null;
	// `TO_SITE_CODE` VARCHAR(10) DEFAULT NULL COMMENT '接收目的地代码',
	private String to_site_code = null;
	// `FROM_SITE_NAME` VARCHAR(30) NOT NULL COMMENT '始发目的地名称',
	private String from_site_name = null;
	// `FROM_SITE_CODE` VARCHAR(10) DEFAULT NULL COMMENT '始发目的地代码',
	private String from_site_code = null;
	// `PRINT_STATUS` INT(4) DEFAULT 0 COMMENT '0未打印1已打印',
	private int print_status = 0;
	// `PRINTER_IP` varchar(64) NOT NULL COMMENT '打印机IP',
	private String printer_ip = null;
	// `PRINT_CMD` varchar(128) DEFAULT NULL COMMENT '打印机命令',
	private String print_cmd = null;
	// `PRINT_IMAG` mediumblob COMMENT '打印机图片数据',
	private String print_imag = null;
	// `PRINT_TIME` VARCHAR(30) DEFAULT NULL COMMENT '箱号最后一次打印时间(YYYY-MM-DD
	// HH:MM:SS.FFFFFFFFFF)',
	private String print_time = null;
	// `PAD_CODE` VARCHAR(60) DEFAULT NULL COMMENT 'PAD控制器序列号(750)',
	private String pad_code = null;
	// `CHUTE_ID` int(4) DEFAULT '0' COMMENT '格口物理编号',
	private int chute_id = 0;
	// `CHUTE_NUM` varchar(50) DEFAULT NULL COMMENT '格口逻辑编号',
	private String chute_num = null;
	// `RECE_TIME` VARCHAR(30) DEFAULT NULL COMMENT '数据接收时间(YYYY-MM-DD
	// HH:MM:SS.FFFFFFFFFF)',
	private String rece_time = null;
	// `USE_FLAG` tinyint(1) NOT NULL COMMENT '是否已用0：未使用1：已使用',
	private int use_flag = 0;
	// `UPDATE_FLAG` INT(4) NOT NULL DEFAULT '0' COMMENT '更新标识0：未更新1：已更新',
	private int update_flag = 0;
	// `UPDATE_TIME` '更新时间(YYYY-MM-DD HH:MM:SS.FFFFFFFFFF)',
	private String update_time = null;
	// `RE_MARK` varchar(100) DEFAULT NULL COMMENT '附加属性或备注',
	private String re_mark = null;
	// `CREATE_TIME` timestamp NOT NULL '生成时间',
	private String create_time = null;
	// `MODIFY_TIME` timestamp NOT NULL COMMENT '修改时间',
	private String modify_time = null;

	public long getF_recno() {
		return f_recno;
	}

	public void setF_recno(long f_recno) {
		this.f_recno = f_recno;
	}

	public int getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(int batch_id) {
		this.batch_id = batch_id;
	}

	public String getBox_code() {
		return box_code;
	}

	public void setBox_code(String box_code) {
		this.box_code = box_code;
	}

	public String getBox_type() {
		return box_type;
	}

	public void setBox_type(String box_type) {
		this.box_type = box_type;
	}

	public String getOpt_code() {
		return opt_code;
	}

	public void setOpt_code(String opt_code) {
		this.opt_code = opt_code;
	}

	public String getMixing_box_type() {
		return mixing_box_type;
	}

	public void setMixing_box_type(String mixing_box_type) {
		this.mixing_box_type = mixing_box_type;
	}

	public String getCate_gory() {
		return cate_gory;
	}

	public void setCate_gory(String cate_gory) {
		this.cate_gory = cate_gory;
	}

	public String getCarriage_router() {
		return carriage_router;
	}

	public void setCarriage_router(String carriage_router) {
		this.carriage_router = carriage_router;
	}

	public String getCarriage_router_num() {
		return carriage_router_num;
	}

	public void setCarriage_router_num(String carriage_router_num) {
		this.carriage_router_num = carriage_router_num;
	}

	public int getPackage_count() {
		return package_count;
	}

	public void setPackage_count(int package_count) {
		this.package_count = package_count;
	}

	public String getTo_site_name() {
		return to_site_name;
	}

	public void setTo_site_name(String to_site_name) {
		this.to_site_name = to_site_name;
	}

	public String getTo_site_code() {
		return to_site_code;
	}

	public void setTo_site_code(String to_site_code) {
		this.to_site_code = to_site_code;
	}

	public String getFrom_site_name() {
		return from_site_name;
	}

	public void setFrom_site_name(String from_site_name) {
		this.from_site_name = from_site_name;
	}

	public String getFrom_site_code() {
		return from_site_code;
	}

	public void setFrom_site_code(String from_site_code) {
		this.from_site_code = from_site_code;
	}

	public int getPrint_status() {
		return print_status;
	}

	public void setPrint_status(int print_status) {
		this.print_status = print_status;
	}

	public String getPrinter_ip() {
		return printer_ip;
	}

	public void setPrinter_ip(String printer_ip) {
		this.printer_ip = printer_ip;
	}

	public String getPrint_cmd() {
		return print_cmd;
	}

	public void setPrint_cmd(String print_cmd) {
		this.print_cmd = print_cmd;
	}

	public String getPrint_time() {
		return print_time;
	}

	public void setPrint_time(String print_time) {
		this.print_time = print_time;
	}

	public String getPad_code() {
		return pad_code;
	}

	public void setPad_code(String pad_code) {
		this.pad_code = pad_code;
	}

	public int getChute_id() {
		return chute_id;
	}

	public void setChute_id(int chute_id) {
		this.chute_id = chute_id;
	}

	public String getChute_num() {
		return chute_num;
	}

	public void setChute_num(String chute_num) {
		this.chute_num = chute_num;
	}

	public String getRece_time() {
		return rece_time;
	}

	public void setRece_time(String rece_time) {
		this.rece_time = rece_time;
	}

	public int getUse_flag() {
		return use_flag;
	}

	public void setUse_flag(int use_flag) {
		this.use_flag = use_flag;
	}

	public int getUpdate_flag() {
		return update_flag;
	}

	public void setUpdate_flag(int update_flag) {
		this.update_flag = update_flag;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
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

	public int getPackage_weight() {
		return package_weight;
	}

	public void setPackage_weight(int package_weight) {
		this.package_weight = package_weight;
	}

	public String getBox_num() {
		return box_num;
	}

	public void setBox_num(String box_num) {
		this.box_num = box_num;
	}

	public String getPrint_imag() {
		return print_imag;
	}

	public void setPrint_imag(String print_imag) {
		this.print_imag = print_imag;
	}

	@Override
	public String toString() {
		return "Box [f_recno=" + f_recno + ", batch_id=" + batch_id + ", box_code=" + box_code + ", box_num=" + box_num + ", box_type=" + box_type + ", opt_code=" + opt_code + ", mixing_box_type=" + mixing_box_type + ", cate_gory=" + cate_gory + ", carriage_router=" + carriage_router + ", carriage_router_num=" + carriage_router_num + ", package_count=" + package_count + ", package_weight=" + package_weight + ", to_site_name=" + to_site_name + ", to_site_code=" + to_site_code + ", from_site_name=" + from_site_name + ", from_site_code=" + from_site_code + ", print_status=" + print_status + ", printer_ip=" + printer_ip + ", print_cmd=" + print_cmd + ", print_imag=" + print_imag + ", print_time=" + print_time + ", pad_code=" + pad_code + ", chute_id=" + chute_id
				+ ", chute_num=" + chute_num + ", rece_time=" + rece_time + ", use_flag=" + use_flag + ", update_flag=" + update_flag + ", update_time=" + update_time + ", re_mark=" + re_mark + ", create_time=" + create_time + ", modify_time=" + modify_time + "]";
	}
	
	

}
