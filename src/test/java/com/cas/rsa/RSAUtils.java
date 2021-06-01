package com.cas.rsa;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

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
     * @param plain_text
     *            明文
     * @return
     */
    public static byte[] sign(PrivateKey privateKey, String plain_text) {
        MessageDigest messageDigest;
        byte[] signed = null;
        try {
            messageDigest = MessageDigest.getInstance(ENCODE_ALGORITHM);
            messageDigest.update(plain_text.getBytes());
            byte[] outputDigest_sign = messageDigest.digest();
            System.out.println("SHA-256加密后-----》" +bytesToHexString(outputDigest_sign));
            Signature Sign = Signature.getInstance(SIGNATURE_ALGORITHM);
            Sign.initSign(privateKey);
            Sign.update(outputDigest_sign);
            signed = Sign.sign();
            System.out.println("SHA256withRSA签名后-----》" + bytesToHexString(signed));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return signed;
    }

    /**
     * 验签
     *
     * @param publicKey
     *            公钥
     * @param plain_text
     *            明文
     * @param signed
     *            签名
     */
    public static boolean verifySign(PublicKey publicKey, String plain_text, byte[] signed) {

        MessageDigest messageDigest;
        boolean SignedSuccess=false;
        try {
            messageDigest = MessageDigest.getInstance(ENCODE_ALGORITHM);
            messageDigest.update(plain_text.getBytes());
            byte[] outputDigest_verify = messageDigest.digest();
            System.out.println("SHA-256加密后-----》" +bytesToHexString(outputDigest_verify));
            Signature verifySign = Signature.getInstance(SIGNATURE_ALGORITHM);
            verifySign.initVerify(publicKey);
            verifySign.update(outputDigest_verify);
            SignedSuccess = verifySign.verify(signed);
            System.out.println("验证成功？---" + SignedSuccess);

        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return SignedSuccess;
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

    public static void main(String[] args) {
        // 测试密钥文件获取私钥和公钥
        testFile();

        // 测试java程序生成密钥对
        testJava();

        //
        test();
    }

    /**
     * 简单java生成公私钥对，测试可行性
     */
    private static void test() {
        Map<String, byte[]> keyMap = generateKeyBytes();
        PublicKey publicKey = restorePublicKey(keyMap.get(PUBLIC_KEY));
        PrivateKey privateKey = restorePrivateKey(keyMap.get(PRIVATE_KEY));
        // 签名
        byte[] sing_byte = sign(privateKey, PLAIN_TEXT);
        // 验签
        verifySign(publicKey, PLAIN_TEXT, sing_byte);
    }

    private static void testFile() {
        // 这里会有一个问题就是路径太长或者是这个项目的问题，导致用长的路径会报错，源对应文件在[/Users/xianglong/IdeaProjects/cas-test/src/main/resources/static/**]
        String rootPath = "/Users/xianglong/Desktop/";
        Base64.Encoder encoder = Base64.getEncoder();
        System.out.println(encoder.encodeToString(publicCrt(rootPath + "test.crt").getEncoded()));
        System.out.println(encoder.encodeToString(privateCrt(rootPath + "test.p12", "szsfkey", "test").getEncoded()));
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
