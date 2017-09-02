package com.digitalnusantarastudio.bakingapp.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;

import com.digitalnusantarastudio.bakingapp.R;
import com.digitalnusantarastudio.bakingapp.data.IngredientContract;

import static com.digitalnusantarastudio.bakingapp.data.IngredientContract.BASE_CONTENT_URI;
import static com.digitalnusantarastudio.bakingapp.data.IngredientContract.PATH_INGREDIENTS;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Uri INGREDIENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENTS).build();
        Cursor cursor = context.getContentResolver().query(
                INGREDIENT_URI,
                null,
                null,
                null,
                null
        );

        String ingredients = "";
        if (cursor != null && cursor.getCount() > 0) {
            try {
                while (cursor.moveToNext()) {
                    int quantity = cursor.getInt(cursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_QUANTITY));
                    String measure = cursor.getString(cursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_MEASURE));
                    String ingredient = cursor.getString(cursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_NAME));
                    ingredients += quantity + " " + measure + " " + ingredient + "\n";
                }
            } finally {
                cursor.close();
            }
        }

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_provider);
        views.setTextViewText(R.id.ingredients_widget_text, ingredients);

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

