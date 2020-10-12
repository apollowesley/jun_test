
import com.google.gson.Gson;
import net.cassite.search.Request;
import net.cassite.search.Result;
import net.cassite.search.Search;
import net.cassite.search.engines.BaiduEngine;
import net.cassite.search.engines.BingEngine;
import net.cassite.search.engines.SoEngine;
import net.cassite.search.engines.SogouEngine;

import java.util.List;

/**
 *
 */
public class Test {
        public static void main(String[] args) throws Exception {
                Search search = new Search(new BaiduEngine());
                Request req = new Request();
                req.setSite("my.oschina.net");
                req.setPage(0); // 页码从0开始
                req.getKeywords().add("wkgcass");
                List<Result> results = search.search(req);
                System.out.println(new Gson().toJson(results));

                /*
                final String word = "开源中国";
                System.out.println("{");
                System.out.println("\"Baidu\":");

                Search search = new Search(new BaiduEngine());
                System.out.println(new Gson().toJson(search.search(word)));

                System.out.println(",");
                System.out.println("\"Bing\":");

                search = new Search(new BingEngine());
                System.out.println(new Gson().toJson(search.search(word)));

                System.out.println(",");
                System.out.println("\"So360\":");

                search = new Search(new SoEngine());
                System.out.println(new Gson().toJson(search.search(word)));

                System.out.println(",");
                System.out.println("\"Sogou\":");

                search = new Search(new SogouEngine());
                System.out.println(new Gson().toJson(search.search(word)));

                System.out.println("}");

                /*
                Document doc=Jsoup.connect("https://www.sogou.com/web?query=abc").get();
                //Document doc = Jsoup.parse(Test.class.getResourceAsStream("/test.txt"), "UTF-8", "");
                System.out.println(doc);
                */
        }
}
