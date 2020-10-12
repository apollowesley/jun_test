package com.feng.zhihao.plugin;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;

import java.io.File;

public class Init extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
        File file = new File(project.getBasePath(),"FreeMarker");
        file.mkdirs();
        project.getProjectFile().refresh(false,false);
    }
}
