package com.rakesh.flickster;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.rakesh.flickster.Helper.NetworkHelper;
import com.rakesh.flickster.adapters.MovieArrayAdapter;
import com.rakesh.flickster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    ArrayList<Movie>  movies;
    MovieArrayAdapter movieAdapter;
    ListView lvItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(NetworkHelper.isNetworkAvailable(getApplicationContext())) {
            setContentView(R.layout.activity_movie);

            lvItems = (ListView) findViewById(R.id.lvMovies);
            movies = new ArrayList<>();
            movieAdapter = new MovieArrayAdapter(this, movies);
            lvItems.setAdapter(movieAdapter);

            setupListViewListeners();

            String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    JSONArray movieJsonResults = null;

                    try {
                        movieJsonResults = response.getJSONArray("results");
                        movies.addAll(Movie.fromJsonArray(movieJsonResults));
                        movieAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "This application needs an active internet connection", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    private void setupListViewListeners() {
        //ItemClick
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View item, int index, long id) {
                        onEditItem(item, index, id);
                    }
                }
        );

    }

    private void onEditItem(View item, int index, long id) {
        FragmentManager fragMan = getSupportFragmentManager();

        Movie movie = movies.get(index);

        MovieDialogFragment movieDialFrag = MovieDialogFragment.newInstance(movie);

        movieDialFrag.show(fragMan,"fragment_movie_dialog");
    }


}
