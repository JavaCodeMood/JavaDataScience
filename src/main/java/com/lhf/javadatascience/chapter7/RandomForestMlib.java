package com.lhf.javadatascience.chapter7;

import scala.Tuple2;

import java.io.Serializable;
import java.util.HashMap;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.RandomForest;
import org.apache.spark.mllib.tree.model.RandomForestModel;
import org.apache.spark.mllib.util.MLUtils;

/**
 * @ClassName RandomForestMlib
 * @Desc 使用MLib的随机森林模型对数据点进行分类
 * 数据集：https://github.com/apache/spark/blob/master/data/mllib/sample_binary_classification_data.txt 下载重命名为rf-data.txt
 * @Author diandian
 * @Date 2022/1/25 21:01
 **/
public class RandomForestMlib implements Serializable {

    /**
     * 使用MLib的随机森林模型对数据点进行分类
     * @param inputFile
     * @return
     */
    public Double randomForest(String inputFile){
        //创建一个Spark配置，并使用该配置，创建一个Spark context。local[4]允许应用程序运行4个线程
        SparkConf configuration = new SparkConf().setMaster("local[4]").setAppName("Any");
        JavaSparkContext sc = new JavaSparkContext(configuration);

        //把输入文件加载为LibSVM文件，读取数据，并将其放入一个RDD之中
        JavaRDD<LabeledPoint> data = MLUtils.loadLibSVMFile(sc.sc(), inputFile).toJavaRDD();
        // 使用70%的数据来训练模型，把剩余的30%用作模型的测试数据，并且数据的选择是随机的
        JavaRDD<LabeledPoint>[] dataSplits = data.randomSplit(new double[]{0.7, 0.3});
        JavaRDD<LabeledPoint> trainingData = dataSplits[0];
        JavaRDD<LabeledPoint> testData = dataSplits[1];

        // 配置参数，建立随机森林，以便根据训练数据生成模型。
        Integer numClasses = 2;  //定义数据点所拥有的分类数
        //为特征数据创建一个Map
        HashMap<Integer, Integer> categoricalFeaturesInfo = new HashMap<Integer, Integer>();
        Integer numTrees = 3; // 定义森林中树的棵数
        String featureSubsetStrategy = "auto"; //设置为auto,让分类器自动选择特征子集过程
        String impurity = "gini";
        Integer maxDepth = 5;
        Integer maxBins = 32;
        Integer seed = 12345;

        //使用以上参数创建一个RandomForest分类器
        final RandomForestModel rfModel = RandomForest.trainClassifier(trainingData, numClasses,
                categoricalFeaturesInfo, numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins,
                seed);

        // 使用创建好的模型，依据给定的特征向量预测数据点的类标签。对每个数据点，Tuple2<Double, Double>中包含着预测值与真实类值
        JavaPairRDD<Double, Double> label =
                testData.mapToPair(new PairFunction<LabeledPoint, Double, Double>() {
                    public Tuple2<Double, Double> call(LabeledPoint p) {
                        return new Tuple2<Double, Double>(rfModel.predict(p.features()), p.label());
                    }
                });

        //计算预测误差，我们简单的统计预测值与真实值不匹配的次数，然后除以测试实例总数，得到平均值
        Double testError =
                1.0 * label.filter(new Function<Tuple2<Double, Double>, Boolean>() {
                    public Boolean call(Tuple2<Double, Double> pl) {
                        return !pl._1().equals(pl._2());
                    }
                }).count() / testData.count();

        System.out.println("Test Error: " + testError);
        //打印随机森林模型
        System.out.println("Learned classification forest model:\n" + rfModel.toDebugString());

        return testError;
    }

    public static void main(String[] args) {
        RandomForestMlib mlib = new RandomForestMlib();
        Double value = mlib.randomForest("input/rf-data.txt");
        System.out.println("误差：" + value);
    }
}