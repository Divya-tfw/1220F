package com.twelvetwenty.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.twelvetwenty.base.Base;
import com.twelvetwenty.constants.DBConstants;
import com.twelvetwenty.constants.GlobalVariables;






//   "jdbc:mysql://localhost/"

/**
 * @author DivyaR
 * connecting to database
 */
public class DataBaseConnection 
{	
	
public static void dataBaseUpdateMultipleWithOutError(
		String ipaddress,String dataBaseName,String user,String pwd,String tableName,			
			String testStatusfield,String testStatusVal,
			String resultsField,String resultsVal,			
			String testErrorFolderPathfield,
			String testerrorFolderPathVal,
			String screenShotPathField,
			String screenShotPathFieldVal,
			String testErrorLogField,
			String testErrorLogFieldVal,							
			String testCaseName,String tcVal,String testIdentifier,String identifierVal,String iteration,int iterationVal)
	{		
		Connection con=null;
		Statement statement=null;
		try
		{
			//System.out.println(ipaddress+dataBaseName+"?" + "user="+user+"&password="+pwd);
			con = (Connection) DriverManager.getConnection(ipaddress+dataBaseName+"?"
			          + "user="+user+"&password="+pwd);
			
			//System.out.println("connected to db");
			statement= (Statement) con.createStatement();			
			String temp = "update "+tableName+" set "+testStatusfield+"='"+testStatusVal+"'"+","+
					resultsField+"='"+resultsVal+"'"+","+					
					testErrorFolderPathfield+"='"+testerrorFolderPathVal+"'"+","+
					screenShotPathField+"='"+screenShotPathFieldVal+"'"+","+
					testErrorLogField+"='"+testErrorLogFieldVal+"'"+										
					"where "+testCaseName+" ='"+tcVal+"'"+" and "+testIdentifier+" = '"+identifierVal +"'"+
					" and "+iteration+" = '"+iterationVal +"'";	
			//System.out.println("Executing query for with out error---->"+temp);
			GlobalVariables.APPICATION_LOGS.info("Executing query ---->"+temp);
			int val = statement.executeUpdate(temp);	
			if(val==1)	
			{
				GlobalVariables.APPICATION_LOGS.info("Updation is succesfull---"+val);
				//System.out.println("Updation is succesfull---"+val);
			}
				 if(con!=null)
				 {
					 con.close();
				 //System.out.println("connection closed");
				 }
		} 
		catch (SQLException e) 
		{
			//e.printStackTrace();
		//System.out.println("Error in updation--" +e.getMessage());	
		Base.errormsgReporter("Error in updating multiple fields excluding error & screenshots" , e.getMessage());
		}
		 finally 
		 {
			 try 
			 {			 			 	
				 	if(statement!=null)
				 		statement.close();
				 	
				 	if(con!=null)
				 		con.close();			 	
			} 
			 catch (SQLException e)
			 {
					//System.out.println("Error while closing connection"+e.getMessage());	
					Base.errormsgReporter("Error while closing connection" , e.getMessage());
			 }
		 }
	}
	
