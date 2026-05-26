@institution
Feature: Institution Event Management

  Background:
    Given the institution user is logged in
    And the institution user navigates to All Events

  @sanity
  Scenario: Institution user creates a new event successfully
    When the institution user clicks Create Event
    And fills in the event form with data from config
    And submits the event form
    Then the Create Event modal closes
    And the new event appears in the upcoming events table
