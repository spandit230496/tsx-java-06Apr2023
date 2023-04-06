package com.github.webhook.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.github.webhook.entity.Commit;
@RestController
@RequestMapping("/api/webhook")
public class WebhookController {

    @PostMapping
    public ResponseEntity<String>send(@RequestBody String requestBody){
        try{
            System.out.println("log"+requestBody);
        }
        catch (Exception e){
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(requestBody, HttpStatus.OK);

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(WebhookController.class);
    private static final String GITHUB_API_URL = "https://api.github.com";

    @GetMapping("/commits")
    public ResponseEntity<String> getCommits() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "WebhookController");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = GITHUB_API_URL + "/repos/spandit230496/dummy-github-events/commits";
        ResponseEntity<Commit[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Commit[].class);
        Commit[] commits = response.getBody();
        for (Commit commit : commits) {
            LOGGER.info("Commit SHA: {}, message: {}", commit.getSha(), commit.getMessage());
        }
        return ResponseEntity.ok("Commits retrieved successfully");
    }
    }


