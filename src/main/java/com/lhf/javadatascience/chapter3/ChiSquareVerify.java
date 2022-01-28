package com.lhf.javadatascience.chapter3;

import org.apache.commons.math3.stat.inference.TestUtils;

/**
 * @ClassName ChiSquareVerify
 * @Desc 执行卡方校验
 * @Author diandian
 * @Date 2022/1/10 20:01
 **/
public class ChiSquareVerify {

    /**
     * 卡方校验
     * 在对两组数据分布进行卡方校验时，其中一种分布叫观测分布（observed distribution）, 另一种分布叫做预期分布（expected distribution）
     * 预期分布是一个double数组，观测分布是long型数组
     * @param expected
     * @param observed
     */
    public void getChiSquareVerify(double[] expected, long[] observed){
        double tCount = TestUtils.chiSquare(expected, observed); //获取卡方分布的t统计量
        System.out.println("tCount = " + tCount);//t statistics

        //获取检验的P值
        double pValue = TestUtils.chiSquareTest(expected, observed);  //p value
        System.out.println("pValue = " + pValue);

        //给定一个置信区间，观察预期分布与观测数据分布之间的差异是否显著
        boolean bool = TestUtils.chiSquareTest(expected, observed, 0.05);
        System.out.println("bool = " + bool);
    }

    public static void main(String[] args) {
        long[] observed = {12, 67, 43, 80, 52, 19};
        double[] expected = {90, 87, 50, 61, 77, 18};

        ChiSquareVerify verify = new ChiSquareVerify();
        verify.getChiSquareVerify(expected, observed);
    }
}