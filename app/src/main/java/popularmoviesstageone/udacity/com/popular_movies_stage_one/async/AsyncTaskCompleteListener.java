package popularmoviesstageone.udacity.com.popular_movies_stage_one.async;

/**
 * Created by ilias on 23.02.2018.
 */

public interface AsyncTaskCompleteListener<T> {
    void onTaskComplete(T result);

    void onPreExecute();
}
