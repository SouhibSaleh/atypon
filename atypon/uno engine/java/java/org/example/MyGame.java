package org.example;
import org.example.Abstractions.ICard;
import org.example.Abstractions.IMessaging;
import org.example.factory.CardsFactory;
import org.example.models.Card;
import org.example.models.User;
import org.example.repos.UsersRepo;
import org.example.services.CardGenerator;
import org.example.services.UserGenerator;
import org.example.services.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
public class MyGame extends Game{
    private int numberOfRounds;
    private final IMessaging viewer;
    private int currentPlayerIndex;
    private ICard currentCard;
    private ICard takeCard()
    {
        var temp = cards.get(0);
        cards.remove(0);
        return temp;
    }
    public MyGame()
    {
        numberOfRounds = 3;
        viewer = new View();
        currentPlayerIndex = 0;
    }
    @Override
    public void generateCards(int n)
    {
        super.generateCards(n);
    }
    @Override
    public void addSpecialCards()
    {
        super.addSpecialCards();
    }
    private List<ICard>getPartOfCards(int n)
    {
        List<ICard>temp = new ArrayList<>();
        for(int x=0;x<n;x++)
        {
            temp.add(cards.get(0));
            cards.remove(0);
        }
        return temp;
    }
    @Override
    void initializeGame() {
        for(var x:UsersRepo.getInstance().getAllUsers())
        {
            x.setCards(null);
        }
        generateCards(40000);
        addSpecialCards();
        Collections.shuffle(cards);
        currentCard = takeCard();
        for(var x:UsersRepo.getInstance().getAllUsers())
        {
            x.setCards(getPartOfCards(7));
        }
    }

    @Override
    void generateUsers() {
        List<String>ls = new ArrayList<>();
        ls.add("SUHIB");
        ls.add("TOLEEN");
        ls.add("SARAH");
        UserGenerator.names = ls;
        var temp = UserGenerator.generateUsers(cards);
        for(var x: temp)
           UsersRepo.getInstance().addUser(x);
    }

    User getCurrentUser()
    {
        String currentName = UsersRepo.getInstance()
                .getAllUsers()
                .get(currentPlayerIndex)
                .getName();

        User currnetUser = UsersRepo.getInstance().getUserByName(currentName);
        return currnetUser;
    }
    void CardOperation(ICard card,User user,ICard a)
    {
        currentCard = card;
        card.play(UsersRepo.getInstance().getAllUsers());
        UsersRepo.getInstance().removeCardFromUser(user.getName(), a.getColor(), a.getValue());
        viewer.sendMessageToAll("User "+user.getName()+ " played "+currentCard.getColor()+"_"+currentCard.getValue());
    }

    @Override
    void playTurn() {
        User user  = getCurrentUser();
        viewer.sendMessageToAll("User "+user.getName()+ " Turn");

        for(int x=0;x<user.getCards().size();x++)
        {
            var a = user.getCards().get(x);
            if(!(a instanceof Card)||currentCard.getColor()==a.getColor()|| currentCard.getValue()==a.getValue()||
                    !(currentCard instanceof Card))
            {
                CardOperation(CardsFactory.createCard(a.getColor(),a.getValue()),user,a);
                return;
            }
        }
        UsersRepo.getInstance().addSingleCardToUser(user.getName(),takeCard());
        viewer.sendMessageToAll("User "+user.getName()+ " Took card");
    }

    @Override
    boolean checkIfFinished() {
        for(var x:UsersRepo.getInstance().getAllUsers())
        {
            if(x.getCards().isEmpty())
            {
                return true;
            }
        }
        return false;
    }
    User winnerUser()
    {
        for(var x:UsersRepo.getInstance().getAllUsers())
        {
            if(x.getCards().isEmpty())
            {
                return x;
            }
        }
        return null;
    }
    void printWinner(User user)
    {
        if(user != null)
        {
            viewer.sendMessageToAll("THE WINNER THIS ROUND IS : "+user.getName());
            UsersRepo.getInstance().UpdateScore(user.getName());
        }
    }
    void printFinalResults()
    {
        viewer.sendMessageToAll("!!SCORE BOARD!!");
        for(var x:UsersRepo.getInstance().getAllUsers())
        {
            System.out.println(x.getName()+" "+x.getScore());
        }
    }
    @Override
    public void play() {
        generateUsers();
        while(numberOfRounds--!=0)
        {
            initializeGame();
            while(!checkIfFinished())
            {
                playTurn();
                currentPlayerIndex++;
                if(currentPlayerIndex==UsersRepo.getInstance().getSize())
                {
                    currentPlayerIndex=0;
                }
            }
            printWinner(winnerUser());
        }
        printFinalResults();
    }

}
