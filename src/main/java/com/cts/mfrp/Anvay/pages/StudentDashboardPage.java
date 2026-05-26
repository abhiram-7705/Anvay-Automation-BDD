package com.cts.mfrp.Anvay.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StudentDashboardPage extends BasePage {

	public StudentDashboardPage(WebDriver driver)
	{
		super(driver);
	}

	@FindBy(xpath = "//div[contains(@class,'page-header')]")
	private WebElement dashboardHeader;

	@FindBy(xpath = "//button[contains(@class,'nav-item') and contains(.,'Event Feed')]")
	private WebElement sidebarEventsLink;

	public boolean isPageLoaded()
	{
		waits.until(ExpectedConditions.visibilityOf(dashboardHeader));
		return dashboardHeader.isDisplayed();
	}

	public void navigateToEventFeed()
	{
		waits.until(ExpectedConditions.elementToBeClickable(sidebarEventsLink)).click();
		waits.until(ExpectedConditions.urlContains("events"));
	}


}