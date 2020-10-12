package com.feng.zhihao.plugin;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GenerateCode extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
        File templateFile = new File(project.getBasePath(),"FreeMarker");
        if(!templateFile.exists() || !templateFile.isDirectory()){
            Messages.showMessageDialog(project,"请初始化","系统提醒",Messages.getQuestionIcon());
        }
        DataContext dataContext = event.getDataContext();
        VirtualFile targetFile = (VirtualFile) dataContext.getData("virtualFile");
        if(!targetFile.isDirectory()){
            Messages.showMessageDialog(project,"请选择文件夹","系统提醒",Messages.getQuestionIcon());
        }
        String modelName = Messages.showInputDialog(
                project,
                "请输入模块名称?",
                "系统提醒",
                Messages.getQuestionIcon());
        File targetDir = new File(targetFile.getPath(),modelName);
        if(targetDir.exists()){
            Messages.showMessageDialog(project,"模块已经存在，请删除！","系统提醒",Messages.getQuestionIcon());
        }
        Map<String, Object> prop = new HashMap<>();
        prop.put("modelName", modelName);
        prop.put("date", new Date());
        targetDir.mkdirs();
        try {
            templateOut(templateFile,targetDir,prop);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        targetFile.refresh(false,true);
    }
    public void templateOut(File templateDir, File targetDir,Map<String, Object> prop ) throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDirectoryForTemplateLoading(templateDir);
        cfg.setDefaultEncoding("UTF-8");

        for (File tp :templateDir.listFiles() ){
            File tg = new File(targetDir,StrUtil.format(tp.getName(),prop.get("modelName")));
            if(tp.isDirectory()){
                tg.mkdirs();
                templateOut(tp,tg,prop);
            }else{
                Template temp = cfg.getTemplate(tp.getName());
                try(BufferedWriter outputStream = FileUtil.getWriter(tg,"UTF-8",false)){
                    temp.process(prop, outputStream);
                }
            }
        }
    }
}
