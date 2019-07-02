Feature: Mail Send

  Background:
    Given user visits our homepage

  # success case
  Scenario: send mail success two
    Given address is "xxx@gmail.com;yyy@gmail.com"
    And subject is "hello"
    And body is "message"
    When send
    Then error_area is none
    And should receive the following emails:
      | from               | to            | subject | body    |
      | eventspark@gmx.com | xxx@gmail.com | hello   | message |
      | eventspark@gmx.com | yyy@gmail.com | hello   | message |

  # replace subject and body placeholder
  # success case
  Scenario: replace subject and body success two person
    Given subject is "Hi $name"
    And address is "user1@gmail.com;user2@gmail.com"
    And body is "Hi $name"
    When send
    Then error_area is none
    And should receive the following emails:
      | from               | to              | subject  | body     |
      | eventspark@gmx.com | user1@gmail.com | Hi user1 | Hi user1 |
      | eventspark@gmx.com | user2@gmail.com | Hi user2 | Hi user2 |

  # preview
  # success case
  @developing
  Scenario: preview single address
    Given subject is "Hi $name"
    And address is "user1@gmail.com"
    And body is "Hi $name"
    When preview
    Then show preview window
    And variables are replaced with "user1" in body
    And variables are replaced with "user1" in subject

# 1 field error
  Scenario: address is empty
    Given address is ""
    And subject is "hello"
    And body is "message"
    When send
    Then error_area is "Address may not be empty"

  Scenario: subject is empty
    Given address is "xxx@gmail.com"
    And subject is ""
    And body is "message"
    When send
    Then error_area is "Subject may not be empty"

  Scenario: body is empty
    Given address is "xxx@gmail.com"
    And subject is "hello"
    And body is ""
    When send
    Then error_area is "Body may not be empty"

# 3 field error
  Scenario: address and subject and body are empty
    Given address is ""
    And subject is ""
    And body is ""
    When send
    Then error_area contains "Address may not be empty"
    And error_area contains "Subject may not be empty"
    And error_area contains "Body may not be empty"

# address format error
  Scenario: address format error: not include @
    Given address is "xxx"
    And subject is "hello"
    And body is "message"
    When send
    Then error_area is "Address format is wrong"

# template error case
  Scenario Outline: replace $name error case
    Given subject is "<subject>"
    And address is "<addresses>"
    And body is "<body>"
    When send
    Then error_area is "When you use template, choose email from contract list that has a name"

    Examples:
      | subject  | addresses                        | body     |
      | Hi $name | noname@gmail.com;user1@gmail.com | hello    |
      | Hi $name | noregisterd@gmail.com            | hello    |
      | Hi       | noname@gmail.com;user1@gmail.com | Hi $name |
      | Hi       | noregisterd@gmail.com            | Hi $name |

# server error case
  Scenario: replace subject and body success two person
    Given subject is "Hi $name"
    And address is "user1@gmail.com;user2@gmail.com"
    And body is "Hi $name"
    And stop the mail server
    When send
    Then error_area is "Try to send email, but failed"
    And start the mail server
