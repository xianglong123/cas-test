package com.cas.bo;

import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * 粤居码卡应用数据转码
 */
public class DesBusHexUtil {

    public static void main(String[] args) {
        String data = "141306E5BCA0E4B889141235303032333731393930303731343738373080000000000000000000000000000000000000003030303030303030303030303030303030303031";
        System.out.println(decodeYjmInfo(data).toString());
    }

    /**
     * 数字身份信息解码
     */
    public static Qiqihar decodeYjmInfo(String hex) {
        Qiqihar qiqihar = new Qiqihar();
        try {
            byte[] buffer = Hex.decodeHex(hex);
            //取到填充数据长度
            byte[] fixDataLenArr = new byte[1];
            System.arraycopy(buffer, 0, fixDataLenArr, 0, 1);
            byte fixLen = fixDataLenArr[0];
            //取到数据部分 1字节填充长度，填充数据，计数器
            byte[] payload = new byte[buffer.length - 1 - fixLen - 20];
            System.arraycopy(buffer, 1, payload, 0, payload.length);

            ByteBuffer byteBuffer = ByteBuffer.wrap(payload);
            byte tag;
            //长度/长度格式
            byte lenType;
            int len;
            while (byteBuffer.position() < payload.length - 1) {
                tag = byteBuffer.get();
                lenType = byteBuffer.get();
                switch (lenType) {
                    case (byte) 0x81:
                        len = byteBuffer.get();
                        break;
                    case (byte) 0x82:
                        len = byteBuffer.getChar();
                        break;
                    case (byte) 0x83:
                        byte[] le = new byte[3];
                        byteBuffer.get(le);
                        len = bytesToInt(le);
                        break;
                    case (byte) 0x84:
                        len = byteBuffer.getInt();
                        break;
                    default:
                        len = lenType;
                        break;
                }
                byte[] tagInfo = new byte[len];
                byteBuffer.get(tagInfo);
                switch (tag) {
                    case 0x13:
                        qiqihar.setName(new String(tagInfo, StandardCharsets.UTF_8));
                        break;
                    case 0x14:
                        qiqihar.setIdCard(new String(tagInfo, StandardCharsets.UTF_8));
                        break;
                    default:
                        System.out.println("error");
//                        throw  new RuntimeException();
                }
            }
        } catch (Exception e) {
//            throw  new RuntimeException();
        }
        return qiqihar;
    }

    private static int bytesToInt(byte[] src) {
        byte[] des = new byte[4];
        int start = des.length - src.length;
        int value;
        System.arraycopy(src, 0, des, start, src.length);
        value = (src[3] & 0xFF)
                | ((src[2] & 0xFF) << 8)
                | ((src[1] & 0xFF) << 16)
                | ((src[0] & 0xFF) << 24);
        return value;
    }


}
