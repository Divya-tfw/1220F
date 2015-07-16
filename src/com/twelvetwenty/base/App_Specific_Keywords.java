package com.twelvetwenty.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.twelvetwenty.constants.GlobalVariables;
import com.twelvetwenty.constants.TestBaseConstants;
import com.twelvetwenty.util.ExcelTestUtil;
import com.twelvetwenty.util.Logs;

/********************************************************************************************
 * Author : DivyaRaju.R LastModifiedDate : 1st may 2015 ClassName :
 * App_Specific_Keywords Description : This class is extended by Keywords class
 * contains methods for working with 12Twenty Application for reading table ,
 * fetching data to excel and validating same
 *
 *********************************************************************************************/

public class App_Specific_Keywords extends Keywords {
	/********************************************************************************************
	 * Author : DivyaRaju.R LastModifiedDate : 1st may 2015 Method name :
	 * loginToSite Description : This method is used for launching 1220
	 * application
	 *
	 *********************************************************************************************/

	public static void loginToSite(String waitVal, String emailAddress,
			String pwd)

	{
		Logs.infoLog("-------------------------------------------------------");
		ExcelTestUtil
				.custReporter("----------------------------------------------");
		// wait for page load
		webdriverWait(waitVal);
		// input email address
		input("txt_EmailAddress", emailAddress, "Enter valid email address");

		// input password
		input("txt_Password", pwd, "Enter valid password");

		// click on login button
		click("btn_Log_in", "Click on login button");

		// wait for page load
		webdriverWait(waitVal);

		// click on data analysis tab
		click("btn_DA", "Click on Data Analysis");
		// click on standard reports btn
		webdriverWait(waitVal);

		if (GlobalVariables.driver.findElement(By.id("modalDialogConfirm"))
				.isDisplayed()) {
			GlobalVariables.driver.findElement(By.id("modalDialogConfirm"))
					.click();
			webdriverWait(waitVal);
			/*
			 * driver.findElement(By.className("calculate")).click();
			 * webdriverWait(waitVal);
			 * driver.findElement(By.id("modalDialogConfirm")).click();
			 */

		}

	}

