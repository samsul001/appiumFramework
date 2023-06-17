package com.ecomapp.TestCases;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.ecomapp.pageObjects.FormPage;
import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BaseTest {

	public AppiumDriverLocalService service;
	public AndroidDriver driver;
	public FormPage form;
	
	
	@BeforeClass
	public void configureAppium() throws MalformedURLException {
		//Start Appium Server Programmatically
				//main.js file is respensible to start Appium server 
				//main.js file location --> C:\Users\samsu\AppData\Roaming\npm\node_modules\appium\build\lib\main.js
				//IP address(127.0.0.1) and port number #4723 should be mentioned to start appium server at the concern IP address & Port
				
				service= new AppiumServiceBuilder()
						.withAppiumJS(new File("C://Users//samsu//AppData//Roaming//npm//node_modules//appium//build//lib//main.js"))
						.withIPAddress("127.0.0.1").usingPort(4723).build();
				service.start();				
	}
	
	
	@BeforeMethod
	public void startApp() throws MalformedURLException {
		//Appium client code --> Appium Server --> UiAutomator2 -->Mobile device
		UiAutomator2Options option = new UiAutomator2Options();
		option.setDeviceName("SamsulEmulator");
		option.setApp("C://Users//samsu//eclipse-workspace//Appium//src//test//java//resources//General-Store.apk");
		option.setChromedriverExecutable("C://Users//samsu//eclipse-workspace//Appium//Drivers//chromedriver.exe");
		
		//Android driver
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), option);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
		form = new FormPage(driver);
	}
	
	@AfterMethod
	public void returnToHome() {
		driver.quit();
	}
	

	@AfterClass
	public void tearDown() {
		
		service.stop();
	}
	
	public void longpressAction(WebElement ele) {
		((JavascriptExecutor) driver).executeScript("mobile: longClickGesture", 
				ImmutableMap.of("elementId", ((RemoteWebElement)ele).getId(), "duration" ,2000));
	}
	
	public void scrollToFindElement(String elementText) {
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+elementText+"\"));"));
	  //driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Austria\"));"));
	}
	
	public void scrollTillEnd() {
		boolean canScrollMore;
		do {
		canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
			    "left", 100, "top", 100, "width", 200, "height", 200,
			    "direction", "down",
			    "percent", 3.0
			));
		
		}while(canScrollMore);
	}
	
	public void swipeAction(WebElement element, String direction) {
		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
			    "elementId", ((RemoteWebElement)element).getId(),
			    "direction", direction,
			    "percent", 0.75
			));
	}
	
	public void dragAndDropTo(WebElement element, int xAxis , int yAxis) {
		driver.executeScript("mobile: dragGesture" , ImmutableMap.of(
				 "elementId" , ((RemoteWebElement)element).getId(),
				 "endX",xAxis,
				 "endY",yAxis
		 ));
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
