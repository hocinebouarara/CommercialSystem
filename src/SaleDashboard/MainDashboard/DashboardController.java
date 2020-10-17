package SaleDashboard.MainDashboard;

import helpers.Links;
import home.HomeViewController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {

    @FXML
    private Label Seller;

    @FXML
    private Circle ARC;

    @FXML
    AnchorPane Parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ARC.setStroke(Color.SEAGREEN);
        Image image=new Image("/SaleDashboard/MainDashboard/image.jpg");
        ARC.setFill(new ImagePattern(image));
        ARC.setEffect(new DropShadow(+25d,0d,+2d,Color.DARKSEAGREEN));

        Seller.setText("Mounis abd elhamid");

    }
    @FXML
    void SaleAction(ActionEvent event) {
        loadViews(Links.SALEVIEW);
    }
    @FXML
    void ChartsAction(ActionEvent event){
        loadViews(Links.ChartView);
    }
    private void loadViews(String viewName) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(viewName));
            Parent.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void Agrandir(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (!stage.isFullScreen()) {
            stage.setFullScreen(true);
        } else {
            stage.setFullScreen(false);
        }

    }

    @FXML
    void Exit(ActionEvent event) {

        Platform.exit();

    }
    @FXML
    void reduire(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);

    }
}
