package com.lhf.javadatascience.chapter4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 * @ClassName WekaClusterTest
 * @Desc Weka使用K均值算法对数据点进行聚类
 * @Author diandian
 * @Date 2022/1/12 20:34
 **/
public class WekaClusterTest {

    private final static Logger log = LogManager.getLogger(WekaClusterTest.class);

    Instances cpu = null;
    SimpleKMeans kmeans;

    /**
     * 加载ARFF数据集文件
     * @param arffInput
     */
    public void loadArff(String arffInput){
        ConverterUtils.DataSource source = null;
        try {
            source = new ConverterUtils.DataSource(arffInput);
            cpu = source.getDataSet();  //读取数据集
            //cpu.setClassIndex(cpu.numAttributes() - 1);  //将ARFF文件的最后一个属性指定为类别属性
        } catch (Exception e) {
            log.error("加载ARFF文件异常：" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 建立聚类器
     *
     */
    public void clusterData(){
        kmeans = new SimpleKMeans();  //聚类器实例化
        kmeans.setSeed(10);  //设置种子值为10，种子用来生成随机数，它是一个整型值
        try {
            kmeans.setPreserveInstancesOrder(true);  //true 设置聚类器保持数据实例的原始顺序
            kmeans.setNumClusters(10);  //把簇数设置为10
            kmeans.buildClusterer(cpu);  //创建聚类器
            int[] assignments = kmeans.getAssignments();
            int i = 0;
            //使用for循环，获取每个实例，以及简单的将K均值算法指派给它们的簇编号
            for(int clusterNum : assignments) {
                System.out.printf("Instance %d -> Cluster %d\n", i, clusterNum);
                i++;
            }
        } catch (Exception e) {
            log.error("建立聚类器异常：" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        WekaClusterTest test = new WekaClusterTest();
        test.loadArff("D:\\Program Files\\Weka-3-9-5\\data\\cpu.arff");
        test.clusterData();
    }
}