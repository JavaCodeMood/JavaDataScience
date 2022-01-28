package com.lhf.javadatascience.chapter4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.Remove;

/**
 * @ClassName WekaFilteredClassifierTest
 * @Desc 使用过滤分类器对新测试数据分类
 * 使用Weka的Filter类进行数据过滤处理
 * 使用Weka的FilteredClassifier类对经过过滤器过滤的数据进行分类处理
 * @Author diandian
 * @Date 2022/1/12 17:55
 **/
public class WekaFilteredClassifierTest {

    private final static Logger log = LogManager.getLogger(WekaFilteredClassifierTest.class);

    Instances weather = null;
    RandomForest rf;

    /**
     * 加载ARFF文件数据集
     * @param arffInput
     */
    public void loadArff(String arffInput){
        DataSource source = null;
        try {
            source = new DataSource(arffInput);
            weather = source.getDataSet();  //读取数据集
            weather.setClassIndex(weather.numAttributes() - 1);  //把数据集的最后一个属性设置为类属性
        } catch (Exception e) {
            log.error("加载ARFF文件异常：" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 使用随机森林分类器来处理数据
     *
     */
    public void buildFilteredClassifier(){
        rf = new RandomForest();  //创建一个随机森林分类器
        Remove rm = new Remove();  //创建一个过滤器
        rm.setAttributeIndices("1");  //从ARFF文件中移除特定的属性
        FilteredClassifier fc = new FilteredClassifier();
        fc.setFilter(rm);  //添加过滤器
        fc.setClassifier(rf);  //添加分类器
        try{
            fc.buildClassifier(weather);  //根据天气数据创建一个随机森林分类器
            for (int i = 0; i < weather.numInstances(); i++){
                double pred = fc.classifyInstance(weather.instance(i));  //分类器为天气数据集的每个实例预测类值
                System.out.print("given value: " + weather.classAttribute().value((int) weather.instance(i).classValue()));  //实际值
                System.out.println("---predicted value: " + weather.classAttribute().value((int) pred));  //预测值
            }
        } catch (Exception e) {
        }
    }

    public static void main(String[] args){
        WekaFilteredClassifierTest test = new WekaFilteredClassifierTest();
        test.loadArff("D:\\Program Files\\Weka-3-9-5\\data\\weather.nominal.arff");
        test.buildFilteredClassifier();
    }

}