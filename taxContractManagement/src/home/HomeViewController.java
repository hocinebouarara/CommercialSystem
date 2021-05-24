/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

import com.jfoenix.controls.JFXButton;
import helpres.Links;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Deeplight
 */
public class HomeViewController implements Initializable {

    private Button btn1;
    @FXML
    private JFXButton homeIcon;
    @FXML
    private JFXButton homeBtn;
    @FXML
    private Button propaBtn;
    @FXML
    private JFXButton propaIcon;
    @FXML
    private Button proprBtn;
    @FXML
    private JFXButton proprIcon;
    @FXML
    private Button contratBtn;
    @FXML
    private JFXButton contratIcon;
    @FXML
    private Button benefiBtn;
    @FXML
    private JFXButton benefiIcon;
    @FXML
    private Button paymentBtn;
    @FXML
    private JFXButton paymentIcon;
    @FXML
    private AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO    
    }

    private void btnClick(MouseEvent event) {
        btn1.setStyle("-fx-background-color:#212121;-fx-font-size:36px;");
    }

    @FXML
    private void getHomeView(MouseEvent event) {
        propaIcon.setStyle("-fx-background-color:transparent;");
        propaBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
        proprIcon.setStyle("-fx-background-color:transparent;");
        proprBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
        benefiIcon.setStyle("-fx-background-color:transparent;");
        benefiBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
        contratIcon.setStyle("-fx-background-color:transparent;");
        contratBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");

        homeIcon.setStyle("-fx-background-color:#81D4FA;-fx-background-radius:12 12 0 12;");
        homeBtn.setStyle("-fx-text-fill:white;-fx-background-color:#0277BD;-fx-background-radius:20 0 0 20;");
    }

    @FXML
    private void getPropaView(MouseEvent event) {
        proprIcon.setStyle("-fx-background-color:transparent;");
        proprBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
        homeIcon.setStyle("-fx-background-color:transparent;");
        homeBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
        benefiIcon.setStyle("-fx-background-color:transparent;");
        benefiBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
         contratIcon.setStyle("-fx-background-color:transparent;");
        contratBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
         paymentIcon.setStyle("-fx-background-color:transparent;");
        paymentBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");



        propaIcon.setStyle("-fx-background-color:#81D4FA;-fx-background-radius:12 12 0 12;");
        propaBtn.setStyle("-fx-text-fill:white;-fx-background-color:#0277BD;-fx-background-radius:20 0 0 20;");
        
        loadViews(Links.PROPERTYVIEW);
    }

    @FXML
    private void getProprView(MouseEvent event) {
        propaIcon.setStyle("-fx-background-color:transparent;");
        propaBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
        homeIcon.setStyle("-fx-background-color:transparent;");
        homeBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
        contratIcon.setStyle("-fx-background-color:transparent;");
        contratBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
        benefiIcon.setStyle("-fx-background-color:transparent;");
        benefiBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
         paymentIcon.setStyle("-fx-background-color:transparent;");
        paymentBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");


        proprIcon.setStyle("-fx-background-color:#81D4FA;-fx-background-radius:12 12 0 12;");
        proprBtn.setStyle("-fx-text-fill:white;-fx-background-color:#0277BD;-fx-background-radius:20 0 0 20;");
    }

    @FXML
    private void getContratsView(MouseEvent event) {
        propaIcon.setStyle("-fx-background-color:transparent;");
        propaBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
        homeIcon.setStyle("-fx-background-color:transparent;");
        homeBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
        proprIcon.setStyle("-fx-background-color:transparent;");
        proprBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
        benefiIcon.setStyle("-fx-background-color:transparent;");
        benefiBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
        paymentIcon.setStyle("-fx-background-color:transparent;");
        paymentBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");

        contratIcon.setStyle("-fx-background-color:#81D4FA;-fx-background-radius:12 12 0 12;");
        contratBtn.setStyle("-fx-text-fill:white;-fx-background-color:#0277BD;-fx-background-radius:20 0 0 20;");

    }

    @FXML
    private void getBenefisView(MouseEvent event) {
        propaIcon.setStyle("-fx-background-color:transparent;");
        propaBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
        homeIcon.setStyle("-fx-background-color:transparent;");
        homeBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
        proprIcon.setStyle("-fx-background-color:transparent;");
        proprBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
        contratIcon.setStyle("-fx-background-color:transparent;");
        contratBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
        paymentIcon.setStyle("-fx-background-color:transparent;");
        paymentBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");

        benefiIcon.setStyle("-fx-background-color:#81D4FA;-fx-background-radius:12 12 0 12;");
        benefiBtn.setStyle("-fx-text-fill:white;-fx-background-color:#0277BD;-fx-background-radius:20 0 0 20;");

    }

    @FXML
    private void getpaymentView(MouseEvent event) {
        propaIcon.setStyle("-fx-background-color:transparent;");
        propaBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
        homeIcon.setStyle("-fx-background-color:transparent;");
        homeBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
        proprIcon.setStyle("-fx-background-color:transparent;");
        proprBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
        contratIcon.setStyle("-fx-background-color:transparent;");
        contratBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");
        benefiIcon.setStyle("-fx-background-color:transparent;");
        benefiBtn.setStyle("-fx-text-fill:#6670a1;-fx-background-color:transparent;");

        paymentIcon.setStyle("-fx-background-color:#81D4FA;-fx-background-radius:12 12 0 12;");
        paymentBtn.setStyle("-fx-text-fill:white;-fx-background-color:#0277BD;-fx-background-radius:20 0 0 20;");
    }
    
     private void loadViews(String viewName) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(viewName));
            anchorPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
