package com.zone.quartz_module.pojo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class User implements Serializable{
	
	private Integer id;
	@NotNull(message="username")
	private String username;
	@NotNull(message="password")
	private String password;
	private String name;
	private String wechat_openid;
	private String wechat_nickname;
	private Integer wechat_sex;
	private String wechat_city;
	private String wechat_country;
	private String wechat_province;
	private String wechat_language;
	private String wechat_headimgurl;
	private Long subscribe_time;
	private String mobile;
	private String email;
	private String address;
	@NotNull(message="org_id")
	private Integer org_id;
	private String org_name;
	private Integer status;
	private Long create_time;
	private String remark;
	
	
	
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWechat_openid() {
		return wechat_openid;
	}
	public void setWechat_openid(String wechat_openid) {
		this.wechat_openid = wechat_openid;
	}
	public String getWechat_nickname() {
		return wechat_nickname;
	}
	public void setWechat_nickname(String wechat_nickname) {
		this.wechat_nickname = wechat_nickname;
	}
	
	public String getWechat_city() {
		return wechat_city;
	}
	public void setWechat_city(String wechat_city) {
		this.wechat_city = wechat_city;
	}
	public String getWechat_country() {
		return wechat_country;
	}
	public void setWechat_country(String wechat_country) {
		this.wechat_country = wechat_country;
	}
	public String getWechat_province() {
		return wechat_province;
	}
	public void setWechat_province(String wechat_province) {
		this.wechat_province = wechat_province;
	}
	public String getWechat_language() {
		return wechat_language;
	}
	public void setWechat_language(String wechat_language) {
		this.wechat_language = wechat_language;
	}
	public String getWechat_headimgurl() {
		return wechat_headimgurl;
	}
	public void setWechat_headimgurl(String wechat_headimgurl) {
		this.wechat_headimgurl = wechat_headimgurl;
	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Integer getOrg_id() {
		return org_id;
	}
	public void setOrg_id(Integer org_id) {
		this.org_id = org_id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getWechat_sex() {
		return wechat_sex;
	}
	public void setWechat_sex(Integer wechat_sex) {
		this.wechat_sex = wechat_sex;
	}

	public Long getSubscribe_time() {
		return subscribe_time;
	}
	public void setSubscribe_time(Long subscribe_time) {
		this.subscribe_time = subscribe_time;
	}
	public Long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Long create_time) {
		this.create_time = create_time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name
				+ ", wechat_openid=" + wechat_openid + ", wechat_nickname=" + wechat_nickname + ", wechat_sex="
				+ wechat_sex + ", wechat_city=" + wechat_city + ", wechat_country=" + wechat_country
				+ ", wechat_province=" + wechat_province + ", wechat_language=" + wechat_language
				+ ", wechat_headimgurl=" + wechat_headimgurl + ", subscribe_time=" + subscribe_time + ", mobile="
				+ mobile + ", email=" + email + ", address=" + address + ", org_id=" + org_id + ", status=" + status
				+ ", create_time=" + create_time + ", remark=" + remark + "]";
	}
	
	

}























