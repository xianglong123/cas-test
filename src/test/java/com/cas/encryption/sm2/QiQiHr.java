package com.cas.encryption.sm2;

import cn.hutool.crypto.Padding;
import com.cas.bo.ByteBuffer;
import com.cas.bo.Command;
import com.cas.bo.HexConverter;
import com.cas.bo.PaddingUtil;
import com.cas.bo.TlvObject;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2023/3/24 1:48 下午
 * @desc 齐齐哈尔
 */
public class QiQiHr {

    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final String NAME_TAG = "13";
    public static final String CARD_TAG = "14";

    public static final byte VALUE_CLA = (byte) 0x80;
    public static final byte CARD_CLA = (byte) 0x84;
    public static final byte VALUE_INS = (byte) 0xDD;
    public static final byte VALUE_P1 = (byte) 0x04;


    public static Command build(byte p2, byte[] cmdData) {
        Command writeCommand = new Command(VALUE_CLA, VALUE_INS, VALUE_P1, p2);
        writeCommand.setData(cmdData);
        return writeCommand;
    }

    public static void main(String[] args) throws Exception {
//        dataInCard();
        descry();
    }

    private static void dataInCard() throws Exception {
        // 数据
        // TLV 13姓名  14身份证
        String name ="张三";
        String idCard = "500237199007147870";
        byte[] nameB = name.getBytes(StandardCharsets.UTF_8);
        byte[] idCardB = idCard.getBytes(StandardCharsets.UTF_8);

        String nameHex = HexConverter.byteArray2HexString(nameB);
        String idCardHex = HexConverter.byteArray2HexString(idCardB);

        System.out.println(nameHex);
        System.out.println(idCardHex);


        byte[] buildData = buildData(name, idCard);
        byte[] padding = PaddingUtil.padding(buildData);

        System.out.println(HexConverter.byteArray2HexString(padding));

        Command build = build((byte) 0x00, padding);
        System.out.println(HexConverter.byteArray2HexString(build.toByteArray()));
        // 28字节 填充32+16 = 48字节、20个0


        // 解密出来的数据
        // 141306E59091E9BE9914123530303233373139393930373134373837328000000000000000000000000000000000000000
    }

    /**
     * 解密
     */
    public static void descry() {
        String enc = "04" + "7EDEC2BB7FB210141F23C531D310E5CFB43AA52D56D8F84A79F7B5EF5B1F981897ACC498A790AE24E8CAC6FF9F002333634456D6199CFFE82157CE094A562510F2CDB8674EEB6FFB3B5DA915985360E59B3B5D161AE42BB31644F0BB98FEBE7D3D0247AC1648816547686843341AAF3F6B6033128955294B378DE2D746DCF525C4102BDFCE9E30913E14739334DB53E50C74353A1B3AD18DA72B200ED536A7D402D9A80D7D";
        String prk = "388772FCE9E53920B302855AFA4A1A596657A169B7DE257CFB2E109827143FB5";
        byte[] bytes = ThreeTypeKeySm2Test.decryData_jd(HexConverter.hexString2ByteArray(enc), prk);
        System.out.println(HexConverter.byteArray2HexString(bytes));
    }

    //

    public static byte[] buildData(String userName, String identityCard)
            throws Exception {
        ByteBuffer buffer = new ByteBuffer();

        byte[] nameByte = StringUtils.isBlank(userName) ? new byte[]{0x00} : userName.getBytes(DEFAULT_CHARSET);
        TlvObject nameObject = new TlvObject();
        nameObject.add(NAME_TAG, HexConverter.hexEncode(nameByte));
        buffer.append(nameObject.build());

        byte[] identityCardByte = StringUtils.isBlank(identityCard) ? new byte[]{0x00}
                : identityCard.getBytes(DEFAULT_CHARSET);
        TlvObject cardObject = new TlvObject();
        cardObject.add(CARD_TAG, HexConverter.hexEncode(identityCardByte));
        buffer.append(cardObject.build());

        return buffer.toByteArray();

    }


}
