package com.pointr.framework.ui;

import static org.junit.Assert.*;

import org.junit.Test;

import com.qa.framework.ui.SeleniumIntegration;

public class TestSeleniumIntegrationGithub {

	static String OK = "OK";
	static String KO = "KO";
	static String driverURLChrome="chromedriver_mac";
	static String driverURLFirefox="geckodriver-mac";
	static String propertyChrome="webdriver.chrome.driver";
	static String propertyFirefox="webdriver.gecko.driver";
	static String chrome="chrome";
	static String firefox="firefox";

	
	@Test
	public void testLoadURL() {
		System.out.println("Running... "+this.getClass().getName());
		String browser =System.getProperty("browser");
		if(browser.equals(chrome)) {
			System.out.println("Running test on Chrome browser...");
			assertEquals(SeleniumIntegration.loadURL(propertyChrome,driverURLChrome), OK);
		}
		else {
			System.out.println("Running test on Firefox browser...");
			assertEquals(SeleniumIntegration.loadURL(propertyFirefox,driverURLFirefox), OK);

		}
	}
}
