package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import com.github.javafaker.Faker;

public class AbstractTest {
	protected final Logger log;
	protected WebDriver driver;
	protected AutomationControl control = new AutomationControl();
	private static HSSFWorkbook excelBook;
	private static HSSFSheet excelSheet;
	private static HSSFCell cell;
	protected Faker faker = new Faker();

	protected boolean verifyTrue(boolean condition, boolean halt) {
		boolean pass = true;
		if (halt == false) {
			try {
				Assert.assertTrue(condition);
				log.info("==Verify condition is PASSED==");
			} catch (final Throwable e) {
				pass = false;

				VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
				Reporter.getCurrentTestResult().setThrowable(e);
				log.info("==Verify condition is FAILED==");
			}
		} else {
			Assert.assertTrue(condition);
		}
		return pass;
	}

	protected boolean verifyTrue(boolean condition) {
		return verifyTrue(condition, false);
	}

	protected boolean verifyFalse(boolean condition, boolean halt) {
		boolean pass = true;
		if (halt == false) {
			try {
				Assert.assertFalse(condition);
				log.info("==Verify condition is PASSED==");
			} catch (final Throwable e) {
				pass = false;

				VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
				Reporter.getCurrentTestResult().setThrowable(e);
				log.info("==Verify condition is FAILED==");
			}
		} else {
			Assert.assertFalse(condition);
		}
		return pass;

	}

	protected boolean verifyFalse(boolean condition) {
		return verifyFalse(condition, false);
	}

	protected boolean verifyEquals(Object actual, Object expected, boolean halt) {
		boolean pass = true;
		if (halt == false) {
			try {
				Assert.assertEquals(actual, expected);
				log.info("==Verify condition is PASSED==");
			} catch (final Throwable e) {
				pass = false;

				VerificationFailures.getFailures().addFailureForTest(Reporter.getCurrentTestResult(), e);
				Reporter.getCurrentTestResult().setThrowable(e);
				log.info("==Verify condition is FAILED==");
			}
		} else {
			Assert.assertEquals(actual, expected);
		}
		return pass;
	}

	protected boolean verifyEquals(Object actual, Object expected) {
		return verifyEquals(actual, expected, false);
	}

	protected void refreshBrowser() {
		driver.navigate().refresh();
		try {
			Thread.sleep(2000);
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openFolder(String browser, String folderName) {
		final String dir = System.getProperty("user.home");
		final String folderPath = dir + "\\" + folderName;
		navigateToURL(folderPath);
	}

	public void navigateToURL(String URL) {
		driver.get(URL);
	}

	protected void openNewTab(WebDriver driver) {
		final String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL, "t");
		driver.findElement(By.linkText("urlLink")).sendKeys(selectLinkOpeninNewTab);
	}

	protected String getUniqueName() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	protected String getUniqueNumber() {
		final Random rand = new Random();
		final int number = rand.nextInt(90000000) + 1;
		final String numberString = Integer.toString(number);
		return numberString;
	}

	public void clearCookie(WebDriver driver) {
		driver.manage().deleteAllCookies();
	}

	protected AbstractTest() {
		log = Logger.getLogger(getClass());
	}

	public String takeScreenshot() {
		final File src = ((TakesScreenshot) Constant.driver).getScreenshotAs(OutputType.FILE);
		final String destFileLoctn = "./Screenshots/" + System.currentTimeMillis() + ".png";
		try {
			FileUtils.copyFile(src, new File(destFileLoctn));
		} catch (final IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(destFileLoctn);
		return destFileLoctn;
	}

	public static String getRandomString(String[] array) {
		final int rnd = new Random().nextInt(array.length);
		return array[rnd];
	}

	public static String RandomEmail() {
		final Random ran = new Random();
		final int a = ran.nextInt(99999) + 1;
		final int b = ran.nextInt(99999) + 1;
		return a + getRandomString(10) + b + "@gmail.com";
	}

	public static String getRandomString(int length) {
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(getRandomChar());
		}
		return sb.toString();
	}

	public static char getRandomChar() {
		final int rnd = (int) (Math.random() * 52); // or use Random or whatever
		final char base = (rnd < 26) ? 'A' : 'a';
		return (char) (base + rnd % 26);
	}

	public static String isColorConvert(WebDriver driver, String locator) {
		final String color = driver.findElement(By.xpath(locator)).getCssValue("color");
		final String[] hexValue = color.replace("rgba(", "").replace(")", "").split(",");
		final int hexValue1 = Integer.parseInt(hexValue[0]);
		hexValue[1] = hexValue[1].trim();
		final int hexValue2 = Integer.parseInt(hexValue[1]);
		hexValue[2] = hexValue[2].trim();
		final int hexValue3 = Integer.parseInt(hexValue[2]);
		final String actualColor = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);
		return actualColor;
	}

