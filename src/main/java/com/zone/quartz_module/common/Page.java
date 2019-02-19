package com.zone.quartz_module.common;

public class Page {
	private Integer currentPage=1;    //当前页数
    private Integer totalPages;       //总页数
    private Long total;            //记录总行数
    private Integer pageSize = 10;    //每页记录行数
    private Integer nextPage;        //下一页
    private Integer prefPage;       //前一页
    private Integer startNumber;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPages() {
        totalPages = (int) (total % pageSize == 0 ? total / pageSize : total / pageSize + 1);
        return totalPages;
    }
    
    

    public Integer getStartNumber() {
		return startNumber;
	}

	public void setStartNumber(Integer startNumber) {
		this.startNumber = startNumber;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getNextPage() {
        if (currentPage < totalPages) {
            nextPage = currentPage + 1;
        } else {
            nextPage = currentPage;
        }
        return nextPage;
    }

    public Integer getPrefPage() {
        if (currentPage > 1) {
            prefPage = currentPage - 1;
        } else {
            prefPage = currentPage;
        }
        return prefPage;
    }
}
