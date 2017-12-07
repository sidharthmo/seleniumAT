package com.dxc.vpc.automation.util;


import java.io.File;
import java.io.InputStream;
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
		sleep(2000);
	}
	
	// clicking on any object
	public void clickcById(String text){
		log.debug("Clicking on " + text);
		selenium.findElement(By.id("submit")).click();
		log.debug("retuned clickcById ");
	}
	
	public void enterTextById(String text, String objectName){
		log.debug("Typing in " + objectName+ " with Id " +text);
		waitUntilElementIsPresentById(text);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(text)));
		log.debug("Is Element present " + selenium.findElement(By.id(text)).isDisplayed());
		selenium.findElement(By.id(text)).sendKeys(objectName);
		log.debug("retuned enterTextById ");
	}
	
	public void selectByXpath(String Xpath){
		log.debug("Selecting Element by Xpath "+ Xpath);
		selenium.findElement(By.xpath(Xpath)).click();
		log.debug("retuned selectByXpath ");
	}
	
	public  void selectCustomerByName(String customerName)	{
		log.debug("inside selectCustomerByName "+ customerName);
		String customerXpath="//subheader/div/div/div/dc-selector/div/dl/ul/li";
		waitUntilElementIsPresentByXpath(customerXpath);
		List<WebElement> itr  = selenium.findElements(By.xpath(customerXpath));
		int counter = 1;
		for(WebElement ele : itr)
		{
            if(ele.getText().equals(customerName)) 	{
            	String xpath =customerXpath+"[" +counter+"]";
            	selectByXpath(xpath);
            										}
            counter++;
        }
		log.debug("retuned selectCustomerByName ");
															}
	
	public void selectDropDownElementByXpath(String xPath){
		log.debug("selectDropDownElementByXpath "+ xPath);
		selenium.findElement(By.xpath(xPath)).click();
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

	public  void explicitWait() 
	{
		waitUntilElementIsPresentByXpath("//ui-view/div/div/div[2]");
		List<WebElement> itr = selenium.findElements(By.xpath("//statistics-list/div/div[1]/div"));
		int counter = 1;
		int counter1 = 1;
		for(WebElement ele : itr)
		{
			System.out.println("*************************************");
			System.out.println(ele.getTagName());
	        System.out.println(ele.getText());
			if(ele.getText().contains("ian.jones@hpe.com")) 	
			{
				System.out.println("inside 1st loop");
				String baseXpath = "//statistics-list/div/div[1]/div["+counter;
	            String dropDownXpath =baseXpath+"]/statistics-list-item/div/div[1]";
	            selectByXpath(dropDownXpath);
	            String itemXpath = baseXpath+"]/statistics-list-item/item-indent/div/div[2]/statistics-list/div/div[1]/div";
	            System.out.println(itemXpath);
	            waitUntilElementIsPresentByXpath(itemXpath);
	            List<WebElement> itr1 = selenium.findElements(By.xpath(itemXpath));
	            for(WebElement ele1 : itr1)
	            {
	            	System.out.println("++++++++++++++++++++++++++++++++");
	    			System.out.println(ele1.getTagName());
	    	        System.out.println(ele1.getText());
	    	        if(ele1.getText().contains("Block Storage for Physical Servers")) {
	    	        	System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAA");
	    	        	String itemXpath1 =baseXpath +"]/statistics-list-item/item-indent/div/div[2]/statistics-list/div/div[1]/div[2]/statistics-list-item/div/div[1]";
	    	        	System.out.println(itemXpath1);
	    	        	waitUntilElementIsPresentByXpath(itemXpath1);
	    	        	selectByXpath(itemXpath1);
	    	        	String itemXpath2 ="//statistics-list/div/div[1]/div[6]/statistics-list-item/item-indent/div/div[2]/statistics-list/div/div[1]/div[2]/statistics-list-item/item-indent/div/div[2]/service-list/div[1]/subscription-list-item/div/div/div[1]";
	    	        	waitUntilElementIsPresentByXpath(itemXpath2);
	    	        	selectByXpath(itemXpath2);
	    	        	waitUntilElementIsPresentByXpath("//subheader/div/div/div");
	    	        	break;
	    	        	
	    	        }
	    	        counter1++;
	    	        
	            }
	            	
	           }
	            counter++;
	        }


	}
	

	
	public  void closeBrowser(){
		selenium.close();
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

