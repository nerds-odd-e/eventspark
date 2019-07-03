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
    And previous button does not exist
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
    And next button does not exist
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
    And previous button does not exist
