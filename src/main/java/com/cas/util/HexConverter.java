//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cas.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexConverter {
    private static final Logger logger = LoggerFactory.getLogger(HexConverter.class);
    private static final char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public HexConverter() {
    }

    public static String encode(byte[] src) {
        return Hex.encodeHexString(src);
    }

    public static byte int2Byte(int i) {
        return int2ByteArray(i, 1)[0];
    }

    public static byte[] int2ByteArray(int i, int byteCount) {
        if (byteCount >= 1 && byteCount <= 4) {
            long maxValue = (long) Math.pow(2.0D, (double) (8 * byteCount)) - 1L;
            if (i >= 0 && (long) i <= maxValue) {
                String hexString = int2HexString(i, byteCount * 2);
                return hexString2ByteArray(hexString);
            } else {
                return null;
            }
        } else {
            return null;
        }
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

    public static String decryptDES(String decryptString, String decryptKey) {
        try {
            Cipher instance = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            instance.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "DESede"));
            byte[] decryptedData = instance.doFinal(Hex.decodeHex(decryptString.substring(6).toCharArray()));
            return new String(decryptedData);
        } catch (Throwable e) {
            return null;
        }
    }

    public static void validate(String src) {
        if (null == src) {
            throw new IllegalArgumentException("src is null");
        } else if (0 != src.length() % 2) {
            logger.error("错误的源数据：" + src);
            throw new IllegalArgumentException("src length is odd");
        } else {
            for (int i = 0; i < src.length(); ++i) {
                if (!isHexChar(src.charAt(i))) {
                    logger.error("错误的源数据：" + src);
                    throw new IllegalArgumentException("illegal char " + src.charAt(i) + " at " + 1);
                }
            }

        }
    }

    public static boolean isHexChar(char hexChar) {
        hexChar = Character.toUpperCase(hexChar);
        char[] var1 = HEX_CHARS;
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            char c = var1[var3];
            if (c == hexChar) {
                return true;
            }
        }

        return false;
    }

    public static boolean isHexString(String hexString) {
        String strExp = "^[a-fA-F0-9]+$";
        Pattern pattern = Pattern.compile(strExp);
        Matcher matcher = pattern.matcher(hexString);
        return matcher.matches();
    }

    public static String toHexString(int i) {
        return toHexString(i, 1);
    }

    public static String toHexString(int i, int byteCount) {
        if (byteCount >= 1 && byteCount <= 4) {
            int maxValue = (int) Math.pow(2.0D, (double) (8 * byteCount)) - 1;
            if (i >= 0 && i <= maxValue) {
                String hex = Integer.toHexString(i);
                if (0 != hex.length() % 2) {
                    hex = "0" + hex;
                }

                int targetLength = byteCount * 2;
                int paddingLength = targetLength - hex.length();
                StringBuffer sb = new StringBuffer();

                for (int loop = 0; loop < paddingLength; ++loop) {
                    sb.append('0');
                }

                return sb.append(hex).toString();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    public static String encode(byte src) {
        return encode(new byte[]{src});
    }

    public static String byte2HexString(byte src) {
        return byteArray2HexString(new byte[]{src});
    }

    public static int byte2Int(byte src) {
        return hexString2Int(byte2HexString(src));
    }

    public static int byteArray2Int(byte[] src) {
        String hexString = byteArray2HexString(src);
        return hexString2Int(hexString);
    }

    public static String byteArray2HexString(byte[] src) {
        return null == src ? null : Hex.encodeHexString(src).toUpperCase();
    }

    public static byte hexString2Byte(String hex) {
        validate(hex);
        if (2 != hex.length()) {
            return '0';
        } else {
            return hexString2ByteArray(hex)[0];
        }
    }

    public static byte[] hexString2ByteArray(String data) {
        if (StringUtils.isEmpty(data)) {
            return new byte[0];
        } else {
            validate(data);
            if (0 != data.length() % 2) {
                return null;
            } else {
                return ByteUtil.hexStringToBytes(data);
            }
        }
    }

    public static String int2HexString(int i) {
        return int2HexString(i, 2);
    }

    public static String int2HexString(int i, int stringLength) {
        if (stringLength >= 1 && stringLength <= 8 && 0 == stringLength % 2) {
            long maxValue = (long) Math.pow(16.0D, (double) stringLength) - 1L;
            if (i >= 0 && (long) i <= maxValue) {
                String hex = int2HexStingWithNecessaryEvenLength(i);
                StringBuffer sb = new StringBuffer();

                for (int loop = hex.length(); loop < stringLength; ++loop) {
                    sb.append('0');
                }

                return sb.append(hex).toString().toUpperCase();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String int2HexStingWithNecessaryEvenLength(int i) {
        return long2HexStingWithNecessaryEvenLength((long) i);
    }

    public static String long2HexString(long l, int stringLength) {
        if (stringLength >= 1 && stringLength <= 16 && 0 == stringLength % 2) {
            long maxValue = (long) Math.pow(16.0D, (double) stringLength) - 1L;
            if (l >= 0L && l <= maxValue) {
                String hex = long2HexStingWithNecessaryEvenLength(l);
                StringBuffer sb = new StringBuffer();

                for (int loop = hex.length(); loop < stringLength; ++loop) {
                    sb.append('0');
                }

                return sb.append(hex).toString().toUpperCase();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static String long2HexStingWithNecessaryEvenLength(long l) {
        String hex = Long.toHexString(l);
        if (0 != hex.length() % 2) {
            hex = "0" + hex;
        }

        return hex;
    }

    public static int hexString2Int(String src) {
        validate(src);
        if (8 < src.length()) {
            return 0;
        } else {
            if (8 == src.length()) {
                char c = src.charAt(0);
                int i = hexString2Int("0" + c);
                if (8 <= i) {
                    return 0;
                }
            }

            return Integer.parseInt(src, 16);
        }
    }

    public static String hexEncode(byte[] input) {
        return Hex.encodeHexString(input);
    }

    public static byte[] hexDecode(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException var2) {
            throw new IllegalStateException("Hex Decoder exception", var2);
        }
    }
}
