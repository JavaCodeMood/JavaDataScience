package com.lhf.javadatascience.chapter6;

import weka.core.*;
import weka.core.converters.*;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.filters.*;
import weka.filters.unsupervised.attribute.*;

import java.io.*;
import java.util.Random;

/**
 * @ClassName WekaClassification
 * @Desc 使用Weka对文本文档进行分类
 * https://waikato.github.io/weka-wiki/text_categorization_with_weka/
 * @Author diandian
 * @Date 2022/1/20 20:46
 **/
public class WekaClassification {
    public static void main(String[] args) throws Exception {
        //创建一个加载器，并设置父目录的路径，以加载所有类目录的父目录
        TextDirectoryLoader loader = new TextDirectoryLoader();
        //这里所用到的数据见input/text_example目录
        loader.setDirectory(new File("input/text_example"));
        //使用加载的html文件创建实例
        Instances data = loader.getDataSet();

        //使用数据字符串创建词向量，首先创建一个过滤器，用来把字符串转换为词向量，然后把上一步得到的原数据设置给它
        StringToWordVector filter = new StringToWordVector();
        filter.setInputFormat(data);
        //为了完成字符串到词向量的转换，使用这个过滤器与数据创建实例
        Instances dataFiltered = Filter.useFilter(data, filter);

        //使用词向量创建朴素贝叶斯分类器
        NaiveBayes nb = new NaiveBayes();
        nb.buildClassifier(dataFiltered);
        System.out.println("\n\nClassifier model:\n\n" + nb);  //打印模型

        //使用K折交叉验证来评估模型
        Evaluation eval = null;
        eval = new Evaluation(dataFiltered);
        eval.crossValidateModel(nb, dataFiltered, 5, new Random(1));
        System.out.println(eval.toSummaryString());
    }
}
/*
Correctly Classified Instances（正确分类的实例）          45               90      %
Incorrectly Classified Instances （错误分类的实例）        5               10      %
Kappa statistic（Kappa统计量）                          　    0.792
Mean absolute error(均值绝对误差)                       　   0.1
Root mean squared error（均方根误差）                  　0.3162
Relative absolute error（相对绝对误差）                 　  20.7954 %
Root relative squared error（相对均方根误差）             62.4666 %
Coverage of cases (0.95 level)          90      %
Mean rel. region size (0.95 level)      50      %
Total Number of Instances（实验的实例总数）               50

=== Detailed Accuracy By Class ===

                  　　TP Rate（真阳性率） FP Rate（假阳性率） Precision（查准率）   Recall（查全率）  F-Measure  MCC（Matthews相关系数）    ROC Area  PRC Area  Class（类别）
                 　　　　0.773    　　　　　　　0        　　　　　　　　1          　　　　 0.773     　　0.872     　　　　0.81   　　　　　　　　　0.886     　0.873     true
                 　　　　　1        　　　　　  0.227    　　　　　　　0.848      　　　　　　1        　　0.918     　　　　0.81   　　　　　　　　　0.886 　    0.848     false
Weighted Avg.    　  0.9      　　　　　　0.127    　　　　　　　0.915      　　　　　 0.9    　　　0.898     　　　　0.81  　 　　　　　　　　0.886     　0.859
 */