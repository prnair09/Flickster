package com.rakesh.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rakesh.flickster.Helper.SizeHelper;
import com.rakesh.flickster.R;
import com.rakesh.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.TreeSet;

/**
 * Created by rparuthi on 3/7/2017.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public enum ViewTypes {
        item_movie,
        item_movie_popular
    }

    private TreeSet itemMoviePopularSet = new TreeSet();

    public MovieArrayAdapter(Context ctx, List<Movie> movies){
        super(ctx,android.R.layout.simple_list_item_1,movies);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    private static final int WIDTH_POSTER = 342;
    private static final int WIDTH_BACKDROP = 780;
    private static final int WIDTH_BACKDROP_LANDSCAPE = 1280;

    public int getViewTypeCount(){
        return ViewTypes.values().length;
    }

    //Returns ViewType
    public int getItemViewType(int position){
        return itemMoviePopularSet.contains(position) ? ViewTypes.item_movie_popular.ordinal()
                                                            :ViewTypes.item_movie.ordinal();
    }

    private void addPopularMovieSet(int position) {
        itemMoviePopularSet.add(position);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        try {


            Movie movie = getItem(position);
            String imageUrl = movie.getPosterPath();
            int imageWidth = WIDTH_POSTER;


            MovieViewHolder viewHolder;

            if (movie.getVoteAverage() > 5.0 && !itemMoviePopularSet.contains(position)) {
                addPopularMovieSet(position);
            }

            int viewType = this.getItemViewType(position);
            int orientation = getContext().getResources().getConfiguration().orientation;

            switch (viewType) {
                case 0:
                    if (convertView == null) {
                        LayoutInflater inflater = (LayoutInflater) getContext().
                                                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = inflater.inflate(R.layout.item_movie, parent, false);

                        viewHolder = new MovieViewHolder(convertView);
                        convertView.setTag(viewHolder);

                    } else {
                        viewHolder = (MovieViewHolder) convertView.getTag();
                    }

                    viewHolder.ivMovieImage.setImageResource(0);

                    viewHolder.tvTitle.setText(movie.getOriginalTitle());
                    viewHolder.tvOverview.setText(movie.getOverView());

                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        viewHolder.ivMovieImage.setImageResource(0);
                        imageUrl = movie.getBackdropPath();
                        imageWidth = WIDTH_BACKDROP;
                    }

                    Picasso.with(getContext()).load(imageUrl)
                            .resize(imageWidth, 0)
                            .placeholder(R.drawable.user_w320placeholder)
                            .error(R.drawable.error)
                            .into(viewHolder.ivMovieImage);
                    break;
                case 1:
                    PopularMovieViewHolder popViewHolder;
                    if (convertView == null) {
                        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        convertView = inflater.inflate(R.layout.item_movie_popular, parent, false);

                        popViewHolder = new PopularMovieViewHolder(convertView);
                        convertView.setTag(popViewHolder);

                    } else {
                        popViewHolder = (PopularMovieViewHolder) convertView.getTag();
                    }

                    popViewHolder.ivMovieImage.setImageResource(0);

                    popViewHolder.ivMovieImage.setImageResource(0);
                    imageUrl = movie.getBackdropPath();
                    //imageWidth = WIDTH_BACKDROP;
                    imageWidth = SizeHelper.getScreenWidth();


                    if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        popViewHolder.ivMovieImage.setImageResource(0);
                        imageUrl = movie.getBackdropPath();
                    }

                    Picasso.with(getContext()).load(imageUrl)
                            .resize(imageWidth, 0)
                            .placeholder(R.drawable.user_w320placeholder)
                            .error(R.drawable.error)
                            .into(popViewHolder.ivMovieImage);
                    break;
                default:
                    break;

            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return convertView;
    }


    private class MovieViewHolder{
        ImageView ivMovieImage;
        TextView tvTitle;
        TextView tvOverview;


        public MovieViewHolder(View view){
            ivMovieImage = (ImageView)view.findViewById(R.id.ivMovieImage);
            tvTitle = (TextView)view.findViewById(R.id.tvTitle);
            tvOverview = (TextView)view.findViewById(R.id.tvOverview);
        }
    }

    private class PopularMovieViewHolder{
        ImageView ivMovieImage;

        public PopularMovieViewHolder(View view){
            ivMovieImage = (ImageView)view.findViewById(R.id.ivMovieImage);
        }
    }
}
