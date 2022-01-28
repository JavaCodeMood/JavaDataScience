package com.lhf.javadatascience.chapter6;

import java.text.BreakIterator;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName WordDetectionTest
 * @Desc 使用Java检测标记（单词）
 * @Author diandian
 * @Date 2022/1/14 12:31
 **/
public class WordDetectionTest {

    /**
     * 使用Java的StringTokenizer类来检测标记
     * @param input  句子字符串
     */
    public void useTokenizer(String input){
        System.out.println("Tokenizer");
        //创建StringTokenizer对象，并接收一个字符串
        StringTokenizer tokenizer = new StringTokenizer(input);
        String word ="";  //用于保存标记
        //遍历tokenizer，并打印
        while(tokenizer.hasMoreTokens()){
            word = tokenizer.nextToken();
            System.out.println(word);
        }
    }

    /**
     * 使用java的BreakIterator类遍历文本中的每个单词
     * @param input
     */
    public void useBreakIterator(String input){
        System.out.println("Break Iterator");
        //创建BreakIterator对象
        BreakIterator tokenizer = BreakIterator.getWordInstance();
        tokenizer.setText(input);
        //获取tokenizer的开始索引
        int start = tokenizer.first();
        //使用for循环获取每个标记，从输入句子提取子字符串后，并打印
        for (int end = tokenizer.next();
             end != BreakIterator.DONE;
             start = end, end = tokenizer.next()) {
            System.out.println(input.substring(start,end));
        }
    }

    /**
     * 使用正则表达式对字符串进行切分
     * @param input
     */
    public void useRegEx(String input){
        System.out.println("Regular Expression");
        //使用正则表达式的模式来捕获标点符号，单个或多个带有连字符的单词，引号，撇号等
        //Pattern pattern = Pattern.compile("\\w[\\w-]+('\\w*)?");
        Pattern pattern = Pattern.compile("/^\\[.*\\]$/g");   //  \\[.*\\]
        Matcher matcher = pattern.matcher(input);
        //使用matcher从输入文本提取所有单词
        while ( matcher.find() ) {
            System.out.println(input.substring(matcher.start(), matcher.end()));
        }
    }

    public static void main(String[] args){
        //String input = "\"Let's get this vis-a-vis\", he said, \"these boys' marks are really that well?\"";
        String input = "[myloan] 恭喜你贷款成功，$<780.00> 已经到你的收款账户，请按时还款 [AprestamoPlus]恭喜获得4000积分";
        WordDetectionTest wordDetection = new WordDetectionTest();
        //wordDetection.useTokenizer(input);
        //wordDetection.useBreakIterator(input);
        wordDetection.useRegEx(input);

    }

}