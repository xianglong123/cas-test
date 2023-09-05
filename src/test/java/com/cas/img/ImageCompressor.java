package com.cas.img;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 图片压缩
 */
public class ImageCompressor {

    public static void main(String[] args) throws IOException {
        
        // 读取原始图片
        File input = new File("/xxxx/demo.jpeg");
        BufferedImage image = ImageIO.read(input);
        
        // 计算压缩比例，这里使用16:9的宽高比
        double ratio = Math.min(192.0 / image.getWidth(), 108.0 / image.getHeight());
        
        // 创建压缩后图片的缓冲区
        int w = (int)(image.getWidth() * ratio);
        int h = (int)(image.getHeight() * ratio);
        BufferedImage compressedImage = new BufferedImage(w, h, image.getType());
        
        // 使用Graphics2D进行缩放
        Image temp = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        Graphics2D g2d = compressedImage.createGraphics();
        g2d.drawImage(temp, 0, 0, null);
        g2d.dispose();
        
        // 输出压缩后的图片
        File output = new File("compressed.jpg");
        ImageIO.write(compressedImage, "jpg", output);
    }
}

