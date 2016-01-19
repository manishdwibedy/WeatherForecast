package weather.manish.com.weatherforecast;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.hamweather.aeris.communication.AerisEngine;

import weather.manish.com.weatherforecast.model.ForecastResponse;

//public class MapActivity extends AppCompatActivity {
public class MapActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");

        double latitude=0;
        double longitude=0;

        ForecastResponse response = new Gson().fromJson(data,ForecastResponse.class);
        latitude = Double.parseDouble(response.getLatitude());
        longitude = Double.parseDouble(response.getLongitude());

        AerisEngine.initWithKeys(this.getString(R.string.aeris_client_id), this.getString(R.string.aeris_client_secret), this);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putString("latitude", Double.toString(latitude));
        bundle.putString("longitude", Double.toString(longitude));

        MapFragment mapFragment = new MapFragment();

        mapFragment.setArguments(bundle);

        fragmentTransaction.add(R.id.ForecastMap,mapFragment);
        fragmentTransaction.commit();
    }

}
