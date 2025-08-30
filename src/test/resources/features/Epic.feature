Feature: Epic Management
  As a Product Manager
  I want to manage epics
  So that I can track their stories and status

  Scenario: Create a new epic
    Given a new epic with title "Login Feature", description "User login functionality", and createdById "pm1"
    When I create the epic
    Then the returned epic should have title "Login Feature" and status "OPEN"

  Scenario: Get all epics
    Given there are epics in the system
    When I fetch all epics
    Then the response should contain at least 1 epic

  Scenario: Get an epic by ID
    Given an existing epic with title "Login Feature" and createdById "pm1"
    When I fetch the epic by its ID
    Then the returned epic should have title "Login Feature"

  Scenario: Close an epic
    Given an existing epic with title "Login Feature" and createdById "pm1"
    When I close the epic
    Then the returned epic should have status "CLOSED"
