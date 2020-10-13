package cn.usbtg.session.plugin;

import cn.usbtg.session.BTGSessionManager;
import cn.usbtg.session.kit.SessionKit;
import com.jfinal.plugin.IPlugin;

/**
 * session管理插件
 */
public class BTGSessionManagerPlugin implements IPlugin {

    private static BTGSessionManager sessionManager;

    public BTGSessionManagerPlugin(BTGSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public boolean start() {
        SessionKit.setSessionManager(sessionManager);
        return true;
    }

    @Override
    public boolean stop() {
        return true;
    }

}
