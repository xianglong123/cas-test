package com.cas.img;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;

/**
 * 图片压缩成jpg格式
 * 如果需要转换成其他格式，只需要改ImageIO.write的第二个参数即可
 */
public class Base64Image {

    public static void main(String[] args) {
        String base64String = "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"; // 将要转换的base64字符串

        try {
            // 将 base64 字符串解码为图片字节数组
            byte[] imageBytes = Base64.getDecoder().decode(base64String.getBytes("UTF-8"));

            // 使用 ByteArrayInputStream 和 ImageIO 读取字节数组并转换为 BufferedImage 对象
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
            BufferedImage image = ImageIO.read(inputStream);

            // 将转换后的图片保存为 JPEG 格式文件
            File output = new File("output.jpg");
            ImageIO.write(image, "jpg", output);

            System.out.println("转换成功!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
