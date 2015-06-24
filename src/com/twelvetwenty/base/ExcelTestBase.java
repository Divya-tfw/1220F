package com.twelvetwenty.base;

import java.io.File;
import java.util.Hashtable;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import atu.testng.reports.ATUReports;
import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;

import com.twelvetwenty.constants.DBConstants;
import com.twelvetwenty.constants.GlobalVariables;
import com.twelvetwenty.constants.TestBaseConstants;
import com.twelvetwenty.db.DataBaseConnection;
import com.twelvetwenty.reports.ReportUtil;
import com.twelvetwenty.util.ExcelTestUtil;
import com.twelvetwenty.util.Logs;
import com.twelvetwenty.util.TestUtil;
import com.twelvetwenty.util.Xls_Reader;

public class ExcelTestBase extends Base
{
	/*********************************************************************************
  	 * 	Author						:	Divya Raju.R
  	 * 	LastModifiedDate	:	30-12-2013  	  
  	 * 	Annotation				:	@BeforeSuite	
  	 * 	MethodName			: 	beforeSuite
  	 * 	Description				:	This method  Performs set of operations before execution of suite
				  											Load Config file & configure log4j 
  	  
  	
  	 *
  	 *
  	 **********************************************************************************/	
	
	{
		System.setProperty("atu.reporter.config","./config/Config.properties");
	}
	@BeforeSuite	
	public  void beforeSuite()
	{
		// Configure log4j for given test script
		DOMConfigurator.configure(TestBaseConstants.LOG4J_FILE);	
		GlobalVariables.APPICATION_LOGS.info("Executing Before suite");
		if(!loadConfig())
		{
			// Load Config file now 
			loadConfig();			
		}
		
		// After loading config file , pick values from config file
		
		// Run from specifies to run in either eclipse or from ant
		GlobalVariables.runFrom=GlobalVariables.CONFIG.getProperty(TestBaseConstants.RUN_FROM);	
		// This value specifies generation of UserDefined Report based on values Y/N
		GlobalVariables.ur=GlobalVariables.CONFIG.getProperty(TestBaseConstants.USER_DEFINED_REPORT);
		//Fetch the suite name based on package name
		GlobalVariables.suiteName=TestUtil.moduleName(this.getClass().getCanonicalName());
		//GlobalVariables.suiteName="TumblrSite";
		
		
		if(GlobalVariables.CONFIG.getProperty("appType").equalsIgnoreCase
				(TestBaseConstants.WEB_APP_TYPE))
		{
		//check whether runfrom is excel or xml. If xml then only proceed else skip
		if(GlobalVariables.runFrom.equalsIgnoreCase(TestBaseConstants.RUN_USING_EXCEL_VAL))
		{
			// check whther user status is Y/N , if Y then only proceed
			if(GlobalVariables.ur.equalsIgnoreCase(TestBaseConstants.USR_STATUS ))
			{
			
		    // set property as Y to for report created so that report is created again while running 2nd suite
			setProperty(TestBaseConstants.USER_REPORT_CREATED, TestBaseConstants.RUNMODE_YES);	
			
			// Folder name for saving user defined report
			GlobalVariables.folder = (TestUtil.now(TestBaseConstants.USR_FILE_FORMAT)+
									"_"+GlobalVariables.suiteName);
			
			//create folder for user defined report
			GlobalVariables.success = (new File(cleanPath
					(GlobalVariables.CONFIG.getProperty(TestBaseConstants.USR_FOLDER_PATH))+
					"/"+ GlobalVariables.folder)).mkdirs();
			
			// Start creation of user defined report in index.html & store few pre values in report
			// like Environment_name,build number ,browser type
			ReportUtil.startTesting(cleanPath(
					 GlobalVariables.CONFIG.getProperty(TestBaseConstants.USR_FOLDER_PATH))+
					 "/"+ GlobalVariables.folder + "/index.html", 
					 TestUtil.now(TestBaseConstants.USR_FILE_FORMAT), 
					 GlobalVariables.CONFIG.getProperty(TestBaseConstants.ENVIRONMENT_NAME), 
					 GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_NUMBER),
					 GlobalVariables.CONFIG.getProperty(TestBaseConstants.BROWSER_TYPE));		
			 	
			
			// start adding suite details to report
			ReportUtil.startSuite(GlobalVariables.suiteName); 
			//System.out.println("Created usr folder");
		
		}
	
		//check status of adding details to database
		 if(GlobalVariables.CONFIG.getProperty("automationResults").equalsIgnoreCase(TestBaseConstants.RUNMODE_YES)  )
		 {
			// set property to N , as bulk insertion is done & don do bulk insertion again for running 2nd suite
			 setProperty("bulkDataInserted", "Y");
			 // if ipaddress value is default then use localhost
			 if(GlobalVariables.CONFIG.getProperty("ipAddress").equalsIgnoreCase("Default"))
			 {
				 GlobalVariables.ipaddressval=TestBaseConstants.LOCALIPADDRESS;
			 }
			 else
			 {	
				// else use ipaddress given in config file
				 GlobalVariables.ipaddressval=GlobalVariables.CONFIG.getProperty("ipAddress");
			 }
			 //System.out.println(GlobalVariables.ipaddressval);
			 
			 // insert bulk data into database
			/* Db_BulkInsertion.db_bulk_insert(GlobalVariables.ipaddressval,GlobalVariables.CONFIG.getProperty("dataBaseName"), 
					 GlobalVariables.CONFIG.getProperty("userName"),GlobalVariables.CONFIG.getProperty("passWord"), 
					 GlobalVariables.CONFIG.getProperty("tableName"),
					 GlobalVariables.CONFIG.getProperty("OS"),GlobalVariables.CONFIG.getProperty("environmentName"),
					 GlobalVariables.CONFIG.getProperty("browserType"),
						DBConstants.dbBuildNumber,GlobalVariables.CONFIG.getProperty("buildNumber"),GlobalVariables.CONFIG.getProperty("testCycleId"),
						GlobalVariables.CONFIG.getProperty("applicationName"), 	GlobalVariables.CONFIG.getProperty("dbStatus_before_execution"));
			 		*/
			 		 }
		
		 
		 //check if video recording is enabled at suite level if yes start the recording
		 try
		 
		 {
			 GlobalVariables.recorder = new ATUTestRecorder(cleanContent(GlobalVariables.CONFIG.getProperty("videoFolderPath")
					  ),GlobalVariables.suiteName+"_"+TestUtil.now("dd.MMMMM.yyyy.hh.mm.ss.aaa"),false); 
			  if(GlobalVariables.CONFIG.getProperty("suiteRecoder").equals("Y") && GlobalVariables.CONFIG.getProperty("videoFolderPath")!=null )
			  {
				  GlobalVariables.recorder.start();
				  GlobalVariables.suiterecording=true;
			  }
		 }
		catch(Exception e)
		 {
			
			GlobalVariables.APPICATION_LOGS.error("Recorder path not found------------------------------->"+e.getMessage());
			Reporter.log("Recorder path not found ------------------------------->"+e.getMessage(),true);
		 }
		 
		 if (GlobalVariables.CONFIG.getProperty("rATUGenerate").equalsIgnoreCase("Y")&& 
				 GlobalVariables.CONFIG.getProperty("reporterLog").equalsIgnoreCase("N"))
					{ 
			 ATUReports.indexPageDescription="ATU reports for "+ GlobalVariables.CONFIG.getProperty
					 ("applicationName")+" Application";
					}
		
		 //To create automation logs folder send few i/p parameters-Build_Number,
		 //Test_cycle_id,Suite_Name,Run_from
		 Logs.automationlogFolderCreation(
				 GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_NUMBER),
				 GlobalVariables.CONFIG.getProperty(TestBaseConstants.TEST_CYCLE_ID), 
				 GlobalVariables.suiteName,
				 GlobalVariables.CONFIG.getProperty(TestBaseConstants.RUN_FROM));
		}
		//if Runfrom value is blank or excel skip the execution 
		 else if( GlobalVariables.runFrom.equalsIgnoreCase("") ||
				 GlobalVariables.runFrom.equalsIgnoreCase(TestBaseConstants.RUN_USING_EXCEL_VAL))
		 {
			 errormsgReporter("Data value is "+ GlobalVariables.runFrom,null);	
			 throw new SkipException("Data value is "+ GlobalVariables.runFrom);
		 }
		}
		else if (
				GlobalVariables.CONFIG.getProperty("appType").equalsIgnoreCase
				(TestBaseConstants.MOBILE_APP_TYPE))
		{
			errormsgReporter("Application type is Mobile , "
					+ "select application type as Web to continue..... ",null);	
			 throw new SkipException("Application type is Mobile , "
						+ "select application type as Web to continue..... ",null);
		}
		
	}

	/*********************************************************************************
	  	 * 	Author						:	Divya Raju.R
	  	 * 	LastModifiedDate			:	30-1-2015  	  
	  	 * 	Annotation					:	@BeforeTest
	  	 * 	MethodName					: 	beforeTest
	  	 * 	Description					:	It fetch's suitename , scriptname and
	  	 * 									 starts the video recording if set to y
	  	 * 
	  	 **********************************************************************************/
	
	@BeforeTest
	public  void beforeTest() throws ATUTestRecorderException
	{
		GlobalVariables.suiteName=TestUtil.moduleName(this.getClass().getCanonicalName());
		 //System.out.println("suiteName =="+suiteName);
		GlobalVariables. scriptName=(this.getClass().getSimpleName());   
		 //System.out.println("scriptName=="+scriptName); 
/*
		GlobalVariables.suiteName="Mba_Standard_Reports";
				GlobalVariables.scriptName="Graduating_Class_A_Report";*/
		  
		
		 if(GlobalVariables.CONFIG.getProperty(TestBaseConstants.TEST_RECORDER).equals
				 (TestBaseConstants.RUNMODE_YES)
				 && GlobalVariables.CONFIG.getProperty(TestBaseConstants.VIDEO_FOLDER_PATH)!=null)
		  {
			 GlobalVariables.recorder.start();
			 GlobalVariables.testrecording=true;
		  }
	}
	
	
	/*********************************************************************************
  	 * 	Author						:	Divya Raju.R
  	 * 	LastModifiedDate	:	30-11-2013  	  
  	 * 	Annotation				:	@BeforeClass
  	 * 	MethodName			: 	beforeClass
  	 * 	Description				:	Performs set operations before class execution of script
	 * 											- load OR & controller files
  	 * 
  	 **********************************************************************************/
	 @BeforeClass
	 public void beforeClass()
	 {
		 // Load OR file
			if(!loadOR())
			{
				loadOR();
			} 

			//Load Suite And Data files 
			//GlobalVariables.suiteName=TestUtil.moduleName(this.getClass().getCanonicalName());
			
			GlobalVariables.controller =new Xls_Reader(
					TestUtil.runInEditor(GlobalVariables.CONFIG.getProperty
							(TestBaseConstants.RUN_IN))
					+"\\"+TestBaseConstants.EXCEL_FOLDER_PATH+"\\"+TestBaseConstants.CONTROLLER_FILE_NAME+".xlsx");	
		
			GlobalVariables.testSuite=new Xls_Reader(
					TestUtil.runInEditor(GlobalVariables.CONFIG.getProperty(TestBaseConstants.RUN_IN))+"\\"+
			TestBaseConstants.EXCEL_FOLDER_PATH+"\\"+ getActualFileName(GlobalVariables.suiteName)+".xlsx" );			
	 }
	
	 
	 /*********************************************************************************
		 * 	Author						:	Divya Raju.R
		 * 	LastModifiedDate	:	30-11-2013  
		 * 	Annotation				:	@BeforeMethod
		 * 	MethodName			: 	beforeMethod
		 * 	Description				:	Performs set operations before class execution of test Method
		 * 											get module name, test case name,get start date & clears old contents 
		 * 											excel data				
		 **********************************************************************************/	
		 @BeforeMethod
		 public void beforeMethod()
		 {
			
	    	
			 ExcelTestUtil.clearExcelCells(GlobalVariables.testSuite, GlobalVariables.scriptName);
			 GlobalVariables.startTime=ExcelTestUtil.now("yyyy/MM/dd HH:mm:ss");		
			 GlobalVariables.startReportTime = ExcelTestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa");
			 if(GlobalVariables.testusappend!=null||GlobalVariables.testusappend!="")
			 {
				 GlobalVariables.testusappend=null;
			 }
			
		 }
		 
		 
		 /*********************************************************************************
			 * 	Author						:	Divya Raju.R
			 * 	LastModifiedDate	:	30-11-2013  
			 * 	Annotation				:	@DataProvider
			 * 	MethodName			: 	getTestData
			 * 	Description				:	Gets the data rows from data excel sheet
			 * 
			 **********************************************************************************/ 
		 		 
			 @DataProvider(name="ExcelData")
				public  Object[][] getData ()
				{
				 /*GlobalVariables.suiteName="Mba_Standard_Reports";
					GlobalVariables.scriptName="Graduating_Class_A_Report";*/
			  
				 
				GlobalVariables.suiteName=TestUtil.moduleName(this.getClass().getCanonicalName());
				 GlobalVariables.sheetPath=new Xls_Reader(TestUtil.runInEditor(GlobalVariables.CONFIG.getProperty
							(TestBaseConstants.RUN_IN))+"\\"+TestBaseConstants.EXCEL_FOLDER_PATH+"\\"+
						 getActualFileName(GlobalVariables.suiteName)+".xlsx");
				 GlobalVariables.scriptName=this.getClass().getSimpleName();
					// if the sheet is not present
					if(!GlobalVariables.sheetPath.isSheetExist(GlobalVariables.scriptName))
					{
						// Making xlsx object free
						GlobalVariables.sheetPath = null;
						// Return 1 row of data without any column data so that testcase can run atleast one time
						return new Object[1][0];
					}
								
					int rows = GlobalVariables.sheetPath.getRowCount(GlobalVariables.scriptName);	
					// Row count in datasheet
					GlobalVariables.dataFileRowCount=rows;
					int cols = GlobalVariables.sheetPath.getColumnCount(GlobalVariables.scriptName);	// Column count in datasheet
					
					Object[][] data = new Object[rows-1][1];
					Hashtable<String,String> table = null;
		
					// print the test data
					for(int rowNum=2; rowNum<=rows; rowNum++)
					{
						table = new Hashtable<String,String>();
						
						GlobalVariables.dataFileRowCount=rowNum;
						for(int colNum=0; colNum<cols; colNum++)
						{
							table.put(GlobalVariables.sheetPath.getCellData(GlobalVariables.scriptName,
									colNum, 1),GlobalVariables.sheetPath.getCellData
									(GlobalVariables.scriptName, colNum, rowNum));
							
						}
						data[rowNum-2][0] = table;
					}
					return data;
				}

	//@Test
	//@Test(dataProvider="ExcelData",groups={"Major"})
	public void test(Hashtable<String,String> data)
	{
		
		
		GlobalVariables.testCaseIdentifier="Automation_Id";	
		GlobalVariables.dataRunStatus=data.get("Runmode");

		GlobalVariables.suiteRunStatus =	ExcelTestUtil.suiteRunstatus(GlobalVariables.suiteName);
		
		GlobalVariables.testRunStatus=ExcelTestUtil.testRunStatus(GlobalVariables.suiteName,GlobalVariables.scriptName); 			
	
		boolean trs = ExcelTestUtil.checkSkiptests(GlobalVariables.suiteRunStatus,
				GlobalVariables.testRunStatus,GlobalVariables.dataRunStatus);
		
		System.out.println("Skipped --->"+trs);
		
		if(trs==false)
		{
			GlobalVariables.testusappend="Pass";
		}
		else
		{
			GlobalVariables.testusappend="Skip";
		}
		
		System.out.println(GlobalVariables.testusappend);
		GlobalVariables.cverify.checkForVerificationErrors();
		
	}
	
	/*********************************************************************************
	  	 * 	Author						:	Divya Raju.R
	  	 * 	LastModifiedDate	:	30-11-2013  	  
	  	 * 	Annotation				:	@AfterMethod
	  	 * 	MethodName			: 	driverAfterMethod
	  	 * 	Description				:	It performs operation after test method execution 
	  	 * 								quit driver, update excel with test case execution	
	  	 * 
	 **********************************************************************************/	

	@AfterMethod
	 public static void afterMethod()
	 {
		/* System.out.println("Data row count is -->"+GlobalVariables.dataFileRowCount);
		 System.out.println("Flag count --------->"+GlobalVariables.executeflag);*/
		 if(GlobalVariables.driver!=null && 
				 GlobalVariables.executeflag <GlobalVariables.dataFileRowCount  )
		 {
			 GlobalVariables.driver.close();
			 GlobalVariables.driver=null;
			 GlobalVariables.executeflag =0;
		 }	
		 if(GlobalVariables.flag2==0)
		 {
			 GlobalVariables.result=errorReportValforExcel(GlobalVariables.errormsg,
					 GlobalVariables.exceptionMsgVal);
			 GlobalVariables.flag2=1;
		 }
		/*System.out.println("Appended test status is--->"+GlobalVariables.testusappend);
		System.out.println("Result value is --->"+GlobalVariables.result);
		System.out.println("Result value is --->"+GlobalVariables.errormsg);
		
		System.out.println("Result value is --->"+GlobalVariables.errormsgVal);*/
		 GlobalVariables.errormsgVal=GlobalVariables.errormsg;
		 if(GlobalVariables.testusappend.contains("Fail") ||GlobalVariables.testusappend==null
				 ||GlobalVariables.testusappend.contains(""))
			{
			 GlobalVariables.result=errorReportValforExcel(GlobalVariables.errormsg,
				GlobalVariables.exceptionMsgVal);
			 GlobalVariables.dbresult=GlobalVariables.result;
			 GlobalVariables.scriptStatus=TestBaseConstants.RESULT_FAILVALUE;
			}
		 else if(GlobalVariables.testusappend.contains("Skip"))
		 {
			 GlobalVariables.result=TestBaseConstants.RESULT_SKIPVALUE; 
		 }
			else
			{
				GlobalVariables.result=TestBaseConstants.RESULT_PASSVALUE;
				GlobalVariables.scriptStatus=TestBaseConstants.RESULT_PASSVALUE;
			}
		 //System.out.println("scriptStatus value is"+scriptStatus);
		 GlobalVariables. endTime=ExcelTestUtil.now("yyyy/MM/dd HH:mm:ss");
		 
		 GlobalVariables. reportendTime = ExcelTestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa");
		 GlobalVariables.exeTime=ExcelTestUtil.scriptExecutionTime(
				 GlobalVariables.startTime, GlobalVariables.endTime);
		 /*System.out.println("Appended test status is--->"+GlobalVariables.testusappend);
			System.out.println("Result value is --->"+GlobalVariables.result);*/
			
		 if( GlobalVariables.testusappend.contains(TestBaseConstants.RESULT_FAILVALUE)  
				  || GlobalVariables.testusappend=="" )
		 {
			 
			/* ExcelTestUtil.reportDataResult( GlobalVariables.testSuite, 
					 GlobalVariables.scriptName,  GlobalVariables.testCaseIdentifier,
					 TestBaseConstants.RESULT_FAILVALUE );
			 ExcelTestUtil.reportSuiteResult(
					 GlobalVariables.testSuite, GlobalVariables.scriptName,
					 TestBaseConstants.RESULT_FAILVALUE);
				ExcelTestUtil.reportTestResult( GlobalVariables.controller,
						 GlobalVariables.suiteName,TestBaseConstants.RESULT_FAILVALUE);
				
				ExcelTestUtil.reportError( GlobalVariables.testSuite,
						 GlobalVariables.scriptName,
						 GlobalVariables.testCaseIdentifier,
						 GlobalVariables.result);	*/
				 if( GlobalVariables.ur.equalsIgnoreCase("Y"))
				 {
					 ReportUtil.addTestCase(
							 GlobalVariables.testCaseIdentifier,
							 GlobalVariables.startReportTime,
							 GlobalVariables.reportendTime,TestBaseConstants.RESULT_FAILVALUE,
							 GlobalVariables.logFolderPath+"/"+
									 GlobalVariables.logFileName, 
									 GlobalVariables.screenShotsPath+"/"+
											 GlobalVariables.screenshotFilename);
				 }
				// GlobalVariables.testusappend=null;
		 }		
		  else
		 if( GlobalVariables.testusappend.contains(TestBaseConstants.RESULT_PASSVALUE) )
		 {
			
				/*ExcelTestUtil.reportTestResult
				( GlobalVariables.controller,
						 GlobalVariables.suiteName,TestBaseConstants.RESULT_PASSVALUE);
				ExcelTestUtil.reportSuiteResult(
						 GlobalVariables.testSuite, 
						 GlobalVariables.scriptName, TestBaseConstants.RESULT_PASSVALUE);
				ExcelTestUtil.reportDataResult(
						 GlobalVariables.testSuite, 
						 GlobalVariables.scriptName, 
						 GlobalVariables.testCaseIdentifier,  TestBaseConstants.RESULT_PASSVALUE  );	*/
				 GlobalVariables.dbresult=TestBaseConstants.RESULT_PASSVALUE;
				 
				 
				 
				 if( GlobalVariables.ur.equalsIgnoreCase("Y"))
				 {
					 ReportUtil.addTestCase(
							 GlobalVariables.testCaseIdentifier,
							 GlobalVariables.startReportTime,
							 GlobalVariables.reportendTime,TestBaseConstants.RESULT_PASSVALUE,
							 GlobalVariables.logFolderPath+"/"+ GlobalVariables.logFileName,
							 GlobalVariables.screenShotsPath);	
				 }
		 }
		 	 else
		 if( GlobalVariables.Skip==true || 
		  GlobalVariables.result.contains(TestBaseConstants.RESULT_SKIPVALUE) )
			{					
			
			 /*ExcelTestUtil.reportDataResult
				( GlobalVariables.testSuite, 
						 GlobalVariables.scriptName,
						 GlobalVariables.testCaseIdentifier,  
						TestBaseConstants.RESULT_SKIPVALUE  );	
				ExcelTestUtil.reportSuiteResult
				( GlobalVariables.testSuite,  GlobalVariables.scriptName,
						TestBaseConstants.resultSkipVal );
				ExcelTestUtil.reportTestResult(
						 GlobalVariables.controller, 
						 GlobalVariables.suiteName,TestBaseConstants.resultSkipVal );*/
				if( GlobalVariables.ur.equalsIgnoreCase("Y"))
				 {
					 ReportUtil.addTestCase(
							 GlobalVariables.testCaseIdentifier,
							 GlobalVariables.startReportTime,
							 GlobalVariables.reportendTime,TestBaseConstants.RESULT_SKIPVALUE,
							 GlobalVariables.logFolderPath+"/"+ GlobalVariables.logFileName,
							 GlobalVariables.screenShotsPath);	
				 }
				GlobalVariables.Skip=false;
				GlobalVariables.result=null;
				
			}
		 
		 GlobalVariables.APPICATION_LOGS.info("*************************"+
		 "Execution of test----> "+
		GlobalVariables.scriptName+"  Ended"+
		 "********************************************************");
		 Logs.infoLog( "*************************"+
		 "Execution of test----> "+GlobalVariables.scriptName+
		 "  Ended"+"********************************************************");
		
		 //System.out.println(errormsgVal);
			if(GlobalVariables.errormsgVal==null)
			{
				
				//errorStringMsg="*****";
				GlobalVariables.errorExistsCheck=StringUtils.isBlank(GlobalVariables.errormsgVal);
			}
			else
			{
				GlobalVariables.errorStringMsg=ExcelTestUtil.Clean(GlobalVariables.errormsgVal);
			//System.out.println("Error msg to update ---->"+errorStringMsg);
			}
			
				
			if(GlobalVariables.screenshotFilename==null)
			{
				GlobalVariables.screenshotFilename="";
				GlobalVariables.screenshotsExistsCheck=StringUtils.isBlank(GlobalVariables. screenshotFilename);
			}
			
					if (GlobalVariables.CONFIG.getProperty("automationResults").
							equalsIgnoreCase(TestBaseConstants.RUNMODE_YES)
							&&
							GlobalVariables.CONFIG.getProperty("dbStatus_at_execution").
							equalsIgnoreCase(DBConstants.dbAtExeStatus)) 
					{														
						
						if(GlobalVariables.errorExistsCheck==true ||
								GlobalVariables.screenshotsExistsCheck==true)
						{
							

							DataBaseConnection.dataBaseUpdateMultipleWithOutError(
									
									GlobalVariables.ipaddressval,
									GlobalVariables.CONFIG.getProperty("dataBaseName"), 
									GlobalVariables.CONFIG.getProperty("userName"),
									GlobalVariables.CONFIG.getProperty("passWord"),
									GlobalVariables.CONFIG.getProperty("tableName"), 
						DBConstants.dbTestStatus, GlobalVariables.CONFIG.getProperty("dbStatus_after_execution"),  
						DBConstants.dbResults, GlobalVariables.dbresult,									
						DBConstants.dbTestErrorFolderPath, GlobalVariables.logFolderPath+"/", 
						DBConstants.dbScreenshotFolderPath, GlobalVariables.screenShotsPath+"/", 
						DBConstants.dbTestErrorLog, GlobalVariables.logFileName, 
					
						DBConstants.dbTestCaseID, GlobalVariables.scriptName,
						DBConstants.dbAutomationID, GlobalVariables.testCaseIdentifier,
						DBConstants.dbTestCaseIteration,GlobalVariables.iterationVal);
							DataBaseConnection.dataBaseUpdate(GlobalVariables.ipaddressval,
									GlobalVariables.CONFIG.getProperty("dataBaseName"), 
									GlobalVariables.CONFIG.getProperty("userName"),
						 			GlobalVariables.CONFIG.getProperty("passWord"),
						 			GlobalVariables.CONFIG.getProperty("tableName"), 
						 			DBConstants.dbExecutionTime,GlobalVariables.exeTime, 
						 			DBConstants.dbTestCaseID, GlobalVariables.scriptName,
						 			DBConstants.dbAutomationID, GlobalVariables.testCaseIdentifier,
						 			DBConstants.dbTestCaseIteration,GlobalVariables.iterationVal  );	
						}
							
						else
						{
						
						
						DataBaseConnection.dataBaseUpdate(GlobalVariables.ipaddressval,
									GlobalVariables.CONFIG.getProperty("dataBaseName"), 
									GlobalVariables.CONFIG.getProperty("userName"),
						 			GlobalVariables.CONFIG.getProperty("passWord"),
						 			GlobalVariables.CONFIG.getProperty("tableName"), 
						 			DBConstants.dbExecutionTime,GlobalVariables.exeTime, 
						 			DBConstants.dbTestCaseID, GlobalVariables.scriptName,
						 			DBConstants.dbAutomationID, GlobalVariables.testCaseIdentifier,
						 			DBConstants.dbTestCaseIteration,GlobalVariables.iterationVal  );
							
							DataBaseConnection.dataBaseUpdate(GlobalVariables.ipaddressval,
									GlobalVariables.CONFIG.getProperty("dataBaseName"), 
									GlobalVariables.CONFIG.getProperty("userName"),
						 			GlobalVariables.CONFIG.getProperty("passWord"),
						 			GlobalVariables.CONFIG.getProperty("tableName"), 
						 			DBConstants.dbErrorMessage, GlobalVariables.errorStringMsg,
						 			DBConstants.dbTestCaseID, GlobalVariables.scriptName,
						 			DBConstants.dbAutomationID, GlobalVariables.testCaseIdentifier,
						 			DBConstants.dbTestCaseIteration,GlobalVariables.iterationVal  );
							

							DataBaseConnection.dataBaseUpdate(GlobalVariables.ipaddressval,
									GlobalVariables.CONFIG.getProperty("dataBaseName"), 
									GlobalVariables.CONFIG.getProperty("userName"),
						 			GlobalVariables.CONFIG.getProperty("passWord"),
						 			GlobalVariables.CONFIG.getProperty("tableName"), 
						 			DBConstants.dbTestErrorFolderPath, GlobalVariables.logFolderPath, 
						 			DBConstants.dbTestCaseID, GlobalVariables.scriptName,
						 			DBConstants.dbAutomationID,GlobalVariables. testCaseIdentifier,
						 			DBConstants.dbTestCaseIteration,GlobalVariables.iterationVal  );
							
							DataBaseConnection.dataBaseUpdate(GlobalVariables.ipaddressval,
									GlobalVariables.CONFIG.getProperty("dataBaseName"), 
									GlobalVariables.CONFIG.getProperty("userName"),
						 			GlobalVariables.CONFIG.getProperty("passWord"),
						 			GlobalVariables.CONFIG.getProperty("tableName"), 
						 			DBConstants.dbScreenshotFolderPath,GlobalVariables. screenShotsPath, 
						 			DBConstants.dbTestCaseID, GlobalVariables.scriptName,
						 			DBConstants.dbAutomationID, GlobalVariables.testCaseIdentifier,
						 			DBConstants.dbTestCaseIteration,GlobalVariables.iterationVal  );
							
							DataBaseConnection.dataBaseUpdate(GlobalVariables.ipaddressval,
									GlobalVariables.CONFIG.getProperty("dataBaseName"), 
									GlobalVariables.CONFIG.getProperty("userName"),
						 			GlobalVariables.CONFIG.getProperty("passWord"),
						 			GlobalVariables.CONFIG.getProperty("tableName"), 
						 			DBConstants.dbTestErrorLog, GlobalVariables.logFileName, 
						 			DBConstants.dbTestCaseID, GlobalVariables.scriptName,
						 			DBConstants.dbAutomationID, GlobalVariables.testCaseIdentifier,
						 			DBConstants.dbTestCaseIteration,GlobalVariables.iterationVal  );
							
							DataBaseConnection.dataBaseUpdate(GlobalVariables.ipaddressval,
									GlobalVariables.CONFIG.getProperty("dataBaseName"), 
									GlobalVariables.CONFIG.getProperty("userName"),
						 			GlobalVariables.CONFIG.getProperty("passWord"),
						 			GlobalVariables.CONFIG.getProperty("tableName"), 
						 			DBConstants.dbScreenshotLinks, GlobalVariables.screenshotFilename, 
						 			DBConstants.dbTestCaseID,GlobalVariables. scriptName,
						 			DBConstants.dbAutomationID, GlobalVariables.testCaseIdentifier,
						 			DBConstants.dbTestCaseIteration,GlobalVariables.iterationVal  );
							DataBaseConnection.dataBaseUpdate(GlobalVariables.ipaddressval,
									GlobalVariables.CONFIG.getProperty("dataBaseName"), 
									GlobalVariables.CONFIG.getProperty("userName"),
						 			GlobalVariables.CONFIG.getProperty("passWord"),
						 			GlobalVariables.CONFIG.getProperty("tableName"), 
						 			DBConstants.dbResults, GlobalVariables.dbresult,	
						 			DBConstants.dbTestCaseID, GlobalVariables.scriptName,
						 			DBConstants.dbAutomationID, GlobalVariables.testCaseIdentifier,
						 			DBConstants.dbTestCaseIteration,GlobalVariables.iterationVal  );
							
							DataBaseConnection.dataBaseUpdate(GlobalVariables.ipaddressval,
									GlobalVariables.CONFIG.getProperty("dataBaseName"), 
									GlobalVariables.CONFIG.getProperty("userName"),
						 			GlobalVariables.CONFIG.getProperty("passWord"),
						 			GlobalVariables.CONFIG.getProperty("tableName"), 
						 			DBConstants.dbTestStatus, GlobalVariables.CONFIG.getProperty("dbStatus_after_execution"),  
						 			DBConstants.dbTestCaseID, GlobalVariables.scriptName,
						 			DBConstants.dbAutomationID, GlobalVariables.testCaseIdentifier,
						 			DBConstants.dbTestCaseIteration,GlobalVariables.iterationVal  );
						}	
							
					}
					Logs.closeLogFile(GlobalVariables.writer);
					GlobalVariables. runmode=true;
					GlobalVariables.Skip=false;
					GlobalVariables. testresult=true;
					GlobalVariables. fail=false;
					GlobalVariables. result=null;
					GlobalVariables.errormsgVal=null;
					GlobalVariables.errorStringMsg=null;
					GlobalVariables. flag2=0;
					
					GlobalVariables.testusappend=null;
					
					GlobalVariables.statusVal="";
		 
		 }

	
	 
	@AfterTest
	public void afterTest() throws ATUTestRecorderException
	{
	
		
		 if(GlobalVariables.CONFIG.getProperty("testRecoder").equals("Y")&& 
				 GlobalVariables.CONFIG.getProperty("videoFolderPath")!=null &&
						 GlobalVariables.testrecording==true)
		  {
			 GlobalVariables.recorder.stop();
		  }
	}
	
	@AfterSuite
	public void suiteEnd() throws Exception
	{
		if(GlobalVariables.driver!=null && 
				GlobalVariables.executeflag ==0)
		{
			GlobalVariables.driver.quit();	
		}
		GlobalVariables.APPICATION_LOGS.info("Ending suite in user report");
		 if(GlobalVariables.ur.equalsIgnoreCase("Y"))
		 {
		ReportUtil.endSuite();
		ReportUtil.updateEndTime(ExcelTestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa"));	
		GlobalVariables.suiteName=null;
		 }
		 if(GlobalVariables.CONFIG.getProperty("suiteRecoder").equals("Y")&&
				 GlobalVariables.CONFIG.getProperty("videoFolderPath")!=null
				 &&GlobalVariables.suiterecording==true)
		  {
			 GlobalVariables.recorder.stop();
			 GlobalVariables.suiterecording=false;
		  }
		 
		//cverify.checkForVerificationErrors();	

}



	 
	 

		
	
}






























