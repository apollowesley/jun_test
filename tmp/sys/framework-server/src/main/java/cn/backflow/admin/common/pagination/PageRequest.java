package cn.backflow.admin.common.pagination;


import cn.backflow.lib.util.StringUtil;
import okhttp3.OkHttpClient;

import java.io.Serializable;
import java.util.*;


/**
 * 分页请求信息
 */
public class PageRequest implements Serializable {
    private static final long serialVersionUID = 4047115735625926686L;

    private Map<String, Object> filters = new HashMap<>(8); // 查询过滤参数
    private Integer pageNumber; // 页号码,页码从1开始
    private Integer pageSize;   // 分页大小
    private String sortColumns; // 排序的多个列, 如: username desc
    private String requestUri = ""; // 内部请求地址
    private String query = "";

    public PageRequest() {
    }

    public PageRequest(int pageSize, int pageNumber, String sortColumns) {
        setSortColumns(sortColumns);
        setPageNumber(pageNumber);
        setPageSize(pageSize);
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public PageRequest addFilter(String name, Object value) {
        this.filters.put(name, value);
        return this;
    }

    public String getFilter(String name) {
        return (String) filters.get(name);
    }

    public String replaceQueryParam(String name, String value) {
        if (StringUtil.isNotBlank(query)) {
            query = query.replaceAll(name + "=.*?($|&)", "").replaceFirst("&$", "");
        }
        return addQueryParam(name, value);
    }

    private String addQueryParam(String query, String name, Object... values) {
        query += query.isEmpty() ? "?" : "&";
        StringBuilder queryBuilder = new StringBuilder(query);
        for (int i = 0, len = values.length; i < len; i++) {
            if (i > 0) queryBuilder.append("&");
            queryBuilder.append(name).append("=").append(values[i]);
        }
        query = queryBuilder.toString();
        return query;
    }

    public String addParamAndGetQuery(String name, Object value) {
        String query = this.query;
        return requestUri + addQueryParam(query, name, value);
    }

    public String getQuery() {
        return requestUri + query;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber < 1 ? 1 : pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageOffset() {
        return (pageNumber - 1) * pageSize;
    }


    public PageRequest setRequestUri(String requestUri) {
        this.requestUri = requestUri;
        return this;
    }

    public String getSortColumns() {
        return sortColumns;
    }

    /**
     * 排序的列,可以同时多列,使用逗号分隔,如 username desc,age asc
     */
    public void setSortColumns(String sortColumns) {
        if (sortColumns == null)
            return;
        if (sortColumns.contains("'") || sortColumns.contains("\\"))
            throw new IllegalArgumentException("sortColumns:" + sortColumns + " has SQL Injection risk");
        if (sortColumns.length() > 50)
            throw new IllegalArgumentException("sortColumns.length() must be less than 50");
        this.sortColumns = sortColumns;
    }

    /**
     * 将sortColumns进行解析以便返回SortInfo以便使用
     */
    public List<SortColumn> getSortColumnList() {
        return SortColumn.parseSortColumns(sortColumns);
    }

    /**
     * 排序的列
     */
    public static class SortColumn {

        public String column;
        public String order;

        SortColumn() { }

        public SortColumn(String column, String order) {
            this.column = column;
            this.order = order;
        }

        public static List<SortColumn> parseSortColumns(String sortColumns) {
            if (sortColumns == null) {
                return Collections.emptyList();
            }
            List<SortColumn> results = new ArrayList<>();
            String[] segments = sortColumns.trim().split(",");
            for (String segment : segments) {
                String[] array = segment.split("\\s+");
                results.add(
                        new SortColumn(
                                array[0],
                                array.length == 2 ? array[1] : null
                        )
                );
            }
            return results;
        }

        @Override
        public String toString() {
            return column + (order == null ? "" : " " + order);
        }
    }
}