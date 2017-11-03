
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
    private final FacebookClient FB_CLIENT;
    private final String PAGE_ID;
    private final STC_PAGE;
    private final String MESSAGE;

    public post(String url, String link) throws JSONException {
        String pageAccessToken;

        try {
            pageAccessToken = System.getenv("fb_page_access_token");
            this.PAGE_ID = System.getenv("fb_page_id");
        } catch (Exception e) {
            // TODO: Log this
            System.out.println("\nFAILED TO GET ENVIRONMENT VARIABLE\n\n" + e);
            this.PAGE_ID = "";
        }

        this.STC_PAGE = link;
        this.fbClient = getFbClient(pageAccessToken);
        this.message = new weeklyMessage(url).getMessage();
    }

    private FacebookClient getFbClient(String accessToken) {
        FacebookClient facebookClient;
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
        
        return c;
    }

    public void makePost() {
        this.fbClient.publish(PAGE_ID + "/feed", FacebookType.class,
                Parameter.with("message", this.message),
                Parameter.with("link", this.STC_PAGE));
    }
}
