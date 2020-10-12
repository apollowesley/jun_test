package com.jeasy.base.controllers;

/**
 * Abstract BaseController
 *
 * @author taomk
 * @version 1.0
 * @since 2014/9/22 14:33
 */
public abstract class BaseController extends HttpSupport {

	protected static final int DEFAULT_PAGE_SIZE = 6;

	protected static final String SUCCESS_INFO = "success";

	protected static final String FAILURE_INFO = "failure";

	private static final String TEXT_PREFIX = "@";

	private static final String JSON_PREFIX = "@json:";

	private static final String XML_PREFIX = "@xml:";

	private static final String PLAIN_PREFIX = "@plain:";

	private static final String HTML_PREFIX = "@html:";

	private static final String FORWARD_PREFIX = "f:";

	private static final String REDIRECT_PREFIX = "r:";

	protected String render(String render) {
		return renderJsp(render);
	}

	protected String renderJsp(String render) {
		return render;
	}

	protected String renderText(String text) {
		return TEXT_PREFIX + text;
	}

	protected String renderJson(String json) {
		return JSON_PREFIX + json;
	}

	protected String renderXml(String xml) {
		return XML_PREFIX + xml;
	}

	protected String renderPlain(String plain) {
		return PLAIN_PREFIX + plain;
	}

	protected String renderHtml(String html) {
		return HTML_PREFIX + html;
	}

	protected String forward(String forward) {
		return FORWARD_PREFIX + forward;
	}

	protected String redirect(String redirect) {
		return REDIRECT_PREFIX + redirect;
	}
}
