@login
Feature: Login Page

  Background:
    Given the application landing page is open
    When the user navigates to the Login page

  @smoke @regression
  Scenario: Login page loads with all UI elements visible
    Then the login page header is displayed
    And the email input is visible and enabled
    And the password input is visible and enabled
    And the submit button is displayed
    And the forgot password link is visible and enabled
    And the sign up link is visible and enabled

  @sanity @regression
  Scenario Outline: Valid login redirects to the correct role dashboard - row <rowNum>
    When the user logs in using valid login Excel row <rowNum>
    Then the browser should redirect to the correct dashboard for row <rowNum>

    Examples:
      | rowNum |
      | 1      |
      | 2      |
      | 3      |
      | 4      |
      | 5      |

  @sanity @regression
  Scenario Outline: Invalid credentials produce appropriate validation errors - row <rowNum>
    When the user enters invalid credentials from Excel row <rowNum>
    Then the validation errors for row <rowNum> should match

    Examples:
      | rowNum |
      | 1      |
      | 2      |
      | 3      |
      | 4      |
      | 5      |
      | 6      |
      | 7      |
      | 8      |
      | 9      |
      | 10     |
      | 11     |
      | 12     |
      | 13     |
      | 14     |
      | 15     |
      | 16     |
      | 17     |
      | 18     |

  @sanity @regression
  Scenario: Inline validation errors appear on blank input and clear on valid input
    When the user clears the email field
    Then the email validation error is shown
    When the user clears the password field
    Then the password validation error is shown
    When the user types a valid email from config
    And the user types a valid password from config
    Then no email validation error is shown
    And no password validation error is shown