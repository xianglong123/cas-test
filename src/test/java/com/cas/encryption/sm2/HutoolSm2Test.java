package com.cas.encryption.sm2;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.cas.util.HexConverter;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.ec.GMCipherSpi;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.crypto.gmss.util.GMSSUtil;
import org.junit.Test;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

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
        System.out.println("sign= " + sign);
        System.out.println("验签结果：" + verifyHex);
    }

    /**
     * SM2签名和验签
     */
    @Test
    public void test33() {
        String content = "你好";
        String privateKey = "ECA65A95757D3C97ACBBBD4034DFE8B0EEB9819A9F3A77EA0CB9DB870C55E38E";
        String publicKey = "A9721CB8C91EE248B60CC7C912B403A97903487DE0464F4F9FE797B33F83C732495F815DA6060F1B6A73705ACC37FBF00DC6F103DC81C4ABDC729CB7D4B89034";
        final SM2 sm2 = SmUtil.sm2(HexConverter.hexString2ByteArray(privateKey), HexConverter.hexString2ByteArray(publicKey));
        byte[] sign = sm2.sign(content.getBytes());
        boolean verifyHex = sm2.verify(content.getBytes(), sign);
        System.out.println("签名: " + HexConverter.byteArray2HexString(sign) +" -- 验签结果：" + verifyHex);
    }

    /**
     * 指定私钥签名测试
     * <i scr="https://i.goto327.top/CryptTools/SM2.aspx?tdsourcetag=s_pctim_aiomsg">秘钥验证</i>
     *
     * #32323232323232323232323232323232
     * #3082012A3081D0A003020102021001008631310010866000000000000200300A06082A811CCF55018375302E310B300906035504061302434E310E300C060355040A0C05484E434143310F300D06035504030C06524F4F544341301E170D3230313231383130323235365A170D3330313231383037313535355A30003059301306072A8648CE3D020106082A811CCF5501822D03420004A9721CB8C91EE248B60CC7C912B403A97903487DE0464F4F9FE797B33F83C732495F815DA6060F1B6A73705ACC37FBF00DC6F103DC81C4ABDC729CB7D4B89034300A06082A811CCF55018375034900304602210089C7758D7D3CBD0F0703D14A42B9A91BC783660915CE2BF30D6D429CD4FB6DA9022100BD72E92B15B0060F2B84950CA153C958132769D5734EE9A1E5B069A1F070764F
     * #4F013E491A34B904F786336C931246A2788C5DB59D2E4EC8D081D2E8166BFB3ADA9D93860AB9F959230F7BD7A7061B5EB1F0CC37046099054D51FBE39B9E7181
     *  9fd0c8eaa1f5cb92065b7501ad1e3957b886905f6c7c6414df43961edca2fd0d3a9a33998b8c97afcb08cf24667392d4393cdc16d3051221102577d8035e78c1
     *
     *
     *
     * 303439353530433543393946353933453041433537394239433642344136393130303430324343313639434638303138383246433444343143433943314641413943344132393137344534393435303941343046334239324439414246454243333541373031414143453835334434463437393845363438313138393630393542463836393033424435394439424138443536453243393341314330334230453438433044303732323445343641383742334345433134464337323235384532384232443230424234304432373138414642443941363033343435423335393646313738424344304646383737364135373541303736414645324338323937464245
     */
    @Test
    public void signTest() {
        //指定的私钥
        String privateKeyHex = "ECA65A95757D3C97ACBBBD4034DFE8B0EEB9819A9F3A77EA0CB9DB870C55E38E";
        String publicKeyHex = "A9721CB8C91EE248B60CC7C912B403A97903487DE0464F4F9FE797B33F83C732495F815DA6060F1B6A73705ACC37FBF00DC6F103DC81C4ABDC729CB7D4B89034";
        //需要加密的明文,得到明文对应的字节数组
        String data = "31313131313131313131313131313131";
        byte[] dataBytes = HexConverter.hexString2ByteArray(data);
        ECPrivateKeyParameters privateKeyParameters = BCUtil.toSm2Params(privateKeyHex);
        String xhex = publicKeyHex.substring(0, 64);
        String yhex = publicKeyHex.substring(64, 128);
        ECPublicKeyParameters ecPublicKeyParameters = BCUtil.toSm2Params(xhex, yhex);
        //创建sm2 对象
        SM2 sm2 = new SM2(privateKeyParameters, ecPublicKeyParameters);
        //这里需要手动设置，sm2 对象的默认值与我们期望的不一致 , 使用明文编码
        sm2.usePlainEncoding();
        sm2.setMode(SM2Engine.Mode.C1C2C3);
//        byte[] encrypt = sm2.encrypt(dataBytes, KeyType.PublicKey);
//        byte[] encrypt = HexConverter.hexString2ByteArray("0C5ADD8755EED8A55B7A1A01315316EB5EB86D2E68243CE22B3501362FEDD62C562665703E41BF77F97B4ACDC80A24647B7B696C335FCD875915FD4F7C3B82EE74430181902961797D3D4986DA26C8DA3757DC9E4655369290CA456FBCBCE81CC57B4C972C2406FC9ACDFD1E186FC775");
        byte[] encrypt = HexConverter.hexString2ByteArray("045bce594725149735f24b8efb0c6aa046469d63c16c49b9f2bbc3deaf3c8f940df3f9455bc12b80f40745b7783954c3a4e820355671ce0073ddb8af4e579252574a741ecb2f7ee9520933644925b6cc9f3c7f011acfe668c6e8c3178c142c9fe820684062c36eb014ec1761527e2baa32");
        byte[] decryptStr = sm2.decrypt(encrypt, KeyType.PrivateKey);
        System.out.println("加密: " + HexUtil.encodeHexStr(encrypt));
        System.out.println("解密: " + HexConverter.byteArray2HexString(decryptStr));
        System.out.println("解密: " + decryptStr);
//        System.out.println("数据: " + HexUtil.encodeHexStr(dataBytes));
//        System.out.println("签名: " + HexUtil.encodeHexStr(sign));
    }

    @Test
    public void signTest2() throws UnsupportedEncodingException {
        //指定的私钥
        String privateKeyHex = "ECA65A95757D3C97ACBBBD4034DFE8B0EEB9819A9F3A77EA0CB9DB870C55E38E";
        String publicKeyHex = "A9721CB8C91EE248B60CC7C912B403A97903487DE0464F4F9FE797B33F83C732495F815DA6060F1B6A73705ACC37FBF00DC6F103DC81C4ABDC729CB7D4B89034";
        String data = "0444224B6870B3FAD09F66557245A5B6BDBA9E4A266B99FB022A7757E222A990A2467213DCCEAC66A0E9CF7F16B2E335722AC09D0BD7C409B4187916480B73558DDF112B470DE7BB19D586802063EA20351FFD2ACD19FE194997D55D1499E8B6EE9E48660D1869C7ADEC80EBB112A99BB8";
//        String data = "31313131313131313131313131313131";
        byte[] dataBytes = data.getBytes();
        ECPrivateKeyParameters privateKeyParameters = BCUtil.toSm2Params(privateKeyHex);
        String xhex = publicKeyHex.substring(0, 64);
        String yhex = publicKeyHex.substring(64, 128);
        ECPublicKeyParameters ecPublicKeyParameters = BCUtil.toSm2Params(xhex, yhex);
        //创建sm2 对象
        SM2 sm2 = new SM2(privateKeyParameters, ecPublicKeyParameters);
        //这里需要手动设置，sm2 对象的默认值与我们期望的不一致 , 使用明文编码
        sm2.usePlainEncoding();
        sm2.setMode(SM2Engine.Mode.C1C3C2);
//        byte[] encrypt = sm2.encrypt(dataBytes, KeyType.PublicKey);
//        byte[] decrypt = sm2.decrypt(encrypt, KeyType.PrivateKey);
        byte[] decrypt = sm2.decrypt(HexUtil.decodeHex(data), KeyType.PrivateKey);
        System.out.println("加密: " + data);
        System.out.println("解密: " + HexUtil.encodeHexStr(decrypt));
    }

    /**
     * 指定私钥签名测试
     * <i scr="https://i.goto327.top/CryptTools/SM2.aspx?tdsourcetag=s_pctim_aiomsg">秘钥验证</i>
     */
    @Test
    public void verifyTest() {
        //指定的公钥
        String publicKeyHex = "A9721CB8C91EE248B60CC7C912B403A97903487DE0464F4F9FE797B33F83C732495F815DA6060F1B6A73705ACC37FBF00DC6F103DC81C4ABDC729CB7D4B89034";
        //需要加密的明文,得到明文对应的字节数组
        byte[] dataBytes = "323232323232323232323232323232323082012A3081D0A003020102021001008631310010866000000000000200300A06082A811CCF55018375302E310B300906035504061302434E310E300C060355040A0C05484E434143310F300D06035504030C06524F4F544341301E170D3230313231383130323235365A170D3330313231383037313535355A30003059301306072A8648CE3D020106082A811CCF5501822D03420004A9721CB8C91EE248B60CC7C912B403A97903487DE0464F4F9FE797B33F83C732495F815DA6060F1B6A73705ACC37FBF00DC6F103DC81C4ABDC729CB7D4B89034300A06082A811CCF55018375034900304602210089C7758D7D3CBD0F0703D14A42B9A91BC783660915CE2BF30D6D429CD4FB6DA9022100BD72E92B15B0060F2B84950CA153C958132769D5734EE9A1E5B069A1F070764F".getBytes();
        //签名值
//        String signHex = "4F013E491A34B904F786336C931246A2788C5DB59D2E4EC8D081D2E8166BFB3ADA9D93860AB9F959230F7BD7A7061B5EB1F0CC37046099054D51FBE39B9E7181";
        String signHex = "7a93c482be3a1da007d34ebd5373d75e9f31ec40e79892928db2c4fff4307126593ec07faa340b4c39ff2200b20f9d979c9874c0e2b0fe73a6e0100cd651a42e";
        //这里需要根据公钥的长度进行加工
        if (publicKeyHex.length() == 130) {
            //这里需要去掉开始第一个字节 第一个字节表示标记
            publicKeyHex = publicKeyHex.substring(2);
        }
        String xhex = publicKeyHex.substring(0, 64);
        String yhex = publicKeyHex.substring(64, 128);
        ECPublicKeyParameters ecPublicKeyParameters = BCUtil.toSm2Params(xhex, yhex);
        //创建sm2 对象
        SM2 sm2 = new SM2(null, ecPublicKeyParameters);
        //这里需要手动设置，sm2 对象的默认值与我们期望的不一致 , 使用明文编码
        sm2.usePlainEncoding();
        sm2.setMode(SM2Engine.Mode.C1C2C3);
        boolean verify = sm2.verify(dataBytes, HexUtil.decodeHex(signHex));
        System.out.println("数据: " + HexUtil.encodeHexStr(dataBytes));
        System.out.println("验签结果: " + verify);
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
     * 使用自定义密钥对加密或解密
     */
    @Test
    public void test22() {
        String text = "我是一段测试aaaa";
        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKey = HexConverter.hexString2ByteArray("308193020100301306072a8648ce3d020106082a811ccf5501822d047930770201010420e435d2097d0bdb91310380a35a24d215d18b5a8f3ac8f144dedb9e2bfdeaaca9a00a06082a811ccf5501822da1440342000456ee688a4205d1003485fa486add1d058ea245f536c72fb74f395990dca33f72a32e73445eee8a8ade4798068d1623fc5d94e0305b9f96130c0567eb8f1b34b9");
        byte[] publicKey = HexConverter.hexString2ByteArray("3059301306072a8648ce3d020106082a811ccf5501822d0342000456ee688a4205d1003485fa486add1d058ea245f536c72fb74f395990dca33f72a32e73445eee8a8ade4798068d1623fc5d94e0305b9f96130c0567eb8f1b34b9");

        System.out.println("---- " + HexConverter.byteArray2HexString(privateKey));
        System.out.println("---- " + HexConverter.byteArray2HexString(publicKey));

        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
        byte[] sign = sm2.sign(text.getBytes());
        boolean verify = sm2.verify(text.getBytes(), sign);
        // 3044 02204F82EF6C6E036C91AB74D1203659C9BC4DE17E989E5C1E9D77F18D80ADDE16C302203B8792A64B712DE279CFEA63029C30F3FF2F3DF6194E71AC224EBD6972D04A89
        // 3044 02204A8B28377ACC24E6A19F9D1422B4F655FE7241889641DA3F0F185EFFCA7ED66402205D60BEE9C49E4B4BC29694333A0C6B9E191CBDBAF66E91A16774521D7A13951C
        // 3045 0220636DF69EC2D4B14E03FBD225044FAFAFA0413AAA5FADD707BEA55A98F54BCE8202210098C6796ED6D50158424FF52CB3D6B44B0C69636C85D9BCA767FC46B18077B854
        System.out.println(HexConverter.byteArray2HexString(sign));
        System.out.println("验证结果: " + verify);
    }

    /**
     * 使用随机生成的密钥对加密或解密
     */
    @Test
    public void test() {
        String text = "123456789"; // 106 - 96 = 10

        SM2 sm2 = SmUtil.sm2();
        // 公钥加密，私钥解密
        String encrypt = sm2.encryptBcd(text, KeyType.PublicKey);
        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encrypt, KeyType.PrivateKey));
        // 045DB3B15840CFEE985F93AE6FEFB0CACDA63E6BF8DB3B2BF84D4CF3753320453BCAA426492098ADA321361E12454C2E24C1E42988F80827B02E18BBBAEC23EC73A0D064E52D7EF2689202F01AA16AED580F512BD88C7AAF09A6E90548C6CEAA2099C7D4C15A2E107539FF7E3AD954AF8C79EAFE05A980
        System.out.println("加密之后的数据： " + encrypt);
        // 我是一段测试aaaa
        System.out.println("解密之后的数据： " + decryptStr);
    }


    @Test
    public void sm2Test() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, DecoderException, BadPaddingException, IllegalBlockSizeException {
        /**
         * BC: Cipher.SM2 -> org.bouncycastle.jcajce.provider.asymmetric.ec.GMCipherSpi$SM2
         *   aliases: [SM2WITHSM3, 1.2.156.10197.1.301.3.2.1]
         *
         *   publicKey == MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEW73V9Nl6FUU63Lxs93AmJpfiPhFZCPqa5EYF/uUs2MyzGEKi+TJjEB3v3KuJzVCBC1Ilow6l1jvHGFEa/oijtw==
         * privateKey == MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQgf+D/qe1w942MyuI5lB1lSk3zcD+WDeo3w+V0njAj+digCgYIKoEcz1UBgi2hRANCAARbvdX02XoVRTrcvGz3cCYml+I+EVkI+prkRgX+5SzYzLMYQqL5MmMQHe/cq4nNUIELUiWjDqXWO8cYURr+iKO3
         * BKZ8bs6POIjawn45BwmReSQqQ5Rk9LLGNhRNzQr4tEnJB6LcFzij8AvbqYJdr/D6isnJ4OMpalfWkbflbjIkeGeUiyxrLpSfw3oPwz2MJ4ivCzNsuE/HrBviRGNjZRedM2L7vVl6IkwZEOj4utQ5w0k=
         */
        Cipher cipher = Cipher.getInstance("SM2WITHSM3", BouncyCastleProvider.PROVIDER_NAME);

        Key sm2 = new SecretKeySpec(HexUtil.decodeHex("MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAEW73V9Nl6FUU63Lxs93AmJpfiPhFZCPqa5EYF/uUs2MyzGEKi+TJjEB3v3KuJzVCBC1Ilow6l1jvHGFEa/oijtw=="), "SM2");
        cipher.init(Cipher.ENCRYPT_MODE, sm2);
        byte[] decryptedData = cipher.doFinal(Hex.decodeHex("31313131313131313131313131313131"));
        System.out.println(HexConverter.byteArray2HexString(decryptedData));
        System.out.println(new String(decryptedData));
    }


    /**
     * 获取加密的密匙，传入的slatKey可以是任意长度的，作为SecureRandom的随机种子，
     * 而在KeyGenerator初始化时设置密匙的长度128bit(16位byte)
     */
    private static Key getSlatKey(String slatKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(slatKey.getBytes());
        kgen.init(128, random);
        Key key = kgen.generateKey();
        return key;
    }

    /**
     * 根据slatKey获取公匙，传入的slatKey作为SecureRandom的随机种子
     * 若使用new SecureRandom()创建公匙，则需要记录下私匙，解密时使用
     */
    private static byte[] getPublicKey(String slatKey) throws Exception {
        KeyPairGenerator keyPairGenerator  = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(slatKey.getBytes());
        keyPairGenerator.initialize(1024, random);//or 2048
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair.getPublic().getEncoded();
    }

    /**
     * 根据slatKey获取私匙，传入的slatKey作为SecureRandom的随机种子
     */
    private static byte[] getPrivateKey(String slatKey) throws Exception {
        KeyPairGenerator keyPairGenerator  = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(slatKey.getBytes());
        keyPairGenerator.initialize(1024, random);// or 2048
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair.getPrivate().getEncoded();
    }


}
