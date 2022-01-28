package com.lhf.javadatascience.chapter3;

import org.apache.commons.math3.stat.inference.TestUtils;

/**
 * @ClassName KSVerifyTest
 * @Desc 执行K-S检验
 * @Author diandian
 * @Date 2022/1/10 20:32
 **/
public class KSVerifyTest {

    /**
     * K-S检验
     * K-S检验是对连续的一维概率分布进行相等性检验
     * 它是用来判断两组数据分布之间的差异是否显著
     * @param x
     * @param y
     */
    public void ksVerify(double[] x, double[] y){
        //获取到d统计量，需要用它来计算检验的P值
        double dValue = TestUtils.kolmogorovSmirnovStatistic(x, y);
        System.out.println("dValue = " + dValue);

        //评估零假设，其值取自于单位正态分布
        double zeroValue = TestUtils.kolmogorovSmirnovTest(x, y, false);
        System.out.println("zeroValue = " + zeroValue);

        //得到显著性检验的P值
        double pValue = TestUtils.exactP(dValue, x.length, y.length, false);
        System.out.println("pValue = " + pValue);
    }

    public static void main(String[] args) {
        double[] x = {12, 45, 67, 90, 61, 30};
        double[] y = {9, 18, 45, 20, 19, 60};
        KSVerifyTest ksVerifyTest = new KSVerifyTest();
        ksVerifyTest.ksVerify(x, y);

    }


}