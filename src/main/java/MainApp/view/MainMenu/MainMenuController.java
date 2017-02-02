package MainApp.view.MainMenu;

import MainApp.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenuController {
    private MainApp mainApp;
    
    @FXML
    private Button quit;
    
    @FXML
    private Button newChar;

    public MainMenuController(){}
    
    @FXML
    private void initialize() {
        addMousePressedEventListener(quit);
        addMousePressedEventListener(newChar);
    }
    
    @FXML
    private void newChar() {
        newChar.getStyleClass().remove("buttonPressed");
        newChar.getStyleClass().add("button");
        mainApp.showNewCharMenu();
    }
    
    @FXML
    private void quit() {
        quit.getStyleClass().remove("buttonPressed");
        quit.getStyleClass().add("button");
        System.exit(0);
    }
    
    private void addMousePressedEventListener(Button btn) {
        btn.setOnMousePressed((e) -> {
            btn.getStyleClass().add("buttonPressed");
        });
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

}
