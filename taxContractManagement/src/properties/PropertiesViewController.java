/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package properties;

import helpres.Links;
import home.HomeViewController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Deeplight
 */
public class PropertiesViewController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Pane editPane;
    @FXML
    private Pane editPane1;
    @FXML
    private AnchorPane PropertiesAnchor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        getPropertiesTable();
    }

    @FXML
    private void getPropertiesTable() {
        loadViews(Links.PROPERTYTABLEVIEW);
    }

    @FXML
    private void statView(MouseEvent event) {
    }

    @FXML
    private void getAddPropertiesView(MouseEvent event) {
        loadViews(Links.ADDPROPERTYVIEW);
    }

    private void loadViews(String viewName) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(viewName));
            PropertiesAnchor.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
