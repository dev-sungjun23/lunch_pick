package com.lunchpick.mvp.vote;

import com.lunchpick.mvp.member.Member;
import com.lunchpick.mvp.restaurant.Restaurant;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "votes",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_vote_member", columnNames = {"member_id"})
    }
)
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    protected Vote() {
    }

    public Vote(Member member, Restaurant restaurant) {
        this.member = member;
        this.restaurant = restaurant;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

}