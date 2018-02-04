package com.nico.noson.entity;

import java.util.List;

/** 
 * 
 * @author nico
 * @version 创建时间：2017年11月26日 下午2:51:23
 */

public class User {
	
	private int id;
	
	private String username;
	
	private String password;
	
	private UserInf userInfo;
	
	private List<Integer> list;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserInf getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInf userInfo) {
		this.userInfo = userInfo;
	}

	public List<Integer> getList() {
		return list;
	}

	public void setList(List<Integer> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ",\n userInfo=" + userInfo
				+ ", list=" + list + "]";
	}

}
