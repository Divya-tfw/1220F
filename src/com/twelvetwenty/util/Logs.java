package com.twelvetwenty.util;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;

import org.apache.commons.io.FileUtils;



import com.twelvetwenty.base.Base;
import com.twelvetwenty.constants.GlobalVariables;
import com.twelvetwenty.constants.TestBaseConstants;


public class Logs extends Base 
{
	
	
	/*********************************************************************************
	 * 	Author						:	Divya Raju.R
	 * 	LastModifiedDate	:	26-12-2013  	  
	 * 	MethodName			: 	automationlogFolderCreation
	 * 	Description				:	Creates folders in given hierarchy to store logs & screen shots to store under
	 * 											 specified build & test cycle id
	 * 
	 **********************************************************************************/	   
	public static void  automationlogFolderCreation(
			String buildNumber,String testCycleId,String suiteName,String runFrom) 
	{
		try
		{
			// Initialize
			String path=null;
			boolean sucess=false;
			
			// Fetch folder name
			GlobalVariables.folder = (TestUtil.now(TestBaseConstants.USR_FILE_FORMAT)+
					"_"+GlobalVariables.suiteName);
		
			// Fetch folder path
			path=cleanPath(GlobalVariables.CONFIG.getProperty(TestBaseConstants.LOG_FOLDER_PATH))
					+"/"+TestBaseConstants.LOG_MAIN_FOLDER_NAME+"/"+
					TestBaseConstants.LOGS_BUILD_NAME+"_"+buildNumber+"-"+
					TestBaseConstants.LOGS_CYCLE_NAME+
					"_"+testCycleId+"/"+GlobalVariables.folder;
			
			// check whether folder creation is needed 
			if(GlobalVariables.CONFIG.getProperty(TestBaseConstants.LOG_FOLDER_CREATION).
					equalsIgnoreCase(TestBaseConstants.RUNMODE_YES))
			{
			
				// Assign the path here
				GlobalVariables.logFolderPath=path+"/"+TestBaseConstants.LOG_FOLDER_NAME;
				GlobalVariables.APPICATION_LOGS.info("Log folder path is "+GlobalVariables.logFolderPath);
				
				// Pass path to file stream
			File  logFolPath=new File( GlobalVariables.logFolderPath);
			
			//if (CONFIG.getProperty("logsOld").equalsIgnoreCase("N")
			if(GlobalVariables.CONFIG.getProperty(TestBaseConstants.LOGS_OLD).
					equalsIgnoreCase(TestBaseConstants.RUNMODE_NO))
			{
				if(logFolPath.exists())			
				{
					// delete the folder if exists
					FileUtils.deleteDirectory(logFolPath);
				}
				 if (logFolPath.exists())	
				 {
					 // check again if folder exists, if yes throw msg
					 GlobalVariables.APPICATION_LOGS.error("Logs Creation failed");
						//System.out.println("creation failed");
				 }
				else 
				{ 
					// print logs folder is deleted
					GlobalVariables.APPICATION_LOGS.info("Old Logs Folder Deleted successfully");
				//	System.out.println(" Log Folder Deleted successfully");
				}
			}
			if(logFolPath.exists()) 
			{
				GlobalVariables.APPICATION_LOGS.info("Logs folder is already present .....");
			//	System.out.println("Logs folder is already present .....");
			}
			else
			{
				// create new directory for logs
				GlobalVariables.success = (logFolPath).mkdirs();	
				//GlobalVariables.APPICATION_LOGS.info("New Logs folder created successfully---"+GlobalVariables.success);
				//System.out.println("Logs folder created successfully---"+success);
				if (!GlobalVariables.success) 
				{
					// Directory creation failed
					GlobalVariables.APPICATION_LOGS.error("Failed to create new Logs folder ......");
					//System.out.println("Failed to create new Logs folder ......");
				}
				else
				{
					// Directory creation successful
					GlobalVariables.APPICATION_LOGS.info("New Logs folder created successfully ......");
					//System.out.println("Logs folder created successfully ......");
				}
			}
			
			GlobalVariables.success = false;
			// screenShotsPath= "./"+"AutomationLogs/"+"Build"+"_"+buildNumber+"-"+"Cycle"+"_"+testCycleId+"/"+suiteName+"/"+"screenshots";
			
			GlobalVariables.screenShotsPath= path+"/"+TestBaseConstants.LOGS_SCREENSHOTS_FOLDER_NAME;
			File screenShotPath=new File(GlobalVariables.screenShotsPath);
			GlobalVariables.APPICATION_LOGS.info("ScreenShotsPath folder path is "+GlobalVariables.screenShotsPath);

		//	if (CONFIG.getProperty("screenShotsOld").equalsIgnoreCase("N")
			if(GlobalVariables.CONFIG.getProperty(TestBaseConstants.SCREEN_SHOTS_OLD).
					equalsIgnoreCase(TestBaseConstants.RUNMODE_NO))
			{
				{
					if (screenShotPath.exists())
						FileUtils.deleteDirectory(screenShotPath);
					if (screenShotPath.exists()) 
					{
						// Directory creation failed
						GlobalVariables.APPICATION_LOGS.error("Failed to delete screenshots folder : "+suiteName+" ......");
						//System.out.println("Failed to delete screenshots folder : "+suiteName+" ......");
					}
					else
					{
						// Directory creation successful
						GlobalVariables.APPICATION_LOGS.info("Old Screenshots folder : "+suiteName+" deleted successfully ......");
						//System.out.println(" screenshots folder : "+suiteName+" deleted successfully ......");
					}
				}
				if(screenShotPath.exists()) 
				{
					GlobalVariables.APPICATION_LOGS.info("New Screenshots folder is already present .....");
				//	System.out.println("Screenshots folder is already present .....");
				}
				else
				{
					// Create new Screenshots folder
					sucess	 = (screenShotPath).mkdirs();
					if (!sucess) 
					{
						
						// Directory creation failed
						GlobalVariables.APPICATION_LOGS.error("Failed to create new Screenshots folder ......");
					//	System.out.println("Failed to create new Screenshots folder ......");
					}
					else
					{
						// Directory creation successful
						
						GlobalVariables.APPICATION_LOGS.info("Screenshots folder created successfully ......");
						//System.out.println("Screenshots folder created successfully ......");
					}
				}
			}	
			}
			else if(GlobalVariables.CONFIG.getProperty("logsFolderCreation").equalsIgnoreCase("N") &&
					GlobalVariables.CONFIG.getProperty("logsFolderCreation").equalsIgnoreCase(" ")  )
				
			{
				GlobalVariables.APPICATION_LOGS.info("Logs folder not created as option is set to N");
				//System.out.println("Logs folder not created as option is set to N");
			}
	}
	catch(Exception e)
	{
		errormsgReporter("Error while creating automation logs folder",e.getCause().toString());
		//System.out.println("error found"+e.getMessage());
	}		
}