	/********************************************************************************************
	 * Author : DivyaRaju.R LastModifiedDate : 1st may 2015 Method name :
	 * selectValueFromDropDown Description : This method is used for selecting a
	 * value from drop down
	 *
	 *********************************************************************************************/
	public static void selectValueFromDropDown(WebElement locatorValue,
			String selectionType, String valueToSelect, String msg) {
		try {
			// WebDriverWait wait = new
			// WebDriverWait(GlobalVariables.driver,15);
			if (valueToSelect != null || valueToSelect == "") {

				if (!valueToSelect.equalsIgnoreCase("NA")) {
					// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath((locatorValue.toString()))));
					Select dropdown = new Select(locatorValue);
					// select value based on visible text in DOM of select class
					if (selectionType.equalsIgnoreCase("Text")) {
						GlobalVariables.APPICATION_LOGS.info("Selecting "
								+ locatorValue
								+ " from drop down using text--->"
								+ valueToSelect);
						Logs.infoLog("Selecting " + locatorValue
								+ " from drop down using text--->"
								+ valueToSelect);
						dropdown.selectByVisibleText(valueToSelect);
					}
					// select value based on value in DOM of select class
					else if (selectionType.equalsIgnoreCase("Value")) {
						Logs.infoLog("Selecting " + locatorValue
								+ " from drop down using Value--->"
								+ valueToSelect);
						GlobalVariables.APPICATION_LOGS.info("Selecting "
								+ locatorValue
								+ " from drop down using Value--->"
								+ valueToSelect);
						dropdown.selectByValue(valueToSelect);
					}
					// select value based on index in DOM
					else if (selectionType.equalsIgnoreCase("Index")) {
						Logs.infoLog("Selecting " + locatorValue
								+ " from drop down using Index--->"
								+ valueToSelect);
						GlobalVariables.APPICATION_LOGS.info("Selecting "
								+ locatorValue
								+ " from drop down using Index--->"
								+ valueToSelect);
						int i = Integer.parseInt(valueToSelect);
						dropdown.selectByIndex(i);
					}
				}
				rATUStatus(GlobalVariables.result, msg);
				GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
				GlobalVariables.testusappend = ExcelTestUtil
						.runStatusAdd(GlobalVariables.result);
			} else {
				GlobalVariables.APPICATION_LOGS
						.info("Value is blank.So nothing is selected");
				Logs.infoLog("Value is blank.So nothing is selected");
			}
		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String errmsgV = "Error while executing select drop down keyword."
					+ " Element not found ----> " + valueToSelect
					+ " and its xpath is " + locatorValue;
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, errmsgV);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(errmsgV);
			Logs.errorLog(errmsgV);
			rATUStatus(GlobalVariables.result, msg);
		}
	}

	/********************************************************************************************
	 * Author : DivyaRaju.R LastModifiedDate : 1st may 2015 Method name :
	 * mT1_TH2_TCN_WriteXLSX Description : This method is used for fetching data
	 * from 1220 application and writing that excel
	 *
	 *********************************************************************************************/
	public static void mT1_TH2_TCN_WriteXLSX(String sheetName,
			String excelFileName, String msg, String tableXpath,
			String subReportName) {
		boolean xlFileCreated = false;
		if (subReportName.equalsIgnoreCase("The Graduating Class (A)")
				|| subReportName.equalsIgnoreCase("Timing of First Job Offer")
				|| subReportName.equalsIgnoreCase("Timing of Job Acceptances")) {
			try {//
					// fetch the folder path to create work book
				/*
				 * String
				 * folderPath=cleanPath(GlobalVariables.CONFIG.getProperty
				 * ("buildFolderPath"))+
				 * "/"+"Build_number_"+GlobalVariables.CONFIG
				 * .getProperty("buildNumber")+
				 * "/"+GlobalVariables.CONFIG.getProperty("buildType")+"/";
				 */

				String folderPath = fetchWriteExcelFolderPath();
				/*
				 * cleanPath(
				 * GlobalVariables.CONFIG.getProperty(TestBaseConstants
				 * .BUILD_FOLDER_PATH)) +TestBaseConstants.PATH_SIGN
				 * +TestBaseConstants.BASELINE_FOLDER_NAME+
				 * GlobalVariables.CONFIG
				 * .getProperty(TestBaseConstants.BUILD_NUMBER)
				 * +TestBaseConstants.PATH_SIGN+ TestBaseConstants.ITERATION+
				 * GlobalVariables
				 * .CONFIG.getProperty(TestBaseConstants.BUILD_ITERATION_VALUE)+
				 * TestBaseConstants.PATH_SIGN+
				 * GlobalVariables.CONFIG.getProperty
				 * (TestBaseConstants.BUILD_TYPE);
				 */// System.out.println("Build folder path is "+folderPath);

				File preBuildFolderPath = new File(folderPath);

				// Create directory
				boolean folderCreated = preBuildFolderPath.mkdirs();
				String filePath = preBuildFolderPath + "/"
						+ GlobalVariables.testCaseIdentifier + ".xlsx";
				File filePath1 = new File(filePath);
				// System.out.println("File Path is -->"+filePath);
				if (folderCreated || preBuildFolderPath.exists()) {
					if (filePath1.exists()) {
						filePath1.delete();
						xlFileCreated = ExcelTestUtil.createXLS(filePath,
								GlobalVariables.testCaseIdentifier);
					} else {
						xlFileCreated = ExcelTestUtil.createXLS(filePath,
								GlobalVariables.testCaseIdentifier);
					}
					/*
					 * //System.out.println("Folder created yes or no-->"+success
					 * ); boolean xlFileCreated=
					 * ExcelTestUtil.createXLS(filePath, sheetName);
					 */
					if (xlFileCreated) {

						FileInputStream fis = new FileInputStream(filePath);
						Workbook wb = WorkbookFactory.create(fis);
						// wb.createSheet(year);
						Sheet s = wb.getSheet(sheetName);

						// To locate table.
						WebElement mytable = GlobalVariables.driver
								.findElement(By.xpath(tableXpath));
						// To locate rows of table.
						List<WebElement> rows_table = mytable.findElements(By
								.tagName(TestBaseConstants.TABLE_ROW_TAG));
						// To calculate no of rows In table.
						int rows_count = rows_table.size();

						// headers
						List<WebElement> Columns_header = rows_table
								.get(0)
								.findElements(
										By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						Row r1 = s.createRow(0);

						// Loop for storing header with merge content

						if (subReportName
								.equalsIgnoreCase("The Graduating Class (A)")) {
							Logs.infoLog("Sub report is The Graduating Class (A) ");
							r1.createCell(0).setCellValue(
									Columns_header.get(0).getText());

							r1.createCell(1).setCellValue(
									Columns_header.get(1).getText());
							s.addMergedRegion(new CellRangeAddress(0, 0, 1, 4));

							r1.createCell(5).setCellValue(
									Columns_header.get(2).getText());
							s.addMergedRegion(new CellRangeAddress(0, 0, 5, 8));

							r1.createCell(9).setCellValue(
									Columns_header.get(3).getText());
							s.addMergedRegion(new CellRangeAddress(0, 0, 9, 12));
						}

						else if (subReportName
								.equalsIgnoreCase("Timing of First Job Offer")
								|| subReportName
										.equalsIgnoreCase("Timing of Job Acceptances")) {
							Logs.infoLog("Sub report is " + subReportName);
							r1.createCell(0).setCellValue(
									Columns_header.get(0).getText());
							r1.createCell(1).setCellValue(
									Columns_header.get(1).getText());

							r1.createCell(2).setCellValue(
									Columns_header.get(2).getText());
							s.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));

							r1.createCell(4).setCellValue(
									Columns_header.get(3).getText());
							s.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));
							r1.createCell(6).setCellValue(
									Columns_header.get(4).getText());
							s.addMergedRegion(new CellRangeAddress(0, 0, 6, 7));
						}

						// Loop will execute till the last row of table.
						for (int row = 1; row < rows_count; row++) {
							// To locate columns(cells) of that specific row.
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

							// To calculate no of columns(cells) In that
							// specific row.
							int columns_count = Columns_row.size();
							// System.out.println("Number of cells In Row "+row+" are "+columns_count);
							Row r = s.createRow(row);

							// Loop will execute till the last cell of that
							// specific row.
							for (int column = 0; column < columns_count; column++) {
								// To retrieve text from that specific cell.
								String celtext = Columns_row.get(column)
										.getText();

								// create cell in excel & store value
								r.createCell(column).setCellValue(celtext);
								/*
								 * if(!celtext.isEmpty()) {
								 * System.out.println("Cell Value Of row number "
								 * +
								 * row+" and column number "+column+" Is "+celtext
								 * ); }
								 */

							}
							/*
							 * System.out.println(
							 * "--------------------------------------------------"
							 * );
							 */
						}
						FileOutputStream fos = new FileOutputStream(filePath);
						wb.write(fos);
						fos.close();
					} else {
						System.out.println("File not created");
					}

				} else {
					System.out.println("Folder not created");
				}

				GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
				GlobalVariables.testusappend = ExcelTestUtil
						.runStatusAdd(GlobalVariables.result);
				rATUStatus(GlobalVariables.result, msg);

			} catch (Exception e) {
				GlobalVariables.exceptionMsgVal = e.getMessage();
				String ermsg = "Error while executing T1_TH2_TBH2_TCNkeyword";
				keywordsErrormsg(GlobalVariables.errormsg,
						GlobalVariables.exceptionMsgVal, ermsg);
				GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
				GlobalVariables.testusappend = ExcelTestUtil
						.runStatusAdd(GlobalVariables.result);
				GlobalVariables.APPICATION_LOGS.error(ermsg);
				Logs.errorLog(ermsg);
				rATUStatus(GlobalVariables.result, msg);
			}
		}

	}// mt1-th2-tcnwrite

	/********************************************************************************************
	 * Author : DivyaRaju.R LastModifiedDate : 1st may 2015 Method name :
	 * mT1_TH2_TCN_ReadXLSX Description : This method is used for comparing 1220
	 * app data with stored value of excel
	 *
	 *********************************************************************************************/
	public static void mT1_TH2_TCN_ReadXLSX(String excelSheetName,
			String automationId, String xpath, String subReportName, String msg) {
		GlobalVariables.testCaseIdentifier = automationId;
		try {
			String path = fetchReadExcelFolderPath();
			/*
			 * cleanPath(GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_FOLDER_PATH)) +
			 * TestBaseConstants.PATH_SIGN +
			 * TestBaseConstants.BASELINE_FOLDER_NAME + GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_NUMBER) +
			 * TestBaseConstants.PATH_SIGN + TestBaseConstants.ITERATION +
			 * GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_ITERATION_VALUE) +
			 * TestBaseConstants.PATH_SIGN +
			 * TestBaseConstants.BASELINE_BUILD_TYPE +
			 * TestBaseConstants.PATH_SIGN + GlobalVariables.testCaseIdentifier
			 * + ".xlsx";
			 */
			// System.out.println("Path of file is -->"+path);
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);

			// wb.createSheet(year);
			Sheet s = wb.getSheet(excelSheetName);

			// To locate table.
			WebElement mytable = GlobalVariables.driver.findElement(By
					.xpath(xpath));
			// To locate rows of table.
			List<WebElement> rows_table = mytable.findElements(By
					.tagName(TestBaseConstants.TABLE_ROW_TAG));
			// To calculate no of rows In table.
			int rows_count = rows_table.size();

			// /headers
			List<WebElement> Columns_header = rows_table.get(0).findElements(
					By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

			if (subReportName.equalsIgnoreCase("The Graduating Class (A)"))

			{
				/*
				 * //get 0th cell data from Excel
				 * System.out.println(s.getRow(0).getCell(0)); //get 0th cell
				 * data from header of webtable
				 * System.out.println(Columns_header.get(0).getText());
				 */

				/*
				 * Assert.assertEquals(Columns_header.get(0).getText(),
				 * s.getRow(0).getCell(0).getStringCellValue());
				 */
				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());

				/*
				 * //get 1st cell data from Excel
				 * System.out.println(s.getRow(0).getCell(1)); //get 1st cell
				 * data from webtable
				 * System.out.println(Columns_header.get(1).getText());
				 */

				/*
				 * Assert.assertEquals(Columns_header.get(1).getText(),
				 * s.getRow(0).getCell(1).getStringCellValue());
				 */
				updateError(0, 1, s.getRow(0).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());

				/*
				 * //get 5th cell data from Excel
				 * System.out.println(s.getRow(0).getCell(5)); //get 2nd cell
				 * data from webtable
				 * System.out.println(Columns_header.get(2).getText());
				 */

				/*
				 * Assert.assertEquals(Columns_header.get(2).getText(),
				 * s.getRow(0).getCell(5).getStringCellValue());
				 */

				updateError(0, 2, s.getRow(0).getCell(5).getStringCellValue(),
						Columns_header.get(2).getText());

				/*
				 * //get 9th cell data from Excel
				 * System.out.println(s.getRow(0).getCell(9)); //get 3rd cell
				 * data from webtable
				 * System.out.println(Columns_header.get(3).getText());
				 */

				/*
				 * Assert.assertEquals(Columns_header.get(3).getText(),
				 * s.getRow(0).getCell(9).getStringCellValue());
				 */
				updateError(0, 3, s.getRow(0).getCell(9).getStringCellValue(),
						Columns_header.get(3).getText());

			} else if (subReportName
					.equalsIgnoreCase("Timing of First Job Offer")
					|| subReportName
							.equalsIgnoreCase("Timing of Job Acceptances"))

			{
				/*
				 * //get 0th cell data from Excel
				 * System.out.println(s.getRow(0).getCell(0)); //get 0th cell
				 * data from webtable
				 * System.out.println(Columns_header.get(0).getText());
				 */

				/*
				 * Assert.assertEquals(Columns_header.get(0).getText(),
				 * s.getRow(0).getCell(0).getStringCellValue());
				 */
				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());

				/*
				 * //get 1st cell data from Excel
				 * System.out.println(s.getRow(0).getCell(1)); //get 1st cell
				 * data from webtable
				 * System.out.println(Columns_header.get(1).getText());
				 */

				/*
				 * Assert.assertEquals(Columns_header.get(1).getText(),
				 * s.getRow(0).getCell(1).getStringCellValue());
				 */
				updateError(0, 1, s.getRow(0).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());

				/*
				 * //get 2nd cell data from Excel
				 * System.out.println(s.getRow(0).getCell(2)); //get 2nd cell
				 * data from webtable
				 * System.out.println(Columns_header.get(2).getText());
				 */

				/*
				 * Assert.assertEquals(Columns_header.get(2).getText(),
				 * s.getRow(0).getCell(2).getStringCellValue());
				 */
				updateError(1, 2, s.getRow(0).getCell(2).getStringCellValue(),
						Columns_header.get(2).getText());

				/*
				 * //get 4th cell data from Excel
				 * System.out.println(s.getRow(0).getCell(4)); //get 3rd cell
				 * data from webtable
				 * System.out.println(Columns_header.get(3).getText());
				 */

				/*
				 * Assert.assertEquals(Columns_header.get(3).getText(),
				 * s.getRow(0).getCell(4).getStringCellValue());
				 */
				updateError(0, 4, s.getRow(0).getCell(4).getStringCellValue(),
						Columns_header.get(3).getText());

				// get 6th cell data from Excel
				/*
				 * System.out.println(s.getRow(0).getCell(6)); //get 4th cell
				 * data from webtable
				 * System.out.println(Columns_header.get(4).getText());
				 */

				/*
				 * Assert.assertEquals(Columns_header.get(4).getText(),
				 * s.getRow(0).getCell(6).getStringCellValue());
				 */
				updateError(0, 6, s.getRow(0).getCell(6).getStringCellValue(),
						Columns_header.get(4).getText());

			}

			// Loop will execute till the last row of table.
			for (int row = 1; row < rows_count; row++) {
				// To locate columns(cells) of that specific row.
				List<WebElement> Columns_row = rows_table.get(row)
						.findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
				Row r = s.getRow(row);
				// To calculate no of columns(cells) In that specific row.
				int columns_count = Columns_row.size();
				// System.out.println("Number of cells In Row "+row+" are "+columns_count);
				// Row r=s.createRow(row);

				// Loop will execute till the last cell of that specific row.
				for (int column = 0; column < columns_count; column++) {
					// To retrieve text from that specific cell.
					String webtext = Columns_row.get(column).getText();
					String xltext = r.getCell(column).getStringCellValue();
					/*
					 * System.out.println("Value from web site-->"+webtext);
					 * System.out.println("Value from excel------>"+xltext);
					 */

					updateError(row, column, xltext, webtext);

				}

			}
			FileOutputStream fos = new FileOutputStream(path);
			wb.write(fos);
			fos.close();
			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);
		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing mT1_TH2_TCN_ReadXLSX keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, ermsg);
		}

	}

	/********************************************************************************************
	 * Author : DivyaRaju.R LastModifiedDate : 1st may 2015 Method name :
	 * updateError Description : This method is used for updating error in given
	 * format
	 *
	 *********************************************************************************************/
	public static void updateError(int row, int column, String expected,
			String actual) {
		String msg = "Validating  expected ->" + expected + " with actual->"
				+ actual;

		String status = CustomVerification.assertEqualsTest(actual, expected);
		msg = msg + " and status is --->" + status;
		GlobalVariables.APPICATION_LOGS.info(msg);
		Logs.infoLog(msg);
		if (status.contains(TestBaseConstants.RESULT_FAILVALUE)) {

			GlobalVariables.APPICATION_LOGS
					.error("Validation status is fail hence creating failed folder ");
			String buildFolderpath = cleanPath(GlobalVariables.CONFIG
					.getProperty(TestBaseConstants.BUILD_FOLDER_PATH));
			String buildNumber = GlobalVariables.CONFIG
					.getProperty(TestBaseConstants.BASELINE_BUILD_NUMBER);
			String filePath = buildFolderpath
					+ TestBaseConstants.PATH_SIGN
					+ TestBaseConstants.BASELINE_FOLDER_NAME
					+ buildNumber
					+ TestBaseConstants.PATH_SIGN
					+ TestBaseConstants.ITERATION
					+ GlobalVariables.CONFIG
							.getProperty(TestBaseConstants.BUILD_ITERATION_VALUE)
					+ TestBaseConstants.PATH_SIGN
					+ TestBaseConstants.ACTUAL_BUILD_TYPE
					+ TestBaseConstants.PATH_SIGN;

			File f = new File(filePath);
			System.out.println("File path exists --->" + filePath);
			boolean folder = ExcelTestUtil.createFolder(GlobalVariables.CONFIG
					.getProperty(TestBaseConstants.BUILD_TYPE));
			GlobalVariables.APPICATION_LOGS
					.info("Folder created --->" + folder);
			GlobalVariables.APPICATION_LOGS.info("File path exists--->"
					+ f.exists());
			if (folder || f.exists()) {
				GlobalVariables.APPICATION_LOGS.info("Copying excel file "
						+ GlobalVariables.testCaseIdentifier
						+ TestBaseConstants.EXCEL_FILE_EXTENSION
						+ "to failed file as -->"
						+ GlobalVariables.testCaseIdentifier
						+ TestBaseConstants.FAILED_EXCEL_FILE_NAME
						+ TestBaseConstants.EXCEL_FILE_EXTENSION);

				/*
				 * System.out.println("Source file is " +
				 * GlobalVariables.testCaseIdentifier +
				 * TestBaseConstants.EXCEL_FILE_EXTENSION);
				 * 
				 * System.out.println("Destination file " +
				 * GlobalVariables.testCaseIdentifier +
				 * TestBaseConstants.FAILED_EXCEL_FILE_NAME +
				 * TestBaseConstants.EXCEL_FILE_EXTENSION);
				 */

				ExcelTestUtil.excelFileCopy(GlobalVariables.testCaseIdentifier
						+ TestBaseConstants.EXCEL_FILE_EXTENSION,
						GlobalVariables.testCaseIdentifier
								+ TestBaseConstants.FAILED_EXCEL_FILE_NAME
								+ TestBaseConstants.EXCEL_FILE_EXTENSION);
				// r.createCell(column).setCellValue(xltext);
				// String
				// buildFolderpath=cleanPath(GlobalVariables.CONFIG.getProperty("buildFolderPath"));

				String errorUpdateFolderPath = buildFolderpath
						+ TestBaseConstants.PATH_SIGN
						+ TestBaseConstants.BASELINE_FOLDER_NAME
						+ buildNumber
						+ TestBaseConstants.PATH_SIGN
						+ TestBaseConstants.ITERATION
						+ GlobalVariables.CONFIG
								.getProperty(TestBaseConstants.BUILD_ITERATION_VALUE)
						+ TestBaseConstants.PATH_SIGN
						+ TestBaseConstants.ACTUAL_BUILD_TYPE
						+ TestBaseConstants.PATH_SIGN
						+ TestBaseConstants.FAILED_FOLDER_NAME
						+ TestBaseConstants.PATH_SIGN;
				GlobalVariables.APPICATION_LOGS.info("Error folder path is -->"
						+ errorUpdateFolderPath);
				GlobalVariables.APPICATION_LOGS
						.info("Setting data path with data to be appended-->"
								+ errorUpdateFolderPath
								+ GlobalVariables.testCaseIdentifier
								+ TestBaseConstants.FAILED_EXCEL_FILE_NAME
								+ TestBaseConstants.EXCEL_FILE_EXTENSION
								+ GlobalVariables.testCaseIdentifier + row
								+ column + expected + "**" + actual);
				ExcelTestUtil.setExcelData(errorUpdateFolderPath
						+ GlobalVariables.testCaseIdentifier
						+ TestBaseConstants.FAILED_EXCEL_FILE_NAME
						+ TestBaseConstants.EXCEL_FILE_EXTENSION,
						GlobalVariables.testCaseIdentifier, row, column,
						expected + "**" + actual);

				GlobalVariables.APPICATION_LOGS.info("Setting data to excel."
						+ expected + "**" + actual);

				/*
				 * ExcelTestUtil.createXLS(errorUpdateFolderPath+
				 * GlobalVariables.testCaseIdentifier+
				 * TestBaseConstants.FAILED_EXCEL_FILE_NAME+
				 * TestBaseConstants.EXCEL_FILE_EXTENSION,
				 * GlobalVariables.testCaseIdentifier);
				 */

			}
		}

	}

	/********************************************************************************************
	 * Author : DivyaRaju.R LastModifiedDate : 1st may 2015 Method name :
	 * mT1_TH2_TBH2_TCN_WriteXLSX Description : This method is used for fetching
	 * data from 1220 application and writing that excel
	 *
	 *********************************************************************************************/

	public static void mT1_TH2_TBH2_TCN_WriteXLSX(String sheetName,
			String excelFileName, String msg, String tableXpath,
			String subReportName) {
		boolean xlFileCreated = false;

		try {// fetch the folder path to create work book

			String folderPath = fetchWriteExcelFolderPath();
			/*
			 * cleanPath( GlobalVariables.CONFIG.getProperty(TestBaseConstants.
			 * BUILD_FOLDER_PATH)) +TestBaseConstants.PATH_SIGN
			 * +TestBaseConstants.BASELINE_FOLDER_NAME+
			 * GlobalVariables.CONFIG.getProperty
			 * (TestBaseConstants.BUILD_NUMBER) +TestBaseConstants.PATH_SIGN+
			 * TestBaseConstants.ITERATION+
			 * GlobalVariables.CONFIG.getProperty(TestBaseConstants
			 * .BUILD_ITERATION_VALUE)+ TestBaseConstants.PATH_SIGN+
			 * GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_TYPE);
			 */
			// System.out.println("Build folder path is "+folderPath);

			File preBuildFolderPath = new File(folderPath);

			// Create directory
			boolean folderCreated = preBuildFolderPath.mkdirs();
			String filePath = preBuildFolderPath + "/"
					+ GlobalVariables.testCaseIdentifier + ".xlsx";
			File filePath1 = new File(filePath);
			// System.out.println("File Path is -->"+filePath);
			if (folderCreated || preBuildFolderPath.exists()) {
				if (filePath1.exists()) {
					filePath1.delete();
					xlFileCreated = ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);
				} else {
					xlFileCreated = ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);
				}

				if (xlFileCreated) {

					if (subReportName
							.equalsIgnoreCase(TestBaseConstants.NALP_EMPLOYMENT_STATUS_BY_GENDER)) {

						// fetch table xpath
						WebElement mytable = GlobalVariables.driver
								.findElement(By.xpath(tableXpath));
						// To locate rows of table.
						List<WebElement> rows_table = mytable.findElements(By
								.tagName(TestBaseConstants.TABLE_ROW_TAG));

						// open stream to open file
						FileInputStream fis = new FileInputStream(filePath);
						Workbook wb = WorkbookFactory.create(fis);
						// get row size
						int rows_count = rows_table.size();
						Sheet s = wb
								.getSheet(GlobalVariables.testCaseIdentifier);

						/*** Fetch headers and store to excel of 1st col **/
						// first row of header
						List<WebElement> Columns_header = rows_table
								.get(0)
								.findElements(
										By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						// create first row
						Row r1 = s.createRow(0);
						String j = Columns_header.get(0).getText();
						// add 1st cell value
						r1.createCell(0).setCellValue(j);
						// merge the text based on rowspan or colspan
						s.addMergedRegion(new CellRangeAddress(0, 1, 0, 1));

						// add value to cell 2
						r1.createCell(2).setCellValue(
								Columns_header.get(1).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));

						// add value to cell 3
						r1.createCell(4).setCellValue(
								Columns_header.get(2).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));

						// second row of header
						r1 = s.createRow(1);

						// fetch 2nd row contents
						Columns_header = rows_table.get(1).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						int cellVal = 2;
						int colVal = 0;
						// enter values to cols 2 to 6
						while (cellVal < Columns_header.size() + 2
								&& colVal < Columns_header.size()) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;
						}
						// first row span content

						Columns_header = rows_table.get(2).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						r1 = s.createRow(2);

						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());
						s.addMergedRegion(new CellRangeAddress(2, 7, 0, 0));
						// System.out.println(Columns_header.get(1).getText());
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());
						Columns_header = rows_table.get(2).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
						cellVal = 2;
						colVal = 0;
						while (cellVal < Columns_header.size() + 2
								&& colVal < Columns_header.size()) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;

						}

						// second rows pan content

						Columns_header = rows_table.get(8).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						r1 = s.createRow(8);
						// System.out.println(Columns_header.get(0).getText());
						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());
						s.addMergedRegion(new CellRangeAddress(8, 11, 0, 0));

						// System.out.println(Columns_header.get(1).getText());
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());
						Columns_header = rows_table.get(8).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
						cellVal = 2;
						colVal = 0;
						while (cellVal < Columns_header.size() + 2
								&& colVal < Columns_header.size()) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;

						}

						// third colspan content
						Columns_header = rows_table.get(12).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						r1 = s.createRow(12);

						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());
						s.addMergedRegion(new CellRangeAddress(12, 12, 0, 1));
						Columns_header = rows_table.get(12).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
						cellVal = 2;
						colVal = 0;
						while (cellVal < Columns_header.size() + 2
								&& colVal < Columns_header.size()) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;

						}

						// Fetch the contents of table from row 3 to last 7th
						// row
						for (int row = 3; row < 8; row++) {
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

							Row r = s.createRow(row);
							String celtext = Columns_row.get(0).getText();

							r.createCell(1).setCellValue(celtext);

							// To locate columns(cells) of that specific row.
							Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
							cellVal = 2;
							colVal = 0;
							while (cellVal < Columns_row.size() + 2
									&& colVal < Columns_row.size()) {
								r.createCell(cellVal).setCellValue(
										Columns_row.get(colVal).getText());
								cellVal++;
								colVal++;

							}
						}
						// Fetch the contents of table from row 8 to last 12th
						// row
						for (int row = 9; row < rows_count - 1; row++) {
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

							Row r = s.createRow(row);
							String celtext = Columns_row.get(0).getText();

							r.createCell(1).setCellValue(celtext);

							Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
							cellVal = 2;
							colVal = 0;
							while (cellVal < Columns_row.size() + 2
									&& colVal < Columns_row.size()) {
								r.createCell(cellVal).setCellValue(
										Columns_row.get(colVal).getText());
								cellVal++;
								colVal++;
							}
						}

						FileOutputStream fos = new FileOutputStream(filePath);
						wb.write(fos);
						fos.close();
					} // Employment Status by Gender report
						//
					else if (subReportName
							.equalsIgnoreCase(TestBaseConstants.NALP_EMPLOYMENT_STATUS_BY_AGE_AT_GRADUATION)
							|| subReportName
									.equalsIgnoreCase(TestBaseConstants.NALP_EMPLOYMENT_STATUS_BY_RACE_OR_ETHNICITY)) {// Employment
																														// Status
																														// by
																														// Age
																														// at
																														// Graduation
																														// fetch
																														// table
																														// xpath
						WebElement mytable = GlobalVariables.driver
								.findElement(By.xpath(tableXpath));
						// To locate rows of table.
						List<WebElement> rows_table = mytable.findElements(By
								.tagName(TestBaseConstants.TABLE_ROW_TAG));
						// open stream to open file
						FileInputStream fis = new FileInputStream(filePath);
						Workbook wb = WorkbookFactory.create(fis);
						// get row size
						int rows_count = rows_table.size();
						Sheet s = wb
								.getSheet(GlobalVariables.testCaseIdentifier);
						List<WebElement> Columns_header = rows_table
								.get(0)
								.findElements(
										By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						// create first row
						Row r1 = s.createRow(0);
						String j = Columns_header.get(0).getText();
						// add 1st cell value
						r1.createCell(0).setCellValue(j);
						// merge the text based on rowspan or colspan
						s.addMergedRegion(new CellRangeAddress(0, 1, 0, 1));

						// add value to cell 2
						r1.createCell(2).setCellValue(
								Columns_header.get(1).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));

						// add value to cell 3
						r1.createCell(4).setCellValue(
								Columns_header.get(2).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));

						// add value to cell 4
						r1.createCell(6).setCellValue(
								Columns_header.get(3).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 6, 7));
						// add value to cell 5
						r1.createCell(8).setCellValue(
								Columns_header.get(4).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 8, 9));

						// add value to cell 6
						r1.createCell(10).setCellValue(
								Columns_header.get(5).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 10, 11));

						// second row of header
						r1 = s.createRow(1);

						// fetch 2nd row contents
						Columns_header = rows_table.get(1).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						int cellVal = 2;
						int colVal = 0;
						// enter values to cols 2 to 12
						while (cellVal < 12 && colVal < 10) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;
						}

						// 3rd row-first col span content i.e fetch Employed
						// value

						Columns_header = rows_table.get(2).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						r1 = s.createRow(2);

						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());
						s.addMergedRegion(new CellRangeAddress(2, 7, 0, 0));
						// System.out.println(Columns_header.get(1).getText());
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());
						Columns_header = rows_table.get(2).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
						cellVal = 2;
						colVal = 0;
						while (cellVal < 12 && colVal < 10) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;

						}
						// second row span content

						Columns_header = rows_table.get(8).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						r1 = s.createRow(8);
						// System.out.println(Columns_header.get(0).getText());
						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());
						s.addMergedRegion(new CellRangeAddress(8, 11, 0, 0));

						// System.out.println(Columns_header.get(1).getText());
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());
						Columns_header = rows_table.get(8).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
						cellVal = 2;
						colVal = 0;
						while (cellVal < 12 && colVal < 10) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;

						}

						// third col span content
						Columns_header = rows_table.get(12).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						r1 = s.createRow(12);

						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());
						s.addMergedRegion(new CellRangeAddress(12, 12, 0, 1));
						Columns_header = rows_table.get(12).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
						cellVal = 2;
						colVal = 0;
						while (cellVal < 12 && colVal < 10) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;

						}

						for (int row = 3; row < 8; row++) {
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

							Row r = s.createRow(row);
							String celtext = Columns_row.get(0).getText();

							r.createCell(1).setCellValue(celtext);

							Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
							cellVal = 2;
							colVal = 0;
							while (cellVal < 12 && colVal < 10) {
								r.createCell(cellVal).setCellValue(
										Columns_row.get(colVal).getText());
								cellVal++;
								colVal++;

							}

						}
						// Fetch the contents of table from row 8 to last 12th
						// row
						for (int row = 9; row < rows_count - 1; row++) {
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

							Row r = s.createRow(row);
							String celtext = Columns_row.get(0).getText();

							r.createCell(1).setCellValue(celtext);

							Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
							cellVal = 2;
							colVal = 0;
							while (cellVal < 12 && colVal < 10) {
								r.createCell(cellVal).setCellValue(
										Columns_row.get(colVal).getText());
								cellVal++;
								colVal++;

							}

						}

						FileOutputStream fos = new FileOutputStream(filePath);
						wb.write(fos);
						fos.close();

					}// Employment Status by Age at

					else if (subReportName
							.equalsIgnoreCase(TestBaseConstants.NALP_EMPLOYEE_DETAIL_BY_RACE_OR_ETHINICITY)) {
						WebElement mytable = GlobalVariables.driver
								.findElement(By.xpath(tableXpath));
						// To locate rows of table.
						List<WebElement> rows_table = mytable.findElements(By
								.tagName(TestBaseConstants.TABLE_ROW_TAG));
						// System.out.println("row count is -->"+rows_table.size());
						// open stream to open file
						FileInputStream fis = new FileInputStream(filePath);
						Workbook wb = WorkbookFactory.create(fis);
						// get row size
						// int rows_count = rows_table.size();
						Sheet s = wb
								.getSheet(GlobalVariables.testCaseIdentifier);

						List<WebElement> Columns_header = rows_table
								.get(0)
								.findElements(
										By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						// System.out.println("Header count is -->"+Columns_header.size());

						// create first row
						Row r1 = s.createRow(0);
						String j = Columns_header.get(0).getText();
						r1.createCell(0).setCellValue(j);
						// merge the text based on rowspan or colspan
						s.addMergedRegion(new CellRangeAddress(0, 1, 0, 1));

						// add value to cell 2
						r1.createCell(2).setCellValue(
								Columns_header.get(1).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));

						// add value to cell 3
						r1.createCell(4).setCellValue(
								Columns_header.get(2).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));

						// add value to cell 4
						r1.createCell(6).setCellValue(
								Columns_header.get(3).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 6, 7));

						// add value to cell 5
						r1.createCell(8).setCellValue(
								Columns_header.get(4).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 8, 9));

						// add value to cell 6
						r1.createCell(10).setCellValue(
								Columns_header.get(5).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 10, 11));

						// second row of header
						r1 = s.createRow(1);

						// fetch 2nd row contents
						Columns_header = rows_table.get(1).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

						// System.out.println(Columns_header.size());
						int cellVal = 2;
						int colVal = 0;
						// enter values to cols 2 to 12
						while (cellVal < 12 && colVal < 10) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;
						}

						// 3rd row-first row span content i.e fetch Employed
						// value

						Columns_header = rows_table.get(2).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						r1 = s.createRow(2);

						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());
						s.addMergedRegion(new CellRangeAddress(2, 5, 0, 0));
						// System.out.println(Columns_header.get(1).getText());
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());
						Columns_header = rows_table.get(2).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
						cellVal = 2;
						colVal = 0;
						while (cellVal < 12 && colVal < 10) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;

						}

						// second row pan content

						Columns_header = rows_table.get(6).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						r1 = s.createRow(6);
						// System.out.println(Columns_header.get(0).getText());
						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());
						s.addMergedRegion(new CellRangeAddress(6, 16, 0, 0));

						// System.out.println(Columns_header.get(1).getText());
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());
						Columns_header = rows_table.get(6).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
						cellVal = 2;
						colVal = 0;
						while (cellVal < 12 && colVal < 10) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;

						}

						// 18th row with no row or col span
						Columns_header = rows_table.get(17).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						r1 = s.createRow(17);

						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());
						Columns_header = rows_table.get(17).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
						cellVal = 2;
						colVal = 0;
						while (cellVal < 12 && colVal < 10) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;

						}

						// third row span content
						Columns_header = rows_table.get(18).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						r1 = s.createRow(18);

						// System.out.println(Columns_header.size());
						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());
						s.addMergedRegion(new CellRangeAddress(18, 26, 0, 0));
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());
						Columns_header = rows_table.get(18).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
						cellVal = 2;
						colVal = 0;
						while (cellVal < 12 && colVal < 10) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;

						}

						// fourth row span content
						Columns_header = rows_table.get(27).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						r1 = s.createRow(27);
						// System.out.println(Columns_header.size());
						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());
						s.addMergedRegion(new CellRangeAddress(27, 31, 0, 0));
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());

						Columns_header = rows_table.get(27).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
						cellVal = 2;
						colVal = 0;
						while (cellVal < 12 && colVal < 10) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;

						}

						// Fetch the contents of table from row 3 to last 7th
						// row
						for (int row = 3; row < 6; row++) {
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

							Row r = s.createRow(row);
							String celtext = Columns_row.get(0).getText();

							r.createCell(1).setCellValue(celtext);

							// To locate columns(cells) of that specific row.
							Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

							cellVal = 2;
							colVal = 0;
							while (cellVal < 12 && colVal < 10) {
								r.createCell(cellVal).setCellValue(
										Columns_row.get(colVal).getText());
								cellVal++;
								colVal++;

							}
						}

						// fetch contents of second block
						for (int row = 7; row < 17; row++) {
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

							Row r = s.createRow(row);
							String celtext = Columns_row.get(0).getText();

							r.createCell(1).setCellValue(celtext);

							// To locate columns(cells) of that specific row.
							Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

							cellVal = 2;
							colVal = 0;
							while (cellVal < 12 && colVal < 10) {
								r.createCell(cellVal).setCellValue(
										Columns_row.get(colVal).getText());
								cellVal++;
								colVal++;

							}
						}

						// fetch contents of 4th block
						for (int row = 19; row < 27; row++) {
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

							Row r = s.createRow(row);
							String celtext = Columns_row.get(0).getText();

							r.createCell(1).setCellValue(celtext);

							// To locate columns(cells) of that specific row.
							Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
							cellVal = 2;
							colVal = 0;
							while (cellVal < 12 && colVal < 10) {
								r.createCell(cellVal).setCellValue(
										Columns_row.get(colVal).getText());
								cellVal++;
								colVal++;

							}
						}

						// fetch contents of 5th block
						for (int row = 28; row < 32; row++) {
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

							Row r = s.createRow(row);
							String celtext = Columns_row.get(0).getText();

							r.createCell(1).setCellValue(celtext);

							// To locate columns(cells) of that specific row.
							Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
							cellVal = 2;
							colVal = 0;
							while (cellVal < 12 && colVal < 10) {
								r.createCell(cellVal).setCellValue(
										Columns_row.get(colVal).getText());
								cellVal++;
								colVal++;

							}
						}

						FileOutputStream fos = new FileOutputStream(filePath);
						wb.write(fos);
						fos.close();
					}// Employer Detail by Race/Ethnicity

					else if (subReportName
							.equalsIgnoreCase(TestBaseConstants.NALP_EMPLOYER_DETAIL_BY_GENDER)) {
						WebElement mytable = GlobalVariables.driver
								.findElement(By.xpath(tableXpath));
						// To locate rows of table.
						List<WebElement> rows_table = mytable.findElements(By
								.tagName(TestBaseConstants.TABLE_ROW_TAG));
						// System.out.println("row count is -->"+rows_table.size());
						// open stream to open file
						FileInputStream fis = new FileInputStream(filePath);
						Workbook wb = WorkbookFactory.create(fis);
						// get row size
						// int rows_count = rows_table.size();
						Sheet s = wb
								.getSheet(GlobalVariables.testCaseIdentifier);

						List<WebElement> Columns_header = rows_table
								.get(0)
								.findElements(
										By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						// System.out.println("Header count is -->"+Columns_header.size());

						// create first row
						Row r1 = s.createRow(0);
						String j = Columns_header.get(0).getText();
						r1.createCell(0).setCellValue(j);
						// merge the text based on rowspan or colspan
						s.addMergedRegion(new CellRangeAddress(0, 1, 0, 1));

						// add value to cell 2
						r1.createCell(2).setCellValue(
								Columns_header.get(1).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 2, 3));

						// add value to cell 3
						r1.createCell(4).setCellValue(
								Columns_header.get(2).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 4, 5));

						// add value to cell 4
						r1.createCell(6).setCellValue(
								Columns_header.get(3).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 6, 7));

						// second row of header
						r1 = s.createRow(1);

						// fetch 2nd row contents
						Columns_header = rows_table.get(1).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

						// System.out.println(Columns_header.size());
						int cellVal = 2;
						int colVal = 0;
						// enter values to cols 2 to 7
						while (cellVal < Columns_header.size() + 2
								&& colVal < Columns_header.size()) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;
						}

						// 3rd row-first row span content i.e fetch Employed
						// value

						Columns_header = rows_table.get(2).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						r1 = s.createRow(2);

						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());
						s.addMergedRegion(new CellRangeAddress(2, 5, 0, 0));
						// System.out.println(Columns_header.get(1).getText());
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());
						Columns_header = rows_table.get(2).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
						cellVal = 2;
						colVal = 0;
						while (cellVal < Columns_header.size() + 2
								&& colVal < Columns_header.size()) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;

						}

						// second row pan content

						Columns_header = rows_table.get(6).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						r1 = s.createRow(6);
						// System.out.println(Columns_header.get(0).getText());
						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());
						s.addMergedRegion(new CellRangeAddress(6, 16, 0, 0));

						// System.out.println(Columns_header.get(1).getText());
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());
						Columns_header = rows_table.get(6).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
						cellVal = 2;
						colVal = 0;
						while (cellVal < Columns_header.size() + 2
								&& colVal < Columns_header.size()) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;

						}

						// 18th row with no row or col span
						Columns_header = rows_table.get(17).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						r1 = s.createRow(17);

						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());
						Columns_header = rows_table.get(17).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
						cellVal = 2;
						colVal = 0;
						while (cellVal < Columns_header.size() + 2
								&& colVal < Columns_header.size()) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;

						}

						// third row span content
						Columns_header = rows_table.get(18).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						r1 = s.createRow(18);

						// System.out.println(Columns_header.size());
						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());
						s.addMergedRegion(new CellRangeAddress(18, 26, 0, 0));
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());
						Columns_header = rows_table.get(18).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
						cellVal = 2;
						colVal = 0;
						while (cellVal < Columns_header.size() + 2
								&& colVal < Columns_header.size()) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;

						}

						// fourth row span content
						Columns_header = rows_table.get(27).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						r1 = s.createRow(27);
						// System.out.println(Columns_header.size());
						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());
						s.addMergedRegion(new CellRangeAddress(27, 31, 0, 0));
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());

						Columns_header = rows_table.get(27).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
						cellVal = 2;
						colVal = 0;
						while (cellVal < Columns_header.size() + 2
								&& colVal < Columns_header.size()) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;

						}

						// Fetch the contents of table from row 3 to last 7th
						// row
						for (int row = 3; row < 6; row++) {
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

							Row r = s.createRow(row);
							String celtext = Columns_row.get(0).getText();

							r.createCell(1).setCellValue(celtext);

							// To locate columns(cells) of that specific row.
							Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

							cellVal = 2;
							colVal = 0;
							while (cellVal < Columns_header.size() + 2
									&& colVal < Columns_header.size()) {
								r.createCell(cellVal).setCellValue(
										Columns_row.get(colVal).getText());
								cellVal++;
								colVal++;

							}
						}

						// fetch contents of second block
						for (int row = 7; row < 17; row++) {
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

							Row r = s.createRow(row);
							String celtext = Columns_row.get(0).getText();

							r.createCell(1).setCellValue(celtext);

							// To locate columns(cells) of that specific row.
							Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

							cellVal = 2;
							colVal = 0;
							while (cellVal < Columns_header.size() + 2
									&& colVal < Columns_header.size()) {
								r.createCell(cellVal).setCellValue(
										Columns_row.get(colVal).getText());
								cellVal++;
								colVal++;

							}
						}

						// fetch contents of 4th block
						for (int row = 19; row < 27; row++) {
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

							Row r = s.createRow(row);
							String celtext = Columns_row.get(0).getText();

							r.createCell(1).setCellValue(celtext);

							// To locate columns(cells) of that specific row.
							Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
							cellVal = 2;
							colVal = 0;
							while (cellVal < Columns_header.size() + 2
									&& colVal < Columns_header.size()) {
								r.createCell(cellVal).setCellValue(
										Columns_row.get(colVal).getText());
								cellVal++;
								colVal++;

							}
						}

						// fetch contents of 5th block
						for (int row = 28; row < 32; row++) {
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

							Row r = s.createRow(row);
							String celtext = Columns_row.get(0).getText();

							r.createCell(1).setCellValue(celtext);

							// To locate columns(cells) of that specific row.
							Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
							cellVal = 2;
							colVal = 0;
							while (cellVal < Columns_header.size() + 2
									&& colVal < Columns_header.size()) {
								r.createCell(cellVal).setCellValue(
										Columns_row.get(colVal).getText());
								cellVal++;
								colVal++;

							}
						}

						FileOutputStream fos = new FileOutputStream(filePath);
						wb.write(fos);
						fos.close();
					}// Employer Detail by Gender

				}// excel file created

				else {
					// System.out.println("File not created");
					GlobalVariables.APPICATION_LOGS.error("File not created");
					Logs.errorLog("File not created");
				}

			} else {
				// System.out.println("Folder not created");
				GlobalVariables.APPICATION_LOGS.error("Folder not created");
				Logs.errorLog("Folder not created");
			}

			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);

		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing mT1_TH2_TBH2_TCN_write keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, msg);
		}

	}

	/********************************************************************************************
	 * Author : DivyaRaju.R LastModifiedDate : 1st may 2015 Method name :
	 * mT1_TH2_TBH2_TCN_ReadXLSX Description : This method is used for fetching
	 * data from 1220 application and writing that excel
	 *
	 *********************************************************************************************/

	public static void mT1_TH2_TBH2_TCN_ReadXLSX(String excelSheetName,
			String automationId, String xpath, String subReportName, String msg) {
		GlobalVariables.testCaseIdentifier = automationId;
		try {
			String path = fetchReadExcelFolderPath();

			/*
			 * cleanPath(GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_FOLDER_PATH)) +
			 * TestBaseConstants.PATH_SIGN +
			 * TestBaseConstants.BASELINE_FOLDER_NAME + GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_NUMBER) +
			 * TestBaseConstants.PATH_SIGN + TestBaseConstants.ITERATION +
			 * GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_ITERATION_VALUE) +
			 * TestBaseConstants.PATH_SIGN +
			 * TestBaseConstants.BASELINE_BUILD_TYPE +
			 * TestBaseConstants.PATH_SIGN + GlobalVariables.testCaseIdentifier
			 * + ".xlsx";
			 */
			// System.out.println("Path of file is -->"+path);
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);

			// wb.createSheet(year);
			Sheet s = wb.getSheet(excelSheetName);

			if (subReportName
					.equalsIgnoreCase(TestBaseConstants.NALP_EMPLOYMENT_STATUS_BY_GENDER))

			{
				WebElement mytable = GlobalVariables.driver.findElement(By
						.xpath(xpath));
				// To locate rows of table.
				List<WebElement> rows_table = mytable.findElements(By
						.tagName(TestBaseConstants.TABLE_ROW_TAG));
				// To calculate no of rows In table.
				int rows_count = rows_table.size();

				// /headers
				List<WebElement> Columns_header = rows_table.get(0)
						.findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				// Validating first row headers and cell contents

				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				updateError(0, 2, s.getRow(0).getCell(2).getStringCellValue(),
						Columns_header.get(1).getText());
				updateError(0, 4, s.getRow(0).getCell(4).getStringCellValue(),
						Columns_header.get(2).getText());

				// 2nd row
				Columns_header = rows_table.get(1).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				int webText = 0;
				int excelTextcel = 2;
				while (webText < Columns_header.size()
						&& excelTextcel < Columns_header.size() + 2) {/*
																	 * System.out
																	 * .println(
																	 * Columns_header
																	 * .
																	 * get(webText
																	 * )
																	 * .getText(
																	 * ));
																	 * System
																	 * .out
																	 * .println
																	 * (s
																	 * .getRow(
																	 * 1)
																	 * .getCell
																	 * (
																	 * excelTextcel
																	 * ).
																	 * getStringCellValue
																	 * ());
																	 */
					updateError(0, excelTextcel,
							s.getRow(1).getCell(excelTextcel)
									.getStringCellValue(),
							Columns_header.get(webText).getText());
					webText++;
					excelTextcel++;
				}

				// 3rd row
				Columns_header = rows_table.get(2).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				updateError(2, 0, s.getRow(2).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				updateError(2, 1, s.getRow(2).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());
				Columns_header = rows_table.get(2).findElements(
						By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

				webText = 0;
				excelTextcel = 2;
				while (webText < Columns_header.size()
						&& excelTextcel < Columns_header.size() + 2) {
					/*
					 * System.out.println(Columns_header.get(webText).getText());
					 * System.out.println(s.getRow(2).getCell(excelTextcel).
					 * getStringCellValue());
					 */
					updateError(2, webText, s.getRow(2).getCell(excelTextcel)
							.getStringCellValue(), Columns_header.get(webText)
							.getText());

					webText++;
					excelTextcel++;
				}

				// 2nd row span

				Columns_header = rows_table.get(8).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				updateError(8, 0, s.getRow(8).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				updateError(8, 1, s.getRow(8).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());
				Columns_header = rows_table.get(8).findElements(
						By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
				webText = 0;
				excelTextcel = 2;
				while (webText < Columns_header.size()
						&& excelTextcel < Columns_header.size() + 2) {
					/*
					 * System.out.println(Columns_header.get(webText).getText());
					 * System.out.println(s.getRow(8).getCell(excelTextcel).
					 * getStringCellValue());
					 */
					updateError(8, webText, s.getRow(8).getCell(excelTextcel)
							.getStringCellValue(), Columns_header.get(webText)
							.getText());

					webText++;
					excelTextcel++;
				}

				// third colspan content

				Columns_header = rows_table.get(12).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				/*
				 * System.out.println(Columns_header.get(0).getText());
				 * System.out
				 * .println(s.getRow(12).getCell(0).getStringCellValue());
				 */

				updateError(12, 0,
						s.getRow(12).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				Columns_header = rows_table.get(12).findElements(
						By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
				webText = 0;
				excelTextcel = 2;
				while (webText < Columns_header.size()
						&& excelTextcel < Columns_header.size() + 2) {
					/*
					 * System.out.println(Columns_header.get(webText).getText());
					 * System.out.println(s.getRow(12).getCell(excelTextcel).
					 * getStringCellValue());
					 */

					updateError(12, webText, s.getRow(12).getCell(excelTextcel)
							.getStringCellValue(), Columns_header.get(webText)
							.getText());
					webText++;
					excelTextcel++;
				}
				for (int row = 3; row < 8; row++) {
					List<WebElement> Columns_row = rows_table
							.get(row)
							.findElements(
									By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
					/*
					 * System.out.println("Web table header row is "+row+
					 * " and its content is " +Columns_row.get(0).getText());
					 * System.out.println("Excel table header row is "+row+
					 * " and its content is "
					 * +s.getRow(row).getCell(1).getStringCellValue());
					 */
					updateError(row, 1, s.getRow(row).getCell(1)
							.getStringCellValue(), Columns_row.get(0).getText());
					Columns_row = rows_table.get(row).findElements(
							By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
					// System.out.println(Columns_row.size());
					webText = 0;
					excelTextcel = 2;
					while (webText < Columns_row.size()
							&& excelTextcel < Columns_row.size() + 2) {
						/*
						 * System.out.println("Web table content "+row+" value is "
						 * +Columns_header.get(webText).getText());
						 * System.out.println
						 * ("Excel table content "+row+" value is "
						 * +s.getRow(row)
						 * .getCell(excelTextcel).getStringCellValue());
						 */
						updateError(row, 1, s.getRow(row).getCell(excelTextcel)
								.getStringCellValue(), Columns_row.get(webText)
								.getText());
						webText++;
						excelTextcel++;
					}
				}

				for (int row = 9; row < rows_count - 1; row++) {
					List<WebElement> Columns_row = rows_table
							.get(row)
							.findElements(
									By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
					updateError(row, 1, s.getRow(row).getCell(1)
							.getStringCellValue(), Columns_row.get(0).getText());

					Columns_row = rows_table.get(row).findElements(
							By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
					webText = 0;
					excelTextcel = 2;
					while (webText < Columns_row.size()
							&& excelTextcel < Columns_row.size() + 2) {
						updateError(row, excelTextcel,
								s.getRow(row).getCell(excelTextcel)
										.getStringCellValue(),
								Columns_row.get(webText).getText());
						webText++;
						excelTextcel++;
					}
				}

			}

			else if (subReportName
					.equalsIgnoreCase(TestBaseConstants.NALP_EMPLOYMENT_STATUS_BY_AGE_AT_GRADUATION)
					|| subReportName
							.equalsIgnoreCase(TestBaseConstants.NALP_EMPLOYMENT_STATUS_BY_RACE_OR_ETHNICITY)) {

				WebElement mytable = GlobalVariables.driver.findElement(By
						.xpath(xpath));
				// To locate rows of table.
				List<WebElement> rows_table = mytable.findElements(By
						.tagName(TestBaseConstants.TABLE_ROW_TAG));
				// To calculate no of rows In table.
				int rows_count = rows_table.size();

				// /headers
				List<WebElement> Columns_header = rows_table.get(0)
						.findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				// Validating first row headers and cell contents
				// compare 1st cell
				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				// compare 2nd cell
				updateError(0, 2, s.getRow(0).getCell(2).getStringCellValue(),
						Columns_header.get(1).getText());
				// compare 3rd cell
				updateError(0, 4, s.getRow(0).getCell(4).getStringCellValue(),
						Columns_header.get(2).getText());
				// compare 4th cell
				updateError(0, 6, s.getRow(0).getCell(6).getStringCellValue(),
						Columns_header.get(3).getText());
				// compare 5th cell
				updateError(0, 8, s.getRow(0).getCell(8).getStringCellValue(),
						Columns_header.get(4).getText());
				// compare 6th cell
				updateError(0, 10,
						s.getRow(0).getCell(10).getStringCellValue(),
						Columns_header.get(5).getText());
				// fetch 2nd row contents
				Columns_header = rows_table.get(1).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				int cellVal = 2;
				int colVal = 0;
				// Get values from cols 2 to 12
				while (cellVal < 12 && colVal < 10) {
					updateError(1, colVal, s.getRow(1).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;
				}
				// 3rd row-first col span content i.e fetch Employed value
				Columns_header = rows_table.get(2).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				updateError(2, 0, s.getRow(2).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				updateError(2, 1, s.getRow(2).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());

				Columns_header = rows_table.get(2).findElements(
						By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
				cellVal = 2;
				colVal = 0;
				while (cellVal < 12 && colVal < 10) {
					updateError(2, colVal, s.getRow(2).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;
				}

				// second row span content

				Columns_header = rows_table.get(8).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				// fetch from 0th cell
				updateError(8, 0, s.getRow(8).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				updateError(8, 1, s.getRow(8).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());
				Columns_header = rows_table.get(8).findElements(
						By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
				cellVal = 2;
				colVal = 0;
				while (cellVal < 12 && colVal < 10) {
					updateError(8, 1, s.getRow(8).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;

				}

				// third col span content
				Columns_header = rows_table.get(12).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

				updateError(12, 0,
						s.getRow(12).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());

				Columns_header = rows_table.get(12).findElements(
						By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
				cellVal = 2;
				colVal = 0;
				while (cellVal < 12 && colVal < 10) {
					updateError(12, 1, s.getRow(12).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;

				}

				// Fetch the contents of table from row 3 to last 8th row
				for (int row = 3; row < 8; row++) {
					List<WebElement> Columns_row = rows_table
							.get(row)
							.findElements(
									By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

					updateError(row, 1, s.getRow(row).getCell(1)
							.getStringCellValue(), Columns_row.get(0).getText());

					Columns_row = rows_table.get(row).findElements(
							By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
					cellVal = 2;
					colVal = 0;
					while (cellVal < 12 && colVal < 10) {

						updateError(8, 1, s.getRow(row).getCell(cellVal)
								.getStringCellValue(), Columns_row.get(colVal)
								.getText());
						cellVal++;
						colVal++;

					}
				}
				// Fetch the contents of table from row 8 to last 12th row
				for (int row = 9; row < rows_count - 1; row++) {
					List<WebElement> Columns_row = rows_table
							.get(row)
							.findElements(
									By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

					updateError(row, 1, s.getRow(row).getCell(1)
							.getStringCellValue(), Columns_row.get(0).getText());

					Columns_row = rows_table.get(row).findElements(
							By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
					cellVal = 2;
					colVal = 0;
					while (cellVal < 12 && colVal < 10) {

						updateError(8, 1, s.getRow(row).getCell(cellVal)
								.getStringCellValue(), Columns_row.get(colVal)
								.getText());
						cellVal++;
						colVal++;

					}
				}

			}

			else if (subReportName
					.equalsIgnoreCase(TestBaseConstants.NALP_EMPLOYEE_DETAIL_BY_RACE_OR_ETHINICITY)) {
				WebElement mytable = GlobalVariables.driver.findElement(By
						.xpath(xpath));
				// To locate rows of table.
				List<WebElement> rows_table = mytable.findElements(By
						.tagName(TestBaseConstants.TABLE_ROW_TAG));
				// To calculate no of rows In table.

				// /headers
				List<WebElement> Columns_header = rows_table.get(0)
						.findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				// 1st row compare cell values
				// compare 1st cell
				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				// compare 2nd cell
				updateError(0, 1, s.getRow(0).getCell(2).getStringCellValue(),
						Columns_header.get(1).getText());

				// compare 3rd cell
				updateError(0, 2, s.getRow(0).getCell(4).getStringCellValue(),
						Columns_header.get(2).getText());
				// compare 4th cell
				updateError(0, 6, s.getRow(0).getCell(6).getStringCellValue(),
						Columns_header.get(3).getText());
				updateError(0, 8, s.getRow(0).getCell(8).getStringCellValue(),
						Columns_header.get(4).getText());
				updateError(0, 10,
						s.getRow(0).getCell(10).getStringCellValue(),
						Columns_header.get(5).getText());
				// second row of header
				Columns_header = rows_table.get(1).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				int cellVal = 2;
				int colVal = 0;
				// enter values to cols 2 to 12
				while (cellVal < Columns_header.size() + 2
						&& colVal < Columns_header.size()) {
					updateError(1, cellVal, s.getRow(1).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;
				}

				// 3rd row-first row span content i.e fetch Employed value

				Columns_header = rows_table.get(2).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				// 1st cell compare
				updateError(2, 0, s.getRow(2).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				// 2nd cell compare
				updateError(2, 1, s.getRow(2).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());
				Columns_header = rows_table.get(2).findElements(
						By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
				cellVal = 2;
				colVal = 0;
				while (cellVal < Columns_header.size() + 2
						&& colVal < Columns_header.size()) {
					updateError(2, cellVal, s.getRow(2).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;
				}

				Columns_header = rows_table.get(6).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				// compare 0th cell
				updateError(6, 0, s.getRow(6).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				// compare 1st cell
				updateError(6, 1, s.getRow(6).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());

				Columns_header = rows_table.get(6).findElements(
						By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
				cellVal = 2;
				colVal = 0;
				while (cellVal < Columns_header.size() + 2
						&& colVal < Columns_header.size()) {
					updateError(6, cellVal, s.getRow(6).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;
				}

				// 18th row with no row or col span
				Columns_header = rows_table.get(17).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				// System.out.println(Columns_header.size());

				/*
				 * System.out.println(s.getRow(17).getCell(0).getStringCellValue(
				 * ) +Columns_header.get(0).getText());
				 * System.out.println(s.getRow
				 * (17).getCell(0).getStringCellValue()
				 * +Columns_header.get(0).getText());
				 */
				updateError(17, 0,
						s.getRow(17).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				updateError(17, 1,
						s.getRow(17).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());

				Columns_header = rows_table.get(17).findElements(
						By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
				// System.out.println(Columns_header.size());
				cellVal = 2;
				colVal = 0;
				while (cellVal < Columns_header.size() + 2
						&& colVal < Columns_header.size()) {
					/*
					 * System.out.println(s.getRow(17).getCell(cellVal).
					 * getStringCellValue()+
					 * Columns_header.get(colVal).getText());
					 */
					updateError(17, cellVal, s.getRow(17).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;

				}

				// third row span content
				Columns_header = rows_table.get(18).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

				updateError(18, cellVal, s.getRow(18).getCell(0)
						.getStringCellValue(), Columns_header.get(0).getText());
				updateError(18, cellVal, s.getRow(18).getCell(1)
						.getStringCellValue(), Columns_header.get(1).getText());

				Columns_header = rows_table.get(18).findElements(
						By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
				cellVal = 2;
				colVal = 0;
				while (cellVal < Columns_header.size() + 2
						&& colVal < Columns_header.size()) {
					/*
					 * System.out.println(s.getRow(18).getCell(cellVal).
					 * getStringCellValue()+
					 * Columns_header.get(colVal).getText());
					 */
					updateError(18, cellVal, s.getRow(18).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;

				}
				// fourth row span content
				Columns_header = rows_table.get(27).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

				/*
				 * System.out.println(s.getRow(27).getCell(0).getStringCellValue(
				 * )+ Columns_header.get(0).getText());
				 * System.out.println(s.getRow
				 * (27).getCell(0).getStringCellValue()+
				 * Columns_header.get(1).getText());
				 */

				updateError(27, cellVal, s.getRow(27).getCell(0)
						.getStringCellValue(), Columns_header.get(0).getText());
				updateError(27, cellVal, s.getRow(27).getCell(1)
						.getStringCellValue(), Columns_header.get(1).getText());

				Columns_header = rows_table.get(27).findElements(
						By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
				cellVal = 2;
				colVal = 0;
				while (cellVal < Columns_header.size() + 2
						&& colVal < Columns_header.size()) {
					/*
					 * System.out.println(s.getRow(27).getCell(cellVal).
					 * getStringCellValue()+
					 * Columns_header.get(colVal).getText());
					 */
					updateError(27, cellVal, s.getRow(27).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;
				}
				// fetch table row contents

				// Fetch the contents of table from row 3 to last 7th row
				for (int row = 3; row < 6; row++) {
					List<WebElement> Columns_row = rows_table
							.get(row)
							.findElements(
									By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
					/*
					 * System.out.println(s.getRow(row).getCell(1).
					 * getStringCellValue() +Columns_row.get(0).getText());
					 */
					updateError(row, cellVal, s.getRow(row).getCell(1)
							.getStringCellValue(), Columns_row.get(0).getText());
					// To locate columns(cells) of that specific row.
					Columns_row = rows_table.get(row).findElements(
							By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

					cellVal = 2;
					colVal = 0;
					while (cellVal < Columns_row.size() + 2
							&& colVal < Columns_row.size()) {

						updateError(row, cellVal, s.getRow(row)
								.getCell(cellVal).getStringCellValue(),
								Columns_row.get(colVal).getText());
						cellVal++;
						colVal++;

					}
					// fetch contents of second block
					for (row = 7; row < 17; row++) {
						Columns_row = rows_table.get(row).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						updateError(row, cellVal, s.getRow(row).getCell(1)
								.getStringCellValue(), Columns_row.get(0)
								.getText());
						Columns_row = rows_table.get(row).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

						cellVal = 2;
						colVal = 0;
						while (cellVal < Columns_row.size() + 2
								&& colVal < Columns_row.size()) {
							updateError(row, cellVal,
									s.getRow(row).getCell(cellVal)
											.getStringCellValue(), Columns_row
											.get(colVal).getText());
							cellVal++;
							colVal++;

						}

					}// close for

					// fetch contents of 4th block
					for (row = 19; row < 27; row++) {
						Columns_row = rows_table.get(row).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						updateError(row, cellVal, s.getRow(row).getCell(1)
								.getStringCellValue(), Columns_row.get(0)
								.getText());
						Columns_row = rows_table.get(row).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

						cellVal = 2;
						colVal = 0;
						while (cellVal < Columns_row.size() + 2
								&& colVal < Columns_row.size()) {

							updateError(row, cellVal,
									s.getRow(row).getCell(cellVal)
											.getStringCellValue(), Columns_row
											.get(colVal).getText());
							cellVal++;
							colVal++;

						}

					}// for

					// fetch contents of 5th block
					for (row = 28; row < 32; row++) {
						Columns_row = rows_table.get(row).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						updateError(row, cellVal, s.getRow(row).getCell(1)
								.getStringCellValue(), Columns_row.get(0)
								.getText());
						Columns_row = rows_table.get(row).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

						cellVal = 2;
						colVal = 0;
						while (cellVal < Columns_row.size() + 2
								&& colVal < Columns_row.size()) {

							updateError(row, cellVal,
									s.getRow(row).getCell(cellVal)
											.getStringCellValue(), Columns_row
											.get(colVal).getText());
							cellVal++;
							colVal++;

						}

					}// for

				}

			}// close if subreport is emp detail by race or ethnicity

			else if (subReportName
					.equalsIgnoreCase(TestBaseConstants.NALP_EMPLOYER_DETAIL_BY_GENDER)) {
				WebElement mytable = GlobalVariables.driver.findElement(By
						.xpath(xpath));
				// To locate rows of table.
				List<WebElement> rows_table = mytable.findElements(By
						.tagName(TestBaseConstants.TABLE_ROW_TAG));
				// To calculate no of rows In table.

				// /headers
				List<WebElement> Columns_header = rows_table.get(0)
						.findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				// 1st row compare cell values
				// compare 1st cell
				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				// compare 2nd cell
				updateError(0, 1, s.getRow(0).getCell(2).getStringCellValue(),
						Columns_header.get(1).getText());

				// compare 3rd cell
				updateError(0, 2, s.getRow(0).getCell(4).getStringCellValue(),
						Columns_header.get(2).getText());
				// compare 4th cell
				updateError(0, 6, s.getRow(0).getCell(6).getStringCellValue(),
						Columns_header.get(3).getText());

				// second row of header
				Columns_header = rows_table.get(1).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				int cellVal = 2;
				int colVal = 0;
				// enter values to cols 2 to 12
				while (cellVal < Columns_header.size() + 2
						&& colVal < Columns_header.size()) {
					updateError(1, cellVal, s.getRow(1).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;
				}

				// 3rd row-first row span content i.e fetch Employed value

				Columns_header = rows_table.get(2).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				// 1st cell compare
				updateError(2, 0, s.getRow(2).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				// 2nd cell compare
				updateError(2, 1, s.getRow(2).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());
				Columns_header = rows_table.get(2).findElements(
						By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
				cellVal = 2;
				colVal = 0;
				while (cellVal < Columns_header.size() + 2
						&& colVal < Columns_header.size()) {
					updateError(2, cellVal, s.getRow(2).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;
				}

				Columns_header = rows_table.get(6).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				// compare 0th cell
				updateError(6, 0, s.getRow(6).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				// compare 1st cell
				updateError(6, 1, s.getRow(6).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());

				Columns_header = rows_table.get(6).findElements(
						By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
				cellVal = 2;
				colVal = 0;
				while (cellVal < Columns_header.size() + 2
						&& colVal < Columns_header.size()) {
					updateError(6, cellVal, s.getRow(6).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;
				}

				// 18th row with no row or col span
				Columns_header = rows_table.get(17).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				// System.out.println(Columns_header.size());

				/*
				 * System.out.println(s.getRow(17).getCell(0).getStringCellValue(
				 * ) +Columns_header.get(0).getText());
				 * System.out.println(s.getRow
				 * (17).getCell(0).getStringCellValue()
				 * +Columns_header.get(0).getText());
				 */
				updateError(17, 0,
						s.getRow(17).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				updateError(17, 1,
						s.getRow(17).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());

				Columns_header = rows_table.get(17).findElements(
						By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
				// System.out.println(Columns_header.size());
				cellVal = 2;
				colVal = 0;
				while (cellVal < Columns_header.size() + 2
						&& colVal < Columns_header.size()) {
					/*
					 * System.out.println(s.getRow(17).getCell(cellVal).
					 * getStringCellValue()+
					 * Columns_header.get(colVal).getText());
					 */
					updateError(17, cellVal, s.getRow(17).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;

				}

				// third row span content
				Columns_header = rows_table.get(18).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

				updateError(18, cellVal, s.getRow(18).getCell(0)
						.getStringCellValue(), Columns_header.get(0).getText());
				updateError(18, cellVal, s.getRow(18).getCell(1)
						.getStringCellValue(), Columns_header.get(1).getText());

				Columns_header = rows_table.get(18).findElements(
						By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
				cellVal = 2;
				colVal = 0;
				while (cellVal < Columns_header.size() + 2
						&& colVal < Columns_header.size()) {
					/*
					 * System.out.println(s.getRow(18).getCell(cellVal).
					 * getStringCellValue()+
					 * Columns_header.get(colVal).getText());
					 */
					updateError(18, cellVal, s.getRow(18).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;

				}
				// fourth row span content
				Columns_header = rows_table.get(27).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

				/*
				 * System.out.println(s.getRow(27).getCell(0).getStringCellValue(
				 * )+ Columns_header.get(0).getText());
				 * System.out.println(s.getRow
				 * (27).getCell(0).getStringCellValue()+
				 * Columns_header.get(1).getText());
				 */

				updateError(27, cellVal, s.getRow(27).getCell(0)
						.getStringCellValue(), Columns_header.get(0).getText());
				updateError(27, cellVal, s.getRow(27).getCell(1)
						.getStringCellValue(), Columns_header.get(1).getText());

				Columns_header = rows_table.get(27).findElements(
						By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
				cellVal = 2;
				colVal = 0;
				while (cellVal < Columns_header.size() + 2
						&& colVal < Columns_header.size()) {
					/*
					 * System.out.println(s.getRow(27).getCell(cellVal).
					 * getStringCellValue()+
					 * Columns_header.get(colVal).getText());
					 */
					updateError(27, cellVal, s.getRow(27).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;
				}
				// fetch table row contents

				// Fetch the contents of table from row 3 to last 7th row
				for (int row = 3; row < 6; row++) {
					List<WebElement> Columns_row = rows_table
							.get(row)
							.findElements(
									By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
					/*
					 * System.out.println(s.getRow(row).getCell(1).
					 * getStringCellValue() +Columns_row.get(0).getText());
					 */
					updateError(row, cellVal, s.getRow(row).getCell(1)
							.getStringCellValue(), Columns_row.get(0).getText());
					// To locate columns(cells) of that specific row.
					Columns_row = rows_table.get(row).findElements(
							By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

					cellVal = 2;
					colVal = 0;
					while (cellVal < Columns_row.size() + 2
							&& colVal < Columns_row.size()) {

						updateError(row, cellVal, s.getRow(row)
								.getCell(cellVal).getStringCellValue(),
								Columns_row.get(colVal).getText());
						cellVal++;
						colVal++;

					}
					// fetch contents of second block
					for (row = 7; row < 17; row++) {
						Columns_row = rows_table.get(row).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						updateError(row, cellVal, s.getRow(row).getCell(1)
								.getStringCellValue(), Columns_row.get(0)
								.getText());
						Columns_row = rows_table.get(row).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

						cellVal = 2;
						colVal = 0;
						while (cellVal < Columns_row.size() + 2
								&& colVal < Columns_row.size()) {
							updateError(row, cellVal,
									s.getRow(row).getCell(cellVal)
											.getStringCellValue(), Columns_row
											.get(colVal).getText());
							cellVal++;
							colVal++;

						}

					}// close for

					// fetch contents of 4th block
					for (row = 19; row < 27; row++) {
						Columns_row = rows_table.get(row).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						updateError(row, cellVal, s.getRow(row).getCell(1)
								.getStringCellValue(), Columns_row.get(0)
								.getText());
						Columns_row = rows_table.get(row).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

						cellVal = 2;
						colVal = 0;
						while (cellVal < Columns_row.size() + 2
								&& colVal < Columns_row.size()) {

							updateError(row, cellVal,
									s.getRow(row).getCell(cellVal)
											.getStringCellValue(), Columns_row
											.get(colVal).getText());
							cellVal++;
							colVal++;

						}

					}// for

					// fetch contents of 5th block
					for (row = 28; row < 32; row++) {
						Columns_row = rows_table.get(row).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						updateError(row, cellVal, s.getRow(row).getCell(1)
								.getStringCellValue(), Columns_row.get(0)
								.getText());
						Columns_row = rows_table.get(row).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

						cellVal = 2;
						colVal = 0;
						while (cellVal < Columns_row.size() + 2
								&& colVal < Columns_row.size()) {

							updateError(row, cellVal,
									s.getRow(row).getCell(cellVal)
											.getStringCellValue(), Columns_row
											.get(colVal).getText());
							cellVal++;
							colVal++;

						}

					}// for

				}// close if

			}

			FileOutputStream fos = new FileOutputStream(path);
			wb.write(fos);
			fos.close();

			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);
		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing mT1_TH2_TBH2_TCN_ReadXLSX keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, ermsg);
		}

	}

	/********************************************************************************************
	 * Author : DivyaRaju.R LastModifiedDate : 1st may 2015 Method name :
	 * mT1_TH2_TBH1_TCN_WriteXLSX Description : This method is used for fetching
	 * data from 1220 application and writing that excel based on table having
	 * 2headers in header section 1 header in body section and table content
	 *
	 *********************************************************************************************/

	public static void mT1_TH2_TBH1_TCN_WriteXLSX(String sheetName,
			String excelFileName, String msg, String tableXpath,
			String subReportName) {
		boolean xlFileCreated = false;
		try {// Pre_Build_Number

			// fetch the folder path to create work book
			String folderPath = fetchWriteExcelFolderPath();
			/*
			 * cleanPath( GlobalVariables.CONFIG.getProperty(TestBaseConstants.
			 * BUILD_FOLDER_PATH)) +TestBaseConstants.PATH_SIGN
			 * +TestBaseConstants.BASELINE_FOLDER_NAME+
			 * GlobalVariables.CONFIG.getProperty
			 * (TestBaseConstants.BUILD_NUMBER) +TestBaseConstants.PATH_SIGN+
			 * TestBaseConstants.ITERATION+
			 * GlobalVariables.CONFIG.getProperty(TestBaseConstants
			 * .BUILD_ITERATION_VALUE)+ TestBaseConstants.PATH_SIGN+
			 * GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_TYPE);
			 */

			File preBuildFolderPath = new File(folderPath);

			// Create directory
			boolean folderCreated = preBuildFolderPath.mkdirs();
			String filePath = preBuildFolderPath + TestBaseConstants.PATH_SIGN
					+ GlobalVariables.testCaseIdentifier
					+ TestBaseConstants.EXCEL_FILE_EXTENSION;
			File filePath1 = new File(filePath);
			// System.out.println("File Path is -->"+filePath);
			if (folderCreated || preBuildFolderPath.exists()) {
				// if excel exists
				if (filePath1.exists()) {
					filePath1.delete();
					xlFileCreated = ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);
				} else // create new
				{
					xlFileCreated = ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);
				}

				if (xlFileCreated) {

					if (subReportName
							.equalsIgnoreCase(TestBaseConstants.NALP_SOURCE_OF_JOB_BY_EMPLOYER_TYPE)) {
						// fetch table xpath
						WebElement mytable = GlobalVariables.driver
								.findElement(By.xpath(tableXpath));
						// To locate rows of table.
						List<WebElement> rows_table = mytable.findElements(By
								.tagName(TestBaseConstants.TABLE_ROW_TAG));

						// open stream to open file
						FileInputStream fis = new FileInputStream(filePath);
						Workbook wb = WorkbookFactory.create(fis);
						// get row size
						int rows_count = rows_table.size();
						Sheet s = wb
								.getSheet(GlobalVariables.testCaseIdentifier);

						/*** Fetch headers and store to excel of 1st col **/
						// first row of header
						List<WebElement> Columns_header = rows_table
								.get(0)
								.findElements(
										By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						// create first row
						Row r1 = s.createRow(0);
						String j = Columns_header.get(0).getText();
						// add 1st cell value
						r1.createCell(0).setCellValue(j);
						// merge the text based on rowspan or colspan
						s.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));

						// add value to cell 2
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 1, 2));

						// add value to cell 3
						r1.createCell(3).setCellValue(
								Columns_header.get(2).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 3, 4));

						// add value to cell 4
						r1.createCell(5).setCellValue(
								Columns_header.get(3).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 5, 6));
						// add value to cell 5
						r1.createCell(7).setCellValue(
								Columns_header.get(4).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 7, 8));
						// add value to cell 6
						r1.createCell(9).setCellValue(
								Columns_header.get(5).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 9, 10));
						// add value to cell 7
						r1.createCell(11).setCellValue(
								Columns_header.get(6).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 11, 12));
						// add value to cell 8
						r1.createCell(13).setCellValue(
								Columns_header.get(7).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 13, 14));
						// add value to cell 9
						r1.createCell(15).setCellValue(
								Columns_header.get(8).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 15, 16));

						// fetch 2nd row contents
						r1 = s.createRow(1);
						Columns_header = rows_table.get(1).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						int cellVal = 1;
						int colVal = 0;
						// enter values to cols 2 to 18
						while (cellVal < Columns_header.size() + 1
								&& colVal < Columns_header.size()) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;
						}

						// Fetch the contents of table from row 3 to last 7th
						// row
						for (int row = 2; row < rows_count; row++) {
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

							Row r = s.createRow(row);
							String celtext = Columns_row.get(0).getText();

							r.createCell(0).setCellValue(celtext);
							// To locate columns(cells) of that specific row.
							Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
							cellVal = 1;
							colVal = 0;
							while (cellVal < Columns_row.size() + 1
									&& colVal < Columns_row.size()) {
								r.createCell(cellVal).setCellValue(
										Columns_row.get(colVal).getText());
								cellVal++;
								colVal++;

							}
						}

						FileOutputStream fos = new FileOutputStream(filePath);
						wb.write(fos);
						fos.close();

					}// Source of Job by Employer Type

					else if (subReportName
							.equalsIgnoreCase(TestBaseConstants.NALP_EMPLOYER_TYPE_BY_AGE_AT_GRADUATION)
							|| subReportName
									.equalsIgnoreCase(TestBaseConstants.NALP_EMPLOYER_TYPES_BY_RACE_OR_ETHNICITY)
							|| subReportName
									.equalsIgnoreCase(TestBaseConstants.NALP_PRIVATE_PRACTICE_DETAIL_BY_RACE_OR_ETHNICITY)) {
						// fetch table xpath
						WebElement mytable = GlobalVariables.driver
								.findElement(By.xpath(tableXpath));
						// To locate rows of table.
						List<WebElement> rows_table = mytable.findElements(By
								.tagName(TestBaseConstants.TABLE_ROW_TAG));

						// open stream to open file
						FileInputStream fis = new FileInputStream(filePath);
						Workbook wb = WorkbookFactory.create(fis);
						// get row size
						int rows_count = rows_table.size();

						// System.out.println("Row count of table is --->"+rows_count);
						Sheet s = wb
								.getSheet(GlobalVariables.testCaseIdentifier);

						/*** Fetch headers and store to excel of 1st col **/
						// first row of header
						List<WebElement> Columns_header = rows_table
								.get(0)
								.findElements(
										By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						// create first row
						Row r1 = s.createRow(0);
						String j = Columns_header.get(0).getText();
						// add 1st cell value
						r1.createCell(0).setCellValue(j);
						// merge the text based on rowspan or colspan
						s.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));

						// add value to cell 2
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 1, 2));

						// add value to cell 3
						r1.createCell(3).setCellValue(
								Columns_header.get(2).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 3, 4));

						// add value to cell 4
						r1.createCell(5).setCellValue(
								Columns_header.get(3).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 5, 6));
						// add value to cell 5
						r1.createCell(7).setCellValue(
								Columns_header.get(4).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 7, 8));

						// add value to cell 6
						r1.createCell(9).setCellValue(
								Columns_header.get(5).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 9, 10));

						// fetch 2nd row contents
						r1 = s.createRow(1);
						Columns_header = rows_table.get(1).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						// System.out.println(Columns_header.size());
						int cellVal = 1;
						int colVal = 0;
						// enter values to cols 1 to 18
						while (cellVal < Columns_header.size() + 1
								&& colVal < Columns_header.size()) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;
						}

						// Fetch the contents of table from row 3 to last 7th
						// row
						for (int row = 2; row < rows_count; row++) {
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

							Row r = s.createRow(row);
							String celtext = Columns_row.get(0).getText();

							r.createCell(0).setCellValue(celtext);

							// To locate columns(cells) of that specific row.
							Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

							cellVal = 1;
							colVal = 0;
							while (cellVal < Columns_row.size() + 1
									&& colVal < Columns_row.size()) {
								r.createCell(cellVal).setCellValue(
										Columns_row.get(colVal).getText());
								cellVal++;
								colVal++;

							}
						}
						FileOutputStream fos = new FileOutputStream(filePath);
						wb.write(fos);
						fos.close();

					}//
					else if (subReportName
							.equalsIgnoreCase(TestBaseConstants.NALP_FULL_AND_PART_TIME_JOBS_BY_EMPLOYEE_TYPE)) {
						// fetch table xpath
						WebElement mytable = GlobalVariables.driver
								.findElement(By.xpath(tableXpath));
						// To locate rows of table.
						List<WebElement> rows_table = mytable.findElements(By
								.tagName(TestBaseConstants.TABLE_ROW_TAG));

						// open stream to open file
						FileInputStream fis = new FileInputStream(filePath);
						Workbook wb = WorkbookFactory.create(fis);
						// get row size
						int rows_count = rows_table.size();
						// System.out.println("Row count is -->"+rows_count);
						Sheet s = wb
								.getSheet(GlobalVariables.testCaseIdentifier);

						/*** Fetch headers and store to excel of 1st col **/
						// first row of header
						List<WebElement> Columns_header = rows_table
								.get(0)
								.findElements(
										By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						// create first row
						Row r1 = s.createRow(0);
						String j = Columns_header.get(0).getText();
						// add 1st cell value
						r1.createCell(0).setCellValue(j);
						// merge the text based on rowspan or colspan
						s.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));

						// add value to cell 2
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 1, 2));

						// add value to cell 3
						r1.createCell(3).setCellValue(
								Columns_header.get(2).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 3, 4));

						// add value to cell 4
						r1.createCell(5).setCellValue(
								Columns_header.get(3).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 5, 6));

						// fetch 2nd row contents
						r1 = s.createRow(1);
						Columns_header = rows_table.get(1).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						int cellVal = 1;
						int colVal = 0;
						// enter values to cols 1 to 18
						while (cellVal < Columns_header.size() + 1
								&& colVal < Columns_header.size()) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;
						}

						// Fetch the contents of table from row 3 to last 7th
						// row
						for (int row = 2; row < rows_count; row++) {
							// To locate columns(cells) of that specific row.
							Row r = s.createRow(row);
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
							/* System.out.println(Columns_row.size()); */
							cellVal = 0;
							colVal = 0;
							while (cellVal < Columns_row.size()
									&& colVal < Columns_row.size()) {
								r.createCell(cellVal).setCellValue(
										Columns_row.get(colVal).getText());
								cellVal++;
								colVal++;

							}
						}
						FileOutputStream fos = new FileOutputStream(filePath);
						wb.write(fos);
						fos.close();

					} else if (subReportName
							.equalsIgnoreCase(TestBaseConstants.NALP_EMPLOYER_TYPES_BY_GENDER)
							|| subReportName
									.equalsIgnoreCase(TestBaseConstants.NALP_PRIVATE_PRACTICE_DETAIL_BY_GENDER)) {
						// fetch table xpath
						WebElement mytable = GlobalVariables.driver
								.findElement(By.xpath(tableXpath));
						// To locate rows of table.
						List<WebElement> rows_table = mytable.findElements(By
								.tagName(TestBaseConstants.TABLE_ROW_TAG));

						// open stream to open file
						FileInputStream fis = new FileInputStream(filePath);
						Workbook wb = WorkbookFactory.create(fis);
						// get row size
						int rows_count = rows_table.size();

						// System.out.println("row count is "+rows_count);
						Sheet s = wb
								.getSheet(GlobalVariables.testCaseIdentifier);
						/*** Fetch headers and store to excel of 1st col **/
						// first row of header
						List<WebElement> Columns_header = rows_table
								.get(0)
								.findElements(
										By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						// System.out.println(Columns_header.size());
						// create first row
						Row r1 = s.createRow(0);
						String j = Columns_header.get(0).getText();
						// add 1st cell value
						r1.createCell(0).setCellValue(j);
						// merge the text based on rowspan or colspan
						s.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));

						// add value to cell 2
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 1, 2));

						// add value to cell 3
						r1.createCell(3).setCellValue(
								Columns_header.get(2).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 3, 4));

						// fetch 2nd row contents
						r1 = s.createRow(1);
						Columns_header = rows_table.get(1).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						int cellVal = 1;
						int colVal = 0;
						// enter values to cols 1 to 18
						while (cellVal < Columns_header.size() + 1
								&& colVal < Columns_header.size()) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;
						}

						// Fetch the contents of table from row 3 to last 7th
						// row
						for (int row = 2; row < rows_count; row++) {
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
							// System.out.println(Columns_row.size());

							Row r = s.createRow(row);
							String celtext = Columns_row.get(0).getText();
							// System.out.println(celtext);
							r.createCell(0).setCellValue(celtext);
							// To locate columns(cells) of that specific row.
							Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
							// System.out.println(Columns_row.size());
							cellVal = 1;
							colVal = 0;
							while (cellVal < Columns_row.size() + 1
									&& colVal < Columns_row.size()) {
								r.createCell(cellVal).setCellValue(
										Columns_row.get(colVal).getText());
								cellVal++;
								colVal++;

							}
						}

						FileOutputStream fos = new FileOutputStream(filePath);
						wb.write(fos);
						fos.close();
					}

				} else {
					// System.out.println("File not created");
					GlobalVariables.APPICATION_LOGS
							.error(TestBaseConstants.EXCEL_FILE_NOT_CREATED);
					Logs.errorLog(TestBaseConstants.EXCEL_FILE_NOT_CREATED);
				}
			} else {
				// System.out.println("Folder not created");
				GlobalVariables.APPICATION_LOGS
						.error(TestBaseConstants.BASELINE_FOLDER_NOT_CREATED);
				Logs.errorLog("Folder not created");

			}

			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);

		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing mT1_TH2_TBH1_TCN_WriteXLSX keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, msg);
		}

	}// mT1_TH2_TBH1_TCN_WriteXLSX

	/********************************************************************************************
	 * Author : DivyaRaju.R LastModifiedDate : 1st may 2015 Method name :
	 * mT1_TH2_TBH1_TCN_ReadXLSX Description : This method is used for comparing
	 * 1220 app data with stored value of excel
	 *
	 *********************************************************************************************/

	public static void mT1_TH2_TBH1_TCN_ReadXLSX(String excelSheetName,
			String automationId, String xpath, String subReportName, String msg) {
		GlobalVariables.testCaseIdentifier = automationId;
		try

		{
			String path = fetchReadExcelFolderPath();
			/*
			 * cleanPath(GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_FOLDER_PATH)) +
			 * TestBaseConstants.PATH_SIGN +
			 * TestBaseConstants.BASELINE_FOLDER_NAME + GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_NUMBER) +
			 * TestBaseConstants.PATH_SIGN + TestBaseConstants.ITERATION +
			 * GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_ITERATION_VALUE) +
			 * TestBaseConstants.PATH_SIGN +
			 * TestBaseConstants.BASELINE_BUILD_TYPE +
			 * TestBaseConstants.PATH_SIGN + GlobalVariables.testCaseIdentifier
			 * + ".xlsx";
			 */
			// System.out.println("Path of file is -->"+path);
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);
			// System.out.println("Hi");
			// wb.createSheet(year);
			Sheet s = wb.getSheet(excelSheetName);
			if (subReportName
					.equalsIgnoreCase(TestBaseConstants.NALP_SOURCE_OF_JOB_BY_EMPLOYER_TYPE))

			{
				WebElement mytable = GlobalVariables.driver.findElement(By
						.xpath(xpath));
				// To locate rows of table.
				List<WebElement> rows_table = mytable.findElements(By
						.tagName(TestBaseConstants.TABLE_ROW_TAG));
				// To calculate no of rows In table.
				int rows_count = rows_table.size();

				// /headers
				List<WebElement> Columns_header = rows_table.get(0)
						.findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				// Validating first row headers and cell contents

				// compare 0th cell of header
				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				// compare 1st cell of header
				updateError(0, 1, s.getRow(0).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());

				// compare 2nd cell of header
				updateError(0, 2, s.getRow(0).getCell(3).getStringCellValue(),
						Columns_header.get(2).getText());
				// compare 3rd cell of header
				updateError(0, 3, s.getRow(0).getCell(5).getStringCellValue(),
						Columns_header.get(3).getText());
				// compare 4th cell of header
				updateError(0, 4, s.getRow(0).getCell(7).getStringCellValue(),
						Columns_header.get(4).getText());
				// compare 5th cell of header
				updateError(0, 5, s.getRow(0).getCell(9).getStringCellValue(),
						Columns_header.get(5).getText());
				// compare 6th cell of header
				updateError(0, 6, s.getRow(0).getCell(11).getStringCellValue(),
						Columns_header.get(6).getText());
				// compare 7th cell of header
				updateError(0, 7, s.getRow(0).getCell(13).getStringCellValue(),
						Columns_header.get(7).getText());
				// compare 8th cell of header
				updateError(0, 8, s.getRow(0).getCell(15).getStringCellValue(),
						Columns_header.get(8).getText());

				// fetch 2nd row contents
				Columns_header = rows_table.get(1).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				int cellVal = 1;
				int colVal = 0;
				// enter values to cols 2 to 18
				while (cellVal < Columns_header.size() + 1
						&& colVal < Columns_header.size()) {
					updateError(1, cellVal, s.getRow(1).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;
				}

				// Fetch the contents of table from 2nd row till last row

				for (int row = 2; row < rows_count; row++) {
					// To locate columns(cells) of that specific row.
					List<WebElement> Columns_row = rows_table
							.get(row)
							.findElements(
									By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
					// System.out.println(Columns_row.size());
					cellVal = 1;
					colVal = 0;
					while (cellVal < Columns_row.size() + 1
							&& colVal < Columns_row.size()) {
						updateError(row, cellVal, s.getRow(row)
								.getCell(cellVal).getStringCellValue(),
								Columns_row.get(colVal).getText());

						cellVal++;
						colVal++;
					}

				}// end if

			}// Source of Job by Employer Type

			else if (subReportName
					.equalsIgnoreCase(TestBaseConstants.NALP_EMPLOYER_TYPE_BY_AGE_AT_GRADUATION)
					|| subReportName
							.equalsIgnoreCase(TestBaseConstants.NALP_EMPLOYER_TYPES_BY_RACE_OR_ETHNICITY)
					|| subReportName
							.equalsIgnoreCase(TestBaseConstants.NALP_PRIVATE_PRACTICE_DETAIL_BY_RACE_OR_ETHNICITY)) {
				WebElement mytable = GlobalVariables.driver.findElement(By
						.xpath(xpath));
				// To locate rows of table.
				List<WebElement> rows_table = mytable.findElements(By
						.tagName(TestBaseConstants.TABLE_ROW_TAG));
				// To calculate no of rows In table.
				int rows_count = rows_table.size();
				// System.out.println("Row count is -->"+rows_count);
				// /headers
				List<WebElement> Columns_header = rows_table.get(0)
						.findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				// Validating first row headers and cell contents

				// compare 0th cell of header
				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				// compare 1st cell of header
				updateError(0, 1, s.getRow(0).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());

				// compare 2nd cell of header
				updateError(0, 2, s.getRow(0).getCell(3).getStringCellValue(),
						Columns_header.get(2).getText());
				// compare 3rd cell of header
				updateError(0, 3, s.getRow(0).getCell(5).getStringCellValue(),
						Columns_header.get(3).getText());
				// compare 4th cell of header
				updateError(0, 4, s.getRow(0).getCell(7).getStringCellValue(),
						Columns_header.get(4).getText());
				// compare 5th cell of header
				updateError(0, 5, s.getRow(0).getCell(9).getStringCellValue(),
						Columns_header.get(5).getText());

				// fetch 2nd row contents
				Columns_header = rows_table.get(1).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				// System.out.println(Columns_header.size() );

				int cellVal = 1;
				int colVal = 0;
				// enter values to cols 2 to 18
				while (cellVal < Columns_header.size() + 1
						&& colVal < Columns_header.size()) {

					updateError(1, 0, s.getRow(1).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;
				}

				// Fetch the contents of table from 2nd row till last row

				for (int row = 2; row < rows_count; row++) {

					// To locate columns(cells) of that specific row.
					List<WebElement> Columns_row = rows_table
							.get(row)
							.findElements(
									By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
					cellVal = 1;
					colVal = 0;
					while (cellVal < Columns_row.size() + 1
							&& colVal < Columns_row.size()) {

						updateError(row, cellVal, s.getRow(row)
								.getCell(cellVal).getStringCellValue(),
								Columns_row.get(colVal).getText());

						cellVal++;
						colVal++;
					}

				}// end for
			}// end if

			else if (subReportName
					.equalsIgnoreCase(TestBaseConstants.NALP_FULL_AND_PART_TIME_JOBS_BY_EMPLOYEE_TYPE)) {
				WebElement mytable = GlobalVariables.driver.findElement(By
						.xpath(xpath));
				// To locate rows of table.
				List<WebElement> rows_table = mytable.findElements(By
						.tagName(TestBaseConstants.TABLE_ROW_TAG));
				// To calculate no of rows In table.
				int rows_count = rows_table.size();

				// /headers
				List<WebElement> Columns_header = rows_table.get(0)
						.findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				// Validating first row headers and cell contents

				// compare 0th cell of header
				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				// compare 1st cell of header
				updateError(0, 1, s.getRow(0).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());

				// compare 2nd cell of header
				updateError(0, 2, s.getRow(0).getCell(3).getStringCellValue(),
						Columns_header.get(2).getText());
				// compare 3rd cell of header
				updateError(0, 3, s.getRow(0).getCell(5).getStringCellValue(),
						Columns_header.get(3).getText());

				// fetch 2nd row contents
				Columns_header = rows_table.get(1).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				int cellVal = 1;
				int colVal = 0;
				// enter values to cols 2 to 18
				while (cellVal < Columns_header.size() + 1
						&& colVal < Columns_header.size()) {
					updateError(1, cellVal, s.getRow(1).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					colVal++;
					cellVal++;
				}

				// Fetch the contents of table from 3rd row till last row

				for (int row = 2; row < rows_count; row++) {

					// To locate columns(cells) of that specific row.
					List<WebElement> Columns_row = rows_table
							.get(row)
							.findElements(
									By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

					cellVal = 0;
					colVal = 0;

					while (cellVal < Columns_row.size()
							&& colVal < Columns_row.size()) {
						updateError(row, cellVal, s.getRow(row)
								.getCell(cellVal).getStringCellValue(),
								Columns_row.get(colVal).getText());

						cellVal++;
						colVal++;
					}

				}// end for

			}// end if

			else if (subReportName
					.equalsIgnoreCase(TestBaseConstants.NALP_EMPLOYER_TYPES_BY_GENDER)
					|| subReportName
							.equalsIgnoreCase(TestBaseConstants.NALP_PRIVATE_PRACTICE_DETAIL_BY_GENDER)) {

				WebElement mytable = GlobalVariables.driver.findElement(By
						.xpath(xpath));
				// To locate rows of table.
				List<WebElement> rows_table = mytable.findElements(By
						.tagName(TestBaseConstants.TABLE_ROW_TAG));
				// To calculate no of rows In table.
				int rows_count = rows_table.size();
				// System.out.println("Row count is "+rows_count);
				// /headers
				List<WebElement> Columns_header = rows_table.get(0)
						.findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				// Validating first row headers and cell contents

				// compare 0th cell of header
				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				// compare 1st cell of header
				updateError(0, 1, s.getRow(0).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());

				// compare 2nd cell of header
				updateError(0, 2, s.getRow(0).getCell(3).getStringCellValue(),
						Columns_header.get(2).getText());

				// fetch 2nd row contents
				Columns_header = rows_table.get(1).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				int cellVal = 1;
				int colVal = 0;
				// enter values to cols 2 to 18
				while (cellVal < Columns_header.size() + 1
						&& colVal < Columns_header.size()) {
					updateError(1, cellVal, s.getRow(1).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;
				}

				// Fetch the contents of table from 2nd row till last row

				for (int row = 2; row < rows_count; row++) {
					List<WebElement> Columns_row = rows_table
							.get(row)
							.findElements(
									By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
					updateError(row, 0, s.getRow(row).getCell(0)
							.getStringCellValue(), Columns_row.get(0).getText());

					// To locate columns(cells) of that specific row.
					Columns_row = rows_table.get(row).findElements(
							By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
					cellVal = 1;
					colVal = 0;
					while (cellVal < Columns_row.size() + 1
							&& colVal < Columns_row.size()) {

						updateError(row, cellVal, s.getRow(row)
								.getCell(cellVal).getStringCellValue(),
								Columns_row.get(colVal).getText());

						cellVal++;
						colVal++;
					}

				}// end for
			}// end if

			FileOutputStream fos = new FileOutputStream(path);
			wb.write(fos);
			fos.close();

			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);
		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing mT1_TH2_TBH1_TCN_ReadXLSX keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, ermsg);
		}
	}

	/********************************************************************************************
	 * Author : DivyaRaju.R LastModifiedDate : 4th June 2015 Method name :
	 * mT2_TH2_TCN_WriteXLSX Description : This method is used for fetching data
	 * from 1220 application and writing that excel
	 *
	 *********************************************************************************************/
	public static void mT2_TH2_TCN_WriteXLSX(String sheetName,
			String excelFileName, String msg, String tableXpath,
			String tableXpath2, String subReportName) {
		boolean xlFileCreated = false;

		try {// fetch the folder path to create work book

			String folderPath = fetchWriteExcelFolderPath();
			/*
			 * cleanPath( GlobalVariables.CONFIG.getProperty(TestBaseConstants.
			 * BUILD_FOLDER_PATH)) +TestBaseConstants.PATH_SIGN
			 * +TestBaseConstants.BASELINE_FOLDER_NAME+
			 * GlobalVariables.CONFIG.getProperty
			 * (TestBaseConstants.BUILD_NUMBER) +TestBaseConstants.PATH_SIGN+
			 * TestBaseConstants.ITERATION+
			 * GlobalVariables.CONFIG.getProperty(TestBaseConstants
			 * .BUILD_ITERATION_VALUE)+ TestBaseConstants.PATH_SIGN+
			 * GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_TYPE);
			 */
			// System.out.println("Build folder path is "+folderPath);

			File preBuildFolderPath = new File(folderPath);

			// Create directory
			boolean folderCreated = preBuildFolderPath.mkdirs();
			String filePath = preBuildFolderPath + "/"
					+ GlobalVariables.testCaseIdentifier + ".xlsx";
			File filePath1 = new File(filePath);
			// System.out.println("File Path is -->"+filePath);
			if (folderCreated || preBuildFolderPath.exists()) {
				if (filePath1.exists()) {
					filePath1.delete();
					xlFileCreated = ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);
				} else {
					xlFileCreated = ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);
				}

				if (xlFileCreated) {
					if (subReportName
							.equalsIgnoreCase(TestBaseConstants.NALP_CLASS_SUMMARY)) {
						WebElement mytable = GlobalVariables.driver
								.findElement(By.xpath(tableXpath));
						// To locate rows of table.
						List<WebElement> rows_table = mytable.findElements(By
								.tagName(TestBaseConstants.TABLE_ROW_TAG));
						// System.out.println("row count is -->"+rows_table.size());
						// open stream to open file
						FileInputStream fis = new FileInputStream(filePath);
						Workbook wb = WorkbookFactory.create(fis);
						// get row size
						// int rows_count = rows_table.size();
						Sheet s = wb
								.getSheet(GlobalVariables.testCaseIdentifier);

						List<WebElement> Columns_header = rows_table
								.get(0)
								.findElements(
										By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						// System.out.println("Header count is -->"+Columns_header.size());

						// create first row
						Row r1 = s.createRow(0);
						r1.createCell(0).setCellValue(
								Columns_header.get(1).getText());
						// System.out.println(Columns_header.get(1).getText());
						// merge the text based on rowspan or colspan
						// s.addMergedRegion(new CellRangeAddress(0, 0, 3, 7));

						r1 = s.createRow(1);

						Columns_header = rows_table.get(1).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

						// second row of header

						for (int a = 1; a < Columns_header.size(); a++) {
							r1.createCell(a).setCellValue(
									Columns_header.get(a).getText());
							GlobalVariables.APPICATION_LOGS.info(Columns_header
									.get(a).getText());
						}
						// Fetch the contents of table from row 3 to last 7th
						// row
						for (int row = 2; row < rows_table.size(); row++) {
							r1 = s.createRow(row);
							// To locate columns(cells) of that specific row.
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
							int columns_count = Columns_row.size();
							// Loop will execute till the last cell of that
							// specific row.
							for (int column = 0; column < columns_count; column++) {
								// To retrieve text from that specific cell.
								String celtext = Columns_row.get(column)
										.getText();
								r1.createCell(column).setCellValue(celtext);
								Logs.infoLog("Cell Value Of row number " + row
										+ " and column number " + column
										+ " Is " + celtext);
								GlobalVariables.APPICATION_LOGS
										.info("Cell Value Of row number " + row
												+ " and column number "
												+ column + " Is " + celtext);
							}

						}

						mytable = GlobalVariables.driver.findElement(By
								.xpath(tableXpath2));
						// To locate rows of table.
						List<WebElement> rows_table1 = mytable.findElements(By
								.tagName(TestBaseConstants.TABLE_ROW_TAG));
						// System.out.println("row count is -->"+rows_table1.size());
						int rts = rows_table.size() + 1;
						// System.out.println(rts);
						// create first row
						r1 = s.createRow(rts);
						Columns_header = rows_table1.get(0).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());

						// create second row
						r1 = s.createRow(rts + 1);
						// second row of header
						Columns_header = rows_table1.get(1).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						// System.out.println(Columns_header.size());
						for (int a = 1; a < Columns_header.size(); a++) {
							r1.createCell(a).setCellValue(
									Columns_header.get(a).getText());
							// System.out.println(Columns_header.get(a).getText());
							GlobalVariables.APPICATION_LOGS.info(Columns_header
									.get(a).getText());
						}
						rts = rts + 2;

						// Fetch the body contents of table
						for (int row = 2; row < rows_table1.size(); row++) {

							r1 = s.createRow(rts);
							// To locate columns(cells) of that specific row.
							List<WebElement> Columns_row = rows_table1
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

							int columns_count = Columns_row.size();
							// System.out.println(columns_count);
							// Loop will execute till the last cell of that
							// specific row.
							for (int column = 0; column < columns_count; column++) {
								// To retrieve text from that specific cell.
								String celtext = Columns_row.get(column)
										.getText();
								r1.createCell(column).setCellValue(celtext);
								Logs.infoLog("Cell Value Of row number " + row
										+ " and column number " + column
										+ " Is " + celtext);
								GlobalVariables.APPICATION_LOGS
										.info("Cell Value Of row number " + row
												+ " and column number "
												+ column + " Is " + celtext);
								// System.out.println("Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);
							}
							rts++;
						}
						FileOutputStream fos = new FileOutputStream(filePath);
						wb.write(fos);
						fos.close();
					}// Graduate Demographics
				}// excel file created

				else {
					// System.out.println("File not created");
					GlobalVariables.APPICATION_LOGS.error("File not created");
					Logs.errorLog("File not created");
				}

			} else {
				// System.out.println("Folder not created");
				GlobalVariables.APPICATION_LOGS.error("Folder not created");
				Logs.errorLog("Folder not created");
			}

			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);

		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing mT2_TH2_TCN_WriteXLSX keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, msg);
		}

	}// mT2_TH2_TCN_WriteXLSX

	/********************************************************************************************
	 * Author : DivyaRaju.R LastModifiedDate : 1st may 2015 Method name :
	 * mT1_TH1_TCN_ReadXLSX Description : This method is used for fetching data
	 * from 1220 application and writing that excel
	 *
	 *********************************************************************************************/

	public static void mT1_TH1_TCN_ReadXLSX(String excelSheetName,
			String automationId, String xpath, String subReportName, String msg) {
		GlobalVariables.testCaseIdentifier = automationId;
		try {

			String path = fetchReadExcelFolderPath();
			/*
			 * cleanPath( GlobalVariables.CONFIG.getProperty(TestBaseConstants.
			 * BUILD_FOLDER_PATH)) +TestBaseConstants.PATH_SIGN
			 * +TestBaseConstants.BASELINE_FOLDER_NAME+
			 * GlobalVariables.CONFIG.getProperty
			 * (TestBaseConstants.BUILD_NUMBER) +TestBaseConstants.PATH_SIGN+
			 * TestBaseConstants.ITERATION+
			 * GlobalVariables.CONFIG.getProperty(TestBaseConstants
			 * .BUILD_ITERATION_VALUE)+ TestBaseConstants.PATH_SIGN+
			 * TestBaseConstants.BASELINE_BUILD_TYPE
			 * +TestBaseConstants.PATH_SIGN
			 * +GlobalVariables.testCaseIdentifier+".xlsx";
			 */
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);

			// wb.createSheet(year);
			Sheet s = wb.getSheet(excelSheetName);

			// To locate table.
			WebElement mytable = GlobalVariables.driver.findElement(By
					.xpath(xpath));
			// To locate rows of table.
			List<WebElement> rows_table = mytable.findElements(By
					.tagName(TestBaseConstants.TABLE_ROW_TAG));
			// To calculate no of rows In table.
			int rows_count = rows_table.size();

			// /headers
			List<WebElement> Columns_header = rows_table.get(0).findElements(
					By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

			if (subReportName
					.equalsIgnoreCase("Compensation by Professional Functions")
					|| subReportName
							.equalsIgnoreCase("Compensation by Industries")
					|| subReportName
							.equalsIgnoreCase("World Regions Breakdown")
					|| subReportName
							.equalsIgnoreCase("Compensation by North American Geographic Regions")
					|| subReportName
							.equalsIgnoreCase("Compensation by Undergraduate Major")
					|| subReportName
							.equalsIgnoreCase("Compensation by Professional Experience"))

			{ // compare 1st cell
				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				// compare 2nd cell
				updateError(0, 1, s.getRow(0).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());
				// compare 3rd cell
				updateError(0, 3, s.getRow(0).getCell(3).getStringCellValue(),
						Columns_header.get(2).getText());
				// compare 4th cell
				updateError(0, 4, s.getRow(0).getCell(4).getStringCellValue(),
						Columns_header.get(3).getText());
				// compare 5th cell
				updateError(0, 5, s.getRow(0).getCell(5).getStringCellValue(),
						Columns_header.get(4).getText());
				// compare 6th cell
				updateError(0, 6, s.getRow(0).getCell(6).getStringCellValue(),
						Columns_header.get(5).getText());
			} else if (subReportName
					.equalsIgnoreCase("Primary Source of Full-Time Job Acceptances")) {
				// compare 1st cell
				updateError(0, 0, s.getRow(0).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());
				// compare 2nd cell
				updateError(0, 1, s.getRow(0).getCell(3).getStringCellValue(),
						Columns_header.get(2).getText());
			} else if (subReportName
					.equalsIgnoreCase("Location of Instate Jobs")) {
				// compare 1st cell
				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				// compare 2nd cell
				updateError(0, 1, s.getRow(0).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());
				// compare 3rd cell
				updateError(0, 2, s.getRow(0).getCell(2).getStringCellValue(),
						Columns_header.get(2).getText());
			}

			else if (subReportName
					.equalsIgnoreCase("Number of Jobs Reported Taken by State")
					|| subReportName
							.equalsIgnoreCase("The Graduating Class (B)")
					|| subReportName.equalsIgnoreCase("Summary")
					|| subReportName.equalsIgnoreCase("Full-Time Employment")
					|| subReportName.equalsIgnoreCase("Part-Time Employment")
					|| subReportName.equalsIgnoreCase("Service Organization")
					|| subReportName.equalsIgnoreCase("Military Service")
					|| subReportName.equalsIgnoreCase("Continuing Education")
					|| subReportName.equalsIgnoreCase("Seeking or Unreported")
					|| subReportName.equalsIgnoreCase("Employment Status")
					|| subReportName
							.equalsIgnoreCase("Law School/University Funded Positions")
					|| subReportName.equalsIgnoreCase("Employment Type")
					|| subReportName.equalsIgnoreCase("Employment Location")) {
				for (int col = 0; col < Columns_header.size(); col++) {
					updateError(0, col, s.getRow(0).getCell(col)
							.getStringCellValue(), Columns_header.get(col)
							.getText());
				}
			}

			// Loop will execute till the last row of table.
			for (int row = 1; row < rows_count; row++) {
				// To locate columns(cells) of that specific row.
				List<WebElement> Columns_row = rows_table.get(row)
						.findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
				Row r = s.getRow(row);
				// To calculate no of columns(cells) In that specific row.
				int columns_count = Columns_row.size();

				// Loop will execute till the last cell of that specific row.
				for (int column = 0; column < columns_count; column++) {
					// To retrieve text from that specific cell.
					String webtext = Columns_row.get(column).getText();
					String xltext = r.getCell(column).getStringCellValue();
					updateError(row, column, xltext, webtext);
				}

			}

			FileOutputStream fos = new FileOutputStream(path);
			wb.write(fos);
			fos.close();
			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);
		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing mT1_TH1_TCN_ReadXLSX keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, ermsg);
		}

	}

	/********************************************************************************************
	 * Author : DivyaRaju.R LastModifiedDate : 1st may 2015 Method name :
	 * mT1_TH1_TCN_WriteXLSX Description : This method is used for fetching data
	 * from 1220 application and writing that excel
	 *
	 *********************************************************************************************/
	public static void mT1_TH1_TCN_WriteXLSX(String sheetName,
			String excelFileName, String msg, String tableXpath,
			String subReportName) {
		boolean xlFileCreated = false;

		try {
			// fetch the folder path to create work book

			String folderPath = fetchWriteExcelFolderPath();
			// System.out.println("Build folder path is "+folderPath);

			File preBuildFolderPath = new File(folderPath);

			// Create directory
			boolean folderCreated = preBuildFolderPath.mkdirs();
			String filePath = preBuildFolderPath + "/"
					+ GlobalVariables.testCaseIdentifier + ".xlsx";
			File filePath1 = new File(filePath);
			// System.out.println("File Path is -->"+filePath);
			if (folderCreated || preBuildFolderPath.exists()) {
				if (filePath1.exists()) {
					filePath1.delete();
					xlFileCreated = ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);
				} else {
					xlFileCreated = ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);
				}
				if (xlFileCreated) {
					FileInputStream fis = new FileInputStream(filePath);
					Workbook wb = WorkbookFactory.create(fis);
					// wb.createSheet(year);
					Sheet s = wb.getSheet(sheetName);

					// To locate table.
					WebElement mytable = GlobalVariables.driver.findElement(By
							.xpath(tableXpath));
					// To locate rows of table.
					List<WebElement> rows_table = mytable.findElements(By
							.tagName(TestBaseConstants.TABLE_ROW_TAG));
					// To calculate no of rows In table.
					int rows_count = rows_table.size();

					// headers
					List<WebElement> Columns_header = rows_table
							.get(0)
							.findElements(
									By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
					Row r1 = s.createRow(0);
					if (subReportName
							.equalsIgnoreCase("Compensation by Professional Functions")
							|| subReportName
									.equalsIgnoreCase("Compensation by Industries")
							|| subReportName
									.equalsIgnoreCase("World Regions Breakdown")
							|| subReportName
									.equalsIgnoreCase("Compensation by North American Geographic Regions")
							|| subReportName
									.equalsIgnoreCase("Compensation by Undergraduate Major")
							|| subReportName
									.equalsIgnoreCase("Compensation by Professional Experience")) {
						Logs.infoLog("Sub report is " + subReportName);
						// 1st cell of 1st header
						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());
						// 2nd cell of header
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 1, 2));
						// 3rd cell
						r1.createCell(3).setCellValue(
								Columns_header.get(2).getText());
						// 4th cell
						r1.createCell(4).setCellValue(
								Columns_header.get(3).getText());
						// 5th cell
						r1.createCell(5).setCellValue(
								Columns_header.get(4).getText());
						// 6th cell
						r1.createCell(6).setCellValue(
								Columns_header.get(5).getText());

					}

					else if (subReportName
							.equalsIgnoreCase("Primary Source of Full-Time Job Acceptances")) {
						Logs.infoLog("Sub report is " + subReportName);
						// 1st cell of 1st header
						/*
						 * r1.createCell(0).setCellValue(Columns_header.get(0).
						 * getText());
						 */
						// 2nd cell of header
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 1, 2));
						// 3rd cell
						r1.createCell(3).setCellValue(
								Columns_header.get(2).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 3, 4));

					}

					else if (subReportName
							.equalsIgnoreCase("Location of Instate Jobs")) {
						Logs.infoLog("Sub report is " + subReportName);
						// 1st cell of 1st header
						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());
						// 2nd cell of header
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());
						// 3rd cell
						r1.createCell(2).setCellValue(
								Columns_header.get(2).getText());
					}

					else if (subReportName
							.equalsIgnoreCase(TestBaseConstants.NALP_NUMBER_OF_JOBS_REPORTED_TAKEN_BY_STATE)
							|| subReportName
									.equalsIgnoreCase("The Graduating Class (B)")
							|| subReportName.equalsIgnoreCase("Summary")
							|| subReportName
									.equalsIgnoreCase("Full-Time Employment")
							|| subReportName
									.equalsIgnoreCase("Part-Time Employment")
							|| subReportName
									.equalsIgnoreCase("Service Organization")
							|| subReportName
									.equalsIgnoreCase("Military Service")
							|| subReportName
									.equalsIgnoreCase("Continuing Education")
							|| subReportName
									.equalsIgnoreCase("Seeking or Unreported")
							|| subReportName
									.equalsIgnoreCase("Employment Status")
							|| subReportName
									.equalsIgnoreCase("Law School/University Funded Positions")
							|| subReportName
									.equalsIgnoreCase("Employment Type")
							|| subReportName
									.equalsIgnoreCase("Employment Location")) {
						Logs.infoLog("Sub report is " + subReportName);
						for (int col = 0; col < Columns_header.size(); col++) {
							Logs.infoLog("Cell Value Of row number " + r1
									+ " and column number " + col + " is "
									+ Columns_header.get(col).getText());

							r1.createCell(col).setCellValue(
									Columns_header.get(col).getText());
						}
					}
					for (int row = 1; row < rows_count; row++) {
						// To locate columns(cells) of that specific row.
						List<WebElement> Columns_row = rows_table
								.get(row)
								.findElements(
										By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

						// To calculate no of columns(cells) In that specific
						// row.
						int columns_count = Columns_row.size();

						Row r = s.createRow(row);

						// Loop will execute till the last cell of that specific
						// row.
						for (int column = 0; column < columns_count; column++) {
							// To retrieve text from that specific cell.
							String celtext = Columns_row.get(column).getText();
							r.createCell(column).setCellValue(celtext);
							Logs.infoLog("Cell Value Of row number " + row
									+ " and column number " + column + " Is "
									+ celtext);
						}
					} // for loop
					FileOutputStream fos = new FileOutputStream(filePath);
					wb.write(fos);
					fos.close();
				} else {
					// System.out.println("File not created");
					GlobalVariables.APPICATION_LOGS.error("File not created");
					Logs.errorLog("File not created");
				}

			} else {
				// System.out.println("Folder not created");
				GlobalVariables.APPICATION_LOGS.error("Folder not created");
				Logs.errorLog("Folder not created");
			}

			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);

		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing mT2_TH2_TCN_WriteXLSX keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, msg);
		}

	} // method mT1_TH1_TCN_WriteXLSX

	public static void mT1_TCN_WriteXLSX(String sheetName,
			String excelFileName, String msg, String subReportName) {
		boolean xlFileCreated = false;

		try {// fetch the folder path to create work book

			String folderPath = fetchWriteExcelFolderPath();
			/*
			 * cleanPath( GlobalVariables.CONFIG.getProperty(TestBaseConstants.
			 * BUILD_FOLDER_PATH)) +TestBaseConstants.PATH_SIGN
			 * +TestBaseConstants.BASELINE_FOLDER_NAME+
			 * GlobalVariables.CONFIG.getProperty
			 * (TestBaseConstants.BUILD_NUMBER) +TestBaseConstants.PATH_SIGN+
			 * TestBaseConstants.ITERATION+
			 * GlobalVariables.CONFIG.getProperty(TestBaseConstants
			 * .BUILD_ITERATION_VALUE)+ TestBaseConstants.PATH_SIGN+
			 * GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_TYPE);
			 */
			// System.out.println("Build folder path is "+folderPath);

			File preBuildFolderPath = new File(folderPath);

			// Create directory
			boolean folderCreated = preBuildFolderPath.mkdirs();
			String filePath = preBuildFolderPath + "/"
					+ GlobalVariables.testCaseIdentifier + ".xlsx";
			File filePath1 = new File(filePath);
			// System.out.println("File Path is -->"+filePath);
			if (folderCreated || preBuildFolderPath.exists()) {
				if (filePath1.exists()) {
					filePath1.delete();
					xlFileCreated = ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);
				} else {
					xlFileCreated = ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);
				}

				if (xlFileCreated) {
					if (subReportName
							.equalsIgnoreCase(TestBaseConstants.BWR_FORGONE_SALARY)) {

						// open stream to open file
						FileInputStream fis = new FileInputStream(filePath);
						Workbook wb = WorkbookFactory.create(fis);
						Sheet s = wb
								.getSheet(GlobalVariables.testCaseIdentifier);

						// To locate rows of table.
						// create first row
						Row r = s.createRow(0);
						String j = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@class='report_list']/tbody/tr[1]/td[1]/p"))
								.getText();
						r.createCell(0).setCellValue(j);

						j = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@class='report_list']/tbody/tr[1]/td[2]/div"))
								.getText();
						r.createCell(1).setCellValue(j);

						// Create second row
						r = s.createRow(1);
						j = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@class='report_list']/tbody/tr[2]/td[1]/p"))
								.getText();
						r.createCell(0).setCellValue(j);
						j = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@class='report_list']/tbody/tr[2]/td[2]/div"))
								.getText();
						r.createCell(1).setCellValue(j);

						FileOutputStream fos = new FileOutputStream(filePath);
						wb.write(fos);
						fos.close();
					}// forgone salary
				}// excel file created

				else {
					// System.out.println("File not created");
					GlobalVariables.APPICATION_LOGS.error("File not created");
					Logs.errorLog("File not created");
				}

			} else {
				// System.out.println("Folder not created");
				GlobalVariables.APPICATION_LOGS.error("Folder not created");
				Logs.errorLog("Folder not created");
			}

			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);

		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing mT1_TCN_WriteXLSX keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, msg);
		}

	} // method mT1_TCN_WriteXLSX

	public static void mT1_TH3_TCN_ReadXLSX(String excelSheetName,
			String automationId, String xpath, String subReportName, String msg) {
		GlobalVariables.testCaseIdentifier = automationId;
		try {
			String path = fetchReadExcelFolderPath();
			/*
			 * cleanPath(GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_FOLDER_PATH)) +
			 * TestBaseConstants.PATH_SIGN +
			 * TestBaseConstants.BASELINE_FOLDER_NAME + GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_NUMBER) +
			 * TestBaseConstants.PATH_SIGN + TestBaseConstants.ITERATION +
			 * GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_ITERATION_VALUE) +
			 * TestBaseConstants.PATH_SIGN +
			 * TestBaseConstants.BASELINE_BUILD_TYPE +
			 * TestBaseConstants.PATH_SIGN + GlobalVariables.testCaseIdentifier
			 * + ".xlsx";
			 */
			// System.out.println("Path of file is -->"+path);
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);

			// wb.createSheet(year);
			Sheet s = wb.getSheet(excelSheetName);

			if (subReportName
					.equalsIgnoreCase(TestBaseConstants.NALP_GRADUATE_DEMOGRAPHICS)) {
				WebElement mytable = GlobalVariables.driver.findElement(By
						.xpath(xpath));
				// To locate rows of table.
				List<WebElement> rows_table = mytable.findElements(By
						.tagName(TestBaseConstants.TABLE_ROW_TAG));
				// To calculate no of rows In table.
				int rows_count = rows_table.size();
				GlobalVariables.APPICATION_LOGS
						.info("No of rows in table are-->" + rows_count);

				List<WebElement> Columns_header = rows_table.get(0)
						.findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

				// comparing first row contents
				// compare 0th cell of header
				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				// compare 2nd cell
				updateError(0, 2, s.getRow(0).getCell(2).getStringCellValue(),
						Columns_header.get(1).getText());

				updateError(0, 6, s.getRow(0).getCell(6).getStringCellValue(),
						Columns_header.get(2).getText());

				// comparing 2nd row contents
				Columns_header = rows_table.get(1).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

				updateError(1, 2, s.getRow(1).getCell(2).getStringCellValue(),
						Columns_header.get(0).getText());

				updateError(1, 4, s.getRow(1).getCell(4).getStringCellValue(),
						Columns_header.get(1).getText());

				// comparing row 3 contents
				Columns_header = rows_table.get(2).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

				int cellVal = 2;
				int colVal = 0;
				// enter values to cols 2 to 12
				while (cellVal < Columns_header.size() + 2
						&& colVal < Columns_header.size()) {
					updateError(3, cellVal, s.getRow(3).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;
				}
				// compare first row span contents
				Columns_header = rows_table.get(3).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				updateError(4, cellVal, s.getRow(4).getCell(0)
						.getStringCellValue(), Columns_header.get(0).getText());

				updateError(4, cellVal, s.getRow(4).getCell(1)
						.getStringCellValue(), Columns_header.get(1).getText());

				Columns_header = rows_table.get(3).findElements(
						By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
				cellVal = 2;
				colVal = 0;
				// enter values to cols 2 to 12
				while (cellVal < Columns_header.size() + 2
						&& colVal < Columns_header.size()) {
					updateError(4, cellVal, s.getRow(4).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;
				}
				// compare 2nd row span content

				Columns_header = rows_table.get(8).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				updateError(9, cellVal, s.getRow(9).getCell(0)
						.getStringCellValue(), Columns_header.get(0).getText());

				updateError(9, cellVal, s.getRow(9).getCell(1)
						.getStringCellValue(), Columns_header.get(1).getText());

				Columns_header = rows_table.get(8).findElements(
						By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
				cellVal = 2;
				colVal = 0;
				// enter values to cols 2 to 12
				while (cellVal < Columns_header.size() + 2
						&& colVal < Columns_header.size()) {
					updateError(9, cellVal, s.getRow(9).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;
				}

				// compare 3rd row span content
				Columns_header = rows_table.get(11).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				updateError(12, cellVal, s.getRow(12).getCell(0)
						.getStringCellValue(), Columns_header.get(0).getText());
				Columns_header = rows_table.get(11).findElements(
						By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
				cellVal = 2;
				colVal = 0;

				// enter values to cols 2 to 12
				while (cellVal < Columns_header.size() + 2
						&& colVal < Columns_header.size()) {
					updateError(12, cellVal, s.getRow(12).getCell(cellVal)
							.getStringCellValue(), Columns_header.get(colVal)
							.getText());
					cellVal++;
					colVal++;
				}

				// compare contents from row 3 to 7

				for (int row = 4; row < 8; row++) {
					List<WebElement> Columns_row = rows_table
							.get(row)
							.findElements(
									By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
					updateError(row + 1, cellVal, s.getRow(row + 1).getCell(1)
							.getStringCellValue(), Columns_row.get(0).getText());
					Columns_row = rows_table.get(row).findElements(
							By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

					cellVal = 2;
					colVal = 0;

					while (cellVal < Columns_header.size() + 2
							&& colVal < Columns_header.size()) {
						updateError(row + 1, cellVal, s.getRow(row + 1)
								.getCell(cellVal).getStringCellValue(),
								Columns_row.get(colVal).getText());
						cellVal++;
						colVal++;
					}

				}
				// compare contents from row 10 & 11
				int row = 9;
				while (row < 11) {
					List<WebElement> Columns_row = rows_table
							.get(row)
							.findElements(
									By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
					updateError(row + 1, cellVal, s.getRow(row + 1).getCell(1)
							.getStringCellValue(), Columns_row.get(0).getText());
					Columns_row = rows_table.get(row).findElements(
							By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

					cellVal = 2;
					colVal = 0;

					while (cellVal < Columns_header.size() + 2
							&& colVal < Columns_header.size()) {
						updateError(row + 1, cellVal, s.getRow(row + 1)
								.getCell(cellVal).getStringCellValue(),
								Columns_row.get(colVal).getText());
						cellVal++;
						colVal++;
					}
					row++;

				}

			}

			FileOutputStream fos = new FileOutputStream(path);
			wb.write(fos);
			fos.close();

			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);
		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing mT1_TH3_TCN_ReadXLSX keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, ermsg);
		}

	}// mT1_TH3_TCN_ReadXLSX

	public static void mT1_TH3_TCN_WriteXLSX(String sheetName,
			String excelFileName, String msg, String tableXpath,
			String subReportName) {
		boolean xlFileCreated = false;

		try {// fetch the folder path to create work book

			String folderPath = fetchWriteExcelFolderPath();
			/*
			 * cleanPath( GlobalVariables.CONFIG.getProperty(TestBaseConstants.
			 * BUILD_FOLDER_PATH)) +TestBaseConstants.PATH_SIGN
			 * +TestBaseConstants.BASELINE_FOLDER_NAME+
			 * GlobalVariables.CONFIG.getProperty
			 * (TestBaseConstants.BUILD_NUMBER) +TestBaseConstants.PATH_SIGN+
			 * TestBaseConstants.ITERATION+
			 * GlobalVariables.CONFIG.getProperty(TestBaseConstants
			 * .BUILD_ITERATION_VALUE)+ TestBaseConstants.PATH_SIGN+
			 * GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_TYPE);
			 */
			// System.out.println("Build folder path is "+folderPath);

			File preBuildFolderPath = new File(folderPath);

			// Create directory
			boolean folderCreated = preBuildFolderPath.mkdirs();
			String filePath = preBuildFolderPath + "/"
					+ GlobalVariables.testCaseIdentifier + ".xlsx";
			File filePath1 = new File(filePath);
			// System.out.println("File Path is -->"+filePath);
			if (folderCreated || preBuildFolderPath.exists()) {
				if (filePath1.exists()) {
					filePath1.delete();
					xlFileCreated = ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);
				} else {
					xlFileCreated = ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);
				}

				if (xlFileCreated) {
					if (subReportName
							.equalsIgnoreCase(TestBaseConstants.NALP_GRADUATE_DEMOGRAPHICS)) {
						WebElement mytable = GlobalVariables.driver
								.findElement(By.xpath(tableXpath));
						// To locate rows of table.
						List<WebElement> rows_table = mytable.findElements(By
								.tagName(TestBaseConstants.TABLE_ROW_TAG));
						// System.out.println("row count is -->"+rows_table.size());
						// open stream to open file
						FileInputStream fis = new FileInputStream(filePath);
						Workbook wb = WorkbookFactory.create(fis);
						// get row size
						// int rows_count = rows_table.size();
						Sheet s = wb
								.getSheet(GlobalVariables.testCaseIdentifier);

						List<WebElement> Columns_header = rows_table
								.get(0)
								.findElements(
										By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						// System.out.println("Header count is -->"+Columns_header.size());

						// create first row
						Row r1 = s.createRow(0);
						String j = Columns_header.get(0).getText();
						r1.createCell(0).setCellValue(j);
						// merge the text based on rowspan or colspan
						s.addMergedRegion(new CellRangeAddress(0, 3, 0, 1));

						// add value to cell 2
						r1.createCell(2).setCellValue(
								Columns_header.get(1).getText());
						s.addMergedRegion(new CellRangeAddress(0, 0, 2, 5));

						// add value to cell 3
						r1.createCell(6).setCellValue(
								Columns_header.get(2).getText());
						s.addMergedRegion(new CellRangeAddress(0, 1, 6, 7));

						r1 = s.createRow(1);

						Columns_header = rows_table.get(1).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

						// second row of header

						r1.createCell(2).setCellValue(
								Columns_header.get(0).getText());
						s.addMergedRegion(new CellRangeAddress(1, 2, 2, 3));
						r1.createCell(4).setCellValue(
								Columns_header.get(1).getText());
						s.addMergedRegion(new CellRangeAddress(1, 2, 4, 5));

						// fetch 3nd row contents
						r1 = s.createRow(3);
						Columns_header = rows_table.get(2).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						// System.out.println(Columns_header.size());
						int cellVal = 2;
						int colVal = 0;
						// enter values to cols 2 to 12
						while (cellVal < Columns_header.size() + 2
								&& colVal < Columns_header.size()) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;
						}

						// 3rd row-first row span content i.e fetch Employed
						// value

						Columns_header = rows_table.get(3).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						r1 = s.createRow(4);

						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());
						s.addMergedRegion(new CellRangeAddress(4, 8, 0, 0));
						// System.out.println(Columns_header.get(1).getText());
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());

						Columns_header = rows_table.get(3).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
						cellVal = 2;
						colVal = 0;
						while (cellVal < Columns_header.size() + 2
								&& colVal < Columns_header.size()) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;

						}

						// second row pan content

						Columns_header = rows_table.get(8).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						r1 = s.createRow(9);
						// System.out.println(Columns_header.get(0).getText());
						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());
						s.addMergedRegion(new CellRangeAddress(9, 11, 0, 0));

						// System.out.println(Columns_header.get(1).getText());
						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());
						Columns_header = rows_table.get(8).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
						cellVal = 2;
						colVal = 0;
						while (cellVal < Columns_header.size() + 2
								&& colVal < Columns_header.size()) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;

						}
						// third row span
						Columns_header = rows_table.get(11).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						r1 = s.createRow(12);

						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());

						Columns_header = rows_table.get(11).findElements(
								By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
						cellVal = 2;
						colVal = 0;
						while (cellVal < Columns_header.size() + 2
								&& colVal < Columns_header.size()) {
							r1.createCell(cellVal).setCellValue(
									Columns_header.get(colVal).getText());
							cellVal++;
							colVal++;

						}
						// Fetch the contents of table from row 3 to last 7th
						// row
						for (int row = 4; row < 8; row++) {
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

							Row r = s.createRow(row + 1);
							String celtext = Columns_row.get(0).getText();

							r.createCell(1).setCellValue(celtext);
							// To locate columns(cells) of that specific row.
							Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

							cellVal = 2;
							colVal = 0;
							while (cellVal < Columns_row.size() + 2
									&& colVal < Columns_row.size()) {
								r.createCell(cellVal).setCellValue(
										Columns_row.get(colVal).getText());
								cellVal++;
								colVal++;
							}
						}

						// fetch contents of second block
						/* for(int row=9;row<12;row++) */
						int row = 9;
						while (row < 11) {
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

							Row r = s.createRow(row + 1);
							String celtext = Columns_row.get(0).getText();

							r.createCell(1).setCellValue(celtext);
							// To locate columns(cells) of that specific row.
							Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

							cellVal = 2;
							colVal = 0;
							while (cellVal < Columns_row.size() + 2
									&& colVal < Columns_row.size()) {
								r.createCell(cellVal).setCellValue(
										Columns_row.get(colVal).getText());
								cellVal++;
								colVal++;
							}
							row++;
						}

						FileOutputStream fos = new FileOutputStream(filePath);
						wb.write(fos);
						fos.close();
					}// Graduate Demographics
				}// excel file created

				else {
					// System.out.println("File not created");
					GlobalVariables.APPICATION_LOGS.error("File not created");
					Logs.errorLog("File not created");
				}

			} else {
				// System.out.println("Folder not created");
				GlobalVariables.APPICATION_LOGS.error("Folder not created");
				Logs.errorLog("Folder not created");
			}

			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);

		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing mT3_TH2_TCN_WriteXLSX keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, msg);
		}

	} // method mT3_TH2_TCN_WriteXLSX

	public static void mT2_TH2_TCN_ReadXLSX(String excelSheetName,
			String automationId, String tableXpath, String tableXpath2,
			String subReportName, String msg) {
		GlobalVariables.testCaseIdentifier = automationId;
		try {
			String path = fetchReadExcelFolderPath();
			/*
			 * cleanPath(GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_FOLDER_PATH)) +
			 * TestBaseConstants.PATH_SIGN +
			 * TestBaseConstants.BASELINE_FOLDER_NAME + GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_NUMBER) +
			 * TestBaseConstants.PATH_SIGN + TestBaseConstants.ITERATION +
			 * GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_ITERATION_VALUE) +
			 * TestBaseConstants.PATH_SIGN +
			 * TestBaseConstants.BASELINE_BUILD_TYPE +
			 * TestBaseConstants.PATH_SIGN + GlobalVariables.testCaseIdentifier
			 * + ".xlsx";
			 */
			// System.out.println("Path of file is -->"+path);
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);

			// wb.createSheet(year);
			Sheet s = wb.getSheet(excelSheetName);

			if (subReportName
					.equalsIgnoreCase(TestBaseConstants.NALP_CLASS_SUMMARY)) {
				WebElement mytable = GlobalVariables.driver.findElement(By
						.xpath(tableXpath));
				// To locate rows of table.
				List<WebElement> rows_table = mytable.findElements(By
						.tagName(TestBaseConstants.TABLE_ROW_TAG));
				// First row of header
				List<WebElement> Columns_header = rows_table.get(0)
						.findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				// compare 1st row of header
				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						Columns_header.get(1).getText());
				// second row of header
				Columns_header = rows_table.get(1).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				for (int a = 1; a < Columns_header.size(); a++) {

					updateError(a, a, s.getRow(1).getCell(a)
							.getStringCellValue(), Columns_header.get(a)
							.getText());

				}

				// Fetch the contents of table
				for (int row = 2; row < rows_table.size(); row++) {
					List<WebElement> Columns_row = rows_table
							.get(row)
							.findElements(
									By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
					int columns_count = Columns_row.size();
					for (int column = 0; column < columns_count; column++) {
						// To retrieve text from that specific cell.
						String celtext = Columns_row.get(column).getText();
						updateError(row, column, s.getRow(row).getCell(column)
								.getStringCellValue(), celtext);
					}

				}

				/*** second table **/
				mytable = GlobalVariables.driver.findElement(By
						.xpath(tableXpath2));
				// To locate rows of table.
				List<WebElement> rows_table1 = mytable.findElements(By
						.tagName(TestBaseConstants.TABLE_ROW_TAG));
				int rts = rows_table.size() + 1;
				Columns_header = rows_table1.get(0).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				updateError(rts, 0, s.getRow(rts).getCell(1)
						.getStringCellValue(), Columns_header.get(1).getText());

				// compare second row
				rts = rts + 1;
				Columns_header = rows_table1.get(1).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				for (int a = 1; a < Columns_header.size(); a++) {
					/*
					 * System.out.println(s.getRow(rts).getCell(a).
					 * getStringCellValue());
					 * System.out.println(Columns_header.get(a).getText());
					 */
					updateError(rts, a, s.getRow(rts).getCell(a)
							.getStringCellValue(), Columns_header.get(a)
							.getText());
				}
				rts = rts + 1;
				// Fetch the body contents of table
				for (int row = 2; row < rows_table1.size(); row++) {
					List<WebElement> Columns_row = rows_table1
							.get(row)
							.findElements(
									By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
					int columns_count = Columns_row.size();
					for (int column = 0; column < columns_count; column++) {
						String celtext = Columns_row.get(column).getText();
						String excelText = s.getRow(rts).getCell(column)
								.getStringCellValue();
						/*
						 * System.out.println(celtext);
						 * System.out.println(excelText);
						 */
						updateError(rts, column, excelText, celtext);
					}
					rts++;
				}
			}

			FileOutputStream fos = new FileOutputStream(path);
			wb.write(fos);
			fos.close();

			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);
		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing mT2_TH2_TCN_ReadXLSX keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, ermsg);
		}

	}// mT2_TH2_TCN_ReadXLSX

	public static void mT3_TH2_TCN_WriteXLSX(String sheetName,
			String excelFileName, String msg, String tableXpath,
			String tableXpath2, String tableXpath3, String subReportName) {
		boolean xlFileCreated = false;

		try {// fetch the folder path to create work book

			String folderPath = fetchWriteExcelFolderPath();
			/*
			 * cleanPath( GlobalVariables.CONFIG.getProperty(TestBaseConstants.
			 * BUILD_FOLDER_PATH)) +TestBaseConstants.PATH_SIGN
			 * +TestBaseConstants.BASELINE_FOLDER_NAME+
			 * GlobalVariables.CONFIG.getProperty
			 * (TestBaseConstants.BUILD_NUMBER) +TestBaseConstants.PATH_SIGN+
			 * TestBaseConstants.ITERATION+
			 * GlobalVariables.CONFIG.getProperty(TestBaseConstants
			 * .BUILD_ITERATION_VALUE)+ TestBaseConstants.PATH_SIGN+
			 * GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_TYPE);
			 */
			// System.out.println("Build folder path is "+folderPath);

			File preBuildFolderPath = new File(folderPath);

			// Create directory
			boolean folderCreated = preBuildFolderPath.mkdirs();
			String filePath = preBuildFolderPath + "/"
					+ GlobalVariables.testCaseIdentifier + ".xlsx";
			File filePath1 = new File(filePath);
			// System.out.println("File Path is -->"+filePath);
			if (folderCreated || preBuildFolderPath.exists()) {
				if (filePath1.exists()) {
					filePath1.delete();
					xlFileCreated = ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);
				} else {
					xlFileCreated = ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);
				}

				if (xlFileCreated) {
					if (subReportName
							.equalsIgnoreCase(TestBaseConstants.MBA_COMPENSATION_REPORT)) {

						// System.out.println("row count is -->"+rows_table.size());
						// open stream to open file
						FileInputStream fis = new FileInputStream(filePath);
						Workbook wb = WorkbookFactory.create(fis);

						WebElement mytable = GlobalVariables.driver
								.findElement(By.xpath(tableXpath));
						// To locate rows of table.
						List<WebElement> rows_table = mytable.findElements(By
								.tagName(TestBaseConstants.TABLE_ROW_TAG));
						// get row size
						// int rows_count = rows_table.size();
						Sheet s = wb
								.getSheet(GlobalVariables.testCaseIdentifier);

						// System.out.println(rows_table.size());

						List<WebElement> Columns_header = rows_table
								.get(0)
								.findElements(
										By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						// System.out.println("Header count is -->"+Columns_header.size());

						// create first row
						Row r1 = s.createRow(0);
						String j = Columns_header.get(0).getText();
						r1.createCell(0).setCellValue(j);
						// merge the text based on rowspan or colspan
						s.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

						r1 = s.createRow(1);

						Columns_header = rows_table.get(1).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

						// second row of header

						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());

						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());

						r1.createCell(2).setCellValue(
								Columns_header.get(2).getText());

						r1.createCell(3).setCellValue(
								Columns_header.get(3).getText());

						r1.createCell(4).setCellValue(
								Columns_header.get(4).getText());

						r1.createCell(5).setCellValue(
								Columns_header.get(5).getText());

						r1.createCell(6).setCellValue(
								Columns_header.get(6).getText());
						// Fetch the contents of table from row 3 to last 7th
						// row
						for (int row = 2; row < rows_table.size(); row++) {
							// To locate columns(cells) of that specific row.
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

							// To calculate no of columns(cells) In that
							// specific row.
							int columns_count = Columns_row.size();

							Row r = s.createRow(row);

							// Loop will execute till the last cell of that
							// specific row.
							for (int column = 0; column < columns_count; column++) {
								// To retrieve text from that specific cell.
								String celtext = Columns_row.get(column)
										.getText();
								r.createCell(column).setCellValue(celtext);
								Logs.infoLog("Cell Value Of row number " + row
										+ " and column number " + column
										+ " Is " + celtext);
								// System.out.println("Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);
							}
						} // for loop

						mytable = GlobalVariables.driver.findElement(By
								.xpath(tableXpath2));
						// To locate rows of table.
						rows_table = mytable.findElements(By
								.tagName(TestBaseConstants.TABLE_ROW_TAG));
						// System.out.println(rows_table.size());
						Columns_header = rows_table.get(0).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						// System.out.println("Header count is -->"+Columns_header.size());

						// create first row
						r1 = s.createRow(6);
						j = Columns_header.get(0).getText();
						r1.createCell(0).setCellValue(j);
						// merge the text based on rowspan or colspan
						s.addMergedRegion(new CellRangeAddress(6, 6, 0, 6));

						r1 = s.createRow(7);

						Columns_header = rows_table.get(1).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						// System.out.println(Columns_header.size());
						// second row of header

						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());

						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());

						r1.createCell(2).setCellValue(
								Columns_header.get(2).getText());

						r1.createCell(3).setCellValue(
								Columns_header.get(3).getText());

						r1.createCell(4).setCellValue(
								Columns_header.get(4).getText());

						r1.createCell(5).setCellValue(
								Columns_header.get(5).getText());

						r1.createCell(6).setCellValue(
								Columns_header.get(6).getText());

						int rw = 8;
						int row = 2;
						while (rw < 11 && row < rows_table.size()) {
							// Fetch the contents of table from row 3 to last
							// 7th row
							// To locate columns(cells) of that specific row.
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

							// To calculate no of columns(cells) In that
							// specific row.
							int columns_count = Columns_row.size();
							// System.out.println(columns_count);
							Row r = s.createRow(rw);

							// Loop will execute till the last cell of that
							// specific row.
							for (int column = 0; column < columns_count; column++) {
								// To retrieve text from that specific cell.
								String celtext = Columns_row.get(column)
										.getText();
								r.createCell(column).setCellValue(celtext);
								Logs.infoLog("Cell Value Of row number " + row
										+ " and column number " + column
										+ " Is " + celtext);
							}
							row++;
							rw++;
						}

						mytable = GlobalVariables.driver.findElement(By
								.xpath(tableXpath3));
						// To locate rows of table.
						rows_table = mytable.findElements(By
								.tagName(TestBaseConstants.TABLE_ROW_TAG));
						Columns_header = rows_table.get(0).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
						// System.out.println("Header count is -->"+Columns_header.size());

						// create first row
						r1 = s.createRow(12);
						j = Columns_header.get(0).getText();
						r1.createCell(0).setCellValue(j);
						// merge the text based on rowspan or colspan
						s.addMergedRegion(new CellRangeAddress(12, 12, 0, 6));

						r1 = s.createRow(13);

						Columns_header = rows_table.get(1).findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

						// second row of header

						r1.createCell(0).setCellValue(
								Columns_header.get(0).getText());

						r1.createCell(1).setCellValue(
								Columns_header.get(1).getText());

						r1.createCell(2).setCellValue(
								Columns_header.get(2).getText());

						r1.createCell(3).setCellValue(
								Columns_header.get(3).getText());

						r1.createCell(4).setCellValue(
								Columns_header.get(4).getText());

						r1.createCell(5).setCellValue(
								Columns_header.get(5).getText());

						r1.createCell(6).setCellValue(
								Columns_header.get(6).getText());
						// Fetch the contents of table from row 3 to last 7th
						// row
						row = 2;
						rw = 14;

						while (rw < 17 && row < rows_table.size()) {
							// Fetch the contents of table from row 3 to last
							// 7th row
							// To locate columns(cells) of that specific row.
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

							// To calculate no of columns(cells) In that
							// specific row.
							int columns_count = Columns_row.size();
							// System.out.println(columns_count);
							Row r = s.createRow(rw);

							// Loop will execute till the last cell of that
							// specific row.
							for (int column = 0; column < columns_count; column++) {
								// To retrieve text from that specific cell.
								String celtext = Columns_row.get(column)
										.getText();
								r.createCell(column).setCellValue(celtext);
								Logs.infoLog("Cell Value Of row number " + row
										+ " and column number " + column
										+ " Is " + celtext);
							}
							row++;
							rw++;
						}

						FileOutputStream fos = new FileOutputStream(filePath);
						wb.write(fos);
						fos.close();
					}// compensate report
				}// excel file created

				else {
					// System.out.println("File not created");
					GlobalVariables.APPICATION_LOGS.error("File not created");
					Logs.errorLog("File not created");
				}

			} else {
				// System.out.println("Folder not created");
				GlobalVariables.APPICATION_LOGS.error("Folder not created");
				Logs.errorLog("Folder not created");
			}

			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);

		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing mT1_TCN_WriteXLSX keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, msg);
		}

	} // method mT1_TCN_WriteXLSX

	public static void mT1_TCN_ReadXLSX(String sheetName, String excelFileName,
			String msg, String subReportName) {
		try {// fetch the folder path to create work book
			String path = fetchReadExcelFolderPath();
			/*
			 * cleanPath(GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_FOLDER_PATH)) +
			 * TestBaseConstants.PATH_SIGN +
			 * TestBaseConstants.BASELINE_FOLDER_NAME + GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_NUMBER) +
			 * TestBaseConstants.PATH_SIGN + TestBaseConstants.ITERATION +
			 * GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_ITERATION_VALUE) +
			 * TestBaseConstants.PATH_SIGN +
			 * TestBaseConstants.BASELINE_BUILD_TYPE +
			 * TestBaseConstants.PATH_SIGN + GlobalVariables.testCaseIdentifier
			 * + ".xlsx";
			 */
			// System.out.println("Path of file is -->"+path);
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);

			// wb.createSheet(year);
			Sheet s = wb.getSheet(sheetName);

			if (subReportName.contains(TestBaseConstants.BWR_FORGONE_SALARY)) {
				// Compare first row

				// 1st cell
				String j = GlobalVariables.driver
						.findElement(
								By.xpath("//*[@class='report_list']/tbody/tr[1]/td[1]/p"))
						.getText();
				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						j);
				// 2nd cell
				j = GlobalVariables.driver
						.findElement(
								By.xpath("//*[@class='report_list']/tbody/tr[1]/td[2]/div"))
						.getText();
				updateError(0, 1, s.getRow(0).getCell(1).getStringCellValue(),
						j);

				// Compare second row
				// 1st cell
				j = GlobalVariables.driver
						.findElement(
								By.xpath("//*[@class='report_list']/tbody/tr[2]/td[1]/p"))
						.getText();
				updateError(1, 0, s.getRow(1).getCell(0).getStringCellValue(),
						j);
				// 2nd cell
				j = GlobalVariables.driver
						.findElement(
								By.xpath("//*[@class='report_list']/tbody/tr[2]/td[2]/div"))
						.getText();
				updateError(1, 0, s.getRow(1).getCell(1).getStringCellValue(),
						j);

			}

			FileOutputStream fos = new FileOutputStream(path);
			wb.write(fos);
			fos.close();

			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);

		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing mT1_TCN_ReadXLSX keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, msg);
		}
	} // method mT1_TCN_ReadXLSX

	public static void mT1_TCN_LST_WriteXLSX(String sheetName,
			String excelFileName, String msg, String subReportName) {
		boolean xlFileCreated = false;

		try {// fetch the folder path to create work book

			String folderPath = fetchWriteExcelFolderPath();

			/*
			 * cleanPath( GlobalVariables.CONFIG.getProperty(TestBaseConstants.
			 * BUILD_FOLDER_PATH)) +TestBaseConstants.PATH_SIGN
			 * +TestBaseConstants.BASELINE_FOLDER_NAME+
			 * GlobalVariables.CONFIG.getProperty
			 * (TestBaseConstants.BUILD_NUMBER) +TestBaseConstants.PATH_SIGN+
			 * TestBaseConstants.ITERATION+
			 * GlobalVariables.CONFIG.getProperty(TestBaseConstants
			 * .BUILD_ITERATION_VALUE)+ TestBaseConstants.PATH_SIGN+
			 * GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_TYPE);
			 */// System.out.println("Build folder path is "+folderPath);

			File preBuildFolderPath = new File(folderPath);

			// Create directory
			boolean folderCreated = preBuildFolderPath.mkdirs();
			String filePath = preBuildFolderPath + "/"
					+ GlobalVariables.testCaseIdentifier + ".xlsx";
			File filePath1 = new File(filePath);
			// System.out.println("File Path is -->"+filePath);
			if (folderCreated || preBuildFolderPath.exists()) {
				if (filePath1.exists()) {
					filePath1.delete();
					xlFileCreated = ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);
				} else {
					xlFileCreated = ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);
				}

				if (xlFileCreated) {
					// open stream to open file
					FileInputStream fis = new FileInputStream(filePath);
					Workbook wb = WorkbookFactory.create(fis);
					Sheet s = wb.getSheet(GlobalVariables.testCaseIdentifier);

					if (subReportName
							.contains(TestBaseConstants.BWR_OVERALL_EMPLOYEMENT)) {
						GlobalVariables.APPICATION_LOGS
								.info("fetching info from table");
						// To locate rows of table.
						// create first row
						Row r = s.createRow(0);
						String j = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@class='report_list']/tbody/tr[1]/td[1]/p"))
								.getText();

						GlobalVariables.APPICATION_LOGS.info(j);
						r.createCell(0).setCellValue(j);

						j = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@class='report_list']/tbody/tr[1]/td[2]/div/label"))
								.getText();
						r.createCell(1).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);
						j = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@class='report_list']/tbody/tr[1]/td[2]/div/label[2]"))
								.getText();
						r.createCell(2).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);

						r = s.createRow(1);
						j = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@class='report_list']/tbody/tr[1]/td[2]/div/label[3]"))
								.getText();
						r.createCell(1).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);

						j = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@class='report_list']/tbody/tr[1]/td[2]/div/label[4]"))
								.getText();
						r.createCell(2).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);

						r = s.createRow(2);
						j = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@class='report_list']/tbody/tr[1]/td[2]/div/label[5]"))
								.getText();
						r.createCell(1).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);

						j = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@class='report_list']/tbody/tr[1]/td[2]/div/label[6]"))
								.getText();
						r.createCell(2).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);

						r = s.createRow(3);
						j = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@class='report_list']/tbody/tr[2]/td[1]/p"))
								.getText();
						r.createCell(0).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);
						j = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@class='report_list']/tbody/tr[2]/td[2]/div/label"))
								.getText();
						r.createCell(1).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);

						j = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@class='report_list']/tbody/tr[2]/td[2]/div/label[2]"))
								.getText();
						r.createCell(2).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);

						r = s.createRow(4);
						j = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@class='report_list']/tbody/tr[2]/td[2]/div/label[3]"))
								.getText();
						r.createCell(1).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);

						j = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@class='report_list']/tbody/tr[2]/td[2]/div/label[4]"))
								.getText();
						r.createCell(2).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);

						r = s.createRow(5);
						j = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@class='report_list']/tbody/tr[2]/td[2]/div/label[5]"))
								.getText();
						r.createCell(1).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);

						j = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@class='report_list']/tbody/tr[2]/td[2]/div/label[6]"))
								.getText();
						r.createCell(2).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);

						// Create 6th row
						r = s.createRow(6);
						j = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@class='report_list']/tbody/tr[3]/td[1]/p"))
								.getText();
						GlobalVariables.APPICATION_LOGS.info(j);
						r.createCell(0).setCellValue(j);
						s.addMergedRegion(new CellRangeAddress(6, 6, 0, 1));
						GlobalVariables.APPICATION_LOGS.info(j);
						// Create 7th row
						r = s.createRow(7);

						j = GlobalVariables.driver.findElement(
								By.xpath("//*[@id='main']/ul/li[1]")).getText();
						r.createCell(0).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);
						/*
						 * j=GlobalVariables.driver.findElement
						 * (By.xpath("//*[@id='main']/ul/li[1]/span"
						 * )).getText(); r.createCell(1).setCellValue(j);
						 * GlobalVariables.APPICATION_LOGS.info(j);
						 */

						j = GlobalVariables.driver.findElement(
								By.xpath("//*[@id='main']/ul/li[2]")).getText();
						r.createCell(2).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);
						/*
						 * j=GlobalVariables.driver.findElement
						 * (By.xpath("//*[@id='main']/ul/li[2]/span"
						 * )).getText(); r.createCell(3).setCellValue(j);
						 * GlobalVariables.APPICATION_LOGS.info(j);
						 */
						// Create 8th row
						r = s.createRow(8);
						j = GlobalVariables.driver.findElement(
								By.xpath("//*[@id='main']/ul/li[3]/span[1]"))
								.getText();
						r.createCell(0).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);
						j = GlobalVariables.driver.findElement(
								By.xpath("//*[@id='main']/ul/li[3]/span[2]"))
								.getText();
						r.createCell(1).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);
						j = GlobalVariables.driver.findElement(
								By.xpath("//*[@id='main']/ul/li[4]/span[1]"))
								.getText();
						r.createCell(2).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);
						j = GlobalVariables.driver.findElement(
								By.xpath("//*[@id='main']/ul/li[4]/span[2]"))
								.getText();
						r.createCell(3).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);

						// Create 9th row
						r = s.createRow(9);
						j = GlobalVariables.driver.findElement(
								By.xpath("//*[@id='main']/ul/li[5]/span[1]"))
								.getText();
						r.createCell(0).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);
						j = GlobalVariables.driver.findElement(
								By.xpath("//*[@id='main']/ul/li[5]/span[2]"))
								.getText();
						r.createCell(1).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);
						j = GlobalVariables.driver.findElement(
								By.xpath("//*[@id='main']/ul/li[6]/span[1]"))
								.getText();
						r.createCell(2).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);
						j = GlobalVariables.driver.findElement(
								By.xpath("//*[@id='main']/ul/li[6]/span[2]"))
								.getText();
						r.createCell(3).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);

						// Create 10th row
						r = s.createRow(10);
						j = GlobalVariables.driver.findElement(
								By.xpath("//*[@id='main']/ul/li[7]/span[1]"))
								.getText();
						r.createCell(0).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);
						j = GlobalVariables.driver.findElement(
								By.xpath("//*[@id='main']/ul/li[7]/span[2]"))
								.getText();
						r.createCell(1).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);
						j = GlobalVariables.driver.findElement(
								By.xpath("//*[@id='main']/ul/li[8]/span[1]"))
								.getText();
						r.createCell(2).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);
						j = GlobalVariables.driver.findElement(
								By.xpath("//*[@id='main']/ul/li[8]/span[2]"))
								.getText();
						r.createCell(3).setCellValue(j);
						GlobalVariables.APPICATION_LOGS.info(j);

					}// Overall employment
					else if (subReportName
							.contains(TestBaseConstants.BWR_TOP_15_EMPLOYESS)) {
						GlobalVariables.APPICATION_LOGS
								.info("Subreport name is " + subReportName);

						Row r = s.createRow(0);

						String m = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@id='main']/table/tbody/tr/td/p"))
								.getText();
						r.createCell(0).setCellValue(m);
						s.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
						int k = 1;

						while (k < 16)
							for (int i = 1; i <= 15; i++) {
								r = s.createRow(k);
								m = GlobalVariables.driver
										.findElement(
												By.xpath("//*[@id='main']/table/tbody/tr/td/ul/li["
														+ i + "]" + "/span[1]"))
										.getText();

								r.createCell(0).setCellValue(m);
								m = GlobalVariables.driver
										.findElement(
												By.xpath("//*[@id='main']/table/tbody/tr/td/ul/li["
														+ i + "]" + "/span[2]"))
										.getText();
								r.createCell(1).setCellValue(m);
								m = GlobalVariables.driver
										.findElement(
												By.xpath("//*[@id='main']/table/tbody/tr/td/ul/li["
														+ i + "]" + "/span[3]"))
										.getText();
								r.createCell(2).setCellValue(m);
								k++;
							}
					}

					else if (subReportName
							.contains(TestBaseConstants.BWR_BASE_SALRY_SIGNING_BONUS_OTHER_GC)) {
						GlobalVariables.APPICATION_LOGS
								.info("Subreport name is " + subReportName);
						// first row fetch
						Row r = s.createRow(0);
						// 1st cell
						String a = getObjectValue("report_BS_SB_OGC_row1_col1")
								.getText();
						r.createCell(0).setCellValue(a);
						// 2nd cell
						a = getObjectValue("report_BS_SB_OGC_row1_col2")
								.getText();
						r.createCell(1).setCellValue(a);

						// 2nd row fetch
						r = s.createRow(1);
						// 3rd cell
						a = getObjectValue("report_BS_SB_OGC_row2_col1")
								.getText();
						r.createCell(0).setCellValue(a);
						// 4th cell
						a = getObjectValue("report_BS_SB_OGC_row2_col2")
								.getText();
						r.createCell(1).setCellValue(a);

						// 3rd row fetch
						r = s.createRow(2);
						a = getObjectValue("report_BS_SB_OGC_row3_col1")
								.getText();
						r.createCell(0).setCellValue(a);
						s.addMergedRegion(new CellRangeAddress(2, 2, 0, 1));

						// 4th row fetch
						r = s.createRow(3);
						a = getObjectValue("report_BS_SB_OGC_list1").getText();
						r.createCell(0).setCellValue(a);
						a = getObjectValue("report_BS_SB_OGC_list2").getText();
						r.createCell(1).setCellValue(a);

						// 5th row fetch
						r = s.createRow(4);
						a = getObjectValue("report_BS_SB_OGC_list3").getText();
						r.createCell(0).setCellValue(a);
						a = getObjectValue("report_BS_SB_OGC_list4").getText();
						r.createCell(1).setCellValue(a);

						// 6th row fetch
						r = s.createRow(5);
						a = getObjectValue("report_BS_SB_OGC_list5").getText();
						r.createCell(0).setCellValue(a);
						a = getObjectValue("report_BS_SB_OGC_list6").getText();
						r.createCell(1).setCellValue(a);

					} else if (subReportName
							.contains(TestBaseConstants.BWR_INTERNSHIPS)) {
						GlobalVariables.APPICATION_LOGS
								.info("Subreport name is " + subReportName);
						// first row fetch
						Row r = s.createRow(0);
						String m = getObjectValue("report_Internships_cell1")
								.getText();
						r.createCell(0).setCellValue(m);
						s.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
						int k = 1;

						while (k < 16)
							for (int i = 1; i <= 16; i++) {
								r = s.createRow(k);
								m = GlobalVariables.driver
										.findElement(
												By.xpath("//*[@id='main']/table/tbody/tr[1]/td/ul/li["
														+ i + "]" + "/span[1]"))
										.getText();

								r.createCell(0).setCellValue(m);
								m = GlobalVariables.driver
										.findElement(
												By.xpath("//*[@id='main']/table/tbody/tr[1]/td/ul/li["
														+ i + "]" + "/span[2]"))
										.getText();
								r.createCell(1).setCellValue(m);
								m = GlobalVariables.driver
										.findElement(
												By.xpath("//*[@id='main']/table/tbody/tr[1]/td/ul/li["
														+ i + "]" + "/span[3]"))
										.getText();
								r.createCell(2).setCellValue(m);
								k++;
							}
						// fetch div contents
						r = s.createRow(17);
						// 1st cell
						m = getObjectValue("report_Internships_cell2")
								.getText();
						r.createCell(0).setCellValue(m);
						// 2nd cell
						m = getObjectValue("report_Internships_cell3")
								.getText();
						r.createCell(1).setCellValue(m);

						r = s.createRow(18);
						// 1st cell
						m = getObjectValue("report_Internships_cell4")
								.getText();
						r.createCell(0).setCellValue(m);
						// 2nd cell
						m = getObjectValue("report_Internships_cell5")
								.getText();
						r.createCell(1).setCellValue(m);

						r = s.createRow(19);
						// 1st cell
						m = getObjectValue("report_Internships_cell6")
								.getText();
						r.createCell(0).setCellValue(m);
						// 2nd cell
						m = getObjectValue("report_Internships_cell7")
								.getText();
						r.createCell(1).setCellValue(m);

					}
					FileOutputStream fos = new FileOutputStream(filePath);
					wb.write(fos);
					fos.close();
				}// excel file created

				else {
					// System.out.println("File not created");
					GlobalVariables.APPICATION_LOGS.error("File not created");
					Logs.errorLog("File not created");
				}

			} else {
				// System.out.println("Folder not created");
				GlobalVariables.APPICATION_LOGS.error("Folder not created");
				Logs.errorLog("Folder not created");
			}

			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);

		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing mT1_TCN_LST_WriteXLSX keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, msg);
		}

	} // method mT1_TCN_LST_WriteXLSX

	public static void mT1_TCN_LST_READXLSX(String sheetName,
			String excelFileName, String msg, String subReportName) {
		try {// fetch the folder path to create work book
			String path = fetchReadExcelFolderPath();
			/*
			 * cleanPath(GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_FOLDER_PATH)) +
			 * TestBaseConstants.PATH_SIGN +
			 * TestBaseConstants.BASELINE_FOLDER_NAME + GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_NUMBER) +
			 * TestBaseConstants.PATH_SIGN + TestBaseConstants.ITERATION +
			 * GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_ITERATION_VALUE) +
			 * TestBaseConstants.PATH_SIGN +
			 * TestBaseConstants.BASELINE_BUILD_TYPE +
			 * TestBaseConstants.PATH_SIGN + GlobalVariables.testCaseIdentifier
			 * + ".xlsx";
			 */
			// System.out.println("Path of file is -->"+path);
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);

			// wb.createSheet(year);
			Sheet s = wb.getSheet(sheetName);

			if (subReportName
					.contains(TestBaseConstants.BWR_OVERALL_EMPLOYEMENT)) {
				// Compare first row

				// 1st cell
				String j = GlobalVariables.driver
						.findElement(
								By.xpath("//*[@class='report_list']/tbody/tr[1]/td[1]/p"))
						.getText();
				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						j);
				// 2nd cell
				j = GlobalVariables.driver
						.findElement(
								By.xpath("//*[@class='report_list']/tbody/tr[1]/td[2]/div/label"))
						.getText();
				updateError(0, 1, s.getRow(0).getCell(1).getStringCellValue(),
						j);
				// 3rd cell
				j = GlobalVariables.driver
						.findElement(
								By.xpath("//*[@class='report_list']/tbody/tr[1]/td[2]/div/label[2]"))
						.getText();
				updateError(0, 2, s.getRow(0).getCell(2).getStringCellValue(),
						j);

				// 2nd row
				j = GlobalVariables.driver
						.findElement(
								By.xpath("//*[@class='report_list']/tbody/tr[1]/td[2]/div/label[3]"))
						.getText();
				updateError(1, 1, s.getRow(1).getCell(1).getStringCellValue(),
						j);
				j = GlobalVariables.driver
						.findElement(
								By.xpath("//*[@class='report_list']/tbody/tr[1]/td[2]/div/label[4]"))
						.getText();
				updateError(1, 2, s.getRow(1).getCell(2).getStringCellValue(),
						j);

				// 3rd row
				j = GlobalVariables.driver
						.findElement(
								By.xpath("//*[@class='report_list']/tbody/tr[1]/td[2]/div/label[5]"))
						.getText();
				updateError(2, 1, s.getRow(2).getCell(1).getStringCellValue(),
						j);
				j = GlobalVariables.driver
						.findElement(
								By.xpath("//*[@class='report_list']/tbody/tr[1]/td[2]/div/label[6]"))
						.getText();
				updateError(2, 2, s.getRow(2).getCell(2).getStringCellValue(),
						j);

				// 4th row
				j = GlobalVariables.driver
						.findElement(
								By.xpath("//*[@class='report_list']/tbody/tr[2]/td[1]/p"))
						.getText();
				GlobalVariables.APPICATION_LOGS.info(j);
				updateError(3, 0, s.getRow(3).getCell(0).getStringCellValue(),
						j);

				j = GlobalVariables.driver
						.findElement(
								By.xpath("//*[@class='report_list']/tbody/tr[2]/td[2]/div/label[1]"))
						.getText();
				updateError(3, 1, s.getRow(3).getCell(1).getStringCellValue(),
						j);

				j = GlobalVariables.driver
						.findElement(
								By.xpath("//*[@class='report_list']/tbody/tr[2]/td[2]/div/label[2]"))
						.getText();
				updateError(3, 2, s.getRow(3).getCell(2).getStringCellValue(),
						j);

				// 5th row
				j = GlobalVariables.driver
						.findElement(
								By.xpath("//*[@class='report_list']/tbody/tr[2]/td[2]/div/label[3]"))
						.getText();
				updateError(4, 1, s.getRow(4).getCell(1).getStringCellValue(),
						j);
				j = GlobalVariables.driver
						.findElement(
								By.xpath("//*[@class='report_list']/tbody/tr[2]/td[2]/div/label[4]"))
						.getText();
				updateError(4, 2, s.getRow(4).getCell(2).getStringCellValue(),
						j);

				// 6th row
				j = GlobalVariables.driver
						.findElement(
								By.xpath("//*[@class='report_list']/tbody/tr[2]/td[2]/div/label[5]"))
						.getText();
				updateError(5, 1, s.getRow(5).getCell(1).getStringCellValue(),
						j);
				j = GlobalVariables.driver
						.findElement(
								By.xpath("//*[@class='report_list']/tbody/tr[2]/td[2]/div/label[6]"))
						.getText();
				updateError(5, 2, s.getRow(5).getCell(2).getStringCellValue(),
						j);

				// 7th row
				j = GlobalVariables.driver
						.findElement(
								By.xpath("//*[@class='report_list']/tbody/tr[3]/td[1]/p"))
						.getText();
				updateError(6, 1, s.getRow(6).getCell(0).getStringCellValue(),
						j);

				// 8th row
				j = GlobalVariables.driver.findElement(
						By.xpath("//*[@id='main']/ul/li[1]")).getText();
				updateError(7, 0, s.getRow(7).getCell(0).getStringCellValue(),
						j);
				j = GlobalVariables.driver.findElement(
						By.xpath("//*[@id='main']/ul/li[2]")).getText();
				updateError(7, 2, s.getRow(7).getCell(2).getStringCellValue(),
						j);

				// 9th row
				j = GlobalVariables.driver.findElement(
						By.xpath("//*[@id='main']/ul/li[3]/span[1]")).getText();
				updateError(8, 0, s.getRow(8).getCell(0).getStringCellValue(),
						j);

				j = GlobalVariables.driver.findElement(
						By.xpath("//*[@id='main']/ul/li[3]/span[2]")).getText();
				updateError(8, 1, s.getRow(8).getCell(1).getStringCellValue(),
						j);

				j = GlobalVariables.driver.findElement(
						By.xpath("//*[@id='main']/ul/li[4]/span[1]")).getText();
				updateError(8, 2, s.getRow(8).getCell(2).getStringCellValue(),
						j);

				j = GlobalVariables.driver.findElement(
						By.xpath("//*[@id='main']/ul/li[4]/span[2]")).getText();
				updateError(8, 3, s.getRow(8).getCell(3).getStringCellValue(),
						j);

				// 10th row
				j = GlobalVariables.driver.findElement(
						By.xpath("//*[@id='main']/ul/li[5]/span[1]")).getText();
				updateError(9, 0, s.getRow(9).getCell(0).getStringCellValue(),
						j);
				j = GlobalVariables.driver.findElement(
						By.xpath("//*[@id='main']/ul/li[5]/span[2]")).getText();
				updateError(9, 1, s.getRow(9).getCell(1).getStringCellValue(),
						j);
				j = GlobalVariables.driver.findElement(
						By.xpath("//*[@id='main']/ul/li[6]/span[1]")).getText();
				updateError(9, 2, s.getRow(9).getCell(2).getStringCellValue(),
						j);
				j = GlobalVariables.driver.findElement(
						By.xpath("//*[@id='main']/ul/li[6]/span[2]")).getText();
				updateError(9, 3, s.getRow(9).getCell(3).getStringCellValue(),
						j);

				// 11th row
				j = GlobalVariables.driver.findElement(
						By.xpath("//*[@id='main']/ul/li[7]/span[1]")).getText();
				updateError(10, 0,
						s.getRow(10).getCell(0).getStringCellValue(), j);
				j = GlobalVariables.driver.findElement(
						By.xpath("//*[@id='main']/ul/li[7]/span[2]")).getText();
				updateError(10, 1,
						s.getRow(10).getCell(1).getStringCellValue(), j);
				j = GlobalVariables.driver.findElement(
						By.xpath("//*[@id='main']/ul/li[8]/span[1]")).getText();
				updateError(10, 2,
						s.getRow(10).getCell(2).getStringCellValue(), j);
				j = GlobalVariables.driver.findElement(
						By.xpath("//*[@id='main']/ul/li[8]/span[2]")).getText();
				updateError(10, 3,
						s.getRow(10).getCell(3).getStringCellValue(), j);

			}

			else if (subReportName
					.contains(TestBaseConstants.BWR_TOP_15_EMPLOYESS)) {
				// 1st row
				String m = GlobalVariables.driver.findElement(
						By.xpath("//*[@id='main']/table/tbody/tr/td/p"))
						.getText();
				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						m);

				// 2nd row
				int k = 1;

				while (k < 16)
					for (int i = 1; i <= 15; i++) {
						m = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@id='main']/table/tbody/tr/td/ul/li["
												+ i + "]" + "/span[1]"))
								.getText();
						updateError(k, 0, s.getRow(k).getCell(0)
								.getStringCellValue(), m);
						m = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@id='main']/table/tbody/tr/td/ul/li["
												+ i + "]" + "/span[2]"))
								.getText();
						updateError(k, 0, s.getRow(k).getCell(1)
								.getStringCellValue(), m);
						m = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@id='main']/table/tbody/tr/td/ul/li["
												+ i + "]" + "/span[3]"))
								.getText();
						updateError(k, 0, s.getRow(k).getCell(2)
								.getStringCellValue(), m);
						k++;
					}

			} else if (subReportName
					.contains(TestBaseConstants.BWR_BASE_SALRY_SIGNING_BONUS_OTHER_GC)) {
				GlobalVariables.APPICATION_LOGS.info("Subreport name is "
						+ subReportName);
				// compare contents of 1st row
				// 1st cell
				String a = getObjectValue("report_BS_SB_OGC_row1_col1")
						.getText();
				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						a);
				// second cell
				a = getObjectValue("report_BS_SB_OGC_row1_col2").getText();
				updateError(0, 1, s.getRow(0).getCell(1).getStringCellValue(),
						a);

				// Compare contents of 2nd row
				// 1st cell
				a = getObjectValue("report_BS_SB_OGC_row2_col1").getText();
				updateError(1, 0, s.getRow(1).getCell(0).getStringCellValue(),
						a);
				// second cell
				a = getObjectValue("report_BS_SB_OGC_row2_col2").getText();
				updateError(1, 1, s.getRow(1).getCell(1).getStringCellValue(),
						a);

				// compare contents of 3rd row
				a = getObjectValue("report_BS_SB_OGC_row3_col1").getText();
				updateError(2, 0, s.getRow(2).getCell(0).getStringCellValue(),
						a);

				// compare contents of 4th row
				a = getObjectValue("report_BS_SB_OGC_list1").getText();
				updateError(3, 0, s.getRow(3).getCell(0).getStringCellValue(),
						a);
				a = getObjectValue("report_BS_SB_OGC_list2").getText();
				updateError(3, 1, s.getRow(3).getCell(1).getStringCellValue(),
						a);

				// compare contents of 5th row
				a = getObjectValue("report_BS_SB_OGC_list3").getText();
				updateError(4, 0, s.getRow(4).getCell(0).getStringCellValue(),
						a);
				a = getObjectValue("report_BS_SB_OGC_list4").getText();
				updateError(4, 1, s.getRow(4).getCell(1).getStringCellValue(),
						a);

				// compare contents of 6th row
				a = getObjectValue("report_BS_SB_OGC_list5").getText();
				updateError(5, 0, s.getRow(5).getCell(0).getStringCellValue(),
						a);
				a = getObjectValue("report_BS_SB_OGC_list6").getText();
				updateError(5, 1, s.getRow(5).getCell(1).getStringCellValue(),
						a);

			} else if (subReportName
					.contains(TestBaseConstants.BWR_INTERNSHIPS)) {
				// 1st row 1st cell compare
				String m = getObjectValue("report_Internships_cell1").getText();
				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						m);

				int k = 1;

				while (k < 16)
					for (int i = 1; i <= 16; i++) {

						m = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@id='main']/table/tbody/tr[1]/td/ul/li["
												+ i + "]" + "/span[1]"))
								.getText();

						updateError(k, 0, s.getRow(k).getCell(0)
								.getStringCellValue(), m);
						m = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@id='main']/table/tbody/tr[1]/td/ul/li["
												+ i + "]" + "/span[2]"))
								.getText();
						updateError(k, 1, s.getRow(k).getCell(1)
								.getStringCellValue(), m);
						m = GlobalVariables.driver
								.findElement(
										By.xpath("//*[@id='main']/table/tbody/tr[1]/td/ul/li["
												+ i + "]" + "/span[3]"))
								.getText();
						updateError(k, 2, s.getRow(k).getCell(2)
								.getStringCellValue(), m);
						k++;
					}

				// fetch div contents
				// 1st cell compare
				m = getObjectValue("report_Internships_cell2").getText();
				updateError(17, 0,
						s.getRow(17).getCell(0).getStringCellValue(), m);
				// 2nd cell compare
				m = getObjectValue("report_Internships_cell3").getText();
				updateError(17, 1,
						s.getRow(17).getCell(1).getStringCellValue(), m);

				// 1st cell
				m = getObjectValue("report_Internships_cell4").getText();
				updateError(18, 0,
						s.getRow(18).getCell(0).getStringCellValue(), m);
				// 2nd cell compare
				m = getObjectValue("report_Internships_cell5").getText();
				updateError(18, 1,
						s.getRow(18).getCell(1).getStringCellValue(), m);

				// 1st cell
				m = getObjectValue("report_Internships_cell6").getText();
				updateError(19, 0,
						s.getRow(19).getCell(0).getStringCellValue(), m);
				// 2nd cell compare
				m = getObjectValue("report_Internships_cell7").getText();
				updateError(19, 1,
						s.getRow(19).getCell(1).getStringCellValue(), m);

			}
			FileOutputStream fos = new FileOutputStream(path);
			wb.write(fos);
			fos.close();

			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);

		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing mT1_TCN_ReadXLSX keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, msg);
		}
	} // method mT1_TCN_ReadXLSX

	/********************************************************************************************
	 * Author : DivyaRaju.R LastModifiedDate : 1st may 2015 Method name :
	 * mT1_TH2_TCN_WriteXLSX Description : This method is used for fetching data
	 * from 1220 application and writing that excel
	 *
	 *********************************************************************************************/
	public static void T2_DIV_TH1_TCN_WriteXLSX(String sheetName,
			String excelFileName, String msg, String subReportName) {
		boolean xlFileCreated = false;

		try {
			// fetch the folder path to create work book

			String folderPath = fetchWriteExcelFolderPath();
			/*
			 * cleanPath( GlobalVariables.CONFIG.getProperty(TestBaseConstants.
			 * BUILD_FOLDER_PATH)) +TestBaseConstants.PATH_SIGN
			 * +TestBaseConstants.BASELINE_FOLDER_NAME+
			 * GlobalVariables.CONFIG.getProperty
			 * (TestBaseConstants.BUILD_NUMBER) +TestBaseConstants.PATH_SIGN+
			 * TestBaseConstants.ITERATION+
			 * GlobalVariables.CONFIG.getProperty(TestBaseConstants
			 * .BUILD_ITERATION_VALUE)+ TestBaseConstants.PATH_SIGN+
			 * GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_TYPE);
			 */
			// System.out.println("Build folder path is "+folderPath);

			File preBuildFolderPath = new File(folderPath);

			// Create directory
			boolean folderCreated = preBuildFolderPath.mkdirs();
			String filePath = preBuildFolderPath + "/"
					+ GlobalVariables.testCaseIdentifier + ".xlsx";
			File filePath1 = new File(filePath);
			// System.out.println("File Path is -->"+filePath);
			if (folderCreated || preBuildFolderPath.exists()) {
				if (filePath1.exists()) {
					filePath1.delete();
					xlFileCreated = ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);
				} else {
					xlFileCreated = ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);
				}

				if (xlFileCreated) {

					FileInputStream fis = new FileInputStream(filePath);
					Workbook wb = WorkbookFactory.create(fis);
					// wb.createSheet(year);
					Sheet s = wb.getSheet(sheetName);
					//
					if (subReportName
							.equalsIgnoreCase(TestBaseConstants.BWR_BASE_SALARY_BY_FUNCTIONAL_AREA)
							|| subReportName
									.equalsIgnoreCase(TestBaseConstants.BWR_BASE_SALARY_BY_INDUSTRY)
							|| subReportName
									.equalsIgnoreCase(TestBaseConstants.BWR_BASE_SALARY_BY_GR)
							|| subReportName
									.equalsIgnoreCase(TestBaseConstants.BWR_BASE_SALARY_BY_NAR)
							|| subReportName
									.equalsIgnoreCase(TestBaseConstants.BWR_BASE_SALARY_BY_EXP_LEVEL)) {
						GlobalVariables.APPICATION_LOGS
								.info("Subreport name is " + subReportName);
						// first row fetch
						Row r = s.createRow(0);
						String m = getObjectValue("report_BSFA_cell1")
								.getText();
						r.createCell(0).setCellValue(m);
						s.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

						// Fetch 2nd row
						r = s.createRow(1);
						m = getObjectValue("report_BSFA_cell2").getText();
						r.createCell(2).setCellValue(m);
						s.addMergedRegion(new CellRangeAddress(1, 1, 2, 3));

						// Fetch table contents
						WebElement mytable = getObjectValue("report_BSFA_Table");
						// To locate rows of table.
						List<WebElement> rows_table = mytable.findElements(By
								.tagName(TestBaseConstants.TABLE_ROW_TAG));
						int rows_count = rows_table.size();
						r = s.createRow(2);
						List<WebElement> Columns_header = rows_table
								.get(0)
								.findElements(
										By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

						for (int b = 0; b < Columns_header.size(); b++) {
							r.createCell(b).setCellValue(
									Columns_header.get(b).getText());
						}

						int k = 3, row = 1;
						while (k <= rows_count + 1 && row <= rows_count + 1) {
							r = s.createRow(k);

							// To locate columns(cells) of that specific row.
							List<WebElement> Columns_row = rows_table
									.get(row)
									.findElements(
											By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

							// To calculate no of columns(cells) In that
							// specific row.
							int columns_count = Columns_row.size();
							// Loop will execute till the last cell of that
							// specific row.
							for (int column = 0; column < columns_count; column++) {
								// To retrieve text from that specific cell.
								String celtext = Columns_row.get(column)
										.getText();
								r.createCell(column).setCellValue(celtext);
								Logs.infoLog("Cell Value Of row number " + row
										+ " and column number " + column
										+ " Is " + celtext);
								GlobalVariables.APPICATION_LOGS
										.info("Cell Value Of row number " + row
												+ " and column number "
												+ column + " Is " + celtext);
							}
							row++;
							k++;
						}
					}

					FileOutputStream fos = new FileOutputStream(filePath);
					wb.write(fos);
					fos.close();
				} else {
					// System.out.println("File not created");
					GlobalVariables.APPICATION_LOGS.error("File not created");
					Logs.errorLog("File not created");
				}

			} else {
				// System.out.println("File not created");
				GlobalVariables.APPICATION_LOGS.error("Folder not created");
				Logs.errorLog("Folder not created");
			}

			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);

		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing mT2_DIV_TH1_TCN_Write_keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, msg);
		}
	}// T2_DIV_TH1_TCN_write

	public static void T2_DIV_TH1_TCN_ReadXLSX(String excelSheetName,
			String automationId, String subReportName, String msg) {
		GlobalVariables.testCaseIdentifier = automationId;
		try {
			String path = cleanPath(GlobalVariables.CONFIG
					.getProperty(TestBaseConstants.BUILD_FOLDER_PATH))
					+ TestBaseConstants.PATH_SIGN
					+ TestBaseConstants.BASELINE_FOLDER_NAME
					+ GlobalVariables.CONFIG
							.getProperty(TestBaseConstants.BUILD_NUMBER)
					+ TestBaseConstants.PATH_SIGN
					+ TestBaseConstants.ITERATION
					+ GlobalVariables.CONFIG
							.getProperty(TestBaseConstants.BUILD_ITERATION_VALUE)
					+ TestBaseConstants.PATH_SIGN
					+ TestBaseConstants.BASELINE_BUILD_TYPE
					+ TestBaseConstants.PATH_SIGN
					+ GlobalVariables.testCaseIdentifier + ".xlsx";
			// System.out.println("Path of file is -->"+path);
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);

			// wb.createSheet(year);
			Sheet s = wb.getSheet(excelSheetName);

			if (subReportName
					.equalsIgnoreCase(TestBaseConstants.BWR_BASE_SALARY_BY_FUNCTIONAL_AREA)
					|| subReportName
							.equalsIgnoreCase(TestBaseConstants.BWR_BASE_SALARY_BY_INDUSTRY)
					|| subReportName
							.equalsIgnoreCase(TestBaseConstants.BWR_BASE_SALARY_BY_GR)
					|| subReportName
							.equalsIgnoreCase(TestBaseConstants.BWR_BASE_SALARY_BY_NAR)
					|| subReportName
							.equalsIgnoreCase(TestBaseConstants.BWR_BASE_SALARY_BY_EXP_LEVEL)) {
				// Compare 1st row
				String m = getObjectValue("report_BSFA_cell1").getText();
				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						m);

				// Compare 2nd row
				m = getObjectValue("report_BSFA_cell2").getText();
				updateError(1, 2, s.getRow(1).getCell(2).getStringCellValue(),
						m);

				// Compare table contents
				WebElement mytable = getObjectValue("report_BSFA_Table");
				// To locate rows of table.
				List<WebElement> rows_table = mytable.findElements(By
						.tagName(TestBaseConstants.TABLE_ROW_TAG));

				int rows_count = rows_table.size();
				// compare header contents
				List<WebElement> Columns_header = rows_table.get(0)
						.findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				for (int b = 0; b < Columns_header.size(); b++) {
					updateError(2, b, s.getRow(2).getCell(b)
							.getStringCellValue(), Columns_header.get(b)
							.getText());
				}

				int k = 3, row = 1;
				while (k <= rows_count + 1 && row <= rows_count + 1) { // To
																		// locate
																		// columns(cells)
																		// of
																		// that
																		// specific
																		// row.
					List<WebElement> Columns_row = rows_table
							.get(row)
							.findElements(
									By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

					// To calculate no of columns(cells) In that specific row.
					int columns_count = Columns_row.size();
					for (int column = 0; column < columns_count; column++) {
						// To retrieve text from that specific cell.
						String celtext = Columns_row.get(column).getText();
						updateError(k, column, s.getRow(k).getCell(column)
								.getStringCellValue(), celtext);
					}
					row++;
					k++;

				}

			}

			FileOutputStream fos = new FileOutputStream(path);
			wb.write(fos);
			fos.close();

			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);
		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing T2_DIV_TH1_TCN_ReadXLSX keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, ermsg);
		}

	}// T2_DIV_TH1_TCN_ReadXLSX

	public static void mT3_TH2_TCN_ReadXLSX(String excelSheetName,
			String automationId, String tableXpath, String tableXpath2,
			String tableXpath3, String subReportName, String msg) {
		GlobalVariables.testCaseIdentifier = automationId;
		try {
			String path = fetchReadExcelFolderPath();
			/*
			 * cleanPath(GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_FOLDER_PATH)) +
			 * TestBaseConstants.PATH_SIGN +
			 * TestBaseConstants.BASELINE_FOLDER_NAME + GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_NUMBER) +
			 * TestBaseConstants.PATH_SIGN + TestBaseConstants.ITERATION +
			 * GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_ITERATION_VALUE) +
			 * TestBaseConstants.PATH_SIGN +
			 * TestBaseConstants.BASELINE_BUILD_TYPE +
			 * TestBaseConstants.PATH_SIGN + GlobalVariables.testCaseIdentifier
			 * + ".xlsx";
			 */
			// System.out.println("Path of file is -->"+path);
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);

			// wb.createSheet(year);
			Sheet s = wb.getSheet(excelSheetName);

			if (subReportName
					.equalsIgnoreCase(TestBaseConstants.MBA_COMPENSATION_REPORT)) {
				WebElement mytable = GlobalVariables.driver.findElement(By
						.xpath(tableXpath));
				// To locate rows of table.
				List<WebElement> rows_table = mytable.findElements(By
						.tagName(TestBaseConstants.TABLE_ROW_TAG));
				// First row of header
				List<WebElement> Columns_header = rows_table.get(0)
						.findElements(
								By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				updateError(0, 0, s.getRow(0).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				// second row of header
				Columns_header = rows_table.get(1).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				updateError(1, 1, s.getRow(1).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());

				updateError(1, 2, s.getRow(1).getCell(2).getStringCellValue(),
						Columns_header.get(2).getText());
				updateError(1, 3, s.getRow(1).getCell(3).getStringCellValue(),
						Columns_header.get(3).getText());
				updateError(1, 4, s.getRow(1).getCell(4).getStringCellValue(),
						Columns_header.get(4).getText());
				updateError(1, 5, s.getRow(1).getCell(5).getStringCellValue(),
						Columns_header.get(5).getText());
				updateError(1, 6, s.getRow(1).getCell(6).getStringCellValue(),
						Columns_header.get(6).getText());
				// Fetch the contents of table from row 3 to last 7th row
				for (int row = 2; row < rows_table.size(); row++) {
					// To locate columns(cells) of that specific row.
					List<WebElement> Columns_row = rows_table
							.get(row)
							.findElements(
									By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

					// To calculate no of columns(cells) In that specific row.
					int columns_count = Columns_row.size();

					for (int column = 0; column < columns_count; column++) {
						// To retrieve text from that specific cell.
						updateError(row, column, s.getRow(row).getCell(column)
								.getStringCellValue(), Columns_row.get(column)
								.getText());
					}
				}

				// Table 2

				mytable = GlobalVariables.driver.findElement(By
						.xpath(tableXpath2));
				// To locate rows of table.
				rows_table = mytable.findElements(By
						.tagName(TestBaseConstants.TABLE_ROW_TAG));
				// System.out.println(rows_table.size());
				Columns_header = rows_table.get(0).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

				updateError(6, 0, s.getRow(6).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());

				Columns_header = rows_table.get(1).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				updateError(7, 1, s.getRow(7).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());

				updateError(7, 2, s.getRow(7).getCell(2).getStringCellValue(),
						Columns_header.get(2).getText());
				updateError(7, 3, s.getRow(7).getCell(3).getStringCellValue(),
						Columns_header.get(3).getText());
				updateError(7, 4, s.getRow(7).getCell(4).getStringCellValue(),
						Columns_header.get(4).getText());
				updateError(7, 5, s.getRow(7).getCell(5).getStringCellValue(),
						Columns_header.get(5).getText());
				updateError(7, 6, s.getRow(7).getCell(6).getStringCellValue(),
						Columns_header.get(6).getText());

				int rw = 8;
				int row = 2;
				while (rw < 11 && row < rows_table.size()) {
					// To locate columns(cells) of that specific row.
					List<WebElement> Columns_row = rows_table
							.get(row)
							.findElements(
									By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

					// To calculate no of columns(cells) In that specific row.
					int columns_count = Columns_row.size();

					// Loop will execute till the last cell of that specific
					// row.
					for (int column = 0; column < columns_count; column++) {
						updateError(row, column, s.getRow(rw).getCell(column)
								.getStringCellValue(), Columns_row.get(column)
								.getText());
					}
					row++;
					rw++;
				}

				// Table3
				mytable = GlobalVariables.driver.findElement(By
						.xpath(tableXpath3));
				// To locate rows of table.
				rows_table = mytable.findElements(By
						.tagName(TestBaseConstants.TABLE_ROW_TAG));
				Columns_header = rows_table.get(0).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));
				updateError(12, 0,
						s.getRow(12).getCell(0).getStringCellValue(),
						Columns_header.get(0).getText());
				Columns_header = rows_table.get(1).findElements(
						By.tagName(TestBaseConstants.TABLE_HEAD_TAG));

				updateError(13, 1,
						s.getRow(13).getCell(1).getStringCellValue(),
						Columns_header.get(1).getText());

				updateError(13, 2,
						s.getRow(13).getCell(2).getStringCellValue(),
						Columns_header.get(2).getText());
				updateError(13, 3,
						s.getRow(13).getCell(3).getStringCellValue(),
						Columns_header.get(3).getText());
				updateError(13, 4,
						s.getRow(13).getCell(4).getStringCellValue(),
						Columns_header.get(4).getText());
				updateError(13, 5,
						s.getRow(13).getCell(5).getStringCellValue(),
						Columns_header.get(5).getText());
				updateError(13, 6,
						s.getRow(13).getCell(6).getStringCellValue(),
						Columns_header.get(6).getText());

				rw = 14;
				row = 2;
				while (rw < 17 && row < rows_table.size()) {
					// To locate columns(cells) of that specific row.
					List<WebElement> Columns_row = rows_table
							.get(row)
							.findElements(
									By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));

					// To calculate no of columns(cells) In that specific row.
					int columns_count = Columns_row.size();

					// Loop will execute till the last cell of that specific
					// row.
					for (int column = 0; column < columns_count; column++) {
						updateError(row, column, s.getRow(rw).getCell(column)
								.getStringCellValue(), Columns_row.get(column)
								.getText());
					}
					row++;
					rw++;
				}

			}

			FileOutputStream fos = new FileOutputStream(path);
			wb.write(fos);
			fos.close();

			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);
		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing mT1_TH3_TCN_ReadXLSX keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, ermsg);
		}

	}// mT3_TH2_TCN_ReadXLSX

	/********************************************************************************************
	 * Author : DivyaRaju.R LastModifiedDate : 4th June 2015 Method name :
	 * mT3_TH1_TCN_WriteXLSX Description : This method is used for fetching data
	 * from 1220 application and writing that excel
	 *
	 *********************************************************************************************/
	public static void mT3_TH1_TCN_WriteXLSX(String sheetName,
			String excelFileName, String msg, String tableXpath,
			String subReportName) {
		boolean xlFileCreated = false;

		try {// fetch the folder path to create work book

			String folderPath = fetchWriteExcelFolderPath();
			/*
			 * cleanPath( GlobalVariables.CONFIG.getProperty(TestBaseConstants.
			 * BUILD_FOLDER_PATH)) +TestBaseConstants.PATH_SIGN
			 * +TestBaseConstants.BASELINE_FOLDER_NAME+
			 * GlobalVariables.CONFIG.getProperty
			 * (TestBaseConstants.BUILD_NUMBER) +TestBaseConstants.PATH_SIGN+
			 * TestBaseConstants.ITERATION+
			 * GlobalVariables.CONFIG.getProperty(TestBaseConstants
			 * .BUILD_ITERATION_VALUE)+ TestBaseConstants.PATH_SIGN+
			 * GlobalVariables.CONFIG.getProperty(TestBaseConstants.BUILD_TYPE);
			 */
			// System.out.println("Build folder path is "+folderPath);

			File preBuildFolderPath = new File(folderPath);

			// Create directory
			boolean folderCreated = preBuildFolderPath.mkdirs();
			String filePath = preBuildFolderPath + "/"
					+ GlobalVariables.testCaseIdentifier + ".xlsx";
			File filePath1 = new File(filePath);
			// System.out.println("File Path is -->"+filePath);
			if (folderCreated || preBuildFolderPath.exists()) {
				if (filePath1.exists()) {
					filePath1.delete();
					xlFileCreated = ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);
				} else {
					xlFileCreated = ExcelTestUtil.createXLS(filePath,
							GlobalVariables.testCaseIdentifier);
				}

				if (xlFileCreated) {
					if (subReportName
							.equalsIgnoreCase(TestBaseConstants.COLUMBIA_CENTRALISED_STUDENT_REPORTING)
							|| subReportName
									.equalsIgnoreCase(TestBaseConstants.UNC_CENTRALISED_STUDENT_REPORTING)
							|| subReportName
									.equalsIgnoreCase(TestBaseConstants.UCLA_LAW_STUDENT_REPORTING)
							|| subReportName
									.equalsIgnoreCase(TestBaseConstants.OHIO_STATE_BSBA_STUDENT_REPORTING)
							|| subReportName
									.equalsIgnoreCase(TestBaseConstants.TEXAS_FTMBA_STUDENT_REPORTING)
							|| subReportName
									.equalsIgnoreCase(TestBaseConstants.HARVARD_FTMBA_STUDENT_REPORTING))

					{

						FileInputStream fis = new FileInputStream(filePath);
						Workbook wb = WorkbookFactory.create(fis);
						// get row size
						//
						Sheet s = wb
								.getSheet(GlobalVariables.testCaseIdentifier);
						if (getObjectValue("text_table_header_value")
								.isDisplayed()) {
							Row r1 = s.createRow(0);
							String row1 = getObjectValue(
									"text_table_header_value").getText();

							// create first row
							r1.createCell(0).setCellValue(row1);
							WebElement mytable = GlobalVariables.driver
									.findElement(By.xpath(tableXpath));
							// To locate rows of table.
							List<WebElement> rows_table = mytable
									.findElements(By
											.tagName(TestBaseConstants.TABLE_ROW_TAG));
							int rows_count = rows_table.size();
							// System.out.println(rows_count);

							int rownum = 1;
							// Fetch the contents of table from row 3 to last
							// 7th row
							for (int row = 0; row < rows_count; row++) {
								r1 = s.createRow(rownum);
								// To locate columns(cells) of that specific
								// row.
								List<WebElement> Columns_row = rows_table
										.get(row)
										.findElements(
												By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
								int columns_count = Columns_row.size();
								// Loop will execute till the last cell of that
								// specific row.
								for (int column = 0; column < columns_count; column++) {
									// To retrieve text from that specific cell.
									String celtext = Columns_row.get(column)
											.getText();
									r1.createCell(column).setCellValue(celtext);
									Logs.infoLog("Cell Value Of row number "
											+ row + " and column number "
											+ column + " Is " + celtext);
									GlobalVariables.APPICATION_LOGS
											.info("Cell Value Of row number "
													+ row
													+ " and column number "
													+ column + " Is " + celtext);
								}
								rownum++;
							}
						}
						FileOutputStream fos = new FileOutputStream(filePath);
						wb.write(fos);
						fos.close();
					}// centralised
				}// excel file created

				else {
					// System.out.println("File not created");
					GlobalVariables.APPICATION_LOGS.error("File not created");
					Logs.errorLog("File not created");
				}

			} else {
				// System.out.println("Folder not created");
				GlobalVariables.APPICATION_LOGS.error("Folder not created");
				Logs.errorLog("Folder not created");
			}

			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);

		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing mT3_TH1_TCN_WriteXLSX keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, msg);
		}
	}// mT3_TH1_TCN_WriteXLSX

	public static void mT3_TH1_TCN_ReadXLSX(String excelSheetName,
			String automationId, String tableXpath, String subReportName,
			String msg) {
		GlobalVariables.testCaseIdentifier = automationId;
		try {
			String path = fetchReadExcelFolderPath();
			/*
			 * cleanPath(GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_FOLDER_PATH)) +
			 * TestBaseConstants.PATH_SIGN +
			 * TestBaseConstants.BASELINE_FOLDER_NAME + GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_NUMBER) +
			 * TestBaseConstants.PATH_SIGN + TestBaseConstants.ITERATION +
			 * GlobalVariables.CONFIG
			 * .getProperty(TestBaseConstants.BUILD_ITERATION_VALUE) +
			 * TestBaseConstants.PATH_SIGN +
			 * TestBaseConstants.BASELINE_BUILD_TYPE +
			 * TestBaseConstants.PATH_SIGN + GlobalVariables.testCaseIdentifier
			 * + ".xlsx";
			 */
			// System.out.println("Path of file is -->"+path);
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(fis);

			// wb.createSheet(year);
			Sheet s = wb.getSheet(excelSheetName);

			if (subReportName
					.equalsIgnoreCase(TestBaseConstants.COLUMBIA_CENTRALISED_STUDENT_REPORTING)
					|| subReportName
							.equalsIgnoreCase(TestBaseConstants.UNC_CENTRALISED_STUDENT_REPORTING)
					|| subReportName
							.equalsIgnoreCase(TestBaseConstants.UCLA_LAW_STUDENT_REPORTING)
					|| subReportName
							.equalsIgnoreCase(TestBaseConstants.OHIO_STATE_BSBA_STUDENT_REPORTING)
					|| subReportName
							.equalsIgnoreCase(TestBaseConstants.TEXAS_FTMBA_STUDENT_REPORTING)
					|| subReportName
							.equalsIgnoreCase(TestBaseConstants.HARVARD_FTMBA_STUDENT_REPORTING)) {
				if (getObjectValue("text_table_header_value").isDisplayed()) {
					String row1 = getObjectValue("text_table_header_value")
							.getText();
					// compare 1st row of header
					updateError(0, 0, s.getRow(0).getCell(0)
							.getStringCellValue(), row1);

					WebElement mytable = GlobalVariables.driver.findElement(By
							.xpath(tableXpath));

					System.out.println("Table is displayed");
					// To locate rows of table.
					List<WebElement> rows_table = mytable.findElements(By
							.tagName(TestBaseConstants.TABLE_ROW_TAG));
					int rows_count = rows_table.size();
					int rownum = 1;
					// Fetch the contents of table
					for (int row = 0; row < rows_count; row++) {
						List<WebElement> Columns_row = rows_table
								.get(row)
								.findElements(
										By.tagName(TestBaseConstants.TABLE_COLUMN_TAG));
						int columns_count = Columns_row.size();
						for (int column = 0; column < columns_count; column++) {
							String celtext = Columns_row.get(column).getText();
							updateError(rownum, column, s.getRow(rownum)
									.getCell(column).getStringCellValue(),
									celtext);
						}
						rownum++;
					}

				} else {
					CustomVerification
							.verifyContent(
									false,
									getObjectValue("text_table_header_value")
											.getText()
											+ " is not present on the page hence failing");
				}

			}

			FileOutputStream fos = new FileOutputStream(path);
			wb.write(fos);
			fos.close();

			GlobalVariables.result = TestBaseConstants.RESULT_PASSVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			rATUStatus(GlobalVariables.result, msg);
		} catch (Exception e) {
			GlobalVariables.exceptionMsgVal = e.getMessage();
			String ermsg = "Error while executing mT2_TH2_TCN_ReadXLSX keyword";
			keywordsErrormsg(GlobalVariables.errormsg,
					GlobalVariables.exceptionMsgVal, ermsg);
			GlobalVariables.result = TestBaseConstants.RESULT_FAILVALUE;
			GlobalVariables.testusappend = ExcelTestUtil
					.runStatusAdd(GlobalVariables.result);
			GlobalVariables.APPICATION_LOGS.error(ermsg);
			Logs.errorLog(ermsg);
			rATUStatus(GlobalVariables.result, ermsg);
		}

	}// mT2_TH2_TCN_ReadXLSX

	public static int buildIterationValue(String path) {

		int count = new File(path).list().length;
		// System.out.println("Number of file : " + count);
		return count;
	}

	public static String fetchWriteExcelFolderPath() {
		String path = null;
		path = Base.cleanPath(GlobalVariables.CONFIG
				.getProperty(TestBaseConstants.BUILD_FOLDER_PATH))
				+ TestBaseConstants.PATH_SIGN
				+ TestBaseConstants.BASELINE_FOLDER_NAME
				+ GlobalVariables.CONFIG
						.getProperty(TestBaseConstants.BUILD_NUMBER);

		System.out.println(path);
		File f = new File(path);
		if (f.exists()) {
			// System.out.println("file exists");
			int i = buildIterationValue(path);
			String va = Integer.toString(i);
			Base.setProperty(TestBaseConstants.BUILD_ITERATION_VALUE, va);
		}
		path = cleanPath(GlobalVariables.CONFIG
				.getProperty(TestBaseConstants.BUILD_FOLDER_PATH))
				+ TestBaseConstants.PATH_SIGN
				+ TestBaseConstants.BASELINE_FOLDER_NAME
				+ GlobalVariables.CONFIG
						.getProperty(TestBaseConstants.BUILD_NUMBER)
				+ TestBaseConstants.PATH_SIGN
				+ TestBaseConstants.ITERATION
				+ GlobalVariables.CONFIG
						.getProperty(TestBaseConstants.BUILD_ITERATION_VALUE)
				+ TestBaseConstants.PATH_SIGN
				+ GlobalVariables.CONFIG
						.getProperty(TestBaseConstants.BUILD_TYPE);
		return path;

	}

	public static String fetchReadExcelFolderPath() {
		String path = null;
		path = Base.cleanPath(GlobalVariables.CONFIG
				.getProperty(TestBaseConstants.BUILD_FOLDER_PATH))
				+ TestBaseConstants.PATH_SIGN
				+ TestBaseConstants.BASELINE_FOLDER_NAME
				+ GlobalVariables.CONFIG
						.getProperty(TestBaseConstants.BASELINE_BUILD_NUMBER);

		// System.out.println(path);
		File f = new File(path);
		if (f.exists()) {
			// System.out.println("file exists");
			int i = buildIterationValue(path);
			String va = Integer.toString(i);
			Base.setProperty(TestBaseConstants.BUILD_ITERATION_VALUE, va);
		}
		path = cleanPath(GlobalVariables.CONFIG
				.getProperty(TestBaseConstants.BUILD_FOLDER_PATH))
				+ TestBaseConstants.PATH_SIGN
				+ TestBaseConstants.BASELINE_FOLDER_NAME
				+ GlobalVariables.CONFIG
						.getProperty(TestBaseConstants.BASELINE_BUILD_NUMBER)
				+ TestBaseConstants.PATH_SIGN
				+ TestBaseConstants.ITERATION
				+ GlobalVariables.CONFIG
						.getProperty(TestBaseConstants.BUILD_ITERATION_VALUE)
				+ TestBaseConstants.PATH_SIGN
				+ TestBaseConstants.BASELINE_BUILD_TYPE
				+ TestBaseConstants.PATH_SIGN
				+ GlobalVariables.testCaseIdentifier + ".xlsx";
		return path;

	}

}
// GlobalVariables.CONFIG.get(TestBaseConstants.BUILD_ITERATION_VALUE) ="1";