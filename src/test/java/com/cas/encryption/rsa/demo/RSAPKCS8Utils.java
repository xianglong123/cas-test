package com.cas.encryption.rsa.demo;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created on 2020/10/13
 *
 * @author: huang_yh
 */
public class RSAPKCS8Utils {

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 245;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 256;

    private static final String RSA = "RSA";

    private static final String charset = "UTF-8";

    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     */
    public static PrivateKey loadPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        byte[] decodedKey = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字符串
     */
    public static PublicKey loadPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        byte[] decodedKey = Base64Utils.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * RSA加密
     *
     * @param data 待加密数据
     * @param privateKey 公钥
     */
    public static String encryptByPrivate(PrivateKey privateKey, String data) throws Exception {
        return encrypt(privateKey, data);
    }

    /**
     * RSA加密
     *
     * @param data 待加密数据
     * @param publicKey 公钥
     */
    public static String encryptByPublic(PublicKey publicKey, String data) throws Exception{
        return encrypt(publicKey, data);
    }


    private static String encrypt(Key publicKey, String data) throws Exception {
        if (publicKey == null) {
            throw new Exception("RSA key is null");
        }
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            int inputLen = data.getBytes().length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offset = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offset > 0) {
                if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
                }
                out.write(cache, 0, cache.length);
                i++;
                offset = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            return Base64Utils.encode(encryptedData);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法", e);
        } catch (NoSuchPaddingException e) {
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查", e);
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法", e);
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏", e);
        }
    }

    /**
     * 私钥解密
     * @param privateKey
     * @param data
     * @return
     * @throws Exception
     */
    public static String decryptByPrivate(PrivateKey privateKey, String data) throws Exception {
        return decrypt(privateKey, data);
    }

    /**
     * 公钥解密
     * @param publicKey
     * @param data
     * @return
     * @throws Exception
     */
    public static String decryptByPublic(PublicKey publicKey, String data) throws Exception {
        return decrypt(publicKey, data);
    }
    /**
     * RSA解密
     *
     * @param data 待解密数据
     * @param key 秘钥
     */
    private static String decrypt(Key key, String data) throws Exception {
        if (key == null) {
            throw new Exception("RSA key is null");
        }
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] dataBytes = Base64Utils.decode(data);
            int inputLen = dataBytes.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offset = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offset > 0) {
                if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
                }
                out.write(cache, 0, cache.length);
                i++;
                offset = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData, charset);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法", e);
        } catch (NoSuchPaddingException e) {
            return null;
        } catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查", e);
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法", e);
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏", e);
        }
    }
}