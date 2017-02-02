package Controller.view.MainSheet;

import Controller.MainApp;
import Controller.view.MainSheet.BackgroundTab.BackgroundTabController;
import Controller.view.MainSheet.ClassTab.ClassTabController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.scene.control.Tab;

public class MainSheetController {
    private MainApp mainApp;
    
    @FXML 
    private ClassTabController classTabController;
    
    @FXML
    private BackgroundTabController backgroundTabController;
    
    @FXML
    private TextField charName;
    
    @FXML
    private Label race;
    
    @FXML
    private Label classes;
    
    @FXML
    private Label hp;
    
    @FXML
    private Label ac;
    
    @FXML
    private Label cash;
    
    @FXML
    private Tab classTabContainer;
    
    @FXML
    private Tab backgroundTabContainer;
    
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
        charName.textProperty().bindBidirectional(mainApp.mainChar.getCharNameProperty());
        race.textProperty().bind(Bindings.when(mainApp.mainChar.getRaceNameProperty().isEqualTo("Choose Race")).then("No race").otherwise(mainApp.mainChar.getRaceNameProperty()));
        classes.textProperty().bind(mainApp.mainChar.getCharClassesProperty());
        hp.textProperty().bind(Bindings.format("HP: %d/%d", mainApp.mainChar.getHitPoints().getCurrentHpProperty(), mainApp.mainChar.getHitPoints().getMaxHpProperty()));
        ac.textProperty().bind(Bindings.format("AC: %d", mainApp.mainChar.getTotalAcProperty()));
        cash.textProperty().bind(Bindings.format("Cash: %d GP", mainApp.mainChar.getGoldTotalProperty()));
        
        bindAbilityLabel(strBonus, strLabel, "STRENGTH");
        bindAbilityLabel(dexBonus, dexLabel, "DEXTERITY");
        bindAbilityLabel(conBonus, conLabel, "CONSTITUTION");
        bindAbilityLabel(intBonus, intLabel, "INTELLIGENCE");
        bindAbilityLabel(wisBonus, wisLabel, "WISDOM");
        bindAbilityLabel(chaBonus, chaLabel, "CHARISMA");
        
        hpLabel.textProperty().bind(Bindings.format("%d/%d", mainApp.mainChar.getHitPoints().getCurrentHpProperty(), mainApp.mainChar.getHitPoints().getMaxHpProperty()));
        acLabel.textProperty().bind(mainApp.mainChar.getTotalAcProperty().asString());
        touchLabel.textProperty().bind(Bindings.format("%d Tch", mainApp.mainChar.getBaseAcProperty()
                                               .add(mainApp.mainChar.getAbilityScore("DEXTERITY").getScoreModifierProperty())));
        flatLabel.textProperty().bind(Bindings.format("%d Flat", mainApp.mainChar.getBaseAcProperty()));
        
        fortRes.textProperty().bind(Bindings.when(mainApp.mainChar.getFortProperty().lessThanOrEqualTo(0))
                                            .then(mainApp.mainChar.getFortProperty().asString())
                                            .otherwise(Bindings.format("+%d", mainApp.mainChar.getFortProperty())));
        refRes.textProperty().bind(Bindings.when(mainApp.mainChar.getRefProperty().lessThanOrEqualTo(0))
                                           .then(mainApp.mainChar.getRefProperty().asString())
                                           .otherwise(Bindings.format("+%d", mainApp.mainChar.getRefProperty())));
        wilRes.textProperty().bind(Bindings.when(mainApp.mainChar.getWilProperty().lessThanOrEqualTo(0))
                                           .then(mainApp.mainChar.getWilProperty().asString())
                                           .otherwise(Bindings.format("+%d", mainApp.mainChar.getWilProperty())));
    }
    
    private void bindAbilityLabel(Label ability, Label modifier, String abilityName) {
        ability.textProperty().bind(Bindings.when(getScoreModifierProperty(abilityName).lessThanOrEqualTo(0))
                                            .then(getScoreModifierProperty(abilityName).asString())
                                            .otherwise(Bindings.format("+%d", getScoreModifierProperty(abilityName))));
        modifier.textProperty().bind(getScoreProperty(abilityName).asString());
    }
    
    private IntegerProperty getScoreProperty(String ability) {
        return mainApp.mainChar.getAbilityScore(ability).getCurrentValueProperty();
    }
    
    private IntegerProperty getScoreModifierProperty(String ability) {
        return mainApp.mainChar.getAbilityScore(ability).getScoreModifierProperty();
    }
    
    public void initializeTabs() {
        classTabController.setMainApp(mainApp);
        classTabContainer.setGraphic(new Label("Classes"));
        classTabContainer.getGraphic().styleProperty().bind(Bindings.when((classTabController.levelUp.visibleProperty().not())
                                                                    .and(classTabController.statBonusButton.disableProperty()))
                                                                    .then("-fx-text-fill: black;")
                                                                    .otherwise("-fx-text-fill: red;"));
    
        backgroundTabController.setMainApp(mainApp);
        backgroundTabContainer.setGraphic(new Label("Background"));
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
