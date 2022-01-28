package com.lhf.javadatascience.chapter3;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

/**
 * @ClassName OLSRegressionTest
 * @Desc 计算普通最小二乘回归
 * @Author diandian
 * @Date 2022/1/8 16:45
 **/
public class OLSRegressionTest {

    /**
     * OLSMultipleLinearRegression支持普通最小二乘回归来拟合线性模型 Y = X*b + u
     * 其中Y是一个n-vector回归，X是一个[n, k]矩阵，k列称为回归因子，b是回归参数的k-vector, u是误差或残差的n-vector
     * @param x
     * @param y
     */
    public void calculateOlsRegression(double[][] x, double[] y){
        //创建OLSMultipleLinearRegression对象
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.newSampleData(y, x);  //添加数据

        //计算相关的回归参数
        double[] beta = regression.estimateRegressionParameters();  //β系数
        double[] residuals = regression.estimateResiduals();  //误差或残差
        double[][] parametersVariance = regression.estimateRegressionParametersVariance();  //参数方差
        double regressandVariance = regression.estimateRegressandVariance();  //回归变数方差
        double  rSquared = regression.calculateRSquared();
        double sigma = regression.estimateRegressionStandardError();

        for(int i = 0; i < beta.length; i++){
            System.out.println(beta[i]);
        }
        System.out.println("\n");
        for(int i = 0; i < residuals.length; i++){
            System.out.println(residuals[i]);
        }
        System.out.println("\n");

        for(int i = 0; i < parametersVariance.length; i++){
            for(int j = 0; j < parametersVariance[i].length; j++){
                System.out.println(parametersVariance[i][j]);
            }
        }

        //System.out.println("beta = " + Arrays.stream(beta).iterator().next() + "\n");
        //System.out.println("residuals = "+ Arrays.stream(residuals).iterator().next() + "\n");
        //System.out.println("parametersVariance = " + Arrays.stream(Arrays.stream(parametersVariance).iterator().next()).iterator().next() + "\n");
        System.out.println("regressandVariance = " + regressandVariance + " , rSquared = " + rSquared + "\n");
        System.out.println("sigma = " + sigma);
    }

    public static void main(String[] args) {
        double[] y = new double[]{11.0, 12.0, 13.0, 14.0, 15.0, 16.0};
        //矩阵
        double[][] x = new double[6][];
        x[0] = new double[]{0, 0, 0, 0, 0};
        x[1] = new double[]{2.0, 0, 0, 0, 0};
        x[2] = new double[]{0, 3.0, 0, 0, 0};
        x[3] = new double[]{0, 0, 4.0, 0, 0};
        x[4] = new double[]{0, 0, 0, 5.0, 0};
        x[5] = new double[]{0, 0, 0, 0, 6.0};

        OLSRegressionTest test = new OLSRegressionTest();
        test.calculateOlsRegression(x, y);

    }
}