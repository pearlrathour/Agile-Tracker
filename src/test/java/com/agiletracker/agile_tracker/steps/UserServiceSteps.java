package com.agiletracker.agile_tracker.steps;

import com.agiletracker.agile_tracker.dto.UserDTO;
import com.agiletracker.agile_tracker.entities.UserEntity;
import com.agiletracker.agile_tracker.repositories.UserRepository;
import com.agiletracker.agile_tracker.services.UserService;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceSteps {

    @Autowired
    private UserRepository userRepository;

    private UserService userService;
    private Optional<UserDTO> createdUser;
    private List<UserDTO> users;
    private Optional<UserDTO> fetchedUser;

    // Initialize UserService before each scenario
    @Before
    public void setup() {
        userService = new UserService(userRepository, new ModelMapper());
    }

    // Create a new user

    @Given("a user with name {string}, email {string}, and role {string}")
    public void a_user_with_name_email_and_role(String name, String email, String role) {
        createdUser = Optional.of(new UserDTO(null, name, email, role));
    }

    @When("I create the user")
    public void i_create_the_user() {
        createdUser = userService.createUser(createdUser.get());
    }

    @Then("the returned user should have name {string} and role {string}")
    public void the_returned_user_should_have_name_and_role(String name, String role) {
        assertThat(createdUser).isPresent();
        createdUser.ifPresent(user -> {
            assertThat(user.getName()).isEqualTo(name);
            assertThat(user.getRole()).isEqualTo(role);
        });
    }

    // Get all users
    @Given("some users exist")
    public void some_users_exist() {
        userService.createUser(new UserDTO(null, "Alice", "alice@example.com", "DEVELOPER"));
        userService.createUser(new UserDTO(null, "Bob", "bob@example.com", "PRODUCT_MANAGER"));
    }

    @When("I fetch all users")
    public void i_fetch_all_users() {
        users = userService.getAllUsers();
    }

    @Then("I should get at least 1 user")
    public void i_should_get_at_least_one_user() {
        assertThat(users).isNotEmpty();
    }

    // Get user by ID
    @Given("a user with id {string} exists")
    public void a_user_with_id_exists(String id) {
        UserEntity entity = UserEntity.builder()
                .id(id)
                .name("Charlie")
                .email("charlie@example.com")
                .role("DEVELOPER")
                .build();
        userRepository.save(entity);
    }

    @When("I fetch the user with id {string}")
    public void i_fetch_the_user_with_id(String id) {
        fetchedUser = userService.getUserById(id);
    }

    @Then("the returned user should have id {string}")
    public void the_returned_user_should_have_id(String id) {
        assertThat(fetchedUser).isPresent();
        fetchedUser.ifPresent(user -> assertThat(user.getId()).isEqualTo(id));
    }
}
