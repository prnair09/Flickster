package com.rakesh.flickster;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rakesh.flickster.Helper.SizeHelper;
import com.rakesh.flickster.models.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by rparuthi on 3/13/2017.
 */

public class MovieDialogFragment extends DialogFragment {

    public MovieDialogFragment(){

    }
    ImageView ivMovieImage;
    public static MovieDialogFragment newInstance(Movie movie){
        MovieDialogFragment fragment = new MovieDialogFragment();
        Bundle args = new Bundle();

        args.putSerializable("Movie",movie);

        //Set Arguments on fragment
        fragment.setArguments(args);

        return fragment;
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);



        return  dialog;
    }

    @Override
    public void onResume() {

        Window window = getDialog().getWindow();

        ViewGroup.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;

        window.setAttributes((android.view.WindowManager.LayoutParams)layoutParams);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_movie_dialog, container);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Movie movie = (Movie) getArguments().getSerializable("Movie");

        setData(view,movie);

    }

    private void setData(View view, Movie movie){
        ivMovieImage = (ImageView) view.findViewById(R.id.ivMovieImage);

        Picasso.with(getContext()).load(movie.getBackdropPath())
                .resize(SizeHelper.getScreenWidth(), 0)
                .placeholder(R.drawable.user_w320placeholder)
                .error(R.drawable.error)
                .into(ivMovieImage);

        TextView tvMovieTitle = (TextView)view.findViewById(R.id.tvMovieTitle) ;
        tvMovieTitle.setText(movie.getOriginalTitle());

        TextView tvReleaseDate = (TextView)view.findViewById(R.id.tvReleaseDate);
        tvReleaseDate.setText(movie.getReleaseDate());

        TextView tvOverView = (TextView)view.findViewById(R.id.tvOverview);
        tvOverView.setText(movie.getOverView());

        RatingBar rbRating = (RatingBar)view.findViewById(R.id.ratingBar);
        rbRating.setRating((float)movie.getVoteAverage()/2);

    }
}
