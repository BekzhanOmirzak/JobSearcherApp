package com.example.profileapplication4.ui.registerActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.profileapplication4.R
import com.example.profileapplication4.databinding.ActivityRegisterBinding
import com.example.profileapplication4.models.Employee
import com.example.profileapplication4.models.Employer
import com.example.profileapplication4.ui.loginActivity.LogInActivity
import com.example.profileapplication4.ui.loginActivity.Resource
import com.example.profileapplication4.ui.messageActivity.MessageActivity
import com.example.profileapplication4.viewmodel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class RegisterActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var registerActivityViewModel: RegisterActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnRegister.setOnClickListener { it ->
            registerTheUser(it)
        }
        binding.txtHaveAccount.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
        }

        registerActivityViewModel =
            ViewModelProvider(this, viewModelFactory)[RegisterActivityViewModel::class.java]

        subscribingRegisterLiveData()
    }

    private fun registerTheUser(it: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(it.applicationWindowToken, 0)
        try {
            val email = binding.edtAccount.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            val name = binding.edtName.text.toString().trim()
            val surname = binding.edtSurName.text.toString().trim()
            registerActivityViewModel.registeringTheUser(
                email,
                password,
                name,
                surname,
                binding.radioGroup.checkedRadioButtonId
            )

        } catch (ex: Exception) {
            Toast.makeText(this, "Please fill  all the blanks", Toast.LENGTH_SHORT).show();
            binding.progressBar.visibility = View.GONE

        }
    }

    private fun subscribingRegisterLiveData() {
        registerActivityViewModel.observingLiveDataRegisteringUser().observe(this) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Success with email ${it.data}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MessageActivity::class.java));
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Error occurred ${it.message}", Toast.LENGTH_SHORT).show();
                }
                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

            }
        }
    }
}