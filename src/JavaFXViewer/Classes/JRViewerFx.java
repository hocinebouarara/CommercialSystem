package JavaFXViewer.Classes;

import java.io.InputStream;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JasperPrint;

import javax.swing.*;

public class JRViewerFx /*extends Application */ {

    private JasperPrint jasperPrint;
    private JRViewerFxMode printMode;
    static Stage JRViewer;

    //@Override
    public void start(Stage primaryStage) throws Exception {
        InputStream fxmlStream;
        try {
            fxmlStream = getClass().getResourceAsStream("/JavaFXViewer/resourses/FRViewerFx.fxml");
            FXMLLoader loader = new FXMLLoader();
            Parent page = (Parent) loader.load(fxmlStream);
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setTitle("Jasper Viewer for JavaFx");
            JRViewer=primaryStage;
            primaryStage.show();
            Object o = loader.getController();
            if (o instanceof JRViewerFxController) {
                JRViewerFxController jrViewerFxController = (JRViewerFxController) o;
                jrViewerFxController.setJasperPrint(jasperPrint);
                jrViewerFxController.show();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public JRViewerFx(JasperPrint jasperPrint, JRViewerFxMode printMode, Stage primaryStage) {
        this.jasperPrint = jasperPrint;
        this.printMode = printMode;
        try {
            start(primaryStage);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

}
