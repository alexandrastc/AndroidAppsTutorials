package com.example.alexandra.moviesapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements
        android.support.v4.app.LoaderManager.LoaderCallbacks<String> {

    EditText mSearchEditText;
    TextView mMovieResults;
    Button mResetButton;
    private static final int MOVIE_APP_LOADER = 22;

    private static final String SEARCH_RESULTS_JSON = "results";
    private static String SEARCH_QUERY_URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchEditText = (EditText) findViewById(R.id.et_search);
        mMovieResults = (TextView) findViewById(R.id.tv_movie_results);
        mResetButton = (Button) findViewById(R.id.button_reset_results);

        mResetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mSearchEditText.setText("");
            }
        });

        if(savedInstanceState != null){

        }

    }


    //inflates the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflate menu
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new android.support.v4.content.AsyncTaskLoader<String>(this) {

            @Override
            public String loadInBackground() {

                String searchQueryUrlString = args.getString(SEARCH_QUERY_URL);

                if (searchQueryUrlString == null || searchQueryUrlString.equals("")){
                    return null;
                }

                try {

                    URL movieUrl = new URL(searchQueryUrlString);

                    //get search results response from site
                    String searchResults = NetworkUtilities.getResponseFromHttpUrl(movieUrl);

                    //make them look prettier
                    String simpleJsonResults = DataUtilities.makeJsonPretty(MainActivity.this,
                            searchResults);

                    return simpleJsonResults;


                } catch (Exception e){
                    e.printStackTrace();
                    return null;
                }

            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();

                if(args == null){
                    return ;
                } else {

                }

            }
        };
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<String> loader, String data) {

        if (data!=null && !data.equals("")) {
            mMovieResults.setText(data);
        }

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader loader) {

    }

    void makeMoviesQuery(){

        //resets movie results text view when you press search button
        mMovieResults.setText("");

        //get text from text edit
        String movieTitle = mSearchEditText.getText().toString();

        //build url
        URL movieSearchUrl = NetworkUtilities.buildUrl(movieTitle);

        //execute in background
        Bundle queryBundle = new Bundle();
        SEARCH_QUERY_URL = movieSearchUrl.toString();

        queryBundle.putString(SEARCH_QUERY_URL, SEARCH_QUERY_URL);

        android.support.v4.app.LoaderManager loaderManager;
        loaderManager = getSupportLoaderManager();

        android.support.v4.content.Loader<String> movieappLoader;
        movieappLoader = loaderManager.getLoader(MOVIE_APP_LOADER);

        if(movieappLoader == null){

            loaderManager.initLoader(MOVIE_APP_LOADER, queryBundle, this).forceLoad();

        } else {

            loaderManager.restartLoader(MOVIE_APP_LOADER, queryBundle, this).forceLoad();

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuButtonPressed = item.getItemId();

        if (menuButtonPressed == R.id.menu_search){
            Context context = MainActivity.this;
            makeMoviesQuery();
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

    }
}
