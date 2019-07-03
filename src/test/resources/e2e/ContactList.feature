Feature: Contact List
  Background:
    Given user visits contact page

# case for name is empty
# success case
  Scenario: add an address
    Given ContactList address is "xxx@gmail.com"
    And ContactList is empty
    When add
    Then ContactList address is added "xxx@gmail.com"
    And ContactList name is added ""

# address format error case
  Scenario: add a wrong address
    Given ContactList address is "xxx"
    When add
    Then ContactList error_area is "Address format is wrong"

# duplicate address error case
  Scenario: add a duplicate address
    Given ContactList address is "xxx@gmail.com"
    And ContactList has "xxx@gmail.com"
    When add
    Then ContactList error_area is "Duplicate address"

# case for adding name
# success case
  Scenario: add both name and email
    Given ContactList address is "xxx@gmail.com"
    And ContactList name is "xxx"
    And ContactList is empty
    When add
    Then ContactList address is added "xxx@gmail.com"
    And ContactList name is added "xxx"

# address format error case
  Scenario: add a name only
    Given ContactList address is ""
    And ContactList name is "xxxxxx"
    When add
    Then ContactList error_area is "Address may not be empty"

# check no address
  Scenario: check no address
    Given No ContactList is checked
    When create email
    Then MailSender address is ""

# check only address
  Scenario: check two addresses
    Given checked ContactList is "user1@gmail.com"
    And checked ContactList is "user2@gmail.com"
    When create email
    Then MailSender address is "user1@gmail.com;user2@gmail.com"

# check all address
  Scenario: check all addresses
    Given checked all ContactList
    When create email
    Then MailSender address is "user1@gmail.com;user2@gmail.com;user3@gmail.com;noname@gmail.com"

# Move to import csv
  Scenario: move to import page
    Given No ContactList is checked
    When click link
    Then move to import page