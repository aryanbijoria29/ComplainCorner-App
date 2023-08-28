package com.celes.complaincorner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class authorityActivity extends AppCompatActivity {
    TextView textView;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int backbuttoncount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority);

        sharedPreferences = getSharedPreferences("authLogin", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        String temp = sharedPreferences.getString("authTypeTemp", "default :(");
        if(sharedPreferences.getString("isLogin", "no").equals("no")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        String test = i.getStringExtra("authType");

        replaceFragment1(new homeFragmentAuthority());

        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home:
                        replaceFragment1(new homeFragmentAuthority());
                        break;
                    case R.id.profile:
                        replaceFragment1(new profileFragmentAuthority());
                        break;
                }
                return true;
            }
        });
    }

    private void replaceFragment1(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {
        if(backbuttoncount>=1){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            backbuttoncount=0;
        }
        else{
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backbuttoncount++;
        }
    }
}