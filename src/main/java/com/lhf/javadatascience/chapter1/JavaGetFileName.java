package com.lhf.javadatascience.chapter1;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName JavaGetFileName
 * @Desc 使用Java从分层目录中提取所有文件名
 * @Author diandian
 * @Date 2021/12/30 17:23
 **/
public class JavaGetFileName {

    public static Set<File> listFiles(File rootDir){
        Set<File> fileSet = new HashSet<>();  //用来包含文件信息
        if(rootDir == null || rootDir.listFiles() == null){  //检查参数指定的根目录及其子目录是否为null
            return fileSet;
        }
        //遍历根目录下的每个目录(或文件)，判断它是文件还是目录
        for(File file : rootDir.listFiles()){
            if(file.isFile()){  //是文件
                fileSet.add(file);
            }else {  //是目录
                fileSet.addAll(listFiles(file));
            }
        }
        return fileSet;
    }

    public static void main(String[] args) {
        System.out.println(listFiles(new File("F:\\pipeline\\sougou\\美女")));
        System.out.println("文件数："+listFiles(new File("F:\\pipeline\\sougou\\美女")).size());
    }
}