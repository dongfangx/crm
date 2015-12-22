package com.ly.friend.vo;

import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;

@Table("jinengpj")
public class Jinengpj{

	@Id
	@Column
	private Long id;

	@Column
	private Long friendid;

	@Column
	private String name;

	@Column
	private String pingjia;


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

	public String getPingjia() {
		return pingjia;
	}

	public void setPingjia(String pingjia) {
		this.pingjia = pingjia;
	}
}
