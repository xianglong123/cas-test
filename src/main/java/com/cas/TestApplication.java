package com.cas;

import com.cas.autoconfiguration.MyService;
import com.cas.util.ByteUtil;
import com.cas.util.HexConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author: xianglong[1391086179@qq.com]
 * @date: 上午10:26 2021/3/22
 * @version: V1.0
 * @review:
 */
@Slf4j
@SpringBootApplication
@RestController
public class TestApplication {
    @Autowired
    MyService myService;

    @GetMapping("/")
    String testStarter(){
        return myService.getName()+":"+myService.getAge();
    }

    public static void main(String[] args) {
        try{
            SpringApplication.run(TestApplication.class, args);
            System.out.println("测试环境启动成功！！！！");
        } catch (Exception e) {
            System.out.println("测试环境启动失败！！！！");
        }
    }

}
