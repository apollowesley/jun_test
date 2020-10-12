package com.zlkj.shiro.dyprem.freemarker.tag;

import freemarker.template.SimpleHash;

/**
 * Shortcut for injecting the tags into Freemarker
 * <p>
 * Usage: cfg.setSharedVeriable("shiro", new ShiroTags());
 * </p>
 */

public class ShiroTags extends SimpleHash {
	private static final long serialVersionUID = 1L;

	/**
	 * 如果采用这种方式 那么就把ShiroTags benan配置到 freemarkerConfig freemarkerVariables变量中
	 * <entry key="shiro" value-ref="shiro"></entry> 标签的用法:
	 * <@shiro.hasPermission name="admin:index"> </@shiro.hasPermission>
	 */
	public ShiroTags() {
		put("authenticated", new AuthenticatedTag());
		put("guest", new GuestTag());
		put("hasAnyRoles", new HasAnyRolesTag());
		put("hasPermission", new HasPermissionTag());
		put("hasRole", new HasRoleTag());
		put("lacksPermission", new LacksPermissionTag());
		put("lacksRole", new LacksRoleTag());
		put("notAuthenticated", new NotAuthenticatedTag());
		put("principal", new PrincipalTag());
		put("user", new UserTag());
	}
}