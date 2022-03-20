package com.cas.encryption.sm4;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.cas.BaseTest;
import com.cas.util.HexConverter;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jcajce.provider.asymmetric.ec.GMCipherSpi;
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
    public void test6() {
        String key = "MIIBqzCCARQCCQDaTgL0O8c2BTANBgkqhkiG9w0BAQsFADAaMQswCQYDVQQGEwJj\n" +
                "bjELMAkGA1UECAwCY20wHhcNMjIwMTE5MDI1NTUwWhcNMjMwMTE5MDI1NTUwWjAa\n" +
                "MQswCQYDVQQGEwJjbjELMAkGA1UECAwCY20wgZ8wDQYJKoZIhvcNAQEBBQADgY0A\n" +
                "MIGJAoGBAN4ca2cZcJqMU8Mc/9x1ENDZbhgKrecLU5Z4nLTbvzBdZuRNRlLod9Il\n" +
                "PzmDlhv7AtYt6kG0HmEj1ElYQBfK61zaQhMCCt0iJrZwglh5KqHYGq1rFjp2678i\n" +
                "o7tlHTJYu45oCfa3nus3/1Ton3LbrA6MJhbOpnvNd30/CUWhRGa5AgMBAAEwDQYJ\n" +
                "KoZIhvcNAQELBQADgYEAWBOm3XD+Dl/tKNF1K2T3Yh/N9qof8wm8eQAVhhZgotxv\n" +
                "VmWngigR3aT4ocaxywIPEi2/zoROIAV10fiMc/9IlY//gIL9OU84DrkKUjivU8gs\n" +
                "PVlsy6dnidQ3SK89IhOgQiakw15yGP9+TQ1oTtB+PCM1ib53Ac7eWsnaDZH8j1o=";

        System.out.println(HexConverter.byteArray2HexString(key.getBytes()));
    }

    @Test
    public void test5() {
        String random = "32323232323232323232323232323232";
        String key = "3082012A3081D0A003020102021001008631310010866000000000000200300A06082A811CCF55018375302E310B300906035504061302434E310E300C060355040A0C05484E434143310F300D06035504030C06524F4F544341301E170D3230313231383130323235365A170D3330313231383037313535355A30003059301306072A8648CE3D020106082A811CCF5501822D03420004A9721CB8C91EE248B60CC7C912B403A97903487DE0464F4F9FE797B33F83C732495F815DA6060F1B6A73705ACC37FBF00DC6F103DC81C4ABDC729CB7D4B89034300A06082A811CCF55018375034900304602210089C7758D7D3CBD0F0703D14A42B9A91BC783660915CE2BF30D6D429CD4FB6DA9022100BD72E92B15B0060F2B84950CA153C958132769D5734EE9A1E5B069A1F070764F";
        String data = random + key;
        String privateKey = "ECA65A95757D3C97ACBBBD4034DFE8B0EEB9819A9F3A77EA0CB9DB870C55E38E";
        Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA);


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
        Key sm4Key = new SecretKeySpec(HexUtil.decodeHex("03030303030303030303030303030303"), "SM4");
        cipher.init(Cipher.DECRYPT_MODE, sm4Key);
        byte[] bytes = cipher.doFinal(HexUtil.decodeHex("BEE200E404B18B2017733C37947E37E1DEA050902AB5473CFA1879A192CA53AACF3B5918CCF1876ABCFF0D092E42D436DE95E322E9D4B7BDA80A6D723650B0905856E42DD9E56F280AF122577E5DFA8F6F23BED078EB7431D9774AA880F8B95BD1CF2FA289D5541BFD10BFE16F44C7AB447F32878D7A9EB9BB48243DD37685A8840450421CE8769C2EEDCC74586049181FD8D73210673E6355C3451625C621C257D06B7A7F492420690F9DE5CBE60EEEDC4D8D08DF932667FBD4502A254E3B9526189711AF30DE0C3AA5FF975F7B91E7A9F8DAEB07D8F913E6AF8B02E08B057C24C82F8FB89141B71924CF2A277321F5A18C4774EAA84593A2DBAE9D29A0BF31458BBC7309A4C9A7AF00AF3284700E532564681572AF346C54088F6155D51FA23A66A94C748F2FE32B25756D9AA4268CC19A75A8728E02F73459A1228F883AEDD05A2B40E5FBA16AF50007AAC21DD351F6277A23F426800A7AD82E5C613D916FF61548A185922C2CB9CAF9593AA3EF6635AF7018B92864179B19EA3821A39CE490B5066999657EEF60BFB843EC31D097A1354EFD24B5461AA341004E0A22453105E2F01532FC0ED6B2C2A61C254E1194BB5AA63A9A0C01701FA680E40CF3F3FDD729D1EC8B1D1F8AE35A226C552EC9BD64082E7CC83EA070849686FA63491F3BF4BE9F512E7C1A11671D82086940D8E4E8EF3D99DD7B86DAF2FA7D1F454670061C0FEBEAC8ED6C800E1679D283244A93B049CF9375A5905CC117B2F00A55666FA166AFCC6AB5EA2CE618C69572816202340E3484DE19808944557C62570858371AEA8812B6750F3F437CB4D1B64DB87CC036076737BA53ECA0A46AA5042A36DB077B19BD03E3D4D7E3CEA70535EB4707370CD9A38ACD78A2D6E7A5268C1FB5519C93422C6F93A2288AF9560375A1506C2A5F012CE099F165C0C500453A6363FA0FCD9005E1123F51BB5AA79DB7CC63B2A9963B8FE17A2342AECBB50A0D4240DDE67AD814283A44D7A0A9ED51793D977039C4577C26050B045672FAF127F319C9534BD76B55DED2EFD195DBC38248ECB441A5A8AD634F6268B90D4753B64E73C57CA013241E384FB4EE09DFC811A0D32B3F032D1D3477EB8133AB72A8E0BB82EF13CAF99FE046E6129B9DB9B8EF3BC956B4D2983875BD614AC952B8784E15A4A62E33834DB85F54753261D8DA51A17995EB24651B888E478A5B296AB23FB0CF47D975080BD82796299FC6B04D6AAD0782B92D0155F6C7B4C94F99F8EF299804A4DBF4E91728F7A20DF53B73FF8434EAA22E403F1F1AA20E4D692EDDFBDDDB691882622C14963D5153FE7DA3EC729BEDA92EB45F3DB62FD0885EC4A6E4903FD8DA6F2628346EC564BBEB0878E4B75C032B5D599D1832C0909FC21910B8038927D5D52A0FEF04E9344187521FB8B6BEF82AAA3978576A4BC40B5FFC7E7D6260643705FCEE94A1859334D9AD639EB57178F02EDE0E8D6EF731D57B60B5A57D0FF22050497697DBA6C465046757335EE6D49126E456CF14727E0C9070CE13E7A5207CEF956EB6C715EB16904B72638784EFD61859794E8C7ED2DFA28C45CA65F8B697CE9FC5CB557BAF2370F3F64B060812F52A46F7FCC284E5344A2E37F1C691BF2FB10795343E710A5A77CF9FE0475BC0450CF4CF83B65843344DABA705F3B9FD2A96A670D21A0E77E27849CA470717AC93BA73D4913A291F2F6693EF6FFEE6A28C59F7DA28C1125F3B30C0AB045DACFAB7BE684AF83AE097AF57418AB2E0FDAB642472ED86BF6642DA6814879A7CFB573EAA9614E3F0E824B3DA99D2529B57C528B8FD4BBD49E4C9D05C17C8E0998136CCF4367351B9229E37D756927B2179650A0BF022D22CCEECCCB22FC85B0701012DB230F3CDE6BA889BA86DF0A6E6C639CF9961A6DEE696F134B94DA35F9E1734FCAF85312CEF54216979CDBBDAF4F37F8906981E8CD66DBE47BF390EE3D38736D7D811E38E8C2AA17B603C94C2F21BE5CF13298E1EF6A030D9A4D797A2A4517780D049E4DF84FE86241CA5475C025E65AE05A4D45FEF5576E2DF5BB773874DD9B1146A8249D8A5D35EBFD91D339F4F3BF6B46223D61B6637EB9B09A31B9EC8DAFCC9E0FCCA65BB6E9142A7845B0AA85D66E73FC9FD43F20327D6B5F8738001697D94D0535D0E6B1C48B04131577D08867A8ED7000CF01D9F01C8ADA7DEEE58EE4B32E051546430C738649BB5D53EE3C1B59459E24FDB36FF6355F800009C4498E2E89F83587E2783A5E4D62357E6A22278D9AF91759FE656227DA06C60C61367F4838F6A321C66F97E86870B3E5C3F34496A7E320A20694EBA78F64328C7D1A0E91AA8FB7D55F3C4BD6C1BFA911E99C57A52C08514592FD272FB696F17AC4A5BD458A7E3D8C8D0C99D145AA70CA65196EF155867EA283229C3B362F645363FFCAC74E6FD91758D955868F30BCF6ACBA2C5DA462B66DB9459D747C9B10F29475878AA0A0F8781238B39927BB93B7CF383A4E99DBD57802FC87885AFFC2A98B94803F5B6E2844809EEA9EEE4090ACEE09126E1DE4477E459048E6C0B69AE695698C20F1E0FA3FCC69683FD781D880192D95A60E61A216E9F895B087D68741B2209487481175E86B1D7D9A5467DEB47F0CB87DF9CD0CBB0066AD52D69D9FF8563B3AAE9C4E663E55D0F800534488573D30AE8965E786A053E91C0559F643667DD3473B791CC8B4283C7530B3CA3F3604B96E91FC655CC6844EA2FE206A03CCCB4241958A7E3D8C8D0C99D145AA70CA65196EF02D65F2EAF463EC68A67D1D3060A6F0C38A612A6AC8746370A458876EF2F4011AA03B44005B318EBA7FCE56EB7F27D2CECFB1BE918E77AF55FAC329E4A5845980166D8327CAF715C820AD4578F2F0F9F21A9143B2C50F6A4F52BF44919FB350F3C174D6AF42FA2B002F8324B2ED95F567E347CEF4D37A1C0F673897CEF0AE788BE833EDB9EC18635882043AC49066228"));
        System.out.println(HexUtil.encodeHexStr(bytes));
        // 6D7620BDFC47FEE1EC523393339CE8D3
        // 6d7620bdfc47fee1ec523393339ce8d3

    }

    @Test
    public void test() {
        String content = "953C098366BF7418D0BB1E2DC591AEC4C1A4BC4739ED3F1DE657258E54D8DFF0202268958759dedbd72827b73350fdaaa838e6fa4f35e5cc3b839450636f71464e907c9c522003111234567890123456000006EIC20220311180331000003002";
        SymmetricCrypto sm4 = SmUtil.sm4(HexUtil.decodeHex("03030303030303030303030303030303"));
        String encryptHex = sm4.encryptHex(content);
        String decryptStr = sm4.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println("加密结果： " + encryptHex);
        System.out.println("解密结果： " + decryptStr);

    }
}
