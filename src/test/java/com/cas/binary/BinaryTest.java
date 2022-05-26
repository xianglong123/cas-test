package com.cas.binary;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.cas.des.DesEncTest;
import com.cas.io.byteIO.I.User;
import com.cas.util.HexConverter;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.util.encoders.Hex;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
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

    @Test
    public void test25() {
        byte[] bytes = "xianglong2123456".getBytes();
        System.out.println(HexConverter.byteArray2HexString(bytes));
    }


    @Test
    public void test26() throws UnsupportedEncodingException {
        String data = "/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAB2AGADASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD16QFyzlyx2g4zke+ff2FHmMuQVyRyWU5+h/z0pXibYHKiKNjjavXBpyr5YUvjb0PYH05rQsjRi6+Wqk4JZfTrnr/k0+Uxxo0rhRGB8zOMKKHZjBvBRYYc/MTgY7n/APXXh/xH+JRvHk0vSZnNqDiWbJHmH29qCWztdX+KGh6ZL9mgV78oT8yHbGPb3rz/AFf4t6zeSN9mkSyjLfcg6492PJryufU55CRvyKrCSYnd8x/Ci6RKueo2XxS8TWU5k+0pcxsRmOeMEAexHSvR/DHxftNduUtdQh+wXDDbvDZjP4npXza91KUARGGO/NT2d6QxWRtp7UOSYNM+04X+0KzLOCjLnK4I9qeiygjDgnILA9CTXzN4M8f33h3UBHO8klq2BJEWJ49vSvonRtSg1azt76ymWSCUFgTnr/8AW6UhGiHeKIIVXOewP504GMngkH1yRhaaXZmG8DDDgj070u3ryw7kDoaAKPnIwZZEwQuMBskY9Sen0qQKswBeQuByHYY/75WmoIWZQRtbOR6fgP6mlljCBm3dASe5A+vag1Z5n8XvFr6RYJpFqyrc3SEztnJEfTvyM/0NfPkksl2629uCzMcYFa/jTWbnXvFWo3LuXxM0akHqikhf0H610vw88Lq7fb7iPLfwA9qicuVCjHmkHhr4cJNAlxqRLMwz5YGRXZReD9Lhi8tbBCPXFddbWiIgyOMdKmNuhPAxXLKTkdcYJaHCTeD7GOM7LVBmuF13wrHFKWRNhB7Cvb5IQpxjNYGuaYk8TMq81MZNF+zizxgWsipsfG5OQT39q9a+EviKe1u20ed0+zTgvEWbBD8ZUZ7H/H1443WbARWE0yLygzmsrS9UkiuYJoMCa2kEi+5zXTTlc5KsOU+rgwY4PGe2f89KcAyorFjjoMjmqWmXAv8ATbS7PHmxh84z1q4Q+7hsKehA6f8A162MCs0UZUkAAY6g4X8W7/hUU25tJuA2CFjcbi2F4H6/jVyGNvJV3bBA69TgdxnpmmBSLN2dY5AwJOepFI1Pju2snm1uSJsM7TbfbJNe86LpqabpUSEYcAAgetcFpOiy/wDCfRNJatBCzySpG3YAnFeo3bxwxZY9OwrKq9TWlHqR/wBqxIwXync/3Vq3HJ5yb9hTPY1x+r69d2wha209TvkClnflR68Ct/SL1pYf34w2ecdKwbOg0ycAkdRWReSXBYgR78+g6Vo3Ey+UwQ84rib3U9ej1eFLbyjbNkOWi3Y6c9R1qUrsLjtU0iWe1lh8v/Wj06V5nqVm+lauY1BClcD8K9qsr2S4+W7hEZ6Ajo3vXOfEPSYf7GguoIwJEkJLf7JB/ritqW5lVV0epeAbwah4G0uRUICwiM5PUjg10QTbtUck8genvXF/C65DeDoLbfloidzKOOT0rtiN64B69SOOK6DjaInj8uRUiY85yCcjFDBpXCpFgKfmB/lRmSGXYcyNISS4/h/CopV2hYgBub+Iggj3pFnM6ro1vFr66ggXLxt07HvVSeHzDkgH2rpNXiVrRWAAMZ9O1YX8WKxqaHVSehmS2iS4DKOKGjSFNyrirEnDtgZ5qN5Y0ZBJnDHGAM1gdFkRp+9Xp1oa1RW+4Cakd4EkRFDruHULxTs5z1z6mlsJRGx2oY5wMioNXsVvNM8l13KM1cQkEgd60LC2E7xxsoYluRWsCKuiN3QNJj0fR4LeJQABlto6k1qMffr6inIgVcBflx2akfKjcHAbsGFdKPPepTh3bVZywYjJweP84p4UNIQXbOON1IQ6lwxVFAB+b9R6U6NsQkqhMeMnIx+vemUV7+BnsJlyDuU4IPc1yHmYBBzntXbrGFiILIA3QL0rh7tGtrySNlK4Y4OMAisKx0UH0I2JTljj3qo92ivlWBqzeItzbhdxH0NZWn2zaWzhSJAxz+8G7FZRs9zpuy2t8kp28KKmW4jcYRgee1QTyNNGV8uIZBGQtV9N04Wf8btj+8c0SSBGp6V03hqDdHJcMMkHC5HSuaLb2AH0GK7nSrVrPTo4z948n6mqpK7Mq8lyl4A49PpTXz2YDPBJpwGOe9MYrgs3AH8XpXT1OEpSERqHHl71PJJ3E+tTGQ7jmRyPRVpjOkEuMhg/IHof8Ky9V1zT9HtTcalqMNqiHOGcA/7vr/jTKbNNPlix5ZUDncxx9K4PVtetdT8Q3VnalXa1jG90PBY9vwrnvF/xYtRZy2+g7nZwwM8o7H+6P61xXwrvPP1nUVkYmRwGyT71FWPulUn7x6iknGM4I65qURo6/MaLm18w/IuD6iqjx3cIAXn8a4uY7kWjBGOcio5JUTjdVUNdueePWpY7RmyXJai4Nl/Sbm1hvYprxgsIbGW6A9BXogZduQRjsa8J+IVw9p4Sn8pijblAI+tanw2+KMEukw2Ouz7WUBUuH5BH+1/jXTRWhyVmew/fGGOcd/SkLFPl7+vrVeC+tbtFkt7mKVGXcCjA5HrxU7Mdma3sc583eMviD4gg17WbCPUZIoYbuWFFTaDtVyoGQM9AO9eaz6jc6jeb7iZ5MnJLNk1p+NpS3jPXxjH/ABMbgAf9tGrFhj2Lz1NaWFcuzy/uduenAFa3w71A2XiofNgSqUNc8x4NXPDEbSeIbcoeVbd+VRVXuFU/iPpS2mEsY5yalIB5IrJ0522oxPy8VrZry2egg47igkBST0pHYKMk1SmmcqzZ4HQUhnn/AMU9QRdDFsDzJIOK820yYpbFfaup+JDPPIkx/wBUp2Ads96422cBABxXo0PhOGvua9p4l1TQ7xLixvJYMH+E16f4c+NtxHGItZtBcgDiSMhW/EdDXjs0YlU5HWqcEjI5jOQRWy1MEeu+Ivgx4kvvFGo3wvtL8m7u5Z1DSyBsM5IyNmM8+tZ8nwU8Sk5+2aRj/rtJ/wDG6KKpF2REfgn4lJ4vtJx/10k/+Iqxpvwb8U6dqEd3Fe6RlDnBmk59f+WdFFKeqLitT1Gw8P30Eaqz25PcbmwP0rSGl3YHJg9OGP8AhRRXG6cex1psbLot1IAd8I7feP8AhUE2gXRQqkkOSMcsf8KKKz5I3FzM848T/CvxPrd9uiu9Ljt4+FRp5OT6kCPGayY/gd4nUKft+kcf9NpP/jdFFdlNJI5qmpbHwT8SlSDfaT/39k/+N1Vl+BHid23Lf6QGxkkzS/8AxuiirRnY/9k=";
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        System.out.println(bytes);
    }

    @Test
    public void test27() {
        String data = "您当前机型暂不支持应用操作，请选择其他业务体验";
         if (data.startsWith("您当前机型暂不支持应用操作")) {
            System.out.println(2);
        } else if (data.contains("您")) {
             System.out.println(1);
         }

        System.out.println(data.startsWith("您当前机型暂不支持应用操作"));
        System.out.println(data.startsWith("您当前机型暂不支持应用操作2"));
    }

    /**
     * StringBuilder 替换指定位置的参数
     * 结论：字符串从0开始算起，包前不包后替换
     */
    @Test
    public void test28() {
        String str = "15811317734";
        StringBuilder stringBuilder = new StringBuilder("158");
        System.out.println(stringBuilder.replace(3, 7, "****"));
        System.out.println(new StringBuilder(str).replace(3, 7, "****"));
    }

    @Test
    public void test29() {
        String seid = "21960009100000275049";
        System.out.println(seid.substring(7,8));
        System.out.println(seid.substring(8,10));
        System.out.println(seid.substring(5,7));

    }


