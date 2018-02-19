package popularmoviesstageone.udacity.com.popular_movies_stage_one;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import popularmoviesstageone.udacity.com.popular_movies_stage_one.adapter.ImageGridViewAdapter;
import popularmoviesstageone.udacity.com.popular_movies_stage_one.model.GridItem;
import popularmoviesstageone.udacity.com.popular_movies_stage_one.model.Movie;
import popularmoviesstageone.udacity.com.popular_movies_stage_one.model.MovieResult;

import static popularmoviesstageone.udacity.com.popular_movies_stage_one.utils.JsonUtils.parseMovieDBJson;
import static popularmoviesstageone.udacity.com.popular_movies_stage_one.utils.NetworkUtils.getResponseFromHttpUrl;

public class MainActivity extends AppCompatActivity {

    private MovieResult movieResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeMovieDbSearchQuery();
    }

    private void makeMovieDbSearchQuery() {
        try {
            URL movieDbSearchUrl = new URL("http://api.themoviedb.org/3/movie/top_rated?api_key=895d45558acb3238127ec72b182ef588");
            new TheMovieDbQueryTask().execute(movieDbSearchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class TheMovieDbQueryTask extends AsyncTask<URL, Void, String> {

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
        protected void onPostExecute(String theMovieDbSearchResult) {
            if (theMovieDbSearchResult != null && !theMovieDbSearchResult.isEmpty()) {
                setMovieResult(parseMovieDBJson(theMovieDbSearchResult));
                populateUI();
            }
        }
    }

    public MovieResult getMovieResult() {
        return movieResult;
    }

    public void setMovieResult(MovieResult movieResult) {
        this.movieResult = movieResult;
    }

    private void populateUI() {
        GridView gridView = findViewById(R.id.gridView);

        MovieResult movieResult = getMovieResult();
        List<Movie> movies = movieResult.getResults();
        String theMovieDbImgUrl = getString(R.string.themoviedb_img_url);
        theMovieDbImgUrl += "w185";
        List<GridItem> gridItems = new ArrayList<>();
        for (Movie movie : movies) {
            String posterPath = movie.getPosterPath();
            gridItems.add(new GridItem(theMovieDbImgUrl + posterPath, movie.getId()));
        }
        ImageGridViewAdapter adapter = new ImageGridViewAdapter(this, R.layout.grid_item_layout, gridItems);
        gridView.setAdapter(adapter);
    }
}