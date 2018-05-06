package com.hobbythai.android.findfriend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.hobbythai.android.findfriend.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set first fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentMainFragment, new MainFragment())
                    .commit();
        }

    } // main method

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Toast.makeText(MainActivity.this,"Can Press!",
                Toast.LENGTH_SHORT).show();
    }


} //main class
