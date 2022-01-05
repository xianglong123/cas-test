package com.cas.binary;

import com.cas.util.ByteUtil;
import com.cas.util.HexConverter;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/12/22 3:00 下午
 * @desc
 */
public class XorTest {

    public static void main(String[] args) {
//        String fl1 = "B838C4547FBD3C641AA2BCB3CF9C4111";
//        String fl2 = "C111AA10BD3C4FCC37A3CAF5ECC12BA4";
        String data = "79296E44C28173A82D017646235D6AB5";
        String padding = "0000000000000000";
        String jyz = "56138966";

//        String string = xorString(fl1, fl2);
//        System.out.println(string);

        byte[] macSessionKey = HexConverter.hexString2ByteArray(data);
        byte[] kap = ByteUtil.subArray(macSessionKey, 0, 8);
        byte[] kbp = ByteUtil.subArray(macSessionKey, 8, 16);
        byte[] key = ByteUtil.contactArray(kap, kbp);
        key = ByteUtil.contactArray(key, kap);
//        System.out.println(HexConverter.byteArray2HexString(encryptDES(kap, HexConverter.hexString2ByteArray(padding))));
//        System.out.println(HexConverter.byteArray2HexString(encryptDES(HexConverter.hexString2ByteArray(string), HexConverter.hexString2ByteArray(padding))));
//        System.out.println(HexConverter.byteArray2HexString(encryptDES(key, HexConverter.hexString2ByteArray(padding))));
        System.out.println(HexConverter.byteArray2HexString(encryptDES(HexConverter.hexString2ByteArray(padding), key)));
    }
//79296E44C28173A82D017646235D6AB5
//79296E44C28173A82D017646235D6AB5
// 56138966 A50D3482
// 56138966

    private static String xorString(String a, String b) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 16; i ++) {
            String xor = xor(a.substring(i * 2, (i * 2) + 2), b.substring(i * 2, (i * 2) + 2));
            stringBuilder.append(xor);
        }
        return stringBuilder.toString();
    }

    public static byte[] encryptDES(byte[] encryptString, byte[] key) {
        try {
            Cipher instance = Cipher.getInstance("DESede/ECB/NoPadding");
            instance.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "DESede"));
            return instance.doFinal(encryptString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String xor(String strHex_X,String strHex_Y){
        //将x、y转成二进制形式
        StringBuilder anotherBinary= new StringBuilder(Integer.toBinaryString(Integer.valueOf(strHex_X, 16)));
        StringBuilder thisBinary= new StringBuilder(Integer.toBinaryString(Integer.valueOf(strHex_Y, 16)));
        StringBuilder result = new StringBuilder();
        //判断是否为8位二进制，否则左补零
        if(anotherBinary.length() != 8){
            for (int i = anotherBinary.length(); i <8; i++) {
                anotherBinary.insert(0, "0");
            }
        }
        if(thisBinary.length() != 8){
            for (int i = thisBinary.length(); i <8; i++) {
                thisBinary.insert(0, "0");
            }
        }
        //异或运算
        for(int i=0;i<anotherBinary.length();i++){
            //如果相同位置数相同，则补0，否则补1
            if(thisBinary.charAt(i)==anotherBinary.charAt(i))
                result.append("0");
            else{
                result.append("1");
            }
        }
        System.out.println("code：" + result);
        return Integer.toHexString(Integer.parseInt(result.toString(), 2));
    }

}
