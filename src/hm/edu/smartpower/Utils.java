package hm.edu.smartpower;

import android.content.Context;
import android.content.Intent;

/**
 * Helper class providing methods and and often used constants to all classes
 * 
 * @author Andreas Bauer
 */
public final class Utils {

	static final String PREFS_NOTIFICATION = "notification";
	static final String SERVER_URL = null;
	/**
	 * Google API project id registered to use GCM. Important!!
	 */
	static final String SENDER_ID = "286995338387";
	/**
	 * Intent used to display a message in the screen.
	 */
	static final String DISPLAY_MESSAGE_ACTION = "hm.edu.smartpower.DISPLAY_MESSAGE";
	/**
	 * Intent's extra that contains the message to be displayed.
	 */
	static final String EXTRA_MESSAGE = "message";

	/**
	 * Notifies UI to display a message.
	 * 
	 * This method is defined in the common helper because it's used both by the UI and the background service.
	 */
	static void displayMessage(Context context, String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}
}
