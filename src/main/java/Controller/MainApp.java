package Controller;

import Controller.JSON.JSONUtils;
import Controller.model.Character;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Controller.view.MainMenu.MainMenuController;
import Controller.view.MainSheet.MainSheetController;
import Controller.view.NewCharacter.NewCharacterController;
import net.sf.json.JSONObject;

public class MainApp extends Application {
    private Stage stage;
    private AnchorPane root;
    
    public JSONObject jsonClasses;
    public JSONObject jsonRaces;
    public Character mainChar;
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.setTitle("Pathfinder Character Sheet");
        
        jsonClasses = JSONUtils.jsonToObject("JSON Files/classes.json");
        jsonRaces = JSONUtils.jsonToObject("JSON Files/races.json");
        showMainMenu();
    }
    
    public void showMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainMenu/Main Menu.fxml"));
            root = (AnchorPane) loader.load();
            
            MainMenuController controller = loader.getController();
            controller.setMainApp(this);
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showNewCharMenu() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/NewCharacter/New Character.fxml"));
            root = (AnchorPane) loader.load();
            
            NewCharacterController controller = loader.getController();
            controller.setMainApp(this);
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showMainSheet() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainSheet/Main Sheet.fxml"));
            root = (AnchorPane) loader.load();
            
            MainSheetController controller = loader.getController();
            controller.setMainApp(this);
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            root.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Stage getPrimaryStage() {
        return stage;
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
