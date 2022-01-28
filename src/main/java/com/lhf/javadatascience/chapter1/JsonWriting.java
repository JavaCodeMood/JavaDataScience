package com.lhf.javadatascience.chapter1;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;

/**
 * @ClassName JsonWriting
 * @Desc 使用json.simple编写json文件
 * @Author diandian
 * @Date 2022/1/5 10:40
 **/
public class JsonWriting {

    /**
     * 生成json文件
     * @param outFileName  要生成的json文件名
     * @return
     */
    public JSONObject writeJson(String outFileName){
        JSONObject json = new JSONObject();
        json.put("name", "李四");
        json.put("age", 28);
        json.put("height", 173);

        JSONArray list = new JSONArray();
        list.add("北国风光");
        list.add("千里冰封");
        list.add("万里雪飘");
        list.add("长城内外");
        list.add("大河上下");

        json.put("content", list);
        try{
            FileWriter fileWriter = new FileWriter(outFileName);
            fileWriter.write(json.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(json);
        return json;

    }

    public static void main(String[] args) {
        JsonWriting jsonWriting = new JsonWriting();
        jsonWriting.writeJson("doc/test.json");
    }
}