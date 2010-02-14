package com.sunlightfoundation.dwg;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class DoorWidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager manager, int[] appWidgetIds) {		
		// Perform this loop procedure for each widget that belongs to this provider
        final int length = appWidgetIds.length;
        for (int i=0; i<length; i++) {
            int appWidgetId = appWidgetIds[i];
            RemoteViews views = buildView(context, R.layout.widget);
            manager.updateAppWidget(appWidgetId, views);
        }
	}
	
	public static RemoteViews buildView(Context context, int layout) {
		RemoteViews views = new RemoteViews(context.getPackageName(), layout);
		
		Intent intent = new Intent(context, DoorService.class);
		views.setOnClickPendingIntent(R.id.widget_button, PendingIntent.getService(context, 0, intent, 0));
		
        return views;
	}
}
