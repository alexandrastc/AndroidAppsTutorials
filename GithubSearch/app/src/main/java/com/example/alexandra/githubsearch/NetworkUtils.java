package com.example.alexandra.githubsearch;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by alexandra on 09/01/18.
 */

public class NetworkUtils {

    static String sortBy = "stars";

    //base url for search
    final static String GITHUB_BASE_URL = "https://api.github.com/search/repositories";

    //query param
    final static String PARAM_QUERY = "q";

    //sort param
    final static String PARAM_SORT = "sort";


    static public URL buildUrl(String githubSearchQuery) {

        //android uri
        Uri builtUri =
                Uri.parse(GITHUB_BASE_URL).buildUpon()
                        .appendQueryParameter(PARAM_QUERY, githubSearchQuery)
                        .appendQueryParameter(PARAM_SORT, sortBy)
                        .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl (URL url) throws IOException{

        //make the connection object
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try{

            //you get an input stream and read it
            InputStream in = urlConnection.getInputStream();

            //make scanner read stream
            Scanner scanner = new Scanner(in);

            //sets stream to beginning
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if(hasInput){

                return scanner.next();

            } else {

                return null;

            }

        } finally {

            urlConnection.disconnect();

        }


    }


}