	/*************************************************************************************************************
	 *		Author 						:	DivyaRaju.R
	 *		LastModifiedDate	:	29-12-2013  
	 *		MethodName			:	dataBaseUpdate
	 *		Description				:	Method which updates multiple fields at run time in mysql table
	 *
	**********************************************************************************************/
	
public static void dataBaseUpdateMultiple(String ipaddress,String dataBaseName,String user,String pwd,String tableName,
			
			String testStatusfield,String testStatusVal,
			String resultsField,String resultsVal,
			String errorMessageField,String errorVal,
			String testErrorFolderPathfield,
			String testerrorFolderPathVal,
			String screenShotPathField,
			String screenShotPathFieldVal,
			String testErrorLogField,
			String testErrorLogFieldVal,
			String screenShotsLinksField,
			String screenShotsLinksFieldVal,
			
			
			String testCaseName,String tcVal,String testIdentifier,String identifierVal,String iteration,int iterationVal)
	{		
		Connection con=null;
		Statement statement=null;
		try
		{
			//System.out.println(ipaddress+dataBaseName+"?" + "user="+user+"&password="+pwd);
			con = (Connection) DriverManager.getConnection(ipaddress+dataBaseName+"?"
			          + "user="+user+"&password="+pwd);
			
			//System.out.println("connected to db");
			statement= (Statement) con.createStatement();			
			String temp = "update "+tableName+" set "+testStatusfield+"='"+testStatusVal+"'"+","+
					resultsField+"='"+resultsVal+"'"+","+
					errorMessageField+"='"+errorVal+"'"+","+
					testErrorFolderPathfield+"='"+testerrorFolderPathVal+"'"+","+
					screenShotPathField+"='"+screenShotPathFieldVal+"'"+","+
					testErrorLogField+"='"+testErrorLogFieldVal+"'"+","+
					screenShotsLinksField+"='"+screenShotsLinksFieldVal+						
					"' where "+testCaseName+" ='"+tcVal+"'"+" and "+testIdentifier+" = '"+identifierVal +"'"+
					" and "+iteration+" = '"+iterationVal +"'";	
			//System.out.println("Executing query ---->"+temp);
			GlobalVariables.APPICATION_LOGS.info("Executing query ---->"+temp);
			int val = statement.executeUpdate(temp);	
			if(val==1)	
			{
				GlobalVariables.APPICATION_LOGS.info("Updation is successfull---"+val);
				//System.out.println("Updation is succesfull---"+val);
			}
				 if(con!=null)
				 {
					 con.close();
				 //System.out.println("connection closed");
				 }
		} 
		catch (SQLException e) 
		{
		//System.out.println("Error in updation--" +e.getMessage());	
		Base.errormsgReporter("Error in updating multiple fields including error & screenshot--" , e.getMessage());
		}
		 finally 
		 {
			 try 
			 {			 			 	
				 	if(statement!=null)
				 		statement.close();
				 	
				 	if(con!=null)
				 		con.close();			 	
			} 
			 catch (SQLException e)
			 {
					//System.out.println("Error while closing connection"+e.getMessage());	
					Base.errormsgReporter("Error while closing connection" , e.getMessage());
			 }
		 }
	}
	/*************************************************************************************************************
	 *		Author 						:	DivyaRaju.R
	 *		LastModifiedDate	:	29-12-2013  
	 *		MethodName			:	dbSelect
	 *		Description				:	Method which returns current  date and time
	 *
	**********************************************************************************************/
	public static int dbSelect(String ipaddress,String dataBaseName,String user,String pwd,String tableName,String bNfield,String bnVal)
	{
		Connection con=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		 String bN=null;
		 int resultId = 0;
		 int testCaseIteration=0;
		 try
		 {
			// System.out.println(ipaddress+dataBaseName+"?" + "user="+user+"&password="+pwd);
			con=(Connection) DriverManager.getConnection(ipaddress+dataBaseName+"?"
			          + "user="+user+"&password="+pwd);
			//System.out.println("connected to db");		
			String t="SELECT "+bNfield +" from "+tableName+ " where "+bNfield +" = '"+bnVal +"'";		
			preparedStatement = (PreparedStatement) con.prepareStatement(t);			          
			resultSet = preparedStatement.executeQuery();			      
			while(resultSet.next())
			{
				 bN=resultSet.getString(DBConstants.dbBuildNumber); 	    	 
			}
				      if(bN==null)
				      {
				    	  GlobalVariables.APPICATION_LOGS.info("Build Value is empty . Hence adding testcase iteration as one");
				         testCaseIteration=1;
				      }
				      else 
				      {
				    	 // System.out.println("Build number is not null--->"+bN);		     
				         PreparedStatement preparedStatement1 = (PreparedStatement) con
						          .prepareStatement("SELECT buildNumber,resultid,testcaseiteration from "+tableName+ " where "+bNfield +" = '"+bnVal +"'");
					      ResultSet resultSet1 = preparedStatement1.executeQuery();
					      while(resultSet1.next())
					      {
					    	 bN=resultSet1.getString(DBConstants.dbBuildNumber); 
					    	 resultId=resultSet1.getInt(DBConstants.dbResultId);
					    	 testCaseIteration=resultSet1.getInt(DBConstants.dbTestCaseIteration);
					      }
					      
					      GlobalVariables.APPICATION_LOGS.info("Result Id ---------->"+resultId);
					      GlobalVariables.APPICATION_LOGS.info("Build Number-->"+bN);
					    
					      GlobalVariables.APPICATION_LOGS.info(" Old testCaseIteration----->"+testCaseIteration);
					      /*   System.out.println("Result Id ---------->"+resultId);
					      
				    System.out.println("Build Number-->"+bN);
	  			      
	  			      System.out.println("Test case Iteration b4-->"+testCaseIteration);*/
	  			      testCaseIteration=testCaseIteration+1;
	  			     // System.out.println(" New testCaseIteration----->"+testCaseIteration);
	  			    GlobalVariables.APPICATION_LOGS.info(" New testCaseIteration----->"+testCaseIteration);
		}	    
		} 
		catch (SQLException e) 
		{
			//System.out.println("Error while selecting data from database"+e.getMessage());
			Base.errormsgReporter("Error while selecting data from database",e.getMessage());	
			
		}
		 finally 
		 {
			 try 
			 {
				 	if(resultSet!=null)			
				 		resultSet.close();
				 	
				 	if(preparedStatement!=null)
				 		preparedStatement.close();
				 	
				 	if(con!=null)
				 		con.close();			 	
			} 
			 catch (SQLException e)
			 {
					//System.out.println("Error while closing connection"+e.getMessage());
					Base.errormsgReporter("Error while closing connection",e.getMessage());	
			 }
		 }	 
	 return testCaseIteration;
	}

	
	/*************************************************************************************************************
	 *		Author 						:	DivyaRaju.R
	 *		LastModifiedDate	:	29-12-2013  
	 *		MethodName			:	dataBaseInsert
	 *		Description				:	Method which inserts values into mysql-table fields as specified
	 *
	**********************************************************************************************/
	public static void dataBaseInsert(String ipaddress,String dataBaseName,String user,String pwd,String tableName,String moduleName ,
		String testCaseID,String userId,	String automationId ,String comments ,String OS ,String environmentName,
		String browserName ,String buildNumber ,String testCycleId,int testCaseIteration,String applicationName,String testStatus  ,
		String results ,String errorMessage ,String executionTime,String testErrorFolderPath ,		
		String screenshotFolderPath ,String testErrorLog ,String screenshotLinks) 	
		{
			Connection con=null;
			PreparedStatement preparedStatement=null;
			try
			{
			/*	System.out.println(ipaddress+dataBaseName+"?" + "user="+user+"&password="+pwd);*/
					con=(Connection) DriverManager.getConnection(ipaddress+dataBaseName+"?"
					          + "user="+user+"&password="+pwd);
					/*System.out.println("connected to db");	*/
					
					long time = System.currentTimeMillis();
					java.sql.Date date = new java.sql.Date(time);
					String date1=date.toString();		
			
					//System.out.println("Insering into db--");
					preparedStatement = (PreparedStatement) con.
															prepareStatement("insert into "+ tableName +" values (default, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ) ");		
				    preparedStatement.setString(1, moduleName);
				    preparedStatement.setString(2,testCaseID );
				    preparedStatement.setString(3,userId );
				    preparedStatement.setString(4, automationId);
				    preparedStatement.setString(5,comments );
				    preparedStatement.setString(6,OS );	 
				    preparedStatement.setString(7, environmentName);	
				    preparedStatement.setString(8,browserName );			    
				    preparedStatement.setString(9,buildNumber );	
				    // preparedStatement.setDate(7, date);	  
				    preparedStatement.setString(10,testCycleId );
				    preparedStatement.setInt(11, testCaseIteration);	
				    preparedStatement.setString(12,applicationName);	
				    preparedStatement.setString(13, testStatus);	
				    preparedStatement.setString(14,results );	
				    preparedStatement.setString(15, errorMessage);	
				    preparedStatement.setString(16, executionTime);
				    preparedStatement.setString(17, date1);	
				    preparedStatement.setString(18, testErrorFolderPath);	
				    preparedStatement.setString(19, screenshotFolderPath);	
				    preparedStatement.setString(20, testErrorLog);
				    preparedStatement.setString(21, screenshotLinks);  	 
				    preparedStatement.executeUpdate();	
				   /* System.out.println("Inserted into db--");			*/
			}
			catch (SQLException e)
			{
					//System.out.println("error"+e.getMessage());
					Base.errormsgReporter("Error while inserting",e.getMessage());
			}
			 finally 
			 {
				 try 
				 {			 			 	
					 	if(preparedStatement!=null)
					 		preparedStatement.close();
					 	
					 	if(con!=null)
					 		con.close();			 	
				} 
				 catch (SQLException e)
				 {
						//System.out.println("Error while closing connection"+e.getMessage());
						Base.errormsgReporter("Error while closing connection",e.getMessage());	
				 }
			 }
}


