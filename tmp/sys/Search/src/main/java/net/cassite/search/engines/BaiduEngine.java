package net.cassite.search.engines;

import net.cassite.search.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * the baidu search engine. connect to http://m.baidu.com/s
 * and the operating system of UA is Android.
 */
public class BaiduEngine implements Engine {
        private static final String BASE_URL = "http://m.baidu.com/s?word=";
        private boolean allowEmptyTitle = false;
        private boolean allowEmptyContent = false;
        private boolean allowJavaScriptLink = false;
        private boolean allowEmptyLink = false;
        private boolean allowEmptyCite = true;

        /**
         * allow result whose title is empty.
         *
         * @param allowEmptyTitle true or false
         * @return the object itself
         */
        public BaiduEngine allowEmptyTitle(boolean allowEmptyTitle) {
                this.allowEmptyTitle = allowEmptyTitle;
                return this;
        }

        /**
         * allow result whose content is empty.
         *
         * @param allowEmptyContent true or false
         * @return the object itself
         */
        public BaiduEngine allowEmptyContent(boolean allowEmptyContent) {
                this.allowEmptyContent = allowEmptyContent;
                return this;
        }

        /**
         * allow result whose link is javascript
         *
         * @param allowJavaScriptLink true or false
         * @return the object itself
         */
        public BaiduEngine allowJavaScriptLink(boolean allowJavaScriptLink) {
                this.allowJavaScriptLink = allowJavaScriptLink;
                return this;
        }

        /**
         * allow result whose link is empty.
         *
         * @param allowEmptyLink true or false
         * @return the object itself
         */
        public BaiduEngine setAllowEmptyLink(boolean allowEmptyLink) {
                this.allowEmptyLink = allowEmptyLink;
                return this;
        }

        /**
         * allow result whose cite is empty.
         *
         * @param allowEmptyCite true or false
         * @return the object itself
         */
        public BaiduEngine allowEmptyCite(boolean allowEmptyCite) {
                this.allowEmptyCite = allowEmptyCite;
                return this;
        }

        /**
         * build url as <code>http://m.baidu.com/s?word={keywords}&amp;pn={page*10}</code>
         *
         * @param request the request object
         * @return the built url string
         * @throws Exception exception
         */
        @Override
        public String buildURL(Request request) throws Exception {
                StringBuilder sb = new StringBuilder();
                for (String s : request.getKeywords()) {
                        sb.append(s).append(" ");
                }
                if (request.getSite() != null) {
                        sb.append("site:").append(request.getSite()).append(" ");
                }
                return BASE_URL + URLEncoder.encode(sb.toString().trim(), "UTF-8") + "&pn=" + (request.getPage() * 10);
        }

        @Override
        public Method method() {
                return Method.GET;
        }

        @Override
        public List<Result> extract(Document document) throws Exception {
                List<Result> results = new ArrayList<Result>();

                Elements elems = document.select("#results>.result.c-result>.c-container");
                for (Element elem : elems) {
                        String title = "";
                        Map<Integer, String> title_keywords = new LinkedHashMap<Integer, String>();
                        String content = "";
                        Map<Integer, String> content_keywords = new LinkedHashMap<Integer, String>();

                        String url = "";

                        // c-blocka
                        // extract title and content
                        for (Element e : elem.select(".c-blocka")) {
                                Elements c_titles = e.select(".c-title");
                                if (c_titles.isEmpty()) {
                                        // content

                                        StringBuilder content_sb = new StringBuilder();
                                        parseHtmlIntoResult(e.childNodes(), content_sb, content_keywords);
                                        if (content.isEmpty()) {
                                                content = content_sb.toString();
                                        } else {
                                                String newContent = content_sb.toString().trim();
                                                if (!newContent.isEmpty()) {
                                                        content += " " + newContent;
                                                }
                                        }
                                } else {
                                        // is title
                                        // get url
                                        url = e.attr("href").trim();

                                        // get title
                                        Element title_elem = c_titles.first();
                                        StringBuilder title_sb = new StringBuilder();

                                        parseHtmlIntoResult(title_elem.childNodes(), title_sb, title_keywords);

                                        title = title_sb.toString();
                                }
                        }
                        title = title.trim();
                        content = content.trim();

                        if (title.isEmpty() && !allowEmptyTitle) continue;
                        if (content.isEmpty() && !allowEmptyContent) continue;
                        if (url.startsWith("javascript:") && !allowJavaScriptLink) continue;
                        if (url.isEmpty() && !allowEmptyLink) continue;

                        // get cite
                        String cite = "";
                        Element c_showurl_span = elem.select(".c-showurl>span").first();
                        if (c_showurl_span != null) {
                                cite = c_showurl_span.html().trim();
                        }
                        if (cite.isEmpty()) {
                                Element c_color_url = elem.select(".c-color-url").first();
                                if (c_color_url != null) {
                                        List<Node> childNodes = c_color_url.childNodes();
                                        if (!childNodes.isEmpty()) {
                                                Node txt = childNodes.get(0);
                                                if (txt instanceof TextNode) {
                                                        cite = ((TextNode) txt).text();
                                                }
                                        }
                                }
                        }

                        if (cite.isEmpty() && !allowEmptyCite) continue;

                        // build result

                        Result result = new Result();
                        Content con = new Content();
                        con.setContent(content);
                        con.getKeywords().putAll(content_keywords);
                        result.setContent(con);

                        Title tit = new Title();
                        tit.setTitle(title);
                        tit.getKeyWord().putAll(title_keywords);
                        result.setTitle(tit);

                        Url u = new Url();
                        u.setCite(cite);
                        u.setLink(url);
                        result.setUrl(u);

                        results.add(result);
                }
                return results;
        }

        private static void parseHtmlIntoResult(List<Node> nodes, StringBuilder sb, Map<Integer, String> keywords) {
                for (Node child : nodes) {
                        if (child instanceof TextNode) {
                                sb.append(
                                        Utils.replaceHtmlEscape(
                                                ((TextNode) child).text()
                                        )
                                );
                        } else if (child instanceof Element) {
                                if (child.nodeName().equals("em")) {
                                        // the keyword
                                        String keyword = Utils.replaceHtmlEscape(
                                                ((Element) child).html()
                                        );
                                        keywords.put(sb.length(), keyword);
                                        sb.append(keyword);
                                } else {
                                        if (child.nodeName().equals("i") || child.nodeName().equals("button")) continue;
                                        parseHtmlIntoResult(child.childNodes(), sb, keywords);
                                }

                        }
                }
        }

        /**
         * @return <code>Mozilla/5.0 (Linux; U; Android 4.1; en-us; GT-N7100 Build/JRO03C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30</code>
         */
        @Override
        public String UA() {
                return "Mozilla/5.0 (Linux; U; Android 4.1; en-us; GT-N7100 Build/JRO03C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
        }
}
