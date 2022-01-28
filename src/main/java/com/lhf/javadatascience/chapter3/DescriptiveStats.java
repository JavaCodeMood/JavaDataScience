package com.lhf.javadatascience.chapter3;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * @ClassName DescriptiveStats
 * @Desc 生成描述性统计
 * @Author diandian
 * @Date 2022/1/7 17:51
 **/
public class DescriptiveStats {

    public void getDescStats(double[] values){
        //创建DescriptiveStatistics对象
        DescriptiveStatistics stats = new DescriptiveStatistics();
        //遍历数组，并添加到DescriptiveStatistics对象中
        for(int i = 0; i < values.length; i++){
            stats.addValue(values[i]);
        }
        double mean = stats.getMean();  //计算平均值
        double std = stats.getStandardDeviation();  //计算标准差
        double min = stats.getMin();  //计算最小值
        double max = stats.getMax();  //计算最大值
        double variance = stats.getVariance();  //计算方差
        double median = stats.getPercentile(50);  //计算中位数
        double sum = stats.getSum();
        double sumsq = stats.getSumsq();
        System.out.println("mean = " + mean + " , std = " + std);
        System.out.println("min = " + min + " , max = " + max);
        System.out.println("variance = " + variance + " , median = " + median);
        System.out.println("sum = " + sum  + " , sumsq =  " + sumsq);
    }

    public static void main(String[] args) {
        double[] values = {32, 39, 89, 0, -23, 87, 22, 50, 100, -2, 34, 71, 80, 110, 30};
        DescriptiveStats descriptiveStats = new DescriptiveStats();
        descriptiveStats.getDescStats(values);
    }
}