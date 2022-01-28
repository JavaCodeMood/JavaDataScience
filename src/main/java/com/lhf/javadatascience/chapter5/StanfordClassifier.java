package com.lhf.javadatascience.chapter5;

import edu.stanford.nlp.classify.Classifier;
import edu.stanford.nlp.classify.ColumnDataClassifier;
import edu.stanford.nlp.ling.Datum;
import edu.stanford.nlp.objectbank.ObjectBank;


/**
 * @ClassName StanfordClassifier
 * @Desc 使用斯坦福分类器对数据点分类
 *
 * @Author diandian
 * @Date 2022/1/13 18:57
 **/
public class StanfordClassifier {

    public static void main(String[] args) {
        //创建一个分类器，并且传入cheeseDisease数据集属性文件的路径
        ColumnDataClassifier columnDataClassifier = new ColumnDataClassifier("examples/cheese2007.prop");
        //使用训练数据，创建一个分类器，Classifier<String, String>第一类是类别，第二列是奶酪或疾病名称
        Classifier<String, String> classifier = columnDataClassifier.makeClassifier(columnDataClassifier.readTrainingExamples("examples/cheeseDisease.train"));
        //遍历测试数据的每一行、测试数据与训练数据类似，第一列是真实类别，第二列是名称
        //类别1后面第奶酪名称，类别2后面是疾病名称
        //在数据集上应用监督分类的目标是创建一个分类器，它可以把奶酪名称和疾病名称区分开来
        for (String line : ObjectBank.getLineIterator("examples/cheeseDisease.test", "utf-8")) {
            Datum<String,String> d = columnDataClassifier.makeDatumFromLine(line);
            System.out.println(line + "  ==>  " + classifier.classOf(d));
        }
    }
}