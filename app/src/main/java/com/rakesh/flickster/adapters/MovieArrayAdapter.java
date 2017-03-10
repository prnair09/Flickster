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

import com.rakesh.flickster.R;
import com.rakesh.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rparuthi on 3/7/2017.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public MovieArrayAdapter(Context ctx, List<Movie> movies){
        super(ctx,android.R.layout.simple_list_item_1,movies);
    }

    private static final int WIDTH_POSTER = 342;
    private static final int WIDTH_BACKDROP = 780;

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = getItem(position);

        MovieViewHolder viewHolder;

        //Check the existing view is reused

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie,parent,false);
            viewHolder = new MovieViewHolder(convertView,movie);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (MovieViewHolder)convertView.getTag();
        }

        String imageUrl = movie.getPosterPath();
        int imageWidth = WIDTH_POSTER;
        viewHolder.ivMovieImage.setImageResource(0);

        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        viewHolder.tvOverview.setText(movie.getOverView());


        int orientation = getContext().getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            viewHolder.ivMovieImage.setImageResource(0);
            imageUrl = movie.getBackdropPath();
            imageWidth = WIDTH_BACKDROP;

        }

        Picasso.with(getContext()).load(imageUrl)
                .resize(imageWidth,0)
                .placeholder(R.drawable.user_w320placeholder)
                .error(R.drawable.error)
                .into(viewHolder.ivMovieImage);

        return convertView;
    }

    private class MovieViewHolder{
        ImageView ivMovieImage;
        TextView tvTitle;
        TextView tvOverview;

        Movie objMovie;

        public MovieViewHolder(View view, Movie movie){
            ivMovieImage = (ImageView)view.findViewById(R.id.ivMovieImage);
            tvTitle = (TextView)view.findViewById(R.id.tvTitle);
            tvOverview = (TextView)view.findViewById(R.id.tvOverview);
            objMovie = movie;
        }
    }
}
