package com.lhf.javadatascience.chapter1;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName TextFileReadApache
 * @Desc 使用Apache Commons IO从文本文件一次性读取所有内容
 * @Author diandian
 * @Date 2021/12/30 17:54
 **/
public class TextFileReadApache {

    public String readFile(String fileName){
        File file = new File(fileName);
        String text = "";
        try {
            text = FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            System.out.println("Error reading " + file.getAbsolutePath());
        }

        return text;
    }
    public static void main(String[] args) {
        TextFileReadApache readTextContent = new TextFileReadApache();
        String content = readTextContent.readFile("doc/meiwen.txt");
        System.out.println("content = " + content);
    }
}