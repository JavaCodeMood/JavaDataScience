package com.lhf.javadatascience.chapter4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weka.associations.Apriori;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 * @ClassName WekaAssociationRuleTest
 * @Desc 使用Weka学习数据间的关联规则
 * @Author diandian
 * @Date 2022/1/13 10:33
 **/
public class WekaAssociationRuleTest {

    private final static Logger log = LogManager.getLogger(WekaAssociationRuleTest.class);

    Instances superMarket = null;  //保存超市数据集
    Apriori apriori;  //Apriori学习器

    /**
     * 加载数据集文件
     * @param arffInput
     */
    public void loadArff(String arffInput){
        ConverterUtils.DataSource source = null;
        try{
            source = new ConverterUtils.DataSource(arffInput);
            superMarket = source.getDataSet();  //读取数据
        }catch (Exception e){
            log.error("加载ARFF文件异常：" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 学习数据间的关联规则
     * Apriori学习器生成的规则数默认为10.如果需要生成更多规则，请在创建关联关系之前，调用setNumRules(n)方法设置规则数，
     * 其中参数n是一个整数，表示规则数
     */
    public void generateRule(){
        apriori = new Apriori();
        try{
            apriori.setNumRules(20);  //指定生成20个规则
            apriori.buildAssociations(superMarket);  //使用给定的数据集创建关联关系

            System.out.println(apriori);  //打印学习器
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WekaAssociationRuleTest test = new WekaAssociationRuleTest();
        test.loadArff("D:\\Program Files\\Weka-3-9-5\\data\\supermarket.arff");
        test.generateRule();
    }
}