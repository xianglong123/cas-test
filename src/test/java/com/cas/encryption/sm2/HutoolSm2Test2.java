package com.cas.encryption.sm2;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.cas.des.des3_ecb.HexConverter;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/9/7 2:58 下午
 * @desc 国密2【SM2】是非对称加密
 */
public class HutoolSm2Test2 {


    /**
     * 汇龙传过来的PID： 043DEFDCC9E2F9A726F729BC04045E93008DC35CCF40E64FDFAAB0E805AEAE3F92DA590463FFAB5E581459F90623526CDE65DB11C8CD387E3523DF64F1E7152F5FD0B1B3CD71F8CD72E76BB95EBC13D246F392C14297417CD30B35059B33DE9776D18336AE31F40F00013AC7181ABCBCEF8A7F4F5B913FAE55A98203D1C31BE559
     * 汇龙传过来的SM2公钥：A9721CB8C91EE248B60CC7C912B403A97903487DE0464F4F9FE797B33F83C732495F815DA6060F1B6A73705ACC37FBF00DC6F103DC81C4ABDC729CB7D4B89034
     * SIM卡读取的PID密文：9E0A4E0F8C8FE292E840F022F40C44F6075BD9D1F9AAC64EA2A0AEE7E571490F54EDD0576342AC441AC53D3DAD9A12EC9BC352655BCC7A68A81060CF0258D24B17A00BC75F2DD3671FFD185A732363502DB964926812A3B50F502C210942420F9352C405350766ECCF38ADA0767085336B8C2B65E3D40D841E223C12F0D4C4595676D863BC616425F45DBBB75F325EE76270FFF20B8E909A0EDB4A15AAD6903AE14197BB8A10439EE8B78798E98B2B5231AE8472C29CE75F02108DA811A173D3CF84645E55CAC0F4BE08127026D7EC5199291F7EA9718FAA611B8C0B8EBC831FC922FA08C1002E8A482BBB
     * SIM读取数据解密明文【PID + 10字节计数器】：043defdcc9e2f9a726f729bc04045e93008dc35ccf40e64fdfaab0e805aeae3f92da590463ffab5e581459f90623526cde65db11c8cd387e3523df64f1e7152f5fd0b1b3cd71f8cd72e76bb95ebc13d246f392c14297417cd30b35059b33de9776d18336ae31f40f00013ac7181abcbcef8a7f4f5b913fae55a98203d1c31be55900000000000000000005
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void signTest2() throws UnsupportedEncodingException {
        //指定的私钥
        String privateKeyHex = "ECA65A95757D3C97ACBBBD4034DFE8B0EEB9819A9F3A77EA0CB9DB870C55E38E";
        String publicKeyHex = "A9721CB8C91EE248B60CC7C912B403A97903487DE0464F4F9FE797B33F83C732495F815DA6060F1B6A73705ACC37FBF00DC6F103DC81C4ABDC729CB7D4B89034";
        // 要添加解密标示04，读取出来的数据不带"04"【加标示加标示加标示，重要的事情说三遍】
        String data = "04" + "9E0A4E0F8C8FE292E840F022F40C44F6075BD9D1F9AAC64EA2A0AEE7E571490F54EDD0576342AC441AC53D3DAD9A12EC9BC352655BCC7A68A81060CF0258D24B17A00BC75F2DD3671FFD185A732363502DB964926812A3B50F502C210942420F9352C405350766ECCF38ADA0767085336B8C2B65E3D40D841E223C12F0D4C4595676D863BC616425F45DBBB75F325EE76270FFF20B8E909A0EDB4A15AAD6903AE14197BB8A10439EE8B78798E98B2B5231AE8472C29CE75F02108DA811A173D3CF84645E55CAC0F4BE08127026D7EC5199291F7EA9718FAA611B8C0B8EBC831FC922FA08C1002E8A482BBB";
        ECPrivateKeyParameters privateKeyParameters = BCUtil.toSm2Params(privateKeyHex);
        String xhex = publicKeyHex.substring(0, 64);
        String yhex = publicKeyHex.substring(64, 128);
        ECPublicKeyParameters ecPublicKeyParameters = BCUtil.toSm2Params(xhex, yhex);
        //创建sm2 对象
        SM2 sm2 = new SM2(privateKeyParameters, ecPublicKeyParameters);
        //这里需要手动设置，sm2 对象的默认值与我们期望的不一致 , 使用明文编码
        sm2.usePlainEncoding();
        byte[] decrypt = sm2.decrypt(HexUtil.decodeHex(data), KeyType.PrivateKey);
        System.out.println("解密数据: " + data);
        String hexStr = HexUtil.encodeHexStr(decrypt);
        System.out.println("解密明文: " + hexStr);
        System.out.println("PID密文: " + hexStr.substring(0, hexStr.length() - 20));
        System.out.println("计数器: " + hexStr.substring(hexStr.length() - 20));
    }

    @Test
    public void signTest() throws UnsupportedEncodingException {
        //指定的私钥
        String privateKeyHex = "ECA65A95757D3C97ACBBBD4034DFE8B0EEB9819A9F3A77EA0CB9DB870C55E38E";
        String publicKeyHex = "A9721CB8C91EE248B60CC7C912B403A97903487DE0464F4F9FE797B33F83C732495F815DA6060F1B6A73705ACC37FBF00DC6F103DC81C4ABDC729CB7D4B89034";
        // 要添加解密标示04，读取出来的数据不带"04"【加标示加标示加标示，重要的事情说三遍】
        String data = "111111111111111122222222222222223333333333333333444444444444444400000000000000000001";
        ECPrivateKeyParameters privateKeyParameters = BCUtil.toSm2Params(privateKeyHex);
        String xhex = publicKeyHex.substring(0, 64);
        String yhex = publicKeyHex.substring(64, 128);
        ECPublicKeyParameters ecPublicKeyParameters = BCUtil.toSm2Params(xhex, yhex);
        //创建sm2 对象
        SM2 sm2 = new SM2(privateKeyParameters, ecPublicKeyParameters);
        //这里需要手动设置，sm2 对象的默认值与我们期望的不一致 , 使用明文编码
        sm2.usePlainEncoding();
        for (int i = 0; i < 100; i++) {
            byte[] encrypt = sm2.encrypt(HexUtil.decodeHex(data), KeyType.PublicKey);
//        byte[] decrypt = sm2.decrypt(encrypt, KeyType.PrivateKey);
            // 84 + 192 = 276
            System.out.println("原数据长度:" + data.length() + "加密数据长度" + HexConverter.byteArray2HexString(encrypt).length());

        }
    }


    /**
     * 密钥生成流程,pkcs#8格式私钥pem文件：
     *
     * 1 生成sm2私钥: openssl ecparam -genkey -name SM2 -out sm2PriKey.pem
     * 2 sm2私钥导出公钥: openssl ec -in sm2PriKey.pem -pubout -out sm2PubKey.pem
     * 3 查看私钥: openssl ec -in sm2PriKey.pem -text
     * 4 私钥pkcs#1转pkcs#8: openssl pkcs8 -topk8 -inform PEM -in sm2PriKey.pem -outform pem -nocrypt -out sm2PriKeyPkcs8.pem
     */
    @Test
    public void pu() throws UnsupportedEncodingException {
        String privateKe = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgWQRf/UMRed26VOm23gWZcbfCeoYTeOChEfjzTApPEGWgCgYIKoEcz1UBgi2hRANCAASYfDrZBZ9xp7dkT+Uwn5is1JISMdE6dDb3FfrcWy8lHc6aDj1KeOJZSI1y36f35D3uOUBcK/OErNyAOetAlQeZ";
        String publicKe = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEmHw62QWfcae3ZE/lMJ+YrNSSEjHROnQ29xX63FsvJR3Omg49SnjiWUiNct+n9+Q97jlAXCvzhKzcgDnrQJUHmQ==";
        byte[] privateKey = Base64Utils.decode(privateKe);
        byte[] publicKey = Base64Utils.decode(publicKe);
        SM2 sm2 = SmUtil.sm2(privateKey,publicKey);
         String encryptStr = sm2.encryptBase64("F50690B52636C58899FCF7E0043B6EF2", KeyType.PublicKey);
         sm2.setMode(SM2Engine.Mode.C1C3C2);
        String decryptStr = StrUtil.utf8Str(sm2.decrypt(encryptStr, KeyType.PrivateKey));
        System.out.println("加密：" + encryptStr);
        System.out.println("解密：" + decryptStr);
    }


