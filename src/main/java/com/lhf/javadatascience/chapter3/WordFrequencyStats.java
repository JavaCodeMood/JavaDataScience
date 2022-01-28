package com.lhf.javadatascience.chapter3;

import org.apache.commons.math3.stat.Frequency;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @ClassName WordFrequencyStats
 * @Desc 计算字符串中的词频
 * @Author diandian
 * @Date 2022/1/7 21:09
 **/
public class WordFrequencyStats {

    /**
     * 计算单词词频
     * @param words
     * @return
     */
    public Map<String, Integer> getWordFreqStats(String[] words){
        Frequency freq = new Frequency();
        for(int i = 0; i < words.length; i++){
            freq.addValue(words[i].trim());
        }

        Map<String, Integer> stringIntegerMap = new HashMap<>();
        for(int i = 0; i < words.length; i++){
            stringIntegerMap.put(words[i], (int)freq.getCount(words[i]));
        }
        return stringIntegerMap;
    }

    public static void main(String[] args) {
        WordFrequencyStats wordFreq = new WordFrequencyStats();
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

        String[] words = str.toLowerCase(Locale.ROOT).split("\\W+");
        Map<String, Integer> strMap = wordFreq.getWordFreqStats(words);
        for(String key : strMap.keySet()){
            Integer value = strMap.get(key);
            System.out.println(key + " 个数：" + value);
        }
    }
}