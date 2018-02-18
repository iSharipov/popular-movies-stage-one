package popularmoviesstageone.udacity.com.popular_movies_stage_one.utils;

import com.google.gson.Gson;

import popularmoviesstageone.udacity.com.popular_movies_stage_one.model.MovieResult;

/**
 * Created by ilias on 18.02.2018.
 */

public class JsonUtils {
    public static MovieResult parseMovieDBJson(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, MovieResult.class);
    }
}
