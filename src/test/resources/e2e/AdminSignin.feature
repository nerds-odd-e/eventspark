Feature: Admin Signin
  Background:
    Given

# case for name is empty
# success case
  Scenario: Open a event list page
    Given A user has not sign-in yet
    When I open a event list page
    Then Can see the button for signing as admin.
