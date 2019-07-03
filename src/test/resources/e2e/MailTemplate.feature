Feature: Mail Template
  Background:
    Given user visits our homepage

  # success case
  Scenario: load a template
    When click load button
    Then subject is filled with "hello $name"
    And body is filled with "hello $name, this is the fixed template."
