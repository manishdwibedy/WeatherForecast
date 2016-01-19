package weather.manish.com.weatherforecast;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        EditText street = (EditText)findViewById(R.id.streetValue);
        EditText city = (EditText)findViewById(R.id.cityValue);

        //For testing
        //street.setText("2656 Ellendale Pl.");
        //city.setText("Los Angeles");

        ImageButton logo = (ImageButton) findViewById(R.id.logo);

        logo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://forecast.io"));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void submitForm(View view){

        EditText street = (EditText)findViewById(R.id.streetValue);
        EditText city = (EditText)findViewById(R.id.cityValue);
        Spinner stateSpinner=(Spinner) findViewById(R.id.stateValue);
        RadioGroup degreeGroup = (RadioGroup) findViewById(R.id.degreeGroup);
        TextView errorMsg = (TextView)findViewById(R.id.errorMsg);

        int selectedDegree = degreeGroup.getCheckedRadioButtonId();
        RadioButton degreeButton = (RadioButton) findViewById(selectedDegree);



        String message = "";
        String streetValue = street.getText().toString();
        String cityValue = city.getText().toString();
        String degreeValue = degreeButton.getText().toString();
        String stateValue = stateSpinner.getSelectedItem().toString();
        Boolean errorsFound = false;

        if(streetValue.equalsIgnoreCase(""))
        {
            errorsFound = true;
            message = "Street value is empty.";
        }
        else if(cityValue.equalsIgnoreCase(""))
        {
            errorsFound = true;
            message = "City value is empty.";
        }
        else if(stateValue.equalsIgnoreCase("Select a state"))
        {
            errorsFound = true;
            message = "Select a state";
        }

        errorMsg.setText(message);

        if(!errorsFound)
        {

            try{
        AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.execute(this.getApplicationContext(),streetValue,cityValue,stateValue,degreeValue.subSequence(0,1));
            }
            catch(Exception e)
            {
            }
        }
    }

    public void clearForm(View view) {
        EditText street = (EditText)findViewById(R.id.streetValue);
        EditText city = (EditText)findViewById(R.id.cityValue);
        Spinner stateSpinner=(Spinner) findViewById(R.id.stateValue);
        RadioButton degree = (RadioButton) findViewById(R.id.fah);
        TextView errorMsg = (TextView)findViewById(R.id.errorMsg);

        street.setText("");
        city.setText("");
        stateSpinner.setSelection(0);
        degree.setChecked(true);
        errorMsg.setText("");
    }


    public static String httpGet(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        if (conn.getResponseCode() != 200) {
            throw new IOException(conn.getResponseMessage());
        }

        // Buffer the result into a string
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();

        conn.disconnect();
        return sb.toString();
    }

    public void getInfo(View view) {
        Intent myIntent = new Intent(MainActivity.this, InfoActivity.class);
        // run the activity
        startActivity(myIntent);
    }


    private class AsyncTaskRunner extends AsyncTask<Object, String, String> {

        private String resp;
        private Context activityContext;
        private String state;
        private String city;
        private String degree;

        @Override
        protected String doInBackground(Object... params) {
            String data = "";
            try{
                activityContext = (Context)params[0];

                state = (String)params[3];
                city = (String)params[2];
                degree = (String)params[4];

                String forecastURL = "http://cs-server.usc.edu:24089/index.php?" +
                        "street=" + URLEncoder.encode((String)params[1], "UTF-8") +
                        "&city=" + URLEncoder.encode((String)params[2], "UTF-8") +
                        "&state=" + URLEncoder.encode((String)params[3], "UTF-8") +
                        "&degree=" + URLEncoder.encode((String)params[4], "UTF-8");


                URL url = new URL(forecastURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if (conn.getResponseCode() != 200) {
                    throw new IOException(conn.getResponseMessage());
                }

                // Buffer the result into a string
                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }
                rd.close();

                conn.disconnect();

                resp = sb.toString();
                Log.e("","");

            }
            catch(Exception e)
            {
                data = "Error!!";
            }

            return data;
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            if(resp.equalsIgnoreCase("No result"))
            {
                Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_LONG).show();

                EditText street = (EditText)findViewById(R.id.streetValue);
                EditText city = (EditText)findViewById(R.id.cityValue);
                Spinner stateSpinner=(Spinner) findViewById(R.id.stateValue);
                RadioButton degree = (RadioButton) findViewById(R.id.fah);

                street.setText("");
                city.setText("");
                stateSpinner.setSelection(0);
                degree.setChecked(true);
            }
            else
            {
                Intent i = new Intent(activityContext, ResultActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("data", resp);
                i.putExtra("state", state);
                i.putExtra("city",city);
                i.putExtra("degree",degree);
                activityContext.startActivity(i);
            }

        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onPreExecute()
         */
        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
        }

        /*
         * (non-Javadoc)
         *
         * @see android.os.AsyncTask#onProgressUpdate(Progress[])
         */
        @Override
        protected void onProgressUpdate(String... text) {
            //Toast.makeText(getApplicationContext(), text[0], Toast.LENGTH_SHORT).show();
            //activityContext.startActivity(new Intent(activityContext, ResultActivity.class));

            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }
    }
}
