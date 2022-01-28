package com.lhf.javadatascience.chapter5;

import mulan.classifier.lazy.MLkNN;
import mulan.classifier.meta.RAkEL;
import mulan.classifier.transformation.LabelPowerset;
import mulan.data.MultiLabelInstances;
import mulan.evaluation.Evaluator;
import mulan.evaluation.MultipleEvaluation;
import weka.classifiers.trees.J48;
/**
 * @ClassName MulanLabelTest
 * @Desc 使用Mulan对多标签数据点进行分类
 * 多标签数据实例是指那些拥有多个类或标签的数据实例
 * Mulan需要使用两个文件来指定一个多标签数据集。
 * 第一个文件是ARFF文件，标签应该是名义属性，带有两个值0与1，前者表示标签不存在，后者表示标签存在
 * 第二个文件是XML文件，它用来指定标签及它们之间的层次关系
 * @Author diandian
 * @Date 2022/1/14 11:05
 **/
public class MulanLabelTest {

    public void mulanLabel(String arffPath, String xmlPath){
        //创建数据集实例
        MultiLabelInstances dataset = null;
        try{
            //读取数据文件数据到数据集实例
            dataset = new MultiLabelInstances(arffPath, xmlPath);

            //创建RAkEL分类器，是一个元分类器，它可以拥有一个多标签学习器，并且通常与LabelPowerset算法配用
            //LabelPowerset是一个基于变换的算法，它可以接收一个单标签分类器作为参数
            RAkEL learner1 = new RAkEL(new LabelPowerset(new J48()));
            //创建MLkNN分类器，是一个自适应分类器，它基于K最近邻算法
            MLkNN learner2 = new MLkNN();
            //创建一个评估器，用来评估分类性能
            Evaluator eval = new Evaluator();
            //用于保存多个评估结果
            MultipleEvaluation results;

            int numFolds = 10;  //10折交叉验证：一份数据分为10份，9份数据做训练，1份数据做测试，

            //对第一个学习器进行评估，显示评估结果
            results = eval.crossValidate(learner1, dataset, numFolds);
            System.out.println("learner1 results = " + results);
            //对第二个学习器进行评估

            results = eval.crossValidate(learner2, dataset, numFolds);
            System.out.println("learner2 results = " + results);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        MulanLabelTest test = new MulanLabelTest();
        test.mulanLabel("input/Mulan/emotions.arff", "input/Mulan/emotions.xml");
    }

}