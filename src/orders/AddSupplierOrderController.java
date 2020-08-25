/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orders;

import com.jfoenix.controls.JFXDatePicker;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.dateTime;
import helpers.DbConnect;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import models.Product;
import models.Supplier;
import suppliers.SuppliersViewController;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class AddSupplierOrderController implements Initializable {

    @FXML
    private AnchorPane productPane;
    @FXML
    private TableView<Product> productsTable;
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
    private AnchorPane suppliersPane;
    @FXML
    private TableView<Supplier> suppliersTable;
    @FXML
    private TableColumn<Supplier, String> supplierIdCol;
    @FXML
    private TableColumn<Supplier, String> supplierNameCol;
    @FXML
    private TableColumn<Supplier, String> agentCol;
    @FXML
    private TableColumn<Supplier, String> adressCol;
    @FXML
    private TableColumn<Supplier, String> phoneCol;

    @FXML
    private TextField productFld;
    @FXML
    private JFXDatePicker delivDateFld;
    @FXML
    private TextField supplierFld;
    @FXML
    private TextField quantityFld;

    String query = null;
    String sql = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement statement = null;
    Product product = null;
    Supplier supplier = null;

    ObservableList<Product> productList = FXCollections.observableArrayList();
    ObservableList<Supplier> suppliersList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadProductsData();
        loadSuppliersData();
        productPane.setVisible(false);
        suppliersPane.setVisible(false);
        quantityFld.setText("" + 0);
        

        SuppliersViewController suppliersViewController = new SuppliersViewController();

    }

    @FXML
    private void getProducts(MouseEvent event) {
        productPane.setVisible(true);
        suppliersPane.setVisible(false);
    }

    @FXML
    private void getSuppliers(MouseEvent event) {
        productPane.setVisible(false);
        suppliersPane.setVisible(true);
    }

    private void loadProductsData() {
        // TODO

        connection = DbConnect.getConnect();
        refreshProductTable();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        referenceCol.setCellValueFactory(new PropertyValueFactory<>("reference"));
        desigCol.setCellValueFactory(new PropertyValueFactory<>("designation"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

    }

    private void refreshProductTable() {
        productList.clear();

        try {
            query = "select * from article";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                productList.add(new Product(
                        resultSet.getInt("IDAR"),
                        resultSet.getString("REFA"),
                        resultSet.getString("DEAR"),
                        resultSet.getInt("QSAR"),
                        resultSet.getString("SEAR"))
                );
                productsTable.setItems(productList);

            }
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            System.err.print(e);

        }
    }

    private void loadSuppliersData() {
        // TODO

        connection = DbConnect.getConnect();
        refreshSupplierTable();
        supplierIdCol.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        supplierNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        agentCol.setCellValueFactory(new PropertyValueFactory<>("agent"));
        adressCol.setCellValueFactory(new PropertyValueFactory<>("adress"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

    }

    private void refreshSupplierTable() {
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
                        resultSet.getString("TEFR")
                )
                );
                suppliersTable.setItems(suppliersList);

            }
            preparedStatement.close();
            resultSet.close();
        } catch (Exception e) {
            System.err.print(e);
        }
    }

    @FXML
    private void minus(MouseEvent event) {
        int value = Integer.parseInt(quantityFld.getText());
        if (value != 0) {

            value = value - 1;
            quantityFld.setText("" + value);

        } else {
            value = 0;
            quantityFld.setText("" + value);

        }
    }

    @FXML
    private void plus(MouseEvent event) {

        int value = Integer.parseInt(quantityFld.getText());
        value = value + 1;
        quantityFld.setText("" + value);

    }

    @FXML
    private void getProductsItem(MouseEvent event) {
        product = (Product) productsTable.getSelectionModel().getSelectedItem();
        productFld.setText(product.getDesignation());

    }

    @FXML
    private void getSuppliersItem(MouseEvent event) {
        supplier = (Supplier) suppliersTable.getSelectionModel().getSelectedItem();
        supplierFld.setText(supplier.getName());

    }

    @FXML
    private void clean() {
        product = null ;
        supplier = null;
        productFld.setText(null);
        supplierFld.setText(null);
        delivDateFld.setValue(null);
        quantityFld.setText(""+0);
        
    }

    @FXML
    private void close(MouseEvent event) {
         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void Registersign() {

        String productName = productFld.getText();
        LocalDate deliveryDate = delivDateFld.getValue();
        String quantity = quantityFld.getText();
        String supplierName = supplierFld.getText();

        if (product == null || deliveryDate == null
                || quantity.isEmpty() || supplierName == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
            return;
        } else {
            isRegister();
            clean();
        }

    }

    private void isRegister() {

        connection = DbConnect.getConnect();

        query = "INSERT INTO `commande_fr`(`DCFR`, `DALF`, `IDFO`) VALUES (?,?,?)";
        sql = "INSERT INTO `ligne_cmd_fr`(`IDAR`, `IDCF`, `QCFR`) VALUES (?,LAST_INSERT_ID(),?)";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, currentDate().toString());
            preparedStatement.setString(2, delivDateFld.getValue().toString());
            preparedStatement.setInt(3, supplier.getSupplierId());

            preparedStatement.execute();

            statement = connection.prepareStatement(sql);
            statement.setInt(1, product.getId());
            statement.setInt(2, Integer.parseInt(quantityFld.getText()));

            statement.execute();

            //JOptionPane.showMessageDialog(null, "succes");
        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public LocalDate currentDate() {

        LocalDate date = LocalDate.now();
        return date;

    }

}
