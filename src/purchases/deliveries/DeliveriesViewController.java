/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package purchases.deliveries;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.PurchaseDelivery;
import models.PurchaseOrder;
import products.ProductsViewController;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class DeliveriesViewController implements Initializable {

    @FXML
    private AnchorPane deliveriesAnchor;
    @FXML
    private TableView<PurchaseDelivery> ordersTable;
    @FXML
    private TableColumn<PurchaseDelivery, String> idOrderCol;
    @FXML
    private TableColumn<PurchaseDelivery, String> idProductCol;
    @FXML
    private TableColumn<PurchaseDelivery, String> IdSupplierCol;
    @FXML
    private TableColumn<PurchaseDelivery, String> SupplierNameCol;
    @FXML
    private TableColumn<PurchaseDelivery, String> DeliveryDateCol;
    @FXML
    private TableColumn<PurchaseDelivery, String> actionCol;
    @FXML
    private TableColumn<PurchaseDelivery, String> desigCol;
    @FXML
    private TableColumn<PurchaseDelivery, String> quantityCol;
    @FXML
    private TableColumn<PurchaseDelivery, String> idDelivCol;
    @FXML
    private TableColumn<PurchaseDelivery, String> priceCol;
    @FXML
    private TableColumn<PurchaseDelivery, String> totalCol;

    String query = null, sql = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    PurchaseDelivery purchaseDelivery = null;

    ObservableList<PurchaseDelivery> purchaseDeliveryList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadData();
    }

    @FXML
    private void addMembers(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/purchases/deliveries/addDelivery.fxml"));
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

        purchaseDeliveryList.clear();

        try {
            query = "SELECT * "
                    + "FROM\n"
                    + "ligne_livr_fr "
                    + "\n"
                    + "INNER JOIN livraision_fr on livraision_fr.IDBM = ligne_livr_fr.IDBM   \n"
                    + "INNER JOIN article ON article.IDAR = ligne_livr_fr.IDAR\n"
                    + "INNER JOIN commande_fr on commande_fr.IDCF = livraision_fr.IDCF\n"
                    + "INNER JOIN fournisseur on fournisseur.IDFO = commande_fr.IDFO\n"
                    + "\n"
                    + "ORDER BY livraision_fr.IDBM";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                purchaseDeliveryList.add(new PurchaseDelivery(
                        resultSet.getInt("ligne_livr_fr.IDBM"),
                        resultSet.getInt("ligne_livr_fr.IDAR"),
                        resultSet.getInt("commande_fr.IDCF"),
                        resultSet.getString("article.DEAR"),
                        resultSet.getFloat("article.PRAC"),
                        resultSet.getInt("ligne_livr_fr.QLMP"),
                        resultSet.getFloat("ligne_livr_fr.TOLAR"),
                        resultSet.getInt("fournisseur.IDFO"),
                        resultSet.getString("fournisseur.NOFR"),
                        resultSet.getDate("livraision_fr.DABM")
                )
                );
                ordersTable.setItems(purchaseDeliveryList);

            }
            preparedStatement.close();
            resultSet.close();
        } catch (Exception e) {
            System.err.print(e);
        }
    }

    private void loadData() {

        connection = DbConnect.getConnect();
        refreshTable();

        idDelivCol.setCellValueFactory(new PropertyValueFactory<>("col1"));
        idOrderCol.setCellValueFactory(new PropertyValueFactory<>("col2"));
        idProductCol.setCellValueFactory(new PropertyValueFactory<>("col3"));
        desigCol.setCellValueFactory(new PropertyValueFactory<>("col4"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("col5"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("col6"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("col7"));
        IdSupplierCol.setCellValueFactory(new PropertyValueFactory<>("col8"));
        SupplierNameCol.setCellValueFactory(new PropertyValueFactory<>("col9"));
        DeliveryDateCol.setCellValueFactory(new PropertyValueFactory<>("col10"));

        Callback<TableColumn<PurchaseDelivery, String>, TableCell<PurchaseDelivery, String>> cellFoctory = (TableColumn<PurchaseDelivery, String> param) -> {

            // make the tablecell containing buttons
            final TableCell<PurchaseDelivery, String> cell = new TableCell<PurchaseDelivery, String>() {

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

                                purchaseDelivery = getTableView().getItems().get(getIndex());

                                query = "DELETE FROM `livraision_fr` WHERE idbm = '" + purchaseDelivery.getCol1() + "'";
                                connection = DbConnect.getConnect();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(ProductsViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });

                        editIcon.setOnMouseClicked((MouseEvent mouseEvent) -> {
                            /*
                            purchaseOrder = ordersTable.getSelectionModel().getSelectedItem();

                            int orderID = purchaseOrder.getOrderId();
                            int productId = purchaseOrder.getProductId();
                            int supplierId = purchaseOrder.getSupplierId();
                            String productName = purchaseOrder.getDesignation();
                            String supplierName = purchaseOrder.getSupplierName();

                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/orders/addSupplierOrder.fxml"));

                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(OrdersViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AddSupplierOrderController addSupplierOrder = loader.getController();
                            addSupplierOrder.setProductId(productId);
                            addSupplierOrder.setSupplierId(supplierId);
                            addSupplierOrder.setOrderId(orderID);
                            addSupplierOrder.setUpdate(true);
                            addSupplierOrder.setTextFields(purchaseOrder.getDesignation(), purchaseOrder.getDeliveryDate().toLocalDate(), purchaseOrder.getQuantity(), purchaseOrder.getSupplierName());
                            Parent p = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(p));
                            stage.initStyle(StageStyle.TRANSPARENT);
                            stage.show();
                             */

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
        actionCol.setCellFactory(cellFoctory);
        ordersTable.setItems(purchaseDeliveryList);

    }

}
