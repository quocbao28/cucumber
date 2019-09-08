package page;

import org.openqa.selenium.WebDriver;

public class PageFactory {
	public static synchronized PageFactory getPageFactory() {
		if (instance == null) {
			instance = new PageFactory();
		}
		return instance;
	}

	/**
	 * return Home Page
	 *
	 * @param driver
	 * @return HomePage
	 */
	public HomePage getHomePage(WebDriver driver) {
		return new HomePage(driver);
	}

	private static PageFactory instance = null;

}
