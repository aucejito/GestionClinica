
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionclinica;

import DBAccess.ClinicDBAccess;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener ;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import model.Patient;
import org.controlsfx.control.textfield.CustomTextField;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class FXMLPacientesMainController implements Initializable {

    TableView<Patient> lPatients;
    Image avatar;
    @FXML
    private ComboBox tipoBusqueda;
  
    @FXML
    private CustomTextField barraBusqueda;
    
    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane scene;
    
    private static final Image imgLupa = new Image("loupe.png");
    static boolean editable;
    @FXML
    private TableView<Patient> tablaPacientes;
    @FXML
    private TableColumn<Patient, Image> columnaFoto;
    @FXML
    private TableColumn<Patient, String> columnaNombre;
    @FXML
    private TableColumn<Patient, String> columnaApellidos;
    @FXML
    private TableColumn<Patient, String> columnaDNI;
    
   ArrayList<Patient> pacientes;
   static Patient current;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       String [] options = {"DNI", "Nombre", "Apellido", "Teléfono"};
       
       tipoBusqueda.setItems(FXCollections.observableArrayList(options));           
                        
        tipoBusqueda.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
        public void changed(ObservableValue ov, Number value, Number new_value){
        barraBusqueda.setPromptText("Introduce aquí el "+ (options[new_value.intValue()]).toLowerCase());
        }
        });
        barraBusqueda.setLeft(new ImageView(imgLupa));
        
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        
        ClinicDBAccess datosClinica = ClinicDBAccess.getSingletonClinicDBAccess();
        pacientes = datosClinica.getPatients();
        tablaPacientes.setItems(FXCollections.observableList(pacientes));
        columnaFoto.setCellValueFactory(celda1 -> new SimpleObjectProperty<Image>(celda1.getValue().getPhoto()));
        columnaFoto.setCellFactory(columna -> {return new TableCell<Patient, Image>(){
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
        columnaNombre.setCellFactory(columna -> {return new TableCell<Patient, String>(){
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
        columnaApellidos.setCellFactory(columna -> {return new TableCell<Patient, String>(){
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
        columnaDNI.setCellFactory(columna -> {return new TableCell<Patient, String>(){
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
    private void addAct(ActionEvent event) throws IOException {
        editable = true;
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/Vista/FXMLPacientesAdd.fxml"));
        AnchorPane root = (AnchorPane) miCargador.load();

            

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Añadir paciente");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        
        /*ClinicDBAccess clinicDBAccess = ClinicDBAccess.getSingletonClinicDBAccess();
        ObservableList<Patient> patientsObservableList;
        //Envolvemos el ArrayList de pacientes de de la clínica en una ObservableList
        patientsObservableList = FXCollections.observableList(clinicDBAccess.getPatients());
        // lPatients es una ListView<Patient>
        lPatients.setItems(patientsObservableList); //Se conecta la lista con el ListView
        Patient patient = new Patient("5307867J","Juan","Cafe Grandes","9376543",avatar);
        patientsObservableList.add(patient); //Se añade un Nuevo paciente
        clinicDBAccess.saveDB(); //Se almacena el cambio en el fichero XML*/
    }

    @FXML
    private void delAct(ActionEvent event) {
        current = tablaPacientes.getSelectionModel().getSelectedItem();
        if(ClinicDBAccess.getSingletonClinicDBAccess().hasAppointments(current)){
            Alert alertAmazon = new Alert(Alert.AlertType.INFORMATION);
            alertAmazon.setTitle("Error");
            alertAmazon.setHeaderText("No se puede borrar este paciente");
            alertAmazon.setContentText("Por favor, compruebe que el paciente no tiene ninguna cita concertada");
            alertAmazon.showAndWait();
        } else {
            //borrar paciente (?)
        }
    }

    @FXML
    private void showAct(ActionEvent event) throws IOException {
        editable = false;
        current = tablaPacientes.getSelectionModel().getSelectedItem();
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/Vista/FXMLPacientesAdd.fxml"));
        AnchorPane root = (AnchorPane) miCargador.load();   
               
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Mostrar detalles paciente");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        
    }

    

      
}
