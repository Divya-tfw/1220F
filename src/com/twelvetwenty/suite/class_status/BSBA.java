package com.twelvetwenty.suite.class_status;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.twelvetwenty.base.App_Specific_Keywords;
import com.twelvetwenty.constants.GlobalVariables;
import com.twelvetwenty.constants.TestBaseConstants;
import com.twelvetwenty.util.ExcelTestUtil;
import com.twelvetwenty.util.Logs;

public class BSBA extends App_Specific_Keywords
{
	/*****************************************************************************************************************
  	 * 	Author						:	Divya Raju.R
  	 * 	LastModifiedDate			:	4-6-2015  	  
  	 * 	Annotation					:	@Test
  	 * 	MethodName					: 	test_BSBA
  	 * 	Description					:	This method is used to perform required functionality test on app
  	 * 
  	 ***************************************************************************************************************/	
	@Test(dataProvider="ExcelData")
	public void test_BSBA(Hashtable<String,String> data) 
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
					 TestBaseConstants.VERSION_VALUE);		 	
		 	 rATUStatus(TestBaseConstants.INFO_VALUE, "Open the browser");
		 	 
		 	 //navigate to url of the application
			 
			 launchSite(
			 cleanPath(GlobalVariables.CONFIG.getProperty("site_Ohio_State_BSBA")));	
			
			 Logs.infoLog("-------------------------------------------------------");
			 ExcelTestUtil. custReporter("----------------------------------------------");
			 // wait for page load
			 Thread.sleep(2000);
			 
		 	 // input email address
			 input("txt_EmailAddress",GlobalVariables.CONFIG.getProperty(TestBaseConstants.SITE_USER_NAME),
					"Enter valid email address");
			 
			 // input password
			 input("txt_Password", cleanContent(GlobalVariables.CONFIG.getProperty(TestBaseConstants.SITE_PASSWORD)),
					 "Enter valid password");
			 
			 //click on login button
			 click("btn_Log_in","Click on login button");			 
			 Thread.sleep(2000);
			 
			//Clicking on Student tracking
			 click("btn_Student_Tracking","Click on Student Tracking");
			 Thread.sleep(4000);
			//Clicking on Student reporting
			 click("btn_Student_Reporting","Click on Student Reporting");
			 Thread.sleep(2000);
			 
			//Select given graduation class
			 String yr= data.get("iGraduationYr");
			 click("btn_GradClass","Click on Grad class button before selecting");	
			 //De select 2015
			 GlobalVariables.driver.findElement(
					 By.xpath("//*[@id='graduationClassDropdown']/*/*/*/*/*/input[@value='2015']")).click();
			 //De select 2014
			 GlobalVariables.driver.findElement(
			 By.xpath("//*[@id='graduationClassDropdown']/*/*/*/*/*/input[@value='"+yr+"']")).click();			
			 click("btn_GradClass","Click on Grad class button after selecting");
			 
			// select graduation term
			 selectValueFromDropDown(
			 getObjectValue(TestBaseConstants.DROP_DOWN_GRADDUATION_TERM),
			 TestBaseConstants.DROP_SELECT_USING_INDEX,data.get(TestBaseConstants.GRADDUATION_TERM),
					 "Select Graduation Term-->"+data.get(TestBaseConstants.GRADDUATION_TERM));
			 Thread.sleep(2000);
			 
			
			// select job phase 
			 selectValueFromDropDown(
					 getObjectValue(TestBaseConstants.DROP_DOWN_JOB_PHASE),
					 TestBaseConstants.DROP_SELECT_USING_INDEX,data.get(TestBaseConstants.JOB_PHASE),
					 "Select Job Phase Id-->"+data.get(TestBaseConstants.JOB_PHASE));
			 Logs.infoLog( "Select Job Phase Id-->"+data.get(TestBaseConstants.JOB_PHASE));
			 
			 
			 //Select Work authorization
			 selectValueFromDropDown(
					 getObjectValue(TestBaseConstants.DROP_DOWN_WORK_AUTHORIZATION),
					 TestBaseConstants.DROP_SELECT_USING_INDEX,data.get(TestBaseConstants.WORK_AUTHORIZATION),
					 "Select Work authorization-->"+data.get(TestBaseConstants.WORK_AUTHORIZATION));
			 
			 
			 //Select Desired Industry
			 selectValueFromDropDown(
					 getObjectValue(TestBaseConstants.DROP_DOWN_DESIRED_INDUSTRY),
					 TestBaseConstants.DROP_SELECT_USING_INDEX,
					 data.get(TestBaseConstants.INDUSTRY),
					 "Select Desired Industry-->"+data.get(TestBaseConstants.INDUSTRY));
			 
			 
			 //Select Include rumor jobs
			 selectValueFromDropDown(
					 getObjectValue(TestBaseConstants.DROP_DOWN_INCLUDE_RUMOUR_JOBS),
					 TestBaseConstants.DROP_SELECT_USING_INDEX,
					 data.get(TestBaseConstants.INCLUDE_RUMOUR_JOBS),
					 "Select Include rumor jobs-->"+data.get(TestBaseConstants.INCLUDE_RUMOUR_JOBS));
			 
			 //Select Included in reporting
			 selectValueFromDropDown(
					 getObjectValue(TestBaseConstants.DROP_DOWN_INCLUDED_IN_REPORTING),
					 TestBaseConstants.DROP_SELECT_USING_INDEX,
					 data.get(TestBaseConstants.INCLUDED_IN_REPORTING),
					 "Select Included in reporting-->"+data.get(TestBaseConstants.INCLUDED_IN_REPORTING));
			//Select Degree level
			 selectValueFromDropDown(
					 getObjectValue(TestBaseConstants.DROP_DOWN_DEGREE_LEVEL),
					 TestBaseConstants.DROP_SELECT_USING_INDEX,data.get(TestBaseConstants.DEGREE_LEVEL),
					 "Select Degree level-->"+data.get(TestBaseConstants.DEGREE_LEVEL));
			 
			 //Select Under graduate major
			 selectValueFromDropDown(
					 getObjectValue(TestBaseConstants.DROP_DOWN_UNDER_GRADUATE_MAJOR),
					 TestBaseConstants.DROP_SELECT_USING_INDEX,
					 data.get(TestBaseConstants.UNDER_GRADUATE_MAJOR),
					 "Select Under graduate major-->"+data.get(TestBaseConstants.UNDER_GRADUATE_MAJOR));
			
		 	 
			 click("btn_GetResults","Click on get results button");
			 
			 //scroll the page upwards
			 scrollPageUp(450);
			 
			//perform write or read to excel using Build type value present in excel
			 	if(GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_TYPE).equalsIgnoreCase(
						TestBaseConstants.BASELINE_BUILD_TYPE))	 	
				
				{
					 Logs.infoLog( "Started writing to excel as Baseline is the build Type");
					 GlobalVariables.APPICATION_LOGS.info("Started writing to excel as Baseline is the build Type");
					 mT3_TH1_TCN_WriteXLSX(
							GlobalVariables.testCaseIdentifier					 			
					 			,GlobalVariables.testCaseIdentifier,
					 			"Writing contents of "+data.get("sSub_Report_Name")+" to excel",
					 			GlobalVariables.OR.getProperty("summary_table_xpath")
					 			,data.get("sSub_Report_Name"));
							
				}
				else if(GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_TYPE).
						equalsIgnoreCase(
						TestBaseConstants.ACTUAL_BUILD_TYPE))
					
				{
					Logs.infoLog( "Started reading from excel as Actual is the build Type");
					
					mT3_TH1_TCN_ReadXLSX(GlobalVariables.testCaseIdentifier,GlobalVariables.testCaseIdentifier
							,GlobalVariables.OR.getProperty("summary_table_xpath")
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
