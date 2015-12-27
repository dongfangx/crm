package com.ly.friend.vo;

import java.util.Date;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.View;

@Table("contact")
public class Contact{

	@Id
	@Column
	private Long id;

	@Column
	private String name;

	@Column
	private Long friendid;

	@Column
	private String content;

	@Column
	private String keyworld;

	@Column
	private Date startdate;

	@Column
	private Date enddate;

	@Column
	private String memo;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getFriendid() {
		return friendid;
	}

	public void setFriendid(Long friendid) {
		this.friendid = friendid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getKeyworld() {
		return keyworld;
	}

	public void setKeyworld(String keyworld) {
		this.keyworld = keyworld;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
