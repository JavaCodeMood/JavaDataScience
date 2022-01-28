package com.lhf.javadatascience.chapter6;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName TextSimilarity
 * @Desc 使用Java8借助余弦相似性测度测量文本相似度
 * 余弦相似度，又称为余弦相似性，是通过计算两个向量的夹角余弦值来评估他们的相似度。余弦相似度将向量根据坐标值，绘制到向量空间中，如最常见的二维空间。
 * @Author diandian
 * @Date 2022/1/20 14:37
 **/
public class TextSimilarity {

    /**
     * 测量文本相似度
     * 1. 首先，从两个字符串提取单词。
     * 2.个字符串中的每个单词，计算出现频率。这里所说的“出现频率”是指单词在
     * 每个句子中出现的次数。设A为单词向量，表示它们在第一个字符串出现的频率， B也是单词向量，表示它们在第二个字符串中出现的频率。
     * 3.通过删除重复项，找出每个字符串的特有单词。
     * 4.找出两个字符串都有的单词。
     * 5.余弦相似度公式的分子是向量A与B的点积。
     * 6.公式的分母是向量A与B大小的算术积。
     * 请注意，两个句子的余弦相似度分数介于-1(表示正好相反)到1(表示完全一样)之间，0分表示去相关。
     * @param s1
     * @param s2
     * @return
     */
    public double calculateCosine(String s1, String s2){
        //使用正则表达式与Java8的并行功能对给定的字符串进行切分，得到单词的两个字符串流
        Stream<String> stream1 = Stream.of(s1.toLowerCase().split("\\W+")).parallel();
        Stream<String> stream2 = Stream.of(s2.toLowerCase().split("\\W+")).parallel();

        //计算每个字符串中每个单词出现的频率，存入map
        Map<String, Long> wordFreq1 = stream1
                .collect(Collectors.groupingBy(String::toString,Collectors.counting()));
        Map<String, Long> wordFreq2 = stream2
                .collect(Collectors.groupingBy(String::toString,Collectors.counting()));

        //对于每个句子的单词列表，删除其中的重复项，仅仅保留唯一的单词
        Set<String> wordSet1 = wordFreq1.keySet();
        Set<String> wordSet2 = wordFreq2.keySet();

        //将两个字符串中共有的单词创建列表，用于计算上面两个Map的点积
        Set<String> intersection = new HashSet<String>(wordSet1);
        intersection.retainAll(wordSet2);

        //计算余弦公式的分子，它是两个Map的点积
        double numerator = 0;
        for (String common: intersection){
            numerator += wordFreq1.get(common) * wordFreq2.get(common);
        }

        //用于保存向量大小的值
        double param1 = 0, param2 = 0;

        //计算第一个向量的大小
        for(String w1: wordSet1){
            param1 += Math.pow(wordFreq1.get(w1), 2);
        }
        param1 = Math.sqrt(param1);

        //计算第二个向量的大小
        for(String w2: wordSet2){
            param2 += Math.pow(wordFreq2.get(w2), 2);
        }
        param2 = Math.sqrt(param2);

        //两个向量大小的乘积，就是余弦相似度的分母
        double denominator = param1 * param2;

        //计算出两个字符串的余弦相似度
        double cosineSimilarity = numerator/denominator;
        return cosineSimilarity;
    }

    public static void main(String[] args){
        TextSimilarity cos = new TextSimilarity();
        System.out.println(cos.calculateCosine("To be, or not to be: that is the question.", "Frailty, thy name is woman!"));
        System.out.println(cos.calculateCosine("The lady doth protest too much, methinks.", "Frailty, thy name is woman!"));

        System.out.println(cos.calculateCosine("hello world", "hello world"));

    }
}