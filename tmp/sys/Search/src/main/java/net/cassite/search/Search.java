package net.cassite.search;

import net.cassite.search.provider.JsoupDocProvider;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * entrance of the searching process.
 */
public class Search {
        private static final Logger LOGGER = LoggerFactory.getLogger(Search.class);

        private final Engine engine;
        private DocumentProvider documentProvider = JsoupDocProvider.get();

        /**
         * set document provider [optional]
         *
         * @param documentProvider document provider
         * @return the object itself
         */
        public Search documentProvider(DocumentProvider documentProvider) {
                if (documentProvider == null) throw new NullPointerException();
                this.documentProvider = documentProvider;
                return this;
        }

        /**
         * construct a Search object with an engine
         *
         * @param engine engine (not null)
         */
        public Search(Engine engine) {
                if (engine == null) throw new NullPointerException();
                this.engine = engine;
        }

        /**
         * search for the keyword. the method will construct a Request object and invoke {@link #search(Request)}.
         *
         * @param keyword keyword (not null)
         * @return the searching result (not null)
         * @throws Exception exception
         */
        public List<Result> search(String keyword) throws Exception {
                Request request = new Request();
                request.getKeywords().add(keyword);
                return search(request);
        }

        /**
         * do search.
         *
         * @param request the request object (not null)
         * @return the searching result (not null)
         * @throws Exception exception
         */
        public List<Result> search(Request request) throws Exception {
                LOGGER.debug("Engine : {}", engine);
                LOGGER.debug("Request : {}", request);

                if (request == null) throw new NullPointerException();

                for (String s : request.getKeywords()) {
                        if (s == null) throw new NullPointerException("keyword list contains null strings");
                }

                String url = engine.buildURL(request);

                LOGGER.debug("BuildURL Result : {}", url);

                if (url == null) throw new NullPointerException();

                DocumentProvider.Arguments arguments = new DocumentProvider.Arguments();
                arguments.method = engine.method();
                arguments.UA = engine.UA();
                arguments.url = url;

                long start = System.currentTimeMillis();
                Document document = documentProvider.provideWith(arguments);
                long end = System.currentTimeMillis();

                LOGGER.debug("Connection Time : {} ms", end - start);

                start = System.currentTimeMillis();
                List<Result> res = engine.extract(document);
                end = System.currentTimeMillis();

                LOGGER.debug("Extract Time : {} ms", end - start);

                LOGGER.debug("Extract Result : {}", res);

                if (res == null)
                        throw new NullPointerException("the result should not be null");

                LOGGER.debug("Extract Result Size : {}", res.size());
                return res;
        }
}
