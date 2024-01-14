package com.piekarskipiotr.remindmehere

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.piekarskipiotr.remindmehere.presentation.home.HomeActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}