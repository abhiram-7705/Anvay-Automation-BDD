package com.cts.mfrp.Anvay.stepdefinitions;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;

import com.cts.mfrp.Anvay.hooks.DriverManager;
import com.cts.mfrp.Anvay.pages.EventFeedPage;
import com.cts.mfrp.Anvay.pages.StudentDashboardPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StudentSteps {

    private WebDriver driver() {
        return DriverManager.getDriver();
    }

    private String registeredEventName;

    @Given("the student is logged in")
    public void theStudentIsLoggedIn() {
    }

    @And("the student navigates to the Event Feed page")
    public void theStudentNavigatesToTheEventFeedPage() {
        new StudentDashboardPage(driver()).navigateToEventFeed();
    }

    @Then("the event feed page is loaded")
    public void theEventFeedPageIsLoaded() {
        assertTrue(new EventFeedPage(driver()).isPageLoaded(),
                "Event feed page should be loaded");
    }

    @And("the upcoming events section is displayed")
    public void theUpcomingEventsSectionIsDisplayed() {
        assertTrue(new EventFeedPage(driver()).isUpcomingSectionDisplayed(),
                "Upcoming section should be displayed");
    }

    @And("at least one event card is visible")
    public void atLeastOneEventCardIsVisible() {
        assertTrue(new EventFeedPage(driver()).isFirstEventCardDisplayed(),
                "At least one event card should be visible");
    }

    @When("the student clicks Register on the first available event")
    public void theStudentClicksRegisterOnTheFirstAvailableEvent() {
        registeredEventName = new EventFeedPage(driver()).clickRegisterAndGetEventName();
    }

    @Then("the event name is captured from the confirmation modal")
    public void theEventNameIsCapturedFromTheConfirmationModal() {
        assertNotNull(registeredEventName, "Event name should have been captured");
        assertFalse(registeredEventName.isEmpty(), "Captured event name should not be empty");
    }

    @And("the registration is confirmed")
    public void theRegistrationIsConfirmed() {
        assertFalse(registeredEventName.isEmpty(), "Registration flow should have completed");
    }

    @When("the student switches to the My Registrations tab")
    public void theStudentSwitchesToTheMyRegistrationsTab() {
        new EventFeedPage(driver()).clickMyRegistrationsTab();
    }

    @Then("the registered event appears in the My Registrations list")
    public void theRegisteredEventAppearsInTheMyRegistrationsList() {
        assertTrue(new EventFeedPage(driver()).isEventDisplayedInTab(registeredEventName),
                "My Registrations tab should contain event: " + registeredEventName);
    }
}