package com.foo.common.base.utils.mac;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;

import com.foo.common.base.utils.FooUtils;

/**
 * Utils for mac os only.See here: https://github.com/herrbischoff/awesome-osx-command-line
 *
 * @author Steve
 */
public enum MacHelper {
    instance;
    private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory
            .getLogger(MacHelper.class);

    public static void main(String[] args) throws IOException {

        if (!SystemUtils.IS_OS_MAC_OSX) {
            logger.error("sorry,support osx only.");
            return;
        }
        boolean isDisplay = true;
        instance.toggle_dashboard_visibility(isDisplay);
        instance.toggle_file_visibility(isDisplay);
    }

    /**
     * show or hide files
     *
     * @param isDisplay
     * @throws IOException
     */
    public void toggle_file_visibility(boolean isDisplay) throws IOException {
        String isDisplayStr = isDisplay ? "true" : "false";
        String command = "defaults write com.apple.finder AppleShowAllFiles -bool "
                + isDisplayStr + " && killall Finder";
        Process p = FooUtils.executeCommand(command);

        StringWriter sWriterSuccess = new StringWriter();
        IOUtils.copy(p.getInputStream(), sWriterSuccess, "utf-8");

        String sWriterSuccessStr = sWriterSuccess.toString();
        logger.info("execute toggleFileVisibility of:【{}】 and result is:{}",
                command, sWriterSuccessStr);
    }

    /**
     * show or hide dashboard
     *
     * @param isDisplay
     * @throws IOException
     */
    public void toggle_dashboard_visibility(boolean isDisplay)
            throws IOException {
        String isDisplayStr = isDisplay ? "yes" : "no";
        String command = "defaults write com.apple.dashboard mcx-disabled -boolean "
                + isDisplayStr + " && killall Dock";
        Process p = FooUtils.executeCommand(command);

        StringWriter sWriterSuccess = new StringWriter();
        IOUtils.copy(p.getInputStream(), sWriterSuccess, "utf-8");

        String sWriterSuccessStr = sWriterSuccess.toString();
        logger.info(
                "execute toggle_dashboard_visibility command of:【{}】 and result is:{}",
                command, sWriterSuccessStr);

    }

}
