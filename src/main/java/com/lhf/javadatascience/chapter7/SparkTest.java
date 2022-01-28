package com.lhf.javadatascience.chapter7;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

/**
 * @ClassName SparkTest
 * @Desc 使用Apache Spark解决单文本挖掘问题 获取单词词频
 * https://github.com/apache/spark
 * @Author diandian
 * @Date 2022/1/25 18:24
 **/
public class SparkTest implements Serializable {

    public void sparkTxt(String inputFile){
        //spark的属性用来控制应用程序设置，每个应用程序都要单独进行配置。设置这些属性时，我们可以使用传递给SparkContext的SparkConf.
        //通过SparkConf我们可以配置一些通用属性
        //local[4] 表示允许应用程序并行运行4个线程
        SparkConf configuration = new SparkConf().setMaster("local[4]").setAppName("My App");
        JavaSparkContext sparkContext = new JavaSparkContext(configuration);
        //JavaRDD是一个分布式的对象集合。创建一个RDD对象，用来收集shakespeare.txt文件中的空行
        JavaRDD<String> rdd = sparkContext.textFile(inputFile).cache();
        //统计输入数据文件中所包含的空行数
        long emptyLines = rdd.filter(new Function<String, Boolean>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Boolean call(String s) throws Exception {
                return s.length() == 0;
            }
        }).count();
        System.out.println("Empty Lines: " + emptyLines);
        //从输入数据文件获取词频
        JavaPairRDD<String, Integer> wordCounts =
                rdd.flatMap(s -> Arrays.asList(s.toLowerCase(Locale.ROOT).split(" ")))
                .mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey((a, b) -> a + b);
        //借助wordCounts RDD,得到文档中的单词及其出现频数，并把它们放入Map中
        Map<String, Integer> wordMap = wordCounts.collectAsMap();
        //遍历Map,把单词-频数成对打印出来
        for(Map.Entry<String, Integer> entry : wordMap.entrySet()){
            System.out.println("Word = " + entry.getKey() + " , Frequency = " + entry.getValue());
        }
        sparkContext.close();

    }

    public static void main(String[] args) {
        SparkTest sparkTest = new SparkTest();
        sparkTest.sparkTxt("input/shakespeare.txt");
    }
}