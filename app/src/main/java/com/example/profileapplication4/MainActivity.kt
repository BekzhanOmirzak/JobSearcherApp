package com.example.profileapplication4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.profileapplication4.di.viewmodelfactory.ViewModelFactory
import com.example.profileapplication4.view.LogInActivity
import com.example.profileapplication4.viewmodel.MainViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {


    lateinit var mainViewModel: MainViewModel


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnStart).setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
        }

        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        findViewById<Button>(R.id.btnStart).setText(mainViewModel.provideString())

    }
}