    @Test
    public void pu_old() {
        String privateKe = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgWQRf/UMRed26VOm23gWZcbfCeoYTeOChEfjzTApPEGWgCgYIKoEcz1UBgi2hRANCAASYfDrZBZ9xp7dkT+Uwn5is1JISMdE6dDb3FfrcWy8lHc6aDj1KeOJZSI1y36f35D3uOUBcK/OErNyAOetAlQeZ";
        String publicKe = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEmHw62QWfcae3ZE/lMJ+YrNSSEjHROnQ29xX63FsvJR3Omg49SnjiWUiNct+n9+Q97jlAXCvzhKzcgDnrQJUHmQ==";
        byte[] privateKey = Base64Utils.decode(privateKe);
        byte[] publicKey = Base64Utils.decode(publicKe);
        SM2 sm2 = SmUtil.sm2(privateKey,publicKey);
        sm2.setMode(SM2Engine.Mode.C1C2C3);
//        String decryptStr = StrUtil.utf8Str(sm2.decrypt("BPq5Hdhu0d1T9X++yUD26KZaTpgr23FmMjBHv8UTebwK/oF6b1mu47L40D2WdhjxsVz4Q1EIYio3z3l/cvX7ujVpdcAuDi5Mp4xY3fu/WALyIHJ9o/XWGSKrdLZ3IHq8RTB3x7d90rt+MvxMWLr1EDYZmqaNSpVjAWF8dv+Qc+F2", KeyType.PrivateKey));
        String decryptStr = StrUtil.utf8Str(sm2.decrypt("BJ6nht1jqQ7yytnnPEQR/FPoCGDRcCLf/kWBJM9FBDKyTrAFT9rAkx4UE0qTA+9feoFNzX2MpnJ+oVMGXwKXoHihqAWoMOx4Nb2Iwnmry/9dioNRnPp8+ecwR0YwKc21pQKFPWxNkEsg2Y2FDjXRQU1NBanHVb5BHhuMlXwa5brO", KeyType.PrivateKey));
        System.out.println("解密：" + decryptStr);
    }


}
