package SaleDashboard.NormallySale;

import JavaFXViewer.Classes.JRViewerFx;
import JavaFXViewer.Classes.JRViewerFxMode;
import SaleDashboard.Otiles.Convert_date;
import SaleDashboard.Otiles.CurrentDate;
import SaleDashboard.Otiles.ExecuteQuery;
import SaleDashboard.Otiles.produit;
import clients.AddClientController;
import clients.ClientsViewController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetImpl;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import helpers.DbConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.Client;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import products.ProductsViewController;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaleController implements Initializable {

    @FXML
    private JFXComboBox<Label> comp;

    @FXML
    private JFXDatePicker DARG;

    @FXML
    private JFXDatePicker DeliveryDate;

    @FXML
    JFXTextField quant;

    @FXML
    private TableColumn<produit, Integer> id_prod;

    @FXML
    private TableColumn<produit, String> nom_prod;

    @FXML
    private TableColumn<produit, Float> prix;

    @FXML
    private TableColumn<produit, Integer> Quantity;


    @FXML
    private TableColumn<?, ?> price_total;

    @FXML
    private TableColumn<?,?> Action;

    @FXML
    private TableView<produit> table;

    @FXML
    private JFXComboBox<Label> ClientCombobox;

    @FXML
    private JFXButton AddNewClient;

    @FXML
    Label Price_total;


    Connection con = null;
    PreparedStatement ps;
    ResultSetImpl rs;
    ObservableList<produit> data = FXCollections.observableArrayList();

    Double prixtotal = 0.0;
    float price;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

            LoadProductItems();
            LoadClient();

        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }

    }

    int i = 1;

    @FXML
    void Add(ActionEvent event) {

        if (comp.getSelectionModel().isEmpty() || quant.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("please shoose a product and quantity");
            alert.show();
        } else {
            try {

                con = (Connection) DbConnect.getConnect();

                System.out.print(comp.getSelectionModel().getSelectedItem().getText());
                ps = (PreparedStatement) con.prepareStatement("SELECT * FROM `article` WHERE DEAR = '" + comp.getSelectionModel().getSelectedItem().getText() + "'");
                rs = (ResultSetImpl) ps.executeQuery();

                rs.next();

                if (true) {

                    price = (float) (Double.parseDouble(quant.getText()) * rs.getFloat(7));
                    int quantities = Integer.parseInt(quant.getText());
                    data.add(new produit(
                            i,
                            rs.getString(3),
                            rs.getFloat(7),
                            quantities,
                            price));

                    for (produit p:data){
                        p.getIconView().setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:red;"
                        );
                        p.getIconView().setOnMouseClicked((MouseEvent mouseevent)->{
                            data.remove(p);
                        });
                    }

                    id_prod.setCellValueFactory(new PropertyValueFactory<>("id"));
                    nom_prod.setCellValueFactory(new PropertyValueFactory<>("nom_pro"));
                    prix.setCellValueFactory(new PropertyValueFactory<>("prix_pro"));
                    Quantity.setCellValueFactory(new PropertyValueFactory<>("Q"));
                    price_total.setCellValueFactory(new PropertyValueFactory<>("prix"));
                    Action.setCellValueFactory(new PropertyValueFactory<>("iconView"));

                    table.setItems(data);

                    prixtotal = prixtotal + (rs.getInt(7) * Integer.parseInt(quant.getText()));
                    Price_total.setText(prixtotal + "  DA");

                    i++;
                    quant.setText("");
                    comp.getSelectionModel().clearSelection();

                } else {
                    JOptionPane.showMessageDialog(null, "Quantity not exsist");
                }
            } catch (SQLException | NumberFormatException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }

        }

    }


    @FXML
    void Reset(ActionEvent event) {
        data.clear();
        ClientCombobox.setSelectionModel(null);
        comp.setSelectionModel(null);
    }

    @FXML
    void Save(ActionEvent event) {

        InsertCommend();
        InsertLigneCmdCl();
        InsertReglementCl();
        InsertFactureVentCl();
        InsertLivraisionCl();
        InsertLivraisionCl();

        JasperPrint jasperPrint = null;
        try {
            com.mysql.jdbc.Connection con = (com.mysql.jdbc.Connection) DbConnect.getConnect();
            com.mysql.jdbc.PreparedStatement ps;
            jasperPrint = JasperFillManager.fillReport("src/SaleDashboard/NormallySale/Facture/report.jasper", new HashMap<>(), con);
            Stage stage = new Stage();
            new JRViewerFx(jasperPrint, JRViewerFxMode.REPORT_VIEW, stage);
        } catch (JRException e) {
            // TODO Auto-generated catch blo
            JOptionPane.showMessageDialog(null,e.getMessage());
        }

    }

    @FXML
    void Update(ActionEvent event) {

    }

    private void addProduct(MouseEvent event) {
        
           /* String SQLCL = "SELECT * FROM `client` where id='" + Integer.parseInt(String.valueOf(ClientCombobox.getSelectionModel().getSelectedItem().getText().charAt(1))) + "'";

            ps = (PreparedStatement) con.prepareStatement(SQLCL);
            rs = (ResultSetImpl) ps.executeQuery();
            rs.next();
            Client client = new Client(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7));

            int billId;

            PreparedStatement preparedStatement;
            ResultSetImpl resultSet;
            Connection connection;

            connection = (Connection) DbConnect.getConnect();

            String sqlClient = "INSERT INTO `client`(`NOCL`, `ADCL`, `VICL`, `TECL`, `FACL`, `NORECL`) VALUES (?,?,?,?,?,?)";
            preparedStatement = (PreparedStatement) connection.prepareStatement(sqlClient);
            preparedStatement.setString(1, client.getNOCL());
            preparedStatement.setString(2, client.getADCL());
            preparedStatement.setString(3, client.getVICL());
            preparedStatement.setString(4, client.getTECL());
            preparedStatement.setString(5, client.getFACL());
            preparedStatement.setString(6, client.getNORECL());
            preparedStatement.execute();

          */

    }

    public void LoadProductItems() {
        try {
            con = (Connection) DbConnect.getConnect();
            String SQLAR = "SELECT * FROM `article`";

            ps = (PreparedStatement) con.prepareStatement(SQLAR);
            rs = (ResultSetImpl) ps.executeQuery();
            while (rs.next()) {

                Label l = new Label(rs.getString(3));
                l.setPrefSize(100, 25);
                l.setAlignment(Pos.CENTER_LEFT);
                l.setFont(Font.font("Tahoma", 18));
                l.setGraphic(new ImageView("/SaleDashboard/NormallySale/4.png"));

                comp.getItems().add(l);

            }
        } catch (SQLException e) {

        }
    }
    public void LoadClient(){
        try{

            String SQLCL = "SELECT * FROM `client`";

            ps = (PreparedStatement) con.prepareStatement(SQLCL);
            rs = (ResultSetImpl) ps.executeQuery();
            while (rs.next()) {

                Label l = new Label(rs.getInt(1) + " - " + rs.getString(2));
                l.setPrefSize(300, 25);
                l.setAlignment(Pos.CENTER_LEFT);
                l.setFont(Font.font("Tahoma", 18));
                l.setGraphic(new ImageView("/SaleDashboard/NormallySale/4.png"));

                ClientCombobox.getItems().add(l);

            }
        }catch (SQLException e){

        }
    }
    public void InsertCommend(){
        try{
            PreparedStatement preparedStatement;
            Connection connection=(Connection)DbConnect.getConnect();
            String sqlOrder = "INSERT INTO `commande_cl`(`DCAR`, `DALR`, `IDCL`, `IDEM`) VALUES (?,?,?,?)";
            preparedStatement = (PreparedStatement) connection.prepareStatement(sqlOrder);
            preparedStatement.setDate(1, Convert_date.CovertToSqlDate(CurrentDate.CurrentDate()));
            preparedStatement.setDate(2, Convert_date.CovertToSqlDate(CurrentDate.CurrentDate()));
            preparedStatement.setInt(3,Integer.parseInt(ClientCombobox.getSelectionModel().getSelectedItem().getText().charAt(0)+""));
            preparedStatement.setInt(4, 1);
            preparedStatement.executeUpdate();

        }catch(SQLException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
    public void InsertLigneCmdCl(){
        try{
            for (produit produit : data) {
                PreparedStatement preparedStatement;
                Connection connection=(Connection)DbConnect.getConnect();
                String sqlOrder = "INSERT INTO `ligne_cmd_cl`(`IDAR`, `IDCA`, `QCDA`) VALUES (?,?,?)";
                preparedStatement = (PreparedStatement) connection.prepareStatement(sqlOrder);
                preparedStatement.setInt(1,produit.getId());
                preparedStatement.setInt(2,new ExecuteQuery().GetLastIdCommend());
                preparedStatement.setInt(3,produit.getQ());
                preparedStatement.execute();

            }
        }catch(SQLException e){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.show();

        }
    }
    public void InsertReglementCl(){
        try{

                PreparedStatement preparedStatement;
                Connection connection=(Connection)DbConnect.getConnect();
                String sqlOrder = "INSERT INTO `reglement_cl`(`DARG`, `MORG`, `ECHERG`, `MTRG`) VALUES (?,?,?,?)";
                preparedStatement = (PreparedStatement) connection.prepareStatement(sqlOrder);
                preparedStatement.setDate(1,Convert_date.CovertToSqlDate(Convert_date.Convert(DARG.getValue())));
                preparedStatement.setString(2,"cash");
                preparedStatement.setDate(3,Convert_date.CovertToSqlDate(Convert_date.Convert(DARG.getValue())));
                preparedStatement.setFloat(4,price);
                preparedStatement.execute();


        }catch(SQLException e){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.show();

        }
    }
    public void InsertFactureVentCl(){
        try{
            int Idclient=Integer.parseInt(ClientCombobox.getSelectionModel().getSelectedItem().getText().charAt(0)+"");
            PreparedStatement preparedStatement;
            Connection connection=(Connection)DbConnect.getConnect();
            String sqlOrder = "INSERT INTO `facture_vente`(`MOHT_V`,`IDRG`, `IDCL`) VALUES (?,?,?)";
            preparedStatement = (PreparedStatement) connection.prepareStatement(sqlOrder);
            preparedStatement.setFloat(1,price);
            preparedStatement.setInt(2,ExecuteQuery.GetLastIdReglement());
            preparedStatement.setInt(3,Idclient);
            preparedStatement.execute();

        }catch(SQLException e){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.show();

        }
    }
    public void InsertLivraisionCl(){
        try{

            PreparedStatement preparedStatement;
            Connection connection=(Connection)DbConnect.getConnect();
            String sqlOrder = "INSERT INTO `livraision_cl`(`DABA`, `TOBA`, `IDCA`, `IDFV`) VALUES (?,?,?,?)";
            preparedStatement = (PreparedStatement) connection.prepareStatement(sqlOrder);
            preparedStatement.setDate(1, Convert_date.CovertToSqlDate(Convert_date.Convert(DeliveryDate.getValue())));
            preparedStatement.setFloat(2,price);
            preparedStatement.setInt(3,ExecuteQuery.GetLastIdCommend());
            preparedStatement.setInt(4,ExecuteQuery.GetLastIdFactureVent());
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();

        }catch(SQLException e){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.show();

        }
    }
    public void InsertLigneLivrCl(){
        try{
            for (produit produit : data) {
                PreparedStatement preparedStatement;
                Connection connection=(Connection)DbConnect.getConnect();
                String sqlOrder = "INSERT INTO `ligne_livr_cl`(`IDAR`, `IDBA`,`QLAR`,`TOQL`) VALUES (?,?,?,?)";
                preparedStatement = (PreparedStatement) connection.prepareStatement(sqlOrder);
                preparedStatement.setInt(1,produit.getId());
                preparedStatement.setInt(2,ExecuteQuery.GetLastIdLivraision());
                preparedStatement.setInt(3,produit.getQ());
                preparedStatement.setFloat(3,price);
                preparedStatement.execute();

            }
        }catch(SQLException e){

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(e.getMessage());
            alert.show();

        }
    }
}
