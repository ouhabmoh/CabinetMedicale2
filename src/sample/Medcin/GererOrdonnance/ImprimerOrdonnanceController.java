/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.Medcin.GererOrdonnance;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Window;
import sample.model.Ordonnance;
import sample.model.Patient;


import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author RIfat
 */
public class ImprimerOrdonnanceController implements Initializable {

    @FXML
    private Label lblId;

    @FXML
    private Label lblPatientName;
    @FXML
    private Label lblPatientDOB;
    @FXML
    private Label lblDate;
    @FXML
    private WebView webView;



   Ordonnance ordonnance = new Ordonnance();
    Patient patient = new Patient();

    PrescriptionMaker maker = new PrescriptionMaker();

    WebEngine engine = new WebEngine();

    File file = null;
    PrintStream out = null;
    Window owner;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            System.out.println("completer apres");
        });
    }

    @FXML
    private void handlePrintButton(ActionEvent event) {
        Printer printer = Printer.getDefaultPrinter();
        printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.EQUAL);
        PrinterJob job = PrinterJob.createPrinterJob(printer);
        job.showPrintDialog(owner);
        
        if (job != null) {
            engine.print(job);
            job.endJob();
        }
    }

    public void loadPrescription(Ordonnance ordonnance,Patient patient,String date) {
        System.out.println(ordonnance);
       lblId.setText("Ordonnance N° : "+ordonnance.getId());
        lblPatientName.setText("Patient Nom : " + patient.getNom()+" "+patient.getPrenom());
        lblPatientDOB.setText("Date De Naissance : " + patient.getDateDeNaissance());
        lblDate.setText("Date : "+date);
        try {
            file = new File("Ordonnance.html");
            file.createNewFile();

            out = new PrintStream(file);
            out.print(maker.makePrescription(ordonnance,patient,date));

            engine = webView.getEngine();
            engine.load(file.toURI().toString());

        // Desktop.getDesktop().browse(URI.create("Prescription.html"));
            System.out.println(file.getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("ordonnance failed"+ex.getMessage());
        }
    }

}
