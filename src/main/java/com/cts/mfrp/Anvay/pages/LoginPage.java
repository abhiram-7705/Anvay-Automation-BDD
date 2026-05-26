package com.cts.mfrp.Anvay.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver)
	{
		super(driver);
	}
	
	@FindBy(xpath = "//div[@class='login-header']")
	private WebElement loginHeader;
	
	@FindBy(xpath = "//input[@formcontrolname='email']")
	private WebElement emailInput;
	
	@FindBy(xpath = "//input[@formcontrolname='password']")
	private WebElement passwordInput;
	
	@FindBy(xpath = "//button[@class='btn-login']")
	private WebElement submitButton;
	
	@FindBy(xpath = "//button[@class='forgot-link']")
	private WebElement forgotPassword;
	
	@FindBy(xpath = "//a[@class='link']")
	private WebElement signUpLink;
	
	@FindBy(xpath = "//form/div[1]/div[2]")
	private WebElement emailError;
	
	@FindBy(xpath = "//form/div[2]/div[2]")
	private WebElement passwordError;
	
	@FindBy(xpath = "//div[@class='error-alert']")
	private WebElement loginError;
	
	public boolean isPageLoaded()
	{
		waits.until(ExpectedConditions.visibilityOf(loginHeader));
		return loginHeader.isDisplayed();
	}
	
	public boolean isEmailInputLoaded()
	{
		return emailInput.isDisplayed() && emailInput.isEnabled();
	}
	public boolean isPasswordInputLoaded()
	{
		return passwordInput.isDisplayed() && passwordInput.isEnabled();
	}
	public boolean isSubmitButtonDisplayed()
	{
		return submitButton.isDisplayed();
	}
	public boolean isSubmitButtonEnabled()
	{
		return submitButton.isEnabled();
	}
	public boolean isForgotLinkLoaded()
	{
		return forgotPassword.isDisplayed() && forgotPassword.isEnabled();
	}
	public boolean isSignUpLinkLoaded()
	{
		return signUpLink.isDisplayed() && signUpLink.isEnabled();
	}
	
	public void enterEmail(String email)
	{
		emailInput.click();
		emailInput.clear();
		emailInput.sendKeys(email);
		emailInput.sendKeys(Keys.TAB);
	}
	public void enterPassword(String password)
	{
		passwordInput.click();
		passwordInput.clear();
		passwordInput.sendKeys(password);
		passwordInput.sendKeys(Keys.TAB);
	}
	public void login(String email, String password)
	{
		enterEmail(email);
		enterPassword(password);
		waits.until(ExpectedConditions.elementToBeClickable(submitButton));
		submitButton.click();
	}

	public void waitForPageTransition(String oldUrl) 
	{
	    waits.until(driver -> !driver.getCurrentUrl().equals(oldUrl));
	}

	
    public boolean isEmailErrorDisplayed()
    {
        try
        {
        	return emailError.isDisplayed();
        }
        catch (Exception e)
        {
            return false;
        }
    }
    public boolean isPasswordErrorDisplayed()
    {
        try
        {
            return passwordError.isDisplayed();
        }
        catch (Exception e)
        {
            return false;
        }
    }
	public String emailErrorMessage() 
	{
	    try 
	    {
	        return emailError.getText();
	    } 
	    catch (Exception e) 
	    {
	        return "error message not displayed";
	    }
	}

	public String passwordErrorMessage() 
	{
	    try 
	    {
	        return passwordError.getText();
	    } 
	    catch (Exception e) 
	    {
	        return "error message not displayed";
	    }
	}

	public String loginErrorMessage() 
	{
	    try 
	    {
	        return loginError.getText();
	    } 
	    catch (Exception e) 
	    {
	        return "error message not displayed";
	    }
	}
	
	public void navigateToSignup()
	{
		signUpLink.click();
		waits.until(ExpectedConditions.urlContains("register"));
	}
}
