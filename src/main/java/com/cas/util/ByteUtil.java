//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cas.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ByteUtil {
    private static final Logger log = LoggerFactory.getLogger(ByteUtil.class);

    public ByteUtil() {
    }

    public static byte[] contactArray(byte[] src1, byte[] src2) {
        if (src1 != null && src2 != null) {
            byte[] dest = new byte[src1.length + src2.length];
            System.arraycopy(src1, 0, dest, 0, src1.length);
            System.arraycopy(src2, 0, dest, src1.length, src2.length);
            return dest;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static byte[] append(byte[] src, byte b) {
        return contactArray(src, new byte[]{b});
    }

    public static void splitArray(byte[] src, byte[] dest1, byte[] dest2, int pos) {
        if (src != null && dest1 != null && dest2 != null) {
            if (src.length != 0 && pos <= src.length) {
                dest1 = new byte[pos];
                dest2 = new byte[src.length - pos];
                System.arraycopy(src, 0, dest1, 0, pos);
                System.arraycopy(src, pos, dest2, 0, src.length - pos);
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static byte[] subArray(byte[] src, int start, int end) {
        log.debug("start is :" + start);
        log.debug("end is : " + end);
        log.debug("src.length is :" + src.length);
        if (start >= 0 && start <= end && end <= src.length) {
            byte[] subBytes = new byte[end - start];
            System.arraycopy(src, start, subBytes, 0, subBytes.length);
            return subBytes;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static byte[] leftSubArray(byte[] src, int pos) {
        return subArray(src, 0, pos);
    }

    public static byte[] rightSubArray(byte[] src, int pos) {
        return subArray(src, pos, src.length);
    }

    public static int arrayIndexOf(byte[] src, byte toFind) {
        int index = -1;

        for(int i = 0; i < src.length; ++i) {
            if (src[i] == toFind) {
                index = i;
                break;
            }
        }

        return index;
    }

    /** @deprecated */
    @Deprecated
    public static String arrayHexString(byte[] src, String delim) {
        if (delim == null) {
            delim = "";
        }

        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < src.length; ++i) {
            byte byteNumber = src[i];
            sb.append(hexString(byteNumber));
            sb.append(delim);
        }

        String toPrint = sb.toString();
        int start = toPrint.length() - delim.length();
        if (delim.equals(toPrint.substring(start, toPrint.length()))) {
            toPrint = toPrint.substring(0, start);
        }

        return toPrint;
    }

    /** @deprecated */
    @Deprecated
    public static String arrayShortHexString(byte[] src, String delim) {
        return arrayHexString(src, delim).replace("0x", "");
    }

    /** @deprecated */
    @Deprecated
    public static String arrayShortHexString(byte[] src) {
        return arrayHexString(src, (String)null).replace("0x", "");
    }

    /** @deprecated */
    @Deprecated
    private static String hexString(byte byteNumber) {
        int toStr = byteNumber;
        if (byteNumber < 0) {
            toStr = byteNumber + 256;
        }

        String byteStr = Integer.toHexString(toStr);
        if (byteStr.length() == 1) {
            byteStr = "0" + byteStr;
        }

        return "0x" + byteStr.toUpperCase();
    }

    /** @deprecated */
    @Deprecated
    public static byte[] hexStringToBytes(String hexStr) {
        int length = hexStr.length();
        if (length % 2 != 0) {
            throw new IllegalArgumentException();
        } else {
            hexStr = hexStr.toUpperCase();
            byte[] outArray = new byte[length / 2];

            for(int i = 0; i < length; i += 2) {
                char li = hexStr.charAt(i);
                char lo = hexStr.charAt(i + 1);
                if (li < '0' || li > 'F' || lo < '0' || lo > 'F') {
                    throw new IllegalArgumentException();
                }

                outArray[i / 2] = (byte)Integer.parseInt(String.valueOf(new char[]{li, lo}), 16);
            }

            return outArray;
        }
    }

    public static byte[] asciiToBcd(String input) {
        byte[] bcd = null;

        try {
            byte[] ascii = input.getBytes("US-ASCII");
            if (ascii.length % 2 != 0) {
                throw new IllegalArgumentException();
            }

            bcd = new byte[ascii.length / 2];

            for(int i = 0; i < ascii.length; i += 2) {
                if (ascii[i] < 48 || ascii[i] > 57) {
                    throw new IllegalArgumentException();
                }

                int temp = (ascii[i] & 15) << 4;
                if (temp > 127) {
                    temp -= 256;
                }

                byte hi = (byte)temp;
                byte lo = (byte)(ascii[i + 1] & 15);
                bcd[i / 2] = (byte)(hi | lo);
            }
        } catch (UnsupportedEncodingException var7) {
            var7.printStackTrace();
        }

        return bcd;
    }

    public static byte[] asciiToCnBcd(String input, int bcdBytesLength) {
        if (input.length() > 2 * bcdBytesLength) {
            throw new IllegalArgumentException("the input String is too long");
        } else {
            input = input.toUpperCase();
            int paddingBytesCount = 2 * bcdBytesLength - input.length();

            for(int i = 0; i < paddingBytesCount; ++i) {
                input = input + 'F';
            }

            int index = input.indexOf(70);
            byte[] cnBcd;
            if (index != -1) {
                String forCheck;
                if (index < input.length() - 1) {
                    forCheck = input.substring(index + 1);

                    for(int i = 0; i < forCheck.length(); ++i) {
                        if (forCheck.charAt(i) != 'F') {
                            throw new IllegalArgumentException("the input String is not valid to convert to CnBcd");
                        }
                    }
                }

                forCheck = null;
                String right = null;
                if (index % 2 != 0) {
                    forCheck = input.substring(0, index - 1);
                    right = input.substring(index - 1);
                } else {
                    forCheck = input.substring(0, index);
                    right = input.substring(index);
                }

                byte[] leftBytes = asciiToBcd(forCheck);
                byte[] rightBytes = hexStringToBytes(right);
                cnBcd = contactArray(leftBytes, rightBytes);
            } else {
                cnBcd = asciiToBcd(input);
            }

            return cnBcd;
        }
    }

    public static String bcdToAscii(byte[] bcd) {
        byte[] ascii = new byte[2 * bcd.length];

        for(int i = 0; i < bcd.length; ++i) {
            byte hi = (byte)(bcd[i] >> 4);
            byte lo = (byte)(bcd[i] & 15);
            if (hi < 0 || hi > 9 || lo < 0 || lo > 9) {
                throw new IllegalArgumentException();
            }

            ascii[2 * i] = (byte)(hi + 48);
            ascii[2 * i + 1] = (byte)(lo + 48);
        }

        return new String(ascii);
    }

    public static byte[] intToCnBcd(int srcInt, int bcdByteCount) {
        if (srcInt < 0) {
            throw new IllegalArgumentException("srcInt should not be less than 0");
        } else {
            String numStr = String.valueOf(srcInt);
            int digitCount = numStr.length();
            int i;
            byte[] bcd;
            if (digitCount % 2 == 0) {
                if (digitCount / 2 > bcdByteCount) {
                    throw new IllegalArgumentException("The digit count of srcInt should not be larger than bcdByteCount");
                }

                bcd = asciiToBcd(numStr);
            } else {
                if ((digitCount + 1) / 2 > bcdByteCount) {
                    throw new IllegalArgumentException("The digit count of srcInt should not be larger than bcdByteCount");
                }

                String leftDigitStr = numStr.substring(0, digitCount - 1);
                bcd = asciiToBcd(leftDigitStr);
                i = Integer.parseInt(numStr.substring(digitCount - 1));
                int temp = (i & 15) << 4;
                if (temp > 127) {
                    temp -= 256;
                }

                byte hi = (byte)temp;
                byte lo = 15;
                byte last = (byte)(hi | lo);
                bcd = contactArray(bcd, new byte[]{last});
            }

            if (bcd == null) {
                throw new IllegalStateException();
            } else {
                if (bcd.length < bcdByteCount) {
                    byte[] supplement = new byte[bcdByteCount - bcd.length];

                    for(i = 0; i < supplement.length; ++i) {
                        supplement[i] = -1;
                    }

                    bcd = contactArray(bcd, supplement);
                }

                return bcd;
            }
        }
    }

    public static int cnBcdToInt(byte[] cnBcd) {
        String toIntStr = cnBcdToAscii(cnBcd);
        return Integer.parseInt(toIntStr);
    }

    public static String cnBcdToAscii(byte[] cnBcd) {
        byte[] splitedBytes = new byte[2 * cnBcd.length];

        int splitIndex;
        for(int i = 0; i < cnBcd.length; ++i) {
            splitIndex = cnBcd[i];
            if (splitIndex < 0) {
                splitIndex += 256;
            }

            splitedBytes[2 * i] = (byte)(splitIndex >> 4);
            splitedBytes[2 * i + 1] = (byte)(splitIndex & 15);
        }

        splitIndex = arrayIndexOf(splitedBytes, (byte)15);
        if (splitIndex == 0) {
            throw new IllegalArgumentException("error CN BCD head");
        } else {
            byte[] toStrBytes;
            int i;
            byte[] headArray;
            if (splitIndex == -1) {
                headArray = splitedBytes;
            } else {
                headArray = leftSubArray(splitedBytes, splitIndex);
                toStrBytes = rightSubArray(splitedBytes, splitIndex);

                for(i = 0; i < toStrBytes.length; ++i) {
                    if (toStrBytes[i] != 15) {
                        throw new IllegalArgumentException("error CN BCD tail");
                    }
                }
            }

            toStrBytes = new byte[headArray.length];

            for(i = 0; i < headArray.length; ++i) {
                if (headArray[i] < 0 || headArray[i] > 9) {
                    throw new IllegalArgumentException("error CN BCD head");
                }

                toStrBytes[i] = (byte)(headArray[i] + 48);
            }

            String toIntStr = null;

            try {
                toIntStr = new String(toStrBytes, "US-ASCII");
            } catch (UnsupportedEncodingException var7) {
                var7.printStackTrace();
            }

            return toIntStr;
        }
    }

    public static String toHexString(byte[] block) {
        return arrayShortHexString(block);
    }

    public static String generateHexString(int byteLength) {
        char[] hexChars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < byteLength; ++i) {
            Random radom = new Random();
            char hi = hexChars[radom.nextInt(16)];
            char lo = hexChars[radom.nextInt(16)];
            sb.append(hi);
            sb.append(lo);
        }

        return sb.toString();
    }

    public static String generateTransIdStr(long input, int outputLength) {
        String output = String.valueOf(input);
        if (output.length() > outputLength) {
            throw new IllegalArgumentException();
        } else {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < outputLength - output.length(); ++i) {
                sb.append('0');
            }

            return sb.append(output).toString();
        }
    }

    public static byte[] intToHexBytes(int srcInt, int byteCount) {
        if (byteCount >= 1 && byteCount <= 4) {
            int max = (int)Math.pow(2.0D, (double)(8 * byteCount)) - 1;
            if (srcInt >= 0 && srcInt <= max) {
                String hexStr = Integer.toHexString(srcInt);
                if (hexStr.length() % 2 != 0) {
                    hexStr = "0" + hexStr;
                }

                int paddingByteCount = byteCount - hexStr.length() / 2;
                if (paddingByteCount < 0) {
                    throw new IllegalArgumentException("srcInt is larger than " + max);
                } else {
                    byte[] paddingBytes = new byte[paddingByteCount];

                    for(int i = 0; i < paddingBytes.length; ++i) {
                        paddingBytes[i] = 0;
                    }

                    byte[] unpaddingBytes = new byte[hexStr.length() / 2];

                    for(int i = 0; i < unpaddingBytes.length; ++i) {
                        String byteHex = hexStr.substring(2 * i, 2 * i + 2);
                        unpaddingBytes[i] = (byte)Integer.parseInt(byteHex, 16);
                    }

                    return contactArray(paddingBytes, unpaddingBytes);
                }
            } else {
                throw new IllegalArgumentException("srcInt should not be less than 0 or larger than " + max);
            }
        } else {
            throw new IllegalArgumentException("byteCount should not be less than 1 or larger than 4");
        }
    }

    public static int binaryToInt(byte[] binary) {
        String hexStr = toHexString(binary);
        return Integer.parseInt(hexStr, 16);
    }

    public static String intToBinaryString(int srcInt, int bitCount) {
        if (bitCount >= 1 && bitCount <= 32) {
            int max = (int)Math.pow(2.0D, (double)bitCount) - 1;
            if (srcInt >= 0 && srcInt <= max) {
                String binaryStr = Integer.toBinaryString(srcInt);
                int paddingBitCount = bitCount - binaryStr.length();
                if (paddingBitCount < 0) {
                    throw new IllegalArgumentException("srcInt is larger than " + max);
                } else {
                    StringBuffer sb = new StringBuffer();

                    for(int i = 0; i < paddingBitCount; ++i) {
                        sb.append('0');
                    }

                    sb.append(binaryStr);
                    return sb.toString();
                }
            } else {
                throw new IllegalArgumentException("srcInt should not be less than 0 or larger than " + max);
            }
        } else {
            throw new IllegalArgumentException("bitCount should not be less than 1 or larger than 32");
        }
    }

    public static String intToKoalFormat(int srcInt) {
        if (srcInt < 0) {
            throw new IllegalArgumentException("the input int should not be less than 0");
        } else {
            String temp = String.valueOf(srcInt);
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < 10 - temp.length(); ++i) {
                sb.append('0');
            }

            sb.append(temp);
            return sb.toString();
        }
    }

    public static byte[] bitComplement(byte[] src) {
        byte[] dest = new byte[src.length];

        for(int i = 0; i < src.length; ++i) {
            dest[i] = (byte)(~src[i]);
        }

        return dest;
    }

    public static byte[] arrayXOR(byte[] a, byte[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException();
        } else {
            byte[] c = new byte[a.length];

            for(int i = 0; i < a.length; ++i) {
                c[i] = (byte)(a[i] ^ b[i]);
            }

            return c;
        }
    }

    public static String formatedHexString(byte[] src) {
        String delim = " ";
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < src.length; ++i) {
            byte byteNumber = src[i];
            sb.append(hexString(byteNumber).substring(2));
            sb.append(delim);
            if ((i + 1) % 16 == 0) {
                sb.append(System.getProperty("line.separator"));
            }
        }

        String toPrint = sb.toString();
        int start = toPrint.length() - delim.length();
        if (delim.equals(toPrint.substring(start, toPrint.length()))) {
            toPrint = toPrint.substring(0, start);
        }

        return toPrint;
    }

    public static long binaryToLong(byte[] binary) {
        String hexStr = toHexString(binary);
        return Long.parseLong(hexStr, 16);
    }

    public static byte[] longToHexBytes(long srcLong, int byteCount) {
        if (byteCount >= 1 && byteCount <= 8) {
            long max = (long)Math.pow(2.0D, (double)(8 * byteCount)) - 1L;
            if (srcLong >= 0L && srcLong <= max) {
                String hexStr = Long.toHexString(srcLong);
                if (hexStr.length() % 2 != 0) {
                    hexStr = "0" + hexStr;
                }

                int paddingByteCount = byteCount - hexStr.length() / 2;
                if (paddingByteCount < 0) {
                    throw new IllegalArgumentException("srcLong is larger than " + max);
                } else {
                    byte[] paddingBytes = new byte[paddingByteCount];

                    for(int i = 0; i < paddingBytes.length; ++i) {
                        paddingBytes[i] = 0;
                    }

                    byte[] unpaddingBytes = new byte[hexStr.length() / 2];

                    for(int i = 0; i < unpaddingBytes.length; ++i) {
                        String byteHex = hexStr.substring(2 * i, 2 * i + 2);
                        unpaddingBytes[i] = (byte)Integer.parseInt(byteHex, 16);
                    }

                    return contactArray(paddingBytes, unpaddingBytes);
                }
            } else {
                throw new IllegalArgumentException("srcLong should not be less than 0 or larger than " + max);
            }
        } else {
            throw new IllegalArgumentException("byteCount should not be less than 1 or larger than 4");
        }
    }

    public static byte[] prepend(byte b, byte[] src) {
        return contactArray(new byte[]{b}, src);
    }

    public static void fill(byte[] src, int offset, byte fillByte) {
        for(int i = offset; i < src.length; ++i) {
            src[i] = fillByte;
        }

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

        for(int i = startIndexInclusive; i < endIndexExclusive; ++i) {
            padded[i] = fillByte;
        }

        return padded;
    }

    public static byte[] convertBytes(Byte[] b1) {
        byte[] b2 = new byte[b1.length];

        for(int i = 0; i < b1.length; ++i) {
            b2[i] = b1[i];
        }

        return b2;
    }

    public static byte[] asciiTrim(byte[] beforeTrim) {
        byte[] buf = new byte[0];

        for(int i = 0; i < beforeTrim.length && beforeTrim[i] != 0; ++i) {
            buf = append(buf, beforeTrim[i]);
        }

        return buf;
    }

    public static List<byte[]> split(byte[] data, int length) {
        int mod = data.length % length;
        int counter = (data.length - mod) / length;
        List<byte[]> exeCodeByteList = new ArrayList();

        for(int i = 0; i < counter; ++i) {
            byte[] buf = subArray(data, i * length, (i + 1) * length);
            exeCodeByteList.add(buf);
        }

        if (mod != 0) {
            byte[] buf = subArray(data, counter * length, data.length);
            exeCodeByteList.add(buf);
        }

        return exeCodeByteList;
    }
}
