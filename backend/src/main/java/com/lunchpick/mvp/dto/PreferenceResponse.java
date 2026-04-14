package com.lunchpick.mvp.dto;

import java.util.List;

public class PreferenceResponse {

    private Long memberId;
    private List<String> likeFoods;
    private List<String> dislikeFoods;

    public PreferenceResponse() {
    }

    public PreferenceResponse(Long memberId, List<String> likeFoods, List<String> dislikeFoods) {
        this.memberId = memberId;
        this.likeFoods = likeFoods;
        this.dislikeFoods = dislikeFoods;
    }

    public Long getMemberId() {
        return memberId;
    }

    public List<String> getLikeFoods() {
        return likeFoods;
    }

    public List<String> getDislikeFoods() {
        return dislikeFoods;
    }
}
