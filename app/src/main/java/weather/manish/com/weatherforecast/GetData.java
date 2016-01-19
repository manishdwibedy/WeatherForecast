package weather.manish.com.weatherforecast;

/**
 * Created by Manish on 11/21/2015.
 */

/*
public class GetData extends AsyncTask<String , Void, Void>
{

    private String resp;

    protected String doInBackground(String... params) {
        publishProgress("Sleeping..."); // Calls onProgressUpdate()
        try {
            // Do your long operations here and return the result
            int time = Integer.parseInt(params[0]);
            // Sleeping for given time period
            Thread.sleep(time);
            resp = "Slept for " + time + " milliseconds";
        } catch (InterruptedException e) {
            e.printStackTrace();
            resp = e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            resp = e.getMessage();
        }
        return resp;
    }

    */
/*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     *//*

    @Override
    protected void onPostExecute(String result) {
        // execution of result of Long time consuming operation
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
    }

    */
/*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#onPreExecute()
     *//*

    @Override
    protected void onPreExecute() {
        // Things to be done before execution of long running operation. For
        // example showing ProgessDialog
    }

    */
/*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#onProgressUpdate(Progress[])
     *//*

    @Override
    protected void onProgressUpdate(String... text) {
        Toast.makeText(getApplicationContext(), text[0], Toast.LENGTH_SHORT).show();
        // Things to be done while execution of long running operation is in
        // progress. For example updating ProgessDialog
    }

    */
/*String data;
    @Override
    protected void doInBackground(String... urls)
    {
        URL url = new URL(urls[0]);
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

        data = Util.getLatLong(sb.toString());
        //return sb.toString();
    }

    @Override
    protected void onProgressUpdate(Integer... progress)
    {
        //setProgressPercent(progress[0]);
    }

    @Override
    protected void onPostExecute(Long result) {
        //showDialog("Downloaded " + result + " bytes");
        Toast.makeText(getApplicationContext(), dat, Toast.LENGTH_SHORT).show();
    }*//*

}
*/
