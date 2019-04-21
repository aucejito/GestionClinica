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
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Patient;


/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class FXMLPacientesAddController implements Initializable {

    @FXML
    private Button cancelButton;
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
    private Button salirButton;
    
    ArrayList<Patient> listaPacientes;
    Image img;
    static boolean guardadoP = false;
    
    ClinicDBAccess clinicDBAccess = ClinicDBAccess.getSingletonClinicDBAccess();
    ObservableList<Patient> patientsObservableList;
    @FXML
    private ImageView imageView;
    @FXML
    private Button loadFotoButton;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        if(FXMLPacientesMainController.editable == false){
            
            dniTextField.setEditable(false);
            nombreTextField.setEditable(false);
            apellidosTextField.setEditable(false);
            telefonoTextField.setEditable(false);
            salirButton.setVisible(true);
            cancelButton.setVisible(false);
            guardarButton.setVisible(false);
            loadFotoButton.setDisable(true);
            
            dniTextField.setText(FXMLPacientesMainController.current.getIdentifier());
            nombreTextField.setText(FXMLPacientesMainController.current.getName());
            apellidosTextField.setText(FXMLPacientesMainController.current.getSurname());
            telefonoTextField.setText(FXMLPacientesMainController.current.getTelephon());
            imageView.setImage(FXMLPacientesMainController.current.getPhoto());
            
        }else{
                                            
            Random rand = new Random();
            int j = rand.nextInt(4);
            img = new Image("huevo/" + j + ".jpg");
            imageView.setImage(img);

            salirButton.setVisible(false);
            cancelButton.setVisible(true);
            guardarButton.setVisible(true);
            loadFotoButton.setDisable(false);
        
        
        }
        

    }    
    
   
    public void start(Stage primaryStage) {
          primaryStage.setTitle("JavaFX App");

        FileChooser fileChooser = new FileChooser();
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
            Patient nuevoPaciente = new Patient(dniTextField.getText(), nombreTextField.getText(), apellidosTextField.getText(), telefonoTextField.getText(), img);
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            
            patientsObservableList = FXCollections.observableList(clinicDBAccess.getPatients());
            patientsObservableList.add(nuevoPaciente);
            guardadoP = true;
            
        }
               
        
        
        
    }

    @FXML
    private void CancelAct(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        guardadoP = false;
    }

    @FXML
    private void fotoAct(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("Image Files", "*.png", "*.jpeg","*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        img = new Image(selectedFile.toURI().toString());
        imageView.setImage(img);
        loadFotoButton.setText("Cambiar");
    }

    @FXML
    private void exitAct(ActionEvent event) {
       Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}

    
    

