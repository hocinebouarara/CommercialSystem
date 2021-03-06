/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import helpers.DbConnect;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
import models.Client;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class AddClientController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField adressField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField faxField;
    @FXML
    private TextField agentField;
    @FXML
    private VBox succesfullyPane;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    private boolean update = false;
    int clientId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        succesfullyPane.setVisible(false);
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public void setTextFieds(int id, String name, String adress, String city,
            String phone, String fax, String agent) {
        clientId = id;
        nameField.setText(name);
        adressField.setText(adress);
        cityField.setText(city);
        phoneField.setText(phone);
        faxField.setText(fax);
        agentField.setText(agent);

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

    }

    @FXML
    public void Registersign() {

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
        } else {
            isRegister();
            clean();
        }

    }

    public void isRegister() {
        connection = DbConnect.getConnect();
        if (update == false) {

            query = "INSERT INTO client (NOCL, ADCL, VICL, TECL, FACL, NORECL) VALUES (?, ?, ?,?, ?, ?)";

        } else {

            query = "UPDATE `client` SET "
                    + "`NOCL`=?,"
                    + "`ADCL`=?,"
                    + "`VICL`=?,"
                    + "`TECL`=?,"
                    + "`FACL`=?,"
                    + "`NORECL`=? WHERE IDCL = '"+clientId+"'";

        }
        insert();
    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void insert() {
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nameField.getText());
            preparedStatement.setString(2, adressField.getText());
            preparedStatement.setString(3, cityField.getText());
            preparedStatement.setString(4, phoneField.getText());
            preparedStatement.setString(5, faxField.getText());
            preparedStatement.setString(6, agentField.getText());

            preparedStatement.execute();
            succesfullyPane.setVisible(true);

            //JOptionPane.showMessageDialog(null, "succes");
        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, e);
        }    }

    @FXML
    private void getBuyTot(KeyEvent event) {
    }

}
