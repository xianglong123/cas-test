package com.cas.des;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * DES加密方式测试
 */
public class DesEncTest {
    //算法名称
    public static final String KEY_ALGORITHM = "DES";
    //算法名称/加密模式/填充方式
    //DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
    public static final String CIPHER_ALGORITHM = "DES/ECB/NoPadding"; //DES/ECB/NoPadding
    /**
     NoPadding
     API或算法本身不对数据进行处理，加密数据由加密双方约定填补算法。例如若对字符串数据进行加解密，可以补充\0或者空格，然后trim
     PKCS5Padding
     加密前：数据字节长度对8取余，余数为m，若m>0,则补足8-m个字节，字节数值为8-m，即差几个字节就补几个字节，字节数值即为补充的字节数，若为0则补充8个字节的8
     */

    /**
     * 生成密钥key对象
     *
     * @param keyStr 密钥字符串
     * @return 密钥对象
     * @throws Exception
     */
    private static SecretKey keyGenerator(String keyStr) throws Exception {
        DESKeySpec desKey = new DESKeySpec(keyStr.getBytes());
        //创建一个密匙工厂，然后用它把DESKeySpec转换成
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(desKey);
        return securekey;
    }

    // 字节数组转换十六进制字符串
    public static String hexStringView(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTmp;
        for (int i = 0; i < bytes.length; i++) {
            sTmp = Integer.toHexString(0xFF & bytes[i]);
            if (sTmp.length() < 2)
                sb.append(0);
            sb.append(sTmp.toUpperCase()).append(" ");
        }
        System.out.println("16进制:" + sb.toString());
        return sb.toString();
    }

    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密后的数据
     */
    public static String encrypt(String data, String key) throws Exception {
        Key deskey = keyGenerator(key);
        // 实例化Cipher对象，它用于完成实际的加密操作
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        SecureRandom random = new SecureRandom();
        //IvParameterSpec random = new IvParameterSpec(key.getBytes());
        // 初始化Cipher对象，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, deskey, random);
        // 加密字节
        byte[] bytes = data.getBytes();
        // 加密字节16进制查看
        hexStringView(bytes);

        byte[] results = cipher.doFinal(bytes);
        // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
        return Base64.encodeBase64String(results);
    }

    /**
     * 解密数据
     *
     * @param data 待解密数据
     *             * @param key 密钥
     *             * @return 解密后的数据
     */
    public static String decrypt(String data, String key) throws Exception {
        Key deskey = keyGenerator(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化Cipher对象，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        // 解密字节-执行解密操作
        byte[] bytes = cipher.doFinal(Base64.decodeBase64(data));
        // 解密字节16进制查看
        // hexStringView(bytes);
        return new String(bytes);
    }

    public static void main(String[] args) throws Exception {
        String key = "19180628";
        System.out.println("加密密钥:" + key);
        System.out.println();
        System.out.println("----------------------先加密再解密------------------------");
        byte[] nul_bytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        String source = "1" + new String(nul_bytes);
        System.out.println("原文值: " + source + ", 原文长度:" + source.length());

        String encryptData = encrypt(source, key);  // 加密
        System.out.println("加密后: " + encryptData);
        String decryptData = decrypt(encryptData, key); // 解密
        System.out.println("解密后: " + decryptData);

        System.out.println();
        System.out.println("----------------------先解密再加密------------------------");

        String targetValue = "ZpabQk9jPLc=";
        System.out.println("解密值: " + targetValue);
        String targetDecryptData = decrypt(targetValue, key);
        System.out.println("解密后: " + targetDecryptData + ", 长度:" + targetDecryptData.length());
        String targetEncryptData = encrypt(targetDecryptData, key);  // 加密
        System.out.println("加密后: " + targetEncryptData);
    }
}
