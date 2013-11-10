package triforce.tech.seedersms;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author Josh Hanrahan
 * 
 */
public class EnterPhone extends Activity {
	EditText phoneNumberField;
	Button nxtButton;
	File folder = new File(Environment.getExternalStorageDirectory().toString()
			+ "/SeederSMS/data");
	String phoneNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_phone);

		startService(new Intent(this, FileObserverService.class));
		folder.mkdirs();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.enter_phone, menu);
		return true;
	}

	public void MainActivity(View view) {
		phoneNumberField = (EditText) findViewById(R.id.phNumber);
		nxtButton = (Button) findViewById(R.id.nxtButton);

		phoneNumber = phoneNumberField.getText().toString();

		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("phNo", phoneNumber);
		editor.commit();
		System.out.println("ph = " + phoneNumber);
		Intent intent = new Intent(this, MainActivity.class);

		startActivity(intent);
	}

}
