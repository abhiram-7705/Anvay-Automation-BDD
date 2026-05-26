package com.cts.mfrp.Anvay.hooks;

import org.openqa.selenium.WebDriver;

import com.cts.mfrp.Anvay.pages.LandingPage;
import com.cts.mfrp.Anvay.pages.LoginPage;
import com.cts.mfrp.Anvay.utils.ConfigReader;

import io.cucumber.java.Before;

public class InstitutionHooks {

    private static boolean loggedIn = false;

    @Before("@institution")
    public void loginOnce() {
        if (!loggedIn) {
            WebDriver driver = DriverManager.getDriver();
            driver.get(ConfigReader.get("base.url"));
            new LandingPage(driver).loginNavigation1();
            new LoginPage(driver).login(
                    ConfigReader.get("institute.username"),
                    ConfigReader.get("institute.password"));
            loggedIn = true;
        }
    }
}