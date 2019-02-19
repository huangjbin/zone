package com.zone.quartz_module.param;


import com.zone.quartz_module.common.Page;

import java.util.List;

public class FindTask {

	private Long id;
	private Page page;
	private String name;
	private Integer status;
	private Long user_id;
	private List<Long> org_ids;
	private Integer type;


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public List<Long> getOrg_ids() {
		return org_ids;
	}

	public void setOrg_ids(List<Long> org_ids) {
		this.org_ids = org_ids;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
