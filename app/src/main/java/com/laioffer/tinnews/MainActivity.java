package com.laioffer.tinnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.laioffer.tinnews.model.NewsResponse;
import com.laioffer.tinnews.network.NewsApi;
import com.laioffer.tinnews.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private NavController navController;//control to which destination

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);//get nav_view
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);//get host_fragment
        navController = navHostFragment.getNavController();//NavHostFragment contains NavController
        //using navigationUI to control the flow, connect nav_view with the controller, nav_graph.xml and bottom_nav_menu.xml got same id
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController);//set up tht title of the UI (actionbar) without this, will only show app name

        //newInstance is whole Api, give to newsApi to customize
        NewsApi api = RetrofitClient.newInstance(this).create(NewsApi.class);
        //callback in another thread, Async
        api.getTopHeadlines("US").enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                    if (response.isSuccessful()) {
                        Log.d("getTopHeadlines", response.body().toString());
                    } else {
                        Log.d("getTopHeadlines", response.toString());
                     }
             }
            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                    Log.d("getTopHeadlines", t.toString());
            }
        });
    }

    /**
     * return button in the app, left up corner, support the return to the last level
     * https://getaround.tech/android-parent-navigation/
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}