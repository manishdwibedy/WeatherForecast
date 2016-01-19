package weather.manish.com.weatherforecast.model;

/**
 * Created by manishdwibedy on 12/9/15.
 */
public class Generator {
    public static void main(String[] args)
    {
        for(int i=24;i<48;i++)
        {
            String line = "";
            if(i%2==0)
                line = "<TableRow" + "\n    android:padding=\"10dp\"" + "\n    android:id=\"@+id/row" + i + "\"" + "\n    android:visibility=\"invisible\"" +
                    "\n    android:background=\"#C9C9C9\"" + "\n    >" + "\n" + "\n    <TextView" + "\n" + "\n        android:text=\"02:00 AM\"" +
                    "\n        android:id=\"@+id/t" + i + "\"" + "\n        android:textSize=\"20dp\"" + "\n        android:textStyle=\"bold\"" + "\n        android:layout_weight=\"1\"" + "\n        />" + "\n" + "\n    <ImageView" + "\n        android:layout_width=\"30dp\"" + "\n        android:layout_height=\"30dp\"" +
                    "\n        android:id=\"@+id/i" + i + "\"" + "\n        android:layout_marginLeft=\"20dp\"" + "\n        android:src=\"@drawable/fb\"" + "\n        android:layout_gravity=\"center\"" + "\n        android:layout_weight=\"1\"/>" + "\n" + "\n    <TextView" +
                    "\n        android:id=\"@+id/temp" + i + "\"" + "\n        android:text=\"50\"" + "\n        android:textSize=\"20dp\"" + "\n        android:layout_column=\"15\"" + "\n        android:layout_gravity=\"right\"" + "\n        android:layout_weight=\"1\"/>" + "\n" + "\n" + "\n</TableRow>";
            else
                line = "<TableRow" + "\n    android:padding=\"10dp\"" + "\n    android:id=\"@+id/row" + i + "\"" + "\n    android:visibility=\"invisible\"" +
                        "\n    >" + "\n" + "\n    <TextView" + "\n" + "\n        android:text=\"02:00 AM\"" +
                        "\n        android:id=\"@+id/t" + i + "\"" + "\n        android:textSize=\"20dp\"" + "\n        android:textStyle=\"bold\"" + "\n        android:layout_weight=\"1\"" + "\n        />" + "\n" + "\n    <ImageView" + "\n        android:layout_width=\"30dp\"" + "\n        android:layout_height=\"30dp\"" +
                        "\n        android:id=\"@+id/i" + i + "\"" + "\n        android:layout_marginLeft=\"20dp\"" + "\n        android:src=\"@drawable/fb\"" + "\n        android:layout_gravity=\"center\"" + "\n        android:layout_weight=\"1\"/>" + "\n" + "\n    <TextView" +
                        "\n        android:id=\"@+id/temp" + i + "\"" + "\n        android:text=\"50\"" + "\n        android:textSize=\"20dp\"" + "\n        android:layout_column=\"15\"" + "\n        android:layout_gravity=\"right\"" + "\n        android:layout_weight=\"1\"/>" + "\n" + "\n" + "\n</TableRow>";

            System.out.print(line);
        }

    }
}
