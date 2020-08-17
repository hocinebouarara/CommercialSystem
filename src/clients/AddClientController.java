/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients;

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
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javax.swing.JOptionPane;
import models.Client;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class AddClientController  implements Initializable {

    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField adressField;
    @FXML
    private JFXTextField cityField;
    @FXML
    private JFXTextField phoneField;
    @FXML
    private JFXTextField faxField;
    @FXML
    private JFXTextField agentField;
    @FXML
    private VBox succesfullyPane;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        succesfullyPane.setVisible(false);
    }


    @FXML
    private void saveHandle(ActionEvent event) {
        
    
        isRegister();
        clean();

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
        }

    }

    public void isRegister()  {
        connection = DbConnect.getConnect();

        query = "INSERT INTO client (NOCL, ADCL, VICL, TECL, FACL, NORECL) VALUES (?, ?, ?,?, ?, ?)";
        try {
            Registersign();

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
        }
    }

}
