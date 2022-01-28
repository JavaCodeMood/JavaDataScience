package com.lhf.javadatascience.chapter4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;

import java.util.Random;

/**
 * @ClassName WekaFeatureSelectionTest
 * @Desc 使用
 * @Author diandian
 * @Date 2022/1/13 10:55
 **/
public class WekaFeatureSelectionTest {

    private final static Logger log = LogManager.getLogger(WekaFeatureSelectionTest.class);

    Instances iris = null;  //保存数据集
    NaiveBayes nb;  //分类器

    /**
     * 加载ARFF文件
     * @param arffInput
     */
    public void loadArff(String arffInput){
        ConverterUtils.DataSource source = null;
        try {
            source = new ConverterUtils.DataSource(arffInput);
            iris = source.getDataSet();  //读取数据
            iris.setClassIndex(iris.numAttributes() - 1);  //把数据集的最后一个属性设置为类别属性
        } catch (Exception e) {
            log.error("加载ARFF文件异常：" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 使用weka的低层属性选择方法
     */
    public void selectFeatures(){
        //创建 AttributeSelection 属性选择对象
        AttributeSelection attSelection = new AttributeSelection();
        //创建 CfsSubsetEval 评估器对象
        CfsSubsetEval eval = new CfsSubsetEval();
        //创建 BestFirst 搜索对象
        BestFirst search = new BestFirst();
        attSelection.setEvaluator(eval);  //为属性选择对象设值
        attSelection.setSearch(search);
        try {
            //使用属性选择对象，借助搜索与评估器从iris数据集选择属性
            attSelection.SelectAttributes(iris);
            int[] attIndex = attSelection.selectedAttributes();  //得到所选属性的索引
            System.out.println(Utils.arrayToString(attIndex));  //打印属性的编号
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建一个属性选择过滤器
     */
    public void selectFeaturesWithFilter(){
        weka.filters.supervised.attribute.AttributeSelection filter = new weka.filters.supervised.attribute.AttributeSelection();
        //创建搜索与评估器对象，并为过滤器设置评估器与搜索对象
        CfsSubsetEval eval = new CfsSubsetEval();
        BestFirst search = new BestFirst();
        filter.setEvaluator(eval);
        filter.setSearch(search);
        try {
            filter.setInputFormat(iris);  //把过滤器应用到iris数据集
            //使用Filter.useFilter()获取新数据,该方法接收两个参数，第一个参数是数据集，第二个方法是过滤器
            Instances newData = Filter.useFilter(iris, filter);
            System.out.println(newData);
        } catch (Exception e) {
        }
    }

    /**
     * 把数据集传送给分类器之前将属性选出来
     */
    public void selectFeaturesWithClassifiers(){
        //创建一个元分类器，用来在把数据传递给NaiveBayes分类器之前，减少数据的维数（即选择属性）
        AttributeSelectedClassifier classifier = new AttributeSelectedClassifier();
        //创建一个评估器
        CfsSubsetEval eval = new CfsSubsetEval();
        //创建一个搜索对象
        BestFirst search = new BestFirst();
        //创建一个NaiveBayes分类器
        nb = new NaiveBayes();
        //把评估器，搜素对象，NaiveBayes分类器设置给元分类器
        classifier.setClassifier(nb);
        classifier.setEvaluator(eval);
        classifier.setSearch(search);
        //使用元分类器技术选择的属性来评估NaiveBayes分类器的性能
        Evaluation evaluation;
        try {
            evaluation = new Evaluation(iris);
            evaluation.crossValidateModel(classifier, iris, 10, new Random(1));  //使用10折交叉验证法
            System.out.println(evaluation.toSummaryString());
        } catch (Exception e) {
        }
    }

    public static void main(String[] args){
        WekaFeatureSelectionTest test = new WekaFeatureSelectionTest();
        test.loadArff("D:\\Program Files\\Weka-3-9-5\\data\\iris.arff");
        test.selectFeatures();
        test.selectFeaturesWithFilter();
        test.selectFeaturesWithClassifiers();
    }
}