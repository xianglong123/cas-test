package com.cas.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        EasyExcel.write(out, ExcelData.class)
                .sheet("近一周拨测数据统计")
                .doWrite(data());
    }


    private List<ExcelData> data() {
        List<ExcelData> list = new ArrayList<ExcelData>();
        for (int i = 0; i < 10; i++) {
            ExcelData data = new ExcelData();
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


    @HeadStyle//表头样式
    @ContentStyle//内容样式
    @ColumnWidth(20)
    public static class ExcelData {
        @ExcelProperty("字符串标题")
        private String string;
        @ExcelProperty("日期标题")
        private Date date;
        @ExcelProperty("数字标题")
        private Double doubleData;
        /**
         * 忽略这个字段
         */
        @ExcelIgnore
        private String ignore;


        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Double getDoubleData() {
            return doubleData;
        }

        public void setDoubleData(Double doubleData) {
            this.doubleData = doubleData;
        }

        public String getIgnore() {
            return ignore;
        }

        public void setIgnore(String ignore) {
            this.ignore = ignore;
        }
    }

}
