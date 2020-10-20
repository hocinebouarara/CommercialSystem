/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commercialsysetm;

import helpers.Links;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author hocin
 */
public class MainSysetm extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        //Parent root = FXMLLoader.load(getClass().getResource(Links.LOGINVIEW));
        Parent root = FXMLLoader.load(getClass().getResource("../login/loginView.fxml"));
        Scene scene  = new Scene(root);
        scene.getStylesheets().add(getClass().getResource(Links.VIEWSTYLE).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);  
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
