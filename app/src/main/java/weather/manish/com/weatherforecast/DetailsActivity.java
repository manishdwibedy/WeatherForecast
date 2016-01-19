package weather.manish.com.weatherforecast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import weather.manish.com.weatherforecast.model.DailyData;
import weather.manish.com.weatherforecast.model.ForecastResponse;
import weather.manish.com.weatherforecast.model.HourlyData;

public class DetailsActivity extends AppCompatActivity {

    View view24Hours;
    View view7Days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Details Activity");

        Button button_7days = (Button) findViewById(R.id.next7daysButton);

        button_7days.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout r1 = (RelativeLayout) findViewById(R.id.tab24);
                r1.setVisibility(View.GONE);
                RelativeLayout r2 = (RelativeLayout) findViewById(R.id.tab7);
                r2.setVisibility(View.VISIBLE);

                Button daysBtn = (Button) findViewById(R.id.next7daysButton);
                daysBtn.setBackgroundResource(R.drawable.selected);
                Button hoursBtn = (Button) findViewById(R.id.next24hoursButton);
                hoursBtn.setBackgroundResource(R.drawable.unselected);


            }
        });

        Button button_24hours = (Button) findViewById(R.id.next24hoursButton);


        button_24hours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout r1 = (RelativeLayout) findViewById(R.id.tab7);
                r1.setVisibility(View.GONE);
                RelativeLayout r2 = (RelativeLayout) findViewById(R.id.tab24);
                r2.setVisibility(View.VISIBLE);
                Button daysBtn = (Button) findViewById(R.id.next7daysButton);
                daysBtn.setBackgroundResource(R.drawable.unselected);
                Button hoursBtn = (Button) findViewById(R.id.next24hoursButton);
                hoursBtn.setBackgroundResource(R.drawable.selected);


            }
        });

        //button_7days.performClick();
        //button_24hours.performClick();


        view24Hours = findViewById(R.id.tab24);
        view7Days = findViewById(R.id.tab7);

        view24Hours.setVisibility(View.VISIBLE);
        setData();
    }

    private void setData(){
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        String degree = intent.getStringExtra("degree");
        String city = intent.getStringExtra("city");
        String state = intent.getStringExtra("state");

        TextView temp = (TextView)findViewById(R.id.Temp);
        temp.setText("Temp(" + (char) 0x00B0 + degree + ")");


        TextView details = (TextView)findViewById(R.id.details);
        details.setText("More Details for " + city + ", " + state);

        ForecastResponse response = new Gson().fromJson(data, ForecastResponse.class);

        HourlyData[] hourly = response.getHourly().getData();
        for(int index = 0; index < 48 ; index++)
        {
            String imageid = "iconH_"+index;
            int resID = getResources().getIdentifier(imageid, "id", getPackageName());
            ImageView iv = (ImageView)findViewById(resID);
            Util.getIcon(hourly[index + 1].getIcon(), iv);

            Date time = new Date();
            String timeFormat = "hh:mm a";

            time.setTime(hourly[index+1].getTime() * 1000);

            SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
            sdf.setTimeZone(TimeZone.getTimeZone(response.getTimezone()));
            String timeid = "timeH_"+index;
            int resID_time = getResources().getIdentifier(timeid, "id", getPackageName());
            TextView tv_time = (TextView)findViewById(resID_time);
            tv_time.setText(sdf.format(time));


            String tempid = "tempH_"+index;
            int resID_temp = getResources().getIdentifier(tempid, "id", getPackageName());
            TextView tv_temp = (TextView)findViewById(resID_temp);
            tv_temp.setText(Math.round(hourly[index+1].getTemperature())+"");
        }

        DailyData[] daily = response.getDaily().getData();

        for (int index=0;index<7;index++)
        {
            String imageid = "icon7_"+index;
            int resID = getResources().getIdentifier(imageid, "id", getPackageName());
            ImageView iv = (ImageView)findViewById(resID);
            Util.getIcon(daily[index + 1].getIcon(), iv);

            Date time = new Date();
            String timeFormat = "EEEE, MMM dd";

            time.setTime(daily[index+1].getTime() * 1000);

            SimpleDateFormat sdf =  new SimpleDateFormat(timeFormat);
            sdf.setTimeZone(TimeZone.getTimeZone(response.getTimezone()));

            String timeid = "day7_"+index;
            int resID_time = getResources().getIdentifier(timeid, "id", getPackageName());
            TextView tv_time = (TextView)findViewById(resID_time);
            tv_time.setText(sdf.format(time));


            String tempid = "temp7_"+index;
            int resID_temp = getResources().getIdentifier(tempid, "id", getPackageName());
            TextView tv_temp = (TextView)findViewById(resID_temp);
            tv_temp.setText("Min: " + Math.round(daily[index+1].getTemperatureMin()) + (char) 0x00B0 + degree +
                            " | Max: " + Math.round(daily[index+1].getTemperatureMax()) + (char) 0x00B0 + degree);

        }

    }

    public void showMore(View view)
    {
        TableRow layout1=(TableRow) findViewById(R.id.showMoreRows);
        layout1.setVisibility(View.GONE);

        for(int index = 24; index < 48; index++)
        {
            String imageid = "row" + index;
            int resID = getResources().getIdentifier(imageid, "id", getPackageName());
            TableRow iv = (TableRow)findViewById(resID);
            iv.setVisibility(View.VISIBLE);
        }
    }

}
