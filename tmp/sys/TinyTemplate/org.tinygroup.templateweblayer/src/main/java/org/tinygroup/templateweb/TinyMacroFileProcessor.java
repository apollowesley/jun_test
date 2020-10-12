package org.tinygroup.templateweb;

import org.tinygroup.bundle.BundleManager;
import org.tinygroup.bundle.config.BundleDefine;
import org.tinygroup.bundle.loader.TinyClassLoader;
import org.tinygroup.commons.tools.StringUtil;
import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.template.TemplateEngine;
import org.tinygroup.template.TemplateException;
import org.tinygroup.template.loader.ClassLoaderResourceLoader;
import org.tinygroup.template.loader.FileObjectResourceLoader;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xmlparser.node.XmlNode;

import java.util.Map;
import java.util.Set;

/**
 * tiny宏文件扫描
 *
 * @author renhui
 */
public class TinyMacroFileProcessor extends AbstractFileProcessor {

    private static final String COMPONENT_FILE_NAME = ".component";

    private static final String TINY_TEMPLATE_CONFIG = "/application/template-config";

    private TemplateEngine engine;

    private BundleManager bundleManager;

    private static boolean hasResourceLoader = false;

    public TemplateEngine getEngine() {
        return engine;
    }

    public void setEngine(TemplateEngine engine) {
        this.engine = engine;
    }

    public BundleManager getBundleManager() {
        return bundleManager;
    }

    public void setBundleManager(BundleManager bundleManager) {
        this.bundleManager = bundleManager;
    }

    public boolean isMatch(FileObject fileObject) {
        if (!fileObject.isFolder() && fileObject.getFileName().endsWith(COMPONENT_FILE_NAME)) {
            return true;
        }
        return false;
    }

    public void process() {
        if (!hasResourceLoader) {
            addResourceLoaders();
            hasResourceLoader = true;
        }
        for (FileObject fileObject : changeList) {
            logger.logMessage(LogLevel.INFO, "正在加载宏模板配置文件[{0}]",
                    fileObject.getAbsolutePath());
            try {
                engine.registerMacroLibrary(fileObject.getPath());
            } catch (TemplateException e) {
                logger.errorMessage("加载宏模板配置文件[{0}]出错", e,
                        fileObject.getAbsolutePath());
            }
            logger.logMessage(LogLevel.INFO, "正在加载宏模板配置文件[{0}]",
                    fileObject.getAbsolutePath());
        }

    }

    private void addResourceLoaders() {
        String templateExtFileName = null;
        String layoutExtFileName = null;
        String componentExtFileName = null;
        if (applicationConfig != null) {
            templateExtFileName = applicationConfig
                    .getAttribute("templateExtFileName");
            layoutExtFileName = applicationConfig
                    .getAttribute("layoutExtFileName");
            componentExtFileName = applicationConfig
                    .getAttribute("componentExtFileName");
        }
        if (StringUtil.isBlank(templateExtFileName)) {
            templateExtFileName = "vm";
        }
        if (StringUtil.isBlank(layoutExtFileName)) {
            layoutExtFileName = "layout";
        }
        if (StringUtil.isBlank(componentExtFileName)) {
            componentExtFileName = "component";
        }
        Map<BundleDefine, TinyClassLoader> bundles = bundleManager
                .getBundleMap();
        for (TinyClassLoader classLoader : bundles.values()) {
            ClassLoaderResourceLoader classResourceLoader = new ClassLoaderResourceLoader(
                    templateExtFileName, layoutExtFileName,
                    componentExtFileName, classLoader);
            engine.addTemplateLoader(classResourceLoader);
        }
        Set<String> scanningPaths = fileResolver.getResolveFileObjectSet();
        for (String path : scanningPaths) {
            FileObjectResourceLoader fileResourceLoader = new FileObjectResourceLoader(
                    templateExtFileName, layoutExtFileName,
                    componentExtFileName, path);
            engine.addTemplateLoader(fileResourceLoader);
        }
    }

    public void config(XmlNode applicationConfig, XmlNode componentConfig) {
        super.config(applicationConfig, componentConfig);

    }


    public String getApplicationNodePath() {
        return TINY_TEMPLATE_CONFIG;
    }

}
