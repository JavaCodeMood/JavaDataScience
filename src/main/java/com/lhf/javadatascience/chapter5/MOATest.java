package com.lhf.javadatascience.chapter5;


import com.yahoo.labs.samoa.instances.Instance;
import moa.classifiers.Classifier;
import moa.classifiers.trees.HoeffdingTree;
import moa.core.TimingUtils;
import moa.streams.generators.RandomRBFGenerator;

/**
 * @ClassName MOATest
 * @Desc 使用MOA对数据点分类
 * https://sourceforge.net/projects/moa-datastream/
 * MOA包括分类、回归、聚类、异常值检测和推荐系统。与 WEKA 项目相关，也是用 Java 编写的，同时扩展到自适应大规模机器学习。
 * MOA实现了多个分类器：朴素贝叶斯，Hoeffding Tree, Hoeffding Option Tree, Hoeffding Adaptive Tree, Bagging, Boosting, Bagging using ADWIN,
 * Leveraging Bagging , SGD, Perceptron, SPegasos
 *
 * @Author diandian
 * @Date 2022/1/14 10:30
 **/
public class MOATest {

    /**
     * MOA
     * @param numInstances   表示要处理的实例数量
     * @param isTesting   是否要对分类器进行测试
     */
    public void moaRun(int numInstances, boolean isTesting){
        //创建一个HoeffdingTree分类器
        Classifier learner = new HoeffdingTree();
        //创建一个随机径向基函数流
        RandomRBFGenerator stream = new RandomRBFGenerator();
        stream.prepareForUse();  //准备要使用的流

        //设置引用到数据流的头部，使用getHeader()方法得到数据流的头部
        learner.setModelContext(stream.getHeader());
        learner.prepareForUse();  //准备要使用的分类器

        int numberSamplesCorrect = 0;  //跟踪样本数
        int numberSamples = 0;  //正确分类的样本数
        //获取跟踪分类耗费的时间
        long evaluateStartTime = TimingUtils.getNanoCPUTimeOfCurrentThread();
        //执行循环，直到数据流没有更多实例，并且分类的样本数达到总实例数
        while (stream.hasMoreInstances() && numberSamples < numInstances){
            //得到数据流每个实例的数据
            Instance trainInst = stream.nextInstance().getData();
            //查看分类器对实例所进行的分类是否正确
            if(isTesting){
                if(learner.correctlyClassifies(trainInst)){
                    numberSamplesCorrect++;
                }
            }
            numberSamples++;
            learner.trainOnInstance(trainInst);
        }
        //计算准确度
        double accuracy = 100.0 * (double) numberSamplesCorrect / (double) numberSamples;
        //计算分类耗费的时间
        double time = TimingUtils.nanoTimeToSeconds(TimingUtils.getNanoCPUTimeOfCurrentThread() - evaluateStartTime);

        System.out.println(numberSamples + " instances processed with " + accuracy + " % accuracy in " + time + " seconds");
    }

    public static void main(String[] args) {
        MOATest test = new MOATest();
        test.moaRun(100000, true);
    }
}