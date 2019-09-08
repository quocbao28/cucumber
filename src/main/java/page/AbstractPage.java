package page;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

import common.AutomationControl;
import common.CommonAction;
import common.Constant;

public class AbstractPage {
	protected AutomationControl control = new AutomationControl();
	protected WebElement element;
	protected Logger log = Logger.getLogger(AbstractPage.class);
	protected Faker faker = new Faker();

	public AbstractPage(WebDriver driver) {
		this.driver = driver;
	}

	public void click(WebDriver driver, String locator) {
		waitForElement(driver, locator, Constant.TimeWait);
		control.findElement(driver, locator).click();
	}

	public void click(WebDriver driver, String controlName, String value) {
		waitForElement(driver, controlName, value, Constant.TimeWait);
		element = control.findElement(driver, controlName, value);
		element.click();
	}

	public void type(WebDriver driver, String controlName, String value) {
		waitForElement(driver, controlName, Constant.TimeWait);
		element = control.findElement(driver, controlName);
		element.clear();
		element.sendKeys(value);
		sleep(200);
	}

	public void type(WebDriver driver, String controlName, String value, String value1) {
		waitForElement(driver, controlName, value, Constant.TimeWait);
		element = control.findElement(driver, controlName, value);
		element.clear();
		element.sendKeys(value1);
		sleep(200);
	}

	public void clear(WebDriver driver, String controlName) {
		waitForElement(driver, controlName, Constant.TimeWait);
		element = control.findElement(driver, controlName);
		element.clear();
	}

