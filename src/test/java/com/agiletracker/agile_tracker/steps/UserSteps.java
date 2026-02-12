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

public class UserSteps {

    @Autowired
    private UserRepository userRepository;

    private UserService userService;
    private UserDTO userDTO;
    private Optional<UserDTO> createdUser;
    private List<UserDTO> users;
    private Optional<UserDTO> fetchedUser;
    private String returnedUserId;

    @Before
    public void setup() {
        userService = new UserService(userRepository, new ModelMapper());
        userRepository.deleteAll();
        userDTO = null;
        createdUser = Optional.empty();
        returnedUserId = null;
    }

    @Given("a user with name {string}, email {string}, and role {string}")
    public void a_user_with_name_email_and_role(String name, String email, String role) {
        userDTO = new UserDTO();
        userDTO.setName(name);
        userDTO.setEmail(email);
        userDTO.setRole(role);
    }

    @When("I create the user")
    public void i_create_the_user() {
        createdUser = userService.createUser(userDTO);
        returnedUserId = createdUser.map(UserDTO::getId).orElse(null);
    }

    @Then("the returned user should have name {string} and role {string}")
    public void the_returned_user_should_have_name_and_role(String name, String role) {
        // This method will be used for both created and fetched user checks
        Optional<UserDTO> userToCheck = createdUser.isPresent() ? createdUser : fetchedUser;
        assertThat(userToCheck).isPresent();
        assertThat(userToCheck.get().getName()).isEqualTo(name);
        assertThat(userToCheck.get().getRole()).isEqualTo(role);
    }

    @Given("some users exist")
    public void some_users_exist() {
        // The user from Background already exists, so nothing to do here.
    }

    @When("I fetch all users")
    public void i_fetch_all_users() {
        users = userService.getAllUsers();
    }

    @Then("I should get at least 1 user")
    public void i_should_get_at_least_one_user() {
        assertThat(users.size()).isGreaterThanOrEqualTo(1);
    }

    @When("I fetch the user by the returned id")
    public void i_fetch_the_user_by_the_returned_id() {
        fetchedUser = userService.getUserById(returnedUserId);
    }

    // Removed the duplicate step definition here

    @When("I update the user's name to {string}")
    public void i_update_the_user_s_name_to(String newName) {
        userDTO.setName(newName);
        createdUser = userService.updateUser(returnedUserId, userDTO);
    }

    @When("I delete the user by the returned id")
    public void i_delete_the_user_by_the_returned_id() {
        userService.deleteUser(returnedUserId);
    }

    @Then("the user should not exist anymore")
    public void the_user_should_not_exist_anymore() {
        Optional<UserDTO> deleted = userService.getUserById(returnedUserId);
        assertThat(deleted).isNotPresent();
    }
}
