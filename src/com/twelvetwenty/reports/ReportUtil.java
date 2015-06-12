package com.twelvetwenty.reports;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.twelvetwenty.util.TestUtil;





public class ReportUtil {
	public static int scriptNumber = 1;
	public static String indexResultFilename;
	public static String currentDir;
	public static String currentSuiteName;
	public static int tcid;
	// public static String currentSuitePath;

	public static double passNumber;
	public static double failNumber;
	public static boolean newTest = true;
	public static ArrayList<String> description = new ArrayList<String>();
	public static ArrayList<String> keyword = new ArrayList<String>();
	public static ArrayList<String> teststatus = new ArrayList<String>();
	public static ArrayList<String> screenShotPath = new ArrayList<String>();

	public static void startTesting(String filename, String testStartTime,
			String env, String rel,String Bro) {
		indexResultFilename = filename;
		currentDir = indexResultFilename.substring(0,
				indexResultFilename.lastIndexOf("//"));

		FileWriter fstream = null;
		BufferedWriter out = null;
		try {
			// Create file

			fstream = new FileWriter(filename);
			out = new BufferedWriter(fstream);

			String RUN_DATE = TestUtil.now("dd.MMMMM.yyyy").toString();

			String ENVIRONMENT = env;// SeleniumServerTest.ConfigurationMap.getProperty("environment");
			String RELEASE = rel;// SeleniumServerTest.ConfigurationMap.getProperty("release");
			String Browser=Bro;//SeleniumServerTest.ConfigurationMap.getProperty("Browser");

			out.newLine();

			out.write("<html>\n");
			out.write("<HEAD>\n");
			out.write(" <TITLE>Automation Test Results</TITLE>\n");
			out.write("</HEAD>\n");

			out.write("<body>\n");
			out.write("<h4 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u> Automation Test Results</u></b></h4>\n");
			out.write("<table  border=1 cellspacing=1 cellpadding=1 >\n");
			out.write("<tr>\n");

			out.write("<h4> <FONT COLOR=660000 FACE=Arial SIZE=4.5> <u>Test Details :</u></h4>\n");
			out.write("<td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Run Date</b></td>\n");
			out.write("<td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>"
					+ RUN_DATE + "</b></td>\n");
			out.write("</tr>\n");
			out.write("<tr>\n");

			out.write("<td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Run StartTime</b></td>\n");

			out.write("<td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>"
					+ testStartTime + "</b></td>\n");
			out.write("</tr>\n");
			out.write("<tr>\n");
			// out.newLine();
			out.write("<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Run EndTime</b></td>\n");
			out.write("<td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>END_TIME</b></td>\n");
			out.write("</tr>\n");
			out.write("<tr>\n");
			// out.newLine();

			out.write("<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>EnvironmentName</b></td>\n");
			out.write("<td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>"
					+ ENVIRONMENT + "</b></td>\n");
			out.write("</tr>\n");
			out.write("<tr>\n");

			out.write("<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Release</b></td>\n");
			out.write("<td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>"
					+ RELEASE + "</b></td>\n");
			out.write("</tr>\n");
			out.write("</tr>\n");
            out.write("<tr>\n");
            out.write("<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Browser</b></td>\n");
            out.write("<td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>"
                                            + Browser + "</b></td>\n");
			
			out.write("</table>\n");
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		} finally {

			fstream = null;
			out = null;
		}
	}

	public static void startSuite(String suiteName) {

		FileWriter fstream = null;
		BufferedWriter out = null;
		currentSuiteName = suiteName.replaceAll(" ", "_");
		tcid = 1;
		try {
			// build the suite folder
			// currentSuitePath = currentDir;
			// //+"//"+suiteName.replaceAll(" ","_");
			// currentSuiteDir = suiteName.replaceAll(" ","_");
			// File f = new File(currentSuitePath);
			// f.mkdirs();

			fstream = new FileWriter(indexResultFilename, true);
			out = new BufferedWriter(fstream);

			out.write("<h4> <FONT COLOR=660000 FACE= Arial  SIZE=4.5> <u>"
					+ suiteName + " Report :</u></h4>\n");
			out.write("<table  border=1 cellspacing=1 cellpadding=1 width=100%>\n");
			out.write("<tr>\n");
			out.write("<td width=10%  align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial SIZE=2><b>Test Script#</b></td>\n");

			out.write("<td width=40% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial SIZE=2><b>Testcase Id</b></td>\n");
			out.write("<td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial SIZE=2><b>Status</b></td>\n");
			out.write("<td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial SIZE=2><b>Run Start Time</b></td>\n");
			out.write("<td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial SIZE=2><b>Run End Time</b></td>\n");
			out.write("<td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial SIZE=2><b>Logfile</b></td>\n");
			out.write("<td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial SIZE=2><b>Screenshot</b></td>\n");

			out.write("</tr>\n");
			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		} finally {

			fstream = null;
			out = null;
		}
	}

