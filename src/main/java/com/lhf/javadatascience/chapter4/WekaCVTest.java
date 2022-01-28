package com.lhf.javadatascience.chapter4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.Random;

/**
 * @ClassName WekaCVTest
 * @Desc 对机器学习模型进行交叉验证
 * @Author diandian
 * @Date 2022/1/11 17:48
 **/
public class WekaCVTest {

    private final static Logger log = LogManager.getLogger(WekaCVTest.class);;

    Instances iris = null;  //用于保存iris数据集的所有实例 iris数据集存在于Weka安装目录的data文件中
    NaiveBayes nb ;   //NaiveBayes分类器

    /**
     * 加载ARFF文件
     * @param arffPath
     */
    public void loadARFF(String arffPath){

        DataSource source = null;
        try{
            source = new DataSource(arffPath);
            iris = source.getDataSet();  //读取内容，并设置类属性的位置
            if(iris.classIndex() == -1){
                //把最后一个属性设置为类属性
                iris.setClassIndex(iris.numAttributes() - 1);
            }
        }catch (Exception e){
            log.error("加载ARFF文件异常：" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 基于iris数据集的朴素贝叶斯分类器
     */
    public void generateModel(){
        nb = new NaiveBayes();
        try {
            nb.buildClassifier(iris); //创建一个基于iris数据集的朴素贝叶斯分类器
        }catch (Exception e){
            log.error("异常：" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 使用weka提供的SerializationHelper工具类将生成的模型保存起来
     * SerializationHelper类有一个名称为write()的方法，接收两个参数
     * 第一个参数是模型的保存路径，第二个参数是保存的模型
     * @param modelPath
     */
    public void saveModel(String modelPath){
        try {
            weka.core.SerializationHelper.write(modelPath, nb);
        }catch (Exception e){
            log.error("保存模型异常：" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 使用iris数据集对模型进行交叉验证，以评估模型的性能
     * 这里使用10折交叉验证
     */
    public void crossValidate(){
        Evaluation eval = null;
        try{
            eval = new Evaluation(iris);
            //nb是所使用的模型，iris是数据集, 10表示使用的是10折交叉验证, 最后一个参数把随机化引入处理过程中
            eval.crossValidateModel(nb, iris, 10, new Random(1));
            System.out.println(eval.toSummaryString());
        }catch (Exception e){
            log.error("异常：" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 加载模型
     * @param modelPath
     */
    public void loadModel(String modelPath){
        try {
            nb = (NaiveBayes) weka.core.SerializationHelper.read(modelPath);  //读取模型
            System.out.println(nb.getCapabilities().listCapabilities());
            System.out.println(nb.displayModelInOldFormatTipText());
            System.out.println(nb.getRevision());
            System.out.println(nb.globalInfo());
            System.out.println(nb.useKernelEstimatorTipText());
        }catch (Exception e){
            log.error("加载模型异常：" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WekaCVTest cvTest = new WekaCVTest();
        //cvTest.loadARFF("D:\\Program Files\\Weka-3-9-5\\data\\iris.arff");
        //cvTest.generateModel();
        //cvTest.saveModel("d:/model/nb.model");
        //cvTest.crossValidate();
        cvTest.loadModel("d:/model/nb.model");
    }
}