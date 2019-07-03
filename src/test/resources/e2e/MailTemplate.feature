Feature: Mail Template
  Background:
    Given user visits send page

  @developing
  # success case
  Scenario: load a template
    When click load button
    Then subject becomes "Hi $name"
    And body becomes "Hello $name"