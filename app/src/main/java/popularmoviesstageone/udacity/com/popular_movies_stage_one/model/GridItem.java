package popularmoviesstageone.udacity.com.popular_movies_stage_one.model;

/**
 * Created by ilias on 18.02.2018.
 */

public class GridItem {
    private final String url;
    private final Long movieId;

    public GridItem(String url, Long movieId) {
        this.url = url;
        this.movieId = movieId;
    }

    public String getUrl() {
        return url;
    }

    public Long getMovieId() {
        return movieId;
    }
}