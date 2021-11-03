package com.cas.img;


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
            String base64Code = encodeBase64File("/Users/xianglong/IdeaProjects/cas-test/src/test/java/com/cas/img/2.jpeg");
            System.out.println(base64Code);
            // base64解密
            decoderBase64File(base64Code, "/Users/xianglong/IdeaProjects/cas-test/src/test/java/com/cas/img/22.jpg");
            // 密文保存R
            toFile(base64Code, "/Users/xianglong/IdeaProjects/cas-test/src/test/java/com/cas/img/base64.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
