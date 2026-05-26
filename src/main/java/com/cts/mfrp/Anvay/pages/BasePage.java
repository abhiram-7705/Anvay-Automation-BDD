package com.cts.mfrp.Anvay.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cts.mfrp.Anvay.utils.ConfigReader;

public class BasePage {
	
	protected WebDriver driver;
	protected WebDriverWait waits;
	
	public BasePage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
		
		Duration explicitWait = Duration.ofSeconds(ConfigReader.getInt("explicit.wait"));
		this.waits = new WebDriverWait(driver, explicitWait);
	}
}
