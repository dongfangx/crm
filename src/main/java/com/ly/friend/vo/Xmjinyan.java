package com.ly.friend.vo;

import java.util.Date;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;

@Table("xmjinyan")
public class Xmjinyan{

	@Id
	@Column
	private Long id;

	@Column
	private Long friendid;

	@Column
	private String name;

	@Column
	private String zhizhe;

	@Column
	private Date startdate;

	@Column
	private Date enddate;

	@Column
	private String des;

	@Column
	private String link;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZhizhe() {
		return zhizhe;
	}

	public void setZhizhe(String zhizhe) {
		this.zhizhe = zhizhe;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
