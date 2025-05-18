package org.example.services;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.example.Abstractions.ICard;
import org.example.factory.CardsFactory;
import org.example.models.Card;

import javax.annotation.processing.SupportedSourceVersion;

public class CardGenerator {

    public static List<String>colors;
    public static List<String>values;

    public CardGenerator(List<String> colors, List<String> values) {
        this.colors = colors;
        this.values = values;
    }

    public static List<ICard> generateSimpleCards(int numOfTimes)
    {
        for(var x:colors)
        {
            for(var y:values)
            {
                CardsFactory.register(x,y,()->new Card(x,y));
            }
        }
        List<ICard>cards = getAvailableCards();
        List<ICard>generatedCards = new ArrayList<>();
        for(int x=0;x<numOfTimes;x++)
        {
            generatedCards.addAll(cards);
        }
        return generatedCards;
    }


    public static List<ICard> getAvailableCards()
    {
        return CardsFactory.getAllCards();
    }
    public static ICard generateRandomCard()
    {
        Random r = new Random();
        return getAvailableCards().get(r.nextInt(getAvailableCards().size()));
    }
}
