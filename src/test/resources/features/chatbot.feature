@chatbot
Feature: Chatbot Interactions

  @sanity
  Scenario Outline: Clicking a quick-select button returns a bot response - <testCaseId>
    When the user opens the chatbot panel
    And the user clicks the quick-select button "<buttonName>"
    Then the chatbot displays a non-empty response
    And the chatbot panel is closed

    Examples:
      | testCaseId | buttonName         |
      | CB_QS_01   | Register for Event |
      | CB_QS_02   | Apply for Leader   |
      | CB_QS_03   | Event Rules        |
      | CB_QS_04   | Join a Club        |
      | CB_QS_05   | Points and Ranking |
      | CB_QS_06   | Create an Event    |

  @sanity
  Scenario Outline: Sending a free-text query with Enter key gets a bot response - <testCaseId>
    When the user opens the chatbot panel
    And the user types the message "<query>"
    And the user sends the message with the Enter key
    Then the chatbot displays a non-empty response
    And the chatbot panel is closed

    Examples:
      | testCaseId | query                                |
      | CB_FT_01   | How do I register for an event       |
      | CB_FT_02   | How do I apply for a leader position |
      | CB_FT_03   | What are the event rules             |
      | CB_FT_04   | How do I join a club                 |
      | CB_FT_05   | How do points and ranking work       |
      | CB_FT_06   | How do I create an event             |
      | CB_FT_07   | RRegister for event                  |
      | CB_FT_08   | Regsiter for event                   |
      | CB_FT_09   | how to regiter for event             |
      | CB_FT_10   | Aply for leader                      |
      | CB_FT_11   | Apply for leeder                     |
      | CB_FT_12   | Appply for leader                    |
      | CB_FT_13   | Evnt rules                           |
      | CB_FT_14   | Event ruls                           |
      | CB_FT_15   | Eventt rulez                         |
      | CB_FT_16   | Jion a club                          |
      | CB_FT_17   | Join a cub                           |
      | CB_FT_18   | Join a cllub                         |
      | CB_FT_20   | Points and rankin                     |
      | CB_FT_21   | Piints and ranking                   |
      | CB_FT_22   | Creat an event                       |
      | CB_FT_24   | Creaet an event                      |

  @sanity
  Scenario Outline: Sending an out-of-scope query receives a redirection response - <testCaseId>
    When the user opens the chatbot panel
    And the user types the message "<query>"
    And the user sends the message using the Send button
    Then the chatbot response indicates it is out of scope
    And the chatbot panel is closed

    Examples:
      | testCaseId | query                                |
      | CB_OS_01   | What is the capital of France?       |
      | CB_OS_02   | Tell me a joke                       |
      | CB_OS_03   | What is 2 + 2?                       |
      | CB_OS_04   | Who is the prime minister of India?  |
      | CB_OS_05   | Book me a flight to Mumbai           |
      | CB_OS_06   | What movies are releasing this week? |