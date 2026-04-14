package com.lunchpick.mvp.vote;

import com.lunchpick.mvp.dto.DecisionResponse;
import com.lunchpick.mvp.dto.VoteRequest;
import com.lunchpick.mvp.dto.VoteResponse;
import com.lunchpick.mvp.history.History;
import com.lunchpick.mvp.history.HistoryRepository;
import com.lunchpick.mvp.history.HistoryService;
import com.lunchpick.mvp.member.Member;
import com.lunchpick.mvp.member.MemberRepository;
import com.lunchpick.mvp.restaurant.Restaurant;
import com.lunchpick.mvp.restaurant.RestaurantRepository;
import com.lunchpick.mvp.team.Team;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class VoteService {

    private final VoteRepository voteRepository;
    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;
    private final HistoryRepository historyRepository;
    private final HistoryService historyService;
    private final Random random = new Random();

    public VoteService(
        VoteRepository voteRepository,
        MemberRepository memberRepository,
        RestaurantRepository restaurantRepository,
        HistoryRepository historyRepository,
        HistoryService historyService
    ) {
        this.voteRepository = voteRepository;
        this.memberRepository = memberRepository;
        this.restaurantRepository = restaurantRepository;
        this.historyRepository = historyRepository;
        this.historyService = historyService;
    }

    public VoteResponse castVote(VoteRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
            .orElseThrow(() -> new IllegalArgumentException("Member not found: " + request.getMemberId()));
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
            .orElseThrow(() -> new IllegalArgumentException("Restaurant not found: " + request.getRestaurantId()));

        Vote vote = voteRepository.findByMemberId(member.getId())
            .orElseGet(() -> new Vote(member, restaurant));
        vote.setMember(member);
        vote.setRestaurant(restaurant);

        Vote saved = voteRepository.save(vote);
        return new VoteResponse(saved.getMember().getId(), saved.getRestaurant().getId());
    }

    public DecisionResponse decide(Long teamId) {
        List<Vote> votes = voteRepository.findByMemberTeamId(teamId);
        if (votes.isEmpty()) {
            throw new IllegalArgumentException("No votes for team: " + teamId);
        }

        Map<Long, Long> voteCountMap = votes.stream()
            .collect(Collectors.groupingBy(v -> v.getRestaurant().getId(), Collectors.counting()));

        long maxVotes = voteCountMap.values().stream()
            .max(Long::compareTo)
            .orElseThrow(() -> new IllegalArgumentException("No votes for team: " + teamId));

        List<Long> topRestaurantIds = voteCountMap.entrySet().stream()
            .filter(entry -> entry.getValue() == maxVotes)
            .map(Map.Entry::getKey)
            .toList();

        Restaurant selected;
        String reason;
        if (topRestaurantIds.size() == 1) {
            Long restaurantId = topRestaurantIds.get(0);
            selected = findRestaurantOrThrow(restaurantId);
            reason = "max-votes";
        } else {
            selected = resolveTie(teamId, topRestaurantIds);
            reason = "tie-break";
        }

        Team team = votes.get(0).getMember().getTeam();
        historyService.saveSelection(team, selected);

        return new DecisionResponse(teamId, selected.getId(), selected.getName(), reason);
    }

    private Restaurant resolveTie(Long teamId, List<Long> topRestaurantIds) {
        List<History> histories = historyRepository.findByTeamIdOrderByDateDescIdDesc(teamId);
        Set<Long> topSet = topRestaurantIds.stream().collect(Collectors.toSet());

        for (History history : histories) {
            Long historyRestaurantId = history.getRestaurant().getId();
            if (topSet.contains(historyRestaurantId)) {
                topSet.remove(historyRestaurantId);
            }
        }

        if (topSet.size() == 1) {
            Long id = topSet.iterator().next();
            return findRestaurantOrThrow(id);
        }

        List<Long> remaining = topSet.isEmpty() ? topRestaurantIds : topSet.stream().toList();
        remaining = remaining.stream().sorted(Comparator.naturalOrder()).toList();
        Long randomId = remaining.get(random.nextInt(remaining.size()));
        return findRestaurantOrThrow(randomId);
    }

    private Restaurant findRestaurantOrThrow(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new IllegalArgumentException("Restaurant not found: " + restaurantId));
    }
}
