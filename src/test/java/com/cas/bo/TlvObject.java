package com.cas.bo;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class TlvObject {

    private static final Logger log = LoggerFactory.getLogger(TlvObject.class);

    private Map<String, List<TwoTruple<ValueHandler, Integer>>> values = new HashMap<String, List<TwoTruple<ValueHandler, Integer>>>();

    private int sequence = 0;

    private static interface ValueHandler {
        byte[] toByteArray();

        TlvValueHandler convertToTlv();

        TlvObject getValueTlv();
    }

    private static class ByteArrayValueHandler implements ValueHandler {

        private byte[] value;

        public ByteArrayValueHandler(byte[] value2) {
            this.value = value2;
        }

        @Override
        public byte[] toByteArray() {
            return value;
        }

        @Override
        public TlvValueHandler convertToTlv() {
            return new TlvValueHandler(TlvObject.parse(value));
        }

        @Override
        public TlvObject getValueTlv() {
            return TlvObject.parse(value);
        }
    }

    private static class TlvValueHandler implements ValueHandler {

        private TlvObject value;

        public TlvValueHandler(TlvObject value) {
            this.value = value;
        }

        @Override
        public byte[] toByteArray() {
            return value.build();
        }

        @Override
        public TlvValueHandler convertToTlv() {
            return this;
        }

        @Override
        public TlvObject getValueTlv() {
            return value;
        }

    }

    public static interface LengthHandler {

        /**
         * 生成代表length的byte[]
         *
         * @param length length
         * @return 代表length的byte[]
         */
        byte[] generate(int length);

        /**
         * 从<code>src</code>的第<code>startIndex</code>解析出length。
         *
         * @param src        源数据，TLV编码的byte数组
         * @param startIndex 解析的起始索引，即length的第一个字节索引
         * @return tag的信息，其中{@link TwoTruple#left left}为length的值，
         * {@link TwoTruple#right right}为value的起始索引，即value的第一个字节在
         * <code>src</code> 中的索引
         */
        TwoTruple<Integer, Integer> parse(byte[] src, int startIndex);
    }

    public static class BreFormatLengthHandlerImpl implements LengthHandler {

        /**
         * 长/短格式标记位的mask
         */
        private static final byte LENGTH_FORMAT_MARKER_MASK = (byte) 0x80;

        /**
         * 长格式情况下length字段值的字节数的mask
         */
        private static final byte LENGTH_FORMAT_LENGTH_BYTE_COUNT_MASK = (byte) 0x7F;

        @Override
        public byte[] generate(int length) {
            byte[] lengthBytes = NumberConverter.int2ByteArrayWithNecessaryLength(length);
            if (1 < lengthBytes.length || 0 != (lengthBytes[0] & LENGTH_FORMAT_MARKER_MASK)) {
                log.debug("需要使用长格式,length为:{}", length);
                byte firstByte = (byte) (((byte) lengthBytes.length) | LENGTH_FORMAT_MARKER_MASK);
                lengthBytes = ByteUtil.contactArray(new byte[]{firstByte}, lengthBytes);
            } else {
                log.debug("需要使用短格式,length为:{}", length);
                lengthBytes = new byte[]{lengthBytes[0]};
            }

            if (log.isDebugEnabled()) {
                log.debug("处理后length为:{}", HexConverter.byteArray2HexString(lengthBytes));
            }

            return lengthBytes;
        }

        @Override
        public TwoTruple<Integer, Integer> parse(byte[] src, int startIndex) {
            byte lengthFirstByte = src[startIndex];
            int length = -1;
            int endIndex = -1;
            if (0 == (lengthFirstByte & LENGTH_FORMAT_MARKER_MASK)) {// 如果长度域的第一个直接的最高bit为0，表示长度由第一个字节的低7bit表示
                if (log.isDebugEnabled()) {
                    log.debug("BRE短格式, length的首字节为:{}", HexConverter.byte2HexString(lengthFirstByte));
                }

                endIndex = startIndex + 1;// 短格式，length字段的下一字节的索引为length字段的起始索引+1
                length = NumberConverter.byte2Int(lengthFirstByte);
            } else {// 如果长度域的第一个直接的最高bit不为0，第一个字节的低7bit表示组成长度值的后续字节数
                if (log.isDebugEnabled()) {
                    log.debug("BRE长格式, length的首字节为:{}", HexConverter.byte2HexString(lengthFirstByte));
                }

                int lengthByteCount = (lengthFirstByte & LENGTH_FORMAT_LENGTH_BYTE_COUNT_MASK);
                endIndex = startIndex + 1 + lengthByteCount;// 长格式，length字段的下一字节的索引为length字段的起始索引+1+表示length字段值字节数长度

                if (src.length < endIndex) {
                    log.error("无法从源数据中解析出length, 源数据为:{}, 起始索引为:{}, 不包括首字节的length长度为:{}",
                            new Object[]{HexConverter.byteArray2HexString(src), startIndex, lengthByteCount});
                    throw new RuntimeException("src中无length");
                }

                length = NumberConverter.byteArray2Int(ArrayUtils.subarray(src, startIndex + 1, endIndex));
            }

            TwoTruple<Integer, Integer> tag = new TwoTruple<Integer, Integer>(length, endIndex// endIndex为length的第一个直接索引，所指向的字节不在tag中
            );

            if (log.isDebugEnabled()) {
                log.debug("从源数据中解析出length, 源数据为:{}, 起始索引为:{}, length为:{}, value的起始索引为:{}", new Object[]{
                        HexConverter.byteArray2HexString(src), startIndex, tag.getLeft(), tag.getRight()});
            }

            return tag;
        }
    }

    public static class LengthFixedLengthHandlerImpl implements LengthHandler {

        int lengthLength;

        public LengthFixedLengthHandlerImpl(int lengthLength) {
            this.lengthLength = lengthLength;
        }

        @Override
        public byte[] generate(int length) {
            byte[] lengthBytes = NumberConverter.int2ByteArray(length, lengthLength);

            if (log.isDebugEnabled()) {
                log.debug("处理后length为:{}", HexConverter.byteArray2HexString(lengthBytes));
            }

            return lengthBytes;
        }

        @Override
        public TwoTruple<Integer, Integer> parse(byte[] src, int startIndex) {
            int endIndex = startIndex + lengthLength;// 计算结束索引

            if (src.length < endIndex) {
                log.error("无法从源数据中解析出length, 源数据为:{}, 起始索引为:{}, length长度为:{}",
                        new Object[]{HexConverter.byteArray2HexString(src), startIndex, lengthLength});
                throw new RuntimeException("src中无length");
            }

            TwoTruple<Integer, Integer> tag = new TwoTruple<Integer, Integer>(
                    NumberConverter.byteArray2Int(ArrayUtils.subarray(src, startIndex, endIndex))// 从src中截取[startIndex,endIndex)的字节数组并转换为整数作为length
                    , endIndex// endIndex为length的第一个直接索引，所指向的字节不在tag中
            );

            if (log.isDebugEnabled()) {
                log.debug("从源数据中解析出length, 源数据为:{}, 起始索引为:{}, length为:{}, value的起始索引为:{}", new Object[]{
                        HexConverter.byteArray2HexString(src), startIndex, tag.getLeft(), tag.getRight()});
            }

            return tag;
        }

        ;
    }

    /**
     * 用于对sequence进行排序，其中sequence保存在TwoTruple.right
     */
    private class SequenceComparator implements Comparator<TwoTruple<?, Integer>> {

        @Override
        public int compare(TwoTruple<?, Integer> o1, TwoTruple<?, Integer> o2) {
            if (null == o1.getRight()) {
                return -1;
            } else if (null == o2.getRight()) {
                return 1;
            } else if (o1.getRight() < o2.getRight()) {
                return -1;
            } else if (o1.getRight() > o2.getRight()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public static interface TagHandler {
        /**
         * 从<code>src</code>的第<code>startIndex</code>解析出tag。
         *
         * @param src        源数据，TLV编码的byte数组
         * @param startIndex 解析的起始索引，即tag的第一个字节索引
         * @return tag的信息，其中{@link TwoTruple#left left}为tag的值，
         * {@link TwoTruple#right right}为length的起始索引，即length的第一个字节在
         * <code>src</code> 中的索引
         */
        TwoTruple<byte[], Integer> parse(byte[] src, int startIndex);
    }

    public static class LengthFixedTagHandlerImpl implements TagHandler {

        int tagLength;

        public LengthFixedTagHandlerImpl(int tagLength) {
            this.tagLength = tagLength;
        }

        @Override
        public TwoTruple<byte[], Integer> parse(byte[] src, int startIndex) {
            int endIndex = startIndex + tagLength;// 计算结束索引

            if (src.length < endIndex) {
                log.error("无法从源数据中解析出tag, 源数据为:{}, 起始索引为:{}, tag长度为:{}",
                        new Object[]{HexConverter.byteArray2HexString(src), startIndex, tagLength});
                throw new RuntimeException("src中无tag");
            }

            TwoTruple<byte[], Integer> tag = new TwoTruple<byte[], Integer>(
                    ArrayUtils.subarray(src, startIndex, endIndex)// 从src中截取[startIndex,endIndex)的字节数组作为tag
                    , endIndex// endIndex为length的第一个直接索引，所指向的字节不在tag中
            );

            if (log.isDebugEnabled()) {
                log.debug("从源数据中解析出tag, 源数据为:{}, 起始索引为:{}, tag为:{}, tag长度为:{}, length的起始索引为:{}",
                        new Object[]{HexConverter.byteArray2HexString(src), startIndex,
                                HexConverter.byteArray2HexString(tag.getLeft()), tagLength, tag.getRight()});
            }

            return tag;
        }
    }

    public static class ValueSpecifiedTagHandlerImpl implements TagHandler {

        Collection<String> tags;

        int maxTagLength = 0;

        public ValueSpecifiedTagHandlerImpl(byte[]... tags) {
            this.tags = new HashSet<String>(tags.length);
            int maxTagLength = 0;
            for (byte[] tag : tags) {
                this.tags.add(HexConverter.byteArray2HexString(tag));
                if (maxTagLength < tag.length) {
                    maxTagLength = tag.length;
                }
            }

            this.maxTagLength = maxTagLength;
            if (log.isDebugEnabled()) {
                log.debug("指定的tag为:{}, tag的最大长度为:{} ", new Object[]{printTags(), this.maxTagLength});
            }
        }

        public ValueSpecifiedTagHandlerImpl(String... tags) {
            this.tags = new HashSet<String>(tags.length);
            int maxTagLength = 0;
            for (String tag : tags) {
                this.tags.add(tag.toUpperCase());
                if (maxTagLength < tag.length()) {
                    maxTagLength = tag.length();
                }
            }

            this.maxTagLength = maxTagLength / 2;
            if (log.isDebugEnabled()) {
                log.debug("指定的tag为:{}, tag的最大长度为:{} ", new Object[]{printTags(), this.maxTagLength});
            }
        }

        @Override
        public TwoTruple<byte[], Integer> parse(byte[] src, int startIndex) {
            int maxTagLength = this.maxTagLength;
            if (src.length < startIndex + maxTagLength) {// src从startIndex开始的剩余字节数小于最长tag的字节数，因此不可能包含最大长度的标签，当前tag的最大长度应该是src从startIndex开始的剩余字节数-1（length至少1字节）
                maxTagLength = src.length - startIndex - 1;
            }

            if (0 > maxTagLength) {
                log.error("无法从源数据中解析出tag, 源数据为:{}, 起始索引为:{}",
                        new Object[]{HexConverter.byteArray2HexString(src), startIndex});
                throw new RuntimeException("src中无tag");
            }

            byte[] tagValue = null;
            for (int i = maxTagLength; i > 0; i--) {
                if (null == tagValue) {
                    String potentialTag = HexConverter
                            .byteArray2HexString(ArrayUtils.subarray(src, startIndex, startIndex + i));
                    if (tags.contains(potentialTag)) {
                        tagValue = HexConverter.hexString2ByteArray(potentialTag);
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug("tag不在指定的tag集合范围中, 源数据为:{}, 起始索引为:{}, tag为:{}, 指定的tag集合", new Object[]{
                                    HexConverter.byteArray2HexString(src), startIndex, potentialTag, printTags()});
                        }
                    }
                }
            }

            if (null == tagValue) {
                log.error("无法从源数据中解析出tag, 源数据为:{}, 起始索引为:{}, 指定的tag为:{}",
                        new Object[]{HexConverter.byteArray2HexString(src), startIndex, printTags()});
                throw new RuntimeException("src中无tag");
            }

            int endIndex = startIndex + tagValue.length;
            TwoTruple<byte[], Integer> tag = new TwoTruple<byte[], Integer>(
                    ArrayUtils.subarray(src, startIndex, endIndex)// 从src中截取[startIndex,endIndex)的字节数组作为tag
                    , endIndex// endIndex为length的第一个直接索引，所指向的字节不在tag中
            );

            if (log.isDebugEnabled()) {
                log.debug("从源数据中解析出tag, 源数据为:{}, 起始索引为:{}, tag为:{}, length的起始索引为:{}",
                        new Object[]{HexConverter.byteArray2HexString(src), startIndex,
                                HexConverter.byteArray2HexString(tag.getLeft()), tag.getRight()});
            }

            return tag;
        }

        /**
         * 打印log工具方法
         *
         * @return log的内容
         */
        private String printTags() {
            StringBuffer sb = new StringBuffer("[");

            for (String tag : tags) {
                sb.append(tag).append(", ");
            }

            return sb.delete(sb.length() - 2, sb.length()).append("]").toString();
        }
    }

    public static class TwoTruple<T, R> {
        private T left;

        private R right;

        public TwoTruple(T left, R right) {
            this.left = left;
            this.right = right;
        }

        public T getLeft() {
            return left;
        }

        public R getRight() {
            return right;
        }

        public byte[] getValueByteArray() {
            if (this.left.getClass() == TlvValueHandler.class || this.left.getClass() == ByteArrayValueHandler.class) {
                return ((ValueHandler) this.left).toByteArray();
            }
            return null;
        }
    }

    /**
     * 将<code>src</code>转换为{@link TlvObject TlvObject} ，转换时会按照tag为固定长度为1
     * byte，length为固定长度1 byte进行处理
     *
     * @param src 待解析的源数据对象，不能为null
     * @return 转换后的{@link TlvObject}对象
     */
    public static TlvObject parse(byte[] src) {
        return parse(src, new LengthFixedTagHandlerImpl(1), new LengthFixedLengthHandlerImpl(1));
    }

    /**
     * 将<code>src</code>转换为{@link TlvObject TlvObject}
     *
     * @param src          待解析的源数据对象，不能为null
     * @param tagLength    tag的指定长度，大于0
     * @param lengthLength length的指定长度，大于0
     * @return 转换后的{@link TlvObject}对象
     */
    public static TlvObject parse(byte[] src, int tagLength, int lengthLength) {
        if (0 >= tagLength) {
            throw new RuntimeException("tag必须大于0");
        }

        if (0 >= lengthLength) {
            throw new RuntimeException("length必须大于0");
        }

        return parse(src, new LengthFixedTagHandlerImpl(tagLength), new LengthFixedLengthHandlerImpl(lengthLength));
    }

    /**
     * 将<code>src</code>转换为{@link TlvObject TlvObject}
     *
     * @param src           待解析的源数据，十六进制字符串，不能为null、空白字符串或空字符串
     * @param tagHandler    处理tag的处理器，不能为null
     * @param lengthHandler 处理length的处理器，不能为null
     * @return 转换后的{@link TlvObject}对象
     */
    public static TlvObject parse(byte[] src, TagHandler tagHandler, LengthHandler lengthHandler) {
        if (null == src) {
            throw new RuntimeException("src不能为null");
        }

        TlvObject tlv = new TlvObject();
        int tagIndex = 0;

        while (tagIndex < src.length) {
            // 解析tag
            TwoTruple<byte[], Integer> tagInfo = tagHandler.parse(src, tagIndex);
            byte[] tag = tagInfo.getLeft();
            int lengthIndex = tagInfo.getRight();

            // 解析length
            TwoTruple<Integer, Integer> lengthInfo = lengthHandler.parse(src, lengthIndex);
            int length = lengthInfo.getLeft();
            int valueIndex = lengthInfo.getRight();

            // 解析value
            int nextTlvIndex = valueIndex + length;
            if (src.length < valueIndex) {
                log.error("无法从源数据中解析出value, 源数据为:{}, tag起始索引为:{}, length起始索引为:{}, value的起始索引为:{}",
                        new Object[]{HexConverter.byteArray2HexString(src), tagIndex, lengthIndex, valueIndex});
                throw new RuntimeException("src中无value");
            }

            if (src.length < nextTlvIndex) {
                log.error("无法从源数据中解析出value, 源数据为:{}, tag起始索引为:{}, length起始索引为:{}, value的起始索引为:{}",
                        new Object[]{HexConverter.byteArray2HexString(src), tagIndex, lengthIndex, valueIndex});
                throw new RuntimeException("tlv格式错误");
            }

            byte[] value = ArrayUtils.subarray(src, valueIndex, nextTlvIndex);

            if (log.isDebugEnabled()) {
                log.debug("解析出tlv, 源数据为:{}, 起始索引为:{}, tag为:{}, value为:{}",
                        new Object[]{HexConverter.byteArray2HexString(src), tagIndex,
                                HexConverter.byteArray2HexString(tag), HexConverter.byteArray2HexString(value)});
            }

            // 保存解析出的tlv
            tlv.add(tag, value);

            tagIndex = nextTlvIndex;
        }

        return tlv;
    }

    /**
     * 将<code>src</code>转换为{@link TlvObject TlvObject} ，转换时会按照tag为固定长度为1
     * byte，length为固定长度1 byte进行处理
     *
     * @param src 待解析的源数据，十六进制字符串，不能为null、空白字符串或空字符串
     * @return 转换后的{@link TlvObject}对象
     */
    public static TlvObject parse(String src) {
        if (StringUtils.isBlank(src)) {
            log.error("src为null、空白字符串或空字符串, src: " + src);
            throw new RuntimeException("src不能为null、空白字符串或空字符串");
        }

        return parse(HexConverter.hexString2ByteArray(src), new LengthFixedTagHandlerImpl(1),
                new LengthFixedLengthHandlerImpl(1));
    }

    /**
     * 将<code>src</code>转换为{@link TlvObject TlvObject}
     *
     * @param src          待解析的源数据，十六进制字符串，不能为null、空白字符串或空字符串
     * @param tagLength    tag的指定长度，大于0
     * @param lengthLength length的指定长度，大于0
     * @return 转换后的{@link TlvObject}对象
     */
    public static TlvObject parse(String src, int tagLength, int lengthLength) {
        if (StringUtils.isBlank(src)) {
            log.error("src为null、空白字符串或空字符串, src: " + src);
            throw new RuntimeException("src不能为null、空白字符串或空字符串");
        }

        return parse(HexConverter.hexString2ByteArray(src), new LengthFixedTagHandlerImpl(tagLength),
                new LengthFixedLengthHandlerImpl(lengthLength));
    }

    /**
     * 将<code>src</code>转换为{@link TlvObject TlvObject}
     *
     * @param src           待解析的源数据，不能为null
     * @param tagHandler    处理tag的处理器，不能为null
     * @param lengthHandler 处理length的处理器，不能为null
     * @return 转换后的{@link TlvObject}对象
     */
    public static TlvObject parse(String src, TagHandler tagHandler, LengthHandler lengthHandler) {
        if (null == src) {
            throw new RuntimeException("src不能为null");
        }

        return parse(HexConverter.hexString2ByteArray(src), tagHandler, lengthHandler);
    }

    /**
     * 添加一个tag-value对，次序指定为null，最终转为byte[]时出现的次序参考
     * {@link (byte[], byte[], Integer) add(byte[] tag, byte[] value,
     * Integer sequence)}的<code>sequence</code>说明
     *
     * @param tag   tag，不能为null或空数组
     * @param value value，不能为null
     */
    public void add(byte[] tag, byte[] value) {
        add(HexConverter.byteArray2HexString(tag), value);
    }

    /**
     * 添加一个tag-value对，次序指定为null，最终转为byte[]时出现的次序参考
     * {@link (byte[], byte[], Integer) add(byte[] tag, byte[] value,
     * Integer sequence)}的<code>sequence</code>说明
     *
     * @param tag   tag，不能为null或空数组
     * @param value value，十六进制字符，不能为空字符串或空白字符串，但可以为null
     *              <br\> 如果是null，将转为空byte[]进行处理
     */
    public void add(byte[] tag, String value) {
        if (ArrayUtils.isEmpty(tag)) {
            throw new RuntimeException("tag不能为null或空数组");
        }

        if (null == value) {
            add(tag, new byte[]{});
        } else if (StringUtils.isBlank(value)) {
            log.error("value为空白字符串或空字符串, value: " + value);
            throw new RuntimeException("value不能为空字符串或空白字符串");
        } else {
            add(tag, HexConverter.hexString2ByteArray(value));
        }
    }

    /**
     * 添加一个tag-value对
     *
     * @param tag    tag，不能为null、空白字符串或空字符串
     * @param value  value，不能为null
     * @param {@link TlvObject}对象转换为byte[]的次序，可以为null<br\> 如果为null，将出现在其他所有
     *               <code>sequence</code>不为null的tag-value对前面，但与其他
     *               <code>sequence</code>为null的tag-value对的次序不确定
     */
    public void add(String tag, byte[] value) {
        if (StringUtils.isBlank(tag)) {
            log.error("src为null、空白字符串或空字符串, tag: " + tag);
            throw new RuntimeException("tag不能为null、空白字符串或空字符串");
        }

        if (null == value) {
            throw new RuntimeException("value不能为null");
        }

        List<TwoTruple<ValueHandler, Integer>> valueList = values.get(tag);

        if (null == valueList) {// tag还未存储过
            valueList = new ArrayList<>();
            values.put(tag, valueList);
        }

        valueList.add(new TwoTruple<ValueHandler, Integer>(new ByteArrayValueHandler(value), sequence));
        sequence++;
    }

    /**
     * 为指定tag添加值<br/>
     * 如果在添加前，指定的tag已经存在，则新添加的部分在原有部分之后
     *
     * @param tag   指定的tag，十六进制字符串形式
     * @param value value，TlvObject对象形式
     */
    public void add(String tag, TlvObject value) {
        add(tag, value.build());
    }

    /**
     * 添加一个tag-value对，次序指定为null，最终转为byte[]时出现的次序参考
     * {@link (byte[], byte[], Integer) add(byte[] tag, byte[] value,
     * Integer sequence)}的<code>sequence</code>说明
     *
     * @param tag   tag，十六进制字符，不能为null、空白字符串或空字符串
     * @param value value，十六进制字符，不能为空字符串或空白字符串，但可以为null
     *              <br\> 如果是null，将转为空byte[]进行处理
     */
    public void add(String tag, String value) {
        if (StringUtils.isBlank(tag)) {
            log.error("tag为null、空白字符串或空字符串, tag: " + tag);
            throw new RuntimeException("tag不能为null、空白字符串或空字符串");
        }

        if (null == value) {
            add(HexConverter.hexString2ByteArray(tag), new byte[]{});
        } else {
            if (StringUtils.isBlank(value)) {
                log.error("value为不能为空字符串或空白字符串, value: " + tag);
                throw new RuntimeException("value不能为空白字符串或空字符串");
            }

            add(HexConverter.hexString2ByteArray(tag), HexConverter.hexString2ByteArray(value));
        }
    }

    /**
     * 将对象转换tlv byte数组，length为1
     *
     * @return tlv byte数组
     */
    public byte[] build() {
        return build(new LengthFixedLengthHandlerImpl(1));
    }

    /**
     * 将对象转换tlv byte数组，length为指定值
     *
     * @param lengthLength 指定的length长度
     * @return tlv byte数组
     */
    public byte[] build(int lengthLength) {
        return build(new LengthFixedLengthHandlerImpl(lengthLength));
    }

    /**
     * 将对象转换为的tlv byte数组，length为指定{@link LengthHandler LengthHandler}的
     * {@link LengthHandler#generate(int) generate(int)}生成
     *
     * @param lengthHandler 用于生成代表length的byte数组的LengthHandler对象
     * @return tlv byte数组
     */
    public byte[] build(LengthHandler lengthHandler) {
        // 数据结构转换，将tag-(value-sequence)转换为(tag-value)-sequence，以便根据sequence进行排序
        List<TwoTruple<TwoTruple<byte[], byte[]>, Integer>> tagValueSequences = new ArrayList<TwoTruple<TwoTruple<byte[], byte[]>, Integer>>();
        for (String tag : values.keySet()) {
            List<TwoTruple<ValueHandler, Integer>> valueInfos = values.get(tag);
            for (TwoTruple<ValueHandler, Integer> valueInfo : valueInfos) {
                TwoTruple<byte[], byte[]> tagValue = new TwoTruple<byte[], byte[]>(
                        HexConverter.hexString2ByteArray(tag), valueInfo.getLeft().toByteArray());
                TwoTruple<TwoTruple<byte[], byte[]>, Integer> tagValueSequence = new TwoTruple<TwoTruple<byte[], byte[]>, Integer>(
                        tagValue, valueInfo.getRight());
                tagValueSequences.add(tagValueSequence);
            }
        }

        // 根据sequence进行排序
        Collections.sort(tagValueSequences, new SequenceComparator());

        // 按照排序后的tag-value组建tlv的byte数组
        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        for (TwoTruple<TwoTruple<byte[], byte[]>, Integer> tagValueSequence : tagValueSequences) {
            byte[] tag = tagValueSequence.getLeft().getLeft();
            byte[] value = tagValueSequence.getLeft().getRight();
            try {
                if (log.isDebugEnabled()) {
                    log.debug("写入tag:{},写入value:{},次序:{}", HexConverter.byteArray2HexString(tag),
                            HexConverter.byteArray2HexString(value), tagValueSequence.getRight());
                }
                resultStream.write(tag);
                resultStream.write(lengthHandler.generate(value.length));
                resultStream.write(value);
            } catch (IOException e) {
                throw new RuntimeException("拼接tlv结果byte[]失败");
            }

            if (log.isDebugEnabled()) {
                log.debug("拼接结果:{}", HexConverter.byteArray2HexString(resultStream.toByteArray()));
            }
        }

        return resultStream.toByteArray();
    }

    /**
     * 获取tag对应的唯一value，如果tag对应的value不唯一，将抛出异常
     *
     * @param tag tag，不能为null或空数组
     * @return tag对应的唯一value<br \> 如果不存在，返回null<br\> 如果不唯一，抛出异常<br\>
     */
    public byte[] getUniqueValueByTag(byte[] tag) {
        if (ArrayUtils.isEmpty(tag)) {
            throw new RuntimeException("tag不能为null或空数组");
        }

        return getUniqueValueByTag(HexConverter.byteArray2HexString(tag));
    }

    /**
     * 获取tag对应的唯一value，如果tag对应的value不唯一，将抛出异常
     *
     * @param tag tag，十六进制字符，不能为null、空白字符串或空字符串
     * @return tag对应的唯一value<br \> 如果不存在，返回null<br\> 如果不唯一，抛出异常<br\>
     */
    public byte[] getUniqueValueByTag(String tag) {
        TwoTruple<ValueHandler, Integer> twoTruple = getUniqueTwoTrupleByTag(tag);
        if (twoTruple == null) {
            return null;
        }
        return twoTruple.getLeft().toByteArray();
    }

    public TwoTruple<ValueHandler, Integer> getUniqueTwoTrupleByTag(String tag) {
        if (StringUtils.isBlank(tag)) {
            log.error("src为null、空白字符串或空字符串, tag: " + tag);
            throw new RuntimeException("tag不能为null、空白字符串或空字符串");
        }

        List<TwoTruple<ValueHandler, Integer>> valueSequences = this.values.get(tag.toUpperCase());
        if (valueSequences != null) {// 如果tag对应的value不存在，直接返回null
            return null;
        } else if (1 < valueSequences.size()) {// 如果tag对应的value不唯一，抛出异常
            log.error("tag对应的value不唯一, tag: " + tag);
            throw new RuntimeException("tag对应的value不唯");
        } else {
            return valueSequences.get(0);
        }
    }

    /**
     * 根据tag获得对应的所有value
     *
     * @param tag tag，不能为null或空数组
     * @return tag对应的所有value，value在List中的顺序参考
     * {@link (byte[], byte[], Integer) add(byte[] tag, byte[]
     * value, Integer sequence)}的<code>sequence</code>说明<br\> 如果不存在，空列表
     * <br\>
     */
    public List<byte[]> getValuesByTag(byte[] tag) {
        if (ArrayUtils.isEmpty(tag)) {
            throw new RuntimeException("tag不能为null或空数组");
        }

        return getValuesByTag(HexConverter.byteArray2HexString(tag));
    }

    /**
     * 根据tag获得对应的所有value
     *
     * @param tag tag，十六进制字符，不能为null、空白字符串或空字符串
     * @return tag对应的所有value，value在List中的顺序参考
     * {@link (byte[], byte[], Integer) add(byte[] tag, byte[]
     * value, Integer sequence)}的<code>sequence</code>说明<br\> 如果不存在，空列表
     * <br\>
     */
    public List<byte[]> getValuesByTag(String tag) {
        if (StringUtils.isBlank(tag)) {
            log.error("src为null、空白字符串或空字符串, tag: " + tag);
            throw new RuntimeException("tag不能为null、空白字符串或空字符串");
        }

        List<byte[]> values = new ArrayList<byte[]>();

        List<TwoTruple<ValueHandler, Integer>> valueSequences = this.values.get(tag.toUpperCase());
        if (valueSequences != null) {
            // 对tag对应的所有value根据sequence进行排序
            Collections.sort(valueSequences, new SequenceComparator());

            // 将排序后的value添加到返回的list中
            for (TwoTruple<ValueHandler, Integer> valueSequence : valueSequences) {
                values.add(valueSequence.getLeft().toByteArray());
            }
        }

        return values;
    }

    /**
     * 根据tag获得对应的所有value-sequence对
     *
     * @param tag tag，不能为null或空数组
     * @return tag对应的所有value，value在List中的顺序参考
     * {@link (byte[], byte[], Integer) add(byte[] tag, byte[]
     * value, Integer sequence)}的<code>sequence</code>说明<br\> 如果不存在，空列表
     * <br\>
     */
    public List<TwoTruple<byte[], Integer>> getValueSequencesByTag(byte[] tag) {
        if (ArrayUtils.isEmpty(tag)) {
            throw new RuntimeException("tag不能为null或空数组");
        }

        return getValueSequencesByTag(HexConverter.byteArray2HexString(tag));
    }

    /**
     * 根据tag获得对应的所有value-sequence对
     *
     * @param tag tag，十六进制字符，不能为null、空白字符串或空字符串
     * @return tag对应的所有value，value在List中的顺序参考
     * {@link (byte[], byte[], Integer) add(byte[] tag, byte[]
     * value, Integer sequence)}的<code>sequence</code>说明<br\> 如果不存在，空列表
     * <br\>
     */
    public List<TwoTruple<byte[], Integer>> getValueSequencesByTag(String tag) {
        if (StringUtils.isBlank(tag)) {
            log.error("src为null、空白字符串或空字符串, tag: " + tag);
            throw new RuntimeException("tag不能为null、空白字符串或空字符串");
        }

        List<TwoTruple<ValueHandler, Integer>> valueSequences1 = this.values.get(tag.toUpperCase());
        List<TwoTruple<byte[], Integer>> valueSequences = new ArrayList<TwoTruple<byte[], Integer>>();

        if (valueSequences1 != null) {
            for (TwoTruple<ValueHandler, Integer> valueSequence1 : valueSequences1) {//
                valueSequences.add(new TwoTruple<byte[], Integer>(valueSequence1.getLeft().toByteArray(),
                        valueSequence1.getRight()));
            }

            // 对tag对应的所有value根据sequence进行排序
            Collections.sort(valueSequences, new SequenceComparator());
        } else {
            valueSequences = new ArrayList<TwoTruple<byte[], Integer>>();
        }

        return valueSequences;
    }

    public Set<String> getTags() {
        return values.keySet();
    }

    /**
     * 根据tag，删掉对应的value
     *
     * @param tag
     */
    public void remove(String tag) {
        if (StringUtils.isBlank(tag)) {
            log.error("src为null、空白字符串或空字符串, tag: " + tag);
            throw new RuntimeException("tag不能为null、空白字符串或空字符串");
        }
        values.remove(tag);
    }

    /**
     * 替换掉tag所对应的value值 先删除掉tag先前对应的值，再加上tag对应的值
     *
     * @param tag
     * @param value
     */
    public void replace(String tag, byte[] value) {
        if (StringUtils.isBlank(tag)) {
            log.error("src为null、空白字符串或空字符串, tag: " + tag);
            throw new RuntimeException("tag不能为null、空白字符串或空字符串");
        }
        if (null == value) {
            throw new RuntimeException("value不能为null");
        }
        remove(tag);
        add(tag, value);
    }

    /**
     * 替换掉参数中最后一个tag的value
     *
     * @param tag
     * @param value
     */
    public void replaceByPath(byte[] value, String... tags) {
        if (tags == null || tags.length == 0) {
            log.error("请指定正确的tags");
            throw new RuntimeException("参数tags至少要一个tag");
        }
        if (null == value) {
            throw new RuntimeException("value不能为null");
        }

        List<TlvObject> tlvs = new ArrayList<TlvObject>();
        TlvObject tlv = this;
        for (int i = 0; i < tags.length - 1; i++) {
            byte[] src = tlv.getUniqueValueByTag(tags[i]);
            if (null == src) {
                log.error("请指定正确的tags");
                throw new RuntimeException("请指定正确的tags");
            }
            tlv = parse(src);
            tlvs.add(tlv);
        }

        // 替换value
        for (int i = tlvs.size(); i > 0; i--) {
            tlv = tlvs.get(i - 1);
            tlv.replace(tags[i], value);
            value = tlv.build();
        }

        this.replace(tags[0], tlvs.get(0).build());
    }

    private TwoTruple<ValueHandler, Integer> getUniqueTwoTrupleByPath(String[] tags) {
        if (tags == null || tags.length == 0) {
            log.error("请指定正确的tags");
            throw new RuntimeException("参数tags至少要一个tag");
        }

        String tag = tags[0];
        TwoTruple<ValueHandler, Integer> src = getUniqueTwoTrupleByTag(tag);
        for (int i = 1; i < tags.length; i++) {
            ValueHandler value = src.getLeft();
            if (value.getClass() != TlvValueHandler.class) {
                TlvValueHandler valueHandler = value.convertToTlv();
                List<TwoTruple<ValueHandler, Integer>> values = new ArrayList<TwoTruple<ValueHandler, Integer>>();
                TwoTruple<ValueHandler, Integer> towTrupleValue = new TwoTruple<ValueHandler, Integer>(
                        valueHandler, src.getRight());
                values.add(towTrupleValue);
                this.values.put(tag, values);
                src = towTrupleValue;
            }
            tag = tags[i];
            src = src.getLeft().getValueTlv().getUniqueTwoTrupleByTag((tag));
        }
        return src;
    }

    /**
     * 根据得到最后一个tag的UniqueValue
     *
     * @param tags
     * @return
     */
    public byte[] getUniqueValueByPath(String... tags) {
        if (tags == null || tags.length == 0) {
            log.error("请指定正确的tag");
            throw new RuntimeException("参数tags至少要一个tag");
        }

        TwoTruple<ValueHandler, Integer> src = getUniqueTwoTrupleByPath(tags);
        if (null != src) {
            return src.getValueByteArray();
        }
        return null;
    }

    public TlvObject getTlvObjectByPath(String... tags) {
        if (tags == null || tags.length == 0) {
            log.error("请指定正确的tag");
            throw new RuntimeException("参数tags至少要一个tag");
        }

        TwoTruple<ValueHandler, Integer> src = getUniqueTwoTrupleByPath(tags);
        if (null != src) {
            return src.getLeft().getValueTlv();
        }
        return null;
    }

    /**
     * 增加到最后一个tag的value
     *
     * @param value
     * @param addTag
     * @param tags
     */
    public void addByPath(byte[] value, String addTag, String... tags) {
        if (StringUtils.isBlank(addTag)) {
            log.error("src为null、空白字符串或空字符串, tag: " + addTag);
            throw new RuntimeException("tag不能为null、空白字符串或空字符串");
        }
        if (tags == null || tags.length == 0) {
            log.error("请指定正确的tags");
            throw new RuntimeException("参数tags至少要一个tag");
        }

        if (null == value) {
            throw new RuntimeException("value不能为null");
        }
        TlvObject tlvObject = getTlvObjectByPath(tags);
        if (null == tlvObject) {
            log.error("请指定正确的tags");
            throw new RuntimeException("请指定正确的tags");
        }
        tlvObject.add(addTag, value);

        value = tlvObject.build();
        // 替换value
        for (int i = tags.length; i > 1; i--) {
            String[] newTags = new String[i];
            System.arraycopy(tags, 0, newTags, 0, i - 1);
            TlvObject tlv = getTlvObjectByPath(newTags);

            tlv.replace(tags[i - 1], value);
            value = tlv.build();
        }

        this.replace(tags[0], value);

    }

    /**
     * 移除掉到最后一个tag的value
     *
     * @param tags
     */
    public void removeByPath(String... tags) {
        if (tags == null || tags.length == 0) {
            log.error("请指定正确的tag");
            throw new RuntimeException("参数tags至少要一个tag");
        }

        if (tags.length == 1) {
            this.remove(tags[0]);
        } else {
            String[] upperTags = new String[tags.length - 1];
            System.arraycopy(tags, 0, upperTags, 0, tags.length - 1);
            TlvObject upperTlvObject = getTlvObjectByPath(upperTags);
            if (null == upperTlvObject) {
                log.error("请指定正确的tags");
                throw new RuntimeException("请指定正确的tags");
            }
            upperTlvObject.remove(tags[tags.length - 1]);

            byte[] value = upperTlvObject.build();

            // 替换value
            for (int i = tags.length; i > 2; i--) {
                String[] newTags = new String[i];
                System.arraycopy(tags, 0, newTags, 0, i - 2);
                TlvObject tlv = getTlvObjectByPath(newTags);

                tlv.replace(tags[i - 2], value);
                value = tlv.build();
            }
            this.replace(tags[0], value);

        }

    }

}
