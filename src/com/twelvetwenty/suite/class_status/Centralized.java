package com.twelvetwenty.suite.class_status;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.twelvetwenty.base.App_Specific_Keywords;
import com.twelvetwenty.constants.GlobalVariables;
import com.twelvetwenty.constants.TestBaseConstants;
import com.twelvetwenty.util.ExcelTestUtil;
import com.twelvetwenty.util.Logs;

public class Centralized extends App_Specific_Keywords
{

	/*****************************************************************************************************************
  	 * 	Author						:	Divya Raju.R
  	 * 	LastModifiedDate			:	19-6-2015  	  
  	 * 	Annotation					:	@Test
  	 * 	MethodName					: 	test_Centralized
  	 * 	Description					:	This method is used to perform required functionality test on app
  	 * 
  	 ***************************************************************************************************************/	
	@Test(dataProvider="ExcelData")
	public void test_Centralized(Hashtable<String,String> data) 
	{
		//Start of script
		GlobalVariables.APPICATION_LOGS.info("--------Execution of test---- "+
				GlobalVariables.scriptName+"  Started--------");
		GlobalVariables.testCaseIdentifier=data.get(TestBaseConstants.AUTOMATION_ID);
		
		Logs.openLogFile(GlobalVariables.logFolderPath, GlobalVariables.testCaseIdentifier); 	
		GlobalVariables.dataRunStatus=data.get(TestBaseConstants.DATA_RUNMODE);			 
		GlobalVariables.suiteRunStatus =	ExcelTestUtil.suiteRunstatus(GlobalVariables.suiteName);
		GlobalVariables. testRunStatus=ExcelTestUtil.testRunStatus(GlobalVariables.suiteName,GlobalVariables.scriptName); 			
		boolean trs = ExcelTestUtil.checkSkiptests(GlobalVariables.suiteRunStatus,GlobalVariables.testRunStatus,
				GlobalVariables.dataRunStatus);
		
		dbUpdate(trs,GlobalVariables.scriptName,GlobalVariables.testCaseIdentifier);
		Logs.infoLog("--------Execution of test---- "+GlobalVariables.scriptName+"  Started----");	
			

	 try
	 {
	
	 	Logs.infoLog("*****Launch Browser******");
	 	
		 
		// Launch Browser
	 	navigate();	
	 	rATUConfigInfo(TestBaseConstants.ATU_INDEX_PAGE_DESCRIPTION,							
	 			data.get(TestBaseConstants.SUB_REPORT_NAME),
				 GlobalVariables.scriptName,
				 TestBaseConstants.AUTHOR_NAME,
				 TestBaseConstants.VERSION_VALUE)	;
	 	
	 	 rATUStatus(TestBaseConstants.INFO_VALUE,
	 			 "Open the browser");
		 		 
		 //navigate to url of application
		 String url=data.get(TestBaseConstants.SCHOOL_NAME);
		 launchSite(
		 cleanPath(GlobalVariables.CONFIG.getProperty(TestBaseConstants.SITE
		 +url)));
	 	
		 //Call the login method to perform login with valid credentials & 
		 //click on data analysis tab	
		 
		 loginToSite(data.get(TestBaseConstants.WAIT_VALUE),
		 GlobalVariables.CONFIG.getProperty(TestBaseConstants.SITE_USER_NAME),
		 cleanContent(GlobalVariables.CONFIG.getProperty(TestBaseConstants.SITE_PASSWORD)));
		 webdriverWait(data.get(TestBaseConstants.WAIT_VALUE));			
		
		 
		 //click standard reports
		 click(TestBaseConstants.BUTTON_STANDARD_REPORTS,"Click on Standard Reports");
			 
		 Thread.sleep(3000);
		 		 
		 // Click on Business week report link
		 click("lnk_Bussiness_week_report","Click on Business week report link");
		 
		//click on Table report
		 GlobalVariables.driver.findElement(By.xpath
				 ( "//tr[td[contains(text(),'"+
		 data.get(TestBaseConstants.SUB_REPORT_NAME)+"')]]//*[text()='Generate']"))
				 .click();
		 Logs.infoLog("Click on "+ data.get(TestBaseConstants.SUB_REPORT_NAME)+" report");		 
		 rATUStatus("Pass","Click on "+ data.get(TestBaseConstants.SUB_REPORT_NAME)+" report");
		 Thread.sleep(2000);
		 
	 
		 // select graduation year
		 selectValueFromDropDown(
		 getObjectValue(TestBaseConstants.DROP_DOWN_GRADUATION_YEAR),
			 TestBaseConstants.DROP_SELECT_USING_TEXT,
			 data.get(TestBaseConstants.GRADUATION_YEAR),
			 "Select Graduation Year-->"+data.get(TestBaseConstants.GRADUATION_YEAR));
		 Thread.sleep(1000);
	 
	
		// select graduation term
		 selectValueFromDropDown(
		 getObjectValue(TestBaseConstants.DROP_DOWN_GRADDUATION_TERM),
		 TestBaseConstants.DROP_SELECT_USING_TEXT,data.get(TestBaseConstants.GRADDUATION_TERM),
				 "Select Graduation Term-->"+data.get(TestBaseConstants.GRADDUATION_TERM));
		 Thread.sleep(2000);
		
		//click on generate report
		 click(TestBaseConstants.BUTTON_GENERATE_REPORT,"Clicking on generate report");
		 
		 //scroll the page upwards
		 scrollPageUp(450);
				 	
			 	
			 	//perform write or read to excel using Build type value present in excel
			 	if(GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_TYPE).equalsIgnoreCase(
						TestBaseConstants.BASELINE_BUILD_TYPE))	 	
				
				{
					 Logs.infoLog( "Started writing to excel as Baseline is the build Type");				 	
					 mT1_TCN_LST_WriteXLSX(
							 GlobalVariables.testCaseIdentifier				 			
					 			,GlobalVariables.testCaseIdentifier,
					 			"Writing contents of "+data.get(TestBaseConstants.SUB_REPORT_NAME)+
					 			" to excel"
					 			,data.get(TestBaseConstants.SUB_REPORT_NAME));
							
				}
				else if(GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_TYPE).
						equalsIgnoreCase(
						TestBaseConstants.ACTUAL_BUILD_TYPE))
					
				{
					Logs.infoLog( "Started reading from excel as Actual is the build Type");
					GlobalVariables.APPICATION_LOGS.info("Calling read method now");
					mT1_TCN_LST_READXLSX(GlobalVariables.testCaseIdentifier,
							GlobalVariables.testCaseIdentifier							
				 			,data.get(TestBaseConstants.SUB_REPORT_NAME),
				 			"Validating contents of table "+
				 			data.get(TestBaseConstants.SUB_REPORT_NAME)+"- with excel"
							);
				}		
		
		
	 }
	 
	 catch(Exception e)
		{			
		 GlobalVariables.fail=true;			
		 String s=e.getMessage();
		  errorReporter(GlobalVariables.errormsg,s);			
			
		}
	
	 	GlobalVariables.cverify.checkForVerificationErrors();
	}
}