package com.dxc.vpc.automation.util;


import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebConnector {

	
	public Logger log = Logger.getLogger("vpgLog");
	static WebDriver selenium =null;
	WebDriver mozilla=null;
	WebDriver chrome=null;
	WebDriver HtmlFireFox=null;
	WebDriver ie=null;
	static WebDriverWait wait;
	static WebConnector w;
	private Properties localProperties=null;


	
	private WebConnector(){
		getConfigration();
	}
	

	// Rading Configration and loading once
	public void getConfigration(){
		String str = "config.properties";
		InputStream localInputStream = ClassLoader.getSystemResourceAsStream(str);
		if (localInputStream == null) {
			localInputStream = WebConnector.class.getResourceAsStream(str);
			
		}
		if (localInputStream != null) {
			localProperties = new Properties();
			try {
				localProperties.load(localInputStream);
			} catch (Exception e) {
			}
		}
	}
	
	public Properties getLocalProperties() {
		return localProperties;
	}
	

	

	public XlsReader getDatatable(String xlspath) {
		String xlsLocation = System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+xlspath;
		System.out.println("Reading xls file from" + xlsLocation);
		return new XlsReader(xlsLocation);
		
	}
	
	

	
	/// ****************Application Independent functions************************ ///

	// opening the browser
	public void openBrowser(String browserType){
		log.debug("Opening browser in "+browserType);
		if(browserType.equals("Mozilla") && mozilla==null){
			selenium = new FirefoxDriver();
			mozilla=selenium;
		}else if(browserType.equals("Mozilla") && mozilla!=null){
			selenium=mozilla;
		}else if(browserType.equals("Chrome") && chrome==null){
			
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//chromedriver.exe");
			selenium=new ChromeDriver();
			chrome=selenium;
		}else if(browserType.equals("HtmlFireFox") && HtmlFireFox==null){
			HtmlFireFox=selenium;
		}
		
		else if(browserType.equals("IE")){
			// set the IE server exe path and initialize
		} else {
			log.info("Browser not defined");
			System.out.println("Browser not defined");
		}
		// max
		selenium.manage().window().maximize();
	
	}
	// navigates to a URL
	public void navigateToURL(String URL) {
		//driver.navigate()
		log.debug("navigate to URL "+URL);
		selenium.get(URL);
		selenium.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		selenium.manage().timeouts().pageLoadTimeout(3000, TimeUnit.SECONDS);
		String loginId ="submit";
		WebDriverWait wait =getWebDriverWaitInstance();
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(loginId)));
		log.debug("retuned navigateToURL ");
	}
	
	// clicking on any object
	public void clickcById(String text){
		log.debug("Clicking on " + text);
		selenium.findElement(By.id("submit")).click();
		log.debug("retuned clickcById ");
	}
	
	public void enterTextById(String text, String objectName){
		log.debug("Typing in " + objectName+ " with Id " +text);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(text)));
		log.debug("Is Element present " + selenium.findElement(By.id(text)).isDisplayed());
		sleep(100);
		selenium.findElement(By.id(text)).sendKeys(objectName);
		log.debug("retuned enterTextById ");
	}
	
	public void selectByXpath(String Xpath){
		log.debug("Selecting Element by Xpath "+ Xpath);
		selenium.findElement(By.xpath(Xpath)).click();
		log.debug("retuned selectByXpath ");
	}
	
	public void selectDropDownElementByXpath(String xPath){
		log.debug("selectDropDownElementByXpath "+ xPath);
		WebElement test = selenium.findElement(By.id("default_dc_link"));
		Select drpSelect = new Select(test);
		System.out.println("Testing ----");
		System.out.println(drpSelect.getAllSelectedOptions());
		drpSelect.selectByVisibleText("Wynyard");
		System.out.println("Testing ----Wynyard");
		//selenium.findElement(By.xpath(xPath)).click();
		log.debug("retuned selectDropDownElementByXpath ");

	}
	
	public  void waitUntilElementIsPresentByXpath(String xpath){
		log.debug("inside waitUntilElementIsPresent "+ xpath);
		WebDriverWait wait = new WebDriverWait(selenium, 1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		log.debug("retuned waitUntilElementIsPresent ");
	}
	
	public  void waitUntilElementIsPresentById(String Id){
		log.debug("inside waitUntilElementIsPresentById "+ Id);
		WebDriverWait wait = new WebDriverWait(selenium, 1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Id)));
		log.debug("retuned waitUntilElementIsPresentById ");
	}
	
	public  String getValueByXpath(String xpath){
		log.debug("inside getValueByXpath "+ xpath);
		return selenium.findElement(By.xpath(xpath)).getText();

	}
	
	public  void sleep(int time){
		log.debug("inside sleep with time "+ time);
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		log.debug("retuned from sleep function ");
	}
	
	public  void explicitWait() {
		int maxWaitTime =100;
		int i=0;
		int waitTime=10;
		String xpath ="ng-scope";
		
		 //a = selenium.findElements(By.className(xpath)).iterator();

		 List<WebElement> elements = selenium.findElements(By.xpath("//ui-view/div/div/div[2]"));
		 sleep(1000);
		 Iterator<WebElement> itr = elements.iterator();
		 while(itr.hasNext()) {
			 WebElement test = itr.next();
		     System.out.println(test.getText());
		     System.out.println(test.getClass());
		     System.out.println(test.getTagName());
		     System.out.println(test.toString());
		     if ((test.getText()).contains("migration")) {
		    	 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//statistics-list-item/div/div[1]")));
		    	 test.findElement(By.xpath("//statistics-list-item/div/div[1]")).click();
		    	 
		     }
		 }

			/*while(a != null) {
			
			if(i != maxWaitTime ) {
				try {
					Thread.sleep(waitTime);
					i= i + waitTime;
					a = ""+selenium.findElements(By.className(xpath)).size();
					} catch (InterruptedException e) {
								e.printStackTrace();
			}
			}
			
		}
		
*/	}
	
	public  void closeBrowser(){

		//div[2]/ui-view/div/div/div[2]/div
		int a = selenium.findElements(By.className("ng-scope")).size();
		System.out.println(a);

		//waitUntilElementIsPresentByXpath("//div/sort-selector[2]/ul/li[3]/a");
		//selenium.findElement(By.xpath("//div/sort-selector[2]/ul/li[3]/a")).click();

		
		//selenium.close();
		//Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='sort-types195147279']/li[2]/a")));
		//dropdown.selectByIndex(1);
		//dropdown.selectByVisibleText("Group By Catalog Item And Owner");
		//driver.findElement(By.id(text)).sendKeys(objectName);

											}
	/// ****************Application dependent functions************************ ///

	//Singleton for WebConnector/
	public static WebConnector getWebConnectorInstance(){
		if(w==null)
			w= new WebConnector();		
		return w;
	}
	
	public static WebDriverWait getWebDriverWaitInstance(){
		if(wait==null)
			wait= new WebDriverWait(selenium,10000);		
		return wait;
	}
	
	//Logging Object/
	public void log(String msg){
		log(msg);
	}

}

