@login
Feature: Login Page

  Background:
    Given the application landing page is open
    When the user navigates to the Login page

  @smoke
  Scenario: Login page loads with all UI elements visible
    Then the login page header is displayed
    And the email input is visible and enabled
    And the password input is visible and enabled
    And the submit button is displayed
    And the forgot password link is visible and enabled
    And the sign up link is visible and enabled

  @sanity
  Scenario Outline: Valid login redirects to the correct role dashboard - <testName>
    When the user logs in with email "<email>" and password "<password>"
    Then the browser should redirect to the "<role>" dashboard

    Examples:
      | testName                | role        | email                 | password  |
      | validSuperAdminLogin    | super admin | admin@anvay.com       | Admin@123 |
      | validInstitutionLogin   | institution | kumar@anvay.com       | Admin@123 |
      | validLeaderLogin        | leader      | mathi@anvay.com       | Admin@123 |
      | validStudentLogin       | student     | abhiram@example.com   | 123456    |
      | spacesAroundEmail       | super admin |  AdMin@anvay.com      | Admin@123 |

  @sanity
  Scenario Outline: Invalid credentials produce appropriate validation errors - <testName>
    When the user enters email "<email>" and password "<password>"
    Then the email validation error should be "<expectedEmailError>"
    And the password validation error should be "<expectedPasswordError>"
    And the login error should be "<expectedLoginError>"

    Examples:
      | testName               | email                  | password        | expectedEmailError                    | expectedPasswordError             | expectedLoginError        |
      | emptyBoth              |                        |                 | Please enter a valid email address    | Password is required              |                           |
      | emptyEmail             |                        | Admin@123       | Please enter a valid email address    |                                   |                           |
      | emptyPassword          | admin@anvay.com        |                 |                                       | Password is required              |                           |
      | invalidEmailFormat1    | notanemail             | Admin@123       | Please enter a valid email address    |                                   |                           |
      | invalidEmailFormat2    | admin@                 | Admin@123       | Please enter a valid email address    |                                   |                           |
      | invalidEmailFormat3    | @anvay.com             | Admin@123       | Please enter a valid email address    |                                   |                           |
      | invalidEmailFormat4    | admin@anvay            | Admin@123       | Please enter a valid email address    |                                   |                           |
      | invalidEmailFormat5    | admin anvay.com        | Admin@123       | Please enter a valid email address    |                                   |                           |
      | shortPassword          | admin@anvay.com        | 123             |                                       | Password must be at least 6 characters |                      |
      | wrongPassword          | admin@anvay.com        | wrongpass       |                                       |                                   | Invalid email or password |
      | wrongEmail             | unknown@test.com       | Admin@123       |                                       |                                   | Invalid email or password |
      | wrongBothCredentials   | wrong@test.com         | wrongpass       |                                       |                                   | Invalid email or password |
      | validEmailWrongPass    | kumar@anvay.com        | wrongpass       |                                       |                                   | Invalid email or password |
      | sqlInjectionEmail      | admin'@anvay.com       | Admin@123       | Please enter a valid email address    |                                   |                           |
      | sqlInjectionPassword   | admin@anvay.com        | ' OR '1'='1    |                                       | Invalid password format           |                           |
      | xssEmail               | <script>@anvay.com    | Admin@123       | Please enter a valid email address    |                                   |                           |
      | spacesOnlyEmail        |                        | Admin@123       | Please enter a valid email address    |                                   |                           |
      | spacesOnlyPassword     | admin@anvay.com        |                 |                                       | Password is required              |                           |

  @sanity
  Scenario: Inline validation errors appear on blank input and clear on valid input
    When the user clears the email field
    Then the email validation error is shown
    When the user clears the password field
    Then the password validation error is shown
    When the user types a valid email from config
    And the user types a valid password from config
    Then no email validation error is shown
    And no password validation error is shown
