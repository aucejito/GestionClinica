/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionclinica;

import DBAccess.ClinicDBAccess;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Appointment;
import model.DateTimeAdapter;
import model.Doctor;
import model.Patient;
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
    private ComboBox<String> medComboBox;
    
    ClinicDBAccess clinicDBAccess = ClinicDBAccess.getSingletonClinicDBAccess();
    ObservableList<Doctor> doctorsObservableList = FXCollections.observableList(clinicDBAccess.getDoctors());
    @FXML
    private Button prevWeekButton;
    @FXML
    private Button nextWeekButton;
    
    Doctor currentDoctor;
   
    @FXML
    private TableView<Appointment> tablaCitas;
    DateTimeAdapter dtAdapter;
    Appointment cita;
    ArrayList<Appointment> listaCitas;
    String hCita;
    @FXML
    private TableColumn<Appointment, String> medColumn;
    @FXML
    private TableColumn<Appointment, String> pacienteColumn;
    @FXML
    private TableColumn<Appointment, String> horaColumn;
    @FXML
    private DatePicker datePicker;
    ArrayList<Appointment> currentAppointments;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        LocalDate fechaFormat;
        currentAppointments = new ArrayList<Appointment>();
        LocalDate l = LocalDate.of(2019, 03, 30);
        datePicker.setValue(l);
        
        listaCitas = clinicDBAccess.getAppointments();
        for (int i = 0; i < listaCitas.size(); i++) {
                fechaFormat = (listaCitas.get(i).getAppointmentDateTime()).toLocalDate();
                
            if(l.equals(fechaFormat)){
                currentAppointments.add(listaCitas.get(i));
            }
                
        }
        tablaCitas.setItems(FXCollections.observableList(currentAppointments));
        medColumn.setCellValueFactory(celda2 -> new SimpleObjectProperty<String>(celda2.getValue().getDoctor().getName()));
        medColumn.setCellFactory(columna -> {return new TableCell<Appointment, String>(){
            private Text view = new Text();
            @Override  
            protected void updateItem(String item, boolean empty){
                if(item == null || empty){
                    setGraphic(null);
                }else{
                view.setText(item);
                setGraphic(view);
                    }
            }
            
            };
        });
        pacienteColumn.setCellValueFactory(celda2 -> new SimpleObjectProperty<String>(celda2.getValue().getPatient().getName()));
        pacienteColumn.setCellFactory(columna -> {return new TableCell<Appointment, String>(){
            private Text view = new Text();
            @Override  
            protected void updateItem(String item, boolean empty){
                if(item == null || empty){
                    setGraphic(null);
                }else{
                view.setText(item);
                setGraphic(view);
                    }
            }
            
            };
        });
        pacienteColumn.setCellValueFactory(celda2 -> new SimpleObjectProperty<String>(celda2.getValue().getPatient().getName()));
        pacienteColumn.setCellFactory(columna -> {return new TableCell<Appointment, String>(){
            private Text view = new Text();
            @Override  
            protected void updateItem(String item, boolean empty){
                if(item == null || empty){
                    setGraphic(null);
                }else{
                view.setText(item);
                setGraphic(view);
                    }
            }
            
            };
        });
        horaColumn.setCellValueFactory(celda2 -> new SimpleObjectProperty<String>(celda2.getValue().getAppointmentDateTime().toString().substring(11, 16)));
        horaColumn.setCellFactory(columna -> {return new TableCell<Appointment, String>(){
            private Text view = new Text();
            @Override  
            protected void updateItem(String item, boolean empty){
                if(item == null || empty){
                    setGraphic(null);
                }else{
                view.setText(item);
                setGraphic(view);
                    }
            }
            
            };
        });
    }

    private void inicializarTabla() throws Exception {
       
    }

    @FXML
    private void addCitas(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/Vista/FXMLCalendarioAdd.fxml"));
        AnchorPane root = (AnchorPane) miCargador.load();

            

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Añadir cita");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    
}
