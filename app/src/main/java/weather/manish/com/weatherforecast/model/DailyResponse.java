package weather.manish.com.weatherforecast.model;

/**
 * Created by manishdwibedy on 12/7/15.
 */
public class DailyResponse {
    private String summary;
    private String icon;
    private DailyData[] data;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public DailyData[] getData() {
        return data;
    }

    public void setData(DailyData[] data) {
        this.data = data;
    }
}
