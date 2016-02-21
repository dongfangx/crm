package com.ly.customer.vo;

import java.util.Date;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.View;

@Table("aftersales")
public class Aftersales{

	@Id
	@Column
	private Long id;

	@Column
	private Long customerid;

	@Column
	private Long aftersalestypeid;

	@Column
	private Date date1;

	@Column
	private String content;

	@Column
	private String employeename;

	@Column
	private Long employeeid;

	@Column
	private String memo;

	@Column
	private Date adddate;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	public Long getAftersalestypeid() {
		return aftersalestypeid;
	}

	public void setAftersalestypeid(Long aftersalestypeid) {
		this.aftersalestypeid = aftersalestypeid;
	}

	public Date getDate1() {
		return date1;
	}

	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmployeename() {
		return employeename;
	}

	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}

	public Long getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(Long employeeid) {
		this.employeeid = employeeid;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getAdddate() {
		return adddate;
	}

	public void setAdddate(Date adddate) {
		this.adddate = adddate;
	}
}
