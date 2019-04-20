/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionclinica;

import DBAccess.ClinicDBAccess;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import model.Doctor;
import model.Patient;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class FXMLCalendarioAddController implements Initializable {

    @FXML
    private ComboBox<String> medComboBox;
    @FXML
    private ComboBox<String> pacientesComboBox;
    private DatePicker datePicker;
    @FXML
    private GridPane calendarGrid;
    @FXML
    private TextField fechaTextField;
    ArrayList<Patient> pacientes;
    ArrayList<Doctor> doctores;
    ClinicDBAccess clinicDBAccess = ClinicDBAccess.getSingletonClinicDBAccess();
    @FXML
    private ComboBox<?> horasComboBox;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        datePicker = new DatePicker(LocalDate.now());
        datePicker.setShowWeekNumbers(true);
        fechaTextField.setText((LocalDate.now()).toString());
       datePicker.setDayCellFactory(cel-> new DiaCelda() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        
        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node popupContent = datePickerSkin.getPopupContent();
        calendarGrid.add(popupContent, 0, 0);
        
        doctores = clinicDBAccess.getDoctors();
        for (int i = 0; i < doctores.size(); i++) {
            medComboBox.getItems().addAll(doctores.get(i).getName() + " " + doctores.get(i).getSurname());
        }
        
        pacientes = clinicDBAccess.getPatients();
        for (int i = 0; i < pacientes.size(); i++) {
            pacientesComboBox.getItems().addAll(pacientes.get(i).getName() + " " + pacientes.get(i).getSurname());
        }
        
        datePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            fechaTextField.setText(newValue.toString());
        });
        
        h
    }    
    
    class DiaCelda extends DateCell {
            
            String newline = System.getProperty("line.separator");
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
 
                // Show Weekends in blue color
                DayOfWeek day = DayOfWeek.from(item);
                if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                    this.setTextFill(Color.ROSYBROWN);
                    this.setDisable(true);
                    this.setText(this.getText()+"\r");
                }
                else this.setText(this.getText()+"\rlibre");
            }

        
    }
    
}
