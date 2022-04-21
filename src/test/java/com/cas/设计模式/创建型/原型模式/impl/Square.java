package com.cas.设计模式.创建型.原型模式.impl;

import com.cas.设计模式.创建型.原型模式.Shape;

public class Square extends Shape {
 
   public Square(){
     type = "Square";
   }
 
   @Override
   public void draw() {
      System.out.println("Inside Square::draw() method.");
   }
}