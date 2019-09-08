package common;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

import org.openqa.selenium.WebDriver;

public class CommonAction {

	public static synchronized CommonAction getCommon() {
		if (instance == null) {
			instance = new CommonAction();
		}
		return instance;
	}

	public String getRandomNumber() {
		final Random rd = new Random();
		final int i = rd.nextInt(10000) + 1;
		return Integer.toString(i);
	}

	public int getRandomNumber(int value) {
		final Random rd = new Random();
		return (rd.nextInt(value) + 1);

	}

	public void pressEnterKey() {
		try {
			final Robot rb = new Robot();
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
		} catch (final Exception e) {

		}
	}

	public void pressCommandKey() {
		try {
			final Robot rb = new Robot();
			rb.keyPress(KeyEvent.VK_META);
			rb.keyRelease(KeyEvent.VK_META);
		} catch (final Exception e) {

		}
	}

	/**
	 * @throws AWTException
	 *
	 */
	public void uploadFile(WebDriver driver, String localFile) throws IOException, AWTException {
		if (driver.toString().contains("firefox")) {
			Runtime.getRuntime().exec(new String[] { "/resource/uploadFirefox.exe", localFile });
		} else if (driver.toString().contains("chrome")) {
			Runtime.getRuntime().exec(new String[] { "/resource/uploadChrome.exe", localFile });
		} else if (driver.toString().contains("ie")) {
			Runtime.getRuntime().exec(new String[] { "/resource/uploadIe.exe", localFile });
		} else if (driver.toString().contains("safari")) {
			final Robot rb = new Robot();

			// import file to clipboards
			final File file = new File(localFile);
			final StringSelection stringSelection = new StringSelection(file.getAbsolutePath());
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

			// Open Goto window
			rb.keyPress(KeyEvent.VK_META);
			rb.keyPress(KeyEvent.VK_SHIFT);
			rb.keyPress(KeyEvent.VK_G);
			rb.keyRelease(KeyEvent.VK_META);
			rb.keyRelease(KeyEvent.VK_SHIFT);
			rb.keyRelease(KeyEvent.VK_G);

			// Paste the file path value

			rb.keyPress(KeyEvent.VK_META);
			rb.keyPress(KeyEvent.VK_V);
			rb.keyRelease(KeyEvent.VK_META);
			rb.keyRelease(KeyEvent.VK_V);

			// Press Enter key to upload and close window

			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
			rb.delay(500);
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);

		}
	}

	public String getDynamicXpath(String yourXpath, String yourText) {
		final String xpath = String.format(yourXpath, yourText);
		return xpath;
	}

	public String GetCurrentLocalDAte() {
		final LocalDate localDate = LocalDate.now();
		final String date = DateTimeFormatter.ofPattern("yyy-MM-dd").format(localDate);
		return date;
	}

	public boolean verifyFormatDate(String datetime) {
		final String datePattern = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
		if (datetime.matches(datePattern)) {
			return true;
		} else {
			return false;
		}
	}

	public Date changeStringToDateFormat(String value) {
		final DateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
		Date date = null;
		try {
			date = formatter.parse(value);
		} catch (final ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public boolean startDateBeforeEndDate(Date startDate, Date endDate) {
		if (startDate.before(endDate)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public boolean startDateAfterEndDate(Date startDate, Date endDate) {
		if (startDate.after(endDate)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public boolean startDateEqualsEndDate(Date startDate, Date endDate) {
		if (startDate.equals(endDate)) {
			return true;
		} else {
			return false;
		}
	}

	private static CommonAction instance = null;
}
