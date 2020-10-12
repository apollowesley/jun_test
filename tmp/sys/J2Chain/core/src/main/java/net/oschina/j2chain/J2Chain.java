package net.oschina.j2chain;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * J2Chain entry
 * @author Winter Lau
 */
public class J2Chain {

    public final static int DIFFICULTY = 1;

    private final static String STORAGE = "j2chain.json";

    public static void main(String[] args) throws IOException  {

        var chain = new ArrayList<Block>();

        try (var reader = new FileReader(STORAGE)) {
            chain.addAll(ChainStorage.load(reader));
        } catch ( FileNotFoundException e ) {
        } catch ( IOException e ) {
            System.err.printf("Failed to load chain data from %s, reason: %s\n", STORAGE, e.toString());
            throw e;
        }

        for(int i = 1; i <= 10; i++) {
            var prevBlock = (chain.size()>0)?chain.get(chain.size()-1):null;
            String prevHash = (prevBlock != null)?prevBlock.getHash():null;
            var block = new Block(prevHash, String.valueOf(i).getBytes());
            block.mine(DIFFICULTY);
            chain.add(block);
        }

        chain.forEach( b -> System.out.println(b));

        try (var writer = new FileWriter(STORAGE)) {
            ChainStorage.save(chain, writer);
        }

        System.out.printf("Saved to %s\n", STORAGE);

    }

}
