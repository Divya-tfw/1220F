package com.twelvetwenty.suite.mba_standard_reports;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.twelvetwenty.base.App_Specific_Keywords;
import com.twelvetwenty.constants.GlobalVariables;
import com.twelvetwenty.constants.TestBaseConstants;
import com.twelvetwenty.util.ExcelTestUtil;
import com.twelvetwenty.util.Logs;

public class Graduating_Class_B_Report extends App_Specific_Keywords  
{
	
	/*****************************************************************************************************************
  	 * 	Author						:	Divya Raju.R
  	 * 	LastModifiedDate			:	19-1-2015  	  
  	 * 	Annotation					:	@Test
  	 * 	MethodName					: 	test_Graduating_Class_B_Report
  	 * 	Description					:	This method is used to perform required functionality test on app
  	 * 
  	 ***************************************************************************************************************/	
	@Test(dataProvider="ExcelData")
	public void test_Graduating_Class_B_Report(Hashtable<String,String> data) 
	{
		
		//Start of script
		GlobalVariables.APPICATION_LOGS.info("--------Execution of test---- "+
				GlobalVariables.scriptName+"  Started--------");
		GlobalVariables.testCaseIdentifier=data.get("Automation_Id");
	
		Logs.openLogFile(GlobalVariables.logFolderPath, GlobalVariables.testCaseIdentifier); 	
		GlobalVariables.dataRunStatus=data.get("Runmode");			 
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
			 	rATUConfigInfo("12Twenty Reports of execution",
					
			 			data.get("sSub_Report_Name"),
						 GlobalVariables.scriptName,
						 "Divya","1.0")	;
			 	 rATUStatus("Info","Open the browser");
				 Logs.infoLog("Navigate to the Data fectch URL of application");
				 rATUStatus("Pass","Navigate to the Data fectch URL of application");
				 
				 //navigate to url of application
				 String url=data.get("sSchoolName");
				 launchSite(cleanPath(GlobalVariables.CONFIG.getProperty("site_"+url)));
				 
				 // Call the login method to perform login with valid credentials & 
				 //click on data analysis tab	 
				 loginToSite(data.get("iWait"),
						 GlobalVariables.CONFIG.getProperty("s1220User"),
						 cleanContent(GlobalVariables.CONFIG.getProperty("s1220pwd")));
				 webdriverWait(data.get("iWait"));			
				
				 
				 //click std reports
				 click("btn_std_reports","Click on Standard Reports");
				 
				 // wait till page load
				Thread.sleep(5000);
				 rATUStatus("Pass","Click on "+data.get("sSub_Report_Name")+" report");
				// click on report 
				 GlobalVariables.driver.findElement(By.xpath(
						 "//tr[td[contains(text(),'"+data.get("sSub_Report_Name")+"')]]//*[text()='Generate']")).click();
				 Logs.infoLog("Click on "+data.get("sSub_Report_Name")+" report");	
				 
				
				 Thread.sleep(2000);
				 
				 if(url.contains("Texas_FTMBA") ||url.contains("Harvard_FTMBA"))
	 				{
	 				 // select graduation year
	 				 selectValueFromDropDown(
	 						getObjectValue("dpdown_GraduationYr"),
	 				 "Text",data.get("iGraduationYr"),"Select Graduation Year-->"+data.get("iGraduationYr"));
	 				 Logs.infoLog("Select Graduation Year-->"+data.get("iGraduationYr"));	
	 				 Thread.sleep(2000);
	 				 
	 				 // select graduation term
	 				 selectValueFromDropDown(
	 						getObjectValue("dpdown_GraduationTerm"),
	 						 "Text",data.get("sGraduationTerm"),
	 						 "Select Graduation Term-->"+data.get("sGraduationTerm"));
	 				 
	 				 Logs.infoLog( "Select Graduation Term-->"+data.get("sGraduationTerm"));			 
	 				 
	 				 Thread.sleep(2000);
	 			
	 				 // select job phase 
					 selectValueFromDropDown(
							 getObjectValue("dpdown_JobPhaseId"),
							 "Text",data.get("sJobPhaseId"),
							 "Select Job Phase Id-->"+data.get("sJobPhaseId"));
					 Logs.infoLog( "Select Job Phase Id-->"+data.get("sJobPhaseId"));
					 
					 //select joint degree
					 
					 selectValueFromDropDown(getObjectValue("dpdown_JointDegree"),
							 "Text",data.get("sJoint_Degree"),
							 "Select Job Phase Id-->"+data.get("sJoint_Degree"));
					 Logs.infoLog( "Select Job Phase Id-->"+data.get("sJoint_Degree"));
					 Thread.sleep(2000);
	 				}
					 else if(url.contains("Texas_PTMBA"))
	 				 {
						// select graduation year
		 				 selectValueFromDropDown
		 				 (getObjectValue("dpdown_GraduationYr"),
		 				 "Text",data.get("iGraduationYr"),"Select Graduation Year-->"+data.get("iGraduationYr"));
		 				 Logs.infoLog("Select Graduation Year-->"+data.get("iGraduationYr"));	
		 				 Thread.sleep(1000);
		 				 
		 				 // select graduation term
		 				 selectValueFromDropDown
		 				 (getObjectValue("dpdown_GraduationTerm"),
		 						 "Text",data.get("sGraduationTerm"),
		 						 "Select Graduation Term-->"+data.get("sGraduationTerm"));
		 				 
		 				 Logs.infoLog( "Select Graduation Term-->"+data.get("sGraduationTerm"));			 
		 				 
		 				 Thread.sleep(2000);
		 				 if(!data.get("sProgram").equalsIgnoreCase("All"))
		 				 {
		 				 //select program
		 					 selectValueFromDropDown(
		 							getObjectValue("dpdown_Program"),
		 					 "Index",data.get("sProgram"),"Select program-->"+data.get("sProgram")); 					
		 					 Thread.sleep(2000);
		 					 Logs.infoLog( "Select program-->"+data.get("sProgram"));
		 				 }
	 					 
	 				 }
				 
				 //click on generate report
				 click("btn_GenerateReport","Clicking on generate report");
				 
				 //scroll the page upwards
				 scrollPageUp(450);
				 // Clicking on generate report
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
				 			GlobalVariables.OR.getProperty("report_Graduating_Class_B_Report")
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
						,GlobalVariables.OR.getProperty("report_Graduating_Class_B_Report")
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
		
		 GlobalVariables.cverify.checkForVerificationErrors();	}

}

