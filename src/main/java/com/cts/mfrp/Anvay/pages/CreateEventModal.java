package com.cts.mfrp.Anvay.pages;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class CreateEventModal extends BasePage {
	
	private JavascriptExecutor js = (JavascriptExecutor) driver;

    public CreateEventModal(WebDriver driver)
    {
        super(driver);
    }
    @FindBy(xpath = "//div[contains(@class,'modal-overlay')]//div[contains(@class,'modal-box')]")
    private WebElement modal;

    @FindBy(xpath = "//select[@formcontrolname='clubId']")
    private WebElement clubDropdown;

    @FindBy(xpath = "//input[@formcontrolname='eventName']")
    private WebElement eventTitleInput;

    @FindBy(xpath = "//select[@formcontrolname='category']")
    private WebElement categoryDropdown;

    @FindBy(xpath = "//input[@formcontrolname='contactNumber']")
    private WebElement contactNumberInput;

    @FindBy(xpath = "//input[@formcontrolname='startDate']")
    private WebElement startDateInput;

    @FindBy(xpath = "//input[@formcontrolname='location']")
    private WebElement locationInput;

    @FindBy(xpath = "//input[contains(@class,'slider-input')]")
    private WebElement maxParticipantsInput;

    @FindBy(xpath = "//input[@formcontrolname='hasWinners']")
    private WebElement allowWinnersCheckbox;

    @FindBy(xpath = "//div[contains(@class,'modal-box')]//button[contains(@class,'btn-primary')]")
    private WebElement submitButton;

    @FindBy(xpath = "//div[contains(@class,'modal-box')]//button[contains(@class,'btn-cancel')]")
    private WebElement cancelButton;

    @FindBy(xpath = "//button[@class='modal-close']")
    private WebElement closeButton;

    @FindBy(xpath = "//div[contains(@class,'form-group')][.//select[@formcontrolname='clubId']]//div[contains(@class,'field-error')]")
    private WebElement clubError;

    @FindBy(xpath = "//div[contains(@class,'form-group')][.//input[@formcontrolname='eventName']]//div[contains(@class,'field-error')]")
    private WebElement eventTitleError;

    @FindBy(xpath = "//div[contains(@class,'form-group')][.//select[@formcontrolname='category']]//div[contains(@class,'field-error')]")
    private WebElement categoryError;

    @FindBy(xpath = "//div[contains(@class,'form-group')][.//input[@formcontrolname='contactNumber']]//div[contains(@class,'field-error')]")
    private WebElement contactError;

    @FindBy(xpath = "//div[contains(@class,'form-group')][.//input[@formcontrolname='startDate']]//div[contains(@class,'field-error')]")
    private WebElement startDateError;

    @FindBy(xpath = "//div[contains(@class,'form-group')][.//input[@formcontrolname='location']]//div[contains(@class,'field-error')]")
    private WebElement locationError;

    @FindBy(xpath = "//div[contains(@class,'modal-msg') and contains(@class,'error')]")
    private WebElement modalErrorMsg;

    public boolean isModalDisplayed()
    {
        try
        {
            waits.until(ExpectedConditions.visibilityOf(modal));
            return modal.isDisplayed();
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public boolean isModalClosed()
    {
        try
        {
            waits.until(ExpectedConditions.invisibilityOf(modal));
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public void selectClub(String club)
    {
        new Select(clubDropdown).selectByVisibleText(club);
    }

    public void enterEventTitle(String title)
    {
        eventTitleInput.click();
        eventTitleInput.clear();
        eventTitleInput.sendKeys(title);
        eventTitleInput.sendKeys(Keys.TAB);
    }

    public void selectCategory(String category)
    {
        new Select(categoryDropdown).selectByVisibleText(category);
    }

    public void enterContactNumber(String contact)
    {
        contactNumberInput.click();
        contactNumberInput.clear();
        contactNumberInput.sendKeys(contact);
        contactNumberInput.sendKeys(Keys.TAB);
    }

    public void enterStartDate(String date)
    {
        String formattedDate = date;
        if (date.matches("\\d{2}-\\d{2}-\\d{4}"))
        {
            String[] parts = date.split("-");
            formattedDate = parts[2] + "-" + parts[1] + "-" + parts[0] + "T10:00";
        }
        js.executeScript(
                        "arguments[0].value = arguments[1];" +
                                "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                                "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                        startDateInput, formattedDate);
    }

    public void enterLocation(String location)
    {
        locationInput.click();
        locationInput.clear();
        locationInput.sendKeys(location);
        locationInput.sendKeys(Keys.TAB);
    }

    public void enterMaxParticipants(String max)
    {
        maxParticipantsInput.click();
        maxParticipantsInput.clear();
        maxParticipantsInput.sendKeys(max);
    }

    public void clickAllowWinners()
    {
        allowWinnersCheckbox.click();
    }

    public void clickSubmit()
    {
        waits.until(ExpectedConditions.elementToBeClickable(submitButton));
        submitButton.click();
        try
        {
            waits.until(ExpectedConditions.or(
                    ExpectedConditions.invisibilityOf(modal),
                    ExpectedConditions.visibilityOf(modalErrorMsg)));
        }
        catch (Exception e) {}
    }
    public void clickSubmitForValidation()
    {
    	
        js.executeScript(
                "var inputs = document.querySelectorAll('input, select');" +
                        "inputs.forEach(function(el) {" +
                        "  el.dispatchEvent(new Event('blur', { bubbles: true }));" +
                        "  el.dispatchEvent(new Event('touched', { bubbles: true }));" +
                        "});"
        );

        waits.until(ExpectedConditions.visibilityOf(submitButton));
        js.executeScript("arguments[0].click();", submitButton);

        try
        {
            waits.until(ExpectedConditions.or(
                    ExpectedConditions.invisibilityOf(modal),
                    ExpectedConditions.visibilityOfElementLocated(
                            org.openqa.selenium.By.xpath("//div[contains(@class,'field-error')]"))));
        }
        catch (Exception e) {}
    }


    public void clickCancel()
    {
        cancelButton.click();
        waits.until(ExpectedConditions.invisibilityOf(modal));
    }

    public void clickClose()
    {
        waits.until(ExpectedConditions.visibilityOf(closeButton));
        closeButton.click();
        waits.until(ExpectedConditions.invisibilityOf(modal));
    }

    public void fillAndSubmit(String club, String title, String category,
                              String contact, String startDate, String location)
    {
        selectClub(club);
        enterEventTitle(title);
        selectCategory(category);
        enterContactNumber(contact);
        enterStartDate(startDate);
        enterLocation(location);
        clickSubmit();
    }

    public boolean isClubErrorDisplayed()
    {
        try { return clubError.isDisplayed(); } catch (Exception e) { return false; }
    }

    public boolean isEventTitleErrorDisplayed()
    {
        try { return eventTitleError.isDisplayed(); } catch (Exception e) { return false; }
    }

    public boolean isCategoryErrorDisplayed()
    {
        try { return categoryError.isDisplayed(); } catch (Exception e) { return false; }
    }

    public boolean isContactErrorDisplayed()
    {
        try { return contactError.isDisplayed(); } catch (Exception e) { return false; }
    }

    public boolean isStartDateErrorDisplayed()
    {
        try { return startDateError.isDisplayed(); } catch (Exception e) { return false; }
    }

    public boolean isLocationErrorDisplayed()
    {
        try { return locationError.isDisplayed(); } catch (Exception e) { return false; }
    }
}
