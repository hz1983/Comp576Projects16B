package com.example.hangzhao.bishare;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Hang Zhao on 26/09/2016.
 */
public class ScanFragment extends Fragment {

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_scan,container,false);

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//        values = getIntent().getExtras().getStringArrayList("values");
////        values = (ArrayList<CharSequence>) savedInstanceState.get("values");
//        Log.i("MapsActivity", values.toString());


        return rootView;
    }
}
