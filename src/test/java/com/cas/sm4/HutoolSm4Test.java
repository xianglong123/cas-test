package com.cas.sm4;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/9/7 3:44 下午
 * @desc 对称加密SM4
 */
public class HutoolSm4Test {

    @Test
    public void test2() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {

        Cipher cipher = Cipher.getInstance("SM4/ECB/NoPadding", BouncyCastleProvider.PROVIDER_NAME);

        System.out.println(cipher);

//        Key sm4Key = new SecretKeySpec(key, "SM4");
//        cipher.init(mode, sm4Key);

    }

    @Test
    public void test() {
        String content = "test中文";
        SymmetricCrypto sm4 = SmUtil.sm4();
        String encryptHex = sm4.encryptHex(content);
        String decryptStr = sm4.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println("加密结果： " + encryptHex);
        System.out.println("解密结果： " + decryptStr);
    // bb09c70193dd7ccfaa68fb0eb000b583543085c221d27ae519f1de6a5136aad2
    // 38e6f1389bd3af7a34283b9e95ca1f1e
        // 加密长度随加密数据变动
    }
}
