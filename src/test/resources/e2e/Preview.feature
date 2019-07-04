Feature: Preview

  Background:
    Given user visits our homepage

  # success case
  Scenario: preview single address
    Given subject is "Hi $name"
    And address is "user1@gmail.com"
    And body is "Hello $name"
    When preview
    Then show preview 0
    And previewed address is "user1@gmail.com"
    And variables are replaced with "user1" in body
    And variables are replaced with "user1" in subject

  Scenario: return to home from preview
    Given subject is "Hi $name"
    And address is "user1@gmail.com"
    And body is "Hello $name"
    And preview
    When press back to home button
    Then show home window
    And address is filled with "user1@gmail.com"
    And subject is filled with "Hi $name"
    And body is filled with "Hello $name"

  Scenario: preview multiple address forward and backward
    Given subject is "Hi $name"
    And address is "user1@gmail.com;user2@gmail.com;user3@gmail.com"
    And body is "Hello $name"
    When preview
    Then show preview 0
    And previewed address is "user1@gmail.com"
    And variables are replaced with "user1" in body
    And variables are replaced with "user1" in subject
    And previous button is disabled
    When click next button
    Then show preview 1
    And previewed address is "user2@gmail.com"
    And variables are replaced with "user2" in body
    And variables are replaced with "user2" in subject
    When click next button
    Then show preview 2
    And previewed address is "user3@gmail.com"
    And variables are replaced with "user3" in body
    And variables are replaced with "user3" in subject
    And next button is disabled
    When click previous button
    Then show preview 1
    And previewed address is "user2@gmail.com"
    And variables are replaced with "user2" in body
    And variables are replaced with "user2" in subject
    When click previous button
    Then show preview 0
    And previewed address is "user1@gmail.com"
    And variables are replaced with "user1" in body
    And variables are replaced with "user1" in subject
    And previous button is disabled
    
  # 1 field error
  Scenario: address is empty
    Given address is ""
    And subject is "hello"
    And body is "message"
    When preview
    Then error_area is "Address may not be empty"

  Scenario: subject is empty
    Given address is "xxx@gmail.com"
    And subject is ""
    And body is "message"
    When preview
    Then error_area is "Subject may not be empty"

  Scenario: body is empty
    Given address is "xxx@gmail.com"
    And subject is "hello"
    And body is ""
    When preview
    Then error_area is "Body may not be empty"

  # 3 field error
  Scenario: address and subject and body are empty
    Given address is ""
    And subject is ""
    And body is ""
    When preview
    Then error_area contains "Address may not be empty"
    And error_area contains "Subject may not be empty"
    And error_area contains "Body may not be empty"

  # address format error
  Scenario: address format error: not include @
    Given address is "xxx"
    And subject is "hello"
    And body is "message"
    When preview
    Then error_area is "Address format is wrong"

  # template error case
  @developing
  Scenario Outline: replace $name error case
    Given subject is "<subject>"
    And address is "<addresses>"
    And body is "<body>"
    When preview
    Then error_area is "When you use template, choose email from contract list that has a name"

    Examples:
      | subject  | addresses                        | body     |
      | Hi $name | noname@gmail.com;user1@gmail.com | hello    |
      | Hi $name | noregisterd@gmail.com            | hello    |
      | Hi       | noname@gmail.com;user1@gmail.com | Hi $name |
      | Hi       | noregisterd@gmail.com            | Hi $name |
