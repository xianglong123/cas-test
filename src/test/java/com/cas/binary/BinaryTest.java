package com.cas.binary;

import cn.hutool.core.util.RandomUtil;
import com.cas.des.DesEncTest;
import com.cas.io.byteIO.I.User;
import com.cas.util.HexConverter;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


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
        String[] data = {"a", "b", "c", "d", "e", "f", "g", "1", "2", "3", "4", "5", "6", "7"};
        int n = 15;
        Map<String, String> map = new HashMap<>();
        for (String str : data) {
            map.put(str, str);
            int h = str.hashCode();
            // 显示二进制计算规则
            int hash = h ^ (h >>> 16);
            System.out.println(String.format("h:[%s] === h>>>16:[%s] === hash:[%s] === 摸:[%s] === index:[%s]",
                    Integer.toBinaryString(h), Integer.toBinaryString(h >>> 16), Integer.toBinaryString(hash), Integer.toBinaryString(n), Integer.toBinaryString(n & hash)));
        }
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
     *
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
        buffer.put((byte) 3);
        buffer.put((byte) 42);//296 = 255 + 41
        buffer.flip(); // count = 255 * index1 + index2
        System.out.println((int) buffer.getChar());
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

    @Test
    public void test91() {
//        System.out.println(RandomUtil.randomNumbers(32));
        String x = RandomUtil.randomString(16);
        System.out.println(HexConverter.byteArray2HexString(x.getBytes()));
    }

    @Test
    public void test12() {
        String string = RandomUtil.randomString(6);
        System.out.println(string.toUpperCase());
    }


    @Test
    public void test13() {
        String data = "1391086179xl1638586081442440049";
        String string = DigestUtils.sha1Hex(data);
        System.out.println(string);
    }


    @Test
    public void test14() {
        // \. 匹配任意字符 \?
        String[] data = {"file.exe***888", "a.png", "b.jpg", "c.bas"};
        String strExp = "(?=\\.hello|\\.exe|\\.png|\\.jpg2)";
        Pattern pattern = Pattern.compile(strExp);
        Arrays.stream(data).forEach((a) -> {
            Matcher matcher = pattern.matcher(a);
            System.out.println(String.format("%s 是否匹配上  %s", a, matcher.find()));
        });
    }


    /**
     * anyMatch 任意一个满足为true， 全部不满足为false
     * allMatch 判断条件里的元素，所有的都是，返回true
     * noneMatch 与allMatch相反，判断条件里的元素，所有的都不是，返回true
     * count 类似list.size()
     */
    @Test
    public void test15() {
        List<User> users = Arrays.asList(new User("xl", "24"), new User("xl", "25"), new User("xl", "26"), new User("xl", "27"));
        boolean anyMatch = users.stream().anyMatch(user -> Integer.parseInt(user.getAge()) < 24);
        boolean allMatch = users.stream().allMatch(user -> Integer.parseInt(user.getAge()) < 24);
        boolean noneMatch = users.stream().noneMatch(user -> Integer.parseInt(user.getAge()) < 24);
        long count = users.stream().count();
        System.out.println(anyMatch);
        System.out.println(allMatch);
        System.out.println(noneMatch);
        System.out.println(count);
    }

    /**
     * distinct 比较通过equal 和 hashcode
     */
    @Test
    public void test16() {
        List<User> users = Arrays.asList(new User("xl", "24"), new User("xl", "24"), new User("xl", "26"), new User("xl", "27"));
        long count = users.stream().distinct().count();
        System.out.println(count);
    }

    /**
     * 利用filter通过单一字段去重
     * 例如：将name重复的去掉
     */
    @Test
    public void test17() {
        ArrayList<User> list = getUsers();
        list.stream().filter(distinctByKey(User::getName))
                .forEach(b -> System.out.println(b.getName()+ "," + b.getAge()));
    }

    private ArrayList<User> getUsers() {
        ArrayList<User> list = new ArrayList<>();
        {
            list.add(new User("xl", "18"));
            list.add(new User("xl", "19"));
            list.add(new User("tt", "17"));
            list.add(new User("ttz", "18"));
            list.add(new User("ttzz", "20"));
        }
        return list;
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * 如果所指定的 key 已经在 HashMap 中存在，返回和这个 key 值对应的 value, 如果所指定的 key 不在 HashMap 中存在，则返回 null。
     * 注意：如果指定 key 之前已经和一个 null 值相关联了 ，则该方法也返回 null。
     */
    @Test
    public void test18() {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        Boolean a = seen.putIfAbsent("a", Boolean.TRUE);
        Boolean b = seen.putIfAbsent("a", Boolean.TRUE);
        System.out.println(a);
        System.out.println(b);
    }

    /**
     * java 1.8 stream
     */
    @Test
    public void test19() {
        /**
         * 过滤
         */
        ArrayList<User> users = getUsers();
        List<User> collect = users.stream().filter(a -> "xl".equals(a.getName())).collect(Collectors.toList());
        collect.forEach(System.out::println);

        /**
         * 排序
         */
        List<User> collect1 = users.stream().sorted(Comparator.comparingInt(s -> Integer.parseInt(s.getAge()))).collect(Collectors.toList());
        collect1.forEach(System.out::println);

    }

    /**
     * 获取证书公钥
     * 0004 + 64字节
     */
    @Test
    public void test20() {
        String data = "308201283081D0A003020102021001008631310010866000000000000100300A06082A811CCF55018375302E310B300906035504061302434E310E300C060355040A0C05484E434143310F300D06035504030C06524F4F544341301E170D3230313231383130323233375A170D3330313231383037313535355A30003059301306072A8648CE3D020106082A811CCF5501822D034200047F61538BB0C8190AF97A35AA6CC072D3A10B958BE8B25750C7F8A5F68A806DC14EAE57E23017AD7B8304A84A452AF81101C9C39263571FC5E3AC41E791E931F5300A06082A811CCF5501837503470030440220584586BE3C4943CC9D453858970DF62EDE4C8EAF647AE7F5D96CF4F47D8DD115022033DD3D62AB274B944231DAAA99E1115F37A9D7721601A1462604FBD93018B3CF";
        int i = data.indexOf("0004");
        String substring = data.substring(i + 4, i + 4 + 128);
        System.out.println(i);
        System.out.println(substring);

    }

    @Test
    public void test21() {
        String data = "1";
        System.out.println(data.getBytes());
    }


    /**
     * 查看源码
     */
    @Test
    public void test22() {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("g");
        list.add("d");
        list.add("i");
        list.add("j");
        list.add("k");
        System.out.println(list.get(2));
        list.remove(2);
        System.out.println(list.get(2));
    }

    /**
     * 查看源码
     */
    @Test
    public void test23() {
        List<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("g");
        list.add("d");
        list.add("i");
        list.add("j");
        list.add("k");
        System.out.println(list.get(2));
        list.remove(2);
        System.out.println(list.get(2));
    }

    /**
     * 写操作在一个复制的数组上进行，读操作还是在原始数组中进行，读写分离，互不影响。
     */
    @Test
    public void test24() {
        List<String> list = new CopyOnWriteArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.get(0);

    }

}
