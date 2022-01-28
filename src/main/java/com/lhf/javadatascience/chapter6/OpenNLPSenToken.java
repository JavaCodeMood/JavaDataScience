package com.lhf.javadatascience.chapter6;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @ClassName OpenNLPSenToken
 * @Desc 使用OpenNLP检测标记（单词）或句子
 * 文档：https://opennlp.apache.org/docs/1.9.4/apidocs/opennlp-tools/index.html
 * 模型下载：https://opennlp.apache.org/models.html
 * @Author diandian
 * @Date 2022/1/14 14:51
 **/
public class OpenNLPSenToken {

    /**
     * 使用OpenNLP分词器与句子检测器模型来进行分词，把字符串文本切分为多个句子
     *
     * @param sourceText   包含源文本的字符串
     * @param modelPath  模型路径
     * @param choice  sentence:切分为句子   word:切分为单词
     */
    public void useOpenNlp(String sourceText, String modelPath, String choice){
        InputStream modelIn = null;
        try{
            //以输入流方式读取模型
            modelIn = new FileInputStream(modelPath);

            //切分为句子
            if(choice.equalsIgnoreCase("sentence")){
                //根据预建模型创建一个句子模型，然后关闭，用来保存预建模型的变量
                SentenceModel model = new SentenceModel(modelIn);
                modelIn.close();
                //创建一个句子检测器
                SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
                //使用句子检测器从源文本检测句子，并把得到的结果保存在字符串数组中
                String sentences[] = sentenceDetector.sentDetect(sourceText);
                System.out.println("Sentences: ");
                //遍历打印出句子
                for(String sentence:sentences){
                    System.out.println(sentence);
                }
            }
            //切分为单词
            else if(choice.equalsIgnoreCase("word")){
                //使用预建模型创建一个TokenizerModel，并关闭模型
                TokenizerModel model = new TokenizerModel(modelIn);
                modelIn.close();
                //创建一个Tokenizer
                Tokenizer tokenizer = new TokenizerME(model);
                //使用Tokenizer从源文本提取标记（单词）,并把它们存入一个字符串数组中
                String tokens[] = tokenizer.tokenize(sourceText);
                System.out.println("Words: ");
                //这个打印单词
                for(String token:tokens){
                    System.out.println(token);
                }
            }
            else{
                System.out.println("Error in choice");
                modelIn.close();
                return;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        OpenNLPSenToken openNlp = new OpenNLPSenToken();
        openNlp.useOpenNlp("My name is Rushdi Shams. "
                + "You can use Dr. before my name as I have a Ph.D. "
                + "but I am a bit shy to use it.", "input/opennlp/opennlp-en-ud-ewt-sentence.bin", "sentence");
        openNlp.useOpenNlp("\"Let's get this vis-a-vis\", he said, \"these boys' marks are really that well?\"", "input/opennlp/opennlp-en-ud-ewt-tokens.bin", "word");
    }

}