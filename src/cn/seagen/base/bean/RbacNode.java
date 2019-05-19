package cn.seagen.base.bean;

public class RbacNode {
	private int id;
	private int sort;//排序
	private int pid;//父节点
	private String nodeUrl;
	private String nodeName;
	private String nodeIcon;
	private int nodeType;
	private int useFlag;//状态:0为权限和菜单,1为权限,2未开启
	private String reMark;
	public RbacNode(int id,int pid,int sort,String nodeName,String nodeUrl,String nodeIcon,int nodeType,int useFlag){
		this.id = id;
		this.sort = sort;
		this.pid = pid;
		this.nodeUrl = nodeUrl;
		this.nodeName = nodeName;
		this.nodeIcon = nodeIcon;
		this.nodeType = nodeType;
		this.useFlag = useFlag;
		
	}
	
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNodeUrl() {
		return nodeUrl;
	}
	public void setNodeUrl(String nodeUrl) {
		this.nodeUrl = nodeUrl;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeIcon() {
		return nodeIcon;
	}
	public void setNodeIcon(String nodeIcon) {
		this.nodeIcon = nodeIcon;
	}
	public int getNodeType() {
		return nodeType;
	}
	public void setNodeType(int nodeType) {
		this.nodeType = nodeType;
	}
	public int getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(int useFlag) {
		this.useFlag = useFlag;
	}
	public String getReMark() {
		return reMark;
	}
	public void setReMark(String reMark) {
		this.reMark = reMark;
	}

	@Override
	public String toString() {
		return "MenuNode [id=" + id + ", sort=" + sort + ", pid=" + pid
				+ ", nodeUrl=" + nodeUrl + ", nodeName=" + nodeName
				+ ", nodeIcon=" + nodeIcon + ", nodeType=" + nodeType
				+ ", useFlag=" + useFlag + ", reMark=" + reMark + "]";
	}
}
