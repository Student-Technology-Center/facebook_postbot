import java.io.IOException;
import org.json.JSONException;

/**
 *
 * @author Connor J Hopkins
 */
public class FacebookPostbot {

    public static void main(String[] args) {
        try {
            jsonPage page = new jsonPage("http://west.wwu.edu/stcworkshops/workshop_json.asp");
            post p = new post(page.getJSONArray(), "https://stc.wwu.edu/workshops");
            p.makePost();
        } catch(IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    
}
