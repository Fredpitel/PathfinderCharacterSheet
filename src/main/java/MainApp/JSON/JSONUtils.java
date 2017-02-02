package MainApp.JSON;

import java.io.FileNotFoundException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 *
 * @author Fred
 */
public class JSONUtils {
    
    public static JSONObject jsonToObject(String nomFichier) {
        String jsonString = "";
        
        try {
            jsonString = Utf8File.loadFileIntoString(nomFichier);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(1);
        }
        return (JSONObject) JSONSerializer.toJSON(jsonString);
    }
    
    
    public static void objectToJson(String nomFichier, JSONObject jsonErreurs) {
        Utf8File.saveStringIntoFile(nomFichier, jsonErreurs.toString(2));
    }
}
