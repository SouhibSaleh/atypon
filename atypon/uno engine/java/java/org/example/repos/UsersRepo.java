package org.example.repos;

import org.example.Abstractions.ICard;
import org.example.models.Card;
import org.example.models.User;

import java.util.ArrayList;
import java.util.List;


public class UsersRepo {
    private List<User>users = new ArrayList<>();
    private int size =0;
    private static class Holder {
        private static final UsersRepo INSTANCE = new UsersRepo();
    }

    public static UsersRepo getInstance() {
        return Holder.INSTANCE;
    }
    public int getSize()
    {
        return size;
    }
    public void addUser(User user)
    {
        size++;
        users.add(user);
    }
    public User getUserByName(String name)
    {
        for(var x:users)
        {
            if(x.getName()==name)
            {
                return x;
            }
        }
        return null;
    }
    public void addCard(String name,List<ICard>cards)
    {
        for(var x:users)
        {
            if(x.getName()==name)
            {
                x.setCards(cards);
                break;
            }
        }

    }
    public void UpdateScore(String name)
    {
        for(var x:users)
        {
            if(x.getName()==name)
            {
                x.setScore(x.getScore()+1);
                break;
            }
        }
    }
    public List<User>getAllUsers()
    {
        return users;
    }
    public void updateAllUsers(List<User>tempUsers)
    {
        users = tempUsers;
    }
    public void removeCardFromUser(String name,String color,String value){
      User tempUser = getUserByName(name);
      for(int x=0;x<tempUser.getCards().size();x++)
      {
          ICard temp= tempUser.getCards().get(x);
            if(temp.getColor()==color&&temp.getValue()==value)
          {
              tempUser.getCards().remove(x);
              return;
          }
      }

    }
    public void addSingleCardToUser(String name,ICard card)
    {
       var temp =  getUserByName(name);
       temp.getCards().add(card);

    }
}
