package com.lhf.javadatascience.chapter4;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.EM;
import weka.clusterers.SimpleKMeans;
import weka.core.DistanceFunction;
import weka.core.EuclideanDistance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.filters.Filter;

/**
 * @ClassName StrongCluster
 * @Desc TODO
 * @Author diandian
 * @Date 2022/1/12 21:23
 **/
public class StrongCluster {
    public static void Kmeans(String ArffFile)
    {
        Instances ins = null;
        Instances tempIns = null;

        SimpleKMeans KM = null;
        DistanceFunction disFun = null;
        try{
            /*
             * 1.读入样本
             */

            Instances data = new Instances(new BufferedReader(new FileReader(ArffFile)));
            data.setClassIndex(data.numAttributes() - 1);
            //l 产生无类别的数据，并用下面代码训练
            weka .filters.unsupervised.attribute.Remove filter = new weka.filters.unsupervised.attribute.Remove();
            filter.setAttributeIndices("" + (data.classIndex() + 1));
            filter.setInputFormat(data);
            Instances dataClusterer = Filter.useFilter(data,filter);
            //  l 学习一个clusterer，比如EM
            /// EM clusterer = new EM();
            // set further options for EM if necessary...
            KM = new SimpleKMeans();
            //设置聚类要得到的类别数量
            KM.setNumClusters(2);
            KM.setSeed(10);
            /*
             * 3.使用聚类算法对样本进行聚类
             */
            //KM.buildClusterer(ins);
            KM.buildClusterer(dataClusterer);
            //  l 用仍然包含类别属性的数据集评价这个clusterer
            ClusterEvaluation evals = new ClusterEvaluation();
            evals.setClusterer(KM);
            evals.evaluateClusterer(data);
            // l 输出评价结果
            System.out.println(evals.clusterResultsToString());

        }catch(Exception e){
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

        System.out.println("Strong Cluster IS STARTED!!");

        String InputArff= "D:\\Program Files\\Weka-3-9-5\\data\\cpu.arff";
        Kmeans(InputArff);
        //Kmeans();


    }

}