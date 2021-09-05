package com.cas.binary;

import com.cas.des.DesEncTest;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 上午9:39 2021/5/22
 * @version: V1.0
 * @review: 二进制操作测试
 */
public class BinaryTest {

    /**
     * 进行异或运行
     */
    @Test
    public void test11() {
        System.out.println(System.currentTimeMillis());

    }

    @Test
    public void test10() {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.minus(1, ChronoUnit.WEEKS);
        System.out.println(nextWeek.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        String name = today.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + today.minus(1, ChronoUnit.WEEKS).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        System.out.println(name);
    }

    @Test
    public void test9() {
        byte level = (byte) (Integer.valueOf("03").intValue() & 0x0F);
        System.out.println(Arrays.toString("00000000".getBytes()));
    }

    @Test
    public void test() {
        int i = 1;
        System.out.println(i >> 2); // i 的幂数 - 1
        System.out.println(i << 2); // i 的幂数 + 1
    }


    /**
     * 16进制转2进制，2进制做加法运算
     */
    @Test
    public void test2() {
        String str = "04";
        String res = "02";
        Integer num = Integer.parseInt(str, 16);
        Integer num2 = Integer.parseInt(res, 16);
        System.out.println(Integer.toBinaryString(num));
        System.out.println(Integer.toBinaryString(num2));
        System.out.println(Integer.toBinaryString(num + num2));
    }

    @Test
    public void test3() {
        String str = "TSMR00000";
        System.out.println(str.startsWith("TSMR"));
    }

    /**
     * 测试System.arraycopy能力
     * @throws Exception
     */
    @Test
    public void test4() throws Exception {
        String seid = "2100000E103003528819";
        byte[] bytes = seid.getBytes();
        byte[] q4 = new byte[16];
        System.arraycopy(bytes, 0, q4, 0, 8);
        System.arraycopy(bytes, bytes.length - 8, q4, 8, 8);
        String decrypt = DesEncTest.encrypt("101112131415161718191A1B1C1D1E1F", Arrays.toString(q4));
        System.out.println(decrypt);
    }

    /**
     * 测试java.nio.ByteBuffer中getChar的计算过程
     * 结论：取两个字节，计算公式：count = 255 * index1 + （index1 + index2)
     */
    @Test
    public void test5() {
//        String data = "2100000E103003528819";
//        byte[] payload = data.getBytes();
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.put((byte)3);
        buffer.put((byte)42);//296 = 255 + 41
        buffer.flip(); // count = 255 * index1 + index2
        System.out.println((int)buffer.getChar());
        // getChar的计算过程
        // 3:42 = 810 = 255 * 3 + 42 + 3 = 765 + 45 = 810
    }


    /**
     * 研究string和byte的转换关系
     * 16进制字符串其实就是ASCii码对应16进制的表示的字符表示，16进制字符串转换成byte[]数组，是每两个字符转换成十进制数据
     * 比如：101282 = [16,18,-128]
     * -128是因为7F是127， 80是-128， 81是-127
     */
    @Test
    public void test6() {
        String data = "10128201284377726E4255352F454A5A5676714E35667365746D3941733077367866684F7076347A693045505350754D6E4D4441774D4445784D4455794D4449784D4459794D7A49774D6A45784D6A497A4D5145414141454271526F784536614630662F6E3730544875686C4F67326476616A526A373475696B6248564D63564E6171354454564C4E46586366737135484142485433784852565A466B64585862444539356557626F32543142626E466A565841433350446B5730493259313338746B4E44525441794E5445794D4555434951433953596C565977557737444558396E6130782F65477857554F517A332F4A74716C6856473049625967656749674439375574574A2B5178594A6A73483462765444614A5635752B74556C743061493150486475374237476B411301001412353030323337313939393037313437383732150100160100174042383742424546394438423345304243424630303136343641464531373142374645333834323244323631454245313842384338443742343944454142393836A76D26A280000000000000000000000000000000";
        System.out.println(Arrays.toString(Hex.decode("10"))); //Hex.decode()就是16进制转成10进制
        // [16,18,-128,1,40]
    }

    /**
     * Hex.decode是【16进制 -> 10进制】
     * Hex.encode是【10进制 -> 16进制】
     */
    @Test
    public void test7() {
        String data = "500237199907147872";
        System.out.println(new String(Hex.decode(Hex.encode("10".getBytes()))));
        System.out.println(Arrays.toString(Hex.encode(data.getBytes())));
    }


    @Test
    public void test8() {
        List<String> list = new ArrayList<>();
        list.add("a");
        System.out.println(list.get(1));
    }


}
