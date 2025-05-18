package org.example.factory;
import org.example.Abstractions.ICard;
import org.example.models.Card;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import java.util.function.Supplier;

public class CardsFactory {
    private static final Map<String, Supplier<ICard>> cardMap = new HashMap<>();

    public static void register(String color,String value, Supplier<ICard> supplier) {
        cardMap.put(color+"_"+value, supplier);
    }

    public static ICard createCard(String color,String value) {
        Supplier<ICard> supplier = cardMap.get(color+"_"+value);
        if (supplier != null) {
            return supplier.get();
        }
        throw new IllegalArgumentException("Unknown card type: " + color+"_"+value);
    }

    public static List<ICard> getAllCards() {
        List<ICard> allCards = new ArrayList<>();
        for (Supplier<ICard> supplier : cardMap.values()) {
            allCards.add(supplier.get());
        }
        System.out.print(allCards.size());
        return allCards;
    }
    public static void clearFactory()
    {
        cardMap.clear();
    }
}
