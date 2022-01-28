package com.lhf.javadatascience.chapter6;

import java.text.BreakIterator;
import java.util.Locale;

/**
 * @ClassName SentenceDetectionTest
 * @Desc 使用Java检测句子
 * @Author diandian
 * @Date 2022/1/14 14:36
 **/
public class SentenceDetectionTest {
    /**
     * 使用Java的BreakIterator类检测句子
     * @param source
     */
    public void useSentenceInstance(String source){
        //创建BreakIterator对象
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        iterator.setText(source);

        //获取字符串的起始索引，并将其存在一个整型变量中
        int start = iterator.first();
        //遍历iterator中的所有句子，并打印
        for(int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()){
            System.out.println(source.substring(start, end));
        }
    }

    public static void main(String[] args) {
        SentenceDetectionTest test = new SentenceDetectionTest();
        String text = "Every Sunday morning I take a light jog around a park near my home. There’s a lake located in one corner of the park. Each time I jog by this lake, I see the same elderly woman sitting at the water’s edge with a small metal cage sitting beside her." +
                "“Well then, don’t you think your time could be better spent? And 99% of these turtles don’t have kind people like you to help them clean off their shells. So, no offense… but how exactly are your localized efforts here truly making a difference?”";
        test.useSentenceInstance(text);
    }
}