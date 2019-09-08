package interfaces;

public class HomePage {
	public static final String HERE_HYPERLINK = "//li[contains(text(),'Visit')]/a";
	public static final String EMAIL_FILED = "//input[@name='emailid']";
	public static final String SUBMIT_BUTTON = "//input[@type='submit']";
	public static final String NEW_USERNAME = "//td[contains(text(),'User ID :')]/following-sibling::td";
	public static final String NEW_PASSWORD= "//td[contains(text(),'Password :')]/following-sibling::td";
	public static final String USER_FILED= "//input[@name='uid']";
	public static final String PASSWORD_FILED= "//input[@name='password']";
	public static final String LOGIN_BUTTON= "//input[@name='btnLogin']";
	public static final String BANK_PROJECT_BUTTON= "//li/a[contains(text(),'Bank Project')]";
	public static final String BANK_TEXT= "//h2[contains(text(),'Gtpl Bank')]";
}
