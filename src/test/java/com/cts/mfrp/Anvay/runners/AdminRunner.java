package com.cts.mfrp.Anvay.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features  = "src/test/resources/features/admin.feature",
    glue      = {
        "com.cts.mfrp.Anvay.hooks",
        "com.cts.mfrp.Anvay.stepdefinitions"
    },
    plugin    = {
        "pretty",
        "html:test-outputs/reports/admin-report.html",
        "json:test-outputs/reports/admin-report.json"
    },
    monochrome = true
)
public class AdminRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
