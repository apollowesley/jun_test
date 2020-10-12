package net.cassite.search.provider;

import net.cassite.search.DocumentProvider;
import net.cassite.search.Method;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * provides {@link org.jsoup.nodes.Document} object with Jsoup. this is a singleton class,
 * use {@link #get()} to retrieve the instance.
 */
public class JsoupDocProvider implements DocumentProvider {
        private static final JsoupDocProvider singleton = new JsoupDocProvider();

        private JsoupDocProvider() {
        }

        /**
         * get the singleton object
         *
         * @return singleton object instance
         */
        public static JsoupDocProvider get() {
                return singleton;
        }

        @Override
        public Document provideWith(Arguments arguments) throws Exception {
                Connection conn = Jsoup.connect(arguments.url)
                        .validateTLSCertificates(false)
                        .userAgent(arguments.UA);

                if (arguments.method == Method.GET) {
                        return conn.get();
                } else if (arguments.method == Method.POST) {
                        return conn.post();
                } else throw new IllegalArgumentException("unknown method " + arguments.method);
        }
}
