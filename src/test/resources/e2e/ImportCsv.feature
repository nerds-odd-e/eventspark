Feature: Import Csv

  Background:
    Given user visits import csv page

  # success case
  # upload csv
  @developing @focus
  Scenario Outline: upload csv
    Given select "<filename>" CSV
    When click import button
    Then move to contact list
    And show message "<result> added"
    And list has name "<name>"
    And list has email "<address>"

    Examples:
      | filename     | result | name              | address                                                |
      | record_0.csv | 0      |                   |                                                       |
      | record_1.csv | 1      | test1             | test1@example.com                                     |
      | record_2.csv | 2      | test1;test2;test3 | test1@example.com;test2@example.com;test3@example.com |
