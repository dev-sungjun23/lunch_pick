package com.lunchpick.mvp.vote;

import com.lunchpick.mvp.dto.DecisionResponse;
import com.lunchpick.mvp.dto.VoteRequest;
import com.lunchpick.mvp.dto.VoteResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoteResponse castVote(@Valid @RequestBody VoteRequest request) {
        return voteService.castVote(request);
    }

    @PostMapping("/teams/{teamId}/decision")
    public DecisionResponse decide(@PathVariable Long teamId) {
        return voteService.decide(teamId);
    }
}
