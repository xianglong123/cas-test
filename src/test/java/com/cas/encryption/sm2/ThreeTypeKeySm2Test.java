package com.cas.encryption.sm2;

import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.cas.des.des3_ecb.HexConverter;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.util.Base64;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/10/17 2:21 下午
 * @desc 3种公私钥类型
 */
public class ThreeTypeKeySm2Test {

    private static final byte[] DATA = "测试数据123".getBytes();

    public static void main(String[] args) {
        // 生成公钥组
        generateSm2Key();
    }

    public static void generateSm2Key() {
        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();
        System.out.println("======================原始格式=========================");
        System.out.println("公钥 = " + HexConverter.byteArray2HexString(publicKey));
        System.out.println("私钥 = " + HexConverter.byteArray2HexString(privateKey));
        byte[] data = encryDate_ys(DATA, HexConverter.byteArray2HexString(publicKey));
        System.out.println("密文：" + HexConverter.byteArray2HexString(data));
        System.out.println("明文：" + new String(decryData_ys(data, HexConverter.byteArray2HexString(privateKey))));;

        System.out.println("======================bas64格式=========================");
        System.out.println("公钥 = " + Base64.getEncoder().encodeToString(publicKey));
        System.out.println("私钥 = " + Base64.getEncoder().encodeToString(privateKey));
        byte[] data_base64 = encryDate_base64(DATA, Base64.getEncoder().encodeToString(publicKey));
        System.out.println("密文：" + HexConverter.byteArray2HexString(data_base64));
        System.out.println("明文：" + new String(decryData_base64(data_base64, Base64.getEncoder().encodeToString(privateKey))));;

        // 私钥 36 - 68
        byte[] outPrivateKey = new byte[32];
        byte[] outPublicKey = new byte[64];
        System.arraycopy(privateKey, 36, outPrivateKey, 0, 32);
        // 公钥 27 - 91
        System.arraycopy(publicKey, 27, outPublicKey, 0, 64);
        System.out.println("======================简短格式=========================");
        System.out.println("公钥 = " + HexConverter.byteArray2HexString(outPublicKey));
        System.out.println("私钥 = " + HexConverter.byteArray2HexString(outPrivateKey));
        byte[] date_jd = encryDate_jd(DATA, HexConverter.byteArray2HexString(outPublicKey));
        System.out.println("密文：" + HexConverter.byteArray2HexString(date_jd));
        System.out.println("明文：" + new String(decryData_jd(date_jd, HexConverter.byteArray2HexString(outPrivateKey))));
    }

    // --------------------------------- 原始模式加解密方法 ------------------------------------
    public static byte[] encryDate_ys(byte[] data, String publicKeyHex) {
        SM2 sm2 = new SM2(null, publicKeyHex);
        //这里需要手动设置，sm2 对象的默认值与我们期望的不一致 , 使用明文编码
        sm2.usePlainEncoding();
        sm2.setMode(SM2Engine.Mode.C1C3C2);
        return sm2.encrypt(data, KeyType.PublicKey);
    }

    public static byte[] decryData_ys(byte[] data, String privateKeyHex) {
        SM2 sm2 = new SM2(privateKeyHex, null);
        sm2.usePlainEncoding();
        sm2.setMode(SM2Engine.Mode.C1C3C2);
        return sm2.decrypt(data, KeyType.PrivateKey);
    }

    // --------------------------------- base64模式加解密方法 ------------------------------------
    public static byte[] encryDate_base64(byte[] data, String publicKeyBase64) {
        SM2 sm2 = new SM2(null, publicKeyBase64);
        //这里需要手动设置，sm2 对象的默认值与我们期望的不一致 , 使用明文编码
        sm2.usePlainEncoding();
        sm2.setMode(SM2Engine.Mode.C1C3C2);
        return sm2.encrypt(data, KeyType.PublicKey);
    }

    public static byte[] decryData_base64(byte[] data, String privateKeyBase64) {
        SM2 sm2 = new SM2(privateKeyBase64, null);
        sm2.usePlainEncoding();
        sm2.setMode(SM2Engine.Mode.C1C3C2);
        return sm2.decrypt(data, KeyType.PrivateKey);
    }

    // --------------------------------- 简短模式加解密方法 ------------------------------------
    public static byte[] encryDate_jd(byte[] data, String publicKeyHex) {
        //需要加密的明文,得到明文对应的字节数组
        String xhex = publicKeyHex.substring(0, 64);
        String yhex = publicKeyHex.substring(64, 128);
        ECPublicKeyParameters ecPublicKeyParameters = BCUtil.toSm2Params(xhex, yhex);
        //创建sm2 对象
        SM2 sm2 = new SM2(null, ecPublicKeyParameters);
        //这里需要手动设置，sm2 对象的默认值与我们期望的不一致 , 使用明文编码
        sm2.usePlainEncoding();
        sm2.setMode(SM2Engine.Mode.C1C3C2);
        return sm2.encrypt(data, KeyType.PublicKey);
    }

    public static byte[] decryData_jd(byte[] data, String privateKeyHex) {
        ECPrivateKeyParameters privateKeyParameters = BCUtil.toSm2Params(privateKeyHex);
        SM2 sm2 = new SM2(privateKeyParameters, null);
        sm2.usePlainEncoding();
        sm2.setMode(SM2Engine.Mode.C1C3C2);
        return sm2.decrypt(data, KeyType.PrivateKey);
    }


}
