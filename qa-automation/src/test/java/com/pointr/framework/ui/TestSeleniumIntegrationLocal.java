package com.pointr.framework.ui;

import static org.junit.Assert.*;

import org.junit.Test;

import com.qa.framework.ui.SeleniumIntegration;

public class TestSeleniumIntegrationLocal {

	static String OK = "200";
	static String KO = "KO";
	static String driverURL="chromedriver_linux";

	
	@Test
	public void testLoadURL() {
		System.out.println("Running... "+this.getClass().getName());
		assertEquals(SeleniumIntegration.loadURL(driverURL), OK);
	}

}
