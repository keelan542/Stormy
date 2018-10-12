package com.keelanb.stormy.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.keelanb.stormy.R;
import com.keelanb.stormy.Weather.Current;
import com.keelanb.stormy.Weather.Forecast;
import com.keelanb.stormy.Weather.Hour;
import com.keelanb.stormy.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private Forecast forecast;

    private ImageView iconImageView;

    private double latitude = 37.827;
    private double longitude = -122.4230;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getForecast(longitude, latitude);
    }

    private void getForecast(double longitude, double latitude) {

        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        TextView darkSky = findViewById(R.id.darkSkyAttribution);

        darkSky.setMovementMethod(LinkMovementMethod.getInstance());

        iconImageView = findViewById(R.id.iconImageView);

        String apiKey = "2a6473b98d11fc375c3068d0f691a876";

        String forecastURL = "https://api.darksky.net/forecast/" + apiKey + "/" + latitude + "," + longitude;

        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(forecastURL)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            forecast = parseForecastData(jsonData);

                            Current current = forecast.getCurrent();

                            final Current displayWeather = new Current(
                                    current.getLocationLabel(),
                                    current.getIcon(),
                                    current.getTime(),
                                    current.getTemperature(),
                                    current.getHumidity(),
                                    current.getPrecipChance(),
                                    current.getSummary(),
                                    current.getTimeZone()
                            );

                            binding.setWeather(displayWeather);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Drawable drawable = getResources().getDrawable(displayWeather.getIconId());
                                    iconImageView.setImageDrawable(drawable);
                                }
                            });
                        } else {
                            alertUserAboutError(0);
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "IO exception caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON exception caught:", e);
                    }
                }
            });
        }
    }

    private Forecast parseForecastData(String jsonData) throws JSONException {
        Forecast forecast = new Forecast();

        forecast.setCurrent(getCurrentDetails(jsonData));
        forecast.setHourlyForecast(getHourlyForecast(jsonData));

        return forecast;
    }

    private Hour[] getHourlyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");

        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        Hour[] hourlyArray = new Hour[data.length()];
        for (int i = 0; i < hourlyArray.length; i++) {
            JSONObject currentHour = data.getJSONObject(i);

            Hour hour = new Hour();

            hour.setTime(currentHour.getLong("time"));
            hour.setSummary(currentHour.getString("summary"));
            hour.setIcon(currentHour.getString("icon"));
            hour.setTemperature(currentHour.getDouble("temperature"));
            hour.setTimeZone(timezone);

            hourlyArray[i] = hour;
        }

        return hourlyArray;
    }

    private Current getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");

        JSONObject currently = forecast.getJSONObject("currently");

        Current current = new Current();

        current.setHumidity(currently.getDouble("humidity"));
        current.setTime(currently.getLong("time"));
        current.setIcon(currently.getString("icon"));
        current.setLocationLabel("Alcatraz Island, CA");
        current.setPrecipChance(currently.getDouble("precipProbability"));
        current.setSummary(currently.getString("summary"));
        current.setTemperature(currently.getDouble("temperature"));
        current.setTimeZone(timezone);

        return current;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;

        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        } else {
            alertUserAboutError(1);
        }

        return isAvailable;
    }



    private void alertUserAboutError(int errorCode) {
        AlertDialogFragment dialogFragment = new AlertDialogFragment();

        Bundle args = new Bundle();
        args.putInt(getString(R.string.error_code_alert_dialog), errorCode);
        dialogFragment.setArguments(args);

        dialogFragment.show(getFragmentManager(), getString(R.string.error_code_tag));
    }

    public void refreshOnClick(View view)  {
        Toast.makeText(this, "Refreshing data", Toast.LENGTH_SHORT).show();
        getForecast(longitude, latitude);
    }

    public void hourlyOnClick(View view) {
        List<Hour> hours = Arrays.asList(forecast.getHourlyForecast());

        Intent intent = new Intent(this, HourlyForecastActivity.class);
        intent.putExtra("HourlyList",(Serializable) hours);
        startActivity(intent);
    }
}
