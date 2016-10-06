package com.example.hangzhao.bishare;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.IdRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    FragmentTransaction ft;
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.activity_main);

        bottomBar = BottomBar.attach(this, savedInstanceState);
        bottomBar.setItems(R.menu.bottombar_menu);
        bottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                showFragment(menuItemId, false);
            }
            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                showFragment(menuItemId, true);
            }
        } );

    }

    Fragment mapFragment;
    Fragment scanFragment;
    Fragment statusFragment;

    private void showFragment(int menuId, boolean isReselected) {
//        ft = getSupportFragmentManager().beginTransaction();
        FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction ft = fm.beginTransaction();
        if (menuId == R.id.locations) {

            if(scanFragment!=null)ft.hide(scanFragment);
            if(statusFragment!=null)ft.hide(statusFragment);

            if(mapFragment==null){
                mapFragment = new MapFragment();
                ft.add(R.id.map_fragment,mapFragment);
            }else{
                ft.show(mapFragment);
            }


        }
        else if (menuId == R.id.scan) {
            if(mapFragment!=null)ft.hide(mapFragment);
            if(statusFragment!=null)ft.hide(statusFragment);

            if(scanFragment==null){
                scanFragment = new ScanFragment();
                ft.add(R.id.map_fragment,scanFragment);
            }else{
                ft.show(scanFragment);
            }

        } else {
            if(mapFragment!=null)ft.hide(mapFragment);
            if(scanFragment!=null)ft.hide(scanFragment);

            if(statusFragment==null){
                statusFragment = new StatusFragment();
                ft.add(R.id.map_fragment,statusFragment);
            }else{
                ft.show(statusFragment);
            }
        }
        ft.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        bottomBar.onSaveInstanceState(outState);
    }
}
