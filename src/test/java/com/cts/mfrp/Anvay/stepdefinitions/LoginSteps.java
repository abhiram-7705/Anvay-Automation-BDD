package com.cts.mfrp.Anvay.stepdefinitions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import com.cts.mfrp.Anvay.hooks.DriverManager;
import com.cts.mfrp.Anvay.pages.LandingPage;
import com.cts.mfrp.Anvay.pages.LoginPage;
import com.cts.mfrp.Anvay.utils.ConfigReader;
import com.cts.mfrp.Anvay.utils.ExcelUtils;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

    private String currentEmail;
    private String currentPassword;
    private String currentRole;
    private String currentExpectedEmailError;
    private String currentExpectedPasswordError;
    private String currentExpectedLoginError;

    private WebDriver driver() {
        return DriverManager.getDriver();
    }

    private LoginPage loginPage() {
        return new LoginPage(driver());
    }

    private LandingPage landingPage() {
        return new LandingPage(driver());
    }

    @Given("the application landing page is open")
    public void theApplicationLandingPageIsOpen() {
        driver().get(ConfigReader.get("base.url"));
        assertTrue(landingPage().isPageLoaded(), "Landing page did not load");
    }

    @When("the user navigates to the Login page")
    public void theUserNavigatesToTheLoginPage() {
        landingPage().loginNavigation1();
    }

    @Then("the login page header is displayed")
    public void theLoginPageHeaderIsDisplayed() {
        assertTrue(loginPage().isPageLoaded(), "Login page header is not displayed");
    }

    @And("the email input is visible and enabled")
    public void theEmailInputIsVisibleAndEnabled() {
        assertTrue(loginPage().isEmailInputLoaded(), "Email input is not ready");
    }

    @And("the password input is visible and enabled")
    public void thePasswordInputIsVisibleAndEnabled() {
        assertTrue(loginPage().isPasswordInputLoaded(), "Password input is not ready");
    }

    @And("the submit button is displayed")
    public void theSubmitButtonIsDisplayed() {
        assertTrue(loginPage().isSubmitButtonDisplayed(), "Submit button is not displayed");
    }

    @And("the forgot password link is visible and enabled")
    public void theForgotPasswordLinkIsVisibleAndEnabled() {
        assertTrue(loginPage().isForgotLinkLoaded(), "Forgot password link is not ready");
    }

    @And("the sign up link is visible and enabled")
    public void theSignUpLinkIsVisibleAndEnabled() {
        assertTrue(loginPage().isSignUpLinkLoaded(), "Sign up link is not ready");
    }

    @When("the user logs in using valid login Excel row {int}")
    public void theUserLogsInUsingValidLoginExcelRow(int rowNum) {
        try {
            Object[][] data = ExcelUtils.getTestData("LoginData.xlsx", "ValidLogin");
            Object[] row = data[rowNum - 1];
            currentEmail    = (String) row[2];
            currentPassword = (String) row[3];
            currentRole     = (String) row[1];
            loginPage().login(currentEmail, currentPassword);
            loginPage().waitForPageTransition(ConfigReader.get("base.url") + "login");
        } catch (Exception e) {
            throw new RuntimeException("Failed to read Excel row " + rowNum + ": " + e.getMessage(), e);
        }
    }

    @Then("the browser should redirect to the correct dashboard for row {int}")
    public void theBrowserShouldRedirectToTheCorrectDashboardForRow(int rowNum) {
        String baseURL = ConfigReader.get("base.url");
        String expectedUrl;
        switch (currentRole.toLowerCase()) {
            case "super admin":
                expectedUrl = baseURL + "dashboard/super-admin"; break;
            case "institution":
                expectedUrl = baseURL + "dashboard/institution"; break;
            case "leader":
                expectedUrl = baseURL + "dashboard/leader"; break;
            default:
                expectedUrl = baseURL + "dashboard/student"; break;
        }
        assertEquals(driver().getCurrentUrl(), expectedUrl,
                "Row " + rowNum + ": Wrong dashboard URL for role: " + currentRole);
    }

    @When("the user enters invalid credentials from Excel row {int}")
    public void theUserEntersInvalidCredentialsFromExcelRow(int rowNum) {
        try {
            Object[][] data = ExcelUtils.getTestData("LoginData.xlsx", "InvalidLogin");
            Object[] row = data[rowNum - 1];
            currentEmail                 = (String) row[1];
            currentPassword              = (String) row[2];
            currentExpectedEmailError    = (String) row[3];
            currentExpectedPasswordError = (String) row[4];
            currentExpectedLoginError    = (String) row[5];
            loginPage().enterEmail(currentEmail);
            loginPage().enterPassword(currentPassword);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read Excel row " + rowNum + ": " + e.getMessage(), e);
        }
    }

    @Then("the validation errors for row {int} should match")
    public void theValidationErrorsForRowShouldMatch(int rowNum) {
        SoftAssert soft = new SoftAssert();

        if (currentExpectedEmailError != null && !currentExpectedEmailError.isEmpty()) {
            soft.assertEquals(loginPage().emailErrorMessage(), currentExpectedEmailError,
                    "Row " + rowNum + ": Email error mismatch");
            soft.assertFalse(loginPage().isSubmitButtonEnabled(),
                    "Row " + rowNum + ": Submit button should be disabled");
        }

        if (currentExpectedPasswordError != null && !currentExpectedPasswordError.isEmpty()) {
            soft.assertEquals(loginPage().passwordErrorMessage(), currentExpectedPasswordError,
                    "Row " + rowNum + ": Password error mismatch");
            soft.assertFalse(loginPage().isSubmitButtonEnabled(),
                    "Row " + rowNum + ": Submit button should be disabled");
        }

        if (currentExpectedLoginError != null && !currentExpectedLoginError.isEmpty()) {
            loginPage().login(currentEmail, currentPassword);
            soft.assertEquals(loginPage().loginErrorMessage(), currentExpectedLoginError,
                    "Row " + rowNum + ": Login error mismatch");
        }

        soft.assertAll();
    }

    @When("the user clears the email field")
    public void theUserClearsTheEmailField() {
        loginPage().enterEmail("");
    }

    @Then("the email validation error is shown")
    public void theEmailValidationErrorIsShown() {
        assertTrue(loginPage().isEmailErrorDisplayed(), "Email error should be shown");
    }

    @When("the user clears the password field")
    public void theUserClearsThePasswordField() {
        loginPage().enterPassword("");
    }

    @Then("the password validation error is shown")
    public void thePasswordValidationErrorIsShown() {
        assertTrue(loginPage().isPasswordErrorDisplayed(), "Password error should be shown");
    }

    @When("the user types a valid email from config")
    public void theUserTypesAValidEmailFromConfig() {
        loginPage().enterEmail(ConfigReader.get("validation.test.email"));
    }

    @And("the user types a valid password from config")
    public void theUserTypesAValidPasswordFromConfig() {
        loginPage().enterPassword(ConfigReader.get("validation.test.password"));
    }

    @Then("no email validation error is shown")
    public void noEmailValidationErrorIsShown() {
        assertFalse(loginPage().isEmailErrorDisplayed(), "Email error should NOT be shown");
    }

    @And("no password validation error is shown")
    public void noPasswordValidationErrorIsShown() {
        assertFalse(loginPage().isPasswordErrorDisplayed(), "Password error should NOT be shown");
    }
}