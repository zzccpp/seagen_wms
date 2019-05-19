package cn.seagen.base.vo;

import cn.seagen.base.enums.MenuType;

/**
 * 这是单一菜单的结构体数据类
 */
public class Menu {
	private int key;// id,必须
	private int pid;// 父id,必须
	private int index; // 排序序号
	private String name;// 名称,必须
	private boolean checked = false;// 是否显示多选框
	private String state = "open";// 是否展开open,closed
	private MenuType mt = MenuType.STANDARD; // 是否是标准菜单
	private int power = 0; // 0权限和菜单，1为权限，2未开启
	private boolean haschild; // 是否显示菜单
	private String iconCls; // 图标
	private MenuAttr attributes;// 可以传递一些属性

	//
	public Menu(int key, int pid, int index, String name, String url, String iconCls, MenuType mt, int power) {
		super();
		this.key = key;
		this.pid = pid;
		this.index = index;
		this.name = name;
		this.power = power;
		this.iconCls = iconCls;
		this.state = "open";
		this.checked = false;
		this.haschild = false;
		this.setMt(mt);
		this.setAttributes(new MenuAttr(url));
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIcon(String iconCls) {
		this.iconCls = iconCls;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isHaschild() {
		return haschild;
	}

	public void setHaschild(boolean haschild) {
		this.haschild = haschild;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public MenuAttr getAttributes() {
		return attributes;
	}

	public void setAttributes(MenuAttr attributes) {
		this.attributes = attributes;
	}

	public MenuType getMt() {
		return mt;
	}

	public void setMt(MenuType mt) {
		this.mt = mt;
	}

	@Override
	public String toString() {
		return "Menun [key=" + key + ", pid=" + pid + ", index=" + index + ", name=" + name + ", checked=" + checked + ", state=" + state + ", power=" + power + ", haschild=" + haschild
				+ ", iconCls=" + iconCls + ", mt=" + mt + ", attributes=" + attributes.toString() + "]";
	}
}


class MenuAttr {
	private String url;// 访问路径//可以扩展很多
	private static String urlprefix = "pages.do?para=";// 菜单询问的前缀

	MenuAttr(String url) {
		super();
		setUrl(url);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		if ((url == null) || (url.isEmpty()))
			this.url = "";
		else if (url.substring(0, 1).equals("#"))
			this.url = url;
		else
			this.url = urlprefix + url;
	}

	@Override
	public String toString() {
		return "MenuAttr [url=" + url + "]";
	}

}
