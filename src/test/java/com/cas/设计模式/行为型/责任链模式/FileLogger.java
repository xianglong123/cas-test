package com.cas.设计模式.行为型.责任链模式;

public class FileLogger extends AbstractLogger {
 
   public FileLogger(int level){
      this.level = level;
   }
 
   @Override
   protected void write(String message) {    
      System.out.println("File::Logger: " + message);
   }
}