	/*************************************************************************************************************
	 *		Author 						:	DivyaRaju.R
	 *		LastModifiedDate	:	29-12-2013  
	 *		MethodName			:	dataBaseUpdate
	 *		Description				:	Method which updates only one field in mysql table
	 *
	**********************************************************************************************/
public static void dataBaseUpdate(String ipaddress,String dataBaseName,String user,String pwd,String tableName,String fieldToUpdate,String valToUpdate,
		String usingField1,String ufVal1,String usingField2,String ufVal2,
		String iteration,int iterationVal)
{		
	Connection con=null;
	Statement statement=null;
	try
	{
		/*System.out.println(ipaddress+dataBaseName+"?" + "user="+user+"&password="+pwd);*/
		con = (Connection) DriverManager.getConnection(ipaddress+dataBaseName+"?"
		          + "user="+user+"&password="+pwd);
		
		//System.out.println("connected to db");
		statement= (Statement) con.createStatement();			
		String temp = "update "+tableName+" set "+fieldToUpdate+"='"+valToUpdate+"' where "+usingField1+" ='"+ufVal1+"'"+
		" and "+usingField2+" = '"+ufVal2 +"'"+
		" and "+iteration+" = '"+iterationVal +"'";		
		//System.out.println(temp);
		int val = statement.executeUpdate(temp);	
		if(val==1)	
		{
			 //System.out.println("Updation succesfull---"+val);
			GlobalVariables.APPICATION_LOGS.info("Updation successfull---"+val);
		}
			 if(con!=null)
				 con.close();
			// System.out.println("connection closed");
	} 
	catch (SQLException e) 
	{
	
	Base.errormsgReporter("Error while updating single entity",e.getMessage());	
	}
	 finally 
	 {
		 try 
		 {			 			 	
			 	if(statement!=null)
			 		statement.close();
			 	
			 	if(con!=null)
			 		con.close();			 	
		} 
		 catch (SQLException e)
		 {
				//System.out.println("Error while closing connection"+e.getMessage());
				Base.errormsgReporter("Error while closing connection",e.getMessage());	
		 }
	 }
}











/*
private static void writeResultSet(ResultSet resultSet) throws SQLException
{
    // ResultSet is initially before the first data set
    while (resultSet.next()) 
    {
      // It is possible to get the columns via name
      // also possible to get the columns via the column number
      // which starts at 1
      // e.g. resultSet.getSTring(2);
    	
    	
      int resultId=resultSet.getInt("ResultId");
      String userID = resultSet.getString("UserId");
      String operatingSystem = resultSet.getString("OS");
      String environmentName = resultSet.getString("EnvironmentName");
      String browserName=resultSet.getString("BrowserName");
      String applicationName=resultSet.getString("ApplicationName");
      String buildNumber=resultSet.getString("BuildNumber");
      String testCycleId=resultSet.getString("TestCycleId");
      String moduleName=resultSet.getString("ModuleName");
      String testRunDate=resultSet.getString("TestRunDate");
      String testCaseID=resultSet.getString("TestCaseID");
      String automationId=resultSet.getString("AutomationId");
      String comments=resultSet.getString("Comments");
      String testStatus=resultSet.getString("TestStatus");
      String results=resultSet.getString("Results");
      String errorMessage=resultSet.getString("ErrorMessage");
      String executionTime=resultSet.getString("ExecutionTime");
      String TestErrorFolderPath=resultSet.getString("TestErrorFolderPath");
      String TestErrorLog=resultSet.getString("TestErrorLog");
      String screenshotsFolderPath=resultSet.getString("ScreenshotFolderPath");
      String screenshotLinks=resultSet.getString("ScreenshotLinks");
      int testCaseIteration=resultSet.getInt("TestCaseIteration");
      
      System.out.println("resultId                     :"+resultId);
      System.out.println("userID                       :"+userID);
      System.out.println("operatingSystem      :"+operatingSystem);
      System.out.println("environmentName  :"+environmentName);
      System.out.println("browserName           :"+browserName);      
      System.out.println("buildNumber            :"+buildNumber);
      System.out.println("applicationName"+applicationName);
      System.out.println("testCycleId				"+testCycleId);
      System.out.println("moduleName"+moduleName);
      System.out.println("testRunDate"+testRunDate);
      System.out.println("testCaseID"+testCaseID);
      System.out.println("automationId"+automationId);
      System.out.println("comments"+comments);
      System.out.println("testStatus"+testStatus);
      System.out.println("results"+results);
      System.out.println("errorMessage"+errorMessage);
      System.out.println("executionTime"+executionTime);
      System.out.println("TestErrorFolderPath"+TestErrorFolderPath);
      System.out.println("TestErrorLog"+TestErrorLog);
      System.out.println("screenshotFolderPath"+screenshotsFolderPath);
      System.out.println("screenshotLinks"+screenshotLinks);
      System.out.println("testCaseIteration"+testCaseIteration);
      
      
      
      
      
      
      
    }
 
}
*/

}

