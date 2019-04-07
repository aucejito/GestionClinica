/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionclinica;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Carlos
 */
public class GestionClinica extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLGestionClinica.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        ObservableList<String> items = FXCollections.observableArrayList();

        items.addAll(new String[] { "All", "Item 1", "Item 2", "Item 3", "Item 4" });

        CheckComboBox<String> controll = new CheckComboBox<String>(items);

        controll.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
            public void onChanged(ListChangeListener.Change<? extends String> c) {

                while (c.next()) {
                    if (c.wasAdded()) {
                        if (c.toString().contains("All")) {

                            // if we call the getCheckModel().clearChecks() this will
                            // cause to "remove" the 'All' too at least inside the model.
                            // So we need to manually clear everything else
                            for (int i = 1; i < items.size(); i++) {
                                controll.getCheckModel().clearCheck(i);
                            }

                        } else {
                            // check if the "All" option is selected and if so remove it
                            if (controll.getCheckModel().isChecked(0)) {
                                controll.getCheckModel().clearCheck(0);
                            }

                        }
                    }
                }
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
