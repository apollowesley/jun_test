package org.coody.czone.entity;

import org.coody.framework.jdbc.entity.Pager;

/**
 * 增强版的Pager，增加了一些方便遍历的属性值
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @version 1.0
 * @website https://www.zhyd.me
 * @date 2018/8/20 10:14
 * @since 1.8
 */
@SuppressWarnings("serial")
public class PageInfo extends Pager {

    public Integer getCurrentPage() {
        Integer currentPage = super.getCurrentPage();
        return null == currentPage ? 0 : currentPage;
    }

    public Integer getTotalPage() {
        Integer totalPage = super.getTotalPage();
        return null == totalPage ? 0 : totalPage;
    }

    public boolean isIsFirstPage() {
        return getCurrentPage() == 1;
    }

    public boolean isIsLastPage() {
        return getCurrentPage().equals(getTotalPage()) || getTotalPage() == 0;
    }

    public boolean isHasPreviousPage() {
        return getCurrentPage() > 1;
    }

    public boolean isHasNextPage() {
        return getCurrentPage() < getTotalPage();
    }

    public Integer getPrePage() {
        if (getCurrentPage() > 1) {
            return getCurrentPage() - 1;
        }
        return 0;
    }

    public Integer getNextPage() {
        if (getCurrentPage() < getTotalPage()) {
            return getCurrentPage() + 1;
        }
        return 0;

    }

}
