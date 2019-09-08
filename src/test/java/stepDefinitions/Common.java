/**
 *
 */
package stepDefinitions;

import org.openqa.selenium.WebDriver;

import cucumberConfig.Hooks;

/**
 * @author quocbao
 *
 */
public class Common {
	WebDriver driver;

	public Common() {
		driver = Hooks.openBrowser();
	}

}
