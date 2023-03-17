package com.cas.encryption.sm4;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.cas.des.des3_ecb.HexConverter;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import org.bouncycastle.crypto.macs.CMac;
import org.bouncycastle.crypto.macs.ISO9797Alg3Mac;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.CipherSpi;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;
import java.util.Objects;

/**
 * @description:
 * @author: dsy
 * @date: 2021/2/25 10:56
 */
public class Sm4Util {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final String ENCODING = BaseSmEnum.ENCODING.getMsg();

    public static final String ALGORITHM_NAME = BaseSmEnum.ALGORITHM_NAME.getMsg();
    // 加密算法/分组加密模式/分组填充方式
    // PKCS5Padding-以8个字节为一组进行分组加密
    // 定义分组加密模式使用：PKCS5Padding

    public static final String ALGORITHM_NAME_CBC_PADDING = BaseSmEnum.ALGORITHM_NAME_CBC_PADDING.getMsg();
    public static final String ALGORITHM_NAME_ECB_PADDING = BaseSmEnum.ALGORITHM_NAME_ECB_NO_PADDING.getMsg();
    // 128-32位16进制；256-64位16进制
    public static final int DEFAULT_KEY_SIZE = 128;

    /**
     * 自动生成密钥
     *
     * @return
     * @explain
     */
    public static byte[] generateKey() throws Exception {
        return generateKey(DEFAULT_KEY_SIZE);
    }


    public static String generateKeyString() throws Exception {
        return ByteUtils.toHexString(generateKey());
    }

