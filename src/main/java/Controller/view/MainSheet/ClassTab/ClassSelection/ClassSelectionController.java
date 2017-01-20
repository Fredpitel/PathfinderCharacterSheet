package Controller.view.MainSheet.ClassTab.ClassSelection;

import Controller.MainApp;
import Controller.JSON.JSONUtils;
import Controller.model.Requirements.Requirement;
import Controller.model.Requirements.RequirementFactory;
import Controller.view.MainSheet.ClassTab.ClassTabController;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ClassSelectionController {
    private MainApp mainApp;
    private Stage stage;
    private ClassTabController classTabController;
    private final ArrayList<Button> buttons;
    private Button selected;
    private int currentLevel = 1;
    
    @FXML
    private Label levelsLeft;
    
    @FXML
    private VBox classBox;
    
    @FXML
    private Label className;
    
    @FXML
    private TextFlow classText;
    
    @FXML
    private Button addLevels;
    
    @FXML
    private Button removeLevels;
    
    @FXML
    private TextField levels;
    
    @FXML
    private Button addAndClose;
    
    @FXML
    private Button add;
    
    @FXML
    private Button close;
    
    public ClassSelectionController(){
        this.buttons = new ArrayList();
    }
    
    @FXML
    private void initialize() {
        addLevels.setText("\u25B6");
        removeLevels.setText("\u25C0");
        
        forceNumberInput(levels);
        addMousePressedEventListener(addAndClose);
        addMousePressedEventListener(add);
        addMousePressedEventListener(close);
    }
    
    @FXML
    private void initializeFields() {
        levelsLeft.textProperty().bind(mainApp.mainChar.getRemainingLevelsProperty().asString("%s level(s) left to add"));
        if(mainApp.mainChar.getRemainingLevels() ==  1) {
            addLevels.setDisable(true);
        }
    }
    
    @FXML
    private void intializeSelection() {
        Set<Map.Entry> classes = mainApp.jsonClasses.entrySet();
        for(Map.Entry jsonClass : classes){
            JSONObject classObject = (JSONObject) jsonClass.getValue();
            Button btn = new Button(classObject.getString("className"));
            btn.getStyleClass().add("button-trans");
            btn.setPrefWidth(548);
            classBox.getChildren().add(btn);
            buttons.add(btn);
            
            if(getErrors(classObject).size() > 0) {
                btn.getStyleClass().add("button-invalid");
            }
            
            addClickListener(btn, classObject);
        }
    }
    
    private void addClickListener(Button btn, JSONObject jsonClass) {
        btn.setOnMouseClicked((e) -> {
            addAndClose.setDisable(false);
            add.setDisable(false);
            
            for(int i = 0; i < buttons.size(); i++){
                buttons.get(i).setStyle("-fx-border-width: 1");
            }
            
            btn.setStyle("-fx-border-width: 3");
            classText.getChildren().clear();
            showClassDescription(jsonClass);
            selected = btn;
        });
    }
    
    private void showClassDescription(JSONObject jsonClass) {
        JSONArray description = jsonClass.getJSONArray("description");
        
        className.setText(jsonClass.getString("className") + "\n\n");
        
        ArrayList<String> errors = getErrors(jsonClass);
        
        if(errors.size() > 0) {
            for(String error: errors) {
                Text errorText = new Text(error);
                errorText.setFill(Color.RED);
                classText.getChildren().add(errorText);
            }
            Text spacing = new Text("\n");
            classText.getChildren().add(spacing);
        }
        
        for(int i = 0; i < description.size(); i++){
            Text titleText = new Text(description.getJSONObject(i).getString("title"));
            if(!titleText.getText().equals("")) {
                titleText.setStyle("-fx-font-weight: bold");
            }
            Text valueText = new Text(description.getJSONObject(i).getString("value") + "\n");
            classText.getChildren().addAll(titleText, valueText);
        }
    }
    
    private ArrayList<String> getErrors(JSONObject jsonClass) {
        ArrayList<String> errors = new ArrayList();
        JSONArray requirements =jsonClass.getJSONArray("requirements");
        
        for(int j = 0; j < requirements.size(); j++) {
            JSONObject req = requirements.getJSONObject(j);
            Requirement requirement = new RequirementFactory().createRequirement(req.getString("type"));
            if(!requirement.validate(mainApp.mainChar, req)) {
                errors.add(req.getString("error_message"));
            }
        }
        
        return errors;
    }
    
    @FXML
    private void addLevels() {
        removeLevels.setDisable(false);
        currentLevel++;
        levels.setText(currentLevel + "");
        if(currentLevel == mainApp.mainChar.getRemainingLevels()) {
            addLevels.setDisable(true);
        }
    }
    
    @FXML
    private void removeLevels() {
        addLevels.setDisable(false);
        currentLevel--;
        levels.setText(currentLevel + "");
        if(currentLevel == 1) {
            removeLevels.setDisable(true);
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
        createClassLevel();
        stage.close();
    }
    
    @FXML
    private void add() {
        add.getStyleClass().remove("buttonPressed");
        createClassLevel();
        levels.setText("1");
        removeLevels.setDisable(true);
        if(mainApp.mainChar.getRemainingLevels() == 1){
            addLevels.setDisable(true);
        } else if(mainApp.mainChar.getRemainingLevels() == 0) {
            addLevels.setDisable(true);
            add.setDisable(true);
            addAndClose.setDisable(true);
        }
    }
    
    private void createClassLevel() {
        int levelsAdded = checkValue(levels, "1", "" + mainApp.mainChar.getRemainingLevels());
        mainApp.mainChar.addCharLevels(mainApp.jsonClasses.getJSONObject(selected.getText()), levelsAdded);
        classTabController.listLevels();
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
        intializeSelection();
    }
}
