package com.cas.encryption.sm2;

import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import com.alibaba.fastjson.JSONObject;
import com.cas.des.des3_ecb.HexConverter;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/9/29 10:23 上午
 * @desc 索引非对称算法
 */
public class IndexSm2Test {

    // 粤居码平台持有
    static Map<Integer, String> yjmMap = new HashMap<Integer, String>() {
        {
            put(0, "EA99282B9211E05A0A414A534AF7843443F88F3682FD4240F09809E03C1CCF3F6071A0C5E92A4BF020E0FF0F936D65F3B8A1F226195D92B17E7EAB6BDF6D5787");
            put(1, "91687EE8F753EAF19AC25E4F0089A3FACB91DF9CA449231CE099F8D226A298A99E0AFEAFB05B327A201A2115360B69929B86397BD204DFC2CA2E233CE1443FC7");
            put(2, "194A0B5E199B5BF148C89B0996CE2BDAA83F20979F84E6A430342C090D99F2782A983502048CD500FC3E22A0E88D34C5EAFBF46F3E1FFECB5E077519D06793E3");
            put(3, "9F9E5EAEECAE933A635C29F4AB4F3BEB04AE5469866BD50AFB07D43C76AFA6D3EF7E0C79B83864D81B1657CE97BC87417E50589BB397DC00910A11F58DFD13D9");
            put(4, "B4CBA560F3BEB6CF3C7C1D74521590D0F9115775197FECF8F46DBF6A8B516EE7F065C0BBD9E837BE73F52E8954C856E2D54EDAA87D3BBA0D75186F1B7168561E");
        }
    };

    // 设备方持有
    static Map<Integer, String> szsfMap = new HashMap<Integer, String>() {
        {
            put(0, "9C535AF6F02540D262952FF40148E1849721845AB27D202401703A2839469C34");
            put(1, "4CBA0A874FA12FBE598F20D5C3607131D9FC3D8890D7E61D15C1766F6CFE2CAF");
            put(2, "FE84B75DA0502A5D9DC77AC471A205F5E459EA9DBDDD12332AF0A074558898B4");
            put(3, "A8E6A311A5A0D762372DAD408C328135EA41572CDFE93972E55949397D2C5342");
            put(4, "6A114EB0631001871CA02A0ACBC31F6C94B6E4A57EE5D9DB1860E931E8945B27");
        }
    };


    public static void main(String[] args) {
        String data = "09B32F1CFE6345F8";
        int index = getIndexByXor(Objects.requireNonNull(HexConverter.hexString2ByteArray(data)), 5);
        String hexString = "0433762A361F6E68F135A1A8A38DE013A4C9858A14D718274EB57BA2A15813F35C77D48BBCC0AE376576A8A2D38FA821DC10FBC3E34BDE87C4C69A95911D9F244916B1A4CC0E5F2B6653B58A00153C1EDEAFFF4B9A543670E2183006AE1B4A58057BAC7EB644E6B0A18403268D382E54D8023FF1F31616590541D492B2C41A3E6737BB7DE966F9C21B4A2A490D2637C71027E7538C659E24C17DF78872B771E264EBDA29E892AFACE345EB7B7378D865DA59D03B17E85FB51B940018422F035F857CB00C912FCFFB82E0363DEE22195E86605BBAE2D7C44DE20C998789";
        int szsfIndex = getIndexByXor(Objects.requireNonNull(HexConverter.hexString2ByteArray(data)), 5);
        byte[] decryData = decryData(HexConverter.hexString2ByteArray(hexString), szsfMap.get(szsfIndex));
        System.out.println(data + " 索引: " + index + " 数据解密：" + new String(decryData));

    }

    private static int getIndexByXor(byte[] data, int size) {
        int len = data.length;
        byte index = data[0];
        for (int i = 1; i < len; i++) {
            index ^= data[i];
        }
        return (index & 0xff) % size;
    }

    public static void generateSm2Key() {
        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();
        // 私钥 36 - 68
        byte[] outPrivateKey = new byte[32];
        byte[] outPublicKey = new byte[64];
        System.arraycopy(privateKey, 36, outPrivateKey, 0, 32);
        // 公钥 27 - 91
        System.arraycopy(publicKey, 27, outPublicKey, 0, 64);
        System.out.println("公钥 = " + HexConverter.byteArray2HexString(outPublicKey));
        System.out.println("私钥 = " + HexConverter.byteArray2HexString(outPrivateKey));
    }


    public static byte[] encryDate(byte[] data, String publicKeyHex) {
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

    public static byte[] decryData(byte[] data, String privateKeyHex) {
        ECPrivateKeyParameters privateKeyParameters = BCUtil.toSm2Params(privateKeyHex);
        SM2 sm2 = new SM2(privateKeyParameters, null);
        sm2.usePlainEncoding();
        sm2.setMode(SM2Engine.Mode.C1C3C2);
        return sm2.decrypt(data, KeyType.PrivateKey);
    }


}
