package Controller.view.ClassSelection;

import Controller.MainApp;
import Controller.JSON.JSONUtils;
import Controller.model.Requirements.Requirement;
import Controller.model.Requirements.RequirementFactory;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ClassSelectionController {
    private MainApp mainApp;
    private JSONArray classes;
    private final ArrayList<Button> buttons;
    private Button selected;
    private final ArrayList<String> errors;
    
    @FXML
    private Label levelsLeft;
    
    @FXML
    private VBox classBox;
    
    @FXML
    private Label className;
    
    @FXML
    private TextFlow classText;
    
    public ClassSelectionController(){
        this.buttons = new ArrayList();
        this.errors = new ArrayList();
    }
    
    @FXML
    private void initialize() {}
    
    @FXML
    private void initializeFields() {
        levelsLeft.setText(mainApp.mainChar.getRemainingLevels() + " level(s) left to add");
    }
    
    @FXML
    private void intializeSelection() {
        JSONObject jsonClasses = JSONUtils.jsonToObject("JSON Files/classes.json");
        
        classes = jsonClasses.getJSONArray("classes");

        for(int i = 0; i < classes.size(); i++){
            Button btn = new Button(classes.getJSONObject(i).getString("className"));
            btn.getStyleClass().add("button-trans");
            btn.setPrefWidth(548);
            classBox.getChildren().add(btn);
            buttons.add(btn);
            
            JSONArray requirements = classes.getJSONObject(i).getJSONArray("requirements");
            for(int j = 0; j < requirements.size(); j++) {
                JSONObject req = requirements.getJSONObject(j);
                Requirement requirement = new RequirementFactory().createRequirement(req.getString("type"));
                if(!requirement.validate(mainApp.mainChar, req)) {
                    btn.getStyleClass().add("button-invalid");
                    errors.add(req.getString("error_message"));
                }
            }
            
            addClickListener(btn, i);
        }
    }
    
    private void addClickListener(Button btn, int index) {
        btn.setOnMouseClicked((e) -> {
            for(int i = 0; i < buttons.size(); i++){
                buttons.get(i).setStyle("-fx-border-width: 1");
            }
            btn.setStyle("-fx-border-width: 3");
            classText.getChildren().clear();
            showClassDescription(index);
        });
        
        selected = btn;
    }
    
    private void showClassDescription(int index) {
        JSONArray description = classes.getJSONObject(index).getJSONArray("description");
        
        className.setText(classes.getJSONObject(index).getString("className") + "\n\n");
        
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
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        initializeFields();
        intializeSelection();
    }
}
