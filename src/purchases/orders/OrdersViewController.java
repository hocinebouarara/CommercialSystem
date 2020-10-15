/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package purchases.orders;

import clients.ClientsViewController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import helpers.DbConnect;
import helpers.Links;
import home.HomeViewController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import models.PurchaseOrder;
import products.ProductsViewController;
import purchases.deliveries.AddDeliveryController;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class OrdersViewController implements Initializable {

    @FXML
    private TableView<PurchaseOrder> ordersTable;
    @FXML
    private TableColumn<PurchaseOrder, String> idOrderCol;
    @FXML
    private TableColumn<PurchaseOrder, String> idProductCol;
    @FXML
    private TableColumn<PurchaseOrder, String> desigCol;
    @FXML
    private TableColumn<PurchaseOrder, String> quantityCol;
    @FXML
    private TableColumn<PurchaseOrder, String> IdSupplierCol;
    @FXML
    private TableColumn<PurchaseOrder, String> SupplierNameCol;
    @FXML
    private TableColumn<PurchaseOrder, String> OrderDateCol;
    @FXML
    private TableColumn<PurchaseOrder, String> DeliveryDateCol;
    @FXML
    private TableColumn<PurchaseOrder, String> actionCol;
    @FXML
    private TableColumn<PurchaseOrder, String> referenceCol;

    String query = null, sql = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null, statement = null;
    PurchaseOrder purchaseOrder = null;
    float sum;

    ObservableList<PurchaseOrder> purchasesOrdersList = FXCollections.observableArrayList();
    private AnchorPane AnchorPane;
    private AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadData();
    }

    private void loadData() {

        connection = DbConnect.getConnect();
        refreshTable();
        idOrderCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        idProductCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        referenceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        desigCol.setCellValueFactory(new PropertyValueFactory<>("designation"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        IdSupplierCol.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        SupplierNameCol.setCellValueFactory(new PropertyValueFactory<>("SupplierName"));
        OrderDateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        DeliveryDateCol.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        //editableCols();
        // ManageCol.setCellValueFactory(new PropertyValueFactory("update"));
        // insert btn in every row
        Callback<TableColumn<PurchaseOrder, String>, TableCell<PurchaseOrder, String>> cellFoctory = (TableColumn<PurchaseOrder, String> param) -> {

            // make the tablecell containing buttons
            final TableCell<PurchaseOrder, String> cell = new TableCell<PurchaseOrder, String>() {

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
                        FontAwesomeIconView checkIcon = new FontAwesomeIconView(FontAwesomeIcon.CHECK_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );

                        checkIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                        );

                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#0288d1;"
                        );

                        deleteIcon.setOnMouseClicked((MouseEvent mouseEvent) -> {

                            try {

                                purchaseOrder = getTableView().getItems().get(getIndex());

                                query = "DELETE FROM `ligne_cmd_fr` WHERE idcf = '" + purchaseOrder.getOrderId() + "'"
                                        + " and idar = '" + purchaseOrder.getProductId() + "'";
                                connection = DbConnect.getConnect();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(ProductsViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });

                        checkIcon.setOnMouseClicked((MouseEvent mouseEvent) -> {

                            purchaseOrder = ordersTable.getSelectionModel().getSelectedItem();

                            int orderID = purchaseOrder.getOrderId();
                            int productId = purchaseOrder.getProductId();
                            int supplierId = purchaseOrder.getSupplierId();
                            float totalProd = purchaseOrder.getPrice() * purchaseOrder.getQuantity();
                            String productName = purchaseOrder.getDesignation();
                            String supplierName = purchaseOrder.getSupplierName();

                            sql = "SELECT  SUM(TOBL) "
                                    + "FROM  \n"
                                    + "livraision_fr\n"
                                    + "WHERE\n"
                                    + "IDBM = '" + orderID + "'";

                            try {
                                preparedStatement = connection.prepareStatement(sql);
                                resultSet = preparedStatement.executeQuery();
                                resultSet.next();
                                sum = resultSet.getFloat("SUM(TOBL)");
                                System.out.println(".updateItem()" + sum);
                            } catch (SQLException ex) {
                                Logger.getLogger(OrdersViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/purchases/deliveries/addDelivery.fxml"));

                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(OrdersViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AddDeliveryController addDelivery = loader.getController();
                            addDelivery.setOrderId(orderID);
                            addDelivery.setProductId(productId);
                            addDelivery.setSupplierId(supplierId);
                            addDelivery.setTextFields(purchaseOrder.getDesignation(), purchaseOrder.getOrderDate().toLocalDate(),
                                    purchaseOrder.getQuantity(), purchaseOrder.getSupplierName(), totalProd, sum);
                            Parent p = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(p));
                            stage.initStyle(StageStyle.TRANSPARENT);
                            stage.show();
                        }
                        );

                        editIcon.setOnMouseClicked((MouseEvent mouseEvent) -> {

                            purchaseOrder = ordersTable.getSelectionModel().getSelectedItem();

                            int orderID = purchaseOrder.getOrderId();
                            int productId = purchaseOrder.getProductId();
                            int supplierId = purchaseOrder.getSupplierId();
                            String productName = purchaseOrder.getDesignation();
                            String supplierName = purchaseOrder.getSupplierName();

                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/purchases/orders/addSupplierOrder.fxml"));

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

                        });

                        HBox managebtn = new HBox(editIcon, checkIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        HBox.setMargin(checkIcon, new Insets(2, 2, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }

                }

            };

            return cell;

        };
        actionCol.setCellFactory(cellFoctory);
        ordersTable.setItems(purchasesOrdersList);
    }

    @FXML
    private void addMembers(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/purchases/orders/addSupplierOrder.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource(Links.VIEWSTYLE).toExternalForm());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            Logger.getLogger(ClientsViewController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void refreshTable() {
        purchasesOrdersList.clear();

        try {
            query = "SELECT ligne_cmd_fr.IDCF,ligne_cmd_fr.IDAR,article.PRAC,article.DEAR,ligne_cmd_fr.QCFR,\n"
                    + "\n"
                    + "fournisseur.IDFO,fournisseur.NOFR,commande_fr.DCFR,commande_fr.DALF\n"
                    + "  \n"
                    + "FROM\n"
                    + "  ligne_cmd_fr\n"
                    + "   INNER JOIN commande_fr ON commande_fr.IDCF = ligne_cmd_fr.IDCF\n"
                    + "   INNER JOIN article ON article.IDAR = ligne_cmd_fr.IDAR \n"
                    + "   INNER JOIN fournisseur on fournisseur.IDFO = commande_fr.IDFO\n"
                    + "   \n"
                    + "ORDER BY commande_fr.IDCF";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                purchasesOrdersList.add(new PurchaseOrder(
                        resultSet.getInt("ligne_cmd_fr.IDCF"),
                        resultSet.getInt("ligne_cmd_fr.IDAR"),
                        resultSet.getFloat("article.PRAC"),
                        resultSet.getString("article.DEAR"),
                        resultSet.getInt("ligne_cmd_fr.QCFR"),
                        resultSet.getInt("fournisseur.IDFO"),
                        resultSet.getString("fournisseur.NOFR"),
                        resultSet.getDate("commande_fr.DCFR"),
                        resultSet.getDate("commande_fr.DALF")
                )
                );
                ordersTable.setItems(purchasesOrdersList);

            }
            preparedStatement.close();
            resultSet.close();
        } catch (Exception e) {
            System.err.print(e);
        }
    }

    public LocalDate currentDate() {

        LocalDate date = LocalDate.now();
        return date;

    }

}
