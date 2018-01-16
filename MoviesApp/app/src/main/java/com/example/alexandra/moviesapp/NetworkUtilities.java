package com.example.alexandra.moviesapp;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Class that does useful network stuff
 */

public class NetworkUtilities {

    private static final String TAG = NetworkUtilities.class.getSimpleName();

    private static final String BASE_URL = "http://www.omdbapi.com/?apikey=e76580e6&";

    private static final String TITLE_PARAM = "t";

    //api supports json or xml
    private static final String FORMAT_PARAM = "r";

    //the format i want
    private static String format = "json";


    /**
     * Builds the url.
     * @param title needs the title of the movie.
     * @return the url.
     */

    public static URL buildUrl(String title){

        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(TITLE_PARAM,title)
                .appendQueryParameter(FORMAT_PARAM,format)
                .build();

        URL url = null;

        try{
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        Log.v(TAG,"Bult url: " + url);

        return url;

    }

    /**
     * Gets response from url
     * @param url the url
     * @return the data
     */

    public static String getResponseFromHttpUrl (URL url) throws IOException {

        //connection object
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try{

            //read input stream
            InputStream in = urlConnection.getInputStream();

            //make scanner read stream
            Scanner scanner = new Scanner(in);

            //sets stream to beginning
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();

            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }

        } finally{

            urlConnection.disconnect();

        }

    }
}
