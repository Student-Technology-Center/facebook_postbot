package edu.wwu.techcenter.facebook_postbot.util;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.exception.FacebookException;

public class FBHandler {
	private static final String FB_PAGE_ID = "1479556868760012";

	public static FacebookClient getFBClient() {
		
                String pageAccessToken = null;
                
                try {
                    Properties pro = new Properties();
                    FileInputStream in = new FileInputStream("src/bot.properties");
                    pro.load(in);
                    // getting values from property file
                    pageAccessToken = pro.getProperty("FB_TOKEN");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                
		if (pageAccessToken == null) {
			System.out.println("\nFAILED TO GET ENVIRONMENT VARIABLE\n\n");
			return null;
		}

		return fetchFBClient(pageAccessToken);
	}

	public static String getPageID() {
            return FB_PAGE_ID;
	}

	@SuppressWarnings("deprecation")
	private static FacebookClient fetchFBClient(String pageAccessToken) {
		FacebookClient facebookClient = null;
		try {
			facebookClient = new DefaultFacebookClient(pageAccessToken);
			return facebookClient;
		} catch (FacebookException e) {
			System.out.println("\nFAILED TO ESTABLISH FACEBOOK CLIENT\n\n" + e);
			return null;
		}
	}
}
