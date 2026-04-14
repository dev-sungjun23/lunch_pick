package com.lunchpick.mvp.history;

import com.lunchpick.mvp.dto.HistoryResponse;
import com.lunchpick.mvp.restaurant.Restaurant;
import com.lunchpick.mvp.team.Team;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HistoryService {

    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public void saveSelection(Team team, Restaurant restaurant) {
        LocalDate today = LocalDate.now();
        if (historyRepository.existsByTeam_IdAndRestaurant_IdAndDate(team.getId(), restaurant.getId(), today)) {
            return;
        }
        historyRepository.save(new History(team, restaurant, today));
    }

    @Transactional(readOnly = true)
    public List<HistoryResponse> getTeamHistory(Long teamId) {
        return historyRepository.findByTeamIdOrderByDateDescIdDesc(teamId)
            .stream()
            .map(history -> new HistoryResponse(
                history.getTeam().getId(),
                history.getRestaurant().getId(),
                history.getRestaurant().getName(),
                history.getDate()
            ))
            .collect(Collectors.toList());
    }
}
