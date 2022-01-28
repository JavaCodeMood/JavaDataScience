package com.lhf.javadatascience.chapter3;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

/**
 * @ClassName PearsonsCorrelationTest
 * @Desc 为两组数据点计算皮尔逊相关数据
 * @Author diandian
 * @Date 2022/1/8 18:29
 **/
public class PearsonsCorrelationTest {

    /**
     * 计算两组数据点皮尔逊相关数据
     * 计算公式：cor(X, Y) = sum[(xi-E(X))(yi-E(Y))] / [(n-1)s(X)s(Y)]
     * 其中E(X)和E(Y)分别是指X和Y的平均值， s(X)和s(Y)是指标准差
     * @param x
     * @param y
     * @return
     */
    public double calculatePearson(double[] x, double[] y){
        //创建PearsonsCorrelation对象
        PearsonsCorrelation correlation = new PearsonsCorrelation();
        //计算两组数据点的相关系数
        double pearsonsValue = correlation.correlation(x,y);
        return pearsonsValue;
    }

    public static void main(String[] args) {
        double[] x = {67, 12, 89, 23, 15, 72, 72, 9};
        double[] y = {41, 93, 12, 38, 73, 10, 8, 26};
        PearsonsCorrelationTest test = new PearsonsCorrelationTest();
        double value = test.calculatePearson(x, y);
        System.out.println("value = " + value);
    }


}