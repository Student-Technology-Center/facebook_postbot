package facebookpostbot;

import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
/**
 *
 * @author Connor J Hopkins
 */
public class jsonPage {
    private JSONArray workshopArray;
    
    public jsonPage(String JSON_URL) throws IOException{
        try {
            workshopArray = jsonUrlToObject(JSON_URL).getJSONArray("workshops");
            //for(int i=0; i < workshopArray.length(); i++)
                //System.out.println(workshopArray.getJSONObject(i));//////////////////////////////////////
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
    /*
    * takes a json url and makes a json object.
    */
    private JSONObject jsonUrlToObject(String JSON_URL) {
        Document doc;
        try {
            doc = Jsoup.connect(JSON_URL).get();
        } catch(IOException e) {
            e.printStackTrace();
            doc = new Document("error");
        }
        
        String jsonText = doc.body().text();
        //System.out.println(doc.body().text());/////////////////////////////////////
        JSONObject obj;
        try{
            obj = new JSONObject(jsonText);
        } catch(JSONException e) {
            e.printStackTrace();
            obj = new JSONObject();
        }
        //System.out.print(obj);
        return obj;
    }
    
    public JSONArray getJSONArray() {
        return this.workshopArray;
    }
        
}

