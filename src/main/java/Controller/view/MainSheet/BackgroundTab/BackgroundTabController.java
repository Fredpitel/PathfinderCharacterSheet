package Controller.view.MainSheet.BackgroundTab;

import Controller.MainApp;
import Controller.view.MainSheet.BackgroundTab.RaceSelection.RaceSelectionController;
import Controller.view.MainSheet.ClassTab.ClassSelection.ClassSelectionController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class BackgroundTabController {
    MainApp mainApp;
    
    @FXML
    private TextField raceField;
    
    @FXML
    private TextField alignmentField;
    
    @FXML
    private TextField deityField;
    
    @FXML
    private void chooseRace() {
        Stage stage = new Stage();
        stage.setTitle("Class Selection");

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainSheet/BackgroundTab/RaceSelection/Race Selection.fxml"));
            AnchorPane root = (AnchorPane) loader.load();

            RaceSelectionController controller = loader.getController();
            controller.setMainApp(mainApp, stage, this);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    } 
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}