# eventspark

## Dependencies
Requires mongodb

## Running locally
`./gradlew bootRun` Defaults to `local` profile

## Running on production
`./gradlew bootRun --args='--spring.profiles.active=prod'`

## run single cucumber test
Add an annotation @focus
```
  @focus
  Scenario: move to import page
    Given No ContactList is checked
    When click link
    Then move to import page
 ```
run `./gradlew cucumber_focus`


