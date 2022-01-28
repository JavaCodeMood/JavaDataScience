package com.lhf.javadatascience.chapter1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @ClassName SeleniumWebdriverGetUrlData
 * @Desc 使用Selenium Webdriver从网站提取Web数据
 * 文档：https://www.selenium.dev/documentation/webdriver/getting_started/first_script/
 * @Author diandian
 * @Date 2022/1/5 11:49
 **/
public class SeleniumWebdriverGetUrlData {

    public String getUrlDataBySeleniumWebdriver(String url){
        //WebDriver driver = new FirefoxDriver();  //创建一个Firefox web驱动器
        //System.setProperty("webdriver.firefox.marionette","C:\\Program Files (x86)\\Mozilla Firefox\\geckodriver.exe");

        //https://www.jianshu.com/p/3a91d120c482
        //System.setProperty("webdriver.ie.driver", "D:\\SETUPS\\IEDriverServer_x64_2.53.1\\IEDriverServer.exe"); // 必须加入
        //WebDriver driver = new InternetExplorerDriver();

        //https://blog.csdn.net/qiyueqinglian/article/details/47419209
        //http://chromedriver.storage.googleapis.com/index.html
        WebDriver driver = new ChromeDriver();  //创建一个Chrom web驱动器
        //ChromeDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", "D:/Program Files/google/chromedriver.exe");
        //driver.get("http://www.baidu.com");
        driver.get(url);  //传递url,调用get方法
        driver.getTitle(); // => "Google"
        driver.close();


        //WebElement webElement = driver.findElement(By.tagName("body"));
        //调用findElement()方法找到特定的元素，并返回一个WebElement对象
        WebElement webElement = driver.findElement(By.xpath("//*[@id='content']"));
        String resultText = webElement.getText();;  //获取Web页面中的文本信息
        return resultText;
    }

    public static void main(String[] args) {
        SeleniumWebdriverGetUrlData web = new SeleniumWebdriverGetUrlData();
        String  webData = web.getUrlDataBySeleniumWebdriver("https://www.imooc.com/course/list?c=java");
        System.out.println("webData = " + webData);
    }


}