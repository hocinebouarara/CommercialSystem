/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import models.Client;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class ClientsViewController implements Initializable {

    @FXML
    private Pane editPane;
    @FXML
    private Pane statPane;
    @FXML
    private TableView<Client> tableView;
    @FXML
    private TableColumn<Client, String> idCol;
    @FXML
    private TableColumn<Client, String> nameCol;
    @FXML
    private TableColumn<Client, String> adressCol;
    @FXML
    private TableColumn<Client, String> CityCol;
    @FXML
    private TableColumn<Client, String> phoneCol;
    @FXML
    private TableColumn<Client, String> faxCol;
    @FXML
    private TableColumn<Client, String> agentCol;
    @FXML
    private TableColumn<Client, String> operationCol;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    
    ObservableList<Client> cliensList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadData();
    }

    private void loadData() {
        // TODO
        
        connection = DbConnect.getConnect();
        refreshTable();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        adressCol.setCellValueFactory(new PropertyValueFactory<>("adress"));
        CityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        faxCol.setCellValueFactory(new PropertyValueFactory<>("fax"));
        agentCol.setCellValueFactory(new PropertyValueFactory<>("agent"));
        //editableCols();
        // ManageCol.setCellValueFactory(new PropertyValueFactory("update"));
        // insert btn in every row
        Callback<TableColumn<Client, String>, TableCell<Client, String>> cellFoctory = (TableColumn<Client, String> param) -> {
            
            // make the tablecell containing buttons
            final TableCell<Client, String> cell = new TableCell<Client, String>() {
                
                // Override updateItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    
                    super.updateItem(item, empty);
                    // ensure that cell is created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        
                        final JFXButton editButton = new JFXButton("update");
                        
                        editButton.setStyle("-fx-background-color:#2196F3;"
                                + "-fx-background-radius:4px;"
                                + "-fx-font-size: 8px; "
                                + "-fx-text-fill: #ffffff;"
                        );
                        
                        final Button deleteButton = new Button("delete");
                        
                        deleteButton.setStyle("-fx-background-color:#f44336;"
                                + "-fx-background-radius:4px;"
                                + "-fx-font-size: 8px; "
                                + "-fx-text-fill: #ffffff;"
                        );
                        
                        tableView.setStyle("-fx-alignment: CENTER-RIGHT;");
                        HBox managebtn = new HBox(editButton, deleteButton);
                        HBox.setMargin(deleteButton, new Insets(5, 8, 5, 3));
                        HBox.setMargin(editButton, new Insets(5, 3, 5, 10));
                        
                        editButton.setOnAction(event -> {
                            //getSelected();
                            
                        });
                        
                        setGraphic(managebtn);
                        
                        setText(null);
                        
                    }
                    
                }
                
            };
            
            return cell;
            
        };
        operationCol.setCellFactory(cellFoctory);
        tableView.setItems(cliensList);
    }

    @FXML
    private void editPane(MouseEvent event) {
        statPane.setStyle("-fx-background-color: transparent");
        editPane.setStyle("-fx-background-color: #651FFF");

    }

    @FXML
    private void statisticsPane(MouseEvent event) {
        editPane.setStyle("-fx-background-color: transparent");
        statPane.setStyle("-fx-background-color: #651FFF");
    }

    private void refreshTable() {
        cliensList.clear();

        try {
            query = "select * from client";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cliensList.add(new Client(
                        resultSet.getInt("IDCL"), 
                        resultSet.getString("NOCL"),
                        resultSet.getString("ADCL"), 
                        resultSet.getString("VICL"), 
                        resultSet.getString("TECL"), 
                        resultSet.getString("FACL"), 
                        resultSet.getString("NORECL")
                )
                );
                tableView.setItems(cliensList);

            }
            preparedStatement.close();
            resultSet.close();
        } catch (Exception e) {
            System.err.print(e);
        }
    }

}
