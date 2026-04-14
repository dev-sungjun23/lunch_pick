package com.lunchpick.mvp.dto;

import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PreferenceSaveRequest {

    @NotNull
    private Long memberId;

    private List<String> likeFoods = new ArrayList<>();
    private List<String> dislikeFoods = new ArrayList<>();

    public PreferenceSaveRequest() {
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

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setLikeFoods(List<String> likeFoods) {
        this.likeFoods = likeFoods != null ? likeFoods : new ArrayList<>();
    }

    public void setDislikeFoods(List<String> dislikeFoods) {
        this.dislikeFoods = dislikeFoods != null ? dislikeFoods : new ArrayList<>();
    }
}
