package org.example.services;
import org.example.Abstractions.ICard;
import org.example.models.Card;
import org.example.models.User;
import org.example.repos.UsersRepo;

import java.util.ArrayList;
import java.util.List;

public class UserGenerator {
    public static List<String>names = new ArrayList<>();


    public static List<User> generateUsers(List<ICard>cards)
    {
        List<User> users =new ArrayList<>();
        for(int x=0;x<names.size();x++)
        {
            users.add(new User(names.get(x),0,null));
        }
        return users;
    }
}
