package com.example.profileapplication4.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.profileapplication4.databinding.ActivityEmployerEmployeeBinding
import com.example.profileapplication4.models.ChatRoom
import com.example.profileapplication4.models.Employee
import com.example.profileapplication4.models.Employer
import com.example.profileapplication4.ui.messageActivity.MessageActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class EmployerEmployeeActivity : AppCompatActivity() {


    private lateinit var binding: ActivityEmployerEmployeeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployerEmployeeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val user_type = getSharedPreferences("db_name", MODE_PRIVATE).getString("user", "None")
        Log.i("Bekzhan", "onCreate: type_user inside employeremployeeactivity $user_type")
        if (user_type != null) {
            if (user_type.equals("employer")) {
                binding.viewAnnouncement.setText("Жұмыс тәжірбиесі")
            }
        }

        val user_ref = intent.extras?.getString("user_ref")
        Log.i("Bekzhan", "onCreate: user_ref $user_ref ")
        if (user_ref != null) {
            if (user_type.equals("employee"))
                FirebaseFirestore.getInstance().collection("employers").document(user_ref)
                    .get().addOnCompleteListener {
                        if (it.isSuccessful) {
                            val employer = it.result?.toObject(Employer::class.java)
                            if (employer != null) {
                                settingTheDetails(employer)
                            }
                        }
                    }

            if (user_type.equals("employer")) {
                FirebaseFirestore.getInstance().collection("employees").document(user_ref)
                    .get().addOnCompleteListener {
                        if (it.isSuccessful) {
                            val employee = it.result?.toObject(Employee::class.java)
                            if (employee != null) {
                                settingTheDetails(employee)
                            }
                        }
                    }
            }
        }

        binding.imgMessageIcon.setOnClickListener {

            val chatroom_id = FirebaseDatabase.getInstance().reference.push().key

            val chatRoom = ChatRoom(
                chatroom_id!!,
                FirebaseAuth.getInstance().currentUser!!.uid,
                user_ref!!,
            )

            FirebaseDatabase.getInstance().reference.child("chatrooms").child(chatroom_id)
                .setValue(chatRoom)
            startActivity(Intent(this, MessageActivity::class.java))

        }


    }

    private fun settingTheDetails(employer: Employer) {
        binding.txtNameSurName.setText(employer.name + "  " + employer.surname)
        binding.txtPhoneNumber.setText(employer.phone_number)
        binding.txtLocation.setText(employer.city + "," + employer.district + "," + employer.village)
        Glide.with(this).asBitmap().load(employer.image_url).into(binding.circleImageView)
    }

    private fun settingTheDetails(employee: Employee) {
        binding.txtNameSurName.setText(employee.name + "  " + employee.surname)
        binding.txtPhoneNumber.setText(employee.phone_number)
        binding.txtLocation.setText(employee.city + "," + employee.district + "," + employee.village)
        Glide.with(this).asBitmap().load(employee.image_url).into(binding.circleImageView)
    }


}