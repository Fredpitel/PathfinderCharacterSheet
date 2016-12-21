package Controller.view.MainSheet;

import Controller.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class MainSheetController {
    private MainApp mainApp;
    
    @FXML
    private TextField charName;
    
    @FXML
    private Label cash;
    
    public MainSheetController(){}
    
    @FXML
    private void initialize() {}
    
    public void initializeFields() {
        charName.setText(mainApp.mainChar.getCharName());
        cash.setText("Cash: " + mainApp.mainChar.getGoldTotal() + " GP");
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
