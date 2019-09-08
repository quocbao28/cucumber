package common;

import org.openqa.selenium.WebDriver;

public class Constant extends CommonAction {
	// ---------------------------------SiteInfo------------------------------------//
	public static final String URL = "http://demo.guru99.com/v4/";
	public static final String USER = "";
	public static final String PASSWORD = "";
	public static String GETTED_USER = "";
	public static String GETTED_PASSWORD = "";

	// ---------------------------------CCV ------------------------------------//

	public static final String CCV_NAME = "DANIELLE BLOMFIELD";
	public static final String CCV_NUMBER = "4242424242424242";
	public static final String CCV_EXPIRATION_YEAR = "2021";
	public static final String CCV_EXPIRATION_MONTH = "Mar";
	public static final String CID = "910";
	public static final String CCV_ADDRESS = "New York";
	public static final String CCV_CITY = "New York";
	public static final String CCV_ZIPCODE = "60000";

	// ---------------------------------Driver------------------------------------//
	public static WebDriver driver = null;
	public static int TimeWait = 10;
	public static final String email = "Automation";

}
