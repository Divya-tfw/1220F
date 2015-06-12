package com.twelvetwenty.util;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.SkipException;

import com.google.common.io.Files;
import com.twelvetwenty.base.Base;
import com.twelvetwenty.base.CustomVerification;
import com.twelvetwenty.constants.GlobalVariables;
import com.twelvetwenty.constants.TestBaseConstants;





/******************************************************************************************************
 *		Author 						:	DivyaRaju.R
 *		LastModifiedDate	:	30-11-2013  
 *		ClassName					:	ExcelTestUtil
 *		Description				:	Class containing utility functions for taking screenshots,taking time stamps& zipping results
 *
*******************************************************************************************************/

public class ExcelTestUtil 
{	

	 /*****************************************************************************************************************
  	 * 	Author						:	Divya Raju.R
  	 * 	LastModifiedDate	:	2-4-2014  	
  	 * 	MethodName			: 	suiteRunstatus
  	 * 	Description				:	This method is used to get suite run status from input file
  	 * 
  	 ***************************************************************************************************************/	
	 public static String suiteRunstatus( String suiteName)
		{
			String suiteRunStatus=null;
			Xls_Reader testSuite = new Xls_Reader(  TestUtil.runInEditor(GlobalVariables.CONFIG.getProperty
					(TestBaseConstants.RUN_IN))+"\\"+TestBaseConstants.EXCEL_FOLDER_PATH+"\\"+TestBaseConstants.CONTROLLER_FILE_NAME+".xlsx");	
			try
			{				
				if(testSuite.isSheetExist(TestBaseConstants.CONTROLLER_SUITE_SHEET_NAME))
				{
					for (int tcid = 2; tcid <= testSuite.getRowCount(TestBaseConstants.CONTROLLER_SUITE_SHEET_NAME); tcid++) 
					{			
						String  currentSuite = testSuite.getCellData(TestBaseConstants.CONTROLLER_SUITE_SHEET_NAME,
								TestBaseConstants.CONTROLLER_TEST_SUITE_NAME, tcid);							
						String runMode= testSuite.getCellData(TestBaseConstants.CONTROLLER_SUITE_SHEET_NAME,
								TestBaseConstants.CONTROLLER_RUNMODE, tcid);					
								 if(currentSuite.equalsIgnoreCase(suiteName))
								 {
									// System.out.println("current test suite name  is ------->"+currentSuite);
									 suiteRunStatus=runMode;
									/*System.out.println("Runmode in suite is ---->"+suiteRunStatus);	*/				 
								 }								 
							 }
					}
					else
					{
						//System.out.println("Sheet is not existing");
						CustomVerification.verifyContent(false,"Sheet is not existing");
					}
				}
				catch(Exception e)
				{
					Base.errormsgReporter("Error while fetching suite run status",e.getMessage());	
				}
			//System.out.println(suiteRunStatus);
			return suiteRunStatus;	
			}
	 /*****************************************************************************************************************
	  	 * 	Author						:	Divya Raju.R
	  	 * 	LastModifiedDate	:	2-4-2014  	
	  	 * 	MethodName			: 	testRunStatus
	  	 * 	Description				:	This method is used to get test run status from input file
	  	 * 
	  	 ***************************************************************************************************************/	
	 
	 
	 public static String testRunStatus(String scriptFileName, String scriptName )
	 {
	 	String testRunStatus=null;
	 	
	   String runPath=TestUtil.runInEditor(GlobalVariables.CONFIG.getProperty
				(TestBaseConstants.RUN_IN));
	   String folderPath=TestBaseConstants.EXCEL_FOLDER_PATH;
	   String fileNAme=scriptFileName+".xlsx";
	 	
	
	 
	 	Xls_Reader testScript = new Xls_Reader(
	 			runPath	+"/"+folderPath
	 					+"/"+fileNAme);	
	 	try
	 	{
	 		if(testScript.isSheetExist(TestBaseConstants.TEST_SUITE_SHEET_NAME))
	 		{
	 			for (int tcid = 2; tcid <= testScript.getRowCount(TestBaseConstants.TEST_SUITE_SHEET_NAME); tcid++) 
	 			{			
	 				String  currentTest = testScript.getCellData(TestBaseConstants.TEST_SUITE_SHEET_NAME,
	 						TestBaseConstants.TEST_SUITE_NAME, tcid);							
	 				String runMode= testScript.getCellData(TestBaseConstants.TEST_SUITE_SHEET_NAME, 
	 						TestBaseConstants. TEST_SUITE_RUNMODE, tcid);					
	 					 if(currentTest.equalsIgnoreCase(scriptName))
	 					 {
	 						 //System.out.println("current test case name  is ------->"+currentTest);								
	 						 testRunStatus=runMode;
	 						// System.out.println("Runmode in suite is ---->"+testRunStatus);
	 									 
	 					 }								 
	 			 }
	 		}
	 	
	 		else
	 		{
	 			//System.out.println("Sheet is not existing");
	 			CustomVerification.verifyContent(false,"Sheet is not existing");
	 		}
	 	}
	 	catch(Exception e)
	 	{
	 		Base.errormsgReporter("Error while fetching run status",e.getMessage());	
	 	}
	 	return testRunStatus;	
	 }
	 
	 
	 public static boolean checkSkiptests(String suiteRunStatus,
			 String testRunStatus,String dataRunStatus )
		{
			
			if(suiteRunStatus.equalsIgnoreCase(TestBaseConstants.RUNMODE_NO)||
					suiteRunStatus.equalsIgnoreCase(""))
			{
				GlobalVariables.Skip=true;	
				 GlobalVariables.result="Skip";			 			 	
				Logs.errorLog("Runmode of Suite is N");
					
				 GlobalVariables. testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
				 GlobalVariables.APPICATION_LOGS.error("Test suite run mode is N in test case  File");
				
			/*	 ExcelTestUtil.reportSuiteResult
					( GlobalVariables.testSuite,  GlobalVariables.scriptName,
							TestBaseConstants.resultSkipVal );*/
				 throw new SkipException("Runmode of Suite is N");
			}		
			else
			if(testRunStatus.equalsIgnoreCase(TestBaseConstants.RUNMODE_NO)||
					testRunStatus.equalsIgnoreCase(""))
			{
				GlobalVariables.Skip=true;	
				 GlobalVariables.result="Skip";			 							 
			 Logs.errorLog("Runmode of test  is N");
				 GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
				 GlobalVariables.APPICATION_LOGS.error("Test suite run mode is N in test case  File");
				 /*ExcelTestUtil.reportTestResult(
						 GlobalVariables.controller, GlobalVariables.suiteName,TestBaseConstants.resultSkipVal );*/
				 
				 throw new SkipException("Skipping Test Case as runmode set to NO.");//reports
			}
			else
			if(dataRunStatus.equalsIgnoreCase(TestBaseConstants.RUNMODE_NO)||
					dataRunStatus.equalsIgnoreCase(""))
			{
				
				GlobalVariables.result="Skip";
				GlobalVariables.Skip=true;
				//System.out.println("Skipped section executed"+GlobalVariables.Skip);
				 GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);				
				 /*ExcelTestUtil.reportDataResult(GlobalVariables.testSuite, 
						 GlobalVariables.scriptName, 
						 GlobalVariables.testCaseIdentifier, TestBaseConstants.RESULT_SKIPVALUE );	
					ExcelTestUtil.reportDataResult
					( GlobalVariables.testSuite, 
							 GlobalVariables.scriptName,
							 GlobalVariables.testCaseIdentifier,  
							TestBaseConstants.RESULT_SKIPVALUE  );	*/
				Logs.errorLog("Runmode of data is N");
				 GlobalVariables.APPICATION_LOGS.error("Data value run mode is set to N");
				 throw new SkipException("Data value run mode is set to N");		
			}
			//System.out.println(GlobalVariables.Skip);
			return GlobalVariables.Skip;
			
		}
	/*************************************************************************************************************
	 *		Author 						:	DivyaRaju.R
	 *		LastModifiedDate	:	30-11-2013  
	 *		MethodName			:	now
	 *		Description				:	Method which returns current  date and time
	 *
	**********************************************************************************************/

	public static String now(String dateFormat) 
	{
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	    return sdf.format(cal.getTime());
	}
	
	/*********************************************************************************************
	 *		Author 						:	DivyaRaju.R
	 *		LastModifiedDate	:	30-11-2013  
	 *		MethodName			:	takeScreenShot
	 *		Description				:	Method which takes screen shot & stores in path specified
	 *
	**********************************************************************************************/	
	
	public static void takeScreenShot(String filePath) 
	{
	
		File scrFile = ((TakesScreenshot)GlobalVariables.driver).
				getScreenshotAs(OutputType.FILE);
	    try
	    {
			FileUtils.copyFile(scrFile, new File(filePath));
		}
	    catch (IOException e)
	    {
	    	System.out.println("Dint take screenshot as path is wrong");
			//e.printStackTrace();
	    	CustomVerification.verifyContent(false,e.getMessage());		
		}	  
	}  
		
	/*********************************************************************************************
	 *		Author 						:	DivyaRaju.R
	 *		LastModifiedDate	:	30-11-2013  
	 *		MethodName			:	zip
	 *		Description				:	Method which zip's results/reports obtained & stores in path specified
	 *
	**********************************************************************************************/	

	public static void zip(String filepath)
	{
	 	try
	 	{
	 		File inFolder=new File(filepath);
	 		File outFolder=new File("Reports.zip");
	 		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFolder)));
	 		BufferedInputStream in = null;
	 		byte[] data  = new byte[1000];
	 		String files[] = inFolder.list();
	 		for (int i=0; i<files.length; i++)
	 		{
	 			in = new BufferedInputStream(new FileInputStream
	 			(inFolder.getPath() + "/" + files[i]), 1000);  
	 			out.putNextEntry(new ZipEntry(files[i])); 
	 			int count;
	 			while((count = in.read(data,0,1000)) != -1)
	 			{
	 				out.write(data, 0, count);
	 			}
	 			out.closeEntry();
		  }
		  out.flush();
		  out.close();
			 	
		}
		  catch(Exception e)
		  {
			  System.out.println("Could not zip results "+e.getMessage());
			  CustomVerification.verifyContent(false,e.getMessage());		
		  } 
	}	
	
	/***********************************************************************************************
	 *		Author 						:	DivyaRaju.R
	 *		LastModifiedDate	:	28-12-2013  
	 *		MethodName			:	clearExcelCells
	 *		Description				:	This method clears contents in excel 
	 *		Input							:	Excel fileName & sheet name as testcaseName
	 *
	**********************************************************************************************/
		public static void clearExcelCells(Xls_Reader xls, String testCaseName)
		{
			try
			{
				for(int i=2; i<= xls.getRowCount(testCaseName); i++)
				{
					xls.setCellData(testCaseName,"Results", i,"");
					xls.setCellData(testCaseName,"Error" , i, "");
				}
			}
			catch(Exception e)
			{
				System.out.println("couldnot clear results "+e.getMessage());
				CustomVerification.verifyContent(false,e.getMessage());	
			}
		}
		
		
		/***********************************************************************************************
		 *		Author 						:	DivyaRaju.R
		 *		LastModifiedDate	:	28-12-2013  
		 *		MethodName			:	reportTestResult
		 *		Description				:	This method reports test case result values to results column in suite sheet
		 *		Input							:	Excel fileName & test script/case Name
		 *
		**********************************************************************************************/
		public static void reportTestResult(Xls_Reader testSuite,String testScriptNameVal,String statusToUpdate)
		{
			try
			{
				int rc=testSuite.getCellRowNum("Test_Suite", "TestSuiteName", testScriptNameVal);	
				boolean val = testSuite.setCellData("Test_Suite", "Results", rc,statusToUpdate );	
				//System.out.println("Reported results successfully into suite sheet -"+val);
				GlobalVariables. APPICATION_LOGS.info("Reported results successfully into suite sheet -"+val);
			}
			catch(Exception e)
			{
				System.out.println("Could not report test results "+e.getMessage());
				CustomVerification.verifyContent(false,e.getMessage());	
			}
		}

		
		
		public static void reportSuiteResult(Xls_Reader testSuite,String testScriptNameVal,String statusToUpdate)
		{
			try
			{
				int rc=testSuite.getCellRowNum("Test_Cases", "TestCaseName", testScriptNameVal);	
				boolean val = testSuite.setCellData("Test_Cases", "Results", rc,statusToUpdate );	
				//System.out.println("Reported results successfully into suite sheet -"+val);
				GlobalVariables.APPICATION_LOGS.info("Reported results successfully into suite sheet -"+val);
			}
			catch(Exception e)
			{
				System.out.println("Could not report test results "+e.getMessage());
				CustomVerification. verifyContent(false,e.getMessage());	
			}
		}

		/***********************************************************************************************
		 *		Author 						:	DivyaRaju.R
		 *		LastModifiedDate	:	28-12-2013  
		 *		MethodName			:	reportDataResult
		 *		Description				:	This method reports test case result values to results column in data sheet
		 *		Input							:	Excel fileName, test script/case Name & IdentifierName value
		 *
		**********************************************************************************************/

		public static void reportDataResult
		(Xls_Reader testData,String testCaseName,String IdentifierValue,String statusVal)
		{
			try
			{		
				int rc=testData.getCellRowNum(testCaseName, "Automation_Id", IdentifierValue);
				//System.out.println("Row count is "+rc);
				boolean val = testData.setCellData(testCaseName, "Results", rc,statusVal );	
				//System.out.println("Reported results successfully into Data sheet -"+val);
				GlobalVariables.APPICATION_LOGS.info("Reported results successfully into Data sheet -"+val);
			}
			catch(Exception e)
			{
				System.out.println("Couldnot report data results "+e.getMessage());
				CustomVerification.verifyContent(false,e.getMessage());	
			}
		}

		/***********************************************************************************************
		 *		Author 						:	DivyaRaju.R
		 *		LastModifiedDate	:	28-12-2013  
		 *		MethodName			:	reportError
		 *		Description				:	This method reports errors in error coulmn of data sheet 
		 *		Input							:	Excel fileName, test script/case Name & IdentifierName value
		 *
		**********************************************************************************************/

		public static void reportError(Xls_Reader testData,String testCaseName,
				String IdentifierValue,String errorVal)
		{
			try
			{
				int rc=testData.getCellRowNum(testCaseName, "Automation_Id", IdentifierValue);	
				boolean val = testData.setCellData(testCaseName, "Error", rc,errorVal );	
				//System.out.println("Reported error successfully into Data sheet - "+val);
				GlobalVariables.APPICATION_LOGS.info("Reported error successfully into Data sheet - "+val);
			}
			catch(Exception e)
			{
				System.out.println("could not report error"+e.getMessage());
				CustomVerification.verifyContent(false,e.getMessage());	
			}
		}
		
		
		/***********************************************************************************************
		 *		Author 						:	DivyaRaju.R
		 *		LastModifiedDate	:	28-12-2013  
		 *		MethodName			:	scriptExecutionTime
		 *		Description				:	This method returns script execution in terms of hrs:min:sec
		 *		Input							:	startTime & endTime of execution		 
		**********************************************************************************************/
		
		
		public static String  scriptExecutionTime(String startTime,String endTime)
		{
			String executedTime=null;
		  	Date d1 = null;
			Date d2 = null;
			String dateStart = startTime;
			String dateStop =endTime ;

				//HH converts hour in 24 hours format (0-23), day calculation
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			try 
			{
					d1 = format.parse(dateStart);		
					d2 = format.parse(dateStop);					
					long diff = d2.getTime() - d1.getTime();	
					long diffSeconds = diff / 1000 % 60;
					long diffMinutes = diff / (60 * 1000) % 60;
					long diffHours = diff / (60 * 60 * 1000) % 24;
					//long diffDays = diff / (24 * 60 * 60 * 1000);			
				//	System.out.print(diffHours + " hrs:"+diffMinutes + " min:"+diffSeconds + " sec");
					 executedTime = diffHours + " hrs:"+diffMinutes + " min:"+diffSeconds + " sec";
			}
			catch (ParseException e) 
			{
					System.out.println("exception occured--->"+e.getMessage());					
			}
			return executedTime ;
			}
		
		
		public static String runStatusAdd(String status)
		{
			GlobalVariables.statusVal+=status+",";		  
			return GlobalVariables.statusVal;
		}
		
		public static String appenedFileNames(String fileNames)
		{
			GlobalVariables.temp+=fileNames+"#";
//			System.out.println(temp );
			return GlobalVariables.temp;
		}
		
		
		public static String getDirectoryPath (String path)
		{
			String classpaths=path;
			if (StringUtils.countMatches(classpaths, ":")==1)
			{
				return classpaths.split("com")[0].split(":")[1];
			}
			else
			{
				//System.out.println ("split content -----------------------"+classpaths.split("com")[0].split(":")[1]);
				String sDrive =classpaths.split("com")[0].split(":")[1];
				sDrive = sDrive.replace("//","");
				//System.out.println("Sdrive content is -----------------"+sDrive+":/"+classpaths.split("com")[0].split(":")[2]);
				String SetDrive=sDrive+":/"+classpaths.split("com")[0].split(":")[2];
				String DrivePath=SetDrive.replace("/", "\\");
				return DrivePath;
				
			}
		}
		
		public static String Clean(String stringToClean)
		{
			stringToClean = stringToClean.replace("\"", "");
			stringToClean = stringToClean.replace("'", "");
			stringToClean = stringToClean.replace("/", "");
			stringToClean = stringToClean.replace("\\", "");
			stringToClean = stringToClean.replace("{", "");
			stringToClean = stringToClean.replace("}", "");	  
			stringToClean = stringToClean.replace(",", "");	  
			stringToClean = stringToClean.replace("", "");	
			stringToClean = stringToClean.replace(":", "");	
			stringToClean = stringToClean.replace("*", "");	
			stringToClean = stringToClean.replace("[", "");	
			stringToClean = stringToClean.replace("]", "");	
			stringToClean = stringToClean.replace("=", "");	
			stringToClean = stringToClean.replace("@", "");	
			return stringToClean;     
		}
		
		
		/*****************************************************************************************************
		 *		Author 						:	DivyaRaju.R
		 *		LastModifiedDate	:	28-12-2013  
		 *		MethodName			:	custReporter
		 *		Description				:	This method prints the log to testNG report & console based on settings in config file
		 *		Input							:	Config file setting values Y/N in  reporterLog & consolePrint option
		*******************************************************************************************************/
		 public static void custReporter(String msg)
		 {		 
			 	String status=GlobalVariables.CONFIG.getProperty("reporterLog");
				//System.out.println("Value in config is --->"+status);
				 String msg1=msg;
				String cStatus =GlobalVariables.CONFIG.getProperty("consolePrint");
				boolean t=true;
				if(cStatus.equalsIgnoreCase("Y"))
				{
				 t=true; 
				}
				else t=false;
				 if(status.equalsIgnoreCase("Y") )
				 {
					 Reporter.log(msg1,t);
				 }
				
			
		 }
		 
		 
		 
		 public static void reportDataToExcel(Xls_Reader testData,String testCaseName,
				 String IdentifierValue,
				 String columnName,String statusVal)
			{
				try
				{		
					int rc=testData.getCellRowNum(testCaseName, "Identifier", IdentifierValue);	
					boolean val = testData.setCellData(testCaseName,columnName , rc,statusVal );	
				//	System.out.println("Reported results successfully into Data sheet -"+val);
					GlobalVariables.APPICATION_LOGS.info("Reported results successfully into Data sheet -"+val);
				}
				catch(Exception e)
				{
					System.out.println("Couldnot report data results "+e.getMessage());
					CustomVerification.verifyContent(false,e.getMessage());	
				}
			}
		 
		 public static String getRowData(Xls_Reader testData,String testCaseName,String IdentifierValue,String columnName,String statusVal)
			{
			 String val=null;
				try
				{		
					int rc=testData.getCellRowNum(testCaseName, "Identifier", IdentifierValue);	
					//System.out.println("RowNumber is"+rc);
					val=testData.getCellData(testCaseName, columnName, rc);
				//	System.out.println("Reported results successfully into Data sheet -"+val);
					GlobalVariables.APPICATION_LOGS.info("Reported results successfully into Data sheet -"+val);
				}
				catch(Exception e)
				{
					System.out.println("Couldnot report data  "+e.getMessage());
					CustomVerification.verifyContent(false,e.getMessage());	
				}
				return val;
			}
		 
		 public static void excelFileCopy(String  sourceFile,String destinationFile)
		 {		
		 		try 
		 		{
		 			String sFolderPath=cleanPath(GlobalVariables.CONFIG.getProperty("buildFolderPath"))+
		 					 "/"+"Build_number_"+GlobalVariables.CONFIG.getProperty("buildNumber")+
		 					 "/"+"Pre_Build/";
		 			String dFolderPath=cleanPath(GlobalVariables.CONFIG.getProperty("buildFolderPath"))+
		 					 "/"+"Build_number_"+GlobalVariables.CONFIG.getProperty("buildNumber")+
		 					 "/"+"Post_Build"+"/Failed/";
		 			Path source = Paths.get(sFolderPath+sourceFile);
				//destination file name with path
				Path destination = Paths.get(dFolderPath+destinationFile);
				Files.copy(source.toFile(), destination.toFile());
		 			System.out.println("Files copied");
		 		} 
		 		catch (IOException e) 
		 		{
		 			System.out.println("Couldnot report data  "+e.getMessage());
		 			CustomVerification.  verifyContent(false,e.getMessage());	
		 		}
		 }
		 
		 public static boolean createFolder(String buildType)
		 {
		 	boolean folderCreated=false;
		 	if(buildType.equalsIgnoreCase(TestBaseConstants.ACTUAL_BUILD_TYPE))
		 	{
		 		
		 		String buildFolderpath=cleanPath(
		 				GlobalVariables.CONFIG.getProperty("buildFolderPath"));
				String buildNumber=GlobalVariables.CONFIG.getProperty("buildNumber");
		 	 String filePath=buildFolderpath+
					 "/"+"Build_number_"+buildNumber+
					 "/"+buildType+"/Failed/";
		 	 System.out.println("Directory path is -->"+filePath);
		 	 
		 	 
		 	folderCreated=new File(filePath).mkdirs();
		 	System.out.println("Directory created");
		 	}
		 	if(folderCreated==true)
		 	{
		 		return folderCreated=true;
		 	}
		 	else 
		 		return false;
		 	}
		 	
		 public static void setExcelData
		 (String fileName, String sheetName, int rowNum, int cellNum, String data)
			{
				Row r;
				try
				{
					FileInputStream fis = new FileInputStream(fileName);
					Workbook wb = WorkbookFactory.create(fis);
					Sheet s = wb.getSheet(sheetName);
					if(s.getRow(rowNum)==null){
						r = s.createRow(rowNum);
					}
					else
					{
						r = s.getRow(rowNum);
					}
					Cell c = r.createCell(cellNum);
					c.setCellValue(data);
					FileOutputStream fos = new FileOutputStream(fileName);
					wb.write(fos);
				} 
				catch (Exception e) 
				{
					//e.printStackTrace();
					CustomVerification.  verifyContent(false,e.getMessage());	
				}
			}
		 	
		 public static boolean createXLS(String xlsFilePath,String sheetName)
		 {
		 	boolean fileCreated=false;
		 	try
		 	{
		 		FileOutputStream fileOut = new FileOutputStream(xlsFilePath);
		 		Workbook wb = new XSSFWorkbook();		
		 		 wb.createSheet(sheetName);		
		 		wb.write(fileOut);
		 		fileOut.close();
		 		fileCreated=true;
		 	}
		 	catch(Exception e)
		 	{
		 		System.out.println(e.getMessage());
		 		CustomVerification.  verifyContent(false,e.getMessage());	
		 		
		 	}
		 	
		 	return fileCreated;
		 }	
		 
		 
		    public static String errorReportValforExcel(String custErrMsg,String exceptionErrmsg)
		    {
		 	   String errorMsgVal=null;
		 	   if(custErrMsg!=null)
		 		 {
		 		   errorMsgVal=custErrMsg;
		 		 }
		 		 else
		 		 {
		 			 errorMsgVal=exceptionErrmsg;
		 		 }
		 	return errorMsgVal;
		 	   
		    }
		    
		    public static String cleanContent(String stringToClean)
			{
				
				stringToClean = stringToClean.replace("\"", "");
				return stringToClean;     
			}
		    
		    public static String cleanPath(String path)
			{
				String s=null;
				if(path!=null)
				{
					s=path;
					cleanContent(s);
				}
				
				else
				{
					//System.out.println("Value is blank. input data");
					CustomVerification.  verifyContent(false,"Value is blank. input data to clean path");
				}
				return s;
			}
		    
		    public static void  launchSite(String URL)
			{
				String s=null;
				if(URL!=null)
				{
					 s=URL;
					cleanContent(s);
					GlobalVariables.driver.get(s);
				}
				else
				{
					//System.out.println("Value is blank. input data");
					CustomVerification.  verifyContent(false,"Value is blank. input url to navigate");
				}
				
			}
		 
	}


