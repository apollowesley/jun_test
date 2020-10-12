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
 * 360 search engine (so.com). connect to https://www.so.com/s
 * and the operating system of UA is Mac OS X.
 */
public class SoEngine implements Engine {
        private static final String BASE_URL = "https://www.so.com/s?q=";
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
        public SoEngine allowEmptyTitle(boolean allowEmptyTitle) {
                this.allowEmptyTitle = allowEmptyTitle;
                return this;
        }

        /**
         * allow result whose content is empty.
         *
         * @param allowEmptyContent true or false
         * @return the object itself
         */
        public SoEngine allowEmptyContent(boolean allowEmptyContent) {
                this.allowEmptyContent = allowEmptyContent;
                return this;
        }

        /**
         * allow result whose link is empty.
         *
         * @param allowEmptyLink true or false
         * @return the object itself
         */
        public SoEngine setAllowEmptyLink(boolean allowEmptyLink) {
                this.allowEmptyLink = allowEmptyLink;
                return this;
        }

        /**
         * allow result whose cite is empty.
         *
         * @param allowEmptyCite true or false
         * @return the object itself
         */
        public SoEngine allowEmptyCite(boolean allowEmptyCite) {
                this.allowEmptyCite = allowEmptyCite;
                return this;
        }

        /**
         * build url as <code>https://www.so.com/s?q={keywords}&amp;pn={page+1}</code>
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

                return BASE_URL + URLEncoder.encode(sb.toString().trim(), "UTF-8") + "&pn=" + (request.getPage() + 1);
        }

        @Override
        public Method method() {
                return Method.GET;
        }

        @Override
        public List<Result> extract(Document document) throws Exception {
                List<Result> results = new ArrayList<Result>();
                Elements elems = document.select("#m-result>.res-list");
                for (Element e : elems) {
                        String title = "";
                        Map<Integer, String> title_keywords = new LinkedHashMap<Integer, String>();
                        String content = "";
                        Map<Integer, String> content_keywords = new LinkedHashMap<Integer, String>();
                        String link = "";
                        String cite = "";

                        // title
                        Element title_elem = e.select(".res-title>a").first();
                        if (title_elem != null) {
                                link = title_elem.attr("href").trim();

                                StringBuilder sb = new StringBuilder();
                                parseHtmlIntoResult(title_elem.childNodes(), sb, title_keywords, false);
                                title = sb.toString().trim();
                        }

                        Element res_linkinfo_parent = e;

                        // content
                        Element res_rich = e.select(".res-rich").first();
                        if (res_rich == null) {
                                Element content_elem = e.select(".res-desc").first();
                                if (content_elem != null) {
                                        StringBuilder sb = new StringBuilder();
                                        parseHtmlIntoResult(content_elem.childNodes(), sb, content_keywords, true);
                                        content = sb.toString().trim();
                                }
                        } else {
                                res_linkinfo_parent = res_rich;

                                // res_rich
                                Element res_comm_con = res_rich.select(".res-comm-con").first();
                                if (res_comm_con == null) {
                                        Element res_desc = res_rich.select(".res-desc").first();
                                        if (res_desc == null) {
                                                StringBuilder sb = new StringBuilder();
                                                parseHtmlIntoResult(res_rich.childNodes(), sb, content_keywords, true);
                                                content = sb.toString().trim();
                                        } else {
                                                StringBuilder sb = new StringBuilder();
                                                parseHtmlIntoResult(res_desc.childNodes(), sb, content_keywords, true);
                                                content = sb.toString().trim();
                                        }
                                } else {
                                        // res_comm_con
                                        res_linkinfo_parent = res_comm_con;

                                        Element content_elem = res_comm_con.select(".res-desc").first();
                                        if (content_elem != null) {
                                                StringBuilder sb = new StringBuilder();
                                                parseHtmlIntoResult(content_elem.childNodes(), sb, content_keywords, true);
                                                content = sb.toString().trim();
                                        } else {
                                                StringBuilder sb = new StringBuilder();
                                                parseHtmlIntoResult(res_comm_con.childNodes(), sb, content_keywords, true);
                                                content = sb.toString().trim();
                                        }
                                }
                        }

                        // cite
                        Element cite_elem = res_linkinfo_parent.select(".res-linkinfo>cite").first();
                        if (cite_elem != null) {
                                cite = Utils.replaceHtmlEscape(
                                        cite_elem.html().replaceAll("<[^<]*>", "").trim()
                                );
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
         * @return <code>Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/601.4.4 (KHTML, like Gecko) Version/9.0.3 Safari/601.4.4</code>
         */
        @Override
        public String UA() {
                return "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_3) AppleWebKit/601.4.4 (KHTML, like Gecko) Version/9.0.3 Safari/601.4.4";
        }

        private static void parseHtmlIntoResult(List<Node> nodes, StringBuilder sb, Map<Integer, String> keywords, boolean textAndEmOnly) {
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
                                                ((Element) child).html().trim()
                                        );
                                        keywords.put(sb.length(), keyword);
                                        sb.append(keyword);
                                } else {
                                        if (!textAndEmOnly)
                                                parseHtmlIntoResult(child.childNodes(), sb, keywords, false);
                                }
                        }
                }
        }
}
