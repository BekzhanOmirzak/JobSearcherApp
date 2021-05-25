package com.example.profileapplication4.ui.loginActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.profileapplication4.databinding.ActivityLogInBinding
import com.example.profileapplication4.ui.messageActivity.MessageActivity
import com.example.profileapplication4.ui.registerActivity.RegisterActivity
import com.example.profileapplication4.viewmodel.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class LogInActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var loginActivityViewModel: LoginActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnLogIn.setOnClickListener {
            userLogIn(it)
        }
        binding.txtRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        loginActivityViewModel =
            ViewModelProvider(this, viewModelFactory)[LoginActivityViewModel::class.java]

        subscribeToLiveData()

    }

    private fun userLogIn(it: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(it.applicationWindowToken, 0)
        val email = binding.edtAccount.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        if (!email.equals("") && !password.equals("")) {
            loginActivityViewModel.signInEmailPassword(email, password)
        } else {
            Toast.makeText(this, "Fill write your email and password!", Toast.LENGTH_SHORT).show();
        }
    }

    private fun subscribeToLiveData() {
        loginActivityViewModel.observeLiveData().observe(this) { resources ->
            if (resources != null) {
                when (resources.status) {
                    Resource.Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this,
                            "Error occurred please check your email and password",
                            Toast.LENGTH_SHORT
                        ).show();
                    }
                    Resource.Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    Resource.Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this,
                            "Success with email ${ resources.data}",
                            Toast.LENGTH_SHORT
                        ).show();
                        startActivity(Intent(this, MessageActivity::class.java))
                    }
                }
            }
        }
    }


}