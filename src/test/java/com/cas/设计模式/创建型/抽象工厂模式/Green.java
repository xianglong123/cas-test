package com.cas.设计模式.创建型.抽象工厂模式;

public class Green implements Color {
 
   @Override
   public void fill() {
      System.out.println("Inside Green::fill() method.");
   }
}