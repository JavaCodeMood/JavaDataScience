package com.lhf.javadatascience.chapter7;

import scala.Tuple2;

import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.regression.LinearRegressionModel;
import org.apache.spark.mllib.regression.LinearRegressionWithSGD;
import org.apache.spark.SparkConf;

import java.io.Serializable;

/**
 * @ClassName LinearRegressionMlib
 * @Desc 使用Mlib创建线性回归模型
 * 数据集：https://github.com/apache/spark/blob/master/data/mllib/ridge-data/lpsa.data 下载重命名为lr-data.txt
 * @Author diandian
 * @Date 2022/1/25 20:31
 **/
public class LinearRegressionMlib implements Serializable {

    /**
     * 创建线性回归模型
     * @param inputFile
     */
    public Double linearRegress(String inputFile){
        //创建一个Spark配置，并使用该配置，创建一个Spark context。local[4]允许应用程序运行4个线程
        SparkConf configuration = new SparkConf().setMaster("local[4]").setAppName("Linear Regression Example");
        JavaSparkContext sparkContext = new JavaSparkContext(configuration);

        // JavaRDD是一个分布式的对相集。创建一个RDD对象，以便读取数据文件
        JavaRDD<String> data = sparkContext.textFile(inputFile);
        //从前面的RDD读取数据值，输入的数据中包含两个部分或区段，这些值之间以逗号键作为分割，对这些数据值进行解析，并把它们读到另一个RDD中
        //在第二部分中，这些特征之间以空格键作为分割。输入数据每一行的第一部分是标记点。
        //使用这些特征，创建一个特征向量，并把它与标记点放在一起
        JavaRDD<LabeledPoint> parsedData = data.map(
                new Function<String, LabeledPoint>() {
                    public LabeledPoint call(String line) {
                        String[] parts = line.split(",");
                        String[] features = parts[1].split(" ");
                        double[] featureVector = new double[features.length];
                        for (int i = 0; i < features.length - 1; i++){
                            featureVector[i] = Double.parseDouble(features[i]);
                        }
                        return new LabeledPoint(Double.parseDouble(parts[0]), Vectors.dense(featureVector));
                    }
                }
        );
        parsedData.cache();

        // 使用100次迭代创建线性回归模型，即使用特征向量，标签点，迭代数信息创建线性回归模型
        int numIterations = 100;
        final LinearRegressionModel model =
                LinearRegressionWithSGD.train(JavaRDD.toRDD(parsedData), numIterations);

        // 使用创建好的模型进行预测，并把预测结果放入另一个名为valuesAndPreds的RDD变量中
        //给出一组特征，模型会产生一个预测值，返回预测结果与实际标签，此时得到的预测结果是针对训练集（lr-data.txt）中数据点所进行的预测
        //Tuple2中即包含着回归模型的预测值，也包含着实际值
        JavaRDD<Tuple2<Double, Double>> valuesAndPreds = parsedData.map(
                new Function<LabeledPoint, Tuple2<Double, Double>>() {
                    public Tuple2<Double, Double> call(LabeledPoint point) {
                        double prediction = model.predict(point.features());
                        return new Tuple2<Double, Double>(prediction, point.label());
                    }
                }
        );
        //计算线性回归模型在训练数据上的均方误差
        //对于每个数据点，误差是指模型预测与数据集中的实际值之差的平方。
        //最后对每个数据点的误差进行平均
        double MSE = new JavaDoubleRDD(valuesAndPreds.map(
                new Function<Tuple2<Double, Double>, Object>() {
                    public Object call(Tuple2<Double, Double> pair) {
                        return Math.pow(pair._1() - pair._2(), 2.0);
                    }
                }
        ).rdd()).mean();
        return MSE;
    }

    public static void main(String[] args) {
        LinearRegressionMlib mlib = new LinearRegressionMlib();
        Double value = mlib.linearRegress("input/lr-data.txt");
        System.out.println("均方误差：" + value);
    }
}