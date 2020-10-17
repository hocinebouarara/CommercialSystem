package SaleDashboard.NormallySale;

import SaleDashboard.Otiles.produit;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSetImpl;
import helpers.DbConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SaleController implements Initializable {

    @FXML
    private JFXComboBox<Label> comp;

    @FXML
    JFXTextField quant;

    @FXML
    private TableColumn<?, ?> id_prod;

    @FXML
    private TableColumn<?, ?> nom_prod;

    @FXML
    private TableColumn<?, ?> prix;

    @FXML
    private TableColumn<?, ?> Quantity;


    @FXML
    private TableColumn<?, ?> price_total;

    @FXML
    private TableView<produit> table;

    @FXML
    Label Price_total;


    Connection con=null;
    PreparedStatement ps;
    ResultSetImpl rs;
    ObservableList<produit> data = FXCollections.observableArrayList();

    Double prixtotal=0.0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {

             con= (Connection) DbConnect.getConnect();
            String SQL="SELECT * FROM `article`";

            ps=(PreparedStatement) con.prepareStatement(SQL);
            rs= (ResultSetImpl) ps.executeQuery();
            while (rs.next()){

                Label l= new Label(rs.getString(3));
                l.setPrefSize(100,25);
                l.setAlignment(Pos.CENTER);
                l.setFont(Font.font("Tahoma",18));
                l.setGraphic(new ImageView("/SaleDashboard/NormallySale/4.png"));

                comp.getItems().add(l);

            }
        } catch (SQLException e) {
        } finally{
            try {
                con.close();
            } catch (Exception e) {
            }
        }

    }
    int i=1;
    @FXML
    void Add(ActionEvent event) {

        if(comp.getSelectionModel().isEmpty() || quant.getText().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setContentText("please shoose a product and quantity");
            alert.show();
        }else{
            try {

                con=(Connection) DbConnect.getConnect();
                ps= (PreparedStatement) con.prepareStatement("SELECT * FROM `article` WHERE DEAR = '"+comp.getSelectionModel().getSelectedItem().getText()+"'");
                rs= (ResultSetImpl) ps.executeQuery();
                rs.next();

                if(true){

                    float price= (float) (Double.parseDouble(quant.getText())*rs.getFloat(7));
                    int quantities=Integer.parseInt(quant.getText());
                    data.add(new produit(i,rs.getString(3),rs.getFloat(7),quantities,price));

                    id_prod.setCellValueFactory(new PropertyValueFactory <> ("id"));
                    nom_prod.setCellValueFactory(new PropertyValueFactory<>("nom_pro"));
                    prix.setCellValueFactory(new PropertyValueFactory <> ("prix_pro"));
                    Quantity.setCellValueFactory(new PropertyValueFactory <> ("Quantite"));
                    price_total.setCellValueFactory(new PropertyValueFactory<>("prix"));

                    table.setItems(data);

                    prixtotal=prixtotal+(rs.getInt(7)*Integer.parseInt(quant.getText()));
                    Price_total.setText(prixtotal+"  DA");

                    quant.setText("");
                    comp.getSelectionModel().clearSelection();
                    i++;
                }else{
                    JOptionPane.showMessageDialog(null,"Quantity not exsist");
                }
            } catch (SQLException | NumberFormatException e) {
                JOptionPane.showMessageDialog(null,e.getMessage());
            }

        }

    }

    @FXML
    void Reset(ActionEvent event) {

    }

    @FXML
    void Save(ActionEvent event) {

    }

    @FXML
    void Update(ActionEvent event) {

    }

}
