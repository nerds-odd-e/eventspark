Feature: Mail Template
  Background:
    Given user visits our homepage

  @developing
  # success case
  Scenario: load a template
    When click load button
    Then subject is filled with "Hi $name"
    And body is filled with "Hello $name"