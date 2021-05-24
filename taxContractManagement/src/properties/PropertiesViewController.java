/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package properties;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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
    private Pane statPane;
    @FXML
    private TableView<?> supplierTable;
    @FXML
    private TableColumn<?, ?> idCol;
    @FXML
    private TableColumn<?, ?> nameCol;
    @FXML
    private TableColumn<?, ?> agentCol;
    @FXML
    private TableColumn<?, ?> adressCol;
    @FXML
    private TableColumn<?, ?> CityCol;
    @FXML
    private TableColumn<?, ?> phoneCol;
    @FXML
    private TableColumn<?, ?> faxCol;
    @FXML
    private TableColumn<?, ?> CnssCol;
    @FXML
    private TableColumn<?, ?> operationCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void editPane(MouseEvent event) {
    }

    @FXML
    private void statisticsPane(MouseEvent event) {
    }

    @FXML
    private void addMembers(MouseEvent event) {
    }

    @FXML
    private void refreshTable(MouseEvent event) {
    }
    
}
