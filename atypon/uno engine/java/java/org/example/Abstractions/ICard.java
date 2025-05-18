package org.example.Abstractions;

import org.example.models.User;
import java.util.List;

public interface ICard {
    String getColor();
    String getValue();
    List<User> play(List<User>users);
}
