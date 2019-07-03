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

  # error case
  @developing
  Scenario Outline: upload uncorrect-format csv
    Given select "<filename>" CSV
    When click import button
    Then show error "<message>" at import page
    Examples:
      | filename                           | message |
      | error_format.csv                   | File format is wrong |
      | error_encode_binarydata.csv        | File format is wrong |

  # warn case
  @developing
  Scenario Outline: upload invalid email csv
    Given select "<filename>" CSV
    When click import button
    Then move to contact list
    And show message "<result> added"
    And list has name "<name>"
    And list has email "<address>"
    And show warn message "<warn>"
    Examples:
      | filename               | result | name    | address                  | warn |
      | test_invalid_email.csv | 1      | correct | correctemail@example.com | invalidemail.com is invalid address. |

  @developing
  Scenario Outline: upload duplicate email csv
    Given select "<filename>" CSV
    When click import button
    Then move to contact list
    And show message "<result> added"
    And list has name "<name>"
    And list has email "<address>"
    And show warn message "<warn>"
    Examples:
      | filename               | result | name    | address                  | warn |
      | test_duplicate_email.csv | 1      | correct1 | correctemail1@example.com | 3 rows are duplicated with duplicate@example.com |

  @developing
  Scenario Outline: upload overwrite csv
    Given select "<filename>" CSV
    When click import button and display warning and click yes
    Then move to contact list
    And show message "<result> added"
    And list has name "<name>"
    And list has email "<address>"
    And show warn message "<warn>"
    Examples:
      | filename     | result | name              | address                                         |
      | test_overwrite_email.csv | 2      | overwrite1;overwrite2 | user1@gmail.com;user2@gmail.com |
