# eventspark


# production run 
-Dspring.profiles.active=prod

# run localhost

```
gradle build
java -jar -Dspring.profiles.active=prod build/libs/eventspark-0.0.1-SNAPSHOT.jar
```

# run single cucumber test
Add an annotation @focus
```
  @focus
  Scenario: move to import page
    Given No ContactList is checked
    When click link
    Then move to import page
 ```

run `./gradlew cucumber_focus`


