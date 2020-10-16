/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import com.mysql.jdbc.PreparedStatement;
import helpers.DbConnect;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author hocinebouarara
 */
public class LoginViewController implements Initializable {

    @FXML
    private TextField userNameFld;
    @FXML
    private PasswordField passwordFld;
    
   
    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    private boolean checkUser = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
     public boolean checkUsername(String username) {

        query = "SELECT * FROM `employee` WHERE `USEM` =?";

        try {

            connection = DbConnect.getConnect();
            preparedStatement = (PreparedStatement) connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                checkUser = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checkUser;
    }

    @FXML
    private void onKeyTyped(KeyEvent event) {
    }

    @FXML
    private void signIn(MouseEvent event) {
    }

    @FXML
    private void close(MouseEvent event) {
    }
    
}
