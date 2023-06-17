package com.ecomapp.utils;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;

public class AppiumUtils {
	
	AndroidDriver driver;
	public AppiumUtils(AndroidDriver driver) {
		this.driver = driver;
	}
	
	//Explicit Wait
		public void explicitlyWait(WebElement element) {
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(element));
		}
		
		public void explicitlyWait(WebElement element, String attribute, String value) {
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.attributeContains(element, attribute, value));
		}
		
		public double getFormattedAmt(String amt, int substringIndexStartFrom) {
			double formattedAmount = Double.parseDouble(amt.substring(substringIndexStartFrom));
			return formattedAmount;
		}

}
