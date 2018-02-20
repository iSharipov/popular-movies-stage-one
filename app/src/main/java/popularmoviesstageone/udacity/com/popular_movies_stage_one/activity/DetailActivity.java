package popularmoviesstageone.udacity.com.popular_movies_stage_one.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

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
        System.out.println(movie);
    }
}
