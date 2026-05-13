package com.codingtutorials.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * MainActivity — The single activity that powers the WeatherApp.
 *
 * <p>This activity provides a simple UI where the user can enter a city name
 * and fetch real-time weather data from the OpenWeatherMap API. The weather
 * information displayed includes temperature, humidity, wind speed, a textual
 * description, and a condition-specific weather icon.</p>
 *
 * <h3>How it works:</h3>
 * <ol>
 *   <li>User enters a city name and taps "Change City" (or the app loads with "Kochi" by default).</li>
 *   <li>{@link #fetchWeatherData(String)} sends an HTTP GET request to the OpenWeatherMap API
 *       on a background thread using {@link ExecutorService} and {@link OkHttpClient}.</li>
 *   <li>The JSON response is passed to {@link #updateUI(String)} on the main thread.</li>
 *   <li>The UI views are updated with the parsed weather data.</li>
 * </ol>
 *
 * @see <a href="https://openweathermap.org/current">OpenWeatherMap Current Weather API</a>
 */
public class MainActivity extends AppCompatActivity {

    // --- UI Elements ---
    private TextView cityNameText, temperatureText, humidityText, descriptionText, windText;
    private ImageView weatherIcon;
    private Button refreshButton;
    private EditText cityNameInput;

    /** OpenWeatherMap API key. Get yours free at https://openweathermap.org/appid */
    private static final String API_KEY = "7be4a25466b8361c2ae28097a6aa5617";

    /**
     * Called when the activity is first created.
     * Initializes all UI elements, sets up the button click listener,
     * and fetches weather data for the default city (Kochi).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind views to their XML counterparts
        cityNameText = findViewById(R.id.cityNameText);
        temperatureText = findViewById(R.id.temperatureText);
        humidityText = findViewById(R.id.humidityText);
        windText = findViewById(R.id.windText);
        descriptionText = findViewById(R.id.descriptionText);
        weatherIcon = findViewById(R.id.weatherIcon);
        refreshButton = findViewById(R.id.fetchWeatherButton);
        cityNameInput = findViewById(R.id.cityNameInput);

        // Handle "Change City" button click
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cityName = cityNameInput.getText().toString();
                if (!cityName.isEmpty()) {
                    fetchWeatherData(cityName);
                } else {
                    cityNameInput.setError("Please enter a city name");
                }
            }
        });

        // Load weather for the default city on app launch
        fetchWeatherData("Kochi");
    }

    /**
     * Fetches current weather data from the OpenWeatherMap API for the given city.
     *
     * <p>The network request runs on a background thread via {@link ExecutorService}
     * to avoid blocking the main UI thread. On success, the JSON response string
     * is passed to {@link #updateUI(String)} on the main thread.</p>
     *
     * @param cityName the name of the city to fetch weather for (e.g., "London", "Kochi")
     */
    private void fetchWeatherData(String cityName) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + API_KEY + "&units=metric";
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            try {
                Response response = client.newCall(request).execute();
                String result = response.body().string();
                runOnUiThread(() -> updateUI(result));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Parses the JSON response from OpenWeatherMap and updates all UI elements.
     *
     * <p>Extracts the following fields from the response:</p>
     * <ul>
     *   <li><b>name</b> → City name display</li>
     *   <li><b>main.temp</b> → Temperature in °C</li>
     *   <li><b>main.humidity</b> → Humidity percentage</li>
     *   <li><b>wind.speed</b> → Wind speed in km/h</li>
     *   <li><b>weather[0].description</b> → Weather condition text</li>
     *   <li><b>weather[0].icon</b> → Icon code mapped to local drawable (e.g., "01d" → ic_01d.png)</li>
     * </ul>
     *
     * @param result the raw JSON response string from the API
     */
    private void updateUI(String result) {
        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject main = jsonObject.getJSONObject("main");
                double temperature = main.getDouble("temp");
                double humidity = main.getDouble("humidity");
                double windSpeed = jsonObject.getJSONObject("wind").getDouble("speed");

                String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");

                // Map the API's icon code to a local drawable resource (e.g., "01d" → R.drawable.ic_01d)
                String iconCode = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
                String resourceName = "ic_" + iconCode;
                int resId = getResources().getIdentifier(resourceName, "drawable", getPackageName());
                weatherIcon.setImageResource(resId);

                // Update all text views with formatted weather data
                cityNameText.setText(jsonObject.getString("name"));
                temperatureText.setText(String.format("%.0f°", temperature));
                humidityText.setText(String.format("%.0f%%", humidity));
                windText.setText(String.format("%.0f km/h", windSpeed));
                descriptionText.setText(description);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}