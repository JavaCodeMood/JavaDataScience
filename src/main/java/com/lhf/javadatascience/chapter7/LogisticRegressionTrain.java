package com.lhf.javadatascience.chapter7;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.csv.CSVParser;
import org.apache.mahout.classifier.sgd.CsvRecordFactory;
import org.apache.mahout.classifier.sgd.LogisticModelParameters;
import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;
import org.apache.mahout.classifier.sgd.RecordFactory;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;

import com.google.common.base.Charsets;

/**
 * @ClassName MahoutOnLineLogisticRegressionTrain
 * @Desc 使用Apache Mahout训练在线逻辑回归模型
 * 在根目录下，创建两个文件夹，model:保存模型 input：存放数据集
 * @Author diandian
 * @Date 2022/1/20 21:25
 **/
public class LogisticRegressionTrain {

    private static double predictorWeight(OnlineLogisticRegression lr, int row, RecordFactory csv, String predictor) {
        double weight = 0;
        for (Integer column : csv.getTraceDictionary().get(predictor)) {
            weight += lr.getBeta().get(row, column);
        }
        return weight;
    }

    /**
     * 逻辑回归训练
     * @param inputFile
     * @param outputFile
     */
    public void logisticRegressionTrain(String inputFile, String outputFile){
        try{
            List<String> predictorList =Arrays.asList("outlook", "temperature", "humidity", "windy", "play");
            List<String> typeList = Arrays.asList("w", "n", "n", "w", "w");
            LogisticModelParameters params = new LogisticModelParameters();
            params.setTargetVariable("play");
            params.setMaxTargetCategories(2);
            params.setNumFeatures(4);
            params.setUseBias(false);
            params.setTypeMap(predictorList,typeList);
            params.setLearningRate(0.5);

            int passes = 10;
            OnlineLogisticRegression olr;

            CsvRecordFactory csv = params.getCsvRecordFactory();
            olr = params.createRegression();

            for(int pass = 0; pass < passes; pass++){

                BufferedReader in = new BufferedReader(new FileReader(inputFile));
                csv.firstLine(in.readLine());
                String row = in.readLine();
                while(row != null){
                    System.out.println(row);
                    Vector input = new RandomAccessSparseVector(params.getNumFeatures());
                    int targetValue = csv.processLine(row, input);
                    olr.train(targetValue, input);
                    row = in.readLine();
                }
                in.close();
            }

            OutputStream modelOutput = new FileOutputStream(outputFile);
            try {
                params.saveTo(modelOutput);
            } finally {
                modelOutput.close();
            }
            PrintWriter output=new PrintWriter(new OutputStreamWriter(System.out, Charsets.UTF_8), true);
            output.println(params.getNumFeatures());
            output.println(params.getTargetVariable() + " ~ ");
            String sep = "";
            for (String v : csv.getTraceDictionary().keySet()) {
                double weight = predictorWeight(olr, 0, csv, v);
                if (weight != 0) {
                    output.printf(Locale.ENGLISH, "%s%.3f*%s", sep, weight, v);
                    sep = " + ";
                }
            }
            output.printf("%n");
            for (int row = 0; row < olr.getBeta().numRows(); row++) {
                for (String key : csv.getTraceDictionary().keySet()) {
                    double weight = predictorWeight(olr, row, csv, key);
                    if (weight != 0) {
                        output.printf(Locale.ENGLISH, "%20s %.5f%n", key, weight);
                    }
                }
                for (int column = 0; column < olr.getBeta().numCols(); column++) {
                    output.printf(Locale.ENGLISH, "%15.9f ", olr.getBeta().get(row, column));
                }
                output.println();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LogisticRegressionTrain train = new LogisticRegressionTrain();
        String inputFile = "input/weather.numeric.csv";
        String outputFile = "model/model";
        train.logisticRegressionTrain(inputFile, outputFile);
    }

}