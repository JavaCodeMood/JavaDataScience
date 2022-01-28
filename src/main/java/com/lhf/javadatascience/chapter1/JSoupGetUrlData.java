package com.lhf.javadatascience.chapter1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @ClassName JSoupGetUrlData
 * @Desc 使用JSoup从URL提取web数据
 * @Author diandian
 * @Date 2022/1/5 11:30
 **/
public class JSoupGetUrlData {

    /**
     * 从指定的URL中提取web数据
     * @param href 地址
     */
    public void getUrlDataByJSoup(String href){
        Document doc = null;
        try {
            //获取url中的内容，并设置超时时间以及忽略错误
            doc = Jsoup.connect(href).timeout(10*1000*10).userAgent("Mozilla").ignoreHttpErrors(true).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(doc != null){
            String title = doc.title();
            String text = doc.text();
            String html = doc.html();
            String body = doc.body().html();
            String bodyText = doc.body().text();

            System.out.println("title: " + title);
            System.out.println("\ntext: " + text);
            System.out.println("\nhtml: " + html);
            System.out.println("\nbody: " + body);
            System.out.println("\nbodyText: " + bodyText);

            Elements links = doc.select("a[href]");
            for(Element link : links){
                String linkHref = link.attr("href");
                String linkText = link.text();
                String linkOuterHtml = link.outerHtml();
                String linkInnerHtml = link.html();
                String baseUri = link.baseUri();

                System.out.println("\nlinkHref: " + linkHref);
                System.out.println("\nlinkText: " + linkText);
                System.out.println("\nlinkOuterHtml: " + linkOuterHtml);
                System.out.println("\nlinkInnerHtml: " + linkInnerHtml);
                System.out.println("\nbaseUri = " + baseUri);
            }
        }
    }

    public static void main(String[] args) {
        JSoupGetUrlData jsoup = new JSoupGetUrlData();
        jsoup.getUrlDataByJSoup("https://www.imooc.com/");
    }
}