package com.cts.mfrp.Anvay.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LandingPage extends BasePage {
	
	public LandingPage(WebDriver driver)
	{
		super(driver);
	}
	
	@FindBy(xpath = "//div[@class='nav-links']/a[1]")
	private WebElement signInLink1;
	
	@FindBy(xpath = "//div[@class='nav-links']/a[2]")
	private WebElement getStartedLink;
	
	
	public boolean isPageLoaded() 
	{
        try 
        {
            waits.until(ExpectedConditions.visibilityOf(signInLink1));
            return true;
        } 
        catch (Exception e) 
        {
            return false;
        }
    }
	
	public boolean isSignInLink1Ready() 
	{
        return signInLink1.isDisplayed() && signInLink1.isEnabled();
    }

    public boolean isGetStartedLinkReady() 
    {
        return getStartedLink.isDisplayed() && getStartedLink.isEnabled();
    }

	public void loginNavigation1()
	{
		signInLink1.click();
		waits.until(ExpectedConditions.urlContains("login"));
	}

	public void registrationNavigation()
	{
		getStartedLink.click();
		waits.until(ExpectedConditions.urlContains("register"));
	}
}
