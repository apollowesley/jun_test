package cn.backflow.admin.common.taglib;

import cn.backflow.admin.common.treeable.Treeable;
import cn.backflow.admin.entity.Permission;
import cn.backflow.lib.util.JsonUtil;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.util.*;

/**
 * dropdown tree-menu for bootstrap-tree
 *
 * @author backflow
 *         2014年10月13日 下午4:04:17
 */
public class TreeTag extends AbstractTagSupport {

    private String name;        // form filed name
    private String type;        // jstree or bootstrap-tree
    private boolean multi;      // multiple select mode
    private boolean inherit;    // inherit select mode
    private int showLevel = 2;  // opened level
    private boolean checkbox;   // show checkboxes
    private Comparable value;   // current value
    private List<Treeable> treeables;
    private Set<? extends Comparable> selected = Collections.emptySet();
    private Set<? extends Comparable> unselectable = Collections.emptySet();
    private int level = 0;

    @Override
    protected void init() {
        boolean isnull = value == null;
        boolean isempty = selected == null || selected.isEmpty();
        if (!isnull && isempty)
            selected = Collections.singleton(NumberUtils.toInt(value.toString()));
        if (isnull && !isempty)
            value = selected.iterator().next();
    }

    @Override
    public void doTagLogic() throws JspException, IOException {
        if ("bstree".equalsIgnoreCase(type))
            writeBootstrapTree();
        else
            writeJsTree();
    }

    private void writeJsTree() throws IOException {
        append("<div class=\"js-tree\" role=\"tree\"");
        append(attributes()).append(">");
        append("<ul>");
        if (treeables != null) {
            for (Treeable t : treeables) {
                writeJsTree(t);
            }
        }
        append("</ul>");
        append("</div>");
    }

    // jstree avalible attributes are : opened selected disabled icon

    private void writeJsTree(Treeable t) throws IOException {
        level++;
        Comparable id = t.getId();
        Permission p = null;
        if (t instanceof Permission) {
            p = (Permission) t;
        }

        Map<String, Object> state = new HashMap<>();
        if (unselectable.contains(id)) {
            state.put("disabled", true);
        }
        if (selected.contains(id)) {
            state.put("selected", true);
        }
        if (t.getParent() == null) {
            state.put("opend", true);
        }
        if (p != null) {
            state.put("icon", p.getIcon());
        }

        List<Treeable> children = t.getChildren();

        append("<li id='").append(id.toString()).append("'");
        jstreeProperties(state);
        append(">");
        append(t.getName());
        if (!children.isEmpty()) {
            append("<ul>");
            for (Treeable c : children) {
                writeJsTree(c);
            }
            append("</ul>");
        }
        append("</li>");
        level--;
    }

    private void jstreeProperties(Map<String, Object> state) throws IOException {
        append(" data-jstree='");
        append(JsonUtil.toJson(state));
        append("'");
    }

    private void writeBootstrapTree() throws IOException {
        append("<ul").append(attributes());
        if (checkbox) append(" data-checkbox=\"true\"");
        if (inherit) append(" data-inherit=\"true\"");
        if (multi) append(" data-multi=\"true\"");
        append("\" role=\"tree\">");
        if (treeables != null) {
            for (Treeable t : treeables) {
                writeBootstrapTree(t);
            }
        }
        append("</ul>");
    }

    private void writeBootstrapTree(Treeable t) throws IOException {
        level++;
        Comparable id = t.getId();
        List<Treeable> children = t.getChildren();
        String sel = selected.contains(id) ? " checked" : "";
        String dis = unselectable.contains(id) ? " disabled" : "";

        if (children.isEmpty()) {
            append("<li class=\"").append(sel).append(dis).append("\" data-id=\"").append(id.toString()).append("\">");
            append("<span>").append(t.getName()).append("</span>");
            append("</li>");
        } else {
            append("<li class=\"parent").append(sel).append(dis).append(level < showLevel ? " open" : "");
            append("\" data-id=\"").append(id.toString()).append("\">");
            append("<span>").append("<em").append(" title=\"展开/关闭\"></em>").append(t.getName()).append("</span>");
            append("<ul>");
            for (Treeable c : children) {
                writeBootstrapTree(c);
            }
            append("</ul>");
            append("</li>");
        }
        level--;
    }

    private void writeCheckbox(Treeable t, String name, String checked) throws IOException {
        append("<label class=\"clip-checkbox\">");
        append("<input type=\"checkbox\" name=\"").append(name).append("\" value=\"").append(t.getId().toString()).append("\"").append(checked).append("><i></i>");
        append(t.getName());
        append("</label>");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(Comparable value) {
        this.value = value;
    }

    public void setShowLevel(int level) {
        this.showLevel = level;
    }

    public void setMulti(boolean multi) {
        this.multi = multi;
    }

    public void setInherit(boolean inherit) {
        this.inherit = inherit;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public void setSelected(Set<Comparable> selected) {
        this.selected = selected;
    }

    public void setTreeables(List<Treeable> treeables) {
        this.treeables = treeables;
    }

    public void setUnselectable(Set<Comparable> unselectable) {
        this.unselectable = unselectable;
    }

    @Override
    protected String getRequiredStyle() {
        return "bootstrap-tree";
    }
}