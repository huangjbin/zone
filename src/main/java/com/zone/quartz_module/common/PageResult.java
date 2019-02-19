package com.zone.quartz_module.common;

public class PageResult extends BaseResult{
	
	private Page page;

	public PageResult(int code, String message, Object data,Page page) {
		super(code, message, data);
		this.page = page;
		// TODO Auto-generated constructor stub
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	

}
