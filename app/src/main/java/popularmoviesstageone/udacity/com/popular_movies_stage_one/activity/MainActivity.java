package popularmoviesstageone.udacity.com.popular_movies_stage_one.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import popularmoviesstageone.udacity.com.popular_movies_stage_one.GridViewItemClickListener;
import popularmoviesstageone.udacity.com.popular_movies_stage_one.R;
import popularmoviesstageone.udacity.com.popular_movies_stage_one.adapter.ImageGridViewAdapter;
import popularmoviesstageone.udacity.com.popular_movies_stage_one.async.AsyncTaskCompleteListener;
import popularmoviesstageone.udacity.com.popular_movies_stage_one.async.TheMovieDbQueryTask;
import popularmoviesstageone.udacity.com.popular_movies_stage_one.model.GridItem;
import popularmoviesstageone.udacity.com.popular_movies_stage_one.model.Movie;
import popularmoviesstageone.udacity.com.popular_movies_stage_one.model.MovieResult;

import static popularmoviesstageone.udacity.com.popular_movies_stage_one.utils.JsonUtils.parseMovieDBJson;
import static popularmoviesstageone.udacity.com.popular_movies_stage_one.utils.NetworkUtils.buildBaseMovieDbdUrl;

public class MainActivity extends AppCompatActivity {

    private MovieResult movieResult;
    private String sortType;
    private ProgressBar progressBar;
    private GridView gridView;
    private NetworkReceiver networkReceiver = new NetworkReceiver();

    private static boolean wifiConnected = false;
    private static boolean mobileConnected = false;
    public static boolean refreshDisplay = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.gridView);
        progressBar = findViewById(R.id.pb_loading_indicator);
        networkReceiver = new NetworkReceiver();
        this.registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadSortMovieTypeFromSharedPreferences();
        updateConnectedFlags();
        if (refreshDisplay) {
            makeMovieDbSearchQuery(getSortType());
        } else {
            addButton();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (networkReceiver != null) {
            this.unregisterReceiver(networkReceiver);
        }
    }

    public void updateConnectedFlags() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            wifiConnected = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        } else {
            wifiConnected = false;
            mobileConnected = false;
        }
    }

    private void makeMovieDbSearchQuery(String sortType) {
        if (wifiConnected || mobileConnected) {
            URL movieDbSearchUrl = buildBaseMovieDbdUrl(getString(R.string.themoviedb_url), sortType, getString(R.string.api_key));
            findViewById(R.id.button_1).setVisibility(View.INVISIBLE);
            new TheMovieDbQueryTask(new TheMovieDbQueryCompleteListener()).execute(movieDbSearchUrl);
        } else {
            addButton();
        }
    }

    private class TheMovieDbQueryCompleteListener implements AsyncTaskCompleteListener<String> {
        @Override
        public void onTaskComplete(String theMovieDbSearchResult) {
            if (theMovieDbSearchResult != null && !theMovieDbSearchResult.isEmpty()) {
                setMovieResult(parseMovieDBJson(theMovieDbSearchResult));
                populateUI();
            }
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                refreshDisplay = true;
            } else {
                refreshDisplay = false;
                Toast.makeText(context, R.string.lost_connection, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void populateUI() {
        MovieResult movieResult = getMovieResult();
        final List<Movie> movies = movieResult.getResults();
        String theMovieDbImgUrl = getString(R.string.themoviedb_img_url);
        theMovieDbImgUrl += "w185";
        List<GridItem> gridItems = new ArrayList<>();
        for (Movie movie : movies) {
            String posterPath = movie.getPosterPath();
            gridItems.add(new GridItem(theMovieDbImgUrl + posterPath, movie.getId()));
        }
        ImageGridViewAdapter adapter = new ImageGridViewAdapter(this, R.layout.grid_item_layout, gridItems);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new GridViewItemClickListener(this, movies));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popular_movies_menu, menu);
        return true;
    }

    public void addButton() {
        Button offlineButton = findViewById(R.id.button_1);
        offlineButton.setVisibility(View.VISIBLE);
        offlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateConnectedFlags();
                makeMovieDbSearchQuery(getSortType());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadSortMovieTypeFromSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortType = sharedPreferences.getString(getString(R.string.pref_sort_type_key),
                getString(R.string.pref_sort_type_default));
        setSortType(sortType);
    }

    public MovieResult getMovieResult() {
        return movieResult;
    }

    public void setMovieResult(MovieResult movieResult) {
        this.movieResult = movieResult;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}