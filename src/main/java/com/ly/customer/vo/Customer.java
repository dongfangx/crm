package com.ly.customer.vo;

import java.util.Date;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.View;

@Table("customer")
public class Customer{

	@Id
	@Column
	private Long id;

	@Column
	private String name;

	@Column
	private String workphone;

	@Column
	private Long xinyongid;

	@Column
	private Long customerlevelid;

	@Column
	private String phone;

	@Column
	private String fax;

	@Column
	private Date adddate;

	@Column
	private Date editdate;

	@Column
	private String adress;

	@Column
	private String postcode;

	@Column
	private String url;

	@Column
	private Long customertypeid;

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

	public String getWorkphone() {
		return workphone;
	}

	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}

	public Long getXinyongid() {
		return xinyongid;
	}

	public void setXinyongid(Long xinyongid) {
		this.xinyongid = xinyongid;
	}

	public Long getCustomerlevelid() {
		return customerlevelid;
	}

	public void setCustomerlevelid(Long customerlevelid) {
		this.customerlevelid = customerlevelid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public Date getAdddate() {
		return adddate;
	}

	public void setAdddate(Date adddate) {
		this.adddate = adddate;
	}

	public Date getEditdate() {
		return editdate;
	}

	public void setEditdate(Date editdate) {
		this.editdate = editdate;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getCustomertypeid() {
		return customertypeid;
	}

	public void setCustomertypeid(Long customertypeid) {
		this.customertypeid = customertypeid;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
