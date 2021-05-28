/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beneficiaries;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.Beneficiaire;
import models.Proprietor;

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
    }

    void setTextField(int id, String name, LocalDate toLocalDate, String wilaya, String commune, String nationnalite) {
    }

    @FXML
    private void save(MouseEvent event) {
    }

    @FXML
    private void clean(MouseEvent event) {
    }
    
}
