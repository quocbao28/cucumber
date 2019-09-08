@SignUp
Feature: Create new account

  @TC_01_CreateNewAccount
  Scenario: Create new account
    Given Click Here button
    When Input new email with random email
    And Click Submit button
    Then Get UserID
    And Get Password

  @TC_02_LoginWithNewAccount
  Scenario: Login With New Account
    Given Go to Login Page
    When Input Username
    And Input Password
    And Click Login Button
    Then Verify login successful 
