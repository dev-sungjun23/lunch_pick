package com.lunchpick.mvp.member;

import com.lunchpick.mvp.dto.MemberCreateRequest;
import com.lunchpick.mvp.dto.MemberResponse;
import com.lunchpick.mvp.team.Team;
import com.lunchpick.mvp.team.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    public MemberService(MemberRepository memberRepository, TeamRepository teamRepository) {
        this.memberRepository = memberRepository;
        this.teamRepository = teamRepository;
    }

    public MemberResponse addMember(MemberCreateRequest request) {
        Team team = teamRepository.findById(request.getTeamId())
            .orElseThrow(() -> new IllegalArgumentException("Team not found: " + request.getTeamId()));

        Member member = new Member(request.getName(), team);
        Member saved = memberRepository.save(member);
        return new MemberResponse(saved.getId(), saved.getName(), saved.getTeam().getId());
    }
}
