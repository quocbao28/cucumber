package page;

import org.openqa.selenium.WebDriver;

public class HomePage extends AbstractPage {

	public HomePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	// Write your action bellow

	/**
	 * click Here hyperlink
	 */
	public void clickHereHyperlink() {
		waitForElement(driver, interfaces.HomePage.HERE_HYPERLINK, timeWaits);
		click(driver, interfaces.HomePage.HERE_HYPERLINK);
	}

	/**
	 * Input email at HomePage
	 */
	public void inputEmail(String email) {
		waitForElement(driver, interfaces.HomePage.EMAIL_FILED, timeWaits);
		type(driver, interfaces.HomePage.EMAIL_FILED, email);
	}

	/**
	 * click Submit button
	 */
	public void clickSubmitButton() {
		waitForElement(driver, interfaces.HomePage.SUBMIT_BUTTON, timeWaits);
		click(driver, interfaces.HomePage.SUBMIT_BUTTON);
	}

	/**
	 * Get New Username
	 */
	public String getNewUserName() {
		waitForElement(driver, interfaces.HomePage.NEW_USERNAME, timeWaits);
		return common.Constant.GETTED_USER = getText(driver, interfaces.HomePage.NEW_USERNAME);
	}

	/**
	 * Get New Password
	 */
	public String getNewPassword() {
		waitForElement(driver, interfaces.HomePage.NEW_PASSWORD, timeWaits);
		return common.Constant.GETTED_PASSWORD = getText(driver, interfaces.HomePage.NEW_PASSWORD);
	}

	/**
	 * Go to Bank Project
	 */
	public void goToBankProject() {
		waitForElement(driver, interfaces.HomePage.BANK_PROJECT_BUTTON, timeWaits);
		click(driver, interfaces.HomePage.BANK_PROJECT_BUTTON);
	}

	/**
	 * Input User Id at loginPage
	 */
	public void inputUserId(String user) {
		waitForElement(driver, interfaces.HomePage.USER_FILED, timeWaits);
		type(driver, interfaces.HomePage.USER_FILED, user);
	}

	/**
	 * Input Password at loginPage
	 */
	public void inputPassword(String password) {
		waitForElement(driver, interfaces.HomePage.PASSWORD_FILED, timeWaits);
		type(driver, interfaces.HomePage.PASSWORD_FILED, password);
	}

	/**
	 * click Login button
	 */
	public void clickLogInButton() {
		waitForElement(driver, interfaces.HomePage.LOGIN_BUTTON, timeWaits);
		click(driver, interfaces.HomePage.LOGIN_BUTTON);
	}

	/**
	 * Verify Login In successful
	 *
	 * @return
	 */
	public boolean isHomePageDisplayed() {
		try {
			waitForElement(driver, interfaces.HomePage.BANK_TEXT, timeWaits);
			return isControlDisplayed(driver, interfaces.HomePage.BANK_TEXT);
		} catch (final Exception e) {
			return false;
		}
	}

	WebDriver driver;
}
