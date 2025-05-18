package org.example;

import org.example.Abstractions.ICard;
import org.example.models.Card;
import org.example.models.User;
import org.example.factory.CardsFactory;

import java.util.ArrayList;
import java.util.List;
import org.example.services.CardGenerator;
import org.example.repos.UsersRepo;
public abstract class Game {
    protected List<ICard>cards = new ArrayList<>();
    private class AddOneToAll implements ICard{
        private String color;
        private String value;
        private int numberOfCards;
        public AddOneToAll()
        {
            this.color = "Black";
            this.value = "++1";
        }

        public void setNumberOfCards(int numberOfCards) {
            this.numberOfCards = numberOfCards;
        }
        public int getNumberOfCards()
        {
            return this.numberOfCards;
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
            for(var x:users)
            {
                ICard card = CardGenerator.generateRandomCard();
                x.getCards().add(card);
            }
            return users;
        }
    }
    public void generateCards(int n)
    {
        CardsFactory.clearFactory();
        cards.clear();
        List<String>colors = new ArrayList<>();
        colors.add("Yellow");
        colors.add("Green");
        colors.add("Blue");
        colors.add("Red");
        List<String>values = new ArrayList<>();
        for(int x=1;x<=9;x++)
        {
            values.add(""+x);
        }
        cards = new CardGenerator(colors,values).generateSimpleCards(n);
    }
    public void addSpecialCards()
    {
        var x = new AddOneToAll();
        x.setNumberOfCards(40);
        CardsFactory.register(x.getColor(),x.getValue(),()->x);
        for(int i=0;i<x.numberOfCards;i++)cards.add(x);

    }
    abstract void initializeGame();
    abstract void generateUsers();
    abstract void playTurn();
    abstract boolean checkIfFinished();
    public abstract void play();
}