    /**
     * @param keySize
     * @return
     * @throws Exception
     * @explain
     */
    public static byte[] generateKey(int keySize) throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
        kg.init(keySize, new SecureRandom());
        return kg.generateKey().getEncoded();
    }

    /**
     * sm4加密
     *
     * @param hexKey   16进制密钥（忽略大小写）
     * @param paramStr 待加密字符串
     * @return 返回16进制的加密字符串
     * @throws Exception
     * @explain 加密模式：CBC
     */
    public static String protectMsg(String hexKey, String paramStr) throws Exception {
        String result = "";
        // 16进制字符串-->byte[]
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        // String-->byte[]
        byte[] srcData = paramStr.getBytes(ENCODING);
        // 加密后的数组
        byte[] cipherArray = encrypt_Cbc_Padding(keyData, srcData);

        // byte[]-->hexString
        result = ByteUtils.toHexString(cipherArray);
        return result;
    }

    /**
     * 加密模式之ECB
     */
    public static byte[] encrypt_Ecb_Padding(byte[] key, byte[] data) throws Exception {
        Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * 加密模式之CBC
     */
    public static byte[] encrypt_Cbc_Padding(byte[] key, byte[] data) throws Exception {
        Cipher cipher = generateCbcCipher(ALGORITHM_NAME_CBC_PADDING, Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    private static Cipher generateCbcCipher(String algorithmName, int mode, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
        cipher.init(mode, sm4Key, generateIV());
        return cipher;
    }

    private static Cipher generateEcbCipher(String algorithmName, int mode, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
        cipher.init(mode, sm4Key);
        return cipher;
    }

    //生成iv
    public static AlgorithmParameters generateIV() throws Exception {
        //iv 为一个 16 字节的数组，这里采用和 iOS 端一样的构造方法，数据全为0
        byte[] iv = new byte[16];
        Arrays.fill(iv, (byte) 0x00);
        AlgorithmParameters params = AlgorithmParameters.getInstance(ALGORITHM_NAME);
        params.init(new IvParameterSpec(iv));
        return params;
    }

    /**
     * sm4解密
     *
     * @param hexKey 16进制密钥
     * @param text   16进制的加密字符串（忽略大小写）
     * @return 解密后的字符串
     * @throws Exception
     * @explain 解密模式：采用CBC
     */
    public static String uncoverMsg(String hexKey, String text) throws Exception {
        // 用于接收解密后的字符串
        String result = "";
        // hexString-->byte[]
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        // hexString-->byte[]
        byte[] resultData = ByteUtils.fromHexString(text);
        // 解密
        byte[] srcData = decrypt_Cbc_Padding(keyData, resultData);
        // byte[]-->String
        result = new String(srcData, ENCODING);
        return result;
    }

    /**
     * 解密
     */
    public static byte[] decrypt_Cbc_Padding(byte[] key, byte[] cipherText) throws Exception {
        Cipher cipher = generateCbcCipher(ALGORITHM_NAME_CBC_PADDING, Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherText);
    }

    /**
     * 解密
     */
    public static byte[] decrypt_Ecb_Padding(byte[] key, byte[] cipherText) throws Exception {
        Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherText);
    }


    public static void main(String[] args) throws Exception {
        String key = "31313131313131313131313131313131";
        String data = "31313131313131313131313131313131313131313131313131313131";
        String xorData = "161A99C13AFB72C441A2FAF04B906C85";
        String padding = "00000000000000000000000000000000";
        String dataPadding = "3131313131313131313131313131313131313131313131313131313131313131" + padding;
        System.out.println(HexConverter.byteArray2HexString(encrypt_Ecb_Padding(HexConverter.hexString2ByteArray(key), HexConverter.hexString2ByteArray(data))));
        System.out.println(HexConverter.byteArray2HexString(encrypt_Ecb_Padding(HexConverter.hexString2ByteArray(key), HexConverter.hexString2ByteArray(xorData))));
        System.out.println(HexConverter.byteArray2HexString(encrypt_Ecb_Padding(HexConverter.hexString2ByteArray(key), HexConverter.hexString2ByteArray(dataPadding))));
        System.out.println(HexConverter.byteArray2HexString(encrypt_Ecb_Padding(HexConverter.hexString2ByteArray(key), HexConverter.hexString2ByteArray(padding))));
        System.out.println(HexConverter.byteArray2HexString(encrypt_Cbc_Padding(HexConverter.hexString2ByteArray(key), HexConverter.hexString2ByteArray(data))));
//        test();

        SymmetricCrypto sm4 = SmUtil.sm4(HexUtil.decodeHex(key));
        System.out.println(sm4.encryptHex(HexConverter.hexString2ByteArray(data)));
    }


    private static void test() throws Exception{
        byte[] data = Objects.requireNonNull(HexConverter.hexString2ByteArray("2C1D61049D22939F4A94F22049BA99C395536BE253D14A1669F836ED07800000"));
        SM4Engine engine = new SM4Engine();
        org.bouncycastle.crypto.Mac mac = new CMac(engine, engine.getBlockSize() * 8);
        CipherParameters cipherParameters = new KeyParameter(Objects.requireNonNull(HexConverter.hexString2ByteArray("6FA6C49C159BA1E7BFC2A1D2CAA19528")));
        mac.init(cipherParameters);
        mac.update(data, 0, data.length);
        byte[] result = new byte[mac.getMacSize()];
        mac.doFinal(result, 0);
        System.out.println(HexConverter.byteArray2HexString(result));
    }


    private static void testMac() throws Exception{
        byte[] data = Objects.requireNonNull(HexConverter.hexString2ByteArray("2C1D61049D22939F4A94F22049BA99C395536BE253D14A1669F836ED07800000"));
        Mac mac = Mac.getInstance("SM4-CMAC", BouncyCastleProvider.PROVIDER_NAME);
        Key key = new SecretKeySpec(Objects.requireNonNull(HexConverter.hexString2ByteArray("6FA6C49C159BA1E7BFC2A1D2CAA19528")), ALGORITHM_NAME);
        mac.init(key);
        mac.update(data);
        byte[] bytes = mac.doFinal();
        System.out.println(HexConverter.byteArray2HexString(bytes));
    }


}

