import java.io.IOException;
import org.json.JSONException;

/**
 *
 * @author Connor J Hopkins
 */
public class FacebookPostbot {
    private static String JSON_URL = "http://west.wwu.edu/stcworkshops/workshop_json.asp";
    private static String STC_PAGE = "https://stc.wwu.edu/workshops";
    public static void main(String[] args) {
    try {
        post p = new post(this.JSON_URL, this.STC_PAGE);
        p.makePost();
    } catch(IOException | JSONException e) {
        e.printStackTrace();
    }
  }
}
