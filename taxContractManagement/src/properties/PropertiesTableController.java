/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package properties;

import beneficiaries.AddBeneficiaireController;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.Property;
import proprietors.ProprietorsViewController;

/**
 * FXML Controller class
 *
 * @author Deeplight
 */
public class PropertiesTableController implements Initializable {

    @FXML
    private TableView<Property> propertiesTable;
    @FXML
    private TableColumn<Property, String> idCol;
    @FXML
    private TableColumn<Property, String> idProprCol;
    @FXML
    private TableColumn<Property, String> nomProprCol;
    @FXML
    private TableColumn<Property, String> articleCol;
    @FXML
    private TableColumn<Property, String> titreCol;
    @FXML
    private TableColumn<Property, String> communeCol;
    @FXML
    private TableColumn<Property, String> reuCol;
    @FXML
    private TableColumn<Property, String> acieCol;
    @FXML
    private TableColumn<Property, String> dateCol;
    @FXML
    private TableColumn<Property, String> operationCol;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Property property = null;

    ObservableList<Property> propertyList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<?, ?> checkCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadDate();
    }

    @FXML
    private void refreshTable() {
        try {
            propertyList.clear();

            query = "SELECT fiche_habitation.id,proprietaire.id,"
                    + "proprietaire.nom_prenom_or_RS,fiche_habitation.nbr_article,"
                    + "fiche_habitation.titre_propriete,fiche_habitation.inspection,"
                    + "fiche_habitation.wilaya,fiche_habitation.commune,"
                    + "fiche_habitation.reu,fiche_habitation.n_acie,fiche_habitation.date "
                    + "FROM "
                    + "fiche_habitation "
                    + "INNER JOIN proprietaire ON proprietaire.id = fiche_habitation.id_propr "
                    + "ORDER BY fiche_habitation.id";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                propertyList.add(new Property(
                        resultSet.getInt("fiche_habitation.id"),
                        resultSet.getInt("proprietaire.id"),
                        resultSet.getString("proprietaire.nom_prenom_or_RS"),
                        resultSet.getString("fiche_habitation.nbr_article"),
                        resultSet.getString("fiche_habitation.titre_propriete"),
                        resultSet.getString("fiche_habitation.commune"),
                        resultSet.getString("fiche_habitation.reu"),
                        resultSet.getString("fiche_habitation.n_acie"),
                        resultSet.getDate("fiche_habitation.date")));
                propertiesTable.setItems(propertyList);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ProprietorsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void loadDate() {

        connection = DbConnect.getConnect();
        refreshTable();

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idProprCol.setCellValueFactory(new PropertyValueFactory<>("id_propr"));
        nomProprCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        articleCol.setCellValueFactory(new PropertyValueFactory<>("article"));
        titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        communeCol.setCellValueFactory(new PropertyValueFactory<>("commune"));
        reuCol.setCellValueFactory(new PropertyValueFactory<>("reu"));
        acieCol.setCellValueFactory(new PropertyValueFactory<>("acie"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        //add cell of button edit 
        Callback<TableColumn<Property, String>, TableCell<Property, String>> cellFoctory = (TableColumn<Property, String> param) -> {
            // make cell containing buttons
            final TableCell<Property, String> cell = new TableCell<Property, String>() {
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
                                property = propertiesTable.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `fiche_habitation` WHERE id = '" + property.getId()+ "'"
                                        + " and id_propr = '" + property.getId_propr()+ "'";
                                connection = DbConnect.getConnect();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(ProprietorsViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                        /*editIcon.setOnMouseClicked((MouseEvent event) -> {

                            property = propertiesTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource(Links.ADDPROPIETORVIEW));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(ProprietorsViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AddPropertyController addPropertyController = loader.getController();
                            addPropertyController.setUpdate(true);
                            addPropertyController.setTextField(property.getId(), property.getId_propr(),
                                    property.getNom(),property.getArticle(),property.getTitre(),property.getCommune(),
                                    property.getReu(),property.getAcie(),property.getDate());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();

                        });*/

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
        propertiesTable.setItems(propertyList);

    }

}
