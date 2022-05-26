package com.cas.设计模式.行为型.责任链模式;


/**
 * author: cainiao
 * 责任链的核心思路就是
 * 1、有一个判断依据
 * 2、每一个处理类中包含下一个处理类的引用
 * 3、判断不成立会将数据传递给下一个处理类
 * 想了想能用的场景并不多，日志级别举例还差不多，生产不会这么用
 */
public abstract class AbstractLogger {
   public static int INFO = 1;
   public static int DEBUG = 2;
   public static int ERROR = 3;
 
   protected int level;
 
   //责任链中的下一个元素
   protected AbstractLogger nextLogger;
 
   public void setNextLogger(AbstractLogger nextLogger){
      this.nextLogger = nextLogger;
   }
 
   public void logMessage(int level, String message){
      if(this.level <= level){
         write(message);
      }
      if(nextLogger !=null){
         nextLogger.logMessage(level, message);
      }
   }
 
   abstract protected void write(String message);
   
}