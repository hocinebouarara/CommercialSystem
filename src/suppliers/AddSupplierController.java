/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suppliers;

import com.jfoenix.controls.JFXTextField;
import helpers.DbConnect;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import models.Supplier;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class AddSupplierController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField adressField;
    @FXML
    private TextField agentField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField faxField;
    @FXML
    private TextField cnssField;
    

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    Supplier supplier = null;
    private boolean update = false;
    int supplierId;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public void setTextFields(int id, String name, String agent, String adress, String city,
            String phone, String fax, String cnss) {
        supplierId = id;
        nameField.setText(name);
        agentField.setText(agent);
        adressField.setText(adress);
        cityField.setText(city);
        phoneField.setText(phone);
        faxField.setText(fax);
        cnssField.setText(cnss);

    }

    @FXML
    private void Registersign() {
        String name = nameField.getText();
        String adress = adressField.getText();
        String city = cityField.getText();
        String phone = phoneField.getText();
        String fax = faxField.getText();
        String agent = agentField.getText();
        String cnss = cnssField.getText();

        if (name.isEmpty() || adress.isEmpty()
                || city.isEmpty() || phone.isEmpty()
                || fax.isEmpty() || agent.isEmpty() || cnss.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
            return;
        } else {
            isRegister();
            clean();
        }
    }

    @FXML
    private void clean() {
        
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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void isRegister() {
        connection = DbConnect.getConnect();

        if (update == false) {

            query = "INSERT INTO fournisseur (NOFR, NORE, ADFR, VIFR, TEFR, FAFR,CNSS) VALUES (?,?, ?, ?,?, ?, ?)";

        } else {
            query = "UPDATE `fournisseur` SET "
                    + "`NOFR`=?,"
                    + "`NORE`=?,"
                    + "`ADFR`=?,"
                    + "`VIFR`=?,"
                    + "`TEFR`=?,"
                    + "`FAFR`=?,"
                    + "`CNSS`=? WHERE IDFO = '" + supplierId + "'";
        }

        insert();
    }

    private void insert() {
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
            
            //JOptionPane.showMessageDialog(null, "succes");
        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, e);
        }
    }

}
