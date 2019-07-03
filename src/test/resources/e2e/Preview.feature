Feature: Preview

  Background:
    Given user visits our homepage

  # success case
  Scenario: preview single address
    Given subject is "Hi $name"
    And address is "user1@gmail.com"
    And body is "Hi $name"
    When preview
    Then show preview window
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