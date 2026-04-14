package com.lunchpick.mvp.preference;

import com.lunchpick.mvp.member.Member;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "preferences")
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", unique = true)
    private Member member;

    @ElementCollection
    @CollectionTable(name = "preference_like_foods", joinColumns = @JoinColumn(name = "preference_id"))
    @Column(name = "food")
    private List<String> likeFoods = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "preference_dislike_foods", joinColumns = @JoinColumn(name = "preference_id"))
    @Column(name = "food")
    private List<String> dislikeFoods = new ArrayList<>();

    protected Preference() {
    }

    public Preference(Member member, List<String> likeFoods, List<String> dislikeFoods) {
        this.member = member;
        this.likeFoods = likeFoods != null ? likeFoods : new ArrayList<>();
        this.dislikeFoods = dislikeFoods != null ? dislikeFoods : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public List<String> getLikeFoods() {
        return likeFoods;
    }

    public List<String> getDislikeFoods() {
        return dislikeFoods;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setLikeFoods(List<String> likeFoods) {
        this.likeFoods = likeFoods != null ? likeFoods : new ArrayList<>();
    }

    public void setDislikeFoods(List<String> dislikeFoods) {
        this.dislikeFoods = dislikeFoods != null ? dislikeFoods : new ArrayList<>();
    }

}