package com.twelvetwenty.suite.nalp_reports;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.twelvetwenty.base.App_Specific_Keywords;
import com.twelvetwenty.constants.GlobalVariables;
import com.twelvetwenty.constants.TestBaseConstants;
import com.twelvetwenty.util.ExcelTestUtil;
import com.twelvetwenty.util.Logs;

public class Emp_Typ_by_Age_at_Grad extends App_Specific_Keywords
{

	/*****************************************************************************************************************
  	 * 	Author						:	Divya Raju.R
  	 * 	LastModifiedDate			:	19-5-2015  	  
  	 * 	Annotation					:	@Test
  	 * 	MethodName					: 	test_Emp_Typ_by_Age_at_Grad
  	 * 	Description					:	This method is used to perform required functionality test on app
  	 * 
  	 ***************************************************************************************************************/	
	@Test(dataProvider="ExcelData")
	public void test_Emp_Typ_by_Age_at_Grad(Hashtable<String,String> data) 
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
	
	Logs.infoLog("Launch Browser");
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
	
	 //System.out.println(driver.findElement(By.xpath("//*[text()='Standard Reports']")).isDisplayed());
	 
	 //click std reports
	 click("btn_std_reports","Click on Standard Reports");
	 
	 
	 Thread.sleep(3000);
	 //click on Table report
	 
	 GlobalVariables.driver.findElement(By.xpath(
			 "//tr[td[contains(text(),'"+data.get("sSub_Report_Name")+"')]]//*[text()='Generate']")).click();
	 Logs.infoLog("Click on "+data.get("sSub_Report_Name")+" report");	
	 
	 rATUStatus("Pass","Click on "+data.get("sSub_Report_Name")+" report");
	 Thread.sleep(2000);
	 // select graduation year
	 selectValueFromDropDown(getObjectValue("dpdown_GraduationYr"),
	 "Text",data.get("iGraduationYr"),"Select Graduation Year-->"+data.get("iGraduationYr"));
	 Logs.infoLog("Select Graduation Year-->"+data.get("iGraduationYr"));	
	 Thread.sleep(1000);
	 // select graduation term
	
		 // select graduation term
		 selectValueFromDropDown( GlobalVariables.driver.findElement(By.id("GraduationTermId")),
				 "Text",data.get("sGraduationTerm"),"Select Graduation Term-->"+data.get("sGraduationTerm"));
		 Thread.sleep(2000);
		 // select offer timing
		 selectValueFromDropDown( GlobalVariables.driver.findElement(By.name("LawOfferTiming")),
				 "Text",data.get("sOffer_Timing"),"Select Job Phase Id-->"+data.get("sOffer_Timing"));
		 Thread.sleep(1000);
		 //select cut off date if with is given else clear it
		if(data.get("sCutoff_Date").equalsIgnoreCase("Without"))
		{
			 GlobalVariables.driver.findElement(By.id("LawCutoffDate")).click();
			 GlobalVariables.driver.findElement(By.id("LawCutoffDate")).clear();
		}
		
		 		 
		 click("btn_GenerateReport","Clicking on generate report");
		 //scroll the page upwards
		 	scrollPageUp(450);
		 	//String sn_forwrite= ExcelTestUtil.now("dd_MMMMM_yyyy_hh.mm.ss aaa");
		 	
		 	//perform write or read to excel using Build type value present in excel
		 	if(GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_TYPE).equalsIgnoreCase(
					TestBaseConstants.BASELINE_BUILD_TYPE))	 	
			
			{
				 Logs.infoLog( "Started writing to excel as Baseline is the build Type");
			 	
				 mT1_TH2_TBH1_TCN_WriteXLSX(
						GlobalVariables.testCaseIdentifier
				 			
				 			,GlobalVariables.testCaseIdentifier,
				 			"Writing contents of "+data.get("sSub_Report_Name")+" to excel",
				 			"//*[@id='report-data']"
				 			,data.get("sSub_Report_Name"));
						
			}
			else if(GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_TYPE).
					equalsIgnoreCase(
					TestBaseConstants.ACTUAL_BUILD_TYPE))
				
			{
				Logs.infoLog( "Started reading from excel as Actual is the build Type");
				//String sn_forRead=sn_forwrite;
				mT1_TH2_TBH1_TCN_ReadXLSX(GlobalVariables.testCaseIdentifier,GlobalVariables.testCaseIdentifier
						,"//*[@id='report-data']"
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
