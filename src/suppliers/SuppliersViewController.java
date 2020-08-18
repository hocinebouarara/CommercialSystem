/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suppliers;

import clients.ClientsViewController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import helpers.DbConnect;
import helpers.Links;
import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.Client;
import models.Supplier;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class SuppliersViewController implements Initializable {

    @FXML
    private TableView<Supplier> supplierTable;
    @FXML
    private TableColumn<Supplier, String> idCol;
    @FXML
    private TableColumn<Supplier, String> nameCol;
    @FXML
    private TableColumn<Supplier, String> agentCol;
    @FXML
    private TableColumn<Supplier, String> adressCol;
    @FXML
    private TableColumn<Supplier, String> CityCol;
    @FXML
    private TableColumn<Supplier, String> phoneCol;
    @FXML
    private TableColumn<Supplier, String> faxCol;
    @FXML
    private TableColumn<Supplier, String> CnssCol;
    @FXML
    private TableColumn<Supplier, String> operationCol;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    Supplier supplier = null;

    ObservableList<Supplier> suppliersList = FXCollections.observableArrayList();
    @FXML
    private Pane editPane;
    @FXML
    private Pane statPane;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
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
        agentCol.setCellValueFactory(new PropertyValueFactory<>("agent"));
        adressCol.setCellValueFactory(new PropertyValueFactory<>("adress"));
        CityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        faxCol.setCellValueFactory(new PropertyValueFactory<>("fax"));
        CnssCol.setCellValueFactory(new PropertyValueFactory<>("cnss"));

        //editableCols();
        // ManageCol.setCellValueFactory(new PropertyValueFactory("update"));
        // insert btn in every row
        Callback<TableColumn<Supplier, String>, TableCell<Supplier, String>> cellFoctory = (TableColumn<Supplier, String> param) -> {

            // make the tablecell containing buttons
            final TableCell<Supplier, String> cell = new TableCell<Supplier, String>() {

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

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                        );

                        deleteIcon.setOnMouseClicked((MouseEvent mouseEvent) -> {
                            
                             try {

                                supplier = supplierTable.getSelectionModel().getSelectedItem();
                                connection = DbConnect.getConnect();
                                query = "delete from fournisseur where idfo =" + supplier.getId();
                                preparedStatement = connection.prepareCall(query);
                                preparedStatement.execute();
                                refreshTable();
                                supplier = null;

                            } catch (SQLException ex) {
                                Logger.getLogger(ClientsViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });

                        editIcon.setOnMouseClicked((MouseEvent mouseEvent) -> {

                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }

                }

            };

            return cell;

        };
        operationCol.setCellFactory(cellFoctory);
        supplierTable.setItems(suppliersList);
    }

    @FXML
    private void addMembers(MouseEvent event) {
         try {
            Parent root = FXMLLoader.load(getClass().getResource("/suppliers/addSupplier.fxml"));
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

    @FXML
    private void refreshTable() {
        suppliersList.clear();

        try {
            query = "SELECT * FROM `fournisseur`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                suppliersList.add(new Supplier(
                        resultSet.getInt("IDFO"),
                        resultSet.getString("NOFR"),
                        resultSet.getString("NORE"),
                        resultSet.getString("ADFR"),
                        resultSet.getString("VIFR"),
                        resultSet.getString("TEFR"),
                        resultSet.getString("FAFR"),
                        resultSet.getString("CNSS")
                )
                );
                supplierTable.setItems(suppliersList);

            }
            preparedStatement.close();
            resultSet.close();
        } catch (Exception e) {
            System.err.print(e);
        }
    }

    @FXML
    private void editPane(MouseEvent event) {
    }

    @FXML
    private void statisticsPane(MouseEvent event) {
    }

}