//    public static void main(String[] args) {
//        String privateKey = "ECA65A95757D3C97ACBBBD4034DFE8B0EEB9819A9F3A77EA0CB9DB870C55E38E";
//        String enc = "821E9052F80EA58A7A477D0E3B22F98EFB4F549A999E1E4E8D8EE92AD86A977E17EB1D7A98E9D752E8EDFEA2C8DBE13323D4C2018A4206CFEEDC8D7F74D8E6FEC07E44BC84DEE52FF11BCD43207F2C6947DD2F2F5703242B0CFCAA8557B38D8D3C40B86BF1610FE8E303513D73F4D85D8E9048A60D41072B896DE4DE4A7D2B942EE9E4574DF324D4BCCD6EF4A57AC80441EA97DDB79B13C881B1C3C438B8EC02B3B1941857106715073063DD60DA8280036632C5BD34552EEB10A6E99A296F7A9BB8FD54D6B3149CECA26380CD711E76D924CBF59F40DBF3C649C7D1DBC204449B1FEABEF5867324FA352154845F09B29159A10164E54D060E0922BF0CAEBD7FA1C7CE8A1ED0A65B2851C827985FA726B8D4CAA7ED363BED56C9DF730097CFFD5DBFBAF6F434E9B83D874A8BDFD1A3CFB78E116E87A65AA692AFD3189A9EF089D4255C66F38A69958025F726A25DA8D3B52E7CF90CD841F2E1D80141FD6649915EE5BA0A00B427A1F2206EAF2A154D17760216453854B193FD0E927E49F68A43C44D5D3D76D937DAB409BCB49DD4AC5544A44CDE33C62E75A90649B5A080F9A47B24C096888F5D3439B1CCB68E953F9A716D998EEA9FCF0A4A27CE36A63AFA5AAB19AD390412D120BE7BD915BC711EFCDA5D14662E143FF2B56956685285EA93550307E071AC957247A98601805A6F24E99F7906BB1E318E6DF9D162CDD4234D27E7F867A6FE9A507582C417A63794C862B8656CE7C02478DB2C6F9CD70E69944DE8518E4112844692127F481EC83367A8E4ABBE09306783B7933E419C48380F3EFEDCC07A0720D1C2432BC5EA8C1C047EAD6D162385EA5EC08C3D23343D5D2AB077AA0C36604D50E2A338F0ED4BD5F925C330A646532FE81ABA39D6D7F7A89D6DEC6BABF5D2B2D8367BB766E058D043E84860ADF37430EC114ABB342D1FA076B3F587CA548810D4E5AFBF62D191F7A7050304E2D69C07E2189B4EF528DB90C649281C07C87F264651DAEA4D1EC038F2455E6B0F9EFA7DDEBDEE8E6627DA0BBFC276B2C187A10A772455D7AC6A5A41380D8836118E22BD754FCEBAD9216F6DE04B22DCA4D60CED474EF06A4371DCA776DCFD5A20E956AF69A05C971A03EAA8B77B1209456246C64C129B13073C06FF8D1A100D456246C64C129B13073C06FF8D1A100D456246C64C129B13073C06FF8D1A100D456246C64C129B13073C06FF8D1A100D456246C64C129B13073C06FF8D1A100D456246C64C129B13073C06FF8D1A100D456246C64C129B13073C06FF8D1A100D456246C64C129B13073C06FF8D1A100D86FC2FC88626ECB16D8413B2D9A751ADB9AE40DD2350FADD1FC3B42EE696C2DE099BD0B045D635175407CC8D3D50A6FCC47AD874251713F217B49004E8A83B35678E61A00AAC330FEB9B2A41363750C92D93C173B1CDEBD7D37F1BC7809792A753525CC6CA1A6E51B85CCF69623B50FC9931950E4048005E6BC2903CF9564C9DA13D2C94975A03AF2547273E2CDEA09FCCED37914D9B4ED9D35D9993BEC401DA9AA4FAA30DF75C49C7B98A8BC2C59026F2EEFF434735216CFBB494F5E260C79E0C2752C7BF7D86268A914C861EAEA788A7DA680693D5C75EF1E81911B5BB68EC0109D26AED8C75847AA14F9C6753BD60AEE560FE3F47EB21CDFDFDE75BEE832FDB608C819D4EE0FBA4F908CD1082831C41489492638CEC7F715ADC21F5E0AFD9F92AFE2CF15C43358F0FE717DF19827B80E8EA3E8B0A99E553A6B18452C594993CEEA2BB9920E585751F9F02C822413CD5984AD5FE22641BC35150D0B3782A738EFA9022456F89334B1E78E0F283CA361DB31CEC401960943F6062E34FA25246A4E662F1A26D5C79280699D05FFBED523268DF21D1507A2140F483EE8AF80829EA0A2ECD56D5B5184F7CADD185A8EF229A197F9785EFC1BE133FA5949D9FE094785254F04A6791578BA5761A6B860BB6E95D8BFE7E2B521933E999532BA537B84EFAFFF26FEBF0DC88CC1A7779B2E849012EBFDFD1B177C5EAF4EC44E196D77A0F15243697F1EBBA3083DB7113311EDB11BC57C246F4E3566D77C80AB037F7BD8ED2EFE9550D719382D6D42F05DED28BA1C4D67E083BE2FB93CD6973CE862A6F20EE3DD2DD40E130FFB4BD9201B7151733E59D01D08C6A647153D785D87F24BA7D1A4F3F710E92C689EE66E40EB55423B00BDB5C769872C2A7D3F18925737CEC89D736C20AA0C2C44799B05C6AD71A1AB352FAD563B164097E2F05C682B5DF6475E0F20725F2C71544D7A30DB767B9969931950E4048005E6BC2903CF9564C9DA13D2C94975A03AF2547273E2CDEA09F3A8F2D46207A8D07DAE4136D4F3FB6B4961D5CC348550AA60C93F5116ED2279B9CB763A36F995D5336BA789C38D8C048FD3257A5F442A41B1FB4857179759B71B28A467FB4DDDBD0A151CF2FD44775A5BFF8325E966149A00C70C64DBF00F3C7937EF52DB71EA99446604C7192DF89F75D18057EE1ADD01FC958F8B0C8329BD6B2F45572DB4559459EA65DC1DB7F2C4702DAED6587F7209DB6785F7C4A9CB5B8FABF6AB6B9C18208075634A448CDA9FA52878D94622A10FD8F4AA10AFA36B93196B821BECACD01C70014BF29386581B045800FDAEF33746F0A59689808D339F6350933CDDC82095CF7232ACAF58272911387268908DD18E1EA96474FF1A8B70B7B0741E5A987824D2BAE02243419B4A7B7C15DE3EDBB19BF7DABD280AAA2EA9E35899251F96A340E188D61F3CAD3BAB79ABF9C7D11EF78FC08FFB7F6A0AE2D21AD53FF3CB133FC454F486C3CB541CCF2277E4B9A6F3D347E55BC17F1D5E190EB5B605FAFFA3B0F48180955EF7ED9E36DF68F43E2F6FB0066254593692512D90734861382E1066CD263C7A1724D165621AB2867E22203477923CEFA31B4CE2C0D2CC1680FE9A06F47DB247601EC9C332D759777A1A0464E12D573C31EB364710EEBB4CE2E18C431E886C515DCC5C381547D1A4F3F710E92C689EE66E40EB5542355B6B858D82F16F05440831D20AAF05ADA139276C5082786F01A3B335E5963BDA339043976AF07EA9002B5CC546E5B552D14EFB7803282C413321E492CC765959C80F085E7E7D8B03E462D5C864F3E50A83A662861AE8175D374E3F48C193F7FEA6261BAF2CA7FF4A3DE75A149786443B7C50A74FE3F65742195F36963008629D511BC39F189E3992A2810635F309BB13843837554884AE6BE1B0C4A91AC564310F6CA266C0CA8E8432F68805FD89D36137613CDBAFBCB9094A3DB166075935BDB51E12427ED891A3CF1DB5A85C12A676989D55C375360C8B24F71093883BDF97090053626AF9770B2E52F645D21F6451ADCDA6987856AF5388D23D5A7A76C70548BFC695B9ED369CC55A1DDDBCC5FC39A25AC18EE9993E6A5AED2DE3E5D1196ADC179D133D61E4EB68EC25AE4867688372096FB5EFFDDA58FCB831491BE7E6B89F79CCDBEF4F2C525D3847CEACD1E3ECB2EA3FCD89C67608714D7924746F036D4FA09BD0CAB428B8E3E72A197D3B7737908512EA9BC2A8DA054ED588B3F72EB3802D1A3BC0ABAB05803B73A14A2F70C82F866FE8FCA0743C8A0C2D2282C5D4A0C02C261E1E371F7E0A9960FD7F1C81EDFF10765A51D967415D41DF887AF8F32E451CD71F7A47F2F1D7A061E89F7A6DCF141D5A4D1A64DE3A92B2FEEC8D8DBDFEC0277ACBA5411DD8700B50EE7AF764A0728654C4F276635520087D9149DD3EDB94DB1E233A3DA451E461F55F04B67433B32131368FADD40EBD2943B36D75CCA6AF81C63286C1DBC20A6EFD3067F46FA5B03C27D08032888C29428AD39C54A0F3BC98228CD45F40AE29FE53203E15C1B5A9F6E6C6E52DCACA344B53929F6329C15888CF936EB0CA35681316C33E93029FE135801532C17C5025BADF5F45A073282DD2E824DE18C9EE52EF9E5D7A4801396D2AC23809B299F7A0D89FBAC53CA0EB7B743EB5DAAB2BBE72458ED794B654CF3BFB9AB557EEE0661FF7EE7E3FF978E30E53469E40C8DE41C2022D43A981F04306565006641D140E4322670D080082708C3CCBE3BE01B4B9BBAF50AEE608F5E3A6E7F36B8065EBD62EE461EF2E45F1600AAD7F9479A1F768AA90D7938C8E959C4ADB8AA816F4FBBF1082CF2019B5E7BDB4EF83F79F07E4BC0D4C91F4E92F833370AD3C6DAE8FF2BB25000BFD90034CFC7BB9884DF640B86B01E01205C2167366EBE8D0F68823C05C1A7E286B53BFB5E6DEED3B4B2A5D01AE9FD25FB0D9E57F96FE8DA49610C5BA96E29629344D23CFE3E662155A5A83ED4371A7925B082E4C0D0D9A71D86234A053938ADA758803A0379AA02785059BB025AE019BAED84E1C638D9809099DC1269712CDE5EA2A4C510095670C95AF22BE149127F2547674752D6191B92F10D8D071236187DCC59F2E2F8C71A4C7B15DEE88C7E50F46198869F761B02418A8DFC665089CDC9A1C577BA284B4B593C9818D963C8CEF1DF06F9E9EBA69E3322C13EE15D98B3794A3C76C35317BA05A48690A28152EB328857B6EDB142ACC454F5C6BB689C65D657D595785F8178949CD3139900EC254D0D3E91F07F80A79A939E1495BB0BE5566B5A55FC3165B073C61D7BF3A29A2D3DFE23E002DC1F7D46E8A9FD83C6E1FA0144D08937B22408B6E51F4E56C45F692F938C78DC01188C188B30C9F3B2EFB1378CB81179DF9BBEB1E82AB16FCE9B11549BCD86DAD3664EF1A2F408BB6FEF4A81757C2E354BEC4EA450CF204B6D76F3183875DF8DBCCBFB33E0D1207887DE8D78E4C94BA5714DDEF41F62AEBD7904549A835217690A66F2DC648CEEE94FB404924453C7CDCD84FBA9D290196D6E2F62A0251E7AD6674798F5DAD102AE8C2E29EABF576A38554A339C920A76BBD8C21975DD34B228218F838D7BF2AAAFE2AA50D2180353720C004545FF3A64B85C261650BD4AD65374311B55FEBA45773A3AE4EB6C24F3FFD6A02FBB1A34411EEB8EFBA390409A4693F9D51182762D671ACE8D12E09DC2DFF839178591F1F92D14F6EBEC04DFFC987749C33C832AF3FE09AFEA1035196D845E8926802910B191810672BC2878B2C2A8A201158319EDC1A86E02043CA3C0F1EAFA24A41F1C3BE75D25AE628241AA3D61CB819763BE5942B54238BCB48FBAB8BAC629D3E36196341E04C99010BE8119ED1C0933E56A8DB4E96F52FADEA3B4F35989C8634F8D3C3F72A35D38449E3B43FE4D95F8924BE61F74D58F774A3AB2AD936EF01DE93B38F54ACEB1BDFD2273A20593035668169F899B4FB1A695FD88EA984A9F04484A5CE671C9AD541CE367F67AA283F1C32C17CF66022611D7AE9F7E861712FB9FB82F629467CD013B05FF95AABE47AEE5789C48E68A85C74941EE3691B7CC4DA33E3E9D37F74917B7590DBD0C298F5399E9B7D929B7C2BC296F9A0217D4A7D03579A42CC3907DE5F58CD66BBEDAC1313368B7177FA21A9E2D71191228BA2B4CEEC264AD01996D0F9195C4A8F745B8FFD751EB21588048F571A4B35C4975BCF9F9D0CF11E5E0E489AC68BCF18D395488B049A285919F9B25F4B9BEF9CBEDDEB842105D60537D4068569D6C065EA75A88BEF546BF0448195CBCAFB0031E27B11A5CAA59C4DE805EA621A0A662585AA812F89F1431A6D1F87609769465D7E24E0E361814DF3BBEE9A42878608BDA5F29B8B9B0DA9705823C2D46D360A1D783FDE8818A1BB00EA9278BC21EB9C21502F88C4AE73E21CEB04A6EA0A58B0F263561672F4DE29DA27FAFC35B8693522FAF8A7618AD30FFDB3D87327B8BB04B234D2959F709290ED00606348945DE109B2D90A4AE2EC6C50F36712249F247FDDA214AD3CFAC80E1CDF767AA4CE5AB59635F0538E82268DE17A3BF4C99B54F0247D6EF5699AD4381B49B6A22F6D098F4A9FF26E40E0EF3417A73F1F45993CCC891F6F72325F66864CA1D6221C8BA54C09D61F385F71D2BCCD9A171CADDE220605251A658AA834230EB112A8413A3763BAA7020D2709A86381B7AFE7347B782155E52443D3E070AF3ECF365522CDFF35BCFA79C97F6E0E5C56AE273635409D67C20584F85AC0351C1BD310552890BD53B6F2FBA73EE94531891A78605DDA6647A49696CADAC0924755FC3D75D25334976B345D03ACCF462E756F5A0CB2C7550703A2A787C390609A7B492A61F3E0C1C119188495C9DBACC1A3CC3CFC13965D1BEC2084237134347809739DD31C7C3705B24FDF944E65FE603616A980B56792B3ED7025CCDD8EEE040DDF86570C6B4DCC68937F0777B291BD11881313C25F50C96240E9611BE0B559C060BBCA6695020125C0150FDF7F13F56B4CDCDAE1D70512D5C4846A8E19786E4BBFC43BDC95E7763F2F98889B2E8DA6002B5C5C5EE0ACC664C81BC0ED04DA9DECA43CBCA4AFDEB785D40D0F6D0329B98AD470D164758A68C60E83681D78736C76073BF125E643DFE4CEA35BEFBD9DF8105633CFEC5EFEA039D4A51CD88EC60780C700851C7A8C5DA904BF2EFF9199DBD8423CF83FFC27ACC1FD48E9CBB4AF64C42F4A80C9CF0279CD207A125C1AF48AC724AD8AAFA93B79BADF9648AAC5BEB3FF323E7848FF22D3483D904A6EBFEB61CF9F5CE7DCC148873680F047561F1C5B58D93C351E8637B340E8090A040D57E2314507F5F9CD76D9F12A3DFE38B463109BAB4611CF4E3EC6ADF1CA8CD44150029459885FC9550143C223E1D8FD72F985561598B6D5D947F0DC1B80CCCE164EAB669F8ED6A923B27DC5812EC3ACC9853931E1747AD2FF1CA71E8EA5E0B96DCCC7AA7343E89F2FDFEC7DF04D9B0A8D8C6E4B40BD9CE43B894F08DAC52E9DB8EBA207491DCEBD2C672965E446988223220D2EB5846DFC9BA57F454B5F47D824C96C3E16579D011192C347DD1ADD9EBB05B1268AD3F4D50CBDE64523ADD5B76BC8032A4B0E5AF445ED86D7A11523D52AA5CE7D4D2E5B7ADE746B8A4ECD168E54F3146A46EA2BC175CFC3A3B65766737DB78644BD6F90BF166581497B857736DAB2F3EC4C981F7056C5E1006A970E0C44541D483E0AA5629DCD913D32623F6B97A450B25C74B11B79404A660BD0C5B3E7ABE076CAFE7BCE18C16EF11FA1D7C31D6D706E0B8B3DDF5D09B91FAE816E7EBFE2546CAEAE09E785538A2536C01A8418F4755A9DEF8800296F3AE53F7CBA2C7E42D280C37D35C6D02005E18BBFF1FF969C7B7FF16F1FD5AEB182631E76D734C8B98720879854CBD4F1D1505D1DB2D2206DCFCCA0C9944F73484297AADC0B7151D08FFAFFE0041B8AA7536AC670F5A3DB34E31862153276AD3D18B19F4D0680B04F6516BC14175102D9E1B048F6A7934401151919734B490673D0E79BA6C5D6673EEC0760A86B9C9B873FA024F1A667238F6B6C935EB5C95FE5F6071497125D8B5A5F830BEB478A1D539DD75BE0556BAE5556E060B673D46B83A13AF3E925F41783A68ED558C3561A4E024978EF0E2D6997F1CDADBD8F384DE89598B22205A848A3C8380795F8873CF377492EFBF5C21580E40CA35FDF1CBB805451AAD9730EDE8AE154585C991BEFE185EF271987584E6C0591D5576E6177C8BCC97FD5E397CDE5F5D99CEFE0FF0E6CE109FF97CBDFF97CBBEC9E40634DD3D921BF74D174B8125AE313A725F721842C71F9EEBF23FEFA34DC6E0843A864B7DB3768A6803DE52FDB100F9BD1461E0406F2002557E303E71F5ADF30599CAC287983F2A4DB905F85DB2116230714A774B47DF70F47BAA76EE10C69A3594A4F6CCDC581201F21804944822FB49E272BCCC3150BCC3169A993F1A034B16765126CCB1058062122CE229159B9FD10B68A75D6EE0856BDAEDEFC2ED22CF7F654071B144012C10437FB9DA20B0AAC66A5A521B943DEE96357DAB63F715F0DB9E97CE96E7AA767CF67DE5BDD5BED5BA7583A7054EFAF7897B3566AFEAF86B4F0DDB35E1BC38958867CB3547699C9C2E3DFD56C795992A12D79D06D4AF1A7711059EBA7677A5BA50F168FDBE5AC6B42D06D05B94F68608AA6A16EC31EC0562C4C2B39576C87B4FCAA6239494319BA13EDD2A679841E273D8D55856D4A0601835827B340573FD06FB3315EF5B552A32945D919A04C77F8D27F6E66665249A0062DCA6F62F0B755487F35A3643E4B3DE031B766E9D4EA97ED866B3CA060D44B06C0915387BFEF90E42AA4D3E257D5B5992AEFE482A20C3395B6D7174F9ACC3E87941D0A84876561D6BF979851ECC6C62E3F5CFA6C1B41DD351A59EA3606C07FA590AE5B623DA3D0FF2789B83BD95E09FEACE0FF6902261644B6C67634D9481112CB522EA5BA3FA1317B178417E9F562A5B37DE6AF3B2B06714C5F71E3DDD768B34F5F4A93BD1DC5D53E0BD2783F536828B8FFAAE82BF71E09E6C71000EFC046473BFF0B67A1757C7D48C54A5269173998C86A0BB6F9CB4DE8EFA55AB5B57AE6CB7987C2A1558FBBE5E9930037B16082223482376221AD61582D8B0FDFD84BFC2BDA1E912560D3E4E18319CAC87B10BF7EB0DF23CDCD6CD24EDFC2F1AE606D1EBB89C2ACD76708206FE9FBADFE9560B40968B98A6A1687BC6686775C0161694B82892235A590D6847F780FFAC4E5F999B53E78548433B52CB8161339FF47126107643598C41407DAD216A2C88FAD38F4FC4D5618C19BFEF2C4DC53893B06DC504F5EBA4ABD40B9E00D53CCCEE5D9DC885C459DD81BB993613FDAEE58FC9EF71831DF4D30D4E633E294A505301F843E99476BF15EA7A23C82206D4B9BC60AEDB9DA374223CE1685BF1BA1ED439248FD333CF183EED97DD42B80995EE92528B4C185595158B28BBC5CF58548FF0D063925958AD322C0589A7873726489D2B523A00447657E0B512BF83BB0284C06CFAECB50F6490A25837F7DF96F8F80C2F3EA742EBEE45D58F8B528866CE39D3108AD595705ECD280DD6A999193448D8430ED0F556C770A78617D27A755817F585C4C7BCA7446418323E40657F7F7DE79565F8F4BE73CD9867D9AE11C87AF9D14AC13CE0D205AD57A11D9918F323D7FE796645503997D7C045EABAAC7560AD1BCF7B86F608D00F8A3C6D08BB9E9A03798B8027EEDCD0CCB10A422BB6404AAA8131C20AABFD4060B9DBE3FD00C51D93C001FDFFBBC30D5722CDA6BD191AD8DFD558DCFD6E40BE792DAEA19B120FAC7A25C5A9CEF23A528A7214B66C95006D0EBF19AD1BB42516118DBF2C054F44427C64A6B5209083A15FB559EB861C4E961EDAA0707A9842FAF86EF286DCAD96C9F5E3456B112311A402E676C37052BE7B443522D3143FDA598984A4934301506E93AF0CDE52AC163A72248D2E6CB3CCDAB43F96DB9015AEFEA42FD1258AA2C1DFD08EB648FE3F884FBEE28B809CBFCC474B007B7ED411F08DABD1BFB9B70B4797798A468D3D20D4D668191C46B2684A2BCD8F18C8AC60982B62B6A3531625C147A021C28E1E2A09D0134683FB84CE47A82123FCCECA88B73C1CE334CACC1253E9E22A86314EAC5C8873DE9D3D4A82F24EC7AC5CA62BD49461585C587025F0B0A41EEC57B7E7ADBADA26CE843E630960ABCD53D9148F33E877E8692CCACBEB70FD4AA303E2951A4EEBF9B51413625AE96920D66CBEAFDCF891F2811A6C1BDB856F0BC617315E4F049262B1094801DF5A15530D0626C53C0EDE52AF605EE302E79D9DE901F5DF379F6C2BAEA2901680E3A0D7C67B4947571D4D4540605403CE0F935419DF72885DC0C6EE577BFD2E492227ABA48965D7005161FBA6CA9E5A9E7B36E5EA1A16C9DC68EC61C34B2D35DD412547A6B63B73AEEBB57BCFFBCF7D71FEBE45DC84B33B0D514BA3699D065EB98A353429E21A4ACB8617A9AC7C2310729828D70685A367274144DDA92B488795F2AD7D1D9F1305994F9620D4E758FED72013BFFD48736CBFB5E6A004091D82741326C4072FDD9F91798D6C3465B239320F3328B026283C07B39B6808D1033BFF1148A8B82BB5BD3747DFE92339EA9966AE3BAF585285BB9FE91439B9AB83559808DAA018C0C47A599A8D3B95806EB77906F4AAC112C549B8435B688AD964EB47A20AB5410D65A0762D9175316C11BBEFF7AB64BDF461BC18DE3DD46468F1B1A7E35A126646841C4C0A4333139CF60664A6EAB1AFF156021061040906ECD91F08EAC1F29F3F1D0FE76C142621790282C0338475E5D2CD70CA29EA2C6F38C5E3B825ACC42510510BF6432364CAD7EEA8163A8FEA4B0A96E98FA76F424A835373E269AE9E662D3492724FF9B8029FF069054A6FA89426315C825C12A3E4B1EC0DB6341448CC455F8EB52439D9C9051A748BB49538E6707D37E2E4A0E760685E5F33A0E1C3122346AC6B92D9D759A853397BED06F82BE3CC3931C2E4BC1A054F7825B3204833D07600F49CC2BEA3ECD1BFF0BDF4D0A7C0E50FF1C0FF442EB756DBAEC8B7AA48CB9903F34BFDAC858705C431B141665A31AF8AB8E8D1BB0EAE9669A6B412EAAA9647302C519C0539F2196431E79AC94BDED1072ED62E56511365EAC56B40574B6E32D1121D9118BB5BDFCD69C25B4ACB35CE82D6E190E6B733AEF8FDB44B514EAFF558CB862872EEEEC7B04AC916E6373A07667DEB35E580474514DC568FB5AB6CBEB1C5D813349E0A0751D44B4324579EAA77329A8C9FCC5A17CCDA3B5E35C2FE758B63A6F512CF5B8EFA34CA03EC5ED4775FC2DC3B70DC5C1A34475B957B3D374F6300AD8CA08597D81D8B942158A4F747DB35AE3BCCC63E510E0C036760EF56FAE018998F5B39DB78CC1B8952E5EE0CB385028E38CA5C6E49E22012BBEE7BC12D001B064C52F6C20462EA2CE648AE4BA120F3B96E3AA76330FC9F25EA1CCEC5FCFE7BD0EF8403D08A666819CDE92192D8D95ABDE00C1C3F947CD518FFCA39716D4C93285349C94B5C6319AB650911142B260D68B3F76F212EF1C696AE098FA258609E5759A572B685A8493D2F0CAFF72FA1AFCD1198CF6BADB74FDAA78236508010EBB604C6236AD22A06A34EB4EF600755B6F8E36BF507B35FFCE95FBACF55269F859800CA92239F29D34FEF031CEB93A053F98D0553B45A5B04A9AEFA366BA3A17482DE17EBE2D3EDECC6314D37C7CCE9192E9BDE0411A3D87CB64803998AA68036921E76A5A55AC9875B72EE380427AA3C37DB344879FD90";
//        String dom = "00000000000000000000000000000000";
//        EcCardInfo ecCardInfo = EcDecryUtil.decryptEcData(privateKey, enc, dom);
//        System.out.println("" + JSONObject.toJSONString(ecCardInfo));
//    }


    @Before
    public void before() {
        System.out.println("before");
    }

    @After
    public void after() {
        System.out.println("after");
    }

}
