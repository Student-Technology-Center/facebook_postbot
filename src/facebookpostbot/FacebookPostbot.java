import org.json.JSONException;

/**
 *
 * @author Connor J Hopkins
 */
public class FacebookPostbot {
    private static final String JSON_URL = "http://west.wwu.edu/stcworkshops/workshop_json.asp";
    private static final String STC_PAGE = "https://stc.wwu.edu/workshops";

    public static void main(String[] args) {

        boolean go = true;
        int count = 0;
        while (go) {
            try {
                post p = new post(JSON_URL, STC_PAGE);
                p.makePost();
                go = false;
            } catch (JSONException e) {
                if (++count > 2) {
                    System.out.println("\nFAILED TO MAKE POST \n\n" + e);
                    go = false;
                }
            }
        }
    }
}
