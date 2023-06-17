package com.ecomapp.pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.ecomapp.utils.AndroidActions;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CartPage extends AndroidActions{

	AndroidDriver driver;	
	public CartPage(AndroidDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
	}
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/toolbar_title")
	private WebElement cartPageHeader;
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/productPrice")
	private List<WebElement> productPrice;
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/totalAmountLbl")
	private WebElement totalAmntLbl;
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/termsButton")
	private WebElement termsBtn;
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/alertTitle")
	private WebElement alertTitle;
	
	@AndroidFindBy(id="android:id/button1")
	private WebElement alertClsBtn;
	
	@AndroidFindBy(className = "android.widget.CheckBox")
	private WebElement checkBox;
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/btnProceed")
	private WebElement proceedBtn;
	
	//Action Methods
	public void waitForCartPageLoad() {
		explicitlyWait(cartPageHeader, "text", "Cart");
	}
	
	public double getProductSum() {
		int count = productPrice.size();
		double totalSum = 0;
		
		for(int i=0;i<count;i++) {
			String price = productPrice.get(i).getText();
			double formattedPriceAmt = getFormattedAmt(price, 1);
			totalSum = totalSum + formattedPriceAmt;
		}
		return totalSum;
	}
	
	public double getTotalAmntDisplayed() {
		String totalAmnt = totalAmntLbl.getText();
		double formattedTotlAmt=getFormattedAmt(totalAmnt, 1);
		return formattedTotlAmt;
	}
	
	public String validateTermsCondition() {
		longpressAction(termsBtn);
		String title = alertTitle.getText();
		return title;
	}
	
	public void acceptTermsConditions() {	
		alertClsBtn.click();
	}
	
	public void submitOrder() {
		checkBox.click();
		proceedBtn.click();
	}
}
