package com.cts.mfrp.Anvay.hooks;

import java.io.File;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.cts.mfrp.Anvay.utils.ConfigReader;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;

public class Hooks {

    @BeforeAll
    public static void suiteSetup() {
        ConfigReader.load();
        System.out.println(ConfigReader.get("base.url"));
        new File("test-outputs/reports").mkdirs();
        new File("test-outputs/screenshots").mkdirs();
        DriverManager.initDriver();
    }

    @AfterAll
    public static void suiteTeardown() {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.localStorage.clear();");
                js.executeScript("window.sessionStorage.clear();");
                driver.manage().deleteAllCookies();
            } catch (Exception e) {
                System.out.println("no session to clear");
            }
        }
        DriverManager.quitDriver();
    }
}
