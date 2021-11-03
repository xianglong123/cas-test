package com.cas.jsoup;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/7/27 10:19 下午
 * @desc 解析前端
 */
public class QueryPhoneDetailTest {

    private static final String path = "/Users/xianglong/IdeaProjects/cas-test/src/test/java/com/cas/jsoup/out.txt";


    public static void main(String[] args) throws IOException {

        String[] phoneBrand = {"华为（HUAWEI"};
        String keyWork = "手机";
        List<String> detailList = new ArrayList<>();
        Map<String, Map<String, String>> detailCount = new HashMap<>();

        for (String brand : phoneBrand) {
            System.out.println("================== ===================" + brand);
            String url = "https://search.jd.com/search?keyword=" + URLEncoder.encode(keyWork) + "&ev=exbrand_" + URLEncoder.encode(brand);
            Connection connect = Jsoup.connect(url);
            Document document = connect.get();
            Element element = document.getElementById("J_goodsList");
            List<String> list = element.getElementsByTag("li").eachAttr("data-sku");
            for (String str : list) {
                // 拿到详情页
                detailList.add(str);
                System.out.println(str);
            }

            for (String dUrl : detailList) {
                String urlDetail = "https://item.jd.com/" + dUrl + ".html";
                Connection dConnect = Jsoup.connect(urlDetail);
                dConnect.header("cookie", "__jdu=1622910505114442578799; shshshfpa=bc2fa747-a36c-5bb8-ec0a-ffd36ab01887-1622910506; shshshfpb=ajaucGrcesAWzoDlHbTqy8Q%3D%3D; __jdc=122270672; __jdv=122270672|direct|-|none|-|1635922401701; areaId=6; ipLoc-djd=6-303-305-5328; user-key=a0032e6e-0767-40c6-9714-f07cb22f8848; __jda=122270672.1622910505114442578799.1622910505.1635922402.1635934463.7; wlfstk_smdl=l15sppulse7gj9syo0h0zxyprw82cqvx; TrackID=16RsBZMYEnYsqO-ev7gd7SuUcBBzMFSUF_ndPPpAFlbh_46gNyr3cdT7N1F4KJhXQ6oIhhKLaffzv7LeFeVyEvBrwfjeMOsgRSU1oGAaMlDBDLdx4W8MXkhFUjCgUXo-o; pinId=pttKG1yO9g12EeRDQ_22rrV9-x-f3wj7; pin=jd_7632c015b20aa; unick=jd_7632c015b20aa; ceshi3.com=000; _tp=Cp8%2BaxhjYZYQ1vULp0%2Fg5iqc33BdBEYRG%2FyOMbFEUWM%3D; _pst=jd_7632c015b20aa; thor=5A4287F67DD119E6052EAC8E1A60B93ACC3730796E749C68A85F448C5F72B77533349AAB47920AFA36AB85866D51C6E73CE0243BD7ECE6E840B47A4553336FC89E4AEA1FB0838527DFA455F3699BB1372DF5248595D265679ACD7FDA599A7DFAF43EFDE0C72FF4BE5FC311DAB401E8BEB7B7726EE7F31239873FDAB9B1FB60577C585C5DA0954848C8AC058971ADA35FE19F0DC2935EB7AC1CC3FC2DBAB19728; shshshfp=351f1771ebe5a0568551433d8e8807d5; shshshsID=a940b0794755a7fafb4c3b18aafc84c8_11_1635941029363; __jdb=122270672.15.1622910505114442578799|7.1635934463; 3AB9D23F7A4B3C9B=WWHOHWILDABO2J6FITDSENXI6JPLTPMRG2N5YLQ7GALAYJRMLZ6WVNXYKPDN2T7YP25EQ432QCDY2V2JGQIBJWHVGU; token=54a245bc362040f47c17e98f0ce749d5,3,908856; __tk=iln5ixeFnZaMn0ByicALWZVzVcjMnxbxVZzyn0Rxn0j,3,908856");
                Document dDocument = dConnect.get();
                Elements elementsByTag = dDocument.getElementById("detail").getElementsByTag("dl");
                Map<String, String> map = new HashMap<>();
                for (Element el : elementsByTag) {
                    List<Node> ns = el.childNodes();
                    String key = null, val = null;
                    for (Node n : ns) {
                        if (n instanceof Element) {
                            if (((Element) n).tagName().equalsIgnoreCase("dt")) {
                                key = ((Element) n).text().trim();
                            } else if (((Element) n).tagName().equalsIgnoreCase("dd")) {
                                val = ((Element) n).text().trim();
                            }
                        }
                    }
                    if (key != null) {
                        map.put(key, val);
                    }
                }
                if (map.size() > 0) {
                    detailCount.put(dUrl, sortMapByKey(map));
                    System.out.println();
                }
            }
            StringBuilder str = new StringBuilder();
            int count = 0;
            for (Map.Entry<String, Map<String, String>> entry : detailCount.entrySet()) {
                str.append("-------------------------- 手机【" + count++ +"】 ---------------------\n");
                entry.getValue().forEach((key, val) -> {
                    System.out.println(key + "\t\t" + val + "\n");
                    str.append(key + "\t\t" + val + "\n");
                });
                str.append("-------------------------- 分割线 ---------------------\n");
            }
            outPrint(str.toString());
        }
    }

    private static void outPrint(String str) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "utf-8");){
            oStreamWriter.append(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    //比较器类
    public static class MapKeyComparator implements Comparator<String> {
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
    }

    @Test
    public void test() {
        String string = "vivo";
//        System.out.println(URLDecoder.decode(string));
        System.out.println(URLEncoder.encode(string));
    }

}
