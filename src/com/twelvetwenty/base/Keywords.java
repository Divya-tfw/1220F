package com.twelvetwenty.base;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

import com.twelvetwenty.constants.DBConstants;
import com.twelvetwenty.constants.GlobalVariables;
import com.twelvetwenty.constants.TestBaseConstants;
import com.twelvetwenty.db.DataBaseConnection;
import com.twelvetwenty.util.ExcelTestUtil;
import com.twelvetwenty.util.Logs;
import atu.testng.reports.utils.Utils;

/********************************************************************************************
 *		Author 						:	DivyaRaju.R
 *		LastModifiedDate	:	8th feb 2014
 *		ClassName					:	Keywords
 *		Description				:	This class is extended by Test base class contains methods for launching browser,
 *												getxpath key
 *
*********************************************************************************************/

public class Keywords extends ExcelTestBase

{
	
	/**************************************************************************************
	 *	 Author							 :	Divya Raju.R 
	 *	 LastModifiedDate		 : 24-11-2013
	 *  Keyword 						 : navigate 
	 *  Description 					 : Launches browser & navigates to specified URL
	 * @throws InterruptedException 
	 *  
	 ***************************************************************************************/
	public static void navigate() throws InterruptedException
	{
		String browser=GlobalVariables.CONFIG.getProperty("browserType");
		//System.out.println("Browser selected is ----->"+browser);
		String waitVal=GlobalVariables.CONFIG.getProperty("implicitWaitVal");
		long waitMval=Long.parseLong(waitVal);
		
		
		if(browser.equalsIgnoreCase(TestBaseConstants.BROWSER_FIREFOX))
		{
			
			
			if(GlobalVariables.CONFIG.getProperty("browserPath").equalsIgnoreCase("Default"))
			{
				GlobalVariables.driver = new FirefoxDriver();
				//GlobalVariables.driver.manage().deleteAllCookies();
				GlobalVariables.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				GlobalVariables.driver.manage().window().maximize();
			}
			else
			{
				String pathVal=cleanPath(GlobalVariables.CONFIG.getProperty("browserPath"));
				FirefoxBinary binary = new FirefoxBinary(new File(pathVal));
				FirefoxProfile profile = new FirefoxProfile();
				GlobalVariables.driver = new FirefoxDriver(binary, profile);
			}
		}
		else if(browser.equalsIgnoreCase(TestBaseConstants.BROWSER_CHROME))
		{
			
			if(GlobalVariables.CONFIG.getProperty("browserPath").equalsIgnoreCase("Default"))
			{
				System.setProperty("webdriver.chrome.driver","./exes/chromedriver.exe");
			    ChromeOptions options = new ChromeOptions();
			    options.addArguments("--test-type");
			    GlobalVariables. driver = new ChromeDriver(options);
			    GlobalVariables.driver.manage().timeouts().implicitlyWait(30, TimeUnit.MINUTES);			
			    GlobalVariables.driver.manage().window().maximize();
			}
			else
			{
				ChromeOptions options = new ChromeOptions();		
				String pathVal=cleanPath(GlobalVariables.CONFIG.getProperty("browserPath"));
				options.setBinary(new File(pathVal));
				System.setProperty("webdriver.chrome.driver",	"./exes/chromedriver.exe");
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				
				GlobalVariables.driver = new ChromeDriver(capabilities);
			}
			
		}
			
			
		else if(browser.equalsIgnoreCase(TestBaseConstants.BROWSER_IE))
		{
			/*System.setProperty("webdriver.ie.driver",	"./exes/IEDriverServer.exe");
			driver  = new InternetExplorerDriver();*/
			
			if(GlobalVariables.CONFIG.getProperty("browserPath").equalsIgnoreCase("Default"))
			{
				System.setProperty("webdriver.ie.driver", "./exes/IEDriverServer.exe");
				GlobalVariables.driver = new InternetExplorerDriver();						
				GlobalVariables.driver.manage().deleteAllCookies();
				GlobalVariables.driver.manage().timeouts().implicitlyWait(2, TimeUnit.MINUTES);
				GlobalVariables.driver.manage().window().maximize();
				Thread.sleep(2000);
			}
			
			
			
				
		}
		else 
		{
			GlobalVariables.driver= new FirefoxDriver();
			GlobalVariables.driver.manage().deleteAllCookies();
			GlobalVariables.driver.manage().timeouts().implicitlyWait(waitMval, TimeUnit.MINUTES);
			GlobalVariables.driver.manage().window().maximize();	
		}
		/*ATUReports.setWebDriver(driver);
		ATUReports.indexPageDescription = "12Twenty ATU Reports";*/
	}
	
	
		
