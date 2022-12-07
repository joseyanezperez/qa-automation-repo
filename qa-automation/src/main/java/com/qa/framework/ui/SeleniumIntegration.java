/**
 * 
 */
package com.qa.framework.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


/**
 * @author jose
 *
 */
public class SeleniumIntegration {

	static String OK = "OK";
	static String KO = "KO";
	static boolean morePostPages = false;
	static boolean firstLoad = true;

	static HashMap<String, Integer> wordCounterMap;

	static List<String> postDetailURL;

	public static String loadURL(String property, String driverURL) {
		System.out.println(property + " " + driverURL);
		postDetailURL = new ArrayList<String>();

		// Creating an object of WebDriver
		System.setProperty(property, driverURL);

		WebDriver driver;
		if (property.contains("chrome")) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			driver = new ChromeDriver(options);
		} else {
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setHeadless(true);
			driver = new FirefoxDriver(firefoxOptions);	
		}

		String baseUrl = new String("https://www.pointr.tech/blog");
		morePostPages = true;
		System.out.println("Starting loading pages checking...");
		while (morePostPages) {
			try {

				// Get URL of the all posts
				driver.get(baseUrl);
				if (firstLoad) {
					WebElement cookies = driver.findElement(By.id("hs-eu-confirmation-button"));
					cookies.click();
					firstLoad = false;
				}
				morePostPages = false;
				WebElement postlist = driver.findElement(By.id("new-blog-test"));

				// Below will return a list of all elements inside element
				List<WebElement> webEleListPostList = postlist.findElements(By.xpath(".//*"));
				for (Iterator iterator = webEleListPostList.iterator(); iterator.hasNext();) {
					WebElement webElement = (WebElement) iterator.next();
					if (webElement.getAttribute("class").equals("read-more")) {
						postDetailURL.add(webElement.getAttribute("href"));
					}

				}

				// Get the next-page link button
				WebElement navigationBar = driver.findElement(By.id("hs_cos_wrapper_module_164726827019523"));
				List<WebElement> webEleListNavigationBar = navigationBar.findElements(By.xpath(".//*"));
				for (Iterator iterator = webEleListNavigationBar.iterator(); iterator.hasNext();) {
					WebElement webElement = (WebElement) iterator.next();
					if (webElement.getAttribute("class").equals("next-link")) {
						morePostPages = true;
						baseUrl = webElement.getAttribute("href");
					}

				}

			} catch (Exception e) {
				System.out.println("Issue on loadURL " + e.getMessage());
				e.printStackTrace();
				return KO;
			}

		}
		System.out.println("End loading pages checking...");

		try {
			System.out.println("Counting and sorting word counter...");
			// Count the most 5 repeated words for the 3 last articles
			wordCounterMap = new HashMap<String, Integer>();
			for (int i = postDetailURL.size() - 1; i > postDetailURL.size() - 4; i--) {

				String url = (String) postDetailURL.get(i);
				System.out.println("Processing the URL: " + url);
				driver.get(url);

				// Process title
				WebElement postTittleElement = driver.findElement(By.id("hs_cos_wrapper_name"));
				buildCounterWordMap(postTittleElement.getText());

				// Process post body
				WebElement postBodyElement = driver.findElement(By.id("hs_cos_wrapper_post_body"));
				List<WebElement> webEleListPostBodyList = postBodyElement.findElements(By.xpath(".//*"));
				for (Iterator iterator = webEleListPostBodyList.iterator(); iterator.hasNext();) {
					WebElement webElement = (WebElement) iterator.next();
					if (webElement.getAttribute("innerHTML").contains("<p>")) {
						buildCounterWordMap(webElement.getText());

					}

				}

			}
			// Sort the list
			wordCounterMap = (HashMap<String, Integer>) sortByValue(wordCounterMap, false);
			// Store the result on file
			createResultFile("mostRepeatedWord.txt");

		} catch (Exception e) {
			System.out.println("Issue on loadURL " + e.getMessage());
			e.printStackTrace();
			return KO;
		}

		driver.quit();
		return OK;
	}

	/**
	 * Store text and counter on Map
	 * 
	 * @param text
	 */
	private static void buildCounterWordMap(String text) {
		String[] spittedText = text.split(" ");
		for (int i = 0; i < spittedText.length; i++) {
			if (wordCounterMap.get(spittedText[i].toUpperCase()) == null) {
				wordCounterMap.put(spittedText[i].toUpperCase(), 1);
			} else {
				wordCounterMap.put(spittedText[i].toUpperCase(), wordCounterMap.get(spittedText[i].toUpperCase()) + 1);
			}
		}
	}

	/**
	 * Sort word map by counter
	 * 
	 * @param sortMap
	 * @param order
	 * @return
	 */
	private static Map<String, Integer> sortByValue(Map<String, Integer> sortMap, final boolean order) {
		List<Entry<String, Integer>> list = new LinkedList<>(sortMap.entrySet());

		// Sorting the list based on values
		list.sort((o1, o2) -> order
				? o1.getValue().compareTo(o2.getValue()) == 0 ? o1.getKey().compareTo(o2.getKey())
						: o1.getValue().compareTo(o2.getValue())
				: o2.getValue().compareTo(o1.getValue()) == 0 ? o2.getKey().compareTo(o1.getKey())
						: o2.getValue().compareTo(o1.getValue()));
		return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));

	}

	/**
	 * Flush result to a File
	 * 
	 * @param fileName
	 */
	private static void createResultFile(String fileName) {
		try {
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			int counter = 0;
			for (Entry<String, Integer> set : wordCounterMap.entrySet()) {
				if (counter < 5) {
					writer.println(set.getKey() + " = " + set.getValue());
					counter++;
				} else {
					break;
				}

			}
			writer.close();
		} catch (IOException e) {
			System.out.println("An error occurred on createResultFile" + e.getMessage());
			e.printStackTrace();
		}
	}

}
