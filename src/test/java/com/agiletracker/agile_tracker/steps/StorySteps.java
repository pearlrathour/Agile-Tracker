package com.agiletracker.agile_tracker.steps;

import com.agiletracker.agile_tracker.dto.EpicDTO;
import com.agiletracker.agile_tracker.dto.StoryDTO;
import com.agiletracker.agile_tracker.entities.EpicEntity;
import com.agiletracker.agile_tracker.entities.StoryEntity;
import com.agiletracker.agile_tracker.services.EpicService;
import com.agiletracker.agile_tracker.services.StoryService;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StorySteps {

    @Autowired
    private EpicService epicService;

    @Autowired
    private StoryService storyService;

    private EpicEntity epic;
    private StoryDTO storyDTO;
    private StoryEntity createdStory;
    private List<StoryEntity> storyList;

    // ------------------- Create Epic (precondition) -------------------
    // NOTE: Renamed step to avoid duplication
    @Given("an epic for story with title {string} and createdById {string}")
    public void an_epic_for_story_with_title_and_createdById(String title, String createdById) {
        EpicDTO epicDTO = new EpicDTO();
        epicDTO.setTitle(title);
        epicDTO.setCreatedById(createdById);
        epicDTO.setDescription("Some description");
        epicDTO.setStoryIds(new ArrayList<>());
        epicDTO.setStatus("OPEN");
        epic = epicService.createEpic(epicDTO)
                .orElseThrow(() -> new RuntimeException("Epic creation failed"));
    }

    // ------------------- Create Story -------------------
    @Given("a new story with title {string}, description {string}, assigneeId {string}, reporterId {string}, and linked to the epic")
    public void a_new_story_with_details(String title, String description, String assigneeId, String reporterId) {
        storyDTO = new StoryDTO();
        storyDTO.setTitle(title);
        storyDTO.setDescription(description);
        storyDTO.setAssigneeId(assigneeId);
        storyDTO.setReporterId(reporterId);
        storyDTO.setEpicId(epic.getId());
        storyDTO.setStoryPoints(5); // default points for test
    }

    @When("I create the story")
    public void i_create_the_story() {
        createdStory = storyService.createStory(storyDTO)
                .orElseThrow(() -> new RuntimeException("Story creation failed"));
    }

    @Then("the returned story should have title {string} and status {string}")
    public void the_returned_story_should_have_title_and_status(String title, String status) {
        assertThat(createdStory.getTitle()).isEqualTo(title);
        assertThat(createdStory.getStatus()).isEqualTo(status);
    }

    // ------------------- Get All Stories -------------------
    @Given("there are stories in the system")
    public void there_are_stories_in_the_system() {
        storyList = storyService.getAllStories();
        if (storyList.isEmpty()) {
            // create a default epic and story
            EpicDTO epicDTO = new EpicDTO();
            epicDTO.setTitle("Default Epic");
            epicDTO.setCreatedById("pm1");
            epicDTO.setDescription("Default epic");
            epicDTO.setStoryIds(new ArrayList<>());
            epicDTO.setStatus("OPEN");
            EpicEntity defaultEpic = epicService.createEpic(epicDTO)
                    .orElseThrow(() -> new RuntimeException("Default epic creation failed"));

            StoryDTO defaultStory = new StoryDTO();
            defaultStory.setTitle("Default Story");
            defaultStory.setDescription("Default description");
            defaultStory.setEpicId(defaultEpic.getId());
            defaultStory.setAssigneeId("dev1");
            defaultStory.setReporterId("pm1");
            defaultStory.setStoryPoints(3);
            storyService.createStory(defaultStory)
                    .orElseThrow(() -> new RuntimeException("Default story creation failed"));
        }
    }

    @When("I fetch all stories")
    public void i_fetch_all_stories() {
        storyList = storyService.getAllStories();
    }

    @Then("the response should contain at least {int} story")
    public void the_response_should_contain_at_least_story(Integer count) {
        assertThat(storyList.size()).isGreaterThanOrEqualTo(count);
    }
}
