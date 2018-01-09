package com.example.alexandra.githubsearch;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import com.example.alexandra.githubsearch.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText mSearchBoxEditText;

    TextView mUrlDisplayTextView;

    TextView mSearchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search);

        mUrlDisplayTextView = (TextView) findViewById(R.id.tw_url_display);

        mSearchResults = (TextView) findViewById(R.id.tv_github_search_results_json);


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

        String githubQuery = mSearchBoxEditText.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        mUrlDisplayTextView.setText(githubSearchUrl.toString());



    }
}

