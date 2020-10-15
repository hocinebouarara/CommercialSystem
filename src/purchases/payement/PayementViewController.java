/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package purchases.payement;

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
import models.Client;
import models.Payement;
import models.PurchaseOrder;
import products.ProductsViewController;
import purchases.deliveries.AddDeliveryController;
import purchases.orders.AddSupplierOrderController;
import purchases.orders.OrdersViewController;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class PayementViewController implements Initializable {

    @FXML
    private AnchorPane deliveriesAnchor;
    @FXML
    private TableView<Payement> payementTable;
    @FXML
    private TableColumn<Payement, String> idProductCol;
    @FXML
    private TableColumn<Payement, String> desigCol;
    @FXML
    private TableColumn<Payement, String> priceCol;
    @FXML
    private TableColumn<Payement, String> quantityCol;
    @FXML
    private TableColumn<Payement, String> totalCol;
    @FXML
    private TableColumn<Payement, String> SupplierNameCol;
    @FXML
    private TableColumn<Payement, String> DeliveryDateCol;
    @FXML
    private TableColumn<Payement, String> actionCol;
    @FXML
    private TableColumn<Payement, String> idBillCol;
    @FXML
    private TableColumn<Payement, String> IdPayementCol;
    @FXML
    private TableColumn<Payement, String> idDeliveryCol;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    Payement payement = null;

    ObservableList<Payement> payementList = FXCollections.observableArrayList();

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
    }

    private void loadData() {

        connection = DbConnect.getConnect();
        refreshTable();
        idBillCol.setCellValueFactory(new PropertyValueFactory<>("billId"));
        IdPayementCol.setCellValueFactory(new PropertyValueFactory<>("payementId"));
        idDeliveryCol.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        idProductCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        desigCol.setCellValueFactory(new PropertyValueFactory<>("desig"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("total"));
        SupplierNameCol.setCellValueFactory(new PropertyValueFactory<>("SupplierName"));
        DeliveryDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        //editableCols();
        // ManageCol.setCellValueFactory(new PropertyValueFactory("update"));
        // insert btn in every row
        Callback<TableColumn<Payement, String>, TableCell<Payement, String>> cellFoctory = (TableColumn<Payement, String> param) -> {

            // make the tablecell containing buttons
            final TableCell<Payement, String> cell = new TableCell<Payement, String>() {

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
                       FontAwesomeIconView printIcon = new FontAwesomeIconView(FontAwesomeIcon.PRINT);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );

                        printIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#0288d1;"
                        );

                        deleteIcon.setOnMouseClicked((MouseEvent mouseEvent) -> {

                            try {

                                payement = getTableView().getItems().get(getIndex());

                                query = "DELETE FROM `facture_achat` WHERE IDFA = '" + payement.getBillId() + "'"
                                        + " and IDPA = '" + payement.getPayementId() + "'";
                                connection = DbConnect.getConnect();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(ProductsViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });

                        printIcon.setOnMouseClicked((MouseEvent mouseEvent) -> {

                            payement = payementTable.getSelectionModel().getSelectedItem();

                            int payementId = payement.getPayementId();
                            int billId = payement.getBillId();
                            int productId = payement.getProductId();
                            int deliveryId = payement.getDeliveryId();
                            String productName = payement.getDesig();
                            String supplierName = payement.getSupplierName();

                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/purchases/orders/addSupplierOrder.fxml"));

                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(OrdersViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            /*
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
                            stage.show();*/

                        });

                        HBox managebtn = new HBox(printIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(printIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }

                }

            };

            return cell;

        };
        actionCol.setCellFactory(cellFoctory);
        payementTable.setItems(payementList);
    }

    @FXML
    private void refreshTable() {

        payementList.clear();

        try {
            query = "SELECT facture_achat.IDFA,payement_fr.IDPA,livraision_fr.IDBM,article.IDAR,\n"
                    + "article.DEAR,article.PRAC,ligne_livr_fr.QLMP,ligne_livr_fr.TOLAR,                  \n"
                    + "fournisseur.NOFR,payement_fr.DAPA\n"
                    + "\n"
                    + "FROM\n"
                    + "ligne_livr_fr\n"
                    + "INNER JOIN livraision_fr ON livraision_fr.IDBM = ligne_livr_fr.IDBM\n"
                    + "INNER JOIN article ON article.IDAR = ligne_livr_fr.IDAR \n"
                    + "INNER JOIN facture_achat on facture_achat.IDFA = livraision_fr.IDFA\n"
                    + "INNER JOIN payement_fr on facture_achat.IDPA = payement_fr.IDPA\n"
                    + "INNER JOIN fournisseur on fournisseur.IDFO = facture_achat.IDFO\n"
                    + "ORDER BY facture_achat.IDFA  ";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                payementList.add(new Payement(
                        resultSet.getInt("facture_achat.IDFA"),
                        resultSet.getInt("payement_fr.IDPA"),
                        resultSet.getInt("livraision_fr.IDBM"),
                        resultSet.getInt("article.IDAR"),
                        resultSet.getString("article.DEAR"),
                        resultSet.getFloat("article.PRAC"),
                        resultSet.getInt("ligne_livr_fr.QLMP"),
                        resultSet.getFloat("ligne_livr_fr.TOLAR"),
                        resultSet.getString("fournisseur.NOFR"),
                        resultSet.getDate("payement_fr.DAPA")
                )
                );
                payementTable.setItems(payementList);

            }
            preparedStatement.close();
            resultSet.close();
        } catch (Exception e) {
            System.err.print(e);
        }

    }

}
