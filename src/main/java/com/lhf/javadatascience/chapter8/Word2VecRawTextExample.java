package com.lhf.javadatascience.chapter8;

import org.deeplearning4j.models.embeddings.WeightLookupTable;
import org.deeplearning4j.models.embeddings.inmemory.InMemoryLookupTable;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.models.word2vec.wordstore.inmemory.InMemoryLookupCache;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.UimaSentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @ClassName Word2VecRawTextExample
 * @Desc 使用DL4j创建Word2Vec神经网络
 * @Author diandian
 * @Date 2022/1/26 17:26
 **/
public class Word2VecRawTextExample {

    /**
     * 使用DL4j创建Word2Vec神经网络
     * @param filePath
     */
    public void word2VecRawText(String filePath){
       try{
           // 获取txt文件中的原始句子，使用迭代器对它们进行遍历，并且进行预处理，比如转化为小写，去除每一行前后的空白
           SentenceIterator iter = UimaSentenceIterator.createWithPath(filePath);
           // Word2vec使用单词或标记，不使用句子，拆分行中的空格以获取单词
           TokenizerFactory t = new DefaultTokenizerFactory();
           t.setTokenPreProcessor(new CommonPreprocessor());
           //DL4j提供了词汇缓存机制，用来处理一般的自然语言任务
           InMemoryLookupCache cache = new InMemoryLookupCache();
           WeightLookupTable table = new InMemoryLookupTable.Builder()
                   .vectorLength(100)
                   .useAdaGrad(false)
                   .cache(cache)
                   .lr(0.025f).build();

           //配置Word2vec神经网络
           //minWordFrequency是单词在语料库中最少出现的次数，在这里是如果单词出现次数少于5次，就不会学习它
           //单词必须出现在多个上下文环境中，以便学习与它们有关的有用特征
           //iterations：指定神经网络为一批数据更新自身系数的次数、设置得太小，可能会导致学习不充分，而设置太大又会导致网络训练时间太长
           //layerSize表示单词向量中的特征数，或者特征空间中的维数
           //interate:方法告知网络当前正在哪批数据上进行训练
           //tokenizer:把当前批中的单词传递给它
           Word2Vec vec = new Word2Vec.Builder()
                   .minWordFrequency(5).iterations(1)
                   .layerSize(100).lookupTable(table)
                   .stopWords(new ArrayList<String>())
                   .vocabCache(cache).seed(42)
                   .windowSize(5).iterate(iter).tokenizerFactory(t).build();


           //启动神经网络
           vec.fit();

           // 把神经网络产生的单词向量写入一个输出文件中
           WordVectorSerializer.writeWordVectors(vec, "word2vec.txt");

           //对特征向量进行评估
           //vec.wordsNearest("man", 5)语句会把神经网络判断为语义类似的单词聚集在一起，并提供给我们，5是设置为想要的最近单词数
           Collection<String> lst = vec.wordsNearest("man", 5);
           System.out.println(lst);
           //用来返回所输入的两个单词的余弦相似度，其值越接近1，这两个单词就越类似
           double cosSim = vec.similarity("cruise", "voyage");
           System.out.println(cosSim);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public static void main(String[] args) throws Exception {

        // Gets Path to Text file
        String filePath = "input/shakespeare.txt";
        Word2VecRawTextExample example = new Word2VecRawTextExample();
        example.word2VecRawText(filePath);

    }
}