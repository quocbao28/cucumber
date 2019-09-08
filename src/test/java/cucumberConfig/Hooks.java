
package cucumberConfig;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.junit.Before;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Hooks {
	private static WebDriver driver;
	private static final Logger log = Logger.getLogger(Hooks.class.getName());

	@Before
	public synchronized static WebDriver openBrowser() {
		// Run by Maven command line
		String browser = System.getProperty("BROWSER");

		if (driver == null) {
			try {
				// Kiem tra BROWSER = null -> gan = chrome
				if (browser == null) {
					browser = System.getenv("BROWSER");
					if (browser == null) {
						browser = "chrome";
					}
				}
				switch (browser) {
				case "chrome":
					WebDriverManager.chromedriver().setup();
					driver = new ChromeDriver();
					break;
				case "":
					WebDriverManager.iedriver().setup();
					driver = new InternetExplorerDriver();
					break;
				case "firefox":
					WebDriverManager.firefoxdriver().setup();
					driver = new FirefoxDriver();
					break;
				case "chrome_headless":
					WebDriverManager.chromedriver().setup();
					final ChromeOptions options = new ChromeOptions();
					options.addArguments("headless");
					options.addArguments("window-size=1366x768");
					driver = new ChromeDriver(options);
					break;
				default:
					WebDriverManager.chromedriver().setup();
					driver = new ChromeDriver();
					break;
				}
			} catch (final UnreachableBrowserException e) {
				driver = new ChromeDriver();
			} catch (final WebDriverException e) {
				driver = new ChromeDriver();
			} finally {
				Runtime.getRuntime().addShutdownHook(new Thread(new BrowserCleanup()));
			}
			driver.get(common.Constant.URL);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			log.info("----------- START BRWOSER -----------");
		}
		try {
			final Robot rb = new Robot();
			rb.mouseMove(0, 0);
		} catch (final AWTException e) {
			e.printStackTrace();
		}
		final JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.focus();");
		return driver;
	}

	private static class BrowserCleanup implements Runnable {
		@Override
		public void run() {
			close();
		}

		private static void close() {
			try {
				if (driver != null) {
					try {
						// Detect OS (Windows/ Linux/ MAC)
						final String osName = System.getProperty("os.name").toLowerCase();
						String cmd = "";
						driver.quit();
						if (driver.toString().toLowerCase().contains("chrome")) {
							// Kill process
							if (osName.toLowerCase().contains("mac")) {
								cmd = "pkill chromedriver";
							} else {
								cmd = "taskkill /F /FI \"IMAGENAME eq chromedriver*\"";
							}
							final Process process = Runtime.getRuntime().exec(cmd);
							process.waitFor();
						}
						if (driver.toString().toLowerCase().contains("firefox")) {
							if (osName.toLowerCase().contains("mac")) {
								cmd = "pkill firefoxdriver";
							} else {
								cmd = "taskkill /F /FI \"IMAGENAME eq firefoxdriver*\"";
							}
							final Process process = Runtime.getRuntime().exec(cmd);
							process.waitFor();
						}
						if (driver.toString().toLowerCase().contains("internetexplorer")) {
							cmd = "taskkill /F /FI \"IMAGENAME eq IEDriverServer*\"";
							final Process process = Runtime.getRuntime().exec(cmd);
							process.waitFor();
						}
					} catch (final Exception e) {
						System.out.println(e.getMessage());
					}
					log.info("------------ CLOSE BROWSER ------------");
				}
			} catch (final UnreachableBrowserException e) {
				log.info("CAN NOT CLOSE BROWSER");
			}
		}
	}
}