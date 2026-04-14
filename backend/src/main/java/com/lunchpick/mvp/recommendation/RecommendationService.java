package com.lunchpick.mvp.recommendation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lunchpick.mvp.dto.RecommendationRequest;
import com.lunchpick.mvp.dto.RestaurantCandidateResponse;
import com.lunchpick.mvp.team.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class RecommendationService {

    private static final Logger log = LoggerFactory.getLogger(RecommendationService.class);
    private static final String KAKAO_LOCAL_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/keyword.json";
    private static final int KAKAO_FETCH_SIZE = 10;
    private static final int CANDIDATE_COUNT = 3;

    private final TeamRepository teamRepository;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final String kakaoRestApiKey;

    public RecommendationService(
        TeamRepository teamRepository,
        @Value("${app.kakao.rest-api-key:}") String kakaoRestApiKey
    ) {
        this.teamRepository = teamRepository;
        this.kakaoRestApiKey = kakaoRestApiKey;
        this.objectMapper = new ObjectMapper();
        this.restTemplate = new RestTemplate();
    }

    public List<RestaurantCandidateResponse> recommend(RecommendationRequest request) {
        teamRepository.findById(request.getTeamId())
            .orElseThrow(() -> new IllegalArgumentException("Team not found: " + request.getTeamId()));

        if (kakaoRestApiKey == null || kakaoRestApiKey.isBlank()) {
            log.warn("Kakao API key is missing");
            return List.of();
        }

        try {
            return recommendFromKakao(request.getLocation());
        } catch (Exception e) {
            log.error("Failed to fetch recommendations from Kakao API. location={}", request.getLocation(), e);
            return List.of();
        }
    }

    private List<RestaurantCandidateResponse> recommendFromKakao(String location) throws Exception {
        String query = (location == null ? "" : location.trim()) + " 음식점";
        if (query.isBlank()) {
            return List.of();
        }

        String url = UriComponentsBuilder
            .fromHttpUrl(KAKAO_LOCAL_SEARCH_URL)
            .queryParam("query", query)
            .queryParam("size", KAKAO_FETCH_SIZE)
            .build()
            .encode()
            .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoRestApiKey);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            return List.of();
        }

        JsonNode root = objectMapper.readTree(response.getBody());
        JsonNode documents = root.path("documents");
        if (!documents.isArray() || documents.isEmpty()) {
            return List.of();
        }

        List<RestaurantCandidateResponse> candidates = new ArrayList<>();
        for (JsonNode doc : documents) {
            String name = doc.path("place_name").asText("");
            if (name.isBlank()) {
                continue;
            }

            String categoryName = doc.path("category_name").asText("");
            String category = extractLastCategory(categoryName);
            String address = doc.path("road_address_name").asText("");
            if (address.isBlank()) {
                address = doc.path("address_name").asText("");
            }
            String placeUrl = doc.path("place_url").asText("");

            candidates.add(new RestaurantCandidateResponse(
                generateCandidateId(),
                name,
                category,
                0,
                0,
                address,
                placeUrl
            ));
        }

        if (candidates.isEmpty()) {
            return List.of();
        }

        Collections.shuffle(candidates);
        return candidates.stream().limit(CANDIDATE_COUNT).toList();
    }

    private String extractLastCategory(String categoryName) {
        if (categoryName == null || categoryName.isBlank()) {
            return "음식점";
        }
        String[] parts = categoryName.split(">");
        return parts[parts.length - 1].trim();
    }

    private Long generateCandidateId() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
}
