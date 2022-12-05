package com.pointr.framework.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.qa.framework.ui.SeleniumIntegration;

public class TestCaseDummy {

	@Test
	public void test() {
		System.out.println("Running... "+this.getClass().getName());
		assertEquals(SeleniumIntegration.sayHello(), "Hello");
	}

}
