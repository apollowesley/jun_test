package com.lijian.devutils.bean;

import java.util.ArrayList;
import java.util.List;

public class PageBean {
	
	private int curPage; 
	private int totalCounts;
	private int rowsPerPage = 6;

	private int totalPages; 
	@SuppressWarnings("rawtypes")
	private List list;
	
	public void setTotalPages() {
		int temp = 0;
		if (this.totalCounts % this.rowsPerPage == 0) {
			temp = this.totalCounts / this.rowsPerPage;
		} else {
			temp = this.totalCounts / this.rowsPerPage;
			temp += 1;
		}
		this.totalPages = temp;
	}
	
	
	public void setList() {
		int temp=this.curPage;
		List tempList=new ArrayList();
		if(this.totalPages<10){
			for(int i=1;i<=this.totalPages;i++){
				tempList.add(i);
			}
		}else{
			
			if((temp-4)<1){
				for(int i=1;i<=10;i++){
					if(i<=this.totalPages){
						tempList.add(i);
					}
				}
			}else{
				if((temp+5)>this.totalPages){
					for(int i=(this.totalPages-9);i<this.totalPages;i++){
						tempList.add(i);
					}
				}else{
					for(int i=(temp-4);i<=this.curPage+5;i++){
						if(i<=this.totalPages){
							tempList.add(i);
						}
					}
				}
			}
		}
		
		this.list = tempList;
	}
	
	

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getTotalCounts() {
		return totalCounts;
	}

	public void setTotalCounts(int totalCounts) {
		this.totalCounts = totalCounts;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public List getList() {
		return list;
	}

	public PageBean() {
		super();
	}

	public PageBean(int curPage, int totalCounts,
			int rowsPerPage) {
		super();
		this.curPage = curPage;
		this.totalCounts = totalCounts;
		this.rowsPerPage = rowsPerPage;
		this.setTotalPages();
		this.setList();
	}
	
	@Override
	public String toString() {
		return "PageBean [curPage=" + curPage + ", totalCounts=" + totalCounts
				+ ", rowsPerPage=" + rowsPerPage + ", totalPages=" + totalPages
				+ ", list=" + list + "]";
	}

}
