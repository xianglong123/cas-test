package com.cas.设计模式.结构型.外观模式;

public class Circle implements Shape {
 
   @Override
   public void draw() {
      System.out.println("Circle::draw()");
   }
}