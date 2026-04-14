package com.lunchpick.mvp.vote;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByMemberId(Long memberId);
    List<Vote> findByMemberTeamId(Long teamId);
}