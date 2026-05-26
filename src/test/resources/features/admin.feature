@admin
Feature: Admin College Management

  Background:
    Given the admin is logged in

  @sanity
  Scenario Outline: Search for institution by name returns expected results - <testCase>
    And the admin navigates to College Management
    And the search box is loaded
    When the admin searches for "<searchTerm>"
    Then the search result should be "<expectedResult>"

    Examples:
      | testCase | searchTerm          | expectedResult |
      | valid1   | iit                 | found          |
      | valid2   | anurag              | found          |
      | extra1   | TRAILING_IIT        | found          |
      | extra2   | LEADING_IIT         | found          |
      | extra3   | BOTH_SPACES_IIT     | found          |
      | invalid1 | kjnsdk              | not found      |
      | invalid2 | jnkanf              | not found      |

  @sanity
  Scenario: Admin approves a pending institution
    And the admin navigates to College Management
    When the admin resets the search
    And the admin clicks Approve on the first pending institution
    Then the approval modal is displayed
    And the approval checkbox is unchecked by default
    And the Approve button is disabled before confirming
    When the admin checks the approval checkbox
    Then the Approve button becomes enabled
    When the admin clicks the Approve Institution button
    Then a success banner is displayed
    And at least one institution row has an active status
    And the active row has a Deactivate button