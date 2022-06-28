package sample;

import javafx.collections.ObservableList;
import sample.model.User;

public interface UserDAO {
   boolean ajouterUser(User user);
   boolean modifierUser(User oldUser, User newUser);
   boolean supprimerUser(User user);
    ObservableList<User> getUsers();
    boolean verifyUsername(String username);
}
