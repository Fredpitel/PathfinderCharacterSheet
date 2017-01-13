package Controller.view.MainSheet;

import Controller.MainApp;
import Controller.view.MainSheet.ClassTab.ClassTabController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Tab;


public class MainSheetController {
    private MainApp mainApp;
    
    @FXML 
    private ClassTabController classTabController;
    
    @FXML
    private TextField charName;
    
    @FXML
    private Label classes;
    
    @FXML
    private Label hp;
    
    @FXML
    private Label cash;
    
    
    public MainSheetController(){}
    
    @FXML
    private void initialize() {}
    
    public void initializeFields() {
        charName.textProperty().bindBidirectional(mainApp.mainChar.charNameProperty());
        classes.textProperty().bind(mainApp.mainChar.charClassesProperty());
        hp.textProperty().bind(Bindings.format("HP: %d/%d", mainApp.mainChar.getCurrentHpProperty(), mainApp.mainChar.getMaxHpProperty()));
        cash.setText("Cash: " + mainApp.mainChar.getGoldTotal() + " GP");
    }
    
    public void initializeTabs() {
        classTabController.setMainApp(mainApp);
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        initializeTabs();
        //classTabContainer.getGraphic().styleProperty().bind(Bindings.when((classTabController.levelUp.disableProperty()).or(classTabController.statBonusButton.disableProperty())).then("-fx-text-fill: red;").otherwise("-fx-text-fill: black;"));
    }
    
    public ClassTabController getClassTabController() {
        return classTabController;
    }
}
