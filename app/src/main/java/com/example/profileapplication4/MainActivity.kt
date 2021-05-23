package com.example.profileapplication4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.profileapplication4.view.LogInActivity
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnStart).setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
        }



    }
}