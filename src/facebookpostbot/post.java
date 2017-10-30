/**
 *
 * @author Connor J Hopkins
 */

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.types.FacebookType;
import org.json.JSONArray;
import org.json.JSONException;

public class post {
    private final String PAGE_ACCESS_TOKEN = "";
    private final String PAGE_ID = "";
    private FacebookClient fbClient;
    private JSONArray workshopArray;
    private final String message, link;
    
    public post(JSONArray workshops, String link) throws JSONException {
        this.workshopArray = workshops;
        this.link = link;
        try {
            this.fbClient = new DefaultFacebookClient(this.PAGE_ACCESS_TOKEN);
        } catch (FacebookException e) {
            e.printStackTrace(); 
        }
        this.message = new weeklyMessage(this.workshopArray).getMessage();
    }
    
    public void makePost() {
        this.fbClient.publish(PAGE_ID + "/feed", 
                         FacebookType.class, 
                         Parameter.with("message",this.message), 
                         Parameter.with("link", this.link));
    }
}

