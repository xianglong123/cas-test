package com.cas.sm2;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.cas.util.HexConverter;
import org.junit.Test;

import java.security.KeyPair;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/9/7 2:58 下午
 * @desc 国密2【SM2】是非对称加密
 */
public class HutoolSm2Test {


    /**
     * 自定义密钥对
     */
    @Test
    public void test4() {
        String content = "我是Hanley";
        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        final SM2 sm2 = new SM2(pair.getPrivate(), pair.getPublic());
        byte[] sign = sm2.sign(content.getBytes());

        // true
        boolean verify = sm2.verify(content.getBytes(), sign);
        System.out.println("验签结果：" + verify);

    }

    /**
     * SM2签名和验签
     */
    @Test
    public void test3() {
        String content = "我是Hanley";
        final SM2 sm2 = SmUtil.sm2();
        String sign = sm2.signHex(HexUtil.encodeHexStr(content));
        boolean verifyHex = sm2.verifyHex(HexUtil.encodeHexStr(content), sign);
        System.out.println("验签结果：" + verifyHex);
    }

    /**
     * 使用自定义密钥对加密或解密
     */
    @Test
    public void test2() {
        String text = "我是一段测试aaaa";
        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();

        System.out.println("私钥： " + HexUtil.encodeHexStr(privateKey));
        System.out.println("公钥： " + HexUtil.encodeHexStr(publicKey));

        System.out.println("---- " + HexConverter.byteArray2HexString(privateKey));
        System.out.println("---- " + HexConverter.byteArray2HexString(publicKey));

        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
        // 公钥加密 私钥解密
        String encryptBcd = sm2.encryptBcd(text, KeyType.PublicKey);
        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encryptBcd, KeyType.PrivateKey));
        // 04EAEAD0D5B529009C01F75BD9964E1FD22E66FE035E59ACDFD779541CF7E2548F9C736B6E11F3B0799430E0964430EA6068A08323D42D46CF2B642E13CD789457F1E02BE5811E4A1ACACC5B0E3F04A6B764197C1170907ECBF5A11875D513DEFA703117A4EE7DF6CB8E9C83EC5A4DFFAB18F89CBC4983
        System.out.println("加密之后的数据： " + encryptBcd);
        // 我是一段测试aaaa
        System.out.println("解密之后的数据： " + decryptStr);
    }

    /**
     * 使用随机生成的密钥对加密或解密
     */
    @Test
    public void test() {
        String text = "我是一段测试aaaa";

        SM2 sm2 = SmUtil.sm2();
        // 公钥加密，私钥解密
        String encrypt = sm2.encryptBcd(text, KeyType.PublicKey);
        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encrypt, KeyType.PrivateKey));
        // 045DB3B15840CFEE985F93AE6FEFB0CACDA63E6BF8DB3B2BF84D4CF3753320453BCAA426492098ADA321361E12454C2E24C1E42988F80827B02E18BBBAEC23EC73A0D064E52D7EF2689202F01AA16AED580F512BD88C7AAF09A6E90548C6CEAA2099C7D4C15A2E107539FF7E3AD954AF8C79EAFE05A980
        System.out.println("加密之后的数据： " + encrypt);
        // 我是一段测试aaaa
        System.out.println("解密之后的数据： " + decryptStr);
    }





}
