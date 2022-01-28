package com.lhf.javadatascience.chapter5;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.classification.evaluation.CrossValidation;
import net.sf.javaml.classification.evaluation.EvaluateDataset;
import net.sf.javaml.classification.evaluation.PerformanceMeasure;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.clustering.evaluation.SumOfSquaredErrors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.distance.PearsonCorrelationCoefficient;
import net.sf.javaml.featureselection.ranking.RecursiveFeatureEliminationSVM;
import net.sf.javaml.featureselection.scoring.GainRatio;
import net.sf.javaml.featureselection.subset.GreedyForwardSelection;
import net.sf.javaml.tools.data.FileHandler;

import java.io.File;
import java.util.Map;

/**
 * @ClassName JavaMachineLearning
 * @Desc 使用Java机器学习库（Java-ML）向数据应用机器学习
 * @Author diandian
 * @Date 2022/1/13 14:41
 **/
public class JavaMachineLearning {

    /**
     * 机器学习
     * @param dataPath  数据文件路径
     * @param outPath
     * @param outPath1
     */
    public void javaMachineLearning(String dataPath, String outPath, String outPath1){
       try{
           //调用Java-ML库中的FileHandler.loadDataset()方法读取iris数据集
           //该方法的参数分别为数据集的路径，分类属性的位置，以及值分隔符。属性的起始索引为0， iris数据集的属性是它的第五个属性
           Dataset dataset = FileHandler.loadDataset(new File(dataPath), 4, ",");
           System.out.println("dataset = " + dataset);  //打印数据集
           System.out.println("===============================================");

           //调用FileHandler.exportDataset()的方法导出数据为txt文件
           //第一个参数是数据，第二个参数是输出文件
           FileHandler.exportDataset(dataset, new File(outPath));  //导出数据
           //读取数据文件内容
           dataset = FileHandler.loadDataset(new File(outPath1), 0, "\t");
           System.out.println("dataset = " + dataset);

           //聚类
           Clusterer km = new KMeans();  //创建一个KMeans聚类器，得到的结果是数据点的多个簇集
           Dataset[] clusters = km.cluster(dataset);
           //循环遍历数据集数组中的数据点
           for(Dataset cluster : clusters){
               System.out.println("Cluster: " + cluster);
           }
           //误差平方和是用来评估聚类器性能的指标之一，这里使用ClusterEvaluation类来测量聚类误差
           ClusterEvaluation sse = new SumOfSquaredErrors();
           //把簇集传递给误差平方和对象的计分方法，计算聚类的误差平方和
           double score = sse.score(clusters);
           System.out.println("score : " + score);  //打印误差分数

           //分类
           //创建一个KNN分类器（K最近邻分类器）,这个分类器使用最靠近的5个邻居的多数投票来预测新数据点的标签
           Classifier knn = new KNearestNeighbors(5);
           //buildClassifier()方法用来训练一个分类器，其参数是一个数据集
           knn.buildClassifier(dataset);

           //K折交叉验证
           //使用分类器创建一个CrossValidation实例
           CrossValidation cv = new CrossValidation(knn);
           //crossValidation()该方法的参数是数据集，返回一个Map,key是对象，value是评估指标
           Map<Object, PerformanceMeasure> cvEvaluation = cv.crossValidation(dataset);
           //打印 这个分类的真正，假正，真负与假负显示出来
           System.out.println("cvEvaluation = "+ cvEvaluation);

           //Held-out测试
           //加载测试数据集，这里的测试数据集用的是训练数据集，真实情况下，要用测试数据集
           Dataset testData = FileHandler.loadDataset(new File(dataPath), 4, ",");
           //获取分类器在测试数据上的性能
           Map<Object, PerformanceMeasure> testEvaluation = EvaluateDataset.testDataset(knn, testData);
           //为每个分类打印结果
           for(Object classVariable : testEvaluation.keySet()){
               System.out.println(classVariable + " = " + testEvaluation.get(classVariable).getAccuracy());
           }

           //特征打分
           //创建一个特征打分算法实例
           GainRatio gainRatio = new GainRatio();
           gainRatio.build(dataset);
           System.out.println("特征分数：");
           for(int i = 0; i < gainRatio.noAttributes(); i++){
               System.out.println(gainRatio.score(i)); //打印每个特征的分数，这里的i是属性索引
           }

           //特征排序
           //创建一个特征排序算法实例，采用特征的SVM排序，它基于特征的递归消除实现。构造函数的参数是最差排序特征的百分率，这些特征是要删除的
           RecursiveFeatureEliminationSVM featureRank = new RecursiveFeatureEliminationSVM(0.2);
           featureRank.build(dataset);
           System.out.println("特征排序：");
           for(int i = 0; i < featureRank.noAttributes(); i++){
               System.out.println(featureRank.rank(i));  //打印每个特征的排序名
           }

           //特征子集选择
           //创建一个特征选择算法实例，通过greedy方法是要正向选择方法
           //在选择特征的过程中，我们需要一个距离测度，这里是要皮尔逊相关系数。构造函数的第一个参数代表要在子集中选择的属性数目
           GreedyForwardSelection forwardSelection = new GreedyForwardSelection(5, new PearsonCorrelationCoefficient());
           forwardSelection.build(dataset);
           System.out.println("特征子集" + forwardSelection.selectedAttributes());

       }catch (Exception e){
           e.printStackTrace();
       }

    }
    public static void main(String[] args) {
        JavaMachineLearning learning = new JavaMachineLearning();
        learning.javaMachineLearning("D:/out//iris.data", "D:/out/output.txt", "D:/out/output.txt");

    }


}