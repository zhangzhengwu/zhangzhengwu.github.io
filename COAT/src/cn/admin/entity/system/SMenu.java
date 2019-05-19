package cn.admin.entity.system;

/**
 * 
 * 菜单实体类 @author kingxu
 * 
 */

public class SMenu {

	// Fields

	private Integer menuid;// 菜单ID
	private String menuname;// 菜单名称
	private String menuAction;// 菜单单击动作
	private Integer parentId;// 父菜单ID
	private Integer childshort;// 子菜单排序
	private Integer parentshort;// 父菜单排序
	private String remark;// 备注
	private String createname;
	private String createDate;
	private String modifier;
	private String modifyDate;
	private String sfyx;// 是否有效【Y:有效，N:失效，D:已删除】

	// Constructors

	/** default constructor */
	public SMenu() {
	}

	public SMenu(Integer menuid, String menuname, Integer parentId) {
		this.menuid = menuid;
		this.menuname = menuname;
		this.parentId = parentId;
	}

	/** minimal constructor */
	public SMenu(Integer menuid) {
		this.menuid = menuid;
	}

	public SMenu(String menuname, String menuAction, Integer parentId,
			Integer childshort, Integer parentshort, String remark,
			String createname, String createDate, String modifier,
			String modifyDate, String sfyx) {
		super();
		this.menuname = menuname;
		this.menuAction = menuAction;
		this.parentId = parentId;
		this.childshort = childshort;
		this.parentshort = parentshort;
		this.remark = remark;
		this.createname = createname;
		this.createDate = createDate;
		this.modifier = modifier;
		this.modifyDate = modifyDate;
		this.sfyx = sfyx;
	}

	/** full constructor */
	public SMenu(Integer menuid, String menuname, String menuAction,
			Integer parentId, Integer childshort, Integer parentshort,
			String remark, String createname, String createDate,
			String modifier, String modifyDate, String sfyx) {
		this.menuid = menuid;
		this.menuname = menuname;
		this.menuAction = menuAction;
		this.parentId = parentId;
		this.childshort = childshort;
		this.parentshort = parentshort;
		this.remark = remark;
		this.createname = createname;
		this.createDate = createDate;
		this.modifier = modifier;
		this.modifyDate = modifyDate;
		this.sfyx = sfyx;
	}

	// Property accessors

	public Integer getMenuid() {
		return this.menuid;
	}

	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}

	public String getMenuname() {
		return this.menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getMenuAction() {
		return this.menuAction;
	}

	public void setMenuAction(String menuAction) {
		this.menuAction = menuAction;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getChildshort() {
		return this.childshort;
	}

	public void setChildshort(Integer childshort) {
		this.childshort = childshort;
	}

	public Integer getParentshort() {
		return this.parentshort;
	}

	public void setParentshort(Integer parentshort) {
		this.parentshort = parentshort;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatename() {
		return this.createname;
	}

	public void setCreatename(String createname) {
		this.createname = createname;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getSfyx() {
		return this.sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}


}