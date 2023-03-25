package com.cas.bo;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;


/**
 * <p>
 * 这个类定义了符合CMS2AC规范的APDU指令的格式
 * </p>
 * <p>
 * APDU的格式为<br />
 * cla|ins|p1|p2|lc(|data)(|le)<br />
 * </p>
 * <p>
 * 所有指令使用IOS/IEC 7816-4中的短消息格式，即lc字段的长度为1 byte
 * </p>
 * <p>
 * 在CMS2AC规范中，
 * <ul>
 * <li>所有指令使用IOS/IEC 7816-4中的短消息格式，即lc字段的长度为1 byte</li>
 * <li>所有指令使用长度（包括报文头）不超过255 byte</li>
 * </ul>
 * </p>
 * <p>
 * 所有继承这个类的子类对应具体的APDU指令，定义了各个字段的取值
 * </p>
 *
 * @author JazGung
 */
public class Command implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -8445506562236038760L;
    public static int INDEX_CLA = 0;
    public static int INDEX_INS = 1;
    public static int INDEX_P1 = 2;
    public static int INDEX_P2 = 3;
    public static int INDEX_LC = 4;
    public static int INDEX_VALUE_FIRST_BYTE = 5;

    public static int LENGTH_COMMAND_MAX = 255;
    public static int LNEGTH_COMMADN_MIN = 5;
    public static int LNEGTH_DATA_MAX_PER_COMMAND = 160;

    protected static final Logger log = LoggerFactory.getLogger(Command.class);

    /**
     * class，指令的类型， 1 byte<br />
     * <br />
     * b<sub>8</sub>=0，ISO/IEC 7816-4中定义的指令<br />
     * b<sub>8</sub>=1，CMS2AC中定义的指令<br />
     * <br />
     * b<sub>4</sub>=0，普通报文<br />
     * b<sub>4</sub>=1，安全报文<br />
     */
    private byte cla;

    /**
     * instruction，指令，这个字段的取值决定了具体的APDU指令<br />
     */
    private byte ins;

    /**
     * parameter1<br />
     * 取值具体由指令决定<br />
     */
    private byte p1;

    /**
     * parameter2<br />
     * 取值具体由指令决定<br />
     */
    private byte p2;

    /**
     * 数据域的长度<br />
     */
    private Byte lc = 0;

    /**
     * 批次号
     */
    private Integer batchNo;

    /**
     * 数据域
     */
    protected byte[] data = new byte[0];

    /**
     * 响应数据域的最大长度<br />
     * 如果le=null，响应不允许存在数据域<br />
     * 如果0&ltle&lt=255，响应数据域最大长度是le <br />
     * 如果le=0，响应数据域最大长度是le<br />
     */
    private Byte le = null;

    public Command() {

    }

    public Command(byte cla, byte ins, byte p1, byte p2) {
        super();
        this.cla = cla;
        this.ins = ins;
        this.p1 = p1;
        this.p2 = p2;
    }

    public byte getCla() {
        return cla;
    }

    public void setCla(byte cla) {
        this.cla = cla;
    }

    public byte getIns() {
        return ins;
    }

    public void setIns(byte ins) {
        this.ins = ins;
    }

    public void setP1(byte p1) {
        this.p1 = p1;
    }

    public byte getP1() {
        return p1;
    }

    public void setP2(byte p2) {
        this.p2 = p2;
    }

    public byte getP2() {
        return p2;
    }

    public Byte getLc() {
        return lc;
    }

    public void setLc(Byte lc) {
        this.lc = lc;
    }

    public Byte getLe() {
        return le;
    }

    public void setLe(Byte le) {
        this.le = le;
    }

    public byte[] getData() {
        return data;
    }

    public Integer getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(Integer batchNo) {
        this.batchNo = batchNo;
    }

    /**
     * 数据域加密后再设值,同时改变数据域长度
     *
     * @param data
     * @author unicorn
     * @version 2014-12-4
     */
    public void setData(byte[] data) {
        this.data = data;
        this.setLc((byte) this.data.length);
    }

    public static byte[] parseValue(byte[] command) {
        int lc = NumberConverter.byte2Int(command[INDEX_LC]);
        int valueLastByteIndex = INDEX_VALUE_FIRST_BYTE + lc;
        return ArrayUtils.subarray(command, INDEX_VALUE_FIRST_BYTE, valueLastByteIndex);
    }

    /***
     * 组建整个指令数据方法
     */
    public byte[] toByteArray() {

        ByteBuffer headerByteBuffer = new ByteBuffer();
        headerByteBuffer.append(getCla());
        headerByteBuffer.append(getIns());
        headerByteBuffer.append(getP1());
        headerByteBuffer.append(getP2());

        ByteBuffer bodyByteBuffer = new ByteBuffer();
        if (null != lc) {
            bodyByteBuffer.append((byte) getLc());
        }
        bodyByteBuffer.append(getData());
        if (null != le) {
            bodyByteBuffer.append(le);
        }
        headerByteBuffer = headerByteBuffer.append(bodyByteBuffer);
        return headerByteBuffer.toByteArray();
    }

    public static Command parse(byte[] commandByte) {
        validateCommandByteArray(commandByte);

        if ((byte) 0x82 != (commandByte[INDEX_INS]) && (commandByte[INDEX_CLA] & 0x04) != 0) {
            throw new RuntimeException("can't parse secured command, cla: " + HexConverter.byte2HexString(commandByte[INDEX_CLA]));
        }
        Command command = new Command(commandByte[INDEX_CLA], commandByte[INDEX_INS], commandByte[INDEX_P1], commandByte[INDEX_P2]);
        command.setData(parseValue(commandByte));
        return command;
    }

    private static void validateCommandByteArray(byte[] command) {
        if (LNEGTH_COMMADN_MIN > command.length) {
            throw new RuntimeException("command lenth is less than min: " + command.length);
        }

        int lc = HexConverter.byte2Int(command[INDEX_LC]);
        int expectCommondLength = lc + 5;
        int actualCommondLength = command.length;
        if (!((actualCommondLength == expectCommondLength) || (actualCommondLength == (expectCommondLength + 1)))) {
            throw new RuntimeException("error, lc is " + lc + " so command length at least " + expectCommondLength
                    + " but command length is " + actualCommondLength);
        }
    }

}
