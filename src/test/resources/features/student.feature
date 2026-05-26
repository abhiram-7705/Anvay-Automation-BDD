@student
Feature: Student Event Feed

  Background:
    Given the student is logged in
    And the student navigates to the Event Feed page

  @smoke
  Scenario: Event Feed page displays upcoming events
    Then the event feed page is loaded
    And the upcoming events section is displayed
    And at least one event card is visible

  @sanity
  Scenario: Student registers for an event
    When the student clicks Register on the first available event
    Then the event name is captured from the confirmation modal
    And the registration is confirmed

  @sanity
  Scenario: Registered event appears in My Registrations tab
    When the student clicks Register on the first available event
    And the student switches to the My Registrations tab
    Then the registered event appears in the My Registrations list
