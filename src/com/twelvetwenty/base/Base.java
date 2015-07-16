package com.twelvetwenty.base;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.testng.annotations.Listeners;
import org.apache.commons.lang3.text.WordUtils;
import org.testng.Assert;





import com.twelvetwenty.constants.GlobalVariables;
import com.twelvetwenty.constants.TestBaseConstants;
import com.twelvetwenty.util.TestUtil;

import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;





/********************************************************************************************
 *		Author 						:	DivyaRaju.R
 *		LastModifiedDate	:	8-2-2014  
 *		ClassName					:	Base
 *		Description				:	Super class which extends CustomVerification class containing
 *												 all methods for performing major operations 
 *
*********************************************************************************************/
@Listeners({ ATUReportsListener.class, ConfigurationListener.class,
	MethodListener.class })
public class Base /*extends ListenerClass*/
{	
	
	/******************************************************************************************
  	 * 	Author						:	Divya Raju.R
  	 * 	LastModifiedDate	:	30-11-2013  	   	
  	 * 	MethodName			: 	loadConfig
  	 * 	Description				:	This method is used to load Config file to bin path during execution
  	 * 	Input							:	CHOSEN_RUN_FROM value has to be set in TestBaseConstants file 
  	 * 	Output						:	Based on CHOSEN_RUN_FROM value it will decide bin path & load files 
  	 *   	  
  	 ********************************************************************************************/	
	public static boolean loadConfig()
	{
		boolean configLoaded=false;
		GlobalVariables.CONFIG = new Properties();
		 
		try 
		 {		
			// fetch the file path present TestBaseConstants file-if run from is ant then
			//chose file path using string util else chose path from eclipse project folder
			 GlobalVariables.fIS = new FileInputStream
					 (TestUtil.runInEditor(TestBaseConstants.CHOSEN_RUN_FROM)+
					  "/"+TestBaseConstants.CONFIG_FOLDER+"/"	
					   +TestBaseConstants.CONFIG_FILE_NAME);
			 
			 GlobalVariables.APPICATION_LOGS.info(TestUtil.runInEditor
					 (TestBaseConstants.CHOSEN_RUN_FROM)+
						"/"+TestBaseConstants.CONFIG_FOLDER+"/"	+
					 TestBaseConstants.CONFIG_FILE_NAME);		
		 }
		 catch (Throwable e) 	
		 {		
			 // throw exception if file not found
			 errormsgReporter("Config"+TestBaseConstants.FILE_NOT_FOUND_ERROR,e.getMessage());	
		 }
		 try 
		 {			
			 // load the found file
			 GlobalVariables.CONFIG.load( GlobalVariables.fIS);		
			 configLoaded=true;				
			 GlobalVariables.APPICATION_LOGS.info("Config File loaded ------------------------------->")	;			
		 }
		catch (Throwable e) 
		{								
			errormsgReporter("Config"+TestBaseConstants.FILE_NOT_LOADED_ERROR,e.getMessage());			
		}		
		return configLoaded;		
	}	
	
	/******************************************************************************************
  	 * 	Author						:	Divya Raju.R
  	 * 	LastModifiedDate	:	30-11-2013  	   	
  	 * 	MethodName			: 	loadOR
  	 * 	Description				:	This method is used to load OR file to bin path during execution
  	 * 	Input							:	runMode value from Config file
  	 * 	Output						:	Based on runMode value it will decide bin path & load files 
  	 *   	  
  	 ********************************************************************************************/	
	public static boolean loadOR()
	{
		boolean orLoaded=false;
		GlobalVariables.OR = new Properties();
		try 
		{			
			GlobalVariables.fIS1 =new FileInputStream
					(TestUtil.runInEditor(GlobalVariables.CONFIG.getProperty("runIn"))+
					"/"+TestBaseConstants.CONFIG_FOLDER+"/"	+TestBaseConstants.OR_FILE_NAME	);		
			
			GlobalVariables.APPICATION_LOGS.info("OR File  found ------------------------------->");
		}
		catch (Throwable e) 
		{		
			errormsgReporter("OR"+TestBaseConstants.FILE_NOT_FOUND_ERROR,e.getMessage());
		}
		try
		{
			GlobalVariables.OR.load(GlobalVariables.fIS1);						
			orLoaded=true;	
			GlobalVariables.APPICATION_LOGS.info("OR File loaded ------------------------------->");		
		}
		
		catch (Throwable e)
		{			 
			 errormsgReporter("OR"+TestBaseConstants.FILE_NOT_LOADED_ERROR,e.getMessage());			
		}		
		return orLoaded;
	}
	
	

	/***************************************************************************************************
  	 * 	Author						:	Divya Raju.R
  	 * 	LastModifiedDate	:	30-11-2013  	   	
  	 * 	MethodName			: 	errorReportValforExcel
  	 * 	Description				:	This method is used to track error during execution
  	 * 	Input							:	Variables to track & decide which error is obtained either exception error or custom error
  	 * 	Output						:	Variable containing eithe of the error to write back to excel
  	 *   	  
  	 ****************************************************************************************************/	
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
    
	
	/***************************************************************************************************
  	 * 	Author						:	Divya Raju.R
  	 * 	LastModifiedDate	:	30-11-2013  	   	
  	 * 	MethodName			: 	cleanContent
  	 * 	Description				:	This method is used to clean content of string by replacing \ by blank value
  	 * 	Input							:	string to clean
  	 * 	Output						:	cleaned string
  	 *   	  
  	 ****************************************************************************************************/	
    public static String cleanContent(String stringToClean)
	{		
		stringToClean = stringToClean.replace("\"", "");
		return stringToClean;     
	}
    
