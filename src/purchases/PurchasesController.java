/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package purchases;

import com.jfoenix.controls.JFXButton;
import helpers.Links;
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

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class PurchasesController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Pane ordersPane;
    @FXML
    private Pane deliveriesPane;
    @FXML
    private JFXButton pyme;
    @FXML
    private Pane payPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        deliveriesPane.setVisible(false);
        ordersPane.setVisible(false);
        payPane.setVisible(false);

    }

    @FXML
    public void getOrdersView(MouseEvent event) {
        loadViews(Links.PURCHASESORDERS);
        deliveriesPane.setVisible(false);
        payPane.setVisible(false);
        ordersPane.setVisible(true);

    }

    @FXML
    private void getDeliveriesView(MouseEvent event) {
        loadViews(Links.PURCHASESDELIVERIES);
        ordersPane.setVisible(false);
        payPane.setVisible(false);
        deliveriesPane.setVisible(true);
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
    private void getPayementView(MouseEvent event) {
        loadViews(Links.PURCHASESPAY);
        ordersPane.setVisible(false);
        deliveriesPane.setVisible(false);
        payPane.setVisible(true);
    }


}
