package com.digitalnusantarastudio.bakingapp.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.digitalnusantarastudio.bakingapp.R;
import com.digitalnusantarastudio.bakingapp.Utils.NetworkUtils;
import com.digitalnusantarastudio.bakingapp.adapter.RecipeAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.FormBody;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<JSONArray>,
        RecipeAdapter.ListItemClickListener{
    private RecipeAdapter adapter;
    private static final int ASYNCTASK_LOADER = 123;
    private static final String TAG = MainActivity.class.getSimpleName();
    SwipeRefreshLayout swipe_refresh_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView.LayoutManager layoutManager;
        //detecting if using tablet or phone.
        //https://stackoverflow.com/questions/35237549/change-layoutmanager-depending-on-device-format
        if(getResources().getBoolean(R.bool.is_phone)){
            layoutManager = new LinearLayoutManager(this);
        } else { //if using tablet. use GridLayoutManager instead.
            layoutManager = new GridLayoutManager(this, 3);
        }

        swipe_refresh_layout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                reload_data();
            }
        });
        RecyclerView recipe_recycler_view = (RecyclerView)findViewById(R.id.recipe_recycler_view);
        recipe_recycler_view.setLayoutManager(layoutManager);
        adapter = new RecipeAdapter(this);
        recipe_recycler_view.setAdapter(adapter);
        reload_data();
        getSupportActionBar().setTitle("Baking App");
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void reload_data(){
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader loader = loaderManager.getLoader(ASYNCTASK_LOADER);
        if(isOnline()){
            if (loader == null) {
                loaderManager.initLoader(ASYNCTASK_LOADER, null, this);
            } else {
                loaderManager.restartLoader(ASYNCTASK_LOADER, null, this);
            }
        } else {
            Toast.makeText(this, "Connection Error. Ensure your phone is connect to internet.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public Loader<JSONArray> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<JSONArray>(this) {

            @Override
            protected void onStartLoading() {
                swipe_refresh_layout.setRefreshing(true);
                forceLoad();
            }

            @Override
            public JSONArray loadInBackground() {
                JSONArray jsonArrayResponse = new JSONArray();
                try {
                    jsonArrayResponse = NetworkUtils.getJsonArrayResponse(NetworkUtils.buildUrl(), new FormBody.Builder().build());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return jsonArrayResponse;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<JSONArray> loader, JSONArray jsonArray) {
        adapter.setData(jsonArray);
        swipe_refresh_layout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<JSONArray> loader) {

    }

    @Override
    public void onListItemClick(int position) {
        try {
            Intent intent = new Intent(this, RecipeStepActivity.class);
            intent.putExtra(getString(R.string.steps_json_key), adapter.getData().getJSONObject(position).getString(getString(R.string.steps_json_key)));
            intent.putExtra(getString(R.string.recipe_name_key), adapter.getData().getJSONObject(position).getString(getString(R.string.recipe_name_key)));
            intent.putExtra(getString(R.string.ingredients_json_key), adapter.getData().getJSONObject(position).getString(getString(R.string.ingredients_json_key)));
            Log.d(TAG, adapter.getData().getJSONObject(position).getString("steps"));
            startActivity(intent);
        } catch (JSONException e) {
            //if no steps key in json, show toast to user. So avoid force close and tell user that something wrong happen
            Toast.makeText(this, "An error occured", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
