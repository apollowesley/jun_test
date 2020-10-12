package net.cassite.search.engines;

import net.cassite.search.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.*;

/**
 * the sogou search engine. connect to https://www.sogou.com/web
 * and the operating system of UA is Mac OS X.
 */
public class SogouEngine implements Engine {
        private static final String BASE_URL = "https://www.sogou.com/web?query=";
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
        public SogouEngine allowEmptyTitle(boolean allowEmptyTitle) {
                this.allowEmptyTitle = allowEmptyTitle;
                return this;
        }

        /**
         * allow result whose content is empty.
         *
         * @param allowEmptyContent true or false
         * @return the object itself
         */
        public SogouEngine allowEmptyContent(boolean allowEmptyContent) {
                this.allowEmptyContent = allowEmptyContent;
                return this;
        }

        /**
         * allow result whose link is empty.
         *
         * @param allowEmptyLink true or false
         * @return the object itself
         */
        public SogouEngine setAllowEmptyLink(boolean allowEmptyLink) {
                this.allowEmptyLink = allowEmptyLink;
                return this;
        }

        /**
         * allow result whose cite is empty.
         *
         * @param allowEmptyCite true or false
         * @return the object itself
         */
        public SogouEngine allowEmptyDomain(boolean allowEmptyCite) {
                this.allowEmptyCite = allowEmptyCite;
                return this;
        }

        /**
         * build url as <code>https://www.sogou.com/web?query={keywords}&amp;page={page+1}</code>
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

                return BASE_URL + URLEncoder.encode(sb.toString().trim(), "UTF-8") + "&page=" + (request.getPage() + 1);
        }

        @Override
        public Method method() {
                return Method.GET;
        }

        @Override
        public List<Result> extract(Document document) throws Exception {
                List<Result> results = new ArrayList<Result>();
                Elements elems = document.select("#main>div>.results").first().children();
                for (Element e : elems) {
                        String title = "";
                        Map<Integer, String> title_keywords = new LinkedHashMap<Integer, String>();
                        String content = "";
                        Map<Integer, String> content_keywords = new LinkedHashMap<Integer, String>();
                        String link = "";
                        String cite = "";

                        if (e.classNames().contains("vrwrap")) {
                                // title
                                Element title_elem = e.select(".vrTitle>a").first();
                                if (title_elem != null) {
                                        link = title_elem.attr("href").trim();

                                        StringBuilder sb = new StringBuilder();
                                        parseHtmlIntoResult(title_elem.childNodes(), sb, title_keywords);
                                        title = sb.toString().trim();
                                }

                                // content
                                Element content_elem = e.select(".strBox>.str_info_div>p").first();
                                if (content_elem == null) {
                                        content_elem = e.select(".strBox>.str_info_div>.str-text-info").first();
                                }

                                if (content_elem != null) {
                                        StringBuilder sb = new StringBuilder();
                                        parseHtmlIntoResult(content_elem.childNodes(), sb, content_keywords);
                                        content = sb.toString().trim();
                                }

                                // cite
                                Element cite_elem = e.select(".strBox>.str_info_div>.fb>cite").first();
                                if (cite_elem != null) {
                                        cite = Utils.replaceHtmlEscape(
                                                cite_elem.html().replaceAll("<[^<]*>", "").trim()
                                        );
                                }
                        } else if (e.classNames().contains("rb")) {
                                // title
                                Element title_elem = e.select("h3>a").first();
                                if (title_elem != null) {
                                        link = title_elem.attr("href").trim();

                                        StringBuilder sb = new StringBuilder();
                                        parseHtmlIntoResult(title_elem.childNodes(), sb, title_keywords);
                                        title = sb.toString().trim();
                                }

                                // content
                                Element content_elem = e.select(".ft").first();
                                if (content_elem != null) {
                                        StringBuilder sb = new StringBuilder();
                                        parseHtmlIntoResult(content_elem.childNodes(), sb, content_keywords);
                                        content = sb.toString().trim();
                                }

                                // cite
                                Element cite_elem = e.select(".fb>cite").first();
                                if (cite_elem != null) {
                                        cite = Utils.replaceHtmlEscape(
                                                cite_elem.html().replaceAll("<[^<]*>", "").trim()
                                        );
                                }
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
                                                ((Element) child).html().replaceAll("<[^<]*>", "").trim()
                                        );
                                        keywords.put(sb.length(), keyword);
                                        sb.append(keyword);
                                } else {
                                        parseHtmlIntoResult(child.childNodes(), sb, keywords);
                                }
                        }
                }
        }
}
