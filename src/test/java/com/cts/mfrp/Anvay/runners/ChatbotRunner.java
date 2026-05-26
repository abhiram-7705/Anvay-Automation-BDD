package com.cts.mfrp.Anvay.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features  = "src/test/resources/features/chatbot.feature",
    glue      = {
        "com.cts.mfrp.Anvay.hooks",
        "com.cts.mfrp.Anvay.stepdefinitions"
    },
    plugin    = {
        "pretty",
        "html:test-outputs/reports/chatbot-report.html",
        "json:test-outputs/reports/chatbot-report.json"
    },
    monochrome = true
)
public class ChatbotRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
