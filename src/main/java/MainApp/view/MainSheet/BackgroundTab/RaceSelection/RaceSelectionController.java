package MainApp.view.MainSheet.BackgroundTab.RaceSelection;

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
import javafx.util.Pair;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class RaceSelectionController {
    private MainApp mainApp;
    private Stage stage;
    private final ArrayList<Button> buttons;
    private Button selected;
    
    @FXML
    private Button select;
    
    @FXML
    private Button cancel;
    
    @FXML
    private VBox raceBox;
    
    @FXML
    private Label raceName;
    
    @FXML
    private TextFlow raceText;
    
    public RaceSelectionController(){
        this.buttons = new ArrayList();
    }
    
    @FXML
    private void initialize() {
        addMousePressedEventListener(select);
        addMousePressedEventListener(cancel);
    }
    
    private void initializeRace() {
        Set<Map.Entry> races = mainApp.jsonRaces.entrySet();
        for(Map.Entry jsonRaces : races){
            JSONObject raceObject = (JSONObject) jsonRaces.getValue();
            Button btn = new Button(raceObject.getString("raceName"));
            btn.getStyleClass().add("button-trans");
            btn.setPrefWidth(548);
            raceBox.getChildren().add(btn);
            buttons.add(btn);
            
            addClickListener(btn, raceObject);
        }
    }
    
    private void showRaceDescription(JSONObject jsonClass) {
        JSONArray description = jsonClass.getJSONArray("description");
        
        raceName.setText(jsonClass.getString("raceName") + "\n\n");
        
        for(int i = 0; i < description.size(); i++){
            Text titleText = new Text(description.getJSONObject(i).getString("title"));
            if(!titleText.getText().equals("")) {
                titleText.setStyle("-fx-font-weight: bold");
            }
            Text valueText = new Text(description.getJSONObject(i).getString("value") + "\n");
            raceText.getChildren().addAll(titleText, valueText);
        }
    }
    
    private void addClickListener(Button btn, JSONObject jsonRace) {
        btn.setOnMouseClicked((e) -> {
            select.setDisable(false);
            
            for(int i = 0; i < buttons.size(); i++){
                buttons.get(i).setStyle("-fx-border-width: 1");
            }
            
            btn.setStyle("-fx-border-width: 3");
            raceText.getChildren().clear();
            showRaceDescription(jsonRace);
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
        JSONArray jsonModifiers =  mainApp.jsonRaces.getJSONObject(selected.getText()).getJSONArray("modifiers");
        ArrayList<Pair> modifiers = new ArrayList<>();
        for(int i = 0; i < jsonModifiers.size(); i++) {
            JSONObject jsonModifier = jsonModifiers.getJSONObject(i);
            Pair pair = new Pair(jsonModifier.getString("stat"), jsonModifier.getInt("modifier"));
            modifiers.add(pair);
        }
        mainApp.mainChar.setRace(selected.getText(), modifiers);
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
