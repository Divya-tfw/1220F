package com.test;

import java.io.File;

public class count 
{
	public static void main(String[] args) 
	{
		int i=buildIterationValue("D://QA/Build_number_15");
		System.out.println("Number of file : " + i);
	}
	public static int buildIterationValue(String path)
	{
		
		int count = new File(path).list().length;
		
		return count;
	}
}
