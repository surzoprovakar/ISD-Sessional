/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isd;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Raju
 */
public class ISD extends Application {
    Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("fx1.fxml"));
        Object root = loader.load();
        
        Fx1Controller controller=loader.getController();
        controller.setMain(this);
        mainStage=stage;
        
        Scene scene = new Scene((Parent) root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