/**
 * 
 * String selectQuery="SELECT buildNumber from "+tableName+" 'where "+buildNumber+" = ' "+bN +" ' ";
			ResultSet  resultSet = con.createStatement().executeQuery(selectQuery);
		    //  writeResultSet(resultSet);
		      while(resultSet.next()==true)
		      {
		    	  String buildNumber1=resultSet.getString("BuildNumber");
		    
		    	  System.out.println("buildNumber            :"+buildNumber1);
		    	  if(bN.equalsIgnoreCase(buildNumber1))
		    	  {
			    	  if(buildNumber1=="")
						{
			    		   testCaseIteration=1;
						}
			    	  else 
			    	  {
			    		   testCaseIteration=testCaseIteration+1;
			    	  }
		    	  }
		      }
			
 */
 
//
//dataBaseUpdate("Automation_Results_New","root","sunday","Autoresults","comments","kathe",
//"automationId","valid1","testcaseid","validlogin");
/*	dataBaseUpdate("Automation_Results_New","root","sunday","Autoresults","ScreenshotLinks","login_ TS01_navigate.jpg#login_ TS02_input.jpg",
		"automationId","valid3",null,null);*/
		
//int tci=dbSelect("Automation_Results_New","root","sunday","Autoresults","buildNumber","1.1");
//System.out.println(tci);

//dataBaseInsert(
//		"Automation_Results_New",
//		"root",
//		"sunday",				
//		"Autoresults",
//		"signin4",
//		"validlogin2",
//		"arun",
//		"valid3",
//		null,
//		"Win_xp",
//		"qa",
//		"chrome",
//		"1.1",
//		"1.11",
//		1,
//		"345#gmail",
//		"not started",
//		null,
//		null,
//		null,				
//		null,
//		"C:/WorkSpace/AutomationExcel/AutomationLogs/Build_1.00-Cycle_1/pages/screenshots",
//		null,
//		null
//		);
//	
	
//
//dataBaseUpdate("Automation_Results_New","root","sunday","TestStatus","inProgress","testcaseid","validlogin","automationId","valid1");

// String temp = "update Autoresults set "+fieldToUpdate+"=' "+testStatus+" ' where "+usingField1+" = ' "+ufVal1 +" ' " +" and "+usingField2+" = ' "+ufVal2 +" ' ";
//
	//	String temp="update Autoresults set testStatus='nothing' where testcaseid='validlogin' and  automationId='valid1' ";		