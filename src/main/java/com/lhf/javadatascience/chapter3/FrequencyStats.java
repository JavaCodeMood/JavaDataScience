package com.lhf.javadatascience.chapter3;

import org.apache.commons.math3.stat.Frequency;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName FrequencyStats
 * @Desc 计算频率分布
 * @Author diandian
 * @Date 2022/1/7 20:45
 **/
public class FrequencyStats {

    /**
     * 计算数组中的值的频率分布
     * @param values
     */
    public Map<Double, Integer> getFrequencyStats(double[] values){
        //创建Frequency 对象
        Frequency freq = new Frequency();
        for(int i = 0; i < values.length; i++){
            freq.addValue(values[i]);
        }

        Map<Double, Integer> freqMap = new HashMap<>();
        //为数组的每个值生成频数，并存入map
        for(int i = 0; i < values.length; i++){
           // System.out.println(freq.getCount(values[i]));

            freqMap.put(values[i], (int)freq.getCount(values[i]));
        }
        return freqMap;
    }

    public static void main(String[] args) {
        FrequencyStats stats = new FrequencyStats();
        double[] values = {1, 4, 6, 8, 0, 1, 2, 5, 7, 8, 0, 3, 4, 5, 6, 7, 9, 2, 4, 5, 0, 1, 8};
        /*List<Double> doubleList = stats.getFrequencyStats(values);
        for(int i = 0; i < doubleList.size(); i++){
            System.out.println(doubleList.get(i));
        }*/
        Map<Double, Integer> map = stats.getFrequencyStats(values);
        for(Double key : map.keySet()){
            Integer value = map.get(key);
            System.out.println(key + " : " + value);
        }
    }
}