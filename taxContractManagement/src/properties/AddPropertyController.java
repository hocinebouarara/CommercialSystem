/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package properties;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import helpres.DbConnect;
import helpres.Links;
import home.HomeViewController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.Beneficiaire;
import models.Property;
import models.Proprietor;
import proprietors.AddProprietorController;
import proprietors.ProprietorsViewController;

/**
 * FXML Controller class
 *
 * @author Deeplight
 */
public class AddPropertyController implements Initializable {

    @FXML
    private JFXTextField articleFld;
    @FXML
    private JFXTextField wilayaFld;
    @FXML
    private JFXTextField communeFld;
    @FXML
    private JFXTextField reuFld;
    @FXML
    private JFXTextField inspectionFld;
    @FXML
    private JFXTextField nTerrainFld;
    @FXML
    private JFXTextField nImmeubleFld;
    @FXML
    private JFXRadioButton rezChaFld;
    @FXML
    private JFXTextField nEtageFld;
    @FXML
    private JFXTextField nApprtFld;
    @FXML
    private JFXTextField nomProprFld;
    @FXML
    private JFXTextField adresseProprFld;
    @FXML
    private JFXComboBox<String> orignPropCombo;
    @FXML
    private JFXTextField titreFld;
    @FXML
    private JFXTextField acieFld;
    @FXML
    private JFXDatePicker dateAcieFld;
    @FXML
    private JFXRadioButton collectifBtn;
    @FXML
    private JFXRadioButton induvidlBtn;
    @FXML
    private JFXTextField superTotFld;
    @FXML
    private JFXTextField SuperBatieFld;
    @FXML
    private JFXTextField superNonBatieFLd;
    @FXML
    private JFXTextField nbrPiecesFld;
    @FXML
    private JFXTextField nbrEtageFld;
    @FXML
    private JFXDatePicker dateAchevFld;
    @FXML
    private JFXComboBox<String> usageCombo;
    @FXML
    private JFXTextField adresseResdcPrincpFld;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Beneficiaire beneficiaire = null;
    private boolean update;
    int propertyId, proprietorId = 1;
    String type = null;
    boolean ground_floor = false;
    @FXML
    private VBox AddProprietorAnchor;
    @FXML
    private TableView<Proprietor> proprietorsTable;
    @FXML
    private TableColumn<Proprietor, String> idCol;
    @FXML
    private TableColumn<Proprietor, String> nameCol;
    @FXML
    private TableColumn<Proprietor, String> dateCol;
    @FXML
    private TableColumn<Proprietor, String> adressCol;
    @FXML
    private TableColumn<Proprietor, String> phoneCol;
    @FXML
    private TableColumn<Proprietor, String> operationCol;

    Proprietor proprietor = null;

    ObservableList<Proprietor> proprietorsList = FXCollections.observableArrayList();
    @FXML
    private VBox proprietor_property;
    @FXML
    private VBox proprietorView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        usageCombo.getItems().addAll("Héritage", "Donation", "Auto construction", "Acquisition");
        orignPropCombo.getItems().addAll("Usage professionnel", "Usage Mixte ( Habitation & Professionnel )",
                "Habitation", "Habitation principale", "Résidence secondaire");

        ToggleGroup group = new ToggleGroup();
        induvidlBtn.setToggleGroup(group);
        collectifBtn.setToggleGroup(group);

