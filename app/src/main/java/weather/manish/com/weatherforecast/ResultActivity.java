package weather.manish.com.weatherforecast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import weather.manish.com.weatherforecast.model.DailyData;
import weather.manish.com.weatherforecast.model.ForecastResponse;

public class ResultActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    private String city;
    private String state;
    private String summary;
    private String temperature;
    private String icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_result);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar!=null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Results Activity");
        }

        String responseS = getIntent().getStringExtra("data");
        String city = getIntent().getStringExtra("city");
        String state = getIntent().getStringExtra("state");
        String degree = getIntent().getStringExtra("degree");

        Gson gson = new Gson();
        ForecastResponse response = gson.fromJson(responseS, ForecastResponse.class);

        setData(response, city, state, degree);

        shareFB();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setData(ForecastResponse response, String city, String state, String degree) {
        HashMap<String, String> units = Util.getUnits(degree.charAt(0));
        TextView summary = (TextView) findViewById(R.id.summary);
        this.state = state;
        this.city = city;
        summary.setText(response.getCurrently().getSummary() + " in " + city + ", " + state);

        this.summary = response.getCurrently().getSummary();
        this.temperature = Math.round(response.getCurrently().getTemperature()) + " " + (char) 0x00B0 + degree.substring(0,1).toUpperCase();
        this.icon = response.getCurrently().getIcon();


        TextView temperature = (TextView) findViewById(R.id.temperature);
        temperature.setText(Math.round(response.getCurrently().getTemperature())+"");

        TextView degreeUnit = (TextView) findViewById(R.id.degree_val);
        if(degree.toLowerCase().startsWith("f"))
            //temperature.setText(Html.fromHtml(Math.round(response.getCurrently().getTemperature()) + "<small><sup>\u2109</sup></small>"));
            degreeUnit.setText((char) 0x00B0 + "F");
        else
            //temperature.setText(Html.fromHtml(Math.round(response.getCurrently().getTemperature()) + "<small><sup>\u2103</sup></small>"));
            degreeUnit.setText((char) 0x00B0 + "C");

        ImageView summaryIcon = (ImageView) findViewById(R.id.icon);
        Util.getIcon(response.getCurrently().getIcon(), summaryIcon);

        DailyData firstDayData = response.getDaily().getData()[0];
        TextView lowHigh = (TextView) findViewById(R.id.lowHigh);
        lowHigh.setText("L:" + Math.round(firstDayData.getTemperatureMin()) + (char) 0x00B0
                + " | "
                + "H: " + Math.round(firstDayData.getTemperatureMax()) + (char) 0x00B0);

        TextView precipitation = (TextView) findViewById(R.id.precipitation);
        precipitation.setText(Util.getPrecipitation(firstDayData.getPrecipIntensity()));

        TextView rain = (TextView) findViewById(R.id.rain);
        rain.setText(Math.round(firstDayData.getPrecipProbability()) + "%");

        TextView wind = (TextView) findViewById(R.id.wind);
        wind.setText(Math.round(firstDayData.getWindSpeed()) + " " + units.get("wind"));


        TextView dew = (TextView) findViewById(R.id.dew);
        dew.setText(Math.round(firstDayData.getDewPoint()) + " " + units.get("dew"));

        TextView humidity = (TextView) findViewById(R.id.humidity);
        humidity.setText(Math.round(firstDayData.getHumidity()) + " %");

        TextView visibility = (TextView) findViewById(R.id.visibility);
        visibility.setText(Math.round(firstDayData.getVisibility()) + " " + units.get("visibility"));

        Date time = new Date();
        String timeFormat = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        sdf.setTimeZone(TimeZone.getTimeZone(response.getTimezone()));

        TextView sunset = (TextView) findViewById(R.id.sunrise);
        time.setTime(firstDayData.getSunsetTime() * 1000);
        sunset.setText(sdf.format(time));

        TextView sunrise = (TextView) findViewById(R.id.sunset);
        time.setTime(firstDayData.getSunriseTime() * 1000);
        sunrise.setText(sdf.format(time));


    }

    private void shareFB() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        final ShareDialog shareDialog = new ShareDialog(this);
        ImageButton fb_share = (ImageButton) findViewById(R.id.fbShare);

        fb_share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Current Weather in " + city +", " + state)
                            .setContentDescription(
                                    summary + ", " + temperature)
                            .setContentUrl(Uri.parse("http://forecast.io"))
                            .setImageUrl(Uri.parse(Util.getPublicIcon(icon)))
                            .build();

                    shareDialog.show(linkContent);
                }
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(getApplicationContext(), "Posted share successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "Post Cancelled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void showDetails(View view) {
        Intent intent = new Intent(getApplicationContext(),DetailsActivity.class);

        String responseS = getIntent().getStringExtra("data");
        String city = getIntent().getStringExtra("city");
        String state = getIntent().getStringExtra("state");
        String degree = getIntent().getStringExtra("degree");

        intent.putExtra("data",getIntent().getStringExtra("data"));
        intent.putExtra("degree", degree);
        intent.putExtra("city", city);
        intent.putExtra("state", state);


        startActivity(intent);

    }

    public void openMap(View view)
    {
        Intent intent = new Intent(getApplicationContext(),MapActivity.class);
        String responseS = getIntent().getStringExtra("data");
        intent.putExtra("data",getIntent().getStringExtra("data"));

        startActivity(intent);
    }

}