		/**************************************************************************************
		 *	 Author							 :	Divya Raju.R
		 *	 LastModifiedDate		 : 29-4-2013
		 *  Keyword 						 : dbUpdate 
		 *  Description 					 : It performs updation of status from not started to in progress status
		 *  
		 ***************************************************************************************/
		
		public static void dbUpdate(boolean status,String scriptNameVal,String identifierVal )
		
		{
			
			 if (status==false &&
					 GlobalVariables.CONFIG.getProperty("automationResults").equalsIgnoreCase("Y"))
			 {
				DataBaseConnection.dataBaseUpdate
						(GlobalVariables.ipaddressval,
								GlobalVariables.CONFIG.getProperty("dataBaseName"), 
								GlobalVariables.CONFIG.getProperty("userName"),
								GlobalVariables.CONFIG.getProperty("passWord"), 
								GlobalVariables.CONFIG.getProperty("tableName"), 
						DBConstants.dbTestStatus, 
						GlobalVariables.CONFIG.getProperty("dbStatus_at_execution"),
						DBConstants.dbTestCaseID, scriptNameVal,
					 	DBConstants.dbAutomationID,identifierVal,
						DBConstants.dbTestCaseIteration,GlobalVariables.iterationVal );
			 }
		}
		
		/**************************************************************************************
		 *	 Author							 :	Divya Raju.R
		 *	 LastModifiedDate		 : 29-4-2013
		 *  Keyword 						 : webdriverWait 
		 *  Description 					 : It makes webdriver to wait until specific operation is complete
		 *  
		 ***************************************************************************************/
		public static void webdriverWait(String waitVal)
		{
			int timeOutInSec = Integer.parseInt(waitVal);
							
			try 
			{
			/*	if(CONFIG.getProperty("browserType").equalsIgnoreCase((TestBaseConstants.browserChrome)))
				{*/
				Thread.sleep(timeOutInSec * 1000);
			/*	}*/
			} 
			catch (InterruptedException e)
			{
				GlobalVariables.APPICATION_LOGS.debug("Cannot find object with key : " +e.getMessage());
				CustomVerification.verifyContent(false,"Error while waiting ");
			}
		}
		
