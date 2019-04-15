/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionclinica;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Carlos
 */
public class FXMLGestionClinicaController implements Initializable {
    
    private Label label;
    @FXML
    private Button pacientesButton;
    @FXML
    private Button doctoresButton;
    @FXML
    private Button calendarioButton;
   
    
    Glow bloom = new Glow();
    @FXML
    private Button opcionesButton;
    @FXML
    private Button nosotrosButton;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private GridPane gridPane;
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        gridPane.prefHeightProperty().bind(anchorPane.heightProperty());
        gridPane.prefWidthProperty().bind(anchorPane.widthProperty());
        bloom.setLevel(0.5);
        
    }    

    @FXML
    private void pacientesAct(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/Vista/FXMLPacientesMain.fxml"));
        AnchorPane root = (AnchorPane) miCargador.load();

            

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Pacientes");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void doctoresAct(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/Vista/FXMLMedicosMain.fxml"));
        AnchorPane root = (AnchorPane) miCargador.load();

            

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Doctores");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void calendarioAct(ActionEvent event) throws IOException {
        FXMLLoader miCargador = new FXMLLoader(getClass().getResource("/Vista/FXMLCalendarioMain.fxml"));
        AnchorPane root = (AnchorPane) miCargador.load();

            

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Calendario Citas");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private void onMExitedPacientes(MouseEvent event) {
        pacientesButton.setEffect(null);
    }

    @FXML
    private void onMEnteredPacientes(MouseEvent event) {
         pacientesButton.setEffect(bloom);
    }

    @FXML
    private void onMExitedDoctores(MouseEvent event) {
        doctoresButton.setEffect(null);
    }

    @FXML
    private void onMEnteredDoctores(MouseEvent event) {
        doctoresButton.setEffect(bloom);
    }


    @FXML
    private void onMEnteredCalendario(MouseEvent event) {
        calendarioButton.setEffect(bloom);
    }

    @FXML
    private void onMExitedCalendario(MouseEvent event) {
        calendarioButton.setEffect(null);
    }

    @FXML
    private void onMEnteredOpciones(MouseEvent event) {
        opcionesButton.setEffect(bloom);
    }

    @FXML
    private void onMEnteredNosotros(MouseEvent event) {
        nosotrosButton.setEffect(bloom);
    }

    @FXML
    private void onMExitedOpciones(MouseEvent event) {
        opcionesButton.setEffect(null);
    }

    @FXML
    private void onMExitedNosotros(MouseEvent event) {
        nosotrosButton.setEffect(null);
    }
    
}
