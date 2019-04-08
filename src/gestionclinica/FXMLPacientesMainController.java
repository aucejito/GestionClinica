
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionclinica;

import DBAccess.ClinicDBAccess;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Patient;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class FXMLPacientesMainController implements Initializable {

    TableView<Patient> lPatients;
    @FXML
    private ImageView myImageView;
    Image avatar;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String urlimg = File.separator+"images"+File.separator+"woman.PNG";
        avatar = null;
        try {
            avatar = new Image(new FileInputStream(urlimg));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLPacientesMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        myImageView.imageProperty().setValue(avatar); 
    }    

    @FXML
    private void addAct(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/Vista/FXMLPacientesAdd.fxml"));
        AnchorPane root = (AnchorPane) miCargador.load();

            

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Añadir paciente");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        
        ClinicDBAccess clinicDBAccess = ClinicDBAccess.getSingletonClinicDBAccess();
        ObservableList<Patient> patientsObservableList;
        //Envolvemos el ArrayList de pacientes de de la clínica en una ObservableList
        patientsObservableList = FXCollections.observableList(clinicDBAccess.getPatients());
        // lPatients es una ListView<Patient>
        lPatients.setItems(patientsObservableList); //Se conecta la lista con el ListView
        Patient patient = new Patient("5307867J","Juan","Cafe Grandes","9376543",avatar);
        patientsObservableList.add(patient); //Se añade un Nuevo paciente
        clinicDBAccess.saveDB(); //Se almacena el cambio en el fichero XML
    }

    @FXML
    private void delAct(ActionEvent event) {
    }

    @FXML
    private void showAct(ActionEvent event) {
    }

    

      
}
