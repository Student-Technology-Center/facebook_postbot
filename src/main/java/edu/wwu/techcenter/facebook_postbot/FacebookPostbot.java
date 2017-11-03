package edu.wwu.techcenter.facebook_postbot;

import org.json.JSONException;

/**
 *
 * @author Connor J Hopkins
 */
public class FacebookPostbot {
    public static void main(String[] args) {
        final String JSON_URL = "http://west.wwu.edu/stcworkshops/workshop_json.asp";
        final String STC_PAGE = "https://stc.wwu.edu/workshops";
        
        post p = null;

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
