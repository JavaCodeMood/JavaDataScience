WEKA存储数据的格式是ARFF（Attribute-Relation File Format）文件，这是一种ASCII文本文件。      
WEAK下载地址：http://www.cs.waikato.ac.nz/ml/weka/downloading.html         

https://sourceforge.net/projects/weka/

weak资料学习地址： https://weka.sourceforge.io/packageMetaData/


下载安装后Weka的安装目录会自带ARFF文件，在WEKA安装目录的“data”子目录下可以找到"weather.arff"文件。              
文件内容如下：

@relation weather           

@attribute outlook {sunny, overcast, rainy}             
@attribute temperature numeric        
@attribute humidity numeric           
@attribute windy {TRUE, FALSE}            
@attribute play {yes, no}           

@data         
sunny,85,85,FALSE,no         
sunny,80,90,TRUE,no          
overcast,83,86,FALSE,yes            
rainy,70,96,FALSE,yes          
rainy,68,80,FALSE,yes           
rainy,65,70,TRUE,no                      
overcast,64,65,TRUE,yes          
sunny,72,95,FALSE,no          
sunny,69,70,FALSE,yes          
rainy,75,80,FALSE,yes         
sunny,75,70,TRUE,yes           
overcast,72,90,TRUE,yes           
overcast,81,75,FALSE,yes            
rainy,71,91,TRUE,no          


文件说明: 
识别ARFF文件的重要依据是分行，因此不能在这种文件里随意的断行。空行（或全是空格的行）将被忽略。         
以“%”开始的行是注释，WEKA将忽略这些行。如果你看到的“weather.arff”文件多了或少了些“%”开始的行，是没有影响的。            
除去注释后，整个ARFF文件可以分为两个部分。         
第一部分给出了头信息（Head information），包括了对关系的声明和对属性的声明。      
第二部分给出了数据信息（Data information），即数据集中给出的数据。从“@data”标记开始，后面的就是数据信息了。          

关系声明     
关系名称在ARFF文件的第一个有效行来定义，格式为          
@relation <relation-name>        
<relation-name>是一个字符串。如果这个字符串包含空格，它必须加上引号（指英文标点的单引号或双引号）。        

属性声明       
属性声明用一列以“@attribute”开头的语句表示。        
数据集中的每一个属性都有它对应的“@attribute”语句，来定义它的属性名称和数据类型。         
这些声明语句的顺序很重要。首先它表明了该项属性在数据部分的位置。
例如，“humidity”是第三个被声明的属性，这说明数据部分那些被逗号分开的列中，第三列数据 85 90 86 96 ... 是相应的“humidity”值。           
其次， 最后一个声明的属性被称作 class 属性，在分类或回归任务中，它是默认的目标变量。          
属性声明的格式为          
@attribute <attribute-name> <datatype>             
其中<attribute-name>是必须以字母开头的字符串。和关系名称一样，如果这个字符串包含空格，它必须加上引号。        

WEKA支持的<datatype>有四种，分别是         
numeric-------------------------数值型         
<nominal-specification>-----分类（nominal）型          
string----------------------------字符串型       
date [<date-format>]--------日期和时间型           
其中<nominal-specification> 和<date-format> 将在下面说明。             
还可以使用两个类型“integer”和“real”，但是WEKA把它们都当作“numeric”看待。       
注意“integer”，“real”，“numeric”，“date”，“string”这些关键字是区分大小写的，而“relation”“attribute ”和“date”则不区分。       

数值属性      
数值型属性可以是整数或者实数，但WEKA把它们都当作实数看待。         

分类属性            
分类属性由<nominal-specification>列出一系列可能的类别名称并放在花括号中：        
{<nominal-name1>, <nominal-name2>, <nominal-name3>, ...} 。数据集中该属性的值只能是其中一种类别。       
例如如下的属性声明说明“outlook”属性有三种类别：“sunny”，“ overcast”和“rainy”。而数据集中每个实例对应的“outlook”值必是这三者之一。        
@attribute outlook {sunny, overcast, rainy}       
如果类别名称带有空格，仍需要将之放入引号中。         

字符串属性       
字符串属性中可以包含任意的文本。这种类型的属性在文本挖掘中非常有用。       
示例：             
@ATTRIBUTE LCC string           

日期和时间属性             
日期和时间属性统一用“date”类型表示，它的格式是           
@attribute <name> date [<date-format>]             
其中<name>是这个属性的名称，<date-format>是一个字符串，来规定该怎样解析和显示日期或时间的格式，默认的字符串是ISO-8601所给的日期时间组合格式“ yyyy-MM-dd T HH:mm:ss ”。           
数据信息部分表达日期的字符串必须符合声明中规定的格式要求（下文有例子）。           

数据信息         
数据信息中“@data”标记独占一行，剩下的是各个实例的数据。       

每个实例占一行。实例的各属性值用逗号“,”隔开。 如果某个属性的值是缺失值（ missing value ），用问号 “?” 表示，且这个问号不能省略。例如：       
@data          
sunny,85,85,FALSE,no           
?,78,90,?,yes           


字符串属性和分类属性的值是区分大小写的。若值中含有空格，必须被引号括起来。例如：  
@relation LCCvsLCSH  
@attribute LCC string  
@attribute LCSH string  
@data  
AG5, 'Encyclopedias and dictionaries.;Twentieth century.'  
AS262, 'Science -- Soviet Union -- History.'


日期属性的值必须与属性声明中给定的相一致。例如：         
@RELATION Timestamps  
@ATTRIBUTE timestamp DATE "yyyy-MM-dd HH:mm:ss"  
@DATA  
"2001-04-03 12:12:12"  
"2001-05-03 12:59:55"

稀疏数据           
有的时候数据集中含有大量的0值（比如购物篮分析），这个时候用稀疏格式的数据存贮更加省空间。  
稀疏格式是针对数据信息中某个实例的表示而言，不需要修改ARFF文件的其它部分。看如下的数据：        
@data      
0, X, 0, Y, "class A"          
0, 0, W, 0, "class B"        
用稀疏格式表达的话就是          
@data          
{1 X, 3 Y, 4 "class A"}         
{2 W, 4 "class B"}          
每个实例用花括号括起来。实例中每一个非0的属性值用<index> <空格> <value>表示。            
<index>是属性的序号，从0开始计；        
<value>是属性值。属性值之间仍用逗号隔开。       
这里每个实例的数值必须按属性的顺序来写，如   {1 X, 3 Y, 4 "class A"} ，不能写成 {3 Y, 1 X, 4 "class A"} 。
注意在稀疏格式中没有注明的属性值不是缺失值，而是 0 值。若要表示缺失值必须显式的用问号表示出来。    

Relational 型属性           
在WEKA 3.5版中增加了一种属性类型叫做Relational，有了这种类型我们可以像关系型数据库那样处理多个维度了。但是这种类型目前还不见广泛应用，暂不作介绍。         