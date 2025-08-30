Feature: User Service

  Scenario: Create a new user
    Given a user with name "Pearl", email "pearl@example.com", and role "PRODUCT_MANAGER"
    When I create the user
    Then the returned user should have name "Pearl" and role "PRODUCT_MANAGER"

  Scenario: Get all users
    Given some users exist
    When I fetch all users
    Then I should get at least 1 user

  Scenario: Get user by ID
    Given a user with id "123" exists
    When I fetch the user with id "123"
    Then the returned user should have id "123"
