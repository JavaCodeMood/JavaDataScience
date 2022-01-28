package com.lhf.javadatascience.chapter7;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import org.apache.mahout.math.Matrix;
import org.apache.mahout.math.SequentialAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.classifier.evaluation.Auc;
import org.apache.mahout.classifier.sgd.CsvRecordFactory;
import org.apache.mahout.classifier.sgd.LogisticModelParameters;
import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * @ClassName LogisticRegressionTrainTest
 * @Desc 使用Apache Mahout应用在线逻辑回归模型
 * @Author diandian
 * @Date 2022/1/25 17:39
 **/
public class LogisticRegressionTrainTest {

    private static String inputFile="input/weather.numeric.csv";  //存放data文件
    private static String modelFile="model";   //存放model文件
    private static boolean showAuc;
    private static boolean showScores;
    private static boolean showConfusion;

    static BufferedReader open(String inputFile) throws IOException {
        InputStream in;
        try {
            in = Resources.getResource(inputFile).openStream();
        } catch (IllegalArgumentException e) {
            in = new FileInputStream(new File(inputFile));
        }
        return new BufferedReader(new InputStreamReader(in, Charsets.UTF_8));
    }

    public static void main(String[] args) throws Exception {
        showAuc = true;
        showConfusion = true;
        //创建一个AUC,计算分类器的AUC(曲线下面试)，并且把它用作性能指标
        Auc collector = new Auc();
        //从model文件读取并加载在线逻辑回归算法的参数
        LogisticModelParameters lmp = LogisticModelParameters.loadFrom(new File(modelFile));
        //创建CsvRecordFactory， 读取测试文件数据
        CsvRecordFactory csv = lmp.getCsvRecordFactory();
        //创建一个OnlineLogisticRegression分类器
        OnlineLogisticRegression lr = lmp.createRegression();
        //读取测试数据文件
        BufferedReader in = LogisticRegressionTrainTest.open(inputFile);
        //测试数据文件的第一行是文件头或特征类别，需要从分类中忽略这一行，从下一行开始读取
        String line = in.readLine();
        csv.firstLine(line);
        line = in.readLine();
        //将分类结果显示在控制台中
        PrintWriter output=new PrintWriter(new OutputStreamWriter(System.out, Charsets.UTF_8), true);
        //把预测类别，模型输出以及对数似然值打印出来
        output.println("\"target\",\"model-output\",\"log-likelihood\"");
        //遍历每个非空的行
        while (line != null) {
            System.out.println("-----" + line);
            //为测试数据创建特征向量
            Vector v = new SequentialAccessSparseVector(lmp.getNumFeatures());
            //创建一个遍历，用来存放每一行或每个数据点的真实类别值
            int target = csv.processLine(line, v);
            //对测试数据点进行分类，并从分类器获取分数
            double score = lr.classifyScalarNoLink(v);
            //把类值，分数，对数似然值打印到控制台中
            output.printf(Locale.ENGLISH, "%d,%.3f,%.6f%n", target, score, lr.logLikelihood(target, v));
            //把类别和分数添加到AUC变量中
            collector.add(target, score);
            //读取下一行
            line = in.readLine();
            System.out.println("I am here");
        }
        //打印AUC
        output.printf(Locale.ENGLISH, "AUC = %.2f%n", collector.auc());
        //打印分类的混淆矩阵，创建一个混淆矩阵
        Matrix m = collector.confusion();
        //由于训练与测试数据只有两个类，所以我们会得到一个2x2的混淆矩阵
        output.printf(Locale.ENGLISH, "confusion: [[%.1f, %.1f], [%.1f, %.1f]]%n",
                m.get(0, 0), m.get(1, 0), m.get(0, 1), m.get(1, 1));
        //Matrix中存放着熵值
        m = collector.entropy();
        output.printf(Locale.ENGLISH, "entropy: [[%.1f, %.1f], [%.1f, %.1f]]%n",
                m.get(0, 0), m.get(1, 0), m.get(0, 1), m.get(1, 1));
    }
}