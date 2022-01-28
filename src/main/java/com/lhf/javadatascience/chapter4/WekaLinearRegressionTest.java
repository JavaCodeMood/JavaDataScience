package com.lhf.javadatascience.chapter4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * @ClassName WekaLinearRegressionTest
 * @Desc 使用Weka的线性回归分类器来创建线性回归模型
 * 大多数线性回归模型遵循一个通用模式，即会有许多独立变量共同产生一个非独立变量的结果
 * @Author diandian
 * @Date 2022/1/12 18:32
 **/
public class WekaLinearRegressionTest {

    private final static Logger log = LogManager.getLogger(WekaLinearRegressionTest.class);

    Instances cpu = null;  //用于保存数据集
    LinearRegression lReg ;  //创建线性回归分类器

    /**
     * 加载ARFF数据集文件
     * @param arffInput
     */
    public void loadArff(String arffInput){
        DataSource source = null;
        try {
            source = new DataSource(arffInput);
            cpu = source.getDataSet();  //读取数据集
            cpu.setClassIndex(cpu.numAttributes() - 1);  //将ARFF文件的最后一个属性指定为类别属性
        } catch (Exception e) {
            log.error("加载ARFF文件异常：" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 创建线性回归模型
     */
    public void buildRegression(){
        lReg = new LinearRegression();
        try {
            lReg.buildClassifier(cpu);  //创建线性回归模型
        } catch (Exception e) {
            log.error("创建线性回归模型异常：" + e.getMessage(), e);
            e.printStackTrace();
        }
        System.out.println(lReg);  //打印输出模型
    }

    public static void main(String[] args){
        WekaLinearRegressionTest test = new WekaLinearRegressionTest();
        test.loadArff("D:\\Program Files\\Weka-3-9-5\\data\\cpu.arff");
        test.buildRegression();
    }

}