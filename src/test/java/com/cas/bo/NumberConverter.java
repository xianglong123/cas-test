//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cas.bo;


public class NumberConverter {
    public NumberConverter() {
    }

    public static byte[] int2ByteArray(int i, int byteCount) {
        byte[] bytes = null;
        if (byteCount >= 1 && byteCount <= 4) {
            long maxValue = (long)Math.pow(2.0D, (double)(8 * byteCount)) - 1L;
            if (i >= 0 && (long)i <= maxValue) {
                String hexString = HexConverter.int2HexString(i, byteCount * 2);
                bytes = HexConverter.hexString2ByteArray(hexString);
            } else {
            }
        } else {
        }
        return bytes;
    }

    public static byte[] int2ByteArrayWithNecessaryLength(int i) {
        String hexString = HexConverter.int2HexStingWithNecessaryEvenLength(i);
        return HexConverter.hexString2ByteArray(hexString);
    }

    public static int byte2Int(byte src) {
        return HexConverter.hexString2Int(HexConverter.byte2HexString(src));
    }

    public static int byteArray2Int(byte[] src) {
        String hexString = HexConverter.byteArray2HexString(src);
        return HexConverter.hexString2Int(hexString);
    }

}
