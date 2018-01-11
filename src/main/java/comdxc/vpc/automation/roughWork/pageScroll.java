package comdxc.vpc.automation.roughWork;

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class pageScroll {
	//http://www.seleniumeasy.com/selenium-tutorials/scrolling-web-page-with-selenium-webdriver-using-java
	//https://stackoverflow.com/questions/12293158/page-scroll-up-or-down-in-selenium-webdriver-selenium-2-using-java

	public static void main(String[] args) {


	}
	
	public void  pageScroll() throws Exception {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
	}

}
