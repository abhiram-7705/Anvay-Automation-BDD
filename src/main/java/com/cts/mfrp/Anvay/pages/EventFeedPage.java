package com.cts.mfrp.Anvay.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EventFeedPage extends BasePage {

	public EventFeedPage(WebDriver driver)
	{
		super(driver);
	}
	private JavascriptExecutor js = (JavascriptExecutor) driver;

	@FindBy(xpath = "//div[contains(@class,'page-header')]")
	private WebElement pageHeader;

	@FindBy(xpath = "(//div[contains(@class,'ev-section')])[1]")
	private WebElement upcomingSection;

	@FindBy(xpath = "(//div[contains(@class,'event-card')])[1]")
	private WebElement firstEventCard;

	@FindBy(xpath = "//button[contains(@class,'ev-tab') and contains(.,'Event Feed')]")
	private WebElement eventFeedTab;

	@FindBy(xpath = "//button[contains(@class,'ev-tab') and contains(.,'My Registrations')]")
	private WebElement myRegistrationsTab;

	@FindBy(xpath = "(//button[contains(@class,'btn-register') and not(@disabled)])[1]")
	private WebElement registerButton;

	@FindBy(xpath = "//div[contains(@class,'reg-confirm-box')]")
	private WebElement confirmationModal;

	@FindBy(xpath = "//div[contains(@class,'reg-event-title')]")
	private WebElement confirmationModalEventName;

	@FindBy(xpath = "//div[contains(@class,'reg-confirm-box')]//button[contains(@class,'btn-submit-modal')]")
	private WebElement confirmRegistrationButton;

	public boolean isPageLoaded()
	{
		waits.until(ExpectedConditions.visibilityOf(pageHeader));
		return pageHeader.isDisplayed();
	}

	public boolean isUpcomingSectionDisplayed()
	{
		try
		{
			return upcomingSection.isDisplayed();
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public boolean isFirstEventCardDisplayed()
	{
		try
		{
			return firstEventCard.isDisplayed();
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public void clickMyRegistrationsTab()
	{
		waits.until(ExpectedConditions.elementToBeClickable(myRegistrationsTab));
		js.executeScript("arguments[0].scrollIntoView({block: 'center'});", myRegistrationsTab);
		js.executeScript("arguments[0].click();", myRegistrationsTab);
		waits.until(ExpectedConditions.attributeContains(myRegistrationsTab, "class", "active"));
		waits.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'ev-section')]")));
		waits.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"//div[contains(@class,'ev-tabs')]" + "/following-sibling::div[contains(@class,'events-grid')]")));
	}

	public void clickEventFeedTab()
	{
		waits.until(ExpectedConditions.elementToBeClickable(eventFeedTab)).click();
		waits.until(ExpectedConditions.visibilityOf(upcomingSection));
	}

	public boolean hasRegisterButton()
	{
		try
		{
			new WebDriverWait(driver, Duration.ofSeconds(5))
				.until(ExpectedConditions.elementToBeClickable(
					By.xpath("(//button[contains(@class,'btn-register') and not(@disabled)])[1]")));
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public String clickRegisterAndGetEventName()
	{
		waits.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
		waits.until(ExpectedConditions.visibilityOf(confirmationModal));
		String eventName = confirmationModalEventName.getText().trim();
		waits.until(ExpectedConditions.elementToBeClickable(confirmRegistrationButton)).click();
		waits.until(ExpectedConditions.invisibilityOf(confirmationModal));
		return eventName;
	}

	public boolean isEventDisplayedInTab(String eventName)
	{
		try
		{
			waits.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[contains(@class,'ev-tabs')]" +
						 "/following-sibling::div[contains(@class,'events-grid')]" +
						 "//h4[contains(@class,'ec-title') and normalize-space(.)='" + eventName + "']")));
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

}