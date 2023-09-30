package com.example.appveterinaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class splash_activity extends AppCompatActivity {

   private static final long SPLASH_DURATION = 2000;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_splash);

      new Handler().postDelayed(()->{
         Intent intent = new Intent(splash_activity.this, MainActivity.class);
         startActivity(intent);
         finish();
      }, SPLASH_DURATION);
   }
}