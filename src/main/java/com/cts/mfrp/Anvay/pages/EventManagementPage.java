package com.cts.mfrp.Anvay.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class EventManagementPage extends BasePage{

	public EventManagementPage(WebDriver driver) 
	{
		super(driver);
	}
	
	@FindBy(xpath = "//button[@class='btn-primary']")
	private WebElement createEventButton;
	
	@FindBy(xpath = "//div[@class='table-responsive']//table/tbody/tr[1]")
	private WebElement firstUpcomingRow;
	
	@FindBy(xpath = "//div[@class='modal-box modal-wide']")
	private WebElement eventModal;
	
	public void clickCreateEvent()
	{
		createEventButton.click();
		waits.until(ExpectedConditions.visibilityOf(eventModal));
	}
	
	public boolean isFirstUpcomingRowDisplayed()
	{
		try
		{
			return firstUpcomingRow.isDisplayed();
		}
		catch (Exception e)
		{
			return false;
		}
	}

}
