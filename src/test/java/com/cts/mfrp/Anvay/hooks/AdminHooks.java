package com.cts.mfrp.Anvay.hooks;

import org.openqa.selenium.WebDriver;

import com.cts.mfrp.Anvay.pages.LandingPage;
import com.cts.mfrp.Anvay.pages.LoginPage;
import com.cts.mfrp.Anvay.utils.ConfigReader;

import io.cucumber.java.Before;

public class AdminHooks {

    private static boolean loggedIn = false;

    @Before("@admin")
    public void loginOnce() {
        if (!loggedIn) {
            WebDriver driver = DriverManager.getDriver();
            driver.get(ConfigReader.get("base.url"));
            new LandingPage(driver).loginNavigation1();
            new LoginPage(driver).login(
                    ConfigReader.get("admin.username"),
                    ConfigReader.get("admin.password"));
            loggedIn = true;
        }
    }
}