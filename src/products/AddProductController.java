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
        Registersign();
        isRegister();
        clean();
    }

    @FXML
    private void clean() {
        
        
    }

    @FXML
    private void close(MouseEvent event) {
    }

    public void isRegister() {
        connection = DbConnect.getConnect();

        query = "INSERT INTO article (REFA, DEAR,QSAR, SEAR,PRAC,PRVE,PRTA,PRTV) VALUES ( ?,?, ?, ?, ?,?, ?, ?)";
        try {
            Registersign();

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, referField.getText());
            preparedStatement.setString(2, desigField.getText());
            preparedStatement.setString(3, QntField.getText());
            preparedStatement.setString(4, CatField.getText());
            preparedStatement.setString(5, buyField.getText());
            preparedStatement.setString(6, saleField.getText());
            preparedStatement.setString(7, buyTotField.getText());
            preparedStatement.setString(8, saleTotField.getText());

            preparedStatement.execute();

            //JOptionPane.showMessageDialog(null, "succes");
        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void Registersign() {

        String reference = referField.getText();
        String designation = desigField.getText();
        String category = CatField.getText();
        String quantity = QntField.getText();
        String buyprice = buyField.getText();
        String totalbuy = buyTotField.getText();
        String saleprice = saleField.getText();
        String totalsales = saleTotField.getText();

        if (reference.isEmpty() || designation.isEmpty()
                || category.isEmpty() || quantity.isEmpty()
                || buyprice.isEmpty() || totalbuy.isEmpty()
                || saleprice.isEmpty() || totalsales.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
            return;
        }

    }

}
