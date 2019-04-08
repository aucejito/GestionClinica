/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionclinica;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Patient;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class FXMLMedicosAddController implements Initializable {

    @FXML
    private ComboBox<?> diasSemana;
    @FXML
    private TextField dniTextField;
    @FXML
    private TextField nombreTextField;
    @FXML
    private TextField apellidosTextField;
    @FXML
    private TextField telefonoTextField;
    @FXML
    private Button guardarButton;
    @FXML
    private Button cancelButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void guardarAct(ActionEvent event) {
        if (dniTextField.getText().isEmpty()){
            Alert alertAmazon = new Alert(Alert.AlertType.INFORMATION);
            alertAmazon.setTitle("Faltan datos");
            alertAmazon.setHeaderText("Por favor, introduce el DNI");
            alertAmazon.setContentText("No se puede guardar el paciente si no introduce el número de identificación personal");
            alertAmazon.showAndWait();
        } else if(nombreTextField.getText().isEmpty()){
            Alert alertAmazon = new Alert(Alert.AlertType.INFORMATION);
            alertAmazon.setTitle("Faltan datos");
            alertAmazon.setHeaderText("Por favor, introduce el nombre");
            alertAmazon.setContentText("No se puede guardar el paciente si no introduce el nombre");
            alertAmazon.showAndWait();
        }else if(apellidosTextField.getText().isEmpty()){
            Alert alertAmazon = new Alert(Alert.AlertType.INFORMATION);
            alertAmazon.setTitle("Faltan datos");
            alertAmazon.setHeaderText("Por favor, introduce los apellidos");
            alertAmazon.setContentText("No se puede guardar el paciente si no introduce los apellidos");
            alertAmazon.showAndWait();
        }else if(telefonoTextField.getText().isEmpty()){
            Alert alertAmazon = new Alert(Alert.AlertType.INFORMATION);
            alertAmazon.setTitle("Faltan datos");
            alertAmazon.setHeaderText("Por favor, introduce el teléfono");
            alertAmazon.setContentText("No se puede guardar el paciente si no introduce el teléfono");
            alertAmazon.showAndWait();
        }else{
            Image img = null;
            Patient x = new Patient(dniTextField.getText(), nombreTextField.getText(), apellidosTextField.getText(), telefonoTextField.getText(), img);
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void cancelarAct(ActionEvent event) {
    }

    @FXML
    private void fotoAct(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpeg","*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
    }
    
}
