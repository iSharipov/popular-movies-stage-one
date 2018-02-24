package popularmoviesstageone.udacity.com.popular_movies_stage_one.async;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

import static popularmoviesstageone.udacity.com.popular_movies_stage_one.utils.NetworkUtils.getResponseFromHttpUrl;

/**
 * 23.02.2018.
 */

public class TheMovieDbQueryTask extends AsyncTask<URL, Void, String> {
    private final AsyncTaskCompleteListener<String> listener;

    public TheMovieDbQueryTask(AsyncTaskCompleteListener<String> listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(URL... urls) {
        URL searchUrl = urls[0];
        String theMovieDbSearchResult = null;
        try {
            theMovieDbSearchResult = getResponseFromHttpUrl(searchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return theMovieDbSearchResult;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        listener.onTaskComplete(s);
    }
}