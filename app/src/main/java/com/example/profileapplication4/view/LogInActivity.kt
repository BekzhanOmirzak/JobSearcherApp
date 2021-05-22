package com.example.profileapplication4.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.profileapplication4.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnLogIn.setOnClickListener {
            userLogIn(it)
        }
        binding.txtRegister.setOnClickListener{
             startActivity(Intent(this,RegisterActivity::class.java))
        }


    }

    private fun userLogIn(it: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(it.applicationWindowToken, 0)
        val email = binding.edtAccount.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        binding.progressBar.visibility = View.VISIBLE
        if (!email.equals("") && !password.equals("")) {

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        Toast.makeText(this, "Log in with $user", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,MessageActivity::class.java))
                    } else {
                        Toast.makeText(
                            this,
                            "Ooops, something went wrong, check your both email and password!",
                            Toast.LENGTH_SHORT
                        ).show();
                    }
                    binding.progressBar.visibility = View.GONE
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this,
                        "Failure! $it",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar.visibility=View.GONE
                }

        } else {
            Toast.makeText(this, "Fill write your email and password!", Toast.LENGTH_SHORT).show();
        }

    }


}