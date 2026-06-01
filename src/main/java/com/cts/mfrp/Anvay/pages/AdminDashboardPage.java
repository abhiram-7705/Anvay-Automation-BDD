package com.cts.mfrp.Anvay.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AdminDashboardPage extends BasePage {

	public AdminDashboardPage(WebDriver driver) 
	{
		super(driver);
	}
	
	@FindBy(xpath = "//nav[@class='sb-nav']/button[2]")
	private WebElement sidebarCollegeMgmtLink;
		
	public void navigateToCollegeManagement()
	{
		waits.until(ExpectedConditions.elementToBeClickable(sidebarCollegeMgmtLink)).click();
		waits.until(ExpectedConditions.urlContains("colleges"));
		waits.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='search-box']/input")));
		waits.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='data-table']/tbody/tr[1]")));
	}
	
	
	

}
