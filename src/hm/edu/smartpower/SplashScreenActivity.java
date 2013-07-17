package hm.edu.smartpower;

import static hm.edu.smartpower.Utils.SENDER_ID;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gcm.GCMRegistrar;

/**
 * SplashScreen of the app. If it is the first start, the app will be registered asyncronous at the GoogleCloudMessage server and gets an individual id.
 * 
 * @author Andreas Bauer
 * 
 */
public class SplashScreenActivity extends Activity {

	private Context context;

	/**
	 * First method to be called. Initialization of the screen.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splashscreen);
		checkNotNull(SENDER_ID, "SENDER_ID");
		context = this;
		// Starting registration
		new RegisterTask().execute(null, null, null);

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
			return true;
		case R.id.options_exit:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void checkNotNull(Object reference, String name) {
		if (reference == null) {
			throw new NullPointerException(getString(R.string.error_config, name));
		}
	}

	private class RegisterTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// Make sure the device has the proper dependencies.
			GCMRegistrar.checkDevice(context);
			GCMRegistrar.checkManifest(context);
			final String regId = GCMRegistrar.getRegistrationId(context);
			if (regId.equals("")) {
				// Automatically registers application on startup.
				// Only done if it is not already registered
				GCMRegistrar.register(context, SENDER_ID);
			}
			return null;
		}

		protected void onPostExecute(Void result) {
			// After finishing the registration open the Main activity
			Intent intent = new Intent(context, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}

}
