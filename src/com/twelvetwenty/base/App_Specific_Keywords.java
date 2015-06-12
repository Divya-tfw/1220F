package com.twelvetwenty.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import com.twelvetwenty.constants.GlobalVariables;
import com.twelvetwenty.constants.TestBaseConstants;
import com.twelvetwenty.util.Logs;
import com.twelvetwenty.util.ExcelTestUtil;

/********************************************************************************************
 *		Author 						:	DivyaRaju.R
 *		LastModifiedDate			:	1st may 2015
 *		ClassName					:	App_Specific_Keywords
 *		Description					:	This class is extended by Keywords class 
 *										contains methods for working with 12Twenty Application 
 *										for reading table , fetching data to excel and 
 *										validating same
 *
*********************************************************************************************/


public class App_Specific_Keywords extends Keywords
{
	/********************************************************************************************
	 *		Author 						:	DivyaRaju.R
	 *		LastModifiedDate			:	1st may 2015
	 *		Method name					:	loginToSite
	 *		Description					:	This method is used for launching 1220 application
	 *
	*********************************************************************************************/
	
	
	
	public static void loginToSite(String waitVal,
			String emailAddress,
			String pwd)
			
	{
				 Logs.infoLog("-------------------------------------------------------");
				 ExcelTestUtil. custReporter("----------------------------------------------");
				 // wait for page load
				 webdriverWait(waitVal);
				 // input email address
				 input("txt_EmailAddress",emailAddress,"Enter valid email address");
				 
				 // input password
				 input("txt_Password",pwd,"Enter valid password");
				 
				 //click on login button
				 click("btn_Log_in","Click on login button");
				 
				 //wait for page load
				 webdriverWait(waitVal);
				
				 //click on data analysis tab
				 click("btn_DA","Click on Data Analysis");
				 // click on standard reports btn
				 webdriverWait(waitVal);
				 
				 if(GlobalVariables.driver.findElement(By.id("modalDialogConfirm")).isDisplayed())
				 {
					 GlobalVariables.driver.findElement(By.id("modalDialogConfirm")).click();
					 webdriverWait(waitVal);
					 /*driver.findElement(By.className("calculate")).click();
					 webdriverWait(waitVal);
					 driver.findElement(By.id("modalDialogConfirm")).click();*/

				 }
			
	}
	
	
	
	/********************************************************************************************
	 *		Author 						:	DivyaRaju.R
	 *		LastModifiedDate			:	1st may 2015
	 *		Method name					:	selectValueFromDropDown
	 *		Description					:	This method is used for selecting a value from drop down
	 *
	*********************************************************************************************/
	public static void selectValueFromDropDown(WebElement locatorValue,String selectionType,
		String valueToSelect,String msg)
	{
	try
	{
		//WebDriverWait wait = new WebDriverWait(GlobalVariables.driver,15);
			if(valueToSelect!=null ||valueToSelect=="")
			{
				
			if(!valueToSelect.equalsIgnoreCase("NA"))
			{
			//	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath((locatorValue.toString()))));
			 Select dropdown = new Select(locatorValue);
			 //select value based on visible text in DOM of select class
			 if(selectionType.equalsIgnoreCase("Text"))
			 {
				 Logs.infoLog("Selecting value from drop down using text--->"+valueToSelect);	
			 dropdown.selectByVisibleText(valueToSelect);
			 }
			//select value based on value in DOM of select class
			 else if(selectionType.equalsIgnoreCase("Value"))
			 {
				 Logs.infoLog("Selecting value from drop down using Value--->"+valueToSelect);	
				 dropdown.selectByValue(valueToSelect);
			 }
			 // select value based on index in DOM
			 else if(selectionType.equalsIgnoreCase("Index"))
			 {
				 Logs.infoLog("Selecting value from drop down using Index--->"+valueToSelect);	
				 int i=Integer.parseInt(valueToSelect);
				 dropdown.selectByIndex(i);
			 }
			}
				rATUStatus(GlobalVariables.result,msg);
				GlobalVariables.result=TestBaseConstants.RESULT_PASSVALUE;
				GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
			}
			else
			{
				GlobalVariables.APPICATION_LOGS.info("Value is blank.So nothing is selected");
				Logs.infoLog("Value is blank.So nothing is selected");
			}
	}
	catch(Exception e)
	{
			GlobalVariables.exceptionMsgVal=e.getMessage();
			String errmsgV=
			"Error while executing select drop down keyword."
			+ " Element not found ----> "+valueToSelect +
					" and its xpath is "+locatorValue;
			keywordsErrormsg
			(GlobalVariables.errormsg,GlobalVariables.exceptionMsgVal,
					errmsgV	);
			GlobalVariables.result=TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(errmsgV);
			Logs.errorLog(errmsgV);
			rATUStatus(GlobalVariables.result,msg);
	}
}
	


	
	/********************************************************************************************
	 *		Author 						:	DivyaRaju.R
	 *		LastModifiedDate			:	1st may 2015
	 *		Method name					:	mT1_TH2_TCN_WriteXLSX
	 *		Description					:	This method is used for fetching data from 1220 application
	 *										 and writing that excel
	 *
	*********************************************************************************************/
	public static void mT1_TH2_TCN_WriteXLSX(String sheetName,String excelFileName,String msg,
		String tableXpath,String subReportName) 
	{
	boolean xlFileCreated=false;
	if(subReportName.equalsIgnoreCase("The Graduating Class (A)") ||subReportName.equalsIgnoreCase("Timing of First Job Offer")
			  ||subReportName.equalsIgnoreCase("Timing of Job Acceptances"))
	  {
		try
		{//Pre_Build_Number
			// fetch the folder path to create work book
			String folderPath=cleanPath(GlobalVariables.CONFIG.getProperty("buildFolderPath"))+
					 "/"+"Build_number_"+GlobalVariables.CONFIG.getProperty("buildNumber")+
					 "/"+GlobalVariables.CONFIG.getProperty("buildType")+"/";
			//System.out.println("Build folder path is "+folderPath);
			
			 File  preBuildFolderPath=new File( folderPath);
			 
			 //Create directory
			 boolean folderCreated=preBuildFolderPath.mkdirs();
			 String filePath=preBuildFolderPath+"/"+GlobalVariables.testCaseIdentifier+".xlsx";
			 File filePath1 =new File(filePath);
			 //System.out.println("File Path is -->"+filePath);
			 if(folderCreated||preBuildFolderPath.exists())
			 {
				 if(filePath1.exists())
					{
						filePath1.delete();
						xlFileCreated= ExcelTestUtil.createXLS(filePath,GlobalVariables.testCaseIdentifier);		 
					}
					else 
					{
						xlFileCreated= ExcelTestUtil.createXLS(filePath,GlobalVariables.testCaseIdentifier);
					}
				 /*//System.out.println("Folder created yes or no-->"+success);
				 boolean xlFileCreated= ExcelTestUtil.createXLS(filePath, sheetName);*/
				 if(xlFileCreated)
				 {
					 
					 FileInputStream fis=new FileInputStream(filePath);
						Workbook wb=WorkbookFactory.create(fis);
						 //wb.createSheet(year);
						 Sheet s=wb.getSheet(sheetName);
				 
						//To locate table.
						  WebElement mytable = GlobalVariables.driver.findElement(By.xpath(tableXpath));
						  //To locate rows of table.
						  List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
						  //To calculate no of rows In table.
						  int rows_count = rows_table.size();
						  
						  //headers
						  List<WebElement> Columns_header = rows_table.get(0).findElements(By.tagName("th"));
						  Row r1=s.createRow(0);
						 
							
						
						//Loop for storing header with merge content
	
						  if(subReportName.equalsIgnoreCase("The Graduating Class (A)"))
						  {
							  Logs.infoLog( "Sub report is The Graduating Class (A) ");
								  r1.createCell(0).setCellValue(Columns_header.get(0).getText());
								  
									 r1.createCell(1).setCellValue(Columns_header.get(1).getText());
									 s.addMergedRegion(new CellRangeAddress(0, 0, 1, 4));
									  
									 r1.createCell(5).setCellValue(Columns_header.get(2).getText());
									 s.addMergedRegion(new CellRangeAddress(0, 0, 5, 8));
									 
									 r1.createCell(9).setCellValue(Columns_header.get(3).getText());
									 s.addMergedRegion(new CellRangeAddress(0, 0, 9, 12));  
							  }
							  
							  else if(subReportName.equalsIgnoreCase("Timing of First Job Offer")
									  ||subReportName.equalsIgnoreCase("Timing of Job Acceptances"))
							  {
								  Logs.infoLog( "Sub report is "+subReportName);
								  r1.createCell(0).setCellValue(Columns_header.get(0).getText());
									 r1.createCell(1).setCellValue(Columns_header.get(1).getText());
	
									 r1.createCell(2).setCellValue(Columns_header.get(2).getText());
									 s.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));					 
									 
									r1.createCell(4).setCellValue(Columns_header.get(3).getText());
									 s.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));  
									 r1.createCell(6).setCellValue(Columns_header.get(4).getText());
									 s.addMergedRegion(new CellRangeAddress(0, 0, 6, 7));
							  }
							  
							 	  
							  
						  
						
						  //Loop will execute till the last row of table.
						  for (int row=1; row<rows_count; row++){
						   //To locate columns(cells) of that specific row.
						   List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
						   
						   //To calculate no of columns(cells) In that specific row.
						   int columns_count = Columns_row.size();
						  // System.out.println("Number of cells In Row "+row+" are "+columns_count);
						   Row r=s.createRow(row);
						   
						    		//Loop will execute till the last cell of that specific row.
						   			for (int column=0; column<columns_count; column++){
						   			//To retrieve text from that specific cell.
						   				String celtext = Columns_row.get(column).getText();
						   			
						   				//create cell in excel & store value
						   				r.createCell(column).setCellValue(celtext);
						   				/*if(!celtext.isEmpty())
						   				{
						   					System.out.println("Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);
						   				}*/
	
						   				
					
						   				}
						   			/*System.out.println("--------------------------------------------------");*/
						  }  
						  FileOutputStream fos=new FileOutputStream(filePath);
							wb.write(fos);
							fos.close();
				 }
				 else
				 {
					 System.out.println("File not created");
				 }
				 
			 }
			 else
			 {
				 System.out.println("Folder not created"); 
			 }
	
			 GlobalVariables.result=TestBaseConstants.RESULT_PASSVALUE;
			 GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
		rATUStatus(GlobalVariables.result,msg);
	
	
 
		}
		catch(Exception e)
		{
			GlobalVariables.exceptionMsgVal=e.getMessage();
			String ermsg="Error while executing T1_TH2_TBH2_TCNkeyword";
			keywordsErrormsg(GlobalVariables.errormsg,GlobalVariables.exceptionMsgVal,ermsg);
			GlobalVariables.result=TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result,msg);
		}
	  }
	
	/*else if(subReportName.contains("Employment Status by Gender"))
	{

		
		System.out.println("sub report is "+subReportName);
	
		reportNALP_Table2_WriteXLSX
		(GlobalVariables.testCaseIdentifier,
				GlobalVariables.testCaseIdentifier,msg,tableXpath,subReportName);
	}*/
}






