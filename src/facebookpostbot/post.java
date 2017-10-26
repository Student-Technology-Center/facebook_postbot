
/**
 *
 * @author Connor J Hopkins
 */
package facebookpostbot;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.types.FacebookType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class post {
    private final String PAGE_ACCESS_TOKEN = "";
    private final String PAGE_ID = "";
    private FacebookClient fbClient;
    private JSONArray workshopArray;
    private final String message, link;
    
    public post(JSONArray arr) throws JSONException {
        this.link = "https://stc.wwu.edu/workshops";
        try {
            this.workshopArray = arr;
            fbClient = new DefaultFacebookClient(PAGE_ACCESS_TOKEN);
        } catch (FacebookException e) {
            e.printStackTrace(); 
        }
        this.message = getWeeklyMessage();
    }
    
    /*
    * gets message from passed JSONArray of dated workshops
    */
    private String getWeeklyMessage() throws JSONException {
        String weeklyMessage = "";
        Date today = new Date();
        Date nextWeek = addWeek(today);

        String workshopDate;
        JSONObject workshop;
        
        int len = this.workshopArray.length();
        for(int i = 0; i < len; i++) {
            workshop = workshopArray.getJSONObject(i);
            workshopDate = workshop.getString("date");
            
            if(earlyEnough(stringToDate(workshopDate), nextWeek))
                weeklyMessage+=("On "+ workshop.getString("date")+ workshop.getString("instuctor") 
                                + "teaches "+ workshop.getString("name") + "\n");
        }
            //System.out.println(weeklyMessage);/////////////////////////////////////////////////
        return weeklyMessage;
    }
    
    private boolean earlyEnough(Date date1, Date date2) {
        return (date1.compareTo(date2) <= 0); //true iff date1 is before or = date2
    }
    
    /*
    * Add 7 days to a Date object
    */
    public static Date addWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 7); 
        return cal.getTime();
    }
    
    /*
    * post a message and a link to a page
    */
    public void makePost() {
        fbClient.publish(PAGE_ID + "/feed", 
                         FacebookType.class, 
                         Parameter.with("message","THIS WEEK'S WORKSHOPS: \n"+this.message), 
                         Parameter.with("link", link));
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

    
}
