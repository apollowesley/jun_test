package cn.backflow.admin.common.taglib;


import cn.backflow.admin.common.pagination.Page;
import cn.backflow.admin.common.pagination.PageRequest;

import javax.servlet.jsp.JspException;
import java.io.IOException;

public class PageTag extends AbstractTagSupport {


    private PageRequest pageRequest;
    private boolean pageByLink = true;  // 默认使用链接分页（即参数通过链接地址传递）
    private Page<?> page;               // 分布对象
    private int count = 9;              // 每页条目数

    @Override
    protected void init() {
    }

    @Override
    public void doTagLogic() throws JspException, IOException {
        append("<nav class='text-xs-center text-md-left px-1'><ul").append(attributes()).append(">");
        for (int i : page.getEllipsisPageNumbers(count)) {
            append(String.format("<li class='page-item%s'><a class='page-link' %s>%s</a></li>",
                    i == page.getPageNumber() ? " active" : "",
                    i == 0 ? "" : "href='" + pageRequest.addParamAndGetQuery("pn", i) + "'",
                    i == 0 ? "..." : i)
            );
        }
        append(String.format("</ul><div class='pagination pagination-info'>显示 %s 条中的 %s 至 %s 条</div></nav>",
                page.getTotalCount(),
                page.getFirstElementNumber(),
                page.getLastElementNumber())
        );
    }

    // « » ‹ ›
    private void printSelect(int[] values, int curr) throws IOException {
        append("<div class='float-sm-right'><label>分页大小</label><select name='pn' onchange='app.changeParam(this)'>");
        for (int i : values) {
            append(String.format("<option value='%1$s' %2$s>%1$s</option>",
                    i,
                    i == curr ? "selected" : "")
            );
        }
        append("</select></div>");
    }

    @Override
    protected String getRequiredStyle() {
        return "pagination";
    }

    public void setPage(Page<?> page) {
        this.page = page;
    }

    public boolean isPageByLink() {
        return pageByLink;
    }

    public void setPageByLink(boolean pageByLink) {
        this.pageByLink = pageByLink;
    }

    public void setPageRequest(PageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }
}