package com.example.alexandra.moviesapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Arrays;

/**
 * Created by alexandra on 10/01/18.
 */

public class DataUtilities {

    /**
     *
     * @param context the context
     * @param movieJsonString the response from the site
     * @return nice looking string
     * @throws JSONException
     */
    public static String makeJsonPretty(Context context, String movieJsonString)
            throws JSONException{

        //movie title
        final String MA_TITLE = "Title";

        //movie year
        final String MA_YEAR = "Year";

        //ratings array
        final String MA_RATINGS = "Ratings";

        //source of ratings
        final String MA_RATING_SOURCE = "Source";

        //value of ratings
        final String MA_RATING_VALUE = "Value";

        //movie genre
        final String MA_GENRE = "Genre";

        String[] parsedMovieData = null;

        JSONObject movieJson = new JSONObject(movieJsonString);

        final String MA_MESSAGE_CODE = "cod";

        //checking for errors ( may not work )
        if(movieJson.has(MA_MESSAGE_CODE)) {
            int errorCode = movieJson.getInt(MA_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    //bad info
                    return null;
                default:
                    //server down
                    return null;
            }
        }


        String movieData = null;

        //get title
        String movieTitle = movieJson.getString(MA_TITLE);

        //get year
        String movieYear = movieJson.getString(MA_YEAR);

        //get genre
        String movieGenre = movieJson.getString(MA_GENRE);

        //put them all together
        movieData = movieTitle + "\n" + " " + movieYear + "\n" + " " + movieGenre + "\n";

        //get ratings array from json
        JSONArray ratingsArray = movieJson.getJSONArray(MA_RATINGS);

        parsedMovieData = new String[ratingsArray.length()];

        for(int i=0; i<ratingsArray.length(); i++){

            String source;
            String value;

            //for each array get source + value and put them in parsed movie data
            JSONObject ratingSource = ratingsArray.getJSONObject(i);

            source = ratingSource.getString(MA_RATING_SOURCE);
            source.trim().replaceAll(",","");

            value = ratingSource.getString(MA_RATING_VALUE);
            value.trim().replaceAll(",","");

            if (source!=null && value != null) {
                parsedMovieData[i] = source + " " + value + "\n";
            }
        }

        //frankenstein together the final string and make it look decent-ish
        return " " + movieData + "\n" + " " + Arrays.toString(parsedMovieData)
                .trim()
                .replaceAll("\\[","")
                .replaceAll("\\]", "")
                .replaceAll(",","");


    }

}