/********************************************************************************************
 *		Author 						:	DivyaRaju.R
 *		LastModifiedDate			:	1st may 2015
 *		Method name					:	mT1_TH2_TCN_ReadXLSX
 *		Description					:	This method is used for comparing 1220 app
 *										 data with stored 
 *										value of excel
 *
*********************************************************************************************/
	public static void mT1_TH2_TCN_ReadXLSX(String excelSheetName,String automationId,
		String xpath,String subReportName,String msg) 
	{
		GlobalVariables.testCaseIdentifier=automationId;
	 try
	 {
	 String path=cleanPath(GlobalVariables.CONFIG.getProperty("buildFolderPath"))+
			 "/"+"Build_number_"+GlobalVariables.CONFIG.getProperty("buildNumber")+
			 "/"+TestBaseConstants.BASELINE_BUILD_TYPE+"/"+
			 GlobalVariables.testCaseIdentifier+".xlsx";
	 //System.out.println("Path of file is -->"+path);
	 FileInputStream fis=new FileInputStream(path);
		Workbook wb=WorkbookFactory.create(fis); 
		 
	 //wb.createSheet(year);
	 Sheet s=wb.getSheet(excelSheetName);

	//To locate table.
	  WebElement mytable = GlobalVariables.driver.findElement(By.xpath(xpath));
	  //To locate rows of table.
	  List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
	  //To calculate no of rows In table.
	  int rows_count = rows_table.size();
	  
	  ///headers
	  List<WebElement> Columns_header = rows_table.get(0).findElements(By.tagName("th"));
	  
	if(subReportName.equalsIgnoreCase("The Graduating Class (A)"))
		
	{
		/*//get 0th cell data from Excel
		  System.out.println(s.getRow(0).getCell(0));
		//get 0th cell data from header of webtable 
		  System.out.println(Columns_header.get(0).getText());*/
		 
		 /* Assert.assertEquals(Columns_header.get(0).getText(),
		s.getRow(0).getCell(0).getStringCellValue());*/
		updateError(0,0,s.getRow(0).getCell(0).getStringCellValue()
				,Columns_header.get(0).getText());
	  
	/*	//get 1st cell data from Excel
		  System.out.println(s.getRow(0).getCell(1));
		//get 1st cell data from webtable
		  System.out.println(Columns_header.get(1).getText());*/
		 
		/* Assert.assertEquals(Columns_header.get(1).getText(), 
		s.getRow(0).getCell(1).getStringCellValue());*/
		updateError(0,1,s.getRow(0).getCell(1).getStringCellValue()
				,Columns_header.get(1).getText()); 
		  
	/*	//get 5th cell data from Excel
		  System.out.println(s.getRow(0).getCell(5));
		//get 2nd  cell data from webtable
		  System.out.println(Columns_header.get(2).getText());*/
		 
		  /*Assert.assertEquals(Columns_header.get(2).getText(),
				  s.getRow(0).getCell(5).getStringCellValue());*/
		  
		updateError(0,2,s.getRow(0).getCell(5).getStringCellValue()
				,Columns_header.get(2).getText()); 
	  
	
		/*//get 9th cell data from Excel
		  System.out.println(s.getRow(0).getCell(9));
		//get 3rd cell data from webtable
		  System.out.println(Columns_header.get(3).getText());*/
	
		/*  Assert.assertEquals(Columns_header.get(3).getText(),
				  s.getRow(0).getCell(9).getStringCellValue());	*/
		  updateError(0,3,
				  s.getRow(0).getCell(9).getStringCellValue()
				  ,Columns_header.get(3).getText()); 
		  
	}
	else if(subReportName.equalsIgnoreCase("Timing of First Job Offer")||
			subReportName.equalsIgnoreCase("Timing of Job Acceptances"))
		
	{
	/*	//get 0th cell data from Excel
		  System.out.println(s.getRow(0).getCell(0));
		//get 0th cell data from webtable
		  System.out.println(Columns_header.get(0).getText());*/
	
		  /*Assert.assertEquals(Columns_header.get(0).getText(),
				  s.getRow(0).getCell(0).getStringCellValue());*/
		  updateError(0,0,
				  s.getRow(0).getCell(0).getStringCellValue()
				  ,Columns_header.get(0).getText()); 
	  
	/*	//get 1st cell data from Excel
		  System.out.println(s.getRow(0).getCell(1));
		//get 1st cell data from webtable
		  System.out.println(Columns_header.get(1).getText());*/

		  /*Assert.assertEquals(Columns_header.get(1).getText(),
				  s.getRow(0).getCell(1).getStringCellValue());*/
		  updateError(0,1,
				  s.getRow(0).getCell(1).getStringCellValue()
				  ,Columns_header.get(1).getText());
		  
/*		//get 2nd  cell data from Excel
		  System.out.println(s.getRow(0).getCell(2));
		//get 2nd cell data from webtable
		  System.out.println(Columns_header.get(2).getText());*/
	
		 /* Assert.assertEquals(Columns_header.get(2).getText(),
				  s.getRow(0).getCell(2).getStringCellValue());*/
		  updateError(1,2,
				  s.getRow(0).getCell(2).getStringCellValue()
				  ,Columns_header.get(2).getText());
	
/*		//get 4th cell data from Excel
		  System.out.println(s.getRow(0).getCell(4));
		//get 3rd cell data from webtable
		  System.out.println(Columns_header.get(3).getText());*/
		 
		 /* Assert.assertEquals(Columns_header.get(3).getText(), 
				  s.getRow(0).getCell(4).getStringCellValue());*/
		  updateError(0,4, 
				  s.getRow(0).getCell(4).getStringCellValue()
				  ,Columns_header.get(3).getText());
		  
		  //get 6th cell data from Excel
		  /*	  System.out.println(s.getRow(0).getCell(6));
			//get 4th cell data from webtable
			  System.out.println(Columns_header.get(4).getText());*/
			
			  /*Assert.assertEquals(Columns_header.get(4).getText(),
					  s.getRow(0).getCell(6).getStringCellValue());*/
			  updateError(0,6,
					  s.getRow(0).getCell(6).getStringCellValue(),
					  Columns_header.get(4).getText());
			  	
	}
	  
		  
	  //Loop will execute till the last row of table.
	  for (int row=1; row<rows_count; row++){
	   //To locate columns(cells) of that specific row.
	   List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
	   Row r=s.getRow(row);
	   //To calculate no of columns(cells) In that specific row.
	   int columns_count = Columns_row.size();
	   //System.out.println("Number of cells In Row "+row+" are "+columns_count);
	  // Row r=s.createRow(row);
	   
	    		//Loop will execute till the last cell of that specific row.
	   			for (int column=0; column<columns_count; column++){
	   			//To retrieve text from that specific cell.
	   				String webtext = Columns_row.get(column).getText();
	   				String xltext = r.getCell(column).getStringCellValue();
	   				/*System.out.println("Value from web site-->"+webtext);
	   				System.out.println("Value from excel------>"+xltext);*/
	   				
	   				
	   				updateError(row,column,xltext,webtext);
	   				
	   				
	   				}
	   				
	   			
	  }  
	  FileOutputStream fos=new FileOutputStream(path);
		wb.write(fos);
		fos.close();
		GlobalVariables.result=TestBaseConstants.RESULT_PASSVALUE;
  		 GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
  	rATUStatus(GlobalVariables.result,msg);		
	 }
	catch(Exception e)
	{
		GlobalVariables.exceptionMsgVal=e.getMessage();
		String ermsg="Error while executing mT1_TH2_TCN_ReadXLSX keyword";
		keywordsErrormsg(GlobalVariables.errormsg,GlobalVariables.exceptionMsgVal,ermsg);
		GlobalVariables.result=TestBaseConstants.RESULT_FAILVALUE;
		GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
		GlobalVariables.APPICATION_LOGS.error(ermsg);
		Logs.errorLog(ermsg);
		rATUStatus(GlobalVariables.result,ermsg);
	}
	 	 
}


	 



	 
	 
	 /********************************************************************************************
	  *		Author 						:	DivyaRaju.R
	  *		LastModifiedDate			:	1st may 2015
	  *		Method name					:	updateError
	  *		Description					:	This method is used for updating error in given format
	  *
	 *********************************************************************************************/
	 public static void updateError(int row, int column,String expected,String actual)
	 {
		 String msg="Validating  expected ->"+expected+" with actual->"+actual;
		
		 String status=CustomVerification.assertEqualsTest(expected, actual);
		 msg=msg+" and status is --->"+status;
			GlobalVariables.APPICATION_LOGS.info(msg);
			Logs.infoLog(msg);
		 if(status.contains("Fail"))
			{
			 GlobalVariables.APPICATION_LOGS.error("Validation status is fail hence creating failed folder ");
			 String buildFolderpath=cleanPath(GlobalVariables.CONFIG.getProperty("buildFolderPath"));
				String buildNumber=GlobalVariables.CONFIG.getProperty("buildNumber");
			 String filePath=buildFolderpath+
					 "/"+"Build_number_"+buildNumber+
					 "/"+"Post_Build"+"/Failed/";
			 	File f= new File(filePath);
				boolean folder=ExcelTestUtil.createFolder(GlobalVariables.CONFIG.getProperty("buildType"));
				GlobalVariables.APPICATION_LOGS.info("Folder created --->"+folder);
				GlobalVariables.APPICATION_LOGS.info("File path exists--->"+f.exists());
				if(folder|| f.exists() )
				{
					GlobalVariables.APPICATION_LOGS.info("Copying excel file "+GlobalVariables.testCaseIdentifier +
							"to failed file as -->"+GlobalVariables.testCaseIdentifier+"_Failed");
				ExcelTestUtil.excelFileCopy(GlobalVariables.testCaseIdentifier+".xlsx",
						GlobalVariables.testCaseIdentifier+"_Failed"+".xlsx");
				//r.createCell(column).setCellValue(xltext);\String buildFolderpath=cleanPath(GlobalVariables.CONFIG.getProperty("buildFolderPath"));
				 
		 	 
				String errorUpdateFolderPath=buildFolderpath+
					 "/"+"Build_number_"+buildNumber+
					 "/"+"Post_Build"+"/Failed/";
				ExcelTestUtil.setExcelData(errorUpdateFolderPath+
						GlobalVariables.testCaseIdentifier+"_Failed"+".xlsx",
						GlobalVariables.testCaseIdentifier,row,column,expected+"**"+actual);
				
				GlobalVariables.APPICATION_LOGS.info("Setting data to excel."+
						expected+"**"+actual);
				//CustomVerification.verifyContent(false,"Validation status of "+expected+" and "+actual+" is " +status);
				}
			}			
		
	 }
	 

	 
	 
	 
	 
	 
	 
	 /********************************************************************************************
		 *		Author 						:	DivyaRaju.R
		 *		LastModifiedDate			:	1st may 2015
		 *		Method name					:	mT1_TH2_TBH2_TCN_WriteXLSX
		 *		Description					:	This method is used for fetching data from 1220
		 *										 application
		 *										 and writing that excel
		 *
		*********************************************************************************************/
	 
	 
	 public static void mT1_TH2_TBH2_TCN_WriteXLSX(String sheetName,String excelFileName,
			 String msg,String tableXpath,String subReportName) 
	 {
		 boolean xlFileCreated=false;
			
		 try
		 {//Pre_Build_Number
				// fetch the folder path to create work book
			String folderPath=cleanPath(GlobalVariables.CONFIG.getProperty("buildFolderPath"))+
						 "/"+"Build_number_"+GlobalVariables.CONFIG.getProperty("buildNumber")+
						 "/"+GlobalVariables.CONFIG.getProperty("buildType")+"/";
			//System.out.println("Build folder path is "+folderPath);
				
			File  preBuildFolderPath=new File( folderPath);
				 
				 //Create directory
		    boolean folderCreated=preBuildFolderPath.mkdirs();
			String filePath=preBuildFolderPath+"/"+GlobalVariables.testCaseIdentifier+".xlsx";
			File filePath1 =new File(filePath);
				 //System.out.println("File Path is -->"+filePath);
	        if(folderCreated||preBuildFolderPath.exists())
			 {
			  if(filePath1.exists())
				{
	    			filePath1.delete();
					xlFileCreated= ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);		 
				}
			 else 
				{
				 xlFileCreated= ExcelTestUtil.createXLS(filePath,
						 GlobalVariables.testCaseIdentifier);
				}
					 /*//System.out.println("Folder created yes or no-->"+success);
					 boolean xlFileCreated= ExcelTestUtil.createXLS(filePath, sheetName);*/
			 if(xlFileCreated)
			 {
				 
			 if(subReportName.equalsIgnoreCase("Employment Status by Gender"))
			 {
				 
	 		 //fetch table xpath
	 		 WebElement mytable = GlobalVariables.driver.findElement(By.xpath(tableXpath));
	 		  //To locate rows of table.
	 		 List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
	 		  
	 		  // open stream to open file
	 		 FileInputStream fis=new FileInputStream(filePath);
	 	     Workbook wb=WorkbookFactory.create(fis);
	 			//get row size
	 		 int rows_count = rows_table.size();	 			 
	 		 Sheet s=wb.getSheet(GlobalVariables.testCaseIdentifier);
	 			 
	 			
	 		
	 			 
	 			 /*** Fetch headers and store to excel of 1st col**/
	 			 // first row of header
	 			 List<WebElement> Columns_header = rows_table.get(0).findElements(By.tagName("th"));
	 			 //create first row  
	 			 Row r1=s.createRow(0);
	 			  String j=Columns_header.get(0).getText();
	 			 // add 1st cell value
	 			  r1.createCell(0).setCellValue(j);
	 			  // merge the text based on rowspan or colspan
	 				 s.addMergedRegion(new CellRangeAddress(0, 1, 0, 1));
	 			 
	 			 // add value to cell 2
	 			 r1.createCell(2).setCellValue(Columns_header.get(1).getText());
	 			 s.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));
	 			 
	 			// add value to cell 3
	 			 r1.createCell(4).setCellValue(Columns_header.get(2).getText());
	 			 s.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));
	 			 
	 			// second row of header
	 			  r1=s.createRow(1);
	 			  
	 			  // fetch 2nd row contents 
	 			 Columns_header = rows_table.get(1).findElements(By.tagName("th")); 
	 			 int cellVal=2;
	 			 int colVal=0;
	 			 // enter values to cols 2 to 6 
	 			  while(cellVal<6 &&colVal<4)
	 			  {
	 			  r1.createCell(cellVal).setCellValue(Columns_header.get(colVal).getText());
	 			  cellVal++;
	 			  colVal++;	 			   
	 			  }		 			 			
	 			 // first row span content
	 			 
	 			 
	 			 Columns_header = rows_table.get(2).findElements(By.tagName("th")); 
	 			 r1=s.createRow(2);
	 			
	 			 r1.createCell(0).setCellValue(Columns_header.get(0).getText());
	 			 s.addMergedRegion(new CellRangeAddress(2, 7, 0, 0));
	 			//System.out.println(Columns_header.get(1).getText());
	 			r1.createCell(1).setCellValue(Columns_header.get(1).getText());
	 			 Columns_header = rows_table.get(2).findElements(By.tagName("td"));
	 			  cellVal=2;
	 				  colVal=0;
	 			  while(cellVal<6 &&colVal<4)
	 			  {
	 			  r1.createCell(cellVal).setCellValue(Columns_header.get(colVal).getText());
	 			  cellVal++;
	 			  colVal++;
	 			   
	 			  }	
	 			
	 			// second rows pan content
	 			
	 			Columns_header = rows_table.get(8).findElements(By.tagName("th")); 
	 			 r1=s.createRow(8);
	 			// System.out.println(Columns_header.get(0).getText());
	 			 r1.createCell(0).setCellValue(Columns_header.get(0).getText());
	 			 s.addMergedRegion(new CellRangeAddress(8, 11, 0, 0));
	 			 
	 			//System.out.println(Columns_header.get(1).getText());
	 			r1.createCell(1).setCellValue(Columns_header.get(1).getText());
	 			 Columns_header = rows_table.get(8).findElements(By.tagName("td"));
	 			  cellVal=2;
	 			  colVal=0;
	 			  while(cellVal<6 &&colVal<4)
	 			  {
	 			  r1.createCell(cellVal).setCellValue(Columns_header.get(colVal).getText());
	 			  cellVal++;
	 			  colVal++;
	 			  					   
	 			  }	
	 			
	 			// third colspan content
	 			Columns_header = rows_table.get(12).findElements(By.tagName("th")); 
	 			 r1=s.createRow(12);
	 			
	 			 r1.createCell(0).setCellValue(Columns_header.get(0).getText());
	 			 s.addMergedRegion(new CellRangeAddress(12, 12, 0, 1));
	 			 Columns_header = rows_table.get(12).findElements(By.tagName("td"));
	 			 cellVal=2;
	 			  colVal=0;
	 			 while(cellVal<6 &&colVal<4)
	 			  {
	 			  r1.createCell(cellVal).setCellValue(Columns_header.get(colVal).getText());
	 			  cellVal++;
	 			  colVal++;
	 			  					   
	 			  }	
	 			 
	 			 
	 			 // Fetch the contents of table from row 3 to last 7th row
	 			 for (int row=3; row<8; row++)
	 			 {
	 			 List<WebElement> Columns_row = rows_table.get(row).
	 					 findElements(By.tagName("th"));
	 			
	 		
	 			 Row r=s.createRow(row);
	 			 String celtext = Columns_row.get(0).getText();
	 			
	 			 r.createCell(1).setCellValue(celtext);	 
	 			 
	 			
	 				   //To locate columns(cells) of that specific row.
	 				  Columns_row = rows_table.get(row).findElements(By.tagName("td"));
	 				   
	 				
	 				 
	 				   
	 				   cellVal=2;
	 					  colVal=0;
	 					  while(cellVal<6 &&colVal<4)
	 					  {
	 					  r.createCell(cellVal).setCellValue(Columns_row.get(colVal).getText());
	 					  cellVal++;
	 					  colVal++;
	 					   
	 					  }
	 			 }
	 			 // Fetch the contents of table from row 8 to last 12th row
	 			 for (int row=9; row<rows_count-1; row++)
	 			 {
	 			 List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("th")); 
	 			
	 		
	 			 Row r=s.createRow(row);
	 			 String celtext = Columns_row.get(0).getText();
	 	
	 			 r.createCell(1).setCellValue(celtext);	
	 			 
	 		
	 				  Columns_row = rows_table.get(row).findElements(By.tagName("td"));
	 				  cellVal=2;
	 				  colVal=0;
	 				  while(cellVal<6 &&colVal<4)
	 				  {
	 				  r.createCell(cellVal).setCellValue(Columns_row.get(colVal).getText());
	 				  cellVal++;
	 				  colVal++;
	 				   
	 				  }			
	 				   			
	 			
	 			 }
	 		
	 			 FileOutputStream fos=new FileOutputStream(filePath);
	 				wb.write(fos);
	 				fos.close();
			 }	//Employment Status by Gender report
			 //
			 else if(subReportName.equalsIgnoreCase("Employment Status by Age at Graduation")
					 ||subReportName.equalsIgnoreCase("Employment Status by Race/Ethnicity") )
			 {//Employment Status by Age at Graduation
				//fetch table xpath
		 		 WebElement mytable = GlobalVariables.driver.findElement(By.xpath(tableXpath));
		 		  //To locate rows of table.
		 		 List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));		 		  
		 		  // open stream to open file
		 		 FileInputStream fis=new FileInputStream(filePath);
		 	     Workbook wb=WorkbookFactory.create(fis);
		 			//get row size
		 		 int rows_count = rows_table.size();	 			 
		 		 Sheet s=wb.getSheet(GlobalVariables.testCaseIdentifier);
		 		 List<WebElement> Columns_header = rows_table.get(0).findElements(By.tagName("th"));
	 			 //create first row  
	 			 Row r1=s.createRow(0);
	 			  String j=Columns_header.get(0).getText();
	 			 // add 1st cell value
	 			  r1.createCell(0).setCellValue(j);
	 			  // merge the text based on rowspan or colspan
	 				 s.addMergedRegion(new CellRangeAddress(0, 1, 0, 1));
	 			 
	 			 // add value to cell 2
	 			 r1.createCell(2).setCellValue(Columns_header.get(1).getText());
	 			 s.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));
	 			 
	 			// add value to cell 3
	 			 r1.createCell(4).setCellValue(Columns_header.get(2).getText());
	 			 s.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));
	 			 
	 			// add value to cell 4
	 			 r1.createCell(6).setCellValue(Columns_header.get(3).getText());
	 			 s.addMergedRegion(new CellRangeAddress(0, 0, 6, 7));
	 			// add value to cell 5
	 			 r1.createCell(8).setCellValue(Columns_header.get(4).getText());
	 			 s.addMergedRegion(new CellRangeAddress(0, 0,8 , 9));
	 			 
	 			// add value to cell 6
	 			 r1.createCell(10).setCellValue(Columns_header.get(5).getText());
	 			 s.addMergedRegion(new CellRangeAddress(0, 0, 10, 11));
	 			 
	 			// second row of header
	 			  r1=s.createRow(1);
	 			  
	 			  // fetch 2nd row contents 
	 			 Columns_header = rows_table.get(1).findElements(By.tagName("th")); 
	 			 int cellVal=2;
	 			 int colVal=0;
	 			 // enter values to cols 2 to 12 
	 			  while(cellVal<12 &&colVal<10)
	 			  {
	 			  r1.createCell(cellVal).setCellValue(Columns_header.get(colVal).getText());
	 			  cellVal++;
	 			  colVal++;	 			   
	 			  }		 	
	 			 
	 			// 3rd row-first col span content i.e fetch Employed value 
		 			 
		 			 
		 			 Columns_header = rows_table.get(2).findElements(By.tagName("th")); 
		 			 r1=s.createRow(2);
		 			
		 			 r1.createCell(0).setCellValue(Columns_header.get(0).getText());
		 			 s.addMergedRegion(new CellRangeAddress(2, 7, 0, 0));
		 			//System.out.println(Columns_header.get(1).getText());
		 			r1.createCell(1).setCellValue(Columns_header.get(1).getText());
		 			 Columns_header = rows_table.get(2).findElements(By.tagName("td"));
		 			  cellVal=2;
		 				  colVal=0;
		 			  while(cellVal<12 &&colVal<10)
		 			  {
		 			  r1.createCell(cellVal).setCellValue(Columns_header.get(colVal).getText());
		 			  cellVal++;
		 			  colVal++;
		 			   
		 			  }	
		 			// second row pan content
			 			
			 			Columns_header = rows_table.get(8).findElements(By.tagName("th")); 
			 			 r1=s.createRow(8);
			 			// System.out.println(Columns_header.get(0).getText());
			 			 r1.createCell(0).setCellValue(Columns_header.get(0).getText());
			 			 s.addMergedRegion(new CellRangeAddress(8, 11, 0, 0));
			 			 
			 			//System.out.println(Columns_header.get(1).getText());
			 			r1.createCell(1).setCellValue(Columns_header.get(1).getText());
			 			 Columns_header = rows_table.get(8).findElements(By.tagName("td"));
			 			  cellVal=2;
			 			  colVal=0;
			 			  while(cellVal<12 &&colVal<10)
			 			  {
			 			  r1.createCell(cellVal).setCellValue(Columns_header.get(colVal).getText());
			 			  cellVal++;
			 			  colVal++;
			 			  					   
			 			  }	
			 			
			 			// third col span content
			 			Columns_header = rows_table.get(12).findElements(By.tagName("th")); 
			 			 r1=s.createRow(12);
			 			
			 			 r1.createCell(0).setCellValue(Columns_header.get(0).getText());
			 			 s.addMergedRegion(new CellRangeAddress(12, 12, 0, 1));
			 			 Columns_header = rows_table.get(12).findElements(By.tagName("td"));
			 			 cellVal=2;
			 			  colVal=0;
			 			 while(cellVal<12 &&colVal<10)
			 			  {
			 			  r1.createCell(cellVal).setCellValue(Columns_header.get(colVal).getText());
			 			  cellVal++;
			 			  colVal++;
			 			  					   
			 			  }	
			 			 // Fetch the contents of table from row 8 to last 12th row
			 			 for (int row=9; row<rows_count-1; row++)
			 			 {
			 			 List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("th")); 
			 			
			 		
			 			 Row r=s.createRow(row);
			 			 String celtext = Columns_row.get(0).getText();
			 	
			 			 r.createCell(1).setCellValue(celtext);	
			 			 
			 		
			 				  Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			 				  cellVal=2;
			 				  colVal=0;
			 				  while(cellVal<12 &&colVal<10)
			 				  {
			 				  r.createCell(cellVal).setCellValue(Columns_row.get(colVal).getText());
			 				  cellVal++;
			 				  colVal++;
			 				   
			 				  }			
			 				   			
			 			
			 			 }
			 		
			 			 FileOutputStream fos=new FileOutputStream(filePath);
			 				wb.write(fos);
			 				fos.close();	
		 			  
			 }//Employment Status by Age at 
			 
			 else if(subReportName.equalsIgnoreCase("Employer Detail by Gender")||
				 subReportName.equalsIgnoreCase("Graduation Employer Detail by Race/Ethnicity") )
			 {
				 WebElement mytable = GlobalVariables.driver.findElement(By.xpath(tableXpath));
		 		  //To locate rows of table.
		 		 List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));		 		  
		 		  // open stream to open file
		 		 FileInputStream fis=new FileInputStream(filePath);
		 	     Workbook wb=WorkbookFactory.create(fis);
		 			//get row size
		 		// int rows_count = rows_table.size();	 			 
		 		 Sheet s=wb.getSheet(GlobalVariables.testCaseIdentifier);
		 		 List<WebElement> Columns_header = rows_table.get(0).findElements(By.tagName("th"));
	 			 //create first row  
	 			 Row r1=s.createRow(0);
	 			  String j=Columns_header.get(0).getText(); 
	 			 r1.createCell(0).setCellValue(j);
	 			  // merge the text based on rowspan or colspan
	 				 s.addMergedRegion(new CellRangeAddress(0, 1, 0, 1));
	 			 
	 			 // add value to cell 2
	 			 r1.createCell(2).setCellValue(Columns_header.get(1).getText());
	 			 s.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));
	 			 
	 			// add value to cell 3
	 			 r1.createCell(4).setCellValue(Columns_header.get(2).getText());
	 			 s.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));
	 			 
	 			// add value to cell 4
	 			 r1.createCell(6).setCellValue(Columns_header.get(3).getText());
	 			 s.addMergedRegion(new CellRangeAddress(0, 0, 6, 7));
	 			 
	 			// second row of header
	 			  r1=s.createRow(1);
	 			  
	 			  // fetch 2nd row contents 
	 			 Columns_header = rows_table.get(1).findElements(By.tagName("th")); 
	 			 int cellVal=2;
	 			 int colVal=0;
	 			 // enter values to cols 2 to 12 
	 			  while(cellVal<8 &&colVal<6)
	 			  {
	 			  r1.createCell(cellVal).setCellValue(Columns_header.get(colVal).getText());
	 			  cellVal++;
	 			  colVal++;	 			   
	 			  }
	 			  
	 			// 3rd row-first row span content i.e fetch Employed value 
		 			 
		 			 
		 			 Columns_header = rows_table.get(2).findElements(By.tagName("th")); 
		 			 r1=s.createRow(2);
		 			
		 			 r1.createCell(0).setCellValue(Columns_header.get(0).getText());
		 			 s.addMergedRegion(new CellRangeAddress(2, 5, 0, 0));
		 			//System.out.println(Columns_header.get(1).getText());
		 			r1.createCell(1).setCellValue(Columns_header.get(1).getText());
		 			 Columns_header = rows_table.get(2).findElements(By.tagName("td"));
		 			  cellVal=2;
		 				  colVal=0;
		 			  while(cellVal<8 &&colVal<6)
		 			  {
		 			  r1.createCell(cellVal).setCellValue(Columns_header.get(colVal).getText());
		 			  cellVal++;
		 			  colVal++;
		 			   
		 			  }	
		 			  
		 			// second row pan content
			 			
			 			Columns_header = rows_table.get(6).findElements(By.tagName("th")); 
			 			 r1=s.createRow(6);
			 			// System.out.println(Columns_header.get(0).getText());
			 			 r1.createCell(0).setCellValue(Columns_header.get(0).getText());
			 			 s.addMergedRegion(new CellRangeAddress(6, 17, 0, 0));
			 			 
			 			//System.out.println(Columns_header.get(1).getText());
			 			r1.createCell(1).setCellValue(Columns_header.get(1).getText());
			 			 Columns_header = rows_table.get(6).findElements(By.tagName("td"));
			 			  cellVal=2;
			 			  colVal=0;
			 			  while(cellVal<8 &&colVal<6)
			 			  {
			 			  r1.createCell(cellVal).setCellValue(Columns_header.get(colVal).getText());
			 			  cellVal++;
			 			  colVal++;
			 			  					   
			 			  }	  
			 			// 18th row with no row or col span
				 			Columns_header = rows_table.get(18).findElements(By.tagName("th")); 
				 			 r1=s.createRow(18);
				 			
				 			 r1.createCell(0).setCellValue(Columns_header.get(0).getText());
				 			r1.createCell(1).setCellValue(Columns_header.get(1).getText());
				 			 Columns_header = rows_table.get(18).findElements(By.tagName("td"));
				 			 cellVal=2;
				 			  colVal=0;
				 			 while(cellVal<8 &&colVal<6)
				 			  {
				 			  r1.createCell(cellVal).setCellValue(Columns_header.get(colVal).getText());
				 			  cellVal++;
				 			  colVal++;
				 			  					   
				 			  }	 
				 			 
				 			// third row span content
					 			Columns_header = rows_table.get(19).findElements(By.tagName("th")); 
					 			 r1=s.createRow(19);
					 			
					 			 r1.createCell(0).setCellValue(Columns_header.get(0).getText());
					 			 s.addMergedRegion(new CellRangeAddress(19, 27, 0, 1));
					 			r1.createCell(1).setCellValue(Columns_header.get(1).getText());
					 			 Columns_header = rows_table.get(19).findElements(By.tagName("td"));
					 			 cellVal=2;
					 			  colVal=0;
					 			 while(cellVal<8 &&colVal<6)
					 			  {
					 			  r1.createCell(cellVal).setCellValue(Columns_header.get(colVal).getText());
					 			  cellVal++;
					 			  colVal++;
					 			  					   
					 			  }	
					 			 
					 			// fourth row span content
						 			Columns_header = rows_table.get(28).findElements(By.tagName("th")); 
						 			 r1=s.createRow(28);
						 			
						 			 r1.createCell(0).setCellValue(Columns_header.get(0).getText());
						 			 s.addMergedRegion(new CellRangeAddress(28, 32, 0, 1));
						 			r1.createCell(1).setCellValue(Columns_header.get(1).getText());
						 			 Columns_header = rows_table.get(28).findElements(By.tagName("td"));
						 			 cellVal=2;
						 			  colVal=0;
						 			 while(cellVal<8 &&colVal<6)
						 			  {
						 			  r1.createCell(cellVal).setCellValue(Columns_header.get(colVal).getText());
						 			  cellVal++;
						 			  colVal++;
						 			  					   
						 			  }	
						 			 
					// fetch table row contents
						 			 
				 			// Fetch the contents of table from row 3 to last 7th row
						 			 for (int row=3; row<6; row++)
						 			 {
						 			 List<WebElement> Columns_row = rows_table.get(row).
						 					 findElements(By.tagName("th"));
						 			
						 		
						 			 Row r=s.createRow(row);
						 			 String celtext = Columns_row.get(0).getText();
						 			
						 			 r.createCell(1).setCellValue(celtext);	 
						 			 
						 			
						 				   //To locate columns(cells) of that specific row.
						 				  Columns_row = rows_table.get(row).findElements(By.tagName("td"));
						 				   
						 				
						 				 
						 				   
						 				   cellVal=2;
						 					  colVal=0;
						 					  while(cellVal<8 &&colVal<6)
						 					  {
						 					  r.createCell(cellVal).setCellValue(Columns_row.get(colVal).getText());
						 					  cellVal++;
						 					  colVal++;
						 					   
						 					  }
						 			 }
	 			
						 			//fetch contents of second block 
						 			 for (int row=7; row<17; row++)
						 			 {
						 			 List<WebElement> Columns_row = rows_table.get(row).
						 					 findElements(By.tagName("th"));
						 			
						 		
						 			 Row r=s.createRow(row);
						 			 String celtext = Columns_row.get(0).getText();
						 			
						 			 r.createCell(1).setCellValue(celtext);	 
						 			 
						 			
						 				   //To locate columns(cells) of that specific row.
						 				  Columns_row = rows_table.get(row).findElements(By.tagName("td"));
						 				   
						 				
						 				 
						 				   
						 				   cellVal=2;
						 					  colVal=0;
						 					  while(cellVal<8 &&colVal<6)
						 					  {
						 					  r.createCell(cellVal).setCellValue(Columns_row.get(colVal).getText());
						 					  cellVal++;
						 					  colVal++;
						 					   
						 					  }
						 			 }			 			 
						 			 
						 			//fetch contents of 4th block 
						 			 for (int row=19; row<27; row++)
						 			 {
						 			 List<WebElement> Columns_row = rows_table.get(row).
						 					 findElements(By.tagName("th"));
						 			
						 		
						 			 Row r=s.createRow(row);
						 			 String celtext = Columns_row.get(0).getText();
						 			
						 			 r.createCell(1).setCellValue(celtext);	 
						 			 
						 			
						 				   //To locate columns(cells) of that specific row.
						 				  Columns_row = rows_table.get(row).findElements(By.tagName("td"));
						 				   
						 				
						 				 
						 				   
						 				   cellVal=2;
						 					  colVal=0;
						 					  while(cellVal<8 &&colVal<6)
						 					  {
						 					  r.createCell(cellVal).setCellValue(Columns_row.get(colVal).getText());
						 					  cellVal++;
						 					  colVal++;
						 					   
						 					  }
						 			 }				 			 
						 			 
						 			//fetch contents of 5th block 
						 			 for (int row=28; row<32; row++)
						 			 {
						 			 List<WebElement> Columns_row = rows_table.get(row).
						 					 findElements(By.tagName("th"));
						 			
						 		
						 			 Row r=s.createRow(row);
						 			 String celtext = Columns_row.get(0).getText();
						 			
						 			 r.createCell(1).setCellValue(celtext);	 
						 			 
						 			
						 				   //To locate columns(cells) of that specific row.
						 				  Columns_row = rows_table.get(row).findElements(By.tagName("td"));
						 				   
						 				
						 				 
						 				   
						 				   cellVal=2;
						 					  colVal=0;
						 					  while(cellVal<8 &&colVal<6)
						 					  {
						 					  r.createCell(cellVal).setCellValue(Columns_row.get(colVal).getText());
						 					  cellVal++;
						 					  colVal++;
						 					   
						 					  }
						 			 }			
			 }//Employer Detail by Gender or Employer Detail by Race/Ethnicity
			 
			 
	 	 }// excel file created
	 	 
		 else
		 {
				// System.out.println("File not created");
				 GlobalVariables.APPICATION_LOGS.error("File not created");
				 Logs.errorLog("File not created");
		 }
			 
		 }
		 else
		 {
			// System.out.println("Folder not created"); 
			 GlobalVariables.APPICATION_LOGS.error("Folder not created");
			 Logs.errorLog("Folder not created");
		 }
	
				 GlobalVariables.result=TestBaseConstants.RESULT_PASSVALUE;
				 GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
				 rATUStatus(GlobalVariables.result,msg);
	
	
 
}
	catch(Exception e)
	{
		GlobalVariables.exceptionMsgVal=e.getMessage();
		String ermsg="Error while executing mT1_TH2_TBH2_TCN_write keyword";
		keywordsErrormsg(GlobalVariables.errormsg,GlobalVariables.exceptionMsgVal,ermsg);
		GlobalVariables.result=TestBaseConstants.RESULT_FAILVALUE;
		GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
		GlobalVariables.APPICATION_LOGS.error(ermsg);
		Logs.errorLog(ermsg);
		rATUStatus(GlobalVariables.result,msg);
	}
			
}


	 /********************************************************************************************
		 *		Author 						:	DivyaRaju.R
		 *		LastModifiedDate			:	1st may 2015
		 *		Method name					:	mT1_TH2_TBH2_TCN_ReadXLSX
		 *		Description					:	This method is used for fetching data from 1220
		 *										 application
		 *										 and writing that excel
		 *
		*********************************************************************************************/
	 
  public static void mT1_TH2_TBH2_TCN_ReadXLSX(String excelSheetName,String automationId,
				String xpath,String subReportName,String msg) 
  {
	GlobalVariables.testCaseIdentifier=automationId;
	try
    {
		 String path=cleanPath(GlobalVariables.CONFIG.getProperty("buildFolderPath"))+
				 "/"+"Build_number_"+GlobalVariables.CONFIG.getProperty("buildNumber")+
				 "/"+TestBaseConstants.BASELINE_BUILD_TYPE+"/"+
				 GlobalVariables.testCaseIdentifier+".xlsx";
		 //System.out.println("Path of file is -->"+path);
		 FileInputStream fis=new FileInputStream(path);
			Workbook wb=WorkbookFactory.create(fis); 
			 
		 //wb.createSheet(year);
		 Sheet s=wb.getSheet(excelSheetName); 
		 
		 if(subReportName.equalsIgnoreCase("Employment Status by Gender"))
				
		 {
			  WebElement mytable = GlobalVariables.driver.findElement(By.xpath(xpath));
			  //To locate rows of table.
			  List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
			  //To calculate no of rows In table.
			  int rows_count = rows_table.size();
			  
			  ///headers
			  List<WebElement> Columns_header = rows_table.get(0).findElements(By.tagName("th"));
			  // Validating first row headers and cell contents
			  
			  updateError(0,0,s.getRow(0).getCell(0).getStringCellValue()
					  ,Columns_header.get(0).getText());
			  updateError(0,2,s.getRow(0).getCell(2).getStringCellValue()
					  ,Columns_header.get(1).getText());
			  updateError(0,4,s.getRow(0).getCell(4).getStringCellValue()
					  ,Columns_header.get(2).getText());
			  
			//2nd row
				Columns_header = rows_table.get(1).findElements(By.tagName("th")); 
				int webText =0;
				int excelTextcel=2;
				while(webText<4 &&excelTextcel<6 )
				{
				/*	System.out.println(Columns_header.get(webText).getText());
					System.out.println(s.getRow(1).getCell(excelTextcel).getStringCellValue());*/
					updateError(0,excelTextcel,s.getRow(1).getCell(excelTextcel).getStringCellValue()
							  ,Columns_header.get(webText).getText());				
					webText++;
					excelTextcel++;
				}
				
				//3rd row
				Columns_header = rows_table.get(2).findElements(By.tagName("th"));
				updateError(2,0,
						s.getRow(2).getCell(0).getStringCellValue()
						  ,Columns_header.get(0).getText());
				updateError(2,1,
						s.getRow(2).getCell(1).getStringCellValue()
						  ,Columns_header.get(1).getText());
				Columns_header = rows_table.get(2).findElements(By.tagName("td"));
				
				webText =0;
				 excelTextcel=2;
				while(webText<4 &&excelTextcel<6 )
				{
					/*System.out.println(Columns_header.get(webText).getText());
					System.out.println(s.getRow(2).getCell(excelTextcel).getStringCellValue());*/
					updateError(2,webText,
							s.getRow(2).getCell(excelTextcel).getStringCellValue()
							  ,Columns_header.get(webText).getText());
					
					webText++;
					excelTextcel++;
				}
				
				// 8th row header 
				
				Columns_header = rows_table.get(12).findElements(By.tagName("th")); 
				/*System.out.println(Columns_header.get(0).getText());
				System.out.println(s.getRow(12).getCell(0).getStringCellValue());*/
				
				updateError(12,0,
						s.getRow(12).getCell(0).getStringCellValue()
						  ,Columns_header.get(0).getText());
				 Columns_header = rows_table.get(12).findElements(By.tagName("td"));
				 webText =0;
				 excelTextcel=2;
				while(webText<4 &&excelTextcel<6 )
				{									
					updateError(12,webText,
							s.getRow(12).getCell(excelTextcel).getStringCellValue()
							  ,Columns_header.get(webText).getText());
					webText++;
					excelTextcel++;
				}
				for (int row=3; row<8; row++)
				 {
					 List<WebElement> Columns_row = rows_table.get(row).
							 findElements(By.tagName("th"));
					/* System.out.println(Columns_row.get(0).getText());
					 System.out.println(s.getRow(row).getCell(1).getStringCellValue());*/
					 updateError(row,1,
							 s.getRow(row).getCell(1).getStringCellValue()
								  ,Columns_row.get(0).getText());
					 Columns_row = rows_table.get(row).findElements(By.tagName("td"));
					 webText =0;
					 excelTextcel=2;
				while(webText<4 &&excelTextcel<6 )
				{
					/*System.out.println(Columns_header.get(webText).getText());
					System.out.println(s.getRow(row).getCell(excelTextcel).getStringCellValue());*/
					updateError(row,1,
					s.getRow(row).getCell(excelTextcel).getStringCellValue(),
					Columns_header.get(webText).getText());					
					webText++;
					excelTextcel++;
				}
				 }
				
				for (int row=9; row<rows_count-1; row++)
				 {
				 List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("th"));
				 updateError(row,1,
						 s.getRow(row).getCell(1).getStringCellValue(),
						 Columns_row.get(0).getText());					 
				 
				 Columns_row = rows_table.get(row).findElements(By.tagName("td"));
				 webText =0;
				 excelTextcel=2;
				while(webText<4 &&excelTextcel<6 )
				{
					updateError(row,excelTextcel,
							s.getRow(row).getCell(excelTextcel).getStringCellValue(),
							Columns_header.get(webText).getText());	
					webText++;
					excelTextcel++;
				}
				 }
				
		 }
		 
		 else if(subReportName.equalsIgnoreCase("Employment Status by Age at Graduation")||
				 subReportName.equalsIgnoreCase("Employment Status by Race/Ethnicity"))
		 {
			 WebElement mytable = GlobalVariables.driver.findElement(By.xpath(xpath));
			  //To locate rows of table.
			  List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
			  //To calculate no of rows In table.
			  int rows_count = rows_table.size();
			  
			  ///headers
			  List<WebElement> Columns_header = rows_table.get(0).findElements(By.tagName("th"));
			  // Validating first row headers and cell contents
			  //compare 1st cell
			  updateError(0,0,s.getRow(0).getCell(0).getStringCellValue()
					  ,Columns_header.get(0).getText());
			 //compare 2nd cell
			  updateError(0,2,s.getRow(0).getCell(2).getStringCellValue()
					  ,Columns_header.get(1).getText());
			  //compare 3rd cell
			  updateError(0,4,s.getRow(0).getCell(4).getStringCellValue()
					  ,Columns_header.get(2).getText());
			  //compare 4th cell
			  updateError(0,6,s.getRow(0).getCell(4).getStringCellValue()
					  ,Columns_header.get(3).getText());
			  //compare 5th cell
			  updateError(0,8,s.getRow(0).getCell(4).getStringCellValue()
					  ,Columns_header.get(4).getText());
			  //compare 6th cell
			  updateError(0,10,s.getRow(0).getCell(4).getStringCellValue()
					  ,Columns_header.get(5).getText());
			// fetch 2nd row contents 
			  Columns_header = rows_table.get(1).findElements(By.tagName("th")); 
	 			 int cellVal=2;
	 			 int colVal=0;
	 			 // Get values from cols 2 to 12 
	 			  while(cellVal<12 &&colVal<10)
	 			  { 
		 			 updateError(1,colVal,s.getRow(1).getCell(cellVal).getStringCellValue()
							  ,Columns_header.get(colVal).getText());
		 			  cellVal++;
		 			  colVal++;	 			   
	 			  }	
	 			// 3rd row-first col span content i.e fetch Employed value 
	 			 Columns_header = rows_table.get(2).findElements(By.tagName("th")); 
	 			updateError(2,0,s.getRow(2).getCell(0).getStringCellValue()
						  ,Columns_header.get(0).getText());
	 			updateError(2,1,s.getRow(2).getCell(1).getStringCellValue()
						  ,Columns_header.get(1).getText());
	 			
	 			Columns_header = rows_table.get(2).findElements(By.tagName("td"));
	 			  cellVal=2;
	 				  colVal=0;
	 			  while(cellVal<12 &&colVal<10)
	 			  {	 	
		 			 updateError(2,colVal,s.getRow(2).getCell(cellVal).getStringCellValue()
							  ,Columns_header.get(colVal).getText());
		 			  cellVal++;
		 			  colVal++;	 			   
	 			  }	
	 			  
	 			// second row pan content
		 			
		 		Columns_header = rows_table.get(8).findElements(By.tagName("th"));
		 		// fetch from 0th cell
		 		 updateError(8,0,s.getRow(8).getCell(0).getStringCellValue()
						  ,Columns_header.get(0).getText());
		 		updateError(8,1,s.getRow(8).getCell(1).getStringCellValue()
						  ,Columns_header.get(1).getText());
		 		Columns_header = rows_table.get(8).findElements(By.tagName("td"));
	 			  cellVal=2;
	 			  colVal=0;
	 			  while(cellVal<12 &&colVal<10)
	 			  {
	 				  updateError(8,1,s.getRow(8).getCell(cellVal).getStringCellValue()
							  ,Columns_header.get(colVal).getText());
		 			  cellVal++;
		 			  colVal++;
	 			  					   
	 			  }	
	 			  
	 			  
	 			// third col span content
		 			Columns_header = rows_table.get(12).findElements(By.tagName("th")); 
		 			
		 			updateError(12,0,s.getRow(12).getCell(0).getStringCellValue()
							  ,Columns_header.get(0).getText());
		 			
		 			Columns_header = rows_table.get(12).findElements(By.tagName("td"));
		 			 cellVal=2;
		 			  colVal=0;
		 			 while(cellVal<12 &&colVal<10)
		 			  {
		 				updateError(12,1,s.getRow(12).getCell(cellVal).getStringCellValue()
								  ,Columns_header.get(colVal).getText());
		 			  cellVal++;
		 			  colVal++;
		 			  					   
		 			  }	
		 			 
		 			 // Fetch the contents of table from row 8 to last 12th row
		 			 for (int row=9; row<rows_count-1; row++)
		 			 {
		 				List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("th"));
		 				
		 				updateError(row,1,s.getRow(row).getCell(1).getStringCellValue()
								  ,Columns_row.get(0).getText());
		 				
		 				Columns_row = rows_table.get(row).findElements(By.tagName("td"));
		 				  cellVal=2;
		 				  colVal=0;
		 				  while(cellVal<12 &&colVal<10)
		 				  {
		 				 
		 				 updateError(8,1,s.getRow(row).getCell(cellVal).getStringCellValue()
								  ,Columns_row.get(colVal).getText());
		 				  cellVal++;
		 				  colVal++;
		 				   
		 				  }			
		 			 }
	 			
		 }
		 
		 else if(subReportName.equalsIgnoreCase("Employer Detail by Gender")||
				 subReportName.equalsIgnoreCase("Employer Detail by Race/Ethnicity")
				 )
		 {
			  WebElement mytable = GlobalVariables.driver.findElement(By.xpath(xpath));
			  //To locate rows of table.
			  List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
			  //To calculate no of rows In table.
			  
			  
			  ///headers
			  List<WebElement> Columns_header = rows_table.get(0).findElements(By.tagName("th")); 
			  // 1st row compare cell values
			  //compare 1st cell
			  updateError(0,0,s.getRow(0).getCell(0).getStringCellValue()
					  ,Columns_header.get(0).getText());
			  //compare 2nd cell
			  updateError(0,1,s.getRow(0).getCell(2).getStringCellValue()
					  ,Columns_header.get(1).getText());
			  
			  // compare 3rd cell
			  updateError(0,2,s.getRow(0).getCell(4).getStringCellValue()
					  ,Columns_header.get(2).getText());
			  // compare 4th cell
			  updateError(0,3,s.getRow(0).getCell(6).getStringCellValue()
					  ,Columns_header.get(3).getText());
			  
			  // second row of header
			  Columns_header = rows_table.get(1).findElements(By.tagName("th")); 
	 			 int cellVal=2;
	 			 int colVal=0;
	 			 // enter values to cols 2 to 12 
	 			  while(cellVal<8 &&colVal<6)
	 			  {
	 				  updateError(1,cellVal,s.getRow(1).getCell(cellVal).getStringCellValue()
						  ,Columns_header.get(colVal).getText());
	 			  cellVal++;
	 			  colVal++;	 			   
	 			  }
	 			  
	 			// 3rd row-first row span content i.e fetch Employed value 		 			 
		 			 
		 		 Columns_header = rows_table.get(2).findElements(By.tagName("th")); 
		 		 //1st cell	compare	 		 
		 		 updateError(2,0,s.getRow(2).getCell(0).getStringCellValue()
						  ,Columns_header.get(0).getText());
		 		 //2nd cell compare
		 		updateError(2,1,s.getRow(2).getCell(1).getStringCellValue()
						  ,Columns_header.get(1).getText());
		 		Columns_header = rows_table.get(2).findElements(By.tagName("td"));
	 			  cellVal=2;
	 				  colVal=0;
	 			  while(cellVal<8 &&colVal<6)
	 			  {
		 			 updateError(2,cellVal,s.getRow(2).getCell(cellVal).getStringCellValue()
						  ,Columns_header.get(colVal).getText());
	 			  cellVal++;
	 			  colVal++;	 			   
	 			  }	
	 			  
	 			 Columns_header = rows_table.get(6).findElements(By.tagName("th")); 
	 			 //compare 0th cell
	 			 updateError(6,0,s.getRow(6).getCell(0).getStringCellValue()
						  ,Columns_header.get(0).getText());
	 			 // compare 1st cell
	 			updateError(6,1,s.getRow(6).getCell(1).getStringCellValue()
						  ,Columns_header.get(1).getText());
	 			
	 			Columns_header = rows_table.get(6).findElements(By.tagName("td"));
	 			  cellVal=2;
	 			  colVal=0;
	 			  while(cellVal<8 &&colVal<6)
	 			  {
	 				 updateError(6,cellVal,s.getRow(6).getCell(cellVal).getStringCellValue()
							  ,Columns_header.get(colVal).getText());	 			  
	 			  cellVal++;
	 			  colVal++;	 			  					   
	 			  }	 
	 			  
	 			// 18th row with no row or col span
	 			 Columns_header = rows_table.get(18).findElements(By.tagName("th")); 
	 			updateError(18,0,s.getRow(18).getCell(0).getStringCellValue()
						  ,Columns_header.get(0).getText());
	 			updateError(18,1,s.getRow(18).getCell(1).getStringCellValue()
						  ,Columns_header.get(1).getText());
	 			
	 			Columns_header = rows_table.get(18).findElements(By.tagName("td"));
	 			 cellVal=2;
	 			  colVal=0;
	 			 while(cellVal<8 &&colVal<6)
	 			  {
	 				updateError(18,cellVal,s.getRow(18).getCell(cellVal).getStringCellValue()
							  ,Columns_header.get(colVal).getText());
	 			  cellVal++;
	 			  colVal++;
	 			  					   
	 			  }	 
	 			// third row span content
		 			Columns_header = rows_table.get(19).findElements(By.tagName("th"));  
		 			updateError(19,cellVal,s.getRow(19).getCell(0).getStringCellValue()
							  ,Columns_header.get(0).getText());
		 			updateError(19,cellVal,s.getRow(19).getCell(1).getStringCellValue()
							  ,Columns_header.get(1).getText());
		 			
		 			Columns_header = rows_table.get(19).findElements(By.tagName("td"));
		 			 cellVal=2;
		 			  colVal=0;
		 			 while(cellVal<8 &&colVal<6)
		 			  {
		 				updateError(19,cellVal,s.getRow(19).getCell(cellVal).getStringCellValue()
								  ,Columns_header.get(colVal).getText());
		 			  cellVal++;
		 			  colVal++;
		 			  					   
		 			  }	
		 			// fourth row span content
			 			Columns_header = rows_table.get(28).findElements(By.tagName("th")); 
			 			
			 			updateError(28,cellVal,s.getRow(28).getCell(0).getStringCellValue()
								  ,Columns_header.get(0).getText());
			 			updateError(28,cellVal,s.getRow(28).getCell(1).getStringCellValue()
								  ,Columns_header.get(1).getText());
			  
			 			Columns_header = rows_table.get(28).findElements(By.tagName("td"));
			 			 cellVal=2;
			 			  colVal=0;
			 			 while(cellVal<8 &&colVal<6)
			 			  {
			 				updateError(28,cellVal,s.getRow(28).getCell(cellVal).getStringCellValue()
									  ,Columns_header.get(colVal).getText());
			 			  cellVal++;
			 			  colVal++;			 			  					   
			 			  }	
			 			// fetch table row contents
			 			 
				 			// Fetch the contents of table from row 3 to last 7th row
						  for (int row=3; row<6; row++)
						  {
							  List<WebElement> Columns_row = rows_table.get(row).
						 					 findElements(By.tagName("th"));
							  
							  updateError(row,cellVal,s.getRow(row).getCell(1).getStringCellValue()
									  ,Columns_row.get(0).getText()); 
							//To locate columns(cells) of that specific row.
			 				  Columns_row = rows_table.get(row).findElements(By.tagName("td"));  
			 				
			 				   cellVal=2;
			 					  colVal=0;
			 					  while(cellVal<8 &&colVal<6)
			 					  {
			 						 updateError(row,cellVal,s.getRow(row).getCell(cellVal).getStringCellValue()
											  ,Columns_header.get(colVal).getText());
			 					  cellVal++;
			 					  colVal++;
			 					   
			 					  }
			 					//fetch contents of second block 
						 	 for ( row=7; row<17; row++)
							 {
							  Columns_row = rows_table.get(row).findElements(By.tagName("th"));
						 	  updateError(row,cellVal,s.getRow(row).getCell(1).getStringCellValue()
											  ,Columns_row.get(0).getText()); 
						 	 Columns_row = rows_table.get(row).findElements(By.tagName("td"));  
				 				
			 				   cellVal=2;
			 					  colVal=0;
			 					  while(cellVal<8 &&colVal<6)
			 					  {
			 						 updateError(row,cellVal,s.getRow(row).getCell(cellVal).getStringCellValue()
											  ,Columns_header.get(colVal).getText());
			 					  cellVal++;
			 					  colVal++;
			 					   
			 					  }	  
						 			  
				 			 }//close for
						 			
						 	//fetch contents of 4th block 
				 			 for ( row=19; row<27; row++)
				 			 {
				 			  Columns_row = rows_table.get(row).findElements(By.tagName("th"));
				 			 updateError(row,cellVal,s.getRow(row).getCell(1).getStringCellValue()
									  ,Columns_row.get(0).getText()); 
				 			 Columns_row = rows_table.get(row).findElements(By.tagName("td"));  
		 				
				 			 cellVal=2;
		 					  colVal=0;
		 					  while(cellVal<8 &&colVal<6)
		 					  {
		 						 updateError(row,cellVal,s.getRow(row).getCell(cellVal).
		 								 getStringCellValue()
										  ,Columns_header.get(colVal).getText());
		 					  cellVal++;
		 					  colVal++;	 					   
		 					  }	  
				 			  
				 			 }//for
				 			 
				 			//fetch contents of 5th block 
				 			 for ( row=28; row<32; row++)
				 			 {
				 				 Columns_row = rows_table.get(row).findElements(By.tagName("th"));
					 			 updateError(row,cellVal,s.getRow(row).getCell(1).getStringCellValue()
										  ,Columns_row.get(0).getText()); 
					 			 Columns_row = rows_table.get(row).findElements(By.tagName("td"));  
			 				
					 			 cellVal=2;
			 					  colVal=0;
			 					  while(cellVal<8 &&colVal<6)
			 					  {
			 						 updateError(row,cellVal,s.getRow(row).getCell(cellVal).
			 								 getStringCellValue()
											  ,Columns_header.get(colVal).getText());
			 					  cellVal++;
			 					  colVal++;	 					   
			 					  }	  
					 			   
				 			 }//for
				 			 
						 }
		 }
		 FileOutputStream fos=new FileOutputStream(path);
			wb.write(fos);
			fos.close();
			
			GlobalVariables.result=TestBaseConstants.RESULT_PASSVALUE;
	   		 GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
	   	rATUStatus(GlobalVariables.result,msg);	
	}
   	catch(Exception e)
   	{
   		GlobalVariables.exceptionMsgVal=e.getMessage();
   		String ermsg="Error while executing mT1_TH2_TBH2_TCN_ReadXLSX keyword";
   		keywordsErrormsg(GlobalVariables.errormsg,GlobalVariables.exceptionMsgVal,ermsg);
   		GlobalVariables.result=TestBaseConstants.RESULT_FAILVALUE;
   		GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
   		GlobalVariables.APPICATION_LOGS.error(ermsg);
   		Logs.errorLog(ermsg);
   		rATUStatus(GlobalVariables.result,ermsg);
   	}
		 
		 
		 
   	 }
		
  /********************************************************************************************
	 *		Author 						:	DivyaRaju.R
	 *		LastModifiedDate			:	1st may 2015
	 *		Method name					:	mT1_TH2_TBH1_TCN_WriteXLSX
	 *		Description					:	This method is used for fetching data from 1220 application
	 *										 and writing that excel based on table having
	 *										2headers in header section
	 *										1 header in body section and table content
	 *
	*********************************************************************************************/
  
  
  
  public static void mT1_TH2_TBH1_TCN_WriteXLSX(String sheetName,String excelFileName,
			 String msg,String tableXpath,String subReportName) 
	 {
		 boolean xlFileCreated=false;
		 try
		 {//Pre_Build_Number
				// fetch the folder path to create work book
			String folderPath=cleanPath(GlobalVariables.CONFIG.getProperty("buildFolderPath"))+
						 "/"+"Build_number_"+GlobalVariables.CONFIG.getProperty("buildNumber")+
						 "/"+GlobalVariables.CONFIG.getProperty("buildType")+"/";
			
				
			File  preBuildFolderPath=new File( folderPath);
				 
				 //Create directory
		    boolean folderCreated=preBuildFolderPath.mkdirs();
			String filePath=preBuildFolderPath+"/"+GlobalVariables.testCaseIdentifier+".xlsx";
			File filePath1 =new File(filePath);
				 //System.out.println("File Path is -->"+filePath);
	        if(folderCreated||preBuildFolderPath.exists())
			 {
			  if(filePath1.exists())
				{
	    			filePath1.delete();
					xlFileCreated= ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);		 
				}
			 else 
				{
				 xlFileCreated= ExcelTestUtil.createXLS(filePath,
						 GlobalVariables.testCaseIdentifier);
				}
			  
			  if(xlFileCreated)
				 {
					 
				 if(subReportName.equalsIgnoreCase("Source of Job by Employer Type"))
				 {
					 //fetch table xpath
			 		 WebElement mytable = GlobalVariables.driver.findElement(By.xpath(tableXpath));
			 		  //To locate rows of table.
			 		 List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
			 		  
			 		  // open stream to open file
			 		 FileInputStream fis=new FileInputStream(filePath);
			 	     Workbook wb=WorkbookFactory.create(fis);
			 			//get row size
			 		 int rows_count = rows_table.size();	 			 
			 		 Sheet s=wb.getSheet(GlobalVariables.testCaseIdentifier);
			 			 
			 			
			 		
			 			 
			 			 /*** Fetch headers and store to excel of 1st col**/
			 			 // first row of header
			 			 List<WebElement> Columns_header = rows_table.get(0).findElements(By.tagName("th"));
			 			 //create first row  
			 			 Row r1=s.createRow(0);
			 			  String j=Columns_header.get(0).getText();
			 			 // add 1st cell value
			 			  r1.createCell(0).setCellValue(j);
			 			  // merge the text based on rowspan or colspan
			 				 s.addMergedRegion(new CellRangeAddress(0, 1, 0, 1));
			 			 
			 			 // add value to cell 2
			 			 r1.createCell(2).setCellValue(Columns_header.get(1).getText());
			 			 s.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));
			 			 
			 			// add value to cell 3
			 			 r1.createCell(4).setCellValue(Columns_header.get(2).getText());
			 			 s.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));
			 			 
			 			 //add value to cell 4
			 			r1.createCell(6).setCellValue(Columns_header.get(3).getText());
			 			 s.addMergedRegion(new CellRangeAddress(0, 0, 6, 7));
			 			//add value to cell 5
			 			r1.createCell(8).setCellValue(Columns_header.get(4).getText());
			 			 s.addMergedRegion(new CellRangeAddress(0, 0, 8, 9));
			 			//add value to cell 6
			 			r1.createCell(10).setCellValue(Columns_header.get(5).getText());
			 			 s.addMergedRegion(new CellRangeAddress(0, 0, 10, 11));
			 			//add value to cell 7
			 			r1.createCell(12).setCellValue(Columns_header.get(6).getText());
			 			 s.addMergedRegion(new CellRangeAddress(0, 0, 12, 13));
			 			//add value to cell 8
			 			r1.createCell(14).setCellValue(Columns_header.get(7).getText());
			 			 s.addMergedRegion(new CellRangeAddress(0, 0, 14, 15));
			 			//add value to cell 9
				 			r1.createCell(14).setCellValue(Columns_header.get(8).getText());
				 			 s.addMergedRegion(new CellRangeAddress(0, 0, 16, 17));
			 			 
			 			 // fetch 2nd row contents
				 			  r1=s.createRow(1);
			 			 Columns_header = rows_table.get(1).findElements(By.tagName("th")); 
			 			 int cellVal=1;
			 			 int colVal=0;
			 			 // enter values to cols 2 to 18 
			 			  while(cellVal<17 &&colVal<16)
			 			  {
			 			  r1.createCell(cellVal).
			 			  setCellValue(Columns_header.get(colVal).getText());
			 			  cellVal++;
			 			  colVal++;	 			   
			 			  }		
			 			  
			 			  
			 			// Fetch the contents of table from row 3 to last 7th row
				 			 for (int row=2; row<rows_count; row++)
				 			 {
				 			 List<WebElement> Columns_row = rows_table.get(row).
				 					 findElements(By.tagName("th"));
				 			
				 		
				 			 Row r=s.createRow(row);
				 			 String celtext = Columns_row.get(0).getText();
				 			
				 			 r.createCell(0).setCellValue(celtext);	 
				 			 
				 			
				 				   //To locate columns(cells) of that specific row.
				 				  Columns_row = rows_table.get(row).findElements(By.tagName("td"));
				 				   
				 				
				 				 
				 				   
				 				   cellVal=0;
				 					  colVal=0;
				 					  while(cellVal<16 &&colVal<16)
				 					  {
				 					  r.createCell(cellVal).setCellValue(Columns_row.get(colVal).getText());
				 					  cellVal++;
				 					  colVal++;
				 					   
				 					  }
				 			 }
		
				 			 FileOutputStream fos=new FileOutputStream(filePath);
					 			wb.write(fos);
					 			fos.close();	 
			 			 
				 }//Source of Job by Employer Type
				 
				 else if(subReportName.equalsIgnoreCase("Employer Types by Age at Graduation") ||
						 subReportName.equalsIgnoreCase("Employer Types by Race/Ethnicity")||
						 subReportName.equalsIgnoreCase("Private Practice Detail by Race/Ethnicity"))
				 {
					 //fetch table xpath
			 		 WebElement mytable = GlobalVariables.driver.findElement(By.xpath(tableXpath));
			 		  //To locate rows of table.
			 		 List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
			 		  
			 		  // open stream to open file
			 		 FileInputStream fis=new FileInputStream(filePath);
			 	     Workbook wb=WorkbookFactory.create(fis);
			 			//get row size
			 		 int rows_count = rows_table.size();	 			 
			 		 Sheet s=wb.getSheet(GlobalVariables.testCaseIdentifier);
			 			 
			 			
			 		
			 			 
			 			 /*** Fetch headers and store to excel of 1st col**/
			 			 // first row of header
			 			 List<WebElement> Columns_header = rows_table.get(0).findElements(By.tagName("th"));
			 			 //create first row  
			 			 Row r1=s.createRow(0);
			 			  String j=Columns_header.get(0).getText();
			 			 // add 1st cell value
			 			  r1.createCell(0).setCellValue(j);
			 			  // merge the text based on rowspan or colspan
			 				 s.addMergedRegion(new CellRangeAddress(0, 1, 0, 1));
			 			 
			 			 // add value to cell 2
			 			 r1.createCell(2).setCellValue(Columns_header.get(1).getText());
			 			 s.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));
			 			 
			 			// add value to cell 3
			 			 r1.createCell(4).setCellValue(Columns_header.get(2).getText());
			 			 s.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));
			 			 
			 			 //add value to cell 4
			 			r1.createCell(6).setCellValue(Columns_header.get(3).getText());
			 			 s.addMergedRegion(new CellRangeAddress(0, 0, 6, 7));
			 			//add value to cell 5
			 			r1.createCell(8).setCellValue(Columns_header.get(4).getText());
			 			 s.addMergedRegion(new CellRangeAddress(0, 0, 8, 9));
			 			
			 			//add value to cell 6
				 			r1.createCell(10).setCellValue(Columns_header.get(5).getText());
				 			 s.addMergedRegion(new CellRangeAddress(0, 0, 10, 11));
				 			
			 			 // fetch 2nd row contents 
				 			  r1=s.createRow(1);
			 			 Columns_header = rows_table.get(1).findElements(By.tagName("th")); 
			 			 int cellVal=1;
			 			 int colVal=0;
			 			 // enter values to cols 1 to 18 
			 			  while(cellVal<12 &&colVal<11)
			 			  {
			 			  r1.createCell(cellVal).setCellValue(Columns_header.get(colVal).getText());
			 			  cellVal++;
			 			  colVal++;	 			   
			 			  }		
			 			  
			 			  
			 			// Fetch the contents of table from row 3 to last 7th row
				 			 for (int row=2; row<rows_count; row++)
				 			 {
				 			 List<WebElement> Columns_row = rows_table.get(row).
				 					 findElements(By.tagName("th"));
				 			
				 		
				 			 Row r=s.createRow(row);
				 			 String celtext = Columns_row.get(0).getText();
				 			
				 			 r.createCell(0).setCellValue(celtext);	 
				 			 
				 			
				 				   //To locate columns(cells) of that specific row.
				 				  Columns_row = rows_table.get(row).findElements(By.tagName("td"));
				 				     
				 				   cellVal=0;
				 					  colVal=0;
				 					  while(cellVal<10 &&colVal<10)
				 					  {
				 					  r.createCell(cellVal).
				 					  setCellValue(Columns_row.get(colVal).getText());
				 					  cellVal++;
				 					  colVal++;
				 					   
				 					  }
				 			 }
				 			 FileOutputStream fos=new FileOutputStream(filePath);
					 			wb.write(fos);
					 			fos.close();	
		 
				 }//
				 else if(subReportName.equalsIgnoreCase
						 ("Full and Part-time Jobs by Employer Type") )
				 {
					//fetch table xpath
			 		 WebElement mytable = GlobalVariables.driver.findElement(By.xpath(tableXpath));
			 		  //To locate rows of table.
			 		 List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
			 		  
			 		  // open stream to open file
			 		 FileInputStream fis=new FileInputStream(filePath);
			 	     Workbook wb=WorkbookFactory.create(fis);
			 			//get row size
			 		 int rows_count = rows_table.size();	 			 
			 		 Sheet s=wb.getSheet(GlobalVariables.testCaseIdentifier);
			 			 
			 			
			 		
			 			 
			 			 /*** Fetch headers and store to excel of 1st col**/
			 			 // first row of header
			 			 List<WebElement> Columns_header = rows_table.get(0).findElements(By.tagName("th"));
			 			 //create first row  
			 			 Row r1=s.createRow(0);
			 			  String j=Columns_header.get(0).getText();
			 			 // add 1st cell value
			 			  r1.createCell(0).setCellValue(j);
			 			  // merge the text based on rowspan or colspan
			 				 s.addMergedRegion(new CellRangeAddress(0, 1, 0, 1));
			 			 
			 			 // add value to cell 2
			 			 r1.createCell(2).setCellValue(Columns_header.get(1).getText());
			 			 s.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));
			 			 
			 			// add value to cell 3
			 			 r1.createCell(4).setCellValue(Columns_header.get(2).getText());
			 			 s.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));
			 			 
			 			 // add value to cell 4
			 			 r1.createCell(6).setCellValue(Columns_header.get(3).getText());
			 			 s.addMergedRegion(new CellRangeAddress(0, 0, 6, 7));
			 			 
			 			
			 					 			
			 			 // fetch 2nd row contents 
			 			  r1=s.createRow(1);
			 			 Columns_header = rows_table.get(1).findElements(By.tagName("th")); 
			 			 int cellVal=1;
			 			 int colVal=0;
			 			 // enter values to cols 1 to 18 
			 			  while(cellVal<7 &&colVal<6)
			 			  {
			 			  r1.createCell(cellVal).setCellValue
			 			  (Columns_header.get(colVal).getText());
			 			  cellVal++;
			 			  colVal++;	 			   
			 			  }		
			 			  
			 			  
			 			// Fetch the contents of table from row 3 to last 7th row
				 			 for (int row=2; row<rows_count; row++)
				 			 {
				 			 List<WebElement> Columns_row = rows_table.get(row).
				 					 findElements(By.tagName("th"));
				 			
				 		
				 			 Row r=s.createRow(row);
				 			 String celtext = Columns_row.get(0).getText();
				 			
				 			 r.createCell(0).setCellValue(celtext);	 
				 			 
				 			
				 				   //To locate columns(cells) of that specific row.
				 				  Columns_row = rows_table.get(row).findElements(By.tagName("td"));
				 				     
				 				   cellVal=0;
				 					  colVal=0;
				 					  while(cellVal<6 &&colVal<6)
				 					  {
				 					  r.createCell(cellVal).
				 					  setCellValue(Columns_row.get(colVal).getText());
				 					  cellVal++;
				 					  colVal++;
				 					   
				 					  }
				 			 }
				 			 FileOutputStream fos=new FileOutputStream(filePath);
					 			wb.write(fos);
					 			fos.close();	
		  
				 }
				 else if(subReportName.equalsIgnoreCase("Employer Types by Gender") ||
						 subReportName.equalsIgnoreCase("Private Practice Detail by Gender"))
				 {
					//fetch table xpath
			 		 WebElement mytable = GlobalVariables.driver.findElement(By.xpath(tableXpath));
			 		  //To locate rows of table.
			 		 List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
			 		  
			 		  // open stream to open file
			 		 FileInputStream fis=new FileInputStream(filePath);
			 	     Workbook wb=WorkbookFactory.create(fis);
			 			//get row size
			 		 int rows_count = rows_table.size();
			 		 
			 		 System.out.println("row count is "+rows_count);
			 		 Sheet s=wb.getSheet(GlobalVariables.testCaseIdentifier);	 
			 			 /*** Fetch headers and store to excel of 1st col**/
			 			 // first row of header
			 			 List<WebElement> Columns_header = rows_table.get(0).findElements(By.tagName("th"));
			 			//System.out.println(Columns_header.size());
			 			 //create first row  
			 			 Row r1=s.createRow(0);
			 			  String j=Columns_header.get(0).getText();
			 			 // add 1st cell value
			 			  r1.createCell(0).setCellValue(j);
			 			  // merge the text based on rowspan or colspan
			 				 s.addMergedRegion(new CellRangeAddress(0, 1, 0, 1));
			 			 
			 			 // add value to cell 2
			 			 r1.createCell(2).setCellValue(Columns_header.get(1).getText());
			 			 s.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));
			 			 
			 			// add value to cell 3
			 			 r1.createCell(4).setCellValue(Columns_header.get(2).getText());
			 			 s.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));
			 					 			
			 			 // fetch 2nd row contents
			 			  r1=s.createRow(1);
			 			 Columns_header = rows_table.get(1).findElements(By.tagName("th")); 
			 			 int cellVal=1;
			 			 int colVal=0;
			 			 // enter values to cols 1 to 18 
			 			  while(cellVal<5 &&colVal<4)
			 			  {
			 			  r1.createCell(cellVal).setCellValue
			 			  (Columns_header.get(colVal).getText());
			 			  cellVal++;
			 			  colVal++;	 			   
			 			  }		
			 			 
			 			  
			 			// Fetch the contents of table from row 3 to last 7th row
				 			 for (int row=2; row<rows_count; row++)
				 			 {
				 			 List<WebElement> Columns_row = rows_table.get(row).
				 					 findElements(By.tagName("th"));
				 			//System.out.println(Columns_row.size());
				 		
				 			 Row r=s.createRow(row);
				 			 String celtext = Columns_row.get(0).getText();
				 			
				 			 r.createCell(0).setCellValue(celtext);	 
				 			 
				 			
				 				   //To locate columns(cells) of that specific row.
				 				  Columns_row = rows_table.get(row).findElements(By.tagName("td"));
				 				    // System.out.println(Columns_row.size());
				 				   cellVal=0;
				 					  colVal=0;
				 					  while(cellVal<4 &&colVal<4)
				 					  {
				 					  r.createCell(cellVal).
				 					  setCellValue(Columns_row.get(colVal).getText());
				 					  cellVal++;
				 					  colVal++;
				 					   
				 					  }
				 			 } 
			 			  
			 			 FileOutputStream fos=new FileOutputStream(filePath);
				 			wb.write(fos);
				 			fos.close();	
				 }
				 
				 
				 }
			  else
				 {
						// System.out.println("File not created");
						 GlobalVariables.APPICATION_LOGS.error("File not created");
						 Logs.errorLog("File not created");
				 }
			 }
	        else
			 {
				 //System.out.println("Folder not created"); 
				 GlobalVariables.APPICATION_LOGS.error("Folder not created");
				 Logs.errorLog("Folder not created");

			 }
			 
	        GlobalVariables.result=TestBaseConstants.RESULT_PASSVALUE;
			 GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
			 rATUStatus(GlobalVariables.result,msg);



}
catch(Exception e)
{
	GlobalVariables.exceptionMsgVal=e.getMessage();
	String ermsg="Error while executing mT1_TH2_TBH1_TCN_WriteXLSX keyword";
	keywordsErrormsg(GlobalVariables.errormsg,GlobalVariables.exceptionMsgVal,ermsg);
	GlobalVariables.result=TestBaseConstants.RESULT_FAILVALUE;
	GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
	GlobalVariables.APPICATION_LOGS.error(ermsg);
	Logs.errorLog(ermsg);
	rATUStatus(GlobalVariables.result,msg);
} 
	        
}
  
  /********************************************************************************************
   *		Author 						:	DivyaRaju.R
   *		LastModifiedDate			:	1st may 2015
   *		Method name					:	mT1_TH2_TBH1_TCN_ReadXLSX
   *		Description					:	This method is used for comparing 1220 app
   *										 data with stored 
   *										value of excel
   *
  *********************************************************************************************/ 
  
  
  public static void mT1_TH2_TBH1_TCN_ReadXLSX(String excelSheetName,String automationId,
			String xpath,String subReportName,String msg) 
 {
	  GlobalVariables.testCaseIdentifier=automationId;
		try
	    {
			 String path=cleanPath(GlobalVariables.CONFIG.getProperty("buildFolderPath"))+
					 "/"+"Build_number_"+GlobalVariables.CONFIG.getProperty("buildNumber")+
					 "/"+TestBaseConstants.BASELINE_BUILD_TYPE+"/"+
					 GlobalVariables.testCaseIdentifier+".xlsx";
			 //System.out.println("Path of file is -->"+path);
			 FileInputStream fis=new FileInputStream(path);
				Workbook wb=WorkbookFactory.create(fis); 
				 
			 //wb.createSheet(year);
			 Sheet s=wb.getSheet(excelSheetName); 
			 if(subReportName.equalsIgnoreCase("Source of Job by Employer Type"))
					
			 {
				 WebElement mytable = GlobalVariables.driver.findElement(By.xpath(xpath));
				  //To locate rows of table.
				  List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
				  //To calculate no of rows In table.
				  int rows_count = rows_table.size();
				  
				  ///headers
				  List<WebElement> Columns_header = rows_table.get(0).
						  findElements(By.tagName("th"));
				  // Validating first row headers and cell contents
				  
				  //compare 0th cell of header
				  updateError(0,0,s.getRow(0).getCell(0).getStringCellValue()
						  ,Columns_header.get(0).getText()); 
				  //compare 1st cell of header
				  updateError(0,1,s.getRow(0).getCell(2).getStringCellValue()
						  ,Columns_header.get(1).getText());
				  
				  //compare 2nd cell of header
				  updateError(0,2,s.getRow(0).getCell(4).getStringCellValue()
						  ,Columns_header.get(2).getText());
				  //compare 3rd cell of header
				  updateError(0,3,s.getRow(0).getCell(6).getStringCellValue()
						  ,Columns_header.get(3).getText());
				  //compare 4th cell of header
				  updateError(0,4,s.getRow(0).getCell(8).getStringCellValue()
						  ,Columns_header.get(4).getText());
				  //compare 5th cell of header
				  updateError(0,5,s.getRow(0).getCell(10).getStringCellValue()
						  ,Columns_header.get(5).getText());
				  //compare 6th cell of header
				  updateError(0,6,s.getRow(0).getCell(12).getStringCellValue()
						  ,Columns_header.get(6).getText());
				  //compare 7th cell of header
				  updateError(0,7,s.getRow(0).getCell(14).getStringCellValue()
						  ,Columns_header.get(7).getText());
				  //compare 8th cell of header
				  updateError(0,8,s.getRow(0).getCell(16).getStringCellValue()
						  ,Columns_header.get(8).getText());
				  
				// fetch 2nd row contents 
		 			 Columns_header = rows_table.get(1).findElements(By.tagName("th")); 
		 			 int cellVal=1;
		 			 int colVal=0;
		 			 // enter values to cols 2 to 18 
		 			  while(cellVal<17 &&colVal<16)
		 			  {		 		
		 				  updateError(1,cellVal,s.getRow(1).getCell(cellVal).getStringCellValue()
							  ,Columns_header.get(colVal).getText());
		 			  cellVal++;
		 			  colVal++;	 			   
		 			  }	
		 			  
		 			  
		 			// Fetch the contents of table from 2nd row till last row
		 			  
		 			 for (int row=2; row<rows_count; row++)
		 			 {
		 			 List<WebElement> Columns_row = rows_table.get(row).
		 					 findElements(By.tagName("th"));
		 			updateError(row,0,s.getRow(row).getCell(0).getStringCellValue()
							  , Columns_row.get(0).getText());		 			
		 			
		 			 //To locate columns(cells) of that specific row.
	 				  Columns_row = rows_table.get(row).findElements(By.tagName("td"));   
	 				   cellVal=0;
	 					  colVal=0;
	 					  while(cellVal<16 &&colVal<17)
	 					  {
	 					
	 						  updateError(row,cellVal,s.getRow(row).getCell(cellVal).getStringCellValue()
								  ,Columns_row.get(colVal).getText());	
	 					  
	 					  cellVal++;
	 					  colVal++;	 					   
	 					  }
		 			 
		 			}//end if				  
				  
			 }//Source of Job by Employer Type 
			 
			 else if(subReportName.equalsIgnoreCase("Employer Types by Age at Graduation") ||
					 subReportName.equalsIgnoreCase("Employer Types by Race/Ethnicity")||
					 subReportName.equalsIgnoreCase("Private Practice Detail by Race/Ethnicity"))
			 {
				 WebElement mytable = GlobalVariables.driver.findElement(By.xpath(xpath));
				  //To locate rows of table.
				  List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
				  //To calculate no of rows In table.
				  int rows_count = rows_table.size();
				  
				  ///headers
				  List<WebElement> Columns_header = rows_table.get(0).
						  findElements(By.tagName("th"));
				  // Validating first row headers and cell contents
				  
				  //compare 0th cell of header
				  updateError(0,0,s.getRow(0).getCell(0).getStringCellValue()
						  ,Columns_header.get(0).getText()); 
				  //compare 1st cell of header
				  updateError(0,1,s.getRow(0).getCell(2).getStringCellValue()
						  ,Columns_header.get(1).getText());
				  
				  //compare 2nd cell of header
				  updateError(0,2,s.getRow(0).getCell(4).getStringCellValue()
						  ,Columns_header.get(2).getText());
				  //compare 3rd cell of header
				  updateError(0,3,s.getRow(0).getCell(6).getStringCellValue()
						  ,Columns_header.get(3).getText());
				  //compare 4th cell of header
				  updateError(0,4,s.getRow(0).getCell(8).getStringCellValue()
						  ,Columns_header.get(4).getText());
				  //compare 5th cell of header
				  updateError(0,5,s.getRow(0).getCell(10).getStringCellValue()
						  ,Columns_header.get(5).getText());
				  				  
				  // fetch 2nd row contents 
		 			 Columns_header = rows_table.get(1).findElements(By.tagName("th")); 
		 			 int cellVal=1;
		 			 int colVal=0;
		 			 // enter values to cols 2 to 18 
		 			  while(cellVal<12 &&colVal<11)
		 			  {		 		
		 				  updateError(1,0,s.getRow(1).getCell(cellVal).getStringCellValue()
							  ,Columns_header.get(colVal).getText());
		 			  cellVal++;
		 			  colVal++;	 			   
		 			  }	
		 			  
		 			  
		 			// Fetch the contents of table from 2nd row till last row
		 			  
		 			 for (int row=2; row<rows_count; row++)
		 			 {
		 			 List<WebElement> Columns_row = rows_table.get(row).
		 					 findElements(By.tagName("th"));
		 			updateError(row,0,s.getRow(row).getCell(0).getStringCellValue()
							  , Columns_row.get(0).getText());		 			
		 			
		 			 //To locate columns(cells) of that specific row.
	 				  Columns_row = rows_table.get(row).findElements(By.tagName("td"));   
	 				   cellVal=0;
	 					  colVal=0;
	 					  while(cellVal<11 &&colVal<11)
	 					  {
	 					
	 						  updateError(row,cellVal,s.getRow(row).getCell(cellVal).getStringCellValue()
								  ,Columns_row.get(colVal).getText());	
	 					  
	 					  cellVal++;
	 					  colVal++;	 					   
	 					  }
		 			 
		 			}//end for		 
			 }//end if 
			 
			 else if(subReportName.equalsIgnoreCase
					 ("Full and Part-time Jobs by Employer Type") )
			 {
				 WebElement mytable = GlobalVariables.driver.findElement(By.xpath(xpath));
				  //To locate rows of table.
				  List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
				  //To calculate no of rows In table.
				  int rows_count = rows_table.size();
				  
				  ///headers
				  List<WebElement> Columns_header = rows_table.get(0).
						  findElements(By.tagName("th"));
				  // Validating first row headers and cell contents
				  
				  //compare 0th cell of header
				  updateError(0,0,s.getRow(0).getCell(0).getStringCellValue()
						  ,Columns_header.get(0).getText()); 
				  //compare 1st cell of header
				  updateError(0,1,s.getRow(0).getCell(2).getStringCellValue()
						  ,Columns_header.get(1).getText());
				  
				  //compare 2nd cell of header
				  updateError(0,2,s.getRow(0).getCell(4).getStringCellValue()
						  ,Columns_header.get(2).getText());
				  //compare 3rd cell of header
				  updateError(0,3,s.getRow(0).getCell(6).getStringCellValue()
						  ,Columns_header.get(3).getText());
				 
				  				  
				  // fetch 2nd row contents 
		 			 Columns_header = rows_table.get(1).findElements(By.tagName("th")); 
		 			 int cellVal=1;
		 			 int colVal=0;
		 			 // enter values to cols 2 to 18 
		 			  while(cellVal<7 &&colVal<6)
		 			  {		 		
		 				  updateError(1,cellVal,s.getRow(1).getCell(cellVal).getStringCellValue()
							  ,Columns_header.get(colVal).getText());
		 			  cellVal++;
		 			  colVal++;	 			   
		 			  }	
		 			  
		 			  
		 			// Fetch the contents of table from 2nd row till last row
		 			  
		 			 for (int row=2; row<rows_count; row++)
		 			 {
		 			 List<WebElement> Columns_row = rows_table.get(row).
		 					 findElements(By.tagName("th"));
		 			updateError(row,0,s.getRow(row).getCell(0).getStringCellValue()
							  , Columns_row.get(0).getText());		 			
		 			
		 			 //To locate columns(cells) of that specific row.
	 				  Columns_row = rows_table.get(row).findElements(By.tagName("td"));   
	 				   cellVal=0;
	 					  colVal=0;
	 					  while(cellVal<7 &&colVal<7)
	 					  {
	 					
	 						  updateError(row,cellVal,s.getRow(row).getCell(cellVal).getStringCellValue()
								  ,Columns_row.get(colVal).getText());	
	 					  
	 					  cellVal++;
	 					  colVal++;	 					   
	 					  }
		 			 
		 			}//end for	 
			 }// end if
			 
			 else if(subReportName.equalsIgnoreCase("Employer Types by Gender") ||
					 subReportName.equalsIgnoreCase("Private Practice Detail by Gender"))
			 {
				 WebElement mytable = GlobalVariables.driver.findElement(By.xpath(xpath));
				  //To locate rows of table.
				  List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
				  //To calculate no of rows In table.
				  int rows_count = rows_table.size();
				  
				  ///headers
				  List<WebElement> Columns_header = rows_table.get(0).
						  findElements(By.tagName("th"));
				  // Validating first row headers and cell contents
				  
				  //compare 0th cell of header
				  updateError(0,0,s.getRow(0).getCell(0).getStringCellValue()
						  ,Columns_header.get(0).getText()); 
				  //compare 1st cell of header
				  updateError(0,1,s.getRow(0).getCell(2).getStringCellValue()
						  ,Columns_header.get(1).getText());
				  
				  //compare 2nd cell of header
				  updateError(0,2,s.getRow(0).getCell(4).getStringCellValue()
						  ,Columns_header.get(2).getText());
				
				 
				  				  
				  // fetch 2nd row contents 
		 			 Columns_header = rows_table.get(1).findElements(By.tagName("th")); 
		 			 int cellVal=1;
		 			 int colVal=0;
		 			 // enter values to cols 2 to 18 
		 			  while(cellVal<5 &&colVal<4)
		 			  {		 		
		 				  updateError(1,cellVal,s.getRow(1).getCell(cellVal).getStringCellValue()
							  ,Columns_header.get(colVal).getText());
		 			  cellVal++;
		 			  colVal++;	 			   
		 			  }	
		 			  
		 			  
		 			// Fetch the contents of table from 2nd row till last row
		 			  
		 			 for (int row=2; row<rows_count; row++)
		 			 {
		 			 List<WebElement> Columns_row = rows_table.get(row).
		 					 findElements(By.tagName("th"));
		 			updateError(row,0,s.getRow(row).getCell(0).getStringCellValue()
							  , Columns_row.get(0).getText());		 			
		 			
		 			 //To locate columns(cells) of that specific row.
	 				  Columns_row = rows_table.get(row).findElements(By.tagName("td"));   
	 				   cellVal=0;
	 					  colVal=0;
	 					  while(cellVal<4 &&colVal<4)
	 					  {
	 					
	 						  updateError(row,cellVal,s.getRow(row).getCell(cellVal).getStringCellValue()
								  ,Columns_row.get(colVal).getText());	
	 					  
	 					  cellVal++;
	 					  colVal++;	 					   
	 					  }
		 			 
		 			}//end for	  
			 }//end if
			 
			 
			 
			 
			 FileOutputStream fos=new FileOutputStream(path);
				wb.write(fos);
				fos.close();
				
				GlobalVariables.result=TestBaseConstants.RESULT_PASSVALUE;
		   		 GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
		   	rATUStatus(GlobalVariables.result,msg);	
		}
	   	catch(Exception e)
	   	{
	   		GlobalVariables.exceptionMsgVal=e.getMessage();
	   		String ermsg="Error while executing mT1_TH2_TBH1_TCN_ReadXLSX keyword";
	   		keywordsErrormsg(GlobalVariables.errormsg,GlobalVariables.exceptionMsgVal,ermsg);
	   		GlobalVariables.result=TestBaseConstants.RESULT_FAILVALUE;
	   		GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
	   		GlobalVariables.APPICATION_LOGS.error(ermsg);
	   		Logs.errorLog(ermsg);
	   		rATUStatus(GlobalVariables.result,ermsg);
	   	}
 }

  
  /********************************************************************************************
	 *		Author 						:	DivyaRaju.R
	 *		LastModifiedDate			:	1st may 2015
	 *		Method name					:	mT1_TH1_TCN_WriteXLSX
	 *		Description					:	This method is used for fetching data from 1220 application
	 *										 and writing that excel
	 *
	*********************************************************************************************/
	public static void mT1_TH1_TCN_WriteXLSX(String sheetName,String excelFileName,String msg,
		String tableXpath,String subReportName) 
	{
	boolean xlFileCreated=false;

		try
		{//Pre_Build_Number
			// fetch the folder path to create work book
			String folderPath=cleanPath(GlobalVariables.CONFIG.getProperty("buildFolderPath"))+
					 "/"+"Build_number_"+GlobalVariables.CONFIG.getProperty("buildNumber")+
					 "/"+GlobalVariables.CONFIG.getProperty("buildType")+"/";
			//System.out.println("Build folder path is "+folderPath);
			
			 File  preBuildFolderPath=new File( folderPath);
			 
			 //Create directory
			 boolean folderCreated=preBuildFolderPath.mkdirs();
			 String filePath=preBuildFolderPath+"/"+GlobalVariables.testCaseIdentifier+".xlsx";
			 File filePath1 =new File(filePath);
			 //System.out.println("File Path is -->"+filePath);
			 if(folderCreated||preBuildFolderPath.exists())
			 {
				 if(filePath1.exists())
					{
						filePath1.delete();
						xlFileCreated= ExcelTestUtil.createXLS(filePath,GlobalVariables.testCaseIdentifier);		 
					}
					else 
					{
						xlFileCreated= ExcelTestUtil.createXLS(filePath,GlobalVariables.testCaseIdentifier);
					}
				 if(xlFileCreated)
				 {
					 FileInputStream fis=new FileInputStream(filePath);
						Workbook wb=WorkbookFactory.create(fis);
						 //wb.createSheet(year);
						 Sheet s=wb.getSheet(sheetName);
				 
						//To locate table.
						  WebElement mytable = GlobalVariables.driver.findElement(By.xpath(tableXpath));
						  //To locate rows of table.
						  List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
						  //To calculate no of rows In table.
						  int rows_count = rows_table.size();
						  
						  //headers
						  List<WebElement> Columns_header = rows_table.get(0).findElements(By.tagName("th"));
						  Row r1=s.createRow(0);
						  if(subReportName.equalsIgnoreCase("Compensation by Professional Functions") ||
							 subReportName.equalsIgnoreCase("Compensation by Industries") ||
							 subReportName.equalsIgnoreCase("World Regions Breakdown") ||
						     subReportName.equalsIgnoreCase("Compensation by North American Geographic Regions") ||
						     subReportName.equalsIgnoreCase("Compensation by Undergraduate Major") ||
						     subReportName.equalsIgnoreCase("Compensation by Professional Experience") )
							  {
							  Logs.infoLog( "Sub report is "+subReportName);
							  //1st cell of 1st header
							  	r1.createCell(0).setCellValue(Columns_header.get(0).getText());
							  // 2nd cell of header
								 r1.createCell(1).setCellValue(Columns_header.get(1).getText());
								 s.addMergedRegion(new CellRangeAddress(0, 0, 1, 2));
								  //3rd cell
								 r1.createCell(3).setCellValue(Columns_header.get(2).getText());								 
								 //4th cell
								 r1.createCell(4).setCellValue(Columns_header.get(3).getText());
								 //5th cell
								 r1.createCell(5).setCellValue(Columns_header.get(4).getText());
								 //6th cell
								 r1.createCell(6).setCellValue(Columns_header.get(5).getText());
								 
							  }
						  
						  else if(subReportName.equalsIgnoreCase("Primary Source of Full-Time Job Acceptances") )
						  {
							  Logs.infoLog( "Sub report is "+subReportName);
							  //1st cell of 1st header
							  	/*r1.createCell(0).setCellValue(Columns_header.get(0).getText()); */
							 // 2nd cell of header
							  	r1.createCell(1).setCellValue(Columns_header.get(1).getText());
								 s.addMergedRegion(new CellRangeAddress(0, 0, 1, 2));
								 //3rd cell 
								 r1.createCell(3).setCellValue(Columns_header.get(2).getText());
								 s.addMergedRegion(new CellRangeAddress(0, 0, 3, 4));
							  	
						  }
						  
						  else if(subReportName.equalsIgnoreCase("Location of Instate Jobs"))
						  {
							  Logs.infoLog( "Sub report is "+subReportName);
							//1st cell of 1st header
							  	r1.createCell(0).setCellValue(Columns_header.get(0).getText());
							// 2nd cell of header
							    r1.createCell(1).setCellValue(Columns_header.get(1).getText());
							 // 3rd cell   
							    r1.createCell(2).setCellValue(Columns_header.get(2).getText());
						  }
						  
						  else if(subReportName.equalsIgnoreCase("Number of Jobs Reported Taken by State") ||
								  subReportName.equalsIgnoreCase("The Graduating Class (B)")||
								  subReportName.equalsIgnoreCase("Summary")||
								  subReportName.equalsIgnoreCase("Full-Time Employment")||
								  subReportName.equalsIgnoreCase("Part-Time Employment")||
								  subReportName.equalsIgnoreCase("Service Organization")||
								  subReportName.equalsIgnoreCase("Military Service")||
								  subReportName.equalsIgnoreCase("Continuing Education")||
								  subReportName.equalsIgnoreCase("Seeking or Unreported")||
								  subReportName.equalsIgnoreCase("Employment Status")||
								  subReportName.equalsIgnoreCase("Law School/University Funded Positions")||
								  subReportName.equalsIgnoreCase("Employment Type")||
								  subReportName.equalsIgnoreCase("Employment Location")
								  )
						  {
							  Logs.infoLog( "Sub report is "+subReportName);
							  for(int col=0;col<Columns_header.size();col++)
							  {
								  Logs.infoLog("Cell Value Of row number "+r1+" and column number "+col+" is "+Columns_header.get(col).getText());
								  
								  r1.createCell(col).setCellValue(Columns_header.get(col).getText());
							  }
						  }
						  for (int row=1; row<rows_count; row++)
						  {
							   //To locate columns(cells) of that specific row.
							   List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
							   
							   //To calculate no of columns(cells) In that specific row.
							   int columns_count = Columns_row.size();
							   
							   Row r=s.createRow(row);
							   
							    		//Loop will execute till the last cell of that specific row.
							   			for (int column=0; column<columns_count; column++)
							   			{
							   			//To retrieve text from that specific cell.
							   				String celtext = Columns_row.get(column).getText();			   			
							   				r.createCell(column).setCellValue(celtext);	
							   				Logs.infoLog("Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);
							   				if(!celtext.isEmpty())
					 		   				{
					 		   					System.out.println("Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);
					 		   				}

							   			}							   			
							  } //for loop 
							 FileOutputStream fos=new FileOutputStream(filePath);
					 			wb.write(fos);
					 			fos.close();	   			
				 }	 
				 else
				 {
					 //System.out.println("File not created");
					 GlobalVariables.APPICATION_LOGS.error("File not created");
					 Logs.errorLog("File not created");
				 }
			
			 }
			 else
			 {
				// System.out.println("Folder not created"); 
				 GlobalVariables.APPICATION_LOGS.error("Folder not created");
				 Logs.errorLog("Folder not created");
			 }

	 GlobalVariables.result=TestBaseConstants.RESULT_PASSVALUE;
	 GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
	 rATUStatus(GlobalVariables.result,msg);



	}
	catch(Exception e)
	{
		GlobalVariables.exceptionMsgVal=e.getMessage();
		String ermsg="Error while executing mT1_TH1_TCN_WriteXLSX keyword";
		keywordsErrormsg(GlobalVariables.errormsg,GlobalVariables.exceptionMsgVal,ermsg);
		GlobalVariables.result=TestBaseConstants.RESULT_FAILVALUE;
		GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
		GlobalVariables.APPICATION_LOGS.error(ermsg);
		Logs.errorLog(ermsg);
		rATUStatus(GlobalVariables.result,msg);
	}
		
	
  }	

	
	
	
	
	
	
	
	 /********************************************************************************************
	 *		Author 						:	DivyaRaju.R
	 *		LastModifiedDate			:	1st may 2015
	 *		Method name					:	mT1_TH1_TCN_ReadXLSX
	 *		Description					:	This method is used for fetching data from 1220
	 *										 application
	 *										 and writing that excel
	 *
	*********************************************************************************************/
 
	public static void mT1_TH1_TCN_ReadXLSX(String excelSheetName,String automationId,
				String xpath,String subReportName,String msg) 
	{
		GlobalVariables.testCaseIdentifier=automationId;
		try
	    {
			 String path=cleanPath(GlobalVariables.CONFIG.getProperty("buildFolderPath"))+
					 "/"+"Build_number_"+GlobalVariables.CONFIG.getProperty("buildNumber")+
					 "/"+TestBaseConstants.BASELINE_BUILD_TYPE+"/"+
					 GlobalVariables.testCaseIdentifier+".xlsx";
			 //System.out.println("Path of file is -->"+path);
			 FileInputStream fis=new FileInputStream(path);
				Workbook wb=WorkbookFactory.create(fis); 
				 
			 //wb.createSheet(year);
			 Sheet s=wb.getSheet(excelSheetName); 
			 
			//To locate table.
			  WebElement mytable = GlobalVariables.driver.findElement(By.xpath(xpath));
			  //To locate rows of table.
			  List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
			  //To calculate no of rows In table.
			  int rows_count = rows_table.size();
			  
			  ///headers
			  List<WebElement> Columns_header = rows_table.get(0).findElements(By.tagName("th"));
			 
			  if(subReportName.equalsIgnoreCase("Compensation by Professional Functions") ||
						 subReportName.equalsIgnoreCase("Compensation by Industries") ||
						 subReportName.equalsIgnoreCase("World Regions Breakdown") ||
					     subReportName.equalsIgnoreCase("Compensation by North American Geographic Regions") ||
					     subReportName.equalsIgnoreCase("Compensation by Undergraduate Major") ||
					     subReportName.equalsIgnoreCase("Compensation by Professional Experience"))
					
				{	// compare 1st cell				
					updateError(0,0,s.getRow(0).getCell(0).getStringCellValue()
							,Columns_header.get(0).getText());
					//compare 2nd cell
					updateError(0,1,s.getRow(0).getCell(1).getStringCellValue()
							,Columns_header.get(1).getText());					
					//compare 3rd cell
					updateError(0,3,s.getRow(0).getCell(3).getStringCellValue()
							,Columns_header.get(2).getText());
					//compare 4th cell
					updateError(0,4,s.getRow(0).getCell(4).getStringCellValue()
							,Columns_header.get(3).getText());
					//compare 5th cell
					updateError(0,5,s.getRow(0).getCell(5).getStringCellValue()
							,Columns_header.get(4).getText());
					//compare 6th cell
					updateError(0,6,s.getRow(0).getCell(6).getStringCellValue()
							,Columns_header.get(5).getText());	
				}	
			  else if(subReportName.equalsIgnoreCase("Primary Source of Full-Time Job Acceptances") )
			  {
				// compare 1st cell				
					updateError(0,0,s.getRow(0).getCell(1).getStringCellValue()
							,Columns_header.get(1).getText());
					//compare 2nd cell
					updateError(0,1,s.getRow(0).getCell(3).getStringCellValue()
							,Columns_header.get(2).getText());	 
			  }
			  else if(subReportName.equalsIgnoreCase("Location of Instate Jobs"))
			  {
				// compare 1st cell				
					updateError(0,0,s.getRow(0).getCell(0).getStringCellValue()
							,Columns_header.get(0).getText());
					//compare 2nd cell
					updateError(0,1,s.getRow(0).getCell(1).getStringCellValue()
							,Columns_header.get(1).getText());					
					//compare 3rd cell
					updateError(0,2,s.getRow(0).getCell(2).getStringCellValue()
							,Columns_header.get(2).getText());
			  }
			  
			  else if(subReportName.equalsIgnoreCase("Number of Jobs Reported Taken by State") ||
					  subReportName.equalsIgnoreCase("The Graduating Class (B)")||
					  subReportName.equalsIgnoreCase("Summary")||
					  subReportName.equalsIgnoreCase("Full-Time Employment")||
					  subReportName.equalsIgnoreCase("Part-Time Employment")||
					  subReportName.equalsIgnoreCase("Service Organization")||
					  subReportName.equalsIgnoreCase("Military Service")||
					  subReportName.equalsIgnoreCase("Continuing Education")||
					  subReportName.equalsIgnoreCase("Seeking or Unreported")||
					  subReportName.equalsIgnoreCase("Employment Status")||
					  subReportName.equalsIgnoreCase("Law School/University Funded Positions")||
					  subReportName.equalsIgnoreCase("Employment Type")||
					  subReportName.equalsIgnoreCase("Employment Location")
					  )
			  {
				  for(int col=0;col<Columns_header.size();col++)
				  {
					  updateError(0,col,s.getRow(0).getCell(col).getStringCellValue()
								,Columns_header.get(col).getText());
				  }
			  }
			 
			//Loop will execute till the last row of table.
			  for (int row=1; row<rows_count; row++)
			  {
			   //To locate columns(cells) of that specific row.
			   List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
			   Row r=s.getRow(row);
			   //To calculate no of columns(cells) In that specific row.
			   int columns_count = Columns_row.size();		   
			   
			    		//Loop will execute till the last cell of that specific row.
			   			for (int column=0; column<columns_count; column++)
			   			{
			   			//To retrieve text from that specific cell.
			   				String webtext = Columns_row.get(column).getText();
			   				String xltext = r.getCell(column).getStringCellValue();
			   				updateError(row,column,xltext,webtext);
			   			}   				
			   		
			  }  			 
			 
			 FileOutputStream fos=new FileOutputStream(path);
				wb.write(fos);
				fos.close();
				GlobalVariables.result=TestBaseConstants.RESULT_PASSVALUE;
		  		 GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
		  		 rATUStatus(GlobalVariables.result,msg);		
			 }
			catch(Exception e)
			{
				GlobalVariables.exceptionMsgVal=e.getMessage();
				String ermsg="Error while executing mT1_TH1_TCN_ReadXLSX keyword";
				keywordsErrormsg(GlobalVariables.errormsg,GlobalVariables.exceptionMsgVal,ermsg);
				GlobalVariables.result=TestBaseConstants.RESULT_FAILVALUE;
				GlobalVariables.testusappend=ExcelTestUtil.runStatusAdd(GlobalVariables.result);
				GlobalVariables.APPICATION_LOGS.error(ermsg);
				Logs.errorLog(ermsg);
				rATUStatus(GlobalVariables.result,ermsg);
			}
		
	}
  

	
	
	
	
}
