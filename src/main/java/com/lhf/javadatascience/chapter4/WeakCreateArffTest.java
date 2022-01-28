package com.lhf.javadatascience.chapter4;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

/**
 * @ClassName WeakCreateArffTest
 * @Desc WEAK创建并保存ARFF文件
 * @Author diandian
 * @Date 2022/1/10 22:24
 **/
public class WeakCreateArffTest {

    public static void main(String[] args) throws Exception {
        ArrayList<Attribute>      attributes;  //保存属性
        ArrayList<String>      classVals;   //保存值
        Instances       data;   //创建一个Instances对象，这个对象将用来为ARFF文件的@DATA部分中的实例建模 @DATA部分的每一行就是一个实例
        double[]        values;   //保存属性值

        /******************************************************** 头信息 ********************************************************/
        // Set up attributes
        attributes = new ArrayList<Attribute>();    //属性实例化，我们将在ARFF文件中创建@ATTRIBUTE部分
        // Numeric attribute
        attributes.add(new Attribute("age"));  //创建一个名为age的数值型属性
        // String attribute
        ArrayList<String> empty = null;  //这个空的ArrayList将用在Attribute类的构造函数中，以表示name是一个String类型的属性
        attributes.add(new Attribute("name", empty)); //创建一个名为name的字符串属性
        // Date attribute
        attributes.add(new Attribute("dob", "yyyy-MM-dd"));  //创建一个日期类型的属性

        classVals = new ArrayList<String>();  //对值进行实例化
        for (int i = 0; i < 5; i++){
            classVals.add("class" + (i + 1));
        }
        Attribute classVal = new Attribute("class", classVals);  //使用这些值创建一个属性，并添加到属性列表中
        attributes.add(classVal);

        // Create Instances object
        data = new Instances("MyRelation", attributes, 0);  //Instance对象接收关系名MyRelation(该参数对应于ARFF文件的@RELATION部分)与所有属性


        /******************************************************** 数据信息 ********************************************************/
        // Data fill up
        // First instance  第一组
        values = new double[data.numAttributes()];  //为4个属性生成4个属性值
        values[0] = 35;
        values[1] = data.attribute(1).addStringValue("John Doe");
        values[2] = data.attribute(2).parseDate("2021-01-20");
        values[3] = classVals.indexOf("class3");

        // add
        data.add(new DenseInstance(1.0, values));  //将属性值添加到数据段

        // Second instance  第二组
        values = new double[data.numAttributes()];  // important: needs NEW array!
        values[0] = 30;
        values[1] = data.attribute(1).addStringValue("Harry Potter");
        values[2] = data.attribute(2).parseDate("2021-07-05");
        values[3] = classVals.indexOf("class1");

        // add
        data.add(new DenseInstance(1.0, values));

        // Three instance  第三组
        values = new double[data.numAttributes()];  // important: needs NEW array!
        values[0] = 25;
        values[1] = data.attribute(1).addStringValue("Tom");
        values[2] = data.attribute(2).parseDate("2021-01-01");
        values[3] = classVals.indexOf("class4");

        // add
        data.add(new DenseInstance(1.0, values));



        //将生成的ARFF文件保存到指定的地方
        BufferedWriter writer = new BufferedWriter(new FileWriter("D:/training.arff"));
        writer.write(data.toString());
        writer.close();

        // Output data
        System.out.println(data);  //将ARFF文件的内容打印出
    }

}