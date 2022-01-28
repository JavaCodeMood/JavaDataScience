package com.lhf.javadatascience.chapter4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

import java.io.*;

/**
 * @ClassName WekaTrainTest
 * @Desc 使用Weka对新的测试数据进行分类
 * 有监督机器学习分类任务是基于带标签的训练实例来训练分类器，然后把分类器用在新的测试实例上
 * 注意：训练集中的属性数量，类型，名称以及取值范围必须与测试数据集中的那些完全对应
 * @Author diandian
 * @Date 2022/1/11 18:33
 **/
public class WekaTrainTest {

    private final static Logger log = LogManager.getLogger(WekaTrainTest.class);;

    NaiveBayes nb;  //分类器
    Instances train, test, labeled;


    /**
     * 加载模型
     * @param modelPath
     */
    public void loadModel(String modelPath){
        try {
            nb = (NaiveBayes) weka.core.SerializationHelper.read(modelPath);
        } catch (Exception e) {
            log.error("加载模型异常：" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 读取训练与测试数据集
     * 我们这里使用weka的Data目录下的iris.arff文件
     * 使用创建好的iris-test.arff文件作为测试文件
     * @param training
     * @param testing
     */
    public void loadDatasets(String training, String testing){
        BufferedReader reader = null;
        try {
            //读取训练集数据
            reader = new BufferedReader(new FileReader(training));  //使用BufferedReader来读取数据集的内容
            train = new Instances (reader);  //创建训练实例
            train.setClassIndex(train.numAttributes() -1);  //把arff文件的最后一个属性设置成这个数据集的类属性
        } catch (IOException e) {
            log.error("读取训练数据异常：" + e.getMessage(), e);
            e.printStackTrace();
        }


        try {
            //读取测试数据集
            reader = new BufferedReader(new FileReader(testing));
            test = new Instances (reader);
            test.setClassIndex(train.numAttributes() -1);
        } catch (IOException e) {
            log.error("读取测试数据异常：" + e.getMessage(), e);
            e.printStackTrace();
        }

        try {
            reader.close();  //关闭BufferedReader
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 为测试数据集打上标签
     */
    public void classify(){
        try {
            nb.buildClassifier(train);  //创建一个NaiveBayes分类器，并且把这个分类器应用到测试数据集中那些之前不曾见过且未打标签的实例上
        } catch (Exception e) {
        }

        labeled = new Instances(test);  //创建带标签的实例，它是测试实例的副本，使用分类器进行预测，所得到的标签会指派给这些实例，保存测试实例不变

        for (int i = 0; i < test.numInstances(); i++) {
            double clsLabel;  //为测试集的每个实例，创建一个类标签
            try {
                clsLabel = nb.classifyInstance(test.instance(i));
                //类标签将指派给类标签变量，并且这个变量的值会被指派为带标签的实例中这个特定实例的类标签
                //在带标签的实例中，测试实例的 ？ 值将被Naive Bayes预测的值所取代
                labeled.instance(i).setClassValue(clsLabel);
                double[] predictionOutput = nb.distributionForInstance(test.instance(i));
                double predictionProbability = predictionOutput[1];
                System.out.println(predictionProbability);  //打印出模型预测的概率值
            } catch (Exception e) {
            }
        }
    }

    /**
     * 把打过标签的测试数据集写入文件系统
     * @param outArff
     */
    public void writeArff(String outArff){
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(outArff));
            writer.write(labeled.toString());
            writer.close();
        } catch (IOException e) {
        }
    }

    public static void main(String[] args) throws Exception{
        WekaTrainTest test = new WekaTrainTest();
        test.loadModel("d:/model/nb.model");
        test.loadDatasets("D:\\Program Files\\Weka-3-9-5\\data\\iris.arff", "doc/iris-test.arff");
        test.classify();
        test.writeArff("d:/model/out.arff");  //out.arff这个文件就是iris-test.arff文件打上标签之后的文件
    }



}