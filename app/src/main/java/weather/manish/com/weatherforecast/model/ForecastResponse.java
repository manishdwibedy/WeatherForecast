package weather.manish.com.weatherforecast.model;

/**
 * Created by Manish on 11/22/2015.
 */
public class ForecastResponse {

    String latitude;
    String longitude;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    String timezone;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    private CurrentlyResponse currently;

    public DailyResponse getDaily() {
        return daily;
    }

    public void setDaily(DailyResponse daily) {
        this.daily = daily;
    }

    private DailyResponse daily;

    private HourlyResponse hourly;

    public HourlyResponse getHourly() {
        return hourly;
    }

    public void setHourly(HourlyResponse hourly) {
        this.hourly = hourly;
    }

    public CurrentlyResponse getCurrently() {
        return currently;
    }

    public void setCurrently(CurrentlyResponse currently) {
        this.currently = currently;
    }
}
