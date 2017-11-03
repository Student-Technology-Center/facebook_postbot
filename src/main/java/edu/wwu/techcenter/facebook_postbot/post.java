package edu.wwu.techcenter.facebook_postbot;

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
    private FacebookClient FB_CLIENT;
    private String STC_PAGE;
    private String MESSAGE;
    private String PAGE_ID = null;

    public post(String url, String link) throws JSONException {
        String pageAccessToken = null;

        try {
            pageAccessToken = System.getenv("fb_page_access_token");
            this.PAGE_ID = System.getenv("fb_page_id");
        } catch (Exception e) {
            // TODO: Log this
            System.out.println("\nFAILED TO GET ENVIRONMENT VARIABLE\n\n" + e);
            
            if (PAGE_ID != null) {
                this.PAGE_ID = "";
            }
        }
        
        // TODO: Should be handled better
        if (pageAccessToken == null) {
            return;
        }

        this.STC_PAGE = link;
        this.FB_CLIENT = getFbClient(pageAccessToken);
        this.MESSAGE = new weeklyMessage(url).getMessage();
    }

    @SuppressWarnings("deprecation")
    private FacebookClient getFbClient(String accessToken) {
        FacebookClient facebookClient = null;
        int count = 0;
        
        while (count < 2) {
            try {
                facebookClient = new DefaultFacebookClient(accessToken);
                break;
            } catch (FacebookException e) {
                System.out.println("\nFAILED TO ESTABLISH FACEBOOK CLIENT\n\n" + e);
                count++;
            }
        }
        
        return facebookClient;
    }

    public void makePost() {
        this.FB_CLIENT.publish(PAGE_ID + "/feed", FacebookType.class,
                Parameter.with("message", this.MESSAGE),
                Parameter.with("link", this.STC_PAGE));
    }
}
