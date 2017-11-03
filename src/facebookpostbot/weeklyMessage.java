import java.text.ParseException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Connor J Hopkins
 */
public class weeklyMessage {
    private final String weeklyMessage, JSON_URL;
    private final JSONArray workshopArray;

    public weeklyMessage(String url) throws JSONException {
        this.JSON_URL = url;
        this.workshopArray = jsonUrlToObject(this.JSON_URL)
                .getJSONArray("workshops");
        this.weeklyMessage = populateMessage(this.workshopArray);
    }

    private String populateMessage(JSONArray workshopArray) throws JSONException {
	Date nextWeek = addWeek(new Date());
	JSONObject workshop;
        String workshopDate, message;
        message = "Workshops happening this week:";
		
        int len = workshopArray.length();
        for(int i = 0; i < len; i++) {
            workshop = workshopArray.getJSONObject(i);
            workshopDate = workshop.getString("date");
            
            //true iff date1 is before or == date2
            if(stringToDate(workshopDate).compareTo(nextWeek) <= 0) {
                message +=   "\n" + workshop.getString("name") + "\n" + "---"+ workshop.getString("date") + "at "+ workshop.getString("start") + "\n";
        }
        message += "\nTo sign up for a workshop, please visit our website at the link below. We hope to see you soon!\n";
        return message;
    }

    /*
     * Add 7 days to a Date object
     */
    private Date addWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }

    /*
     * makes a string in MM/dd/yyy format into a Date object
     */
    private Date stringToDate(String d) {
        String sdate = d;
        Date date = null;
        try {
            date = new SimpleDateFormat("MM/dd/yyyy").parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    private JSONObject jsonUrlToObject(String JSON_URL) throws JSONException {
        Document doc;
        JSONObject obj = null;

        String error = "GET JSON FROM WEBPAGE";
        boolean go = true;
        int count = 0;
        while (go) {
            try {
                doc = Jsoup.connect(JSON_URL).get();
                error = "CREATE JSON OBJECT";
                obj = new JSONObject(doc.body().text());
                go = false;
            } catch (IOException e) {
                if (++count > 2) {
                    System.out.println("\nFAILED TO " + error + "\n\n" + e);
                    go = false;
                }
            }
        }
        return obj;
    }

    public String getMessage() {
        return this.weeklyMessage;
    }
}
