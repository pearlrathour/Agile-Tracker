Feature: Story Management
  As a Product Manager
  I want to manage stories
  So that I can track development work linked to epics

  Scenario: Create a new story
    Given an epic for story with title "Login Feature" and createdById "pm1"
    And a new story with title "User login page", description "Create login page", assigneeId "dev1", reporterId "pm1", and linked to the epic
    When I create the story
    Then the returned story should have title "User login page" and status "TODO"

  Scenario: Get all stories
    Given there are stories in the system
    When I fetch all stories
    Then the response should contain at least 1 story
