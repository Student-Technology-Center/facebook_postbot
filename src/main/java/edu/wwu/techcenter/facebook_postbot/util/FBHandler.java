package edu.wwu.techcenter.facebook_postbot.util;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.exception.FacebookException;

public class FBHandler {
	private static final String FB_TOKEN = "fb_page_access_token";
	private static final String FB_PAGE_ID = "fb_page_id";

	public static FacebookClient getFBClient() {
		String pageAccessToken = System.getenv(FB_TOKEN);

		if (pageAccessToken == null) {
			System.out.println("\nFAILED TO GET ENVIRONMENT VARIABLE\n\n");
			return null;
		}

		return fetchFBClient(pageAccessToken);
	}

	public static String getPageID() {
		String pageID = System.getenv(FB_PAGE_ID);
		if (pageID == null) {
			System.out.println("\nFAILED TO GET ENVIRONMENT VARIABLE\n\n");
			return null;
		}

		return pageID;
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