	public static void endSuite() {
		FileWriter fstream = null;
		BufferedWriter out = null;

		try {
			fstream = new FileWriter(indexResultFilename, true);
			out = new BufferedWriter(fstream);
			out.write("</table>\n");
			out.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		} finally {

			fstream = null;
			out = null;
		}

	}

	public static void addTestCase(String testCaseName,
			String testCaseStartTime, String testCaseEndTime, String status, String logfilePath, String screenshotPath) {
		newTest = true;
		FileWriter fstream = null;
		BufferedWriter out = null;

		try {
			newTest = true;

			fstream = new FileWriter(indexResultFilename, true);
			out = new BufferedWriter(fstream);
			// out.newLine();

			out.write("<tr>\n");
			out.write("<td width=10% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>" + scriptNumber + "</b></td>\n");
			out.write("<td width=40% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>" + testCaseName + "</b></td>\n");

			tcid++;
			if (status.startsWith("Pass"))
				out.write("<td width=10% align= center  bgcolor=#BCE954><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
						+ status + "</b></td>\n");
			else if (status.startsWith("Fail"))
				out.write("<td width=10% align= center  bgcolor=Red><FONT COLOR=WHITE FACE= Arial  SIZE=2><b>"
						+ status + "</b></td>\n");
			else if (status.equalsIgnoreCase("Skipped")
					|| status.equalsIgnoreCase("Skip"))
				out.write("<td width=10% align= center  bgcolor=yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>"
						+ status + "</b></td>\n");
			out.write("<td width=20% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>"
					+ testCaseStartTime + "</b></td>\n");
			out.write("<td width=20% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>"
					+ testCaseEndTime + "</b></td>\n");
			out.write("<td width=20% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b><a href='"
					+"file://" +logfilePath + "' target=_blank>Logfile</a></b></td>\n");
			/*if (status.startsWith("Fail"))*/
				out.write("<td width=20% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b><a href='"
					+ "file://"+screenshotPath + "' target=_blank>Screenshot</a></b></td>\n");

			out.write("</tr>\n");

			scriptNumber++;
		} catch (IOException e)
		{
			System.out.println("Could not add test case "+e.getMessage());
			//e.printStackTrace();
			return;
		} finally {
			try {
				out.close();
			} catch (IOException e) 
			{
				System.out.println("IO exception occured"+e.getMessage());
				//e.printStackTrace();
			}
		}

		description = new ArrayList<String>();
		keyword = new ArrayList<String>();
		teststatus = new ArrayList<String>();
		screenShotPath = new ArrayList<String>();
		newTest = false;
	}

	public static void addKeyword(String desc, String key, String stat,
			String path) {

		description.add(desc);
		keyword.add(key);
		teststatus.add(stat);
		screenShotPath.add(path);

	}

	public static void updateEndTime(String endTime) {
		StringBuffer buf = new StringBuffer();
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(indexResultFilename);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			// Read File Line By Line

			while ((strLine = br.readLine()) != null) {

				if (strLine.indexOf("END_TIME") != -1) {
					strLine = strLine.replace("END_TIME", endTime);
				}
				buf.append(strLine);

			}
			// Close the input stream
			in.close();
			//System.out.println(buf);
			FileOutputStream fos = new FileOutputStream(indexResultFilename);
			DataOutputStream output = new DataOutputStream(fos);
			output.writeBytes(buf.toString());
			fos.close();

		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}

}
