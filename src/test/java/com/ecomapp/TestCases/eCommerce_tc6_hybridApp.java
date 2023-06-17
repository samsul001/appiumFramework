package com.ecomapp.TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ecomapp.pageObjects.CartPage;

import com.ecomapp.pageObjects.ProductsPage;


public class eCommerce_tc6_hybridApp extends BaseTest{

	@Test
	public void navToChromeBroserfromNativeApp() throws InterruptedException {
		
		form.SelectCountryName("Bhutan");
		form.setNameField("test");
		form.setGender("female");
		ProductsPage products =form.submitForm();
		products.addItemToCartByIndex(0);
		products.addItemToCartByIndex(0);
		CartPage cartPage = products.goToCartPage();
		cartPage.waitForCartPageLoad();
		double sumOfProductPrice = cartPage.getProductSum();
		double displayTotalPurchaseAmnt = cartPage.getTotalAmntDisplayed();
		Assert.assertEquals(sumOfProductPrice, displayTotalPurchaseAmnt);
		String alertTitle = cartPage.validateTermsCondition();
		Assert.assertEquals(alertTitle, "Terms Of Conditions");
		cartPage.acceptTermsConditions();
		cartPage.submitOrder();

	}
}
