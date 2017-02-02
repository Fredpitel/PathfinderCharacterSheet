package MainApp.view.MainSheet.BackgroundTab.AlignmentSelection;

import MainApp.MainApp;
import MainApp.view.MainSheet.BackgroundTab.BackgroundTabController;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AlignmentSelectionController {
    private MainApp mainApp;
    private Stage stage;
    private final ArrayList<Button> buttons;
    private Button selected;
    
    @FXML
    private Button select;
    
    @FXML
    private Button cancel;
    
    @FXML
    private VBox alignmentBox;
    
    @FXML
    private Label alignmentName;
    
    @FXML
    private TextFlow alignmentText;
    
    public AlignmentSelectionController(){
        this.buttons = new ArrayList();
    }
    
    @FXML
    private void initialize() {
        addMousePressedEventListener(select);
        addMousePressedEventListener(cancel);
    }
    
    private void initializeRace() {
        Set<Map.Entry> alignments = mainApp.jsonAlignments.entrySet();
        for(Map.Entry jsonAlignments : alignments){
            JSONObject raceObject = (JSONObject) jsonAlignments.getValue();
            Button btn = new Button(raceObject.getString("alignmentName"));
            btn.getStyleClass().add("button-trans");
            btn.setPrefWidth(548);
            alignmentBox.getChildren().add(btn);
            buttons.add(btn);
            
            addClickListener(btn, raceObject);
        }
    }
    
    private void showAlignmentDescription(JSONObject jsonAlignment) {
        JSONArray description = jsonAlignment.getJSONArray("description");
        
        alignmentName.setText(jsonAlignment.getString("alignmentName") + "\n\n");
        
        Text prefaceText = new Text(description.getJSONObject(0).getString("preface") + "\n");
        prefaceText.setStyle("-fx-font-style: italic");
        alignmentText.getChildren().add(prefaceText);
        
        for(int i = 1; i < description.size(); i++){
            Text titleText = new Text(description.getJSONObject(i).getString("title"));
            if(!titleText.getText().equals("")) {
                titleText.setStyle("-fx-font-weight: bold");
            }
            Text valueText = new Text(description.getJSONObject(i).getString("value") + "\n");
            alignmentText.getChildren().addAll(titleText, valueText);
        }
    }
    
    private void addClickListener(Button btn, JSONObject jsonAlignment) {
        btn.setOnMouseClicked((e) -> {
            select.setDisable(false);
            
            for(int i = 0; i < buttons.size(); i++){
                buttons.get(i).setStyle("-fx-border-width: 1");
            }
            
            btn.setStyle("-fx-border-width: 3");
            alignmentText.getChildren().clear();
            showAlignmentDescription(jsonAlignment);
            selected = btn;
        });
    }
    
    private void addMousePressedEventListener(Button btn) {
        btn.setOnMousePressed((e) -> {
            btn.getStyleClass().add("buttonPressed");
        });
    }
    
    @FXML
    private void select() {
        JSONObject jsonAlignment = mainApp.jsonAlignments.getJSONObject(selected.getText());
        mainApp.mainChar.setAlignment(selected.getText(), jsonAlignment.getString("abb"));
        stage.close();
    }
    
    @FXML
    private void cancel() {
        stage.close();
    }
    
    public void setMainApp(MainApp mainApp, Stage stage, BackgroundTabController backgroundTabController) {
        this.mainApp = mainApp;
        this.stage = stage;
        initializeRace();
    }
}
