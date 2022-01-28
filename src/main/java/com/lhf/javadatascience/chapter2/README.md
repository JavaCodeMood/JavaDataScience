Lucene官网：https://lucene.apache.org/core/downloads.html    

Package: org.apache.lucene.document

这个包提供了一些为封装要索引的文档所需要的类，比如 Document, Field。这样，每一个文档最终被封装成了一个 Document 对象。

    Package: org.apache.lucene.analysis

这个包主要功能是对文档进行分词，因为文档在建立索引之前必须要进行分词，所以这个包的作用可以看成是为建立索引做准备工作。

    Package: org.apache.lucene.index

这个包提供了一些类来协助创建索引以及对创建好的索引进行更新。这里面有两个基础的类：IndexWriter 和 IndexReader，其中 IndexWriter 是用来创建索引并添加文档到索引中的，IndexReader 是用来删除索引中的文档的。

    Package: org.apache.lucene.search

这个包提供了对在建立好的索引上进行搜索所需要的类。比如 IndexSearcher 和 Hits, IndexSearcher 定义了在指定的索引上进行搜索的方法，Hits 用来保存搜索得到的结果。

一个简单的搜索应用程序

假设我们的电脑的目录中含有很多文本文档，我们需要查找哪些文档含有某个关键词。为了实现这种功能，我们首先利用 Lucene 对这个目录中的文档建立索引，然后在建立好的索引中搜索我们所要查找的文档。通过这个例子读者会对如何利用 Lucene 构建自己的搜索应用程序有个比较清楚的认识。

建立索引

    为了对文档进行索引，Lucene 提供了五个基础的类，他们分别是 Document, Field, IndexWriter, Analyzer, Directory。下面我们分别介绍一下这五个类的用途：

    Document
    Document 是用来描述文档的，这里的文档可以指一个 HTML 页面，一封电子邮件，或者是一个文本文件。一个 Document 对象由多个 Field 对象组成的。可以把一个 Document 对象想象成数据库中的一个记录，而每个 Field 对象就是记录的一个字段。

    Field
    Field 对象是用来描述一个文档的某个属性的，比如一封电子邮件的标题和内容可以用两个 Field 对象分别描述。

    Analyzer
在一个文档被索引之前，首先需要对文档内容进行分词处理，这部分工作就是由 Analyzer 来做的。Analyzer 类是一个抽象类，它有多个实现。针对不同的语言和应用需要选择适合的 Analyzer。Analyzer 把分词后的内容交给 IndexWriter 来建立索引。

    IndexWriter
    IndexWriter 是 Lucene 用来创建索引的一个核心的类，他的作用是把一个个的 Document 对象加到索引中来。

    Directory
    这个类代表了 Lucene 的索引的存储的位置，这是一个抽象类，它目前有两个实现，第一个是 FSDirectory，它表示一个存储在文件系统中的索引的位置。第二个是 RAMDirectory，它表示一个存储在内存当中的索引的位置。


IndexFiles.java
1. 在项目中创建input和index文件夹
2. 下载实例数据http://norvig.com/ngrams/，下载shakespeare.txt文件，放入input文件中
3. 在Run Configurations窗口中，在Program Arguments中输入-docs input\ -index index\
4. 启动运行
