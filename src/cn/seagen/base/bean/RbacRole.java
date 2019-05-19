package cn.seagen.base.bean;
/**
 * 
 * @author sjd
 *
 */
public class RbacRole {
	//id int(4) not null auto_increment comment '主键',
	// role_name varchar(50) default null,
	//use_flag char(1) default '0' comment '启动状态0:启用  1:不启用',
	//remark varchar(50) default null,
	private int id;
	private String roleName;
	private String useFlag;
	private String remark;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public String toString() {
		return "RbacRole [id=" + id + ", roleName=" + roleName + ", useFlag="
				+ useFlag + ", remark=" + remark + "]";
	}
}
