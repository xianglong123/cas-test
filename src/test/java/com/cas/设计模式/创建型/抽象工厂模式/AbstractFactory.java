package com.cas.设计模式.创建型.抽象工厂模式;

public abstract class AbstractFactory {
   public abstract Color getColor(String color);
   public abstract Shape getShape(String shape);
}