        loadData();
        proprietor_property.setVisible(true);
        proprietorView.setVisible(false);
    }

    String getTypeImmbeuble() {

        return type;
    }

    void setUpdate(boolean b) {
        this.update = b;
    }

    void setTextField(int id, int id_propr, String nom, String article, String titre, String commune, String reu, String acie, Date date) {
    }

    @FXML
    private void save(MouseEvent event) {
        connection = DbConnect.getConnect();

        if (articleFld.getText().isEmpty() || wilayaFld.getText().isEmpty() || communeFld.getText().isEmpty()
                || reuFld.getText().isEmpty()
                || nomProprFld.getText().isEmpty() || adresseProprFld.getText().isEmpty()
                || orignPropCombo.getValue() == null || titreFld.getText().isEmpty() || acieFld.getText().isEmpty()
                || dateAcieFld.getValue() == null
                || superTotFld.getText().isEmpty() || SuperBatieFld.getText().isEmpty() || superNonBatieFLd.getText().isEmpty()
                || nbrPiecesFld.getText().isEmpty() || nbrEtageFld.getText().isEmpty() || dateAchevFld.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All Data");
            alert.showAndWait();

        } else {
            getQuery();
            insert();

        }
    }

    @FXML
    private void clean(MouseEvent event) {
    }

    @FXML
    private void getProprietorsView(MouseEvent event) {
        proprietorView.setVisible(true);
        proprietor_property.setVisible(false);
    }

    private void getQuery() {

        if (update == false) {

            query = "INSERT INTO `fiche_habitation`(`nbr_article`, `inspection`, `wilaya`, `commune`,"
                    + " `reu`, `origin_propriete`, `n_terrain`, `n_immeuble`, `n_etage`, `n_appartement`,"
                    + " `rez_chaussee`, `nbr_etage`, `nbr_apparemment`, `type_immbeuble`, `superficie_tot`,"
                    + " `superficie_batie`, `superficie_non_batie`, `date_achev`,`usage`, `adresse_prcpl`, `id_propr`, `titre_propriete`,"
                    + " `n_acie`, `date`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        } else {

        }

    }

    private void insert() {
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, articleFld.getText());
            preparedStatement.setString(2, inspectionFld.getText());
            preparedStatement.setString(3, wilayaFld.getText());
            preparedStatement.setString(4, communeFld.getText());
            preparedStatement.setString(5, reuFld.getText());
            preparedStatement.setString(6, orignPropCombo.getValue());
            preparedStatement.setString(7, nTerrainFld.getText());
            preparedStatement.setString(8, nImmeubleFld.getText());
            preparedStatement.setString(9, nEtageFld.getText());
            preparedStatement.setString(10, nApprtFld.getText());
            preparedStatement.setBoolean(11, ground_floor);
            preparedStatement.setString(12, nbrEtageFld.getText());
            preparedStatement.setString(13, nbrPiecesFld.getText());
            preparedStatement.setString(14, getTypeImmbeuble());
            preparedStatement.setString(15, superTotFld.getText());
            preparedStatement.setString(16, SuperBatieFld.getText());
            preparedStatement.setString(17, superNonBatieFLd.getText());
            preparedStatement.setString(18, String.valueOf(dateAchevFld.getValue()));
            preparedStatement.setString(19, usageCombo.getValue());
            preparedStatement.setString(21, String.valueOf(proprietor.getId()));
            preparedStatement.setString(20, adresseProprFld.getText());
            preparedStatement.setString(22, titreFld.getText());
            preparedStatement.setString(23, acieFld.getText());
            preparedStatement.setString(24, String.valueOf(dateAcieFld.getValue()));

            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AddProprietorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void CellectifType(MouseEvent event) {
        type = "Cellectif";
    }

    @FXML
    private void IndividuelType(MouseEvent event) {
        type = "individuel";
    }

    @FXML
    private void groundFloor(MouseEvent event) {
        ground_floor = true;
    }

    private void loadViews(String viewName) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(viewName));
            AddProprietorAnchor.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void addProprietor(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(Links.ADDPROPIETORVIEW));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ProprietorsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    @FXML
    private void refreshTable() {
        try {
            proprietorsList.clear();

            query = "SELECT * FROM `proprietaire`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                proprietorsList.add(new Proprietor(
                        resultSet.getInt("id"),
                        resultSet.getString("nom_prenom_or_RS"),
                        resultSet.getDate("date_nss"),
                        resultSet.getString("adress"),
                        resultSet.getString("telephone")));
                proprietorsTable.setItems(proprietorsList);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ProprietorsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadData() {

        connection = DbConnect.getConnect();
        refreshTable();

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        adressCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        //add cell of button edit 
        Callback<TableColumn<Proprietor, String>, TableCell<Proprietor, String>> cellFoctory = (TableColumn<Proprietor, String> param) -> {
            // make cell containing buttons
            final TableCell<Proprietor, String> cell = new TableCell<Proprietor, String>() {
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
                                + "-glyph-size:14px;"
                                + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:14px;"
                                + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            try {
                                proprietor = proprietorsTable.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `proprietaire` WHERE id  =" + proprietor.getId();
                                connection = DbConnect.getConnect();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(ProprietorsViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {

                            proprietor = proprietorsTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/proprietors/addProprietor.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(ProprietorsViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AddProprietorController addProprietorController = loader.getController();
                            addProprietorController.setUpdate(true);
                            addProprietorController.setTextField(proprietor.getId(), proprietor.getName(),
                                    proprietor.getDate().toLocalDate(), proprietor.getAdresse(), proprietor.getPhone());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();

                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 3, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        operationCol.setCellFactory(cellFoctory);
        proprietorsTable.setItems(proprietorsList);

    }

    @FXML
    private void seletProprietor(MouseEvent event) {

        proprietor = (Proprietor) proprietorsTable.getSelectionModel().getSelectedItem();
        if (proprietor == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une ligne");
            alert.showAndWait();
        } else {
            nomProprFld.setText(proprietor.getName());
            proprietorView.setVisible(false);
            proprietor_property.setVisible(true);
        }
    }

    @FXML
    private void back(MouseEvent event) {
        proprietorView.setVisible(false);
        proprietor_property.setVisible(true);
        proprietor = null;
    }

}
