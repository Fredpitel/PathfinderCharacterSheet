package Controller.view.MainSheet.ClassTab;

import Controller.MainApp;
import Controller.view.ClassSelection.ClassSelectionController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ClassTabController {
    private MainApp mainApp;
    
    @FXML
   private VBox classTable;
    
    @FXML
    private void initialize() {}
    
    public void createLevelUpPrompt() {
        VBox levelBox = new VBox();
        levelBox.setAlignment(Pos.CENTER);
        levelBox.getStyleClass().add("levelBox");
        Label levelUp = new Label("Level Up!");
        Label clickMe = new Label("Click here to add class");
        
        levelBox.getChildren().addAll(levelUp, clickMe);
        
        levelBox.setOnMouseClicked((e) -> {
            Stage stage = new Stage();
            stage.setTitle("Class Selection");
           
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/ClassSelection/Class Selection.fxml"));
                AnchorPane root = (AnchorPane) loader.load();

                ClassSelectionController controller = loader.getController();
                controller.setMainApp(mainApp);
                
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        });
        
        classTable.getChildren().add(0, levelBox);
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        if(mainApp.mainChar.getRemainingLevels() > 0) {
            createLevelUpPrompt();
        }
    }
}
