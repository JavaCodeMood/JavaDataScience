package com.lhf.javadatascience.chapter7;

import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.SparkConf;

import java.io.Serializable;


/**
 * @ClassName KMeansClusteringMlib
 * @Desc 使用MLib的K均值算法对无标签数据点进行聚类
 * 数据集：https://github.com/apache/spark/blob/master/data/mllib/kmeans_data.txt 下载重命名为km_data.txt
 * @Author diandian
 * @Date 2022/1/25 19:52
 **/
public class KMeansClusteringMlib implements Serializable {

    /**
     * K均值算法对无标签数据点进行聚类
     * @param inputFile
     */
    public Double kMeansMlib(String inputFile){
        //创建一个Spark配置，并使用该配置，创建一个Spark context。local[4]允许应用程序运行4个线程
        SparkConf conf = new SparkConf().setMaster("local[4]").setAppName("K-means Example");
        JavaSparkContext sc = new JavaSparkContext(conf);

        //JavaRDD是一个分布式的对相集。创建一个RDD对象，以便读取数据文件
        JavaRDD<String> data = sc.textFile(inputFile);
        //从前面的RDD读取数据值，这些值之间以空格键作为分割，对这些数据值进行解析，并把它们读到另一个RDD中
        JavaRDD<Vector> parsedData = data.map(
                new Function<String, Vector>() {
                    public Vector call(String s) {
                        String[] sarray = s.split(" ");
                        double[] values = new double[sarray.length];
                        for (int i = 0; i < sarray.length; i++)
                            values[i] = Double.parseDouble(sarray[i]);
                        return Vectors.dense(values);
                    }
                }
        );
        parsedData.cache();

        // 为K均值聚类算法定义几个参数，这里使用2个簇集来分离数据点，最多10吃迭代。使用解析的数据与参数创建聚类器
        int numClusters = 2;
        int numIterations = 20;
        KMeansModel clusters = KMeans.train(parsedData.rdd(), numClusters, numIterations);

        // 在聚类器集合中，计算误差平方和
        double WSSSE = clusters.computeCost(parsedData.rdd());
        System.out.println("误差平方和 = " + WSSSE);

        sc.close();  //关闭
        return WSSSE;
    }

    public static void main(String[] args) {
        KMeansClusteringMlib mlib = new KMeansClusteringMlib();
        Double value = mlib.kMeansMlib("input/km_data.txt");
        System.out.println("误差平方和：" + value);
    }
}