/* if(GlobalVariables.CONFIG.getProperty(TestBaseConstants.AUTOMATION_RESULTS).
 * equalsIgnoreCase(TestBaseConstants.RUNMODE_YES)  )
 {
	 setProperty(TestBaseConstants.BULK_DATA_INSERTED, TestBaseConstants.RUNMODE_YES);
	 if(GlobalVariables.CONFIG.getProperty(TestBaseConstants.IP_ADDRESS_VAL).
			 equalsIgnoreCase(TestBaseConstants.DEFAULT_VAL))
	 {
		 GlobalVariables.ipaddressval=TestBaseConstants.LOCALIPADDRESS;
	 }
	 else
	 {
		 GlobalVariables.ipaddressval=GlobalVariables.CONFIG.getProperty(TestBaseConstants.IP_ADDRESS_VAL);
	 }
	 System.out.println(GlobalVariables.ipaddressval);
	 Db_BulkInsertion.db_bulk_insert(
			 GlobalVariables.ipaddressval,
			 GlobalVariables.CONFIG.getProperty(TestBaseConstants.DATA_BASE_NAME), 
			 GlobalVariables.CONFIG.getProperty(TestBaseConstants.USER_NAME),
			 GlobalVariables.CONFIG.getProperty(TestBaseConstants.PASSWORD), 
			 GlobalVariables.CONFIG.getProperty(TestBaseConstants.TABLE_NAME),
			 GlobalVariables.CONFIG.getProperty(TestBaseConstants.OS),
			 GlobalVariables.CONFIG.getProperty(TestBaseConstants.ENVIRONMENT_NAME),
			 GlobalVariables.CONFIG.getProperty(TestBaseConstants.BROWSER_TYPE),
			 DBConstants.dbBuildNumber,
			 GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_NUMBER),
			 GlobalVariables.CONFIG.getProperty(TestBaseConstants.TEST_CYCLE_ID),
		     GlobalVariables.CONFIG.getProperty(TestBaseConstants.APPLICATION_NAME), 
		     GlobalVariables.CONFIG.getProperty(TestBaseConstants.DBSTATUS_BEFORE_EXECUTION));
	 
	
 }*/

