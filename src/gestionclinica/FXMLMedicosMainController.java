/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionclinica;

import DBAccess.ClinicDBAccess;
import static gestionclinica.FXMLPacientesMainController.current;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Doctor;
import model.Patient;
import org.controlsfx.control.textfield.CustomTextField;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class FXMLMedicosMainController implements Initializable {

    @FXML
    private AnchorPane scene;
    @FXML
    private BorderPane borderPane;
    @FXML
    private ComboBox tipoBusqueda;
    @FXML
    private CustomTextField barraBusqueda;
    private static final Image imgLupa = new Image("loupe.png");
    @FXML
     TableView<Doctor> tablaDoctores;
    @FXML
    private TableColumn<Doctor, Image> columnaFoto;
    @FXML
    private TableColumn<Doctor, String> columnaDNI;
    @FXML
    private TableColumn<Doctor, String> columnaNombre;
    @FXML
    private TableColumn<Doctor, String> columnaApellidos;
    
    ArrayList<Doctor> doctores;
    static boolean editableM;
    static Doctor currentM;
    @FXML
    private Button delButton;
    @FXML
    private Button showButton;
    ClinicDBAccess clinicDBAccess = ClinicDBAccess.getSingletonClinicDBAccess();
    ObservableList<Doctor> doctorsObservableList;
    ArrayList<Doctor> introducirDoctores = new ArrayList<Doctor>();
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        doctores = clinicDBAccess.getDoctors();
        doctorsObservableList = FXCollections.observableList(clinicDBAccess.getDoctors());
        String [] options = {"DNI", "Nombre", "Apellido"};
        
        tipoBusqueda.setItems(FXCollections.observableArrayList(options));           
                        
        tipoBusqueda.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
        public void changed(ObservableValue ov, Number value, Number new_value){
        barraBusqueda.setPromptText("Introduce aquí el "+ (options[new_value.intValue()]).toLowerCase());
        }
        });
        barraBusqueda.setLeft(new ImageView(imgLupa));
        
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        
        inicializarTabla(doctores);
        
        delButton.disableProperty().bind(tablaDoctores.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
        showButton.disableProperty().bind(tablaDoctores.getSelectionModel().selectedIndexProperty().isEqualTo(-1));
    }    

    @FXML
    private void addAct(ActionEvent event) throws IOException {
        editableM = true;
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/Vista/FXMLMedicosAdd.fxml"));
        AnchorPane root = (AnchorPane) miCargador.load();

            

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Añadir Médico");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        
        if(FXMLMedicosAddController.guardadoD == true){
            tablaDoctores.setItems(doctorsObservableList);
        }
        
        
    }

    @FXML
    private void delAct(ActionEvent event) {
        currentM = tablaDoctores.getSelectionModel().getSelectedItem();
        if(ClinicDBAccess.getSingletonClinicDBAccess().hasAppointments(currentM)){
            Alert alertAmazon = new Alert(Alert.AlertType.INFORMATION);
            alertAmazon.setTitle("Error");
            alertAmazon.setHeaderText("No se puede borrar este doctor");
            alertAmazon.setContentText("Por favor, compruebe que el doctor no tiene ninguna cita concertada");
            alertAmazon.showAndWait();
        } else {
            //borrar doctor (?)
            doctores.remove(tablaDoctores.getSelectionModel().getSelectedIndex());
            tablaDoctores.setItems(doctorsObservableList);
        }
    }

    @FXML
    private void showAct(ActionEvent event) throws IOException {
        editableM = false;
        currentM = tablaDoctores.getSelectionModel().getSelectedItem();
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/Vista/FXMLMedicosAdd.fxml"));
        AnchorPane root = (AnchorPane) miCargador.load();   
               
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Mostrar detalles doctor");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    
    
    private void inicializarTabla(ArrayList<Doctor> doctores1){
        
        currentM = tablaDoctores.getSelectionModel().getSelectedItem();
        tablaDoctores.setItems(FXCollections.observableList(doctores1));
        columnaFoto.setCellValueFactory(celda1 -> new SimpleObjectProperty<>(celda1.getValue().getPhoto()));
        columnaFoto.setCellFactory(columna -> {return new TableCell<Doctor, Image>(){
            private ImageView view = new ImageView();
            @Override
            protected void updateItem(Image item, boolean empty){
                if(item == null || empty) setGraphic(null);
                else{
                    view.setImage(item);
                    view.setFitHeight(60);
                    view.setFitWidth(60);
                    view.setPreserveRatio(true);
                    setGraphic(view);
                    }
                }
            };
        });
        columnaNombre.setCellValueFactory((celda2 -> new SimpleObjectProperty<String>(celda2.getValue().getName())));
        columnaNombre.setCellFactory(columna -> {return new TableCell<Doctor, String>(){
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
        columnaApellidos.setCellValueFactory((celda3 -> new SimpleObjectProperty<String>(celda3.getValue().getSurname())));
        columnaApellidos.setCellFactory(columna -> {return new TableCell<Doctor, String>(){
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
        columnaDNI.setCellValueFactory((celda4 -> new SimpleObjectProperty<String>(celda4.getValue().getIdentifier())));
        columnaDNI.setCellFactory(columna -> {return new TableCell<Doctor, String>(){
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
    private void buscarAct(ActionEvent event) {
        String bus = barraBusqueda.getText().toLowerCase();


        for (int i = introducirDoctores.size(); i > 0; i--) {
            introducirDoctores.remove(0);
        }
        inicializarTabla(introducirDoctores);
        switch(tipoBusqueda.getSelectionModel().getSelectedIndex()){

            case 0:
                for (int i = 0; i < doctores.size(); i++) {
                    String patient = doctores.get(i).getIdentifier().toLowerCase();
                    System.out.println(patient);
                    System.out.println(bus);
                    if(patient.equals(bus) ||  patient.startsWith(bus)){
                        introducirDoctores.add(doctores.get(i));
                    }
                }
                inicializarTabla(introducirDoctores);

                break;

            case 1:
                for (int i = 0; i < doctores.size(); i++) {
                    String patient = doctores.get(i).getName().toLowerCase();
                    if(patient.equals(bus) ||  patient.startsWith(bus)){
                        introducirDoctores.add(doctores.get(i));
                    }
                }
                inicializarTabla(introducirDoctores);
                break;

            case 2:
                for (int i = 0; i < doctores.size(); i++) {
                    String patient = doctores.get(i).getSurname().toLowerCase();
                    if(patient.equals(bus) || patient.startsWith(bus)){
                        introducirDoctores.add(doctores.get(i));
                    }
                }
                inicializarTabla(introducirDoctores);
                break;

        }
    }

    @FXML
    private void cancelarBusc(ActionEvent event) {
        inicializarTabla(doctores);
    }
    
    
}
