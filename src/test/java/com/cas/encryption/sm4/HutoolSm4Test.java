package com.cas.encryption.sm4;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.cas.BaseTest;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/9/7 3:44 下午
 * @desc 对称加密SM4
 */
public class HutoolSm4Test extends BaseTest {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    @Test
    public void test4() {
        String testStr = "test中文";

        // 此处密钥如果有非ASCII字符，考虑编码
        byte[] key = "password".getBytes();
        HMac mac = new HMac(HmacAlgorithm.HmacMD5, key);

        // b977f4b13f93f549e06140971bded384
        String macHex1 = mac.digestHex(testStr);
        System.out.println(macHex1);

        boolean verify = mac.verify(HexUtil.decodeHex(macHex1), HexUtil.decodeHex("b977f4b13f93f549e06140971bded384"));
        System.out.println(verify);
    }

    @Test
    public void test3() {
        String data = "97B3853BAF1B04271BC1AA7600000000011282012843376949704A2B47507948736D3651324A586F347563696A4843624C6E41614B396E3455646F452F4B4449364D4441774D4445784D4455794D4449784D4463784E5449774D6A49774D5445314D5145414141454271526F784536614630662F6E373051452B4C52504A6B6F6C755670786E474E7934436F6646542B3858794F332F735032746941535946517359466F4B302F6462464448714338745149744C35436F326A523846594666496D50562F6239437837354456534B426E516C45553551305647516A67314D455543495144773544344A766D647A646B2B6F4A49727472384C695745704164795749715767316531775733583030347749674B73376A6C56646849592F684C38752F636E655752703057325977364F364463366A6D2F534764324A3530411301001401001501001601001740383638334139444545373033434535413041303646304238323536323836304445334646444246414441463544353436453634453939353143464230423231468000000000";
        String iv = "00000000000000000000000000000000";
        byte[] key = HexUtil.decodeHex("E0E1E2E3E4E5E6E7E8E9EAEBECEDEEEF");
        SM4Engine engine = new SM4Engine();
        Mac mac = new CBCBlockCipherMac(engine, engine.getBlockSize() * 8, null);
        CipherParameters cipherParameters = new KeyParameter(key);
        mac.init(new ParametersWithIV(cipherParameters, HexUtil.decodeHex(iv)));
        mac.update(HexUtil.decodeHex(data), 0, data.length());
        byte[] result = new byte[mac.getMacSize()];
        mac.doFinal(result, 0);
        System.out.println(HexUtil.encodeHexStr(result));
    }

    @Test
    public void test2() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("SM4/ECB/NoPadding", BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(HexUtil.decodeHex("9479ACD513F09ECA90395A1E1A15747E"), "SM4");
        cipher.init(Cipher.ENCRYPT_MODE, sm4Key);
        byte[] bytes = cipher.doFinal(HexUtil.decodeHex("11111111111111111111111111111111"));
        System.out.println(HexUtil.encodeHexStr(bytes));
        // 6D7620BDFC47FEE1EC523393339CE8D3
        // 6d7620bdfc47fee1ec523393339ce8d3
    }

    @Test
    public void test() {
        String content = "9479ACD513F09ECA90395A1E1A15747E";
        SymmetricCrypto sm4 = SmUtil.sm4(HexUtil.decodeHex("11111111111111111111111111111111"));
        String encryptHex = sm4.encryptHex(content);
        String decryptStr = sm4.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println("加密结果： " + encryptHex);
        System.out.println("解密结果： " + decryptStr);
    // bb09c70193dd7ccfaa68fb0eb000b583543085c221d27ae519f1de6a5136aad2
    // 38e6f1389bd3af7a34283b9e95ca1f1e
        // 加密长度随加密数据变动
    }
}
