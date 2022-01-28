package com.lhf.javadatascience.chapter3;

import org.apache.commons.math3.stat.correlation.Covariance;

/**
 * @ClassName CovarianceTest
 * @Desc 计算两组数据的协方差
 * @Author diandian
 * @Date 2022/1/8 17:34
 **/
public class CovarianceTest {

    /**
     * 计算两组数据的无偏协方差
     * 无偏协方差的计算公式为：cov(X, Y) = sum[(xi - E(X)(yi-E(Y)] / (n -1)
     * 其中E(X)是指X值的平均值， E(Y)是指Y值的平均值
     * 无偏估计使用n来代替n-1
     * 为了指定协方差是否为有偏估计，我们需要额外设置一个可选参数，即biasCorrected，其默认值为true
     * @param x
     * @param y
     * @return
     */
    public double calculateCov(double[] x, double[] y){
        Covariance covariance = new Covariance();
        //如果去掉false，我们得到无偏协方差
        //covariance(final double[] xArray, final double[] yArray, boolean biasCorrected)
        //biasCorrected为true是有偏协方差  为false是无偏协方差
        double covarianceValue = covariance.covariance(x, y, false);
        return covarianceValue;
    }

    public static void main(String[] args) {
        double[] x = {67, 12, 89, 23, 15, 72, 72, 9};
        double[] y = {41, 93, 12, 38, 73, 10, 8, 26};
        CovarianceTest test = new CovarianceTest();
        double value = test.calculateCov(x, y);
        System.out.println("value = " + value);
    }
}