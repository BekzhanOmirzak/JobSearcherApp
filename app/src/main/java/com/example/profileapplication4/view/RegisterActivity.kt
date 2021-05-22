package com.example.profileapplication4.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.profileapplication4.R
import com.example.profileapplication4.databinding.ActivityRegisterBinding
import com.example.profileapplication4.models.Employee
import com.example.profileapplication4.models.Employer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val firebaseAuth = FirebaseAuth.getInstance();

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

    }

    private fun registerTheUser(it: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(it.applicationWindowToken, 0)
        binding.progressBar.visibility = View.VISIBLE
        val email = binding.edtAccount.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        if (!email.equals("") && !password.equals("")) {

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    when (binding.radioGroup.checkedRadioButtonId) {
                        R.id.radioJobSearcher -> {
                            val employee = Employee(
                                firebaseAuth.currentUser!!.uid,
                                email,
                                binding.edtName.text.toString(),
                                binding.edtSurName.text.toString(),
                                password
                            )
                            Log.i("Bekzhan", "registerTheUser: ${employee.employee_id}")
                            val fireStore = FirebaseFirestore.getInstance()
                            fireStore.collection("employees").document(employee.employee_id)
                                .set(employee)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        Toast.makeText(
                                            this, "Success with email $email", Toast
                                                .LENGTH_SHORT
                                        ).show();
                                        binding.progressBar.visibility = View.GONE
                                        startActivity(Intent(this, MessageActivity::class.java))
                                    } else {
                                        Toast.makeText(
                                            this, "UnSuccessful with email $email", Toast
                                                .LENGTH_SHORT
                                        ).show();
                                    }
                                }.addOnFailureListener {
                                    Toast.makeText(
                                        this,
                                        "Exception is thrown $it",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    binding.progressBar.visibility = View.GONE
                                }
                        }
                        R.id.radioJobGiver -> {
                            val employer = Employer(
                                firebaseAuth.currentUser!!.uid,
                                email,
                                password,
                                binding.edtName.text.toString(),
                                binding.edtSurName.text.toString()
                            )
                            val fireStore = FirebaseFirestore.getInstance()
                            fireStore.collection("employers").document(employer.employer_id)
                                .set(employer).addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        Toast.makeText(
                                            this,
                                            "Success! ${employer.email}",
                                            Toast.LENGTH_SHORT
                                        ).show();
                                        startActivity(Intent(this, MessageActivity::class.java))
                                    }
                                }.addOnFailureListener {
                                    Toast.makeText(this, "Exception is thrown", Toast.LENGTH_SHORT)
                                        .show();
                                    startActivity(Intent(this, MessageActivity::class.java))
                                }
                            binding.progressBar.visibility = View.GONE
                        }
                    }

                }

            }.addOnFailureListener {
                Toast.makeText(this, "Failure $it", Toast.LENGTH_SHORT).show();
                binding.progressBar.visibility = View.GONE
            }
        }

    }
}