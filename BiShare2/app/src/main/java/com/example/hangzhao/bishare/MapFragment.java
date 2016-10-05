package com.example.hangzhao.bishare;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hang Zhao on 26/09/2016.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    ArrayList<String> values;
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_map,container,false);

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//        values = getIntent().getExtras().getStringArrayList("values");
////        values = (ArrayList<CharSequence>) savedInstanceState.get("values");
//        Log.i("MapsActivity", values.toString());


        return rootView;



    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        com.google.android.gms.maps.MapFragment fragment = (com.google.android.gms.maps.MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        //        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        GoogleMap mGoogleMap = map;

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
//             mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
//                 @Override
//                 public boolean onMyLocationButtonClick() {
//                     // custom code at here:
//                     return true;
//                 }
//             });
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.setBuildingsEnabled(true);
//            Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();

            displayAllBayStation(mGoogleMap);

            List<String> groupList = new ArrayList<>();
            Map<String,List<String>> inputMap = new HashMap<>();
            for(int i=0;i<values.size();i++){
//                values.add(bay.get("d_id").toString());
//                values.add(longitude+"");
//                values.add(latitude+"");
//                values.add(bicycle_num+"");
                if(i%4==0){
                    groupList = new ArrayList<>();
                    String d_id = values.get(i);
                    inputMap.put(d_id,groupList);
                }
                groupList.add(values.get(i));
            }


            LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(14).build();
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                for(Map.Entry<String,List<String>> entry:inputMap.entrySet()){
                    List<String> dock_loc = entry.getValue();
                    LatLng docker = new LatLng(Double.parseDouble(dock_loc.get(2)), Double.parseDouble(dock_loc.get(1)));
                    map.addMarker(new MarkerOptions().position(docker).title(dock_loc.get(0)+" has bicycles "+dock_loc.get(3)));
                }
            } else {
                Toast.makeText(getActivity(), "location == null", Toast.LENGTH_LONG).show();
            }
        } else {
            // show rationale and request permission.
            Toast.makeText(getActivity(), "Permission Not Granted", Toast.LENGTH_LONG).show();
        }
    }

    public void displayAllBayStation(GoogleMap currentMap) {
        // custom codes here to display all docking bay's location with its capacities.
        if (null != currentMap) {
            //
            new Thread(new Runnable() {
                public void run() {
                    try {
                        URL url = new URL("http://localhost:8088/BiShareJettyServlet/BiShareServlet");
                        URLConnection connection = url.openConnection();
                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String returnedJsonString = "";
                        String line = null;
                        while ((line = in.readLine()) != null) {
                            returnedJsonString += line;
                        }
                        in.close();
                    } catch (Exception e) {
                        Log.d("Exception", e.toString());
                    }
                }
            }).start();
        }
    }
}

/*/
    https://developers.google.com/maps/documentation/android-api/
    https://developers.google.com/places/android-api/

    https://developers.google.com/maps/documentation/android-api/start
    https://developers.google.com/maps/documentation/android-api/marker
    https://developers.google.com/maps/documentation/android-api/infowindows
    https://developers.google.com/maps/documentation/android-api/utility/geojson
    http://code.tutsplus.com/tutorials/getting-started-with-google-maps-for-android-basics--cms-24635

    http://www.veereshr.com/Android/AndroidToServlet
    http://hmkcode.com/android-send-json-data-to-server/

    Run Apps on a Hardware Device:
    https://developer.android.com/studio/run/device.html

    Design Pattern: Builder
    https://developers.google.com/android/guides/api-client
//*/
