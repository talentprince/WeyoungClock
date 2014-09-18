package org.weyoung.clock;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.weyoung.clock.utils.LogUtils;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.widget.RemoteViews;

public class UpdateService extends Service {

	private static final String TAG = "UpdateService";
	private SimpleDateFormat df = new SimpleDateFormat("HHmmss");

	public static Context context;
	public static AppWidgetManager appWidgetManager;
	public static RemoteViews remoteViews;
	// number pics
	private int[] numberIcon = new int[] { R.drawable.n0, R.drawable.n1,
	        R.drawable.n2, R.drawable.n3, R.drawable.n4, R.drawable.n5,
	        R.drawable.n6, R.drawable.n7, R.drawable.n8, R.drawable.n9 };
	// number views
	private int[] numberView = new int[] { R.id.hour01, R.id.hour02,
	        R.id.minute01, R.id.minute02 };

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		LogUtils.i(TAG, "--service created--");
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_TIME_TICK); // time tick
		intentFilter.addAction(Intent.ACTION_TIME_CHANGED); // time modified
		registerReceiver(boroadcastReceiver, intentFilter);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LogUtils.i(TAG, "--service started--");
		updateUI(); // update
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(boroadcastReceiver);
	}

	private BroadcastReceiver boroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context acontext, Intent intent) {
			// update clock when time goes
			updateUI();
		}
	};

	private void updateUI() {
		String timeString = df.format(new Date());
		int num;
		for (int i = 0; i < numberView.length; i++) {
			num = timeString.charAt(i) - 48;
			remoteViews.setImageViewResource(numberView[i], numberIcon[num]);
		}
		// update widget UI
		ComponentName componentName = new ComponentName(context,
		        WeyoungClock.class);
		appWidgetManager.updateAppWidget(componentName, remoteViews);
	}
}
