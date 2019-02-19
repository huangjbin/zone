package com.zone.quartz_module.param;


import com.zone.quartz_module.common.Page;

import java.util.List;

public class FindDevLogBean extends ParamBean{

	private Page page;
	private String name;
	private String sno;
	private Long end_time;
	private Long start_time;
	private Integer order;
	private List<Integer> object_ids;
	private Integer pageNumber;
	private Integer pageSize;
	private Long user_id;
	private List<Long> org_ids;

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

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
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

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public Long getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Long end_time) {
		this.end_time = end_time;
	}

	public Long getStart_time() {
		return start_time;
	}

	public void setStart_time(Long start_time) {
		this.start_time = start_time;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public List<Integer> getObject_ids() {
		return object_ids;
	}

	public void setObject_ids(List<Integer> object_ids) {
		this.object_ids = object_ids;
	}
}
