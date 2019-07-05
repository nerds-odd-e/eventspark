Feature: Import Csv

  Background:
    Given user visits import csv page

  # success case
  # upload csv
  @focus
  Scenario Outline: upload csv
    Given select "<filename>" CSV
    When click import button
    Then move to contact list
    And show message "added <result> contacts"
    And ContactList multiple values are added "<name>"
    And ContactList multiple values are added "<address>"
    Examples:
      | filename     | result | name              | address                                               |
      | record_0.csv | 0      |                   |                                                       |
      | record_1.csv | 1      | test1             | test1@example.com                                     |
      | record_2.csv | 2      | test2;test3       | test2@example.com;test3@example.com                   |

  # error case
  @focus
  Scenario Outline: upload uncorrect-format csv
    Given select "<filename>" CSV
    When click import button
    Then show error "<message>" at import page
    Examples:
      | filename                           | message |
      | error_format.csv                   | CSV must have 2 fields(mail,name).   |
      | no_header.csv                      | CSV file header requires mail,name.  |
      | error_wrong.txt                    | Please specify csv file.             |
      | invalid_header.csv                 | CSV file header requires mail,name.  |
      | error_encode_binarydata.csv        | Uploaded file is binary data.        |
      | test_invalid_email.csv             | invalidemail.com is invalid address. |
      | test_duplicate_email.csv           | 1 and 2 and 3 rows are duplicated with duplicate@example.com |

  @focus
  Scenario Outline: upload overwrite csv
    Given select "<filename>" CSV
    When click import button
    And click modal yes
    Then move to contact list
    And show message "added <result> contacts"
    And ContactList multiple values are added "<name>"
    And ContactList multiple values are added "<address>"
    Examples:
      | filename     | result | name              | address                                         |
      | test_overwrite_email.csv | 2      | overwrite1;overwrite2 | user1@gmail.com;user2@gmail.com |
