package com.lhf.javadatascience.chapter3;

import org.apache.commons.math3.stat.inference.TestUtils;

/**
 * @ClassName TVerifyTest
 * @Desc 配对t校验
 * @Author diandian
 * @Date 2022/1/8 18:44
 **/
public class TVerifyTest {

    public void getTtest(double[] sample1, double[] sample2){
        double tCount = TestUtils.pairedT(sample1, sample2); //得到两种分布的t统计量
        System.out.println("tCount = " + tCount);//t statistics

        double tVerifyPValue = TestUtils.pairedTTest(sample1, sample2);  //得到t校验的P值
        System.out.println("tVerifyPValue = " + tVerifyPValue);//p value

        //对于任意给定的置信区间或alpha值，两种分布的差异显著性
        //这里设置为0.05，表示我们想了解当把alpha水平设为0.05或者95%的置信区间时差异是否显著
        boolean alphaValue = TestUtils.pairedTTest(sample1, sample2, 0.05);
        System.out.println("alphaValue = " + alphaValue);
    }

    public static void main(String[] args){
        double[] sample1 = {41, 12, 25, 42, 57, 59};
        double[] sample2 = {99, 45, 19, 79, 7, 11};
        TVerifyTest test = new TVerifyTest();
        test.getTtest(sample1, sample2);
    }
}