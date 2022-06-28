package sample;

public class  CabinetMedicale {
    private static PatientDAO patientDAO = new PatientDAOImpl();
    private static UserDAO userDAO = new UserDAOImpl();

    public static PatientDAO getPatientDAO(){
        return patientDAO;
    }

    public static UserDAO getUserDAO() {
        return userDAO;
    }
}
