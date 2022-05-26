package com.cas.easypoi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/12/1 2:03 上午
 * @desc
 */
public class PoiTest {

    @Test
    public void one() throws Exception {
        TemplateExportParams params = new TemplateExportParams(
                "/Users/xianglong/IdeaProjects/cas-test/src/test/java/com/cas/easypoi/foreach1.xlsx");
        Map<String, Object> temp = new HashMap<>();
        List<ImageEntity> list = new ArrayList<>();
        for (int i = 1; i < 8; i++) {
            list.add(new ImageEntity(i, i+1, i+2));
        }
        temp.put("list", list);
        Workbook book = ExcelExportUtil.exportExcel(params, temp);
        File savefile = new File("/Users/xianglong/IdeaProjects/cas-test/src/test/java/com/cas/easypoi");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("/Users/xianglong/IdeaProjects/cas-test/src/test/java/com/cas/easypoi/test.xlsx");
        book.write(fos);
        fos.close();
    }

}
