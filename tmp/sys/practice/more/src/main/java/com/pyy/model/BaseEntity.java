package com.pyy.model;

/**
 * @author PYY
 * 基本模型
 */
public abstract class BaseEntity {

    private Pageable page;
    
    //查询的起始条数
    private int startIndex;

    public Pageable getPage() {
    	if(null == page){
    		page = new Pageable(1, 10);
    	}
        return page;
    }

    public void setPage(Pageable page) {
        this.page = page;
    }
    
    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }


}
