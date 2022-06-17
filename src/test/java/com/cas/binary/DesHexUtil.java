package com.cas.binary;

import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;

/**
 * 卡应用数据转码
 */
public class DesHexUtil {

    public static void main(String[] args) {
        // 有计数器的数据
        String data = "011282012870736347677A79706F354B726F5842366E575A677442754B78516A7457693147356246322F64666461596871386A58584A64552B4F776C7A4532382F357263306267624237655963436331686549366B71566F797166502B6A62463463643868582F44462B4778677A3532554C764E5565447A52666E6965394F7941546635367334425438485771636F6E684C6B3272346B4B5653315151582B54544F645479652F68596D676A55774476614F546449426A774D54675665374C775967447334706A7378535643463142534B473437784639735142435158652F6252326E36322F4E2B47756A55376552713542624C566D7449754B496A6B7838493670464E3730303030303030303030303030303030303030303030303030303030303030303030303030303030130100140100150100160100174030433342453943454138343637413933394237393043313537303936304636383032393538374544424541433944413145413242343036313346333739433246A013828D80";
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
