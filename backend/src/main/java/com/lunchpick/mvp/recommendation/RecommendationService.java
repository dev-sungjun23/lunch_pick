package com.lunchpick.mvp.recommendation;

import com.lunchpick.mvp.dto.RecommendationRequest;
import com.lunchpick.mvp.dto.RestaurantCandidateResponse;
import com.lunchpick.mvp.member.Member;
import com.lunchpick.mvp.member.MemberRepository;
import com.lunchpick.mvp.preference.Preference;
import com.lunchpick.mvp.preference.PreferenceRepository;
import com.lunchpick.mvp.restaurant.Restaurant;
import com.lunchpick.mvp.restaurant.RestaurantRepository;
import com.lunchpick.mvp.team.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RecommendationService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final PreferenceRepository preferenceRepository;
    private final RestaurantRepository restaurantRepository;

    public RecommendationService(
        TeamRepository teamRepository,
        MemberRepository memberRepository,
        PreferenceRepository preferenceRepository,
        RestaurantRepository restaurantRepository
    ) {
        this.teamRepository = teamRepository;
        this.memberRepository = memberRepository;
        this.preferenceRepository = preferenceRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<RestaurantCandidateResponse> recommend(RecommendationRequest request) {
        teamRepository.findById(request.getTeamId())
            .orElseThrow(() -> new IllegalArgumentException("Team not found: " + request.getTeamId()));

        List<Member> members = memberRepository.findByTeamId(request.getTeamId());
        Set<String> dislikedCategories = collectDislikedCategories(members);

        return restaurantRepository
            .findByDistanceLessThanEqualAndPriceRangeLessThanEqual(request.getMaxDistance(), request.getBudget())
            .stream()
            .filter(restaurant -> !dislikedCategories.contains(normalize(restaurant.getCategory())))
            .sorted(Comparator.comparing(Restaurant::getDistance).thenComparing(Restaurant::getPriceRange))
            .limit(3)
            .map(restaurant -> new RestaurantCandidateResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getCategory(),
                restaurant.getDistance(),
                restaurant.getPriceRange()
            ))
            .collect(Collectors.toList());
    }

    private Set<String> collectDislikedCategories(List<Member> members) {
        return members.stream()
            .map(member -> preferenceRepository.findByMemberId(member.getId()).orElse(null))
            .filter(preference -> preference != null)
            .map(Preference::getDislikeFoods)
            .flatMap(List::stream)
            .map(this::normalize)
            .collect(Collectors.toSet());
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
    }
}
