package com.twelvetwenty.base;

import org.testng.Assert;
import org.testng.Reporter;

/********************************************************************************************
 *		Author 						:	DivyaRaju.R
 *		LastModifiedDate	:	30-11-2013  
 *		ClassName					:	CustomVerification 
 *		Description				:	This class contains methods for handling verification errors
 *
*********************************************************************************************/
public class CustomVerification 
{
		public  static StringBuffer verificationErrors;
		public static String errormsg=null;
		public static int iterationVal;
	
		
		 public CustomVerification()
		 {
		  verificationErrors = new StringBuffer();
		 }
	
		 public  void clearVerificationErrors() 
		 {
		  verificationErrors = new StringBuffer();
		  
		 }
		 
		   /*********************************************************************************
		  	 * 	Author						:	Divya Raju.R
		  	 * 	LastModifiedDate	:	30-11-2013  	  
		  	 * 	MethodName			: 	assertEqualsTest
		  	 * 	Description				:	method performs validation by comparing expected with actual values
		  	 * 	Input							:	keywordName,Expected value & Actual value
		  	 * 
		  	 **********************************************************************************/	    
		 public static String  assertEqualsTest(Object actual,Object expected)
		 {
			 String status = null;
			 try
			 {
				Assert.assertEquals(((String) actual).trim(),((String) expected).trim());
				 
				 status="Pass";
			 }
			 catch (Error e)
			 {
				// updateError(status,row,column,xltext,webtext);
				   verificationErrors.append(e);				   
				   Reporter.log("Fail----"+e.getMessage()+" ", true);
				   status="Fail"+e.getMessage();
			}
			
			return status;
		 }
		 
		   /*********************************************************************************
		  	 * 	Author						:	Divya Raju.R
		  	 * 	LastModifiedDate	:	30-11-2013  	  
		  	 * 	MethodName			: 	verifyContent
		  	 * 	Description				:	This method is used to capture failures during execution
		  	 * 	Input							:	Boolean values(true/false),msg to be thrown with error
		  	 * 
		  	 **********************************************************************************/	   
			 public static void verifyContent(Boolean b, String msg) 
			 {
				  try 
				  {				
					  Assert.fail(msg);
				  } 
				  catch (Error e) 
				  {			
					  errormsg=msg;
					   verificationErrors.append(e);
					   Reporter.log(msg +"");
					 
				  } 
			 }
			 
			 /**************************************************************************************
			  	 * 	Author						:	Divya Raju.R
			  	 * 	LastModifiedDate	:	30-11-2013  	  
			  	 * 	MethodName			: 	checkForVerificationErrors
			  	 * 	Description				:	This method is checks for any verification failures & tracks it at the end
			  	 * 				  	 
			  	 **************************************************************************************/	   
			 
				 public void checkForVerificationErrors() 
				 {
						 String verificationErrorString = verificationErrors.toString();
						  // Clear Verification Errors so that it is ready to test new verifications
						  clearVerificationErrors();
						  if (!"".equals(verificationErrorString))
						   fail(verificationErrorString);
				 }
				 
				 
				 public static void fail(String message) {
				    	Assert.fail(message);
				    }
	
}
