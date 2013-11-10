package triforce.tech.seedersms;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * @author Josh Hanrahan
 *
 */
public class AlertSending extends Activity {
	GPSTracker gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alert_sending);
		// Show the Up button in the action bar.
		setupActionBar();
		gps = new GPSTracker(AlertSending.this);

		// check if GPS enabled
		if (gps.canGetLocation()) {

			double latitude = gps.getLatitude();
			double longitude = gps.getLongitude();

			Toast.makeText(
					getApplicationContext(),
					"Your Location is - \nLat: " + latitude + "\nLong: "
							+ longitude, Toast.LENGTH_LONG).show();
		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}
		String locLink = "http://maps.google.com/maps?saddr=" + gps.latitude
				+ "," + gps.longitude;
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String phNoSetting = preferences.getString("phNo", null);
		SmsManager manager = SmsManager.getDefault();
		String message = "EMERGENCY ALERT! Seeder has Stopped! Location: "
				+ locLink;
		manager.sendTextMessage(phNoSetting, null, message, null, null);

	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.alert_sending, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void sendMessage(View view) {
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		Uri uri = Uri.parse("mailto:joshua.hanrahan@gmail.com");
		intent.setData(uri);
		intent.putExtra(Intent.EXTRA_SUBJECT, "Fundamentals");
		intent.putExtra(Intent.EXTRA_TEXT, "This is a message");
		startActivity(Intent.createChooser(intent, "Send a message with: "));
	}

}
