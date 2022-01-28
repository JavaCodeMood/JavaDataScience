package com.lhf.javadatascience.chapter3;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

/**
 * @ClassName SummaryStatistics
 * @Desc 生成概要统计
 * @Author diandian
 * @Date 2022/1/7 20:13
 **/
public class SummaryStats {

    public void getSummaryStats(double[] values){
        SummaryStatistics stats = new SummaryStatistics();
        for(int i = 0; i < values.length; i++){
            stats.addValue(values[i]);
        }
        double mean = stats.getMean();  //平均值
        double sum = stats.getSum();  //求和
        double variance = stats.getVariance();  //计算方差
        double min = stats.getMin();
        double max = stats.getMax();
        double n = stats.getN();
        double quadraticMean = stats.getQuadraticMean();  //计算均方值
        double standardDeviation = stats.getStandardDeviation();  //计算标准偏差
        double geometricMean = stats.getGeometricMean();  //计算几何平均值
        System.out.println("mean = " + mean + " , sum = " + sum);
        System.out.println("variance = " + variance + " , n = " + n);
        System.out.println("min = " + min + " , max = " + max);
        System.out.println("quadraticMean = " + quadraticMean + " , standardDeviation = " + standardDeviation);
        System.out.println("geometricMean = " + geometricMean);
    }

    public static void main(String[] args) {
        SummaryStats summaryStats = new SummaryStats();
        double[] values = {80, 90, 10, 23, 6, 7, 1, 0 , 3, 44, 56, 7, 8 ,100, 23, 91};
        summaryStats.getSummaryStats(values);
    }
}