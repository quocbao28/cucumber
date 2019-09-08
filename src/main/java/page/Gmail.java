// package page;
//
// import org.openqa.selenium.WebDriver;
//
// public class Gmail extends AbstractPage {
//
// public Gmail(WebDriver driver) {
// super(driver);
// this.driver = driver;
// }
//
// /**
// * @ login Gmail
// *
// * @param username
// * @param password
// */
// public void logInGmail(String username, String password) {
// openURL(driver, " https://accounts.google.com/");
// waitForElement(driver, interfaces.Gmail.EMAIL_OR_PHONE_FIELD, timeWaits);
// type(driver, interfaces.Gmail.EMAIL_OR_PHONE_FIELD, username);
// waitForElement(driver, interfaces.Gmail.NEXT_BUTTON, timeWaits);
// click(driver, interfaces.Gmail.NEXT_BUTTON);
// sleep(5000);
// waitForElement(driver, interfaces.Gmail.PASSWORD_FIELD, timeWaits);
// type(driver, interfaces.Gmail.PASSWORD_FIELD, password);
// waitForElement(driver, interfaces.Gmail.NEXT_BUTTON, timeWaits);
// click(driver, interfaces.Gmail.NEXT_BUTTON);
// sleep(5000);
// openURL(driver, "https://mail.google.com/mail/");
// }
//
// /**
// * @param title
// * @return
// */
// public boolean isTitleEmailDisplayd(String title) {
// try {
// waitForElement(driver, interfaces.Gmail.EMAIL_TITLE, title, timeWaits);
// return isControlDisplayed(driver, interfaces.Gmail.EMAIL_TITLE, title);
// } catch (final Exception e) {
// return false;
// }
// }
//
// /**
// * Delete all Email
// */
// public void deleteAllMail() {
// waitForElement(driver, interfaces.Gmail.SELECT_ALL_MAIL_CHECKBOX, timeWaits);
// click(driver, interfaces.Gmail.SELECT_ALL_MAIL_CHECKBOX);
// waitForElement(driver, interfaces.Gmail.DELETE_BUTTON, timeWaits);
// click(driver, interfaces.Gmail.DELETE_BUTTON);
// sleep(5000);
// }
//
// WebDriver driver;
// }
