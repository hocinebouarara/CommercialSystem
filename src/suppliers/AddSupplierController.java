/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suppliers;

import clients.ClientsViewController;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import helpers.DbConnect;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import models.Client;
import models.Supplier;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class AddSupplierController implements Initializable {

    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField adressField;
    @FXML
    private JFXTextField agentField;
    @FXML
    private JFXTextField cityField;
    @FXML
    private JFXTextField phoneField;
    @FXML
    private JFXTextField faxField;
    @FXML
    private JFXTextField cnssField;
    @FXML
    private VBox succesfullyPane;
    
    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    Supplier supplier = null;

    ObservableList<Supplier> suppliersList = FXCollections.observableArrayList();


    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    @FXML
    private void Registersign() {
        String name = nameField.getText();
        String adress = adressField.getText();
        String city = cityField.getText();
        String phone = phoneField.getText();
        String fax = faxField.getText();
        String agent = agentField.getText();

        if (name.isEmpty() || adress.isEmpty()
                || city.isEmpty() || phone.isEmpty()
                || fax.isEmpty() || agent.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
            return;
        }else{
            isRegister();
            clean();
        }
    }

    @FXML
    private void clean() {
        succesfullyPane.setVisible(false);
        nameField.setText(null);
        adressField.setText(null);
        cityField.setText(null);
        phoneField.setText(null);
        faxField.setText(null);
        agentField.setText(null);
        cnssField.setText(null);
        
    }

    @FXML
    private void close(MouseEvent event) {
    }

    private void isRegister() {
         connection = DbConnect.getConnect();

        query = "INSERT INTO fournisseur (NOFR, NORE, ADFR, VIFR, TEFR, FAFR,CNSS) VALUES (?,?, ?, ?,?, ?, ?)";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nameField.getText());
             preparedStatement.setString(2, agentField.getText());
            preparedStatement.setString(3, adressField.getText());
            preparedStatement.setString(4, cityField.getText());
            preparedStatement.setString(5, phoneField.getText());
            preparedStatement.setString(6, faxField.getText());
            preparedStatement.setString(7, cnssField.getText());
            
           

            preparedStatement.execute();
            succesfullyPane.setVisible(true);
            
     
            
            //JOptionPane.showMessageDialog(null, "succes");
        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
}
