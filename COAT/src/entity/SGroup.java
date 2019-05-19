package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * SGroup entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "s_group", catalog = "namecardpro")
public class SGroup implements java.io.Serializable {

	// Fields

	private Integer groupid;
	private String groupcode;
	private String groupname;
	private String groupfullname;
	private String remark;
	private String createname;
	private String createDate;
	private String modifier;
	private String modifyDate;
	private String sfyx;

	// Constructors

	/** default constructor */
	public SGroup() {
	}

	/** full constructor */
	public SGroup(String groupcode, String groupname, String groupfullname,
			String remark, String createname, String createDate,
			String modifier, String modifyDate, String sfyx) {
		this.groupcode = groupcode;
		this.groupname = groupname;
		this.groupfullname = groupfullname;
		this.remark = remark;
		this.createname = createname;
		this.createDate = createDate;
		this.modifier = modifier;
		this.modifyDate = modifyDate;
		this.sfyx = sfyx;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "groupid", unique = true, nullable = false)
	public Integer getGroupid() {
		return this.groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	@Column(name = "groupcode", length = 50)
	public String getGroupcode() {
		return this.groupcode;
	}

	public void setGroupcode(String groupcode) {
		this.groupcode = groupcode;
	}

	@Column(name = "groupname")
	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	@Column(name = "groupfullname")
	public String getGroupfullname() {
		return this.groupfullname;
	}

	public void setGroupfullname(String groupfullname) {
		this.groupfullname = groupfullname;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "createname", length = 20)
	public String getCreatename() {
		return this.createname;
	}

	public void setCreatename(String createname) {
		this.createname = createname;
	}

	@Column(name = "createDate", length = 50)
	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Column(name = "modifier", length = 20)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	@Column(name = "modifyDate", length = 50)
	public String getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Column(name = "sfyx", length = 1)
	public String getSfyx() {
		return this.sfyx;
	}

	public void setSfyx(String sfyx) {
		this.sfyx = sfyx;
	}

}