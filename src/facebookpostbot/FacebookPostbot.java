import org.json.JSONException;

/**
 *
 * @author Connor J Hopkins
 */
public class FacebookPostbot {
    private final String JSON_URL = "http://west.wwu.edu/stcworkshops/workshop_json.asp";
    private final String STC_PAGE = "https://stc.wwu.edu/workshops";

    public static void main(String[] args) {
        post p;

        int count = 0;
        while (count < 2) {
            // TODO: Cleaner for now but we shouldn't be handling the error
            // here.
            try {
                p = new post(JSON_URL, STC_PAGE);
            } catch (JSONException e) {
                System.out.println("\nFAILED TO MAKE POST \n\n" + e);
                count++;
            }
        }

        if (p == null) {
            return;
        }

        p.makePost();
    }
}
