package hm.edu.smartpower;

import static hm.edu.smartpower.Utils.DISPLAY_MESSAGE_ACTION;
import static hm.edu.smartpower.Utils.EXTRA_MESSAGE;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Main Screen of the app. Popups for notifications shown. It is possible to acitivate/deactivate the notifications here.
 * 
 * @author Andreas Bauer
 * 
 */

public class MainActivity extends Activity {

	TextView mDisplay;
	static String lastMessage;
	public AlertDialog myAlertDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mDisplay = (TextView) findViewById(R.id.textView_main_activity_message);
		registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.options_clear:
			mDisplay.setText(null);
			return true;
		case R.id.options_exit:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Show the last notification if there is one every time we reenter the screen
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if (lastMessage != null && lastMessage != "") {
			mDisplay.setText(lastMessage);
			buildAltert(lastMessage);
			lastMessage = "";
		}

	}

	/**
	 * Unregister the receiver if the acitivty is destroyed. Necessary!
	 */
	@Override
	protected void onDestroy() {
		unregisterReceiver(mHandleMessageReceiver);
		super.onDestroy();
	}

	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			mDisplay.setText(newMessage + "\n");
			buildAltert(newMessage);
			lastMessage = newMessage + "\n";
		}
	};

	public void saveActivate(View v) {
		SharedPreferences prefs = this.getSharedPreferences("prefs", Context.MODE_PRIVATE);
		prefs.edit().putBoolean(Utils.PREFS_NOTIFICATION, true).commit();
		mDisplay.setText("Benachrichtigungen aktiviert");
	}

	public void saveDeactivate(View v) {
		SharedPreferences prefs = this.getSharedPreferences("prefs", Context.MODE_PRIVATE);
		prefs.edit().putBoolean(Utils.PREFS_NOTIFICATION, false).commit();
		mDisplay.setText("Benachrichtigungen deaktiviert");

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Build the alert here, set the app name set as title.
	 * 
	 * @param message
	 */
	public void buildAltert(String message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		if (myAlertDialog != null && myAlertDialog.isShowing()) {
			myAlertDialog.dismiss();
		}
		// set title
		alertDialogBuilder.setTitle(getString(R.string.app_name));
		// set dialog message
		alertDialogBuilder.setMessage(message).setCancelable(false).setNeutralButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// Do nothing here, just close the alert.
			}
		});
		myAlertDialog = alertDialogBuilder.create();
		// show the alert
		myAlertDialog.show();

	}
}