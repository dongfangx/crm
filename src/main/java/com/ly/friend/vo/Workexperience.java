package com.ly.friend.vo;

import java.util.Date;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.View;

@Table("workexperience")
public class Workexperience{

	@Id
	@Column
	private Long id;

	@Column
	private Long friendid;

	@Column
	private String post;

	@Column
	private String corpname;

	@Column
	private Date startdate;

	@Column
	private Date enddate;

	@Column
	private String content;

	@Column
	private String memo;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFriendid() {
		return friendid;
	}

	public void setFriendid(Long friendid) {
		this.friendid = friendid;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getCorpname() {
		return corpname;
	}

	public void setCorpname(String corpname) {
		this.corpname = corpname;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
