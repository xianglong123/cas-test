package com.cas.设计模式.结构型.适配器模式;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2022/5/5 3:07 下午
 * @desc
 */
public class VlcPlayer implements AdvancedMediaPlayer{

    @Override
    public void playVlc(String fileName) {
        System.out.println("Playing vlc file. Name:" + fileName);
    }

    @Override
    public void playMp4(String fileName) {
        // 什么也不做
    }
}
