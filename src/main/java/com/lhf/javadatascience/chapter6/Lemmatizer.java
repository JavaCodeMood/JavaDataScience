package com.lhf.javadatascience.chapter6;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.Properties;

/**
 * @ClassName Lemmatizer
 * @Desc 使用Stanford CoreNLP从标记中提取词根，词性以及识别命名实体
 *
 * @Author diandian
 * @Date 2022/1/20 12:04
 **/
public class Lemmatizer {

    /**
     *使用Stanford CoreNLP从标记中提取词根，词性以及识别命名实体
     * @param inputStr
     */
    public void stanfordCoreNlp( String inputStr){
        //创建Stanford CoreNLP流水线，通过这个流水线你可以把多个属性值提供给CoreNLP引擎
        StanfordCoreNLP pipelines;
        //创建属性对象
        Properties props = new Properties();
        //props.put("annotators", "tokenize, ssplit, pos, lemma, ner");  //添加对象,使用词性标注与词形还原来进行标记
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
        props.setProperty("pos.model", "russian-ud-pos.tagger");
        props.setProperty("ner.model", "english.all.3class.distsim.crf.ser.gz");
        props.setProperty("ner.useSUTime", "false");
        props.setProperty("ner.applyNumericClassifiers", "false");
        props.setProperty("sutime.includeRange", "false");

        //创建CoreNLP对象
        pipelines = new StanfordCoreNLP(props, false);
        //使用给定的文本创建Annotation
        Annotation document = pipelines.process(inputStr);
        //为每个标记获取原始标记与标记词根
        for(CoreMap sentence : document.get(SentencesAnnotation.class)){
            for(CoreLabel token : sentence.get(TokensAnnotation.class)){
                String word = token.get(TextAnnotation.class);
                String lemma = token.get(LemmaAnnotation.class);
                System.out.println(word + " -->" + lemma );

                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                System.out.println(word + " -->" + lemma + "-->" + pos + "-->" + ne);
            }
        }
    }

    public static void main(String[] args) {
        Lemmatizer lemma = new Lemmatizer();
        String text = "In this section we cover getting started with CoreNLP and different usage modes. CoreNLP can be used via the command line, in Java code, or with calls to a server, and can be run on multiple languages including Arabic, Chinese, English, French, German, and Spanish.";
        lemma.stanfordCoreNlp(text);
    }
}