/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beneficiaries;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import helpres.DbConnect;
import helpres.Links;
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
import models.Beneficiaire;
import models.Proprietor;
import proprietors.AddProprietorController;
import proprietors.ProprietorsViewController;

/**
 * FXML Controller class
 *
 * @author Deeplight
 */
public class BeneficiairesTableController implements Initializable {

    @FXML
    private TableView<Beneficiaire> beneficiairesTable;
    @FXML
    private TableColumn<Beneficiaire, String> idCol;
    @FXML
    private TableColumn<Beneficiaire, String> nameCol;
    @FXML
    private TableColumn<Beneficiaire, String> dateCol;
    @FXML
    private TableColumn<Beneficiaire, String> wilayaCol;
    @FXML
    private TableColumn<Beneficiaire, String> communeCol;
    @FXML
    private TableColumn<Beneficiaire, String> natCol;
    @FXML
    private TableColumn<Beneficiaire, String> operationCol;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Beneficiaire beneficiaire = null;

    ObservableList<Beneficiaire> beneficiairesList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadDate();
    }
    
    private void refreshTable() {
        try {
            beneficiairesList.clear();

            query = "SELECT * FROM `beneficiaire`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                beneficiairesList.add(new Beneficiaire(
                        resultSet.getInt("id"),
                        resultSet.getString("nom_prenom_or_RS"),
                        resultSet.getDate("date_nss"),
                        resultSet.getString("wilaya"),
                        resultSet.getString("commune"),
                        resultSet.getString("nationalite")));
                beneficiairesTable.setItems(beneficiairesList);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ProprietorsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadDate() {

        connection = DbConnect.getConnect();
        refreshTable();

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        wilayaCol.setCellValueFactory(new PropertyValueFactory<>("wilaya"));
        communeCol.setCellValueFactory(new PropertyValueFactory<>("commune"));
        natCol.setCellValueFactory(new PropertyValueFactory<>("nationnalite"));

        //add cell of button edit 
        Callback<TableColumn<Beneficiaire, String>, TableCell<Beneficiaire, String>> cellFoctory = (TableColumn<Beneficiaire, String> param) -> {
            // make cell containing buttons
            final TableCell<Beneficiaire, String> cell = new TableCell<Beneficiaire, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
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
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            try {
                                beneficiaire = beneficiairesTable.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `beneficiaire` WHERE id  =" + beneficiaire.getId();
                                connection = DbConnect.getConnect();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(ProprietorsViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {

                            beneficiaire = beneficiairesTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource(Links.ADDBENEFICIAIREVIEW));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(ProprietorsViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AddBeneficiaireController addBeneficiaireController = loader.getController();
                            addBeneficiaireController.setUpdate(true);
                            addBeneficiaireController.setTextField(beneficiaire.getId(), beneficiaire.getName(),
                                    beneficiaire.getDate().toLocalDate(), beneficiaire.getWilaya(), beneficiaire.getCommune(),beneficiaire.getNationnalite());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();

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
        beneficiairesTable.setItems(beneficiairesList);

    }

}
