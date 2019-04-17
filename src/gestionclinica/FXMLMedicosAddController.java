/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionclinica;

import DBAccess.ClinicDBAccess;
import java.io.File;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.DateTimeAdapter;
import model.Days;
import static model.Days.Monday;
import model.Doctor;
import model.ExaminationRoom;
import model.LocalTimeAdapter;
import model.Patient;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.textfield.CustomTextField;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class FXMLMedicosAddController implements Initializable {

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
    @FXML
    private CustomTextField horaInicio;
    @FXML
    private CustomTextField horaFin;

    private static final Image imgHora = new Image("hora.png");
    @FXML
    private Button salirButton;
    @FXML
    private CheckComboBox<Days> diasComboBox;
    @FXML
    private ComboBox<String> consultaComboBox;
    
      
    LocalTimeAdapter lTAdapter = new LocalTimeAdapter();
    ClinicDBAccess clinicDBAccess = ClinicDBAccess.getSingletonClinicDBAccess();
    ObservableList<Doctor> doctorsObservableList;
    ArrayList<ExaminationRoom> consultas;
    ExaminationRoom examRoom = new ExaminationRoom();
    ArrayList<Integer> idConsultas;
    
    static boolean guardadoD;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        horaInicio.setLeft(new ImageView(imgHora));
        horaFin.setLeft(new ImageView(imgHora));
        
        diasComboBox.getItems().addAll(Days.Monday, Days.Tuesday, Days.Wednesday, Days.Thursday, Days.Friday);
        consultas =  clinicDBAccess.getExaminationRooms();
                 
        for(int i = 0; i < consultas.size(); i++){
            consultaComboBox.getItems().addAll("Consulta " + consultas.get(i).getIdentNumber());
        }
        
        if(FXMLMedicosMainController.editableM == false){
            dniTextField.setEditable(false);
            nombreTextField.setEditable(false);
            apellidosTextField.setEditable(false);
            telefonoTextField.setEditable(false);
            salirButton.setVisible(true);
            cancelButton.setVisible(false);
            guardarButton.setVisible(false);
            
            dniTextField.setText(FXMLMedicosMainController.currentM.getIdentifier());
            nombreTextField.setText(FXMLMedicosMainController.currentM.getName());
            apellidosTextField.setText(FXMLMedicosMainController.currentM.getSurname());
            telefonoTextField.setText(FXMLMedicosMainController.currentM.getTelephon());
            try {
                horaInicio.setText(lTAdapter.marshal(FXMLMedicosMainController.currentM.getVisitStartTime()));
                horaFin.setText(lTAdapter.marshal(FXMLMedicosMainController.currentM.getVisitEndTime()));
                
            } catch (Exception ex) {
                Logger.getLogger(FXMLMedicosAddController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //consultaComboBox.setItems(examRoom.getIdentNumber().FXMLMedicosMainController.currentM.getExaminationRoom());
            
            
            
            try {
            horaInicio.setText(lTAdapter.marshal(FXMLMedicosMainController.currentM.getVisitStartTime()));
            } catch (Exception ex) {
            Logger.getLogger(FXMLMedicosAddController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
            horaFin.setText(lTAdapter.marshal(FXMLMedicosMainController.currentM.getVisitEndTime()));
            } catch (Exception ex) {
            Logger.getLogger(FXMLMedicosAddController.class.getName()).log(Level.SEVERE, null, ex);
            }
                        
        }else{
        salirButton.setVisible(false);
        cancelButton.setVisible(true);
        guardarButton.setVisible(true);
        }
        
        // TODO
    }    

    @FXML
    private void guardarAct(ActionEvent event) throws Exception {
        if (dniTextField.getText().isEmpty()){
            Alert alertAmazon = new Alert(Alert.AlertType.INFORMATION);
            alertAmazon.setTitle("Faltan datos");
            alertAmazon.setHeaderText("Por favor, introduce el DNI");
            alertAmazon.setContentText("No se puede guardar el médico si no introduce el número de identificación personal");
            alertAmazon.showAndWait();
        } else if(nombreTextField.getText().isEmpty()){
            Alert alertAmazon = new Alert(Alert.AlertType.INFORMATION);
            alertAmazon.setTitle("Faltan datos");
            alertAmazon.setHeaderText("Por favor, introduce el nombre");
            alertAmazon.setContentText("No se puede guardar el médico si no introduce el nombre");
            alertAmazon.showAndWait();
        }else if(apellidosTextField.getText().isEmpty()){
            Alert alertAmazon = new Alert(Alert.AlertType.INFORMATION);
            alertAmazon.setTitle("Faltan datos");
            alertAmazon.setHeaderText("Por favor, introduce los apellidos");
            alertAmazon.setContentText("No se puede guardar el médico si no introduce los apellidos");
            alertAmazon.showAndWait();
        }else if(telefonoTextField.getText().isEmpty()){
            Alert alertAmazon = new Alert(Alert.AlertType.INFORMATION);
            alertAmazon.setTitle("Faltan datos");
            alertAmazon.setHeaderText("Por favor, introduce el teléfono");
            alertAmazon.setContentText("No se puede guardar el médico si no introduce el teléfono");
            alertAmazon.showAndWait();
        }else{
            Image img = null;
            int i = Integer.parseInt(consultaComboBox.getValue().substring(9));
            
            
            
            ObservableList<Days> dias;
            dias = diasComboBox.getCheckModel().getCheckedItems();
            
            
            Doctor nuevoDoctor = new Doctor(consultas.get(i), dias.get(0), lTAdapter.unmarshal(horaInicio.getText()),lTAdapter.unmarshal(horaFin.getText()) ,
            dniTextField.getText(), nombreTextField.getText(), apellidosTextField.getText(), telefonoTextField.getText(), img);
            
            if(dias.size() > 1){
                for(int k = 0; k < dias.size(); k++){
                    dias.remove(0);
                    nuevoDoctor.addVisitDay(dias.get(0));
                }
            }
                    
                    

            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            doctorsObservableList = FXCollections.observableList(clinicDBAccess.getDoctors());
            doctorsObservableList.add(nuevoDoctor);
            guardadoD = true;
        }
    }

    @FXML
    private void cancelarAct(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void fotoAct(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpeg","*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
    }

    @FXML
    private void salirAct(ActionEvent event) {
        Stage stage = (Stage) salirButton.getScene().getWindow();
        stage.close();
    }
    
}
