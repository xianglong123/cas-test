package com.cas.binary;

import com.alibaba.fastjson.JSONObject;
import com.cas.des.des3_ecb.HexConverter;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ByteTest {

    @Test
    public void test() {
        byte a1 = '8';
        byte b1 = 'b';
        System.out.println((char) ((a1 << 8) + b1));
        byte[] c1 = {'a', 'b', 'c'};
        System.out.println(Arrays.toString(c1));
    }

    @Test
    public void test1() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("234");
        list.add("345");
        String x = JSONObject.toJSONString(list);
        System.out.println(x);


        Object parse = JSONObject.parse(x);
        System.out.println(parse);
    }

    @Test
    public void test2() throws UnsupportedEncodingException {
        String string = "4D4947664D413047435371475349623344514542415155414134474E4144434269514B42675143552B4242796347634D4461735942724C4836465A6C544B4A465373314736785838384B52575A417765704E7A344D4F4551745A4F4562645154756E39394161626A4F4E3270754F39576233347358455A33327A505A554C674C3941622B5054716A616E796F5A6D6446343445306150786F2F6F687962753273367653713333785633524E2F434E3337764566355359386836306A3348327172656E6833662B304E6250794A376471744777494441514142";
        byte[] bytes = HexConverter.hexString2ByteArray(string);
        System.out.println(new String(bytes, "utf-16be"));

    }

    @Test
    public void test3() throws UnsupportedEncodingException {
//        createMis(TypeEnum.ID_CARD.getCode(), "李美|女|汉|1995.05.25|北京市大兴区枣园小区5-3-205|110224199505252825|北京市公安局大兴分局|2018.05.02-2028.05.02");
//        createMis(TypeEnum.ACCOUNT_BOOK_01.getCode(), "100024523|农业户口|李东|200351|北京市大兴区枣园小区5-4-201|清源路派出所齐琳|2018年9月20日");
//        createMis(TypeEnum.ACCOUNT_BOOK_02.getCode(), "北京市大兴区枣园小区5-3-205|2019年8月27日|清源路派出所齐琳");
//        createMis(TypeEnum.ACCOUNT_BOOK_03.getCode(), "李美|之女||女|北京市大兴区|汉|北京市大兴区|1995.05.25|无|无|110224199505252825|165cm|O|大学本科|未婚||||||清源路派出所齐琳|2018年9月20日");
//        createMis(TypeEnum.PASSPORT.getCode(), "P|CHN|G00000012|李/LI 美/Mei|女/F|25 May 1995|北京/BEIJING|13 May 2015|北京/BEIJING|13 May 2025|公安部出入境管理局");
//        createMis(TypeEnum.MEDICARE_CARD.getCode(), "李美|女|汉|G01035|110224199505252825|2012.08.13|(0792)8586309|北京市医疗保险局");
//        createMis(TypeEnum.SOCIAL_SECURITY_CARD.getCode(), "李美|110224199505252825|北京市|H1097289X|2012.02.13");
//        createMis(TypeEnum.DRIVER_LICENSE.getCode(), "李美|C1|正常|110224199505252825|女|1995.05.25|中国|510177456789|2013-4-18至2023-4-18|2019年9月1日");
//        createMis(TypeEnum.CTID.getCode(), "李美|110224199505252825|4371244843|2025.05.21");
//        createMis(TypeEnum.IDENTITY_INFORMATION.getCode(), "李美|110224199505252825|13514389157");
//        createMis(TypeEnum.DIGITAL_CERTIFICATE.getCode(), "V3|5672141563146|带RSA 加密的SHA-512 ( 1.2.840.113549.1.1.13)|无|中国|中华人民共和国公安部|中华人民共和国公安部证书管理中心|公安部CA中心|21/09/1993 9:33:18 AM|09/11/2027 5:22:23 PM|| RSA加密(1.2.840.113549.1.1.1)|无|216字节 4D4947664D413047435371475349623344514542415155414134474E4144434269514B42675143552B4242796347634D4461735942724C4836465A6C544B4A465373314736785838384B52575A417765704E7A344D4F4551745A4F4562645154756E39394161626A4F4E3270754F39576233347358455A33327A505A554C674C3941622B5054716A616E796F5A6D6446343445306150786F2F6F687962753273367653713333785633524E2F434E3337764566355359386836306A3348327172656E6833662B304E6250794A376471744777494441514142|密钥使用( 2.5.29.15)|关键 是|用途 数字签名, 密钥证书签名, CRL签名|基本约束 ( 2.5.29.19 )|关键 是|证书颁发机构 是|主题密钥标识符( 2.5.29.14 )|关键 否|密钥ID:31 EA 76 A9 23 74 A5 DF D4 FD EE A0 C1 A6 9E C6 11 0E 11 EC |授权密钥标识符 ( 2.5.29.35 )|关键 否|密钥ID:2B D0 69 47 94 76 09 FE F4 6B 8D 2E 40 A6 F7 47 4D 7F 08 5E");

        createMis(TypeEnum.ID_CARD.getCode(), "范浩|男|汉|1992.03.25|北京市大兴区枣园小区1-3-201|110224199203252815|北京市公安局大兴分局|2018.05.02-2028.05.02");
        createMis(TypeEnum.ACCOUNT_BOOK_01.getCode(), "100024516|非农业户口|范明哲|200891|北京市大兴区枣园小区1-4-201|清源路派出所齐琳|2018年9月20日");
        createMis(TypeEnum.ACCOUNT_BOOK_02.getCode(), "北京市大兴区枣园小区1-3-201| 2019年10月20日|清源路派出所齐琳");
        createMis(TypeEnum.ACCOUNT_BOOK_03.getCode(), "范浩|之子||男|北京市大兴区|汉|北京市大兴区|1992.03.25|无|无|110224199203252815|176cm|A|高中|未婚||||||清源路派出所齐琳|2018年9月20日");

        createMis(TypeEnum.PASSPORT.getCode(), "P|CHN|G00000001|范/FAN 浩/Hao|男/M|25 Mar 1992|北京/BEIJING|13 Mar 2015|北京/BEIJING|13 Mar 2025|公安部出入境管理局");
        createMis(TypeEnum.MEDICARE_CARD.getCode(), "范浩|男|汉|G01033|110224199203252815|2012.02.13|(0792)8586309|北京市医疗保险局");
        createMis(TypeEnum.SOCIAL_SECURITY_CARD.getCode(), "范浩|110224199203252815|北京市|H1098289X|2012.02.13|6217978888888888888");

        createMis(TypeEnum.DRIVER_LICENSE.getCode(), "范浩|C1|正常|110224199203252815|男|1992.03.25|中国|510123456789|2013-4-18至2023-4-18|2019年9月1日");
        createMis(TypeEnum.DIGITAL_CERTIFICATE.getCode(), "V3|5672141563146|带RSA 加密的SHA-512 ( 1.2.840.113549.1.1.13)|无|中国|中华人民共和国公安部|中华人民共和国公安部证书管理中心|公安部CA中心|21/09/1993 9:33:18 AM|09/11/2027 5:22:23 PM|| RSA加密(1.2.840.113549.1.1.1)|无|216字节 4D4947664D413047435371475349623344514542415155414134474E4144434269514B42675143552B4242796347634D4461735942724C4836465A6C544B4A465373314736785838384B52575A417765704E7A344D4F4551745A4F4562645154756E39394161626A4F4E3270754F39576233347358455A33327A505A554C674C3941622B5054716A616E796F5A6D6446343445306150786F2F6F687962753273367653713333785633524E2F434E3337764566355359386836306A3348327172656E6833662B304E6250794A376471744777494441514142|密钥使用( 2.5.29.15)|关键 是|用途 数字签名, 密钥证书签名, CRL签名|基本约束 ( 2.5.29.19 )|关键 是|证书颁发机构 是|主题密钥标识符( 2.5.29.14 )|关键 否|密钥ID:31 EA 76 A9 23 74 A5 DF D4 FD EE A0 C1 A6 9E C6 11 0E 11 EC |授权密钥标识符 ( 2.5.29.35 )|关键 否|2B D0 69 47 94 76 09 FE F4 6B 8D 2E 40 A6 F7 47 4D 7F 08 5E");

        createMis(TypeEnum.CTID.getCode(), "范浩|110224199203252815|4371234853|2025.05.21");
        createMis(TypeEnum.IDENTITY_INFORMATION.getCode(), "范浩|110224199203252815|13511389167");

    }


    private String createMis(String tag, String str) throws UnsupportedEncodingException {
        String header = "00B28000";
        String readApdu = "00B4";
        byte[] bytes = str.getBytes("UTF-16be");
        String hexString = HexConverter.byteArray2HexString(bytes);
        int length = hexString.length() / 2;
        if (length > 255) {
            System.out.println("长度（自己转hex，替换null）：" + length);
            cf(tag, length, hexString);
            return null;
        }
        String s = "00" + HexConverter.int2HexString(length);
        String s1 = tag + s + hexString;
        int i = s1.length() / 2;
        String end = header + HexConverter.int2HexString(i) + s1;
//        System.out.println("\n\n");
        System.out.println(end);
        System.out.println(readApdu + tag + HexConverter.int2HexString(i));
//        System.out.println("读卡指令：" + readApdu + tag + HexConverter.int2HexString(i));
        return end;
    }

    /**
     * 数据超过FF拆分数据
     */
    public void cf(String tag, int len, String data) {
        String startHeader = "00B200";
        String endHeader = "00B280";
        String readApdu = "00B4";
        byte a = (byte) 0xFB;
        int x = HexConverter.byte2Int(a);
        // 有多少组
        int zCount = len / x;

        // 最后一组有多少数据
        int y = len % x;

        for (int i = 0; i < zCount; i++) {
            // 计算ff的
            String da = data.substring(i * 2 * x, (i + 1) * 2 * x);
            if (i == 0) {
                String ad = startHeader + "0" + i + "FF" + tag + com.cas.util.HexConverter.int2HexString(len) + da;
                System.out.println(ad);
//                System.out.println("\n");
//                System.out.println(readApdu + tag + HexConverter.int2HexString(x));
            } else {
                String bd = startHeader + "0" + i + com.cas.util.HexConverter.byte2HexString(a) + da;
                System.out.println(bd);
//                System.out.println("\n");
//                System.out.println(readApdu + tag + HexConverter.int2HexString(x));
            }
        }

        if (y > 0) {
            String cd = endHeader + "0" + zCount + com.cas.util.HexConverter.int2HexString(y) + data.substring(zCount * x * 2, len * 2);
//            System.out.println(readApdu + tag + HexConverter.int2HexString(y));
            System.out.println(cd);
        }
    }

    @Test
    public void test44() {
        byte a = (byte) 0xFB;
        System.out.println(a);
        System.out.println(HexConverter.byte2Int(a));
    }

    @Test
    public void test55() {
        String string = "abcdefghijkl";
        System.out.println(string.substring(2));
    }


    public enum TypeEnum {
        ID_CARD("0101", "身份证"),
        ACCOUNT_BOOK_01("0201", "户口本第一页"),
        ACCOUNT_BOOK_02("0202", "户口本第二页"),
        ACCOUNT_BOOK_03("0203", "户口本第三页"),
        PASSPORT("0301", "护照"),
        MEDICARE_CARD("0401", "医保卡"),
        SOCIAL_SECURITY_CARD("0501", "社保卡"),
        DRIVER_LICENSE("0601", "驾驶证"),
        DIGITAL_CERTIFICATE("0002", "数字证书"),
        CTID("0003", "CTID网证"),
        IDENTITY_INFORMATION("0004", "身份信息"),

        ;

        public String code;
        public String msg;

        TypeEnum(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}
