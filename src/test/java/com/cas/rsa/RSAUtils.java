package com.cas.tsm.utils;

import java.io.FileInputStream;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 上午9:29 2021/5/14
 * @version: V1.0
 * @review: RSA非对称加密工具
 * <p>
 * 查看.p12的数据：keytool -list -keystore keyFile.p12 -storepass password -storetype PKCS12
 */
public class RSAUtils {

    public static final String KEY_ALGORITHM = "RSA";
    public static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    public static final String PUBLIC_KEY = "publicKey";
    public static final String PRIVATE_KEY = "privateKey";
    public static final int KEY_SIZE = 2048;
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    public static final String ENCODE_ALGORITHM = "SHA-256";
    public static final String PLAIN_TEXT = "test string";

    /**
     * 生成密钥对
     *
     * @return
     */
    public static Map<String, byte[]> generateKeyBytes() {

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator
                    .getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            Map<String, byte[]> keyMap = new HashMap<String, byte[]>();
            keyMap.put(PUBLIC_KEY, publicKey.getEncoded());
            keyMap.put(PRIVATE_KEY, privateKey.getEncoded());
            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原公钥
     *
     * @param keyBytes
     * @return
     */
    public static PublicKey restorePublicKey(byte[] keyBytes) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            return factory.generatePublic(x509EncodedKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原私钥
     *
     * @param keyBytes
     * @return
     */
    public static PrivateKey restorePrivateKey(byte[] keyBytes) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        try {
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            return factory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    // --------------------------------------------------------------1、分割线------------------------------------------------------------------------------
    /**
     * 根据crt文件获取公钥
     *
     * @param fileName
     */
    public static PublicKey publicCrt(String fileName) {
        PublicKey publicKey = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(new FileInputStream(fileName));
            publicKey = cert.getPublicKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    /**
     * 根据p12文件获取密钥
     *
     * @param fileName
     */
    public static PrivateKey privateCrt(String fileName, String password, String alias) {
        PrivateKey privateKey = null;
        try {
            FileInputStream is = new FileInputStream(fileName);
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            char[] passwd = password.toCharArray();
            keystore.load(is, passwd);
            privateKey = (PrivateKey) keystore.getKey(alias, passwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    // --------------------------------------------------------------2、分割线------------------------------------------------------------------------------

    /**
     * 签名
     *
     * @param privateKey
     *            私钥
     *            明文
     * @return
     */
    public static String sign(PrivateKey privateKey, String message) throws Exception {
        Signature sign = Signature.getInstance("SHA1withRSA");
        sign.initSign(privateKey);
        sign.update(message.getBytes("UTF-8"));
        return new String(Base64.getEncoder().encodeToString(sign.sign()));
    }

    /**
     * 验签
     *
     * @param publicKey
     */
    public static boolean verify(PublicKey publicKey, String message, String signature)throws Exception {
        Signature sign = Signature.getInstance("SHA1withRSA");
        sign.initVerify(publicKey);
        sign.update(message.getBytes("UTF-8"));
        return sign.verify(Base64.getDecoder().decode(signature));
    }


    /**
     * bytes[]换成16进制字符串
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            // 对一位的数据进行 0 补位
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    // --------------------------------------------------------------3、分割线------------------------------------------------------------------------------

    /**
     * String 转 PublicKey
     * @param key
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] keyBytes = decoder.decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * String 转 PrivateKey
     * @param key
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] keyBytes = decoder.decode(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    // --------------------------------------------------------------4、分割线------------------------------------------------------------------------------


    public static void main(String[] args) throws Exception{
        // 1、测试密钥文件获取私钥和公钥
//        testFile();

        // 2、测试java程序生成密钥对
        //testJava();

        // 3、简单测试
        //test();

        // 4、我自己写的测试完整性代码
        testVerifySign();
    }

    private static void testVerifySign() throws Exception {
        String sj = "test";
        //  privateKey:
        //  publicSignKey:

        List<Map<String, String>> list = new ArrayList<>();

        Map<String, String> map = new HashMap<String, String>() {{
            put(PUBLIC_KEY, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCU+BBycGcMDasYBrLH6FZlTKJFSs1G6xX88KRWZAwepNz4MOEQtZOEbdQTun99AabjON2puO9Wb34sXEZ32zPZULgL9Ab+PTqjanyoZmdF44E0aPxo/ohybu2s6vSq33xV3RN/CN37vEf5SY8h60j3H2qrenh3f+0NbPyJ7dqtGwIDAQAB");
            put(PRIVATE_KEY, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJT4EHJwZwwNqxgGssfoVmVMokVKzUbrFfzwpFZkDB6k3Pgw4RC1k4Rt1BO6f30BpuM43am471ZvfixcRnfbM9lQuAv0Bv49OqNqfKhmZ0XjgTRo/Gj+iHJu7azq9KrffFXdE38I3fu8R/lJjyHrSPcfaqt6eHd/7Q1s/Int2q0bAgMBAAECgYAyuIuRC2hqmDTLB2zT1+2irAcMJL3kCaMA7kZmC8Z8oJGEB9B5yfkiO+rblMJXo7pY30HJyefjvC5vmDN+F6p9K7+bwdNUPKyq/pTUzZN+lQjDD9bRumuW/xmIvxZhPZd1EYA2SKcHLjv+xCYlLZGbxQMUAqFVvPM0eUSq38GH8QJBANrdDWf8AIeAr6GzBNgBwIxwCiqypDCv3oKSXzNh0X3HYLEah9dvetTMYIPFNV1vjJ3xm1McctSowkvG0+8W+i8CQQCuPvMO110R+pHit7+o03j9UBRqXgjMrwH70M7SotaVKj8FgGnuqz3SIVd4BFPPme8lIa0Ins6+k2pOjzf5nrzVAkEAnGQEpl8uSaUs2xC+z1NBMZkFysjoBlpFV2wcVuz48zW65BKfKtRgIxr/hGkw3tlM07fHU7YqX8dPPzKOUnRKxQJAJRpMYTWkoMZtOAyOaCGXmsDph/i8APGnB3rf/2QjMyIKx14fsG2QPWVSHcE2I3eQv6RbFwHR3iy/rzi535JYfQJBAK13cnyqVDN0KEbhw5Whg261btM/nkj7m4eXsqD04639+Z/I9lXRHhZWHxRRYwFl++0jgLNCXY+TZy2Uh8ld3cI=");
        }};

        Map<String, String> map1 = new HashMap<String, String>() {{
            put(PUBLIC_KEY, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCDN70NRLHpbemf3wDwBqgVi7oClnERyel6nGXBd6ng8MNMDyWc8DG3QPgbGhbWikN8559rlzXPkowqx2ZqQUfLWcMSZ0YqMh7wdT25uQfjmQJ2t5lZt5ZjsWuJit5mjIyDaQgymBaB3Lnvk+5Z6WFhrkNg0zSVs10JusXwiDhS3wIDAQAB");
            put(PRIVATE_KEY, "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAK6AIkez1lshkXQltwAaqkJl4CgRS6LJyaSVI37YA05hnCoQPTHlk2FlV0mA6miDjoK7h42Vy8sj2rUXv93H8216WrpvJdmWkQg4yaAWjbbOz/61DGKl0DYNZFgBKcZ1La25MthQpSid0dJvQRDKQb3WZD0FRDfwQVXEyfq9ZbANAgMBAAECgYBRnWRrHNWY8e5R4IHi7pXLUFKy6sPxc2d4LrjXTrjdJBIiKk401CdGWluk6UK5dy5kPsl7kyAp9q8IG/+E/0v+lcJHN0skr/RXe38M4Esc5UH6qRjeh+XaG+rcu8WDqQp/aSd0EoDFYJ22xT5l3OO1185hD1KKFgHxHz+P8At7gQJBAOdizmf6uUi+C9O1b+uPJenGFM2RCKNSmdRqxjcLyEovrRaO68fS8XC1joEdNU+C4XsNPP66afszXQ1WjlynZgkCQQDBEDNwjHmkgelA+lPrUflbLSAqVW/8PYSwZdZJLf/qzH02xxdyTC+/isghrYC3xHvY4e8KWZ6UxklrLSQnlprlAkBzP/i1O+H8BIUZjz7O4r8soLgN6BaYWvU6I3DVTC4YHYUmPkvcfQo7bIMtdmHuV699vgtKiYluUJgho6JHLi4ZAkANy6WRVkhe6/WJ8hlLCGmEDV5uB/rfkFnJ7Qz537KYyZHs9x8CyNTkn/sbBPXcq8qZvdNB7xN42A5o/SnHNmjNAkAyLZ/H+hMMQ/ry/SB7NBrbxTmU/YazgWE9nC410JwB9gmtb5Vp5pShh/hkVLsIjQtG5AS3QQvY/l3Kvv+XpAlI");
        }};

        list.add(map);
        list.add(map1);

        for (int i = 0; i < list.size(); i ++) {
            Map<String, String> resMap = list.get(0);
            String publicKey = resMap.get(PUBLIC_KEY);
            String privateKey = resMap.get(PRIVATE_KEY);
            String sign = sign(getPrivateKey(privateKey), sj);
            // 验证正确性
            boolean b = verify(getPublicKey(publicKey), sj, sign);
            System.out.println(String.format("[公钥] %s", publicKey));
            System.out.println(String.format("[私钥] %s", privateKey));
            System.out.println(String.format("[第%s组正确性] %s", i + 1, b));

        }
    }

    /**
     * 简单java生成公私钥对，测试可行性
     */
    private static void test() throws Exception {
        Map<String, byte[]> keyMap = generateKeyBytes();
        PublicKey publicKey = restorePublicKey(keyMap.get(PUBLIC_KEY));
        PrivateKey privateKey = restorePrivateKey(keyMap.get(PRIVATE_KEY));
        // 签名
        String sign = sign(privateKey, PLAIN_TEXT);
        // 验签
        verify(publicKey, PLAIN_TEXT, sign);
    }

    private static void testFile() {
        // 这里会有一个问题就是路径太长或者是这个项目的问题，导致用长的路径会报错，源对应文件在[/Users/xianglong/IdeaProjects/cas-test/src/main/resources/static/**]
        String rootPath = "/Users/xianglong/Desktop/bip/other/";
        Base64.Encoder encoder = Base64.getEncoder();
        System.out.println(encoder.encodeToString(publicCrt(rootPath + "baserelease.crt").getEncoded()));
//        System.out.println(encoder.encodeToString(privateCrt(rootPath + "test.p12", "szsfkey", "test").getEncoded()));
    }

    /**
     * 测试java生成的私公钥对，并打印
     */
    private static void testJava() {
        Map<String, byte[]> stringMap = generateKeyBytes();
        System.out.println(restorePrivateKey(stringMap.get("privateKey")));
        System.out.println("\n\n");
        System.out.println(restorePublicKey(stringMap.get("publicKey")));
    }

}
