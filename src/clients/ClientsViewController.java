/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clients;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import helpers.DbConnect;
import helpers.Links;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        Icon dicon = new ImageIcon("C:\\Users\\hocin\\Documents\\NetBeansProjects\\commercialsysetm\\src\\image\\icons8-trash-48.png");
                        Icon eicon = new ImageIcon("give path of the image");

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                  + "-glyph-size:22px"
                                
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:22px"
                                
                        );

                        deleteIcon.setOnMouseClicked((MouseEvent mouseEvent) -> {
                            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                               
                            }
                            refreshTable();
                        });

                        editIcon.setOnMouseClicked((MouseEvent mouseEvent) -> {
                            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                                if (mouseEvent.getClickCount() == 1) {
                                    System.out.println("edit btn clicked");
                                    
                                }
                            }
                           
                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(1, 0, 0, 5));
                        HBox.setMargin(editIcon, new Insets(8, 0, 5, 0));

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

    @FXML
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

    @FXML
    private void addMembers(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/clients/addClient.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(Links.VIEWSTYLE).toExternalForm());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(ClientsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
