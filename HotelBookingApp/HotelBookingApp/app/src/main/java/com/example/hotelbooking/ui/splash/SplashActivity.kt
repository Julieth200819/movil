package com.example.hotelbooking.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hotelbooking.ui.home.HomeActivity
import com.example.hotelbooking.ui.login.LoginActivity
import com.example.hotelbooking.utils.SessionManager

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Sin layout, solo decide a dónde ir
        val destination = if (SessionManager(this).isLoggedIn()) {
            HomeActivity::class.java
        } else {
            LoginActivity::class.java
        }
        startActivity(Intent(this, destination))
        finish()
    }
}
