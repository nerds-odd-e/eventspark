# eventspark


# production run 
-Dspring.profiles.active=prod

# run localhost

gradle build

java -jar -Dspring.profiles.active=prod build/libs/course-mailer-0.0.1-SNAPSHOT.jar

