/*package com.twelvetwenty.base;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;


import com.twelvetwenty.constants.GlobalVariables;




public class Grid extends ExcelTestBase
{
	*//*********************************************************************************
	 * 	Author						:	Divya Raju.R
	 * 	LastModifiedDate	:	30-11-2013  
	 * 	Annotation				:	@BeforeMethod
	 * 	MethodName			: 	beforeMethod
	 * 	Description				:	Performs set of  operations required to run grid functionality
	 **********************************************************************************//*	
	@Parameters({"browsertype","port","ipValue"})
	 @BeforeMethod
	 public void beforeMethod(String browser,String port,String ipValue)
	 {
		 GlobalVariables.startTime=TestUtil.now(TestBaseConstants.TRANSACTION_TIME_FORMAT);		
		 GlobalVariables.startReportTime = TestUtil.now(TestBaseConstants.USR_FILE_FORMAT);		
		 DesiredCapabilities capability= new DesiredCapabilities();
		  capability.setBrowserName(browser);		

		 //capability.setPlatform(Platform.VISTA);
		 //System.out.println("Running on browser ---------------->"+browser);
		 //String gb="Y";
		 if(GlobalVariables.CONFIG.getProperty("runInGrid").equalsIgnoreCase(TestBaseConstants.RUNMODE_YES))
		 {
			 //System.out.println("Calling grid");
			 GlobalVariables.browser=browser;
			 GlobalVariables.port=port;
			 GlobalVariables.gridIpAddress=ipValue;
			  
		 }
		 else 
		 {
			 //System.out.println("Grid not selected");
			 
				//XMLKeywords.navigate();
			 
		 }
    	
		 
		
	 }
 
	 public static   WebDriver getRemoteWebDriver(String browsertype,String port,String ipValue)
	 {
		 String ip="http://"+ipValue+":";
		 String waitVal=GlobalVariables.CONFIG.getProperty("implicitWaitVal");
			long waitMval=Long.parseLong(waitVal);
		 try 
		 {
			 if(browsertype.equalsIgnoreCase("IE"))
			 {
				 DesiredCapabilities capability= new DesiredCapabilities();
			// DesiredCapabilities capability = DesiredCapabilities.internetExplorer();	
				// capability.setPlatform(Platform.VISTA);
				 GlobalVariables.driver= new RemoteWebDriver(new URL(ip.concat(port).concat("/wd/hub")), capability);
				 GlobalVariables.driver.manage().timeouts().implicitlyWait(waitMval, TimeUnit.MINUTES);
				 GlobalVariables.driver.manage().window().maximize();	
			 }
			 else if(browsertype.equalsIgnoreCase("Firefox"))
			 {
				 DesiredCapabilities capability = DesiredCapabilities.firefox();		
				 GlobalVariables.driver= new RemoteWebDriver(new URL(ip.concat(port).concat("/wd/hub")), capability);
				 GlobalVariables.driver.manage().timeouts().implicitlyWait(waitMval, TimeUnit.MINUTES);
				 GlobalVariables.driver.manage().window().maximize();	
			 }
			 else if(browsertype.equalsIgnoreCase("Chrome"))
			 {				 
				 DesiredCapabilities capability = DesiredCapabilities.chrome();
				 GlobalVariables.driver= new RemoteWebDriver(new URL(ip.concat(port).concat("/wd/hub")), capability);
				 GlobalVariables.driver.manage().timeouts().implicitlyWait(waitMval, TimeUnit.MINUTES);
				 GlobalVariables.driver.manage().window().maximize();	
			 }
			 else if(browsertype.equalsIgnoreCase("canary"))
			 {
				 DesiredCapabilities capability = DesiredCapabilities.chrome();
				 GlobalVariables.driver= new RemoteWebDriver(new URL(ip.concat(port).concat("/wd/hub")), capability);
				 GlobalVariables.driver.manage().timeouts().implicitlyWait(waitMval, TimeUnit.MINUTES);
				 GlobalVariables.driver.manage().window().maximize();	
			 }
		 
		 }
		 catch (Exception e) 
		 {
			 
			 GlobalVariables.APPICATION_LOGS.error("Failure during sending driver  ------------------------------->"+e.getMessage());
				Reporter.log("Failure during sending driver ------------------------------->"+e.getMessage(),true);
		 }
		 return GlobalVariables.driver;
		 }
}



 * Note : we can run node with only browser val set ex: ff in testNG.xml & calling node as FF val 
 * in another instance different calling different browser val in grid & val changed in testNG .xml as chrome
 * In local machine
 * 
 * To run in same machine
 * Tumblr site works fine only in IE 10 version & not proper in IE 9
 *   
 *   to run in FF browser
 *  java -jar selenium-server-standalone-2.43.1.jar -role hub	  
 *  java -jar selenium-server-standalone-2.43.1.jar -role webdriver -hub http://localhost:4444/grid/register -port 5556 -browser browserName=firefox
 * 
 *  
 *  
 *  to run in chrome
 *   java -jar selenium-server-standalone-2.43.1.jar -role hub	  
 *   send   chrome driver file path
 *   java -Dwebdriver.chrome.driver=C:\exes\chromedriver.exe -jar  selenium-server-standalone-2.43.1.jar -role webdriver -hub http://localhost:4444/grid/register -port 5555 -browser browserName=chrome
 *   
 *   to run in chrome canary
 *   java -jar selenium-server-standalone-2.43.1.jar -role hub	
 *   java -Dwebdriver.chrome.driver=C:\exes\chromedriver.exe -jar  selenium-server-standalone-2.43.1.jar -role webdriver -hub http://localhost:4444/grid/register -port 5555 -browser browserName=chrome -Dchrome_options=C:\Users\DivyaR\FF\Chrome SxS\Application\\chrome.exe
 *   
 *   to run in IE
 *    java -jar selenium-server-standalone-2.43.1.jar -role hub
 *    java -Dwebdriver.ie.driver=C:\exes\IEDriverServer.exe -jar  selenium-server-standalone-2.43.1.jar -role webdriver -hub http://localhost:4444/grid/register -port 5555 -browser browserName=IE
 *    
 *    to run in different versions of browsers
 *    cmd s in different cmds
 *    java -jar selenium-server-standalone-2.43.1.jar -role hub	 
 *    java -jar selenium-server-standalone-2.43.1.jar -role webdriver -hub http://localhost:4444/grid/register -port 5556 -browser browserName=firefox firefox_binary=C:\Users\DivyaR\FF\Mozilla Firefox23\firefox.exe
 *    
 *    
 *    Running nodes in different machines
 *     use ip address/ host name  of my laptop while passing in http
 *    ff
 *    java -jar selenium-server-standalone-2.43.1.jar -role hub	
 *    java -jar selenium-server-standalone-2.43.1.jar -role webdriver -hub http://172.18.21.26:4444/grid/register -port 5556 -browser browserName=firefox
 *    
 *    chrome
 *     java -jar selenium-server-standalone-2.43.1.jar -role hub	
 *      java -Dwebdriver.chrome.driver=C:\exes\chromedriver.exe -jar  selenium-server-standalone-2.43.1.jar -role webdriver -hub http://172.18.21.26:4444/grid/register -port 5555 -browser browserName=chrome
 *     
 *     IE
 *     java -jar selenium-server-standalone-2.43.1.jar -role hub
 *     java -Dwebdriver.ie.driver=C:\exes\IEDriverServer.exe -jar  selenium-server-standalone-2.43.1.jar -role webdriver -hub http://172.18.21.26:4444/grid/register -port 5555 -browser browserName=IE
 *     
 *     
 
*/