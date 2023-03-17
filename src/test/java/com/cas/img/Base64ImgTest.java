package com.cas.img;


import com.cas.des.des3_ecb.HexConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 下午3:44 2021/4/27
 * @version: V1.0
 * @review: 图片base64加密解密
 */
//图片文件转Base64编码
public class Base64ImgTest {


    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(buffer);
    }

    /**
     * <p>将base64字符解码保存文件</p>
     */
    public static void decoderBase64File(String base64Code, String targetPath) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] buffer = decoder.decode(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    /**
     * <p>将base64字符保存文本文件</p>
     */
    public static void toFile(String base64Code, String targetPath) throws Exception {
        byte[] buffer = base64Code.getBytes();
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }


    public static void main(String[] args) {
        try {
            // base64加密
            String rootPath = "/Users/xianglong/IdeaProjects/cas-test/src/test/java/com/cas/img";
            String base64Code = encodeBase64File(rootPath + "/224.jpeg");
//            System.out.println(base64Code);
//            System.out.println(HexConverter.hexString2ByteArray());
//             base64解密
//            decoderBase64File(base64Code, rootPath + "/22.jpg");
//             密文保存R
            toFile(base64Code, rootPath + "/base66.txt");

//            String code = "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";
//            decoderBase64File(code, rootPath + "3.jpg");
//            byte[] bytes = code.getBytes();
//            System.out.println(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
