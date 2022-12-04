package com.pointr.framework.ui;

import static org.junit.Assert.*;

import org.junit.Test;

import qa.HelloWorld;

public class TestHelloWorld {

	@Test
	public void test() {
		assertEquals(HelloWorld.sayHello(), "Hello");
	}

}
