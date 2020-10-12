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
 * the bing search engine. connect to http://cn.bing.com/search
 * and the operating system of UA is Android.
 */
public class BingEngine implements Engine {
        private static final String BASE_URL = "http://cn.bing.com/search?q=";
        private boolean allowEmptyTitle = false;
        private boolean allowEmptyContent = false;
        private boolean allowEmptyLink = false;
        private boolean allowEmptyCite = true;

        /**
         * allow result whose title is empty.
         *
         * @param allowEmptyTitle true or false
         * @return the object itself
         */
        public BingEngine allowEmptyTitle(boolean allowEmptyTitle) {
                this.allowEmptyTitle = allowEmptyTitle;
                return this;
        }

        /**
         * allow result whose content is empty.
         *
         * @param allowEmptyContent true or false
         * @return the object itself
         */
        public BingEngine allowEmptyContent(boolean allowEmptyContent) {
                this.allowEmptyContent = allowEmptyContent;
                return this;
        }

        /**
         * allow result whose link is empty.
         *
         * @param allowEmptyLink true or false
         * @return the object itself
         */
        public BingEngine setAllowEmptyLink(boolean allowEmptyLink) {
                this.allowEmptyLink = allowEmptyLink;
                return this;
        }

        /**
         * allow result whose cite is empty.
         *
         * @param allowEmptyCite true or false
         * @return the object itself
         */
        public BingEngine allowEmptyCite(boolean allowEmptyCite) {
                this.allowEmptyCite = allowEmptyCite;
                return this;
        }

        /**
         * build url as <code>http://cn.bing.com/search?q={keywords}&amp;first={page*10+1}</code>
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

                return BASE_URL + URLEncoder.encode(sb.toString().trim(), "UTF-8") + "&first=" + (request.getPage() * 10 + 1);
        }

        @Override
        public Method method() {
                return Method.GET;
        }

        @Override
        public List<Result> extract(Document document) throws Exception {
                List<Result> results = new ArrayList<Result>();

                Elements elems = document.select("#b_results>.b_algo");
                for (Element e : elems) {
                        String title = "";
                        Map<Integer, String> title_keywords = new LinkedHashMap<Integer, String>();
                        String content = "";
                        Map<Integer, String> content_keywords = new LinkedHashMap<Integer, String>();
                        String link = "";
                        String cite = "";

                        // title
                        Element title_elem = e.select("h2>a").first();
                        if (title_elem != null) {
                                link = title_elem.attr("href").trim();
                                StringBuilder title_sb = new StringBuilder();
                                parseHtmlIntoResult(title_elem.childNodes(), title_sb, title_keywords);
                                title = title_sb.toString().trim();
                        }

                        // content
                        Element content_elem = e.select(".b_caption>p").first();
                        if (content_elem != null) {
                                StringBuilder content_sb = new StringBuilder();
                                parseHtmlIntoResult(content_elem.childNodes(), content_sb, content_keywords);
                                if (content.isEmpty()) {
                                        content = content_sb.toString().trim();
                                } else {
                                        content += content_sb.toString().trim();
                                }
                        }

                        // cite
                        Element cite_elem = e.select(".b_caption>.b_attribution>cite").first();
                        if (cite_elem != null) {
                                cite = cite_elem.html().trim();
                        }

                        if (title.isEmpty() && !allowEmptyTitle) continue;
                        if (content.isEmpty() && !allowEmptyContent) continue;
                        if (link.isEmpty() && !allowEmptyLink) continue;
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
                        u.setLink(link);
                        result.setUrl(u);

                        results.add(result);
                }

                return results;
        }

        /**
         * @return "Mozilla/5.0 (Linux; U; Android 4.1; en-us; GT-N7100 Build/JRO03C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
         */
        @Override
        public String UA() {
                return "Mozilla/5.0 (Linux; U; Android 4.1; en-us; GT-N7100 Build/JRO03C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
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
                                if (child.nodeName().equals("strong")) {
                                        // the keyword
                                        String keyword = Utils.replaceHtmlEscape(
                                                ((Element) child).html()
                                        );
                                        keywords.put(sb.length(), keyword);
                                        sb.append(keyword);
                                } else {
                                        if (!child.attr("mb_ok_tag").isEmpty()) continue;
                                        parseHtmlIntoResult(child.childNodes(), sb, keywords);
                                }

                        }
                }
        }
}
