package popularmoviesstageone.udacity.com.popular_movies_stage_one.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import popularmoviesstageone.udacity.com.popular_movies_stage_one.R;
import popularmoviesstageone.udacity.com.popular_movies_stage_one.model.GridItem;

/**
 * Created by ilias on 18.02.2018.
 */

public class ImageGridViewAdapter extends ArrayAdapter<GridItem> {

    private final Context context;
    private final int layoutResourceId;
    private final List<GridItem> data;
    private final LayoutInflater mInflater;

    public ImageGridViewAdapter(Context context, int layoutResourceId, List<GridItem> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageView = row.findViewById(R.id.grid_item_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        GridItem gridItem = data.get(position);
        Picasso.with(context).load(gridItem.getUrl()).into(holder.imageView);
        return row;
    }

    public int getLayoutResourceId() {
        return layoutResourceId;
    }

    public List<GridItem> getData() {
        return data;
    }

    private static class ViewHolder {
        private ImageView imageView;
        private Long movieId;

        public ImageView getImageView() {
            return imageView;
        }

        void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public Long getMovieId() {
            return movieId;
        }

        void setMovieId(Long movieId) {
            this.movieId = movieId;
        }
    }
}