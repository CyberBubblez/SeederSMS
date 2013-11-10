package triforce.tech.seedersms;

import android.app.Service;
import android.content.Intent;
import android.os.FileObserver;
import android.os.IBinder;
import android.util.Log;

public class FileObserverService extends Service {
	public final String TAG = "DEBUG";
	public static FileObserver observer;

	public FileObserverService() {

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startid) {
		Log.d(TAG, "onStart");

		final String pathToWatch = android.os.Environment
				.getExternalStorageDirectory().toString() + "/SeederSMS/data/";

		observer = new FileObserver(pathToWatch) {

			public void onEvent(int event, String file) {

				if ((FileObserver.CREATE & event) != 0) {
					Intent dialogIntent = new Intent(getBaseContext(),
							AlertSending.class);
					dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					getApplication().startActivity(dialogIntent);
				}
				if ((FileObserver.MODIFY & event) != 0) {
					Intent dialogIntent = new Intent(getBaseContext(),
							AlertSending.class);
					dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					getApplication().startActivity(dialogIntent);
				} else {
					return;
				}
			}
		};
		observer.startWatching();
		return Service.START_NOT_STICKY;

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
