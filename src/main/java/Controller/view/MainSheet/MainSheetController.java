package Controller.view.MainSheet;

import Controller.MainApp;
import Controller.model.Stats;
import Controller.view.MainSheet.ClassTab.ClassTabController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;


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
    private Label ac;
    
    @FXML
    private Label cash;
    
    @FXML
    private TabPane tabs;
    
    @FXML
    private Tab classTabContainer;
    
    @FXML
    private Label strBonus;
    
    @FXML
    private Label strLabel;
    
    @FXML
    private Label dexBonus;
    
    @FXML
    private Label dexLabel;
    
    @FXML
    private Label conBonus;
    
    @FXML
    private Label conLabel;
    
    @FXML
    private Label intBonus;
    
    @FXML
    private Label intLabel;
    
    @FXML
    private Label wisBonus;
    
    @FXML
    private Label wisLabel;
    
    @FXML
    private Label chaBonus;
    
    @FXML
    private Label chaLabel;
    
    @FXML
    private Label hpLabel;
    
    @FXML
    private Label acLabel;
    
    @FXML
    private Label touchLabel;
    
    @FXML
    private Label flatLabel;
    
    @FXML
    private Label fortRes;
    
    @FXML
    private Label refRes;
    
    @FXML
    private Label wilRes;
    
    public MainSheetController(){}
    
    @FXML
    private void initialize() {}
    
    public void createBindings() {
        Stats stats = mainApp.mainChar.stats;
        
        charName.textProperty().bindBidirectional(mainApp.mainChar.getCharNameProperty());
        classes.textProperty().bind(mainApp.mainChar.getCharClassesProperty());
        hp.textProperty().bind(Bindings.format("HP: %d/%d", mainApp.mainChar.getCurrentHpProperty(), mainApp.mainChar.getMaxHpProperty()));
        ac.textProperty().bind(Bindings.format("AC: %d", mainApp.mainChar.getTotalACProperty()));
        cash.textProperty().bind(Bindings.format("Cash: %d GP", mainApp.mainChar.getGoldTotalProperty()));
        
        strBonus.textProperty().bind(Bindings.when(stats.getStatModifier("STRENGTH").lessThanOrEqualTo(0)).then(stats.getStatModifier("STRENGTH").asString()).otherwise(Bindings.format("+%d", stats.getStatModifier("STRENGTH"))));
        strLabel.textProperty().bind(mainApp.mainChar.stats.getStat("STRENGTH").asString());
        dexBonus.textProperty().bind(Bindings.when(stats.getStatModifier("DEXTERITY").lessThanOrEqualTo(0)).then(stats.getStatModifier("DEXTERITY").asString()).otherwise(Bindings.format("+%d", stats.getStatModifier("DEXTERITY"))));
        dexLabel.textProperty().bind(mainApp.mainChar.stats.getStat("DEXTERITY").asString());
        conBonus.textProperty().bind(Bindings.when(stats.getStatModifier("CONSTITUTION").lessThanOrEqualTo(0)).then(stats.getStatModifier("CONSTITUTION").asString()).otherwise(Bindings.format("+%d", stats.getStatModifier("CONSTITUTION"))));
        conLabel.textProperty().bind(mainApp.mainChar.stats.getStat("CONSTITUTION").asString());
        intBonus.textProperty().bind(Bindings.when(stats.getStatModifier("INTELLIGENCE").lessThanOrEqualTo(0)).then(stats.getStatModifier("INTELLIGENCE").asString()).otherwise(Bindings.format("+%d", stats.getStatModifier("INTELLIGENCE"))));
        intLabel.textProperty().bind(mainApp.mainChar.stats.getStat("INTELLIGENCE").asString());
        wisBonus.textProperty().bind(Bindings.when(stats.getStatModifier("WISDOM").lessThanOrEqualTo(0)).then(stats.getStatModifier("WISDOM").asString()).otherwise(Bindings.format("+%d", stats.getStatModifier("WISDOM"))));
        wisLabel.textProperty().bind(mainApp.mainChar.stats.getStat("WISDOM").asString());
        chaBonus.textProperty().bind(Bindings.when(stats.getStatModifier("CHARISMA").lessThanOrEqualTo(0)).then(stats.getStatModifier("CHARISMA").asString()).otherwise(Bindings.format("+%d", stats.getStatModifier("CHARISMA"))));
        chaLabel.textProperty().bind(mainApp.mainChar.stats.getStat("CHARISMA").asString());
        
        hpLabel.textProperty().bind(Bindings.format("%d/%d", mainApp.mainChar.getCurrentHpProperty(), mainApp.mainChar.getMaxHpProperty()));
        acLabel.textProperty().bind(mainApp.mainChar.getTotalACProperty().asString());
        touchLabel.textProperty().bind(Bindings.format("%d Tch", mainApp.mainChar.getBaseACProperty().add(mainApp.mainChar.stats.getStatModifier("DEXTERITY"))));
        flatLabel.textProperty().bind(Bindings.format("%d Flat", mainApp.mainChar.getBaseACProperty()));
        
        fortRes.textProperty().bind(Bindings.when(mainApp.mainChar.getFortProperty().lessThanOrEqualTo(0)).then(mainApp.mainChar.getFortProperty().asString()).otherwise(Bindings.format("+%d", mainApp.mainChar.getFortProperty())));
        refRes.textProperty().bind(Bindings.when(mainApp.mainChar.getRefProperty().lessThanOrEqualTo(0)).then(mainApp.mainChar.getRefProperty().asString()).otherwise(Bindings.format("+%d", mainApp.mainChar.getRefProperty())));
        wilRes.textProperty().bind(Bindings.when(mainApp.mainChar.getWilProperty().lessThanOrEqualTo(0)).then(mainApp.mainChar.getWilProperty().asString()).otherwise(Bindings.format("+%d", mainApp.mainChar.getWilProperty())));
    }
    
    public void initializeTabs() {
        classTabController.setMainApp(mainApp);
        classTabContainer.setGraphic(new Label("Classes"));
        classTabContainer.getGraphic().styleProperty().bind(Bindings.when((classTabController.levelUp.visibleProperty().not()).and(classTabController.statBonusButton.disableProperty())).then("-fx-text-fill: black;").otherwise("-fx-text-fill: red;"));
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        createBindings();
        initializeTabs();
    }
    
    public ClassTabController getClassTabController() {
        return classTabController;
    }
}
