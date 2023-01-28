package com.cas.encryption.sm3;

import cn.hutool.crypto.SmUtil;
import com.cas.des.des3_ecb.HexConverter;
import org.junit.Test;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/9/7 3:39 下午
 * @desc 摘要加密算法SM3, Hash算法
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
        String digestHex = SmUtil.sm3("hello");
        System.out.println(digestHex.toUpperCase());
    }

    // becbbfaae6548b8bf0cfcad5a27183cd1be6093b1cceccc303d9c61d0a645268
    // becbbfaae6548b8bf0cfcad5a27183cd1be6093b1cceccc303d9c61d0a645268
    // BECBBFAAE6548B8BF0CFCAD5A27183CD1BE6093B1CCECCC303D9C61D0A645268

    @Test
    public void test1() {
        String data = "80E640002200000AA0000006320101060C020012EF10A10EA10C4F0AA0000006320101050C0200";
        System.out.println(HexConverter.int2HexString(HexConverter.hexString2ByteArray(data).length));
        System.out.println(HexConverter.byteArray2HexString(HexConverter.int2ByteArray(39, 1)));
        System.out.println(HexConverter.byteArray2HexString(HexConverter.int2ByteArray(39, 2)));
        System.out.println(HexConverter.byteArray2HexString(HexConverter.int2ByteArray(256, 2)));
        System.out.println(HexConverter.toHexString(256, 2));
    }


}
