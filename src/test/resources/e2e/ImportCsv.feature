Feature: Import Csv

  Background:
    Given user visits send page

  # success case
  @developing
  Scenario: move to import page
    Given open contact list page
    When click link
    Then move to import page

  # upload csv
  @developing
  Scenario: upload csv
    Given select "filename" CSV
    When click import button
    Then move to contact list
    And show message ”result件追加されました"
    And list has name "name"
    And list has email "address"
      | filename     | result | name              | address                                                |
      | record_0.csv | 0      |                   |                                                       |
      | record_1.csv | 1      | test1             | test1@example.com                                     |
      | record_2.csv | 2      | test1;test2;test3 | test1@example.com;test2@example.com;test3@example.com |
