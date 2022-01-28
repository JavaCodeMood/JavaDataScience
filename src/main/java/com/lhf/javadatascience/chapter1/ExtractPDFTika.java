package com.lhf.javadatascience.chapter1;


import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.ocr.TesseractOCRConfig;
import org.apache.tika.parser.pdf.PDFParserConfig;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.parser.pdf.PDFParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName ExtractPDFTika
 * @Desc 使用Apache Tika提取PDF内容
 * @Author diandian
 * @Date 2021/12/30 18:06
 **/
public class ExtractPDFTika {

    /**
     * 提取PDF内容
     * @param fileName  文件名称
     */
    public String convertPdf(String fileName){
        String fileContent = null;
        InputStream stream = null;  //创建一个输入流，以字节流的形式存放PDF数据
        try {
            stream = new FileInputStream(fileName);
            AutoDetectParser parser = new AutoDetectParser();  //创建解析器
            //创建一个handler，用来处理文件的正文内容，设置为-1表示忽略文件内容长度
            BodyContentHandler handler = new BodyContentHandler(-1);
            Metadata metadata = new Metadata();
            //调用解析器的parse()方法
            parser.parse(stream, handler, metadata, new ParseContext());
            fileContent = handler.toString();
            System.out.println(fileContent);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if (stream != null)
                try {
                    stream.close();
                } catch (IOException e) {
                    System.out.println("Error closing stream");
                }
        }
        return fileContent;
    }

    public String paserPdf(String filaPath) {

        try {
            File file = new File(filaPath);

            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            FileInputStream fileInputStream = new FileInputStream(file);
            ParseContext parseContext = new ParseContext();

            //提取图像信息
            //JpegParser JpegParser = new JpegParser();
            //提取PDF
            PDFParser pdfParser = new PDFParser();
            pdfParser.parse(fileInputStream,handler,metadata,parseContext);

            return handler.toString();
            /*String[] names = metadata.names();
            for (String name : names) {
                System.out.println("name:"+metadata.get(name));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public String readPdf(String fileName){
       try {
           //Tika默认是10*1024*1024，这里防止文件过大导致Tika报错
           BodyContentHandler handler = new BodyContentHandler(100*1024*1024);

           Metadata metadata = new Metadata();
           FileInputStream inputstream = new FileInputStream(new File(fileName));
           ParseContext pcontext = new ParseContext();

           // 解析PDF文档时应由超类AbstractParser的派生类PDFParser实现
           PDFParser pdfparser = new PDFParser();
           pdfparser.parse(inputstream, handler, metadata, pcontext);

           // 获取PDF文档的内容
           System.out.println("PDF文档内容:" + handler.toString());

           // 获取PDF文档的元数据
           System.out.println("PDF文档元数据:");
           String[] metadataNames = metadata.names();

           for (String name : metadataNames) {
               System.out.println(name + " : " + metadata.get(name));
           }
           return handler.toString();
       }catch (Exception e){
           e.printStackTrace();
           return null;
       }

    }

    public String readPDF(String fileName){
        try{
            Parser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler(Integer.MAX_VALUE);

            TesseractOCRConfig config = new TesseractOCRConfig();
            PDFParserConfig pdfConfig = new PDFParserConfig();
            pdfConfig.setExtractInlineImages(true);

            ParseContext parseContext = new ParseContext();
            parseContext.set(TesseractOCRConfig.class, config);
            parseContext.set(PDFParserConfig.class, pdfConfig);
            //need to add this to make sure recursive parsing happens!
            parseContext.set(Parser.class, parser);

            FileInputStream stream = new FileInputStream(fileName);
            Metadata metadata = new Metadata();
            parser.parse(stream, handler, metadata, parseContext);
            System.out.println(metadata);
            String content = handler.toString();
            System.out.println("===============");
            System.out.println(content);
            System.out.println("Done");
            return content;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        ExtractPDFTika tika = new ExtractPDFTika();
        //String fileContent = tika.convertPdf("doc/list.pdf");
        //System.out.println("文件内容：" + fileContent);

        //String fileContent1 = tika.paserPdf("doc/list.pdf");
        //System.out.println("文件内容：" + fileContent1);

        //String fileContent2 = tika.readPdf("doc/list.pdf");
        //System.out.println("文件内容：" + fileContent2);

        String fileContent3 = tika.readPdf("doc/list.pdf");
        System.out.println("文件内容：" + fileContent3);
    }
}