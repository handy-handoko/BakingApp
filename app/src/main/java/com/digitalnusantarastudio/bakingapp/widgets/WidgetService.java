package com.digitalnusantarastudio.bakingapp.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.digitalnusantarastudio.bakingapp.R;
import com.digitalnusantarastudio.bakingapp.data.IngredientContract;

import java.util.ArrayList;

import static com.digitalnusantarastudio.bakingapp.data.IngredientContract.BASE_CONTENT_URI;
import static com.digitalnusantarastudio.bakingapp.data.IngredientContract.PATH_INGREDIENTS;

/**
 * Created by luqman on 03/09/17.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return(new WidgetFactory(this.getApplicationContext()));
    }

    class WidgetFactory implements RemoteViewsFactory {
        Context context;
        Cursor mCursor;


        public WidgetFactory(Context context) {
            this.context = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            Uri INGREDIENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENTS).build();
            mCursor = context.getContentResolver().query(
                    INGREDIENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        public void onDestroy() {
            mCursor.close();
        }

        @Override
        public int getCount() {
            if (mCursor == null) return 0;
            return mCursor.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (mCursor == null || mCursor.getCount() == 0) return null;
            mCursor.moveToPosition(position);

            int quantity = mCursor.getInt(mCursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_QUANTITY));
            String measure = mCursor.getString(mCursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_MEASURE));
            String ingredient_name = mCursor.getString(mCursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_INGREDIENT_NAME));
            String ingredient = quantity + " " + measure + " " + ingredient_name;

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_text);
            views.setTextViewText(R.id.ingredients_widget_text, ingredient);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

}
