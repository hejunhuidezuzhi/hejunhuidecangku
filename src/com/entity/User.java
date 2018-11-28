package com.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.management.relation.Role;

public class User {
	private int id;
	private String typeNo;
	private String productionDate;
	private BigDecimal productionPrice;
	private String userCode;
	private String userName;
	private String userPassword;
	private Date birthday;  //出生日期
	private String phone;   //电话
	private String address; //地址
	private Integer gender;  //性别
	private Integer userRole;    //用户角色
	private Integer modifyBy;     //更新者
	private Date modifyDate;
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	private Date creationDate; //创建时间
	private Integer age;
	private Integer createdBy;
	private String userRoleName;
	
	private String workPicPath;
	public String getWorkPicPath() {
		return workPicPath;
	}
	public void setWorkPicPath(String workPicPath) {
		this.workPicPath = workPicPath;
	}
	public String getUserRoleName() {
		return userRoleName;
	}
	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}
	private String idPicPath;	//图片路径
	public String getIdPicPath() {
		return idPicPath;
	}
	public void setIdPicPath(String idPicPath) {
		this.idPicPath = idPicPath;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	private Role role;
	
//	private List<Address> addressList;
//	public List<Address> getAddressList() {
//		return addressList;
//	}
//	public void setAddressList(List<Address> addressList) {
//		this.addressList = addressList;
//	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Integer getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(Integer modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Integer getUserRole() {
		return userRole;
	}
	
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTypeNo() {
		return typeNo;
	}
	public void setTypeNo(String typeNo) {
		this.typeNo = typeNo;
	}
	public String getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(String productionDate) {
		this.productionDate = productionDate;
	}
	public BigDecimal getProductionPrice() {
		return productionPrice;
	}
	public void setProductionPrice(BigDecimal productionPrice) {
		this.productionPrice = productionPrice;
	}
}
