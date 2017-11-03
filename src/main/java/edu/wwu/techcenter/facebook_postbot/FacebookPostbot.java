package edu.wwu.techcenter.facebook_postbot;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

import edu.wwu.techcenter.facebook_postbot.util.FBHandler;
import edu.wwu.techcenter.facebook_postbot.util.JSONParser;
import edu.wwu.techcenter.facebook_postbot.util.Workshop;

/**
 *
 * @author Connor J Hopkins
 */
public class FacebookPostbot {
	public static void main(String[] args) {
		final String STC_PAGE = "https://stc.wwu.edu/workshops";
		final int TRIES = 3;
		final long TIMEOUT_AMOUNT = 1000;

		int count = 0;
		while (count < TRIES) {
			String fbPageID = FBHandler.getPageID();
			// No environment variables were passed, no point in trying more
			if (fbPageID == null) {
				return;
			}

			// If in getting the workshop list there was an issue, it'll always return null
			ArrayList<Workshop> workshopList = JSONParser.getWorkshopList();
			if (workshopList == null) {
				count++;
				try {
					TimeUnit.MILLISECONDS.sleep(TIMEOUT_AMOUNT);
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
				continue;
			}

			// The list is only 0 if there were no valid workshops
			if (workshopList.size() == 0) {
				// TODO: Log this
				System.out.println("No workshops this week");
				return;
			}

			// If in getting the facebook client there was an issue, it'll always return
			// null
			// TODO: Code is being repeated here, made into a function somehow?
			FacebookClient fbClient = FBHandler.getFBClient();
			if (fbClient == null) {
				count++;
				try {
					TimeUnit.MILLISECONDS.sleep(TIMEOUT_AMOUNT);
				} catch (InterruptedException e) {
					e.printStackTrace();
					return;
				}
				continue;
			}

			String message = "Workshops happening this week:";

			for (Workshop workshop : workshopList) {
				message += "\n" + workshop.name + "\n" + "---" + workshop.date + "at " + workshop.start + "\n";
			}

			message += "\nTo sign up for a workshop, please visit our website at the link below. We hope to see you soon!\n";

			fbClient.publish(fbPageID + "/feed", FacebookType.class, Parameter.with("message", message),
					Parameter.with("link", STC_PAGE));
		}
	}
}
