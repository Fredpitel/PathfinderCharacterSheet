package Controller.view.MainSheet.ClassTab;

import Controller.MainApp;
import Controller.model.Experience;
import Controller.model.Level;
import Controller.view.ClassSelection.ClassSelectionController;
import Controller.view.MainSheet.ClassTab.BonusSelection.BonusSelectionController;
import java.io.IOException;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.json.JSONArray;

public class ClassTabController {
    private MainApp mainApp;
    private VBox levelBox;
    
    @FXML
    private GridPane classTable;
    
    @FXML
    public VBox levelUp;
    
    @FXML
    private HBox nextLevelBox;
    
    @FXML
    private HBox currentLevelBox;
    
    @FXML
    private Label nextLevelLabel;
    
    @FXML 
    private ComboBox favoredClassCombo;
    
    @FXML
    private Label levelLabel;
    
    @FXML
    private Label xpLabel;
    
    @FXML
    private GridPane bonusPointsTable;
    
    @FXML
    public Button statBonusButton;
    
    @FXML
    private void initialize() {}
    
    public void createLevelUpPrompt() {
        levelBox = new VBox();
        levelBox.setAlignment(Pos.CENTER);
        levelBox.getStyleClass().add("levelBox");
        Label levelUpLabel = new Label("Level Up!");
        Label clickMe = new Label("Click here to add class");
        
        levelUpLabel.getStyleClass().add("levelUp");
        clickMe.getStyleClass().add("levelUp");
        
        levelBox.getChildren().addAll(levelUpLabel, clickMe);
        
        levelBox.setOnMouseClicked((e) -> {
            Stage stage = new Stage();
            stage.setTitle("Class Selection");
           
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/ClassSelection/Class Selection.fxml"));
                AnchorPane root = (AnchorPane) loader.load();

                ClassSelectionController controller = loader.getController();
                controller.setMainApp(mainApp, stage, this);
                
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        });
        
        levelUp.getChildren().add(0, levelBox);
    }
    
