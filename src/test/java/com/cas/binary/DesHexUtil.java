package com.cas.binary;

import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;

/**
 * 卡应用数据转码
 */
public class DesHexUtil {

    public static void main(String[] args) {
        // 有计数器的数据
        String data = "01128201284332773073637063653464716F727A6739537356364142306436426F30496B4F7A45757A347753666845644D4D4441774D4445784D4455794D4449784D5449794D4449774D6A49774E6A49774D5145414141454271526F784536614630662F6E3730532B4843532B6A6E535136584A6D306B6C4C70554D424676716B6C566E6A422F575852596574483137415538657A306C4D50633858476D75727A74357941496374414E305A385872504F786F53496542557A704770506F433761456A67324E6A457A4F5559774D4559434951443245497643314A2B79794F6C4F70746452512F62346151596363534C6735417A4650757A7A3352714452674968414947317A46537A613551707A386C434556303548386C65376679364951767352464B6B424C314564544336130100140100150100160100174034374435384439393946313346333135463638313539373037303836324546314639433838463034333731344544313038343338414333354545353631344142D73789F980";
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
