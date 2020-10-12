package com.omniselling.common.util;

import org.apache.commons.codec.digest.DigestUtils;
/**
 * 
 * @author junhu
 *
 */
public class CiphertextUtil
{
    public static enum AlgorithmsName
    {
        MD2, MD5, SHA_1, SHA_256, SHA_384, SHA_512;
    }

    /**
     * 加密字符串
     * 
     * @param sourceStr
     *            需要加密的源字符串
     * @param algorithmsName
     *            算法名称(如:MD2, MD5, SHA_1, SHA_256, SHA_384, SHA_512)
     * @return
     */
    public static String passAlgorithmsCiphering(String sourceStr, AlgorithmsName algorithmsName)
    {
        String password = "";
        switch (algorithmsName)
        {
        case MD2:
            password = DigestUtils.md2Hex(sourceStr);
            break;
        case MD5:
            password = DigestUtils.md5Hex(sourceStr);
            break;
        case SHA_1:
            password = DigestUtils.sha1Hex(sourceStr);
            break;
        case SHA_256:
            password = DigestUtils.sha256Hex(sourceStr);
            break;
        case SHA_384:
            password = DigestUtils.sha384Hex(sourceStr);
            break;
        case SHA_512:
            password = DigestUtils.sha512Hex(sourceStr);
            break;
        }
        return password;
    }
}
