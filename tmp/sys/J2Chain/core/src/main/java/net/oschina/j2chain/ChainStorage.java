package net.oschina.j2chain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.io.Writer;
import java.util.List;

/**
 * blockchain storage interface define
 * @author Winter Lau
 */
public class ChainStorage {

    private final static Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();

    /**
     * load the chain data
     * @param reader
     * @return
     */
    public static List<Block> load(Reader reader) {
        var listType = new TypeToken<List<Block>>(){}.getType();
        return gson.fromJson(reader, listType);
    }

    /**
     * save chain to persistent storage
     * @param chain
     * @param writer
     */
    public static void save(List<Block> chain, Writer writer) {
        gson.toJson(chain, writer);
    }


}
