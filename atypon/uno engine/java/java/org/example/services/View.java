package org.example.services;

import org.example.Abstractions.IMessaging;

public class View implements IMessaging {

    @Override
    public void sendMessageToAll(String message) {
        System.out.println(message);
    }

    @Override
    public void sendMessageToUser(String name, String message) {
        System.out.println("Player "+name+" -> " +message);
    }

    @Override
    public String getMessageFromUser(String name,String message) {
        return "From Player "+name+" -> " +message;
    }
}
