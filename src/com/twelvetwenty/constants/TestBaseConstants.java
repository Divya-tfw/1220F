/**
 * Constants file to be used in the framework
 */
package com.twelvetwenty.constants;

public class TestBaseConstants
{
	public static String USR_FOLDER_NAME="dd.MMMMM.yyyy.hh.mm.ss.aaa";
	public static String BROWSER_FIREFOX="FireFox";
	public static String BROWSER_CHROME="Chrome";
	public static String BROWSER_IE="IE";
	public static String BROWSER_HTMLUNITDRIVER="Html";
	
	//User Defined Constants
	public static String USR_STATUS="Y";
	public static String resultSkipVal="Skip";
	
	// Time formats
	public static String USR_FILE_FORMAT="dd.MMMMM.yyyy.hh.mm.ss.aaa";
	public static String TRANSACTION_TIME_FORMAT="yyyy/MM/dd HH:mm:ss";
	
	//Database Related
	public static String LOCALIPADDRESS="jdbc:mysql://localhost/";
	
	//XML Related
	public static String XML_SUITE_ATTRIBUTE_NAME="name";
	public static String XML_TEST_ATTRIBUTE_NAME="Test";
	public static String XML_FOLDER_NAME="xml_suite";
	public static String XML_SUITE_FILE_NAME="Controller";
	public static String LOCALIPADRESSVAL="jdbc:mysql://localhost/";
	public static String DEFAULT_VAL="Default";
	
	public static String RUNMODE_YES = "Y";
	public static String RUNMODE_NO = "N";
	public static String CONTROLLER_SUITE_SHEET_NAME="Test_Suite";	
	public static String CONTROLLER_FILE_NAME="Suite";
	public static String CONTROLLER_TEST_SUITE_NAME="TestSuiteName";
	public static String CONTROLLER_RUNMODE="Runmode";
	public static String CONTROLLER_SUITE_AUTHOR="User_Name";	
	public static String CONTROLLER_SUITE_LOCATION="Test_Suite_Location";
	public static String CONTROLLER_DATA_LOCATION="Test_Data_Location";
	public static String TEST_SUITE_SHEET_NAME="Test_Cases";
	public static String TEST_SUITE_NAME="TestCaseName";
	public static String TEST_SUITE_RUNMODE="Runmode";
	public static String TEST_DATA_IDENTIFIER="Identifier";
	public static String TEST_DATA_RUNSTATUS="Runmode";	
	
	public static String TEST_SCRIPT_NAME="TSN";
	public static String TEST_SCRIPT_DESCRIPTION="Description";
	public static String TEST_SCRIPT_STEPS="Steps";
	
	
	
	
	// Folder Names
	
	public static String CONFIG_FOLDER="config";
	public static String XML_SUITE_FOLDER="xml_suite";
	public static String EXECUTABLES_FOLDER="exes";
	public static String EXCEL_SUITE_FOLDER="excel_suite";
	
//	File Names 

	public static String CONFIG_FILE_NAME="Config.properties";
	public static String OR_FILE_NAME="OR.properties";

	// Run using values
	public static String RUN_USING_EXCEL_VAL="Excel";
	public static String RUN_USING_XML_VAL="XML";

	// used
	
	

	public static String EXCEL_FOLDER_PATH="excels";
	public static String RESULT_PASSVALUE="Pass";
	public static String RESULT_FAILVALUE="Fail";
	public static String RESULT_SKIPVALUE="Skip";
	
	
	// Browser Constants

	
	//User Defined Constants

	
	
	// Time formats

	
	
	

	
	// Run from values
	public static String RUN_FROM_ECLIPSE="Eclipse";
	public static String RUN_FROM_ANT="Ant";
	public static String CHOSEN_RUN_FROM=RUN_FROM_ECLIPSE;
	//public static String CHOSEN_RUN_FROM=RUN_FROM_ANT;
	public static String RUN_IN="runIn";
	
	// skip messages
	public static String skipSuiteMsg="Run status of Suite is N/blank";
	public static String skipTestMsg="Run status of Test is N/blank";
	public static String skipDataMsg="Run status of Data is N/blank";
	
	// Error messages
	public static String FILE_NOT_FOUND_ERROR="File not found in specified location";
	public static String FILE_NOT_LOADED_ERROR="File could not be loaded";
	
	// Database status
	public static String DB_STATUS_NOT_STARTED="Not_Started";
	public static String DB_STATUS_STARTED="Started";
	public static String DB_STATUS_IN_PROGRESS="In_Progress";
	public static String DB_STATUS_COMPLETED="Completed";
	
	// logs 
	public static String LOG_FOLDER_NAME="Logs";
	public static String LOGS_BUILD_NAME="Build";
	public static String LOGS_CYCLE_NAME="Cycle";
	public static String LOGS_FILE_FORMAT="ddMMMyy";
	public static String LOGS_FILES_FOLDER_NAME="Logfiles";
	public static String LOGS_SCREENSHOTS_FOLDER_NAME="ScreenShots";
	public static String LOG_MAIN_FOLDER_NAME="AutomationLogs";
	
	
	
