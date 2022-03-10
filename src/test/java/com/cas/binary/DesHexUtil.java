package com.cas.binary;

import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;

/**
 * 卡应用数据转码
 */
public class DesHexUtil {

    public static void main(String[] args) {
        // 有计数器的数据
        String data = "0112820128437A415A4D7368724C6D382F332F64746C4553596979477756706E6364396D726252506C52456763326D43754D4441774D4445784D4455794D4449794D4445774E5449774D6A49774E7A41314D5145414141454271526F784536614630662F6E3730526E526148385261384457744C77546B322F7679385243395A6F4A512F2B3958516262794C6C766E7578692F58546A4B39443744694E4B64436448634E667449544C4E48766D485157624C594658686D32465A5358326449727A477A68434D5549324E3051354D45554349514378333672762F4641426B3033414E5A636539573361736E326634783764687170655255797334712F44314149675458754E36692F2B4A2F4D5A4E6759596A79507A4739625868616D4F2F7073334932684F4C52502F59724141130100140100150100160100174031313131313131313131313131313131323232323232323232323232323232323333333333333333333333333333333334343434343434343434343434343434F89DF8C880";
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
