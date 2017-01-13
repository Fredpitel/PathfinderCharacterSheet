package Controller.view.NewCharacter;

import Controller.model.Character;
import Controller.MainApp;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class NewCharacterController {
    private MainApp mainApp;
    private int currentLevel = 1;
    
    @FXML
    private TextField charName;
    
    @FXML
    private TextField playerName;
    
    @FXML
    private TextField startLevels;
    
    @FXML
    private Button upLevels;
    
    @FXML
    private Button downLevels;
    
    @FXML
    private TextField plat;
    
    @FXML
    private TextField gold;
    
    @FXML
    private TextField silv;
    
    @FXML
    private TextField copp;
    
    @FXML
    private Button back;
    
    @FXML
    private Button createChar;
    
    public NewCharacterController(){}
    
    @FXML
    private void initialize() {
        upLevels.setText("\u25B2");
        downLevels.setText("\u25BC");
        
        forceNumberInput(startLevels);
        forceNumberInput(plat);
        forceNumberInput(gold);
        forceNumberInput(silv);
        forceNumberInput(copp);
        
        clearErrorBorder(charName);
        clearErrorBorder(playerName);
        
        addMousePressedEventListener(back);
        addMousePressedEventListener(createChar);
    }
    
    @FXML
    private void upLevels() {
        downLevels.setDisable(false);
        currentLevel++;
        startLevels.setText(currentLevel + "");
        if(currentLevel == 20) {
            upLevels.setDisable(true);
        }
    }
    
    @FXML
    private void downLevels() {
        upLevels.setDisable(false);
        currentLevel--;
        startLevels.setText(currentLevel + "");
        if(currentLevel == 0) {
            downLevels.setDisable(true);
        }
    }
    
    @FXML
    private void back() {
        back.getStyleClass().remove("buttonPressed");
        back.getStyleClass().add("button");
        mainApp.showMainMenu();
    }
    
    @FXML
    private void createChar() {
        createChar.getStyleClass().remove("buttonPressed");
        createChar.getStyleClass().add("button");
        
        if(charName.getText().equals("")) {
            charName.getStyleClass().add("textfield-error");
        } else if (playerName.getText().equals("")) {
            playerName.getStyleClass().add("textfield-error");
        } else {
            mainApp.mainChar = new Character(mainApp, charName.getText(), playerName.getText(), checkValue(startLevels, "1", "99"), checkValue(plat, "0", "999"), checkValue(gold, "0", "999"), checkValue(silv, "0", "999"), checkValue(copp, "0", "999"));
            mainApp.showMainSheet();   
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
    
    private void clearErrorBorder(TextField tf) {
        tf.textProperty().addListener((e) -> {
            tf.getStyleClass().remove("textfield-error");
            tf.getStyleClass().add("textfield");
        });
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
