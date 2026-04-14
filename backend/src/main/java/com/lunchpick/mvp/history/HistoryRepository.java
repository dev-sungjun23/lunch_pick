package com.lunchpick.mvp.history;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByTeamIdOrderByDateDescIdDesc(Long teamId);

    boolean existsByTeam_IdAndRestaurant_IdAndDate(Long teamId, Long restaurantId, LocalDate date);
}