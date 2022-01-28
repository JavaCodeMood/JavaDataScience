package com.lhf.javadatascience.chapter3;

import org.apache.commons.math3.stat.regression.SimpleRegression;

/**
 * @ClassName SimpleRegressionTest
 * @Desc 计算简单回归
 * @Author diandian
 * @Date 2022/1/8 16:20
 **/
public class SimpleRegressionTest {

    /**
     * 计算简单回归
     * @param datas 二维数组，数组元素表示一系列的（x, y）值
     */
    public void calculateRegression(double[][] datas){
        /**
         * SimpleRegression类支持带有一个自变量的普通最小二乘回归 y = intercept + slope * x
         * 其中intercept是可选参数
         * 这个类还能为intercept提供标准误差
         * 你可以向模型一个个地添加观测值（x,y）对，也可以通过一个二维数组提供
         */
        SimpleRegression regression = new SimpleRegression();
        regression.addData(datas);  //添加数据

        double intercept = regression.getIntercept();  //计算截距
        double slope = regression.getSlope();  //计算斜率
        double interceptStdErr = regression.getInterceptStdErr();   //计算标准误差
        double slopeStdErr = regression.getSlopeStdErr();  //计算截距与斜率的标准误差
        System.out.println("intercept = " + intercept + " , slope = " + slope);
        System.out.println("interceptStdErr = " + interceptStdErr + " , slopeStdErr = " + slopeStdErr);
    }

    public static void main(String[] args) {
        double[][] datas = {{1, 3}, {2, 5}, {6,7}, {4, 8}, {9, 1}, {5, 15}, {8, 2}};
        SimpleRegressionTest regressionTest = new SimpleRegressionTest();
        regressionTest.calculateRegression(datas);
    }
}