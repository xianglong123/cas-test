package com.cas.easyExcel;

import com.alibaba.excel.EasyExcel;
import com.cas.easyExcel.bean.FillData;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/12/1 10:22 下午
 * @desc
 */
public class ExcelTest {

    @Test
    public void simpleFill() {
        // 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
        String templateFileName = "/Users/xianglong/IdeaProjects/cas-test/src/test/resources/" + "demo" + File.separator + "fill" + File.separator + "simple.xlsx";
//        // 方案1 根据对象填充
//        String fileName = "/Users/xianglong/IdeaProjects/cas-test/src/test/java/com/cas/easyExcel/FillTest1.xlsx";
//        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
//        FillData fillData = new FillData();
//        fillData.setName("张三");
//        fillData.setNumber(5.2);
//        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(fillData);

        // 方案2 根据Map填充
        String fileName = "/Users/xianglong/IdeaProjects/cas-test/src/test/resources/" + "simpleFill" + System.currentTimeMillis() + ".xlsx";
        // 这里 会填充到第一个sheet， 然后文件流会自动关闭
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("number", 5.2);
        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(map);
    }


}
