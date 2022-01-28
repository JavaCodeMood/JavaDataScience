package com.lhf.javadatascience.chapter4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 * @ClassName WekaLogisticRegressionTest
 * @Desc 使用Weka的Logistic类创建与使用带有零估计的多项逻辑回归模型
 * https://blog.csdn.net/jiangsujiangjiang/article/details/80568196
 * @Author diandian
 * @Date 2022/1/12 19:59
 **/
public class WekaLogisticRegressionTest {

    private final static Logger log = LogManager.getLogger(WekaLogisticRegressionTest.class);

    Instances iris = null;  //保存数据集
    Logistic logReg ;  //逻辑回归分类器

    /**
     * 加载ARFF数据集文件
     * @param arffInput
     */
    public void loadArff(String arffInput){
        ConverterUtils.DataSource source = null;
        try {
            source = new ConverterUtils.DataSource(arffInput);
            iris = source.getDataSet();  //读取数据集
            iris.setClassIndex(iris.numAttributes() - 1);  //将ARFF文件的最后一个属性指定为类别属性
        } catch (Exception e) {
            log.error("加载ARFF文件异常：" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 创建逻辑回归分类器
     */
    public void buildRegression(){
        logReg = new Logistic();

        try {
            logReg.buildClassifier(iris);
        } catch (Exception e) {
            log.error("创建逻辑回归模型异常：" + e.getMessage(), e);
            e.printStackTrace();
        }
        System.out.println(logReg);
    }

    public static void main(String[] args) throws Exception{
        WekaLogisticRegressionTest test = new WekaLogisticRegressionTest();
        test.loadArff("D:\\Program Files\\Weka-3-9-5\\data\\iris.arff");
        test.buildRegression();
    }
}