	public static String SCREEN_SHOT_FOR_FAILED="screenShotForFailed";
	public static String BROWSER_TYPE="browserType";
	public static String TEST_CYCLE_ID="testCycleId";
	public static String IP_ADDRESS_VAL="ipAddress";
	public static String OS="OS";
	public static String LOGS_OLD="logsOld";
	public static String TABLE_NAME="tableName";
	public static String LOGS_FOLDER_PATH="logsFolderPath";
	public static String DBSTATUS_AFTER_EXECUTION="dbStatus_after_execution";
	public static String TEST_SITE_URL="testSiteURL";
	public static String SCREEN_SHOT_FOR_ALL_STEPS="screenShotForAllSteps";
	public static String PASSWORD="passWord";
	public static String CONSOLE_PRINT="consolePrint";
	public static String RUN_FROM="runFrom";
	public static String SCREEN_SHOTS_OLD="screenShotsOld";
	public static String USER_NAME="userName";
	public static String ENVIRONMENT_NAME="environmentName";
	public static String BROWSER_PATH="browserPath";
	public static String APPLICATION_NAME="applicationName";
	public static String USER_DEFINED_REPORT="userDefinedReport";
	public static String DBSTATUS_AT_EXECUTION="dbStatus_at_execution";
	public static String AUTOMATION_RESULTS="automationResults";
	public static String DBSTATUS_BEFORE_EXECUTION="dbStatus_before_execution";
	public static String BUILD_NUMBER="buildNumber";
	public static String REPORTER_LOG="reporterLog";
	public static String USR_FOLDER_PATH="userDefinedReportFolder";
	public static String RUN_USING_GRID="runUsingGrid";
	public static String BROWSER_NAME="browserName";
	public static String DATA_BASE_NAME="dataBaseName";
	public static String ITERATION_VALUE="iterationVal";
	public static String IMPLICIT_WAIT_VAL="implicitWaitVal";
	public static String BULK_DATA_INSERTED="bulkDataInserted";
	public static String USER_SET="userSet";
	public static String RUN_IN_EDITOR="runIn";
	public static String LOG_FOLDER_CREATION="logsFolderCreation";
	public static String LOG_FILE_CREATION="logFileCreation";
	
	public static String LOG4J_FILE="log4j.xml";
	public static String USER_REPORT_CREATED="usrSet";
	public static String LOG_FOLDER_PATH="logsFolder";
	
	// ATU report
	
	public static String INFO_VALUE="Info";
public static String MOBILE_APP_TYPE="Mobile";
	
	public static String WEB_APP_TYPE="Web";
	// Mobile Appium constants
	
		public static String DEVICE_TYPE="device";
		public static String APK_FILE_PATH="app";
		public static String DEVICE_NAME="deviceName";
		public static String PLATFORM_NAME="platformName";
		public static String PLATFORM_VERSION="platformVersion";
		public static String APP_PACKAGE="app-package";
		public static String APP_ACTIVITY="app-activity";
		//Cloud execution on Test droid constants
		
		public static String TESTDROID_TARGET="testdroid_target";
		public static String TESTDROID_USERNAME="testdroid_username";
		public static String TESTDROID_PASSWORD="testdroid_password";
		public static String TESTDROID_PROJECT="testdroid_project";
		public static String TESTDROID_TESTRUN_NAME="testdroid_testrun";
		public static String TESTDROID_DEVICE="testdroid_device";
		public static String TESTDROID_APP_KEY_VALUE="testdroid_app";
	
		
		
		// Recording related 
		
		public static String TEST_RECORDER="testRecoder";
		public static String VIDEO_FOLDER_PATH="videoFolderPath";
		//1220 app
		public static String BASELINE_BUILD_TYPE="Baseline";
		public static String ACTUAL_BUILD_TYPE="Actual";
		public static String BUILD_TYPE="buildType";
		
		//Script constants
		
		public static String AUTOMATION_ID="Automation_Id";
		public static String DATA_RUNMODE="Runmode";
		public static String SCHOOL_NAME="sSchoolName";
		public static String SITE="site_";
		public static String SITE_USER_NAME="s1220User";		
		public static String SITE_PASSWORD="s1220pwd";
		public static String TEXT_EMAIL_ADDRESS="txt_EmailAddress";
		public static String TEXT_PASSWORD="txt_Password";
		public static String DROP_SELECT_USING_TEXT="Text";
		public static String DROP_SELECT_USING_INDEX="Index";
		public static String DROP_SELECT_USING_VALUE="Value";
		public static String CUT_OFF_DATE_WITH="With";
		public static String CUT_OFF_DATE_WITHOUT="Without";
		public static String BUTTON_GENERATE_REPORT="btn_GenerateReport";
		public static String BUTTON_STANDARD_REPORTS="btn_std_reports";
		public static String LINK_ABA_REPORTS="lnk_ABA_report";
		public static String WAIT_VALUE="iWait";
		
		
		
		
		// DROP DOWN CONSTANTS
		public static String DROP_DOWN_GRADUATION_YEAR="dpdown_GraduationYr";
		public static String DROP_DOWN_GRADDUATION_TERM="dpdown_GraduationTerm";
		public static String DROP_DOWN_OFFER_TIMING="dpdown_Offer_timing";
		public static String DROP_DOWN_CUT_OFF_DATE="dpdown_LawCutoffDate";
		public static String DROP_DOWN_JOB_PHASE="dpdown_JobPhaseId";
		public static String DROP_DOWN_JOINT_DEGREE="dpdown_JointDegree";
		public static String DROP_DOWN_PROGRAM="dpdown_Program";
		
		
		//SCRIPT DATA CONSTANTS
		public static String SUB_REPORT_NAME="sSub_Report_Name";
		public static String GRADUATION_YEAR="iGraduationYr";
		public static String GRADDUATION_TERM="sGraduationTerm";
		public static String OFFER_TIMING="sOffer_Timing";		
		public static String CUT_OFF_DATE="sCutoff_Date";
		public static String JOB_PHASE="sJobPhaseId";
		public static String JOINT_DEGREE="sJoint_Degree";
		public static String PROGRAM="sProgram";
		
		
		//ATU CONSTANTS
		public static String AUTHOR_NAME="DIVYA";
		public static String VERSION_VALUE="1.0";
		public static String ATU_INDEX_PAGE_DESCRIPTION="12Twenty Reports of execution";
		
		
		
	
		
		
}
