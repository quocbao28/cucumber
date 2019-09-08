package stepDefinitions;

import org.openqa.selenium.WebDriver;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumberConfig.Hooks;
import page.HomePage;
import page.PageFactory;

public class HomePageStep {
	WebDriver driver;
	HomePage homePage;

	public HomePageStep() {
		driver = Hooks.openBrowser();
		homePage = PageFactory.getPageFactory().getHomePage(driver);
	}

	@Given("^Click Here button$")
	public void clickHereButton() {
		homePage.clickHereHyperlink();
	}

	@When("^Input new email with random email$")
	public void inputNewEmail() {
		String email = "test" + common.AbstractTest.getRandomString(4) +"@gmail.com";
		homePage.inputEmail(email);
	}

	@When("^Click Submit button$")
	public void clickSubmitButton() {
		homePage.clickSubmitButton();
	}

	@Then("^Get UserID$")
	public void getUserID() {
		homePage.getNewUserName();
	}

	@Then("^Get Password$")
	public void getPassword() {
		homePage.getNewPassword();
	}
	
    @Given("^Go to Login Page$")
    public void go_to_login_page()  {
      homePage.goToBankProject();
    }

    @When("^Input Username$")
    public void input_username()  {
       homePage.inputUserId(common.Constant.GETTED_USER);
    }

    @And("^Input Password$")
    public void input_password()  {
      homePage.inputPassword(common.Constant.GETTED_PASSWORD );
    }

    @And("^Click Login Button$")
    public void click_login_button()  {
        homePage.clickLogInButton();
    }
    
    @Then("^Verify login successful$")
    public void verifyLoginSuccessful() {
      homePage.isHomePageDisplayed();
    }

}
