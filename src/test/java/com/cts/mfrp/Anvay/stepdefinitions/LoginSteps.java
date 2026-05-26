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

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

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

    @When("the user logs in with email {string} and password {string}")
    public void theUserLogsInWithEmailAndPassword(String email, String password) {
        loginPage().login(email.trim(), password);
        loginPage().waitForPageTransition(ConfigReader.get("base.url") + "login");
    }

    @Then("the browser should redirect to the {string} dashboard")
    public void theBrowserShouldRedirectToTheDashboard(String role) {
        String baseURL = ConfigReader.get("base.url");
        String expectedUrl;
        switch (role.toLowerCase()) {
            case "super admin":
                expectedUrl = baseURL + "dashboard/super-admin"; break;
            case "institution":
                expectedUrl = baseURL + "dashboard/institution"; break;
            case "leader":
                expectedUrl = baseURL + "dashboard/leader"; break;
            default:
                expectedUrl = baseURL + "dashboard/student"; break;
        }
        assertEquals(driver().getCurrentUrl(), expectedUrl, "Wrong dashboard URL for role: " + role);
    }

    @When("the user enters email {string} and password {string}")
    public void theUserEntersEmailAndPassword(String email, String password) {
        loginPage().enterEmail(email);
        loginPage().enterPassword(password);
    }

    @Then("the email validation error should be {string}")
    public void theEmailValidationErrorShouldBe(String expectedEmailError) {
        if (!expectedEmailError.isEmpty()) {
            SoftAssert soft = new SoftAssert();
            soft.assertEquals(loginPage().emailErrorMessage(), expectedEmailError, "Email error mismatch");
            soft.assertFalse(loginPage().isSubmitButtonEnabled(), "Submit button should be disabled");
            soft.assertAll();
        }
    }

    @And("the password validation error should be {string}")
    public void thePasswordValidationErrorShouldBe(String expectedPasswordError) {
        if (!expectedPasswordError.isEmpty()) {
            SoftAssert soft = new SoftAssert();
            soft.assertEquals(loginPage().passwordErrorMessage(), expectedPasswordError, "Password error mismatch");
            soft.assertFalse(loginPage().isSubmitButtonEnabled(), "Submit button should be disabled");
            soft.assertAll();
        }
    }

    @And("the login error should be {string}")
    public void theLoginErrorShouldBe(String expectedLoginError) {
        if (!expectedLoginError.isEmpty()) {
            loginPage().login(
                    loginPage().emailErrorMessage().isEmpty() ? "wrong@example.com" : "",
                    "wrongpwd");
            SoftAssert soft = new SoftAssert();
            soft.assertEquals(loginPage().loginErrorMessage(), expectedLoginError, "Login error mismatch");
            soft.assertAll();
        }
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