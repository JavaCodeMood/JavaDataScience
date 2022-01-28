package com.lhf.javadatascience.chapter1;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.util.List;

/**
 * @ClassName FileListing
 * @Desc 使用Apache Commons IO从多层目录中提取所有文件名
 * @Author diandian
 * @Date 2021/12/30 17:32
 **/
public class FileListing {

    public void listFiles(String rootDir){
        File dir = new File(rootDir);

        //提取所有文件名放入list列表中
        //TrueFileFilter.INSTANCE 用于匹配所有目录
        List<File> fileList = (List<File>) FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        for(File file : fileList){
            System.out.println("file:" + file.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        FileListing fileListing = new FileListing();
        fileListing.listFiles("F:\\pipeline\\sougou\\美女");
    }
}