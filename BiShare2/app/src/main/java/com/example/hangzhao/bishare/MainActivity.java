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

    private void showFragment(int menuId, boolean isReselected) {
//        ft = getSupportFragmentManager().beginTransaction();
        FragmentManager fm = getFragmentManager();

        if (menuId == R.id.locations) {
            Fragment mapFragment = new MapFragment();
            fm.beginTransaction().replace(R.id.activity_main, mapFragment).commit();
        }

//        else if (menuId == R.id.scan) {
//            Toast.makeText(this, "Scan Button Selected", Toast.LENGTH_SHORT).show();
//            ft.replace(R.id.fragment_place_holder, new StatusFragment());
//        } else {
//            Toast.makeText(this, "Status Button Selected", Toast.LENGTH_SHORT).show();
//            ft.replace(R.id.fragment_place_holder, new ScanFragment());
//        }
//        ft.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        bottomBar.onSaveInstanceState(outState);
    }
}
