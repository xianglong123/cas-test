package com.cas;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 上午10:26 2021/3/22
 * @version: V1.0
 * @review:
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.cas")
public class TestApplication {

    public static void main(String[] args) {
        try{
            SpringApplication.run(TestApplication.class, args);
            System.out.println("测试环境启动成功！！！！");
        } catch (Exception e) {
            System.out.println("测试环境启动失败！！！！");
        }
    }
}
