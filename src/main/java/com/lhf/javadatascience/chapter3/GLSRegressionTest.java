package com.lhf.javadatascience.chapter3;

import org.apache.commons.math3.stat.regression.GLSMultipleLinearRegression;

/**
 * @ClassName GLSRegressionTest
 * @Desc 计算广义最小二乘回归
 * @Author diandian
 * @Date 2022/1/8 17:22
 **/
public class GLSRegressionTest {

    /**
     * GLSMultipleLinearRegression实现广义最小二乘回归
     * 拟合线性模型：Y = X * b + u
     * 其中Y是一个n-vector回归，X是一个[n, k]矩阵，k列称为回归因子，b是回归参数的k-vector, u是误差或残差的n-vector
     * @param x
     * @param y
     * @param omega
     */
    public void calculateGlsRegression(double[][] x, double[] y, double[][] omega){
        GLSMultipleLinearRegression regression = new GLSMultipleLinearRegression();
        regression.newSampleData(y, x, omega);

        //计算相关的回归参数
        double[] beta = regression.estimateRegressionParameters();  //β系数
        double[] residuals = regression.estimateResiduals();  //误差或残差
        double[][] parametersVariance = regression.estimateRegressionParametersVariance();  //参数方差
        double regressandVariance = regression.estimateRegressandVariance();  //回归变数方差
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

        System.out.println("regressandVariance = " + regressandVariance + "\n");
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

        double[][] omega = new double[6][];
        omega[0] = new double[]{1.1, 0, 0, 0, 0, 0};
        omega[1] = new double[]{0, 2.2, 0, 0, 0, 0};
        omega[2] = new double[]{0, 0, 3.3, 0, 0, 0};
        omega[3] = new double[]{0, 0, 0, 4.4, 0, 0};
        omega[4] = new double[]{0, 0, 0, 0, 5.5, 0};
        omega[5] = new double[]{0, 0, 0, 0, 0, 6.6};

        GLSRegressionTest test = new GLSRegressionTest();
        test.calculateGlsRegression(x, y, omega);
    }
}