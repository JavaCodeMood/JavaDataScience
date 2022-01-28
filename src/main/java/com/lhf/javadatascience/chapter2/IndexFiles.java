package com.lhf.javadatascience.chapter2;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

/**
 * @ClassName IndexFiles
 * @Desc 使用Apache Lucene为数据建立索引
 * @Author diandian
 * @Date 2022/1/6 16:39
 **/
public class IndexFiles {

    /**
     * 该方法将使用指定的索引为给定的任意文件建立索引
     * 如果为该方法提供的参数是一个目录，这个方法就会遍历给定目录下的所有文件与子目录
     * 这个方法会为每个输入文件建立索引
     * @param writer
     * @param path
     * @throws IOException
     */
    public static void indexDocs(final IndexWriter writer, Path path) throws IOException {
        //如果提供的是目录，这个目录会被递归遍历
        if (Files.isDirectory(path)) {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                        //用来根据给定的路径与基本的文件属性访问文件或目录
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            try {
                                indexDoc(writer, file, attrs.lastModifiedTime().toMillis());
                            } catch (IOException ignore) {
                                ignore.printStackTrace();
                            }
                            return FileVisitResult.CONTINUE;
                        }
                    }
            );
        } else {
            indexDoc(writer, path, Files.getLastModifiedTime(path).toMillis());
        }
    }

    /**
     * 为单个文件建立索引
     * @param writer
     * @param file
     * @param lastModified
     * @throws IOException
     */
    public static void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException {
        try (InputStream stream = Files.newInputStream(file)) {
            Document doc = new Document();  //创建一个空文档
            //添加文件路径字段，输入path作为字段名，这个字段是可检索或可索引的
            Field pathField = new StringField("path", file.toString(), Field.Store.YES);
            doc.add(pathField);
            doc.add(new LongPoint("modified", lastModified));  //添加文件的最后修改日期
            //添加文件内容到名为contents的字段中
            doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));

            //为文件创建索引
            if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
                System.out.println("adding " + file);
                writer.addDocument(doc);
            } else {
                //如果文件已经有了索引，将使用updateDocument代替旧的那个，以匹配准确的路径
                System.out.println("updating " + file);
                writer.updateDocument(new Term("path", file.toString()), doc);
            }
        }
    }
    public static void main(String[] args) {
        //index:该参数是包含索引的文件夹
        //docs: 该参数是包含文本文件的文件夹
        //update: 该参数是你想创建新索引还是更新旧索引

        String indexPath = "index";  //索引目录
        String docsPath = "input";  //文档目录
        boolean create = true;
        //设置三个选项的值
        for(int i=0;i<args.length;i++) {
            if ("-index".equals(args[i])) {
                indexPath = args[i+1];
                i++;
            } else if ("-docs".equals(args[i])) {
                docsPath = args[i+1];
                i++;
            } else if ("-update".equals(args[i])) {
                create = false;
            }
        }

        //设置文档目录
        final Path docDir = Paths.get(docsPath);

        //开始为目录中的文件建立索引
        Date start = new Date();  //设置时间，对创建索引的延迟时间进行计算
        try {
            System.out.println("Indexing to directory '" + indexPath + "'...");
            //创建一个目录与分析器
            Directory dir = FSDirectory.open(Paths.get(indexPath));
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

            if (create) {  //创建索引
                iwc.setOpenMode(OpenMode.CREATE);  //索引模式
            } else {  //更新索引
                iwc.setOpenMode(OpenMode.CREATE_OR_APPEND); //索引模式
            }

            //创建索引
            IndexWriter writer = new IndexWriter(dir, iwc);
            indexDocs(writer, docDir);

            writer.close();

            Date end = new Date();
            System.out.println(end.getTime() - start.getTime() + " total milliseconds");  //创建索引耗费时间

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}