package com.digitalnusantarastudio.bakingapp.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.digitalnusantarastudio.bakingapp.R;
import com.digitalnusantarastudio.bakingapp.activities.MainActivity;
import com.digitalnusantarastudio.bakingapp.data.IngredientContract;

import static com.digitalnusantarastudio.bakingapp.data.IngredientContract.BASE_CONTENT_URI;
import static com.digitalnusantarastudio.bakingapp.data.IngredientContract.PATH_INGREDIENTS;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        final String TAG = IngredientsWidgetProvider.class.getSimpleName();

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_provider);

        // Set the GridWidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, WidgetService.class);
        views.setRemoteAdapter(R.id.listViewWidget, intent);
        Log.d(TAG, views.toString());

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

