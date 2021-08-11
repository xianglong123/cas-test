package com.cas.controller;

import com.cas.service.Impl.XiangMing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/8/9 10:47 上午
 * @desc
 */
@RestController
public class SingleController {

    @Autowired
    private XiangMing xiangMing;

    @GetMapping("single")
    public void single() throws InterruptedException {
        xiangMing.say();
    }

}
