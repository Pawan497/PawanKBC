package com.pawanyadav497.kbcwithpawan;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.pawanyadav497.kbcwithpawan.databinding.ActivityMainBinding;
import com.pawanyadav497.kbcwithpawan.ui.home.HomeViewModel;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;      //ye class option deti h  Navigation User Interface method ko jo interact krta hai appbar se
    private ActivityMainBinding binding;   //isne Find view by ID k jaroorat kam kr di h is class ka object bna k

    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);


        binding = ActivityMainBinding.inflate(getLayoutInflater());      // inflate creates instance of ActivityMain1Binding
            setContentView(binding.getRoot());

            View decorView = getWindow().getDecorView();
            int uiOptions =View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);

        setSupportActionBar(binding.appBarMain1.toolbar);
        getSupportActionBar().hide();// I can hide toolbar by getSupportActionBar().hide();

           DrawerLayout drawer = binding.drawerLayout;                            //Xml drawer layout ko drawer object m assign kr dia
           NavigationView navigationView = binding.navView;                      //Xml navView ko navigationView m assign kr dia
           // Passing each menu ID as a set of Ids because each
           // menu should be considered as top level destinations.
           mAppBarConfiguration = new AppBarConfiguration.Builder(             // new followed by call is used to initialise, builder ka use -> create new builder with specific set of destination
                   R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                   .setOpenableLayout(drawer)                                       // method displaying navigation button as a drawer symbol
                   .build();                                                        // creates instance(object) of class appConfiguration
           NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);  // Each host fragment have a NavController which instructs the navigation to occur
           NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration); // to add navigation support to default action bar
           NavigationUI.setupWithNavController(navigationView, navController);       // sets up a navigation view for use with  a navcontroller

        binding.appBarMain1.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeViewModel.signOut();
                Navigation.findNavController(MainActivity.this,R.id.nav_host_fragment_content_main).navigate(R.id.introFragment);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)       //Toast hi h ye
                        .setAction("Action", null).show();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {                                //initialize the contents of the Activity"s standard option menu
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main1_drawer, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {                                          //This method is called whenever the user chooses to navigate Up within your application's activity hierarchy from the action bar
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main); // NavHostFraqement is basically that part of your UI that's going to change whenever you change
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}