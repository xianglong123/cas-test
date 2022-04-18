package com.cas.controller;

import com.alibaba.excel.EasyExcel;
import com.cas.po.DemoData;
import com.cas.service.Impl.CommonServiceImpl;
import com.cas.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/8/9 10:47 上午
 * @desc
 */
@RestController
public class SingleController {

    private static final Logger log = LoggerFactory.getLogger(SingleController.class);

    @Autowired
    private CommonServiceImpl commonService;

//    @Resource
//    private JmjProperties jmjProperties;
//
//    @Resource
//    private WeightRandom<String, Integer> weightRandom;
    // w a n

    @GetMapping("single")
    public void single() throws InterruptedException {
        commonService.say();
    }


    @GetMapping("export")
    public void export(HttpServletResponse response) throws IOException {
        LocalDate today = LocalDate.now();
        String name = today.minus(1, ChronoUnit.WEEKS).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" +today.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                + "-Dial_Test";
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-disposition", "attachment;filename=" + name + ".xlsx");
        EasyExcel.write(out, DemoData.class)
                .sheet("近一周拨测数据统计")
                .doWrite(data());
    }


    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    /**
     * 获取环境变量
     * @param data
     * @return
     */
    @GetMapping("print/{data}")
    public String print(@PathVariable("data") String data) {
        String property = System.getProperty(data);
        if (StringUtils.isBlank(property)) {
            return data + " is not found";
        }
        return property;
    }

    @PostMapping("/jmj2")
    public String jmj2() {
        StringUtil.put();
        return "ok";
    }

    @PostMapping("/jmj")
    public String jmj() {
        System.out.println(StringUtil.get());
        return "ok";
    }


}
