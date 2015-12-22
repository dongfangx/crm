package com.ly.friend.vo;

import java.util.Date;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;

@Table("educationexperience")
public class Educationexperience{

	@Id
	@Column
	private Long id;

	@Column
	private Long friendid;

	@Column
	private String schoolname;

	@Column
	private String educationid;

	@Column
	private String zhuanye;

	@Column
	private Date enddate;

	@Column
	private Date startdate;

	@Column
	private String memo;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getfriendid() {
		return friendid;
	}

	public void setfriendid(Long friendid) {
		this.friendid = friendid;
	}

	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public String getEducationid() {
		return educationid;
	}

	public void setEducationid(String educationid) {
		this.educationid = educationid;
	}

	public String getZhuanye() {
		return zhuanye;
	}

	public void setZhuanye(String zhuanye) {
		this.zhuanye = zhuanye;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
