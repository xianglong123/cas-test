package com.cas.设计模式.原型模式;

import com.cas.设计模式.原型模式.impl.Rectangle;
import com.cas.设计模式.原型模式.impl.Square;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/9/1 7:15 下午
 * @desc
 */
public class Psvm {


    public static void main(String[] args) {
        Square square = new Square();
        System.out.println(square);
        Rectangle rectangle = new Rectangle(square);
        Rectangle clone = (Rectangle) rectangle.clone();
        System.out.println(rectangle.getSquare().getType());
        System.out.println(clone.getSquare().getType());
        rectangle.getSquare().setType("123");
        System.out.println(clone.getSquare().getType());
        System.out.println(rectangle.getSquare().getType());
    }

}
