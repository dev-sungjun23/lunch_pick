package com.lunchpick.mvp.preference;

import com.lunchpick.mvp.dto.PreferenceResponse;
import com.lunchpick.mvp.dto.PreferenceSaveRequest;
import com.lunchpick.mvp.member.Member;
import com.lunchpick.mvp.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final MemberRepository memberRepository;

    public PreferenceService(PreferenceRepository preferenceRepository, MemberRepository memberRepository) {
        this.preferenceRepository = preferenceRepository;
        this.memberRepository = memberRepository;
    }

    public PreferenceResponse savePreference(PreferenceSaveRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
            .orElseThrow(() -> new IllegalArgumentException("Member not found: " + request.getMemberId()));

        Preference preference = preferenceRepository.findByMemberId(member.getId())
            .orElseGet(() -> new Preference(member, request.getLikeFoods(), request.getDislikeFoods()));

        preference.setMember(member);
        preference.setLikeFoods(request.getLikeFoods());
        preference.setDislikeFoods(request.getDislikeFoods());

        Preference saved = preferenceRepository.save(preference);
        return new PreferenceResponse(
            saved.getMember().getId(),
            saved.getLikeFoods(),
            saved.getDislikeFoods()
        );
    }
}
