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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateTimeStringConverter;
import model.Appointment;
import model.Days;
import model.Doctor;
import model.Patient;
import utils.SlotAppointmentsWeek;

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
    ArrayList<Appointment> citas;
    ClinicDBAccess clinicDBAccess = ClinicDBAccess.getSingletonClinicDBAccess();
    @FXML
    private ComboBox<String> horasComboBox;
    ArrayList<LocalDateTime> horasDisponibles;
    Doctor currentDoctor;
    Patient currentPatient;
    LocalTime horaInicioDoctor;
    LocalTime horaFinalDoctor;
    SlotAppointmentsWeek sAppWeek = new SlotAppointmentsWeek();
    ArrayList<String> slotsDisponibles;
    ArrayList<Days> diasTrabajados;
    @FXML
    private Button cancelButton;
    ObservableList<Appointment> citasObservableList;
    static boolean guardadoApp = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        datePicker = new DatePicker(LocalDate.now());
        datePicker.setShowWeekNumbers(true);
        fechaTextField.setText((LocalDate.now()).toString());
        datePicker.setDayCellFactory(cel -> new DiaCelda() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
             
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 || date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY);
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

        medComboBox.valueProperty().addListener((ov, oldValue, newValue) -> {
            for (int i = 0; i < doctores.size(); i++) {
                if (newValue.equals(doctores.get(i).getName() + " " + doctores.get(i).getSurname())) {
                    currentDoctor = doctores.get(i);
                    break;
                }
            }

            //Desabilitar dias no trabajados doctor
            diasTrabajados = currentDoctor.getVisitDays();
            datePicker.setDayCellFactory(cel -> new DiaCelda() {
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    

                    if (!diasTrabajados.contains(Days.Monday) && date.getDayOfWeek() == DayOfWeek.MONDAY) {
                        this.setDisable(true);
                    }
                    if (!diasTrabajados.contains(Days.Tuesday) && date.getDayOfWeek() == DayOfWeek.TUESDAY) {
                        this.setDisable(true);
                    }
                    if (!diasTrabajados.contains(Days.Wednesday) && date.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
                        this.setDisable(true);
                    }
                    if (!diasTrabajados.contains(Days.Thursday) && date.getDayOfWeek() == DayOfWeek.THURSDAY) {
                        this.setDisable(true);
                    }
                    if (!diasTrabajados.contains(Days.Friday) && date.getDayOfWeek() == DayOfWeek.FRIDAY) {
                        this.setDisable(true);
                    }

                    LocalDate today = LocalDate.now();
                    setDisable(empty || date.compareTo(today) < 0);
                }
            });

            slotsDisponibles = new ArrayList<String>();
            citas = clinicDBAccess.getAppointments();
            horaInicioDoctor = currentDoctor.getVisitStartTime();
            horaFinalDoctor = currentDoctor.getVisitEndTime();

            int h = horaFinalDoctor.getHour() - horaInicioDoctor.getHour();
            int min = horaFinalDoctor.getMinute() - horaInicioDoctor.getMinute();

            int slotsToAdd = (h * 60 + min) / 15;

            for (int i = 0; i < slotsToAdd; i++) {
                for (int j = 0; j < citas.size(); j++) {

                    if (horaInicioDoctor.plusMinutes(15 * i).equals(citas.get(j).getAppointmentDateTime().toLocalTime())) //
                    {
                        break;
                    }
                }
                slotsDisponibles.add(horaInicioDoctor.plusMinutes(15 * i).toString() + " - " + horaInicioDoctor.plusMinutes(15 * (i + 1)).toString());
            }
            horaInicioDoctor = currentDoctor.getVisitStartTime();

            horasComboBox.setItems(FXCollections.observableList(slotsDisponibles));
        }
        );

       
    }

    @FXML
    private void crearCitaAct(ActionEvent event) {
        for (int i = 0; i < pacientes.size(); i++) {
                if (pacientesComboBox.getSelectionModel().getSelectedItem().equals(pacientes.get(i).getName() + " " + pacientes.get(i).getSurname())) {
                    currentPatient = pacientes.get(i);
                    break;
                }
        }
        
        
        System.out.println(horasComboBox.getSelectionModel().getSelectedItem().substring(0, 2));
        System.out.println(horasComboBox.getSelectionModel().getSelectedItem().substring(3, 5));
        LocalDate localDate = LocalDate.of(datePicker.valueProperty().getValue().getYear(),datePicker.valueProperty().getValue().getMonth().getValue(),datePicker.valueProperty().getValue().getDayOfMonth());
        LocalTime localTime = LocalTime.of(Integer.parseInt(horasComboBox.getSelectionModel().getSelectedItem().substring(0, 2)),
                                           Integer.parseInt(horasComboBox.getSelectionModel().getSelectedItem().substring(3, 5)));
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        
        Appointment nuevaCita = new Appointment(localDateTime, currentDoctor, currentPatient );
        
        Alert alertAmazon = new Alert(Alert.AlertType.INFORMATION);
            alertAmazon.setTitle("Cita creada");
            alertAmazon.setHeaderText("Cita creada satisfactoriamente");
            alertAmazon.setContentText("Cita añadida al doctor " + currentDoctor.getName() + " " + currentDoctor.getSurname() +  " para el paciente " + currentPatient.getName() + " " + currentPatient.getSurname() + 
                                        " de " + horasComboBox.getSelectionModel().getSelectedItem() + " el día " + localDate.toString());
            alertAmazon.showAndWait();
        
            
         citasObservableList = FXCollections.observableList(clinicDBAccess.getAppointments()); 
         Stage stage = (Stage) cancelButton.getScene().getWindow();
         stage.close();
         citasObservableList.add(nuevaCita);
         guardadoApp = true;
    }

    @FXML
    private void cancelAct(ActionEvent event) {
         Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        guardadoApp = false;
    }

}

class DiaCelda extends DateCell {

    String newline = System.getProperty("line.separator");

    @Override
    public void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.

        // Show Weekends in blue color
        LocalDate today = LocalDate.now();
        DayOfWeek day = DayOfWeek.from(item);
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY || item.compareTo(today) < 0 ) {
            this.setTextFill(Color.ROSYBROWN);
            this.setDisable(true);
            this.setText(this.getText() + "\r");
        } else {
            this.setText(this.getText() + "\r");
            this.setTextFill(Color.GREEN);
        }
    }

}
