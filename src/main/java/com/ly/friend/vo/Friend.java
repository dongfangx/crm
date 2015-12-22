package com.ly.friend.vo;

import java.util.Date;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.View;

@Table("friend")
public class Friend{

	@Id
	@Column
	private Long id;

	@Column
	private String no;

	@Column
	private String cnname;

	@Column
	private String name;

	@Column
	private Long guanxiid;

	@Column
	private Long xinyongid;

	@Column
	private Long pinlvid;

	@Column
	private Long genderid;

	@Column
	private String nation;

	@Column
	private String nationality;

	@Column
	private Long partyid;

	@Column
	private String hobby;

	@Column
	private String health;

	@Column
	private Long height;

	@Column
	private Long weight;

	@Column
	private Long bloodid;

	@Column
	private Long maritalid;

	@Column
	private String address;

	@Column
	private String phone;

	@Column
	private String phone2;

	@Column
	private String linkman;

	@Column
	private String email;

	@Column
	private Long educationid;

	@Column
	private String major;

	@Column
	private String school;

	@Column
	private Date graduatetime;

	@Column
	private Date startworkingdate;

	@Column
	private Date birthday;

	@Column
	private Long age;

	@Column
	private String xinge;

	@Column
	private String memo;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getGuanxiid() {
		return guanxiid;
	}

	public void setGuanxiid(Long guanxiid) {
		this.guanxiid = guanxiid;
	}

	public Long getXinyongid() {
		return xinyongid;
	}

	public void setXinyongid(Long xinyongid) {
		this.xinyongid = xinyongid;
	}

	public Long getPinlvid() {
		return pinlvid;
	}

	public void setPinlvid(Long pinlvid) {
		this.pinlvid = pinlvid;
	}

	public Long getGenderid() {
		return genderid;
	}

	public void setGenderid(Long genderid) {
		this.genderid = genderid;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Long getPartyid() {
		return partyid;
	}

	public void setPartyid(Long partyid) {
		this.partyid = partyid;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(String health) {
		this.health = health;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Long getWeight() {
		return weight;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public Long getBloodid() {
		return bloodid;
	}

	public void setBloodid(Long bloodid) {
		this.bloodid = bloodid;
	}

	public Long getMaritalid() {
		return maritalid;
	}

	public void setMaritalid(Long maritalid) {
		this.maritalid = maritalid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getEducationid() {
		return educationid;
	}

	public void setEducationid(Long educationid) {
		this.educationid = educationid;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public Date getGraduatetime() {
		return graduatetime;
	}

	public void setGraduatetime(Date graduatetime) {
		this.graduatetime = graduatetime;
	}

	public Date getStartworkingdate() {
		return startworkingdate;
	}

	public void setStartworkingdate(Date startworkingdate) {
		this.startworkingdate = startworkingdate;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public String getXinge() {
		return xinge;
	}

	public void setXinge(String xinge) {
		this.xinge = xinge;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
