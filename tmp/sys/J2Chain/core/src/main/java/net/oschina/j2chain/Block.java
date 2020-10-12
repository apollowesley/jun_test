package net.oschina.j2chain;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;

/**
 * Block define
 * @author Winter Lau
 */
public class Block implements Serializable {

    private String hash;
    private String prevHash;
    private byte[] data;
    private long timestamp;
    private long nonce;

    public Block(String prevHash, byte[] data) {
        this.prevHash = prevHash;
        this.data = new byte[data.length];
        System.arraycopy(data, 0, this.data, 0 , data.length);
        this.timestamp = System.currentTimeMillis();
        this.hash = hash();
    }

    /**
     * mine block
     * @param difficulty
     */
    public void mine(int difficulty) {
        //Create a string with difficulty * "0"
        String target = new String(new char[difficulty]).replace('\0', '0');
        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = hash();
        }
        //System.out.println("Block Mined!!! : " + hash);
    }

    /**
     * Generate hash for block
     * @return
     */
    private String hash() {
        String hashStr = prevHash + '#' + timestamp + '#' + nonce;
        byte[] hashData = hashStr.getBytes();
        byte[] wholeData = new byte[hashData.length + data.length];
        System.arraycopy(hashData,0,wholeData,0,hashData.length);
        System.arraycopy(data, 0, wholeData, hashData.length, data.length);
        return DigestUtils.sha256Hex(wholeData);
    }

    public String getHash() {
        return hash;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public byte[] getData() {
        return data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("BLOCK[%s -> %s] WITH TIMESTAMP: %d\t DATA: %s", hash, prevHash, timestamp, Hex.encodeHexString(data));
    }

}
