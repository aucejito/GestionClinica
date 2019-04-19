/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionclinica;

import DBAccess.ClinicDBAccess;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import model.Doctor;
import org.controlsfx.control.textfield.CustomTextField;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class FXMLCalendarioMainController implements Initializable {

    @FXML
    private AnchorPane scene;
    @FXML
    private BorderPane borderPane;
    @FXML
    private ComboBox<?> tipoBusqueda;
    @FXML
    private CustomTextField barraBusqueda;
    @FXML
    private ComboBox<String> medComboBox;
    
    ClinicDBAccess clinicDBAccess = ClinicDBAccess.getSingletonClinicDBAccess();
    ObservableList<Doctor> doctorsObservableList;
    @FXML
    private Button prevWeekButton;
    @FXML
    private Button nextWeekButton;
    
    Doctor currentDoctor;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        doctorsObservableList = FXCollections.observableList(clinicDBAccess.getDoctors());
      
        for(int i = 0; i < doctorsObservableList.size(); i++){
            medComboBox.getItems().addAll(doctorsObservableList.get(i).getName());
    }    
        doctorsObservableList = FXCollections.observableList(clinicDBAccess.getDoctors());
        // USAR DESPUES currentDoctor = doctorsObservableList.get(medComboBox.getSelectionModel().getSelectedIndex());
                
    

    }
    
}