	/*********************************************************************************
	 * 	Author						:	Divya Raju.R
	 * 	LastModifiedDate	:	26-12-2013  	  
	 * 	MethodName			: 	openLogFile
	 * 	Description				:	opens file to record logs by taking log folder path & testIdentifier
	 * 
	 **********************************************************************************/	   
	public static String  openLogFile(String logFolderPath,String testIdentifier )
	{
		
		try 
		{
			
			if(GlobalVariables.CONFIG.getProperty(TestBaseConstants.LOG_FILE_CREATION).
			equalsIgnoreCase(TestBaseConstants.RUNMODE_YES))
			{
				
				//System.out.println("Log file creation is Y");
			// open the text file for writing
			String folName =logFolderPath+"//"+testIdentifier;
			GlobalVariables.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(folName+".txt", false), "utf-8"));
			GlobalVariables.logFileName=testIdentifier+".txt";
			
			}
		} 
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			errormsgReporter("Error while creating log file",e.getCause().toString());
		}
		//System.out.println("Log file name---"+GlobalVariables.logFileName);
		return GlobalVariables.logFileName;
	}

	/*********************************************************************************
	 * 	Author						:	Divya Raju.R
	 * 	LastModifiedDate	:	26-12-2013  	  
	 * 	MethodName			: 	infoLog
	 * 	Description				:	Writes info about executing step to logfile
	 * 
	 **********************************************************************************/	   
	public static void infoLog(String Step)
	{
		try
		{
			if(GlobalVariables.CONFIG.getProperty(TestBaseConstants.LOG_FILE_CREATION).
					equalsIgnoreCase(TestBaseConstants.RUNMODE_YES))
			{
			// enter the info to file as step along date & time format
			GlobalVariables.date = new Date ();
			GlobalVariables.writer.write(GlobalVariables.dateFormat.format(GlobalVariables.date)+" STEP : "+Step);
			GlobalVariables.writer.write(GlobalVariables.newline);
			GlobalVariables.date = null;
			GlobalVariables.logFileOpened=true;
			}
		}
		catch (IOException e)
		{
		 
			errormsgReporter("Error while writting to file",e.getCause().toString());
		}	
	}



	/*********************************************************************************
	 * 	Author						:	Divya Raju.R
	 * 	LastModifiedDate	:	26-12-2013  	  
	 * 	MethodName			: 	errorLog
	 * 	Description				:	Writes error info about executing step to logfile
	 * 
	 **********************************************************************************/	  
	public static void errorLog(String Step)
	{
		try
		{
			if(GlobalVariables.CONFIG.getProperty(TestBaseConstants.LOG_FILE_CREATION).
					equalsIgnoreCase(TestBaseConstants.RUNMODE_YES)
				&& GlobalVariables.logFileOpened==true	)
			{
			// log error messages with step as error along with date & time
			GlobalVariables.date = new Date ();
			GlobalVariables.writer.write(GlobalVariables.dateFormat.format(GlobalVariables.date)+" ERROR : "+Step);
			GlobalVariables.writer.write(GlobalVariables.newline);		
			GlobalVariables.date = null;
		}
		}
		catch (IOException e)
		{
			errormsgReporter("Error while writting error message to log file",e.getCause().toString());
		}	
	}
	/*********************************************************************************
	 * 	Author						:	Divya Raju.R
	 * 	LastModifiedDate	:	26-12-2013  	  
	 * 	MethodName			: 	Closes log file
	 * 	Description				:	closes the opened log file
	 * 
	 **********************************************************************************/	  
	public static void closeLogFile(Writer writer)
	{
		try 
		{
			if(GlobalVariables.CONFIG.getProperty(TestBaseConstants.LOG_FILE_CREATION).
					equalsIgnoreCase(TestBaseConstants.RUNMODE_YES)&&
					GlobalVariables.logFileOpened==true)
			{
			// close the opened file
			writer.close();
			}
		} catch (IOException e)
		{
			errormsgReporter("Error while closing the file",e.getCause().toString());	
		}
	}
/*	@BeforeTest
	public void bt()
	{
		automationlogFolderCreation("1.00","1","pages","Excel");
	}
		@Test
	public void testlogs()
	{
			automationlogFolderCreation("1.00","1","pages","Excel");
		String testIdentifier = "lhateu";	
	openLogFile(logFolderPath,testIdentifier);
	errorLog ("oye ???");
	errorLog ("------------------");
	closeLogFile(writer);	
		
	}
		@Test
		public void testlog2()
		{
			automationlogFolderCreation("1.00","1","pages","Excel");
			String testIdentifier = "lhateu1";
			 openLogFile(logFolderPath,testIdentifier);
				errorLog ("oye ???");
				errorLog ("------------------");
				closeLogFile(writer);
		}*/
		
	/*@Test
	public void testlogslov()
	{
	String testIdentifier1 = "loginnowno";

	automationlogFolderCreation("1.00","1","resister","Excel");
	System.out.println(logFolderPath);
	System.out.println(screenShotsPath);
	openLogFile(logFolderPath,testIdentifier1);
	errorLog ("wht u mean???");
	errorLog ("------------------");
	closeLogFile(writer);

	}*/
}


