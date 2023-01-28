package com.cas.binary;

import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;

/**
 * 卡应用数据转码
 */
public class DesHexUtil {

    public static void main(String[] args) {
        // 有计数器的数据
        String data = "";
        System.out.println(decodeSimIdInfo(data).toString());

    }

    /**
     * 数字身份信息解码
     */
    public static SimIdCardInfo decodeSimIdInfo(String hex) {
        SimIdCardInfo simIdCardInfo = new SimIdCardInfo();
        try {
            byte[] buffer = Hex.decodeHex(hex);
            //取到填充数据长度
            byte[] fixDataLenArr = new byte[1];
            System.arraycopy(buffer, 0, fixDataLenArr, 0, 1);
            byte fixLen = fixDataLenArr[0];
            //取到数据部分  1字节开头填充数据长度，4字节填充数据前4位Mac地址
            byte[] payload = new byte[buffer.length - fixLen - 1 - 4];
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
                    case 0x11:
                        simIdCardInfo.setUserConfirmResult(Hex.encodeHexString(tagInfo));
                        break;
                    case 0x12:
                        simIdCardInfo.setDigitalIdInfo(new String(tagInfo));
                        break;
                    case 0x13:
                        simIdCardInfo.setName(new String(tagInfo));
                        break;
                    case 0x14:
                        simIdCardInfo.setIdNumber(new String(tagInfo));
                        break;
                    case 0x15:
                        simIdCardInfo.setValid(new String(tagInfo));
                        break;
                    case 0x16:
                        simIdCardInfo.setQrCode(new String(tagInfo));
                        break;
                    case 0x17:
                        simIdCardInfo.setPid(new String(tagInfo));
                        break;
                    default:
                        System.out.println("error");
//                        throw  new RuntimeException();
                }
            }
        } catch (Exception e) {
//            throw  new RuntimeException();
        }
        return simIdCardInfo;
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
