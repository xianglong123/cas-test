package com.cas.service.Impl;

import com.cas.service.CommonService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/8/9 10:40 上午
 * @desc
 */
@Service
public class CommonServiceImpl extends CommonService {

    @Override
    public void say() throws InterruptedException {
        Date date = new Date();
        list.add(date.toString());
        System.out.println("say() is sleep start...");
        Thread.sleep(100000);
        System.out.println("say() is sleep end.....");
    }
}
