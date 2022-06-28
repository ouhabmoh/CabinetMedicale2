/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.Medcin.GererOrdonnance;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Login.LoginController;
import sample.model.Medicament;
import sample.model.Ordonnance;
import sample.model.Patient;
import sample.model.User;

import static j2html.TagCreator.*;


public class PrescriptionMaker {
    
    User user ;
    


    public String makePrescription(Ordonnance ordonnance, Patient patient, String date) {
        
        user = LoginController.getUser();
        String nomComplet = user.getNom()+" "+user.getPrenom();
        String phone = user.getTel();
        String address = user.getAdresse();
        String email = user.getEmail();
        ObservableList<Medicament> medicaments = FXCollections.observableArrayList(ordonnance.getMedicamentList());


        String string = html(
                head(
                        title("Ordonnance")
                ),
                body(
                        h2(nomComplet),
                        p(

                                span(phone),
                                br(),
                                span(email),
                                br(),
                                span(address)
                        ),
                        hr(),
                        table().with(
                                tr().with(
                                        td().with(
                                                span("Nom : " + patient.getNom()+" "+patient.getPrenom())
                                        ),

                                        td().with(
                                                span("Date De Naissance : " + patient.getDateDeNaissance())
                                        ),
                                        td().with(
                                                span("Date : " + date)
                                        )
                                )
                        ).withStyle("width:100%;"),

                        div(
                                h1("Medicament"),
                                ol(
                                        each(medicaments, medicament -> tr(
                                        li( b(" " + medicament.getNom()), span(" " + medicament.getDose() + " "), ul(
                                                li(medicament.getAstuce() + " " ).withStyle("list-style: none")

                                        ).withStyle("padding-left: 10px"))
                                ))
                                ),ol(ordonnance.getObservation())
                        ).withStyle("display:inline;float:left;width:60%;")

                )
        ).render();

        return string;
    }


}
