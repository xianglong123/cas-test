package com.cas.sm3;

import cn.hutool.crypto.SmUtil;
import org.junit.Test;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/9/7 3:39 下午
 * @desc 摘要加密算法SM3
 *
 * SM3密码杂凑（哈希、散列）算法给出了杂凑函数算法的计算方法和计算步骤，并给出了运算示例。此算法适用于商用密码应用中的数字签名和验证，消息认证码的生成与验证以及随机数的生成，可满足多种密码应用的安全需求。在SM2，SM9标准中使用。
 * 此算法对输入长度小于2的64次方的比特消息，经过填充和迭代压缩，生成长度为256比特的杂凑值，其中使用了异或，模，模加，移位，与，或，非运算，由填充，迭代过程，消息扩展和压缩函数所构成。具体算法及运算示例见SM3标准。
 *
 *
 * hash算法你懂吧，就是这个数据怎么算结果都是这个还是厉害的
 */
public class HutoolSm3Test {

    @Test
    public void test() {
        String digestHex = SmUtil.sm3("aaaaa");
        System.out.println(digestHex);
    }


}
