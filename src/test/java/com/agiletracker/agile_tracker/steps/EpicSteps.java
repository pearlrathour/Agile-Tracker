package com.agiletracker.agile_tracker.steps;

import com.agiletracker.agile_tracker.dto.EpicDTO;
import com.agiletracker.agile_tracker.entities.EpicEntity;
import com.agiletracker.agile_tracker.services.EpicService;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class EpicSteps {

    @Autowired
    private EpicService epicService;

    private EpicDTO epicDTO;
    private EpicEntity createdEpic;
    private List<EpicEntity> epicList;

    // ------------------- Create Epic -------------------
    @Given("a new epic with title {string}, description {string}, and createdById {string}")
    public void a_new_epic_with_title_description_and_createdById(String title, String description, String createdById) {
        epicDTO = new EpicDTO();
        epicDTO.setTitle(title);
        epicDTO.setDescription(description);
        epicDTO.setCreatedById(createdById);
    }

    @When("I create the epic")
    public void i_create_the_epic() {
        createdEpic = epicService.createEpic(epicDTO);
    }

    @Then("the returned epic should have title {string} and status {string}")
    public void the_returned_epic_should_have_title_and_status(String title, String status) {
        assertThat(createdEpic.getTitle()).isEqualTo(title);
        assertThat(createdEpic.getStatus()).isEqualTo(status);
    }

    // ------------------- Get All Epics -------------------
    @Given("there are epics in the system")
    public void there_are_epics_in_the_system() {
        epicList = epicService.getAllEpics();
        if (epicList.isEmpty()) {
            epicDTO = new EpicDTO();
            epicDTO.setTitle("Test Epic");
            epicDTO.setDescription("Some description");
            epicDTO.setCreatedById("pm1");
            epicDTO.setStoryIds(new ArrayList<>());
            epicDTO.setStatus("OPEN");

            epicService.createEpic(epicDTO);
        }
    }


    @When("I fetch all epics")
    public void i_fetch_all_epics() {
        epicList = epicService.getAllEpics();
    }

    @Then("the response should contain at least {int} epic")
    public void the_response_should_contain_at_least_epic(Integer count) {
        assertThat(epicList.size()).isGreaterThanOrEqualTo(count);
    }

    // ------------------- Get Epic By ID -------------------
    private String epicId;

    @Given("an existing epic with title {string} and createdById {string}")
    public void an_existing_epic_with_title_and_createdById(String title, String createdById) {
        EpicDTO dto = new EpicDTO();
        dto.setTitle(title);
        dto.setCreatedById(createdById);
        dto.setStatus("OPEN");
        EpicEntity epic = epicService.createEpic(dto);
        epicId = epic.getId();
        createdEpic = epic;
    }

    @When("I fetch the epic by its ID")
    public void i_fetch_the_epic_by_its_ID() {
        Optional<EpicEntity> optionalEpic = epicService.getEpicById(epicId);
        createdEpic = optionalEpic.orElse(null);
    }

    @Then("the returned epic should have title {string}")
    public void the_returned_epic_should_have_title(String title) {
        assertThat(createdEpic.getTitle()).isEqualTo(title);
    }

    // ------------------- Close Epic -------------------
    @When("I close the epic")
    public void i_close_the_epic() {
        createdEpic = epicService.closeEpic(epicId);
    }

    @Then("the returned epic should have status {string}")
    public void the_returned_epic_should_have_status(String status) {
        assertThat(createdEpic.getStatus()).isEqualTo(status);
    }
}
