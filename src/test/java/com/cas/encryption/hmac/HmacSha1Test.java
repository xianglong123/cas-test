package com.cas.encryption.hmac;

import com.cas.util.HexConverter;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/11/11 12:04 下午
 * @desc hmacsha1 是需要一个密钥和一个数据, sha1不需要密钥，直接能计算分散数据
 */
public class HmacSha1Test {

    /**
     * hmac+签名算法 加密
     * @param content  内容
     * @param charset  字符编码
     * @param key	         加密秘钥
     * @param hamaAlgorithm hamc签名算法名称:例如HmacMD5,HmacSHA1,HmacSHA256
     * @return
     */
    public static String getHmacSign(String content, String charset,String key,String hamaAlgorithm){
        byte[] result = null;
        try {
            //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            SecretKeySpec signinKey = new SecretKeySpec(key.getBytes(), hamaAlgorithm);
            //生成一个指定 Mac 算法 的 Mac 对象
            Mac mac = Mac.getInstance(hamaAlgorithm);
            //用给定密钥初始化 Mac 对象
            mac.init(signinKey);
            //完成 Mac 操作
            byte[] rawHmac;
            rawHmac = mac.doFinal(content.getBytes(charset));
            result = Base64.encodeBase64(rawHmac);

        } catch (NoSuchAlgorithmException | IllegalStateException | UnsupportedEncodingException | InvalidKeyException e) {
            System.err.println(e.getMessage());
        }
        if (null != result) {
            return HexConverter.byteArray2HexString(result);
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        String hmacSign = getHmacSign("testjoiaj", "UTF-8", "gail", "HmacMD5");
        String hmacSign2 = getHmacSign("testjoiaj", "UTF-8", "gail", "HmacSHA1");
        String hmacSign3 = getHmacSign("testjoiaj", "UTF-8", "gail", "HmacSHA256");
        System.out.println(hmacSign);
        System.out.println(hmacSign2);
        System.out.println(hmacSign3);
    }

}
