package com.example.college;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    NavController navController;
    DrawerLayout drawer;
    NavigationView navigationView;
    AppBarConfiguration appBarConfiguration;
    BottomNavigationView bottomNavigationView;

    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (id)
                {
                    case R.id.log_out:
                    {
                        Intent intent= new Intent(MainActivity.this,login_activity.class);
                        startActivity(intent);
                        finish();
                        SharedPreferences preferences = getSharedPreferences("Login",MODE_PRIVATE);
                        preferences.edit().clear().commit();
                        break;

                    }
                    case R.id.payment:
                    {
                        Intent intent = new Intent(MainActivity.this,paymentgatway.class);
                        startActivity(intent);
                        break;
                    }
                }
                return true;
            }
        });
    }

    private void init()
    {
        drawer = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        navController = Navigation.findNavController(this,R.id.main);
        appBarConfiguration = new AppBarConfiguration.Builder(new int[]{R.id.admission,R.id.notes,R.id.enquiry,R.id.lecture,R.id.live,R.id.payment}).setDrawerLayout(drawer).build();

        View header= navigationView.getHeaderView(0);
        textView=header.findViewById(R.id.TV_nav_email);
        SharedPreferences preferences = getSharedPreferences("Login",MODE_PRIVATE);
        textView.setText(preferences.getString("email",null));
    }



    @Override
    public boolean onSupportNavigateUp()
    {

        return NavigationUI.navigateUp(navController,appBarConfiguration);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
