package com.cas.des;


import com.cas.des.des3_ecb.HexConverter;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * DES加密方式测试
 */
public class DesEncTest {

    private final static String IV_PARAMETER = "12345678";
    //算法名称
    public static final String KEY_ALGORITHM = "DES";
    //算法名称/加密模式/填充方式
    //DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
    public static final String CIPHER_ALGORITHM = "DES/ECB/NoPadding"; //DES/ECB/NoPadding
    /**
     * 默认编码
     */
    private static final String CHARSET = "utf-8";
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
        return keyFactory.generateSecret(desKey);
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
        Key deskey = getKey(key);
        // 实例化Cipher对象，它用于完成实际的加密操作
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

//        SecureRandom random = new SecureRandom();
//        IvParameterSpec random = new IvParameterSpec(IV_PARAMETER.getBytes(CHARSET));
        // 初始化Cipher对象，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] results = cipher.doFinal(data.getBytes());
        // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
        return HexConverter.byteArray2HexString(results);
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

    /**
     * 自定义一个key
     * @param keyRule
     */
    public static Key getKey(String keyRule) {
        byte[] keyByte = keyRule.getBytes();
        // 创建一个空的八位数组,默认情况下为0
        byte[] byteTemp = new byte[8];
        // 将用户指定的规则转换成八位数组
        for (int i = 0; i < byteTemp.length && i < keyByte.length; i++) {
            byteTemp[i] = keyByte[i];
        }
        return new SecretKeySpec(byteTemp, "DES");
    }


    public static void main(String[] args) throws Exception {
        String source = "404142434445464748494A4B4C4D4E4F";
//        System.out.println("加密密钥:" + key);
        System.out.println();
        System.out.println("----------------------先加密再解密------------------------");
        String key = "500001759720F0012050000175970F01";
        System.out.println("原文值: " + source + ", 原文长度:" + source.length());

        String encryptData1 = encrypt(source, key);  // 加密
        System.out.println("加密后: " + encryptData1);

        String encryptData2 = encrypt(encryptData1, key);  // 加密
        System.out.println("加密后: " + encryptData2);

        String encryptData3 = encrypt(encryptData2, key);  // 加密
        System.out.println("加密后: " + encryptData3);

        //示例：
        //根密钥：404142434445464748494A4B4C4D4E4F
        //ICCID：89860120815000017597
        //KVN：20
        //分散因子：
        //ENC：500001759720F0012050000175970F01
        //MAC：500001759720F0022050000175970F02
        //DEK：500001759720F0032050000175970F03
        //初始密钥值：
        //ENC：C072C1E0DDB378AE89DA72D0CBF316D3
        //MAC：A6667FB0D4A760BB638B7A69D0348F50
        //DEK：7F4FA0BA34AAEA0AF9B65FFBA1CBA867



//        String decryptData = decrypt(encryptData, key); // 解密
//        System.out.println("解密后: " + decryptData);
//
//        System.out.println();
//        System.out.println("----------------------先解密再加密------------------------");
//
//        String targetValue = "ZpabQk9jPLc=";
//        System.out.println("解密值: " + targetValue);
//        String targetDecryptData = decrypt(targetValue, key);
//        System.out.println("解密后: " + targetDecryptData + ", 长度:" + targetDecryptData.length());
//        String targetEncryptData = encrypt(targetDecryptData, key);  // 加密
//        System.out.println("加密后: " + targetEncryptData);
    }
}
