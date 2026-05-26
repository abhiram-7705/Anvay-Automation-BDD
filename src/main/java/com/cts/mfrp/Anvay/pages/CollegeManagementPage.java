package com.cts.mfrp.Anvay.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CollegeManagementPage extends BasePage {

	public CollegeManagementPage(WebDriver driver) 
	{
		super(driver);
	}
	
	@FindBy(xpath = "//table[@class='data-table']/tbody/tr[1]")
	private WebElement firstTableRow;

	@FindBy(xpath = "//div[@class='search-box']/input")
	private WebElement searchInput;

	@FindBy(xpath = "//div[@class='toolbar']/button")
	private WebElement searchButton;
	
	@FindBy(xpath = "//table[@class='data-table']/tbody//td[@class='empty-cell']")
	private WebElement noResultsMsg;

	@FindBy(xpath = "//div[@class='modal-box']")
	private WebElement approveModal;

	@FindBy(xpath = "//div[@class='modal-box']//label[contains(@class,'verify-check')]")
	private WebElement approveCheckbox;

	@FindBy(xpath = "//div[@class='modal-box']//button[@class='modal-btn-approve']")
	private WebElement approveInstitutionButton;
	
	private By rowsLocator = By.xpath("//table[contains(@class,'data-table')]//tbody/tr");
	private By statusLocator = By.xpath(".//span[contains(@class,'status-badge')]");
	private By approveBtnLocator = By.xpath(".//button[contains(@class,'btn-success')]");
	private By deactivateBtnLocator = By.xpath(".//button[contains(@class,'btn-danger')]");
	
	@FindBy(xpath = "//div[@class='msg-bar success']")
	private WebElement successBanner;
	
	public boolean isSearchLoaded()
	{
		return searchInput.isDisplayed() && searchInput.isEnabled();
	}

	public void enterSearchTerm(String term)
	{
		searchInput.click();
		searchInput.clear();
		searchInput.sendKeys(term);
	}

	public void clickSearch()
	{
		searchButton.click();
		waits.until(ExpectedConditions.visibilityOf(firstTableRow));
	}

	public void searchFor(String term)
	{
		enterSearchTerm(term);
		clickSearch();
	}
	
	public boolean isNoResultsMsgDisplayed()
	{
		try
		{
			return noResultsMsg.isDisplayed();
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	public void resetSearch()
	{
		searchFor("ABC");
		waits.until(ExpectedConditions.visibilityOf(firstTableRow));
	}
	
	private List<WebElement> getRows()
	{
		waits.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(rowsLocator));
		waits.until(driver -> {
			List<WebElement> rows = driver.findElements(rowsLocator);
			return rows.stream().allMatch(row -> row.findElements(statusLocator).size() > 0);
		});
	    return driver.findElements(rowsLocator);
	}
	
	public void clickFirstPendingApprove()
	{
		waits.until(ExpectedConditions.visibilityOf(firstTableRow));
		waits.until(ExpectedConditions.presenceOfAllElementsLocatedBy(rowsLocator));
	    for (WebElement row : getRows())
	    {
	    	List<WebElement> badges = row.findElements(statusLocator);
	    	if (badges.isEmpty())
	    	{
	    		continue;
	    	}

	        String status = badges.get(0).getText().trim();

	        if (status.equalsIgnoreCase("pending"))
	        {
	            List<WebElement> approveBtns = row.findElements(approveBtnLocator);
	            if (approveBtns.isEmpty())
	            {
	            	continue;
	            }

	            waits.until(ExpectedConditions.elementToBeClickable(approveBtns.get(0)));
	            approveBtns.get(0).click();

	            waits.until(ExpectedConditions.visibilityOf(approveModal));
	            return;
	        }
	    }

	    throw new RuntimeException("No pending rows available");
	}
	
	public boolean isApproveModalDisplayed()
	{
		try
		{
			waits.until(ExpectedConditions.visibilityOf(approveModal));
			return approveModal.isDisplayed();
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	public boolean isApproveCheckboxSelected()
	{
	    WebElement checkboxInput = approveCheckbox.findElement(By.xpath(".//input"));
	    return checkboxInput.isSelected();
	}

	public void clickApproveCheckbox()
	{
		waits.until(ExpectedConditions.visibilityOf(approveModal));
		approveCheckbox.click();
	}

	public boolean isApproveButtonEnabled()
	{
		waits.until(ExpectedConditions.visibilityOf(approveModal));
		return approveInstitutionButton.isEnabled();
	}
	
	public void clickApproveInstitutionButton()
	{
		waits.until(ExpectedConditions.elementToBeClickable(approveInstitutionButton));
		approveInstitutionButton.click();
	}

	public boolean isSuccessBannerDisplayed()
	{
		try
		{
			waits.until(ExpectedConditions.visibilityOf(successBanner));
			return successBanner.isDisplayed();
		}
		catch (Exception e)
		{
			return false;
		}
	}

	public boolean isAnyRowApproved()
	{
	    for (WebElement row : getRows())
	    {
	    	List<WebElement> badges = row.findElements(statusLocator);
	    	if (badges.isEmpty()) continue;
	        if (badges.get(0).getText().trim().equalsIgnoreCase("active"))
	        {
	            return true;
	        }
	    }
	    return false;
	}

	public boolean isDeactivatePresentForActiveRow()
	{
	    for (WebElement row : getRows())
	    {
	    	List<WebElement> badges = row.findElements(statusLocator);
	    	if (badges.isEmpty()) continue;
	        if (badges.get(0).getText().trim().equalsIgnoreCase("active"))
	        {
	            return row.findElements(deactivateBtnLocator).size() > 0;
	        }
	    }
	    return false;
	}

	public boolean isStatusPresent(String expectedStatus)
	{
	    for (WebElement row : getRows())
	    {
	    	List<WebElement> badges = row.findElements(statusLocator);
	    	if (badges.isEmpty()) continue;
	        if (badges.get(0).getText().trim().equalsIgnoreCase(expectedStatus))
	        {
	            return true;
	        }
	    }
	    return false;
	}
}