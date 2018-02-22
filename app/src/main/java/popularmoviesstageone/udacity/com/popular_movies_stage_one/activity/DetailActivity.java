package popularmoviesstageone.udacity.com.popular_movies_stage_one.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import popularmoviesstageone.udacity.com.popular_movies_stage_one.R;
import popularmoviesstageone.udacity.com.popular_movies_stage_one.model.Movie;

/**
 * Created by ilias on 18.02.2018.
 */

public class DetailActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);
        Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        populateUI(movie);
    }

    private void populateUI(Movie movie) {
        String theMovieDbImgUrl = getString(R.string.themoviedb_img_url);
        theMovieDbImgUrl += "w185";
        String posterPath = movie.getPosterPath();
        ImageView poster = findViewById(R.id.poster);
        Picasso.with(this).load(theMovieDbImgUrl + posterPath).into(poster);
        TextView movieTitle = findViewById(R.id.movie_title);
        movieTitle.setText(movie.getTitle());
        TextView movieOverview = findViewById(R.id.movie_overview);
        movieOverview.setText(movie.getOverview());
        TextView userRating = findViewById(R.id.user_rating);
        userRating.setText(String.valueOf(movie.getVoteAverage()));
        TextView releaseDate = findViewById(R.id.release_date);
        releaseDate.setText(movie.getReleaseDate());
    }
}
