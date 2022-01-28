package com.lhf.javadatascience.chapter1;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @ClassName addCookie
 * @Desc https://www.selenium.dev/documentation/webdriver/browser/cookies/
 * @Author diandian
 * @Date 2022/1/5 14:15
 **/
public class addCookie {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("http://www.example.com");

            // Adds the cookie into current browser context
            driver.manage().addCookie(new Cookie("key", "value"));
        } finally {
            driver.quit();
        }
    }
}