	public void waitForElement(WebDriver driver, String locator, int TimeOutInSeconds) {
		try {
			final WebDriverWait wait = new WebDriverWait(driver, TimeOutInSeconds);
			final WebElement element = control.findElement(driver, locator);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (final Exception e) {
			System.out.println("No Element is found");
		}
	}

	public void waitForElement(WebDriver driver, final String locator, String value, long TimeOutInSeconds) {
		try {
			final By by = control.getBy(driver, locator, value);
			final WebDriverWait wait = new WebDriverWait(driver, TimeOutInSeconds);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (final Exception e) {
			System.out.println("No Element is found");
		}
	}

	public void waitForElementInvisible(WebDriver driver, String locator, int timeOutInSeconds) {
		final WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)));
	}

	public void selectItemCombobox(WebDriver driver, String controlName, String item) {
		element = control.findElement(driver, controlName);
		final Select select = new Select(element);
		select.selectByVisibleText(item);
	}

	public void selectItemComboboxByValue(WebDriver driver, String controlName, String value) {
		element = control.findElement(driver, controlName);
		final Select select = new Select(element);
		select.selectByValue(value);
	}

	public void selectItemComboboxByClick(WebDriver driver, String controlName, String value) {
		element = control.findElement(driver, controlName);
		final WebElement option = control.findElement(driver, "//option[@value='" + value + "']");
		element.click();
		option.click();
	}

	public void randomSelectItemCombobox(WebDriver driver, String controlName) {
		element = control.findElement(driver, controlName);
		final Select select = new Select(element);
		final int index = CommonAction.getCommon().getRandomNumber(select.getOptions().size());
		select.selectByIndex(index);
	}

	public void selectItemCombobox(WebDriver driver, String controlName, int index) {
		element = control.findElement(driver, controlName);
		final Select select = new Select(element);
		select.selectByIndex(index);
	}

	public void selectItemCombobox(WebDriver driver, String controlName, String value, String item) {
		element = control.findElement(driver, controlName, value);
		final Select select = new Select(element);
		select.selectByVisibleText(item);
	}

	public String getItemSelectedCombobox(WebDriver driver, String controlName) {
		element = control.findElement(driver, controlName);
		final Select select = new Select(element);
		final String itemSelected = select.getFirstSelectedOption().getText();
		return itemSelected;
	}

	public String getItemSelectedCombobox(WebDriver driver, String controlName, String value) {
		element = control.findElement(driver, controlName, value);
		final Select select = new Select(element);
		final String itemSelected = select.getFirstSelectedOption().getText();
		return itemSelected;
	}

	public void refresh(WebDriver driver) {
		driver.navigate().refresh();
		sleep(2000);
	}

	public void back(WebDriver driver) {
		driver.navigate().back();
	}

	public void forward(WebDriver driver) {
		driver.navigate().forward();
	}

	/**
	 * Handle Unexpected Alert
	 */
	public void handleUnexpectedAlert(WebDriver driver) {
		try {
			final Alert al = driver.switchTo().alert();
			al.accept();
		} catch (final Exception e) {

		}
	}

	public void openURL(WebDriver driver, String url) {
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	public String getPageTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public void waitForAlert(WebDriver driver) {
		new WebDriverWait(driver, 60).ignoring(NoAlertPresentException.class)
				.until(ExpectedConditions.alertIsPresent());
	}

	public void acceptJavascriptAlert(WebDriver driver) {
		final Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	public boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} catch (final NoAlertPresentException e) {
			return false;
		}
	}

	public void dismissJavascriptAlert(WebDriver driver) {
		final Alert alert = driver.switchTo().alert();
		alert.dismiss();
	}

	public String getTextJavascriptAlert(WebDriver driver) {
		String message;
		try {
			final Alert alert = driver.switchTo().alert();
			message = alert.getText();
			alert.accept();
		} catch (final WebDriverException e) {
			message = null;
		}
		return message;
	}

	public void acceptJavascriptPrompt(WebDriver driver, String value) {
		final Alert alert = driver.switchTo().alert();
		driver.switchTo().alert().sendKeys(value);
		alert.accept();
	}

	public void waitForAjaxDone(WebDriver driver) {
		while (true) {
			final Boolean ajaxIsComplete = (Boolean) executeJavaScript(driver, "returnjQuery.active == 0");
			if (ajaxIsComplete) {
				break;
			}
			sleep(5000);
		}
	}

	public static void waitForAjax(WebDriver driver) {
		new WebDriverWait(driver, 180).until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				final JavascriptExecutor js = (JavascriptExecutor) driver;
				return (Boolean) js.executeScript("return jQuery.active == 0");
			}
		});
	}

	public void waitUntilAjaxRequestCompletes(WebDriver driver) {
		new FluentWait<WebDriver>(driver).withTimeout(45, TimeUnit.SECONDS).pollingEvery(15, TimeUnit.SECONDS)
				.until(new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(WebDriver d) {
						final JavascriptExecutor jsExec = (JavascriptExecutor) d;
						return (Boolean) jsExec.executeScript("return jQuery.active == 0");
					}
				});
	}

	public void waitForElementNotDisplayed(WebDriver driver, final String controlName, long timeout) {
		try {
			final By by = control.getBy(driver, controlName);
			final WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public String getAttributeValue(WebDriver driver, String controlName, String attribute) {
		waitForElement(driver, controlName, Constant.TimeWait);
		element = control.findElement(driver, controlName);
		return element.getAttribute(attribute);
	}

	public String getAttributeValue(WebDriver driver, String controlName, String value, String attribute) {
		waitForElement(driver, controlName, Constant.TimeWait);
		element = control.findElement(driver, controlName, value);
		return element.getAttribute(attribute);
	}

	public void doubleClick(WebDriver driver, String controlName) {
		waitForElement(driver, controlName, Constant.TimeWait);
		final Actions action = new Actions(driver);
		element = control.findElement(driver, controlName);
		action.doubleClick(element).perform();
	}

	public int countElement(WebDriver driver, String controlName) {
		waitForElement(driver, controlName, Constant.TimeWait);
		return control.findElements(driver, controlName).size();
	}

	public void moveMouseToElement(WebDriver driver, String controlName) {
		waitForElement(driver, controlName, Constant.TimeWait);
		scrollToTop();
		final Actions action = new Actions(driver);
		action.moveToElement(getElement(driver, controlName), 156, 20);
		action.build().perform();
	}

	public void moveMouseElementAndClick(WebDriver driver, String controlName) {
		waitForElement(driver, controlName, Constant.TimeWait);
		final Actions action = new Actions(driver);
		action.moveToElement(getElement(driver, controlName), 156, 20).click();
		action.build().perform();
	}

	public Object scrollToBottom() {
		try {
			final JavascriptExecutor jse6 = (JavascriptExecutor) driver;
			return jse6.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		} catch (final Exception e) {
			e.getMessage();
			return null;
		}
	}

	public Object scrollToTop() {
		try {
			final JavascriptExecutor jse6 = (JavascriptExecutor) driver;
			return jse6.executeScript("window.scrollTo(0, 0)");
		} catch (final Exception e) {
			e.getMessage();
			return null;
		}
	}

	public WebElement getElement(WebDriver driver, String controlName) {
		element = control.findElement(driver, controlName);
		return element;
	}

	public List<WebElement> getElements(WebDriver driver, String controlName) {
		return control.findElements(driver, controlName);
	}

	public Object executeJavaScript(WebDriver driver, String javaSript) {
		try {
			final JavascriptExecutor js = (JavascriptExecutor) driver;
			return js.executeScript(javaSript);
		} catch (final Exception e) {
			e.getMessage();
			return null;
		}
	}

	public Object executeJavaScript(WebDriver driver, String javaSript, String controlName) {
		try {
			element = control.findElement(driver, controlName);
			final JavascriptExecutor js = (JavascriptExecutor) driver;
			return js.executeScript(javaSript, element);
		} catch (final Exception e) {
			e.getMessage();
			return null;
		}
	}

	public String getCurrentUrl(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	public void scrollToBottomPage(WebDriver driver) {
		executeJavaScript(driver,
				"window.scrollTo(0,Math.max(document.documentElement.scrollHeight,document.body.scrollHeight,document.documentElement.clientHeight));");
	}

	public void scrollPageToElement(WebDriver driver, String controlName) {
		waitForElement(driver, controlName, 5);
		element = control.findElement(driver, controlName);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
		sleep(2000);
	}

	public String randomString() {
		final Random rand = new Random();
		final int number = rand.nextInt(9000) + 1;
		final String numberString = Integer.toString(number);
		return numberString;
	}

	public void uncheckTheCheckbox(WebDriver driver, String controlName) {
		element = control.findElement(driver, controlName);
		if (element.isSelected()) {
			click(driver, controlName);
		}
	}

	public boolean isCheckboxChecked(WebDriver driver, String controlName) {
		element = control.findElement(driver, controlName);
		return element.isSelected();
	}

	public void dragAndDrop(WebDriver driver, String sourceControl, String targetControl) {
		final WebElement source = control.findElement(driver, sourceControl);
		final WebElement target = control.findElement(driver, targetControl);
		final Actions action = new Actions(driver);
		action.dragAndDrop(source, target);
		action.perform();
	}

	public boolean isControlDisplayed(WebDriver driver, String controlName) {
		final List<WebElement> element = control.findElements(driver, controlName);
		return (!(element.size() == 0));
	}

	public boolean isControlDisplayed(WebDriver driver, String controlName, String value) {
		final List<WebElement> element = control.findElements(driver, controlName, value);
		return (!(element.size() == 0));
	}

	public boolean isControlSelected(WebDriver driver, String controlName) {
		try {
			element = control.findElement(driver, controlName);
			return element.isSelected();
		} catch (final Exception e) {
			return false;
		}
	}

	public boolean isControlEnabled(WebDriver driver, String controlName) {
		try {
			element = control.findElement(driver, controlName);
			return element.isEnabled();
		} catch (final Exception e) {
			return false;
		}
	}

	public String getText(WebDriver driver, String controlName) {
		try {
			waitForElement(driver, controlName, Constant.TimeWait);
			final WebElement element = control.findElement(driver, controlName);
			return element.getText();
		} catch (final Exception e) {
			return null;
		}
	}

	public String getText(WebDriver driver, String controlName, String value) {
		try {
			waitForElement(driver, controlName, value, Constant.TimeWait);
			final WebElement element = control.findElement(driver, controlName, value);
			return element.getText();
		} catch (final Exception e) {
			return null;
		}
	}

	public void sleep(long timeout) {
		try {
			Thread.sleep(timeout);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void checkTheCheckbox(WebDriver driver, String controlName) {
		element = control.findElement(driver, controlName);
		if (!element.isSelected()) {
			click(driver, controlName);
		}
	}

	public void checkTheCheckbox(WebDriver driver, String controlName, String value) {
		element = control.findElement(driver, controlName, value);
		if (!element.isSelected()) {
			click(driver, controlName, value);
		}
	}

	public void closeBrowser(WebDriver driver) {
		try {
			driver.quit();
			System.gc();
			if (driver.toString().toLowerCase().contains("chrome")) {
				final String cmd = "taskkill /IM chromedriver.exe /F";
				final Process process = Runtime.getRuntime().exec(cmd);
				process.waitFor();
			}
			if (driver.toString().toLowerCase().contains("internetexplorer")) {
				final String cmd = "taskkill /IM IEDriverServer.exe /F";
				final Process process = Runtime.getRuntime().exec(cmd);
				process.waitFor();
			}
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public String getControlHref(WebDriver driver, String controlName) {
		return getAttributeValue(driver, controlName, "href");
	}

	public int convertStringtoInt(String text) {
		final String newStr = text.replaceAll("[^\\d.]+", "");
		return (int) Float.parseFloat(newStr);
	}

	public WebDriver switchOtherWindow(WebDriver driver) {
		WebDriver tmp = driver;
		try {
			Thread.sleep(5000);
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (final String winHandle : driver.getWindowHandles()) {
			tmp = driver.switchTo().window(winHandle);
		}
		return tmp;
	}

	public String getCurrentDriver(WebDriver driver) {
		return driver.getWindowHandle();
	}

	public WebDriver switchCurrentDriver(WebDriver driver, String currentHandle) {
		try {
			driver.close();
			return driver.switchTo().window(currentHandle);
		} catch (final Exception ex) {
			System.out.println(ex);
		}
		return driver;
	}

	public void switchToPopUp(WebDriver driver) {
		String subWindowHandler = null;
		final Set<String> handles = driver.getWindowHandles(); // get all window
		// handles
		final Iterator<String> iterator = handles.iterator();
		while (iterator.hasNext()) {
			subWindowHandler = iterator.next();
		}
		driver.switchTo().window(subWindowHandler);
	}

	public void closeOtherWindows(WebDriver driver, String parentPage) {
		final Set<String> set = driver.getWindowHandles();
		set.remove(parentPage);
		assert set.size() == 1;
		driver.switchTo().window((String) set.toArray()[0]);
		driver.close();
		driver.switchTo().window(parentPage);
	}

	/**
	 * Switch to frame
	 *
	 * @param driver
	 * @param yourFrame
	 */
	public static void switchToFrame(WebDriver driver, String yourFrame) {
		try {
			final WebElement iFrame = driver.findElement(By.xpath(yourFrame));
			driver.switchTo().frame(iFrame);
		} catch (final Exception e) {
			System.out.println("No frame was found");
		}
	}

	/**
	 * Switch to main page
	 *
	 * @param driver
	 */
	public static void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	/**
	 * Upload File
	 */
	public void uploadFile(String fileName) {
		try {
			CommonAction.getCommon().uploadFile(driver, fileName);
			sleep(2);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create Date data in the future
	 */
	public String getFutureDate() {
		final SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
		final Calendar cal = Calendar.getInstance();
		cal.setTime(new Date()); // Now use today date.
		cal.add(Calendar.DATE, 2); // Adding 2 days
		return date.format(cal.getTime());
	}

	/**
	 * @param locator
	 * @param inputDate
	 *            format dd/MM/yyy
	 */
	public void handleDatePicker(String locator, String inputDate) {
		int no_date, no_month;
		final String[] dateArray = inputDate.split("/");
		String date = dateArray[0];
		String month = dateArray[1];
		final String year = dateArray[2];

		no_date = Integer.parseInt(date);
		no_month = Integer.parseInt(month);

		if (no_date < 10) {
			no_date = no_date % 10;
		}
		date = String.valueOf(no_date);

		if (no_month < 10) {
			no_month = no_month % 10;
		}
		month = String.valueOf(no_month);

		click(driver, locator);
		selectMonth(month);
		selectYear(year);
		selectDate(date);
	}

	/**
	 * Select Month in Calendar
	 *
	 * @param Month
	 */
	public void selectMonth(String month) {
		waitForElement(driver, "interfaces.CreateEventPage.MONTH_DROPDOWN", timeWaits);
		selectItemComboboxByValue(driver, "interfaces.CreateEventPage.MONTH_DROPDOWN", month);
	}

	/**
	 * Select Year in Calendar
	 *
	 * @param Year
	 */
	public void selectYear(String year) {
		waitForElement(driver, "interfaces.CreateEventPage.YEAR_DROPDOWN", timeWaits);
		selectItemComboboxByValue(driver, "interfaces.CreateEventPage.YEAR_DROPDOWN", year);
	}

	/**
	 * Select Date in Calendar
	 *
	 * @param Date
	 */
	public void selectDate(String date) {
		waitForElement(driver, "interfaces.CreateEventPage.DYNAMIC_DATE", date, timeWaits);
		click(driver, "interfaces.CreateEventPage.DYNAMIC_DATE", date);
	}

	final WebDriver driver;
	final int timeWaits = Constant.TimeWait;
	final String homeUrl = Constant.URL;
}