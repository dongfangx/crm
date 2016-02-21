package com.ly.product.vo;

import java.util.Date;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.View;

@Table("product")
public class Product{

	@Id
	@Column
	private Long id;

	@Column
	private String name;

	@Column
	private String specs;

	@Column
	private Float price;

	@Column
	private Long storage;

	@Column
	private Long unitid;

	@Column
	private Float lowdiscount;

	@Column
	private String smallimage;

	@Column
	private String maximage;

	@Column
	private String descript;

	@Column
	private Date adddate;

	@Column
	private Long employeeid;

	@Column
	private Long memo;


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

	public String getSpecs() {
		return specs;
	}

	public void setSpecs(String specs) {
		this.specs = specs;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Long getStorage() {
		return storage;
	}

	public void setStorage(Long storage) {
		this.storage = storage;
	}

	public Long getUnitid() {
		return unitid;
	}

	public void setUnitid(Long unitid) {
		this.unitid = unitid;
	}

	public Float getLowdiscount() {
		return lowdiscount;
	}

	public void setLowdiscount(Float lowdiscount) {
		this.lowdiscount = lowdiscount;
	}

	public String getSmallimage() {
		return smallimage;
	}

	public void setSmallimage(String smallimage) {
		this.smallimage = smallimage;
	}

	public String getMaximage() {
		return maximage;
	}

	public void setMaximage(String maximage) {
		this.maximage = maximage;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public Date getAdddate() {
		return adddate;
	}

	public void setAdddate(Date adddate) {
		this.adddate = adddate;
	}

	public Long getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(Long employeeid) {
		this.employeeid = employeeid;
	}

	public Long getMemo() {
		return memo;
	}

	public void setMemo(Long memo) {
		this.memo = memo;
	}
}
