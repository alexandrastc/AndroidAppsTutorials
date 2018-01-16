package com.example.alexandra.moviesapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText mSearchEditText;
    TextView mMovieResults;
    Button mResetButton;


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

    }


    //inflates the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflate menu
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }

    //make task
    public class MoviesQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override
        protected String doInBackground(URL... urls) {

            //pass the url
            URL searchUrl = urls[0];

            try {

                //get search results response from site
                String searchResults = NetworkUtilities.getResponseFromHttpUrl(searchUrl);

                //make them look prettier
                String simpleJsonResults = DataUtilities.makeJsonPretty(MainActivity.this,
                        searchResults);

                return simpleJsonResults;


            } catch (Exception e){
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {

            if (s!=null && !s.equals("")) {
                mMovieResults.setText(s);
            }

        }
    }


    void makeMoviesQuery(){

        //resets movie results text view when you press search button
        mMovieResults.setText("");

        //get text from text edit
        String movieTitle = mSearchEditText.getText().toString();

        //build url
        URL movieSearchUrl = NetworkUtilities.buildUrl(movieTitle);

        //execute in background
        new MoviesQueryTask().execute(movieSearchUrl);


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
}
