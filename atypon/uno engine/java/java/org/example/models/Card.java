package org.example.models;

import org.example.Abstractions.ICard;

import java.util.List;
import java.util.Objects;

public class Card implements ICard {
    private String color;
    private String value;

    public Card(String color, String value) {
        this.color = color;
        this.value = value;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public List<User> play(List<User> users) {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(color, card.color) && Objects.equals(value, card.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, value);
    }

    @Override
    public String toString() {
        return "Card{" +
                "color='" + color + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
