package com.cts.mfrp.Anvay.stepdefinitions;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import com.cts.mfrp.Anvay.hooks.DriverManager;
import com.cts.mfrp.Anvay.pages.AdminDashboardPage;
import com.cts.mfrp.Anvay.pages.CollegeManagementPage;
import com.cts.mfrp.Anvay.utils.ExcelUtils;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AdminSteps {

    private String currentSearchTerm;
    private String currentExpectedResult;

    private WebDriver driver() {
        return DriverManager.getDriver();
    }

    @Given("the admin is logged in")
    public void theAdminIsLoggedIn() {
    }

    @And("the admin navigates to College Management")
    public void theAdminNavigatesToCollegeManagement() {
        new AdminDashboardPage(driver()).navigateToCollegeManagement();
    }

    @And("the search box is loaded")
    public void theSearchBoxIsLoaded() {
        assertTrue(new CollegeManagementPage(driver()).isSearchLoaded(),
                "College management search box is not loaded");
    }

    @When("the admin searches using Excel row {int}")
    public void theAdminSearchesUsingExcelRow(int rowNum) {
        try {
            Object[][] data = ExcelUtils.getTestData("AdminData.xlsx", "InstitutionSearch");
            Object[] row = data[rowNum - 1];
            currentSearchTerm     = (String) row[1];
            currentExpectedResult = (String) row[2];
            new CollegeManagementPage(driver()).searchFor(currentSearchTerm);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read Excel row " + rowNum + ": " + e.getMessage(), e);
        }
    }

    @Then("the search result from Excel row {int} should match")
    public void theSearchResultFromExcelRowShouldMatch(int rowNum) {
        SoftAssert soft = new SoftAssert();
        CollegeManagementPage page = new CollegeManagementPage(driver());
        if ("found".equalsIgnoreCase(currentExpectedResult)) {
            soft.assertFalse(page.isNoResultsMsgDisplayed(),
                    "Row " + rowNum + ": Expected results for [" + currentSearchTerm + "] but got no results");
        } else {
            soft.assertTrue(page.isNoResultsMsgDisplayed(),
                    "Row " + rowNum + ": Expected no results for [" + currentSearchTerm + "] but results were found");
        }
        soft.assertAll();
    }

    @When("the admin resets the search")
    public void theAdminResetsTheSearch() {
        new CollegeManagementPage(driver()).resetSearch();
    }

    @And("the admin clicks Approve on the first pending institution")
    public void theAdminClicksApproveOnTheFirstPendingInstitution() {
        new CollegeManagementPage(driver()).clickFirstPendingApprove();
    }

    @Then("the approval modal is displayed")
    public void theApprovalModalIsDisplayed() {
        assertTrue(new CollegeManagementPage(driver()).isApproveModalDisplayed(),
                "Approval modal was not displayed");
    }

    @And("the approval checkbox is unchecked by default")
    public void theApprovalCheckboxIsUncheckedByDefault() {
        assertFalse(new CollegeManagementPage(driver()).isApproveCheckboxSelected(),
                "Approval checkbox should be unchecked by default");
    }

    @And("the Approve button is disabled before confirming")
    public void theApproveButtonIsDisabledBeforeConfirming() {
        assertFalse(new CollegeManagementPage(driver()).isApproveButtonEnabled(),
                "Approve button should be disabled before checkbox is checked");
    }

    @When("the admin checks the approval checkbox")
    public void theAdminChecksTheApprovalCheckbox() {
        new CollegeManagementPage(driver()).clickApproveCheckbox();
    }

    @Then("the Approve button becomes enabled")
    public void theApproveButtonBecomesEnabled() {
        assertTrue(new CollegeManagementPage(driver()).isApproveButtonEnabled(),
                "Approve button should be enabled after checking the checkbox");
    }

    @When("the admin clicks the Approve Institution button")
    public void theAdminClicksTheApproveInstitutionButton() {
        new CollegeManagementPage(driver()).clickApproveInstitutionButton();
    }

    @Then("a success banner is displayed")
    public void aSuccessBannerIsDisplayed() {
        assertTrue(new CollegeManagementPage(driver()).isSuccessBannerDisplayed(),
                "Success banner was not displayed after approval");
    }

    @And("at least one institution row has an active status")
    public void atLeastOneInstitutionRowHasAnActiveStatus() {
        assertTrue(new CollegeManagementPage(driver()).isAnyRowApproved(),
                "No row with active status found after approval");
    }

    @And("the active row has a Deactivate button")
    public void theActiveRowHasADeactivateButton() {
        assertTrue(new CollegeManagementPage(driver()).isDeactivatePresentForActiveRow(),
                "Deactivate button not present for the active row");
    }
}