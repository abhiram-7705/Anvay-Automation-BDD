package com.cts.mfrp.Anvay.hooks;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;

public class LoginHooks {

    @After("@login")
    public void clearSession() {
        try {
            WebDriver driver = DriverManager.getDriver();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.localStorage.clear();");
            js.executeScript("window.sessionStorage.clear();");
            driver.manage().deleteAllCookies();
        } catch (Exception e) {
            System.out.println("no session to clear");
        }
    }
}