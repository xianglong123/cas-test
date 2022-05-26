package com.cas.设计模式.行为型.命令模式;

public class Stock {
   
   private String name = "ABC";
   private int quantity = 10;
 
   public void buy(){
      System.out.println("buy:" + name + ": " + quantity);
   }
   public void sell(){
      System.out.println("sell:" + name + ": " + quantity);
   }
}