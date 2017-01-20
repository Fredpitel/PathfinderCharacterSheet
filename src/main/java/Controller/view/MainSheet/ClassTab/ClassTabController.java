package Controller.view.MainSheet.ClassTab;

import Controller.MainApp;
import Controller.model.Experience;
import Controller.model.FavoredBonus.FavoredBonus;
import Controller.model.FavoredBonus.HitPointBonus;
import Controller.model.FavoredBonus.SkillPointBonus;
import Controller.model.Level;
import Controller.view.MainSheet.ClassTab.ClassSelection.ClassSelectionController;
import Controller.view.MainSheet.ClassTab.BonusSelection.BonusSelectionController;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
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

public class ClassTabController {
    private MainApp mainApp;
    
    @FXML
    private GridPane classTable;
    
    @FXML
    public VBox levelUp;
    
    @FXML
    public HBox nextLevelBox;
    
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
        VBox levelBox = new VBox();
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
                loader.setLocation(MainApp.class.getResource("view/MainSheet/ClassTab/ClassSelection/Class Selection.fxml"));
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
        favoredClass.visibleProperty().bind(level.charClass.getIsFavoredClassProperty());
        Label className = new Label(level.charClass.className);
        Label hitDieLabel = new Label("(d" + level.charClass.hitDie + ")");
        
        TextField hitDie = new TextField();
        hitDie.setAlignment(Pos.CENTER);
        hitDie.setText(level.getHpGained() + "");
        if(level.levelNumber == 1) {
            hitDie.setEditable(false);
            hitDie.getStyleClass().add("textfield-trans");
        } else {
            addHpListener(hitDie, level);
        }
        
        Label hitBonus = new Label();
        hitBonus.textProperty().bind(Bindings.when(mainApp.mainChar.stats.getStatModifier("CONSTITUTION").isNotEqualTo(0)).then(Bindings.format("+%d", mainApp.mainChar.stats.getStatModifier("CONSTITUTION"))).otherwise("-"));
        hitBonus.setAlignment(Pos.CENTER);
        
        ObservableList<FavoredBonus> bonus = FXCollections.observableArrayList(new HitPointBonus(), new SkillPointBonus());
        ComboBox<FavoredBonus> favoredBonus = new ComboBox(bonus);
        favoredBonus.valueProperty().bindBidirectional(level.getFavoredBonusProperty());
        favoredBonus.visibleProperty().bind(level.charClass.getIsFavoredClassProperty());
        
        Button removeLevel = new Button("Remove");
        
        removeLevel.setOnMouseClicked((e) -> {
            mainApp.mainChar.removeLevel(level);
            listLevels();
            listBonusPoints();
        });
        
        classTable.addRow(counter, levelNumber, favoredClass, className, hitDieLabel, hitDie, hitBonus, favoredBonus, removeLevel);
    }
    
    public void listLevels() {
        int counter = 0;
        classTable.getChildren().clear();
        for(int i = mainApp.mainChar.getCharLevels().size() - 1; i >= 0; i--) {
            Level level = (Level)mainApp.mainChar.getCharLevels().get(i);
            createLevelInfo(level, counter++);
        }
        System.out.println();
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
        for(int i = mainApp.mainChar.bonusStatPoints.size() - 1; i >= 0; i--) {
            createBonusInfo(mainApp.mainChar.bonusStatPoints.get(i), counter++, i);
        }
    }
    
    private void addHpListener(TextField tf, Level level) {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            pause.setOnFinished((e) -> {
                if (!newValue.matches("\\d*")) {
                    tf.setText(newValue.replaceAll("[^\\d]", ""));
                }

                int newBonus;
                try {
                    if(newValue.equals("")) {
                        newBonus = 1;
                    } else {
                        newBonus = Integer.parseInt(newValue);
                        if(newBonus > level.charClass.hitDie) newBonus = level.charClass.hitDie;
                    }
                } catch (Exception ex) {
                    newBonus = level.charClass.hitDie;
                }

                tf.setText(newBonus + "");
                level.setHpGained(newBonus);
            });
            pause.playFromStart();
        });
    }
    
    public void initializeComboBox()  {
        ObservableList<String> options = FXCollections.observableArrayList("(your first level class)");
        Set<Map.Entry> classes = mainApp.jsonClasses.entrySet();
        
        for(Map.Entry className : classes) {
            options.add((String)className.getKey());
        }
        
        favoredClassCombo.getItems().addAll(options);
        favoredClassCombo.getSelectionModel().selectFirst();
        
        favoredClassCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(oldValue != newValue) {
                String newFavoredClass = newValue.toString();
                if(newValue.equals("(your first level class)")) {
                    Level temp = (Level)mainApp.mainChar.getCharLevels().get(0);
                    newFavoredClass = temp.charClass.className;
                }
                
                mainApp.mainChar.setFavoredClass(newFavoredClass);
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
        levelUp.visibleProperty().bind(mainApp.mainChar.getRemainingLevelsProperty().greaterThan(0));
        levelUp.managedProperty().bind(levelUp.visibleProperty());
        nextLevelBox.visibleProperty().bind(mainApp.mainChar.getRemainingLevelsProperty().isEqualTo(0));
        nextLevelBox.managedProperty().bind(nextLevelBox.visibleProperty());
        nextLevelLabel.textProperty().bind(Bindings.format("Next level: %d (%d XP to go)", Bindings.size(mainApp.mainChar.getCharLevels())
                                           .add(1), Experience.getNextLevelXpValueProperty()
                                           .subtract(mainApp.mainChar.getTotalXpProperty())));
        currentLevelBox.visibleProperty().bind(Bindings.size(mainApp.mainChar.getCharLevels()).greaterThan(0));
        statBonusButton.disableProperty().bind(mainApp.mainChar.getBonusStatLeftProperty().isEqualTo(0));
        statBonusButton.textProperty().bind(Bindings.when(statBonusButton.disableProperty())
                                                    .then(Bindings.format("Next attribute bonus in %d levels", Bindings.createIntegerBinding(() ->
                                                        4 - (Bindings.size(mainApp.mainChar.getCharLevels()).intValue() % 4), mainApp.mainChar.getCharLevels())))
                                                    .otherwise("Click to increase attributes!"));
        statBonusButton.styleProperty().bind(Bindings.when(statBonusButton.disableProperty().not()).then("-fx-text-fill: red;").otherwise("-fx-text-fill: black;"));
        levelLabel.textProperty().bind(Bindings.size(mainApp.mainChar.getCharLevels()).asString());
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
