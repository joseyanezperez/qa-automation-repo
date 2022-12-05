package com.pointr.framework.ui;

import static org.junit.Assert.*;

import org.junit.Test;

import com.qa.framework.ui.SeleniumIntegration;

public class TestSeleniumIntegration {

	static String OK = "200";
	static String KO = "KO";

	/*
	 * @Test public void testsayHello() {
	 * assertEquals(SeleniumIntegration.sayHello(), "Hello"); }
	 */
	
	@Test
	public void testLoadURL() {
		assertEquals(SeleniumIntegration.loadURL(), OK);
	}

}
