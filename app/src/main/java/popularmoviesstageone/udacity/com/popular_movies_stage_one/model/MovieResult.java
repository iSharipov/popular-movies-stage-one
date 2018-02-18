package popularmoviesstageone.udacity.com.popular_movies_stage_one.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ilias on 18.02.2018.
 */

public class MovieResult implements Serializable {
    private Long page;
    @SerializedName("total-results")
    private Long totalResults;
    @SerializedName("total-pages")
    private Long totalPages;
    private List<Movie> results;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
