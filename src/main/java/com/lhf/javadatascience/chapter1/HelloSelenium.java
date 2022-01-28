package com.lhf.javadatascience.chapter1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @ClassName HelloSelenium
 * @Desc TODO
 * @Author diandian
 * @Date 2022/1/5 12:27
 **/
public class HelloSelenium {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        driver.get("https://google.com");

        driver.getTitle(); // => "Google"

        WebElement searchBox = driver.findElement(By.name("q"));
        WebElement searchButton = driver.findElement(By.name("btnK"));

        searchBox.sendKeys("Selenium");
        searchButton.click();

        driver.findElement(By.name("q")).getAttribute("value"); // => "Selenium"

        driver.quit();
    }
}