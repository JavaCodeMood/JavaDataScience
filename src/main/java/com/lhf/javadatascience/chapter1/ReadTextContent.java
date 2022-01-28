package com.lhf.javadatascience.chapter1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @ClassName ReadTextContent
 * @Desc 一次性读取文本文件中的所有内容
 * @Author diandian
 * @Date 2021/12/30 17:41
 **/
public class ReadTextContent {

    //使用Paths类的get()方法可以读取到待读文件的路径
    //lines()方法包含于Files类中，用来读取一个文件的所有行，并返回stream
    public void readText(String filePath){
        try(Stream<String> stream = Files.lines(Paths.get(filePath))){
            stream.forEach(System.out::println);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ReadTextContent readTextContent = new ReadTextContent();
        readTextContent.readText("doc/meiwen.txt");
    }
}