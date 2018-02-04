package com.nico.noson.entity;

import java.util.Date;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月26日 下午2:59:31
 */

public class UserInf {

	private String name;
	
	private String address;
	
	private Date birthday;
	
	private Double sale;

	@Override
	public String toString() {
		return "UserInf [name=" + name + ", address=" + address + ", birthday=" + birthday + ", sale=" + sale + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Double getSale() {
		return sale;
	}

	public void setSale(Double sale) {
		this.sale = sale;
	}
	
	
}
