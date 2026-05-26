package com.cts.mfrp.Anvay.stepdefinitions;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import com.cts.mfrp.Anvay.hooks.DriverManager;
import com.cts.mfrp.Anvay.pages.ChatbotPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ChatBotSteps {

    private WebDriver driver() {
        return DriverManager.getDriver();
    }

    private ChatbotPage chatbotPage() {
        return new ChatbotPage(driver());
    }

    @When("the user opens the chatbot panel")
    public void theUserOpensTheChatbotPanel() {
        chatbotPage().clickFloatingButton();
        assertTrue(chatbotPage().isPanelDisplayed(), "Chatbot panel should be visible");
    }

    @And("the user clicks the quick-select button {string}")
    public void theUserClicksTheQuickSelectButton(String buttonName) {
        switch (buttonName) {
            case "Register for Event":
                chatbotPage().clickQuickSelectRegisterEvent(); break;
            case "Apply for Leader":
                chatbotPage().clickQuickSelectApplyLeader(); break;
            case "Event Rules":
                chatbotPage().clickQuickSelectEventRules(); break;
            case "Join a Club":
                chatbotPage().clickQuickSelectJoinClub(); break;
            case "Points and Ranking":
                chatbotPage().clickQuickSelectPointsRanking(); break;
            case "Create an Event":
                chatbotPage().clickQuickSelectCreateEvent(); break;
            default:
                throw new IllegalArgumentException("Unknown quick-select button: " + buttonName);
        }
    }

    @Then("the chatbot displays a non-empty response")
    public void theChatbotDisplaysANonEmptyResponse() {
        assertTrue(chatbotPage().isLastBotMessageDisplayed(), "Bot should display a response");
        assertFalse(chatbotPage().getLastBotMessageText().isEmpty(), "Bot response should not be empty");
    }

    @And("the chatbot panel is closed")
    public void theChatbotPanelIsClosed() {
        try {
            if (chatbotPage().isPanelDisplayed()) {
                chatbotPage().clickClose();
            }
        } catch (Exception e) {
            System.out.println("Chatbot panel already closed or not found: " + e.getMessage());
        }
    }

    @And("the user types the message {string}")
    public void theUserTypesTheMessage(String message) {
        chatbotPage().typeMessage(message);
    }

    @And("the user sends the message with the Enter key")
    public void theUserSendsTheMessageWithTheEnterKey() {
        chatbotPage().sendWithEnterKey();
    }

    @And("the user sends the message using the Send button")
    public void theUserSendsTheMessageUsingTheSendButton() {
        chatbotPage().clickSend();
    }

    @Then("the chatbot response indicates it is out of scope")
    public void theChatbotResponseIndicatesItIsOutOfScope() {
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(chatbotPage().isLastBotMessageDisplayed(),
                "Bot should respond to out-of-scope query");
        String response = chatbotPage().getLastBotMessageText().toLowerCase();
        soft.assertTrue(
                response.contains("multi-tenant") || response.contains("pod 2") || response.contains("only the queries"),
                "Out-of-scope response text is not appropriate. Actual: " + response);
        soft.assertAll();
    }
}