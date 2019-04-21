/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionclinica;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class FXMLCalendarioDetallesController implements Initializable {

    @FXML
    private ImageView fotoPaciente;
    @FXML
    private TextField nombrePacienteTextField;
    @FXML
    private TextField apellidoPacienteTextfield;
    @FXML
    private TextField horaTextField;
    @FXML
    private TextField telefonoPacienteTextField;
    @FXML
    private TextField fechaTextfield;
    @FXML
    private TextField nombreDoctorTextField;
    @FXML
    private TextField apellidosDoctorTextField;
    @FXML
    private TextField consultaTextfield;
    @FXML
    private TextField telefonoDoctorTextfield;
    @FXML
    private ImageView fotoDoctor;
    @FXML
    private Button salirButton;
    @FXML
    private AnchorPane scene;
    @FXML
    private GridPane gridPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fotoPaciente.setImage(FXMLCalendarioMainController.selectedAppointment.getPatient().getPhoto());
        nombrePacienteTextField.setText(FXMLCalendarioMainController.selectedAppointment.getPatient().getName());
        apellidoPacienteTextfield.setText(FXMLCalendarioMainController.selectedAppointment.getPatient().getSurname());
        telefonoPacienteTextField.setText(FXMLCalendarioMainController.selectedAppointment.getPatient().getTelephon());
        
        fechaTextfield.setText(FXMLCalendarioMainController.selectedAppointment.getAppointmentDateTime().toLocalDate().toString());
        horaTextField.setText(FXMLCalendarioMainController.selectedAppointment.getAppointmentDateTime().toLocalTime().toString());
        consultaTextfield.setText("Consulta Nº " + FXMLCalendarioMainController.selectedAppointment.getDoctor().getExaminationRoom().getIdentNumber());
        
        nombreDoctorTextField.setText(FXMLCalendarioMainController.selectedAppointment.getDoctor().getName());
        apellidosDoctorTextField.setText(FXMLCalendarioMainController.selectedAppointment.getDoctor().getSurname());
        telefonoDoctorTextfield.setText(FXMLCalendarioMainController.selectedAppointment.getDoctor().getTelephon());
        fotoDoctor.setImage(FXMLCalendarioMainController.selectedAppointment.getDoctor().getPhoto());
        
        gridPane.prefHeightProperty().bind(scene.heightProperty());
        gridPane.prefWidthProperty().bind(scene.widthProperty());
    }    

    @FXML
    private void salirAct(ActionEvent event) {
        Stage stage = (Stage) salirButton.getScene().getWindow();
        stage.close();
    }
    
}