	/**
	 * Open the Excel file
	 *
	 * @param sheetName
	 * @throws Exception
	 */
	public void createExcelFile(String sheetName) {
		try {
			final FileInputStream file = new FileInputStream(new File("Report.xls"));
			excelBook = new HSSFWorkbook(file);
			excelSheet = excelBook.createSheet(sheetName);
			System.out.println("Create Excel File");
		} catch (final Exception e) {
			System.out.print(e.getMessage());
		}
	}

	/**
	 * Get data in excel file
	 *
	 * @param rowNum
	 * @param colNum
	 * @return
	 */
	public String getCellData(int rowNum, int colNum) {
		try {
			cell = excelSheet.getRow(rowNum).getCell(colNum);
			final String CellData = cell.getStringCellValue();
			return CellData;
		} catch (final Exception e) {
			return "";
		}
	}

	/**
	 * @param title
	 */
	public void switchToWindowByTitle(String title) {
		final Set<String> allWindows = driver.getWindowHandles();
		for (final String runWindows : allWindows) {
			driver.switchTo().window(runWindows);
			final String currentWin = driver.getTitle();
			if (currentWin.equals(title)) {
				break;
			}
		}
	}

	/**
	 * @param parentWindow
	 * @return
	 */
	public boolean closeAllWithoutParentWindows(String parentWindow) {
		final Set<String> allWindows = driver.getWindowHandles();
		for (final String runWindows : allWindows) {
			if (!runWindows.equals(parentWindow)) {
				driver.switchTo().window(runWindows);
				driver.close();
			}
		}
		driver.switchTo().window(parentWindow);
		if (driver.getWindowHandles().size() == 1) {
			return true;
		} else {
			return false;
		}
	}

	public String getParentWindows() {
		final String parentWindow = driver.getWindowHandle();
		return parentWindow;
	}

	public void sleep(int timeout) {
		try {
			Thread.sleep(timeout);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Sheet readExcel(String filePath, String fileName, String sheetName) throws IOException {

		// Create a object of File class to open xlsx file
		final File file = new File(filePath + "\\" + fileName);

		// Create an object of FileInputStream class to read excel file
		final FileInputStream inputStream = new FileInputStream(file);
		Workbook workbook = null;

		// Find the file extension by spliting file name in substing and getting
		// only extension name
		final String fileExtensionName = fileName.substring(fileName.indexOf("."));

		// Check condition if the file is xlsx file
		if (fileExtensionName.equals(".xlsx")) {
			// If it is xlsx file then create object of XSSFWorkbook class
			workbook = new XSSFWorkbook(inputStream);
		}

		// Check condition if the file is xls file
		else if (fileExtensionName.equals(".xls")) {
			// If it is xls file then create object of XSSFWorkbook class
			workbook = new HSSFWorkbook(inputStream);
		}

		// Read sheet inside the workbook by its name
		final Sheet sheet = workbook.getSheet(sheetName);
		return sheet;
	}
}
