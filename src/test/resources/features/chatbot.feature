@chatbot
Feature: Chatbot Interactions

  @sanity @regression
  Scenario Outline: Clicking a quick-select button returns a bot response - row <rowNum>
    When the user opens the chatbot panel
    And the user clicks the quick-select button from Excel row <rowNum>
    Then the chatbot displays a non-empty response
    And the chatbot panel is closed

    Examples:
      | rowNum |
      | 1      |
      | 2      |
      | 3      |
      | 4      |
      | 5      |
      | 6      |

  @sanity @regression
  Scenario Outline: Sending a free-text query with Enter key gets a bot response - row <rowNum>
    When the user opens the chatbot panel
    And the user types the free text message from Excel row <rowNum>
    And the user sends the message with the Enter key
    Then the chatbot displays a non-empty response
    And the chatbot panel is closed

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
      | 19     |
      | 20     |
      | 21     |
      | 22     |

  @sanity @regression
  Scenario Outline: Sending an out-of-scope query receives a redirection response - row <rowNum>
    When the user opens the chatbot panel
    And the user types the out of scope message from Excel row <rowNum>
    And the user sends the message using the Send button
    Then the chatbot response indicates it is out of scope
    And the chatbot panel is closed

    Examples:
      | rowNum |
      | 1      |
      | 2      |
      | 3      |
      | 4      |
      | 5      |
      | 6      |