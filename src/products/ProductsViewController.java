/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products;

import clients.ClientsViewController;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import models.Client;
import models.Product;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class ProductsViewController implements Initializable {

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> idCol;
    @FXML
    private TableColumn<Product, String> referenceCol;
    @FXML
    private TableColumn<Product, String> desigCol;
    @FXML
    private TableColumn<Product, String> categoryCol;
    @FXML
    private TableColumn<Product, String> quantityCol;
    @FXML
    private TableColumn<Product, String> buyCol;
    @FXML
    private TableColumn<Product, String> saleCol;
    @FXML
    private TableColumn<Product, String> buyTotCol;
    @FXML
    private TableColumn<Product, String> saleTotCol;
    @FXML
    private TableColumn<Product, String> actionCol;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    Client Product = null;

    ObservableList<Product> productList = FXCollections.observableArrayList();
    @FXML
    private Pane editPane;
    @FXML
    private Pane statPane;

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
        referenceCol.setCellValueFactory(new PropertyValueFactory<>("reference"));
        desigCol.setCellValueFactory(new PropertyValueFactory<>("designation"));
        saleTotCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("buyingPrice"));
        buyCol.setCellValueFactory(new PropertyValueFactory<>("salePrice"));
        saleCol.setCellValueFactory(new PropertyValueFactory<>("totalBuy"));
        buyTotCol.setCellValueFactory(new PropertyValueFactory<>("totalSale"));

        //editableCols();
        // ManageCol.setCellValueFactory(new PropertyValueFactory("update"));
        // insert btn in every row
        Callback<TableColumn<Product, String>, TableCell<Product, String>> cellFoctory = (TableColumn<Product, String> param) -> {

            // make the tablecell containing buttons
            final TableCell<Product, String> cell = new TableCell<Product, String>() {

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
                                + "-glyph-size:22px"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:22px"
                        );

                        deleteIcon.setOnMouseClicked((MouseEvent mouseEvent) -> {

                        });

                        editIcon.setOnMouseClicked((MouseEvent mouseEvent) -> {

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
        actionCol.setCellFactory(cellFoctory);
        productTable.setItems(productList);
    }
    @FXML
    private void refreshTable() {
        productList.clear();

        try {
            query = "select * from article";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                productList.add(new Product(
                        resultSet.getInt("IDAR"),
                        resultSet.getString("REFA"), resultSet.getString("DEAR"),
                        resultSet.getInt("QSAR"), resultSet.getString("SEAR"), resultSet.getFloat("PRAC"),
                        resultSet.getFloat("PRVE"), resultSet.getFloat("PRTA"), resultSet.getFloat("PRTV"))
                );
                productTable.setItems(productList);

            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            System.err.print(e);
            
        }
        
    }

    @FXML
    private void editPane(MouseEvent event) {
    }

    @FXML
    private void statisticsPane(MouseEvent event) {
    }

    @FXML
    private void addMembers(MouseEvent event) {
    }


}
