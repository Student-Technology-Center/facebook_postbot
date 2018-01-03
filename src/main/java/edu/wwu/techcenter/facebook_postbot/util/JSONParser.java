package edu.wwu.techcenter.facebook_postbot.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JSONParser {
	private final static String JSON_URL = "http://west.wwu.edu/stcworkshops/workshop_json.asp";

	public static ArrayList<Workshop> getWeeklyWorkshopList() {
		JSONArray jsonArray = getWorkshopJSONArray();

		if (jsonArray == null) {
			return null;
		}

		ArrayList<Workshop> workshopList = new ArrayList<Workshop>();
		Date nextWeek = getNextWeek();

		int len = jsonArray.length();
		for (int i = 0; i < len; i++) {
			JSONObject workshopObject;
			String stringWorkshopWeek;
			Workshop workshop;

			try {
				// All 3 lines are capable of throwing JSONExceptions
				workshopObject = jsonArray.getJSONObject(i);
				stringWorkshopWeek = workshopObject.getString("date");
				workshop = new Workshop(workshopObject.getString("name"), stringWorkshopWeek,
						workshopObject.getString("start"));
			} catch (JSONException e) {
				e.printStackTrace();
				return null;
			}

			Date workshopWeek = stringToDate(stringWorkshopWeek);
			// I really doubt this will, but just in case
			if (workshopWeek == null) {
				return null;
			}

			if (workshopWeek.compareTo(nextWeek) <= 0) {
				workshopList.add(workshop);
			}
		}

		return workshopList;
	}

	private static Date getNextWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 7);
		return cal.getTime();
	}

	private static Date stringToDate(String stringDate) {
		Date date;
		try {
			date = new SimpleDateFormat("MM/dd/yyyy").parse(stringDate);
			return date;
		} catch (ParseException e) {
			// Why does this even throw errors...
			e.printStackTrace();
			return null;
		}
	}

	// Grabs the document from the JSON_URL
	// Returns null if it fails
	private static Document grabDocument() {
		try {
			Document doc = Jsoup.connect(JSON_URL).get();
			return doc;
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
	}

	// Grabs the JSONArray from the document
	private static JSONArray getWorkshopJSONArray() {
		Document doc = grabDocument();

		if (doc == null) {
			return null;
		}

		try {
			JSONArray workshopJSONArray = new JSONObject(doc.body().text()).getJSONArray("workshops");
			return workshopJSONArray;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
