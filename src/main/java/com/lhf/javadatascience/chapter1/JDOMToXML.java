package com.lhf.javadatascience.chapter1;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName JDOMToXML
 * @Desc 使用JDOM解析XML文件
 * @Author diandian
 * @Date 2022/1/4 22:21
 **/
public class JDOMToXML {
    public static void main(String[] args){
        JDOMToXML test = new JDOMToXML();
        test.parseXml("doc/book.xml");

    }

    public void parseXml(String fileName){
        SAXBuilder builder = new SAXBuilder();
        File file = new File(fileName);
        try {
            //创建Document对象，它表示XML文件
            Document document = (Document) builder.build(file);
            //创建Element类型的rootNode对象，用来保存根元素
            Element rootNode = document.getRootElement();
            //获取根节点下所有名称为author的子节点，用列表来存储
            List list = rootNode.getChildren("author");
            //遍历子节点列表
            for (int i = 0; i < list.size(); i++) {
                Element node = (Element) list.get(i);
                System.out.println("First Name : " + node.getChildText("firstname"));
                System.out.println("Last Name : " + node.getChildText("lastname"));
            }
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }



    }
}