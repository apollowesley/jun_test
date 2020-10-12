package cn.backflow.admin.common.pagination;

import java.io.Serializable;
import java.util.*;

/**
 * 分页信息 第一页从1开始
 */
public class Page<T> implements Serializable, Iterable<T> {
    private static final long serialVersionUID = 3035157868344176539L;

    private int pageSize, pageNumber, totalCount;
    private List<T> items;

    public Page(PageRequest p, int totalCount) {
        this(p.getPageNumber(), p.getPageSize(), totalCount);
    }

    public Page(int pageNumber, int pageSize, int totalCount) {
        this(pageNumber, pageSize, totalCount, new ArrayList<>(pageSize));
    }

    public Page(int pageNumber, int pageSize, int totalCount, List<T> items) {
        this.pageSize = pageSize;
        this.pageNumber = correctPageNumber(pageNumber, pageSize, totalCount);
        this.totalCount = totalCount;
        setItems(items);
    }

    public Page<T> setItems(List<T> items) {
        if (items == null)
            throw new IllegalArgumentException("[items] must be not null");
        this.items = items;
        return this;
    }

    /** 分页大小 */
    public int getPageSize() {
        return pageSize;
    }

    /** 当前页 */
    public int getPageNumber() {
        return pageNumber;
    }

    /** 总条目数 */
    public int getTotalCount() {
        return totalCount;
    }

    /** 是否为第一页 */
    public boolean isFirstPage() {
        return pageNumber == 1;
    }

    /** 是否为最后一页 */
    public boolean isLastPage() {
        return getPageNumber() == getLastPageNumber();
    }

    /** 是否有下一页 */
    public boolean hasNextPage() {
        return getLastPageNumber() > getPageNumber();
    }

    /** 是否有上一页 */
    public boolean hasPrevPage() {
        return getPageNumber() > 1;
    }

    /** 获取最后一页页码(总页数) */
    public int getLastPageNumber() {
        return computeLastPageNumber(totalCount, pageSize);
    }


    /** 得到数据库的第一条记录号 */
    public int getFirstElementIndex() {
        return (pageNumber - 1) * pageSize;
    }

    /** 获取当前页的首条数据的行编码 */
    public int getFirstElementNumber() {
        return totalCount == 0 ? 0 : (getPageNumber() - 1) * pageSize + 1;
    }

    /** 获取当前页尾数据的行编码 */
    public int getLastElementNumber() {
        int fullPage = getFirstElementNumber() + getPageSize() - 1;
        return getTotalCount() < fullPage ? getTotalCount() : fullPage;
    }

    /** 下一页 */
    public int getNextPageNumber() {
        return hasNextPage() ? getPageNumber() + 1 : getLastPageNumber();
    }

    /** 上一页 */
    public int getPrevPageNumber() {
        return hasPrevPage() ? getPageNumber() - 1 : 1;
    }

    /** 得到用于多页跳转的页码 */
    public List<Integer> getPageNumbers(int count) {
        return computePageNumbers(getPageNumber(), getLastPageNumber(), count);
    }

    public List<Integer> getPageNumbers() {
        return computePageNumbers(getPageNumber(), getLastPageNumber(), 9);
    }

    /** 得到略过多余页数的页码 */
    public List<Integer> getEllipsisPageNumbers(int count) {
        return computeEllipsisPageNumbers(getPageNumber(), getLastPageNumber(), count);
    }

    /** 得到略过多余页数的页码 */
    public List<Integer> getEllipsisPageNumbers() {
        return computeEllipsisPageNumbers(getPageNumber(), getLastPageNumber(), 9);
    }

    /** 分页条目集合 */
    public List<T> getItems() {
        return items;
    }

    @Override
    public Iterator<T> iterator() {
        return items == null ? Collections.emptyListIterator() : items.iterator();
    }

    /**
     * 生成页数列表
     *
     * @param current 当前页
     * @param last    最后一页
     * @param count   跳转链接数
     */
    private LinkedList<Integer> computePageNumbers(int current, int last, int count) {
        int avg = count / 2;
        int start = current - avg;
        if (start < 1) {
            start = 1;
        }
        int end = start + count - 1;
        if (end > last) {
            end = last;
        }
        if (end - start < count) {
            start = end - count + 1;
            if (start < 1) {
                start = 1;
            }
        }
        LinkedList<Integer> pages = new LinkedList<>();
        for (int i = start; i <= end; i++) {
            pages.add(i);
        }
        return pages;
    }

    /** 计算总页数 */
    private int correctPageNumber(int pageNumber, int pageSize, int totalCount) {
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        int lastPageNumber = computeLastPageNumber(totalCount, pageSize);
        if (Integer.MAX_VALUE == pageNumber || pageNumber > lastPageNumber) {
            pageNumber = lastPageNumber;
        }
        return pageNumber;
    }

    /**
     * 计算可略过的页码
     *
     * @param current 当前页
     * @param last    最后一页
     * @param count   跳转链接数
     */
    private List<Integer> computeEllipsisPageNumbers(int current, int last, int count) {
        LinkedList<Integer> pages = computePageNumbers(current, last, count);
        if (pages.getFirst() != 1) {
            pages.addFirst(1);
            if (pages.size() > count) {
                pages.set(1, 0);
                pages.remove(2);
            }
        }
        if (pages.getLast() != last) {
            pages.addLast(last);
            int size = pages.size();
            if (size > count) {
                pages.set(size - 2, 0);
                pages.remove(size - 3);
            }
        }
        return pages;
    }

    /**
     * 计算出最后一页的页数
     *
     * @param totalCount 总记录数
     * @param pageSize   页面大小
     */
    private int computeLastPageNumber(int totalCount, int pageSize) {
        int result = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        return result < 1 ? 1 : result;
    }
}