		/**************************************************************************************
		 *	 Author							 :	Divya Raju.R
		 *	 LastModifiedDate		 : 29-4-2013
		 *  Keyword 						 : errorReporter 
		 *  Description 					 : It performs operation of writting to log files & data files & takes screen
		 *  											 shots in case of failure
		 *  
		 ***************************************************************************************/
	public static void errorReporter(String custErrMsg,String exceptionErrmsg)	
	{
		// ExcelTestUtil.reportDataResult(testSuite, scriptName, testCaseIdentifier,"Fail" );	
		 if(custErrMsg!=null)
		 {
			 GlobalVariables.result="Failed ----->" +custErrMsg;					 
			 GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);		
			 GlobalVariables.errormsgVal=custErrMsg;
			 if(GlobalVariables.flag2==0)
			 {
			 ExcelTestUtil.reportError
			 (GlobalVariables.testSuite,
					 GlobalVariables.scriptName, 
					 GlobalVariables.testCaseIdentifier,
					 GlobalVariables.result);	
			 GlobalVariables.flag2=1;
			 }
			 
			 Logs.errorLog("Fail ________ " + custErrMsg);				 				
			 CustomVerification.verifyContent(false,"Fail ________ " + custErrMsg);						
			 Reporter.log("Failed---------->"+custErrMsg,true);
			 GlobalVariables.APPICATION_LOGS.error("Failed ------------------------------->"+custErrMsg);
		 }
		 else 
		 {
			 GlobalVariables.result="Failed ----->" +exceptionErrmsg;					 
			 GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);		
			 GlobalVariables.errormsgVal=exceptionErrmsg;
			 if(GlobalVariables.flag2==0)
			 {
			 ExcelTestUtil.reportError(GlobalVariables.testSuite,
					 GlobalVariables.scriptName, 
					 GlobalVariables.testCaseIdentifier,
					 GlobalVariables.result);	
			 GlobalVariables.flag2=1;
			 }
			 Logs.errorLog("Fail - ------>" + exceptionErrmsg);				 				
			 CustomVerification.verifyContent(false,"Fail ------> " + exceptionErrmsg);						
			 Reporter.log("Failed--"+exceptionErrmsg,true);
			 GlobalVariables.APPICATION_LOGS.error("Failed ------------------------------->"+exceptionErrmsg);
		 }
		//take screen shots for failed
		 GlobalVariables.screenshotFilename=
				 GlobalVariables.suiteName+"_"+GlobalVariables.scriptName+"_"+
						 GlobalVariables.testCaseIdentifier+".jpg";									
		 ExcelTestUtil.takeScreenShot(
				 GlobalVariables.screenShotsPath+ "//"+ 
						 GlobalVariables.screenshotFilename);
	}
		
	
	public static String input(String orObject,String inputVal,String msg)
	{
		GlobalVariables.APPICATION_LOGS.info(msg);
		Logs.infoLog(msg);
		try 
		{			 
			if(GlobalVariables.OR.getProperty(orObject) != null )
			{
				//getObject(orObject).sendKeys(inputVal);
				getObjectValue(orObject).sendKeys(inputVal);
				GlobalVariables.result=TestBaseConstants.RESULT_PASSVALUE;
				GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
				rATUStatus(GlobalVariables.result,msg);
				
			}
		}
		catch(Exception e)
		{
			GlobalVariables.exceptionMsgVal=e.getMessage();
			keywordsErrormsg(GlobalVariables.errormsg,GlobalVariables.exceptionMsgVal,"Error while executing input keyword");
			GlobalVariables.result=TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error("Error while executing input keyword");
			Logs.errorLog("Error while executing input keyword");
			rATUStatus(GlobalVariables.result,msg);
		}
		return GlobalVariables.result;
		
	}
		

	public static void keywordsErrormsg(String custErrMsg,String exceptionErrmsg,String keywordmsg) 
	{
		 if(custErrMsg!=null)
		 {
			CustomVerification.verifyContent(false,keywordmsg +" "+ custErrMsg);						
			ExcelTestUtil.custReporter(keywordmsg +" "+ custErrMsg);
			GlobalVariables.APPICATION_LOGS.error(keywordmsg+" "+custErrMsg);
		 }
		 else
		 {
			 CustomVerification.verifyContent(false,keywordmsg +" "+ exceptionErrmsg);						
				ExcelTestUtil. custReporter(keywordmsg +" "+ exceptionErrmsg);
				GlobalVariables.APPICATION_LOGS.error(keywordmsg+" "+exceptionErrmsg);
		 }
		 GlobalVariables.screenshotFilename=
				 GlobalVariables.suiteName+"_"+
						 GlobalVariables.scriptName+"_"+
						 GlobalVariables.testCaseIdentifier+".jpg";									
		 ExcelTestUtil.takeScreenShot(GlobalVariables.screenShotsPath+ "//"+ GlobalVariables.screenshotFilename);
	}
	
	public static String click(String orObject,String msg)
	{
		GlobalVariables.APPICATION_LOGS.info(msg);
		Logs.infoLog(msg);
		try 
		{			 
			if(	GlobalVariables.OR.getProperty(orObject) != null )
			{
				//getObject(orObject).click();
				getObjectValue(orObject).click();
				GlobalVariables.result=TestBaseConstants.RESULT_PASSVALUE;
				GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
				rATUStatus(GlobalVariables.result,msg);
			}
		}
		
			catch(Exception e)
			{
				GlobalVariables.exceptionMsgVal=e.getMessage();
				keywordsErrormsg(GlobalVariables.errormsg,GlobalVariables.exceptionMsgVal,"Error while executing Click keyword");
				GlobalVariables.result=TestBaseConstants.RESULT_FAILVALUE;
				GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
				GlobalVariables.APPICATION_LOGS.error("Error while executing Click keyword");
				Logs.errorLog("Error while executing Click keyword");
				rATUStatus(GlobalVariables.result,msg);
			}
	
		return GlobalVariables.result;
	}
	
	public static String clear(String orObject,String msg)
	{
		GlobalVariables.APPICATION_LOGS.info(msg);
			Logs.infoLog(msg);
		try 
		{			
			if(GlobalVariables.OR.getProperty(orObject) != null )
			{
				//getObject(orObject).clear();
				getObjectValue(orObject).clear();
				GlobalVariables.result=TestBaseConstants.RESULT_PASSVALUE;
				GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
			}
		}
	
			catch(Exception e)
			{
				GlobalVariables.exceptionMsgVal=e.getMessage();
				keywordsErrormsg(GlobalVariables.errormsg,GlobalVariables.exceptionMsgVal,"Error while executing Clear keyword");
				GlobalVariables.result=TestBaseConstants.RESULT_FAILVALUE;
				GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
				GlobalVariables.APPICATION_LOGS.error("Error while executing clear keyword");
				Logs.errorLog("Error while executing Clear keyword");
				rATUStatus(GlobalVariables.result,msg);
			}
	
		return GlobalVariables.result;
	}
	
	
	public static String verifyContent(String orObject,String expected,String msg)
	{
		String err="Error while executing verifyContent keyword";
		GlobalVariables.APPICATION_LOGS.info(msg);
		Logs.infoLog(msg);
		try
		{
			if((GlobalVariables.OR.getProperty(orObject) != null )&& expected!=null)
			{
				WebDriverWait wait = new WebDriverWait(GlobalVariables.driver, 5);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(GlobalVariables.OR.getProperty(orObject))));
				String Actual = GlobalVariables.driver.findElement(By.xpath(GlobalVariables.OR.getProperty(orObject))).getText();						
				Logs.infoLog("Actual data obtained is ---->" + Actual);
				GlobalVariables.result	=	CustomVerification.assertEqualsTest(Actual,expected);				
				GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
				if(!GlobalVariables.result.equals("Pass"))
				{
					GlobalVariables.errormsg=GlobalVariables.result;
					keywordsErrormsg(GlobalVariables.errormsg,"",err);
				}
				
			}
		}
		catch(Exception e)
		{
			GlobalVariables.exceptionMsgVal=e.getMessage();			
			keywordsErrormsg(GlobalVariables.errormsg,GlobalVariables.exceptionMsgVal,err);
			GlobalVariables.result=TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(err);
			Logs.errorLog(err);
		}
		return GlobalVariables.result;
	}
	
	public static WebElement getObjectValue(String orObject)
	{
		int flag=0;
		WebElement temp=null;
		if(GlobalVariables.OR.getProperty(orObject) != null )
		{	//if(OR.getProperty(orObject) != null )	
			String s= GlobalVariables.OR.getProperty(orObject);
		//	System.out.println(s);
			String[] spiltVal1= s.split("#");		
			for (int i = 0; i < spiltVal1.length; i++) 
			{		//for (int i = 0; i < spiltVal1.length; i++) 
				String[] spiltVal= spiltVal1[i].split(",");
				for (int j = 0; j < spiltVal.length; j++) 
				{		// for (int j = 0; j < spiltVal.length; j++) 
					if(j==1)
					{// start 	if(j==1)
						String locator=spiltVal[0].trim();// triming is done only on either sie & not in between
						String locatorValue=spiltVal[1].trim();//triming is done only on either sie & not in between
						
						if(locator.equals(null) || locatorValue.equals(null))
						{// start for if(locator.equals(null) || locatorValue.equals(null))
							System.out.println("Locator is null or value is null enter correct details");
						}// end for if(locator.equals(null) || locatorValue.equals(null))
						else
						{// start for else
							if(flag==0)
							{// start for if flag==0
								 WebDriverWait wait = new WebDriverWait(GlobalVariables.driver,15);
								switch(locator)
								{// start of switch case
									case "id": if(locator.equalsIgnoreCase("id"))
														{
															flag=1;
															/*System.out.println("Chosen Val is Id");*/														
															wait.until(ExpectedConditions.presenceOfElementLocated(By.id((locatorValue))));
															temp =GlobalVariables.driver.findElement(By.id(locatorValue));
															break;						
														}
								case "name": if(locator.equalsIgnoreCase("name"))
														{
															flag=1;
															/*System.out.println("Chosen Val is name");	*/												
															wait.until(ExpectedConditions.presenceOfElementLocated(By.name((locatorValue))));
															temp =GlobalVariables.driver.findElement(By.name(locatorValue));
															break;
														}
								case "classname": if(locator.equalsIgnoreCase("classname"))
															{
																flag=1;
																/*System.out.println("Chosen Val is classname");*/
																wait.until(ExpectedConditions.presenceOfElementLocated(By.className((locatorValue))));
																temp =GlobalVariables.driver.findElement(By.className(locatorValue));
																break;
															}
								case "linkText": if(locator.equalsIgnoreCase("linkText"))
															{
																flag=1;
																/*System.out.println("Chosen Val is linktext");*/
																wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText((locatorValue))));
																temp =GlobalVariables.driver.findElement(By.linkText(locatorValue));
																break;
															}
								
								case "partialLinkText":if(locator.equalsIgnoreCase("partialLinkText"))
																{
																	flag=1;
																	/*System.out.println("Chosen Val is partialLinkText");*/
																	wait.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText((locatorValue))));
																	temp =GlobalVariables.driver.findElement(By.partialLinkText(locatorValue));
																	break;
																}
								case "tagName":if(locator.equalsIgnoreCase("tagName"))
															{
																flag=1;
																/*System.out.println("Chosen Val is tagName");*/
																wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName((locatorValue))));
																temp =GlobalVariables.driver.findElement(By.tagName(locatorValue));
																break;
															}
								case "cssSelector":if(locator.equalsIgnoreCase("cssSelector"))
															{
																flag=1;
																/*System.out.println("Chosen Val is cssSelector");*/
																wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector((locatorValue))));
																temp =GlobalVariables.driver.findElement(By.cssSelector(locatorValue));
																break;
															}
								case "xpath":if(locator.equalsIgnoreCase("xpath"))
														{
															flag=1;
															/*System.out.println("Chosen Val is xpath");
															System.out
																	.println("Locator value is -->"+locatorValue);*/
															wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath((locatorValue))));
															temp =GlobalVariables.driver.findElement(By.xpath(locatorValue));
															break;
														}
							    default: 	flag=1;
							    				System.out.println("locator s not found"); 
							    				break;
								}// end of switch case
						
						
					}// end for if flag==0
						
				}// end for else
						
				}	// end if(j==1)
			}//for (int j = 0; j < spiltVal.length; j++) 
			
			}//for (int i = 0; i < spiltVal1.length; i++) 
			
		}//if(OR.getProperty(orObject) != null )
		flag=0;
		return temp;
		}
	
	
	public static boolean setDriverToATU()
	{
		boolean setDriver=false;
		if (GlobalVariables.CONFIG.getProperty("rATUGenerate").equalsIgnoreCase("Y")&& 
				GlobalVariables.CONFIG.getProperty("reporterLog").equalsIgnoreCase("N") && 
				(GlobalVariables.driver!=null))
		{
			ATUReports.setWebDriver(GlobalVariables.driver);
			setDriver=true;
		}
		return setDriver;
	}
	
	public static void rATUConfigInfo(String indexPageDescription,String testReqCoverageInfo,
			String currentRunDescription,
		String authorName,
		String version)
	{
		boolean setATU=setDriverToATU();
		if (setATU==true)
				{
		ATUReports.indexPageDescription = indexPageDescription;
		 ATUReports.setTestCaseReqCoverage(testReqCoverageInfo);
		 ATUReports.currentRunDescription = currentRunDescription;
		 ATUReports.setAuthorInfo(authorName, Utils.getCurrentTime(),version);
		
				}
	}
	public static void rATUStatus(String status,String msg)
	{	
		boolean setATU=setDriverToATU();
	
	if (setATU==true)
	{	
		if(status.contains(TestBaseConstants.RESULT_PASSVALUE))
		{
			 ATUReports.add(msg, LogAs.PASSED, new CaptureScreen(
						ScreenshotOf.DESKTOP));
		}
		else if(status.contains(TestBaseConstants.RESULT_FAILVALUE))
		{
			 ATUReports.add(msg, LogAs.FAILED, new CaptureScreen(
						ScreenshotOf.DESKTOP));
		}
		else if(status.contains(TestBaseConstants.INFO_VALUE))
		{
			ATUReports.add(msg, LogAs.INFO, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}
	}
	}
	
	public static void printValidationSteps(String desc,String expected,String actual,String status)
	{
		
		boolean setATU=setDriverToATU();
		
		if (setATU==true)
		{	
		if(status.equalsIgnoreCase(TestBaseConstants.RESULT_PASSVALUE))
		{
			 ATUReports.add(desc,expected, actual,LogAs.PASSED, new CaptureScreen(
						ScreenshotOf.DESKTOP));
		}
		else if(status.equalsIgnoreCase(TestBaseConstants.RESULT_FAILVALUE))
		{
			 ATUReports.add(desc,expected, actual,LogAs.FAILED, new CaptureScreen(
						ScreenshotOf.DESKTOP));
		}
		else if(status.equalsIgnoreCase(TestBaseConstants.INFO_VALUE))
		{
			ATUReports.add(desc,expected, actual, LogAs.INFO, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}
	}
		
		
		
	}
	


	public static void scrollPageUp(Object i)
	{
		JavascriptExecutor jse = (JavascriptExecutor)GlobalVariables.driver;
		jse.executeScript("scroll(0, "+i+")"); 
	}
	
}


















/*public static WebElement getObject(String xpathKey)
{			
	WebElement we = null;
	
	try
	{
	
		WebDriverWait wait = new WebDriverWait(GlobalVariables.driver,15);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(OR.getProperty(xpathKey))));
		//System.out.println(xpathKey+" is available on the page.");
		
		we = GlobalVariables.driver.findElement(By.xpath(GlobalVariables.OR.getProperty(xpathKey)));
		GlobalVariables.APPICATION_LOGS.info("Object obtained is : " +xpathKey);
		//System.out.println(we);
		//Thread.sleep(500);
		if (we == null)
			throw new Throwable ("Element : "+xpathKey+" is not found on the page.");
		return we;
	}
	catch(Throwable t)
	{			
					
		GlobalVariables.APPICATION_LOGS.debug("Cannot find object with key : " +xpathKey);
		CustomVerification.verifyContent(false,"Fail - " + t.getMessage());
			return null;				
	}

}*/




/*public static String frameSwitch(String orObject,String msg)
{
	try 
	{
		 APPICATION_LOGS.info(msg);
		ExcelLogs.infoLog(msg);
		if(	OR.getProperty(orObject) != null )
		{
			WebElement frameElement = driver.findElement(By.xpath(OR.getProperty(orObject)));
			 driver.switchTo().frame(frameElement);	
			result=TestBaseConstants.resultPassVal;
			testusappend=ExcelTestUtil.runStatusAdd(result);
		}
	}
	catch(Exception e)
	{
		exceptionMsgVal=e.getMessage();
		String err="Error while executing frameSwitch keyword";
		keywordsErrormsg(errormsg,exceptionMsgVal,err);
		result=TestBaseConstants.resultFailVal;
		testusappend=ExcelTestUtil.runStatusAdd(result);
		APPICATION_LOGS.error(err);
		ExcelLogs.errorLog(err);
	}
	return result;

}*/
/*public static String frameSwitchBack(String msg)
{
	 APPICATION_LOGS.info(msg);
		ExcelLogs.infoLog(msg);	
	try 
	{		
			driver.switchTo().defaultContent();	
			result=TestBaseConstants.resultPassVal;
			testusappend=ExcelTestUtil.runStatusAdd(result);
		
	}
	catch(Exception e)
	{
		exceptionMsgVal=e.getMessage();
		String err="Error while executing frameSwitch keyword";
		keywordsErrormsg(errormsg,exceptionMsgVal,err);
		result=TestBaseConstants.resultFailVal;
		testusappend=ExcelTestUtil.runStatusAdd(result);
		APPICATION_LOGS.error(err);
		ExcelLogs.errorLog(err);
	}
	return result;

}*/






/*public static String dataFetchFromConfig(String property)
{
	String propertyValue=null;
	
	try
	{
		Xls_Reader config = new Xls_Reader(classPath+"\\excel_suite\\Config.xlsx" );
		
		int rc1=config.getCellRowNum("Test_Configurations", "Parameters", property);
		//System.out.println(rc1);
		propertyValue=config.getCellData("Test_Configurations", "Values", rc1);
		//System.out.println(userName);		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	return propertyValue;
}*/

	



/*public static void ATUReport(String message,String status)
{
try
{
  if(CONFIG.getProperty("rATUGenerate").equals("Y") && CONFIG.getProperty("reporterLog")=="N" )
  {
	 if(status.equalsIgnoreCase("Pass"))
	 {
		 ATUReporter.add("Open Browser", LogAs.PASSED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
	 }
  }
 
}
catch(Exception e)
{
	System.out.println("ATU error"+e.getMessage());
}
}*/
/*	@Parameters({"browser","port"})
	public static void browserUsingGrid()
	{
		 if(ur.equalsIgnoreCase("Y"))
			 {
				 ReportUtil.addTestCase(testCaseIdentifier,startReportTime,reportendTime,"Fail",logFolderPath, screenShotsPath+"/"+screenshotFilename);
			 }
	}*/

