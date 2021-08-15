package com.cas.easyExcel.test;

import com.alibaba.excel.EasyExcel;
import com.cas.easyExcel.bean.DemoData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/8/13 9:45 上午
 * @desc
 */
public class EasyExcelTest {

    public static void main(String[] args) {
        String fileName = "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, DemoData.class)
                .sheet("模板").doWrite(data());

    }

    private static List<DemoData> data() {
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

}
