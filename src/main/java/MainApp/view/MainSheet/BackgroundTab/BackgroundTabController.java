package MainApp.view.MainSheet.BackgroundTab;

import MainApp.MainApp;
import MainApp.view.MainSheet.BackgroundTab.AlignmentSelection.AlignmentSelectionController;
import MainApp.view.MainSheet.BackgroundTab.RaceSelection.RaceSelectionController;
import java.io.IOException;
import javafx.beans.binding.Bindings;
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
        stage.setTitle("Race Selection");

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
    
    @FXML
    private void chooseAlignment() {
        Stage stage = new Stage();
        stage.setTitle("Alignment Selection");

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainSheet/BackgroundTab/AlignmentSelection/Alignment Selection.fxml"));
            AnchorPane root = (AnchorPane) loader.load();

            AlignmentSelectionController controller = loader.getController();
            controller.setMainApp(mainApp, stage, this);

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    } 
    
    public void createBindings() {
        raceField.textProperty().bind(mainApp.mainChar.getRaceNameProperty());
        raceField.styleProperty().bind(Bindings.when(mainApp.mainChar.getRaceNameProperty().isEqualTo("Choose Race")).then("-fx-text-fill: red;").otherwise("-fx-text-fill: black;"));
        alignmentField.textProperty().bind(mainApp.mainChar.getAlignmentProperty());
        alignmentField.styleProperty().bind(Bindings.when(mainApp.mainChar.getAlignmentProperty().isEqualTo("Choose Alignment")).then("-fx-text-fill: red;").otherwise("-fx-text-fill: black;"));
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        createBindings();
    }
}
