package com.lhf.javadatascience.chapter1;

import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName UnivocityReadTsv
 * @Desc 使用Univocity解析TSV文件
 * @Author diandian
 * @Date 2022/1/4 22:11
 **/
public class UnivocityReadTsv {
    /**
     * 解析TSV文件
     * @param fileName  待解析的文件名
     * @return
     */
    public List<String[]> parseTsv(String fileName){
        //创建一个配置对象
        TsvParserSettings settings = new TsvParserSettings();
        //这里的TSV文件的行分隔符为换行符或n
        settings.getFormat().setLineSeparator("n");
        //创建一个TSV解析器
        TsvParser parser = new TsvParser(settings);
        //把TSV文件中的所有行一次性解析出来
        List<String[]> allRows = parser.parseAll(new File(fileName));
        /*for(int i = 0; i < allRows.size(); i++){
            System.out.println(Arrays.asList(allRows.get(i)));
        }*/
        return allRows;
    }

    public static void main(String[] args) {
        UnivocityReadTsv readTsv = new UnivocityReadTsv();
        List<String[]> allRows = readTsv.parseTsv("doc/test.tsv");
        for(int i = 0; i < allRows.size(); i++){
            System.out.println(Arrays.asList(allRows.get(i)));
        }
    }
}