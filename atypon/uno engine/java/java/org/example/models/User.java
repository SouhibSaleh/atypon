package org.example.models;

import org.example.Abstractions.ICard;

import java.util.*;

public class User {
    private String name;
    private int score;
    private  List<ICard>cards;

    public User(String name, int score, List<ICard> cards) {
        this.name = name;
        this.score = score;
        this.cards = cards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<ICard> getCards() {
        return cards;
    }

    public void setCards(List<ICard> cards) {
        this.cards = cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return score == user.score && Objects.equals(name, user.name) && Objects.equals(cards, user.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, score, cards);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", cards=" + cards +
                '}';
    }
}
