package weather.manish.com.weatherforecast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.MapViewFragment;
import com.hamweather.aeris.tiles.AerisTile;

/**
 * Created by manishdwibedy on 12/8/15.
 */
public class MapFragment extends MapViewFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.content_map_fragment,container,false);

        mapView = (AerisMapView)view.findViewById(R.id.aerisfragment_map);
        mapView.init(savedInstanceState, AerisMapView.AerisMapType.GOOGLE);

        Bundle bundle = getArguments();
        String latitudeS = bundle.getString("latitude");
        String longitudeS = bundle.getString("longitude");

        double latitude = Double.parseDouble(latitudeS);
        double longitude = Double.parseDouble(longitudeS);
        loadMap(latitude, longitude);

        return view;
    }

    private void loadMap(double latitude, double longitude)
    {
        mapView.moveToLocation(new LatLng(latitude, longitude), 10);
        mapView.addLayer(AerisTile.RADSAT);
    }
}
