/**
 *
 */
package JavaFXViewer.Classes;

import java.util.HashMap;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;


public class JRViewerMain {

    JasperPrint jasperPrint = null;

    public void start() throws Exception {

        Stage primaryStage = null;
        jasperPrint=JasperFillManager.fillReport("C:\\Users\\hamid\\Documents\\NetBeansProjects\\my_project_for_free_time\\src\\port_2\\FXMLs\\Langagement.jasper", new HashMap(), new JREmptyDataSource());
        JRViewerFx viewer = new JRViewerFx(jasperPrint, JRViewerFxMode.REPORT_VIEW, primaryStage);

    }
}
