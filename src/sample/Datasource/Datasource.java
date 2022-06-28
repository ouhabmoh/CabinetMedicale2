package sample.Datasource;

import sample.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Datasource {

    public static final String DB_PATH = "../../dist/db/CabinetMedicale.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite: "+ DB_PATH;
    private Connection conn;
    private static Datasource instance = new Datasource();

    public static final String USERS_TABLE = "USERS";
    public static final String RENDEZ_VOUS_TABLE = "RENDEZ_VOUS";
    public static final String CONSULTATIONS_TABLE = "CONSULTATIONS";
    public static final String PATIENTS_TABLE = "PATIENTS";
    public static final String ORDONNANCES_TABLE = "ORDONNANCE";

    public static final String COLUMN_USER_ID = "id_user";
    public static final String COLUMN_USERNAME = "pseudo";
    public static final String COLUMN_PASSWORD = "motpasse";
    public static final String COLUMN_USER_NAME = "nom";
    public static final String COLUMN_USER_SNAME = "prenom";
    public static final String COLUMN_USER_PHONE = "tel";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_ADRESSE = "adresse";
    public static final String COLUMN_USER_DOB = "date_naissance";
    public static final String COLUMN_USER_ROLE = "role";

    //ResultSet column index starts from 1, zero is invalid

    public static final int INDEX_USER_ID = 1;
    public static final int INDEX_USERNAME = 2;
    public static final int INDEX_PASSWORD = 3;
    public static final int INDEX_USER_NAME = 4;
    public static final int INDEX_USER_SNAME = 5;
    public static final int INDEX_USER_PHONE = 6;
    public static final int INDEX_USER_EMAIL = 7;
    public static final int INDEX_USER_ADRESSE = 8;
    public static final int INDEX_USER_DOB = 9;
    public static final int INDEX_USER_ROLE = 10;

    public static final String COLUMN_PATIENT_ID = "id_patient";
    public static final String COLUMN_PATIENT_NAME = "nom";
    public static final String COLUMN_PATIENT_SNAME = "prenom";
    public static final String COLUMN_PATIENT_PHONE = "tel";
    public static final String COLUMN_PATIENT_EMAIL = "email";
    public static final String COLUMN_PATIENT_ADRESSE = "adresse";
    public static final String COLUMN_PATIENT_DOB = "date_naissance";
    public static final String COLUMN_PATIENT_POID = "poid";
    public static final String COLUMN_PATIENT_SANG = "sang";
    public static final String COLUMN_PATIENT_LONGUEUR = "longueur";
    public static final String COLUMN_PATIENT_MALADIES = "maladies";
    public static final String COLUMN_PATIENT_FIRST_DATE = "date1";

    public static final int INDEX_PATIENT_ID = 1;
    public static final int INDEX_PATIENT_NAME = 2;
    public static final int INDEX_PATIENT_SNAME = 3;
    public static final int INDEX_PATIENT_PHONE = 4;
    public static final int INDEX_PATIENT_EMAIL = 5;
    public static final int INDEX_PATIENT_ADRESSE = 6;
    public static final int INDEX_PATIENT_DOB = 7;
    public static final int INDEX_PATIENT_POID = 8;
    public static final int INDEX_PATIENT_SANG = 9;
    public static final int INDEX_PATIENT_LONGUEUR = 10;
    public static final int INDEX_PATIENT_MALADIES = 11;
    public static final int INDEX_PATIENT_FIRST_DATE = 12;

    public static final String COLUMN_RENDEZ_VOUS_ID = "id_rendez_vous";
    public static final String COLUMN_RENDEZ_VOUS_NAME = "nom";
    public static final String COLUMN_RENDEZ_VOUS_SNAME = "prenom";
    public static final String COLUMN_RENDEZ_VOUS_PHONE = "tel";
    public static final String COLUMN_RENDEZ_VOUS_EMAIL = "email";
    public static final String COLUMN_RENDEZ_VOUS_DATE = "date";
    public static final String COLUMN_RENDEZ_VOUS_HEURE = "heure";

    public static final String COLUMN_CONSULTATION_ID = "id_consultation";
    public static final String COLUMN_CONSULTATION_DIAGNOSTIC = "diagnostic";
    public static final String COLUMN_CONSULTATION_MALADIES = "maladies";
    public static final String COLUMN_CONSULTATION_OBSERVATION = "observation";
    public static final String COLUMN_CONSULTATION_MONTANT_PAYEE = "montant_payee";
    public static final String COLUMN_CONSULTATION_DATE = "date";
    public static final String COLUMN_CONSULTATION_CODE = "consultation_code";

    public static final int INDEX_CONSULTATION_ID = 1;
    public static final int INDEX_CONSULTATION_ID_PATIENT = 2;
    public static final int INDEX_CONSULTATION_NAME_USER = 3;
    public static final int INDEX_CONSULTATION_DIAGNOSTIC = 4;
    public static final int INDEX_CONSULTATION_MALADIES = 5;
    public static final int INDEX_CONSULTATION_OBSERVATION = 6;
    public static final int INDEX_CONSULTATION_MONTANT_PAYEE = 7;
    public static final int INDEX_CONSULTATION_DATE = 8;
    private static final int INDEX_CONSULTATION_CODE = 9 ;


    public static final String COLUMN_ORDONNANCE_ID = "id_ordannance";
    public static final String COLUMN_ORDONNANCE_ID_CONSULTAION = "id_consultation";
    public static final String COLUMN_ORDONNANCE_MEDICAMENTS = "medicaments";
    public static final String COLUMN_ORDONNANCE_OBSERVATION = "observation";

    public static final int INDEX_ORDONNANCE_ID = 1;
    public static final int INDEX_ORDONNANCE_ID_CONSULTAION = 2;
    public static final int INDEX_ORDONNANCE_MEDICAMENTS = 3;


    public static final String QUERY_LOGIN = "SELECT " + COLUMN_USER_ID + "," + COLUMN_PASSWORD + " FROM " + USERS_TABLE + " WHERE " + COLUMN_USERNAME + " = ? LIMIT 1";
    public static final String QUERY_USER = "SELECT * FROM " + USERS_TABLE + " WHERE " + COLUMN_USER_ID + " = ? LIMIT 1";
    public static final String QUERY_RENDEZ_VOUS_ALL = "SELECT * FROM " + RENDEZ_VOUS_TABLE;

    public static final String QUERY_RENDEZ_VOUS = "SELECT * FROM " + RENDEZ_VOUS_TABLE + " WHERE " + COLUMN_RENDEZ_VOUS_DATE + "= ? ORDER BY " + COLUMN_RENDEZ_VOUS_DATE;
    public static final String QUERY_CONSULTATIONS = "SELECT * FROM " + CONSULTATIONS_TABLE + " WHERE " + COLUMN_PATIENT_ID + " = ? ORDER BY " + COLUMN_CONSULTATION_DATE + " DESC";
    public static final String COUNT_CONSULTATIONS = "SELECT COUNT(*) FROM "+CONSULTATIONS_TABLE ;
    public static final String QUERY_CONSULTATION_DATE = "SELECT " + COLUMN_CONSULTATION_DATE + " FROM " + CONSULTATIONS_TABLE + " WHERE " + COLUMN_PATIENT_ID + " = ? ORDER BY " + COLUMN_CONSULTATION_DATE + " DESC LIMIT 1";
    public static final String QUERY_PATIENTS = "SELECT * FROM " + PATIENTS_TABLE;
    public static final String QUERY_PATIENT = "SELECT * FROM " + PATIENTS_TABLE + " WHERE " + COLUMN_PATIENT_NAME + " LIKE ? AND " + COLUMN_PATIENT_SNAME + " LIKE ? LIMIT 1";
    public static final String QUERY_USERS = "SELECT * FROM " + USERS_TABLE;
    public static final String QUERY_ORDONNANCE = "SELECT * FROM " + ORDONNANCES_TABLE + " WHERE " + COLUMN_CONSULTATION_ID + " = ? LIMIT 1";

    public static final String INSERT_USER = "INSERT INTO " + USERS_TABLE
            + '(' + COLUMN_USERNAME + "," + COLUMN_PASSWORD + "," + COLUMN_USER_NAME + "," + COLUMN_USER_SNAME + ","
            + COLUMN_USER_PHONE + "," + COLUMN_USER_EMAIL + "," + COLUMN_USER_ADRESSE + "," + COLUMN_USER_DOB+ "," + COLUMN_USER_ROLE
            + ") VALUES (?,?,?,?,?,?,?,?,?)";

    public static final String INSERT_PATIENT = "INSERT INTO " + PATIENTS_TABLE
            + '(' + COLUMN_PATIENT_NAME + "," + COLUMN_PATIENT_SNAME + "," + COLUMN_PATIENT_PHONE + "," + COLUMN_PATIENT_EMAIL
            + "," + COLUMN_PATIENT_DOB + "," + COLUMN_PATIENT_ADRESSE + "," + COLUMN_PATIENT_POID + "," + COLUMN_PATIENT_SANG
            + "," + COLUMN_PATIENT_LONGUEUR + "," + COLUMN_PATIENT_MALADIES + "," + COLUMN_PATIENT_FIRST_DATE + ") VALUES (?,?,?,?,?,?,?,?,?,?,?)";

    public static final String INSERT_RENDEZ_VOUS = "INSERT INTO " + RENDEZ_VOUS_TABLE + '(' + COLUMN_RENDEZ_VOUS_NAME + "," + COLUMN_RENDEZ_VOUS_SNAME + "," +
            COLUMN_RENDEZ_VOUS_DATE + "," + COLUMN_RENDEZ_VOUS_HEURE + ") VALUES (?,?,?,?)";

    public static final String INSERT_CONSULTATION = "INSERT INTO " + CONSULTATIONS_TABLE + '(' + COLUMN_PATIENT_ID + "," + COLUMN_USER_ID + "," + COLUMN_CONSULTATION_DIAGNOSTIC + "," + COLUMN_CONSULTATION_MALADIES + ","
            + COLUMN_CONSULTATION_OBSERVATION + "," + COLUMN_CONSULTATION_MONTANT_PAYEE + "," + COLUMN_CONSULTATION_DATE+","+COLUMN_CONSULTATION_CODE + ") VALUES (?,?,?,?,?,?,?,?)";

    public static final String INSERT_ORADANNANCE = "INSERT INTO " + ORDONNANCES_TABLE + '(' + COLUMN_ORDONNANCE_ID_CONSULTAION + "," + COLUMN_ORDONNANCE_MEDICAMENTS+"," + COLUMN_ORDONNANCE_OBSERVATION + ") VALUES (?,?,?)";

    public static final String UPDATE_CONSULTATION = "UPDATE " + CONSULTATIONS_TABLE + " SET " + COLUMN_CONSULTATION_DIAGNOSTIC + " = ? ," + COLUMN_CONSULTATION_MALADIES + " = ? ,"
            + COLUMN_CONSULTATION_OBSERVATION + " = ? ," + COLUMN_CONSULTATION_MONTANT_PAYEE + " = ? ," + COLUMN_CONSULTATION_DATE + " = ? WHERE " + COLUMN_CONSULTATION_ID + " = ? ";

    public static final String UPDATE_USER = "UPDATE "+USERS_TABLE+" SET "+ COLUMN_USERNAME + " = ? ," + COLUMN_PASSWORD + " = ? ," + COLUMN_USER_NAME + " = ? ," + COLUMN_USER_SNAME + " = ? ,"
            + COLUMN_USER_PHONE + " = ? ," + COLUMN_USER_EMAIL + " = ? ," + COLUMN_USER_ADRESSE + " = ? ," + COLUMN_USER_DOB+ " = ? ," + COLUMN_USER_ROLE+" = ? WHERE "+COLUMN_USER_ID+" = ?";

    public static final String UPDATE_PATIENT = "UPDATE " + PATIENTS_TABLE + " SET " + COLUMN_PATIENT_NAME + " = ? ," + COLUMN_PATIENT_SNAME + "= ? ," + COLUMN_PATIENT_PHONE + "= ? ," + COLUMN_PATIENT_EMAIL
            + "= ?," + COLUMN_PATIENT_DOB + "= ?," + COLUMN_PATIENT_ADRESSE + "= ?," + COLUMN_PATIENT_POID + "= ?," + COLUMN_PATIENT_SANG
            + "= ?," + COLUMN_PATIENT_LONGUEUR + "= ?," + COLUMN_PATIENT_MALADIES + "= ? WHERE " + COLUMN_PATIENT_ID + " = ?";

    public static final String UPDATE_ORDONNANCE = "UPDATE "+ ORDONNANCES_TABLE +" SET "+COLUMN_ORDONNANCE_MEDICAMENTS+" = ?," + COLUMN_ORDONNANCE_OBSERVATION +" = ? WHERE "+COLUMN_ORDONNANCE_ID+" = ?";

    //public static final String UPDATE_CONSULTATION = "UPDATE "+CONSULTATIONS_TABLE+" SET ";

    public static final String DELETE_PATIENT = "DELETE FROM " + PATIENTS_TABLE + " WHERE " + COLUMN_PATIENT_ID + " = ?";
    public static final String DELETE_CONSULTATION = "DELETE FROM "+CONSULTATIONS_TABLE+" WHERE "+COLUMN_CONSULTATION_ID+" = ? ";
    public static final String DELETE_ORDONNANCE = "DELETE FROM "+ ORDONNANCES_TABLE +" WHERE "+COLUMN_ORDONNANCE_ID +" = ?";
    public static final String DELETE_USER = "DELETE FROM "+USERS_TABLE+" WHERE "+COLUMN_USER_ID+" = ?";
    public static final String DELETE_RENDEZ_VOUS = "DELETE FROM "+RENDEZ_VOUS_TABLE+" WHERE "+COLUMN_RENDEZ_VOUS_ID+" = ?";


    private PreparedStatement queryLogin;
    private PreparedStatement queryUser;
    private PreparedStatement queryRendezVousAll;
    private PreparedStatement queryRendezVous;
    private PreparedStatement queryConsultation;
    private PreparedStatement countConsultations;
    private PreparedStatement queryPatients;
    private PreparedStatement queryPatient;
    private PreparedStatement queryUsers;
    private PreparedStatement queryOrdonnance;
    private PreparedStatement insertUser;
    private PreparedStatement insertPatient;
    private PreparedStatement insertRendezVous;
    private PreparedStatement insertConsultation;
    private PreparedStatement insertOrdonnance;
    private PreparedStatement updateConsultation;
    private PreparedStatement updatePatient;
    private PreparedStatement updateOrdonnance;
    private PreparedStatement updateUser;
    private PreparedStatement deletePatient;
    private PreparedStatement deleteConsultation;
    private PreparedStatement deleteOrdonnance;
    private PreparedStatement deleteUser;
    private PreparedStatement deleteRendezVous;

    private Datasource() {

    }

    public static Datasource getInstance() {
        return instance;
    }

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);

            queryLogin = conn.prepareStatement(QUERY_LOGIN);
            queryUser = conn.prepareStatement(QUERY_USER);
            queryRendezVousAll = conn.prepareStatement(QUERY_RENDEZ_VOUS_ALL);
            queryRendezVous = conn.prepareStatement(QUERY_RENDEZ_VOUS);
            queryConsultation = conn.prepareStatement(QUERY_CONSULTATIONS);
            countConsultations = conn.prepareStatement(COUNT_CONSULTATIONS);
            queryPatients = conn.prepareStatement(QUERY_PATIENTS);
            queryPatient = conn.prepareStatement(QUERY_PATIENT);
            queryUsers = conn.prepareStatement(QUERY_USERS);
            queryOrdonnance = conn.prepareStatement(QUERY_ORDONNANCE);

            insertUser = conn.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            insertPatient = conn.prepareStatement(INSERT_PATIENT, Statement.RETURN_GENERATED_KEYS);
            insertRendezVous = conn.prepareStatement(INSERT_RENDEZ_VOUS, Statement.RETURN_GENERATED_KEYS);
            insertConsultation = conn.prepareStatement(INSERT_CONSULTATION, Statement.RETURN_GENERATED_KEYS);
            insertOrdonnance = conn.prepareStatement(INSERT_ORADANNANCE, Statement.RETURN_GENERATED_KEYS);

            updateConsultation = conn.prepareStatement(UPDATE_CONSULTATION);
            updatePatient = conn.prepareStatement(UPDATE_PATIENT);
            updateOrdonnance = conn.prepareStatement(UPDATE_ORDONNANCE);
            updateUser = conn.prepareStatement(UPDATE_USER);

            deletePatient = conn.prepareStatement(DELETE_PATIENT);
            deleteConsultation = conn.prepareStatement(DELETE_CONSULTATION);
            deleteOrdonnance = conn.prepareStatement(DELETE_ORDONNANCE);
            deleteUser = conn.prepareStatement(DELETE_USER);
            deleteRendezVous = conn.prepareStatement(DELETE_RENDEZ_VOUS);


            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (queryLogin != null)
                queryLogin.close();
            if (queryUser != null)
                queryUser.close();
            if (queryRendezVousAll != null)
                queryRendezVousAll.close();
            if (queryUsers != null)
                queryUsers.close();
            if (queryPatients != null)
                queryPatients.close();
            if (queryPatient != null)
                queryPatient.close();
            if (queryConsultation != null)
                queryConsultation.close();
            if(countConsultations != null)
                countConsultations.close();
            if (queryRendezVous != null)
                queryRendezVous.close();
            if (queryOrdonnance != null)
                queryOrdonnance.close();

            if (insertOrdonnance != null)
                insertOrdonnance.close();
            if (insertConsultation != null)
                insertConsultation.close();
            if (insertRendezVous != null)
                insertRendezVous.close();
            if (insertUser != null)
                insertUser.close();
            if (insertPatient != null)
                insertUser.close();
            if (updateConsultation != null)
                updateConsultation.close();
            if (updatePatient != null)
                updatePatient.close();
            if(updateOrdonnance != null)
                updateOrdonnance.close();
            if(updateUser != null)
                updateUser.close();
            if(deleteConsultation != null)
                deleteConsultation.close();
            if(deletePatient != null)
                deletePatient.close();
            if(deleteOrdonnance != null)
                deleteOrdonnance.close();
            if(deleteUser != null)
                deleteUser.close();
            if(deleteRendezVous != null)
                deleteRendezVous.close();


            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    // implement the factory design pattern
    public List<String> login(String username) {
        try {
            queryLogin.setString(1, username);

        } catch (SQLException e) {
            System.out.println("Setting failed: " + e.getMessage());
        }

        try (ResultSet results = queryLogin.executeQuery()) {
            System.out.println("done0");
            if (!results.next())
                return null;
            System.out.println("done 1");
            String id = results.getString(1);
            String password = results.getString(2);
            List<String> loginInfo = new ArrayList<>(2);
            loginInfo.add(id);
            loginInfo.add(password);
            System.out.println("done");
            return loginInfo;


        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;

        }
    }

    public User queryUser(int id) {
        try {
            queryUser.setInt(1, id);

        } catch (SQLException e) {
            System.out.println("Setting failed: " + e.getMessage());
        }

        try (ResultSet results = queryUser.executeQuery()) {
            if (!results.next())
                return null;

            String role = results.getString(INDEX_USER_ROLE);
            UserFactory userFactory = new UserFactory();
            User user = userFactory.getUser(role);


            user.setId(results.getInt(INDEX_USER_ID));
            user.setRole(role);

            user.setPseudo(results.getString(INDEX_USERNAME));
            user.setMotDePasse(results.getString(INDEX_PASSWORD));

            user.setNom(results.getString(INDEX_USER_NAME));

            user.setPrenom(results.getString(INDEX_USER_SNAME));


            user.setAdresse(results.getString(INDEX_USER_ADRESSE));

            user.setEmail(results.getString(INDEX_USER_EMAIL));

            user.setTel(results.getString(INDEX_USER_PHONE));

            user.setDateDeNaissance(results.getString(INDEX_USER_DOB));



            return user;


        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;

        }
    }

    public List<RendezVous> queryRendezVous() {

        try (ResultSet results = queryRendezVousAll.executeQuery()) {
            //try(Statement statement = conn.createStatement(); ResultSet results = statement.executeQuery("SELECT * FROM "+RENDEZ_VOUS_TABLE)){

            List<RendezVous> rendezVousList = new ArrayList<>();

            while (results.next()) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    System.out.println("Interuppted: " + e.getMessage());
                }
                RendezVous rendezVous = new RendezVous();


                rendezVous.setId(results.getInt(1));
                rendezVous.setNom(results.getString(2));
                rendezVous.setPrenom(results.getString(3));
                rendezVous.setDate(results.getString(4));

                rendezVousList.add(rendezVous);


            }


            return rendezVousList;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<RendezVous> queryRendezVous(String date) {
        try {
            queryRendezVous.setString(1,date);
        } catch (SQLException e) {
            System.out.println("query failed "+e.getMessage());
        }

        try (ResultSet results = queryRendezVous.executeQuery()) {
            //try(Statement statement = conn.createStatement(); ResultSet results = statement.executeQuery("SELECT * FROM "+RENDEZ_VOUS_TABLE)){

            List<RendezVous> rendezVousList = new ArrayList<>();

            while (results.next()) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    System.out.println("Interuppted: " + e.getMessage());
                }
                RendezVous rendezVous = new RendezVous();


                rendezVous.setId(results.getInt(1));
                rendezVous.setNom(results.getString(2));
                rendezVous.setPrenom(results.getString(3));
                rendezVous.setDate(results.getString(4));
                rendezVous.setHeure(results.getString(8));
                if(queryPatient(rendezVous.getNom(),rendezVous.getPrenom()) != null)
                    rendezVous.setPatientStatut("Patient déja inscrit");
                else
                    rendezVous.setPatientStatut("Premier Consultation");

                rendezVousList.add(rendezVous);


            }


            return rendezVousList;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<Consultation> queryConsultations(int idPatient) {
        try {
            queryConsultation.setInt(1, idPatient);
            ResultSet resultSet = queryConsultation.executeQuery();
            List<Consultation> consultations = new ArrayList<>();
            while (resultSet.next()) {

                Consultation consultation = new Consultation();

                consultation.setId(resultSet.getInt(INDEX_CONSULTATION_ID));
                System.out.println(consultation.getId());
                consultation.setMedcin(resultSet.getString(INDEX_CONSULTATION_NAME_USER));
                consultation.setDiagnostic(resultSet.getString(INDEX_CONSULTATION_DIAGNOSTIC));
                consultation.setMaladies(resultSet.getString(INDEX_CONSULTATION_MALADIES));
                consultation.setObservation(resultSet.getString(INDEX_CONSULTATION_OBSERVATION));
                consultation.setMontantPayee(resultSet.getInt(INDEX_CONSULTATION_MONTANT_PAYEE));
                consultation.setDate(resultSet.getString(INDEX_CONSULTATION_DATE));
                System.out.println(consultation.getDate());//new SimpleDateFormat("yyyy/MM/dd").parse(resultSet.getString(INDEX_CONSULTATION_DATE))
                consultation.setCode(resultSet.getString(INDEX_CONSULTATION_CODE));
                System.out.println(consultation.getCode());
                Ordonnance ordonnance = queryOrdonnance(consultation.getId()) ;
                if(ordonnance != null){
                    consultation.setOrdonnance(ordonnance);
                    System.out.println(ordonnance);
                }


                consultations.add(consultation);
            }
            System.out.println(consultations);
            return consultations;



        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }

    }

    public int countConsultations(){
        try{
            ResultSet resultSet = countConsultations.executeQuery();
            if(!resultSet.next())
                return -1;

            return resultSet.getInt(1);

        } catch (SQLException e) {
            System.out.println("count failed "+e.getMessage());
            return -1;
        }
    }


    public Ordonnance queryOrdonnance(int id_Consultation) {
        try {
            queryOrdonnance.setInt(1, id_Consultation);

        } catch (SQLException e) {
            System.out.println("Failed query ordonnance"+e.getMessage());
        }
        try (ResultSet resultSet = queryOrdonnance.executeQuery()) {

            if (resultSet.next()) {

                Ordonnance ordonnance = new Ordonnance();

                ordonnance.setId(resultSet.getInt(1));

                List<Medicament> medicamentList = new ArrayList<>();

                String[] medicaments = resultSet.getString(3).split("-");



                for (String string : medicaments) {
                    String[] medic = string.split(",");

                    Medicament medicament = new Medicament(medic[0], medic[1], medic[2],medic[3]);
                    medicamentList.add(medicament);

                }
                // until here

                ordonnance.setMedicamentList(medicamentList);

                ordonnance.setObservation(resultSet.getString(4));

                return ordonnance;

            }

            return null;


        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }

    }

    public List<Patient> queryPatients() {

        try (ResultSet resultSet = queryPatients.executeQuery()) {
            List<Patient> patientList = new ArrayList<>();
            while (resultSet.next()) {
                Patient patient = new Patient();
                //Adresse adresse = new Adresse();
                patient.setId(resultSet.getInt(INDEX_PATIENT_ID));
                patient.setNom(resultSet.getString(INDEX_PATIENT_NAME));
                patient.setPrenom(resultSet.getString(INDEX_PATIENT_SNAME));
                patient.setTel(resultSet.getString(INDEX_PATIENT_PHONE));
                patient.setEmail(resultSet.getString(INDEX_PATIENT_EMAIL));


                patient.setAdresse(resultSet.getString(INDEX_PATIENT_ADRESSE));
                patient.setDateDeNaissance(resultSet.getString(INDEX_PATIENT_DOB));


                patient.setPoids(resultSet.getInt(INDEX_PATIENT_POID));
                patient.setGrSang(resultSet.getString(INDEX_PATIENT_SANG));
                patient.setLongueur(resultSet.getInt(INDEX_PATIENT_LONGUEUR));

                patient.setMaladies(resultSet.getString(INDEX_PATIENT_MALADIES));

                patient.setDate1(resultSet.getString(INDEX_PATIENT_FIRST_DATE));


                patientList.add(patient);


            }
            return patientList;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());

            return null;
        }
    }


    public Patient queryPatient(String nom, String prenom) {
        try {
            queryPatient.setString(1, nom);
            queryPatient.setString(2, prenom);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try (ResultSet resultSet = queryPatient.executeQuery()) {

            if (resultSet.next()) {
                Patient patient = new Patient();

                patient.setId(resultSet.getInt(INDEX_PATIENT_ID));
                patient.setNom(resultSet.getString(INDEX_PATIENT_NAME));
                patient.setPrenom(resultSet.getString(INDEX_PATIENT_SNAME));
                patient.setTel(resultSet.getString(INDEX_PATIENT_PHONE));
                patient.setEmail(resultSet.getString(INDEX_PATIENT_EMAIL));
                patient.setAdresse(resultSet.getString(INDEX_PATIENT_ADRESSE));
                patient.setDateDeNaissance(resultSet.getString(INDEX_PATIENT_DOB));
                patient.setPoids(resultSet.getInt(INDEX_PATIENT_POID));
                patient.setGrSang(resultSet.getString(INDEX_PATIENT_SANG));
                patient.setLongueur(resultSet.getInt(INDEX_PATIENT_LONGUEUR));
                patient.setMaladies(resultSet.getString(INDEX_PATIENT_MALADIES));

                patient.setDate1(resultSet.getString(INDEX_PATIENT_FIRST_DATE));

                return patient;


            }
            return null;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<User> queryUsers(){
        try (ResultSet results = queryUsers.executeQuery()) {
            List<User> users = new ArrayList<>();
           while (results.next()){

            String role = results.getString(INDEX_USER_ROLE);
            UserFactory userFactory = new UserFactory();

            User user = userFactory.getUser(role);


            user.setId(results.getInt(INDEX_USER_ID));
            user.setRole(role);

            user.setPseudo(results.getString(INDEX_USERNAME));
            user.setMotDePasse(results.getString(INDEX_PASSWORD));
            user.setNom(results.getString(INDEX_USER_NAME));

            user.setPrenom(results.getString(INDEX_USER_SNAME));

            user.setAdresse(results.getString(INDEX_USER_ADRESSE));

            user.setEmail(results.getString(INDEX_USER_EMAIL));

            user.setTel(results.getString(INDEX_USER_PHONE));

            user.setDateDeNaissance(results.getString(INDEX_USER_DOB));

            // user.setDateDeNaissance(new SimpleDateFormat("yyyy/MM/dd").parse(results.getString(INDEX_PATIENT_DOB)));


            users.add(user);
           }
           return users;

        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;

        }
    }


    // public int insertPatient(String nom, String prenom, Date dateDeNaissance, Adresse adresse, String email, String tel, List<String> maladies, int poids, int longueur, String grSang, String date1){
    public int insertPatient(Patient patient) {
        try {


            insertPatient.setString(1, patient.getNom());
            insertPatient.setString(2, patient.getPrenom());
            insertPatient.setString(3, patient.getTel());
            insertPatient.setString(4, patient.getEmail());
            insertPatient.setString(5, patient.getDateDeNaissance());
            insertPatient.setString(6, patient.getAdresse());
            insertPatient.setInt(7, patient.getPoids());
            insertPatient.setString(8, patient.getGrSang());
            insertPatient.setInt(9, patient.getLongueur());
            insertPatient.setString(10, patient.getMaladies());
            insertPatient.setString(11, patient.getDate1());

            int affectedRows = insertPatient.executeUpdate();
            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert Patient!");
            }

            ResultSet generatedKeys = insertPatient.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Couldn't get _id for patient");
            }
        } catch (SQLException e) {
            System.out.println("Insertion failed: " + e.getMessage());
            return -1;
        }


    }

    public int insertUser(User user) {
        try {

            insertUser.setString(1, user.getPseudo());
            insertUser.setString(2, user.getMotDePasse());
            insertUser.setString(3, user.getNom());
            insertUser.setString(4, user.getPrenom());
            insertUser.setString(5, user.getTel());
            insertUser.setString(6,user.getEmail());
            insertUser.setString(7, user.getAdresse());
            insertUser.setString(8, user.getDateDeNaissance());
            insertUser.setString(9, user.getRole());
            int affectedRows = insertUser.executeUpdate();
            if (affectedRows != 1)
                throw new SQLException("couldn't insert user");
            ResultSet generatedKey = insertUser.getGeneratedKeys();

            return generatedKey.getInt(1);

        } catch (SQLException e) {
            System.out.println("Insertion failed: " + e.getMessage());
            return -1;
        } 

    }

    public int insertConsultation(int idPatient, Consultation consultation) {
        try {

            insertConsultation.setInt(1, idPatient);
            insertConsultation.setString(2, consultation.getMedcin());
            insertConsultation.setString(3, consultation.getDiagnostic());
            insertConsultation.setString(4, consultation.getMaladies());
            insertConsultation.setString(5, consultation.getObservation());
            insertConsultation.setInt(6, consultation.getMontantPayee());
            insertConsultation.setString(7, consultation.getDate());
            insertConsultation.setString(8,consultation.getCode());

            int affectedRow = insertConsultation.executeUpdate();
            if (affectedRow != 1)
                throw new SQLException("couldn't insert Consultation");
            ResultSet generatedKey = insertConsultation.getGeneratedKeys();

            return generatedKey.getInt(1);

        } catch (SQLException e) {
            System.out.println("Insertion failed: " + e.getMessage());
            return -1;
        }

    }

    public int insertOrdonnance(int idConsultation, Ordonnance ordonnance) {
        try {
            insertOrdonnance.setInt(1, idConsultation);
            insertOrdonnance.setString(2, ordonnance.medicamentsString());
            insertOrdonnance.setString(3,ordonnance.getObservation());

            int affectedRow = insertOrdonnance.executeUpdate();
            if (affectedRow != 1)
                throw new SQLException("couldn't insert Ordonnance");

            ResultSet generatedKey = insertOrdonnance.getGeneratedKeys();

            return generatedKey.getInt(1);

        } catch (SQLException e) {
            System.out.println("Insertion failed: " + e.getMessage());
            return -1;
        }


    }

    public int insertRendezVous(RendezVous rendezVous) {

        try {
            insertRendezVous.setString(1, rendezVous.getNom());
            insertRendezVous.setString(2, rendezVous.getPrenom());
            insertRendezVous.setString(3, rendezVous.getDate());
            insertRendezVous.setString(4, rendezVous.getHeure());
            int affectedRow = insertRendezVous.executeUpdate();

            if (affectedRow != 1)
                throw new SQLException("couldn't insert Rendez-Vous");

            ResultSet generatedKey = insertRendezVous.getGeneratedKeys();

            return generatedKey.getInt(1);

        } catch (SQLException e) {
            System.out.println("Insertion failed: " + e.getMessage());
            return -1;
        }


    }

    public boolean updatePatient(Patient patient) {
        try {

            updatePatient.setString(1, patient.getNom());
            updatePatient.setString(2, patient.getPrenom());
            updatePatient.setString(3, patient.getTel());
            updatePatient.setString(4, patient.getEmail());
            updatePatient.setString(5, patient.getDateDeNaissance());
            updatePatient.setString(6, patient.getAdresse());
            updatePatient.setInt(7, patient.getPoids());
            updatePatient.setString(8, patient.getGrSang());
            updatePatient.setInt(9, patient.getLongueur());
            updatePatient.setString(10, patient.getMaladies());
            updatePatient.setInt(11, patient.getId());


            int affectedRows = updatePatient.executeUpdate();
            System.out.println(affectedRows);
            return affectedRows == 1;


        } catch (SQLException e) {
            System.out.println("Update failed: " + e.getMessage());
            return false;
        }

    }

    public boolean updateUser(User user){
        try{
            updateUser.setString(1, user.getPseudo());
            updateUser.setString(2, user.getMotDePasse());
            updateUser.setString(3, user.getNom());
            updateUser.setString(4, user.getPrenom());
            updateUser.setString(5, user.getTel());
            updateUser.setString(6,user.getEmail());
            updateUser.setString(7, user.getAdresse());
            updateUser.setString(8, user.getDateDeNaissance());
            updateUser.setString(9, user.getRole());
            updateUser.setInt(10,user.getId());
            return updateUser.executeUpdate() == 1;
        } catch  (SQLException e) {
            System.out.println("update user failed "+e.getMessage());
            return false;
        }
    }

    public boolean updateConsultation(Consultation consultation) {
        try {
            updateConsultation.setString(1, consultation.getDiagnostic());
            updateConsultation.setString(2, consultation.getMaladies());
            updateConsultation.setString(3, consultation.getObservation());
            updateConsultation.setInt(4, consultation.getMontantPayee());
            updateConsultation.setString(5, consultation.getDate());
            updateConsultation.setInt(6,consultation.getId());

            return updateConsultation.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("update failed " + e.getMessage());
            return false;
        }

    }

    public boolean updateOrdonnance(Ordonnance ordonnance){
        try{
            updateOrdonnance.setString(1,ordonnance.medicamentsString());
            updateOrdonnance.setString(2,ordonnance.getObservation());
            updateOrdonnance.setInt(3,ordonnance.getId());

            return updateOrdonnance.executeUpdate() == 1;
        }catch(SQLException e){
            System.out.println("update failed "+e.getMessage());
            return false;
        }
    }

    public boolean deletePatient(int id) {
        try {
            deletePatient.setInt(1, id);

            return deletePatient.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("Delete Failed " + e.getMessage());
            return false;
        }
    }

    public boolean deleteConsultation(int id){
        try{

            deleteConsultation.setInt(1,id);

            return deleteConsultation.executeUpdate() == 1;

        }catch (SQLException e){
            System.out.println("delete failed "+ e.getMessage());
            return false;
        }

    }

    public boolean deleteOrdonnance(int id){
        try{
            deleteOrdonnance.setInt(1,id);

            return deleteOrdonnance.executeUpdate() == 1;

        }catch(SQLException e){
            System.out.println("delete failed "+e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(int id){
        try{
            deleteUser.setInt(1,id);

            return deleteUser.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("delete user failed"+e.getMessage());
            return false;
        }


    }

    public boolean supprimerRendezVous(int id){
        try {
            deleteRendezVous.setInt(1,id);

            return deleteRendezVous.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("delete rendezvous failed "+e.getMessage());
            return false;
        }
    }

    public LocalDate queryLastConsultationDate(int id) {
        try {
            PreparedStatement lastConsultationDate = conn.prepareStatement(QUERY_CONSULTATION_DATE);
            lastConsultationDate.setInt(1, id);
            try (ResultSet resultSet = lastConsultationDate.executeQuery()) {
                if (resultSet.next())
                    return LocalDate.parse(resultSet.getString(1));
                return null;

            }
        } catch (SQLException e) {
            System.out.println("query failed " + e.getMessage());
            return null;
        }


    }




}
