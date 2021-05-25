package com.example.profileapplication4.ui.mainActivity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.profileapplication4.R
import com.example.profileapplication4.viewmodel.ViewModelFactory
import com.example.profileapplication4.ui.loginActivity.LogInActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {


    lateinit var mainViewModel: MainActivityViewModel


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnStart).setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
        }

        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]


    }
}