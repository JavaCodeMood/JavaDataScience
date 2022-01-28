package com.lhf.javadatascience.chapter1;

/**
 * @ClassName CleaningData
 * @Desc 使用正则表达式清洗ASCII文本文件
 * @Author diandian
 * @Date 2022/1/4 21:34
 **/
public class CleaningData {

    public String clearText(String strText){
        strText = strText.replaceAll("[^p{ASCII}]", "");   //去掉非ASCII字符
        strText = strText.replaceAll("s+", " ");  //把连续的空格替换为单个空格
        strText = strText.replaceAll("\\p{Cntrl}", "");  //清除所有ASCII控制字符
        strText = strText.replaceAll("[^p{Print}]", "");  //去除ASCII非打印字符
        strText = strText.replaceAll("\\p{C}", "");  //从Unicode移除非打印字符
        return strText;
    }

    public static void main(String[] args) {
        CleaningData cleaningData = new CleaningData();
        String text = "For instance, one study conducted in March 2020 involving more than 6,000 Americans found that the more time participants spent consuming COVID news in a day, the unhappier they felt.";
        System.out.println(cleaningData.clearText(text));
    }
}