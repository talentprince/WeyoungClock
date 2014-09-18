package org.weyoung.clock;

import org.weyoung.clock.utils.LogUtils;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class WeyoungClock extends AppWidgetProvider {
	private static final String TAG = "WeyoungClock";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
	        int[] appWidgetIds) {
		LogUtils.i(TAG, "--update--");
		// init remote view
		RemoteViews views = new RemoteViews(context.getPackageName(),
		        R.layout.clock);
		views.setImageViewResource(R.id.double_dot, R.drawable.dot);
		// give service what needed
		UpdateService.appWidgetManager = appWidgetManager;
		UpdateService.context = context;
		UpdateService.remoteViews = views;
		// start ui update service
		Intent intent = new Intent(context, UpdateService.class);
		context.startService(intent);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		LogUtils.i(TAG, "--deleted--");
		Intent intent = new Intent(context, UpdateService.class);
		context.stopService(intent);
	}
}
