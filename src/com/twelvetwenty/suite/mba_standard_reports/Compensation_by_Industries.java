package com.twelvetwenty.suite.mba_standard_reports;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.twelvetwenty.base.App_Specific_Keywords;
import com.twelvetwenty.constants.GlobalVariables;
import com.twelvetwenty.constants.TestBaseConstants;
import com.twelvetwenty.util.ExcelTestUtil;
import com.twelvetwenty.util.Logs;

public class Compensation_by_Industries extends App_Specific_Keywords  
{
	
	/*****************************************************************************************************************
  	 * 	Author						:	Divya Raju.R
  	 * 	LastModifiedDate			:	19-1-2015  	  
  	 * 	Annotation					:	@Test
  	 * 	MethodName					: 	test_Compensation_by_Industries
  	 * 	Description					:	This method is used to perform required functionality test on app
  	 * 
  	 ***************************************************************************************************************/	
	@Test(dataProvider="ExcelData")
	public void test_Compensation_by_Industries(Hashtable<String,String> data) 
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
		 		 
		 //navigate to url of the application
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
						 
		 // wait till page load
		Thread.sleep(5000);
		 GlobalVariables.driver.findElement(By.xpath
				 ( "//tr[td[contains(text(),'"+
		 data.get(TestBaseConstants.SUB_REPORT_NAME)+"')]]//*[text()='Generate']"))
				 .click();
		 Logs.infoLog("Click on "+ data.get(TestBaseConstants.SUB_REPORT_NAME)+" report");
		 rATUStatus("Pass","Click on "+ data.get(TestBaseConstants.SUB_REPORT_NAME)+" report");
		 Thread.sleep(2000);
		 
		 if(url.contains(TestBaseConstants.SCHOOL_TEXAS_FTMBA )||
				 url.contains( TestBaseConstants.SCHOOL_HARDVARD_FTMBA))
				{
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
			 TestBaseConstants.DROP_SELECT_USING_INDEX,data.get(TestBaseConstants.GRADDUATION_TERM),
					 "Select Graduation Term-->"+data.get(TestBaseConstants.GRADDUATION_TERM));
			 Thread.sleep(2000);
			
			// select job phase 
			 selectValueFromDropDown(
					 getObjectValue(TestBaseConstants.DROP_DOWN_JOB_PHASE),
					 TestBaseConstants.DROP_SELECT_USING_TEXT,data.get("sJobPhaseId"),
					 "Select Job Phase Id-->"+data.get(TestBaseConstants.JOB_PHASE));
			 Logs.infoLog( "Select Job Phase Id-->"+data.get(TestBaseConstants.JOB_PHASE));
			 
			 //select joint degree					 
			 selectValueFromDropDown(getObjectValue(TestBaseConstants.DROP_DOWN_JOINT_DEGREE),
					 TestBaseConstants.DROP_SELECT_USING_TEXT,data.get(TestBaseConstants.JOINT_DEGREE),
					 "Select Joint degree-->"+data.get(TestBaseConstants.JOINT_DEGREE));
			 Logs.infoLog( "Select Job Phase Id-->"+data.get(TestBaseConstants.JOINT_DEGREE));
			 Thread.sleep(2000);
				}
			 else if(url.contains( TestBaseConstants.SCHOOL_TEXAS_PTMBA))
				 {
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
						 TestBaseConstants.DROP_SELECT_USING_INDEX,
						 data.get(TestBaseConstants.GRADDUATION_TERM),
						 "Select Graduation Term-->"+data.get(TestBaseConstants.GRADDUATION_TERM));
				 Thread.sleep(2000); 
 				 
 				 Thread.sleep(2000);
 				 if(!data.get(TestBaseConstants.PROGRAM).equalsIgnoreCase("All"))
 				 {
 				 //select program
 					 selectValueFromDropDown(
 							 getObjectValue(TestBaseConstants.DROP_DOWN_PROGRAM),
 							 TestBaseConstants.DROP_SELECT_USING_INDEX,
 							 data.get(TestBaseConstants.PROGRAM),
 							 "Select program-->"+data.get(TestBaseConstants.PROGRAM)); 					
 					 Thread.sleep(2000);
 					
 				 }
					 
				 }
		 
		 //click on generate report
		 click("btn_GenerateReport","Clicking on generate report");
		 
		 //scroll the page upwards
		 scrollPageUp(450);	
				 	
				 	//perform write or read to excel using Build type value present in excel
			if(GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_TYPE).equalsIgnoreCase(
					TestBaseConstants.BASELINE_BUILD_TYPE))	 	
			
			{
				 Logs.infoLog( "Started writing to excel as Baseline is the build Type");
			 	//call method to store contents of web table to excel
				mT1_TH1_TCN_WriteXLSX(
						GlobalVariables.testCaseIdentifier
				 			
				 			,GlobalVariables.testCaseIdentifier,
				 			"Writing contents of "+data.get("sSub_Report_Name")+" to excel",
				 			GlobalVariables.OR.getProperty("report_Compensation_by_Industries")
				 			,data.get("sSub_Report_Name"));
						
			}
			else if(GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_TYPE).
					equalsIgnoreCase(
					TestBaseConstants.ACTUAL_BUILD_TYPE))
				
			{
				Logs.infoLog( "Started reading from excel as Actual is the build Type");
			    // call method to read contents from excel and table and validate it
				mT1_TH1_TCN_ReadXLSX(GlobalVariables.testCaseIdentifier,
						GlobalVariables.testCaseIdentifier
						,GlobalVariables.OR.getProperty("report_Compensation_by_Industries")
			 			,data.get("sSub_Report_Name"),
			 			"Validating contents of table "+data.get("sSub_Report_Name")+"- with excel"
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

