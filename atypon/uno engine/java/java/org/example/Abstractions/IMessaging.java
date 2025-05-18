package org.example.Abstractions;

public interface IMessaging {
     void sendMessageToAll(String message);
     void sendMessageToUser(String name,String message);
     String getMessageFromUser(String name,String message);
}
