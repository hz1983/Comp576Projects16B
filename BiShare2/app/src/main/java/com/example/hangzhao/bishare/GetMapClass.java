package com.example.hangzhao.bishare;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Hang Zhao on 18/09/2016.
 */
public class GetMapClass extends AsyncTask<String, Void, String>{

    private AppCompatActivity activity;


    public GetMapClass(AppCompatActivity activity){
        this.activity = activity;
    }


    @Override
    protected String doInBackground(String... strings) {
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL("http://172.20.10.2:8088/BiShareJettyServlet/BiShareServlet?type=map");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");

            //add request header

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //print result
            Log.i("GetMapClass",response.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            Intent intent = new Intent(activity, MapFragment.class);
            JSONArray bays = new JSONArray(s);
            Bundle bundle = new Bundle();
            ArrayList<String> values = new ArrayList<>();
            for(int i=0;i<bays.length();i++){
                JSONObject bay = bays.getJSONObject(i);
                JSONArray bicycles = new JSONArray(bay.get("bicycles").toString());
                double longitude = (double) bay.get("longitude");
                double latitude = (double) bay.get("latitude");
                int bicycle_num = bicycles.length();
                values.add(bay.get("d_id").toString());
                values.add(longitude+"");
                values.add(latitude+"");
                values.add(bicycle_num+"");
                Log.i("GetMapClass","longitude="+longitude+",latitude="+latitude+",bicycle_num="+bicycle_num);
            }
            bundle.putStringArrayList("values",values);
            intent.putExtras(bundle);
            activity.startActivity(intent);
        } catch (JSONException e) {
            Log.e("GetMapClass",e.toString());
        }

    }
}
