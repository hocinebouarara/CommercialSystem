/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package purchases.deliveries;

import com.jfoenix.controls.JFXDatePicker;
import helpers.DbConnect;
import java.net.URL;
import java.sql.Connection;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import models.Product;
import models.Supplier;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class AddDeliveryController implements Initializable {

    @FXML
    private TextField productName;
    @FXML
    private TextField quantity;
    @FXML
    private RadioButton cashBtn;
    @FXML
    private RadioButton retailBtn;
    @FXML
    private TextField supplierName;
    @FXML
    private TextField totalDelivery;
    @FXML
    private TextField totalProduct;
    @FXML
    private JFXDatePicker paymentDeadline;

    String payementSql = null;
    String billSql = null;
    String deliverySql = null;
    String deliveryLineSql = null;
    Connection connection = null;
    ResultSet resultSet = null;
    String mode = null;

    PreparedStatement payementStatement = null;
    PreparedStatement billStatement = null;
    PreparedStatement deliveryStatement = null;
    PreparedStatement deliveryLineStatement = null;
    private Product product = null;
    private Supplier supplier = null;
    private int productId, supplierId, orderId;
    private float price;
    private boolean update = false;
    private boolean another = false;

    ObservableList<Product> productList = FXCollections.observableArrayList();
    ObservableList<Supplier> suppliersList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ToggleGroup gp = new ToggleGroup();

        cashBtn.setToggleGroup(gp);
        retailBtn.setToggleGroup(gp);

    }

    @FXML
    private void close(MouseEvent event) {
    }

    @FXML
    private void minus(MouseEvent event) {
        int value = Integer.parseInt(quantity.getText());
        float oldTotal = Float.valueOf(totalProduct.getText());
        float price = oldTotal / value ;
        if (value != 1) {
            
            value = value - 1;
            float newTotal = price * value ;
            quantity.setText("" + value);
            totalProduct.setText(""+newTotal);
            

        } else {
            value = 1;
            quantity.setText("" + value);
        }
    }

    @FXML
    private void plus(MouseEvent event) {

        int value = Integer.parseInt(quantity.getText());
        value = value + 1;
        quantity.setText("" + value);

    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setTextFields(String productN, LocalDate date, int quntity, String supplierN, float totalP, float totalDeliv) {
        productName.setText(productN);
        paymentDeadline.setValue(date);
        quantity.setText(String.valueOf(quntity));
        supplierName.setText(supplierN);
        totalProduct.setText(String.valueOf(totalP));
        totalDelivery.setText(String.valueOf(totalDeliv));

    }

    @FXML
    private void save(MouseEvent event) {

        String product = productName.getText();
        LocalDate paymentDate = paymentDeadline.getValue();
        String Qntt = quantity.getText();
        String supplier = supplierName.getText();

        if (product == null || paymentDate == null
                || Qntt.isEmpty() || supplier == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
            return;
        } else {
            getQuery();
            clean();
            insert();

        }

    }

    private void getQuery() {

        connection = DbConnect.getConnect();
        payementSql = "INSERT INTO `payement_fr`(`DAPA`, `MOPA`, `ECHEPA`, `MTPA`) VALUES (?,?,?,?)";
        billSql = "INSERT INTO `facture_achat`(`DAFA`,  `IDFO`, `IDPA`) VALUES (?,?,LAST_INSERT_ID())";
        deliverySql = "INSERT INTO `livraision_fr`(`DABM`, `TOBL`, `IDFA`, `IDCF`) VALUES (?,?,LAST_INSERT_ID(),?)";
        deliveryLineSql = "INSERT INTO `ligne_livr_fr`(`IDAR`, `IDBM`, `QLMP`, `TOLAR`) VALUES (?,LAST_INSERT_ID(),?,?)";
    }

    public void insert() {

        try {
            payementStatement = connection.prepareStatement(payementSql);
            payementStatement.setString(1, currentDate().toString());
            payementStatement.setString(2, getPayementMode());
            payementStatement.setString(3, paymentDeadline.getValue().toString());
            payementStatement.setFloat(4, Float.valueOf(totalDelivery.getText()));
            payementStatement.execute();

            // this insertion for table bill (facture)
            billStatement = connection.prepareStatement(billSql);
            billStatement.setString(1, currentDate().toString());
            billStatement.setInt(2, supplierId);
            billStatement.execute();

            deliveryStatement = connection.prepareStatement(deliverySql);
            deliveryStatement.setString(1, currentDate().toString());
            deliveryStatement.setFloat(2, Float.valueOf(totalDelivery.getText()));
            deliveryStatement.setInt(3, orderId);
            deliveryStatement.execute();

            deliveryLineStatement = connection.prepareStatement(deliveryLineSql);
            deliveryLineStatement.setInt(1, productId);
            deliveryLineStatement.setInt(2, Integer.valueOf(quantity.getText()));
            deliveryLineStatement.setFloat(3, Float.valueOf(totalProduct.getText()));
            deliveryLineStatement.execute();

            refreshProducts();

        } catch (SQLException ex) {
            Logger.getLogger(AddDeliveryController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("This order has already been delivered");
            alert.showAndWait();
        }

    }

    private void clean() {
    }

    public LocalDate currentDate() {

        LocalDate date = LocalDate.now();
        return date;

    }

    @FXML
    private void cashMode(MouseEvent event) {
        mode = "Retail";
    }

    @FXML
    private void retailMode(MouseEvent event) {
        mode = "Cash";
    }

    public String getPayementMode() {
        return mode;
    }

    public void refreshProducts() {

        System.out.println();

        float sellPrice, buyPrice, totalSell, totaBuy;
        int oldQnt, recentQnt;
        int newQnt = Integer.valueOf(quantity.getText());

        try {
            String query = "SELECT `IDAR`, `REFA`, `DEAR`, `QSAR`,"
                    + " `SEAR`, `PRAC`, `PRVE`, `PRTA`, `PRTV` FROM `article`";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            rs.next();
            sellPrice = rs.getFloat("PRAC");
            buyPrice = rs.getFloat("PRVE");
            totalSell = rs.getFloat("PRTA");
            totaBuy = rs.getFloat("PRTV");
            oldQnt = rs.getInt("QSAR");

            recentQnt = oldQnt + newQnt;
            totalSell = sellPrice * recentQnt;
            totaBuy = buyPrice * recentQnt;

            String Sql = "UPDATE `article` "
                    + "SET `QSAR` = ?, "
                    + "`PRTA` = ?, "
                    + "`PRTV` = ? WHERE `article`.`IDAR` = '" + productId + "';";

            statement = connection.prepareStatement(Sql);
            statement.setString(1, String.valueOf(recentQnt));
            statement.setString(2, String.valueOf(totalSell));
            statement.setString(3, String.valueOf(totaBuy));

            statement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AddDeliveryController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
