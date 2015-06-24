package com.twelvetwenty.suite.bussiness_week_reports;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.twelvetwenty.base.App_Specific_Keywords;
import com.twelvetwenty.constants.GlobalVariables;
import com.twelvetwenty.constants.TestBaseConstants;
import com.twelvetwenty.util.ExcelTestUtil;
import com.twelvetwenty.util.Logs;

public class Base_Salary_By_EL extends App_Specific_Keywords  
{
	
	/*****************************************************************************************************************
  	 * 	Author						:	Divya Raju.R
  	 * 	LastModifiedDate			:	10-6-2015  	  
  	 * 	Annotation					:	@Test
  	 * 	MethodName					: 	test_Employment_Status
  	 * 	Description					:	This method is used to perform required functionality test on app
  	 * 
  	 ***************************************************************************************************************/	
	@Test(dataProvider="ExcelData")
	public void test_Employment_Status(Hashtable<String,String> data) 
	{
		//Start of script
		GlobalVariables.APPICATION_LOGS.info("--------Execution of test---- "+
				GlobalVariables.scriptName+"  Started--------");
		GlobalVariables.testCaseIdentifier=data.get("Automation_Id");
		GlobalVariables.executeflag++;
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
				 String wrongPage=GlobalVariables.driver.getTitle();
					//login again only when navigated to log out page 
					 if(wrongPage.contains("SAGE | Login"))
					 {
						 input("txt_EmailAddress",GlobalVariables.CONFIG.getProperty("s1220User"),
								 "Enter valid email address");
						 
						 // input password
						 input("txt_Password",cleanContent(GlobalVariables.CONFIG.getProperty("s1220pwd")),"Enter valid password");
						 
						 //click on login button
						 click("btn_Log_in","Click on login button");
															 
					 }
				 // wait till page load
					 Thread.sleep(3000);
				 //click on aba report
				 click("lnk_ABA_report","Click on ABA Reports");
				
				 						
				 // wait till page load
				 Thread.sleep(5000);
				 
				// click on report 
				 rATUStatus("Pass","Click on "+data.get("sSub_Report_Name")+" report");		
				 
				
				/* GlobalVariables.driver.findElement(By.xpath
						 ( "//tr[td[contains(text(),'"+
				 data.get("sSub_Report_Name")+"')]]//*[text()='Generate']"))
						 .click();*/
				 GlobalVariables.driver.findElement(By.xpath
						 ( "//*[@id='aba']/table/thead/tr[2]/td[2]/a"))
						 .click();
				 
				 Logs.infoLog("Click on "+data.get("sSub_Report_Name")+" report");	
				 
				
				 Thread.sleep(3000);
				 
				 // select graduation year
				 selectValueFromDropDown(
						 getObjectValue("dpdown_GraduationYr"),
				 "Text",data.get("iGraduationYr"),"Select Graduation Year-->"+data.get("iGraduationYr"));
				 Logs.infoLog("Select Graduation Year-->"+data.get("iGraduationYr"));	
				 Thread.sleep(1000);
				 
				 
				 // select graduation term
				 selectValueFromDropDown(
				 getObjectValue("dpdown_GraduationTerm"),
						 "Text",data.get("sGraduationTerm"),
						 "Select Graduation Term-->"+data.get("sGraduationTerm"));
				 Thread.sleep(2000);
				 
				 // select offer timing
				 selectValueFromDropDown(
						 getObjectValue("dpdown_Offer_timing"),
						 "Text",data.get("sOffer_Timing"),
						 "Select Job Phase Id-->"+data.get("sOffer_Timing"));
				 Thread.sleep(1000);
				 
				 //select cut off date if with is given else clear it
				if(data.get("sCutoff_Date").equalsIgnoreCase("Without"))
				{
					 GlobalVariables.driver.findElement(By.id("LawCutoffDate")).click();
					 GlobalVariables.driver.findElement(By.id("LawCutoffDate")).clear();
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
					 	
						 mT1_TH1_TCN_WriteXLSX(
								GlobalVariables.testCaseIdentifier
						 			
						 			,GlobalVariables.testCaseIdentifier,
						 			"Writing contents of "+data.get("sSub_Report_Name")+" to excel",
						 			GlobalVariables.OR.getProperty("report_Employment_Status")
						 			,data.get("sSub_Report_Name"));
								
					}
					else if(GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_TYPE).
							equalsIgnoreCase(
							TestBaseConstants.ACTUAL_BUILD_TYPE))
						
					{
						Logs.infoLog( "Started reading from excel as Actual is the build Type");
						//String sn_forRead=sn_forwrite;
						mT1_TH1_TCN_ReadXLSX(GlobalVariables.testCaseIdentifier,GlobalVariables.testCaseIdentifier
								,GlobalVariables.OR.getProperty("report_Employment_Status")
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
	