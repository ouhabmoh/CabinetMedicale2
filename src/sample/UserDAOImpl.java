package sample;

import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.beans.property.adapter.JavaBeanObjectProperty;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.util.Callback;
import sample.Datasource.Datasource;
import sample.model.Patient;
import sample.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl  implements UserDAO{

    private ObservableList<User> users = FXCollections.observableArrayList(extractor);
    static Callback<User, Observable[]> extractor = new Callback<User, Observable[]>() {
        List<Property> properties = new ArrayList<>();
        @Override
        public Observable[] call(User p) {
            JavaBeanObjectProperty pseudo = null;
            JavaBeanObjectProperty nom = null;
            JavaBeanObjectProperty prenom = null;
            try {
                pseudo = JavaBeanObjectPropertyBuilder.create()
                        .bean(p).name("pseudo").build();
                nom = JavaBeanObjectPropertyBuilder.create()
                        .bean(p).name("nom").build();
                prenom = JavaBeanObjectPropertyBuilder.create()
                        .bean(p).name("prenom").build();
                // hack around losing weak references ...
                properties.add(pseudo);
                properties.add(prenom);
                properties.add(nom);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return new Observable[] {nom, prenom, pseudo};
        }
    };
    public UserDAOImpl() {
        userList();
    }

    @Override
    public boolean ajouterUser(User user) {
        int id = Datasource.getInstance().insertUser(user);
        if(id > -1){
            user.setId(id);
            users.add(user);
            return true;
        }

        return false;
    }

    @Override
    public boolean modifierUser(User oldUser, User newUser) {
        if(Datasource.getInstance().updateUser(newUser)){
            oldUser.setPseudo(newUser.getPseudo());
            oldUser.setNom(newUser.getNom());
            oldUser.setPrenom(newUser.getPrenom());
            return true;
        }
        return false;
    }

    @Override
    public boolean supprimerUser(User user) {
        if(Datasource.getInstance().deleteUser(user.getId())){
            users.remove(user);
            return true;
        }

        return false;
    }

    @Override
    public ObservableList<User> getUsers() {
        SortedList<User> sortedList = new SortedList<>(users,
                (o1, o2) -> o1.getId() > o2.getId() ? -1 : 1);
        return sortedList;

    }

    @Override
    public boolean verifyUsername(String username) {
        return Datasource.getInstance().login(username) != null;
    }

    private void userList() {
        Task<ObservableList<User>> taskUsers = new GetAllUsers();
        taskUsers.setOnSucceeded((e) -> users.setAll(taskUsers.getValue()));
        new Thread(taskUsers).start();

    }

    class GetAllUsers extends Task {
        @Override
        public ObservableList<User> call() {
            return FXCollections.observableArrayList
                    (Datasource.getInstance().queryUsers());
        }
    }
}
