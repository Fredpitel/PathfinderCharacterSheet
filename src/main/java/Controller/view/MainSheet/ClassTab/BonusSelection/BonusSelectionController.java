package Controller.view.MainSheet.ClassTab.BonusSelection;

import Controller.MainApp;
import Controller.view.MainSheet.ClassTab.ClassTabController;
import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class BonusSelectionController {
    private MainApp mainApp;
    private Stage stage;
    private ClassTabController classTabController;
    private final ArrayList<Button> buttons;
    private Button selected;
    private int currentLevel = 1;
    
    @FXML
    private Button strength;
    
    @FXML
    private Button dexterity;
    
    @FXML
    private Button constitution;
    
    @FXML
    private Button intelligence;
    
    @FXML
    private Button wisdom;
    
    @FXML
    private Button charisma;
    
    @FXML
    private Label pointsLeft;
    
    @FXML
    private Button addPoints;
    
    @FXML
    private Button removePoints;
    
    @FXML
    private TextField points;
    
    @FXML
    private Button addAndClose;
    
    @FXML
    private Button add;
    
    @FXML
    private Button close;
    
    public BonusSelectionController(){
        this.buttons = new ArrayList();
    }
    
    @FXML
    private void initialize() {
        addPoints.setText("\u25B6");
        removePoints.setText("\u25C0");
        
        buttons.add(strength);
        buttons.add(dexterity);
        buttons.add(constitution);
        buttons.add(intelligence);
        buttons.add(wisdom);
        buttons.add(charisma);
        
        forceNumberInput(points);
        addMousePressedEventListener(addAndClose);
        addMousePressedEventListener(add);
        addMousePressedEventListener(close);
    }
    
    @FXML
    private void initializeFields() {
        pointsLeft.textProperty().bind(mainApp.mainChar.getBonusStatLeftProperty().asString("%s bonus point(s) left to add"));
        if(mainApp.mainChar.getBonusStatLeft() ==  1) {
            addPoints.setDisable(true);
        }
    }
    
    @FXML
    private void statChosen(MouseEvent me) {
        addAndClose.setDisable(false);
        add.setDisable(false);
        
        Button btn = (Button) me.getSource();
        
        for(Button button : buttons) {
            button.setStyle("-fx-border-width: 1");
        }
        
        btn.setStyle("-fx-border-width: 3");
        selected = btn;
    }
    
    @FXML
    private void addPoints() {
        removePoints.setDisable(false);
        currentLevel++;
        points.setText(currentLevel + "");
        if(currentLevel == mainApp.mainChar.getBonusStatLeft()) {
            addPoints.setDisable(true);
        }
    }
    
    @FXML
    private void removePoints() {
        addPoints.setDisable(false);
        currentLevel--;
        points.setText(currentLevel + "");
        if(currentLevel == 1) {
            removePoints.setDisable(true);
        }
    }
    
    private int checkValue(TextField tf, String min, String max) {
        if(tf.getText().equals("")){
            tf.setText(min);
        } else {
            try {
                int i = Integer.parseInt(tf.getText());
                if(i > Integer.parseInt(max)) {
                    tf.setText(max);
                }
            } catch (Exception e) {
                tf.setText(max);
            }
        }
        
        return Integer.parseInt(tf.getText());
    }
    
    private void forceNumberInput(TextField tf) {
        tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
    
    @FXML
    private void addAndClose() {
        addStatPoints();
        stage.close();
    }
    
    @FXML
    private void add() {
        add.getStyleClass().remove("buttonPressed");
        addStatPoints();
        points.setText("1");
        removePoints.setDisable(true);
        if(mainApp.mainChar.getBonusStatLeft() == 1){
            addPoints.setDisable(true);
        } else if(mainApp.mainChar.getBonusStatLeft() == 0) {
            addPoints.setDisable(true);
            add.setDisable(true);
            addAndClose.setDisable(true);
        }
    }
    
    private void addStatPoints() {
        int pointsAdded = checkValue(points, "1", "" + mainApp.mainChar.getBonusStatLeft());
        mainApp.mainChar.addBonusStatPoints(selected.getText(), pointsAdded);
        classTabController.listBonusPoints();
    }
    
    @FXML
    private void close() {
        stage.close();
    }
    
    private void addMousePressedEventListener(Button btn) {
        btn.setOnMousePressed((e) -> {
            btn.getStyleClass().add("buttonPressed");
        });
    }
    
    public void setMainApp(MainApp mainApp, Stage stage, ClassTabController classTabController) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.classTabController = classTabController;
        initializeFields();
    }
}
