package com.cas.设计模式.创建型.抽象工厂模式;

public class Square implements Shape {
 
   @Override
   public void draw() {
      System.out.println("Inside Square::draw() method.");
   }
}