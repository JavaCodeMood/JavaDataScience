package com.lhf.javadatascience.chapter1;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Iterator;

/**
 * @ClassName JsonReading
 * @Desc 使用JSON.simple读取json文件
 * @Author diandian
 * @Date 2022/1/5 11:14
 **/
public class JsonReading {

    /**
     * 读取json文件
     * @param fileName
     */
    public void readJson(String fileName){
        //创建一个JSON解析器
        JSONParser parser = new JSONParser();
        try{
            //解析器读取json文件
            Object obj = parser.parse(new FileReader(fileName));
            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject.toString());

            String name = (String) jsonObject.get("name");
            Long age = (Long) jsonObject.get("age");
            Long height = (Long) jsonObject.get("height");
            System.out.println("name:" + name + " , age: " + age + " , height: " + height);

            JSONArray content = (JSONArray) jsonObject.get("content");
            Iterator<String> iterator = content.iterator();
            while (iterator.hasNext()){
                System.out.println(iterator.next());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JsonReading jsonReading = new JsonReading();
        jsonReading.readJson("doc/test.json");
    }
}