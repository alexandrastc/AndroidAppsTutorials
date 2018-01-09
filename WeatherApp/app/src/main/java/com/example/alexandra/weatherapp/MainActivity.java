package com.example.alexandra.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mWeatherText;

    String[] fakeWeatherData ={
            "Today, May 17 - Clear - 17°C / 15°C\n",
            "Tomorrow - Cloudy - 19°C / 15°C\n",
            "Thursday - Rainy- 30°C / 11°C\n",
            "Friday - Thunderstorms - 21°C / 9°C\n",
            "Saturday - Thunderstorms - 16°C / 7°C\n",
            "Sunday - Rainy - 16°C / 8°C\n",
            "Monday - Partly Cloudy - 15°C / 10°C\n",
            "Tue, May 24 - Meatballs - 16°C / 18°C\n",
            "Wed, May 25 - Cloudy - 19°C / 15°C\n",
            "Thu, May 26 - Stormy - 30°C / 11°C\n",
            "Fri, May 27 - Hurricane - 21°C / 9°C\n",
            "Sat, May 28 - Meteors - 16°C / 7°C\n",
            "Sun, May 29 - Apocalypse - 16°C / 8°C\n",
            "Mon, May 30 - Post Apocalypse - 15°C / 10°C\n"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mWeatherText = (TextView) findViewById(R.id.tv_weather_id);


        for (String fakeWeather : fakeWeatherData){

            mWeatherText.append(fakeWeather);

        }


    }
}
