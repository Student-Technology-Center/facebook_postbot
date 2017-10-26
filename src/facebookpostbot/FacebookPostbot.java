package facebookpostbot;

/**
 *
 * @author Connor J Hopkins
 */
public class FacebookPostbot {

    public static void main(String[] args) {
        try {
            jsonPage page = new jsonPage("http://west.wwu.edu/stcworkshops/workshop_json.asp");
            post p = new post(page.getJSONArray());
            p.makePost();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}
