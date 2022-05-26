package com.cas.设计模式.结构型.适配器模式;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/5/5 3:08 下午
 * @desc
 */
public class Mp4Player implements AdvancedMediaPlayer{

    @Override
    public void playVlc(String fileName) {
        // no do
    }

    @Override
    public void playMp4(String fileName) {
        System.out.println("Playing mp4 file.Name:" + fileName);
    }
}
