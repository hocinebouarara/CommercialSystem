/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proprietors;

import com.jfoenix.controls.JFXDatePicker;
import helpres.DbConnect;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.Proprietor;

/**
 * FXML Controller class
 *
 * @author Deeplight
 */
public class AddProprietorController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private JFXDatePicker dateFld;
    @FXML
    private TextField adressField;
    @FXML
    private TextField telephoneField;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Proprietor proprietor = null;
    private boolean update;
    int proprietorId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void setUpdate(boolean b) {
        this.update = b;

    }

    void setTextField(int id, String name, LocalDate toLocalDate, String adress, String email) {

        proprietorId = id;
        nameField.setText(name);
        dateFld.setValue(toLocalDate);
        adressField.setText(adress);
        telephoneField.setText(email);

    }

    @FXML
    private void save(MouseEvent event) {
        connection = DbConnect.getConnect();
        String name = nameField.getText();
        String birth = String.valueOf(dateFld.getValue());
        String adress = adressField.getText();
        String phone = telephoneField.getText();

        if (name.isEmpty() || birth.isEmpty() || adress.isEmpty() || phone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        } else {
            getQuery();
            insert();
            clean();

        }

    }

    @FXML
    private void clean() {
        nameField.setText(null);
        dateFld.setValue(null);
        adressField.setText(null);
        telephoneField.setText(null);
    }

    private void getQuery() {
        if (update == false) {

            query = "INSERT INTO `proprietaire`(`nom_prenom_or_RS`, `date_nss`, `adress`, `telephone`) VALUES (?,?,?,?)";

        } else {
            query = "UPDATE `proprietaire` SET "
                    + "`nom_prenom_or_RS`=?,"
                    + "`date_nss`=?,"
                    + "`adress`=?,"
                    + "`telephone`=? WHERE id = '" + proprietorId + "'";

        }
    }

    private void insert() {

        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nameField.getText());
            preparedStatement.setString(2, String.valueOf(dateFld.getValue()));
            preparedStatement.setString(3, adressField.getText());
            preparedStatement.setString(4, telephoneField.getText());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AddProprietorController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
