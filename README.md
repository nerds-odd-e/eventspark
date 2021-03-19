# eventspark

## Dependencies
Requires mongodb (https://www.mongodb.com/community) and at least node 15.3.0.

## Running locally
`./gradlew bootRun` Defaults to `local` profile

## Running on production
Requires setting environment variables email sender and password in order to send email.

`./gradlew bootRun --args='--spring.profiles.active=prod'`

## Run single cucumber test
Add an annotation @focus
```
  @focus
  Scenario: move to import page
    Given No ContactList is checked
    When click link
    Then move to import page
 ```
run `./gradlew cucumber_focus`


