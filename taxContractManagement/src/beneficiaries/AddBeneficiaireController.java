/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beneficiaries;

import com.jfoenix.controls.JFXComboBox;
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
import models.Beneficiaire;
import models.Proprietor;
import proprietors.AddProprietorController;

/**
 * FXML Controller class
 *
 * @author Deeplight
 */
public class AddBeneficiaireController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private JFXDatePicker dateFld;
    @FXML
    private TextField communeFld;
    @FXML
    private TextField wilayaField;
    @FXML
    private TextField nationaFld;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Beneficiaire beneficiaire = null;
    private boolean update;
    int beneficiaireId;

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

    void setTextField(int id, String name, LocalDate toLocalDate, String wilaya, String commune, String nationnalite) {
        beneficiaireId = id;
        nameField.setText(name);
        dateFld.setValue(toLocalDate);
        wilayaField.setText(wilaya);
        communeFld.setText(commune);
        nationaFld.setText(nationnalite);
    }

    @FXML
    private void save(MouseEvent event) {
        connection = DbConnect.getConnect();
        String name = nameField.getText();
        String birth = String.valueOf(dateFld.getValue());
        String wilaya = wilayaField.getText();
        String commune = communeFld.getText();
        String nationnalite = nationaFld.getText();

        if (name.isEmpty() || birth.isEmpty() || wilaya.isEmpty() || commune.isEmpty() || nationnalite.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All Data");
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
        wilayaField.setText(null);
        communeFld.setText(null);
        nationaFld.setText(null);
    }

    private void getQuery() {
        if (update == false) {

            query = "INSERT INTO `beneficiaire`(`nom_prenom_or_RS`, `date_nss`, `wilaya`, `commune`,`nationalite`) VALUES (?,?,?,?,?)";

        } else {
            query = "UPDATE `beneficiaire` SET "
                    + "`nom_prenom_or_RS`=?,"
                    + "`date_nss`=?,"
                    + "`wilaya`=?,"
                    + "`commune`=?,"
                    + "`nationalite`=? WHERE id = '" + beneficiaireId + "'";

        }
    }

    private void insert() {
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nameField.getText());
            preparedStatement.setString(2, String.valueOf(dateFld.getValue()));
            preparedStatement.setString(3, wilayaField.getText());
            preparedStatement.setString(4, communeFld.getText());
            preparedStatement.setString(5, nationaFld.getText());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AddProprietorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
