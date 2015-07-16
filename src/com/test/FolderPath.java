package com.test;

import java.io.File;

import com.twelvetwenty.base.Base;
import com.twelvetwenty.constants.GlobalVariables;
import com.twelvetwenty.constants.TestBaseConstants;

public class FolderPath
{
public static void main(String[] args)
{
	if(!Base.loadConfig())
	{
		// Load Config file now 
		Base.loadConfig();			
	}
	
	
	

	System.out.println(GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_NUMBER));
 
	
	String path=Base.cleanPath(
			GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_FOLDER_PATH))
			+TestBaseConstants.PATH_SIGN
			+TestBaseConstants.BASELINE_FOLDER_NAME+
			GlobalVariables.CONFIG.getProperty(TestBaseConstants.BASELINE_BUILD_NUMBER);
					
	System.out.println(path);
	File f=new File(path);
	if(f.exists())
	{	
		System.out.println("file exists");
	int i=buildIterationValue(path);
	String va= Integer.toString(i);
	Base.setProperty(TestBaseConstants.BUILD_ITERATION_VALUE, va);
	}

		

	/*String writefolderPath=Base.cleanPath(
			GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_FOLDER_PATH))
			+TestBaseConstants.PATH_SIGN
			+TestBaseConstants.BASELINE_FOLDER_NAME+
			GlobalVariables.CONFIG.getProperty(TestBaseConstants.BASELINE_BUILD_NUMBER)
			+TestBaseConstants.PATH_SIGN+
			TestBaseConstants.ITERATION+
			GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_ITERATION_VALUE)+				
			TestBaseConstants.PATH_SIGN+
			GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_TYPE);
		System.out.println("Build folder path is "+writefolderPath);*/
		
		
		String readFolderPath=Base.cleanPath(
				GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_FOLDER_PATH))
				+TestBaseConstants.PATH_SIGN
				+TestBaseConstants.BASELINE_FOLDER_NAME+
				GlobalVariables.CONFIG.getProperty(TestBaseConstants.BASELINE_BUILD_NUMBER)
				+TestBaseConstants.PATH_SIGN+
				TestBaseConstants.ITERATION+
				GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_ITERATION_VALUE)+				
				TestBaseConstants.PATH_SIGN+
				TestBaseConstants.BASELINE_BUILD_TYPE
				+TestBaseConstants.PATH_SIGN+GlobalVariables.testCaseIdentifier+".xlsx";
		System.out.println("Read folder path is "+readFolderPath);
}

public static int buildIterationValue(String path)
{
	
	int count = new File(path).list().length;
	System.out.println("Number of file : " + count);
	return count;
}



}


/*public static int fetchBuildIterationValue()
{
int iterationValue = 0;	

if(GlobalVariables.buildNumber.equals(null))
{
	iterationValue=1;
	GlobalVariables.buildNumber=GlobalVariables.CONFIG.get(TestBaseConstants.BUILD_NUMBER);
}
else if(GlobalVariables.buildNumber.equals(GlobalVariables.CONFIG.get(TestBaseConstants.BUILD_NUMBER)))
{
	iterationValue++;
}
return iterationValue;	
}*/
