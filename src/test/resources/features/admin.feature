@admin
Feature: Admin College Management

  Background:
    Given the admin is logged in

  @sanity @regression
  Scenario Outline: Search for institution by name returns expected results - row <rowNum>
    And the admin navigates to College Management
    And the search box is loaded
    When the admin searches using Excel row <rowNum>
    Then the search result from Excel row <rowNum> should match

    Examples:
      | rowNum |
      | 1      |
      | 2      |
      | 3      |
      | 4      |
      | 5      |
      | 6      |
      | 7      |

  @sanity @regression
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