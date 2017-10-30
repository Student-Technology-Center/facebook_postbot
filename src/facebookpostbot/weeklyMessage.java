import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Connor J Hopkins
 */
public class weeklyMessage {
    private String weeklyMessage = "Workshops happening this week: \n";
    private final JSONArray workshopArray;
    
    public weeklyMessage(JSONArray workshops) throws JSONException{
        this.workshopArray = workshops;
        
        Date nextWeek = addWeek(new Date());
        String workshopDate;
        JSONObject workshop;
        
        int len = this.workshopArray.length();
        for(int i = 0; i < len; i++) {
            
            workshop = this.workshopArray.getJSONObject(i);
            workshopDate = workshop.getString("date");
            
            if(earlyEnough(stringToDate(workshopDate), nextWeek))
                this.weeklyMessage+=("\n"+workshop.getString("name") + "\n" 
                              + "---"+workshop.getString("date")+ "at " + workshop.getString("start")) + "\n";
        }
        this.weeklyMessage+="\nTo sign up for a workshop, please visit our website at the link below. We hope to see you soon!\n";
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
    
    public String getMessage() {
        return this.weeklyMessage;
    }
}

