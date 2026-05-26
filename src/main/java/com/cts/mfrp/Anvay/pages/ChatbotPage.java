package com.cts.mfrp.Anvay.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ChatbotPage extends BasePage {

    private JavascriptExecutor js;

    public ChatbotPage(WebDriver driver) {
        super(driver);
        this.js = (JavascriptExecutor) driver;
    }

    @FindBy(xpath = "//button[contains(@class,'chat-fab')]")
    private WebElement floatingButton;

    @FindBy(xpath = "//div[contains(@class,'chat-panel')]")
    private WebElement chatbotPanel;

    @FindBy(xpath = "//button[contains(@class,'chat-fab')]")
    private WebElement closeChatButton;

    @FindBy(xpath = "//div[contains(@class,'quick-replies')]//button[contains(normalize-space(.),'Register for Event')]")
    private WebElement quickSelectRegisterEvent;

    @FindBy(xpath = "//div[contains(@class,'quick-replies')]//button[contains(normalize-space(.),'Apply for Leader')]")
    private WebElement quickSelectApplyLeader;

    @FindBy(xpath = "//div[contains(@class,'quick-replies')]//button[contains(normalize-space(.),'Event Rules')]")
    private WebElement quickSelectEventRules;

    @FindBy(xpath = "//div[contains(@class,'quick-replies')]//button[contains(normalize-space(.),'Join a Club')]")
    private WebElement quickSelectJoinClub;

    @FindBy(xpath = "//div[contains(@class,'quick-replies')]//button[contains(normalize-space(.),'Points')]")
    private WebElement quickSelectPointsRanking;

    @FindBy(xpath = "//div[contains(@class,'quick-replies')]//button[contains(normalize-space(.),'Create an Event')]")
    private WebElement quickSelectCreateEvent;

    @FindBy(xpath = "//textarea[@id='chat-input']")
    private WebElement chatInput;

    @FindBy(xpath = "//button[contains(@class,'chat-send-btn')]")
    private WebElement sendButton;

    @FindBy(xpath = "(//div[contains(@class,'msg-bot')]//div[@class='msg-bubble'])[last()]")
    private WebElement lastBotMessage;

    private void jsClick(WebElement element) {
        waits.until(ExpectedConditions.visibilityOf(element));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        js.executeScript("arguments[0].click();", element);
    }

    public void clickFloatingButton() {
        waits.until(ExpectedConditions.elementToBeClickable(floatingButton));
        js.executeScript("arguments[0].click();", floatingButton);
        waits.until(ExpectedConditions.visibilityOf(chatbotPanel));
    }

    public boolean isPanelDisplayed() {
        try {
            return chatbotPanel.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickQuickSelectRegisterEvent() {
        jsClick(quickSelectRegisterEvent);
        waits.until(ExpectedConditions.visibilityOf(lastBotMessage));
    }

    public void clickQuickSelectApplyLeader() {
        jsClick(quickSelectApplyLeader);
        waits.until(ExpectedConditions.visibilityOf(lastBotMessage));
    }

    public void clickQuickSelectEventRules() {
        jsClick(quickSelectEventRules);
        waits.until(ExpectedConditions.visibilityOf(lastBotMessage));
    }

    public void clickQuickSelectJoinClub() {
        jsClick(quickSelectJoinClub);
        waits.until(ExpectedConditions.visibilityOf(lastBotMessage));
    }

    public void clickQuickSelectPointsRanking() {
        jsClick(quickSelectPointsRanking);
        waits.until(ExpectedConditions.visibilityOf(lastBotMessage));
    }

    public void clickQuickSelectCreateEvent() {
        jsClick(quickSelectCreateEvent);
        waits.until(ExpectedConditions.visibilityOf(lastBotMessage));
    }

    public void typeMessage(String message) {
        waits.until(ExpectedConditions.elementToBeClickable(chatInput));
        chatInput.click();
        chatInput.clear();
        chatInput.sendKeys(message);
    }

    public void clickSend() {
        waits.until(ExpectedConditions.elementToBeClickable(sendButton));
        sendButton.click();
        waits.until(ExpectedConditions.visibilityOf(lastBotMessage));
    }

    public void sendWithEnterKey() {
        waits.until(ExpectedConditions.elementToBeClickable(chatInput));
        chatInput.sendKeys(Keys.ENTER);
        waits.until(ExpectedConditions.visibilityOf(lastBotMessage));
    }

    public String getLastBotMessageText() {
        try {
            return lastBotMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isLastBotMessageDisplayed() {
        try {
            return lastBotMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickClose() {
        waits.until(ExpectedConditions.elementToBeClickable(closeChatButton));
        js.executeScript("arguments[0].click();", closeChatButton);
        waits.until(ExpectedConditions.invisibilityOf(chatbotPanel));
        waits.until(ExpectedConditions.elementToBeClickable(floatingButton));
    }
}