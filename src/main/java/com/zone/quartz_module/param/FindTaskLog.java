package com.zone.quartz_module.param;


import com.zone.quartz_module.common.Page;

public class FindTaskLog {

	private Long id;
	private Page page;
	private String msg;
	private Integer result;
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
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	
	
}
