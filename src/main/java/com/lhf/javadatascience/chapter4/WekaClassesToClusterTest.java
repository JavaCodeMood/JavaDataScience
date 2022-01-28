package com.lhf.javadatascience.chapter4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.EM;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

/**
 * @ClassName WekaClassesToClusterTest
 * @Desc 使用Weka依据类别对数据进行聚类处理
 * Weka在进行聚类处理时先忽略分类属性，然后在测试阶段，根据每个簇分类属性的多数值把分类指派给各个簇
 * @Author diandian
 * @Date 2022/1/13 10:07
 **/
public class WekaClassesToClusterTest {

    private final static Logger log = LogManager.getLogger(WekaClassesToClusterTest.class);

    Instances weather = null;  //保存数据集实例
    EM clusterer;   //EM(期望最小化)聚类器

    /**
     * 加载ARFF数据集文件
     * @param arffInput
     */
    public void loadArff(String arffInput){
        ConverterUtils.DataSource source = null;
        try {
            source = new ConverterUtils.DataSource(arffInput);
            weather = source.getDataSet();  //读取数据集
            weather.setClassIndex(weather.numAttributes() - 1);  //将ARFF文件的最后一个属性指定为类别属性
        } catch (Exception e) {
            log.error("加载ARFF文件异常：" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 根据分类生成簇集
     *
     */
    public void generateClassToCluster(){
        Remove remove = new Remove();  //创建Remove过滤器，用来从数据集中移除分类属性，在进行聚类处理时，weka会忽略这个属性
        remove.setAttributeIndices("" + (weather.classIndex() + 1));
        try{
            remove.setInputFormat(weather);   //把数据集加入过滤器
            Instances dataClusterer = Filter.useFilter(weather, remove);  //获取不带类别变量的数据集，并且依据数据创建一个EM聚类器
            clusterer = new EM();
            clusterer.buildClusterer(dataClusterer);  //创建聚类器

            //使用原数据集的分类信息对聚类进行评估
            ClusterEvaluation eval = new ClusterEvaluation();
            eval.setClusterer(clusterer);
            eval.evaluateClusterer(weather);

            System.out.println(eval.clusterResultsToString());  //打印聚类结果
        }catch (Exception e){
            log.error("异常：" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WekaClassesToClusterTest test = new WekaClassesToClusterTest();
        test.loadArff("D:\\Program Files\\Weka-3-9-5\\data\\weather.nominal.arff");
        test.generateClassToCluster();
    }
}