Feature: User Service

  Background:
    Given a user with name "Pearl", email "pearl@example.com", and role "PRODUCT_MANAGER"
    And I create the user

  Scenario: Create a new user
    Then the returned user should have name "Pearl" and role "PRODUCT_MANAGER"

  Scenario: Get all users
    Given some users exist
    When I fetch all users
    Then I should get at least 1 user

  Scenario: Get user by ID
    When I fetch the user by the returned id
    Then the returned user should have name "Pearl" and role "PRODUCT_MANAGER"

  Scenario: Update a user
    When I update the user's name to "Samuel"
    Then the returned user should have name "Samuel" and role "PRODUCT_MANAGER"

  Scenario: Delete a user
    When I delete the user by the returned id
    Then the user should not exist anymore