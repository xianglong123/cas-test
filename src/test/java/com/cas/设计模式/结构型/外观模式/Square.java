package com.cas.设计模式.结构型.外观模式;

public class Square implements Shape {
 
   @Override
   public void draw() {
      System.out.println("Square::draw()");
   }
}