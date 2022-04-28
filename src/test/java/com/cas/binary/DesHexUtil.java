package com.cas.binary;

import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;

/**
 * 卡应用数据转码
 */
public class DesHexUtil {

    public static void main(String[] args) {
        // 有计数器的数据
        String data = "011282012843326731717A79726B6E44457442534A414230625A524275342B5459324A50434941747969776F6C2B6769554D4441774D4445784D4455794D4449794D444D794E5449774D6A49774F5449314D5145414141454271526F784536614630662F6E3730514F38554430654652767A734B2F62522B325A69412B542B65774C51626C4D627A7639646C44435474444441524A7348362B52726C7A6132452B56556D5A736573596E71713436356274476F454D2B4E535462454C326D314456614445324E6A42464F5442474D4555434951443173524D794F6B334D4B57376D4E35335262476C4E4A7968384671716D67704A36447944464B4A6B7A677749674C36663848646A5574477346386A6F6B31616F777639754A465466594C436E3443744246514D36375A453841130100140100150100160100174036304646414145443530353043303445364331414331333736444439433843393130373538383345333030423535333343414343374446343238393532363035D58FFA1380";
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
