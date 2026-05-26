package com.cts.mfrp.Anvay.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InstitutionDashboardPage extends BasePage{

	public InstitutionDashboardPage(WebDriver driver) 
	{
		super(driver);
	}
	
	@FindBy(xpath = "//nav[@class='sb-nav']/button[2]")
	private WebElement sidebarAllEventsLink;
	
	public void navigateToAllEvents()
	{
		waits.until(ExpectedConditions.elementToBeClickable(sidebarAllEventsLink)).click();
		waits.until(ExpectedConditions.urlContains("events"));
	}

}
