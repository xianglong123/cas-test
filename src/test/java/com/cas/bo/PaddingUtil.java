package com.cas.bo;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

;

public class PaddingUtil {
    private static final Logger log = LoggerFactory.getLogger(PaddingUtil.class);

    public PaddingUtil() {
    }

    public static byte[] olsysDesPadding(byte[] src) {
        int mod = src.length % 8;
        if (mod != 0) {
            for (int i = 0; i < 8 - mod; ++i) {
                src = ByteUtil.append(src, (byte) 0);
            }
        }

        return src;
    }

    public static byte[] olsysMacPadding(byte[] src) {
        int mod = src.length % 8;
        if (mod != 0) {
            src = ByteUtil.append(src, (byte) -128);
            mod = src.length % 8;
            if (mod != 0) {
                for (int i = 0; i < 8 - mod; ++i) {
                    src = ByteUtil.append(src, (byte) 0);
                }
            }
        } else {
            src = ByteUtil.contactArray(src, HexConverter.hexString2ByteArray("8000000000000000"));
        }

        return src;
    }

/*	public static String leftPad(String str, int size, char padChar) {
		return StringUtils.leftPad(str, size, padChar);
	}*/

/*	public static String rightPad(String str, int size, char padChar) {
		return StringUtils.rightPad(str, size, padChar);
	}*/

    public static int getPaddingCount8(int rawDataLength) {
        int mod = rawDataLength % 8;
        return mod == 0 ? 0 : 8 - mod;
    }

    public static int getPaddingCount16(int rawDataLength) {
        int mod = rawDataLength % 16;
        if (mod == 0) {
            return 0;
        } else {
            return 16 - mod;
        }
    }

    public static int getPaddingCount48(int rawDataLength) {
        int mod = rawDataLength % 48;
        if (mod == 0) {
            return 0;
        } else {
            return 48 - mod;
        }
    }

    public static byte[] getPaddingBytes(int rawDataLength, byte toPad) {
        int paddingCount = getPaddingCount16(rawDataLength);
        return getPadByPaddingCount(toPad, paddingCount);
    }

    public static byte[] getPadByPaddingCount(byte toPad, int paddingCount) {
        byte[] paddingBytes = new byte[0];

        for (int i = 0; i < paddingCount; ++i) {
            paddingBytes = ByteUtil.append(paddingBytes, toPad);
        }

        return paddingBytes;
    }

    public static byte[] getRightPadByPaddingCount(byte[] toPad, int paddingCount) {
        byte[] paddingBytes = new byte[]{0};
        if (paddingCount < 1) {
            return toPad;
        } else {
            for (int i = 1; i < paddingCount; ++i) {
                paddingBytes = ByteUtil.contactArray(paddingBytes, new byte[]{0});
            }

            return ByteUtil.contactArray(toPad, paddingBytes);
        }
    }

    public static byte[] encryptPadding(byte[] src) {
        byte[] paddingBytes = getPaddingBytes(src.length, (byte) 0);
        return ByteUtil.contactArray(src, paddingBytes);
    }

    public static byte[] padding(byte[] src, byte padByte, boolean isRight) {
        byte[] paddingBytes = getPaddingBytes(src.length, padByte);
        return isRight ? ByteUtil.contactArray(src, paddingBytes) : ByteUtil.contactArray(paddingBytes, src);
    }

    public static byte[] fixedPadding(byte[] src, int fixedLength, byte padByte, boolean isRight) {
        byte[] paddingBytes = getFixedPaddingBytes(src, fixedLength, padByte);
        return isRight ? ByteUtil.contactArray(src, paddingBytes) : ByteUtil.contactArray(paddingBytes, src);
    }

    public static byte[] getFixedPaddingBytes(byte[] src, int fixedLength, byte toPad) {
        int paddingCount = fixedLength - src.length;
        return getPadByPaddingCount(toPad, paddingCount);
    }

    public static byte[] macPadding(byte[] src) {
        byte[] paddingBytes = getPaddingBytes(src.length, (byte) 32);
        return ByteUtil.contactArray(src, paddingBytes);
    }

    public static byte[] descryptUnpadding(byte[] decryptedBytes) {
        return unpadding(decryptedBytes, 0);
    }

    public static byte[] unpadding(byte[] decryptedBytes, int paddingByte) {
        int unpaddingPos = decryptedBytes.length;

        for (int i = decryptedBytes.length; i > 0; --i) {
            if (decryptedBytes[i] != paddingByte) {
                unpaddingPos = i;
                break;
            }
        }

        return ByteUtil.subArray(decryptedBytes, 0, unpaddingPos);
    }

