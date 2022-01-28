package com.lhf.javadatascience.chapter3;

import org.apache.commons.math3.stat.inference.TestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName OneWayANOVATest
 * @Desc 单因素方差分析
 * @Author diandian
 * @Date 2022/1/10 20:18
 **/
public class OneWayANOVATest {

    /**
     * 单因素方差分析
     * ANOVA(方差分析) 使用单因素方差分析法分析5组数据（及以上）独立无关的数据点之间的差异是否显著
     *
     * @param dataList
     */
    public void calculateAnova( List<double[]> dataList){
       //获取单因素分析的F值
        double fValue = TestUtils.oneWayAnovaFValue(dataList);
        System.out.println("FValue = " + fValue);

        //获取单因素分析的P值
        double pValue = TestUtils.oneWayAnovaPValue(dataList);
        System.out.println("PValue = " + pValue);

        //判断给定的数据之间的差异是否显著
        boolean bool = TestUtils.oneWayAnovaTest(dataList, 0.05);
        System.out.println("bool = " + bool);
    }

    public static void main(String[] args) {
        double[] value1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        double[] value2 = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        double[] value3 = {100, 200, 300, 400, 500, 600, 700, 800, 900, 1000};
        double[] value4 = {1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};
        double[] value5 = {80, 90, 100, 110, 120, 130, 150, 160, 170, 180};

        List<double[]> dataList = new ArrayList<>();
        dataList.add(value1);
        dataList.add(value2);
        dataList.add(value3);
        dataList.add(value4);
        dataList.add(value5);

        OneWayANOVATest anovaTest = new OneWayANOVATest();
        anovaTest.calculateAnova(dataList);
    }
}