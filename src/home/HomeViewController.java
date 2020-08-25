/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

import helpers.Links;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class HomeViewController implements Initializable {

    @FXML
    private Circle myImage;
    @FXML
    private VBox boxButton;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private BorderPane borderPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    @FXML
    private void min(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void max(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setFullScreen(true);
    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void setNode(Node node) {
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add((Node) node);
    }

    @FXML
    private void getHomeView(MouseEvent event) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        borderPane.setCenter(null);

    }

    private void loadViews(String viewName) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(viewName));
            anchorPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void getproductsView(MouseEvent event) {
        loadViews(Links.PRODUCTSVIEW);
    }

    @FXML
    private void getClientsView(MouseEvent event) {
        loadViews(Links.CLIENTSVIEW);

    }

    @FXML
    private void getSuppliersView(MouseEvent event) {
        loadViews(Links.SUPPLIERSVIEW);

    }

    @FXML
    private void getPurchasesView(MouseEvent event) {
        loadViews(Links.PURCHASESORDERS);
    }

    @FXML
    private void getSalesView(MouseEvent event) {
    }

}
