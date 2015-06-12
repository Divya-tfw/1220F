package com.twelvetwenty.base;

import org.testng.IClass;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.twelvetwenty.constants.GlobalVariables;



public class ListenerClass extends TestListenerAdapter 
{
	Throwable s=null;
	@Override
	public void onTestSuccess(ITestResult tr)
	{
		log("Test '" + tr.getName() + "' PASSED");	
		log(tr.getTestClass());		
	}

	@Override
	public void onTestFailure(ITestResult tr) {

		log("Test '" + tr.getName() + "' FAILED");
		s= tr.getThrowable();
		GlobalVariables.errormsgVal=s.toString();
		log("Failed due to --->" +s.toString());
		
	}

	@Override
	public void onTestSkipped(ITestResult tr)
	{
		log("Test '" + tr.getName() + "' SKIPPED");
		s= tr.getThrowable();
		GlobalVariables.errormsgVal=s.toString();
		log("Skipped due to --->" +s.toString());
	}

	private void log(String methodName)
	{
		System.out.println(methodName);
	}

	private void log(IClass testClass) 
	{
		System.out.println(testClass);
	}

	@Override
	public void onConfigurationFailure(ITestResult itr) 
	{
		log("Test '" + itr.getName() + "' FAILED");
		//log("Configuration Failed due to --->" +itr.getThrowable());
		 s= itr.getThrowable();
		 GlobalVariables.errormsgVal=s.toString();
		 log("Configuration Failed due to --->" +s.toString());
		
	}
	
	@Override
	public void onConfigurationSkip(ITestResult itr) 
	{
		log("Test '" + itr.getName() + "' SKIPPED");
		log("Configuration Skipped due to --->" +itr.getThrowable());
		 s= itr.getThrowable();
		 GlobalVariables.errormsgVal=s.toString();
		 log("Configuration Failed due to --->" +s.toString());
	}
	@Override
	public void onConfigurationSuccess(ITestResult itr)
	{
		//log("Test '" + itr.getName() + "' SUCCESS");
		
	}
	
	
}
