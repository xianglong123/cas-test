package com.cas.设计模式.原型模式.impl;

import com.cas.设计模式.原型模式.Shape;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/9/1 7:06 下午
 * @desc
 */
public class Rectangle extends Shape {

    private Square square;

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public Rectangle(Square square) {
        this.square = square;
    }

    public Rectangle(){
        type = "Rectangle";
    }

    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
