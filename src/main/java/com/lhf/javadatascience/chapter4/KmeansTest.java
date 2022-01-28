package com.lhf.javadatascience.chapter4;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @ClassName KmeansTest
 * @Desc TODO
 * @Author diandian
 * @Date 2022/1/12 21:33
 **/
public class KmeansTest {

   public void doWithKmens(String arffPath){
       try{
           BufferedReader breader = null;
           breader = new BufferedReader(new FileReader(arffPath));
           Instances Train = new Instances(breader);
           //Train.setClassIndex(Train.numAttributes() - 1); // comment out this line
           SimpleKMeans kMeans = new SimpleKMeans();
           kMeans.setSeed(10);
           kMeans.setPreserveInstancesOrder(true);
           kMeans.setNumClusters(3);
           kMeans.buildClusterer(Train);
           int[] assignments = kMeans.getAssignments();
           int i = 0;
           for (int clusterNum : assignments) {
               System.out.printf("Instance %d -> Cluster %d\n", i, clusterNum);
               i++;
           }
           breader.close();
       }catch (Exception e){
           e.printStackTrace();
       }
   }
    public static void main(String[] args) throws Exception {
        KmeansTest test = new KmeansTest();
        test.doWithKmens("D:\\Program Files\\Weka-3-9-5\\data\\cpu.arff");
    }

}