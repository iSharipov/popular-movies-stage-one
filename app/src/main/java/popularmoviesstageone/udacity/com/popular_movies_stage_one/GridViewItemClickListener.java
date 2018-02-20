package popularmoviesstageone.udacity.com.popular_movies_stage_one;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.List;

import popularmoviesstageone.udacity.com.popular_movies_stage_one.activity.DetailActivity;
import popularmoviesstageone.udacity.com.popular_movies_stage_one.model.GridItem;
import popularmoviesstageone.udacity.com.popular_movies_stage_one.model.Movie;

/**
 * Created by ilias on 20.02.2018.
 */

public class GridViewItemClickListener implements AdapterView.OnItemClickListener {

    private final Context context;
    private final List<Movie> movies;

    public GridViewItemClickListener(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GridItem gridItem = (GridItem) parent.getItemAtPosition(position);
        Intent intent = new Intent(context, DetailActivity.class);
        Long movieId = gridItem.getMovieId();
        Movie result = null;
        for (Movie movie : movies) {
            if (movie.getId().equals(movieId)) {
                result = movie;
                break;
            }
        }
        if (result != null) {
            intent.putExtra("movie", result);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Movie not found", Toast.LENGTH_LONG).show();
        }
    }
}