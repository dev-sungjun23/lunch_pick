package com.lunchpick.mvp.team;

import com.lunchpick.mvp.dto.TeamCreateRequest;
import com.lunchpick.mvp.dto.TeamResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public TeamResponse createTeam(TeamCreateRequest request) {
        Team team = new Team(request.getName());
        Team saved = teamRepository.save(team);
        return new TeamResponse(saved.getId(), saved.getName());
    }
}
