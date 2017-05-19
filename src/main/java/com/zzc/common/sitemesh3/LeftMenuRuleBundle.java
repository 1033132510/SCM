package com.zzc.common.sitemesh3;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ExportTagToContentRule;
import org.sitemesh.tagprocessor.State;

/**
 * Created by wufan on 2015/11/9.
 */
public class LeftMenuRuleBundle implements TagRuleBundle {

    @Override
    public void install(State state, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
        state.addRule("leftMenu", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("leftMenu"), false));
    }

    @Override
    public void cleanUp(State state, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {

    }
}
