/**
 *
 * @author Connor J Hopkins
 */
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.types.FacebookType;
import org.json.JSONException;

public class post {
    private String PAGE_ACCESS_TOKEN, PAGE_ID;
    private final FacebookClient fbClient;
    private final String message, JSON_URL, STC_PAGE;
    
    public post(String url, String link) throws JSONException {
        try {
            this.PAGE_ACCESS_TOKEN = System.getenv("fb_page_access_token");
            this.PAGE_ID = System.getenv("fb_page_id");
        } catch (Exception e) {
            System.out.println("\nFAILED TO GET ENVIRONMENT VARIABLE\n\n" + e);
            this.PAGE_ACCESS_TOKEN = this.PAGE_ID = "";
        }
        this.JSON_URL = url;
        this.STC_PAGE = link;
        this.fbClient = getFbClient(this.PAGE_ACCESS_TOKEN);
        this.message = new weeklyMessage(this.JSON_URL).getMessage();
    }
    
    private FacebookClient getFbClient (String accessToken) {
        FacebookClient c = null;
        int count = 0;
        boolean go = true;
        while(go) {
            try {
                c = new DefaultFacebookClient(accessToken);
                go = false;
            } catch (FacebookException e) {
                if(++count > 2) {
                    System.out.println("\nFAILED TO ESTABLISH FACEBOOK CLIENT\n\n" + e); 
                    go = false;
                }
            }
        }
        return c;
    }
    
    public void makePost() {
        this.fbClient.publish(PAGE_ID + "/feed", 
                         FacebookType.class, 
                         Parameter.with("message",this.message), 
                         Parameter.with("link", this.STC_PAGE));
    }
}
