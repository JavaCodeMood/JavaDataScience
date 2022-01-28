package com.lhf.javadatascience.chapter3;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName WordFrequencyStatsJava
 * @Desc 计算字符串中的词频
 * @Author diandian
 * @Date 2022/1/7 21:45
 **/
public class WordFrequencyStatsJava {

    /**
     * 采用Java8的方式计算字符串中的词频
     * @param str
     * @return
     */
    public Map<String, Long> getWordFreqStats(String str){
        Stream<String> stream = Stream.of(str.toLowerCase(Locale.ROOT).split("\\W+")).parallel();
        Map<String, Long> wordFreq = stream.collect(Collectors.groupingBy(String :: toString, Collectors.counting()));
        return wordFreq;
    }

    public static void main(String[] args) {
        String str = "A good persuasion : therefore , hear me , Hermia .\n" +
                "I have a widow aunt , a dowager \n" +
                "Of great revenue , and she hath no child :\n" +
                "From Athens is her house remote seven leagues ;\n" +
                "And she respects me as her only son .\n" +
                "There , gentle Hermia , may I marry thee ,\n" +
                "And to that place the sharp Athenian law \n" +
                "Cannot pursue us . If thou lov'st me then ,\n" +
                "Steal forth thy father's house to-morrow night ,\n" +
                "And in the wood , a league without the town ,\n" +
                "Where I did meet thee once with Helena ,\n" +
                "To do observance to a morn of May ,\n" +
                "There will I stay for thee .";
        WordFrequencyStatsJava statsJava = new WordFrequencyStatsJava();
        Map<String, Long> stringLongMap = statsJava.getWordFreqStats(str);
        stringLongMap.forEach((k,v) -> System.out.println(k + " 出现的个数：" + v));

    }
}