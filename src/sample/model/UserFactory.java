package sample.model;

public class UserFactory {
    public User getUser(String userRole){
        if(userRole == null)
            return null;
        if(userRole.equalsIgnoreCase("MEDCIN")){
            System.out.println(userRole);
            return new Medcin();
        }

        if(userRole.equalsIgnoreCase("SECRETAIRE"))
            return new Secretaire();

        return null;
    }
}
