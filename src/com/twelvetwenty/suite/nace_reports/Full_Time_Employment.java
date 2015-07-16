package com.twelvetwenty.suite.nace_reports;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.twelvetwenty.base.App_Specific_Keywords;
import com.twelvetwenty.constants.GlobalVariables;
import com.twelvetwenty.constants.TestBaseConstants;
import com.twelvetwenty.util.ExcelTestUtil;
import com.twelvetwenty.util.Logs;

public class Full_Time_Employment  extends App_Specific_Keywords  
{
	
	/*****************************************************************************************************************
  	 * 	Author						:	Divya Raju.R
  	 * 	LastModifiedDate			:	19-1-2015  	  
  	 * 	Annotation					:	@Test
  	 * 	MethodName					: 	test_Full_Time_Employment
  	 * 	Description					:	This method is used to perform required functionality test on app
  	 * 
  	 ***************************************************************************************************************/	
	@Test(dataProvider="ExcelData")
	public void test_Full_Time_Employment(Hashtable<String,String> data) 
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
				
				// click on report 
				 rATUStatus("Pass","Click on "+data.get("sSub_Report_Name")+" report");						
				 GlobalVariables.driver.findElement(By.xpath
						 ( "//tr[td[contains(text(),'"+
				 data.get("sSub_Report_Name")+"')]]//*[text()='Generate']"))
						 .click();
				 Logs.infoLog("Click on "+data.get("sSub_Report_Name")+" report");	
				 
				
				 Thread.sleep(2000);
				 
				 
				// select graduation year
				 selectValueFromDropDown(
						 getObjectValue("dpdown_GraduationYr"),
				 "Text",data.get("iGraduationYr"),"Select Graduation Year-->"+data.get("iGraduationYr"));
				 Logs.infoLog("Select Graduation Year-->"+data.get("iGraduationYr"));	
				 Thread.sleep(1000);
				 
				// select graduation term
				 selectValueFromDropDown( 
						 getObjectValue("dpdown_GraduationTerm"),
						 "Text",data.get("sGraduationTerm"),"Select Graduation Term-->"+data.get("sGraduationTerm"));
				 Thread.sleep(2000);
				 
				//select college 
				 if(data.get("sCollege").contains("FC AT ROSE HILL"))
				 { 
					 GlobalVariables.driver.findElement(By.xpath
							 ("//*[@id='reportsForm']/div/ul/li[3]/div/button")).click();
					 Thread.sleep(2000);
					 GlobalVariables.driver.findElement(By.xpath
							 ("//*[@id='reportsForm']/div/ul/li[3]/div/ul/li[3]/a/label/input")).click();
				 }
				 Thread.sleep(3000);
				 //select reporting major
				 selectValueFromDropDown( 
						 getObjectValue("dpdown_Reporting_Major"),
						 "Index",data.get("sReportingMajor"),"Select Reporting major-->"+data.get("sReportingMajor"));
				 Thread.sleep(2000);
				 //select degree level
				 selectValueFromDropDown( 
						 getObjectValue("dpdown_DegreeLevel"),
						 "Text",data.get("sDegreeLevel"),"Select Degree level-->"+data.get("sDegreeLevel"));
				 Thread.sleep(2000);
				 
				 if(data.get("sSchoolName").equalsIgnoreCase("Columbia_Centralized"))
				 {
					//select department  //*[@id='reportsForm']/div/ul/li[5]/div/ul/li[1]/div/input
					 GlobalVariables.driver.findElement(By.xpath
							 ("//*[@id='reportsForm']/div/ul/li[5]/div/button")).click();
					//type to search value 
					 Thread.sleep(2000);
					 GlobalVariables.driver.findElement(By.xpath
							 ("//*[@id='reportsForm']/div/ul/li[5]/div/ul/li[1]/div/input")).click();
					 if(GlobalVariables.driver.findElement(By.xpath
							 ("//*[@id='reportsForm']/div/ul/li[5]/div/ul/li[16]/a/label/input")).isDisplayed())
					 {
						 GlobalVariables.driver.findElement(By.xpath
								 ("//*[@id='reportsForm']/div/ul/li[5]/div/ul/li[16]/a/label/input")).click();
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
					 	
						 mT1_TH1_TCN_WriteXLSX(
								GlobalVariables.testCaseIdentifier
						 			
						 			,GlobalVariables.testCaseIdentifier,
						 			"Writing contents of "+data.get("sSub_Report_Name")+" to excel",
						 			GlobalVariables.OR.getProperty("report_Full_Time_Employment")
						 			,data.get("sSub_Report_Name"));
								
					}
					else if(GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_TYPE).
							equalsIgnoreCase(
							TestBaseConstants.ACTUAL_BUILD_TYPE))
						
					{
						Logs.infoLog( "Started reading from excel as Actual is the build Type");
						
						mT1_TH1_TCN_ReadXLSX(GlobalVariables.testCaseIdentifier,GlobalVariables.testCaseIdentifier
								,GlobalVariables.OR.getProperty("report_Full_Time_Employment")
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