    public static byte[] padding(byte[] paddingSource) {
        int paddingCount = getPaddingCount48(paddingSource.length);
        byte[] paddingBytes;
        if (paddingCount == 0) {
            // 0000000000000000
            // 涉及excAuth
            paddingBytes = HexConverter.hexString2ByteArray("8000000000000000");
        } else if (paddingCount == 1) {
            paddingBytes = HexConverter.hexString2ByteArray("80");
        } else {
            byte[] firstPaddingBytes = HexConverter.hexString2ByteArray("80");
            byte[] secondPaddingBytes = getPadByPaddingCount((byte) 0, paddingCount - 1);
            paddingBytes = ByteUtil.contactArray(firstPaddingBytes, secondPaddingBytes);
        }

        byte[] data = ByteUtil.contactArray(paddingSource, paddingBytes);
        return ByteUtil.contactArray(HexConverter.int2ByteArray(paddingCount, 1), data);
    }

    public static byte[] scp02Padding(byte[] paddingSource) {
        int paddingCount = getPaddingCount8(paddingSource.length);
        byte[] paddingBytes;
        if (paddingCount == 0) {
            // 0000000000000000
            // 涉及excAuth
            paddingBytes = HexConverter.hexString2ByteArray("8000000000000000");
        } else if (paddingCount == 1) {
            paddingBytes = HexConverter.hexString2ByteArray("80");
        } else {
            byte[] firstPaddingBytes = HexConverter.hexString2ByteArray("80");
            byte[] secondPaddingBytes = getPadByPaddingCount((byte) 0, paddingCount - 1);
            paddingBytes = ByteUtil.contactArray(firstPaddingBytes, secondPaddingBytes);
        }

        return ByteUtil.contactArray(paddingSource, paddingBytes);
    }

    public static byte[] scp02Padding16(byte[] paddingSource) {
        int paddingCount = getPaddingCount16(paddingSource.length);
        byte[] paddingBytes;
        if (paddingCount == 0) {
            // 0000000000000000
            // 涉及excAuth
            paddingBytes = HexConverter.hexString2ByteArray("80000000000000000000000000000000");
        } else if (paddingCount == 1) {
            paddingBytes = HexConverter.hexString2ByteArray("80");
        } else {
            byte[] firstPaddingBytes = HexConverter.hexString2ByteArray("80");
            byte[] secondPaddingBytes = getPadByPaddingCount((byte) 0, paddingCount - 1);
            paddingBytes = ByteUtil.contactArray(firstPaddingBytes, secondPaddingBytes);
        }

        return ByteUtil.contactArray(paddingSource, paddingBytes);
    }

    public static byte[] pkcs1Padding(byte[] digest, String digestAlgorithmID, int targetLength) {
        String digestStr = ByteUtil.toHexString(digest);
        String header = "0001";
        int currentLength = (header.length() + digestStr.length() + digestAlgorithmID.length()) / 2 + 1;
        int paddingLength = targetLength - currentLength;
        StringBuilder padding = new StringBuilder();

        for (int i = 0; i < paddingLength; ++i) {
            padding.append("FF");
        }

        StringBuilder result = new StringBuilder();
        result = result.append(header).append(padding).append("00").append(digestAlgorithmID).append(digestStr);
        return HexConverter.hexString2ByteArray(result.toString());
    }

    public static byte[] pad(byte[] src, int startIndexInclusive, int endIndexExclusive, byte fillByte) {
        byte[] padded = src;
        log.debug("对byte数组进行填充，填充byte的索引值：[" + startIndexInclusive + "," + endIndexExclusive + ")");
        log.debug("源byte数组最大索引值：" + (src.length - 1));
        log.debug("期待的byte数组最小索引值：" + endIndexExclusive);
        if (src.length < endIndexExclusive) {
            log.debug("需要重新创建byte数组");
            padded = new byte[endIndexExclusive];
            System.arraycopy(src, 0, padded, 0, startIndexInclusive);
        }

        for (int i = startIndexInclusive; i < endIndexExclusive; ++i) {
            padded[i] = fillByte;
        }

        return padded;
    }

    public static byte[] getPadding(byte[] paddingSource) {
        int paddingCount = getPaddingCount16(paddingSource.length);
        byte[] paddingBytes = null;
        if (paddingCount == 0) {
            paddingBytes = HexConverter.hexString2ByteArray("80000000000000000000000000000000");
        } else if (paddingCount == 1) {
            paddingBytes = HexConverter.hexString2ByteArray("80");
        } else {
            byte[] firstPaddingBytes = HexConverter.hexString2ByteArray("80");
            byte[] secondPaddingBytes = getPadByPaddingCount((byte) 0x00, paddingCount - 1);
            paddingBytes = ByteUtil.contactArray(firstPaddingBytes, secondPaddingBytes);
        }
        return paddingBytes;
    }

}
