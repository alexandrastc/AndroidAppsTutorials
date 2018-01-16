package com.example.alexandra.githubsearch;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import com.example.alexandra.githubsearch.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText mSearchBoxEditText;

    TextView mUrlDisplayTextView;

    TextView mSearchResults;

    TextView mErrorMessage;

    ProgressBar mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mErrorMessage = (TextView) findViewById(R.id.tv_error_message_display);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search);

        mUrlDisplayTextView = (TextView) findViewById(R.id.tw_url_display);

        mSearchResults = (TextView) findViewById(R.id.tv_github_search_results_json);

        mLoadingBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);


    }

    //method to show json data
    private void showJsonDataView(){

        //sets the error invisible
        mErrorMessage.setVisibility(View.INVISIBLE);

        //sets the results visible
        mSearchResults.setVisibility(View.VISIBLE);
    }

    //func to show error message
    private void showErrorMessage(){

        //hide results
        mSearchResults.setVisibility(View.INVISIBLE);

        //show error
        mErrorMessage.setVisibility(View.VISIBLE);
    }



    //first param is what you give, last what it gives you, sorta
    public class GithubQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            //show loading bar
            mLoadingBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(URL... urls) {

            //pass the url
            URL searchUrl = urls[0];

            //initialize search results
            String githubSearchResults = null;

            try {

                //save search results
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);

            } catch (IOException e){

                e.printStackTrace();

            }

            return githubSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {

            //hide loading bar when stuff is done loading
            mLoadingBar.setVisibility(View.INVISIBLE);

            if (s != null && !s.equals("")){
                showJsonDataView();
                mSearchResults.setText(s);
            } else {
                showErrorMessage();
            }

        }
    }

    //inflates the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }


    //when you press the item it shows a toast
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuItemThatWasSelected = item.getItemId();

        if (menuItemThatWasSelected == R.id.action_search){

            //sets this activity as current
            Context context = MainActivity.this;

            makeGithubSearchQuery();

        }

        return super.onOptionsItemSelected(item);
    }

    void makeGithubSearchQuery(){


        //get text from text edit
        String githubQuery = mSearchBoxEditText.getText().toString();

        //build url using query
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);

        //display url to text view
        mUrlDisplayTextView.setText(githubSearchUrl.toString());

        //execute query in background
        new GithubQueryTask().execute(githubSearchUrl);


    }
}

