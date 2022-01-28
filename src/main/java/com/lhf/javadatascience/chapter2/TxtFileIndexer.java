package com.lhf.javadatascience.chapter2;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * @ClassName TxtFileIndexer
 * @Desc 使用Tika对象自动解析文档，提取文档内容
 * @Author diandian
 * @Date 2022/1/6 17:35
 **/
public class TxtFileIndexer {

    public static void main(String[] args) throws Exception{
        //indexDir is the directory that hosts Lucene's index files
        String indexPath = "G:\\liuhefei\\test";
        File indexDir = new File(indexPath);
        //dataDir is the directory that hosts the text files that to be indexed
        File  dataDir  = new File("input");
        Analyzer luceneAnalyzer = new StandardAnalyzer();
        File[] dataFiles  = dataDir.listFiles();

        //IndexWriter indexWriter = new IndexWriter(indexDir,luceneAnalyzer,true);
        Directory dir = FSDirectory.open(Paths.get(indexPath));
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(dir, iwc);

        long startTime = new Date().getTime();
        for(int i = 0; i < dataFiles.length; i++){
            if(dataFiles[i].isFile() && dataFiles[i].getName().endsWith(".txt")){
                System.out.println("Indexing file " + dataFiles[i].getCanonicalPath());
                Document document = new Document();
                Reader txtReader = new FileReader(dataFiles[i]);
                Field pathField = new StringField("path", dataFiles[i].getCanonicalPath(), Field.Store.YES);
                document.add(pathField);
                document.add(new TextField("contents",txtReader));
                indexWriter.addDocument(document);
            }
        }
        //indexWriter.optimize();

        indexWriter.close();
        long endTime = new Date().getTime();

        System.out.println("It takes " + (endTime - startTime)
                + " milliseconds to create index for the files in directory "
                + dataDir.getPath());
    }
}