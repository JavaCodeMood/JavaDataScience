package com.lhf.javadatascience.chapter1;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName TikaExtraction
 * @Desc 使用Tika对象自动解析文档，提取文档内容
 * @Author diandian
 * @Date 2022/1/6 17:56
 **/
public class TikaExtraction {

    public static void main(String[] args) throws IOException, TikaException {
        Tika tika = new Tika();
        // 新建存放各种文件的files文件夹
        File fileDir = new File("G:\\liuhefei\\G\\code1\\JavaDataScience\\input");
        // 如果文件夹路径错误，退出程序
        if (!fileDir.exists()) {
            System.out.println("文件夹不存在, 请检查!");
            System.exit(0);
        }
        // 获取文件夹下的所有文件，存放在File数组中
        File[] fileArr = fileDir.listFiles();
        String filecontent;
        for (File f : fileArr) {
            // 获取文件名
            System.out.println("File Name: " + f.getName());
            //filecontent = tika.parseToString(f);// 自动解析
            filecontent = tika.parseToString(f);
            // 获取文件内容
            System.out.println("Extracted Content: " + filecontent);
        }
    }

}