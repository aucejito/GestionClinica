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
    @FXML
    private Button actualizarTablaButton;
    @FXML
    private Button delButton;
    @FXML
    private Button showButton;
    static Appointment selectedAppointment;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        LocalDate fechaFormat;
        currentAppointments = new ArrayList<Appointment>();
        LocalDate l = LocalDate.now();
        datePicker.setValue(l);
        
        listaCitas = clinicDBAccess.getAppointments();
        for (int i = 0; i < listaCitas.size(); i++) {
                fechaFormat = (listaCitas.get(i).getAppointmentDateTime()).toLocalDate();
                
            if(l.equals(fechaFormat)){
                currentAppointments.add(listaCitas.get(i));
            }
                
        }
        try {
            inicializarTabla(currentAppointments);
        } catch (Exception ex) {
            Logger.getLogger(FXMLCalendarioMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        delButton.disableProperty().bind(tablaCitas.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
        showButton.disableProperty().bind(tablaCitas.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
    }

    private void inicializarTabla(ArrayList<Appointment> listacitas) throws Exception {
       tablaCitas.setItems(FXCollections.observableList(listacitas));
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

    @FXML
    private void addCitas(ActionEvent event) throws IOException, Exception {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/Vista/FXMLCalendarioAdd.fxml"));
        AnchorPane root = (AnchorPane) miCargador.load();

            

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Añadir cita");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        if(FXMLCalendarioAddController.guardadoApp == true){
            tablaCitas.setItems(FXCollections.observableList(listaCitas));
        }
        refrescarTabla();
        
    }

    @FXML
    private void refreshTableAct(ActionEvent event) throws Exception {
        LocalDate fechaFormat;
        currentAppointments = new ArrayList<Appointment>();
        LocalDate l = datePicker.getValue();
                
        listaCitas = clinicDBAccess.getAppointments();
        for (int i = 0; i < listaCitas.size(); i++) {
                fechaFormat = (listaCitas.get(i).getAppointmentDateTime()).toLocalDate();
                
            if(l.equals(fechaFormat)){
                currentAppointments.add(listaCitas.get(i));
            }
        }
        inicializarTabla(currentAppointments);
    }

    private void refrescarTabla() throws Exception{
        LocalDate fechaFormat;
        currentAppointments = new ArrayList<Appointment>();
        LocalDate l = datePicker.getValue();
                
        listaCitas = clinicDBAccess.getAppointments();
        for (int i = 0; i < listaCitas.size(); i++) {
                fechaFormat = (listaCitas.get(i).getAppointmentDateTime()).toLocalDate();
                
            if(l.equals(fechaFormat)){
                currentAppointments.add(listaCitas.get(i));
            }
        }
        inicializarTabla(currentAppointments);

    }
    
    @FXML
    private void delAct(ActionEvent event) throws Exception {
        listaCitas.remove(tablaCitas.getSelectionModel().getSelectedIndex());
        refrescarTabla();
        
    }

    @FXML
    private void showAct(ActionEvent event) throws IOException {
        selectedAppointment = tablaCitas.getSelectionModel().getSelectedItem();
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/Vista/FXMLCalendarioDetalles.fxml"));
        AnchorPane root = (AnchorPane) miCargador.load();   
               
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Mostrar detalles cita");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    
}