	/***************************************************************************************************
  	 * 	Author						:	Divya Raju.R
  	 * 	LastModifiedDate	:	30-11-2013  	   	
  	 * 	MethodName			: 	cleanPath
  	 * 	Description				:	This method is used to clean path of string by replacing \ by blank value
  	 * 	Input							:	path to clean
  	 * 	Output						:	cleaned path
  	 *   	  
  	 ****************************************************************************************************/	
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
			errormsgReporter("Value is blank.Input data",null);			
		}
		return s;
	}
        
    /***************************************************************************************************
  	 * 	Author						:	Divya Raju.R
  	 * 	LastModifiedDate	:	30-11-2013  	   	
  	 * 	MethodName			: 	launchSite
  	 * 	Description				:	This method is used to clean URL string by replacing \ by blank value & launch the browser
  	 * 	Input							:	URL to clean
  	 * 	Output						:	cleaned URL
  	 *   	  
  	 ****************************************************************************************************/	
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
			errormsgReporter("Value is blank.Input data",null);			
		}
		
	}
    
    
    
    /***************************************************************************************************
  	 * 	Author						:	Divya Raju.R
  	 * 	LastModifiedDate	:	30-11-2013  	   	
  	 * 	MethodName			: 	setProperty
  	 * 	Description				:	This method is used to set property in config file
  	 * 	Input							:	property name , property value
  	 * 	Output						:	property set with new value
  	 *   	  
  	 ****************************************************************************************************/	
    public static void setProperty(String propertName,String propertyVal)
    {
    	FileOutputStream out = null;
		try 
		{
			out = new FileOutputStream(TestUtil.runInEditor(TestBaseConstants.CHOSEN_RUN_FROM)+
					"/"+TestBaseConstants.CONFIG_FOLDER+"/"	+TestBaseConstants.CONFIG_FILE_NAME);
			GlobalVariables.CONFIG.setProperty(propertName,propertyVal);
			GlobalVariables.CONFIG.store(out, null);
			out.close();
		} 
		catch (Exception e)
		{
			errormsgReporter("Could not set property value",e.getMessage());	
		}  
    	 
    	}
   
    
    
    /***************************************************************************************************
  	 * 	Author						:	Divya Raju.R
  	 * 	LastModifiedDate	:	30-11-2013  	   	
  	 * 	MethodName			: 	errormsgReporter
  	 * 	Description				:	This method is track custom error & exception error & print either of them
  	 * 	Input							:	Custom Error Msg , Exception Error msg
  	 * 	Output						:	Prints the error in report, console & log files
  	 *   	  
  	 ****************************************************************************************************/	
		public static void errormsgReporter(String custErrMsg,String exceptionErrmsg) 
		{
			 if(custErrMsg!=null)
			 {
										
				TestUtil. custReporter(custErrMsg);
				GlobalVariables.APPICATION_LOGS.error(custErrMsg);
				GlobalVariables.errormsgVal=custErrMsg;
				//throwAssertionFailure(custErrMsg);
				CustomVerification.verifyContent(false, custErrMsg);
			 }
			 else
			 {
									
					TestUtil. custReporter( exceptionErrmsg);
					GlobalVariables. APPICATION_LOGS.error(exceptionErrmsg);
					GlobalVariables.errormsgVal=exceptionErrmsg;
					//throwAssertionFailure(exceptionErrmsg);
					CustomVerification.verifyContent(false, custErrMsg);
			 }
			
		}
		
		
		/***************************************************************************************************
	  	 * 	Author						:	Divya Raju.R
	  	 * 	LastModifiedDate	:	30-11-2013  	   	
	  	 * 	MethodName			: 	getActualFileName
	  	 * 	Description				:	This method is used get actual file name irrespective of appended characters
	  	 * 	Input							:	currentFileName
	  	 * 	Output						:	Converted File Name
	  	 *   	  
	  	 ****************************************************************************************************/	
		public static String getActualFileName(String currentName)
		{
			String convertedFileName=null;
			convertedFileName=WordUtils.capitalizeFully(currentName, '_');
			return convertedFileName;
		}
		
		/***************************************************************************************************
	  	 * 	Author						:	Divya Raju.R
	  	 * 	LastModifiedDate	:	30-11-2013  	   	
	  	 * 	MethodName			: 	throwException
	  	 * 	Description				:	This method is used to throw assertion error when ever script fails
	  	 * 	Input							:	Message to print along with failed assertion
	  	 * 	Output						:	Assertion failure along with given message
	  	 *   	  
	  	 ****************************************************************************************************/	
		 public static void throwAssertionFailure(String msg) 
		 {
			 if(msg!=null)
			 {
			 Assert.fail(msg);
			 }
		 }
}
