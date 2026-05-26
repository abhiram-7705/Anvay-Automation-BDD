package com.cts.mfrp.Anvay.stepdefinitions;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;

import com.cts.mfrp.Anvay.hooks.DriverManager;
import com.cts.mfrp.Anvay.pages.CreateEventModal;
import com.cts.mfrp.Anvay.pages.EventManagementPage;
import com.cts.mfrp.Anvay.pages.InstitutionDashboardPage;
import com.cts.mfrp.Anvay.utils.ConfigReader;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class InstitutionSteps {

    private WebDriver driver() {
        return DriverManager.getDriver();
    }

    @Given("the institution user is logged in")
    public void theInstitutionUserIsLoggedIn() {
    }

    @And("the institution user navigates to All Events")
    public void theInstitutionUserNavigatesToAllEvents() {
        new InstitutionDashboardPage(driver()).navigateToAllEvents();
    }

    @When("the institution user clicks Create Event")
    public void theInstitutionUserClicksCreateEvent() {
        new EventManagementPage(driver()).clickCreateEvent();
    }

    @And("fills in the event form with data from config")
    public void fillsInTheEventFormWithDataFromConfig() {
        CreateEventModal modal = new CreateEventModal(driver());
        modal.selectClub(ConfigReader.get("event.club"));
        modal.enterEventTitle(ConfigReader.get("event.title"));
        modal.selectCategory(ConfigReader.get("event.category"));
        modal.enterContactNumber(ConfigReader.get("event.contact"));
        modal.enterStartDate(ConfigReader.get("event.start.date"));
        modal.enterLocation(ConfigReader.get("event.location"));
    }

    @And("submits the event form")
    public void submitsTheEventForm() {
        new CreateEventModal(driver()).clickSubmit();
    }

    @Then("the Create Event modal closes")
    public void theCreateEventModalCloses() {
        assertTrue(new CreateEventModal(driver()).isModalClosed(),
                "Create Event modal should have closed after successful submission");
    }

    @And("the new event appears in the upcoming events table")
    public void theNewEventAppearsInTheUpcomingEventsTable() {
        assertTrue(new EventManagementPage(driver()).isFirstUpcomingRowDisplayed(),
                "New event should appear in the upcoming events table");
    }
}