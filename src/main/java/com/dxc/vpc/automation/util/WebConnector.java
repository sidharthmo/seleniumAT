package com.dxc.vpc.automation.util;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.dxc.vpc.automation.constant.LoginPageConstant;
import com.dxc.vpc.automation.constant.ServicesPageConstant;



public class WebConnector {

	
	public Logger log = Logger.getLogger("vpcLog");
	static WebDriver selenium =null;
	WebDriver mozilla=null;
	WebDriver chrome=null;
	WebDriver HtmlFireFox=null;
	WebDriver ie=null;
	static WebDriverWait wait;
	static WebConnector w;
	private Properties localProperties=null;
	private String customerExpendIconXpath =ServicesPageConstant.customerExpendIconXpath;
	private String detailHeaderXpath =ServicesPageConstant.detailHeaderXpath;
	private String baseXpath = ServicesPageConstant.baseXpath;
	private String customerExpendGridDataXpath = ServicesPageConstant.customerExpendGridDataXpath;


	
	private WebConnector(){
		try {
			getConfigration();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	

	// Reading Configuration and loading once
	public void getConfigration() throws FileNotFoundException{
		String str = System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"config.properties";
		InputStream localInputStream = new FileInputStream(str);

		if (localInputStream != null) {
			localProperties = new Properties();
			try {
				localProperties.load(localInputStream);
			} catch (Exception e) {
				log.info("config properties not loaded"+ e);
			}
		}
	}
	
	public Properties getLocalProperties() {
		return localProperties;
	}
	

	
	// Reading XLS
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
		log.debug("Loading URL "+URL);
		selenium.get(URL);
		selenium.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		selenium.manage().timeouts().pageLoadTimeout(3000, TimeUnit.SECONDS);
		String loginId =LoginPageConstant.SUBMITBUTTONID;
		WebDriverWait wait =getWebDriverWaitInstance();
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(loginId)));
		log.debug("retuned navigateToURL ");
		sleep(2000);
	}
	
	// clicking on any object By ID
	public void clickcById(String text){
		log.debug("Clicking on " + text);
		selenium.findElement(By.id("submit")).click();
		log.debug("retuned clickcById ");
	}
	
	public void enterTextById(String text, String keys){
		log.debug("Typing in " + keys+ " with Id " +text);
		waitUntilElementIsPresentById(text);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(text)));
		log.debug("Is Element present " + selenium.findElement(By.id(text)).isDisplayed());
		selenium.findElement(By.id(text)).sendKeys(keys);
		log.debug("retuned enterTextById ");
	}
	
	public void selectByXpath(String Xpath){
		log.debug("Selecting Element by Xpath "+ Xpath);
		selenium.findElement(By.xpath(Xpath)).click();
		log.debug("retuned selectByXpath ");
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



	public void getRespectiveCustomerFromGrid(String gridDataXpath,String customerName,String serverName) throws Exception {
		List<WebElement> itr = selenium.findElements(By.xpath(gridDataXpath));
		int counter = 1;
		for(WebElement ele : itr)
		{
			if(ele.getText().contains(customerName)) {

				clickRespectiveCustomerFromGrid(baseXpath+counter+customerExpendIconXpath,baseXpath+counter+customerExpendGridDataXpath);
				getRespectiveCustomerDetail(baseXpath+counter,serverName);
				return;
			}
			counter++;	

	}throw new Exception();
	}
	public void clickRespectiveCustomerFromGrid(String customerExpendIconXpath, String customerExpendGridDataXpath) {
        selectByXpath(customerExpendIconXpath);
        waitUntilElementIsPresentByXpath(customerExpendGridDataXpath);
    
	}
	

	public void getRespectiveCustomerDetail(String baseXpath,String serverName) throws Exception {
		String customerExpendGridDataXpath = baseXpath+ServicesPageConstant.customerExpendGridDataXpath;
		int counter = 1;
		List<WebElement> itr = selenium.findElements(By.xpath(customerExpendGridDataXpath));
        for(WebElement ele : itr)
        {
            if(ele.getText().contains(serverName)) {
            	String customerExpendGridDataDropDownIconXpath =baseXpath +ServicesPageConstant.customerExpendGridDataDropDownIconFirstHalfXpath+counter+ServicesPageConstant.customerExpendGridDataDropDownIconSecondHalfXpath;
            	waitUntilElementIsPresentByXpath(customerExpendGridDataDropDownIconXpath+ServicesPageConstant.customerExpendGridDataDropDownIconXpath);
            	selectByXpath(customerExpendGridDataDropDownIconXpath+ServicesPageConstant.customerExpendGridDataDropDownIconXpath);
            	expendRespectiveCustomerDetail(customerExpendGridDataDropDownIconXpath);
            	return;
            	
            }
	        counter++;
	        
        }throw new Exception();
	}
	
	public void expendRespectiveCustomerDetail(String customerExpendGridDataDropDownIconXpath) {
    	List<WebElement> itr = selenium.findElements(By.xpath(customerExpendGridDataDropDownIconXpath+ServicesPageConstant.expendRespectiveCustomerDetailListXpath));
    	int randomNumber=randInt(1, itr.size());
    	String detailIconXpath= customerExpendGridDataDropDownIconXpath+ServicesPageConstant.expendRespectiveCustomerDetailListXpath+"["+randomNumber+"]"+ServicesPageConstant.expendRespectiveCustomerDetailListDetailXpath;
    	waitUntilElementIsPresentByXpath(detailIconXpath);
    	selectByXpath(detailIconXpath);
/*            	for(WebElement ele1 : itr)
        {
        	System.out.println(ele1.getText());
        	
        	
        }*/

    	waitUntilElementIsPresentByXpath(detailHeaderXpath);
    	
	}
	public  void selectItemFromDropDown(String xpath,String Name) throws Exception{
		waitUntilElementIsPresentByXpath(xpath);
    	List<WebElement> itr = selenium.findElements(By.xpath(xpath));
    	int counter =1;
    	String iconXpath = null;
        for(WebElement ele : itr)
        {
            if(ele.getText().equalsIgnoreCase(Name)) {
            	iconXpath =xpath +"["+counter+"]";
            	waitUntilElementIsPresentByXpath(iconXpath);
            	selectByXpath(iconXpath);
            	break;  	
            }
	        counter++;        
        }
        
        if(iconXpath == null) {
        	throw new Exception("Message");
        }
        
	}
    //TODO
    //page should be scrolled until all items are loaded.Login need to be updated   
    	public void  scrollPageByXpath(String xpath) throws Exception {
    	    List<WebElement> initialItem = selenium.findElements(By.xpath(xpath));
    	    int initialCount =initialItem.size();
    		scrollPage();
    	    List<WebElement> updatedItem = selenium.findElements(By.xpath(xpath));
    	    int itemSize =updatedItem.size();

    	    for(initialCount=0;initialCount<=itemSize;initialCount++) {
    	    	scrollPage();
    	    	
    	    }
	
    	}
    	
    	public void  scrollPageByXpath1(String xpath) throws Exception {
    	    List<WebElement> initialItem = selenium.findElements(By.xpath(xpath));
    	    int prvSize =initialItem.size();
    		scrollPage();
    	    List<WebElement> updatedItem = selenium.findElements(By.xpath(xpath));
    	    int newSize =updatedItem.size();

    	    while (true) {
    	    	if(prvSize == newSize) {
    	    		break;
    	    	}else if (newSize > prvSize) {
    	    		prvSize = newSize;
    	    		scrollPage();
    	    		updatedItem = selenium.findElements(By.xpath(xpath));
    	    	    newSize =updatedItem.size();
    	    	}
    	    }

	
    	}
    	public void  scrollPage()throws Exception {
    		
    		Robot robot = new Robot();
       		int counter =50;
    	    for(int i=0;i<=counter;i++){  
    	    	robot.keyPress(KeyEvent.VK_PAGE_DOWN);
        		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
    	    } 
    	    sleep(100);
    	}
    	
    	public void takeScreenShot() throws IOException{
    		File scrFile = ((TakesScreenshot)selenium).getScreenshotAs(OutputType.FILE);
    		
    		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    		Calendar cal = Calendar.getInstance();
    		System.out.println(dateFormat.format(cal.getTime()));
    		
    		String scrFilepath = scrFile.getAbsolutePath();
    		System.out.println("scrFilepath: " +scrFilepath);
    		
    		File currentDirFile = new File("Screenshots");
    		String path = currentDirFile.getAbsolutePath();
    		System.out.println("path: " +path+"+++");
    		
    		System.out.println("****\n"+path+"\\screenshot"+dateFormat.format(cal.getTime())+".jpg");
    		FileUtils.copyFile(scrFile, new File(path+"\\screenshot"+dateFormat.format(cal.getTime())+".jpg"));
    		
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
	
	//Logging Object
	public void log(String msg){
		log(msg);
	}
	
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}


}

