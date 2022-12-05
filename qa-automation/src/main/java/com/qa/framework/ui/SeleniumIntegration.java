/**
 * 
 */
package com.qa.framework.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * @author jose
 *
 */
public class SeleniumIntegration {

	static String OK = "200";
	static String KO = "KO";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Hello world!");
	}

	public static String sayHello() {
		return "Hello";

	}

	public static String loadURL() {
		// Creating an object of ChromeDriver
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver_mac");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		WebDriver driver = new ChromeDriver(options);
		
		try {
			driver.get("https://www.pointr.tech/blog");
		} catch (Exception e) {
			System.out.println("Issue on loadURL " + e.getMessage());
			e.printStackTrace();
			return KO;
		}
		driver.quit();
		return OK;
	}

}
