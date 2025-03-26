package com.github.user.activity.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/github")
public class GithubUserController {

    // GitHub Official API for Users
    private final String GITHUB_API_BASE_URL = "https://api.github.com/users/";
    private static final Logger logger = LoggerFactory.getLogger(GithubUserController.class);

    @GetMapping("/activity/{username}")
    public List<JsonNode> getUserActivity(@PathVariable String username, @RequestParam(required = false) String eventType,
                                          @RequestParam(required = false) String repoName,
                                          @RequestParam(required = false) String actor) {

        RestTemplate restTemplate = new RestTemplate();
        String url = GITHUB_API_BASE_URL + username + "/events";

        try {
            String response = restTemplate.getForObject(url, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode events= objectMapper.readTree(response);
            List<JsonNode> filteredEvents = new ArrayList<>();

            for (JsonNode event : events) {
                boolean isType = (eventType == null) || event.get("type").asText().equalsIgnoreCase(eventType);
                boolean isRepo = (repoName == null) || event.get("repo").get("name").asText().equalsIgnoreCase(repoName);
                boolean isActor = (actor == null) || event.get("actor").get("login").asText().equalsIgnoreCase(actor);

                if (isType && isRepo && isActor) {
                    filteredEvents.add(event);
                    logger.info("username: " + event.get("actor").get("login"));
                }
            }

            return filteredEvents;
        } catch (Exception e) {
            logger.error("Username not found");
            return List.of();
        }
    }
}
