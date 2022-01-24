package com.cas.encryption.sm2;


import org.apache.commons.codec.binary.Base64;

/**
 * Created on 2020/10/13
 *
 * @author: huang_yh
 * BASE64编码解码工具包
 */
public class Base64Utils {

    /** */
    /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;

    /** */
    /**
     * <p>
     * BASE64字符串解码为二进制数据
     * </p>
     *
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decode(String base64) {
        return Base64.decodeBase64(base64);
    }

    /**
     * <p>
     * 二进制数据编码为BASE64字符串
     * </p>
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }
}
