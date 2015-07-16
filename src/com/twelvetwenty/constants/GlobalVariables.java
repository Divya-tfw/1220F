package com.twelvetwenty.constants;

import io.appium.java_client.AppiumDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import org.w3c.dom.Document;

import atu.testrecorder.ATUTestRecorder;








import com.twelvetwenty.base.CustomVerification;
import com.twelvetwenty.base.ExcelTestBase;
import com.twelvetwenty.util.Xls_Reader;





public class GlobalVariables extends CustomVerification
{
	public static Properties CONFIG;
	public static Properties OR;				
	public static Xls_Reader testSuite;
	public static Xls_Reader controller;
	public static Xls_Reader testData;
	public static String runFrom=null;
		public static String testCaseID;
	public static String currentSuite;
	public static String currentTest;
	public static String keyword;
	public static String testsuitelocation;
	public static String testdatalocation;
	public static WebDriver driver = null;
	//public static Selenium selenium=null;
	//public static EventFiringWebDriver driver = null;
	public static String object;
	public static String currentTSID;
	public static String folder;		
	public static boolean success;
	public static boolean fol;
	public static int iteratenum;
	public static String stepDescription;
	public static String proceedOnFail;
	public static String testStatus=null;
	public static String classpath;
	public static String data_column_name;
	public static int testRepeat;
	public static int iterate;
	public static int iteration;	
	public static boolean Condition_Val=true;
	public static FileInputStream fIS;
	public static FileInputStream fIS1;
	public static FileInputStream fIS2;
	public static String suiteVal=null;
	public static String startTime = null;
	public static String errorStringMsg=null;;
	public static Writer writer = null;
	public static FileWriter fw=null;
	public static BufferedWriter bw=null;
	public static Object configDataValue;
	public static String suiteName=null;
	public static String scriptName=null;
	public static Xls_Reader sheetPath;
	public static int flag=0;		
	public static SoftAssert sa=new SoftAssert();		
	public static Logger APPICATION_LOGS = Logger.getLogger(ExcelTestBase.class.getName());
	public static CustomVerification cverify=new CustomVerification();
	public  static StringBuffer verificationErrors;
	public static String tR=null;
	 public static  String testCaseIdentifier=null;
	 public static String userName=null;
	public static String reportStartTime=null;
	public static String logFolderPath=null;
	public static String screenShotsPath=null;			
	public static String newline = System.getProperty("line.separator");	
	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static Date date = null;
	public static String reportVal=null;
	public static String temp="";
	public static String screenshotsLinksForAllSteps=null;
	public static String screenshotsLinksForFailed=null;
	public static String logsLinks=null;
	public static String  logFileName=null;
	public static String  endTime=null;
	public static String  reportendTime=null;
	public static String exeTime=null;
	public static String suiteResult=null;
	public static int  dbFlag=0;
	public static 	String Runmode=null;
	public static String testScriptIdentifier=null;
	public static String errmsg=null;
	public static String dbstatus=null;	
	public static String modifiedstatusVal=null;
	public static String autoResults=null;
	public static String testusappend=null;
	public static String statusVal="";
	public static int errorLength;
	public static  String startReportTime=null;
	public static  String ur=null;
	public static boolean isBrowserOpened = false;
	public static boolean runmode=true;
	public static boolean Skip=false;
	public static boolean testresult=true;
	public static boolean fail=false;
	public static String result=null;
	public static String suiteRunStatus=null;
	public static String testRunStatus=null;
	public static String dataRunStatus=null;
	public static String screenshotFilename=null;
	public static String errormsgVal=null;
	public static int flag2=0;
	public static String exceptionMsgVal=null;
	public static String usr_setstatus=null;
	public static String ipaddressval=null; 
	public static String dbresult=null;
	public static String scriptStatus=null;
	// XML variables
	public static String testSuiteStatus=null;
	public static String testScript=null;
	public static String xmltestScriptName=null;
	public static boolean skiptests=false;
	public static int iterationVal;
	public static Document doc;
	public static File testLocation;
	public static DocumentBuilderFactory dbFactory=null;
	public static  DocumentBuilder dBuilder=null;
	public static boolean errorExistsCheck=false;
	public static boolean screenshotsExistsCheck=false;
	public static File fXmlFile = null;
	public static String browser=null;
	public static String port=null;
	public static String gridIpAddress=null;
	public static String custkeywordMsg=null;

	public static ATUTestRecorder recorder;
	public static boolean suiterecording=false;
	public static boolean testrecording=false;

	public static String appType=null;
	public static boolean logFileOpened=false;
	public static AppiumDriver mdriver = null;
	
	public static  WebDriverWait wait=null;
	
	public static int dataFileRowCount=0;
	public static int executeflag=0;
	public static boolean filecopied=false;
	
	public static Object buildNumber=null;
}
