package com.lhf.javadatascience.chapter3;

import org.apache.commons.math3.stat.descriptive.AggregateSummaryStatistics;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

/**
 * @ClassName DiverseStats
 * @Desc 从多种分布生成概要统计
 * @Author diandian
 * @Date 2022/1/7 20:30
 **/
public class AggregateStats {

    public void getDiverseStats(double[] values1, double[] values2, double[] values3){
        //创建AggregateSummaryStatistics对象
        AggregateSummaryStatistics aggregate = new AggregateSummaryStatistics();
        //生成三个概要统计对象
        SummaryStatistics oneSet = aggregate.createContributingStatistics();
        SummaryStatistics twoSet = aggregate.createContributingStatistics();
        SummaryStatistics threeSet = aggregate.createContributingStatistics();
        //将三个数组的值添加到对应的概要统计对象中
        for(int i = 0; i < values1.length; i++){
            oneSet.addValue(values1[i]);
        }
        for(int j = 0; j < values2.length; j++){
            twoSet.addValue(values2[j]);
        }
        for(int k = 0; k < values3.length; k++){
            threeSet.addValue(values3[k]);
        }
        double valueSum = aggregate.getSum();
        double valueMin = aggregate.getMin();
        double valueMax = aggregate.getMax();
        double valueMean = aggregate.getMean();
        double valueVariance = aggregate.getVariance();  //计算方差
        double valueStd = aggregate.getStandardDeviation();  //计算标准偏差
        System.out.println("valueSum = " + valueSum + " , valueMean = " + valueMean);
        System.out.println("valueMin = " + valueMin + " , valueMax = " + valueMax);
        System.out.println("valueVariance = " + valueVariance + " , valueStd = " + valueStd);

    }

    public static void main(String[] args) {
        AggregateStats aggregateStats = new AggregateStats();
        double[] values1 = {3, 8, 9, 10, 12, 42, 56, 78, 90, 10, 31, 82, 30, 1, 0, 4};
        double[] values2 = {67 , 98 , 43 ,  1, -1 , 0, 23, 7, 0, 1, 8, 12, 35};
        double[] values3 = {-20, 78, 1, 0 , 3, 8, 21, 39, 47, 22, 11};
        aggregateStats.getDiverseStats(values1, values2, values3);


    }


}