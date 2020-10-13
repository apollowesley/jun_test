package org.iartisan.admin.template.config.sitemesh;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ExportTagToContentRule;
import org.sitemesh.tagprocessor.State;


public class ExtHtmlRuleBundle implements TagRuleBundle {

    public static final String PAGEFOOTER = "pagefooter";

    @Override
    public void install(State state, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
        state.addRule(PAGEFOOTER, new ExportTagToContentRule(siteMeshContext, contentProperty.getChild(PAGEFOOTER), false));
    }

    @Override
    public void cleanUp(State state, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {

    }
}