    public void createLevelInfo(Level level, int counter) {
        Label levelNumber = new Label(level.levelNumber + "");
        Label favoredClass = new Label("*");
        favoredClass.visibleProperty().bind(level.getIsFavoredClassProperty());
        Label className = new Label(level.className);
        Label hitDieLabel = new Label("(d" + level.hitDie + ")");
        
        TextField hitDie = new TextField();
        hitDie.setAlignment(Pos.CENTER);
        hitDie.setText(level.hpGained + "");
        if(level.levelNumber == 1) {
            hitDie.setEditable(false);
            hitDie.getStyleClass().add("textfield-trans");
        } else {
            addHpListener(hitDie, level);
        }
        
        Label hitBonus = new Label();
        hitBonus.textProperty().bind(Bindings.when(mainApp.mainChar.stats.getConBonusProperty().isNotEqualTo(0)).then(Bindings.format("+%d", mainApp.mainChar.stats.getConBonusProperty())).otherwise("-"));
        hitBonus.setAlignment(Pos.CENTER);
        
        ObservableList<String> options = FXCollections.observableArrayList("+1 Hit Point", "+1 Skill Point");
        ComboBox favoredBonus = new ComboBox(options);
        if(level.favoredBonus == null || level.favoredBonus.getBonusString().equals("HitPoint")){
            favoredBonus.getSelectionModel().selectFirst();
        } else {
            favoredBonus.getSelectionModel().selectLast();
        }
        
        favoredBonus.visibleProperty().bind(level.getIsFavoredClassProperty());
        
        favoredBonus.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue != newValue) {
                level.switchBonus(mainApp.mainChar, newValue.toString());
            }
        });
        
        Button removeLevel = new Button("Remove");
        
        removeLevel.setOnMouseClicked((e) -> {
            mainApp.mainChar.removeLevel(level);
            listLevels();
        });
        
        classTable.addRow(counter, levelNumber, favoredClass, className, hitDieLabel, hitDie, hitBonus, favoredBonus, removeLevel);
    }
    
    public void listLevels() {
        int counter = 0;
        classTable.getChildren().clear();
        for(int i = mainApp.mainChar.charLevels.size() - 1; i >= 0; i--) {
            createLevelInfo(mainApp.mainChar.charLevels.get(i), counter++);
        }
    }
    
    public void createBonusInfo(String stat, int counter, int level) {
        Label bonusLevelLabel = new Label("Level " + (level * 4 + 4) + ":");
        Label bonusStatLabel = new Label("+1 " + stat);
        Button removeBonus = new Button("Remove");
        
        removeBonus.setOnMouseClicked( (e) ->{
            mainApp.mainChar.removeBonusStatPoint(stat);
            listBonusPoints();
        });
        
        bonusPointsTable.addRow(counter, bonusLevelLabel, bonusStatLabel, removeBonus);
    }
    
    public void listBonusPoints() {
        int counter = 0;
        bonusPointsTable.getChildren().clear();
        for(int i = mainApp.mainChar.bonusPoints.size() - 1; i >= 0; i--) {
            createBonusInfo(mainApp.mainChar.bonusPoints.get(i), counter++, i);
        }
    }
    
    private void addHpListener(TextField tf, Level level) {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            pause.setOnFinished((e) -> {
                if (!newValue.matches("\\d*")) {
                    tf.setText(newValue.replaceAll("[^\\d]", ""));
                }
                
                int currentBonus = level.hpGained;
                int newBonus;
                try {
                    if(newValue.equals("")) {
                        newBonus = 1;
                    } else {
                        newBonus = Integer.parseInt(newValue);
                        if(newBonus > level.hitDie) newBonus = level.hitDie;
                    }
                } catch (Exception ex) {
                    newBonus = level.hitDie;
                }

                tf.setText(newBonus + "");
                mainApp.mainChar.setMaxHp(mainApp.mainChar.getMaxHp() - currentBonus);
                mainApp.mainChar.setMaxHp(mainApp.mainChar.getMaxHp() + newBonus);
                mainApp.mainChar.setCurrentHp(mainApp.mainChar.getCurrentHp() - currentBonus);
                mainApp.mainChar.setCurrentHp(mainApp.mainChar.getCurrentHp() + newBonus);
                level.hpGained = newBonus;
            });
            pause.playFromStart();
        });
    }
    
    public void initializeComboBox()  {
        ObservableList<String> options = FXCollections.observableArrayList("(your first level class)");
        JSONArray classes = mainApp.jsonClasses.getJSONArray("classes");
        
        for(int i = 0; i < classes.size(); i++) {
            options.add(classes.getJSONObject(i).getString("className"));
        }
        
        favoredClassCombo.getItems().addAll(options);
        favoredClassCombo.getSelectionModel().selectFirst();
        
        favoredClassCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue != newValue) {
                if(!newValue.equals("(your first level class)")) {
                    mainApp.mainChar.updateFavoredClass(newValue.toString());
                } else {
                    mainApp.mainChar.updateFavoredClass(mainApp.mainChar.charLevels.get(0).className);
                }
            }
        });
    }
    
    public void initializeStatButton() {
        statBonusButton.setOnMouseClicked((e) -> {
            Stage stage = new Stage();
            stage.setTitle("Select Bonus Ability Score");
           
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/MainSheet/ClassTab/BonusSelection/Bonus Selection.fxml"));
                AnchorPane root = (AnchorPane) loader.load();

                BonusSelectionController controller = loader.getController();
                controller.setMainApp(mainApp, stage, this);
                
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        });
    }
    
    public void createBindings() {
        levelBox.visibleProperty().bind(mainApp.mainChar.getRemainingLevelsProperty().greaterThan(0));
        levelBox.managedProperty().bind(levelBox.visibleProperty());
        nextLevelBox.visibleProperty().bind(mainApp.mainChar.getRemainingLevelsProperty().isEqualTo(0));
        nextLevelBox.managedProperty().bind(nextLevelBox.visibleProperty());
        nextLevelLabel.textProperty().bind(Bindings.format("Next level: %d (%d XP to go)", mainApp.mainChar.getCurrentLevelProperty().add(1), Experience.getNextLevelXpValueProperty().subtract(mainApp.mainChar.getTotalXpProperty())));
        currentLevelBox.visibleProperty().bind(mainApp.mainChar.getCurrentLevelProperty().greaterThan(0));
        statBonusButton.disableProperty().bind(mainApp.mainChar.getBonusLeftProperty().isEqualTo(0));
        statBonusButton.textProperty().bind(Bindings.when(statBonusButton.disableProperty()).then(Bindings.format("Next attribute bonus in %d levels", Bindings.createIntegerBinding(() -> 4 - (mainApp.mainChar.getCurrentLevel() % 4), mainApp.mainChar.getCurrentLevelProperty()))).otherwise("Click to increase attributes!"));
        statBonusButton.styleProperty().bind(Bindings.when(statBonusButton.disableProperty().not()).then("-fx-text-fill: red;").otherwise("-fx-text-fill: black;"));
        levelLabel.textProperty().bind(mainApp.mainChar.getCurrentLevelProperty().asString());
        xpLabel.textProperty().bind(Bindings.format("%d/%d", mainApp.mainChar.getTotalXpProperty(), Experience.getNextLevelXpValueProperty()));
    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        listLevels();
        createLevelUpPrompt();
        initializeComboBox();
        initializeStatButton();
        createBindings();
    }
}
