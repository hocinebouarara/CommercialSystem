/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products;

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
import models.Product;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class AddProductController implements Initializable {

    @FXML
    private JFXTextField referField;
    @FXML
    private JFXTextField desigField;
    @FXML
    private JFXTextField QntField;
    @FXML
    private JFXTextField CatField;
    @FXML
    private JFXTextField buyField;
    @FXML
    private JFXTextField saleField;
    @FXML
    private JFXTextField buyTotField;
    @FXML
    private JFXTextField saleTotField;
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
    }

    @FXML
    private void getImage(MouseEvent event) {
    }

    @FXML
    private void saveHandle(ActionEvent event) {
        
    }

    @FXML
    private void clean() {
    }

    @FXML
    private void close(MouseEvent event) {
    }

    

    

}
