# eventspark

## Dependencies
Requires mongodb (https://www.mongodb.com/community) and at least node 15.3.0.

## Spring Profiles
Databases are named according to the profile names.
* local - default profile
* test - use for ./gradlew test
* e2e - use for ./gradlew cucumber
* prod - use for production

## How to run
### Running locally
`./gradlew bootRun` Defaults to `local` profile

### Running on prod profile
Requires setting environment variables email sender and password in order to send email. Ask your team members or trainers to get the credentials.

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


