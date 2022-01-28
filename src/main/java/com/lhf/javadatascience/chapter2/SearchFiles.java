package com.lhf.javadatascience.chapter2;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths;

/**
 * @ClassName SearchFiles
 * @Desc 使用Apache Lucene搜索带索引的数据
 * @Author diandian
 * @Date 2022/1/6 18:30
 **/
public class SearchFiles {

    public static final String INDEX_DIRECTORY = "index";  //用来保存你的index的路径
    public static final String FIELD_CONTENTS = "contents";  //保存带搜索的字段内容

    public static void main(String[] args) throws Exception {
        //打开index目录中的索引
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        //创建一个搜索器
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        //创建一个标准分析器
        Analyzer analyzer = new StandardAnalyzer();
        //创建一个查询分析器
        QueryParser queryParser = new QueryParser(FIELD_CONTENTS, analyzer);
        //String searchString = "over-full AND shakespeare";  //要搜索的词汇,查找同时包含over-full 和 shakespeare的文档
        String searchString = "my";
        //使用搜索字符串，创建一个查询
        Query query = queryParser.parse(searchString);
        //搜索器查看索引，看看是否能够找到搜索词 这里的5为返回的搜索结果数
        TopDocs results = indexSearcher.search(query, 5);
        //保存hits
        ScoreDoc[] hits = results.scoreDocs;

        //搜索命中的文档数量
        TotalHits numTotalHits = results.totalHits;
        System.out.println(numTotalHits + " total matching documents");
        //遍历hits，获取搜索命中的文档ID,然后通过文档ID创建文档，并打印文档的路径与Lucene为文档计算的分数
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = indexSearcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("path") + " score=" + hits[i].score);
        }
    }

}