package com.cas.jsoup;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/7/27 10:19 下午
 * @desc 解析前端
 */
public class JsoupTest {

    public static void main(String[] args) throws IOException {
        String url = "https://search.jd.com/Search?keyword=java";
        Connection connect = Jsoup.connect(url);
        Document document = connect.get();
        Element element = document.getElementById("J_goodsList");

        Elements elements = element.getElementsByTag("li");

        for (Element el : elements) {
            String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img");
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            System.out.println("======================");
            System.out.println(img);
            System.out.println(price);
            System.out.println(title);
        }
    }

    @Test
    public void test() {
        String string = "%E5%8D%8E%E4%B8%BA%EF%BC%88HUAWEI%EF%BC%89%5E";
        System.out.println(URLDecoder.decode(string));
    }

}
