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

## End to end (e2e) tests with cucumber

### For scenarios working in progress
Annotate with @developing in the scenario so that it will not be run in the CI server.

### Run a specific scenario
Add an annotation @focus
```
  @focus
  Scenario: move to import page
    Given No ContactList is checked
    When click link
    Then move to import page
 ```
then run `./gradlew cucumber_focus`

## Known Problems
### Gradle test may fail
`./gradlew test` fails with "npm not found" when OS' nodejs is installed with nvm. Try to install nodejs directly in OS as a workaround for the time being.