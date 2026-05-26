package com.cts.mfrp.Anvay.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {
	
	public static String capture(WebDriver driver, String testName) 
	{

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName  = testName + "_" + timestamp + ".png";
        String filePath  = ConfigReader.get("screenshot.path") + fileName;

        try {
        	
            File src  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File(filePath);
            dest.getParentFile().mkdirs();
            FileUtils.copyFile(src, dest);

        } 
        catch (Exception e) {
            System.out.println("Screenshot failed: " + e.getMessage());
        }

        return filePath;
    }
	
    public static String captureAsBase64(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }

}
